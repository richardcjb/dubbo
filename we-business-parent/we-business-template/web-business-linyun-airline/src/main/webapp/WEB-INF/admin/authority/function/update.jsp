<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%> 
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>编辑</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
	<link rel="stylesheet" href="${base }/public/dist/css/font-awesome.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/ionicons.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/skins/_all-skins.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css" />
	<!-- style -->
	<link rel="stylesheet" href="${base}/public/css/style.css">
</head>
<body onresize=hero();>
          <div class="modal-top">
          <form id="functionEditForm" method="post">
              <div class="modal-header boderButt">
                  <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">返回</button>
     			  <button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
                  <h4>编辑</h4>
              </div>
                <div class="modal-body">
                 <div class="tab-content">
                       <input name="id" type="hidden" value="${obj.function.id}"/>
                       <div class="row">
                         <div class="form-group">
                            <label class="col-sm-3 text-right padding">上级功能：</label>
                            <div class="col-sm-8 padding">
								<select name="parentId" class="form-control input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${obj.list}" var="pro" >
										<c:choose>
										   <c:when test="${pro.id == obj.function.parentId}">
										   		<option value="${pro.id}" selected="selected">${pro.name}</option>
										   </c:when>
										   <c:otherwise>
										   		<option value="${pro.id}">${pro.name}</option>
										   </c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
                            </div>
                           </div>
                        </div>
                        <div class="row">
                          <div class="form-group">
                        	<label class="col-sm-3 text-right padding">访问地止：</label>
                            <div class="col-sm-8 padding">
                            	<input name="url" type="text" class="form-control input-sm"  value="${obj.function.url}"/>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="form-group">
                        	<label class="col-sm-3 text-right padding">功能名称：</label>
                            <div class="col-sm-8 padding">
                              <input name="name" type="text" class="form-control input-sm" value="${obj.function.name}"/>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="form-group">
                        	<label class="col-sm-3 text-right padding">功能等级：</label>
                            <div class="col-sm-8 padding">
                            	<input name="level" type="text" class="form-control input-sm" value="${obj.function.level}"/>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="form-group">
                        	<label class="col-sm-3 text-right padding">序号：</label>
                            <div class="col-sm-8 padding">
                            	<input name="sort" type="text" class="form-control input-sm" value="${obj.function.sort}"/>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="form-group">
                            <label class="col-sm-3 text-right padding">备注：</label>
                            <div class="col-sm-8 padding">
                              <textarea name="remark" id="remark" class="form-control">${obj.function.remark}</textarea>
                            </div>
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
<script src="${base}/common/js/layer/layer.js"></script>
<script type="text/javascript">
//校验
$(document).ready(function(){
	$('#functionEditForm').bootstrapValidator({
		message: '验证不通过!',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	name: {
                validators: {
                    notEmpty: {
                        message: '功能名称不能为空!'
                    }
                }
            },
            level: {
            	validators: {
                    notEmpty: {
                        message: '功能等级不能为空!'
                    },
                    regexp: {
                        regexp: /^[0-9]*$/,
                        message: '功能等级只能为数字'
                    }
                }
            },
            sort: {
            	validators: {
                    notEmpty: {
                        message: '序号不能为空!'
                    },
                    regexp: {
                        regexp: /^[0-9]*$/,
                        message: '序号只能为数字'
                    }
                }
            }
        }
	});
});
//更新提交
$("#submit").click(function(){
	$('#functionEditForm').bootstrapValidator('validate');
	var bootstrapValidator = $("#functionEditForm").data('bootstrapValidator');
	if (bootstrapValidator.isValid()) {
		$.ajax({
	           cache: true,
	           type: "POST",
	           url:'${base}/admin/authority/function/update.html',
	           data:$('#functionEditForm').serialize(),// 你的formid
	           error: function(request) {
	              layer.msg('修改失败!',{time:2000});
	           },
	           success: function(data) {
					layer.load(1, {
						 shade: [0.1,'#fff'] //0.1透明度的白色背景
					});
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					parent.layer.close(index);
					window.parent.successCallback('2');
	          }
	      });
	}
});
//提交时开始验证
$('#submit').click(function() {
	$('#functionEditForm').bootstrapValidator('validate');
});
//点击返回
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
</script>
