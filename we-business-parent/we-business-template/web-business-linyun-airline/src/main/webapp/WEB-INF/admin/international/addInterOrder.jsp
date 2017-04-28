<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>国际-添加订单</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
	<link rel="stylesheet" href="${base}/public/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="${base}/public/ionicons/css/ionicons.min.css">
	<%--<link rel="stylesheet" type="text/css" href="${base}/public/dist/css/inlandCross.css"> --%>
	<link rel="stylesheet" href="${base}/public/dist/css/internationOrderDetail.css"><!--本页面styleFlie-->
</head>
<body>
	<div class="modal-top">
     <div class="modal-header">
          <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow()">取消</button>
          <button type="submit" class="btn btn-primary right btn-sm" onclick="saveInterOrderInfo()">保存</button>
          <select id="interOrderStatus" name="interOrderStatus" class="form-control input-sm orderSelect right">
	            <c:forEach var="map" items="${obj.orderstatusenum}" >
			   		<option value="${map.key}">${map.value}</option>
				</c:forEach>
          </select>
          <label class="right orderLabel">生成订单</label>
          <input class="right orderInput" type="checkbox" checked="checked">
          <h4>添加订单</h4>
     </div>
      <div class="modal-body modal-hei" style="height:484px;overflow-y: auto;">
          <div class="customerInfo"><!--客户信息-->
               <div class="infoTop">
                 <p>客户信息</p>
               </div>
               <div class="infofooter">
                 <table>
                   <tr>
                     <td><label><font id="custInfoName">客户姓名：</font></label></td> 
                     <td><select id="linkName" name="linkName" class="form-control input-sm" multiple="multiple" placeholder="请输入客户姓名">
                     	</select>
                     	<input id="customerId" name="customerId" type="hidden">
                     	<input id="discountFare" name="discountFare" type="hidden">
                     	<input id="fees" name="fees" type="hidden">
                     	</td>
                     <td><label style="position: relative;top: 4px;">结算方式：</label></td>
                     <td colspan="3"><pre class="preTxt">
							<span id="payTypeId">不限</span> 
								&nbsp;信用额度：<span id="creditLineId">0.00</span>   
								<font id="fontLSqk">
									&nbsp;历史欠款：<span id="arrearsId">0.00</span> 
								</font>  
								&nbsp;预存款：<span id="preDepositId">0.00</span> 
						</pre></td>
                     <td><input type="button"  id="clearBtn" value="清空" class="btn btn-primary btn-sm clearBtn"><i class="UnderIcon fa fa-chevron-circle-down"></i></td>
                   </tr>
                 </table>

                 <table class="hideTable none hideTable">
                   <tr>
                     <td><label>公司简称：</label></td>
                     <td><input id="shortName" name="shortName" type="text" class="form-control input-sm" placeholder="" readonly="true"></td>
                     <td><label>电话：</label></td>
                     <td><input id="telephone" name="telephone" type="text" class="form-control input-sm" readonly="true"></td>
                     <td><label>地址：</label></td>
                     <td colspan="3"><input id="address" name="address" type="text" class="form-control input-sm addressInput" readonly="true"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>负责人：</label></td>
                     <td><input id="responsible" name="responsible" type="text" class="form-control input-sm" readonly="true"></td>
                     <td><label>网址：</label></td>
                     <td><input id="siteUrl" name="siteUrl" type="text" class="form-control input-sm" readonly="true"></td>
                     <td><label>传真：</label></td>
                     <td><input id="fax" name="fax" type="text" class="form-control input-sm" readonly="true"></td>
                     <td><label>出发城市：</label></td>
                     <td><input id="departureCity" name="departureCity" type="text" class="form-control input-sm addressInput" readonly="true"></td>
                   </tr>
                 </table>
               </div>
          </div><!--end 客户信息-->
          <div class="customerInfo"><!--航段信息-->
               <div class="infoTop">
                 <p>航段信息</p>
               </div>
               <div id="infofooter" class="infofooter">
                 <table class="addHDtable">
                    <tbody>
                          <tr>
                             <td><label>航空公司：</label></td>
                             <td><select id="airlinecom" name="airlinecom" class="form-control input-sm disab" multiple="multiple"></select></td>
                             <td><label>人数：</label></td>
                             <td><input id="peoplecount" name="peoplecount" type="text" class="form-control input-sm disab mustNumber"></td>
                             <td><label>成本单价：</label></td>
                             <td><input id="costsingleprice" name="costsingleprice" type="text" class="form-control input-sm disab mustNumberPoint"></td>
                           </tr>
                          <tr>
                            <td><label>记录编号：</label></td>
                            <td colspan="11"><input id="pnr" name="pnr" type="text" class="form-control input-sm numTd PNRlength"></td>
                          </tr>
                          <tr class="addHD-tr">
                            <td><label>出发城市：</label></td>
                            <td><select id="leavecity" name="leavecity" type="text" class="form-control input-sm" multiple="multiple"></select></td>
                            <td><label>抵达城市：</label></td>
                            <td><select id="arrivecity" name="arrivecity" type="text" class="form-control input-sm" multiple="multiple"></select></td>
                            <td><label>出发日期：</label></td>
                            <td><input id="leavedate" name="leavedate" type="text" class="form-control input-sm" onFocus="WdatePicker({minDate:'%y-%M-%d'})" placeholder="2017-03-16"></td>
                            <td><label>航班号：</label></td>
                            <td><select id="ailinenum" name="ailinenum" type="text" class="form-control input-sm" placeholder="ca309" multiple="multiple"></select></td>
                            <td><label>出发时间：</label></td>
                            <td><input id="leavetime" name="leavetime" type="text" class="form-control input-sm mustTimes" placeholder=""></td>
                            <td><label>抵达时间：</label></td>
                            <td><input id="arrivetime" name="arrivetime" type="text" class="form-control input-sm mustArriveTimes" placeholder=""></td>
                            <td><a href="javascript:;" class="glyphicon glyphicon-plus addHDIcon"></a></td>
                          </tr>
                    </tbody>
                 </table>
               </div>
          </div><!--end 航段信息-->
      </div>
	</div>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
   <!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<!-- select2 -->
	<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
	<!-- My97DatePicker -->
	<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<!--layer -->
	<script src="${base}/common/js/layer/layer.js"></script>
	<script src="${base }/admin/order/ordercommon.js"></script>
	<script src="${base}/admin/international/addinterorder.js"></script><!-- AdminLTE App -->
	<script src="${base }/admin/international/addairline.js"></script>
  <script type="text/javascript">
