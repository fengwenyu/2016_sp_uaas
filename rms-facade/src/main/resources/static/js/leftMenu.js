$(function () {

    /*leftMenu handle*/
    $("#mx-leftmenu li").find("a")[0].on('click', function () {
        
        var that = $(this),
        object = $(that).find('p'),
        IsShow = $(object).attr("IsShow");

        that.addClass('cur').siblings('li').removeClass('cur');
        $("#mx-leftmenu li").find('p').hide();

        if (IsShow == "ture") {

            $(object).hide();
            $(object).attr("IsShow","false")
            //return false;
        } else {
            $(object).show()
            $(object).attr("IsShow", "ture")
        }

    });

});