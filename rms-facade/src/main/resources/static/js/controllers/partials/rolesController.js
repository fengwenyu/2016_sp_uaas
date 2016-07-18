var defaultUserPage = {'pageSize': 50, 'page': 1};
var manager = null;
var part = null;
var api = com.shangpin.uaas.api;
var data = new Array();
var maxUserPage = {'pageSize': 1000, 'page': 1};

var groupapi = com.shangpin.uaas.api.admin.org.GroupAdminFacade;
var userapi = com.shangpin.uaas.api.admin.user.UserAdminFacade;
var roleapi = com.shangpin.uaas.api.admin.role.RoleAdminFacade;
var menuapi = com.shangpin.uaas.api.admin.menu.MenuAdminFacade;
var resourceNodeapi = com.shangpin.uaas.api.admin.resource.ResourceNodeAdminFacade;
var resourceapi = com.shangpin.uaas.api.admin.resource.ResourceAdminFacade;
var organizationapi = com.shangpin.uaas.api.admin.org.OrganizationAdminFacade;
var permissionapi = com.shangpin.uaas.api.admin.permission.PermissionAdminFacade;

angular.module("app.index.Roles", []).config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/addRole', { templateUrl: 'partials/role/addRole.html', controller: 'addRoleController' });
    $routeProvider.when('/editRole/:id', { templateUrl: 'partials/role/modifyRole.html', controller: 'editRoleController' });
    $routeProvider.when('/addRolesRig', { templateUrl: 'partials/role/addRoleRig.html', controller: 'addRolesRigController' });
    $routeProvider.when('/roleManager/bindPermissionWithRole/:id', { templateUrl: 'partials/role/bindPermissionWithRole.html', controller: 'bindPermissionWithRoleController' });
    $routeProvider.when('/role/:id/user', { templateUrl: 'partials/role/user.html', controller: 'roleUserController' });
    $routeProvider.when('/deleteRole/:id', { templateUrl: 'partials/role/modifyRole.html', controller: 'editRoleController' });
}])

    .controller("bindPermissionWithRoleController", ["$scope", "$rootScope", "$routeParams", function ($scope, $rootScope, $routeParams) {
        $rootScope.pageTitle = "角色赋权";
        var id = $routeParams.id;
        $scope.role = roleapi.getRole(id);
        var currentResources = resourceNodeapi.findResourceNodesByRoleId([id], maxUserPage);

        currentResources = _.map(currentResources.list, function (obj) {
            return obj.id
        });
        /*console.log(currentResources);*/

        var getChildrenPermissions = function (permissionNodeId) {
            permissionNodeId = permissionNodeId || "1";
            var allResources = resourceNodeapi.findResourceNodeWithFunctionsByParentResourceId(permissionNodeId);
            grantCurrentFunc(allResources);
            return allResources;
        };

        var grantCurrentFunc = function (allResources) {
            _.each(allResources, function (obj) {
                _.each(obj.functions, function (it) {
                    /*console.log(it.resourceId);*/
                    if (_.contains(currentResources, it.id)) {
                        it.granted = true;
                    }
                });
            });
        };

        $scope.subsystems = getChildrenPermissions();

        $scope.grantFunction = function (func) {
            if (func.granted == true) {
                func.granted = false;
                currentResources = _.reject(currentResources, function (obj) {
                    return obj == func.id;
                });
               /* console.log(currentResources);*/
            } else {
                func.granted = true;
                currentResources.push(func.id);
                currentResources = _.uniq(currentResources);
               /* console.log(currentResources);*/
            }
        };

        $scope.toggleNode = function (node) {
            if (!node.hasChildren) {
                console.error('Cannot expand node that does not have children.');
                return;
            }
            if (node.expanded) {
                node.expanded = false;
                node.children = [];
            }
            else {
                console.log(node);
                //
                node.expanded = true;
                node.children = getChildrenPermissions(node.id); //可以把这个替换为传递id
            }
        };

        $scope.bind = function () {
            try {
                permissionapi.bindRoleWithPermissions(id, currentResources);
                alert("绑定权限成功");
                location.reload(true);
            } catch (e) {
                console.error(e);
                alert("绑定权限失败");
            }
        };
    }])
    .controller("roleController", function ($scope) {
        var criteria = {"nameLike": "", "isEnabled": ""};

        $scope.criteria = criteria;
        $scope.changeRoleState = function (role) {
            try {
                roleapi.enabledRole(role.id, !role.isEnabled);
                alert("修改成功！");
            } catch (e) {
                alert("修改失败：" + e);
            }
            $scope.criteriaSearch();

        };

        $scope.paginator = {'pageSize': defaultUserPage.pageSize, 'page': defaultUserPage.page};
        $scope.pageSizeOptions = [10, 20, 50];

        $scope.criteriaSearch = function () {
            var resultCriteria = {"nameLike": $scope.criteria.nameLike, "isEnabled": $scope.criteria.isEnabled}

            if ("" == resultCriteria.isEnabled) {
                resultCriteria.isEnabled = null;
            } else if ("true" == resultCriteria.isEnabled) {
                resultCriteria.isEnabled = true;
            } else {
                resultCriteria.isEnabled = false;
            }

            var returnResult = roleapi.findAllRolesByCriteria(resultCriteria, $scope.paginator);
            var result = returnResult.list;
            $scope.totalPage = returnResult.totalPage;
            $scope.totalItems = returnResult.totalCount;
            $scope.roleList = result;
        };
        $scope.criteriaSearch();
        $scope.pageChanged = function () {
            $scope.criteriaSearch();
        };

        $scope.deleteRole = function (role) {
            if (confirm("删除之后将无法恢复,确认删除该角色么？")) {
                try {
                    roleapi.deleteRole(role.id);
                }catch (e) {

                }
                $scope.criteriaSearch();
            }
        }
    })

    .controller("addRolesRigController", function ($scope) {

    })

    .controller("addRoleController", function ($scope) {
        $scope.role = {
            "id": "",
            "code": "",
            "isEnabled": "true",
            "type": "普通",
            "description": ""
        };

        $scope.addRole = function () {
            try {
                var result = checkRoleCode($scope.role.code);
                if (result) {
                    try {
                       var data =  roleapi.createRole($scope.role);
                        if(data=="success"){
                            alert("创建成功");
                            history.go(-1);
                        }else{
                            alert(data);
                        }
                    } catch (e) {
                        alert("创建失败：" + e);
                    }

                } else {
                    alert("该角色已存在！");
                }
            } catch (e) {
                alert(e)
            }
        }
    })

    .controller("editRoleController", function ($scope, $routeParams) {

        var result = roleapi.getRole($routeParams.id);
        $scope.result = result;
        $scope.submitEditRole = function () {
            var state = checkModifyRoleCode(result.code, result.id);
            if (state) {
                try {
                    roleapi.modifyRole(result);
                    alert("修改成功");
                    history.go(-1);
                } catch (e) {
                    alert("修改失败：" + e);
                }
            }else{
                alert("该角色已存在");
                return;
            }
        }
    })

    .controller("roleUserController", ['$scope', '$routeParams', function ($scope, $routeParams) {
        var roleId = $routeParams.id;
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
            "hasThisRole": ""
        };

        $scope.paginator = {'pageSize': defaultUserPage.pageSize, 'page': defaultUserPage.page};
        $scope.pageSizeOptions = [10,20,50];

        $scope.hasThisRoleOptions = {
            '关联角色状态':'',
            '已关联': true,
            '未关联': false
        };
        $scope.criteriaSearch = function () {

            if (undefined == $scope.pid || "1" == $scope.pid) {
                $scope.criteria.organizationId = "";
            }else{
                $scope.criteria.organizationId = $scope.pid;
            }
            /*if($scope.criteria.hasThisRole==""){
                $scope.criteria.hasThisRole=null;
            }*/
            var returnResult = roleapi.findAllUsersWithRoleByCriteria($scope.criteria, roleId, $scope.paginator);
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
            if(user.hasThisRole)
                roleapi.revokeRoleFromUser(roleId, user.id);
            else
                roleapi.assignRoleToUser(roleId, user.id);
            $scope.criteriaSearch();
        }

    }])

    .filter("isEnabled", function () {
        return function (e) {
            if (e) {
                return "启用";
            } else {
                return "停用";
            }
        }
    });

