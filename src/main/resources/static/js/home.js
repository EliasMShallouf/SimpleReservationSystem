window.getCookie = function(name) {
    let match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
    if (match)
        return match[2];
    return '';
}

window.fixApiTime = function(time) {
    if(!time)
       return '';
   return (time.match(/:/g) || []).length == 2 ? time : (time + ':00');
}

function cap(t) {
    if(t == null || t === undefined || t == '')
        return '';
    return (t.charAt(0) + '').toUpperCase() + t.substring(1);
}

$(function() {
    $(".nav-item .nav-link").on("click", function(){
       $(".nav-item").find(".active").removeClass("active");
       $(this).addClass("active");
    });

    let currentURL = window.location;
    $.each($('.nav-link'), function(idx, link) {
        if(link.href == currentURL) {
            $(link).parent().addClass('active');
        }
    });
    document.title = "Home / " + cap(currentURL.pathname.substring(1));
});