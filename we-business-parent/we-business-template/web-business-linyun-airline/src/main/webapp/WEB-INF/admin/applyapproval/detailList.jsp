<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
  <link rel="stylesheet" href="${base}/public/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="${base}/public/ionicons/css/ionicons.min.css">
  <link rel="stylesheet" href="${base}/public/dist/css/applyApproval.css">
  <title>审批手机-审批详情</title>
</head>
<body>
  <header class="header">
	<ul class="list-ul">
		<li><a href="${base}/admin/applyapproval/dataList.html?operation=${obj.operation}&date=${obj.date}" id="return"><i class="fa fa-angle-left"></i>返回</a></li>
		<li><h3>审批详情</h3></li>
		<li> </li>
	</ul>     
  </header>
  <c:if test="${obj.reduce=='YES' }">
  		<ul class="content-ul">
			<li>
				<span>订单号</span>
				<span>${obj.reduceList.ordersnum }</span>
			</li>
			<li>
				<span>减免对象</span>
				<span>${obj.reduceList.customname }</span>
			</li>
			<li>
				<span>项目用途</span>
				<span>${obj.reduceList.purposeStr }</span>
			</li>
			<li>
				<span>资金种类</span>
				<span></span>
			</li>
			<li>
				<span>金额</span>
				
				<span><fmt:formatNumber type="number" value="${obj.reduceList.account }" pattern="0.00" maxFractionDigits="2"></fmt:formatNumber></span>
			
			</li>
			<li>
				<span>手续费</span>
				<span></span>
			</li>
			<li>
				<span>币种</span>
				<span>${obj.reduceList.currency }</span>
			</li>
			<li>
				<span>发票</span>
				<span></span>
			</li>
			<li>
				<span>签收时间</span>
				<span><fmt:formatDate value="${obj.reduceList.optime }" pattern="yyy-MM-dd HH:mm:ss"/></span>
			</li>
			<li>
				<span>申请人</span>
				<span>${obj.reduceList.fullName }</span>
			</li>
		</ul>
  </c:if>
  <c:if test="${obj.reduce=='reduceno'}">
  		<ul class="content-ul">
			<li>
				<span>订单号</span>
				<span>${obj.detaillist.ordersnum }</span>
			</li>
			<li>
				<span>支付对象</span>
				<span>${obj.detaillist.shortName }</span>
			</li>
			<li>
				<span>项目用途</span>
				<span>${obj.detaillist.purposeStr }</span>
			</li>
			<li>
				<span>资金种类</span>
				<span>${obj.detaillist.fundTypeStr }</span>
			</li>
			<li>
				<span>金额</span>
				
				<c:if test="${obj.operation=='inlandNum'}">
				
					
					<span><fmt:formatNumber type="number" value="${obj.detaillist.costpricesum }" pattern="0.00" maxFractionDigits="2"></fmt:formatNumber></span>
				</c:if>
				<c:if test="${obj.operation=='international'}">
					<span><fmt:formatNumber type="number" value="${obj.detaillist.amount }" pattern="0.00" maxFractionDigits="2"></fmt:formatNumber></span>
					
				</c:if>
			</li>
			<li>
				<span>手续费</span>
				<%-- <span>${obj.detaillist.payFees }</span> --%>
				<span><fmt:formatNumber type="number" value="${obj.detaillist.payFees }" pattern="0.00" maxFractionDigits="2"></fmt:formatNumber></span>
			</li>
			<li>
				<span>币种</span>
				<span>${obj.detaillist.currencyStr }</span>
			</li>
			<li>
				<span>发票</span>
				<c:if test="${obj.detaillist.isInvioce ==1 }">
					<span>有</span>
				</c:if>
				<c:if test="${obj.detaillist.isInvioce ==0 }">
					<span>无</span>
				</c:if>
			</li>
			<li>
				<span>签收时间</span>
				
				<c:if test="${obj.operation=='inlandNum'}">
				
					<span><fmt:formatDate value="${obj.detaillist.approveTime }" pattern="yyy-MM-dd HH:mm:ss"/></span>
				</c:if>
				<c:if test="${obj.operation=='international'}">
					<span><fmt:formatDate value="${obj.detaillist.orderstime }" pattern="yyy-MM-dd HH:mm:ss"/></span>
				</c:if>
			</li>
			<li>
				<span>申请人</span>
				<span>${obj.detaillist.fullName }</span>
			</li>
		</ul>
  </c:if>
  <content class="content-list">
		
		
  </content>
  <input name="id" id="id" value="${obj.detaillist.id }" type="hidden">
  <input name="userId" id="userId" value="${obj.userId }" type="hidden">
  <input name="usingId" id="usingId" value="${obj.detaillist.usingId }" type="hidden">
  <input name="orderId" id="orderId" value="${obj.detaillist.orderId }" type="hidden">
  <input name="resultId" id="resultId" value="${obj.detaillist.resultId }" type="hidden">

