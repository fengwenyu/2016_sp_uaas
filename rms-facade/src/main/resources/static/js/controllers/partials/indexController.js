/**
 * Created by Percy on 14-7-14.
 */
angular.module("app.controllers.index", [])
    .controller("indexCtrl", ["$scope" , function ($scope) {
        $scope.content = {
            top: "partials/commons/top-menu.html",
            left: "partials/commons/left-menu.html"
        };
        $scope.menuClick = function () {

            console.debug(this.title);
        }
        var match = document.cookie.match(new RegExp("UAAS_TOKEN=([^;]+)" , "i") )
        var token;
        if(match && match.length >= 2){
            token = match[1]
        }else{
            alert("当前用户未登录或令牌已过期！")
//            throw "当前用户未登录或令牌已过期！"
            window.location.href = "login.html"
        }
        var match = document.cookie.match(new RegExp("UAAS_USER=([^;]+)" , "i") )
        var userName;
        if(match && match.length >= 2){
            userName = unescape(match[1])
            $scope.UserName = userName
        }else{
            alert("当前用户未登录！")
//            throw "当前用户未登录！"
            window.location.href = "login.html"
        }
        $scope.logout = function(){
            $.ajax({url : "userLogin/logout"});

//            com.shangpin.uaas.api.AuthenticationFacade.logout(token);
//            var date = new Date();
//            date.setTime(date.getTime() - 10000);
//            document.cookie = "token=" + token + ";expires=" + date.toUTCString();
//            document.cookie = "user=" + userName + ";expires=" + date.toUTCString();
//            window.location.href = "login.html"
        }

        $scope.returnIndex = function(){
            window.location.href = "nav.html?token=" + token + "&user=" + userName
        }

    }])
    .controller("leftMenuCtrl", ["$scope","$rootScope",'$location', function ($scope,$rootScope,$location) {
        $scope.$location = $location;
        
        $scope.menus = [
            {href: "#/userManager", title: "用户管理", content: "用户管理", active: false, reg: "\/userManager.*"},
            {href: "#/roleManager", title: "角色管理", content: "角色管理", active: false, reg: "\/roleManager.*"},
            {href: "#/groupManager", title: "用户组管理", content: "用户组管理", active: false, reg: "\/groupManager.*"},
            {href: "#/organizationManager", title: "部门管理", content: "部门管理", active: false, reg: "\/organizationManager.*"},
            {href: "#/resourceManager", title: "权限管理", content: "权限管理", active: false, reg: "\/resourceManager.*"},
            {href: "#/menuManager", title: "菜单管理", content: "菜单管理", active: false, reg: "\/menuManager.*"}
        ];
        $scope.isActive=function(menu){
            return new RegExp(menu.reg).test($location.path());
        };

//        $scope.select=function(menu){
//            angular.forEach($scope.menus,function(it){
//                if(it.active && it!==menu){
//                    it.active = false;
//                }
//            });
//            menu.active = true;
////            $rootScope.currentMenu=menu.href;
//        };
//        $scope.$watch('currentMenu',function(newValue,oldValue){
//            console.log(newValue);
//            if(newValue!=oldValue){
//                console.log("11111");
//            }
//        });
    }])

