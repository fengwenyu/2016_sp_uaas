/**
 * Created by Percy on 14-7-21.
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
jQuery(function(){
    var user_facede_api = com.shangpin.uaas.api.facade.user.UserFacade;
    var authentication_facede_api = com.shangpin.uaas.api.facade.auth.AuthenticationFacade;
    var authorization_facede_api = com.shangpin.uaas.api.facade.auth.AuthorizationFacade;

    var $inp = $('input');
    $inp.keypress(function (e) { //这里给function一个事件参数命名为e，叫event也行，随意的，e就是IE窗口发生的事件。
        var key = e.which; //e.which是按键的值
        if (key == 13) {
            login();
        }
    });
    var result = true;
    $(":button[name='submit']").click(login);

    var token = getQueryString("token");
    var userName = getQueryString("user");
    if(token){
        var ms = user_facede_api.findTopMenusByToken(token);

        $(ms).each(function(i, data) {
            if ("uaas" == data.name) {
                location.href = "index.html#/userManager" + "?token=" + token + "&user=" + userName;
            }
        });
        var as = [];
        var $tr = $("<tr>");
        $(ms).each(function(i ,data){
            $tr.append($("<td>").append($("<a>",{href: data.url + "?token=" + token,text : data.name})
                .append($("<span>",{text : data.uri}))));
            if((i+1)%4 === 0 || i === ms.length - 1){
                as.push($tr);
                $tr = $("<tr>");
            }
        });
        $("table:eq(0)").append(as);

        $(".modify-pwd").attr("href" , $(".modify-pwd").attr("href") + "?token=" + token + "&user=" + userName)
        $("#controlManager").attr("href" , $("#controlManager").attr("href") + "?token=" + token + "&user=" + userName)
    }
});

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

function login(){
    var username = $("input[name='username']:eq(0)").val();
    var pwd = $("input[name='password']:eq(0)").val();
    var verifyCode = $("input[name='verifyCode']:eq(0)").val();
//    if(!verifyCode && /(^\s*)|(\s*$)/g.test(verifyCode)){
//        $(".errorMsg").html("*&nbsp;请输入验证码！").show();
//        return;
//    }
    if(!username && /(^\s*)|(\s*$)/g.test(username)){
        $(".errorMsg").html("*&nbsp;请输入您的用户名！").show();
        return;
    }
    if(!pwd && /(^\s*)|(\s*$)/g.test(pwd)){
        $(".errorMsg").html("*&nbsp;请输入您的登录密码！").show();
        return;
    }

    $.ajax({
        url : "userLogin/login",
        data : {username:username, password:pwd, verifyCode:verifyCode},
        type : "POST",
        cache : true,
        dataType: "json",
        async: false,
        success : function(result){
            if(result.code == "1"){
                var clientUrl = getQueryString("client_url");
                if (undefined == clientUrl || null == clientUrl || "" == clientUrl) {
//                    document.cookie = "token=" + result.data;
                    location.href = "nav.html" + "?token=" + result.data + "&user=" + username;
                } else {
//                    location.href = "/uaas/redirect.gsp?clientUrl=" + clientUrl + "&token=" + result.data;
                    location.href = clientUrl + "?token=" + result.data;
                }
//                var Day = 1;
//                var exp  = new Date();
//                exp.setTime(exp.getTime() + Day*24*60*60*1000);
//                document.cookie = "token=" + result.data + ";expires=" + exp.toUTCString();
//                document.cookie = "user=" + escape(username) + ";expires=" + exp.toUTCString();
            }
            if(result.code == "0"){
                $(".errorMsg").html("*&nbsp;" + result.msg + "！").show();
                $("img").trigger("click")
            }
        }
    });


}
