<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>员工管理</title>
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet" href="${base}/public/dist/css/user.css">
<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
<style type="text/css">
	.modal-header {border-bottom:0;}
	.tab-content {margin-top: 15px;}
	.TCinput{width: 84%;}
	.TC{display: inline-block;}
</style>
</head>
<body onload="document.getElementById('fullName').focus()">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">返回</button>
    			<button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tabs_1" data-toggle="tab">基本资料</a></li>
				<li><a href="#tabs_2" data-toggle="tab">工资</a></li>
			</ul>
		</div>
		<form id="addUserForm" method="post">
			<div class="modal-body">
				<div class="tab-content">
					<div class="tab-pane active" id="tabs_1">
						<div class="tab-content">
		                  <div class="row">
		                  	<div class="form-group">
		                  		<label class="col-sm-3 text-right padding">用户姓名：</label>
		                      	<div class="col-sm-3 padding">
		                        	<input id="fullName" name="fullName" type="text" class="form-control input-sm inputWidth" placeholder="请输入用户姓名" />
		                        	<span class="prompt">*</span>
		                      	</div>
		                  	</div>
			               	<div class="form-group form-group1">
			                   <label class="col-sm-2 text-right padding">用户名/手机号码：</label>
			                   <div class="col-sm-3 padding">
			                     <input id="telephone" name="telephone" type="text" class="form-control input-sm inputWidth" placeholder="请输入用户名/手机号码" />
			                     <span class="prompt">*</span>
			                   </div>
		                    </div>
		                  </div>

		                  <div class="row">
		                  	<div class="form-group">
		                      <label class="col-sm-3 text-right padding">联系QQ：</label>
		                      <div class="col-sm-3 padding">
		                        <input id="qq" name="qq" type="text" class="form-control input-sm inputWidth" placeholder="请输入联系QQ" />
		                      </div>
		                    </div>
		                    <div class="form-group form-group1">
		                      <label class="col-sm-2 text-right padding">座机号码：</label>
		                      <div class="col-sm-3 padding">
		                        <input id="landline" name="landline" type="text" class="form-control input-sm inputWidth" placeholder="请输入座机号码" />
		                      </div>
		                     </div>
		                  </div>

		                  <div class="row">
		                  	<div class="form-group">
		                      <label class="col-sm-3 text-right padding">电子邮箱：</label>
		                      <div class="col-sm-3 padding">
		                        <input id="email" name="email" type="eamil" class="form-control input-sm inputWidth" placeholder="请输入邮箱" />
		                      </div>
		                    </div>
		                    <div class="form-group form-group1">
		                     <label class="col-sm-2 text-right padding">所属部门：</label>
		                      <div class="col-sm-3 padding">
		                        <select id="deptId" name="deptId" onchange="selectDeptName();" class="form-control input-sm inputWidth">
		                         	<option value=" ">--请选择--</option>
		                         	<c:forEach items="${obj.queryList}" var="one">
			                           	<option value="${one.id }">${one.deptName }</option>
									</c:forEach>
		                        </select>
		                        <span class="prompt">*</span>
		                      </div>
		                     </div>
		                  </div>

		                  <div class="row">
		                  	<div class="form-group">
		                      <label class="col-sm-3 text-right padding">用户职位：</label>
		                      <div class="col-sm-3 padding">
		                         <select id="jobId" name="jobId" class="form-control input-sm inputWidth">
		                 			<option value="">--请选择--</option>
		              			 </select>
		                        <span class="prompt">*</span>
		                      </div>
		                    </div>
							<div class="form-group form-group1">
		                      <label class="col-sm-2 text-right padding">用户是否禁用：</label>
		                      <div class="col-sm-3 padding">
		                        <select id="disableStatus" name="disableStatus" class="form-control input-sm inputWidth">
		                          <option value="0" selected="selected">否</option>
		                          <option value="1">是</option>
		                        </select>
		                      </div>
		                     </div>
		                  </div>
						
		                  	<div class="row">
			                  	<div class="form-group">
				                      <label class="col-sm-3 text-right padding">负责区域：</label>
				                      <div class="col-sm-8 padding iconCla">
				                         <select id="areaSelect" name="dictAreaName" onchange="setSelectedAreaIds();"
											class="form-control select2 inputWidth" multiple="multiple"
											data-placeholder="请输入区域名称">
										 </select>
										 <!-- 区域ID -->
										 <input id="selectedAreaIds" name="selectedAreaIds" type="hidden"/>
				                         <span class="prompt">*</span>
				                      </div>
			                     </div>
                  			</div>
                  			<!-- 设置已选中的项 -->
							<script type="text/javascript">
								function setSelectedAreaIds() {
									var _selectedAreaIds = $("#areaSelect").select2("val");
									$("#selectedAreaIds").val(_selectedAreaIds);
								}
							</script>
            			</div>
					</div>
					<div class="tab-pane" id="tabs_2">
						<div class="tab-content">
		                  <div class="row">
		                  	<div class="form-group">
		                  		<label class="col-sm-3 text-right padding">基本工资：</label>
		                      	<div class="col-sm-3 padding">
		                        	<input id="baseWagesId" name="baseWages" type="text" class="form-control input-sm inputWidth" placeholder="请输入基本工资"/>
		                        	<span class="prompt">*</span>
		                      	</div>
		                  	</div>
			               	<div class="form-group form-group1">
			                   <label class="col-sm-2 text-right padding">纳税：</label>
			                   <div class="col-sm-3 padding">
			                     <input id="ratepayingId" name="ratepaying" type="text" class="form-control input-sm inputWidth" placeholder="请输入纳税金额"/>
			                   </div>
		                    </div>
		                  </div>
		
		                  <div class="row">
		                  	<div class="form-group">
		                      <label class="col-sm-3 text-right padding">五险一金：</label>
		                      <div class="col-sm-3 padding">
		                        <input id="wuXianYiJinId" name="wuXianYiJin" type="text" class="form-control input-sm inputWidth" placeholder="请输入五险一金"/>
		                      </div>
		                    </div>
		                    <div class="form-group form-group1">
		                      <label class="col-sm-2 text-right padding">提成：</label>
		                      <div class="col-sm-3 padding">
		                        <input id="commissionId" name="commission" type="text" class="form-control input-sm inputWidth TCinput" placeholder="请输入提成"/>
		                        <p class="TC">%</p>
		                        <span class="prompt">*</span>
		                      </div>
		                     </div>
		                  </div>
		
		                  <div class="row">
		                  	<div class="form-group">
		                      <label class="col-sm-3 text-right padding">奖金：</label>
		                      <div class="col-sm-3 padding">
		                        <input id="bonusId" name="bonus" type="eamil" class="form-control input-sm inputWidth" placeholder="请输入奖金"/>
		                      </div>
		                    </div>
		                    <div class="form-group form-group1">
		                     <label class="col-sm-2 text-right padding">罚款：</label>
		                      <div class="col-sm-3 padding">
		                        <input id="forfeitId" name="forfeit" type="text" class="form-control input-sm inputWidth" placeholder="请输入罚款金额">
		                      </div>
		                     </div>
		                  </div>
						
		                  <div class="row">
		                  	<div class="form-group">
		                      <label class="col-sm-3 text-right padding">其他：</label>
		                      <div class="col-sm-8 padding">
		                         <input id="remarkId" name="remark" type="text" class="form-control input-sm inpImpWid" placeholder="备注">
		                      </div>
		                     </div>
		                  </div>
            			</div>
					</div>
				</div>
			</div>
		</form>
	</div>
