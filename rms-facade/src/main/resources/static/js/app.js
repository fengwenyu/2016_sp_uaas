'use strict'
// 声明 app level module 以及所依赖的 filters, and services

angular.module('app', ["app.user","app.controllers.index", "app.index.Menu", "app.index.Roles","app.group", "app.index.Resource", "app.index.organization", "ngRoute", "ui.bootstrap", 'app.animate', 'ui.select2']).config(['$routeProvider', function ($routeProvider) {

    $routeProvider.when('/userManager', { templateUrl: 'partials/user/userManager.html', controller: 'userController' });
//    $routeProvider.when('/userManagerOrganization/:orgId', { templateUrl: 'partials/user/userManager.html', controller: 'userController' });
    $routeProvider.when('/roleManager', { templateUrl: 'partials/role/roleManager.html', controller: 'roleController' });
    $routeProvider.when('/resourceManager', { templateUrl: 'partials/permission/resourceManager.htm', controller: 'resourceManager' });
    $routeProvider.when('/organizationManager', { templateUrl: 'partials/organization/organizationManager.html', controller: 'organizationController' });
    $routeProvider.when('/menuManager', { templateUrl: 'partials/menu/menuManager.html', controller: 'menuController'});
    $routeProvider.when('/userManager/:orgId', { templateUrl: 'partials/user/userManager.html', controller: 'userController' });
    $routeProvider.when('/groupManager', { templateUrl: 'partials/group/groupManager.html', controller: 'groupController' });

}]);

angular.module('app.animate', ['ngAnimate']);