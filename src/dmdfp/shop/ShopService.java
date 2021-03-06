package dmdfp.shop;

import dmdfp.share.Cloudy;
import dmdfp.share.Environment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.transform.XSLTransformer;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by khk on 3/2/14.
 */
@Path("shop")
public class ShopService
{
    private static final String
            ENV = "env",
            CLOUDY = "cloudy",
            DOCUMENT = "document",
            ITEM = "item",
            ITEM_ID = "itemID",
            ITEM_NAME = "itemName",
            ITEM_URL = "itemURL",
            ITEM_PRICE = "itemPrice",
            ITEM_STOCK = "itemStock",
            ITEM_DESCRIPTION = "itemDescription",
            ID = "id",
            NAME = "name",
            URL = "url",
            PRICE = "price",
            STOCK = "stock",
            DESCRIPTION = "description",
            CUSTOMER = "customer",
            BASKET = "basket",
            AMOUNT = "amount";

    @Context
    ServletContext context;

    Environment env;

    Cloudy cloud;

    @PostConstruct
    public void init()
    {
        if (context.getAttribute(ENV) == null)
        {
            String basePath = context.getRealPath("/");
            Environment env = new Environment(basePath);
            context.setAttribute(ENV, env);
        }

        env = (Environment) context.getAttribute(ENV);

        if (context.getAttribute(CLOUDY) == null)
        {
            Cloudy cl = new Cloudy();
            cl.setSchemaPath(env.getCloudSchemaPath());
            context.setAttribute(CLOUDY, cl);
        }

        cloud = (Cloudy) context.getAttribute(CLOUDY);
    }

    private Basket getBasket(HttpSession session)
    {
        Object basket = session.getAttribute(BASKET);

        if (basket == null)
        {
            basket = new Basket();
            session.setAttribute(BASKET, basket);
        }

        return (Basket) basket;
    }

    private Customer getCustomer(HttpSession session)
    {
        Object customer = session.getAttribute(CUSTOMER);

        if (customer == null)
            return null;
        else
            return (Customer) customer;
    }

    @GET
    @Path("getBasketItems")
    public String getBasketItems(@Context HttpServletRequest request)
    {
        Basket basket = getBasket(request.getSession(true));

        JSONArray arr = new JSONArray();
        JSONObject obj;
        for (BasketItem item : basket.getItems())
        {
            obj = new JSONObject();
            obj.put(ID, item.getItemId());
            obj.put(AMOUNT, item.getAmount());

            arr.put(obj);
        }

        //TODO: You can create a MessageBodyWriter so you don't have to call toString() every time
        return arr.toString();
    }


    @GET
    @Path("getUsername")
    public String getUsername(@Context HttpServletRequest request) {
        Customer cust = getCustomer(request.getSession(true));
        if(cust != null) {
            return cust.getName();
        } else {
            return "";
        }
    }
    @POST
    @Path("login")
    public boolean login(@Context HttpServletRequest request,
                         @FormParam("username") String username,
                         @FormParam("password") String password)
    {
        try
        {
            int custId = cloud.login(username, password);
            if (custId != -1)
            {
                Customer customer = new Customer(custId, username);
                request.getSession(true).setAttribute(CUSTOMER, customer);
                return true;
            }
        }
        catch (IOException|JDOMException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    @GET
    @Path("logout")
    public boolean logout(@Context HttpServletRequest request)
    {
        request.getSession().invalidate();
        return true;
    }

    @POST
    @Path("addItemToBasket")
    public void addItemToBasket(@Context HttpServletRequest request,
                                @FormParam("itemId") int itemId)
    {
        getBasket(request.getSession()).addItem(itemId, 1);
    }

    @POST
    @Path("adjustAmount")
    public void adjustAmount(@Context HttpServletRequest request,
                             @FormParam("itemId") int itemId,
                             @FormParam("amount") int amount)
    {
        getBasket(request.getSession()).adjustItemAmount(itemId, amount);
    }

    @GET
    @Path("sellItems")
    public boolean sellItems(@Context HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Basket basket = getBasket(session);
        List<BasketItem> items = basket.getItems();
        boolean hasError = false;
        try
        {
            for (BasketItem item : items)
            {
                String status = cloud.sellItem(
                        item.getItemId(),
                        getCustomer(session).getId(),
                        item.getAmount());
                if (!status.equals("OK")) {
                    hasError = true;
                }
            }
            basket.clear();
            return !hasError;
        }
        catch (IOException|JDOMException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    @POST
    @Path("createCustomer")
    public int createCustomer(@Context HttpServletRequest request,
                              @FormParam("username") String username,
                              @FormParam("password") String password)
    {
        try {
            int resp = cloud.createCustomer(username, password);

            if (resp != -1)
            {
                login(request, username, password);
            }

            return resp;
        } catch (IOException|JDOMException e) {
            e.printStackTrace();
        }

      return -1;
    }

    @GET
    @Path("listItems")
    public String getItems()
    {
        try
        {
            Document resp = cloud.listItems();
            List<Element> children = resp.getRootElement().getChildren(ITEM, Cloudy.NS);

            JSONArray arr = new JSONArray();
            JSONObject obj;
            for (Element c : children)
            {
                obj = new JSONObject();
                obj.put(ID, getInt(c, ITEM_ID));
                obj.put(NAME, getText(c, ITEM_NAME));

                URI uri = new URI(getText(c, ITEM_URL));
                if (!uri.isAbsolute())
                {
                    uri = new URI(context.getContextPath() + uri);
                }

                obj.put(URL, uri.toString());
                obj.put(PRICE, getInt(c, ITEM_PRICE));
                obj.put(STOCK, getInt(c, ITEM_STOCK));

                Element desc = c.getChild(ITEM_DESCRIPTION, Cloudy.NS)
                                .getChild(DOCUMENT, Cloudy.NS)
                                .clone();
                obj.put(DESCRIPTION, toHtml(desc));

                arr.put(obj);
            }

            //TODO: You can create a MessageBodyWriter so you don't have to call toString() every time
            return arr.toString();
        }
        catch (IOException|JDOMException|URISyntaxException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private int getInt(Element elm, String child)
    {
        return Integer.parseInt(getText(elm, child));
    }

    private String getText(Element elm, String child)
    {
        return elm.getChild(child, Cloudy.NS).getText();
    }

    private String toHtml(Element elm)
    {
        try
        {
            XSLTransformer tr = new XSLTransformer(
                    env.getItemDescriptionStylesheet());

            Document doc = tr.transform(new Document(elm));

            return new XMLOutputter(Format.getPrettyFormat())
                    .outputString(doc.getRootElement());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
