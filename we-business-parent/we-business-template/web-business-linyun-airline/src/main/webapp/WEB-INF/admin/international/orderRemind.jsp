<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>提醒设置</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
  <link rel="stylesheet" href="${base}/public/dist/css/internationOrderDetail.css"><!--本页面styleFlie-->
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow()">取消</button>
            <input type="submit" id="submit" class="btn btn-primary right btn-sm" onclick="saveRemindMessage()" value="保存"/>
            <input type="hidden" id="orderid" name="orderid" value="${obj.orderid }">
            <h4>提醒设置</h4>
          </div>
          <div class="modal-body" style="height:381px;overflow-y:auto; ">
              <table class="remindTable">
                <tbody>
                  <tr>
                    <td><label class="remindLabel">预订</label></td>
                    <td><input type="text" class="form-control input-sm remindData" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value="${obj.booking.reminddate }" pattern="yyyy-MM-dd HH:mm:ss" />"></td>
                    <td>
                      <input type="hidden" id="orderstatus" name="orderstatus" value="2">
                      <input type="hidden" id="remindstatus" name="remindstatus" value="2">
                      <select class="form-control input-sm messageType">
                          <c:forEach var="map" items="${obj.orderRemindEnum }" >
					   		<c:choose>
	                          	<c:when test="${map.key eq obj.booking.remindtype }">
							   		<option value="${map.key}" selected="selected">${map.value}</option>
	                          	</c:when>
	                          	<c:otherwise>
							   		<option value="${map.key}">${map.value}</option>
	                          	</c:otherwise>
                          	</c:choose>
						 </c:forEach>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td><label class="remindLabel">一订</label></td>
                    <td><input type="text" class="form-control input-sm remindData" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value="${obj.onebook.reminddate }" pattern="yyyy-MM-dd HH:mm:ss" />"></td>
                    <td>
                      <input type="hidden" id="orderstatus" name="orderstatus" value="3">
                      <input type="hidden" id="remindstatus" name="remindstatus" value="6">
                      <select class="form-control input-sm messageType">
                          <c:forEach var="map" items="${obj.orderRemindEnum }" >
					   		<c:choose>
	                          	<c:when test="${map.key eq obj.onebook.remindtype }">
							   		<option value="${map.key}" selected="selected">${map.value}</option>
	                          	</c:when>
	                          	<c:otherwise>
							   		<option value="${map.key}">${map.value}</option>
	                          	</c:otherwise>
                          	</c:choose>
						 </c:forEach>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td><label class="remindLabel">二订</label></td>
                    <td><input type="text" class="form-control input-sm remindData" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value="${obj.twobook.reminddate }" pattern="yyyy-MM-dd HH:mm:ss" />"></td>
                    <td>
                      <input type="hidden" id="orderstatus" name="orderstatus" value="4">
                      <input type="hidden" id="remindstatus" name="remindstatus" value="7">
                      <select class="form-control input-sm messageType">
                          <c:forEach var="map" items="${obj.orderRemindEnum }" >
                          	<c:choose>
	                          	<c:when test="${map.key eq obj.twobook.remindtype }">
							   		<option value="${map.key}" selected="selected">${map.value}</option>
	                          	</c:when>
	                          	<c:otherwise>
							   		<option value="${map.key}">${map.value}</option>
	                          	</c:otherwise>
                          	</c:choose>
						 </c:forEach>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td><label class="remindLabel">三订</label></td>
                    <td><input type="text" class="form-control input-sm remindData" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value="${obj.threebook.reminddate }" pattern="yyyy-MM-dd HH:mm:ss" />"></td>
                    <td>
                      <input type="hidden" id="orderstatus" name="orderstatus" value="5">
                      <input type="hidden" id="remindstatus" name="remindstatus" value="8">
                      <select class="form-control input-sm messageType">
                          <c:forEach var="map" items="${obj.orderRemindEnum }" >
					   		<c:choose>
	                          	<c:when test="${map.key eq obj.threebook.remindtype }">
							   		<option value="${map.key}" selected="selected">${map.value}</option>
	                          	</c:when>
	                          	<c:otherwise>
							   		<option value="${map.key}">${map.value}</option>
	                          	</c:otherwise>
                          	</c:choose>
						 </c:forEach>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td><label class="remindLabel">全款</label></td>
                    <td><input type="text" class="form-control input-sm remindData" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value="${obj.fullamount.reminddate }" pattern="yyyy-MM-dd HH:mm:ss" />"></td>
                    <td>
                      <input type="hidden" id="orderstatus" name="orderstatus" value="6">
                      <input type="hidden" id="remindstatus" name="remindstatus" value="9">
                      <select class="form-control input-sm messageType">
                          <c:forEach var="map" items="${obj.orderRemindEnum }" >
					   		<c:choose>
	                          	<c:when test="${map.key eq obj.fullamount.remindtype }">
							   		<option value="${map.key}" selected="selected">${map.value}</option>
	                          	</c:when>
	                          	<c:otherwise>
							   		<option value="${map.key}">${map.value}</option>
	                          	</c:otherwise>
                          	</c:choose>
						 </c:forEach>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td><label class="remindLabel">尾款</label></td>
                    <td><input type="text" class="form-control input-sm remindData" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value="${obj.tailmoney.reminddate }" pattern="yyyy-MM-dd HH:mm:ss" />"></td>
                    <td>
                      <input type="hidden" id="orderstatus" name="orderstatus" value="7">
                      <input type="hidden" id="remindstatus" name="remindstatus" value="10">
                      <select class="form-control input-sm messageType">
                          <c:forEach var="map" items="${obj.orderRemindEnum }" >
					   		<c:choose>
	                          	<c:when test="${map.key eq obj.tailmoney.remindtype }">
							   		<option value="${map.key}" selected="selected">${map.value}</option>
	                          	</c:when>
	                          	<c:otherwise>
							   		<option value="${map.key}">${map.value}</option>
	                          	</c:otherwise>
                          	</c:choose>
						 </c:forEach>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td><label class="remindLabel">出票</label></td>
                    <td><input type="text" class="form-control input-sm remindData" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value="${obj.ticketing.reminddate }" pattern="yyyy-MM-dd HH:mm:ss" />"></td>
                    <td>
                      <input type="hidden" id="orderstatus" name="orderstatus" value="8">
                      <input type="hidden" id="remindstatus" name="remindstatus" value="3">
                      <select class="form-control input-sm messageType">
                          <c:forEach var="map" items="${obj.orderRemindEnum }" >
					   		<c:choose>
	                          	<c:when test="${map.key eq obj.ticketing.remindtype }">
							   		<option value="${map.key}" selected="selected">${map.value}</option>
	                          	</c:when>
	                          	<c:otherwise>
							   		<option value="${map.key}">${map.value}</option>
	                          	</c:otherwise>
                          	</c:choose>
						 </c:forEach>
                      </select>
                    </td>
                  </tr>
                </tbody>
              </table>
          </div>
	</div>
   <!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>

	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<!-- My97DatePicker --> 
   <script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	//关闭窗口
    function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	
	function saveRemindMessage(){
		var orderremind = {};
		var orderid = $('#orderid').val();
		orderremind.orderid = orderid;
		var remindinfos = [];
		$('.remindTable').find('tr').each(function(i){
			var remindinfo = {};
			var remindData = $(this).find('.remindData').val();
			remindinfo.remindData = remindData;
			var messageType = $(this).find('.messageType').val();
			remindinfo.messageType = messageType;
			var orderstatus = $(this).find('#orderstatus').val();
			remindinfo.orderstatus = orderstatus;
			var remindstatus = $(this).find('#remindstatus').val();
			remindinfo.remindstatus = remindstatus;
			remindinfos.push(remindinfo);
		});
		orderremind.remindinfos = remindinfos;
		$.ajax({ 
			type: 'POST', 
			data: {data:JSON.stringify(orderremind)}, 
			url: '${base}/admin/international/saveOrderRemindInfo.html',
            success: function (data) { 
            	closewindow();
            	window.parent.successCallback(3);
	        },
	        error: function (xhr) {
	           layer.msg("保存失败","",3000);
	        } 
      });
	}
	</script>
</body>
</html>