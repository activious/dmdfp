/**
 * Created by khk on 3/2/14.
 */
function listItems()
{
    ajaxGet("/dmdfp/rest/shop/listItems", function(resp) {
        var items = JSON.parse(resp);
        var grid = $("#product-grid");

        for (var i = 0; i < items.length; i++)
        {
            var item = items[i];
            var container = $('<div class="product"></div>');

            // Item id
            container.append(sprintf('Item ID: %d', item.id));

            // Item image
            container.append($(sprintf(
                '<a class="product-image" href="javascript:viewItem(%d)">'
                + '<img alt="%s" src="%s" />'
                + '</a>',
                item.id, item.name, item.url
            )));

            // Item name
            var info = $('<div class="product-info"></div>');
            container.append(info);
            info.append($(sprintf(
                '<div class="product-name">'
                + '<a href="javascript:viewItem(%d)">%s</a>'
                + '</div>',
                item.id, item.name
            )));

            // Item description
            info.append($(sprintf(
                '<div class="product-description">%s</div>',
                item.description
            )));

            // Item price
            info.append($(sprintf(
                '<div class="product-price">DKK %d</div>',
                item.price
            )));

            grid.append(container);
        }
    });
}

function createCustomer() {
    var username = $("#username").val();
    var password = $("#password").val();
    var message = $("#message-box");
    if (3 <= username.length && username.length <= 20 && 2<=password.length && password.length <= 20) {
        ajaxPost("/dmdfp/rest/shop/createCustomer", {username:username, password:password}, function(resp) {
            if (resp == -1) {
                message.text("Username already taken")
            } else {
                message.text(resp)
            }
        });
    } else {
        message.text("Username or Password too long or too short!")
    }
}
