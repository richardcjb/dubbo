<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/public/dist/css/dict.css">
	<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
</head>
<body onload="document.getElementById('areaName').focus()">
	<div class="modal-top">
		<form id="addAreaForm" method="post">
		     <div class="modal-header">
		          <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">取消</button>
		          <button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
		          <h4>添加区域</h4>
		     </div>
		      <div class="modal-body">
		          <div class="form-group">
		             <div class="col-md-8 col-md-offset-2">
						<input id="areaName" onfocus="aaa();" name="areaName" type="text" placeholder="请输入区域名称" class="form-control input-sm">
		             </div>
		          </div>
		      </div>
		</form>
	</div>
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
<script type="text/javascript">
//验证
$(document).ready(function(){
	$('#addAreaForm').bootstrapValidator({
		message: '验证不通过!',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	areaName: {
                validators: {
                    notEmpty: {
                        message: '区域名称不能为空!'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                         url: '${base}/admin/area/checkAreaNameExist.html',//验证地址
                         message: '区域名称已存在，请重新输入!',//提示消息
                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                         type: 'POST',//请求方式
                         //自定义提交数据，默认值提交当前input value
                         data: function(validator) {
                            return {
                            	areaName:$('input#areaName').val()
                            };
                         }
                     }
                }
            }
        }
	});
});
</script>
<!-- Select2 Onchange事件 -->
<script type="text/javascript">
//添加区域
$("#submit").click(function(){
	$('#addAreaForm').bootstrapValidator('validate');
	var bootstrapValidator = $("#addAreaForm").data('bootstrapValidator');
	if(bootstrapValidator.isValid()){
		$.ajax({
           type: "POST",
           url:'${base}/admin/area/add.html',
           data:$('#addAreaForm').serialize(),
           error: function(request) {
              layer.msg('添加失败!');
           },
            success: function(data) {
			layer.load(1, {
				 shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
              	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				window.location.reload(); // 父页面刷新
				window.parent.successCallback('1');
				$("#areaName").focus();     
           }
       });
	}
});
//提交时开始验证
$('#submit').click(function() {
    $('#addAreaForm').bootstrapValidator('validate');
});
//点击取消
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
</script>
</body>
</html>	