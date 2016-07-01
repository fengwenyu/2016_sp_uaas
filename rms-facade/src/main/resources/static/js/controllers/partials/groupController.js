(function () {
    var defaultPage = {'pageSize': 5, 'page': 1};
    var page = {'pageSize': 1000, 'page': 1};
    var groupapi = com.shangpin.uaas.api.admin.org.GroupAdminFacade;
    var userapi = com.shangpin.uaas.api.admin.user.UserAdminFacade;
    var roleapi = com.shangpin.uaas.api.admin.role.RoleAdminFacade;
    var menuapi = com.shangpin.uaas.api.admin.menu.MenuAdminFacade;
    var resourceNodeapi = com.shangpin.uaas.api.admin.resource.ResourceNodeAdminFacade;
    var resourceapi = com.shangpin.uaas.api.admin.resource.ResourceAdminFacade;
    var organizationapi = com.shangpin.uaas.api.admin.org.OrganizationAdminFacade;
    var permissionapi = com.shangpin.uaas.api.admin.permission.PermissionAdminFacade;


    var appGroup = angular.module("app.group", ["ngRoute"]).config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when("/addGroup",{templateUrl : "partials/group/addGroup.html" , controller: "addGroupController"});
        $routeProvider.when("/uploadGroup",{templateUrl : "partials/group/uploadGroup.html" , controller: "groupUploadController"});
        $routeProvider.when("/editGroup/:id",{templateUrl : "partials/group/modifyGroup.html" , controller: "editGroupController"});
        $routeProvider.when("/usergroup/:id",{templateUrl : "partials/group/groupUserManager.html" , controller: "userGroupManagerController"});
        $routeProvider.when("/rolegroup/:id",{templateUrl : "partials/group/bindRolesWithGroup.html" , controller: "roleGroupManagerController"});
    }])
        .controller("groupController", function ($scope) {
        /**
         * 分页查找用户组列表
         */
        $scope.paginator = {'pageSize': defaultPage.pageSize, 'page': defaultPage.page};
        $scope.pageSizeOptions = [5,10,20];
        $scope.criteriaSearch = function () {
            var returnResult =  groupapi.findAllGroups($scope.paginator);
            var result = returnResult.list;
            $scope.totalPage = returnResult.totalPage;
            $scope.totalItems = returnResult.totalCount;
            $scope.groupList = result;
        };
        $scope.criteriaSearch();

        $scope.pageChanged = function(){
            $scope.criteriaSearch();
        };

        /**
         *  修改工作组状态
         */
        $scope.modifyStatus = function(groudId , status){
            try{
                groupapi.enabledGroup(groudId , status);
            }catch(e){
                alert("操作失败!");
            }
            $scope.criteriaSearch();
        }
    })
        .controller("addGroupController" , function($scope) {
            $scope.group = {isEnabled : "true"};

            /**
             * 添加用户组
             */
            $scope.addGroup = function(){
                var resultList = groupapi.findGroupsByName($scope.group.name)
                if(resultList.length > 0){
                    alert("该用户组已存在！");
                    return false;
                }
                if($scope.group.isEnabled == "true"){
                    $scope.group.isEnabled = true;
                }else{
                    $scope.group.isEnabled = false;
                }
                try{
                    groupapi.createGroup($scope.group);
                    alert("创建成功！");
                    history.go(-1);
                }catch(e){
                    alert("创建失败！");
                }
            }

        })

        .controller("editGroupController" , function($scope , $routeParams){
            /**
             * 初始化用户组属性
             */
            $scope.group = groupapi.getGroup($routeParams.id);
            $scope.group.isEnabled = $scope.group.isEnabled ? "true" : "false";
            /**
             * 提交用户组修改
             */
            $scope.editGroup = function(){
                var resultList = groupapi.findGroupsByName($scope.group.name);
                if(resultList.length > 1){
                    alert("该用户组已存在！");
                    return false;
                }else if(resultList.length == 1){
                    var item = resultList[0];
                    if(item.id != $scope.group.id){
                        alert("该用户组已存在！");
                        return false;
                    }
                }
                try{
                    groupapi.updateGroup($scope.group);
                    alert("创建成功！");
                }catch(e){
                    alert("创建失败！");
                }
            }
        })
            .controller("userGroupManagerController"  , function($scope , $routeParams){
            var groupId = $routeParams.id;
            //$rootScope.pageTitle="用户管理";

            <!--====================tree结构事件开始====================-->
            var treeArr = organizationapi.getAllOrganizations();
            var dataList = treeGridDataHandler(treeArr);
            var treeNodeList = createTreeContent(dataList);
            var treeContainer = $("#treeContainer");
            treeContainer.append(treeNodeList);
            bindTreeEvent($scope);

            $scope.treeArr = treeArr;
            $scope.resourceType = "PAGE";
            $scope.resourceState = "1";
            <!--====================tree结构事件结束====================-->

            $scope.criteria = {
                "realNameLike": "",
                "gender": "",
                "workLocation": "",
                "status": "",
                "organizationId": "",
                "organizationNameLike":""
            };

            $scope.paginator = {'pageSize': defaultUserPage.pageSize, 'page': defaultUserPage.page};
            $scope.pageSizeOptions = [5,10,20];

            $scope.criteriaSearch = function () {
//                alert("===================");
                if (undefined == $scope.pid || "1" == $scope.pid) {
                    $scope.criteria.organizationId = "";
                }else{
                    $scope.criteria.organizationId = $scope.pid;
                }
                var returnResult = groupapi.findUserByGroupId($scope.criteria, groupId, $scope.paginator);
                var result = returnResult.list;
                result = handleShowUserDTO(result);
                $scope.totalPage = returnResult.totalPage;
                $scope.totalItems = returnResult.totalCount;
                $scope.userList = result;
            };
            $scope.criteriaSearch();

            $scope.pageChanged = function(){
                $scope.criteriaSearch();
            };

            $scope.modifyMembership = function (user) {
//                alert(user.hasThisGroup == false)
                if(user.hasThisGroup == false)
                    groupapi.addUserToGroup(user.id, groupId);
                else
                    groupapi.revokeUserToGroup(user.id, groupId);
                $scope.criteriaSearch();
            }
            })
        .controller("roleGroupManagerController" , function($scope , $routeParams){

            var id = $routeParams.id;
            $scope.group = groupapi.getGroup(id);
            var getChildrenPermissions = function(permissionNodeId) {
                var resourceArr = resourceapi.getAllResources();
                var dataList = permissionTreeHandler(resourceArr , $scope);
                return [{name : "权限列表" , hasChildren : true , children : dataList , permissions: []}];
            };
            $scope.subsystems = getChildrenPermissions();
            $scope.toggleNode = function(node) {
                if(!node.hasChildren) {
                    console.error('Cannot expand node that does not have children.');
                    return;
                }
                if(node.expanded) {
                    node.expanded = false;
                    node.children = [];
                }
                else {
                    //
                    node.expanded = true;
                    node.children = node.childrens; //可以把这个替换为传递id
                }
            };
            /**
             * 获取当前用户组所拥有的角色
             */
            var assignedRoles = groupapi.findRolesByGroupId($routeParams.id);
            $scope.assignedRoles = assignedRoles;
            /**
             * 获取所有角色列表
             */
            var allRoles = roleapi.findAllRoles(page).list;
            var allRolesMap = _.indexBy(allRoles, 'id');
            var getRoleId = function (role) {
                return role.id;
            };

            $scope.notSelectedRoles = _.difference(allRoles.map(getRoleId), assignedRoles.map(getRoleId))
                .map(function (id) {
                    return allRolesMap[id];
                });

            /**
             * 为用户组添加角色事件
             * @param role
             */
            $scope.addRoleWithGroup = function (role) {
                $scope.notSelectedRoles = _.filter($scope.notSelectedRoles, function (r) {
                    return r.id != role.id;
                });
                $scope.assignedRoles.push(role);
                updatePermission($scope);
            };

            $scope.removeRoleWithGroup = function (role) {
                $scope.assignedRoles = _.filter($scope.assignedRoles, function (r) {
                    return r.id != role.id;
                });
                $scope.notSelectedRoles.push(role);
                updatePermission($scope);
            };

            $scope.bindingRole = function () {
                var assignedRoles = $scope.assignedRoles;
                var roleIds = new Array();
                for (var i = 0; i < assignedRoles.length;i++) {
                    var role = assignedRoles[i];
                    roleIds.push(role.id)
                }
                try {
                    /**
                     * 绑定角色与用户组
                     */
//                    alert($routeParams.id);
                    groupapi.bindingRolesWithGroup($routeParams.id, roleIds)
                    alert("绑定成功");
                } catch (e) {
                    alert("绑定失败：" + e);
                }
            };

            /**
             * 初始化权限列表
             */
            updatePermission($scope);

            function updatePermission(context) {
                for(k in context.permissions){
                    context.permissions[k].granted = false;
                }
                var roles = context.assignedRoles;
                console.log(context.permissions);
                for(var i = 0 ; i < roles.length ; i++){
                    var dataList = permissionapi.findAllResourceNodesByRoles([roles[i].id],"BUTTON",page).list;
                    for(var j = 0 ; j < dataList.length ; j++){
                        if(context.permissions[dataList[j].id]){
                            context.permissions[dataList[j].id].granted = true;
                        }
                    }
                }
            }

            /**
             * 绑定用户组角色列表
             */
            $scope.bindRolesToGroup = function(){
                groupapi.bindingRolesWithGroup($routeParams.id , roleIds);
            }

        })
        .controller("groupUploadController" , function($scope , $routeParams , $location){
            $scope.back = function(){
                $location.path("/groupManager");
            }
        })

})();
/**
 * iframe文件长传结果处理事件
 * @param iframe
 */
function onIFrameLoaded(iframe){
    var doc = iframe.contentWindow.document;
    var html = doc.body.innerHTML;
    if (html != '') {
        alert(html);
    }
}


