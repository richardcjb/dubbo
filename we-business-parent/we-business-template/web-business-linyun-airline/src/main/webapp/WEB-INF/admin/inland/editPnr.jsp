<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>+PNR</title>
	<link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
  <link rel="stylesheet" href="${base }/public/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="${base }/public/ionicons/css/ionicons.min.css">
  <link rel="stylesheet" href="${base }/public/dist/css/bookingOrderDetail.css"><!--本页面styleFlie-->
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="window.history.go(-1);">取消</button>
            <input type="button" id="submit" class="btn btn-primary right btn-sm" onclick="savePnrInfo();" value="保存"/>
            <h4>+PNR</h4>
          </div>
          <div class="modal-body" style="padding-top:0;">
            <div class="tab-content backcard">
            	<form id="addPnrForm">
            	<input id="pnrid" name="pnrid" type="hidden" value="${obj.pnrinfo.id }" >
                <table class="PNRtable">
                  <tr>
                    <td>PNR：</td>
                    <td><input id="pnr" name="pnr" type="text" class="form-control input-sm PNRlength" value="${obj.pnrinfo.PNR }"></td>
                    <td>登录帐号：</td>
                    <td>
                    	<select id="loginid" name="loginid" class="form-control input-sm">
                    		<option value="">请选择</option>
                    		<c:forEach items="${obj.loginselect }" var="one">
	                    		<c:choose>
	                        		<c:when test="${obj.pnrinfo.loginid eq one.id}">
	                        			<option value="${one.id }" selected="selected">${one.loginNumName }</option>
	                        		</c:when>
	                        		<c:otherwise>
			                    		<option value="${one.id }">${one.loginNumName }</option>
	                        		</c:otherwise>
	                        	</c:choose>
	                    	</c:forEach>
                    	</select>
                    </td>
                    <td>人数：</td>
                    <td><input id="peoplecount" name="peoplecount" type="text" class="form-control input-sm mustNumber" value="${obj.pnrinfo.peoplecount }"></td></td>
                    <td>币种：</td>
                    <td>
                      <select id="currency" name="currency" class="form-control input-sm">
                        <option value="">请选择</option>
                        <c:forEach items="${obj.bzcode }" var="one"> 
                        	<c:choose>
                        		<c:when test="${obj.pnrinfo.currency eq one.dictCode }">
				                     <option value="${one.dictCode }" selected="selected">${one.dictCode }</option>
                        		</c:when>
                        		<c:otherwise>
				                     <option value="${one.dictCode }">${one.dictCode }</option>
                        		</c:otherwise>
                        	</c:choose>
                     	</c:forEach>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td>成本单价：</td>
                    <td><input id="costprice" name="costprice" type="text" class="form-control input-sm mustNumberPoint" value="<fmt:formatNumber type="number" value="${obj.pnrinfo.costprice }" pattern="0.00" maxFractionDigits="2"/>"></td>
                    <td>成本总价：</td>
                    <td><input id="costpricesum" name="costpricesum" type="text" class="form-control input-sm mustNumberPoint" value="<fmt:formatNumber type="number" value="${obj.pnrinfo.costpricesum }" pattern="0.00" maxFractionDigits="2"/>"></td></td>
                    <td>销售单价：</td>
                    <td><input id="salesprice" name="salesprice" type="text" class="form-control input-sm mustNumberPoint" value="<fmt:formatNumber type="number" value="${obj.pnrinfo.salesprice }" pattern="0.00" maxFractionDigits="2"/>"></td></td>
                    <td>销售总价：</td>
                    <td><input id="salespricesum" name="salespricesum" type="text" class="form-control input-sm mustNumberPoint" value="<fmt:formatNumber type="number" value="${obj.pnrinfo.salespricesum }" pattern="0.00" maxFractionDigits="2"/>"></td></td>
                  </tr>
                </table>

                <div class="multiselectDiv">
                  <div class="row">
                    <div class="col-sm-5">
                      <p class="multiselectP">请选择</p>
                      <select name="from" id="multiselect" class="form-control" size="8" multiple="multiple">
                      	<c:forEach items="${obj.visitors }" var="one"> 
		                     <option value="${one.id }" data-position="${one.id }">${one.visitorname }</option>
                     	</c:forEach>
                      </select>
                    </div>
                    
                    <div class="col-sm-2">
                      <button type="button" id="multiselect_rightAll" class="btn btn-block"><i class="fa fa-angle-double-right"></i></button>
                      <button type="button" id="multiselect_rightSelected" class="btn btn-block"><i class="fa fa-angle-right"></i></button>
                      <button type="button" id="multiselect_leftSelected" class="btn btn-block"><i class="fa fa-angle-left"></i></button>
                      <button type="button" id="multiselect_leftAll" class="btn btn-block"><i class="fa fa-angle-double-left"></i></button>
                    </div>
                    
                    <div class="col-sm-5">
                      <p class="multiselectP">已选择</p>
                      <select name="to" id="multiselect_to" class="form-control" size="8" multiple="multiple">
                      	<c:forEach items="${obj.include }" var="one"> 
		                     <option value="${one.id }" data-position="${one.id }">${one.visitorname }</option>
                     	</c:forEach>
                      </select>
                    </div>
                  </div>
                </div>
                </form>
            </div>
          </div>
	</div>
   <!--JS 文件-->
	<script src="${base }/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base }/public/bootstrap/js/bootstrap.min.js"></script>

	<script src="${base }/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base }/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base }/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
    <script src="${base }/public/dist/js/multiselect.min.js"></script>
    <script src="${base }/public/dist/js/prettify.min.js"></script>
    <script src="${base }/admin/order/ordercommon.js"></script>
    <!--layer -->
  <script src="${base}/common/js/layer/layer.js"></script>
    <script type="text/javascript">
      $(function(){
          $('#multiselect').multiselect();
      });
    //票价折扣
  	var discountFare = '${obj.custominfo.discountFare}';
  	//手续费
  	var fees = '${obj.custominfo.fees}'; 
      //关闭窗口
      function closewindow(){
  		var index = parent.parent.layer.getFrameIndex(window.name); //获取窗口索引
  		parent.layer.close(index);
  	}
     //保存PNR信息
     function savePnrInfo(){
    	 var visitor = '';
    	 $('#multiselect_to option').each(function(i){
    		 visitor += $(this).val() + ',';
    	 });
    	 visitor = visitor.substr(0,visitor.length-1);
    	 var addPnrData = getFormJson('#addPnrForm');
    	 addPnrData.visitor = visitor;
    	 addPnrData.peoplecount = $('#peoplecount').val();
    	 //alert(JSON.stringify(addPnrData));
    	 layer.load(1);
    	 $.ajax({ 
 			type: 'POST', 
 			data: addPnrData, 
 			url: '${base}/admin/inland/editPnrInfo.html',
           success: function (data) { 
           	//alert("添加成功");
           	//location.reload();
           	layer.closeAll('loading');
           	closewindow();
           	window.parent.successCallback('2');
           },
           error: function (xhr) {
           	layer.msg("保存失败","",3000);
           } 
       });
     }
     
     
   //获取form下所有值
     function getFormJson(form) {
   	  var o = {};
   	  var a = $(form).serializeArray();
   	  $.each(a, function (){
   		  if (o[this.name] != undefined) {
   		  	if (!o[this.name].push) {
   	  	  		o[this.name] = [o[this.name]];
   	  		}
   	  		o[this.name].push(this.value || '');
   	  	  } else {
   	  	  	o[this.name] = this.value || '';
   	  	  }
   	  });
   	  return o;
     }
     //给人数赋值
     function assignment(){
  	   var count = $('#multiselect_to option').length; 
  	   if(count == 0){
  		   $('#peoplecount').val('');
  	   }else{
  		   $('#peoplecount').val(count);
  	   }
     }
     $('#costprice').on('input',function(){
  	   //成本单价
  	   var costprice = $('#costprice').val();
  	   //人数
  	   var peoplecount = $('#peoplecount').val();
  	   //自动填充销售单价
  	   var salesprice = parseFloat(costprice) * parseFloat(discountFare)/100 + parseFloat(fees);
  	   if(costprice){
  	 		if(isNaN(salesprice)){
  	 			$('#salesprice').val('');
  	 		}else{
  	 			$('#salesprice').val(salesprice.toFixed(2));
  	 		}
  	 	}else{
  	 		$('#salesprice').val('');
  	 	}
  	   if(peoplecount){
  		   var costpricesum = parseFloat(costprice) * parseFloat(peoplecount);
  		   $('#costpricesum').val(costpricesum.toFixed(2));
  		   var salespricesum = parseFloat(salesprice) * parseFloat(peoplecount);
  		   $('#salespricesum').val(salespricesum.toFixed(2));
  	   }
     });
     $('#salesprice').on('input',function(){
  	   //人数
  	   var peoplecount = 0;
  	   if($('#peoplecount').val()){
  		   peoplecount = parseInt($('#peoplecount').val());
  	   }
  	   var salesprice = 0;
  	   if($(this).val()){
  		   salesprice = $(this).val();
  	   }
  	   var salespricesum = salesprice * peoplecount;
  	   if(salespricesum != 0){
  		   $('#salespricesum').val(salespricesum.toFixed(2));
  	   }
     });
  </script>
</body>
</html>	