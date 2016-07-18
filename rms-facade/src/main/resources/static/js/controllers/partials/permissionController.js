(function () {
    var groupapi = com.shangpin.uaas.api.admin.org.GroupAdminFacade;
    var userapi = com.shangpin.uaas.api.admin.user.UserAdminFacade;
    var roleapi = com.shangpin.uaas.api.admin.role.RoleAdminFacade;
    var menuapi = com.shangpin.uaas.api.admin.menu.MenuAdminFacade;
    var resourceNodeapi = com.shangpin.uaas.api.admin.resource.ResourceNodeAdminFacade;
    var resourceapi = com.shangpin.uaas.api.admin.resource.ResourceAdminFacade;
    var organizationapi = com.shangpin.uaas.api.admin.org.OrganizationAdminFacade;
    var permissionapi = com.shangpin.uaas.api.admin.permission.PermissionAdminFacade;
    var page = {'pageSize': 1000, 'page': 1};

    angular.module("app.index.Resource", []).config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/addResource', { templateUrl: 'partials/permission/addResource.htm', controller: 'addResourceController'})
            $routeProvider.when('/editResource/:id', { templateUrl: 'partials/permission/editResource.htm', controller: 'editResourceController'});
            $routeProvider.when('/bindingResourcesWithRole/:id', { templateUrl: 'partials/permission/bindingResourcesWithRole.html', controller: 'bindResourceWithRoleController'});
            $routeProvider.when('/uploadResource', { templateUrl: 'partials/permission/uploadResource.html', controller: 'uploadController'});
            $routeProvider.when('/exportResource', { templateUrl: 'partials/permission/exportResource.html', controller: 'exportController'});
        }])
        .controller("uploadController", function($scope , $routeParams , $location) {
            $scope.back = function(){
                $location.path("/resourceManager")
            }
        })
        .controller("exportController", function($scope , $routeParams , $location) {
            $scope.back = function(){
                $location.path("/resourceManager")
            }
        })
        .controller("resourceManager", function ($scope) {
            $scope.criteria = {
                "isEnabled" : "",
                "nameLike" : ""
            };
            <!--=====================tree grid start===========================-->
            var resourceArr = resourceapi.getAllResources();
            var dataList = treeGridDataHandler(resourceArr);
            var emtList = generateTreeGridEmt(dataList , resourceCellDetailHandler);
            $(".top-tbody").append(emtList);
            var $container = $(".gridtree-container");
            $container.delegate(".treegrid-collapsed" , "click" , function(){
                    var that = $(this).parents("tr");
                    var pid = that.attr("id");
                    recShow(that , pid);
                    $(this).removeClass("treegrid-collapsed").addClass("treegrid-expanded");
                })
                .delegate(".treegrid-expanded" , "click" , function(){
                    console.log(".treegrid-expanded click event");
                    var that = $(this).parents("tr");
                    var pid = that.attr("id");
                    recHide(that , pid);
                    $(this).removeClass("treegrid-expanded").addClass("treegrid-collapsed");
                });
            <!--=====================tree grid end===========================-->

            $scope.searchResource = function(){
//            var criteria = {};
//            criteria.nameLike = $scope.nameLike;
//            if($scope.isEnabled == 1){
//                criteria.isEnabled = "true";
//            }else if($scope.isEnabled == 0){
//                criteria.isEnabled = "false";
//            } else {
//                criteria.isEnabled = "";
//            }
                var resultList = resourceNodeapi.findResourceNodesByCriteria($scope.criteria);
                var dataList = treeGridDataHandler(resultList);
                var emtList = generateTreeGridEmt(dataList , resourceCellDetailHandler);
                $(".top-tbody").empty().append(emtList);
            }
        })
        .controller("bindResourceWithRoleController", function ($scope, $routeParams) {
            console.debug("绑定资源控制器");
            var id = $routeParams.id;
            var resourceNode = resourceapi.getResourceNode(id)
            $scope.resourceNode = resourceNode;

            var assignedRoles = permissionapi.findAllRolesByResourceNode(id)
            $scope.assignedRoles = assignedRoles;
            var allRoles = roleapi.findAllRoles(page).list;
            var allRolesMap = _.indexBy(allRoles, 'id');

            var getRoleId = function (role) {
                return role.id;
            };
            $scope.notSelectedRoles = _.difference(allRoles.map(getRoleId), assignedRoles.map(getRoleId))
                .map(function (id) {
                    return allRolesMap[id];
                });

            $scope.addRoleWithUser = function (role) {
                $scope.notSelectedRoles = _.filter($scope.notSelectedRoles, function (r) {
                    return r.id != role.id;
                });
                $scope.assignedRoles.push(role);

            };

            $scope.removeRoleWithUser = function (role) {
                $scope.assignedRoles = _.filter($scope.assignedRoles, function (r) {
                    return r.id != role.id;
                });
                $scope.notSelectedRoles.push(role);

            };

            $scope.bindingRole = function () {
                console.debug("权限绑定角色");
                var roleIds = new Array();
                var tempAssignedRoles = $scope.assignedRoles;
                for (var i = 0; i < tempAssignedRoles.length; i++) {
                    var role = tempAssignedRoles[i];
                    console.debug("========" + role.id);
                    roleIds.push(role.id);
                }
                try {
                    console.debug("待绑定的角色：" + roleIds);
                    permissionapi.bindPermissionWithRoles(id,roleIds);
                    alert("绑定成功");
                    location.reload(true);
                } catch (e) {
                    alert("绑定角色出错：" + e);
                }

            };


        })

        .controller("addResourceController", function ($scope) {
            <!--====================tree结构事件开始====================-->
            var resourceArr = resourceapi.getAllResources();
            resourceArr = handleResourceNodeFilterPage(resourceArr);
            var dataList = treeGridDataHandler(resourceArr);
            var treeNodeList = createTreeContent(dataList);
            var treeContainer = $("#treeContainer");
            treeContainer.append(treeNodeList);
            bindTreeEvent($scope);
            $scope.resourceType = "PAGE";
            $scope.resourceState = "0";
            <!--====================tree结构事件结束====================-->
            console.debug("=======添加资源=======");
            $scope.addResource = function(){

                if($scope.resourceName.length > 10){
                    alert("模块名称过长！");
                    return false;
                }
                var name = $scope.resourceName;
                var state = $scope.resourceState;
                var type = $scope.resourceType;
                var pid = $scope.pid || "1";
                var desc = $scope.resourceDesc;
                var uri = $scope.resourceURI;
                var resourceDTO = {
                    name : name,
                    isEnabled : (state == 1),
                    type : type ,
                    uri : uri ,
                    parentId : pid,
                    description : desc
                };

                if ("1" == resourceDTO.parentId && resourceDTO.type != "PAGE") {
                    console.log("=======顶级模块只能是PAGE=======");
                    var confirm = window.confirm("顶级模块类型只能是页面类型，确认添加该权限或默认修改为页面类型？");
                    if (confirm) {
                        resourceDTO.type = "PAGE";
                        var data = resourceapi.createResource(resourceDTO);
                        if( data =="success"){
                            alert("添加成功");
                            history.go(-1);
                        }else{
                            alert(data);
                        }
                    }
                } else {
                    var data = resourceapi.createResource(resourceDTO);
                    if(data=="success"){
                        alert("添加成功");
                        history.go(-1);
                    }else{
                        alert(data);
                    }
                }

            }

        })
        .controller("editResourceController" , function($scope , $routeParams){
            var res = resourceapi.getResourceNode($routeParams.id);
            $scope.resourceName = res.name;
            $scope.resourceState = res.isEnabled || "1";
            $scope.resourceType = res.type || "PAGE";
            $scope.resourceDesc = res.description;
            $scope.resourceURI = res.uri;
            $scope.pid = res.parentId;
            if($scope.pid!="1"){
                $scope.parentResourceName = resourceapi.getResourceNode($scope.pid).name;
            }else{
                $scope.parentResourceName = "根级菜单";
            }
            <!--====================tree结构事件开始====================-->
            var resourceArr = resourceapi.getAllResources();
            resourceArr = handleResourceNodeFilterPage(resourceArr);
            var dataList = treeGridDataHandler(resourceArr);
            var treeNodeList = createTreeContent(dataList);
            var treeContainer = $("#treeContainer");
            treeContainer.append(treeNodeList);
            bindTreeEvent($scope);
            <!--====================tree结构事件结束====================-->
            $scope.editResource = function(){

                if($scope.resourceName.length > 10){
                    alert("模块名称过长！");
                    return false;
                }

                var resourceDTO = {
                    id : res.id,
                    name : $scope.resourceName,
                    isEnabled : ($scope.resourceState == 1),
                    //type : $scope.resourceType.name || "BUTTON",
                    type : $scope.resourceType || "BUTTON",
                    uri : $scope.resourceURI,
                    parentId : $scope.pid,
                    description : $scope.resourceDesc
                };

                console.debug("resourceDTO.parentId:" + resourceDTO.parentId);
                if ("1" == resourceDTO.parentId && resourceDTO.type != "PAGE") {
                    console.log("=======顶级模块只能是PAGE=======");
                    var confirm = window.confirm("顶级模块类型只能是页面类型，确认添加该权限或默认修改为页面类型？");
                    if (confirm) {
                        resourceDTO.type = "PAGE";
                        var data = resourceapi.modifyResource(resourceDTO);
                        if( data =="success"){
                            alert("修改成功");
                            location.reload();
                        }else{
                            alert(data);
                        }
                    }
                } else {
                    var data = resourceapi.modifyResource(resourceDTO);
                    if( data =="success"){
                        alert("修改成功");
                        location.reload();
                    }else{
                        alert(data);
                    }
                }
            }
        })

})();

