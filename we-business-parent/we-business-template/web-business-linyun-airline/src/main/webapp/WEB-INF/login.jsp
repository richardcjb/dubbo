<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>航空票务系统</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.css">
  <link rel="stylesheet" href="${base }/public/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="${base }/public/dist/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="${base}/public/plugins/iCheck/square/blue.css">
  
  <link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
</head>

<body class="hold-transition login-page bodySll">
<div class="login-box">
  <div class="login-logo">
    <h3 class="loginTitle">航空票务系统</h3>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">

    <form class="required-validate" id="loginForm" action="${base}/admin/login.html" method="post" onsubmit="return validateCallback(this)">
      <div class="LoginLabel">
        <label>用户名</label>
      </div>
      <div class="form-group marginBott5 has-feedback cf">
        <input id="loginName" name="loginName" value="${obj.loginName}" type="text" class="form-control loginpaddingrig" autofocus placeholder="用户名/手机号" 
        data-bv-notempty
        data-bv-notempty-message="请输入用户名/手机号"/>
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="LoginLabel">
        <label>密码</label>
      </div>
      <div class="form-group marginBott5 has-feedback cf">
        <input id="password" name="password" value="${obj.password}" type="password" class="form-control loginpaddingrig" placeholder="密码"
        data-bv-notempty
        data-bv-notempty-message="请输入密码"/>
        <span class="fa fa-unlock-alt form-control-feedback"></span>
      </div>
      <div class="LoginLabel">
        <label>验证码</label>
      </div>
      <div class="form-group marginBott5 cf">
        <input id="validateCode" name="validateCode" value="${obj.validateCode}" type="text" class="form-control authText" placeholder=""
        data-bv-notempty
        data-bv-notempty-message="请输入验证码"/>
        <div class="authImg">
        	<img title="看不清，点击换一张" onclick="changeValidateCode()" id="confirmCode" src="${base}/validateImage.html"/>
        </div>
      </div>
      <div class="row LoginBtn">
        <div class="col-xs-12">
          <button type="submit" class="btn btn-primary btn-block">登录</button>
        </div>
        <!-- /.col -->
      </div>
      
      	<!-- 
		登录消息提示区
		<p style="color: red; margin-left: 10px;"></p>
		 -->
		<c:if test="${obj != null}">
			<label class="red toolText">${obj.errMsg}！</label>
		</c:if>
      
    </form>
    
    <!-- /.social-auth-links -->
  </div>
  <!-- /.login-box-body -->
  <p class="login-p">版权 © 2016 聚优国际旅行社（北京）有限公司</p>
</div>
<!-- /.login-box -->

<!-- /.login-box -->
<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="${base}/public/plugins/iCheck/icheck.min.js"></script>
<script src="${base}/public/dist/js/bootstrapValidator.js"></script>

<script>
  $(function () {
	    $('input').iCheck({
	      checkboxClass: 'icheckbox_square-blue',
	      radioClass: 'iradio_square-blue',
	      increaseArea: '20%' // optional
	    });
	    
	 	// validate form
	    $("form.required-validate").each(function() {
	        var $form = $(this);
	        $form.bootstrapValidator();
	    });
	 	
	    $("input").each(function() {
	        var $input = $(this);
	        $input.blur(function(e) {
	           $("label.toolText").addClass("none");
	        });
	    });
  });
  
  function validateCallback(form, callback, confirmMsg) {
	    var $form = $(form);

	    var data = $form.data('bootstrapValidator');
	    if (data) {
	    	// 修复记忆的组件不验证
	        data.validate();

	        if (!data.isValid()) {
	            return false;
	        }
	    }
	    return true;
  }
  
  function changeValidateCode(){
      var timenow = new Date().getTime(); 
      var _obj = $("#confirmCode");
      _obj.attr("src","${base}/validateImage.html?d="+timenow);
  }
 
</script>
</body>
</html>
