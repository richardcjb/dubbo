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
	<!-- js -->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
</head>
<body onresize=hero(); onload="document.getElementById('thirdCompanyNameId').focus()">
          <div class="modal-top">
          <form id="addForm" method="post"> 
              <div class="modal-header boderButt">
                  <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">返回</button>
                  <button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
                  <h4>添加</h4>
              </div>
                <div class="modal-body" style="height:360px;overflow-y: auto;">
                 <div class="tab-content">
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">第三方公司名称：</label>
                            <div class="col-sm-8 padding">
                              <input id="thirdCompanyNameId" name="thirdCompanyName" class="form-control input-sm inpImpWid" placeholder="请输入第三方公司名称" />
                              <span class="prompt">*</span>
                            </div>
                        </div>
                        <div class="form-group row">
                        	<label class="col-sm-3 text-right padding">银行卡名称：</label>
                            <div class="col-sm-8 padding">
                              <input id="bankCardNameId" name="bankCardName" class="form-control input-sm inpImpWid" placeholder="请输入银行卡名称" />
                              <span class="prompt">*</span>
                            </div>
                        </div>
                        <div class="form-group row">
                        	<label class="col-sm-3 text-right padding">卡号：</label>
                            <div class="col-sm-8 padding">
                              <input id="bankCardNumId" name="bankCardNum" class="form-control input-sm inpImpWid" onkeyup="this.value=this.value.replace(/\s/g,'').replace(/....(?!$)/g,'$& ')"  maxlength="32" placeholder="请输入银行卡账号" />
                              <span class="prompt">*</span>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">状态：</label>
                            <div class="col-sm-8 padding">
                              <select id="status" name="status" class="form-control input-sm inpImpWid">
                                <option value="1" selected="selected">启用</option>
								<option value="2">删除</option>
                              </select>
                            </div>
                        </div>
						<div class="form-group row">
                            <label class="col-sm-3 text-right padding">备注：</label>
                            <div class="col-sm-8 padding ">
                              <textarea name="remark" class="form-control inpImpWid textareaHei"></textarea>
                            </div>
                        </div>
                      </form>
                    </div>
                </div>
            </div>
          </div>  
	<script type="text/javascript">
	//验证
	$(document).ready(function(){
		$('#addForm').bootstrapValidator({
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
	        	/* comDdictCode: {
	                validators: {
	                    notEmpty: {
	                        message: '字典代码不能为空!'
	                    },
	                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
	                         url: '${base}/admin/companydict/comdictinfo/checkTypeCodeExist.html',//验证地址
	                         message: '字典代码已存在，请重新输入!',//提示消息
	                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
	                         type: 'POST',//请求方式
	                         //自定义提交数据，默认值提交当前input value
	                         data: function(validator) {
	                            return {
	                            	comDdictCode:$('#comDdictCode').val(),
	                            	comTypeCode:$('#comTypeCode').val()
	                            };
	                         }
	                     },
		                regexp: {
		                	regexp: /^[a-zA-Z]+$/,
	                        message: '字典代码只能为英文字母!'
	                    }
	                }
	            }, */
	            bankCardNum: {
	            	validators: {
	                    notEmpty: {
	                        message: '银行卡账号不能为空!'
	                    },
	                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
		                    url: '${base}/admin/companydict/comdictinfo/checkBankCardNumExist.html',//验证地址
		                         message: '银行卡账号已存在，请重新输入!',//提示消息
		                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
		                         type: 'POST',//请求方式
		                         //自定义提交数据，默认值提交当前input value
		                         data: function(validator) {
		                            return {
		                            	bankCardNum:$('input[name="bankCardNum"]').val(),
		                            	comTypeCode:$('#comTypeCode').val()
		                            };
		                         }
		                   }
	                }
	            }
	        }
		});
	});
	//添加
	$("#submit").click(function(){
		$('#addForm').bootstrapValidator('validate');
		var bootstrapValidator = $("#addForm").data('bootstrapValidator');
		if(bootstrapValidator.isValid()){
			$.ajax({
	           type: "POST",
	           url:'${base}/admin/companydict/comdictinfo/addThirdPayMent.html',
	           data:$('#addForm').serialize(),// 你的formid
	           error: function(request) {
	              layer.msg('添加失败!',{time:2000});
	           },
	            success: function(data) {
					layer.load(1, {
						 shade: [0.1,'#fff'] //0.1透明度的白色背景
					});
		              	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						window.location.reload(); // 父页面刷新
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
