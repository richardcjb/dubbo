<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>编辑付款</title>
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet" type="text/css" href="${base}/public/dist/css/receivePayment.css">
<link href="${base }/public/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<!--本页面style-->
</head>
<body>
<form id="confirmInlandPayForm">
	<div class="modal-top">
		<div class="modal-header boderButt">
			<button type="button" id="closePayWindow" class="btn btn-primary right btn-sm">取消</button>
			<button  type="button" id="submit" onclick="updateConfirmPay();" class="btn btn-primary right btn-sm">保存</button>
			<h4>编辑付款</h4>
		</div>
		<div class="modal-body" style="height:580px; overflow-y: auto;">
			
			<table id="receivablesTable" class="table table-bordered table-hover">
				<thead>
					<tr>
						<th>订单号</th>
						<th>PNR</th>
						<th>客户团号</th>
						<th>收款单位</th>
						<th>开票日期</th>
						<th>人数</th>
						<th>开票人</th>
						<th>金额</th>
					</tr>
				</thead>
				<tbody id="inlandConfirmPayTbody">
					<input id="payIds" name="payIds" type="hidden" value="${obj.payId }">
					<input id="payNames" name="payNames" type="hidden" value="${obj.sameName }">
					<input id="operators" name="operators" type="hidden" value="${obj.operators }"><!-- 水单url -->
					<c:forEach var="one" items="${obj.payList}">
                		<tr ondblclick="trDetail(${one.uid });">
                			<td>${one.ordersnum }</td>
                			<td>${one.pnr }</td>
                			<td>${one.cusgroupnum }</td>
                			<td>${one.shortname }</td>
                			<td>${one.billingdate }</td>
                			<td>${one.peoplecount }</td>
                			<td>${one.approver }</td>
                			<td>
                				<fmt:formatNumber type="number" value="${one.salesprice }" pattern="0.00" maxFractionDigits="2"/>
                			</td>
                		</tr>
                	</c:forEach>
				</tbody>
			</table>
			<%-- <table class="selectTable">
				<tr>
					<td>银行：</td>
					<td>
						<select id="bankComp" name="bankComp" onchange="bankSelect();" class="form-control input-sm">
							<!-- <option>--请选择--</option> -->
							<c:forEach var="one" items="${obj.bankList}">
	                 			<c:choose>
	                          		<c:when test="${obj.companybank.bankcompid eq one.bankNameId }">
			                        	 <option value="${one.bankNameId }" selected="selected">${one.bankName }</option>
	                          		</c:when>
	                          		<c:otherwise>
		                        	 <option value="${one.bankNameId }">${one.bankName }</option>
	                          		</c:otherwise>
	                          	</c:choose>       	
	                        </c:forEach>
	                       
						</select>
					</td>
					<td>银行卡名称：</td>
					<td><select id="cardName" name="cardName" onchange="cardSelect();" class="form-control input-sm">
							<!-- <option>--请选择--</option> -->
							<option>${obj.companybank.cardName }</option>
					</select></td>
					<td>卡号：</td>
					<td>
						<select id="cardNum" name="cardNum" class="form-control input-sm">
							<!-- <option>--请选择--</option> -->
							<option>${obj.companybank.cardNum }</option>
						</select>
					</td>
					<td>合计：</td>
					<td id="totalMoney">
						<fmt:formatNumber type="number" value="${obj.totalMoney }" pattern="0.00" maxFractionDigits="2"/>
					</td>
					<input id="totalMoney" name="totalMoney" type="hidden" value="${obj.totalMoney }">
				</tr>
			</table> --%>
			<table class="payTable2">
				<tr>
					<td>银行：</td>
					<td>
						<select id="bankComp" name="bankComp" onchange="bankSelect();" class="form-control input-sm" disabled="disabled">
							<option value="0">--请选择--</option>
							<c:forEach var="one" items="${obj.bankList}">
	                 			<c:choose>
	                          		<c:when test="${obj.companybank.bankcompid eq one.bankNameId }">
			                        	 <option value="${one.bankNameId }" selected="selected">${one.bankName }</option>
	                          		</c:when>
	                          		<c:otherwise>
		                        	 <option value="${one.bankNameId }">${one.bankName }</option>
	                          		</c:otherwise>
	                          	</c:choose>       	
	                        </c:forEach>
	                       
						</select>
					</td>
					<td>银行卡名称：</td>
					<td><select id="cardName" name="cardName" onchange="cardSelect();" class="form-control input-sm" disabled="disabled">
							<!-- <option>--请选择--</option> -->
							<option>${obj.companybank.cardName }</option>
					</select></td>
					<td>卡号：</td>
					<td>
						<select id="cardNum" name="cardNum" class="form-control input-sm" disabled="disabled">
							<!-- <option>--请选择--</option> -->
							<option>${obj.companybank.cardNum }</option>
						</select>
					</td>
					<td>合计：</td>
					<td id="totalMoney">
						<fmt:formatNumber type="number" value="${obj.totalMoney }" pattern="0.00" maxFractionDigits="2"/>
					</td>
					<input id="totalMoney" name="totalMoney" type="hidden" value="${obj.totalMoney }">
				</tr>
				<tr>
					<td>国内外：</td>
					<td><select id="payAddress" name="payAddress" class="form-control input-sm">
						<c:if test='${obj.payList[0].payaddress eq 1}'>
							<option value=1 selected="selected">国内</option>
							<option value=2>境外</option>
						</c:if>
						<c:if test='${obj.payList[0].payaddress eq 2}'>
							<option value=1>国内</option>
							<option value=2 selected="selected">境外</option>
						</c:if>
					</select></td>
					<td>项目用途：</td>
					<td><select id="purpose" name="purpose" class="form-control input-sm">
							<option value="0">--请选择--</option>
							<c:forEach var="one" items="${obj.fkytList}">
	                        	<%-- <option value="${one.id }">${one.dictName }</option> --%>
	                        	<c:choose>
	                          		<c:when test="${obj.payList[0].purpose eq one.id }">
			                        	 <option value="${one.id }" selected="selected">${one.comDictName }</option>
	                          		</c:when>
	                          		<c:otherwise>
		                        	 <option value="${one.id }">${one.comDictName }</option>
	                          		</c:otherwise>
	                          	</c:choose>  
	                        </c:forEach>
					</select></td>
					<td>资金种类：</td>
					<td><select id="fundType" name="fundType" class="form-control input-sm">
							<option value="0">--请选择--</option>
							<c:forEach var="one" items="${obj.zjzlList}">
	                        	<c:choose>
	                          		<c:when test="${obj.payList[0].fundtype eq one.id }">
			                        	 <option value="${one.id }" selected="selected">${one.comDictName }</option>
	                          		</c:when>
	                          		<c:otherwise>
		                        	 <option value="${one.id }">${one.comDictName }</option>
	                          		</c:otherwise>
	                          	</c:choose>  
	                        </c:forEach>
					</select></td>
					<td>付款时间：</td>
					<td><input id="payDate" name="payDate" value="${obj.payList[0].payDate}" type="text" onFocus="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})" placeholder="2017-02-20" class="form-control input-sm"></td>
				</tr>
				<tr>
					<td>手续费：</td>
					<td><input id="payFees" name="payFees" value="<fmt:formatNumber type="number" value="${obj.payList[0].payfees}" pattern="0.00" maxFractionDigits="2"/>" type="text" class="form-control input-sm"></td>
					<td>金额：</td>
					<td><input id="payMoney" name="payMoney" value="<fmt:formatNumber type="number" value="${obj.payList[0].paymoney}" pattern="0.00" maxFractionDigits="2"/>"  type="text" class="form-control input-sm"></td>
					<td colspan="2">
						<input id="chineseMoney" name="payChineseMoney" value="${obj.payList[0].paychinesemoney}" type="text" class="form-control input-sm textIpnu" readonly="readonly"></td>
					<td class="bj">币种：</td>
					<td><select id="payCurrency" name="payCurrency" class="form-control input-sm">
							<option value="0">--请选择--</option>
							<c:forEach var="one" items="${obj.bzList}">
	                        	<%-- <option value="${one.id }">${one.dictCode }</option> --%>
	                        	<c:choose>
	                          		<c:when test="${obj.payList[0].paycurrency eq one.id }">
			                        	 <option value="${one.id }" selected="selected">${one.dictCode }</option>
	                          		</c:when>
	                          		<c:otherwise>
		                        	 <option value="${one.id }">${one.dictCode }</option>
	                          		</c:otherwise>
	                          	</c:choose>  
	                        </c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>发票：</td>
					<td><select id="isInvioce" name="isInvioce" class="form-control input-sm">
						<c:if test='${obj.payList[0].isinvioce eq "1"}'>
							<option value=1 selected="selected">有</option>
							<option value=0>无</option>
						</c:if>
						<c:if test='${obj.payList[0].isinvioce eq "0"}'>
							<option value=1>有</option>
							<option value=0 selected="selected">无</option>
						</c:if>
					</select></td>
					<td>申请人：</td>
					<td><input id="proposer" name="proposer" type="text" class="form-control input-sm" disabled="disabled" value="${obj.proposer}"></td>
					<td>审批人：</td>
					<td><input id="approver" name="approver" type="text" class="form-control input-sm" disabled="disabled" value="${obj.approver}"></td>
					<td>审批结果：</td>
					<td><input id="approveResult" name="approveResult" type="text" class="form-control input-sm" disabled="disabled" value="${obj.approveresult}"></td>
				</tr>
			</table>
			
			<input type="text" name="uploadFile" id="uploadFile" />
			<input id="receiptUrl" name="receiptUrl" value="${obj.receiptUrl }" type="hidden" ><!-- 水单url -->
			<div class="bankSlipImg"  align="center">
				<img id="receiptImg" width="100%" height="305" alt="" src="${obj.receiptUrl}">
			</div>
		</div>
	</div>
