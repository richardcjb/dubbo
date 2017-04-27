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
    //加载pnr表格
    loadPNRdata(1);
    //航班信息的 + 按钮
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
        newDiv.find('[name=ispriceempty]').val('1');
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
    $('.addXuQiu').click(function(){
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
        newDiv.find('[name=tickettype]').val('');
        newDiv.find('[name=realtimexrate]').val('');
        newDiv.find('[name=avgexrate]').val('');
        newDiv.find('[name=paycurrency]').val('');
        newDiv.find('[name=paymethod]').val('');
        newDiv.find('[name=remark]').val('');
        newDiv.find('[name=pnrinfodata]').html('');
        initCitySelect2(newDiv);
        //divTest.after(newDiv);
        $('.DemandDiv').last().after(newDiv);
        var No = parseInt(divTest.find("p").html())+1;//用p标签显示序号
        newDiv.find("p").html(No); 
        newDiv.find('.addDemand').remove();
        newDiv.find('[name=customneedid]').before('<a href="javascript:;" class="btn btn-primary btn-sm removeDemand"><b>-</b>&nbsp;&nbsp;&nbsp;需求</a>');
        var divId=document.getElementById('infofooter').getElementsByTagName('div');
        newDiv.find('.titleNum').text(divId.length);
        newDiv.find('.paymethod').trigger("change");
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
        		$(this).find('[name=ispriceempty]').val('1');
        		initAirInfoSelect2($(this));
        	}
        });
        /*只在最后一个需求上显示 备注项*/
        //$('.remarkTr').remove();
        //$('.customerInfo #infofooter:last-child .cloTable tbody .pnrTr').after('<tr class="remarkTr"><td></span><label>备注：</label></td><td colspan="11"><input type="text" id="remark" name="remark" disabled="disabled" class="form-control input-sm noteText" placeholder=" " value=" "></td></tr>');
        $('.DemandDiv').each(function(i){
        	$(this).find('.titleNum').html(i+1);
        });
    });
    //客户需求的 -需求 按钮
    $(document).on("click",".removeDemand",function(){
        $(this).parent().remove();
        $('.DemandDiv').each(function(i){
        	$(this).find('.titleNum').html(i+1);
        });
        /*判断最后一个需求是否有 备注项 如果没有 就添加备注项*/
        //var cl=$('.customerInfo #infofooter:last-child .cloTable tbody tr:last-child').hasClass('remarkTr');
        //if(cl==false){
        //	$('.customerInfo #infofooter:last-child .cloTable tbody').append('<tr class="remarkTr"><td></span><label>备注：</label></td><td colspan="11"><input type="text" id="remark" name="remark" disabled="disabled" class="form-control input-sm noteText" placeholder=" " value=" "></td></tr>');
        //}
    });
  });
//加载pnr信息数据
function loadPNRdata(status){
	$('.DemandDiv').each(function(i){
		var customDiv = $(this);
		var customneedid = customDiv.find('[name=customneedid]').val();
		$.ajax({ 
			type: 'POST', 
			data: {customneedid:customneedid}, 
			dataType:'json',
			url: BASE_PATH + '/admin/inland/loadPNRdata.html',
            success: function (data) { 
            	var result = '';
            	for(var i=0 ; i<data.length ; i++){
            		result += '<tr>';
            		if(data[i].pnr != undefined){
            			result +='<td>'+data[i].pnr+'</td>';
            		}else{
            			result +='<td></td>';
            		}
            		if(data[i].costprice != undefined){
            			result +='<td>'+data[i].costprice.toFixed(2)+'</td>';
            		}else{
            			result +='<td></td>';
            		}
            		if(data[i].costpricesum != undefined){
            			result +='<td>'+data[i].costpricesum.toFixed(2)+'</td>';
            		}else{
            			result +='<td></td>';
            		}
            		if(data[i].salesprice != undefined){
            			result +='<td>'+data[i].salesprice.toFixed(2)+'</td>';
            		}else{
            			result +='<td></td>';
            		}
            		if(data[i].salespricesum != undefined){
            			result +='<td>'+data[i].salespricesum.toFixed(2)+'</td>';
            		}else{
            			result +='<td></td>';
            		}
            		if(data[i].peoplecount != undefined){
            			result +='<td>'+data[i].peoplecount+'</td>';
            		}else{
            			result +='<td></td>';
            		}
            		if(data[i].loginid != undefined){
            			result +='<td>'+data[i].loginid+'</td>';
            		}else{
            			result +='<td></td>';
            		}
            		if(status){
            			result +='<td></td>';
            		}else{
            			result +='<td><a href="javascript:openDetailPage('+data[i].id+');" class="PNRdetails">详情</a></td>';
            		}
            		result += '</tr>';
            	}
            	customDiv.find('[name=pnrinfodata]').html(result);
            },
            error: function (xhr) {
          	
            } 
         });
	});
}

function openDetailPage(id){
	layer.open({
        type: 2,
        title:false,
        skin: false, //加上边框
        closeBtn:false,//默认 右上角关闭按钮 是否显示
        shadeClose:false,
        scrollbar: false,
        area: ['900px', '500px'],
        content: BASE_PATH + '/admin/inland/pnrDetailPage.html?pnrid='+id,
        end:function(){
	       	 //设置财务信息
        	setFinanceInfo();
        }
      });
}

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
//设置付款方式
$(document).on("change",".paymethod",function(){
	var paymethod = $(this).val();
	var parentDiv = $(this).parent().parent().parent().parent().parent();
	var customerneedid = parentDiv.find('[name=customneedid]').val();
	if(paymethod == 1){
		parentDiv.find('[name=threepaytd]').show();
		parentDiv.find('[name=threepaymethod]').show();
		parentDiv.find('[name=internationalcard]').hide();
		$.ajax({
			type: 'POST', 
			data: {customerneedid:customerneedid}, 
			dataType:'json',
			url: BASE_PATH + '/admin/inland/loadCustomeSelect.html',
            success: function (data) { 
            	var result = '';
            	for(var i=0 ; i<data.thirdPayMent.length ; i++){
            		if(data.threepaymethod == data.thirdPayMent[i].id){
            			result += '<option value="'+data.thirdPayMent[i].id+'" selected="selected">'+data.thirdPayMent[i].thirdCompanyName+'</option>';
            		}else{
            			result += '<option value="'+data.thirdPayMent[i].id+'">'+data.thirdPayMent[i].thirdCompanyName+'</option>';
            		}
            	}
            	parentDiv.find('[name=thirdcustomid]').html(result);
            },
            error: function (xhr) {
          	
            } 
         });
	}else if(paymethod){
		parentDiv.find('[name=threepaytd]').hide();
		parentDiv.find('[name=threepaymethod]').hide();
		parentDiv.find('[name=internationalcard]').show();
		$.ajax({
			type: 'POST', 
			data: {paymethod:paymethod},  
			dataType:'json',
			url: BASE_PATH + '/admin/inland/loadBalance.html',
            success: function (data) { 
            	parentDiv.find('[name=internationalcard]').html('　余额：'+data.balance);
            },
            error: function (xhr) {
          	
            } 
         });
	}else{
		parentDiv.find('[name=threepaytd]').hide();
		parentDiv.find('[name=threepaymethod]').hide();
		parentDiv.find('[name=internationalcard]').hide();
	}
    //$(this).parent().parent().remove();
});
//自动触发付款方式select事件
triggerSelect();
function triggerSelect(){
	$('.DemandDiv').each(function(i){
		$(this).find('.paymethod').trigger("change");
	});
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
