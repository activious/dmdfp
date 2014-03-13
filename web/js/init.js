/**
 * Created by khk on 3/2/14.
 */
var basket;
var username = "";

$(function() {
    $.when(
        $.getScript("/dmdfp/js/sprintf.min.js"),
        $.getScript("/dmdfp/js/requests.js"),
        $.getScript("/dmdfp/js/basket.js")
    ).done(function() {
        listItems();
        basket = new Basket();
        updateBasket();
        updateUsername();
    });

    $("#basket-drop")
        .on("dragover", prevent) // Allow drop on element
        .on("dragenter", prevent) // Allow drop on element (IE)
        .on("drop", function(event) {
            event.preventDefault();
            var itemId = parseInt(event.originalEvent.dataTransfer.getData("Text"));
            basket.addItem(itemId);
        });

    /*$(document)
        .bind("ajaxStart", function() {
        })
        .bind("ajaxStop", function() {
        });*/
});

function prevent(event)
{
    event.preventDefault();
}

function dragItem(event, itemId)
{
    event.dataTransfer.setData("Text", itemId);
}

function ajaxGet(url, callback)
{
    $.ajax(url)
        .done(callback)
        .fail(function() {
            console.log("Ajax error: Failed to receive data from " + url);
        });
}

function ajaxPost(url, data, callback)
{
    $.ajax(url, { type: "POST", data: data })
        .done(callback)
        .fail(function() {
            console.log("Ajax error: Failed to send data to " + url);
        });
}
