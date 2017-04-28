/*航空公司查询*/
$("#airline").select2({
	ajax : {
		url : BASE_PATH  + "/admin/search/getAirLineSelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				airlinename : params.term,
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.dictCode;
				obj.text = obj.dictCode +"-"+ obj.dictName; 
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : true
	},

	escapeMarkup : function(markup) {
		return markup;

	}, 
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN",
	maximumSelectionLength : 1, 
	tags : false,
});

/*------------------------搜索条件 下拉列表  start----------------------------*/
/*成人下拉列表*/
var _agentSelect = document.getElementById("agentSelect");
for ( i = 1; i <= 10; i++){  
	var _option = document.createElement("option");  
	_option.value = i;  
	_option.text = "成人"+i;  
	_agentSelect.appendChild(_option);  
}  
/*儿童下拉列表*/
var _childrenSelect = document.getElementById("childrenSelect");
for ( i = 1; i <= 10; i++){  
	var _option = document.createElement("option");  
	_option.value = i;  
	_option.text = "儿童"+i;  
	_childrenSelect.appendChild(_option);  
}  
/*婴儿下拉列表*/
var _babySelect = document.getElementById("babySelect");
for ( i = 1; i <= 10; i++){  
	var _option = document.createElement("option");  
	_option.value = i;  
	_option.text = "婴儿"+i;  
	_babySelect.appendChild(_option);  
}  
/*-----------------------搜索条件 下拉列表  end----------------------------*/

/*-----------------------select2隐藏域赋值  start------------------------*/
/* 客户姓名 */
linkNameOpt = function(){
	$("#linkNameValidator").val($('#linkNameId').find("option:selected").text());
}
/*-----------------------select2隐藏域赋值  end------------------------*/

/*-----------------------单程、往返段数查询  start--------------------------------*/
$(document).on('click','#num1',function(){
	document.getElementById('paragraphListInfo').innerHTML="";
	var index=0;
	$("#airInfoList").val(1);
	$("#origin").val($("#outCity"+index).select2("val"));
	$("#destination").val($("#singleArriveCity"+index).select2("val"));
	$("#departuredate").val($("#outDatepicker"+index).val());
	$("#returndate").val($("#returnDatepicker"+index).val());
	searchInlandOrder();
	$("#num1").attr("class","btnStyle");
	$("#num2").attr("class","");
});
$(document).on('click','#num2',function(){
	document.getElementById('paragraphListInfo').innerHTML="";
	var index=0;
	$("#airInfoList").val(2);
	$("#origin").val($("#singleArriveCity"+index).select2("val"));
	$("#destination").val($("#outCity"+index).select2("val"));
	$("#departuredate").val($("#returnDatepicker"+index).val());
	$("#returndate").val("");
	searchInlandOrder();
	$("#num1").attr("class","");
	$("#num2").attr("class","btnStyle");
});
/*-----------------------往返段数查询  end--------------------------------*/

//清空跨海内陆和国际的任一项
function clearTicketHtml(){
	clearSearchHtml();
	$("#outCity0").val(null).trigger("change");
	$("#singleArriveCity0").val(null).trigger("change");
	document.getElementsByName("voyageType")[1].checked="checked";//radio 默认 选中往返
	document.getElementsByName("nonstopType").checked=true;
	$("#outDatepicker0").val("");
	$("#returnDatepicker0").val("");
}

//清除跨海内陆 国际 列表项
function clearSearchHtml(){
	document.getElementById('travelArea').innerHTML="";
	document.getElementById('travelTypeNum').innerHTML="";
	document.getElementById('travelDateNum').innerHTML="";
	document.getElementById('paragraphListInfo').innerHTML="";
	$("#airline").val(null).trigger("change");
	$("#agentSelect").val("0");
	$("#childrenSelect").val("0");
	$("#babySelect").val("0");
	$("#airLevel").val("1");
}

//清除检索结果
function clearSearchResult(){
	document.getElementById('travelArea').innerHTML="";
	document.getElementById('travelTypeNum').innerHTML="";
	document.getElementById('travelDateNum').innerHTML="";
	document.getElementById('paragraphListInfo').innerHTML="";
}

//清除机票库 列表项
function clearSearchTeamHtml(){
	$("#teamAirline").val(null).trigger("change");
	document.getElementById('travelTeamTypeNum').innerHTML="";
	document.getElementById('datatable2_info').innerHTML="";
	document.getElementById('datatable2_paginate').innerHTML="";
	document.getElementById('datatable2').innerHTML="";
	$("#teamAirLevel").val("1");
}


