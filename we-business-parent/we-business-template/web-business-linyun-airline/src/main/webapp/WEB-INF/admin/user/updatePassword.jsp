<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html>
 <head>
   <meta charset="UTF-8">
   <title>修改密码</title>
   <link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
   <link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
   <link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
   <link rel="stylesheet" href="${base}/public/dist/css/personalInfo.css"><!--本页的styleFlie-->	
 </head>
<body onload="document.getElementById('oldPass').focus()">
	<div class="modal-top">
	     <div class="modal-header">
	          <button type="button" class="btn btn-primary right btn-sm returnBtn" onclick="closewindow('${obj.personalInfo[0].id}');">取消</button>
	          <button id="updatePassDateId" onclick="updatePassData();"  class="btn btn-primary right btn-sm saveBtn">保存</button>
	          <h5>修改密码</h5>
	     </div>
		 <form id="passwordForm" method="post">
	      	<div class="modal-body pwdBody">
	          <div class="form-group pwd">
	          	<input id="userId" name="id" type="hidden" value="${obj.personalInfo[0].id}"/>
	       		<label>用户密码：</label>
	            <input id="oldPass" name="password" type="password" placeholder="请输入旧密码"  class="form-control input-sm">
	            <input id="newPass" name="newPass" type="password" placeholder="请输入新密码" class="form-control input-sm">
	            <input id="repeatPass" name="repeatPass" type="password" placeholder="请再输入新密码" class="form-control input-sm">
	          </div>
	        </div>
        </form>
	</div>
   <!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base}/common/js/layer/layer.js"></script>
<script type="text/javascript">
//修改密码
function updatePassData(){
    parent.layer.confirm('您确认修改密码吗？', {
	   btn: ['是','否'], //按钮
	   shade: false //不显示遮罩
	}, function(){
		$.ajax({
			type : "POST",
			url : '${base}/admin/user/updatePassword.html',
			data : $('#passwordForm').serialize(),
			success : function(data) {
				if ("200" == data.status) {
					window.location.href="${base}/admin/logout.html";
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			      	parent.layer.close(index);
			      	window.parent.successCallback('1');
				} else {
					parent.layer.msg("操作失败!"); 
				}
			},
			error : function(request) {
				parent.layer.msg('操作失败!');
			}
		});
	}, function(){
		 //取消之后不做任何操作
	});
}
//点击取消
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
</script>
</body>
</html>