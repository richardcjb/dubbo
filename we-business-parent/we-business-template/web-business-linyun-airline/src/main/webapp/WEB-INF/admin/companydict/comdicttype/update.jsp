<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>


<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>编辑</title>
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/public/dist/css/dict.css">
	<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
</head>
<body onresize=hero();>
	<div class="modal-top">
		<form id="updateForm" method="post">
			<div class="modal-header boderButt">
				<button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">返回</button>
                <input type="button" id="submitButton" class="btn btn-primary right btn-sm" onclick="submitType();" value="保存"/>
				<h4>编辑</h4>
			</div>
			<div class="modal-body" style="height:310px;overflow-y: auto;">
				<div class="tab-content">
					<div class="form-group row">
						<input name="id" type="hidden" value="${obj.dirtype.id}" /> <label
							class="col-sm-3 text-right padding">字典类型编码：</label>
						<div class="col-sm-8 padding">
							<input name="comTypeCode" id="comTypeCode" type="text"
								class="form-control input-sm" oninput="this.value=this.value.toUpperCase().replace(/(^\s*)|(\s*$)/g, '')"
								value="${obj.dirtype.comTypeCode}" />
						</div>
						<span class="prompt">*</span>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 text-right padding">字典类别名称：</label>
						<div class="col-sm-8 padding">
							<input name="comTypeName" id="comTypeName" type="text"
								class="form-control input-sm"
								value="${obj.dirtype.comTypeName}" />
						</div><span class="prompt">*</span>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 text-right padding">状态：</label>
						<div class="col-sm-8 padding">
							<select id="status" name="status" class="form-control input-sm">
								<c:forEach var="map" items="${obj.dataStatusEnum}">
									<c:choose>
										<c:when test="${map.key == obj.dirtype.status}">
											<option value="${map.key}" selected="selected">${map.value}</option>
										</c:when>
										<c:otherwise>
											<option value="${map.key}">${map.value}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 text-right padding">备注：</label>
						<div class="col-sm-8 padding">
							<textarea name="remark" id="remark"
								class="form-control textareaHei">${obj.dirtype.remark}</textarea>
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
	//验证输入内容不能为空
function validateParams(){
	var options = {
			message: '验证不通过!',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	comTypeCode: {
	                validators: {
	                    notEmpty: {
	                        message: '字典类别编码不能为空!'
	                    },
	                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
	                         url: '${base}/admin/companydict/comdicttype/checkTypeCodeExist.html',//验证地址
	                         message: '字典类别编码已存在，请重新输入!',//提示消息
	                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
	                         type: 'POST',//请求方式
	                         //自定义提交数据，默认值提交当前input value
	                         data: function(validator) {
	                            return {
	                            	comTypeCode:$('input[name="comTypeCode"]').val(),
	                            	id:'${obj.dirtype.id}'
	                            };
	                         }
	                     },
	                     regexp: {
	                        regexp: /^[A-Za-z0-9]+$/,
	                        message: '字典代码只能为字母或数字'
	                    }
	                }
	            },
	            comTypeName: {
	            	validators: {
	                    notEmpty: {
	                        message: '字典类别名称不能为空!'
	                    },
	                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
	                    url: '${base}/admin/companydict/comdicttype/checkTypeNameExist.html',//验证地址
	                         message: '字典类别名称重复，请重新输入!',//提示消息
	                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
	                         type: 'POST',//请求方式
	                         //自定义提交数据，默认值提交当前input value
	                         data: function(validator) {
	                            return {
	                            	comTypeName:$('input[name="comTypeName"]').val(),
	                            	id:'${obj.dirtype.id}'
	                            };
	                         }
	                     }
	                }
	            }
	      }
	};
	$("#updateForm").bootstrapValidator(options);  
	$("#updateForm").data('bootstrapValidator').validate();
	return $("#updateForm").data('bootstrapValidator').isValid();
}
validateParams();
	function submitType(){
		var valid = validateParams();
		if(valid){
			$.ajax({
				type : "POST",
				data : $('#updateForm').serialize(),// 你的formid 
				url : '${base}/admin/companydict/comdicttype/update.html',
				success : function(data) {
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	            	parent.layer.close(index);
	            	window.parent.successCallback('2');
				},
				error : function(request) {
					layer.msg('修改失败!',{time:2000});
				}
			});
		}
	}
	//点击返回关闭当前layer弹框
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
</script>