/* -------------------------日期小卡片 start------------------------------- */
cardDate = function(obj){
	var d = new Date(obj.getAttribute("value"));
	var seperator1 = "-";
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var strDate = d.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var cardStr = year +'-'+ month +'-'+ strDate;
	$("#departuredate").val(cardStr);
	var id = obj.getAttribute("id");
	$("#addbtnStyle").val(id);
	document.getElementById('paragraphListInfo').innerHTML="";
	searchInlandOrder();
}
getDateCard =function(){
	var dateNumHtml = null;
	var outStr = $("#departuredate").val();
	var outDate = new Date(outStr.replace(/-/g,"/"));
	var returnStr = $("#returndate").val();
	var returnDate = new Date(returnStr.replace(/-/g,"/"));
	/* 出发前的日期 */
	var time = outDate.getTime() - new Date().getTime() ; //日期的long型值之差
	var days = Math.floor(time/(24*60*60*1000))+1;
	if(days <=3){
		var dataCardHtml = "";
		for(var i=days; i>=1; i--){
			var beforeDate= outDate.getTime()- 1000*60*60*24*i;
			var changeDate=new Date(beforeDate);
			var formatDate = getNowFormatDate(changeDate);
			dataCardHtml += '<li onclick="cardDate(this);" value="'+changeDate+'" id="card'+formatDate+'">'+formatDate+'</li>';
		}
	}else{
		var dataCardHtml = "";
		for(var i=3; i>=1; i--){
			var beforeDate= outDate.getTime()- 1000*60*60*24*i;
			var changeDate=new Date(beforeDate);
			var formatDate = getNowFormatDate(changeDate).substring(0, 5);
			
			dataCardHtml += '<li onclick="cardDate(this);" value="'+changeDate+'" id="card'+formatDate+'">'+getNowFormatDate(changeDate)+'</li>';
		}
	}
	/* 出发日期 */
	dataCardHtml += '<li onclick="cardDate(this);" class="btnStyle" value="'+outDate+'" id="card'+getNowFormatDate(outDate).substring(0, 5)+'">'+getNowFormatDate(outDate)+'</li>';
	/* 出发后的日期 */
	var travelTime = returnDate.getTime() - outDate.getTime();
	var travelDays = Math.floor(travelTime/(24*60*60*1000))+1;
	if(travelDays>3){
		var afterDate = outDate.getTime();
		for(var i=1; i<=3; i++){
			afterDate+=1000*60*60*24;
			var changeDate=new Date(afterDate);
			var formatDate = getNowFormatDate(changeDate).substring(0, 5);
			dataCardHtml += '<li onclick="cardDate(this);" value="'+changeDate+'" id="card'+formatDate+'">'+getNowFormatDate(changeDate)+'</li>';
		}
	}else{
		var afterDate = outDate.getTime();
		for(var i=1; i<travelDays; i++){
			afterDate+=1000*60*60*24;
			var changeDate=new Date(afterDate);
			var formatDate = getNowFormatDate(changeDate).substring(0, 5);
			dataCardHtml += '<li onclick="cardDate(this);" value="'+changeDate+'" id="card'+formatDate+'">'+getNowFormatDate(changeDate)+'</li>';
		}
	}
	if(!returnDate.getTime()){
		var afterDate = outDate.getTime();
		for(var i=1; i<=3; i++){
			afterDate+=1000*60*60*24;
			var changeDate=new Date(afterDate);
			var formatDate = getNowFormatDate(changeDate).substring(0, 5);
			dataCardHtml += '<li onclick="cardDate(this);" value="'+changeDate+'" id="card'+formatDate+'">'+getNowFormatDate(changeDate)+'</li>';
		}
	}
	document.getElementById('travelDateNum').innerHTML=dataCardHtml;
	
}
/* -------------------------日期小卡片 end------------------------------- */


/* -------------------------日期格式转换 start---------------------------- */
//将分钟数量转换为小时和分钟字符串
toHourMinute = function(minutes){
	var m = parseInt(minutes);
	return (Math.floor(m/60) + "h" + (m%60) + "m" );
}
/*获取某一天是周几*/
getWeekByDay = function(i){ 
	var today = new Array("周日","周一","周二","周三","周四","周五","周六"); //创建星期数组
	return today[i];  //返一个星期中的某一天，其中0为星期日 
}
/*格式化日期为年月*/
getNowFormatDate = function (changeDate){
	var seperator1 = "-";
	var seperator2 = " ";
	var year = changeDate.getFullYear();
	var month = changeDate.getMonth() + 1;
	var strDate = changeDate.getDate();
	var weekday = changeDate.getDay();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var outdate = month +seperator1+ strDate +seperator2+ getWeekByDay(weekday);
	return outdate;
}
/*------------------------日期加减天数   默认返程日期是出发日期的15天---------------------*/
function addDate(dd,count){
	var a = new Date(dd)
	a = a.valueOf()
	a = a + count * 24 * 60 * 60 * 1000
	a = new Date(a)
	return a;
}
defaultDate = function(){
	defaultReturnDate = addDate($("#outDatepicker").val(),15);
	years = defaultReturnDate.getFullYear();
	months = defaultReturnDate.getMonth()+1;
	days = defaultReturnDate.getDate();
	if (months >= 1 && months <= 9) {
		months = "0" + months;
	}
	if (days >= 0 && days <= 9) {
		days = "0" + days;
	}
	var returnDateStr = years +'-'+ months +'-'+days;
	return returnDateStr;
}
function selectSingle(){
	$("#searchSingleTicketsBtn").click();
}
/* -------------------------日期格式转换 end---------------------------- */


