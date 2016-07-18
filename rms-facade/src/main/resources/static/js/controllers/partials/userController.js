(function () {
    var defaultUserPage = {'pageSize': 50, 'page': 1};
    var manager = null;
    var part = null;
    var combox = null;
    var page = {'pageSize': 1000, 'page': 1};
    var api = com.shangpin.uaas.api;

    var groupapi = com.shangpin.uaas.api.admin.org.GroupAdminFacade;
    var userapi = com.shangpin.uaas.api.admin.user.UserAdminFacade;
    var roleapi = com.shangpin.uaas.api.admin.role.RoleAdminFacade;
    var menuapi = com.shangpin.uaas.api.admin.menu.MenuAdminFacade;
    var resourceNodeapi = com.shangpin.uaas.api.admin.resource.ResourceNodeAdminFacade;
    var resourceapi = com.shangpin.uaas.api.admin.resource.ResourceAdminFacade;
    var organizationapi = com.shangpin.uaas.api.admin.org.OrganizationAdminFacade;
    var permissionapi = com.shangpin.uaas.api.admin.permission.PermissionAdminFacade;
    
    var appUser = angular.module("app.user", ["ngRoute"]).config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/addUserRoles/:id', { templateUrl: 'partials/user/addRolesWithUser.html', controller: 'addUserRolesControllers' });
        $routeProvider.when('/addUser', { templateUrl: 'partials/user/addUser.html', controller: 'addUserController' });
        $routeProvider.when('/editUser/:id', { templateUrl: 'partials/user/editUser.html', controller: 'editUserController' });
        $routeProvider.when('/uploadUser',{templateUrl:'partials/user/uploadUser.html' , controller: 'uploadUserController'});
        $routeProvider.when('/uploadUserWithRole',{templateUrl:'partials/user/uploadUserWithRole.html' , controller: 'uploadUserController'});
    }])
        .controller("addUserRolesControllers", function ($scope, $routeParams) {
            var id = $routeParams.id;
            var getChildrenPermissions = function(permissionNodeId) {
                var resourceArr = resourceapi.getAllResources();
                var dataList = permissionTreeHandler(resourceArr , $scope);
                console.log(dataList);
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
            var user = userapi.getUser(id);
            $scope.user = user;
            var assignedRoles = roleapi.findRolesByUserId(id, page).list;
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
                updatePermission($scope);
            };

            $scope.removeRoleWithUser = function (role) {
                $scope.assignedRoles = _.filter($scope.assignedRoles, function (r) {
                    return r.id != role.id;
                });
                $scope.notSelectedRoles.push(role);
                updatePermission($scope);
            };

            $scope.bindingRole = function () {
                console.debug("开始绑定角色。。。");
                var assignedRoles = $scope.assignedRoles;
                console.debug("选定角色的数量："+ assignedRoles.length);
                var userId = $scope.user.id;

                var roles = $scope.assignedRoles;
                var roleIds = new Array();
                for (var i = 0; i < assignedRoles.length;i++) {
                    var role = assignedRoles[i];
                    roleIds.push(role.id)
                }
                try {
                    roleapi.modifyRoleAndUserBindings(userId, roleIds)
                    alert("绑定成功");
                    location.reload(true);
                } catch (e) {
                    alert("绑定失败：" + e);
                }

            };

//            初始化权限列表
            updatePermission($scope);

            function updatePermission(context) {
                //traverse tree to update permissions
                //$("a.combo_btn2_selected").removeClass("combo_btn2_selected");
                for(k in context.permissions){
                    context.permissions[k].granted = false;
                }
                var roles = context.assignedRoles;
                console.log(context.permissions)
                for(var i = 0 ; i < roles.length ; i++){
                    var dataList = permissionapi.findAllResourceNodesByRoles([roles[i].id],"BUTTON",page).list;
                    for(var j = 0 ; j < dataList.length ; j++){
                        if(context.permissions[dataList[j].id]){
                            context.permissions[dataList[j].id].granted = true;
                        }
                    }
                }
            }


        })

        .controller("addUserController", function ($scope) {

            <!--====================tree结构事件开始====================-->
            var treeArr = organizationapi.getAllOrganizations();
            var dataList = treeGridDataHandler(treeArr);
            var treeNodeList = createTreeContent(dataList).html();
            var treeContainer = $("#treeContainer_adduser");
            treeContainer.html(treeNodeList);
            bindTreeEvent($scope, function(a,b){
//                console.debug("=====" + $scope.pid);
                /**
                 * 初始化所有员工－－直属领导选择
                 * @type {{realNameLike: string, gender: string, organizationNameLike: string, workLocation: string, status: string, organizationId: string}}
                 */


            });

            var allUserCriteria = {
                "realNameLike": "",
                "gender": "",
                "organizationNameLike": "",
                "workLocation": "",
                "status": "",
                "organizationId": ""
            };

            $scope.allUsers = userapi.findAllUsersByCriteria(allUserCriteria, page).list;
            console.log($scope.allUsers);
            var users = $scope.allUsers;

            console.debug("===========" + $("#txt3").val);

            $scope.treeArr = treeArr;
            $scope.resourceType = "PAGE";
            $scope.resourceState = "1";
            <!--====================tree结构事件结束====================-->

            $scope.user = {
                "id": "",
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
                "status": "ENABLED",
                "gender": "MALE",
                "workPlace": "",
                "password": "shangpin001",
                "directLeaderId": "",

                "jobTitle": "",
                "company": "",
                "entryDate": "",
                "jobTitleDate": "",
                "contractStartDate": "",
                "probationEndDate": "",
                "positiveDate": "",
                "firstContractEndDate": "",
                "secondContractEndDate": "",
                "turnoverDate": "",
                "employeeStatus": "",

                "identity": "",
                "identityNumber": "",
                "age": "",
                "nation": "",
                "nationality": "",
                "education": "",
                "degree": "",
                "learningType": "",
                "politicalAffiliation": "",
                "workStartDate": "",
                "graduationTime": "",
                "familyRegister": "",
                "foreignEnglish": "",
                "foreignRussian": "",
                "foreignFrench": "",
                "foreignKorean": "",
                "foreignJapanese": "",
                "maritalStatus": "",
                "birthPlace": "",
                "salaryBank": "",
                "salaryBankNumber": ""

            };

            /**
             * 点击时候自动生成随机密码
             */
            $scope.randomPassword = function () {
                $scope.user.password = randomPassword();
            };

            /**
             * 添加用户
             */
            $scope.addUser = function () {

                var checkState = checkUserHtml($scope);
                if(!checkState){
                    return;
                }
                checkState = checkUserNo($scope.user.userCode);
                if (!checkState) {
                    return false;
                }
                checkState = checkUserName($scope.user.username);
                if (!checkState) {
                    return false;
                }

                if (checkState) {
                    try {
                        console.debug($scope.user.organizationId);
                        $scope.user.organizationId = $scope.pid;
                        var data = userapi.createUser($scope.user);
                        if(data =="success"){
                            alert("创建用户成功！");
                            history.go(-1);
                        }else{
                            alert(data);
                        }
                    } catch (e) {
                        alert("创建用户出错：" + e);
                    }
                }
            };

            $scope.copyDate = function(){
                var idNumber = $scope.user.identityNumber;
                if (idNumber!=null && idNumber!="") {
                    //15位和18位身份证号码的正则表达式
                    var regIdCard=/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
                    //如果通过该验证，说明身份证格式正确，但准确性还需计算
                    if(regIdCard.test(idNumber)&&idNumber.length == 18) {
                        var year = idNumber.substring(6, 10)+"-"+idNumber.substring(10, 12)+"-"+idNumber.substring(12, 14);
                        var age=userapi.getUserAge(year);
                        $scope.user.birth = year;
                         $scope.user.age = age;
                    }
                }
            };
            $scope.copyAge = function(){
               var year = $scope.user.birth;
                var age=userapi.getUserAge(year);
               // $("#userAge").val(age);
                $scope.user.age = age;
            }

        })

        .controller("userController", function ($rootScope, $scope, $routeParams) {
            //$rootScope.pageTitle="用户管理";
//            alert($routeParams.orgId);
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
                "organizationNameLike": "",
                "workLocation": "",
                "status": "",
                "organizationId": ""
            };


//            if ($rootScope.organizationId != undefined) {
//                $scope.criteria.organizationId = $rootScope.organizationId;
//            }

            $scope.paginator = {'pageSize': defaultUserPage.pageSize, 'page': defaultUserPage.page};
            $scope.pageSizeOptions = [10,20,50];

            var organizationCriteria = {
                "organizationNameLike": "",
                "leaderNameLike": ""
            };
            if ($routeParams.orgId != undefined && $routeParams.orgId != $scope.criteria.organizationId) {
//                    alert("asdfgsadf");
                $scope.pid = $routeParams.orgId
            }
//            $scope.organizations = organizationapi.findAllOrganizationsByCriteria(organizationCriteria, page).list;

            $scope.criteriaSearch = function () {

                if (undefined == $scope.pid || "1" == $scope.pid) {
                    $scope.criteria.organizationId = "";
                }else{
                    $scope.criteria.organizationId = $scope.pid;
                }



                var returnResult = userapi.findAllUsersByCriteria($scope.criteria, $scope.paginator);
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

            $scope.modifyUserState = function (user) {
                console.debug(user.status);
                if (user.status == "DISABLED") {
                    try {
                        userapi.modifyEnable(user.id, "ENABLED");
                        var returnResult = userapi.findAllUsersByCriteria($scope.criteria, $scope.paginator);
                        var result = returnResult.list;
                        console.debug("=======" + result.length);
                        result = handleShowUserDTO(result);
                        $scope.userList = result;
                        $scope.totalItems = returnResult.totalCount;
                    } catch (e) {
                        alert("修改状态出错：" + e);
                    }

                } else {
                    try {
                        userapi.modifyEnable(user.id, "DISABLED");
                        var returnResult = userapi.findAllUsersByCriteria($scope.criteria, $scope.paginator);
                        var result = returnResult.list;
                        var totalPage = returnResult.totalPage;
                        var currentPage = returnResult.currentPage;
                        result = handleShowUserDTO(result);
                        $scope.userList = result;
                        $scope.totalItems = returnResult.totalCount;
                    } catch (e) {
                        alert("修改状态出错：" + e);
                    }
                }
                $scope.criteriaSearch();
            }

        })

        .controller("editUserController",function ($scope, $routeParams, $location) {


            <!--====================tree结构事件开始====================-->
            var treeArr = organizationapi.getAllOrganizations();
            var dataList = treeGridDataHandler(treeArr);
            var treeNodeList = createTreeContent(dataList);
            var treeContainer = $("#treeContainer_edituser");
            treeContainer.html(treeNodeList);
//            $(treeNodeList).each(function(){
//                treeContainer.append(treeNodeList);
//            });
            bindTreeEvent($scope);
            $scope.treeArr = treeArr;
            $scope.resourceType = "PAGE";
            $scope.resourceState = "1";
            <!--====================tree结构事件结束====================-->

            var id = $routeParams.id;
            $scope.user = userapi.getUser(id);
            $scope.user.password = "";
            $scope.user.organizationName = organizationapi.getOrganization($scope.user.organizationId).name;
            $scope.pid = $scope.user.organizationId;
            /**
             * 初始化部门
             */
//            $scope.organizations = organizationapi.getAllOrganizations();

            /**
             * 初始化所有员工－－直属领导选择
             * @type {{realNameLike: string, gender: string, organizationNameLike: string, workLocation: string, status: string, organizationId: string}}
             */
            var allUserCriteria = {
                "realNameLike": "",
                "gender": "",
                "organizationNameLike": "",
                "workLocation": "",
                "status": "",
                "organizationId": ""
            };

            $scope.allUsers = userapi.findAllUsersByCriteria(allUserCriteria, page).list;
            $scope.copyDate = function(){
                var idNumber = $scope.user.identityNumber;
                if (idNumber!=null && idNumber!="") {
                    //15位和18位身份证号码的正则表达式
                    var regIdCard=/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
                    //如果通过该验证，说明身份证格式正确，但准确性还需计算
                    if(regIdCard.test(idNumber)&&idNumber.length == 18) {
                        var year = idNumber.substring(6, 10)+"-"+idNumber.substring(10, 12)+"-"+idNumber.substring(12, 14);
                        var age=userapi.getUserAge(year);
                        $scope.user.birth = year;
                        $scope.user.age = age;
                    }
                }
            };
            $scope.copyAge = function(){
                var year = $scope.user.birth;
                var age=userapi.getUserAge(year);
                // $("#userAge").val(age);
                $scope.user.age = age;
            }
            //创建用户树开始

           /* var dataList_user = treeGridDataHandler($scope.allUsers);
            var treeNodeList_user = createTreeContent(dataList_user);
            var treeContainer_user = $("#select2-search-choice-close");
            treeContainer.html(treeContainer_user);
            bindTreeEvent($scope);
            $scope.treeArr = treeArr;
            $scope.resourceType = "PAGE";
            $scope.resourceState = "1";*/


            //创建用户树结束






            $scope.editUser = function () {
                var checkState = checkUserHtml($scope);
                if(!checkState){
                    return ;
                }
                checkState = checkModifyUserName($scope.user.id,$scope.user.username);
                if(!checkState){
                    return ;
                }
                checkState = checkModifyUserNo($scope.user.id,$scope.user.userCode);
                if(!checkState){
                    return ;
                }
                if (checkState) {
                    $scope.user.organizationId = $scope.pid;
                    try {
                        userapi.modifyUser($scope.user);
                        alert("用户修改成功！");
                        history.go(-1);
                    } catch (e) {
                        alert("用户修改失败" + e);
                    }
                }
            };
            $scope.back = function(){
                $location.path("/userManager");
            }
        })

        .controller("uploadUserController" , function($scope , $routeParams , $location){
            $scope.back = function(){
                $location.path("/userManager")
            }
        })

        .filter("enum", function () {
            return function (e) {
            	if(e=="MALE"){
            		return "男";
            	}else if(e=="FEMALE"){
            		return "女";
            	}else if(e=="ENABLED"){
            		return "启用";
            	}else if(e=="DISABLED"){
            		return "停用";
            	}else{
            		return "";
            	} 
            }
        });

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

    function handleSelectedRoles(roles, obj) {
        var rs = [];
        for (var i = 0; i < roles.length; i++) {
            if (roles[i].id != obj.id) {
                rs.push(roles[i].id)
            }
        }
        return rs
    }

    function getAllSelectedRoleId(roles) {
        var roleIds = [];
        for (var i = 0; i < roles.length; i++) {
            roleIds.push(roles[i].id);
        }
        return roleIds;
    }

    /**
     * 检查用户编号
     *
     * @returns {boolean}
     */
    function checkUserNo(userNo) {
        var user = null;
        try {
            user = userapi.getUserByUserCode(userNo);
        } catch (e) {
            alert("获取用户编号出错:" + e);
        }

        if (null != user) {
            alert("该用户编号已存在!");
            return false;
        }
        return true;
    }

    /**
     * 检查用户名是否存在
     *
     * @param userName
     * @returns {boolean}
     */
    function checkUserName(userName) {
        var user = null;
        try {
            user = userapi.getUserByUsername(userName);
        } catch (e) {
            alert("获取用户名时候出错:" + e);
        }
        if (null != user) {
            alert("该用户名已存在!");
            return false;
        }
        return true;
    }

    function checkModifyUserNo(userId,userNo) {
       // console.debug("焦点离开～" + $("#userNo").val());
       // var userNo = $("#userNo").val();
        var user = userapi.getUserByUserCode(userNo);
        if (user !=null  && user.id != userId) {
            alert("该用户编号已存在!");
            return false
        }
        return true;
    }

    /**
     * 修改用户时候检查用户名
     *
     * 该方法需要多一个判断，就是当前的用户名如果一致情况可以重复的
     *
     * @param userId
     * @returns {boolean}
     */
    function checkModifyUserName(userId , userName) {
        var b = userapi.verifyUserName(userId , userName);
        if(!b){
            alert("用户名称已存在!");
            return false;
        }
        return true;
    }

    /**
     * 检查邮箱格式 Angular的默认检查没有@.**
     *
     * @param email
     */
    function checkEmail (email) {
        var re = /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;

        if (email != null && email != "" && !re.test(email)) {
            alert("邮箱格式错误");
            return false;
        }

        return true;
    }

    /**
     * 生成随机密码
     * @returns {string}
     */
    function randomPassword() {
        var $chars = 'abcdefghijklmnopqrstuvwxyz';
        var maxPos = $chars.length;
        var pwd = '';
        for (i = 0; i < 3; i++) {
            pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
        }

        var $nums = '1234567890'
        maxPos = $nums.length;
        for (i = 0; i < 3; i++) {
            pwd += $nums.charAt(Math.floor(Math.random() * maxPos));
        }
        return pwd;
    }

    /**
     * 处理顶级数据方法
     *
     * @param organizations
     * @returns {{name: string, permissions: {resourceName: string, granted: boolean}[], hasChildren: boolean, children: Array}[]}
     */
    function handleTopNodes(resourceNodes) {

        var allNodes = new Array();
        for (var i = 0; i < resourceNodes.length; i++) {
            var resourceNode = resourceNodes[i]
            node = {
                "name":resourceNode.name,
                "permissions":[],
                "hasChildren":true,
                "children":[]
            };
            var permissions = new Array();
            var subPermissions = resourceNodeapi.findResourceNodeByParentResourceId(resourceNode.id);

            for (var j = 0; j < subPermissions.length; j++) {
                var perm = subPermissions[j];

                permissions.push()

            }

            allNodes.push(node)
        }

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


    function checkUserHtml($scope){
        /**
         * 用户编码长度校验
         */
        if($scope.user.userCode.length > 25){
            alert("用户编码过长！");
            return false;
        }

        /**
         * 用户名称长度校验
         */
        if($scope.user.username.length > 250){
            alert("用户名称过长！");
            return false;
        }

        /**
         * 用户姓名长度校验
         */
        if($scope.user.realName.length > 25){
            alert("员工姓名过长！");
            return fasle;
        }
        var checkmobile = checkMobile();
        if (!checkmobile) {
            return checkmobile;
        }

        /**
         * 校验邮箱格式
         */
        if (undefined != $scope.user.email && "" != $scope.user.email) {
            if(!/(^\s*)[_|0-9|a-z|A-Z]+@[0-9|a-z|A-Z]+\.[a-z]+(\s*$)/g.test($scope.user.email)){
                alert("邮箱格式不正确");
                return  false;
            }
        }

        if (undefined == $scope.pid || "1" == $scope.pid) {
            alert("请选择所在部门");
            return false;
        }
        if ($scope.user.workPlace==undefined||$scope.user.workPlace == "") {
            alert("请选择办公区");
            return false;
        }

        if ($scope.user.employeeStatus==undefined||$scope.user.employeeStatus == "") {
            alert("请选择员工状态");
            return false;
        }


        if ($scope.user.identity==null || $scope.user.identity == "") {
            alert("请选择身份标识");
            return false;
        }

        if ($scope.user.identityNumber !=null && $scope.user.identityNumber!="") {
            if($scope.user.identity=="身份证"){
                var idCard = $scope.user.identityNumber;
                //15位和18位身份证号码的正则表达式
                var regIdCard=/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
                //如果通过该验证，说明身份证格式正确，但准确性还需计算
                if(regIdCard.test(idCard)) {
                    if (idCard.length == 18) {
                        var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); //将前17位加权因子保存在数组里
                        var idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2); //这是除以11后，可能产生的11位余数、验证码，也保存成数组
                        var idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
                        for (var i = 0; i < 17; i++) {
                            idCardWiSum += idCard.substring(i, i + 1) * idCardWi[i];
                        }

                        var idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
                        var idCardLast = idCard.substring(17);//得到最后一位身份证号码

                        //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
                        if (idCardMod == 2) {
                            if (idCardLast != "X" && idCardLast != "x") {
                                alert("请输入正确的证件号码");
                                return false;
                            }
                        } else {
                            //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                            if (idCardLast != idCardY[idCardMod]) {
                                alert("请输入正确的证件号码");
                                return false;
                            }
                        }
                    }
                }else{
                    alert("请输入正确的证件号码");
                    return false;
                }
            }
        }else{
            alert("请输入证件号码");
            return false;
        }
        return true;
    }


    /**
     * 检查手机格式
     */
    function checkMobile() {

        var re = /^1[3|4|5|8][0-9]\d{4,8}$/;
        var mobile = $("#mobile").val();
        if (mobile != null && mobile != "" && !re.test(mobile)) {
            alert("手机号格式错误");
            return false;
        }

        return true;
    }

    /**
     * 检查电话格式
     *
     * @returns {boolean}
     */
    function checkTelephone() {

        var re = /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;
        var telephone = $("#telephone").val();
        if (telephone != null && telephone != "" && !re.test(telephone)) {
            alert("座机号码格式不正确");
            return false;
        }
        return true;
    }



})();




