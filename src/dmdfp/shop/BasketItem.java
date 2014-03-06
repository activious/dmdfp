package dmdfp.shop;

/**
 * Created by khk on 3/6/14.
 */
public class BasketItem
{
    private int itemId;
    private int amount;

    public BasketItem(int itemId, int amount)
    {
        setItemId(itemId);
        setAmount(amount);
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
