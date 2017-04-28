//只能输入数字
$(document).on("input",".mustNumber",function(){
	$(this).val($(this).val().replace(/[^\d]/g,''));
});
//只能输入数字（带小数点）
$(document).on("input",".mustNumberPoint",function(){
	$(this).val($(this).val().replace(/[^.\d]/g,''));
});
/*//只能输入时间 （例如12:00）
$(document).on("input",".mustTimes",function(){
	$(this).val($(this).val().replace(/[^:\d]/g,''));
	if($(this).val().length == 2){
		$(this).val($(this).val()+':');
	}else if($(this).val().length > 5){	
		$(this).val($(this).val().substr(0,5));
	}
});
//只能输入时间 （例如12:00）
$(document).on("input",".mustArriveTimes",function(){
	$(this).val($(this).val().replace(/[^-+:\d]/g,''));
	if($(this).val().length == 2){
		$(this).val($(this).val()+':');
	}else if($(this).val().length > 7){	
		$(this).val($(this).val().substr(0,7));
	}
});*/

//只能输入时间 （例如1200）
$(document).on("input",".mustTimes",function(){
	$(this).val($(this).val().replace(/[^\d]/g,''));
	if($(this).val().length == 2){
		$(this).val($(this).val());
	}else if($(this).val().length > 4){	
		$(this).val($(this).val().substr(0,4));
	}
});
//只能输入时间 （例如1800）
$(document).on("input",".mustArriveTimes",function(){
	$(this).val($(this).val().replace(/[^-+\d]/g,''));
	if($(this).val().length == 2){
		$(this).val($(this).val());
	}else if($(this).val().length > 6){	
		$(this).val($(this).val().substr(0,6));
	}
});

//PNR最多输入13位
$(document).on("input",".PNRlength",function(){
	if($(this).val().length > 13){
		$(this).val($(this).val().substr(0,13));
	}
});

//加载日志
function loadOrderLog(orderid){
	$.ajax({ 
		type: 'POST', 
		data: {orderid:orderid}, 
		url: BASE_PATH + '/admin/inland/loadOrderLog.html',
        success: function (data) { 
        	var loghtml = '<ul>';
        	$.each(data.logs, function(name, value) {
        		loghtml += '<li style="list-style:none;">'+value.optime+'　'+value.content+'</li>';
      		});
        	loghtml += '</ul>';
        	$('#orderlog').html(loghtml);
        },
        error: function (xhr) {
        	layer.msg("保存失败","",3000);
        } 
    });
}
//datatables自动高度
function autoHighLoad(obj){
	obj.find('tr').each(function () {//全部 table 自适应高度      
       $(this).children('td').each(function(){
          var liLength = $(this).children('ul').find("li").length;
          if(liLength==1){
            $(this).children('ul').find("li").addClass('eq');
          }else if(liLength==2){
            $(this).children('ul').find("li").eq(1).addClass('eq1');
            $(this).children('ul').find("li").eq(0).addClass('eq0');
          }else if(liLength==2){
            $(this).children('ul').find("li").eq(2).addClass('eq2');
          }
       });
 	});
}