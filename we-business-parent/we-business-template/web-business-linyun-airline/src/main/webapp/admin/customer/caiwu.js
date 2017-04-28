$("#sInvID").select2({
	ajax : {
		url : BASE_PATH + "/admin/customer/isInvioce.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				q : params.term, // search term
				ids:$('#sInvName').val(),
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;

			return {
				results : data
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