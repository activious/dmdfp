package dmdfp.shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khk on 3/6/14.
 */
public class Basket
{
    private List<BasketItem> items;

    public Basket()
    {
        items = new ArrayList<BasketItem>();
    }

    public void addItem(int itemId, int amount)
    {
        BasketItem item = new BasketItem(itemId, amount);

        items.add(item);
    }

    public void removeItem(int itemId)
    {
        BasketItem item = getItemById(itemId);

        if (item != null)
            items.remove(item);
    }

    public void adjustItemAmount(int itemId, int amount)
    {
        BasketItem item = getItemById(itemId);

        if (item != null)
        {
            item.setAmount(amount);
        }
    }

    public BasketItem getItemById(int itemId)
    {
        for (BasketItem item : items)
        {
            if (item.getItemId() == itemId)
                return item;
        }

        return null;
    }
}
