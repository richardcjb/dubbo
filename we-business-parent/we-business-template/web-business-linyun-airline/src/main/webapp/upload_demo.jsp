<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<meta http-equiv="Access-Control-Allow-Origin" content="*" />
<meta name="alexaVerifyID" content="" />
<title>凌云航票系统</title>

<link rel="stylesheet" type="text/css" href="${base}/plugin/local/upload/upload.css" />
<link rel="stylesheet" type="text/css" href="${base}/plugin/webuploader/css/webuploader.css">

</head>
<body>

<div class="panel_box">
	<div id="uploader">
		<div class="queueList">
			<div id="dndArea" class="placeholder">
				<div id="filePicker"></div>
				<p>上传图片</p>
			</div>
		</div>
		<div class="statusBar" style="display: none;">
			<div class="progress">
				<span class="text">0%</span> <span class="percentage"></span>
			</div>
			<div class="info"></div>
			<div class="btns">
				<div id="filePicker2"></div>
				<div class="uploadBtn">开始上传</div>
			</div>
		</div>
	</div>
	
	<h5>已上传图片</h5>
	<div id="image_list">
	<c:if test="${obj.list != null}">
		<c:forEach items="${obj.list}" var="one" varStatus="status">
			<p id="${one.id}">
				<img alt="" src="${one.image}">
				<a href="#" class="btn btn_mini btn_del" onclick="deleteHandouts(${one.id});">删除</a>
				<hr/>
			</p>
		</c:forEach>
	</c:if>
	</div>
</div>

<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script type="text/javascript">
	window.PROJECT_CONTEXT = "${base}";
	//除了文件以外需要额外传输的数据
	var formData = new FormData() ;
	//接收数据的服务端地址
	var serverUrl = "${base}/multiupload/demo.html";
	
	/*
	        后台的写法
		@At
		@POST
		@AdaptBy(type = UploadAdaptor.class, args = { "ioc:imgUpload" })
		@Ok("json")
		public Object addHandouts(@Param("::form.") final ChapterQueryForm form,@Param("file") final TempFile[] fts) {
			return chapterService.addHandouts(form,fts); 
		}
	 */
	 
	/*删除图片*/ 
	function deleteImage(_id){ 
		
	}
</script>
<script type="text/javascript" src="${base}/plugin/webuploader/js/webuploader.js"></script>
<script type="text/javascript" src="${base}/plugin/local/upload/upload.js"></script>
</body>
</html>