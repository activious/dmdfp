package dmdfp.admin;

import dmdfp.share.Cloudy;
import dmdfp.share.Environment;
import org.jdom2.Document;
import org.jdom2.Element;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by khk on 2/21/14.
 */
@ManagedBean
@ApplicationScoped
public class ItemList implements Serializable
{
    private static final String
            DOCUMENT = "document",
            ITEM = "item",
            ITEM_ID = "itemID",
            ITEM_NAME = "itemName",
            ITEM_URL = "itemURL",
            ITEM_PRICE = "itemPrice",
            ITEM_STOCK = "itemStock",
            ITEM_DESCRIPTION = "itemDescription";

    private List<Item> items;

    private Item currentItem;

    @ManagedProperty("#{env}")
    private Environment env;

    private Cloudy cloud;

    @PostConstruct
    public void init()
    {
        cloud = new Cloudy();
        cloud.setSchemaPath(env.getCloudSchemaPath());

        listItems();
    }

    public List<Item> getItems()
    {
        return items;
    }

    public Item getItemById(int id)
    {
        for (Item item : items)
        {
            if (id == item.getId())
            {
                return item;
            }
        }

        return null;
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
    }

    public void addItem(Item item)
    {
        items.add(item);
    }

    public void listItems()
    {
        items = new ArrayList<Item>();

        try {
            Document resp = cloud.listItems();

            Item item;
            for (Element elm : resp.getRootElement().getChildren(ITEM, Cloudy.NS))
            {
                item = new Item(env);
                item.setId(Integer.parseInt(elm.getChild(ITEM_ID, Cloudy.NS).getText()));
                item.setName(elm.getChild(ITEM_NAME, Cloudy.NS).getText());
                item.setUrl(elm.getChild(ITEM_URL, Cloudy.NS).getText());
                item.setPrice(Integer.parseInt(elm.getChild(ITEM_PRICE, Cloudy.NS).getText()));
                item.setStock(Integer.parseInt(elm.getChild(ITEM_STOCK, Cloudy.NS).getText()));
                item.setDescription(elm.getChild(ITEM_DESCRIPTION, Cloudy.NS).getChild(DOCUMENT, Cloudy.NS).clone());
                items.add(item);
            }
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
