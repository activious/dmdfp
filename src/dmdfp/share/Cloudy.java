package dmdfp.share;

import dk.au.cs.dwebtek.Validator;
import dmdfp.shop.SaleStatus;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Cloudy
{
    private static final int SHOP_ID = 445;

    private static final String
        SHOP_KEY = "A0CB70B099C98ED5FA678BD7",
        CLOUD_URL = "http://services.brics.dk/java4/cloud/",
        GET = "GET",
        POST = "POST",
        CREATE_ITEM = "createItem",
        MODIFY_ITEM = "modifyItem",
        SELL_ITEM ="sellItem",
        SALE_AMOUNT = "saleAmount",
        LIST_ITEMS = "listItems",
        ADJUST_ITEM_STOCK = "adjustItemStock",
        ADJUSTMENT = "adjustment",
        SHOP_KEY_NAME = "shopKey",
        ITEM_ID = "itemID",
        ITEM_NAME = "itemName",
        ITEM_URL = "itemURL",
        ITEM_PRICE = "itemPrice",
        ITEM_STOCK = "itemStock",
        ITEM_DESCRIPTION = "itemDescription",
        CREATE_CUSTOMER = "createCustomer",
        CUSTOMER_NAME = "customerName",
        CUSTOMER_PASS = "customerPass",
        CUSTOMER_ID = "customerID",
        LOGIN = "login",
        LIST_SHOPS = "listShops",
        LIST_SHOP_SALES = "listShopSales",
        LIST_CUSTOMERS = "listCustomers",
        LIST_CUSTOMER_SALES = "listCustomerSales";

    public static final Namespace
        NS = Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014");

    private static boolean interactive = false;

    private Path schemaPath;

    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            printUsage();
            return;
        }

        interactive = true;
        String req = args[0];

        try
        {
            Cloudy cloud = new Cloudy();

            switch (req)
            {
                case CREATE_ITEM:
                {
                    if (args.length < 3)
                    {
                        errorNEA();
                        return;
                    }

                    Path xml = Paths.get(args[1]);
                    Path xsd = Paths.get(args[2]);

                    if (!validatePaths(xml, xsd))
                        return;

                    Document doc = Validator.readAndValidateXML(
                            Files.newInputStream(xml), xsd);

                    cloud.setSchemaPath(xsd);
                    cloud.createItem(doc);
                    break;
                }
                case CREATE_CUSTOMER:
                {
                    if (args.length < 4)
                    {
                        errorNEA();
                        return;
                    }

                    Path xsd = Paths.get(args[3]);

                    if (!validatePaths(xsd))
                        return;

                    cloud.setSchemaPath(xsd);
                    cloud.createCustomer(args[1], args[2]);
                    break;
                }
                case SELL_ITEM:
                {
                    if (args.length <5)
                    {
                        errorNEA();
                        return;
                    }
                    Path xsd = Paths.get(args[4]);

                    if (!validatePaths(xsd))
                        return;

                    cloud.setSchemaPath(xsd);
                    cloud.sellItem(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                    break;
                }

                case MODIFY_ITEM:
                {
                    if (args.length < 3)
                    {
                        errorNEA();
                        return;
                    }

                    Path xml = Paths.get(args[1]);
                    Path xsd = Paths.get(args[2]);

                    if (!validatePaths(xml, xsd))
                        return;

                    Document doc = Validator.readAndValidateXML(
                            Files.newInputStream(xml), xsd);

                    cloud.setSchemaPath(xsd);
                    cloud.modifyItem(doc);
                    break;
                }
                case LOGIN:
                {
                    if (args.length < 4)
                    {
                        errorNEA();
                        return;
                    }

                    Path xsd = Paths.get(args[3]);
                    if (!validatePaths(xsd))
                        return;

                    cloud.setSchemaPath(xsd);
                    cloud.login(args[1], args[2]);
                    break;
                }
                case ADJUST_ITEM_STOCK:
                {
                    if (args.length < 3)
                    {
                        errorNEA();
                        return;
                    }

                    cloud.adjustItemStock(
                            Integer.parseInt(args[1]),
                            Integer.parseInt(args[2]));
                    break;
                }
                case LIST_ITEMS:
                {
                    if (args.length < 2)
                    {
                        errorNEA();
                        return;
                    }

                    Path xsd = Paths.get(args[1]);
                    if (!validatePaths(xsd))
                        return;

                    cloud.setSchemaPath(xsd);
                    cloud.listItems();
                    break;
                }
                case LIST_SHOPS:
                {
                    if (args.length < 2)
                    {
                        errorNEA();
                        return;
                    }

                    Path xsd = Paths.get(args[1]);
                    if (!validatePaths(xsd))
                        return;

                    cloud.setSchemaPath(xsd);
                    cloud.listShops();
                    break;
                }
                case LIST_SHOP_SALES:
                {
                    if (args.length < 2)
                    {
                        errorNEA();
                        return;
                    }

                    Path xsd = Paths.get(args[1]);
                    if (!validatePaths(xsd))
                        return;

                    cloud.setSchemaPath(xsd);
                    cloud.listShopSales();
                    break;
                }
                case LIST_CUSTOMERS:
                {
                    if (args.length < 2)
                    {
                        errorNEA();
                        return;
                    }

                    Path xsd = Paths.get(args[1]);
                    if (!validatePaths(xsd))
                        return;

                    cloud.setSchemaPath(xsd);
                    cloud.listCustomers();
                    break;
                }
                case LIST_CUSTOMER_SALES:
                {
                    if (args.length < 3)
                    {
                        errorNEA();
                        return;
                    }

                    Path xsd = Paths.get(args[2]);
                    if (!validatePaths(xsd))
                        return;

                    cloud.setSchemaPath(xsd);
                    cloud.listCustomerSales(Integer.parseInt(args[1]));
                    break;
                }
                default:
                {
                    error("Unknown request: %s", req);
                }
            }
        }
        catch (IOException|JDOMException e)
        {
            e.printStackTrace();
        }
    }

    public Path getSchemaPath() {
        return schemaPath;
    }

    public void setSchemaPath(Path schemaPath) {
        this.schemaPath = schemaPath;
    }

    private static boolean validatePaths(Path... paths)
    {
        for (Path p : paths)
        {
            if (!Files.exists(p))
            {
                error("File does not exist: %s", p);
                return false;
            }

            if (!Files.isRegularFile(p))
            {
                error("File is not a regular file: %s", p);
                return false;
            }
        }

        return true;
    }

    private static Document newReq(String request)
    {
        Element root = new Element(request, NS);
        Document doc = new Document(root);

        appendTextElement(root, SHOP_KEY_NAME, SHOP_KEY);

        return doc;
    }

    private static void appendTextElement(Element parent, String name, String content)
    {
        Element elm = new Element(name, NS);
        elm.setText(content);
        parent.addContent(elm);
    }

    public boolean adjustItemStock(int itemId, int adjustment)
            throws IOException, JDOMException
    {
        Document req = newReq(ADJUST_ITEM_STOCK);
        Element root = req.getRootElement();

        appendTextElement(root, ITEM_ID, String.valueOf(itemId));
        appendTextElement(root, ADJUSTMENT, String.valueOf(adjustment));

        HttpURLConnection con = openCon(ADJUST_ITEM_STOCK, POST);
        int respCode = send(req, con);
        closeCon(con);

        return (respCode == 200);
    }

    public boolean login(String username, String password)
            throws IOException, JDOMException
    {
        Element root = new Element(LOGIN, NS);
        Document doc = new Document(root);

        appendTextElement(root, CUSTOMER_NAME, username);
        appendTextElement(root, CUSTOMER_PASS, password);

        HttpURLConnection con = openCon(LOGIN, POST);
        int respCode = send(doc, con);

        if (respCode != 200)
        {
            closeCon(con);
            return false;
        }

        Document resp = Validator.readAndValidateXML(
                con.getInputStream(), schemaPath);

        closeCon(con);

        if (interactive)
        {
            printResponse(resp);
        }

        return true;
    }

    public int createCustomer(String username, String password)
            throws IOException, JDOMException
    {
        Document doc = newReq(CREATE_CUSTOMER);
        Element root = doc.getRootElement();

        appendTextElement(root, CUSTOMER_NAME, username);
        appendTextElement(root, CUSTOMER_PASS, password);

        HttpURLConnection con = openCon(CREATE_CUSTOMER, POST);
        int respCode = send(doc, con);

        if (respCode != 200)
        {
            closeCon(con);
            throw new IOException("Customer creation failed");
        }

        Document resp = Validator.readAndValidateXML(
                con.getInputStream(), schemaPath);

        closeCon(con);

        if (interactive)
        {
            printResponse(resp);
        }

        Element custId = resp.getRootElement().getChild(CUSTOMER_ID, NS);
        return (custId == null
                ? -1
                : Integer.parseInt(custId.getText()));
    }

    public int createItem(Document itemDoc)
            throws IOException, JDOMException
    {
        Element item = itemDoc.getRootElement();

        return createItem(
                item.getChild(ITEM_NAME).getText(),
                item.getChild(ITEM_URL).getText(),
                Integer.parseInt(item.getChild(ITEM_PRICE).getText()),
                new Document(item.getChild(ITEM_DESCRIPTION).clone()));
    }

    public int createItem(String itemName,
                                 String itemURL,
                                 int itemPrice,
                                 Document itemDescription)
            throws IOException, JDOMException
    {
        Document doc = newReq(CREATE_ITEM);
        Element root = doc.getRootElement();

        appendTextElement(root, ITEM_NAME, itemName);

        HttpURLConnection con = openCon(CREATE_ITEM, POST);
        int respCode = send(doc, con);

        if (respCode != 200)
        {
            closeCon(con);
            throw new IOException("Item creation failed");
        }

        Document resp = Validator.readAndValidateXML(
                con.getInputStream(), schemaPath);

        closeCon(con);

        int itemId = Integer.parseInt(
                resp.getRootElement().getText());

        if (interactive)
        {
            printf(">>> New ItemID = %d%n", itemId);
            printResponse(resp);
        }

        modifyItem(
                itemId,
                itemName,
                itemURL,
                itemPrice,
                itemDescription);

        return itemId;
    }
    
    public boolean modifyItem(Document itemDoc)
            throws IOException, JDOMException
    {
        Element item = itemDoc.getRootElement();

        return modifyItem(
                Integer.parseInt(item.getChild(ITEM_ID).getText()),
                item.getChild(ITEM_NAME).getText(),
                item.getChild(ITEM_URL).getText(),
                Integer.parseInt(item.getChild(ITEM_PRICE).getText()),
                new Document(item.getChild(ITEM_DESCRIPTION).clone()));
    }

    public boolean modifyItem(int itemId,
                              String itemName,
                              String itemURL,
                              int itemPrice,
                              Document itemDescription)
            throws IOException, JDOMException
    {
        Document doc = newReq(MODIFY_ITEM);
        Element root = doc.getRootElement();

        appendTextElement(root, ITEM_ID, String.valueOf(itemId));
        appendTextElement(root, ITEM_NAME, itemName);
        appendTextElement(root, ITEM_URL, itemURL);
        appendTextElement(root, ITEM_PRICE, String.valueOf(itemPrice));

        Element itemDesc = new Element(ITEM_DESCRIPTION, NS);
        itemDesc.addContent(itemDescription.getRootElement().clone());
        root.addContent(itemDesc);

        HttpURLConnection con = openCon(MODIFY_ITEM, POST);
        int respCode = send(doc, con);
        closeCon(con);

        return (respCode == 200);
    }

    public Enum sellItem(int itemId,
                         int customerID,
                         int saleAmount)
            throws IOException, JDOMException
    {
        Document doc = newReq(SELL_ITEM + "s"); // Cloud wants it plural
        Element root = doc.getRootElement();

        appendTextElement(root, ITEM_ID, String.valueOf(itemId));
        appendTextElement(root, CUSTOMER_ID, String.valueOf(customerID));
        appendTextElement(root, SALE_AMOUNT, String.valueOf(saleAmount));

        HttpURLConnection con = openCon(SELL_ITEM, POST);
        int respCode = send(doc, con);
        Document resp = Validator.readAndValidateXML(
                con.getInputStream(), schemaPath);
        closeCon(con);
        if (interactive)
        {
            printResponse(resp);
        }
        String name = resp.getRootElement().getChildren().get(0).getName();
        switch (name) {
            case "ok": return SaleStatus.OK;
            case "itemSoldOut": return SaleStatus.SOLD_OUT;
            default: return SaleStatus.ERROR;
        }
    }

    public Document listItems()
            throws IOException, JDOMException
    {
        HttpURLConnection con = openCon(LIST_ITEMS + "?shopID=" + SHOP_ID);

        Document resp = Validator.readAndValidateXML(
                con.getInputStream(), schemaPath);

        closeCon(con);

        if (interactive)
        {
            printResponse(resp);
        }

        return resp;
    }

    public Document listShops()
            throws IOException, JDOMException
    {
        HttpURLConnection con = openCon(LIST_SHOPS);

        Document resp = Validator.readAndValidateXML(
                con.getInputStream(), schemaPath);

        closeCon(con);

        if (interactive)
        {
            printResponse(resp);
        }

        return resp;
    }

    public Document listShopSales()
            throws IOException, JDOMException
    {
        HttpURLConnection con = openCon(LIST_SHOP_SALES + "?shopKey=" + SHOP_KEY);

        Document resp = Validator.readAndValidateXML(
                con.getInputStream(), schemaPath);

        closeCon(con);

        if (interactive)
        {
            printResponse(resp);
        }

        return resp;
    }

    public Document listCustomers()
            throws IOException, JDOMException
    {
        HttpURLConnection con = openCon(LIST_CUSTOMERS);

        Document resp = Validator.readAndValidateXML(
                con.getInputStream(), schemaPath);

        closeCon(con);

        if (interactive)
        {
            printResponse(resp);
        }

        return resp;
    }

    public Document listCustomerSales(int custId)
            throws IOException, JDOMException
    {
        HttpURLConnection con = openCon(LIST_CUSTOMER_SALES + "?customerID=" + custId);

        Document resp = Validator.readAndValidateXML(
                con.getInputStream(), schemaPath);

        closeCon(con);

        if (interactive)
        {
            printResponse(resp);
        }

        return resp;
    }

    private static HttpURLConnection openCon(String url)
            throws IOException
    {
        return openCon(url, GET);
    }

    private static HttpURLConnection openCon(String cmd, String method)
            throws IOException
    {
        if (interactive)
        {
            printf(">>> Connecting to %s%n", CLOUD_URL + cmd);
        }

        URL url = new URL(CLOUD_URL + cmd);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod(method);
        con.setDoOutput(method.equals(POST));
        con.connect();

        return con;
    }

    private static void closeCon(HttpURLConnection con)
    {
        con.disconnect();

        if (interactive)
        {
            print(">>> Connection closed.");
        }
    }

    private static int send(Document doc, HttpURLConnection con)
            throws IOException
    {
        if (interactive)
        {
            printf(">>> Sending data:%n%n");
            printXML(doc);
        }

        new XMLOutputter(Format.getRawFormat())
                .output(doc, con.getOutputStream());

        int respCode = con.getResponseCode();

        if (interactive)
        {
            printf("%n>>> Got response code %d: %s%n",
                    respCode, con.getResponseMessage());
        }

        return respCode;
    }

    private static void printResponse(Document resp)
            throws IOException
    {
        printf(">>> Response:%n%n");
        printXML(resp);
    }

    private static void printXML(Document doc)
            throws IOException
    {
        new XMLOutputter(Format.getPrettyFormat()).output(doc, System.out);
    }

    private static void print(String str)
    {
        System.out.println(str);
    }

    private static void printf(String str, Object... args)
    {
        System.out.printf(str, args);
    }

    private static void errorNEA()
    {
        error("Not enough arguments");
        printUsage();
    }

    private static void error(String msg, Object... args)
    {
        System.err.printf("Error: " + msg + "%n", args);
    }

    private static void printUsage()
    {
        print("Usage: Cloudy [ createItem XML_FILE SCHEMA_FILE |");
        print("                createCustomer USERNAME PASSWORD SCHEMA_FILE |");
        print("                modifyItem XML_FILE SCHEMA_FILE |");
        print("                adjustItemStock ITEM_ID ADJUSTMENT |");
        print("                login USERNAME PASSWORD SCHEMA_FILE |");
        print("                listItems SCHEMA_FILE |");
        print("                sellItem ITEM_ID CUSTOMER_ID AMOUNT SCHEMA_FILE ]");
    }
}
