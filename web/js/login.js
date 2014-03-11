/**
 * Created by Kasper on 03/03/14.
 */

$(function() {
$("#login-box").hide();
});

$(document).ready(
function() {
    showLogin();
});

function showLogin()
{
    $("#login-link").on("click", function(){
        $("body").find("#login-box").slideToggle();
    });
}