//function handRoleDTO(result) {
//    for (var i = 0; i < result.length; i++) {
//        var role = result[i];
//        if ((true == role.isEnabled)) {
//            role.state = true;
//            role.isEnabled = "启用"
//        } else {
//            role.state = false;
//            role.isEnabled = "停用"
//        }
//    }
//}

/**
 * 处理用户的显示信息
 *
 * @param result
 */
function handleShowUserDTO(result) {
    for (var i = 0; i < result.length; i++) {
        var user = result[i];
        handleShowUserLevel(user);
    }
    return result;
}

/**
 * 处理用户的职务级别
 *
 * @param user
 * @returns {*}
 */
function handleShowUserLevel(user) {
    if (user.jobLevel == "1") {
        user.jobLevel = "一级（CEO）";
    } else if (user.jobLevel == "2") {
        user.jobLevel = "二级（总经理/VP）";
    } else if (user.jobLevel == "3") {
        user.jobLevel = "三级（高级总监）";
    } else if (user.jobLevel == "4") {
        user.jobLevel = "四级（总监）";
    } else if (user.jobLevel == "5") {
        user.jobLevel = "五级（高级经理/总管）";
    } else if (user.jobLevel == "6") {
        user.jobLevel = "六级（经理）";
    } else if (user.jobLevel == "7") {
        user.jobLevel = "七级（正式职员）";
    } else if (user.jobLevel == "8") {
        user.jobLevel = "八级（试用期职员）";
    } else if (user.jobLevel == "9") {
        user.jobLevel = "九级（兼职/劳务外派）";
    } else {
        user.jobLevel = "未定义";
    }
    return user;
}


