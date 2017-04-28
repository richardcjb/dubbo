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
</head>
<body onresize=hero();>
          <div class="modal-top">
            <form id="updateComDictForm" method="post">
              <div class="modal-header boderButt">
                  <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">返回</button>
                  <input type="button" id="submitButton" class="btn btn-primary right btn-sm" onclick="submitInfo();" value="保存"/>
                  <h4>编辑</h4>
              </div>
                <div class="modal-body" style="height:360px;overflow-y: auto;">
                 		<div class="tab-content">
	                        <div class="form-group row">
	                        	<%-- 字典类别id --%>
	                        	<input name="id" type="hidden" type="text" value='${obj.single.id}'/>
	                            <label class="col-sm-3 text-right padding">第三方公司名称：</label>
	                            <div class="col-sm-8 padding">
	                              <input id="thirdCompanyNameId" name="thirdCompanyName" type="text" class="form-control input-sm inpImpWid" value="${obj.single.thirdCompanyName}" />
	                              <span class="prompt">*</span>
	                            </div>
	                        </div>
                        </div>
                        <div class="form-group row">
                        	<label class="col-sm-3 text-right padding">银行卡名称：</label>
                            <div class="col-sm-8 padding">
                              	<input name="bankCardName" id="bankCardNameId" type="text" class="form-control input-sm inpImpWid"  value="${obj.single.bankCardName}" />
                            	<span class="prompt">*</span>
                            </div>
                        </div>
                        <div class="form-group row">
                        	<label class="col-sm-3 text-right padding">卡号：</label>
                            <div class="col-sm-8 padding">
                              	<input name="bankCardNum" id="bankCardNumId" type="text" class="form-control input-sm inpImpWid" onkeyup="this.value=this.value.replace(/\s/g,'').replace(/....(?!$)/g,'$& ')"  maxlength="32"  value="${obj.single.bankCardNum}" />
                            	<span class="prompt">*</span>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">状态：</label>
                            <div class="col-sm-8 padding">
                              	<select id="status" name="status" class="form-control input-sm inpImpWid">
									<c:forEach var="map" items="${obj.dataStatusEnum}" >
										<c:choose>
										   <c:when test="${map.key == obj.single.status}">
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
                              <textarea name="remark" id="remark" type="text" class="form-control textareaHei">${obj.single.remark}</textarea>
                            </div>
                        </div>
                    </div>
               </form>
            </div>
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
//验证
function validateParams(){
	var options = {
		message: '验证不通过!',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	thirdCompanyName: {
                validators: {
                    notEmpty: {
                        message: '第三方公司名称不能为空!'
                    }
                }
            },
            bankCardName: {
                validators: {
                    notEmpty: {
                        message: '银行卡名称不能为空!'
                    }
                }
            },
            bankCardNum: {
            	validators: {
                    notEmpty: {
                        message: '银行卡账号不能为空!'
                    },
                    remote: {
	                    url: '${base}/admin/companydict/comdictinfo/checkBankCardNumExist.html',//验证地址
	                         message: '银行卡账号已存在，请重新输入!',//提示消息
	                         delay :  2000,
	                         type: 'POST',//请求方式
	                         //自定义提交数据，默认值提交当前input value
	                         data: function(validator) {
	                            return {
	                            	bankCardNum:$('input[name="bankCardNum"]').val(),
	                            	id:'${obj.single.id}'
	                            };
	                         }
	                   }
                }
            }
        }
	};
	$("#updateComDictForm").bootstrapValidator(options);  
	$("#updateComDictForm").data('bootstrapValidator').validate();
	return $("#updateComDictForm").data('bootstrapValidator').isValid();
}
validateParams();
//修改保存
function submitInfo(){
	var valid = validateParams();
	if(valid){
		$.ajax({
			type: 'POST', 
			data: $("#updateComDictForm").serialize(), 
			url: '${base}/admin/companydict/comdictinfo/updateThirdPayMent.html',
            success: function (data) { 
            	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
            	parent.layer.close(index);
            	window.parent.successCallback('2');
            },
            error: function (xhr) {
            	layer.msg("编辑失败",{time:2000});
            } 
        });
	}
}
//点击返回
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
</script>
