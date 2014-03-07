function Basket()
{
    this.items = [];
}

Basket.prototype.addItem = function(itemId)
{
    this.items.push({ id: itemId, amount: 1 });
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

Basket.prototype.setItems = function(items)
{
    this.items = items;
}

Basket.prototype.getSize = function()
{
    return this.items.length;
}

Basket.prototype.updateDisplay = function()
{

}
