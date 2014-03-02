package dmdfp.admin;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * Created by khk on 2/21/14.
 */
@ManagedBean
@RequestScoped
public class NewItem implements Serializable
{
    @ManagedProperty("#{itemList}")
    private ItemList items;

    private String newName = "New item";

    @PostConstruct
    public void init()
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Item item = ctx.getApplication().evaluateExpressionGet(ctx, "#{item}", Item.class);
        items.setCurrentItem(item);
    }

    public ItemList getItems() {
        return items;
    }

    public void setItems(ItemList items) {
        this.items = items;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