/*内陆跨海*/
document.getElementsByName("voyageType")[1].checked="checked";//radio 默认 选中往返
/*国际*/
//document.getElementsByName("voyageType1")[1].checked="checked";//radio 默认 选中往返



//全局回车事件
var keydownIndex = 0;
document.onkeydown=function(event){
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if(keydownIndex){
		var tab1Aira = $("#tab_1Id").attr("aria-expanded");//跨海内陆
		var tab2Aira = $("#tab_2Id").attr("aria-expanded");//机票库
		if(e && e.keyCode==13){ // enter 键
			if(tab1Aira=="true"){
				$("#searchSingleTicketsBtn").click();
			}
			if(tab2Aira=="true"){
				$("#searchTeamTicketsBtn").click();
			}
		}
	}else{
		var tab2Aira = $("#tab_2Id").attr("aria-expanded");
		if(e && e.keyCode==13){ // enter 键
			if(tab2Aira=="true"){
				$("#searchTeamTicketsBtn").click();
			}else{
				$("#searchSingleTicketsBtn").click();
			}
		}
		keydownIndex=1;
	}
}; 

/*清除卡片按钮样式*/
function clearBtnClass(){
	$("#travelDateNum li").attr("class", "");
	var clickBtnId = $("#addbtnStyle").val();
	var btn = document.getElementById(clickBtnId);
	if(btn){
		btn.setAttribute("class", "btnStyle");
	}
}



/*直飞勾选*/
$('#nonstopType').click(function(){                
	if($(this).prop('checked')){
		$("#nonstopType").prop('value',"true");
	}else{
		$("#nonstopType").prop('value',"false");
	}
});

/*点击跨海内陆tab*/
$("#tab_1Id").click(function(){
	clearTicketHtml();
	radioFunct();
	$("#orderStatus option").remove();
	addOtherSelectOpt($("#orderStatus"));
	$("#orderType").val("inlandOrderType");
});
/*点击国际tab*/
$("#tab_3Id").click(function(){
	clearTicketHtml();
	radioFunct();
	$("#orderStatus option").remove();
	addInterSelectOpt($("#orderStatus"));
	$("#orderType").val("interOrderType");
});
/*点击机票库tab*/
$("#tab_2Id").click(function(){
	$("#orderStatus option").remove();
	addOtherSelectOpt($("#orderStatus"));
	document.getElementsByName("voyageType1")[1].checked="checked";
	$("#orderType").val("interOrderType");
	radioFunct1();
});

//国际   订单下拉选项状态
function addInterSelectOpt(obj){
	var optionStr = '<option value="1" selected="selected">查询</option>'+
					'<option value="2">预订</option>'+
					'<option class="interOption" value="6">一订</option>'+
					'<option class="interOption" value="7">二订</option>'+
					'<option class="interOption" value="8">三订</option>'+
					'<option class="interOption" value="9">全款</option>'+
					'<option class="interOption" value="10">尾款</option>'+
					'<option value="3">出票</option>'+
					'<option value="4">开票</option>'+
					'<option value="5">关闭</option>';
	obj.append(optionStr);
}

