/**
 * Created by khk on 3/2/14.
 */
$(function() {
    $.when(
        $.getScript("/dmdfp/js/sprintf.min.js"),
        $.getScript("/dmdfp/js/requests.js")
    ).done(function() {
        listItems();
    });

    /*$(document)
        .bind("ajaxStart", function() {
        })
        .bind("ajaxStop", function() {
        });*/
});

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
