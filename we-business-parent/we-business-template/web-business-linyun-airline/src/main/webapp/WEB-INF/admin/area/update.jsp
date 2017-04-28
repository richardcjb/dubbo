<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑区域</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
	<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
</head>
<body>
	<div class="modal-top">
		<form id="editAreaForm" method="post">
		     <div class="modal-header">
		     	  <%-- <input id="id" name="id" type="hidden" value="${obj.id}"> --%>
		          <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">取消</button>
		          <button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
		          <button type="button" onclick="physicalDelete('${obj.id}');" class="btn right btn-sm" data-dismiss="modal">删除</button>
		          <h4>编辑区域</h4>
		     </div>
		      <div class="modal-body">
		          <div class="form-group">
		             <div class="col-md-8 col-md-offset-2">
		             	<input id="areaName" name="areaName" type="text" value="${obj.areaName}" class="form-control input-sm">
		             </div>
		          </div>
		      </div>
		</form>
	</div>
   <!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
<script type="text/javascript">
//验证
function validateParams(){
	var options = {
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
	                            	areaName:$('input#areaName').val(),
	                            	id:'${obj.id}'
	                            };
	                         }
	                     }
	                }
	            }
	        }
	};
	$("#editAreaForm").bootstrapValidator(options);  
	$("#editAreaForm").data('bootstrapValidator').validate();
	return $("#editAreaForm").data('bootstrapValidator').isValid();
}
validateParams();
//编辑保存
$("#submit").click(function(){
	var valid = validateParams();
	var bootstrapValidator = $("#editAreaForm").data('bootstrapValidator');
	var _areaName = $("input#areaName").val();
	if(valid){
		$.ajax({
           cache: true,
           type: "POST",
           url:'${base}/admin/area/update.html',
           data:{
				areaName:_areaName,
				id:'${obj.id}'
		   },
           error: function(request) {
              layer.msg('编辑失败!');
           },
            success: function(data) {
			layer.load(1, {
				 shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
              	layer.msg('编辑成功!',{time: 5000});
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				parent.layer.close(index);
				window.parent.successCallback('2');
           }
       });
	}
});
//提交时开始验证
$('#submit').click(function() {
    $('#editAreaForm').bootstrapValidator('validate');
});
//点击返回
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
//删除
function physicalDelete(id) {
	layer.confirm("您确认删除信息吗？", {
	    btn: ["是","否"], //按钮
	    shade: false //不显示遮罩
	}, function(){
		// 点击确定之后
		var url = '${base}/admin/area/delete.html';
		$.ajax({
			type : 'POST',
			data : {
				id : id
			},
			dataType : 'json',
			url : url,
			success : function(data) {
				if ("200" == data.status) {
					layer.msg("操作成功!", "", 3000);
					 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				     parent.layer.close(index);
				     window.parent.successCallback('3');
				} else {
					layer.msg("操作失败!", "", 3000);
				}
			},
			error : function(xhr) {
				layer.msg("操作失败", "", 3000);
			}
		});
	}, function(){
	    // 取消之后不用处理
	});
}
</script>
</body>
</html>	