initAddSelect2();
function initAddSelect2(){
	$("#aircom").select2({
		ajax : {
			url : BASE_PATH + "/admin/customneeds/getAirComSelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				return {
					aircom : params.term, // search term
					page : params.page
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;
				var selectdata = $.map(data, function (obj) {
					obj.id =  obj.dictCode; // replace pk with your identifier
					obj.text =  obj.dictName; // replace pk with your identifier
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
	$("#travelname").select2({
		ajax : {
			url : BASE_PATH + "/admin/customneeds/getTravelNameSelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				return {
					travelname : params.term, // search term
					page : params.page
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;
				var selectdata = $.map(data, function (obj) {
					obj.id =  obj.shortName; // replace pk with your identifier
					obj.text =  obj.shortName; // replace pk with your identifier
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
//加载出发航班号下拉
	$("#leaveairline").select2({
		ajax : {
			url : BASE_PATH + "/admin/customneeds/getAirLineSelect.html",
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
//加载返程航班下拉
	$("#backairline").select2({
		ajax : {
			url : BASE_PATH + "/admin/customneeds/getAirLineSelect.html",
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
//加载起飞城市下拉
	$("#leavescity").select2({
		ajax : {
			url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				return {
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
//加载降落城市下拉
	$("#backscity").select2({
		ajax : {
			url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				return {
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
//加载联运城市下拉
	$("#unioncity").select2({
		ajax : {
			url : BASE_PATH + "/admin/customneeds/getUnionCitySelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				return {
					cityname : params.term, // search term
					page : params.page
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;
				var selectdata = $.map(data, function (obj) {
					obj.id = obj.dictCode; // replace pk with your identifier
				    var text = obj.dictCode;
				    if(obj.englishName){
					    text = obj.dictCode+'-'+obj.englishName+'-'+obj.countryName;
				    }
				    obj.text = text; // replace pk with your identifier
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
		tags : false, //设置必须存在的选项 才能选中
	});
}
//选择公司之后触发
function changeaircom(){
	var aircom = $("#aircom").select2("val");
	$("#airline").val(aircom);
}
//选择旅行社之后触发
function changetravelname(){
	var travelname = $("#travelname").select2("val");
	$("#travel").val(travelname);
}
//选择联运要求之后触发
function changeunioncity(){
	var unioncity = $("#unioncity").select2("val");
	$("#uniontransport").val(unioncity);
}
//选择出发城市之后触发
function changeleavescity(){
	var leavescity = $("#leavescity").select2("val");
	$("#leavecity").val(leavescity);
}
//选择出发航班之后触发
function changeleaveairline(){
	var leaveairline = $("#leaveairline").select2("val");
	$("#leaveflight").val(leaveairline);
}
//选择返回城市之后触发
function changebackscity(){
	var backscity = $("#backscity").select2("val");
	$("#backcity").val(backscity);
}
//选择返回城市之后触发
function changebackairline(){
	var backairline = $("#backairline").select2("val");
	$("#backflight").val(backairline);
}
