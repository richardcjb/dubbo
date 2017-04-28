/* 加载客户姓名下拉 */
$("#linkNameId").select2({
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
		cache : true
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

/* 加载电话下拉 */
$("#phoneNumId").select2({
	ajax : {
		url : BASE_PATH+'/admin/search/getPhoneNumSelect.html',
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				phonenum : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; // replace pk with your identifier
				obj.text = obj.telephone; // replace pk with your identifier
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
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});
/* 加载出发城市样式 */
$("#city").select2({});

/* 选中客户名称 */
$("#linkNameId").on('select2:select', function (evt) {
	var customerId = $(this).select2("val");
	$("#linkManId").val(customerId);
	$("#phoneId").val(customerId);
	$.ajax({
		type : 'POST',
		data : {
			"id":$("#linkManId").val()
		},
		dataType:'json',
		url : BASE_PATH+'/admin/search/getCustomerById.html',
		success : function(data) {
			var dataJson = jQuery.parseJSON(data); 
			var phoneNum = dataJson.customerInfoEntity.telephone;
			/* 电话补全 */
			$("#phoneNumId").append('<option selected="true" value='+ id +'>'+phoneNum+'</option>'); 
			/* 出发城市补全 */
			$("#city").select2({
				initSelection : function (element, callback) {
					var data = dataJson.outcitylist;
					callback(data);
				}
			});
		
			if(dataJson.isArrearsRed){
				$('#fontLSqk').css("color","red");
				$("#custInfoName").css("color","red");
			}
			
			var id = dataJson.customerInfoEntity.id;
			var payType = dataJson.customerInfoEntity.payType;
			var creditLine = dataJson.customerInfoEntity.creditLine;
			var arrears = dataJson.customerInfoEntity.arrears;
			var preDeposit = dataJson.customerInfoEntity.preDeposit;
			$("#addressId").val(dataJson.customerInfoEntity.address);
			$("#shortNameId").val(dataJson.customerInfoEntity.shortName);
			$("#responsibleId").val(dataJson.responsibleName);
			$("#siteUrlId").val(dataJson.customerInfoEntity.siteUrl);
			$("#faxId").val(dataJson.customerInfoEntity.fax);
			
			/*票价折扣*/
			$("#discountHidden").val(dataJson.customerInfoEntity.discountFare);
			/*手续费*/
			$("#feeHidden").val(dataJson.customerInfoEntity.fees);
			/*汇率*/
			$("#ratesHidden").val(dataJson.customerInfoEntity.exchangeRates);
			
			if(payType == 1){
				$("#payTypeId").html("月结");
			}else if(payType == 2){
				$("#payTypeId").html("周结");
			}else if(payType == 3){
				$("#payTypeId").html("单结");
			}else if(payType == 4){
				$("#payTypeId").html(dataJson.customerInfoEntity.paytypeName);
			}
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
/* 选中电话 */
$("#phoneNumId").on('select2:select', function (evt) {
	var _customerId = $(this).select2("val");
	$("#phoneId").val(_customerId);
	$("#linkManId").val(_customerId);
	$.ajax({
		type : 'POST',
		data : {
			id:$("#phoneId").val()
		},
		dataType:'json',
		url : BASE_PATH+'/admin/search/getCustomerById.html',
		success : function(data) {
			var dataJson = jQuery.parseJSON(data); 
			var linkName = dataJson.customerInfoEntity.linkMan;
			/* 客户名称补全 */
			$("#linkNameId").append('<option selected="true" value='+ id +'>'+linkName+'</option>'); 
			/* 出发城市补全 */
			$("#city").select2({
				initSelection : function (element, callback) {
					var data = dataJson.outcitylist;
					callback(data);
				}
			});
			
			if(dataJson.isArrearsRed){
				$('#fontLSqk').css("color","red");
				$("#custInfoName").css("color","red");
			}
			
			var id = dataJson.customerInfoEntity.id;
			var payType = dataJson.customerInfoEntity.payType;
			var creditLine = dataJson.customerInfoEntity.creditLine;
			var arrears = dataJson.customerInfoEntity.arrears;
			var preDeposit = dataJson.customerInfoEntity.preDeposit;
			$("#addressId").val(dataJson.customerInfoEntity.address);
			$("#shortNameId").val(dataJson.customerInfoEntity.shortName);
			$("#responsibleId").val(dataJson.responsibleName);
			$("#siteUrlId").val(dataJson.customerInfoEntity.siteUrl);
			$("#faxId").val(dataJson.customerInfoEntity.fax);
			
			/*票价折扣*/
			$("#discountHidden").val(dataJson.customerInfoEntity.discountFare);
			/*手续费*/
			$("#feeHidden").val(dataJson.customerInfoEntity.fees);
			/*汇率*/
			$("#ratesHidden").val(dataJson.customerInfoEntity.exchangeRates);
			
			if(payType == 1){
				$("#payTypeId").html("月结");
			}else if(payType == 2){
				$("#payTypeId").html("周结");
			}else if(payType == 3){
				$("#payTypeId").html("单结");
			}else if(payType == 4){
				$("#payTypeId").html(dataJson.customerInfoEntity.paytypeName);
			}
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
/* 取消选中时 */
$("#linkNameId").on('select2:unselect', function (evt) {
	clearText();
}); 
$("#phoneNumId").on('select2:unselect', function (evt) {
	clearText();
});


/* 清除按钮 */
$("#clearBtn").click(function(){
	clearText();
});
/*客户信息 清除回显内容*/
function clearText(){
	//客户姓名清空
	$("#linkNameId").val(null).trigger("change");
	//电话清空
	$("#phoneNumId").val(null).trigger("change");
	//出发城市清空
	$("#city").val(null).trigger("change");
	//文本框清空
	$("#addressId").val("");
	$("#shortNameId").val("");
	$("#responsibleId").val("");
	$("#siteUrlId").val("");
	$("#faxId").val("");
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

