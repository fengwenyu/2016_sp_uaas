var manager = null;
var part = null;
var page = {'pageSize': 1000, 'page': 1};
var menuapi = com.shangpin.uaas.api.admin.menu.MenuAdminFacade;
var resourceNodeapi = com.shangpin.uaas.api.admin.resource.ResourceNodeAdminFacade;
var organizationapi = com.shangpin.uaas.api.admin.org.OrganizationAdminFacade;
angular.module("app.index.Menu", []).config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/addMenu', { templateUrl: 'partials/menu/addMenu.html', controller: 'addMenuController'});
    $routeProvider.when('/editMenu/:id',{templateUrl:'partials/menu/editMenu.html' , controller:'editMenuController'});
    $routeProvider.when('/uploadMenu',{templateUrl:'partials/menu/uploadMenu.html' , controller: 'uploadMenuController'});
    $routeProvider.when('/exportMenu',{templateUrl:'partials/menu/exportMenu.html' , controller: 'exportMenuController'});
}])
    .controller("menuController", function ($scope) {
        var menuArr = menuapi.getAllMenus();
        var dataList = treeGridDataHandler(menuArr);
        var emtList = generateTreeGridEmt(dataList , menuCellDetailHandler);
        $(".top-tbody").append(emtList);
        var $container = $(".gridtree-container");
        $container.delegate(".treegrid-collapsed" , "click" , function(){
            console.log(".treegrid-collapsed click event");
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

        $scope.updateMenuNotParentId = function () {

            var node = manager.getSelected();
            var menuDto = {id: node.data.id, name: $("#menuName").val(), uri: $("#menuUri").val(), sort: $("#menuSort").val()};
            menuapi.modifyMenu(menuDto);
            alert("修改成功");
            location.reload(true);
        }

        $scope.selectNodeSite = function () {
            if (manager.getSelected()) {
                part = $("#div_part");
                part.empty();
                $("#nodeMessage").css("display", "none");
                $("#nodeSite").css("display", "block");
                var menuArr = menuapi.findMenusByParentId("1", page).list;
                fetchSelect(menuArr);
            } else {
                alert('请先选择节点');
            }
        }

        $scope.updateMenuParentId = function () {
            var node = manager.getSelected();
            var menuDto = {id: node.data.id, parentId: getParentId()};
            updateMenu(menuDto);
        }
        $scope.deleteMenu = function(){
            if (confirm("删除之后将无法恢复,确认删除该菜单么？")) {
                menuapi.deleteMenu(id);
                location.reload(true);
            }
        }
        $scope.removeTreeItem = function () {
            var node = manager.getSelected();
            if (node) {
                if (confirm("删除之后将无法恢复,确认删除该节点么？")) {
                    menuapi.deleteMenu(node.data.id);
                    manager.remove(node.target);
                }
            } else {
                alert('请先选择节点');
            }
        }
    })

    .controller("addMenuController", function ($scope) {

        <!--====================tree结构事件开始====================-->
        var menuArr = menuapi.getAllMenus();
        var dataList = treeGridDataHandler(menuArr);
        var treeNodeList = createTreeContent(dataList);
        var treeContainer = $("#treeContainer");
        treeContainer.append(treeNodeList);
        bindTreeEvent($scope);
        <!--====================tree结构事件结束====================-->

        $scope.addMenu = function () {
            var menuName = $scope.menuName;
            var menuUri = $scope.menuUri;
            var menuSort = $scope.menuSort;
            var pid = $scope.pid;
            if(null ==pid || undefined == pid ||  "" == pid){
                alert("请选择父菜单！");
                return;
            }
            var url = $scope.url;
            if($scope.menu_form.$valid){
                var menuDTO = {id: "",name : menuName , url:url, uri : menuUri , parentId : pid, appCode:$scope.appCode, sort:menuSort};
                try {
                    if ((undefined == menuDTO.parentId || "1" == menuDTO.parentId) && (null == menuDTO.appCode || undefined == menuDTO.appCode ||  "" == menuDTO.appCode)) {
                        alert("顶级模块必须添加应用编码");
                    }else {
                        var data = menuapi.createMenu(menuDTO);
                        if(data =="success"){
                            alert("添加成功");
                            history.go(-1);
                        }else{
                            alert(data);
                        }
                    }
                } catch (e) {
                    alert("创建失败：" + e)
                }

            }else{
//                $scope.menu_form.submitted = true;
                alert("请检查各个字段是否正确");
            }
        }
    })
    .controller("editMenuController" , function($scope , $routeParams){
        var id = $routeParams.id;
        var menu = menuapi.getMenu(id);
        console.log(menu);
        $scope.menuName = menu.name;
        $scope.menuUri = menu.uri;
        $scope.menuSort = menu.sort;
        $scope.pid = menu.parentId;
        $scope.appCode = menu.appCode;
        $scope.url = menu.url;
        <!--====================tree结构事件开始====================-->
        var menuArr = menuapi.getAllMenus();
        var dataList = treeGridDataHandler(menuArr);
        var treeNodeList = createTreeContent(dataList);
        var treeContainer = $("#treeContainer");
        treeContainer.append(treeNodeList);
        bindTreeEvent($scope);
        <!--====================tree结构事件结束====================-->
        $scope.editMenu = function(){
            if($scope.pid == id){
                alert("请选择其他父级菜单");
                return false;
            }
            if($scope.menu_form.$valid){
                var menuDTO = {
                    name : $scope.menuName,
                    uri : $scope.menuUri,
                    sort : $scope.menuSort,
                    parentId : $scope.pid,
                    id : id,
                    appCode:$scope.appCode,
                    url:$scope.url
                };

                try {
                    if ((undefined == menuDTO.parentId || "1" == menuDTO.parentId) && (null == menuDTO.appCode || undefined == menuDTO.appCode ||  "" == menuDTO.appCode)) {
                        alert("顶级模块必须添加应用编码");
                    }else {
                        var data = menuapi.modifyMenu(menuDTO);
                        if(data =="success"){
                            alert("修改成功");
                            location.reload(true);
                        }else{
                            alert(data);
                        }
                    }
                } catch (e) {
                    alert("修改失败：" + e)
                }
            }else{
//                $scope.menu_form.$submitted = true;
                alert("请检查各字段值");
            }
        }
    })
    .controller("uploadMenuController" , function($scope , $routeParams , $location){
        $scope.back = function(){
            $location.path("/menuManager")
        }
    })
    .controller("exportMenuController" , function($scope , $routeParams , $location){
        $scope.back = function(){
            $location.path("/menuManager")
        }
    })

function createTreeContent(dataList){
    var nodeData = {isLeaf : false , children : dataList , name : "根级菜单" , id : 1}
    return createTreeNode(nodeData);
}

function generateTreeEmt(dataList){
    var elementList = [];
    for(var i = 0 ; i < dataList.length ; i++){
        var nodeData = dataList[i];
        elementList.push(createTreeNode(nodeData));
    }
    return elementList;
}

function bindTreeEvent(context , targetListenFuc){
    var state = false;
    $(".combo").click(function(){
        var that = $(this),
            arrow = that.find(".arrow"),
            popup_0 = that.find(".popup_0");

        if(!that.hasClass("in")){
            that.addClass("in");
            arrow.addClass("in");
            if(!state){
                var treeContainer = $("#treeContainer");
                treeContainer.append(context.treeNodeList);
                state = true;
            }
            popup_0.show();
        }else{
            that.removeClass("in");
            arrow.removeClass("in");
            popup_0.hide();
        }
        return false;
    });

    $("body:not(.combo)").click(function(){
        $(".popup").hide();
        $(".combo .arrow, .combo").removeClass("in");
    });

    $(".combo .popuplist").delegate("a.hasLeaf i" , "click" , function(){
        var that = $(this).parent(),
            sbuList = that.siblings(".popuplist");
        if(!that.hasClass("selected")){
            that.addClass("selected");
            sbuList.show();
        }else{
            that.removeClass("selected");
            sbuList.hide();
        }
        return false;
    }).delegate("a em" , "click" , function(){
        var selectTxt = $(this).text();
        $(this).closest(".popup").siblings(".combo_text").text(selectTxt);
        context.pid = $(this).parent().attr("id");
        context.pNodeName = $(this).parent().attr("title");
        if(targetListenFuc){
            targetListenFuc.apply(this,[$(this), context]);
        }
        //return false;
    });
}

function createTreeNode(nodeData){
    if(!nodeData.children || nodeData.children.length == 0 ){
        return $("<a>" , {href : "javascript:void(0)" , title : nodeData.name , id : nodeData.id})
            .addClass("comboItem").append($("<em>").text(nodeData.name));
    }else{
        var pNode = $("<a>" , {href : "javascript:void(0)" , title : nodeData.name , id : nodeData.id})
            .addClass("hasLeaf").append($("<i>")).append($("<em>").text(nodeData.name));
        var subDiv = $("<div>").hide().addClass("popuplist subList");
        var subNodes = generateTreeEmt(nodeData.children);
        subDiv.append(subNodes);
        return $("<div>").append(pNode).append(subDiv);
    }
}

function generateTreeGridEmt(dataList , detailFuc){
    var elementList = [];
    for(var i = 0 ; i < dataList.length ; i++){
        elementList.push(createTreeGridRow(dataList[i] , 0 , detailFuc));
        if(!dataList[i].isLeaf){
            subTreeGridEmt(elementList , dataList[i].children , 1 , detailFuc);
        }
    }
    return  elementList;
}

function subTreeGridEmt(elementList , subList , deepLevel , detailFuc) {
    for (var i = 0; i < subList.length; i++) {
        elementList.push(createTreeGridRow(subList[i], deepLevel , detailFuc));
        if (!subList[i].isLeaf) {
            subTreeGridEmt(elementList , subList[i].children , deepLevel + 1 , detailFuc);
        }
    }
}

function createTreeGridRow(row , deepLevel , detailFuc){
    var cellDiv = $("<div>").addClass("treegrid-cell");
    var indent = $("<span>").addClass("treegrid-indent");
    //var leaf = $("<span>").addClass("treegrid-leaf");
    var title = $("<span>").addClass("treegrid-title");
    //var noLeaf = $("<span>").addClass("treegrid-noleaf");
    //var expended = $("<span>").addClass("treegrid-expanded treegrid-hit");
    var collapsed = $("<span>").addClass("treegrid-collapsed treegrid-hit");
    //var checkbox = $("<input>",{type : "checkbox"});

    if(row.isLeaf){
        for(var i = 0 ; i < deepLevel ; i++){
            cellDiv.append(indent.clone());
                //.append(indent.clone());
        }
        //cellDiv.append(leaf);
    }else{
        for(var i = 0 ; i < deepLevel ; i++){
            cellDiv.append(indent.clone());
        }
        cellDiv.append(collapsed);
            //cellDiv.append(noLeaf);
    }
    cellDiv.append(title.text(row.name));
    var $tr = $("<tr>",{id : row.id , pid : row.parentId})
//        .append($("<td>").addClass("txtCenter").append(checkbox))
        .append($("<td>").append(cellDiv));
    var tds = detailFuc.apply(this,[row]);
    for(var i = 0 ; i < tds.length ; i++){
        $tr.append(tds[i]);
    }
    if(row.hasParent){
        $tr.hide();
    }
    return $tr;
}

function menuCellDetailHandler(row){
    var edit = $("<a>",{text : "编辑",href : "#/editMenu/" + row.id}).addClass("combo_btn");
    var del = $("<a>" , {text : "删除" , href : "javascript:deleteMenu('" + row.id + "')"}).addClass("combo_btn nv-mg");

    return [
        $("<td>").text(row.uri),
        $("<td>").append(edit).append(del)
    ]
}

function deleteMenu (id){
    if (confirm("删除之后将无法恢复,确认删除该菜单么？")) {
        menuapi.deleteMenu(id);
        location.reload(true);
    }
}

function setNodeExtendProperty(node , extendProperty){
    for(var p in extendProperty){
        node[p] = extendProperty[p];
    }
}

function treeGridDataHandler(dataList , extendProperty){
    var result = [];
    for(var i = 0 ; i < dataList.length ; i++){
        if(dataList[i].hasParent)
        continue;
        var progList = parseTreeGridSubData(dataList[i].id , dataList , extendProperty);
        if(progList.length <= 0){
                dataList[i].isLeaf = true;
                dataList[i].hasChildren = false;
                setNodeExtendProperty(dataList[i] , extendProperty);
            }else{
                dataList[i].isLeaf = false;
                dataList[i].hasChildren = true;
                dataList[i].children = progList;
                setNodeExtendProperty(dataList[i] , extendProperty);
            }
        if(!dataList[i].hasParent){
            result.push(dataList[i]);
        }
    }
    return result;
}

function parseTreeGridSubData(pid , dataList , extendProperty){
    var subList = [];
    for(var i = 0 ; i < dataList.length ; i++){
        if(dataList[i].parentId == pid && !(dataList[i].isLeaf)){
            subList.push(dataList[i]);
            dataList[i].hasParent = true;
            var progList = parseTreeGridSubData(dataList[i].id , dataList , extendProperty);
            if(progList.length > 0){
                dataList[i].isLeaf = false;
                dataList[i].hasChildren = true;
                dataList[i].children = progList;
                setNodeExtendProperty(dataList[i] , extendProperty);
            }else{
                dataList[i].isLeaf = true;
                dataList[i].hasChildren = false;
                setNodeExtendProperty(dataList[i] , extendProperty);
            }
        }
    }
    return subList;
}

//<!--tree grid start-->
function recHide(that , pid){
    var trs = that.siblings("tr[pid='" + pid + "']");
    trs.each(function(){
        if($(this).find("span.treegrid-leaf").size() <= 0){
            recHide($(this) , $(this).attr("id"));
        }
        $(this).hide();
    });
}

function recShow(that , pid){
    var trs = that.siblings("tr[pid='" + pid + "']");
    trs.each(function(){
        if($(this).find("span.treegrid-expanded").size() > 0){
            recShow($(this) , $(this).attr("id"));
        }
        $(this).show();
    });
}

function updateMenu(menuDto) {
    menuapi.modifyMenu(menuDto);
    alert("修改成功");
    location.reload(true);
}

function getParentId() {
    var parentId = "";
    var selects = part.find('select');
    if (selects.length == 1)return selects.val();
    else {

        for (var i = selects.length - 1; i >= 0; i--) {
            if (jQuery(selects[i]).val() != 0) {
                parentId = jQuery(selects[i]).val();
                break;
            }
        }
    }
    return parentId;
}

function selectNode() {
    var node = manager.getSelected();
    $("#nodeMessage").css("display", "block");
    $("#nodeSite").css("display", "none");
    $("#menuName").val(node.data.text);
    $("#menuUri").val(node.data.uri);
    $("#menuSort").val(node.data.sort);
    $("#menuParentId").val(node.data.parentId);
    $("#menuId").val(node.data.id);
}

function fetchSelect(dataArr, type) {
    if (dataArr != null && dataArr != "") {
        var sel = "<select>";
        sel += "<option value='0'>请选择</option>";
        for (var i = 0; i < dataArr.length; i++) {
            var m = dataArr[i];
            //if(m.id == id)continue;
            sel += "<option value=\"" + m.id + "\">" + m.name + "</option>";
        }
        sel += "</select>";
        part.append(sel);
        refreshSelects(type);
    }
}

function refreshSelects(type) {
    var selects = part.find('select');
    selects.unbind('change').bind('change', function () {
        // The selected option
        var optioned = $(this).find('option').eq(this.selectedIndex);
        optioned.closest('select').nextAll().remove();
        var menuArr = null;
        if (type == "menu") {
            menuArr = menuapi.findMenusByParentId(optioned.val(), page).list;
        } else if (type == "ResourceNode") {
            menuArr = resourceNodeapi.findResourceNodeByParentResourceId(optioned.val(), page).list;
        } else if (type == "org") {
            menuArr = organizationapi.findAllSubOrganizationsByParentId(optioned.val(), page).list;
        }
        fetchSelect(menuArr, type);
    });
}

