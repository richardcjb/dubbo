//客户姓名下拉
$("#linkName").select2({
	ajax : {
		url : BASE_PATH+'/admin/search/getLinkNameSelect.html',
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				linkname : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; // replace pk with your identifier
				obj.text = obj.linkMan; // replace pk with your identifier
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
//选中客户姓名之后，其他信息自动填充
/* 选中客户名称 */
$("#linkName").on('select2:select', function (evt) {
	var customerId = $(this).select2("val");
	$("#customerId").val(customerId);
	$.ajax({
		type : 'POST',
		data : {
			"id":$("#customerId").val()
		},
		dataType:'json',
		url : BASE_PATH+'/admin/search/getCustomerById.html',
		success : function(data) {
			var dataJson = jQuery.parseJSON(data); 
			$("#shortName").val(dataJson.customerInfoEntity.shortName);
			$("#telephone").val(dataJson.customerInfoEntity.telephone);
			$("#address").val(dataJson.customerInfoEntity.address);
			$("#responsible").val(dataJson.responsibleName);
			$("#siteUrl").val(dataJson.customerInfoEntity.siteUrl);
			$("#fax").val(dataJson.customerInfoEntity.fax);
			/* 出发城市补全 */
			$("#departureCity").val(dataJson.customerInfoEntity.departureCity);
			var discountFare = 0;
			if(dataJson.customerInfoEntity.discountFare){
				discountFare = dataJson.customerInfoEntity.discountFare;
			}
			$("#discountFare").val(discountFare);
			var fees = 0;
			if(dataJson.customerInfoEntity.fees){
				fees = dataJson.customerInfoEntity.fees;
			}
			$("#fees").val(fees);
			
			if(dataJson.isArrearsRed){
				$('#fontLSqk').css("color","red");
				$("#custInfoName").css("color","red");
			}
			var payType = dataJson.customerInfoEntity.payType;
			if(payType == 1){
				$("#payTypeId").html("月结");
			}else if(payType == 2){
				$("#payTypeId").html("周结");
			}else if(payType == 3){
				$("#payTypeId").html("单结");
			}else if(payType == 4){
				$("#payTypeId").html(dataJson.customerInfoEntity.paytypeName);
			}
			var creditLine = dataJson.customerInfoEntity.creditLine;
			var arrears = dataJson.customerInfoEntity.arrears;
			var preDeposit = dataJson.customerInfoEntity.preDeposit;
			if(creditLine){
				$("#creditLineId").html(dataJson.customerInfoEntity.creditLine);
			}else{
				$("#creditLineId").html("0.00");
			}
			if(arrears){
				$("#arrearsId").html(dataJson.customerInfoEntity.arrears);
			}else{
				$("#arrearsId").html("0.00");
			}
			if(preDeposit){
				$("#preDepositId").html(dataJson.customerInfoEntity.preDeposit);
			}else{
				$("#preDepositId").html("0.00");
			}
		},
		error : function() {
		}
	});
});
/* 清除按钮 */
$("#clearBtn").click(function(){
	clearText();
});
/*客户信息 清除回显内容*/
function clearText(){
	//客户姓名清空
	$("#linkName").val(null).trigger("change");
	//文本框清空
	$("#telephone").val("");
	$("#discountFare").val("");
	$("#fees").val("");
	$("#shortName").val("");
	$("#telephone").val("");
	$("#address").val("");
	$("#responsible").val("");
	$("#siteUrl").val("");
	$("#fax").val("");
	$("#departureCity").val("");
	//付款方式清除
	$("#payTypeId").html("不限");
	//信用额度清除
	$("#creditLineId").html("0.00");
	//历史欠款清除
	$("#arrearsId").html("0.00");
	$('#fontLSqk').css("color","");
	//预存款
	$("#preDepositId").html("0.00");
	//客户名称
	$("#custInfoName").css("color","");
	//清空客户信息隐藏域
	$('#linkManId').val("");
	$("#phoneId").val("");
}
//航空公司下拉
//航红公司下拉
$('#airlinecom').select2({
	ajax : {
		url : BASE_PATH + '/admin/search/getAirLineSelect.html',
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				airlinename : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				  obj.id = obj.dictCode; // replace pk with your identifier
				  obj.text = obj.dictCode + "-" + obj.dictName; // replace pk with your identifier
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
/***************************addinternationalorder**********************/