</form>
	<!--JS 文件-->
	<!-- ./wrapper -->
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<!-- My97DatePicker -->
	<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
	<!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
	<!-- uploadify -->
	<script type="text/javascript" src="${base }/public/plugins/uploadify/jquery.uploadify.min.js"></script>
	
	<!-- 確認付款js -->
	<script src="${base}/admin/receivePayment/inland/confirmPay.js"></script>
	<script src="${base}/admin/receivePayment/inland/inlandPay.js"></script>
	<!-- 收付款Common js -->
	<script src="${base}/admin/receivePayment/recPayCommon.js"></script>
	
	<script type="text/javascript">
	
		function updateConfirmPay(){
			$("#bankComp").removeAttr("disabled");
			$("#cardName").removeAttr("disabled");
			$("#cardNum").removeAttr("disabled");
			var bankComp = $("#bankComp").val();
			if(bankComp=="0"){
				layer.msg('银行不能为空');
				return;
			}
			$.ajax({
				type : 'POST',
				data : $("#confirmInlandPayForm").serialize(),
				async: false,
				url: BASE_PATH + '/admin/receivePay/inland/updateInlandPay.html',
				success : function(data) {
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					parent.layer.close(index);
					parent.layer.msg("编辑成功", "", 1000);
					parent.inlandPayTable.ajax.reload(
							function(json){
								autoHighLoad($('#inlandPayTable'));
							}
					);
				},
				error: function () {
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					parent.layer.close(index);
					parent.layer.msg("付款失败", "", 1000);
				}
			});
		}
		
		$(function(){
			//银行名称
			bankSelect();
			//文件上传
			$.fileupload1 = $('#uploadFile').uploadify({
				'auto' : true,//选择文件后自动上传
				'formData' : {
					'fcharset' : 'uft-8',
					'action' : 'uploadimage'
				},
				'buttonText' : '上传水单',//按钮显示的文字
				'fileSizeLimit' : '3000MB',
				'fileTypeDesc' : '文件',//在浏览窗口底部的文件类型下拉菜单中显示的文本
				'fileTypeExts' : '*.png; *.jpg; *.bmp; *.gif; *.jpeg;',//上传文件的类型
				'swf' : '${base}/public/plugins/uploadify/uploadify.swf',//指定swf文件
				'multi' : false,//multi设置为true将允许多文件上传
				'successTimeout' : 1800,
				'queueSizeLimit' : 100,
				'uploader' : '${base}/admin/drawback/grabfile/uploadFile.html;jsessionid=${pageContext.session.id}',
				'onUploadStart' : function(file) {
					$("#submit").attr('disabled',true);
				},
				'onUploadSuccess' : function(file, data, response) {
					var jsonobj = eval('(' + data + ')');
					var url  = jsonobj;//地址
					var fileName = file.name;//文件名称
					$('#receiptUrl').val(url);
					$('#receiptImg').attr('src',url);
					$("#submit").attr('disabled',false);
				},
				//加上此句会重写onSelectError方法【需要重写的事件】
				'overrideEvents': ['onSelectError', 'onDialogClose'],
				//返回一个错误，选择文件的时候触发
				'onSelectError':function(file, errorCode, errorMsg){
						switch(errorCode) {
						case -110:
							alert("文件 ["+file.name+"] 大小超出系统限制");
							break;
						case -120:
							alert("文件 ["+file.name+"] 大小异常！");
							break;
						case -130:
							alert("文件 ["+file.name+"] 类型不正确！");
							break;
						}
					},
					onError: function(event, queueID, fileObj) {　
						$("#submit").attr('disabled',false);
			        }  
				});
			});
		
			//银行名称改变
			function bankSelect(){
				$.ajax({
					cache : false,
					type : "POST",
					data : {
						bankId:$('#bankComp').val()
					},
					url : '${base}/admin/receivePay/inter/getCardNames.html',
					success : function(data) {
						/* var option = "<option>--请选择--</option>"; */
						var option = "";
						var nameNtr = option;
						var numStr = option;
						for(var i=0;i< data.length;i++){
							nameNtr += '<option value="'+data[i]+'">'+data[i]+'</option>';
						}
						document.getElementById("cardName").innerHTML = nameNtr;
						/* document.getElementById("cardNum").innerHTML = numStr; */
						cardSelect();
					},
					error : function(request) {
						
					}
				});
			}
			
			//银行卡名称改变
			function cardSelect(){
				$.ajax({
					cache : false,
					type : "POST",
					data : {
						cardName:$('#cardName').val()
					},
					url : '${base}/admin/receivePay/inland/getCardNums.html',
					success : function(data) {
						/* var str = "<option>--请选择--</option>"; */
						var str = "";
						for(var i=0;i< data.length;i++){
							str += '<option value="'+data[i]+'">'+data[i]+'</option>';
						}
						document.getElementById("cardNum").innerHTML=str;
					},
					error : function(request) {
						
					}
				});
			}
			
			function trDetail(id){
				var url = '${base}/admin/inland/bookingDetail.html?id='+ id;
				window.open(url);
			}
		</script>
</body>
</html>
