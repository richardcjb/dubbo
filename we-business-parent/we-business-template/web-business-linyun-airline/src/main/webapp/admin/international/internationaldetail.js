//获取form下所有值
  function getFormJson(form) {
	  var o = {};
	  var a = $(form).serializeArray();
	  $.each(a, function (){
		  if (o[this.name] != undefined) {
		  	if (!o[this.name].push) {
	  	  		o[this.name] = [o[this.name]];
	  		}
	  		o[this.name].push(this.value || '');
	  	  } else {
	  	  	o[this.name] = this.value || '';
	  	  }
	  });
	  return o;
  }
function getFinanceFormData(){
	//开票日期
	var billingdate = $('#billingdate').val();
	//销售
	var salesperson = $('#salesperson').val();
	//开票人
	var issuer = $('#issuer').val();
	//减免
	var relief = $('#relief').val();
	//进澳时间航公公司
	var enteraircom = $('#enteraircom').val();
	if (enteraircom) {
		enteraircom = enteraircom.join(',');
	}
	//出澳时间航空公司
	var outaircom = $('#outaircom').val();
	if (outaircom) {
		outaircom = outaircom.join(',');
	}
	var financeForm = getFormJson('#financeForm');
	financeForm.billingdate = billingdate;
	financeForm.salesperson = salesperson;
	financeForm.issuer = issuer;
	financeForm.relief = relief;
	financeForm.enteraircom = enteraircom;
	financeForm.outaircom = outaircom;
	return financeForm;
}

//保存订单详情
function saveInternationalDetail(){
	var orderinfo = {};
	var orderType = $('#orderType').val();
	orderinfo.orderType = orderType;
	var orderedid = $('#orderedid').val();
	orderinfo.orderedid = orderedid;
	var customerId = $('#customerId').val();
	orderinfo.customerId = customerId;
	var airlinecom = $('#airlinecom').val();
	if (airlinecom) {
		airlinecom = airlinecom.join(',');
		}
	orderinfo.airlinecom = airlinecom;
	var peoplecount = $('#peoplecount').val();
	orderinfo.peoplecount = peoplecount;
	var costsingleprice = $('#costsingleprice').val();
	orderinfo.costsingleprice = costsingleprice;
	$.ajax({ 
		type: 'POST', 
		data: {data:JSON.stringify(orderinfo),financeData:JSON.stringify(getFinanceFormData())}, 
		url: BASE_PATH + '/admin/international/saveInternationalDetail.html',
      success: function (data) { 
      	//alert("添加成功");
      	//location.reload();
      	layer.closeAll('loading');
      	window.location.reload();
      	//layer.msg("保存成功",{time: 2000, icon:1});
      },
      error: function (xhr) {
      	layer.msg("保存失败","",3000);
      } 
  });
}
//上传游客
$(document).on('change','.uploadVisitors', function(){
	var pnrid = $(this).parent().find('[name=pnrid]').val();
	var file = this.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		var dataUrl = e.target.result;
		var blob = dataURLtoBlob(dataUrl) ;
    	var formData = new FormData(); 
    	formData.append("excelfile", blob,file.name);
    	formData.append("pnrid",pnrid);
    	$.ajax({ 
            type: "POST",//提交类型  
            dataType: "json",//返回结果格式  
            url: BASE_PATH + '/admin/international/uploadVisitorInfo.html',//请求地址  
            async: true  ,
            processData: false, //当FormData在jquery中使用的时候需要设置此项
            contentType: false ,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
          	//请求数据  
            data:formData ,
            success: function (obj) {//请求成功后的函数  
            	layer.msg("上传成功","",3000);
            },  
            error: function (obj) {
            	layer.msg("上传失败","",3000);
            }
    	});  // end of ajaxSubmit
	};
	reader.readAsDataURL(file);
});
//把dataUrl类型的数据转为blob
function dataURLtoBlob(dataurl) { 
    var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], {type:mime});
}
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

loadCustominfo();
function loadCustominfo(){
	var creditLinenum = 0;
	if(creditLine){
		creditLinenum = parseFloat(creditLine);
	}
	var arrearsnum = 0;
	if(arrears){
		arrearsnum = parseFloat(arrears);
	}
	if(creditLinenum - arrearsnum < 10000){
		$('#customeidcolor').attr('color','red');
		$('#historyqiancolor').attr('color','red');
	}
}
//航空公司下拉
$('.aircomselect').select2({
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