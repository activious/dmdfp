package dmdfp;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 * Created by khk on 2/21/14.
 */
@ManagedBean
@RequestScoped
public class CurrentItem implements Serializable
{
    @ManagedProperty("#{param.itemId}")
    private int itemId;

    private Item item;

    @ManagedProperty("#{itemList}")
    transient private ItemList items;

    @PostConstruct
    public void init()
    {
        if (itemId > 0)
        {
            setItem(items.getItemById(itemId));
            items.setCurrentItem(this.item);
        }
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setItems(ItemList items) {
        this.items = items;
    }
}
