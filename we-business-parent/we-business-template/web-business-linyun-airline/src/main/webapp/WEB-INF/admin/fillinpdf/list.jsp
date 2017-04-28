<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>德国中英签证表填写</title>
<link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.min.css">
<link href="${base }/public/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<h1 style="margin-left: 490px;">德国中英签证表填写</h1>
	<br/>
	<br/>
	<br/>
		<form  class="form-horizontal" id="fillPdf" style="margin-left: 360px;" action="${base}/admin/fillinpdf/add.html"
		method="post" enctype="multipart/form-data">
		
		  <div class="form-group">
		    <label class="col-sm-2 control-label">姓</label>
		    <div class="col-sm-10">
		   	  <input type="text" class="form-control" placeholder="请输入姓" name="xing" style="width: 230px;">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label class="col-sm-2 control-label">出生时姓</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" placeholder="请输入出生时姓" name="birthxing" style="width: 230px;">
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">名</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" placeholder="请输入名" name="birthname" style="width: 230px;">
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">出生年月日</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" placeholder="请输入出生年月日" name="birthtime" style="width: 230px;">
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">出生地</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" placeholder="请输入出生地" name="birtharea" style="width: 230px;">
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">出生国</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" placeholder="请输入出生国" name="birthcountry" style="width: 230px;">
		    </div>
		  </div>
		   <div class="form-group">
		    <label class="col-sm-2 control-label">上传头像</label>
		    <div class="col-sm-10">
		    	<!-- <button id="file" name="file" type="file" class="btn btn-primary btn-sm" >上传</button> -->
		   		<input type="file" name="fileName">
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">现国籍</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" placeholder="请输入现国籍" name="nowcountry" style="width: 230px;">
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">合法监护人的姓名和住址</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" placeholder="请输入监护人信息" name="vormundnameandaddress" style="width: 230px;">
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">身份证号</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" placeholder="请输入身份证号" name="identitycard" style="width: 230px;">
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">旅行证件编号</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" placeholder="请输入旅行证号" name="travelnum" style="width: 230px;">
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">旅行证签发日期</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" placeholder="请输入旅行证签发日期" name="issuetime" style="width: 230px;">
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">旅行证有效期</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" placeholder="请输入旅行证有效期" name="effectivetime" style="width: 230px;">
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">签发机关</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" placeholder="请输入签发机关" name="issueoffice" style="width: 230px;">
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">申请人住址以及电子邮件</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" placeholder="请输入申请人信息" name="applicantaddressandemail" style="width: 230px;">
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">电话号码</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" placeholder="请输入电话号码" name="phonenumber" style="width: 230px;">
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">居留证号码</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" placeholder="请输入居留证号码" name="permitnumber" style="width: 230px;">
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">居留证有效日期</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" placeholder="请输入居留证有效日期" name="effectivetimeofcountry" style="width: 230px;">
		    </div>
		  </div>
		<!--
		  <div class="form-group">
		    <div class="col-sm-10 col-sm-offset-2">
		    	<div class="checkbox">
		        	<label>
		            	<input type="checkbox">记住密码
		            </label>
		        </div>
		    </div>
		  </div> -->
		  
		  <div class="form-group">
		  	<div class="col-sm-10 col-sm-offset-2">
		    	<input type="submit" value="提交" class="btn btn-info" >
		    	&nbsp;&nbsp;&nbsp;&nbsp;
		    	<a href="${url1 }" id="see" target="_blank">在线查看pdf</a>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<a href="${base}/admin/fillinpdf/download.html?downloadurl=${url1}" id="down">生成pdf</a>
				  <input type="hidden" name="phoneurl" id="phoneurl" >
		    </div>
		  </div>
		  <!-- <div class="form-group">
		  	<div class="col-sm-10 col-sm-offset-2">
		    	<input type="submit" value="生成pdf" class="btn btn-info">
		    	<a href="" id="see">在线查看pdf</a>
		    </div>
		  </div>
		  <div class="form-group">
		  	<div class="col-sm-10 col-sm-offset-2">
		    	<input type="submit" value="生成pdf" class="btn btn-info">
		    	<a href="" id="down">生成pdf</a>
		    </div>
		  </div> -->
		  
		</form>
		<%-- <form action="${base}/admin/fillinpdf/uploadFile1.html" enctype="multipart/form-data" method="post">
			<input type="file" name="fileName">
			<input type="submit" name="" value="上传">
		</form> --%>