<c:if test="${obj.operation=='inlandNum'}">
<input name="status" id="status" value="${obj.detaillist.orderPnrStatus }" type="hidden">
</c:if>

<!-- ***********减免需要的参数************* -->
<input name="reduceStatus" id="reduceStatus" value="${obj.reduceList.applyResult }" type="hidden">

<input name="reduceId" id="reduceId" value="${obj.reduceList.id }" type="hidden">
<input name="reduce" id="reduce" value="${obj.reduce }" type="hidden">



<c:if test="${obj.operation=='international'}">
<input name="status" id="status" value="${obj.detaillist.paystatus }" type="hidden">
</c:if>
  
  <input name="operation" id="operation" value="${obj.operation}" type="hidden">
  <footer>
  	
	<button id="agree" type="button" onclick="control('agree')">同意</button>
	<button  id="refuse" type="button" onclick="control('refuse')">拒绝</button>
  </footer>
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!--layer -->
<script src="${base}/common/js/layer/layer.js"></script>
<script type="text/javascript">
function control(flag){
	var status=$("#status").val();
	var reduceStatus=$("#reduceStatus").val();
	
	if(status==1){
		if(flag=="agree"){
			agree("agree");
			
		}
		if(flag=="refuse"){
			agree("refuse");
			
		}
		
	}else if(status==2){
		
	}else if(status==4){
		if(flag=="agree"){
			agree("agree");
			
		}
		
	}
	if(reduceStatus==1){
		if(flag=="agree"){
			agree("agree");
			
		}
		if(flag=="refuse"){
			agree("refuse");
			
		}
		
	}else if(reduceStatus==2){
		
	}else if(reduceStatus==4){
		if(flag=="agree"){
			agree("agree");
			
		}
		
	}
	
	
	
	
}
//删除提示
function agree(temp) {
	if(temp=='agree'){
		var ope="同意";
	}else if(temp="refuse"){
		var ope="拒绝";
	}
	layer.confirm("您确认"+ope+"吗？", {
	    btn: ["是","否"], //按钮
	    shade: false //不显示遮罩
	}, function(){
		// 点击确定之后
		var url = "${base}/admin/applyapproval/agree.html";
		
		$.ajax({
			type : 'POST',
			data : {
				id : $('#id').val(),
				usingId : $('#usingId').val(),
				status:$('#status').val(),
				temp:temp,
				orderId:$("#orderId").val(),
				resultId:$("#resultId").val(),
				operation:$("#operation").val(),
				reduce:$("#reduce").val(),
				reduceId:$("#reduceId").val(),
				userId:$("#userId").val(),
				reduceStatus:$("#reduceStatus").val()
			},
			dataType : 'json',
			url : url,
			success : function(data) {
				if ("200" == data.status) {
					layer.msg("审核成功!", "", 4000);
					/*  var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				     parent.layer.close(index);
				     window.parent.successCallback('3'); */
				     
					     window.location.href="${base}/admin/applyapproval/dataList.html?operation=${obj.operation}"; 
				    
				      
				     
				} else {
					layer.msg("审核失败!", "", 3000);
				}
			},
			error : function(xhr) {
				layer.msg("审核失败", "", 3000);
			}
		});
	}, function(){
	    // 取消之后不用处理
	});
}
function refresh(){
	
}

</script>
</body>
</html>
