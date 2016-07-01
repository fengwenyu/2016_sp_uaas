// JavaScript Document
$(function(){
	
	//左侧菜单树目录导航
	$("#menu li a.tree").click(function(){
		var that = $(this);
		if(!that.hasClass("open")){
			that.addClass("open");
			that.siblings("p").slideDown(300);
		}else{
			that.removeClass("open");
			that.siblings("p").slideUp(300);
		}
		return false;
	});

	
	//*====================   分类搜索框   ====================*//
	$(".combo").click(function(){

        var that = $(this),
			arrow = that.find(".arrow"),
			popup_0 = that.find(".popup_0");
		
		if(!that.hasClass("in")){
			that.addClass("in");
			arrow.addClass("in");
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
	
	$(".combo .popuplist a.hasLeaf").click(function(){
		var that = $(this),
			sbuList = that.siblings(".popuplist");
		
		if(!that.hasClass("selected")){
			that.addClass("selected");
			sbuList.show();
			
		}else{
			that.removeClass("selected");
			sbuList.hide();
		}
		return false;
	});
	
	$(".combo .popuplist a.comboItem").click(function(){
		var selectTxt = $(this).text();
		$(this).closest(".popup").siblings(".combo_text").text(selectTxt);
	});
	
	//高级查询事件
	var isAll = false;
//	$(".main_filter a.combo_btn").click(function(){
//		var that = $(this),
//			subbox = $(".main_filter .sub_filter");
//
//		if(isAll == false){
//			that.text("收 起");
//			subbox.slideDown(300);
//			isAll = true;
//		}else{
//			that.text("高级查询");
//			subbox.slideUp(300);
//			isAll = false;
//		}
//
//	});
	
	//*====================   分类搜索框end   ====================*//
	
	
	//*====================   列表事件   ====================*//
	
	//tab切换事件
	var tabs = function(nav,content){
		$(nav).find("li").bind("click", function(){
			var index = $(this).index();
			$(this).addClass("cur").siblings().removeClass("cur");
			$(content).eq(index).show().siblings(content).hide();
		});
	}
	
	tabs(".tabsMenu", ".tabsCell");
	
	$(".top-table td .yesNo").click(function(){
		$(this).toggleClass("yes");
		return false;
	});
	
	$(".top-table th a.closeBtn").click(function(){
		
		var that = $(this),
			box = that.attr("data-title");
		
		if(!that.hasClass("up")){
			that.addClass("up");
			$(".top-table tbody[title='"+ box +"']").hide();
		}else{
			that.removeClass("up");
			$(".top-table tbody[title='"+ box +"']").show();
		}
		
	});
	
	//*====================   列表事件end   ====================*//
	
});