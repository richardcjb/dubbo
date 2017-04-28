<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<link rel="stylesheet" type="text/css" href="${base}/plugin/local/upload/upload.css" />
<link rel="stylesheet" type="text/css" href="${base}/plugin/webuploader/css/webuploader.css">
<div class="panel_box">
	<div class="panel_content nopadding">
	
		<div class="form_item">
			<label class="form_label">章节名称：</label>
			<div class="form_ctrl red" >
				${obj.chapter.name}
			</div>
		</div>
	
		<div id="uploader">
			<div class="queueList">
				<div id="dndArea" class="placeholder">
					<div id="filePicker"></div>
					<p>上传讲义</p>
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
		<hr/>
	
	<h5>已上传讲义</h5>
	<div id="handouts_list">
	<c:if test="${obj.list != null}">
		<c:forEach items="${obj.list}" var="one" varStatus="status">
			<p id="${one.id}">
				<img alt="" src="${one.handoutsUrl}${h5_course_detail}">
				<a href="#" class="btn btn_mini btn_del" onclick="deleteHandouts(${one.id});">删除</a>
				<hr/>
			</p>
		</c:forEach>
	</c:if>
	</div>
</div>

<script>
	window.PROJECT_CONTEXT = "${base}";
	var chapter_id = "${obj.chapter.id}" ;
	var image_format = "${h5_course_detail}" ;
	
	function deleteHandouts(_id){
		/*自定义confirm*/
		$dialog.confirm({
			title:'是否删除',
			ok:function(){
				$.ajax({ 
		            type: "POST",//提交类型  
		            dataType: "json",//返回结果格式  
		            url: "${base}/course/chapter/deleteHandouts.html",//请求地址  
		            async: true  ,
		          	//请求数据  
		            data:{
		            	id:_id
		            },
		            success: function (obj) {//请求成功后的函数  
		            	$dialog.alert(obj.message); 
		            	var status = obj.status;
		            	if( status == 200 ){
		            		$("p#"+_id).remove();
		            	}
		            },  
		            error: function (obj) {
		            	$dialog.alert(obj.message); 
		            }
		    	});  // end of ajaxSubmit
			},
			cancel:function(){}
		});
	}
	
</script>
<script type="text/javascript" src="${base}/plugin/webuploader/js/webuploader.js"></script>
<script type="text/javascript" src="${base}/plugin/local/upload/uploadHandouts.js"></script>