//内陆跨海等   订单下拉选项状态
function addOtherSelectOpt(obj){
	var optionStr = '<option value="1" selected="selected">查询</option>'+
					'<option value="2">预订</option>'+
					'<option value="3">出票</option>'+
					'<option value="4">开票</option>'+
					'<option value="5">关闭</option>';
	obj.append(optionStr);
}
/************************************飞机票 点击选择按钮  start ************************************/
//航空公司名称
var airCompName = ""; 
//航空号
var airLineNum = "";
//出发时间
var DepartureDateTime = "";
//抵达时间
var ArrivalDateTime = "";
//成本价
var airTotalMoney = "";
$(document).on("click",".chooseAirLineBtn",function(){
	$(".chooseLi").remove();
	var clickedLi = $(this).parent().parent();
	clickedLi.each(function(i){
		airCompName = $(this).find('[class=lineCode]').html();
		airLineNum = $(this).find('[class=p]').html();
		DepartureDateTime = $(this).find('[class=DepartureDateTime]').html();
		ArrivalDateTime = $(this).find('[class=ArrivalDateTime]').html();
		airTotalMoney = $(this).find('[class=airTotalMoney]').html();
	});
	//选择项
	var custLines = '';
	var msgFlag = false;
	$('.DemandDiv').each(function(i){
		var custNeedNum = $(this).find('[class=titleNum]').html();
		var custOutCity = $(this).find('[name=leavecity]').select2("val");
		var custArrivalCity = $(this).find('[name=arrivecity]').select2("val");
		var outFlag = true;
		var arrFlag = true;
		
		if(null==custOutCity || ""==custOutCity){
			custOutCity = "无";
			outFlag = false;
		}
		if(null==custArrivalCity || ""==custArrivalCity){
			custArrivalCity = "无";
			arrFlag = false;
		}
		if(i==0){
			if("无"==custOutCity && "无"==custArrivalCity){
				msgFlag=true;
			}
		}
		
		var custLine = custNeedNum +'. '+ custOutCity +' - '+ custArrivalCity;
		//如果out和arr都为空，则不添加选项
		if(outFlag || arrFlag){
			custLines += '<li class="chooseLi"><a href="javascript:;" class="custLineChoose" value='+custNeedNum+'>'+ custLine +'</a></li>';
		}
	});
	if(msgFlag){
		/*layer.msg("客户需求需填一个城市", "", 3000);*/
		
		$(".airLineCity").hide();
	}else{
		$(".airLineCity").removeAttr("style");
		$(".airLineCity").append(custLines);
	}
});

/************************************飞机票 选择项点击事件  start ************************************/
$(document).on("click",".custLineChoose",function(){
	var chooseLiIndex = $(this).attr("value"); //选择项中的value值
	$('.DemandDiv').each(function(i, demandE){
		var custNeedNum =$(demandE).find('[class=titleNum]').html(); //客户需求的序号 P
		if(custNeedNum == chooseLiIndex){

			//第一次 不加航空段数
			//var ClickHiddenInput =  $(this).find('[name=airLineClickHidden]').val();
			if(i == 0){
				var lengthAir = '';
				
				var aircom = $(this).find('[name=aircom]').val();
				var ailinenum = $(this).find('[name=ailinenum]').val();
				var cAirOutDate = $(this).find('[name=leavetime]').val();
				var cAirArrivalDate = $(this).find('[name=arrivetime]').val();
				var cAirCost = $(this).find('[name=formprice]').val();
				var cAirPretium = $(this).find('[name=price]').val();
				
				if (aircom) {
					aircom = aircom.join(',');
					lengthAir += aircom;
				}else{
					lengthAir += '';
				}
				if (ailinenum) {
					ailinenum = ailinenum.join(',');
					lengthAir += ailinenum;
				}else{
					lengthAir += '';
				}
				lengthAir += $(this).find('[name=leavetime]').val();
				lengthAir += $(this).find('[name=arrivetime]').val();
				lengthAir += $(this).find('[name=formprice]').val();
				lengthAir += $(this).find('[name=price]').val();
				if(lengthAir.length > 0){
					$(demandE).find('[name=addButton]').click();
				}
			}else{
				$(demandE).find('[name=addButton]').click();
			}

			//字典填充航空段数
			$(demandE).find('[name=airlineinfo]').last().find('td').each(function(i, tdE){
				if(airCompName.indexOf("-")<0){
					//航空公司回显
					$.ajax({  
						url : BASE_PATH + "/admin/search/getCAirNameByCode.html",
						dataType : 'json',
						type : 'post',
						async: false,
						data : {  
							"airCompCode" : airCompName  
						},
						success : function(obj) {
							airCompName = airCompName +'-'+ obj;
						}  
					}); 
				}
				$(tdE).find('[name=aircom]').append('<option class="autoAddairLineName" selected="true">'+airCompName+'</option>'); 
				//航班号回显
				$(tdE).find('[name=ailinenum]').append('<option class="autoAddairLineNum" selected="true">'+airLineNum+'</option>'); 
				$(tdE).find('[name=leavetime]').val(DepartureDateTime);
				$(tdE).find('[name=arrivetime]').val(ArrivalDateTime);
				//成本
				var costRMB = airTotalMoney;
		    	/*//票价折扣
		    	var discountFare = discountFare;
		    	//手续费
		    	var fees = $("#fee").val(); */
		    	var PretiumRMB = parseFloat((costRMB * discountFare)/100) + parseFloat(fees);
				$(tdE).find('[name=price]').val(PretiumRMB.toFixed(2));
				$(tdE).find('[name=formprice]').val(costRMB);
			});
		}

	});
});