<!--JS 文件-->
<script type="text/javascript">
	var BASE_PATH = '${base}';
</script>
<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
<!-- Select2 -->
<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
<!-- 页面select2下拉框js -->
<script src="${base}/admin/areaSelect2/area.js"></script>
<script src="${base}/common/js/layer/layer.js"></script>
<script type="text/javascript">
//验证输入内容不能为空
$(document).ready(function(){
	formValidator();
});
//表单验证
function formValidator(){
	$('#addUserForm').bootstrapValidator({
		message: '验证不通过!',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	fullName: {
                validators: {
                    notEmpty: {
                        message: '用户姓名不能为空!'
                    },
                    /* remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                         url: '${base}/admin/user/checkUserNameExist.html',//验证地址
                         message: '用户名称已存在，请重新输入!',//提示消息
                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                         type: 'POST',//请求方式
                         //自定义提交数据，默认值提交当前input value
                         data: function(validator) {
                            return {
                            	fullName:$('input[name="fullName"]').val()
                            };
                         }
                     }, */
                     stringLength: {/*长度提示*/
                   	    min: 1,
                   	    max: 6,
                   	    message: '用户名长度不得超出6个汉字!'
                   	  }
                }
            },
            telephone: {
            	validators: {
                    notEmpty: {
                        message: '电话号码不能为空!'
                    },
                    regexp: {
                	 	regexp: /^[1][34578][0-9]{9}$/,
                        message: '电话号码号格式错误!'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                    url: '${base}/admin/user/checkTelephoneExist.html',//验证地址
                         message: '此号码已被其他公司录入过，请重新输入',//提示消息
                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                         type: 'POST',//请求方式
                         //自定义提交数据，默认值提交当前input value
                         data: function(validator) {
                            return {
                            	telephone:$('input[name="telephone"]').val(),
                            	disableStatus:$('input[name="disableStatus"]').val()
                            };
                         }
                     }
                }
            },
            qq: {
            	validators: {
            		regexp: {
                        regexp: /^[0-9]*$/,
                        message: 'qq只能输入数字!'
                    }
                }
            },
            email: {
            	validators: {
            		regexp: {
                        regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
                        message: '邮箱格式错误!'
                    }
                }
            },
            deptId: {
            	validators: {
                    notEmpty: {
                        message: '部门不能为空!'
                    }
                }
            },
            jobId: {
            	validators: {
                    notEmpty: {
                        message: '职位不能为空!'
                    }
                }
            },
            dictAreaName: {
            	validators: {
                    notEmpty: {
                        message: '区域不能为空!'
                    }
                }
            },
            baseWages: {
            	validators: {
                    notEmpty: {
                        message: '基本工资不能为空!'
                    }
                }
            },
            commission: {
            	validators: {
                    notEmpty: {
                        message: '提成不能为空!'
                    }
                }
            }
        }
	});
}
//部门职位联动查询
function selectDeptName(){
	$.ajax({
		type : "POST",
		url : '${base}/admin/user/selectDeptName.html',
		data : {
			deptId:$('#deptId').val()
		},
		success : function(data) {
			var str = '<option>--请选择--</option>';
			for(var i=0;i< data.length;i++){
				str += '<option value="'+data[i].id+'">'+data[i].name+'</option>';
			}
			document.getElementById("jobId").innerHTML=str;
		},
		error : function(request) {
			
		}
	});
}
//添加成功提示
$("#submit").click(function() {
	$('#addUserForm').bootstrapValidator('validate');
	var bootstrapValidator = $("#addUserForm").data('bootstrapValidator');
	if(bootstrapValidator.isValid()){
		//获取必填项信息
		var selectedAreaIds = $('#areaSelect').find("option:selected").text();
		var fullName = $("#fullName").val();
		var telephone = $("#telephone").val();
		var deptId = $("#deptId").val();
		var jobId = $("#jobId").val();
		var baseWages = $("#baseWagesId").val();
		var commission = $("#commissionId").val();
		if(selectedAreaIds=="" || selectedAreaIds==null){
			layer.msg('区域不能为空');
			return;
		}
		if(fullName=="" || fullName==null){
			layer.msg('用户姓名不能为空');
			return;
		}
		if(telephone=="" || telephone==null){
			layer.msg('用户名/手机号不能为空');
			return;
		}
		if(deptId=="" || deptId==null){
			layer.msg('部门不能为空');
			return;
		}
		if(jobId=="" || jobId==null){
			layer.msg('职位不能为空');
			return;
		}
		if(baseWages=="" || baseWages==null){
			layer.msg('基本工资不能为空');
			return;
		}
		if(commission=="" || commission==null){
			layer.msg('提成不能为空');
			return;
		}
		$.ajax({
			type : "POST",
			url : '${base}/admin/user/add.html',
			data : $('#addUserForm').serialize(),// 你的formid
			error : function(request) {
				layer.msg('添加失败!');
			},
			success : function(data) {
				layer.load(1, {
					 shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
				formValidator();
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			    parent.layer.close(index);
			    window.parent.successCallback('1');
			    
			}
		});
	}
});
//提交时开始验证
$('#submit').click(function() {
    $('#addUserForm').bootstrapValidator('validate');
});
//点击返回
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
</script>
</body>
</html>