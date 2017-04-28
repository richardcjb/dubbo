<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>个人信息</title>
  <link rel="stylesheet" type="text/css" href="${base}/public/dist/css/personalInfo.css"><!--本页的styleFlie-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
  <!-- Content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row row-top">
          <div class="box">
            <div class="box-header">
                <div class="form-group row cf personalBtn">
                    <button type="button" onclick="editPersonal('${obj.personalInfo[0].id}');" class="btn btn-primary btn-sm right personalEtie">编辑</button>
                    <a type="button" onclick="openUpdatePassword();" class="btn btn-primary btn-sm right updatePwd" >修改密码</a>
                </div>
            </div>
            <div class="personalTable">
              <table id="PersonDatatable" class="table table-bordered table-hover">
                <tbody>
                  <tr class="strip1">
                    <td>用户名称</td>
                    <td>${obj.personalInfo[0].fullName}</td>
                  </tr>
                  <tr class="strip2">
                    <td>用户名/手机号码</td>
                    <td>${obj.personalInfo[0].telephone }</td>
                  </tr>
                  <tr class="strip1">
                    <td>座机号码</td>
                    <td>${obj.personalInfo[0].landline }</td>
                  </tr>
                  <tr class="strip2">
                    <td>联系QQ</td>
                    <td>${obj.personalInfo[0].qq }</td>
                  </tr>
                  <tr class="strip1">
                    <td>电子邮箱</td>
                    <td>${obj.personalInfo[0].email }</td>
                  </tr>
                  <tr class="strip2">
                    <td>所属部门</td>
                    <td>${obj.personalInfo[0].deptName }</td>
                  </tr>
                  <tr class="strip1">
                    <td>用户职位</td>
                    <td>${obj.personalInfo[0].jobName }</td>
                  </tr>
                   <tr class="strip2">
                    <td>负责区域</td>
                    <td>${obj.personalInfo[0].areaName }</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
      </div>
    </section>
  </div>
  <!-- end Content -->

  <!--Footer -->
  <%@include file="/WEB-INF/public/footer.jsp"%>
<!-- end Footer -->
<script type="text/javascript">
//打开修改密码弹框
function openUpdatePassword(){
  layer.open({
	    type: 2,
	    title: false,
	  	closeBtn:false,
	    fix: false,
	    maxmin: false,
	    shadeClose: false,
	    area: ['550px', '320px'],
	    content: '${base}/admin/user/updatePassword.html',
	    end: function(){
	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
	    }
	});
}
//打开编辑个人信息页面
function editPersonal(userId){
	layer.open({
  	    type: 2,
  	    title: false,
  	  	closeBtn:false,
  	    fix: false,
  	    maxmin: false,
  	    shadeClose: false,
  	    area: ['700px', '400px'],
  	    content: '${base}/admin/user/updatePersonal.html?id='+userId
  	  });
}
//事件提示
function successCallback(id){
	  window.location.reload();
	  if(id == '1'){
		  layer.msg("密码修改成功",{time:2000});
	  }else if(id == '2'){
		  layer.msg("修改成功",{time:5000});
	  }
  }
</script>
</body>
</html>
