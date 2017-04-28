/************************************客户需求加载下拉列表 start ************************************/
function initCustNeedsSelect2(){
	//加载出发城市下拉
	$("#cOutcity").select2({
		ajax : {
			url : BASE_PATH + "/admin/search/getCustomerCitySelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				var cArrivalcity = $('#cArrivalcity').val();
				if(cArrivalcity){
					cArrivalcity = cArrivalcity.join(',');
				}
				return {
					exname : cArrivalcity,
					cityname : params.term, // search term
					page : params.page
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;
				var selectdata = $.map(data, function (obj) {
					obj.id = obj.dictCode; // replace pk with your identifier
					obj.text = obj.dictCode+'-'+obj.englishName+'-'+obj.countryName; // replace pk with your identifier
					return obj;
				});
				return {
					results : selectdata
				};
			},
			cache : false
		},
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		minimumInputLength : 1,
		maximumInputLength : 20,
		language : "zh-CN", //设置 提示语言
		maximumSelectionLength : 1, //设置最多可以选择多少项
		tags : false //设置必须存在的选项 才能选中
	});

	//加载抵达城市下拉
	$("#cArrivalcity").select2({
		ajax : {
			url : BASE_PATH + "/admin/search/getCustomerCitySelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				var cOutcity = $('#cOutcity').val();
				if(cOutcity){
					cOutcity = cOutcity.join(',');
				}
				return {
					exname : cOutcity,
					cityname : params.term, // search term
					page : params.page
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;
				var selectdata = $.map(data, function (obj) {
					obj.id = obj.dictCode; // replace pk with your identifier
					obj.text = obj.dictCode+'-'+obj.englishName+'-'+obj.countryName; // replace pk with your identifier
					return obj;
				});
				return {
					results : selectdata
				};
			},
			cache : false
		},
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		minimumInputLength : 1,
		maximumInputLength : 20,
		language : "zh-CN", //设置 提示语言
		maximumSelectionLength : 1, //设置最多可以选择多少项
		tags : false //设置必须存在的选项 才能选中
	});

	//加载航空公司下拉
	$("#cAirlineCompany").select2({
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

	//航班号下拉
	$("#cAirlineNum").select2({
		ajax : {
			url : BASE_PATH + "/admin/customneeds/getAirLineSelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				return {
					exname : "",
					airlinename : params.term, // search term
					page : params.page
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;
				var selectdata = $.map(data, function (obj) {
					obj.id = obj.airlinenum; // replace pk with your identifier
					obj.text = obj.airlinenum; // replace pk with your identifier
					return obj;
				});
				return {
					results : selectdata
				};
			},
			cache : false
		},
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		minimumInputLength : 1,
		maximumInputLength : 20,
		language : "zh-CN", //设置 提示语言
		maximumSelectionLength : 1, //设置最多可以选择多少项
		tags : false //设置必须存在的选项 才能选中
	});
}
/************************************客户需求加载下拉列表 end ************************************/

/************************************客户需求的 +需求 按钮 start ************************************/
$('.addDemand').click(function(){
	var divTest = $(this).parent(); 
	var newDiv = divTest.clone(false,true);
	//divTest.after(newDiv);
	$('#infofooter').append(newDiv);
	var No = parseInt(divTest.find("p").html())+1;//用p标签显示序号
	newDiv.find("p").html(No); 
	newDiv.find('.addDemand').remove();
	newDiv.prepend('<a href="javascript:;" class="btn btn-primary btn-sm removeDemand"><b>-</b>&nbsp;&nbsp;&nbsp;需求</a>');
	var divId=document.getElementById('infofooter').getElementsByTagName('div');
	newDiv.find('.titleNum').text(divId.length);
	/**************************************客户需求 设置新的出发城市**************************************/
	newDiv.find('[name=cOutcity]').next().remove();
	newDiv.find('[name=cOutcity]').select2({
		ajax : {
			url : BASE_PATH + "/admin/search/getCustomerCitySelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				var cArrivalcity = newDiv.find('[name=cArrivalcity]').select2("val");
				if(cArrivalcity){
					cArrivalcity = cArrivalcity.join(',');
				}
				return {
					exname : cArrivalcity,
					cityname : params.term, // search term
					page : params.page
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;
				var selectdata = $.map(data, function (obj) {
					obj.id = obj.dictCode; // replace pk with your identifier
					obj.text = obj.dictCode+'-'+obj.englishName+'-'+obj.countryName; // replace pk with your identifier
					return obj;
				});
				return {
					results : selectdata
				};
			},
			cache : false
		},
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		minimumInputLength : 1,
		maximumInputLength : 20,
		language : "zh-CN", //设置 提示语言
		maximumSelectionLength : 1, //设置最多可以选择多少项
		tags : false //设置必须存在的选项 才能选中
	});

	/**************************************客户需求 设置新的抵达城市**************************************/
	newDiv.find('[name=cArrivalcity]').next().remove();
	newDiv.find('[name=cArrivalcity]').select2({
		ajax : {
			url : BASE_PATH + "/admin/search/getCustomerCitySelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				var cOutcity = newDiv.find('[name=cOutcity]').select2("val");
				if(cOutcity){
					cOutcity = cOutcity.join(',');
				}
				return {
					exname : cOutcity,
					cityname : params.term, // search term
					page : params.page
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;
				var selectdata = $.map(data, function (obj) {
					obj.id = obj.dictCode; // replace pk with your identifier
					obj.text = obj.dictCode+'-'+obj.englishName+'-'+obj.countryName; // replace pk with your identifier
					return obj;
				});
				return {
					results : selectdata
				};
			},
			cache : false
		},
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		minimumInputLength : 1,
		maximumInputLength : 20,
		language : "zh-CN", //设置 提示语言
		maximumSelectionLength : 1, //设置最多可以选择多少项
		tags : false //设置必须存在的选项 才能选中
	});

	/**************************************航空段数  设置新的航空公司**************************************/
	newDiv.find('[class=autoAddairLineName]').remove();
	newDiv.find('[name=cAirlineCompany]').select2({
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

	/**************************************设置新的航班号**************************************/
	newDiv.find('[class=autoAddairLineNum]').remove();
	newDiv.find('[name=cAirlineNum]').select2({
		ajax : {
			url : BASE_PATH + "/admin/customneeds/getAirLineSelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				return {
					exname : "",
					airlinename : params.term, // search term
					page : params.page
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;
				var selectdata = $.map(data, function (obj) {
					obj.id = obj.airlinenum; // replace pk with your identifier
					obj.text = obj.airlinenum; // replace pk with your identifier
					return obj;
				});
				return {
					results : selectdata
				};
			},
			cache : false
		},
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		minimumInputLength : 1,
		maximumInputLength : 20,
		language : "zh-CN", //设置 提示语言
		maximumSelectionLength : 1, //设置最多可以选择多少项
		tags : false //设置必须存在的选项 才能选中
	});
	//航空段数 只显示一条
	newDiv.find('.addCustomerAirline').each(function(j){
		if(j == 0){
			//设置新的 航空公司
			newDiv.find('[name=cAirlineCompany]').next().next().remove();
			//设置新的 航班号
			newDiv.find('[name=cAirlineNum]').next().next().remove();
			//设置新的 航空段数   出发日期、抵达日期、销售价、成本
			$(this).find('[name=cAirOutDate]').val('');
			$(this).find('[name=cAirArrivalDate]').val('');
			$(this).find('[name=cAirPretium]').val('');
			$(this).find('[name=cAirCost]').val('');
		}else{
			$(this).remove();
		}
	});
	//客户需求出发日期、人数、早中晚、备注清除
	newDiv.find('[name=cOutDate]').val('');
	newDiv.find('[name=cPersonAmount]').val('');
	//newDiv.find('[name=morningDay]').val(0);
	newDiv.find('[name=cRemark]').val('');
	newDiv.find('[name=airLineClickHidden]').val("1");
	
	/*只在最后一个需求上显示 备注项*/
	/*var cRemarkTxt = $("#cRemark").val();
    $('.remarkTr').remove();*/
   /* $('.DemandDiv:last-child .cloTable tbody').append('<tr name="cRemarkTr" class="remarkTr"><td></span><label>备注：</label></td><td colspan="11"><input type="text" id="cRemark" name="cRemark" value="'+cRemarkTxt+'" class="form-control input-sm noteText" placeholder=" " value=" "></td></tr>');*/
    //$('.DemandDiv:last-child .cloTable tbody').append('<tr name="cRemarkTr" class="remarkTr"><td></span><label>备注：</label></td><td colspan="11"><textarea rows="5" cols="140" id="cRemark" name="cRemark">'+cRemarkTxt+'</textarea></td></tr>');
});

/************************************客户需求的 +需求 按钮 end ************************************/
//客户需求的 -需求 按钮
$(document).on("click",".removeDemand",function(){
	var cRemarkTxt = $("#cRemark").val();
	$(this).parent().remove();
	/*判断最后一个需求是否有 备注项 如果没有 就添加备注项*/
    /*var cl=$('.DemandDiv:last-child .cloTable tbody tr').hasClass('remarkTr');
    if(cl==false){
    	$('.DemandDiv:last-child .cloTable tbody').append('<tr name="cRemarkTr" class="remarkTr"><td></span><label>备注：</label></td><td colspan="11"><input type="text" id="remark" name="remark" class="form-control input-sm noteText" placeholder=" " value=" "></td></tr>');
    	 $('.DemandDiv:last-child .cloTable tbody').append('<tr name="cRemarkTr" class="remarkTr"><td></span><label>备注：</label></td><td colspan="11"><textarea rows="5" cols="140" id="cRemark" name="cRemark">'+cRemarkTxt+'</textarea></td></tr>');
    }*/
});


/************************************客户需求的航空段数 + 按钮 start ************************************/
$(document).on("click",".addIcon",function(){
	var divTest = $(this).parent().parent(); 
	var newDiv = divTest.clone(false,true);
	divTest.parent().find('tr[name!="cRemarkTr"]').last().after(newDiv);

	var No = parseInt(divTest.find("p").html())+1;//用p标签显示序号
	newDiv.find("p").html(No); 
	newDiv.find('.addIcon').parent().remove();
	newDiv.append('<td class="removeIconTd"><i class="glyphicon glyphicon-minus removIcon"></i></td>');

	/**************************************设置新的航空公司**************************************/
	newDiv.find('[name=cAirlineCompany]').next().remove();
	newDiv.find('[class=autoAddairLineName]').remove();
	newDiv.find('[name=cAirlineCompany]').select2({
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

	/**************************************设置新的航班号**************************************/
	newDiv.find('[name=cAirlineNum]').next().remove();
	newDiv.find('[class=autoAddairLineNum]').remove();
	newDiv.find('[name=cAirlineNum]').select2({
		ajax : {
			url : BASE_PATH + "/admin/customneeds/getAirLineSelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				return {
					exname : "",
					airlinename : params.term, // search term
					page : params.page
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;
				var selectdata = $.map(data, function (obj) {
					obj.id = obj.airlinenum; // replace pk with your identifier
					obj.text = obj.airlinenum; // replace pk with your identifier
					return obj;
				});
				return {
					results : selectdata
				};
			},
			cache : false
		},
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		minimumInputLength : 1,
		maximumInputLength : 20,
		language : "zh-CN", //设置 提示语言
		maximumSelectionLength : 1, //设置最多可以选择多少项
		tags : false //设置必须存在的选项 才能选中
	});
	//航空段数   出发日期、抵达日期、销售价、成本清除
	newDiv.find('[name=cAirOutDate]').val('');
	newDiv.find('[name=cAirArrivalDate]').val('');
	newDiv.find('[name=cAirPretium]').val('');
	newDiv.find('[name=cAirCost]').val('');
	newDiv.find('[name=airLineClickHidden]').val("1");
});
/************************************客户需求的航空段数 + 按钮 end ************************************/

//客户需求的航空段数 - 按钮
$(document).on("click",".removIcon",function(){
	$(this).parent().parent().remove();
});


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
		var custOutCity = $(this).find('[name=cOutcity]').select2("val");
		var custArrivalCity = $(this).find('[name=cArrivalcity]').select2("val");
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
			var ClickHiddenInput =  $(this).find('[name=airLineClickHidden]').val();
			if(ClickHiddenInput == "1"){
				var lengthAir = '';
				$(demandE).find('[name=airLineClickHidden]').val("0");
				
				var aircom = $(this).find('[name=cAirlineCompany]').val();
				var ailinenum = $(this).find('[name=cAirlineNum]').val();
				var cAirOutDate = $(this).find('[name=cAirOutDate]').val();
				var cAirArrivalDate = $(this).find('[name=cAirArrivalDate]').val();
				var cAirCost = $(this).find('[name=cAirCost]').val();
				var cAirPretium = $(this).find('[name=cAirPretium]').val();
				
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
				lengthAir += $(this).find('[name=cAirOutDate]').val();
				lengthAir += $(this).find('[name=cAirArrivalDate]').val();
				lengthAir += $(this).find('[name=cAirCost]').val();
				lengthAir += $(this).find('[name=cAirPretium]').val();
				if(lengthAir.length > 0){
					$(demandE).find('[name=addButton]').click();
				}
			}else{
				$(demandE).find('[name=addButton]').click();
			}

			//字典填充航空段数
			$(demandE).find('[class=addCustomerAirline]').last().find('td').each(function(i, tdE){
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
				$(tdE).find('[name=cAirlineCompany]').append('<option class="autoAddairLineName" selected="true">'+airCompName+'</option>'); 
				//航班号回显
				$(tdE).find('[name=cAirlineNum]').append('<option class="autoAddairLineNum" selected="true">'+airLineNum+'</option>'); 
				$(tdE).find('[name=cAirOutDate]').val(DepartureDateTime);
				$(tdE).find('[name=cAirArrivalDate]').val(ArrivalDateTime);
				//成本
				var costRMB = airTotalMoney;
		    	//票价折扣
		    	var discountFare = $("#discountHidden").val();
		    	//手续费
		    	var fees = $("#feeHidden").val(); 
		    	var PretiumRMB = parseFloat(costRMB * discountFare / 100) + parseFloat(fees);
				$(tdE).find('[name=cAirPretium]').val(PretiumRMB);
				$(tdE).find('[name=cAirCost]').val(costRMB);
			});
		}

	});
});
