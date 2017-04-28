<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%> 
<%@include file="/WEB-INF/common/tld.jsp"%>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>登录账号-编辑</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/public/dist/css/dict.css">
	<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
	<!-- js -->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
	<style>
		.iconCla .select2-container{width: 95.5% !important;}
		.form-control-feedback {top: 0px;left: 210px;}
	</style>
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
        <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">返回</button>
        <input type="button" id="submitBtn" class="btn btn-primary right btn-sm" onclick="saveUpdateSubmit();" value="保存">
        <h4>编辑</h4>
    </div>
          <form id="updateLoginForm" method="post">
	          <div class="modal-body">
	            <div class="tab-content backcard">
	                <div class="row">
	                      <div class="form-group">
	                      <%-- 字典类别id --%>
	                      <input name="id" type="hidden" value="${obj.loginNumData.id}"/>
	                          <label class="col-sm-3 text-right padding">网站地址：</label>
	                            <div class="col-sm-3 padding">
	                              <input id="webURlId" name="webURl" type="text" class="form-control input-sm inputWidth" value="${obj.loginNumData.webURl }" />
	                              <span class="prompt">*</span>
	                            </div>
	                      </div>
	                      <div class="form-group form-group1">
	                         <label class="col-sm-2 text-right padding">登录账号：</label>
	                         <div class="col-sm-3 padding">
	                           <input id="loginNumNameId" name="loginNumName" type="text" class="form-control input-sm inputWidth" value="${obj.loginNumData.loginNumName }" />
	                           <span class="prompt">*</span>
	                         </div>
	                      </div>
	                </div>
	
	                <div class="row">
	                        <!-- <div class="form-group">
	                          <label class="col-sm-3 text-right padding">航空公司：</label>
	                          <div class="col-sm-3 padding">
	                            <input id="airlineNameId" name="airlineName" type="text" class="form-control input-sm inputWidth" placeholder="航空公司" />
	                          </div>
	                        </div> -->
	                        <div class="form-group">
			                      <label class="col-sm-3 text-right padding">航空公司：</label>
			                      <div class="col-sm-3 padding iconCla">
			                         <select id="airlineNameId" name="airlineName1" onchange="setSelectedAirlineIds();"
										class="form-control select2 inputWidth" multiple="multiple"
										data-placeholder="请输入航空公司">
										<option value="${obj.airlineInfo[0].id }" selected="selected">${obj.airlineInfo[0].text }</option>
										<%-- <c:forEach var="one" items="${obj.airlineInfo }">
											<option value="${one.text }" selected="selected">${one.text}</option>
										</c:forEach> --%>
									 </select>
									 <span class="prompt">*</span>
									 <!-- 区域ID -->
									 <input id="airlineIds" name="airlineName" type="hidden"/>
			                      </div>
			                      <!-- 设置已选中的项 -->
								<script type="text/javascript">
									function setSelectedAirlineIds() {
										var _airlineIds = $("#airlineNameId").select2("val");
										//var _airlineNames = $("#airlineNameId").text();
										$("#airlineIds").val(_airlineIds);
									}
								</script>
		                    </div>
	                        <div class="form-group form-group1">
	                          <label class="col-sm-2 text-right padding">状态：</label>
	                          <div class="col-sm-3 padding">
		                            <select id="status" name="status" class="form-control input-sm inputWidth">
		                                <c:forEach var="map" items="${obj.dataStatusEnum}" >
											<c:choose>
											   <c:when test="${map.key == obj.loginNumData.status}">
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
	                </div>
	
	                <div class="row">
	                    <div class="form-group">
	                        <label class="col-sm-3 text-right padding">备注：</label>
	                        <div class="col-sm-8 padding">
	                             <textarea id="remarkId" name="remark" class="form-control inpImpWid textareaHei">${obj.loginNumData.remark}</textarea>
	                        </div>
	                    </div>
	                </div>
	            </div>
	          </div>
	       </form>
	</div>
<!--JS 文件-->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
<!-- Select2 -->
<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
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
	        	webURl: {
	                validators: {
	                    notEmpty: {
	                        message: '网站地址不能为空!'
	                    },
	                    remote: { 
	                        url: '${base}/admin/companydict/comdictinfo/checkWebURlExist.html',//验证地址
	                             message: '网站地址已存在，请重新输入!',//提示消息
	                             delay :  2000,
	                             type: 'POST',//请求方式
	                             data: function(validator) {
	                                return {
	                                	webURl:$('input[name="webURl"]').val(),
	                                	id:'${obj.loginNumData.id}'
	                             };
	                        }
	                    }
	                }
	            },
	            loginNumName: {
	            	validators: {
	                    notEmpty: {
	                        message: '登录账号不能为空!'
	                    }
	                }
	            },
	            airlineName1: {
	            	validators: {
	                    notEmpty: {
	                        message: '航空公司不能为空!'
	                    }
	                }
	            }
	        }
	};
	$("#updateLoginForm").bootstrapValidator(options);  
	$("#updateLoginForm").data('bootstrapValidator').validate();
	return $("#updateLoginForm").data('bootstrapValidator').isValid();
}
//航空公司select2
var _areaSelect = $("#airlineNameId").select2({
	ajax : {
		url : '${base}/admin/companydict/comdictinfo/airLine.html',
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				airline : params.term, // search term
				selectedAreaIds:$("#airlineIds").val(),
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;

			return {
				results : data
			};
		},
	},
	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false, //设置必须存在的选项 才能选中
});
_areaSelect.val([${obj.areaIds}]).trigger("change");
//编辑
validateParams();
function saveUpdateSubmit(){
	var valid = validateParams();
	if(valid){
		$.ajax({
           type: "POST",
           url:'${base}/admin/companydict/comdictinfo/updateLoginNum.html',
           data:$('#updateLoginForm').serialize(),// 你的formid
           error: function(request) {
              layer.msg('编辑失败!',{time:2000});
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
}
//点击返回
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
</script>
</body>
</html>