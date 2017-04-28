<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>附件预览</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
    <link rel="stylesheet" type="text/css" href="${base}/public/dist/css/receivePayment.css"><!--本页面style-->
    <link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
    <style type="text/css">
    	.form-control-feedback {position: absolute;top: 1px;right: -10px;}
    </style>
</head>
<body>
	<div class="modal-top">
		<form id="addForm" method="post">
    		<div class="modal-header boderButt">
	            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">取消</button>
	            <button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
	            <h4>附件预览</h4>
          	</div>
          	<div class="modal-body modal-bod" style="height:632px;overflow-y:auto; ">
              <div class="row"><!--PNR/代理返点/汇款金额-->
              	<div class="form-group inline">
                  <label class="col-sm-2 text-right padding">PNR：</label>
                  <div class="col-sm-2 padding">
                  	  	<input id="pnrInfoId" name="PNR" type="text" class="form-control input-sm inputWidth" placeholder="请输入PNR" />
                  </div>
                </div> 
                <div class="form-group inline">
                  <label class="col-sm-1 text-right padding">代理返点：</label>
                  <div class="col-sm-2 padding">
                  		<input id="agentRebateId" name="agentRebate" type="text" class="form-control input-sm inputWidth" placeholder="请输入代理返点"/>
                  </div>
                </div>  
                <div class="form-group inline"> 
                  <label class="col-sm-1 text-right padding">汇款：</label>
                  <div class="col-sm-2 padding">
                  		<input id="remitId" name="remit" type="text" class="form-control input-sm inputWidth" placeholder="请输入用户汇款金额" />
                  </div>
         	 	</div>
         	  </div><!--end PNR/代理返点/汇款金额-->
         	  
              <div class="row"><!--刷卡费/票价/消费税-->
              	<div class="form-group inline">
                  <label class="col-sm-2 text-right padding">刷卡费：</label>
                  <div class="col-sm-2 padding">
                  		<input id="swipeId" name="swipe" type="text" class="form-control input-sm inputWidth" placeholder="请输入刷卡费" />
                  </div>
                </div> 
                <div class="form-group inline">
                  <label class="col-sm-1 text-right padding">票价：</label>
                  <div class="col-sm-2 padding">
                  		<input id="ticketPriceId" name="ticketPrice" type="text" class="form-control input-sm inputWidth" placeholder="请输入票价" />
                  </div>
                </div>  
                <div class="form-group inline"> 
                  <label class="col-sm-1 text-right padding">消费税：</label>
                  <div class="col-sm-2 padding">
                  		<input id="exciseTax1Id" name="exciseTax1" type="text" class="form-control input-sm inputWidth" placeholder="请输入消费税(GST)" />
                  </div>
         	 	</div>
         	  </div><!--end 刷卡费/票价/消费税-->
         	  
              <div class="row"><!--税金/杂项/票价/消费税-->
              	<div class="form-group inline">
                  <label class="col-sm-2 text-right padding">税金/杂项：</label>
                  <div class="col-sm-2 padding">
                  		<input id="taxId" name="tax" type="text" class="form-control input-sm inputWidth" placeholder="请输入税金/杂项" />
                  </div>
                </div> 
                <div class="form-group inline">
                  <label class="col-sm-1 text-right padding">入澳时间：</label>
                  <div class="col-sm-2 padding">
                  		<input id="inAustralianTimeId" name="inAustralianTime" type="text" class="form-control input-sm inputWidth" placeholder="请输入入澳时间" />
                  </div>
                </div>  
                <div class="form-group inline"> 
                  <label class="col-sm-1 text-right padding">出澳时间：</label>
                  <div class="col-sm-2 padding">
                  		<input id="outAustralianTimeId" name="outAustralianTime" type="text" class="form-control input-sm inputWidth" placeholder="请输入出澳时间" />
                  </div>
         	 	</div>
         	  </div><!--end 税金(杂项)/入澳时间/出澳时间-->
         	  
              <div class="row"><!--退税状态/备注-->
              	<div class="form-group inline">
                  <label class="col-sm-2 text-right padding">退税状态：</label>
                  <div class="col-sm-2 padding">
                  		<select id="backStatusId" name="backStatus" class="form-control input-sm inputWidth">
                           <option value="0">已退</option>
                           <option value="1" selected="selected">未退</option>
                        </select>
                  </div>
                </div> 
                <div class="form-group inline">
                  <label class="col-sm-1 text-right padding">备注：</label>
                  <div class="col-sm-2 padding">
                  		<input id="remarkId" name="remark" type="text" class="form-control input-sm inputWidth" placeholder="请输入备注" />
                  </div>
                </div>  
         	  </div><!--end 退税状态/备注-->
         	  
              <div class="bankSlipImg">
              	  <iframe id="zhuce" style="height:445px; width:100%;" name="main" src="${obj.fileurl.url}" frameBorder="0" scrolling="no"></iframe>
              </div>
          </div>
        </form>  
	</div>
	
<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
<script src="${base}/common/js/layer/layer.js"></script>
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
        	PNR: {
                validators: {
                    notEmpty: {
                        message: 'PNR不能为空!'
                    },
                    stringLength: {/*长度提示*/
                   	    min: 1,
                   	    max: 13,
                   	    message: 'PNR长度不得超出13位!'
                   	  }
                }
            },
            agentRebate: {
                validators: {
                	regexp: {
                        regexp: /^(\d+(\.\d{1,20})?)?$/,
                        message: '代理返点只能输入整数或者小数!'
                    }
                }
            },
            remit: {
                validators: {
                	regexp: {
                        regexp: /^(\d+(\.\d{1,20})?)?$/,
                        message: '汇款只能输入整数或者小数!'
                    }
                }
            },
            swipe: {
                validators: {
                	regexp: {
                        regexp: /^(\d+(\.\d{1,20})?)?$/,
                        message: '刷卡费只能输入整数或者小数!'
                    }
                }
            },
            ticketPrice: {
                validators: {
                	regexp: {
                        regexp: /^(\d+(\.\d{1,20})?)?$/,
                        message: '票价只能输入整数或者小数!'
                    }
                }
            },
            exciseTax1: {
                validators: {
                	regexp: {
                        regexp: /^(\d+(\.\d{1,20})?)?$/,
                        message: '消费税只能输入整数或者小数!'
                    }
                }
            },
            tax: {
                validators: {
                	regexp: {
                        regexp: /^(\d+(\.\d{1,20})?)?$/,
                        message: '税金/杂项只能输入整数或者小数!'
                    }
                }
            }
        }
	});
});
</script>
<script type="text/javascript">
//提交保存
$("#submit").click(function() {
	$('#addForm').bootstrapValidator('validate');
	var bootstrapValidator = $("#addForm").data('bootstrapValidator');
	if(bootstrapValidator.isValid()){
		$.ajax({
			cache : false,
			type : "POST",
			url : '${base}/admin/drawback/grabreport/add.html',
			data : $('#addForm').serialize(),
			error : function(request) {
				layer.msg('添加失败!');
			},
			success : function(data) {
				layer.load(1, {
					shade : [ 0.1, '#fff' ]
				//0.1透明度的白色背景
				});
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				parent.layer.close(index);
				window.parent.successCallback('8');
			}
		});
	}
});
//提交时开始验证
$('#submit').click(function() {
    $('#addForm').bootstrapValidator('validate');
});
//点击取消
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
</script>
</body>
</html>