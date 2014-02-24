package dmdfp;

import dk.au.cs.dwebtek.Validator;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.transform.XSLTransformer;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

/**
 * Created by khk on 2/21/14.
 */
@ManagedBean
@SessionScoped
public class Item
{
    private int id;
    private String name;
    private String url;
    private int price;
    private int stock;
    private Document description;

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
            return null;
        }
    }

    public String getHtmlDescription()
    {
        try
        {
            XSLTransformer tr = new XSLTransformer(Schema.XSL);

            //Helper.printXML(description);

            Document doc = tr.transform(description);

            if (doc == null)
                System.out.println("Doc is null!");
            else
            {
                //System.out.println(">>> TRANSFORMED DOCUMENT:");
                //Helper.printXML(doc);
            }

            return new XMLOutputter(Format.getPrettyFormat())
                    .outputString(doc.getRootElement());
        }
        catch (Exception e)
        {
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
}
