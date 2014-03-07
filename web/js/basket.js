function Basket()
{
    this.items = [];
}

Basket.prototype.addItem = function(itemId)
{
    var item = new BasketItem(itemId, 1);
    this.items.push(item);
}

Basket.prototype.removeItem = function(itemId)
{
    for (var i = 0; i < this.items.length; i++)
    {
        if (this.items[i].id == itemId)
        {
            this.items.splice(i, 1);
            return;
        }
    }
}

function BasketItem(itemId, amount)
{
    this.id = itemId;
    this.amount = amount;
}
