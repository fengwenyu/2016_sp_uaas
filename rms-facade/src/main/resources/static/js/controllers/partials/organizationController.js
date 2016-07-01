(function () {
    var PartList = new Array();
    var organizationResponsersList = new Array();//存储已选负责人列表
    var part = null;
    var page = {'pageSize': 1000, 'page': 1};
    var manager;

    var groupapi = com.shangpin.uaas.api.admin.org.GroupAdminFacade;
    var userapi = com.shangpin.uaas.api.admin.user.UserAdminFacade;
    var roleapi = com.shangpin.uaas.api.admin.role.RoleAdminFacade;
    var menuapi = com.shangpin.uaas.api.admin.menu.MenuAdminFacade;
    var resourceNodeapi = com.shangpin.uaas.api.admin.resource.ResourceNodeAdminFacade;
    var organizationapi = com.shangpin.uaas.api.admin.org.OrganizationAdminFacade;

    var appOrg = angular.module("app.index.organization", []).config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/addOrganization', { templateUrl: 'partials/organization/addOrganization.html', controller: 'addOrganization' });
            $routeProvider.when('/modifyOrganization/:id', { templateUrl: 'partials/organization/editOrganization.html', controller: 'modifyOrganization' });
            $routeProvider.when('/moveOrganization/:id', { templateUrl: 'partials/organization/moveOrganization.html', controller: 'moveOrganization' });

        }])
        .controller("organizationController", function ($rootScope, $scope) {

            var getChildrenPermissions = function (permissionNodeId) {
                if (!permissionNodeId) {
                    //如果是顶层node，则返回所有子系统和子系统的一级菜单
                    var organizations = organizationapi.findOneLevelOrganizationsByParentId("1");
                    var result = handleOrganizations(organizations);
                    console.log(result);
                    return result;
                }
                else {
                    //如果不是顶层node，则返回传入node的直接子级菜单
                    console.debug("permissionNodeId.id" + permissionNodeId.id);
                    if (permissionNodeId.id != undefined) {
                        var subOrganizations = organizationapi.findOneLevelOrganizationsByParentId(permissionNodeId.id);
                        var result = handleSubOrganizations(subOrganizations);
                        return result;
                    }
                }
            };

            $scope.subsystems = getChildrenPermissions();

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
                    node.expanded = true;
                    node.children = getChildrenPermissions(node); //可以把这个替换为传递id
                }
            };

            $scope.operation = function (operation) {

                console.debug("操作名称：" + operation.resourceName);
                console.debug("操作名称：" + operation.id);
                if ("编辑" == operation.resourceName) {
                    location.href = "#/modifyOrganization/" + operation.id;
                }

                if ("用户管理" == operation.resourceName) {
                    console.debug("开始跳转");
                    $rootScope.organizationId = operation.id;
                    location.href = "#/userManager/" + operation.id;
                }

                if ("移动" == operation.resourceName) {
                    console.debug("开始跳转");
                    $rootScope.organizationId = operation.id;
                    location.href = "#/moveOrganization/" + operation.id;
                }

                if ("删除" == operation.resourceName) {
                    if(window.confirm("你确定要删除该部门么?")){
                        var users = userapi.findAllTreeUsersByOrganizationId(operation.id, page).list
                        if (users.length > 0) {
                            alert("该部门下有用户不能删除，如果想删除该部门，请移动用户到其他的部门方可删除！");
                        } else {
                            if (operation.hasChildren) {
                                alert("该部门下有子部门，不能删除；如果想删除该部门，请首先删除子部门！");
                            }else {
                                try {
                                    organizationapi.deleteOrganization(operation.id);
                                    location.reload(true);
                                } catch (e) {
                                    alert("删除部门出错：" + e);
                                }
                            }
                        }
                    }

                }
            };

        })

        .controller("modifyOrganization", function ($scope, $routeParams) {
            console.debug("编辑页面");
            var id = $routeParams.id;
            var organization = organizationapi.getOrganization(id);
            $scope.organization = organization;

            var criteria = {
                "realNameLike": "",
                "gender": "",
                "organizationNameLike": "",
                "workLocation": "",
                "status": "",
                "organizationId": ""
            };
            var returnResult = userapi.findAllUsersByCriteria(criteria, page).list;
            $scope.allUsers = returnResult;

            if (organization.organizationLeaders != undefined && organization.organizationLeaders.length > 0) {
                $scope.responserId = organization.organizationLeaders[0].id;
            }

            $scope.editOrganization = function () {
                var organization = $scope.organization;

                if ($scope.responserId != "") {
                    var user = {
                        "id": $scope.responserId,
                        "organizationName": "",
                        "createdTime": "",
                        "userCode": "",
                        "username": "",
                        "realName": "",
                        "mobile": "",
                        "telephone": "",
                        "email": "",
                        "birth": "",
                        "organizationId": "",
                        "jobLevel": "",
                        "status": "",
                        "gender": "",
                        "workPlace": "",
                        "password": "",
                        "directLeaderId": ""
                    };
                    var leaders = new Array();
                    leaders.push(user);
                    organization.organizationLeaders = leaders;
                }

                try {
                    organizationapi.modifyOrganization(organization);
                    alert("修改成功");
                    location.reload(true);
                } catch (e) {
                    alert("修改失败：" + e);
                }

            }
        })

        .controller("moveOrganization", function ($scope, $routeParams) {
            console.debug("移动页面");

            <!--====================tree结构事件开始====================-->
            var treeArr = organizationapi.getAllOrganizations();
            var dataList = treeGridDataHandler(treeArr);
            var treeNodeList = createTreeContent(dataList);
            var treeContainer = $("#treeContainer");
            treeContainer.append(treeNodeList);
            bindTreeEvent($scope);
            $scope.resourceType = "PAGE";
            $scope.resourceState = "1";
            <!--====================tree结构事件结束====================-->


            var id = $routeParams.id;
            var organization = organizationapi.getOrganization(id);
            $scope.organization = organization;

            $scope.moveOrganization = function () {
                try {
                    $scope.organization.parentId = organization.parentId = $scope.pid || "1";
                    organizationapi.moveOrganization(organization.id, $scope.organization.parentId);
                    alert("修改成功");
                    location.reload(true);
                } catch (e) {
                    alert("修改失败：" + e);
                }
            }

        })

        .controller("addOrganization", function ($scope) {

            console.debug("添加部门");
            $scope.organization = {
                id: "",
                description: "",
                isEnabled: "true",
                name: "",
                parentId: null,
                organizationLeaders: null,
                rdn: ""
            };
            var criteria = {
                "realNameLike": "",
                "gender": "",
                "organizationNameLike": "",
                "workLocation": "",
                "status": "",
                "organizationId": ""
            };


            <!--====================tree结构事件开始====================-->
            var treeArr = organizationapi.getAllOrganizations();
            var dataList = treeGridDataHandler(treeArr);
            var treeNodeList = createTreeContent(dataList);
            var treeContainer = $("#treeContainer");
            treeContainer.append(treeNodeList);
            bindTreeEvent($scope);
            $scope.resourceType = "PAGE";
            $scope.resourceState = "1";
            <!--====================tree结构事件结束====================-->

            var returnResult = userapi.findAllUsersByCriteria(criteria, page).list;
            $scope.allUsers = returnResult;

            $scope.addOrganization = function () {
                var organization = $scope.organization;

                if ($scope.responserId != "") {
                    var user = {
                        "id": $scope.responserId,
                        "organizationName": "",
                        "createdTime": "",
                        "userCode": "",
                        "username": "",
                        "realName": "",
                        "mobile": "",
                        "telephone": "",
                        "email": "",
                        "birth": "",
                        "organizationId": "",
                        "jobLevel": "",
                        "status": "",
                        "gender": "",
                        "workPlace": "",
                        "password": "",
                        "directLeaderId": ""
                    };
                    var leaders = new Array();
                    leaders.push(user);
                    organization.organizationLeaders = leaders;
                }
                try {
                    organization.parentId = $scope.pid || "1";

                    var ldapOrganization = organizationapi.findOrganizationByCode(organization.code);
                    if (null == ldapOrganization) {
                        organizationapi.createOrganization(organization);
                        alert("创建成功");
                        //location.reload(true);
                        history.go(-1);
                    } else {
                        alert("该部门编号已存在！");
                    }

                } catch (e) {
                    alert("创建失败：" + e);
                }

            };

        })
        .controller("modifyOrganiztion", function () {
            var id = window.location.href.toString().split('=')[1];  //获取跨View传的ID值
            var organiztion = {id: id, name: "测试名称", parentId: "", organizationResponsers: [
                {id: "001"}
            ]};
            //修改组织架构
            $scope.modelOrganiztion = function () {
                try {
                    $scope.Orglist = organizationapi.modifyOrganization(organiztion).list;
                } catch (e) {
                    alert(e);
                }
            };
            //返回主页面
            $scope.cancel = function () {
                window.history.back();
            };
            //根据ID获取组织架构的信息
            try {
                $scope.orgInfo = organizationapi.getOrganization(id);
            }
            catch (e) {
                alert(e);
            }
        });

    appOrg.filter('searchFor', function () {
        return function (arr, searchString) {
            if (!searchString) {
                return arr;
            }
            var result = [];
            searchString = searchString.toLowerCase();
            // Using the forEach helper method to loop through the array
            angular.forEach(arr, function (item) {
                if (item.title.toLowerCase().indexOf(searchString) !== -1) {
                    result.push(item);
                }
            });
            return result;
        };

    });

    function InstantSearchController($scope) {
        var plist = new Array();
        var pageinfo = {"pageSize": 20, "page": 1};
        var criteria = {"realNameLike": "", "gender": "", "gender": "", "organizationNameLike": "", "workLocation": "", "status": "", "organizationId": "" }
        try {
            var obj = userapi.findAllUsersByCriteria(criteria, pageinfo).list;
            for (var i = 0; i < obj.length; i++) {
                var p = {id: obj[i].id, title: obj[i].realName};
                plist.push(p);
            }
        } catch (e) {
            alert(e);
        }
        $scope.items = plist;
    }

    function selectNodeExt() {
        var node = manager.getSelected();
        $("#nodeMessage").css("display", "block");
        $("#nodeSite").css("display", "none");
        $("#orgName").val(node.data.text);
        $("#orgId").val(node.data.id);
        var pStr = "";
        for (var i = 0; i < node.data.plist.length; i++) {
            pStr = pStr + "已选负责人:&nbsp;&nbsp;<font><span p='pname' value='" + node.data.plist[i].id + "'>" + node.data.plist[i].realName + "</span><a style='text-decoration: underline; color: #1924B1;cursor:pointer' onclick='RemoveName(this)'>删除</a></font>"
        }
        jQuery("#div_selectnamelist").html(pStr);
    }

    function getModifyOrgDTO(organization) {
        organization.id = $("#orgId").val()
        organization.name = $("#orgName").val()
//    var leadIds = $("#div_selectnamelist font span").attr("value");
        var personArr = new Array();
        $("#div_selectnamelist font span").each(function (index, obje) {
            var responserId = {"id": jQuery(obje).attr("value"), "realName": "", "username": "", "gender": null, "userCode": "", "status": null, "mobile": "", "telephone": "", "email": "", "organizationId": "", "directLeaderId": "", "jobLevel": "", "password": "", "workPlace": "", "birth": "", "createdTime": ""};
            personArr.push(responserId);
        })
        organization.organizationLeaders = personArr;
        return organization;
    }

    /**
     * 处理顶级数据方法
     *
     * @param organizations
     * @returns {{name: string, permissions: {resourceName: string, granted: boolean}[], hasChildren: boolean, children: Array}[]}
     */
    function handleOrganizations(organizations) {

        var allNodes = [
            {
                name: '部门名称',
                permissions: [
                    {
                        resourceName: '功能',
                        granted: false
                    }
                ],
                hasChildren: true,
                children: []
            }
        ];

        var items = new Array();
        for (var i = 0; i < organizations.length; i++) {
            var organization = organizations[i];
            var isEnabled = "";
            if (organization.isLeaf == true) {
                console.debug("部门是否是叶子节点if" + isEnabled);
                isEnabled = false;
            } else {
                console.debug("部门是否是叶子节点else" + isEnabled);
                isEnabled = true;
            }
//        console.debug("部门是否是叶子节点" + isEnabled);

//        console.debug("顶级节点====：" + organization.isLeaf);
            var item = {
                name: '',
                leader: "",
                id: "",
                permissions: [
                    {
                        id: organization.id,
                        resourceName: '编辑',
                        granted: false,
                        hasChildren: isEnabled
                    },
                    {
                        id: organization.id,
                        resourceName: '移动',
                        granted: false,
                        hasChildren: isEnabled
                    },
                    {
                        id: organization.id,
                        resourceName: '删除',
                        granted: false,
                        hasChildren: isEnabled
                    },
                    {
                        id: organization.id,
                        resourceName: '用户管理',
                        granted: false,
                        hasChildren: isEnabled
                    }
                ],
                hasChildren: isEnabled
            };
            item.name = organization.name;
            item.leader = organization.organizationLeaders;
            item.id = organization.id;
            items.push(item);
        }
        console.debug("items.length" + items.length);
        allNodes[0].children = items;

        return allNodes;
    }

    /**
     * 处理子级树方法
     *
     * @param organizations
     */
    function handleSubOrganizations(organizations) {

        var subOrganizations = new Array();

        for (var i = 0; i < organizations.length; i++) {
            var organization = organizations[i];

            var isEnabled = "";
            if (organization.isLeaf == true) {
                console.debug("部门是否是叶子节点if" + isEnabled);
                isEnabled = false;
            } else {
                console.debug("部门是否是叶子节点else" + isEnabled);
                isEnabled = true;
            }
//        console.debug("部门是否是叶子节点" + isEnabled);
            var node = {
                name: organization.name,
                leader: "",
                id: "",
                permissions: [
                    {
                        id: organization.id,
                        resourceName: '编辑',
                        granted: false,
                        hasChildren: isEnabled
                    },
                    {
                        id: organization.id,
                        resourceName: '移动',
                        granted: false,
                        hasChildren: isEnabled
                    },
                    {
                        id: organization.id,
                        resourceName: '删除',
                        granted: false,
                        hasChildren: isEnabled
                    },
                    {
                        id: organization.id,
                        resourceName: '用户管理',
                        granted: false,
                        hasChildren: isEnabled
                    }
                ],
                hasChildren: isEnabled
            };
            node.leader = organization.organizationLeaders;
            node.id = organization.id;
            subOrganizations.push(node);
        }

        return subOrganizations;
    }

})();

