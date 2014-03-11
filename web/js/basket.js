function Basket()
{
    this.items = [];
}

Basket.prototype.addItem = function(itemId)
{
    for (var i = 0; i < this.items.length; i++) {
        if (itemId == this.items[i].id) {
            this.items[i].amount++;
            this.updateDisplay();
            return;
        }
    }
    this.items.push({ id: itemId, amount: 1 });
    this.updateDisplay();
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
    var list = $("#basket-list");
    list.empty();
    for (var i = 0; i < this.items.length; i++)
    {
        var item = this.items[i];
        list.append($(sprintf('<li>Item id: %d, amount: %d</li>', item.id, item.amount)));
    }
}

Basket.prototype.clearBasket = function() {
    this.items = []
    var list = $("#basket-list");
    list.empty();
    this.updateDisplay();
}
