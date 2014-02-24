package dmdfp;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 * Created by khk on 2/21/14.
 */
@ManagedBean
@RequestScoped
public class NewItem {
    @ManagedProperty("#{itemList}")
    private ItemList items;

    private String newName = "New item";

    @PostConstruct
    public void init()
    {

        items.setCurrentItem(new Item());
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
