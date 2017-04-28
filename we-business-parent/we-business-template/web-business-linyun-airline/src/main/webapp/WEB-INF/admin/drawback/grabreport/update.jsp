<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>附件编辑</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
    <link rel="stylesheet" type="text/css" href="${base}/public/dist/css/receivePayment.css"><!--本页面style-->
</head>
<body>
	<div class="modal-top">
		<form id="updateForm" method="post">
    		<div class="modal-header boderButt">
	            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">取消</button>
	            <button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
	            <h4>附件编辑</h4>
          	</div>
          	<input name="id" type="hidden" value="${obj.reportList.id}" />
          	<div class="modal-body modal-bod" style="height: 483px;overflow-y:auto; ">
          		<div class="row">
                  	<div class="form-group">
                  		<label class="col-sm-3 text-right padding">汇款：</label>
                      	<div class="col-sm-3 padding">
                        	<input id="remitId" name="remit" type="text" class="form-control input-sm inputWidth" value="${obj.reportList.remit}" />
                      	</div>
                  	</div>
	               	<div class="form-group form-group1">
	                   <label class="col-sm-2 text-right padding">代理返点：</label>
	                   <div class="col-sm-3 padding">
	                     <input id="agentRebateId" name="agentRebate" type="text" class="form-control input-sm inputWidth" value="${obj.reportList.agentRebate}"/>
	                   </div>
                    </div>
                </div>
          	
          		<div class="row">
                  	<div class="form-group">
                  		<label class="col-sm-3 text-right padding">票价：</label>
                      	<div class="col-sm-3 padding">
                        	<input id="ticketPriceId" name="ticketPrice" type="text" class="form-control input-sm inputWidth" value="${obj.reportList.ticketPrice}" />
                      	</div>
                  	</div>
	               	<div class="form-group form-group1">
	                   <label class="col-sm-2 text-right padding">刷卡费：</label>
	                   <div class="col-sm-3 padding">
	                     <input id="swipeId" name="swipe" type="text" class="form-control input-sm inputWidth" value="${obj.reportList.swipe}" />
	                   </div>
                    </div>
                 </div>
                 
          		<div class="row">
                  	<div class="form-group">
                  		<label class="col-sm-3 text-right padding">税金/杂项：</label>
                      	<div class="col-sm-3 padding">
                        	<input id="taxId" name="tax" type="text" class="form-control input-sm inputWidth" value="${obj.reportList.tax}" />
                      	</div>
                  	</div>
	               	<div class="form-group form-group1">
	                   <label class="col-sm-2 text-right padding">消费税(GST)：</label>
	                   <div class="col-sm-3 padding">
	                     <input id="exciseTax1Id" name="exciseTax1" type="text" class="form-control input-sm inputWidth" value="${obj.reportList.exciseTax1}" />
	                   </div>
                    </div>
                 </div>
                 
          		<div class="row">
                  	<div class="form-group">
                  		<label class="col-sm-3 text-right padding">退税状态：</label>
                      	<div class="col-sm-3 padding">
                      		<select id="backStatusId" name="backStatus"
								class="form-control input-sm inputWidth">
								<option value="0"
									<c:if test="${'0' eq obj.reportList.backStatus}">selected</c:if>>已退</option>
								<option value="1"
									<c:if test="${'1' eq obj.reportList.backStatus}">selected</c:if>>未退</option>
							</select>
                      	</div>
                  	</div>
	               	<div class="form-group form-group1">
                  		<label class="col-sm-2 text-right padding">入澳时间：</label>
                      	<div class="col-sm-3 padding">
                        	<input id="inAustralianTimeId" name="inAustralianTime" type="text" class="form-control input-sm inputWidth" value="${obj.reportList.inAustralianTime}" />
                      	</div>
                  	</div>
                 </div>
                 
                 
          		<div class="row">
	               	<div class="form-group">
	                   <label class="col-sm-3 text-right padding">出澳时间：</label>
	                   <div class="col-sm-3 padding">
	                     <input id="outAustralianTimeId" name="outAustralianTime" type="text" class="form-control input-sm inputWidth" value="${obj.reportList.outAustralianTime}" />
	                   </div>
                    </div>
                    <div class="form-group  form-group1">
		                <label class="col-sm-2 text-right padding">备注：</label>
		                <div class="col-sm-3 padding">
		                 	<input id="remarkId" name="remark" type="text" class="form-control input-sm inputWidth" value="${obj.reportList.remark}" />
	               		</div>
	                </div>
                </div>
              <div class="bankSlipImg">
              		<iframe id="zhuce" style="height:350px; width:100%;" name="main" src="${obj.fileurl.url}" frameBorder="0" scrolling="no"></iframe>
              </div>
          </div>
        </form>  
	</div>
	
<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
<script src="${base}/common/js/layer/layer.js"></script>
<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
<script type="text/javascript">
//提交保存
$("#submit").click(function() {
	$.ajax({
		cache : false,
		type : "POST",
		url : '${base}/admin/drawback/grabreport/update.html',
		data : $('#updateForm').serialize(),
		error : function(request) {
			layer.msg('编辑失败!');
		},
		success : function(data) {
			layer.load(1, {
				shade : [ 0.1, '#fff' ]
			//0.1透明度的白色背景
			});
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
			window.parent.successCallback('9');
		}
	});
});
//点击取消
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
</script>
</body>
</html>