package dmdfp;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.transform.XSLTransformer;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.ByteArrayInputStream;
import java.io.Serializable;

/**
 * Created by khk on 2/21/14.
 */
@ManagedBean
@RequestScoped
public class Item implements Serializable
{
    private int id;
    private String name;
    private String url;
    private int price;
    private int stock;
    private Document description;

    @ManagedProperty("#{env}")
    private Environment env;

    public Item() {}

    public Item(Environment e)
    {
        env = e;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int value)
    {
        id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Document getDescription()
    {
        return description;
    }

    public String getDescriptionString()
    {
        try
        {
            return new XMLOutputter(Format.getPrettyFormat())
                    .outputString(description.getRootElement());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String getHtmlDescription()
    {
        try
        {
            XSLTransformer tr = new XSLTransformer(
                    env.getItemDescriptionStylesheet());

            Document doc = tr.transform(description);

            return new XMLOutputter(Format.getPrettyFormat())
                    .outputString(doc.getRootElement());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void setDescription(Element root)
    {
        setDescription(new Document(root));
    }

    public void setDescription(Document desc)
    {
        description = desc;
    }

    @SuppressWarnings("deprecation")
    public void setDescriptionString(String xml)
    {
        try
        {
            SAXBuilder builder = new SAXBuilder(false);
            description = builder.build(
                    new ByteArrayInputStream(xml.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setEnv(Environment e)
    {
        env = e;
    }
}
