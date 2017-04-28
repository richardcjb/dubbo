<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>添加</title>
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet" href="${base}/public/dist/css/dict.css">
<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
<script src="${base}/common/js/layer/layer.js"></script>
<style type="text/css">
	.newfile{display: inline-block;width: 260px;}
	.newfile input {width: 96%;display: inline-block;}
	.contextFrom{width: 90%;margin:17px auto;}
</style>
</head>
<body onresize=hero(); onload="document.getElementById('folderId').focus()">
	<div class="modal-top">
		<form id="addForm" method="post">
			<div class="modal-header boderButt">
				<input type="button" class="btn btn-primary right btn-sm" onclick="closewindow();" value="返回" />
                <button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
				<h4>添加文件夹</h4>
			</div>
			<div class="modal-body" style="height:100px;overflow-y: auto;">
				<%-- <div class="tab-content">
					<div class="form-group row">
						<div class="col-sm-8 padding">
						<label class="col-sm-3 text-right padding">上级文件夹：</label>
							<input id="parentId" name="parentId" value="${obj.dirfolder.fileName }" class="form-control input-sm inpImpWid" style="width:200px;" disabled/>
						</div>
					</div>
				</div> --%>
				<div class="tab-content">
					<div class="form-group row contextFrom">
						<label class="col-sm-3 text-right padding">文件名：</label>
						<div class="newfile">
							<input id="folderId" name="fileName" class="form-control input-sm" placeholder="请输入文件夹名称" /><span class="prompt">*</span>
						</div>
							
					</div>
				</div>
			</div>
		</form>
	</div>
<script type="text/javascript">
//验证输入内容不能为空
$(document).ready(function(){
	$('#addForm').bootstrapValidator({
		message: '验证不通过!',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	fileName: {
                validators: {
                    notEmpty: {
                        message: '文件夹名称不能为空!'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                         url: '${base}/admin/dictionary/dirtype/checkTypeCodeExist.html',//验证地址
                         message: '该文件夹名称已存在，请重新输入!',//提示消息
                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                         type: 'POST',//请求方式
                         //自定义提交数据，默认值提交当前input value
                         data: function(validator) {
                            return {
                            	typeCode:$('input[name="fileName"]').val()
                            };
                         }
                     }
                }
            }
        }
	});
});
//添加成功提示
$("#submit").click(function() {
	$('#addForm').bootstrapValidator('validate');
	var bootstrapValidator = $("#addForm").data('bootstrapValidator');
	if(bootstrapValidator.isValid()){
		$.ajax({
			cache : false,
			type : "POST",
			url : '${base}/admin/drawback/grabfile/add.html',
			data : 
			{
				parentId:'${obj.dirfolder.id}',
				fileName:$('input[name="fileName"]').val()
			},
			error : function(request) {
				layer.msg('新建文件夹失败!',{time:2000});
			},
			success : function(data) {
				layer.load(1, {
					shade : [ 0.1, '#fff' ]
				//0.1透明度的白色背景
				});
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				parent.layer.close(index);
				window.parent.successCallback('1');
			}
		});
	}
});
//提交时开始验证
$('#submit').click(function() {
       $('#addForm').bootstrapValidator('validate');
   });
//点击返回
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
</script>
</body>
</html>