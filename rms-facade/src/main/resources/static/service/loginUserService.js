module.service('loginUser', ['$rootScope', function ($rootScope) {
    var service = {
        LoginUser: [
            { UserName: "admin", PassWord: "123456" }
        ],
        AddLoginUser: function (user) {
            service.User.push(user);
            $rootScope.$broadcast('User.Update')
        }
    }
    return service;
}])