//关闭窗口
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
    $(function(){
      $('.UnderIcon').on('click',function(){//客户信息 显示/隐藏
          $('.hideTable').toggle();
        });
     /*  //客户需求的 + 按钮
      $('.addHDIcon').click(function(){
          var divTest = $(this).parent().parent(); 
          var newDiv = divTest.clone(true);
          divTest.after(newDiv);
          var No = parseInt(divTest.find("p").html())+1;//用p标签显示序号
          newDiv.find("p").html(No); 
          newDiv.find('.addHDIcon').parent().remove();
          newDiv.append('<td class="removeIconTd"><i class="glyphicon glyphicon-minus removHDIcon"></i></td>');
      });

      //客户需求的 - 按钮
      $(document).on("click",".removHDIcon",function(){
          $(this).parent().parent().remove();
      }); */
    });
  //保存航段信息
 	 function saveInterOrderInfo(){
 		 var data = {};
 		 var customerId = $('#customerId').val();
 		 data.customerId = customerId;
 		 var interOrderStatus = $('#interOrderStatus').val();
 		 data.interOrderStatus = interOrderStatus;
 		 //var airlinecom = $("#airlinecom").select2("val");
 		 var airlinecom = $("#airlinecom").val();
			if (airlinecom) {
				airlinecom = airlinecom.join(',');
			}
 		 data.airlinecom = airlinecom;
 		 var peoplecount = $('#peoplecount').val();
 		 data.peoplecount = peoplecount;
 		 var costsingleprice = $('#costsingleprice').val();
 		 data.costsingleprice = costsingleprice;
 		 var pnr = $('#pnr').val();
 		 data.pnr = pnr;
 		 var airinfos = [];
 		 $('.addHD-tr').each(function(i){
 			 var airinfo = {};
 			var leavecity = $(this).find('[name=leavecity]').val();
 			if (leavecity) {
 				leavecity = leavecity.join(',');
 			}
 			airinfo.leavecity = leavecity;
 			//抵达城市
 			var arrivecity = $(this).find('[name=arrivecity]').val();
 			if (arrivecity) {
 				arrivecity = arrivecity.join(',');
 			}
 			airinfo.arrivecity = arrivecity;
 			var ailinenum = $(this).find('[name=ailinenum]').val();
			if (ailinenum) {
				ailinenum = ailinenum.join(',');
 			}
			airinfo.ailinenum = ailinenum;
 			var leavedate = $(this).find('[name=leavedate]').val();
			airinfo.leavedate = leavedate;
 			var leavetime = $(this).find('[name=leavetime]').val();
			airinfo.leavetime = leavetime;
 			var arrivetime = $(this).find('[name=arrivetime]').val();
			airinfo.arrivetime = arrivetime;
			airinfos.push(airinfo);
 		 });
 		 data.airinfos = airinfos;
 		if(customerId){
	 		layer.load(1);
	 		$.ajax({ 
				type: 'POST', 
				data: {data:JSON.stringify(data)}, 
				url: '${base}/admin/international/saveInterOrderInfo.html',
	           success: function (data) { 
	           	layer.closeAll('loading');
	           	closewindow();
	           	window.parent.successCallback('1');
	           },
	           error: function (xhr) {
	           	layer.msg("添加失败","",3000);
	           } 
	       });
 		}else{
 			layer.msg("请选择客户姓名","",3000);
 		}
 	 }
  </script>
</body>
</html>	
