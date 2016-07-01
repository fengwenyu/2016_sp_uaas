'use strict';

//设置组织结构的服务
var module = angular.module("OrigModule");
module.service('Organization', ['$rootScope', function ($rootScope) {
    var services = {
        Organization: [
            { cn: "",objectClass: "", UAASOID: "", UAASOrganizationLevel: "", description: "", sn: "", UAASOrganizationParentID: "", UAASOrganizationUserLeaderId: "" }
        ],
        AddOrganization: function (Organization) {
            //执行$http添加
            service.Organization.push(Organization);
            $rootScope.$broadcast('Organization.update');
        },
        GetOrganization: function ($scope, $http) {
            //$http.get('http://192.168.1.133:8080/uaas/facade/json/com.shangpin.uaas.api.admin/OrganizationManager/findSubOrgByLevel', {params{ID: ""}}).success(function (data) {
               // $scope.OrgList = data;
           // })
        }
    }
}])

