var _airlinename = $("#airlinename").select2({
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
_airlinename.val([airlinename ]).trigger("change");
var _travelname = $("#travelname").select2({
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
_travelname.val([travelname ]).trigger("change");
//加载联运城市下拉 
var _unioncity = $("#unioncity").select2({
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
_unioncity.val([unioncity ]).trigger("change");
initEditPageSelect();
function initEditPageSelect(){
	$('.hangduan').each(function(i){
		var editdiv = $(this);
		loadEditPageSelect(editdiv);
	});
}
function loadEditPageSelect(divObj){
	//加载出发航班号下拉
	var _leaveairline = divObj.find('[name=leaveairline]').select2({
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
	//_leaveairline.val([leaveairline ]).trigger("change");
	//加载起飞城市下拉
	var _leavescity = divObj.find('[name=leavescity]').select2({
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
	//_leavescity.val([leavescity ]).trigger("change");
	//加载降落城市下拉
	var _backscity = divObj.find('[name=backscity]').select2({
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
	//_backscity.val([backscity ]).trigger("change");
}
//为加号绑定事件
$('.addAirPlan').click(function(e) {
	var airdiv = $('.hangduan').first();
	var lastdiv = $('.hangduan').last();
	var newDiv = airdiv.clone(false,true);
	lastdiv.after(newDiv);
	//初始化出发城市
	var leavescity = newDiv.find('[name=leavescity]');
	leavescity.empty();
	leavescity.next().remove();
    //设置新的去程抵达城市下拉框ID
    var backscity = newDiv.find('[name=backscity]');
    backscity.empty();
    backscity.next().remove();
    //初始化航班
    var leaveairline = newDiv.find('[name=leaveairline]');
    leaveairline.empty();
    leaveairline.next().remove();
    //初始化出发日期
    newDiv.find('[name=setoffdate]').val('');
    //初始化时间
    newDiv.find('[name=setofftime]').val('');
    var addBtn = newDiv.find('[name=addButton]');
    addBtn.attr('name','closeButton');
    addBtn.addClass('glyphicon-minus removeAirPlan');
    addBtn.removeClass('glyphicon-plus addAirPlan');
    loadEditPageSelect(newDiv);
});
//绑定减号的事件
$(document).on('click', '.removeAirPlan', function(e) {
	$(this).parent().remove();
});
//航班时间输入框绑定事件
$(document).on('keyup','.inputtime',function(e) {
	  var values = $(this).val();
	  if(values.length <= 4){
		  values = values.replace(/\D/g,'');
		  if(values.length == 4){
			  values += '/';
		  }
	  }else if(values.length <= 9){
		  var temp1 = values.substr(0,5);
		  var temp2 = values.substr(5,values.length);
		  temp2 = temp2.replace(/\D/g,'');
		  values = temp1 + temp2;
	  }else{
		  values = values.substr(0,9);
	  }
	  $(this).val(values);
});