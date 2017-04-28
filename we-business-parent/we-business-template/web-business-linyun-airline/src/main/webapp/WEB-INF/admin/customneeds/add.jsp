<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=7" />
	<meta http-equiv="Access-Control-Allow-Origin" content="*" />
	<meta name="alexaVerifyID" content="" />
    <title>添加</title>
	<link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
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
<body>
	<div class="modal-top">
		<form id="customNeedsAddForm"> 
	    <div class="modal-header boderButt">
	       <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">返回</button>
	       <input type="submit" id="submitButton" class="btn btn-primary right btn-sm" onclick="submitCustomNeeds()" value="保存"/>
	       <h4>添加客户需求</h4>
	     </div>
	     <div class="modal-body">
	       <div class="tab-content">
	       		<div class="row">
		           <div class="form-group"><!--航空公司/旅行社-->
		             <label class="col-sm-2 text-right padding customerEdit">航空公司：</label>
		             <div class="col-sm-2 padding">
		             	<select id="aircom" name="aircom" class="form-control select2" multiple="multiple" onchange="changeaircom();"></select>
		               <input name="airline" id="airline" type="hidden" placeholder="首航-CA" />
		             </div>
		           </div>
		           <div class="form-group form-group1">
		             <label class="col-sm-2 text-right padding">旅行社：</label>
		             <div class="col-sm-2 padding">
		             	<select id="travelname" name="travelname" onchange="changetravelname();" class="form-control select2" multiple="multiple"></select>
		               <input name="travel" id="travel" type="hidden" class="form-control input-sm" placeholder=" " />
		             </div>
		           </div><!--end 航空公司/旅行社-->
				</div>
				<div class="row">
		           <div class="form-group"><!--人数/天数/联运要求-->
		             <label class="col-sm-2 text-right padding customerEdit">人数：</label>
		             <div class="col-sm-2 padding">
		               <input name="totalcount" type="text" class="form-control input-sm" placeholder="" />
		             </div>
		            </div>
			        <div class="form-group form-group1">
		             <label class="col-sm-2 text-right padding">天数：</label>
		             <div class="col-sm-2 padding">
		               <input name="totalday" type="text" class="form-control input-sm" placeholder=" " />
		             </div>
		             </div>
			         <div class="form-group form-group1">
		             <label class="col-sm-2 text-right padding">联运要求：</label>
		             <div class="col-sm-2 padding">
		             	<select id="unioncity" name="unioncity" onchange="changeunioncity();" class="form-control select2" multiple="multiple"></select>
		                <input name="uniontransport" id="uniontransport" type="hidden" class="form-control input-sm" placeholder=" " />
		             </div>
		           </div><!--end 人数/天数/联运要求-->
				</div>
				<div class="row">
		           <div class="form-group"><!--去程日期/出发城市/出发航班-->
		             <label class="col-sm-2 text-right padding customerEdit">去程日期：</label>
		             <div class="col-sm-2 padding">
		               <input id="leavedateString" name="leavedateString" onFocus="WdatePicker({minDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'backdateString\')}'})" type="text" class="form-control input-sm inputdatestr" placeholder="2016-12-01" />
		             </div>
		            </div>
				    <div class="form-group form-group1">
		             <label class="col-sm-2 text-right padding">出发城市：</label>
		             <div class="col-sm-2 padding">
		             <select id="leavescity" name="leavescity" onchange="changeleavescity();" class="form-control select2" multiple="multiple"></select>
		               <input name="leavecity" id="leavecity" type="hidden" class="form-control input-sm" placeholder="" />
		             </div>
		            </div>
				    <div class="form-group form-group1">
		             <label class="col-sm-2 text-right padding">出发航班：</label>
		             <div class="col-sm-2 padding">
		             	<select id="leaveairline" name="leaveairline" onchange="changeleaveairline();" class="form-control select2" multiple="multiple"></select>
		               <input name="leaveflight" id="leaveflight" type="hidden" class="form-control input-sm" placeholder=" " />
		             </div>
		           </div><!--end 去程日期/出发城市/出发航班-->
				</div>
				<div class="row">
		           <div class="form-group"><!--回程日期/返回城市/回程航班-->
		             <label class="col-sm-2 text-right padding customerEdit">回程日期：</label>
		             <div class="col-sm-2 padding">
		               <input id="backdateString" name="backdateString" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'leavedateString\')}'})" class="form-control input-sm inputdatestr" placeholder="2016-12-01" />
		             </div>
		            </div>
					<div class="form-group form-group1">
		             <label class="col-sm-2 text-right padding">返回城市：</label>
		             <div class="col-sm-2 padding">
		             	<select id="backscity" name="backscity" onchange="changebackscity();" class="form-control select2" multiple="multiple"></select>
		               <input name="backcity" id="backcity" type="hidden" class="form-control input-sm" placeholder="" />
		             </div>
		            </div>
					<div class="form-group form-group1">
		             <label class="col-sm-2 text-right padding">回程航班：</label>
		             <div class="col-sm-2 padding">
		             	<select id="backairline" name="backairline" onchange="changebackairline();" class="form-control select2" multiple="multiple"></select>
		               <input name="backflight" id="backflight" type="hidden" class="form-control input-sm" placeholder=" " />
		             </div>
		           </div><!--end 回程日期/返回城市/回程航班-->
	           </div>
	           <context class="remarkContext">
				   <div class="remarkDiv">
						<table class="remarkTable">
							<tr name="cRemarkTr" class="remarkTr">
								<td><label>备注：</label></td>
								<td>
									<textarea class="form-control" id="remark" name="remark"></textarea>
								</td>
							</tr>
						</table>
				   </div>	
				</context>
	       </div>
	     </div>
	     </form>
	</div>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<!-- jQuery 2.2.3 -->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<!--layer -->
	<script src="${base}/common/js/layer/layer.js"></script>
	<!-- Select2 -->
	<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
	<!--pikaday -->
	<script src="${base}/public/dist/js/pikaday.js"></script>
	<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/admin/airline/loadaddselect2.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		//页面校验
		formValidator();
	});
	// Validate the form manually
    $('#submitButton').click(function() {
        $('#customNeedsAddForm').bootstrapValidator('validate');
    });
	//关闭弹框
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	//提交表单数据
	function submitCustomNeeds(){
		$('#customNeedsAddForm').bootstrapValidator('validate');
		var bootstrapValidator = $("#customNeedsAddForm").data('bootstrapValidator');
		if(bootstrapValidator.isValid()){
			$.ajax({ 
				type: 'POST', 
				data: $("#customNeedsAddForm").serialize(), 
				url: '${base}/admin/customneeds/add.html',
	            success: function (data) { 
	            	//alert("添加成功");
	            	layer.alert("添加成功",{time: 2000, icon:1});
	            	$("#customNeedsAddForm")[0].reset();
	                initAddSelect2();
	            	$("#customNeedsAddForm").data('bootstrapValidator').destroy();
	                $('#customNeedsAddForm').data('bootstrapValidator', null);
	                formValidator();
	            	//location.reload();
	            },
	            error: function (xhr) {
	            	layer.alert("添加失败","",3000);
	            } 
	        });
		}
	}
	//页面校验方法
	function formValidator(){
		$('#customNeedsAddForm').bootstrapValidator({
			 message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	aircom: {
	                validators: {
	                    notEmpty: {
	                        message: '航空公司不能为空'
	                    }
	                }
	            },
	            travelname: {
	            	validators: {
	                    notEmpty: {
	                        message: '旅行社不能为空'
	                    }
	                }
	            },
	            totalcount: {
	            	validators: {
	            		notEmpty: {
	                        message: '人数不能为空'
	                    },
	            		regexp: {
	                        regexp: /^[0-9]+$/,
	                        message: '人数只能为数字'
	                    }
	                }
	            },
	            totalday: {
	            	validators: {
	            		notEmpty: {
	                        message: '天数不能为空'
	                    },
	            		regexp: {
	                        regexp: /^[0-9]+$/,
	                        message: '天数只能为数字'
	                    }
	                }
	            },
	            unioncity:{
	                validators: {
	                    notEmpty: {
	                        message: '联运要求不能为空'
	                    }
	                }
	            },
	            /* leavedateString:{
	                validators: {
	                    notEmpty: {
	                        message: '去程日期不能为空'
	                    }
	                }
	            }, */
	            leavescity:{
	                validators: {
	                    notEmpty: {
	                        message: '出发城市不能为空'
	                    }
	                }
	            },
	            leaveairline:{
	                validators: {
	                    notEmpty: {
	                        message: '出发航班不能为空'
	                    }
	                }
	            },
	            /* backdateString:{
	                validators: {
	                    notEmpty: {
	                        message: '回程日期不能为空'
	                    }
	                }
	            }, */
	            backscity:{
	                validators: {
	                    notEmpty: {
	                        message: '返回城市不能为空'
	                    }
	                }
	            },
	            backairline:{
	                validators: {
	                    notEmpty: {
	                        message: '回程航班不能为空'
	                    }
	                }
	            }
	        }
		});
	}
	  //输入格式必须为yyyy-MM-dd
	  $(".inputdatestr").keyup(function(){
		  var values = $(this).val();
		  if(values.length <= 4){
			  values = values.replace(/\D/g,'');
			  if(values.length == 4){
				  values += '-';
			  }
		  }else if(values.length <= 7){
			  var temp1 = values.substr(0,5);
			  var temp2 = values.substr(5,values.length);
			  temp2 = temp2.replace(/\D/g,'');
			  values = temp1 + temp2;
			  if(values.length == 7){
				  values += '-';
			  }
		  }else if(values.length > 7){
			  var temp3 = values.substr(0,8);
			  var temp4 = values.substr(8,values.length);
			  temp4 = temp4.replace(/\D/g,'');
			  values = temp3 + temp4;
			  if(values.length >10){
				  values = values.substr(0,10);
			  }
		  }
		  $(this).val(values);
	  });
	  $(".inputdatestr").bind("propertychange", function() { 
	  	alert($(this).val()); 
	  }); 
</script>
	
</body>
</html>	
