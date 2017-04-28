//只能输入数字
$(document).on("input",".mustNumber",function(){
	$(this).val($(this).val().replace(/[^\d]/g,''));
});
//只能输入数字（带小数点）
$(document).on("input",".mustNumberPoint",function(){
	$(this).val($(this).val().replace(/[^.\d]/g,''));
});

//只能输入时间 （例如12:00）
$(document).on("input",".mustTimes",function(){
	$(this).val($(this).val().replace(/[^\d]/g,''));
	/*if($(this).val().length == 2){
		$(this).val($(this).val()+':');
	}else if($(this).val().length > 5){	*/
		$(this).val($(this).val().substr(0,4));
	//}
});
//只能输入时间 （例如12:00）
$(document).on("input",".mustArriveTimes",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36))
		   return;
	var pos=$(this).getCurPos();//保存原始光标位置
	var temp = $(this).val();
	$(this).val($(this).val().replace(/[^-+\d]/g,''));
	if($(this).val().length > 6){
		var afterstr = $(this).val().substr(pos-(temp.length-$(this).val().length),$(this).val().length);
		var beforestr = $(this).val().substr(0,pos-1);
		$(this).val(beforestr + afterstr);
	}
	pos=pos-(temp.length-$(this).val().length);//当前光标位置
	setCaretPosition($(this)[0],pos);//设置光标
	/*if($(this).val().length == 2){
		$(this).val($(this).val()+':');
	}else if($(this).val().length > 7){	*/
	//}
});
//获取光标位置
(function($){
	$.fn.extend({
		// 获取当前光标位置的方法
		getCurPos:function() {
			var getCurPos = '';
			if ( navigator.userAgent.indexOf("MSIE") > -1 ) {  // IE
				// 创建一个textRange,并让textRange范围包含元素里所有内容
				var all_range = document.body.createTextRange();all_range.moveToElementText($(this).get(0));$(this).focus();
				// 获取当前的textRange,如果当前的textRange是一个具体位置而不是范围,则此时textRange的范围是start到end.此时start等于end
				var cur_range = document.selection.createRange();
				// 将当前textRange的start,移动到之前创建的textRange的start处,这时,当前textRange范围就变成了从整个内容的start处,到当前范围end处
				cur_range.setEndPoint("StartToStart",all_range);
				// 此时当前textRange的Start到End的长度,就是光标的位置
				curCurPos = cur_range.text.length;
			} else {
				// 获取当前元素光标位置
				curCurPos = $(this).get(0).selectionStart;
			}
			// 返回光标位置
			return curCurPos;
		}
	});
})(jQuery);
/*
定位光标
*/
function setCaretPosition(ctrl, pos){
    if(ctrl.setSelectionRange)
    {
        ctrl.focus();
        ctrl.setSelectionRange(pos,pos);
    }
    else if (ctrl.createTextRange) {
        var range = ctrl.createTextRange();
        range.collapse(true);
        range.moveEnd('character', pos);
        range.moveStart('character', pos);
        range.select();
    }
}
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
          }else if(liLength==3){
            $(this).children('ul').find("li").eq(2).addClass('eq2');
          }else if(liLength==4){
        	$(this).children('ul').find("li").addClass('eq3');
        	$(this).children('ul').find("li").eq(2).addClass('eq4');
        	$(this).children('ul').find("li").eq(3).addClass('eq2');
          }else if(liLength>=5){
        	$(this).children('ul').find("li").css("line-height","13px");
        	$(this).children('ul').find("li").css("height","13px");
          }
       });
 	});
}
