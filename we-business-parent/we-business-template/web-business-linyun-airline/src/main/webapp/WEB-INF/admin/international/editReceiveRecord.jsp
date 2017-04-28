<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>添加记录-预收款</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
  <link rel="stylesheet" href="${base}/public/plugins/datatables/dataTables.bootstrap.css">
  <link rel="stylesheet" href="${base}/public/dist/css/internationOrderDetail.css"><!--本页面styleFlie-->
</head>
<body>
	<div class="modal-top">
    	<form id="addForm">
    	<div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow()">取消</button>
            <input type="submit" id="submit" class="btn btn-primary right btn-sm" value="保存" onclick="saveData()"/>
            <input id="id" name="id" type="hidden" value="${obj.recordinfo.id }">
            <input id="orderid" name="orderid" type="hidden" value="${obj.recordinfo.orderid }">
            <input id="recordtype" name="recordtype" type="hidden" value="${obj.recordinfo.recordtype }">
            <input id="orderstatus" name="orderstatus" type="hidden" value="${obj.recordinfo.orderstatus }">
            <input id="orderstatusid" name="orderstatusid" type="hidden" value="${obj.recordinfo.orderstatusid }">
            <h4>预收款</h4>
          </div>
          <div class="modal-body" style="height:130px;overflow-y:auto; ">
              <table class="addYSKtable">
                <tbody>
                      <tr>
                        <td><label>销售单价：</label></td>
                        <td><input id="costprice" name="costprice" type="text" class="form-control input-sm mustNumberPoint autocalc" value="<fmt:formatNumber type="number" value="${obj.recordinfo.costprice }" pattern="0.00" maxFractionDigits="2"/>"></td>
                        <td><label>预收款比例 ：</label></td>
                        <td><input id="prepayratio" name="prepayratio" type="text" class="form-control input-sm mustNumberPoint autocalc" value="${obj.prepayratio }"><span class="bfh">%</span></td>
                        <td><label>实际人数：</label></td>
                        <td><input id="actualnumber" name="actualnumber" type="text" class="form-control input-sm mustNumber autocalc" value="${obj.recordinfo.actualnumber }"></td>
                        <td><label>免罚金可减人数：</label></td>
                        <td><input id="freenumber" name="freenumber" type="text" class="form-control input-sm mustNumber autocalc" value="${obj.recordinfo.freenumber }"></td>
                      </tr>
                      <tr>
                        <td><label>本期罚金：</label></td>
                        <td><input id="currentfine" name="currentfine" type="text" class="form-control input-sm mustNumberPoint" value="<fmt:formatNumber type="number" value="${obj.recordinfo.currentfine }" pattern="0.00" maxFractionDigits="2"/>"></td>
                        <td><label>本期应付：</label></td>
                        <td><input id="currentdue" name="currentdue" type="text" class="form-control input-sm mustNumberPoint" value="<fmt:formatNumber type="number" value="${obj.recordinfo.currentdue }" pattern="0.00" maxFractionDigits="2"/>"></td>
                        <td><label>税金单价：</label></td>
                        <td><input id="ataxprice" name="ataxprice" type="text" class="form-control input-sm mustNumberPoint autocalc" value="<fmt:formatNumber type="number" value="${obj.recordinfo.ataxprice }" pattern="0.00" maxFractionDigits="2"/>"></td>
                        <td><label>本期实付：</label></td>
                        <td><input id="currentpay" name="currentpay" type="text" class="form-control input-sm mustNumberPoint" value="<fmt:formatNumber type="number" value="${obj.recordinfo.currentpay }" pattern="0.00" maxFractionDigits="2"/>"></td>
                        <td><label>币种：</label></td>
                        <td>
                          <select id="currency" name="currency" class="form-control input-sm">
                            <c:forEach items="${obj.bzcode }" var="one"> 
                            	<c:choose>
                            		<c:when test="${obj.recordinfo.currency eq one.id }">
					                     <option value="${one.id }" selected="selected">${one.dictCode }</option>
                            		</c:when>
                            		<c:otherwise>
					                     <option value="${one.id }">${one.dictCode }</option>
                            		</c:otherwise>
                            	</c:choose>
	                     	</c:forEach>
                          </select>
                        </td>
                      </tr>
                </tbody>
              </table>
          </div>
        </form>
	</div>
   <!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base }/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<!--layer -->
  	<script src="${base}/common/js/layer/layer.js"></script>
  	<script src="${base }/admin/order/ordercommon.js"></script>
	<script type="text/javascript">
	//关闭窗口
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	//保存记录
	function saveData(){
		layer.load(1);
		$.ajax({
			type: 'POST', 
			data: $("#addForm").serialize(), 
			url: '${base}/admin/international/saveEditRecord.html',
			async:false,
            success: function (data) {
            	layer.closeAll('loading');
            	closewindow();
            	window.parent.successCallback('2');
            },
            error: function (xhr) {
            	layer.msg("修改失败",{time: 2000});
            } 
      });
	}
	
	//自动加载利润合计
	  $(document).on('input', '.autocalc', function(e) {
		  
		  $('#actualyreduce').val('');
		  //已收钱数
		  var fineprice = 0;
		  if($('#fineprice').val()){
			  fineprice = $('#fineprice').val();
		  }
		  //原有人数
		  var autualypeople = 0;
		  if($('#autualypeople').val()){
			  autualypeople = $('#autualypeople').val();
		  }
		  //成本单价
		  var costprice = 0;
		  if($('#costprice').val()){
			  costprice = $('#costprice').val();
		  }
		  //预付款比例
		  var prepayratio = 0;
		  if($('#prepayratio').val()){
			  prepayratio = $('#prepayratio').val();
		  }
		  //实际人数
		  var actualnumber = 0;
		  if($('#actualnumber').val()){
			  actualnumber = $('#actualnumber').val();
		  }
		  //免罚金可减人数
		  var freenumber = 0;
		  if($('#freenumber').val()){
			  freenumber = $('#freenumber').val();
		  }
		  //税金单价
		  var ataxprice = 0;
		  if($('#ataxprice').val()){
			  ataxprice = $('#ataxprice').val();
		  }
		  if($('#prepayratio').val() && $('#actualnumber').val()){
			  //实收金额
			  var currentdue =  parseInt(actualnumber) * parseFloat(costprice) * parseFloat(prepayratio)/100 - parseFloat(fineprice);
			  if(!isNaN(currentdue) && currentdue != 0){
			   	 $('#currentdue').val(currentdue.toFixed(2));
			  }
			  //已减人数
			  var alreadydec = autualypeople - actualnumber;
			  if(!isNaN(alreadydec) && alreadydec > 0){
				  $('#actualyreduce').val(alreadydec);
			  }
			  //本期罚金
			  var currentfine = 0;
			  if(alreadydec > freenumber){
				  currentfine = parseFloat(fineprice) / parseInt(autualypeople) * (parseInt(alreadydec) -  parseInt(freenumber));
				  if(!isNaN(currentfine) && currentfine != 0){
				   	 $('#currentfine').val(currentfine.toFixed(2));
				  }
			  }
			  var currentpay = parseFloat(currentdue) + parseFloat(currentfine) + parseFloat(ataxprice);
			  if(!isNaN(currentpay) && currentpay != 0){
			   	 $('#currentpay').val(currentpay.toFixed(2));
			  }
		  }
	  });
	</script>
</body>
</html>	