function handleState(state) {
    if (state) {
        $("#stateEnabled").attr("checked", "checked")
    } else {
        $("#stateDisabled").attr("checked", "checked")
    }
}


function permissionTreeHandler(dataList , context) {
    var result = [];
    for (var i = 0; i < dataList.length; i++) {
        if (!dataList[i].permissions) {
            dataList[i].permissions = [{id: dataList[i].id , resourceName : "浏览", granted: false}];
        }
        if (dataList[i].hasParent)
            continue;
        var progList = parsePmisTreeSubData(dataList[i], dataList , context);
        if (progList.length > 0) {
            dataList[i].hasChildren = true;
            dataList[i].childrens = progList;
        } else {
            dataList[i].hasChildren = false;
        }
        if (!dataList[i].hasParent && dataList[i].type == "PAGE") {
            result.push(dataList[i]);
        }
    }
    context.permissions = {};
    return filterPmisProperty(result , context);
}

function filterPmisProperty(dataList , context) {
    var resultList = [];
    for (var i = 0; i < dataList.length; i++) {
        var node = dataList[i];
        var item = {name: node.name, id: node.id, hasChildren: node.hasChildren, permissions: node.permissions};
        for(var k = 0 ; k < node.permissions.length ; k++){
            context.permissions[node.permissions[k].id] = node.permissions[k];
        }
        if (node.hasChildren) {
            var children = filterPmisProperty(node.childrens , context);
            item.childrens = children;
        }
        resultList.push(item);
    }
    return resultList;
}

function parsePmisTreeSubData(parent, dataList , context) {
    var subList = [];
    for (var i = 0; i < dataList.length; i++) {
        if (dataList[i].parentId == parent.id) {
            if (dataList[i].type == "BUTTON") {
                var permiss1 = {id: dataList[i].id, resourceName: dataList[i].name, granted: false};
                if (!parent.permissions) {
                    var scan = {id: parent.id , resourceName: "浏览", granted: false};
                    parent.permissions = [scan];
                }
                parent.permissions.push(permiss1);
            } else {
                subList.push(dataList[i]);
                dataList[i].hasParent = true;
                var progList = parsePmisTreeSubData(dataList[i], dataList , context);
                if (progList.length > 0) {
                    dataList[i].hasChildren = true;
                    dataList[i].childrens = progList;
                } else {
                    dataList[i].hasChildren = false;
                }
            }
        }
    }
    return subList;
}


function getRoleState() {
    return $("input:radio[name='state']").filter(":checked").val()
}

function checkRoleCode(roleCode) {
    var role = roleapi.getRoleByCode(roleCode);
    if (null == role) {
        return true;
    }
    return false;
}

function checkModifyRoleCode(roleCode, roleId) {
    var role = roleapi.getRoleByCode(roleCode);
    if (null == role || roleId == role.id) {
        return true;
    }
    return false;
}


function getChecked() {
    var notes = manager.getChecked();
    var permissions = new Array();
    for (var i = 0; i < notes.length; i++) {
        var id = notes[i].data.resourceId;
        console.debug("主键的标识长度为:" + id.length);
        if (id.length < 37) {
            permissions.push(id);
        }
    }

    return permissions
}

function handleSelectPermission(data, roleHasPermissions) {
    for (var i = 0; i < roleHasPermissions.length; i++) {
        var resourceId = roleHasPermissions[i].resourceId;
        console.debug("========" + resourceId);
        for (var j = 0; j < data.length; j++) {
            if (resourceId == data[j].resourceId) {
                data[j].ischecked = true;
            }
        }
    }
    return data;
}

function checkResourceNode(note, checked) {

//    var allResourceNodes =
//    console.debug("" + allResourceNodes.length);
//    for (var i = 0; i < allResourceNodes.length; i++) {
//        var resource = allResourceNodes[i];
//        resource.ischecked = true;
//    }
//    var ss = [];
//    ss.push({id: "11", text: "zhangsan"});
//
//    var notes = manager.getChecked();
//    var newData = new Array();
//    for (var i = 0; i < notes.length; i++) {
//        var note = notes[i];
//        for (var j = 0; j < data.length; j++) {
//            var resource = data[j];
//            if (note.uri == resource.uri) {
//                resource.ischecked = true;
//            } else {
//                resource.ischecked = false;
//            }
//            newData.push(resource);
//
//        }
//    }
//
//    for (var j = 0; j < data.length; j++) {
//        var resource = data[j];
//        for (var i = 0; i < notes.length; i++) {
//            var note = notes[i];
//            if (note.uri == resource.uri) {
//                resource.ischecked = true;
//            } else {
//                resource.ischecked = false;
//            }
//        }
//        newData.push(resource);
//    }
//
//    manager.clear();
//    manager.setData(newData)
//    alert(newData.length);
}