function resourceCellDetailHandler(row){
    var edit = $("<a>",{text : "编辑",href : "#/editResource/" + row.id}).addClass("combo_btn");
    var isEnabled = $("<a>",{href : "javascript:modifyResState('" + row.id + "','" + row.isEnabled + "')"}).addClass("combo_btn");
    if(row.isEnabled){
        isEnabled.text("停用");
    }else{
        isEnabled.text("启用");
    }
    //var del = $("<a>" , {text : "删除" , href : "javascript:deleteResource('" + row.id + "','" + row.isLeaf + "')"}).addClass("combo_btn nv-mg");
    var roleBinding = $("<a>" , {text : "角色关联" , href : "javascript:bindingResourcesWithRole('" + row.id + "')"}).addClass("combo_btn nv-mg");
    /*var type;
        if(row.type == 'PAGE'){
            type ="页面";
        }else if(row.type == 'BUTTON'){
            type='功能';
        }else{
            type="";
        }
    row.type=type;*/
    return [
        $("<td>").text(row.uri),

        $("<td>").text(row.type),
        $("<td>")
            .append(edit)
            .append(isEnabled)
            //.append(del)
            .append(roleBinding)
    ]
}
function modifyResState(id , state){
    state = state != "true";
    resourceapi.modifyEnable(id , state);
    alert("修改成功");
    location.reload(true);
}


//function deleteResource(id , isLeaf){
//    if(isLeaf != "true"){
//        alert("请先删除该资源的孩子节点！");
//        return;
//    }
//    resourceapi.deleteResource(id);
//    alert("删除成功");
//    location.reload(true);
//}

function bindingResourcesWithRole(resourceId) {
    location.href = "#/bindingResourcesWithRole/" + resourceId;

}

/**
 * 筛选资源几点为PAGE的节点
 *
 * @param resourceNodes
 * @returns {Array}
 */
function handleResourceNodeFilterPage (resourceNodes) {
    var result = new Array();
    for(var i=0;i<resourceNodes.length;i++) {
        var resourceNode = resourceNodes[i];
//        alert(resourceNode.id)
        if (resourceNode.type == "PAGE") {
            result.push(resourceNode)
        }
    }
    return result;
}