<script src="${base }/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base }/public/plugins/uploadify/jquery.uploadify.min.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
<script type="text/javascript">
	//执行添加的操作、

	/* function addFileInfo(){
				$.ajax({
					cache : false,
					type : "POST",
					dataType : 'json',
					url : '${base}/admin/fillinpdf/add.html',
					data : $('#fillPdf').serialize(),// 你的formid
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(JSON.stringify(XMLHttpRequest));
						alert(textStatus);
						alert(errorThrown);
						
					},
					success : function(data) {
						alert(data.url);
						$("#see").attr("href",data.url);
						$("#down").attr("href","${base}/admin/fillinpdf/download.html?downloadurl="+data.url);
					  
					    
					    
					}
				});
			 }  */
	  $(document).ready(function (){
			
			uploadFile();
			
			
		}); 
	
		//文件上传
		 function uploadFile(){
			 var index=null;
			$.fileupload1 = $('#file').uploadify({
				'auto' : true,//选择文件后自动上传
				'formData' : {
					'fcharset' : 'uft-8',
					'action' : 'uploadimage'
				},
				'buttonText' : '上传',//按钮显示的文字
				'fileSizeLimit' : '3000MB',
				'fileTypeDesc' : '文件',//在浏览窗口底部的文件类型下拉菜单中显示的文本
				'fileTypeExts' : '*.png; *.jpg; *.gif;*.jpeg;',//上传文件的类型
				'swf' : '${base}/public/plugins/uploadify/uploadify.swf',//指定swf文件
				'multi' : false,//multi设置为true将允许多文件上传
				'successTimeout' : 1800,
				/* 'queueSizeLimit' : 100, */
				'uploader' : '${base}/admin/fillinpdf/uploadFile.html;jsessionid=${pageContext.session.id}',
				//下面的例子演示如何获取到vid
				'onUploadStart':function(file){
					/* index = layer.load(1, {shade: [0.1,'#fff']});//0.1透明度的白色背景  */
					
				},
				 'onUploadSuccess' : function(file, data, response) {
					 alert("11111");
					/* var jsonobj = eval('(' + data + ')');
					var url  = jsonobj;//地址
					var fileName = file.name;//文件名称
					$("#phoneurl").val(url);
					alert(url); */
					/* $("#url").val(url);
					$("#fileName").val(fileName);
					$("#uploading").val(3);
					if(index!=null){
						
					layer.close(index);
					} */
					/* 解决办法，上传成功后，将文件名字和路径添加到form表单的隐藏域中，点击保存的时候将其一起提交到后台进行保存，
					保存的时候判断文件名字是否存在从而判断需不需要再次进行预览格式的转换*/
					//var id = $("input#currentDirId").val();//文件pid
					/*  $.ajax({
						cache : false,
						type : "POST",
						data : {
							url : url,
							fileName : fileName
						
						},
						dataType : 'json',
						url : '${base}/admin/airlinepolicy/saveUploadFile.html',
						error : function(request) {
							layer.msg('上传失败!');
						},
						success : function(data) {
						     window.parent.successCallback('6');
							var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						    parent.layer.close(index); 
						}
					});  */
					
					var innerHtml = "";
                /*  if (response) {
                     innerHtml = "<div class='SCbtn'><a id='downloadA' href='${base}/admin/airlinepolicy/download.html?url="+url+"&fileName="+fileName+"'>"
                             + file.name
                             + "</a>&nbsp;&nbsp;<span>上传成功</span>&nbsp;&nbsp;&nbsp;&nbsp;"
                             + "<input type='button' class='deleteBtn' onclick='deleteFile();' value='删除'>";
                 } else {
                     innerHtml = "<div>该附件上传失败，请重新上传</div>";
                 } */
                /*  $("#completeFileName").html("");
                 $("#completeFileName").html($("#completeFileName").html() + innerHtml); */
                 
		           // $("#completeFileName").html($("#completeFileName").html() + innerHtml);
				},
		        //加上此句会重写onSelectError方法【需要重写的事件】
		        'overrideEvents': ['onSelectError', 'onDialogClose'],
		        //返回一个错误，选择文件的时候触发
		        'onSelectError':function(file, errorCode, errorMsg){
		        	
		            switch(errorCode) {
		                case -110:
		                    alert("文件 ["+file.name+"] 大小超出系统限制！");
		                    break;
		                case -120:
		                    alert("文件 ["+file.name+"] 大小异常！");
		                    break;
		                case -130:
		                    alert("文件 ["+file.name+"] 类型不正确！");
		                    break;
		            }
		        }
			});
		}
		
	



</script>






</body>
</html>
