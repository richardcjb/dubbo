$("#companyId").select2({
	ajax : {
		url : BASE_PATH + "/admin/customer/company.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				q : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; 
				obj.text = obj.text; 
				obj.comType = obj.comType; 
				return obj;
			});
			/*alert(JSON.stringify(selectdata));*/
			return {
				results : selectdata
			};
		},
		cache : false
	},
	val: agentId,
	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false, //设置必须存在的选项 才能选中
});


$("#city").select2({
		ajax : {
			url : BASE_PATH  + "/admin/customer/goCity.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				return {
					q : params.term, // search term
					ids:$('#outcity').val(),
					page : params.page
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;
				var selectdata = $.map(data, function (obj) {
					obj.id = obj.id; 
					obj.text = obj.text; 
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
		maximumSelectionLength : 5, //设置最多可以选择多少项
		tags : false, //设置必须存在的选项 才能选中
	});

