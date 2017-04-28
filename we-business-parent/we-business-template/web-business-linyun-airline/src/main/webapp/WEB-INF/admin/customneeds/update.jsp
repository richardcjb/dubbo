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
	<link rel="stylesheet" href="${base}/public/css/pikaday.css">
	<link rel="stylesheet" type="text/css" href="${base }/public/dist/css/airlinesModule.css">
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
		html, body {min-height: 0;min-width: 0;overflow-x: auto !important;}
	</style>
</head>
<body >
     <div class="modal-top">
		<form id="customNeedsUpdatedForm"> 
	    <div class="modal-header boderButt">
	       <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">返回</button>
	       <input type="submit" id="submitButton" class="btn btn-primary right btn-sm" onclick="submitCustomNeeds()" value="保存"/>
	       <c:choose>
		       <c:when test="${obj.isclose eq 0 }">
		      	 <button type="button" class="btn right btn-sm" onclick="closeCustomNeeds();">关闭</button>
	       	   </c:when>
	       	   <c:otherwise>
	       	   		<button type="button" class="btn right btn-sm" onclick="enableCustomNeeds();">启用</button>
	       	   </c:otherwise>
	       </c:choose>
	       <h4>编辑客户需求</h4>
	     </div>
	     <div class="modal-body">
	       <div class="tab-content">
	       		<div class="row">
		           <div class="form-group"><!--航空公司/旅行社-->
		           	 <input type="hidden" id="id" name="id" value="${obj.id }">
		             <label class="col-sm-2 text-right padding customerEdit">航空公司：</label>
		             <div class="col-sm-2 padding">
		               <input name="airline" type="text" class="form-control input-sm" placeholder="首航-CA" value="${obj.airline }"/>
		             </div>
		            </div>
					<div class="form-group form-group1">
		             <label class="col-sm-2 text-right padding">旅行社：</label>
		             <div class="col-sm-2 padding">
		               <input name="travel" type="text" class="form-control input-sm" placeholder=" " value="${obj.travel}"/>
		             </div>
		           </div><!--end 航空公司/旅行社-->
				</div>
				<div class="row">
		           <div class="form-group"><!--人数/天数/联运要求-->
		             <label class="col-sm-2 text-right padding customerEdit">人数：</label>
		             <div class="col-sm-2 padding">
		               <input name="totalcount" type="text" class="form-control input-sm" placeholder="" value="${obj.totalcount}"/>
		             </div>
		            </div>
					<div class="form-group form-group1">
		             <label class="col-sm-2 text-right padding">天数：</label>
		             <div class="col-sm-2 padding">
		               <input name="totalday" type="text" class="form-control input-sm" placeholder=" " value="${obj.totalday}"/>
		             </div>
		            </div>
					<div class="form-group form-group1">
		             <label class="col-sm-2 text-right padding">联运要求：</label>
		             <div class="col-sm-2 padding">
		               <input name="uniontransport" type="text" class="form-control input-sm" placeholder=" " value="${obj.uniontransport}"/>
		             </div>
		           </div><!--end 人数/天数/联运要求-->
				</div>
				<div class="row">
		           <div class="form-group"><!--去程日期/出发城市/出发航班-->
		             <label class="col-sm-2 text-right padding customerEdit">去程日期：</label>
		             <div class="col-sm-2 padding">
		               <input id="leavedateString" name="leavedateString"  type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'backdateString\')}'})" class="form-control input-sm" value="<fmt:formatDate value="${obj.leavedate}" pattern="yyyy-MM-dd" />"/>
		             </div>
		            </div>
					<div class="form-group form-group1">
		             <label class="col-sm-2 text-right padding">出发城市：</label>
		             <div class="col-sm-2 padding">
		               <input name="leavecity" type="text" class="form-control input-sm" placeholder="" value="${obj.leavecity}"/>
		             </div>
		            </div>
					<div class="form-group form-group1">
		             <label class="col-sm-2 text-right padding">出发航班：</label>
		             <div class="col-sm-2 padding">
		               <input name="leaveflight" type="text" class="form-control input-sm" placeholder=" " value="${obj.leaveflight}"/>
		             </div>
		           </div><!--end 去程日期/出发城市/出发航班-->
				</div>
				<div class="row">
		           <div class="form-group"><!--回程日期/返回城市/回程航班-->
		             <label class="col-sm-2 text-right padding customerEdit">回程日期：</label>
		             <div class="col-sm-2 padding">
		               <input id="backdateString" name="backdateString" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'leavedateString\')}'})" class="form-control input-sm" value="<fmt:formatDate value="${obj.backdate}" pattern="yyyy-MM-dd" />"/>
		             </div>
		            </div>
					<div class="form-group form-group1">
		             <label class="col-sm-2 text-right padding">返回城市：</label>
		             <div class="col-sm-2 padding">
		               <input name="backcity" type="text" class="form-control input-sm" placeholder="" value="${obj.backcity}"/>
		             </div>
		            </div>
					<div class="form-group form-group1">
		             <label class="col-sm-2 text-right padding">回程航班：</label>
		             <div class="col-sm-2 padding">
		               <input name="backflight" type="text" class="form-control input-sm" placeholder=" " value="${obj.backflight}"/>
		             </div>
		           </div><!--end 回程日期/返回城市/回程航班-->
	           </div>
	           <context class="remarkContext">
				   <div class="remarkDiv">
						<table class="remarkTable">
							<tr name="cRemarkTr" class="remarkTr">
								<td><label>备注：</label></td>
								<td>
									<textarea class="form-control" id="remark" name="remark" >${obj.remark}</textarea>
								</td>
							</tr>
						</table>
				   </div>	
				</context>
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
	<!--pikaday -->
	<script src="${base}/public/dist/js/pikaday.js"></script>
	<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		//页面校验
		formValidator();
		//选择日期控件
	});
	// Validate the form manually
    $('#submitButton').click(function() {
        $('#customNeedsUpdatedForm').bootstrapValidator('validate');
    });
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	//提交表单
	function submitCustomNeeds(){
		$('#customNeedsUpdatedForm').bootstrapValidator('validate');
		var bootstrapValidator = $("#customNeedsUpdatedForm").data('bootstrapValidator');
		if(bootstrapValidator.isValid()){
			$.ajax({ 
				type: 'POST', 
				data: $("#customNeedsUpdatedForm").serialize(), 
				url: '${base}/admin/customneeds/update.html',
	            success: function (data) { 
	            	layer.alert("修改成功",{time: 2000, icon:1});
	            	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	            	window.parent.successCallback('2');
	            	parent.layer.close(index);
	            },
	            error: function (xhr) {
	            	layer.alert("修改失败","",3000);
	            } 
	        });
		}
	}
	//关闭客户需求信息
	function closeCustomNeeds(){
		layer.confirm('确定要关闭该需求吗?', {icon: 3, title:'提示'}, function(){
			$.ajax({ 
				type: 'POST', 
				data: {id:'${obj.id}'}, 
				url: '${base}/admin/customneeds/closeCustomNeeds.html',
	            success: function (data) { 
	            	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	            	window.parent.successCallback('3');
	            	parent.layer.close(index);
	            },
	            error: function (xhr) {
	            	layer.alert("关闭失败","",3000);
	            } 
	        });
		});
	}
	//启用客户需求
	function enableCustomNeeds(){
		layer.confirm('确定要启用该需求吗?', {icon: 3, title:'提示'}, function(){
			$.ajax({ 
				type: 'POST', 
				data: {id:'${obj.id}'}, 
				url: '${base}/admin/customneeds/enableCustomNeeds.html',
	            success: function (data) { 
	            	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	            	window.parent.successCallback('4');
	            	parent.layer.close(index);
	            },
	            error: function (xhr) {
	            	layer.alert("启用失败","",3000);
	            } 
	        });
		});
	}
	//页面校验方法
	function formValidator(){
		$('#customNeedsUpdatedForm').bootstrapValidator({
			 message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	airline: {
	                validators: {
	                    notEmpty: {
	                        message: '航空公司不能为空'
	                    }
	                }
	            },
	            travel: {
	            	validators: {
	                    notEmpty: {
	                        message: '旅行社不能为空'
	                    }
	                }
	            },
	            totalcount: {
	            	validators: {
	            		regexp: {
	                        regexp: /^[0-9]+$/,
	                        message: '人数只能为数字'
	                    }
	                }
	            },
	            totalday: {
	            	validators: {
	            		regexp: {
	                        regexp: /^[0-9]+$/,
	                        message: '天数只能为数字'
	                    }
	                }
	            }
	        }
		});
	}
</script>
	
</body>
</html>	
	
	
	
	
	
	
	
	
	
	
	
