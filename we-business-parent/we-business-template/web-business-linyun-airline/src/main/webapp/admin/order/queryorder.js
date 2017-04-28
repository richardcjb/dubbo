function initCitySelect2(obj){
	//加载起飞城市下拉
	obj.find('[name=leavecity]').select2({
		ajax : {
			url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				var backscity = obj.find('[name=arrivecity]').val();
				if(backscity){
					backscity = backscity.join(',');
				}
				return {
					exname : backscity,
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
	
	//加载起飞城市下拉
	obj.find('[name=arrivecity]').select2({
		ajax : {
			url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				var backscity = obj.find('[name=leavecity]').val();
				if(backscity){
					backscity = backscity.join(',');
				}
				return {
					exname : backscity,
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
}
//初始化航班信息select2
function initAirInfoSelect2(obj){
	obj.find('[name=aircom]').select2({
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
	//加载返程航班下拉
	obj.find('[name=ailinenum]').select2({
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
}
$(function(){
	//var firstDemandDiv = $('.DemandDiv').first();
	$('.DemandDiv').each(function(i){
		initCitySelect2($(this));
		$(this).find('[name=airlineinfo]').each(function(i){
			initAirInfoSelect2($(this));
		});
	});
    $('.UnderIcon').on('click',function(){//客户信息 显示/隐藏
        $('.hideTable').toggle();
      });
    //客户需求的 + 按钮
    $(document).on("click",".addIcon",function(){
        var divTest = $(this).parent().parent().parent().find('[name=airlineinfo]').last(); 
        var newDiv = divTest.clone(false,true);
        divTest.after(newDiv);
        newDiv.find('[name=airlineid]').val('');
        newDiv.find('[name=aircom]').next().remove();
        newDiv.find('[name=aircom]').empty();
        newDiv.find('[name=ailinenum]').next().remove();
        newDiv.find('[name=ailinenum]').empty();
        newDiv.find('[name=leavetime]').val('');
        newDiv.find('[name=arrivetime]').val('');
        newDiv.find('[name=formprice]').val('');
        newDiv.find('[name=price]').val('');
		initAirInfoSelect2(newDiv);
        var No = parseInt(divTest.find("p").html())+1;//用p标签显示序号
        newDiv.find("p").html(No); 
        newDiv.find('.tdBtn').remove();
        newDiv.find('.removeIconTd').remove();
        newDiv.append('<td class="removeIconTd"><i class="glyphicon glyphicon-minus removIcon"></i></td>');
    });
    //客户需求的 - 按钮
    $(document).on("click",".removIcon",function(){
        $(this).parent().parent().remove();
    });

    //客户需求的 +需求 按钮
    $('.addDemand').click(function(){
        var divTest = $(this).parent(); 
        var newDiv = divTest.clone(false,true);
        newDiv.find('[name=customneedid]').val('');
        newDiv.find('[name=leavecity]').next().remove();
        newDiv.find('[name=leavecity]').empty();
        newDiv.find('[name=arrivecity]').next().remove();
        newDiv.find('[name=arrivecity]').empty();
        //清空出发日期
        newDiv.find('[name=leavedate]').val('');
        //清空人数
        newDiv.find('[name=peoplecount]').val('');
        //清空票务类型
        newDiv.find('[name=tickettype]').val('1');
        newDiv.find('[name=remark]').val('');
        initCitySelect2(newDiv);
        //divTest.after(newDiv);
        $('.DemandDiv').last().after(newDiv);
        var No = parseInt(divTest.find("p").html())+1;//用p标签显示序号
        newDiv.find("p").html(No); 
        newDiv.find('.addDemand').remove();
        newDiv.prepend('<a href="javascript:;" class="btn btn-primary btn-sm removeDemand"><b>-</b>&nbsp;&nbsp;&nbsp;需求</a>');
        newDiv.find('[name=airlineinfo]').each(function(i){
        	if(i > 0){
        		$(this).remove();
        	}else{
        		$(this).find('[name=airlineid]').val('');
        		$(this).find('[name=aircom]').next().remove();
        		$(this).find('[name=aircom]').empty();
        		$(this).find('[name=ailinenum]').next().remove();
        		$(this).find('[name=ailinenum]').empty();
        		$(this).find('[name=leavetime]').val('');
        		$(this).find('[name=arrivetime]').val('');
        		$(this).find('[name=formprice]').val('');
        		$(this).find('[name=price]').val('');
        		initAirInfoSelect2($(this));
        	}
        });
        $('.DemandDiv').each(function(i){
        	$(this).find('.titleNum').html(i+1);
        });
        /*只在最后一个需求上显示 备注项*/
        //$('.remarkTr').remove();
        //$('.DemandDiv:last-child .cloTable tbody').append('<tr class="remarkTr"><td></span><label>备注：</label></td><td colspan="11"><input type="text" id="remark" name="remark" class="form-control input-sm noteText" placeholder=" " value=" "></td></tr>');
    });
    //客户需求的 -需求 按钮
    $(document).on("click",".removeDemand",function(){
        $(this).parent().remove();
        $('.DemandDiv').each(function(i){
        	$(this).find('.titleNum').html(i+1);
        });
        /*判断最后一个需求是否有 备注项 如果没有 就添加备注项*/
        //var cl=$('.DemandDiv:last-child .cloTable tbody tr').hasClass('remarkTr');
        //if(cl==false){
        //	$('.DemandDiv:last-child .cloTable tbody').append('<tr class="remarkTr"><td></span><label>备注：</label></td><td colspan="11"><input type="text" id="remark" name="remark" class="form-control input-sm noteText" placeholder=" " value=" "></td></tr>');
        //}
    });
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
