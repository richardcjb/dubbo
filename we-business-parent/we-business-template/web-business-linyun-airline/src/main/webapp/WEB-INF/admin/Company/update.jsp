<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=7" />
	<meta http-equiv="Access-Control-Allow-Origin" content="*" />
	<meta name="alexaVerifyID" content="" />
    <title>编辑</title>
	<link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
	<link rel="stylesheet" href="${base }/public/dist/css/font-awesome.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/ionicons.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/skins/_all-skins.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
	<!-- style -->
    <link rel="stylesheet" href="${base }/public/css/style.css">
	<style type="text/css">
		.wu-example .statusBar .btns .uploadBtn {
		    background: #3c8dbc !important;
		    color: #fff;
		    border-color: transparent;
		    position: relative;
		    top: -122px;
		    height: 40px;
		    border-radius: 5px;
		}
		.form-control-feedback {position: absolute;top: -2px;right: -25px;}
		html, body {min-height: 0;min-width: 0;overflow-x: auto !important;}
	</style>
</head>
<body onresize=hero();>
          <div class="modal-top">
          		
             <form  id="companyUpdateForm"> 
              <div class="modal-header boderButt">
                  <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">返回</button>
                  <input type="button" id="submitButton" class="btn btn-primary right btn-sm" onclick="submitCompany();" value="保存"/>
                  <button type="button" class="btn right btn-sm" onclick="deleteCompany();">删除</button>
                  <h4>&nbsp;&nbsp;&nbsp;基本资料</h4>
              </div>
                <div class="modal-body">	
                 <div class="tab-content">
                 		<input name="id" type="hidden" value="${obj.company.id }">
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">公司名称：</label>
                            <div class="col-sm-7 padding">
                              <input name="comName" type="tel" class="form-control input-sm inpImpWid" placeholder="请输入公司名称" value="${obj.company.comName }"/><span class="prompt">*</span>
                            </div>
                        </div>
                        <div class="row">
                        	<div class="form-group">
	                            <label class="col-sm-3 text-right padding">用户名：</label>
	                            <div class="col-sm-3 padding">
	                              <input name="adminId" type="hidden" value="${obj.company.adminId }">
	                              <input name="telephone" type="tel" class="form-control input-sm inpImportant" placeholder="请输入用户名" value="${obj.telephone }"/><span class="prompt">*</span>
	                            </div>
                        	</div>
                          	<div class="form-group form-group1">
	                            <label class="col-sm-1 text-right padding">联系人：</label>
	                            <div class="col-sm-3 padding">
	                              <input name="connect" type="tel" class="form-control input-sm inpImportant" placeholder="请输入联系人姓名" value="${obj.company.connect }"/><span class="prompt">*</span>
	                            </div>
                          	</div>
                        </div>

                        <div class="row">
                        	<div class="form-group">
	                          <label class="col-sm-3 text-right padding">联系电话：</label>
	                            <div class="col-sm-3 padding">
	                              <input name="mobile" type="tel" class="form-control input-sm inpImportant" placeholder="请输入联系人手机号" value="${obj.company.mobile }"/><span class="prompt">*</span>
	                            </div>
                            </div>
                          	<div class="form-group form-group1">
	                            <label class="col-sm-1 text-right padding">联系邮箱：</label>
	                            <div class="col-sm-3 padding">
	                              <input name="email" type="tel" class="form-control input-sm inpImportant" placeholder="请输入联系邮箱" value="${obj.company.email }"/>
	                            </div>
	                        </div>
                        </div>

                        <div class="row">
                        	<div class="form-group">
	                          <label class="col-sm-3 text-right padding">座机电话：</label>
	                            <div class="col-sm-3 padding">
	                              <input name="phonenumber" type="tel" class="form-control input-sm inpImportant" placeholder="请输入公司座机号" value="${obj.company.phonenumber }"/>
	                            </div>
                        	</div>
                          	<div class="form-group form-group1">
	                            <label class="col-sm-1 text-right padding">公司类型：</label>
	                            <div class="col-sm-3 padding">
	                              <select class="form-control input-sm inpImportant" name="comType" id="comType">
	                                <option value="">==请选择==</option>
	                                <option value="1" <c:if test="${'1' eq obj.company.comType}">selected</c:if>>上游公司</option>
	                                <option value="2" <c:if test="${'2' eq obj.company.comType}">selected</c:if>>代理商</option>
	                              </select><span class="prompt">*</span>
	                            </div>
	                        </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">地址：</label>
                            <div class="col-sm-7 padding">
                              <input name="address" type="tel" class="form-control input-sm inpImpWid" placeholder="请输入详细地址" value="${obj.company.address }"/><span class="prompt">*</span>
                            </div>
                        </div>


						 <div class="form-group row">
                            <label class="col-sm-3 text-right padding">上传营业执照：</label>
                            <div class="col-sm-8 padding">
                              <input type="hidden" id="webupload_picture" name="license" value="${obj.company.license }"/>
								        <div class="wu-example" id="uploader_00" style="width:300px;height:200px;">
								        	<div id="imgUrlMessage" name="imgUrlMessage"></div> 
								        </div>
                            </div>
                        </div>



                 </div>
                </div>
                 </form>
            </div>
	<!-- jQuery 2.2.3 -->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<!--layer -->
	<script src="${base}/common/js/layer/layer.js"></script>
	<jsp:include page="/WEB-INF/common/webupload_resource.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function(){
		var urllist = [] ;
		var license = '${obj.company.license}';
		if(license){
			urllist.push(license);
		}
		if($("#uploader_00").length>0){
			inituploader("","00",urllist);
		}
		//$('#comType').attr("disabled",true);
		$('#companyUpdateForm').bootstrapValidator({
			 message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {	
	        	comName: {
	                validators: {
	                    notEmpty: {
	                        message: '公司名称不能为空'
	                    }
	                }
	            },
	            telephone: {
	            	validators: {
	                    notEmpty: {
	                        message: '用户名不能为空'
	                    },
	                    regexp: {
	                        regexp: /^[A-Za-z0-9]+$/,
	                        message: '用户名只能为字母或数字'
	                    }
	                }
	            },
	            connect: {
	            	validators: {
	                    notEmpty: {
	                        message: '联系人不能为空'
	                    }
	                }
	            },
	            mobile: {
	            	validators: {
	            		notEmpty: {
	                        message: '联系人手机号不能为空'
	                    },
	            		regexp: {
	                        regexp: /^[1][34578][0-9]{9}$/,
	                        message: '联系人手机号格式错误'
	                    }
	                }
	            },
	            email: {
	            	validators: {
	            		regexp: {
	                        regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
	                        message: '邮箱格式错误'
	                    }
	                }
	            },
	            phonenumber: {
	            	validators: {
	            		regexp: {
	                        regexp: /^((0\d{2,3}-\d{7,8})|(1[35784]\d{9}))$/,
	                        message: '座机格式错误'
	                    }
	                }
	            },
	            comType: {
	            	validators: {
	            		notEmpty: {
	                        message: '请选择公司类型'
	                    }
	                }
	            },
	            address: {
	            	validators: {
	            		notEmpty: {
	                        message: '公司地址不能为空'
	                    }
	                }
	            }
	        }
		});
	});
	
	// Validate the form manually
    $('#submitButton').click(function() {
        $('#companyUpdateForm').bootstrapValidator('validate');
    });
	//关闭当前弹层
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	
	function submitCompany(){
		$('#companyUpdateForm').bootstrapValidator('validate');
		var bootstrapValidator = $("#companyUpdateForm").data('bootstrapValidator');
		if(bootstrapValidator.isValid()){
			layer.load(1);
			$.ajax({ 
				type: 'POST', 
				data: $("#companyUpdateForm").serialize(), 
				url: '${base}/admin/Company/update.html',
	            success: function (data) { 
	            	layer.closeAll('loading');
	            	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	            	window.parent.successCallback('2');
	            	parent.layer.close(index);
	            	
	            },
	            error: function (xhr) {
	            	layer.msg("修改失败","",3000);
	            } 
	        });
		}
	}
	function deleteCompany(){
		layer.confirm('确认你的操作?', {icon: 3, title:'提示'}, function(){
			$.ajax({ 
				type: 'POST', 
				data: {id:'${obj.company.id}'}, 
				url: '${base}/admin/Company/updateDeleteStatus.html',
	            success: function (data) { 
	            	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	            	window.parent.successCallback('3');
	            	parent.layer.close(index);
	            },
	            error: function (xhr) {
	            	layer.msg("删除失败","",3000);
	            } 
	        });
		});
	}
</script>
	
</body>
</html>	
	
	
	
	
	
	
	
	
	
	
	
