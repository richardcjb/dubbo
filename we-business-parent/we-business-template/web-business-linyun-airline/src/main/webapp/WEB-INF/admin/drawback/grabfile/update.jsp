<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%> 
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/public/dist/css/dict.css">
	<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
	<style type="text/css">
	.newfile{display: inline-block;width: 260px;}
	.newfile input {width: 96%;display: inline-block;}
	.contextFrom{width: 90%;margin:17px auto;}
	</style>
</head>
<body onresize=hero();>
          <div class="modal-top">
          <form id="updateForm">
              <div class="modal-header boderButt">
                  <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">返回</button>
                  <input type="button" id="submitButton" class="btn btn-primary right btn-sm" onclick="submitInfo();" value="保存"/>
                  <h4>修改文件夹名称</h4>
              </div>
                <div class="modal-body" style="height:100px;overflow-y: auto;">
                 <div class="tab-content">
                        <div class="form-group row">
                            <%-- <div class="tab-content">
									<div class="form-group row">
										<div class="col-sm-8 padding">
										<label class="col-sm-3 text-right padding">上级文件夹：</label>
											<input id="parentId" name="parentId" value="${obj.dirfolder.fileName }" class="form-control input-sm inpImpWid" style="width:200px;"/>
										</div>
									</div>
								</div> --%>
                        </div>
                        <div class="form-group row contextFrom">
                        		<label class="col-sm-3 text-right padding">文件名：</label>
                        		<div class="newfile">
                        			<input id="folderId" name="fileName" class="form-control input-sm" value="${obj.dirfolder.fileName}"/><span class="prompt">*</span>
                        		</div>
                        </div>
                    </div>
                </div>
          </form>
     </div>
</body>
</html>	
<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
<!--layer -->
<script src="${base}/common/js/layer/layer.js"></script>
<script type="text/javascript">
//修改保存
function submitInfo(){
	$.ajax({
		type: 'POST', 
		data : 
		{
			id:'${obj.dirfolder.id}',
			fileName:$('input[name="fileName"]').val()
		},
		url: '${base}/admin/drawback/grabfile/update.html',
           success: function (data) { 
           	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
           	parent.layer.close(index);
           	window.parent.successCallback('2');
           },
           error: function (xhr) {
           	layer.msg("编辑失败","",3000);
           } 
       });
}
//点击返回
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
</script>
