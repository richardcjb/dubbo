<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>预订订单详情</title>
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.css">
  <!-- 图标 -->
  <link rel="stylesheet" href="${base }/public/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="${base }/public/ionicons/css/ionicons.min.css">
  <link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
  <link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
  <link rel="stylesheet" href="${base }/public/dist/css/skins/_all-skins.min.css">
  <link rel="stylesheet" href="${base }/public/plugins/datatables/dataTables.bootstrap.css">
  <link rel="stylesheet" href="${base }/public/dist/css/query.css">
  <link rel="stylesheet" href="${base }/public/dist/css/queryOrderDetail.css">
  <link rel="stylesheet" href="${base }/public/dist/css/bookingOrderDetail.css"><!--本页面styleFlie-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
  <!--Header -->
  <header class="main-header">
    <!-- Logo -->
    <a href="index2.html" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini">航空</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg">航空票务系统</span>
    </a>

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
      <!-- Sidebar toggle button-->
      <!-- <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a> -->
      <!-- Navbar Right Menu -->
      
    </nav>
  </header>
  <!--end Header -->

  <!--right Content-->
  <div class="content-wrapper">
    <section class="content">
        <div class="row col-sm-10">
          <div class="customerInfo"><!--客户信息-->
               <div class="infoTop">
                 <p>客户信息</p>
                 <div class="infoTopContent">
                   <span>${obj.orderinfo.ordersnum }</span>
                   <select id="orderType" name="orderType" class="form-control input-sm conSelect cf" disabled="disabled">
                     <c:forEach var="map" items="${obj.orderstatusenum}" >
                     	<c:if test="${obj.orderinfo.ordersstatus <= map.key }">
                     		<c:choose>
                     			<c:when test="${obj.orderinfo.ordersstatus eq map.key }">
                     				<option value="${map.key}" selected="selected">${map.value}</option>
                     			</c:when>
                     			<c:otherwise>
							   		<option value="${map.key}">${map.value}</option>
                     			</c:otherwise>
                     		</c:choose>
                     	</c:if>
					</c:forEach>
                   </select>
                   <button type="button" class="btn btn-primary input-sm btnSave none">保存</button>
                   <button type="button" class="btn btn-primary input-sm btnCancel none">取消</button>
                   <button type="button" class="btn btn-primary input-sm editBtn">编辑</button>
                 </div>
               </div>
               <div class="infofooter">
                 <table>
                   <tr>
                     <td><label>
                     	<font id="customeidcolor"> 客户姓名：</font>
                     </label></td>
                     <td><input id="linkName" name="linkName" disabled="disabled" type="text" class="form-control input-sm" value="${obj.custominfo.linkMan }">
                     	<input id="customerId" name="customerId" type="hidden" value="${obj.custominfo.id }"/>
                     	<!-- 订单id -->
                     	<input id="orderedid" name="orderedid" type="hidden" value="${obj.orderinfo.id }"></td>
                     <td><label style="position: relative;top: 4px;">结算方式：</label></td>
                     <td colspan="3"><pre class="preTxt">
                     <c:choose>
                     	<c:when test="${obj.custominfo.payType eq 1 }">
                     		现金
                     	</c:when>
                     	<c:when test="${obj.custominfo.payType eq 2 }">3
                     		支票
                     	</c:when>
                     	<c:when test="${obj.custominfo.payType eq 3 }">
                     		银行汇款
                     	</c:when>
                     	<c:when test="${obj.custominfo.payType eq 4 }">
                     		第三方
                     	</c:when>
                     	<c:when test="${obj.custominfo.payType eq 5 }">
                     		其他
                     	</c:when>
                     </c:choose>　
                     	 信用额度：<fmt:formatNumber type="number" value="${empty obj.custominfo.creditLine?0:obj.custominfo.creditLine}" pattern="0.00" maxFractionDigits="2"/>  
                     		<font id="historyqiancolor"> 历史欠款：<fmt:formatNumber type="number" value="${empty obj.historymony? 0.00:obj.historymony}" pattern="0.00" maxFractionDigits="2"/></font>　
                   		 预存款：<fmt:formatNumber type="number" value="${empty obj.custominfo.preDeposit?0:obj.custominfo.preDeposit}" pattern="0.00" maxFractionDigits="2"/></pre></td>
                     <td><i class="UnderIcon fa fa-chevron-circle-down"></i></td>
                   </tr>
                 </table>

                 <table class="hideTable none">
                   <tr>
                     <td><label>公司简称：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="" value="${obj.custominfo.shortName }" readonly="true"></td>
                     <td><label>电话：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="" value="${obj.custominfo.telephone }" readonly="true"></td>
                     <td><label>地址：</label></td>
                     <td colspan="3"><input type="text" class="form-control input-sm addressInput" placeholder="" value="${obj.custominfo.address }" readonly="true"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>负责人：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="" value="${obj.responsible }" readonly="true"></td>
                     <td><label>网址：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="" value="${obj.custominfo.siteUrl }" readonly="true"></td>
                     <td><label>传真：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="" value="${obj.custominfo.fax }" readonly="true"></td>
                     <td><label>出发城市：</label></td>
                     <td><input type="text" class="form-control input-sm addressInput" placeholder="" value="${obj.custominfo.id }" readonly="true"></td>
                     
                   </tr>
                 </table>

               </div>
          </div><!--客户信息-->

			<iframe name='hidden_frame' id='hidden_frame' style="display: none"></iframe>
          <div class="customerInfo"><!--客户需求-->
               <div class="infoTop">
                 <p>客户需求</p>
               </div>
               <c:choose>
               		<c:when test="${fn:length(obj.customneedinfo)>0}">
		               <c:forEach var="customneed" items="${obj.customneedinfo }" varStatus="varstatus">
		               <div id="infofooter" class="infofooter">
		                <div class="DemandDiv">
		                 <span class="titleNum">${varstatus.index+1 }</span>
		                 <c:choose>
			                 <c:when test="${varstatus.index eq 0 }">
				                 <a href="${base }/admin/inland/downloadVisitorTemplate.html" class="btn btn-primary btn-sm addDemand none" target="hidden_frame">游客模板</a>
				                 <!-- <a href="javascript:;" class="btn btn-primary btn-sm addDemand none">上传游客</a> -->
				                 <form id="uploadExcelForm" action="${base}/admin/inland/importVisitor.html?dingdanid=${obj.orderinfo.id }" name="form3" enctype="multipart/form-data" method="post" target="hidden_frame" style="display: inline;">
				                     <p class="flie_A btn btn-primary btn-sm addDemand none">上传游客<input name="excelFile" id="excelFile" onchange="javascript:onfileChange();" type="file"/></p>
								</form>
				                 <a href="javascript:;" class="btn btn-primary btn-sm addDemand none addXuQiu"><b>+</b>&nbsp;&nbsp;需求</a>
			                 </c:when>
			                 <c:otherwise>
			                 	<a href="javascript:;" class="btn btn-primary btn-sm removeDemand none"><b>-</b>&nbsp;&nbsp;需求</a>
			                 </c:otherwise>
		                 </c:choose>
		                 <input type="hidden" id="customneedid" name="customneedid" value="${customneed.cusinfo.id }">
		                 <table class="cloTable" id="tableWid">
		                   <tr>
		                     <td><label>出发城市：</label></td>
		                     <td><select id="leavecity" name="leavecity" disabled="disabled" class="form-control input-sm select2" multiple="multiple" placeholder="PEK(北京)">
			                     	<c:forEach var="one" items="${obj.city }">
			                    		<c:choose>
			                    			<c:when test="${customneed.cusinfo.leavecity eq one.dictCode }">
												<option value="${one.dictCode }" selected="selected">${one.dictCode}-${one.englishName }-${one.countryName }</option>
			                    			</c:when>
			                    			<c:otherwise>
												<option value="${one.dictCode }">${one.dictCode}-${one.englishName }-${one.countryName }</option>
			                    			</c:otherwise>
			                    		</c:choose>
									</c:forEach>
			                     </select>
			                 </td>
		                     <td><label>抵达城市：</label></td>
		                     <td><select id="arrivecity" name="arrivecity" disabled="disabled" class="form-control input-sm" multiple="multiple" placeholder="SYD(悉尼)">
			                     	<c:forEach var="one" items="${obj.city }">
			                    		<c:choose>
			                    			<c:when test="${customneed.cusinfo.arrivecity eq one.dictCode }">
												<option value="${one.dictCode }" selected="selected">${one.dictCode}-${one.englishName }-${one.countryName }</option>
			                    			</c:when>
			                    			<c:otherwise>
												<option value="${one.dictCode }">${one.dictCode}-${one.englishName }-${one.countryName }</option>
			                    			</c:otherwise>
			                    		</c:choose>
									</c:forEach>
			                     </select></td>
		                     <td><label>出发日期：</label></td>
		                     <td><input id="leavedate" name="leavedate" disabled="disabled" type="text" class="form-control input-sm textWid" placeholder="2017-02-22" onFocus="WdatePicker({minDate:'${customneed.cusinfo.leavetdate }'})" value="<fmt:formatDate value="${customneed.cusinfo.leavetdate }" pattern="yyyy-MM-dd" />"/></td>
		                     <td><label>人数：</label></td>
		                     <td><input id="peoplecount" name="peoplecount" disabled="disabled" type="text" class="form-control input-sm textWid mustNumber" value="${customneed.cusinfo.peoplecount }"/></td>
		                     <td><label class="labelWid">早中晚：</label></td>
		                     <td>
		                       <select id="tickettype" name="tickettype" disabled="disabled" class="form-control input-sm textWid" value="${customneed.cusinfo.tickettype }">
			                         <c:choose>
			                       	 	<c:when test="${customneed.cusinfo.tickettype eq 1 }">
					                         <option value="1" selected="selected">早</option>
			                       	 	</c:when>
			                       	 	<c:otherwise>
				                         <option value="1">早</option>
			                       	 	</c:otherwise>
			                       	 </c:choose>
			                       	 <c:choose>
			                       	 	<c:when test="${customneed.cusinfo.tickettype eq 2 }">
					                         <option value="2" selected="selected">中</option>
			                       	 	</c:when>
			                       	 	<c:otherwise>
				                         	<option value="2">中</option>
			                       	 	</c:otherwise>
			                       	 </c:choose>
			                       	 <c:choose>
			                       	 	<c:when test="${customneed.cusinfo.tickettype eq 3 }">
					                         <option value="3" selected="selected">晚</option>
			                       	 	</c:when>
			                       	 	<c:otherwise>
				                         	<option value="3">晚</option>
			                       	 	</c:otherwise>
			                       	 </c:choose>
			                       </select>
		                     </td>
		                   </tr>
		                   <c:choose>
			                   		<c:when test="${fn:length(customneed.ailines)>0}">
					                   <c:forEach var="airline" items="${customneed.ailines }" varStatus="status">
					                   
					                   <tr name="airlineinfo" class="airlineinfo">
					                     <td></span><label>航空公司：</label></td>
					                     <td><select id="aircom" name="aircom" disabled="disabled" class="form-control input-sm"  multiple="multiple" placeholder="">
					                     	   <c:forEach items="${obj.aircom }" var="one"> 
						                   			<c:choose>
							                   			<c:when test="${airline.aircom  eq one.dictCode  }">
															<option value="${one.dictCode }" selected="selected">${one.dictCode }-${one.dictName }</option>
							                   			</c:when>
							                   			<c:otherwise>
								                     		<option value="${one.dictCode }">${one.dictCode }-${one.dictName }</option>
							                   			</c:otherwise>
						                    		</c:choose>
						                     	</c:forEach>
					                     	</select>
					                     	<!-- 航班信息隐藏域 -->
					                     	<input type="hidden"  id="airlineid" name="airlineid" value="${airline.id }">
					                     	</td>
					                     <td><label>航班号：</label></td>
					                     <td><select id="ailinenum" name="ailinenum" disabled="disabled" class="form-control input-sm"  multiple="multiple" placeholder="SYD(悉尼)">
					                     	<c:forEach items="${obj.airline }" var="one"> 
						                   			<c:choose>
							                   			<c:when test="${airline.ailinenum  eq one.dictName  }">
															<option value="${one.dictName }" selected="selected">${one.dictName }</option>
							                   			</c:when>
							                   			<c:otherwise>
								                     		<option value="${one.dictName }">${one.dictName }</option>
							                   			</c:otherwise>
						                    		</c:choose>
						                     	</c:forEach>
					                     	</select></td>
					                     <td><label>出发时间：</label></td>
					                     <td><input id="leavetime" name="leavetime" disabled="disabled" type="text" class="form-control input-sm textWid mustTimes" placeholder="" value="${airline.leavetime }"/></td>
					                     <td><label>抵达时间：</label></td>
					                     <td><input id="arrivetime" name="arrivetime" disabled="disabled" type="text" class="form-control input-sm textWid mustArriveTimes" value="${airline.arrivetime }"/></td>
					                     <td><label class="labelWid">成本价：</label></td>
					                     <td><input id="formprice" name="formprice" type="text" disabled="disabled" class="form-control input-sm textWid mustNumberPoint costPrice" value="<fmt:formatNumber type="number" value="${airline.formprice }" pattern="0.00" maxFractionDigits="2"/>"/></td>
					                     <td><label class="labelWid">销售价：</label></td>
					                     <td><input id="price" name="price" type="text" disabled="disabled" class="form-control input-sm textWid mustNumberPoint xiaoShouCls" value="<fmt:formatNumber type="number" value="${airline.price }" pattern="0.00" maxFractionDigits="2"/>"/>
					                     	 <c:choose>
					                     	 	<c:when test="${empty airline.price } ">
					                     	 		<input type="hidden" id="ispriceempty" name="ispriceempty" value="1">
					                     	 	</c:when>
					                     	 	<c:otherwise>
					                     	 		<input type="hidden" id="ispriceempty" name="ispriceempty">
					                     	 	</c:otherwise>
					                     	 </c:choose>
					                     </td>
					                     <c:choose>
					                     	<c:when test="${status.index eq 0 }">
							                     <td colspan="3" class="tdBtn">
							                      <a href="javascript:;" name="addButton" class="glyphicon glyphicon-plus addIcon removAddMake none"></a>
							                     </td>
					                     	</c:when>
					                     	<c:otherwise>
												<td class="removeIconTd"><i class="glyphicon glyphicon-minus removIcon none"></i></td>			                     	
					                     	</c:otherwise>
					                     </c:choose>
					                   </tr>
					                   </c:forEach>
			                   		</c:when>
			                   		<c:otherwise>
					                   <tr name="airlineinfo" class="airlineinfo">
					                     <td></span><label>航空公司：</label></td>
					                     <td><select id="aircom" name="aircom" disabled="disabled" class="form-control input-sm"  multiple="multiple" placeholder="" ></select></td>
					                     <td><label>航班号：</label></td>
					                     <td><select id="ailinenum" name="ailinenum" disabled="disabled" class="form-control input-sm"  multiple="multiple" placeholder="SYD(悉尼)"></select></td>
					                     <td><label>出发时间：</label></td>
					                     <td><input id="leavetime" name="leavetime" disabled="disabled" type="text" class="form-control input-sm textWid mustTimes" placeholder=""/></td>
					                     <td><label>抵达时间：</label></td>
					                     <td><input id="arrivetime" name="arrivetime" disabled="disabled" type="text" class="form-control input-sm textWid mustArriveTimes" /></td>
					                     <td><label class="labelWid">成本价：</label></td>
					                     <td><input id="formprice" name="formprice" disabled="disabled" type="text" class="form-control input-sm textWid mustNumberPoint costPrice" /></td>
					                     <td><label class="labelWid">销售价：</label></td>
					                     <td><input id="price" name="price" type="text" disabled="disabled" class="form-control input-sm textWid mustNumberPoint xiaoShouCls"/>
					                     	 <input type="hidden" id="ispriceempty" name="ispriceempty">
					                     </td>
					                     <td class="tdBtn">
					                      <a href="javascript:;" name="addButton" class="glyphicon glyphicon-plus addIcon removAddMake none"></a>
					                     </td>
					                   </tr>
			                   		</c:otherwise>
			                   </c:choose>
			               <tr>
		                     <td><label>平均汇率：</label></td>
		                     <td><input id="avgexrate" name="avgexrate" disabled="disabled" type="text" class="form-control input-sm mustNumberPoint textWid" value="${customneed.cusinfo.avgexrate }"/></td>
		                     <td><label>实时汇率：</label></td>
		                     <td><input id="realtimexrate" name="realtimexrate" disabled="disabled" type="text" class="form-control input-sm mustNumberPoint textWid" value="${customneed.cusinfo.realtimexrate }"/>
			                 </td>
		                     <td><label>币种：</label></td>
		                     <td><select id="paycurrency" name="paycurrency" disabled="disabled" class="form-control input-sm">
		                     		<option value="">请选择</option>
		                            <c:forEach items="${obj.bzcode }" var="one"> 
		                        	<c:choose>
		                        		<c:when test="${customneed.cusinfo.paycurrency eq one.dictCode }">
						                     <option value="${one.dictCode }" selected="selected">${one.dictCode }</option>
		                        		</c:when>
		                        		<c:otherwise>
						                     <option value="${one.dictCode }">${one.dictCode }</option>
		                        		</c:otherwise>
		                        	</c:choose>
		                     	</c:forEach>
		                        </select>
		                     </td>
		                     <td><label>付款方式：</label></td>
		                     <td colspan="1"> 
								<select id="paymethod" name="paymethod" disabled="disabled" class="form-control input-sm paymethod">
		                            <option value="">请选择</option>
		                            <c:forEach var="map" items="${obj.paymethod}" >
		                            	<c:choose>
		                            		<c:when test="${customneed.cusinfo.paymethod eq map.id }">
		                            			<option value="${map.id}" selected="selected">${map.cardName}</option>
		                            		</c:when>
		                            		<c:otherwise>
										   		<option value="${map.id}">${map.cardName}</option>
		                            		</c:otherwise>
		                            	</c:choose>
									 </c:forEach>
		                        </select>
							 </td>
							 <td name="internationalcard" colspan="2" style="display: none;" align="left"><label>余额：</label></td>
							 <td name="threepaytd"></td>
							 <td name="threepaymethod" style="display: none;"><select name="thirdcustomid" disabled="disabled" class="form-control input-sm"></select></td>
		                   </tr>
		                   <tr class="pnrTr">
		                     <td colspan="13" class="addPNR">
		                        <table class="table table-bordered table-hover">
		                         <thead>
		                          <tr>
		                            <td>PNR</td>
		                            <td>成本单价</td>
		                            <td>成本总价</td>
		                            <td>销售单价</td>
		                            <td>销售总价</td>
		                            <td>人数</td>
		                            <td>登录帐号</td>
		                            <td>操作</td>
		                          </tr>
		                         </thead>
		                         <tbody id="pnrinfodata" name="pnrinfodata">
		                         </tbody>
		                        </table>
		                        <div class="PNRbtnTD none">
		                        	<a href="javascript:;" class="btn btn-primary btn-sm PNRbtn"><b>+</b>&nbsp;PNR</a>
		                     	</div>
		                     </td>
		                   </tr>
		                   <%-- <tr class="remarkTr">
		                     <td></span><label>备注：</label></td>
		                     <td colspan="11"><input type="text" id="remark" name="remark" disabled="disabled" class="form-control input-sm noteText" placeholder=" " value="${customneed.cusinfo.remark }"></td>
		                   </tr> --%>
		                 </table>
		                </div>
		               </div>
		               </c:forEach>
               		</c:when>
               		<c:otherwise>
               			<div id="infofooter" class="infofooter">
		                <div class="DemandDiv">
		                 <span class="titleNum">1</span>
		                 <a href="${base }/admin/inland/downloadVisitorTemplate.html" class="btn btn-primary btn-sm addDemand none" target="hidden_frame">游客模板</a>
		                 <!-- <a href="javascript:;" class="btn btn-primary btn-sm addDemand none">上传游客</a> -->
		                 <form id="uploadExcelForm" action="${base}/admin/inland/importVisitor.html?dingdanid=${obj.orderinfo.id }" name="form3" enctype="multipart/form-data" method="post" target="hidden_frame" style="display: inline;">
		                     <p class="flie_A btn btn-primary btn-sm addDemand none scykP">上传游客<input name="excelFile" id="excelFile" onchange="javascript:onfileChange();" type="file"/></p>
						</form>
		                 <a href="javascript:;" class="btn btn-primary btn-sm addDemand none addXuQiu"><b>+</b>&nbsp;&nbsp;需求</a>
		                 <input type="hidden" id="customneedid" name="customneedid">
		                 <table class="cloTable">
		                   <tr>
		                     <td><label>出发城市：</label></td>
		                     <td><select id="leavecity" name="leavecity" disabled="disabled" class="form-control input-sm select2" multiple="multiple" placeholder="PEK(北京)">
			                     </select>
			                 </td>
		                     <td><label>抵达城市：</label></td>
		                     <td><select id="arrivecity" name="arrivecity" disabled="disabled" class="form-control input-sm" multiple="multiple" placeholder="SYD(悉尼)">
			                     </select></td>
		                     <td><label>出发日期：</label></td>
		                     <td><input id="leavedate" name="leavedate" disabled="disabled" type="text" class="form-control input-sm textWid" placeholder="2017-02-22" onFocus="WdatePicker({minDate:''})"/></td>
		                     <td><label>人数：</label></td>
		                     <td><input id="peoplecount" name="peoplecount" disabled="disabled" type="text" class="form-control input-sm textWid mustNumber"/></td>
		                     <td><label class="labelWid">早中晚：</label></td>
		                     <td>
		                       <select id="tickettype" name="tickettype" disabled="disabled" class="form-control input-sm textWid">
			                         <option value="1">早</option>
			                         <option value="2">中</option>
			                         <option value="3">晚</option>
			                       </select>
		                     </td>
		                   </tr>
		                   <tr name="airlineinfo" class="airlineinfo">
		                     <td></span><label>航空公司：</label></td>
		                     <td><select id="aircom" name="aircom" disabled="disabled" class="form-control input-sm"  multiple="multiple" placeholder="" ></select></td>
		                     <td><label>航班号：</label></td>
		                     <td><select id="ailinenum" name="ailinenum" disabled="disabled" class="form-control input-sm"  multiple="multiple" placeholder="SYD(悉尼)"></select></td>
		                     <td><label>出发时间：</label></td>
		                     <td><input id="leavetime" name="leavetime" disabled="disabled" type="text" class="form-control input-sm textWid mustTimes" placeholder=""/></td>
		                     <td><label>抵达时间：</label></td>
		                     <td><input id="arrivetime" name="arrivetime" disabled="disabled" type="text" class="form-control input-sm textWid mustArriveTimes" /></td>
		                     <td><label class="labelWid">成本价：</label></td>
		                     <td><input id="formprice" name="formprice" disabled="disabled" type="text" class="form-control input-sm textWid mustNumberPoint costPrice" /></td>
		                     <td><label class="labelWid">销售价：</label></td>
		                     <td><input id="price" name="price" type="text" disabled="disabled" class="form-control input-sm textWid mustNumberPoint xiaoShouCls"/>
		                     	 <input type="hidden" id="ispriceempty" name="ispriceempty">
		                     </td>
		                     <td class="tdBtn">
		                      <a href="javascript:;" name="addButton" class="glyphicon glyphicon-plus addIcon removAddMake none"></a>
		                     </td>
		                   </tr>
			               <tr>
		                     <td><label>平均汇率：</label></td>
		                     <td><input id="avgexrate" name="avgexrate" disabled="disabled" type="text" class="form-control input-sm mustNumberPoint textWid"/></td>
		                     <td><label>实时汇率：</label></td>
		                     <td><input id="realtimexrate" name="realtimexrate" disabled="disabled" type="text" class="form-control input-sm mustNumberPoint textWid"/>
			                 </td>
		                     <td><label>币种：</label></td>
		                     <td><select id="paycurrency" name="paycurrency" disabled="disabled" class="form-control input-sm">
		                     		<c:forEach items="${obj.bzcode }" var="one"> 
					                     <option value="${one.dictCode }">${one.dictCode }</option>
			                     	</c:forEach>
		                        </select>
		                     </td>
		                     <td><label>付款方式：</label></td>
		                     <td colspan="1">
								<select id="paymethod" name="paymethod" disabled="disabled" class="form-control input-sm paymethod">
		                            <option value="">请选择</option>
		                            <c:forEach var="map" items="${obj.paymethod}" >
								   		<option value="${map.id}">${map.cardName}</option>
									 </c:forEach>
		                        </select>
							 </td>
							 <td name="internationalcard" colspan="2" style="display: none;" align="left"><label>余额：</label></td>
							 <td name="threepaytd"></td>
							 <td name="threepaymethod" style="display: none;"><select name="thirdcustomid" disabled="disabled" class="form-control input-sm"></select></td>
		                   </tr>
		                   <tr>
		                     <td colspan="13" class="addPNR">
		                        <table class="table table-bordered table-hover">
		                         <thead>
		                          <tr>
		                            <td>PNR</td>
		                            <td>成本单价</td>
		                            <td>成本总价</td>
		                            <td>销售单价</td>
		                            <td>销售总价</td>
		                            <td>人数</td>
		                            <td>登录帐号</td>
		                            <td>操作</td>
		                          </tr>
		                         </thead>
		                         <tbody id="pnrinfodata" name="pnrinfodata">
		                         </tbody>
		                        </table>
		                        <div class="PNRbtnTD none">
			                        <a href="javascript:;" class="btn btn-primary btn-sm PNRbtn"><b>+</b>&nbsp;&nbsp;PNR</a>
			                    </div>
		                     </td>
		                   </tr>
		                   <%-- <tr>
		                     <td></span><label>备注：</label></td>
		                     <td colspan="11"><input type="text" id="remark" name="remark" disabled="disabled" class="form-control input-sm noteText" placeholder=" " value="${customneed.cusinfo.remark }"></td>
		                   </tr> --%>
		                 </table>
		                </div>
		               </div>
               		</c:otherwise>
               </c:choose>
               <context class="remarkContext">
				   <div class="remarkDiv">
						<table class="remarkTable">
							<tr name="cRemarkTr" class="remarkTr">
								<td><label>备注：</label></td>
								<td>
									<textarea class="form-control" id="cRemark" name="cRemark" disabled="disabled">${obj.orderinfo.remark }</textarea>
								</td>
							</tr>
						</table>
				   </div>	
				</context>
          </div><!--end 客户需求-->

          <div class="customerInfo"><!--信息-->
            <div class="infoTop">
                 <p>信息</p>
            </div>
            <div class="infofooter">
            	<form id="financeForm">
            		<input id="id" name="id" type="hidden" value="${obj.finance.id }" >
            		<input id="orderid" name="orderid" type="hidden" value="${obj.orderinfo.id }"/>
                 <table>
                   <tr>
                     <td><label>客户团号：</label></td>
                     <td><input id="cusgroupnum" name="cusgroupnum" type="text" class="form-control input-sm disab" disabled="disabled" value="${obj.finance.cusgroupnum }"></td>
                     <td><label>类型：</label></td>
                     <td>
                        <select id="teamtype" name="teamtype" class="form-control input-sm disab" disabled="disabled">
                            <option value="">请选择</option>
                            <c:forEach var="map" items="${obj.passengertypeenum }" >
                            	<c:choose>
	                            	<c:when test="${obj.finance.teamtype eq map.key}">
			                            <option value="${map.key}" selected="selected">${map.value}</option>
	                            	</c:when>
	                            	<c:otherwise>
								   		<option value="${map.key}">${map.value}</option>
	                            	</c:otherwise>
	                            </c:choose>
							 </c:forEach>
                        </select>
                     </td>
                     <td><label>内陆跨海：</label></td>
                     <td>
                        <select id="neilu" name="neilu" class="form-control input-sm disab" disabled="disabled">
                            <option value="">请选择</option>
                            <c:forEach items="${obj.nlkhcode }" var="one"> 
	                        	<c:choose>
	                        		<c:when test="${obj.finance.neilu eq one.id }">
					                     <option value="${one.id }" selected="selected">${one.dictName }</option>
	                        		</c:when>
	                        		<c:otherwise>
					                     <option value="${one.id }">${one.dictName }</option>
	                        		</c:otherwise>
	                        	</c:choose>
	                     	</c:forEach>
                        </select>
                     </td>
                     <td><label>开票日期：</label></td>
                     <td><input id="billingdate" name="billingdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" type="text" class="form-control input-sm" value="<fmt:formatDate value="${obj.finance.billingdate }" pattern="yyyy-MM-dd" />" disabled="disabled"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>销售：</label></td>
                     <td><input id="salesperson" name="salesperson" value="候小凌" type="text" class="form-control input-sm" disabled="disabled"></td>
                     <td><label>开票人：</label></td>
                     <td><input id="issuer" name="issuer" type="text" value="${empty obj.finance.issuer?obj.user.fullName:obj.finance.issuer }" class="form-control input-sm" disabled="disabled"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>人头数：</label></td>
                     <td><input id="personcount" name="personcount" value="${obj.finance.personcount }" type="text" class="form-control input-sm disab mustNumber" disabled="disabled"></td>
                     <td><label>是否结算：</label></td>
                     <td>
                        <select id="billingstatus" name="billingstatus" class="form-control input-sm disab" disabled="disabled">
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                     </td>
                     <%-- <td><label>进澳时间：</label></td>
                     <td><input id="enterausdate" name="enterausdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${obj.finance.enterausdate }" pattern="yyyy-MM-dd" />" type="text" class="form-control input-sm disab" disabled="disabled"></td>
                     <td><label>出澳时间：</label></td>
                     <td><input id="outausdate" name="outausdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${obj.finance.outausdate }" pattern="yyyy-MM-dd" />" type="text" class="form-control input-sm disab" disabled="disabled"></td> --%>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>进澳时间：</label></td>
                     <td><input id="enterausdate" name="enterausdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${obj.finance.enterausdate }" pattern="yyyy-MM-dd" />" type="text" class="form-control input-sm disab" disabled="disabled"></td>
                   	 <td><label>航空公司：</label></td>
                     <td><select id="enteraircom" name="enteraircom" type="text" class="form-control input-sm disab aircomselect" disabled="disabled" multiple="multiple">
                     	<c:forEach items="${obj.aircom }" var="one"> 
                   			<c:choose>
	                   			<c:when test="${obj.finance.enteraircom  eq one.dictCode  }">
									<option value="${one.dictCode }" selected="selected">${one.dictCode }-${one.dictName }</option>
	                   			</c:when>
	                   			<c:otherwise>
		                     		<option value="${one.dictCode }">${one.dictCode }-${one.dictName }</option>
	                   			</c:otherwise>
                    		</c:choose>
                     	</c:forEach>
                     </select></td>
                     <td><label>出发时间：</label></td>
                     <td><input id="enterstarttime" name="enterstarttime" value="${obj.finance.enterstarttime }" type="text" class="form-control input-sm disab mustTimes" disabled="disabled"></td>
                     <td><label>抵达时间：</label></td>
                     <td><input id="enterarrivetime" name="enterarrivetime" value="${obj.finance.enterarrivetime }" type="text" class="form-control input-sm disab mustArriveTimes" disabled="disabled"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>出澳时间：</label></td>
                     <td><input id="outausdate" name="outausdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${obj.finance.outausdate }" pattern="yyyy-MM-dd" />" type="text" class="form-control input-sm disab" disabled="disabled"></td>
                   	 <td><label>航空公司：</label></td>
                     <td><select id="outaircom" name="outaircom" type="text" class="form-control input-sm disab aircomselect" disabled="disabled" multiple="multiple">
                     	<c:forEach items="${obj.aircom }" var="one"> 
                   			<c:choose>
	                   			<c:when test="${obj.finance.outaircom  eq one.dictCode  }">
									<option value="${one.dictCode }" selected="selected">${one.dictCode }-${one.dictName }</option>
	                   			</c:when>
	                   			<c:otherwise>
		                     		<option value="${one.dictCode }">${one.dictCode }-${one.dictName }</option>
	                   			</c:otherwise>
                    		</c:choose>
                     	</c:forEach>
                     </select></td>
                     <td><label>出发时间：</label></td>
                     <td><input id="outstarttime" name="outstarttime" value="${obj.finance.outstarttime }" type="text" class="form-control input-sm disab mustTimes" disabled="disabled"></td>
                     <td><label>抵达时间：</label></td>
                     <td><input id="outarrivetime" name="outarrivetime" value="${obj.finance.outarrivetime }" type="text" class="form-control input-sm disab mustArriveTimes" disabled="disabled"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>应收：</label></td>
                     <td><input id="receivable" name="receivable" type="text" class="form-control input-sm disab mustNumberPoint" value="<fmt:formatNumber type="number" value="${obj.finance.receivable }" pattern="0.00" maxFractionDigits="2"/>" disabled="disabled"></td>
                     <td><label><a href="javascript:;" class="" id="jianMian" disabled="disabled">减免</a>：</label></td>
                     <td><input id="relief" name="relief" type="text" class="form-control input-sm" disabled="disabled" value="<fmt:formatNumber type="number" value="${obj.finance.relief }" pattern="0.00" maxFractionDigits="2"/>"></td>
                     <td><label>实收合计：</label></td>
                     <td><input id="incometotal" name="incometotal" type="text" class="form-control input-sm loadprofit mustNumberPoint" disabled="disabled" value="<fmt:formatNumber type="number" value="${obj.finance.incometotal }" pattern="0.00" maxFractionDigits="2"/>"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>成本合计：</label></td>
                     <td><input id="costtotal" name="costtotal" type="text" class="form-control input-sm disab loadprofit mustNumberPoint" disabled="disabled" value="<fmt:formatNumber type="number" value="${obj.finance.costtotal }" pattern="0.00" maxFractionDigits="2"/>"></td>
                     <td><label>应返：</label></td>
                     <td><input id="returntotal" name="returntotal" type="text" class="form-control input-sm disab loadprofit mustNumberPoint" disabled="disabled" value="<fmt:formatNumber type="number" value="${obj.finance.returntotal }" pattern="0.00" maxFractionDigits="2"/>"></td>
                     <td><label>利润合计：</label></td>
                     <td><input id="profittotal" name="profittotal" type="text" class="form-control input-sm mustNumberPoint" disabled="disabled" value="<fmt:formatNumber type="number" value="${obj.finance.profittotal }" pattern="0.00" maxFractionDigits="2"/>"></td>
                   </tr>
                 </table>
                </form>
            </div>
          </div><!--end 信息-->
          
        </div>
        <div class="col-sm-2 rightRemind">
            <div class="infoTop">
              <p>提醒设置</p>
            </div>
            <div class="infofooter">
                 <table class="remindSet">
                   <tr>
                     <td><input id="remindTime" disabled="disabled" type="text" class="form-control input-sm" placeholder="2020-01-01 00:00:00" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value="${obj.orderinfo.remindTime }" pattern="yyyy-MM-dd HH:mm:ss" />"/></td>
                     <td>
                       <select id="remindType" disabled="disabled" class="form-control input-sm">
                         <c:forEach var="map" items="${obj.orderRemindEnum}" >
                         	<c:choose>
                         		<c:when test="${map.key eq obj.orderinfo.remindType }">
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
                 </table>
            </div>

            <div class="infoTop">
              <p>日志</p>
            </div>
            <div class="infofooter" id="orderlog">
                 
            </div>
        </div>
    </section>
  </div>
  <!--end right Content-->

  <!--footer-->
  <footer class="main-footer">
    <div class="pull-right hidden-xs">
    </div>
    <strong>版权 &copy; 2016 <a href="#">聚优国际旅行社（北京）有限公司</a>.</strong> 保留所有权利.
  </footer>
  <!--end footer-->
  <script type="text/javascript">
	 var BASE_PATH = '${base}';
	 var creditLine = '${obj.custominfo.creditLine}';
	 var arrears = '${obj.historymony}';
  </script>
  <!--Javascript Flie-->
  <script src="${base }/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
  <script src="${base }/public/bootstrap/js/bootstrap.min.js"></script>
  <script src="${base }/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
  <script src="${base }/public/plugins/fastclick/fastclick.js"></script>
  <!--layer -->
  <script src="${base}/common/js/layer/layer.js"></script>
  <!-- select2 -->
  <script src="${base}/public/plugins/select2/select2.full.min.js"></script>
  <script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
  <!-- DataTables -->
  <script src="${base }/public/plugins/datatables/jquery.dataTables.min.js"></script>
  <script src="${base }/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
  <!-- My97DatePicker --> 
  <script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
  <script src="${base }/admin/order/bookingorder.js"></script>
  <script src="${base }/admin/order/ordercommon.js"></script>
  <script type="text/javascript">
    $(function(){
        //编辑按钮 click事件
        $('.editBtn').click(function(){
          $(this).addClass('none');
          $('.btnSave').toggle();//保存按钮 显示
          $('.btnCancel').toggle();//取消按钮 显示
          if($(".addDemand").hasClass("sw")){//显示 +需求 按钮
            $('.addDemand').removeClass('sw');
          }else{
            $('.addDemand').addClass('sw');
           }
          $('.removeDemand').removeClass('none');//显示 -需求 按钮
          $('.removAddMake').toggle();//显示 圆圈+ 按钮
          $('.removIcon').toggle();//显示 圆圈- 按钮
          $(".listInfo").toggle();//选项卡 显示
          $('.remindSet tbody tr td input').removeAttr("disabled");//删除 提醒设置 input 禁止编辑的状态
          $('.disab').removeAttr("disabled");//信息模块 input 禁止编辑的状态
          $('.PNRbtnTD').removeClass('none');//+PNR 按钮 显示
          $('#orderType').removeAttr("disabled");//订单状态禁止编辑的状态
          $('#remindTime').removeAttr("disabled");//订单状态禁止编辑的状态
          $('#remindType').removeAttr("disabled");//订单状态禁止编辑的状态
          $('#cRemark').removeAttr("disabled");//订单状态禁止编辑的状态
          $('#jianMian').addClass("jianMian");//减免禁止编辑的状态
          //页面不可编辑
          $('.DemandDiv').each(function(i){
        	  $(this).find('[name=leavecity]').removeAttr('disabled');
        	  $(this).find('[name=arrivecity]').removeAttr('disabled');
              $(this).find('[name=leavedate]').removeAttr('disabled');
              $(this).find('[name=peoplecount]').removeAttr('disabled');
              $(this).find('[name=tickettype]').removeAttr('disabled');
              $(this).find('[name=realtimexrate]').removeAttr('disabled');
              $(this).find('[name=avgexrate]').removeAttr('disabled');
              $(this).find('[name=paycurrency]').removeAttr('disabled');
              $(this).find('[name=paymethod]').removeAttr('disabled');
              $(this).find('[name=thirdcustomid]').removeAttr('disabled');
              $(this).find('[name=remark]').removeAttr('disabled');
              $(this).find('[name=airlineinfo]').each(function(i){
            	$(this).find('[name=airlineid]').removeAttr('disabled');
          		$(this).find('[name=aircom]').removeAttr('disabled');
          		$(this).find('[name=ailinenum]').removeAttr('disabled');
          		$(this).find('[name=leavetime]').removeAttr('disabled');
          		$(this).find('[name=arrivetime]').removeAttr('disabled');
          		$(this).find('[name=formprice]').removeAttr('disabled');
          		$(this).find('[name=price]').removeAttr('disabled');
              });
          });
          loadPNRdata();
        });
        //取消按钮 click事件
        $('.btnCancel').click(function(){
          $('.editBtn').removeClass('none');//显示 编辑 按钮
          $('.btnSave').toggle();//保存 按钮 隐藏
          $('.btnCancel').toggle();//取消 按钮 隐藏
          $('.addDemand').removeClass('sw');//+需求 隐藏
          $('.removeDemand').addClass('none');//-需求 隐藏
          $('.removAddMake').toggle();//圆圈+ 按钮 隐藏
          $('.removIcon').toggle();//圆圈- 按钮 隐藏
          $(".listInfo").toggle();//选项卡 隐藏
          $('.remindSet tbody tr td input').attr("disabled",'disabled');//提醒设置 input 添加 不可编辑属性
          $('.disab').attr("disabled",'disabled');//信息模块 input 添加 不可编辑属性
          $('.PNRbtnTD').addClass('none');//+PNR 按钮 隐藏
          $('#orderType').attr("disabled",'disabled');//订单状态添加 不可编辑属性
          $('#remindTime').attr("disabled",'disabled');//提醒时间添加 不可编辑属性
          $('#remindType').attr("disabled",'disabled');//提醒状态添加 不可编辑属性
          $('#cRemark').attr("disabled",'disabled');//提醒状态添加 不可编辑属性
          $('#jianMian').removeClass("jianMian");//减免添加 不可编辑属性
          //页面可以编辑
          $('.DemandDiv').each(function(i){
        	  var customneedid = $(this).find('[name=customneedid]').val();
        	  if(!customneedid){
        		  $(this).remove();
        	  }else{
	        	  $(this).find('[name=leavecity]').attr('disabled','disabled');
	        	  $(this).find('[name=arrivecity]').attr('disabled','disabled');
	              $(this).find('[name=leavedate]').attr('disabled','disabled');
	              $(this).find('[name=peoplecount]').attr('disabled','disabled');
	              $(this).find('[name=tickettype]').attr('disabled','disabled');
	              $(this).find('[name=realtimexrate]').attr('disabled','disabled');
	              $(this).find('[name=avgexrate]').attr('disabled','disabled');
	              $(this).find('[name=paycurrency]').attr('disabled','disabled');
	              $(this).find('[name=paymethod]').attr('disabled','disabled');
	              $(this).find('[name=thirdcustomid]').attr('disabled','disabled');
	              $(this).find('[name=remark]').attr('disabled','disabled');
	              $(this).find('[name=airlineinfo]').each(function(i){
	            	$(this).find('[name=airlineid]').attr('disabled','disabled');
	          		$(this).find('[name=aircom]').attr('disabled','disabled');
	          		$(this).find('[name=ailinenum]').attr('disabled','disabled');
	          		$(this).find('[name=leavetime]').attr('disabled','disabled');
	          		$(this).find('[name=arrivetime]').attr('disabled','disabled');
	          		$(this).find('[name=formprice]').attr('disabled','disabled');
	          		$(this).find('[name=price]').attr('disabled','disabled');
	              });
        	  }
          });
          loadPNRdata(1);
        });

        $('.clearBtn').click(function(){//清楚按钮 隐藏
          $('.hideTable').hide('400');
        });

        $('.paragraphBtn li').click(function(){//段数 样式切换
          $(this).addClass('btnStyle').siblings().removeClass('btnStyle');
        });

        //添加 .addMore
        $('.addMore').click(function(){
            var divTest = $(this).parent().parent(); 
            var newDiv = divTest.clone(true);
            divTest.after(newDiv);
            var No = parseInt(divTest.find("p").html())+1;//假设你用p标签显示序号
            newDiv.find("p").html(No); 
            newDiv.find('.addIconTd').remove();
            newDiv.append('<td class="removeIconTd"><i class="glyphicon glyphicon-minus removeMore"></i></td>');
        });
        //删除 .addMore
        $(document).on('click','.removeMore',function(){
            $(this).parent().parent().remove(); 
        });

        //点击 减免 弹框
        $(document).on("click",".jianMian",function(){
            layer.open({
                type: 2,
                title:false,
                skin: false, //加上边框
                closeBtn:false,//默认 右上角关闭按钮 是否显示
                shadeClose:true,
                area: ['770px', '240px'],
                content: '${base}/admin/inland/mitigate.html?id=${obj.orderinfo.id }&customeid=${obj.custominfo.id }',
                end:function(){
                	loadJianMianAccount('${obj.orderinfo.id }');
                }
              });
        });
    });
  </script>	

  <script type="text/javascript">
//保存订单
  $('.btnSave').click(function(){
  	//var data = [];
		var customdata = {};
		var customerId = $('#customerId').val();
		customdata.customerId = customerId;
		var id = $('#orderedid').val();
		customdata.id = id;
		var cRemark = $('#cRemark').val();
		customdata.remark = cRemark;
		var remindTime = $('#remindTime').val();
		customdata.remindTime = remindTime;
		var remindType = $('#remindType').val();
		customdata.remindType = remindType;
		var orderType = $('#orderType').val();
		customdata.orderType = orderType;
		var row = [];
		$('.DemandDiv').each(function(i){
			//临时变量判断客户需求是否为空
			var lenthcustom = '';
			var row1 = {};
			var leavecity = $(this).find('[name=leavecity]').val();
			//出发城市
			if (leavecity) {
				leavecity = leavecity.join(',');
				lenthcustom += leavecity;
			}else{
				lenthcustom += '';
			}
			row1.leavecity = leavecity;
			//抵达城市
			var arrivecity = $(this).find('[name=arrivecity]').val();
			if (arrivecity) {
				arrivecity = arrivecity.join(',');
				lenthcustom += arrivecity;
			}else{
				lenthcustom += '';
			}
			row1.arrivecity = arrivecity;
			row1.customneedid = $(this).find('[name=customneedid]').val();
			row1.leavedate = $(this).find('[name=leavedate]').val();
			row1.peoplecount = $(this).find('[name=peoplecount]').val();
			row1.tickettype = $(this).find('[name=tickettype]').val();
			row1.realtimexrate = $(this).find('[name=realtimexrate]').val();
			row1.avgexrate = $(this).find('[name=avgexrate]').val();
			row1.paycurrency = $(this).find('[name=paycurrency]').val();
			row1.paymethod = $(this).find('[name=paymethod]').val();
			row1.thirdcustomid = $(this).find('[name=thirdcustomid]').val();
			row1.remark = $(this).find('[name=remark]').val();
			lenthcustom += $(this).find('[name=customneedid]').val();
			lenthcustom += $(this).find('[name=leavedate]').val();
			lenthcustom += $(this).find('[name=peoplecount]').val();
			lenthcustom += $(this).find('[name=realtimexrate]').val();
			lenthcustom += $(this).find('[name=avgexrate]').val();
			lenthcustom += $(this).find('[name=paycurrency]').val();
			lenthcustom += $(this).find('[name=paymethod]').val();
			lenthcustom += $(this).find('[name=remark]').val();
			var airrows = [];
			$(this).find('[name=airlineinfo]').each(function(i){
				var lengthAir = '';
				var airrow = {};
				var aircom = $(this).find('[name=aircom]').val();
				if (aircom) {
					aircom = aircom.join(',');
					lengthAir += aircom;
	  			}else{
	  				lengthAir += ''
	  			}
				airrow.aircom = aircom;
				var ailinenum = $(this).find('[name=ailinenum]').val();
				if (ailinenum) {
					ailinenum = ailinenum.join(',');
					lengthAir += ailinenum;
	  			}else{
	  				lengthAir += '';
	  			}
				airrow.ailinenum = ailinenum;
				airrow.airlineid = $(this).find('[name=airlineid]').val();
				airrow.leavetime = $(this).find('[name=leavetime]').val();
				airrow.arrivetime = $(this).find('[name=arrivetime]').val();
				airrow.formprice = $(this).find('[name=formprice]').val();
				airrow.price = $(this).find('[name=price]').val();
				lengthAir += $(this).find('[name=leavetime]').val();
				lengthAir += $(this).find('[name=arrivetime]').val();
				lengthAir += $(this).find('[name=formprice]').val(); 
				lengthAir += $(this).find('[name=price]').val();
				if(lengthAir.length > 0){
					airrows.push(airrow);
				}
				lenthcustom += lengthAir;
			});
			row1.airinfo = airrows;
			if(lenthcustom.length > 0){
				row.push(row1);
			}
		});
		customdata.customdata=row;
		//data.push(customdata);
		//alert(JSON.stringify(data));
		console.log(JSON.stringify(customdata));
		layer.load(1);
		//开票日期
		var billingdate = $('#billingdate').val();
		//销售
		var salesperson = $('#salesperson').val();
		//开票人
		var issuer = $('#issuer').val();
		//减免
		var relief = $('#relief').val();
		//实收合计
		var incometotal = $('#incometotal').val();
		//利润合计
		var profittotal = $('#profittotal').val();
		//进澳时间航公公司
		var enteraircom = $('#enteraircom').val();
		if (enteraircom) {
			enteraircom = enteraircom.join(',');
		}
		//出澳时间航空公司
		var outaircom = $('#outaircom').val();
		if (outaircom) {
			outaircom = outaircom.join(',');
		}
		var financeForm = getFormJson('#financeForm');
		financeForm.billingdate = billingdate;
		financeForm.salesperson = salesperson;
		financeForm.issuer = issuer;
		financeForm.relief = relief;
		financeForm.incometotal = incometotal;
		financeForm.profittotal = profittotal;
		financeForm.enteraircom = enteraircom;
		financeForm.outaircom = outaircom;
		$.ajax({ 
			type: 'POST', 
			data: {data:JSON.stringify(customdata),financeData:JSON.stringify(financeForm)}, 
			url: '${base}/admin/inland/saveBookingOrderInfo.html',
          success: function (data) { 
          	//alert("添加成功");
          	//location.reload();
          	layer.closeAll('loading');
          	window.location.reload();
          	//layer.msg("保存成功",{time: 2000, icon:1});
          },
          error: function (xhr) {
          	layer.msg("保存失败","",3000);
          } 
      });
  }); 
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
  //选中后开始导入
  function onfileChange() {
	   uploadfile();
  }
  //导入Excel
  function uploadfile() {
  		var filepath = document.getElementById("excelFile").value;
  		var extStart = filepath.lastIndexOf(".");
  		var ext = filepath.substring(extStart, filepath.length).toUpperCase();
  		if (ext != ".XLS" && ext != ".XLSX") {
  			layer.alert("请选择正确的Excel文件");
  			return;
  		}
  		document.getElementById("uploadExcelForm").submit();
  		layer.load(1);
  	}
  	
  function callback(){
	  layer.alert("导入成功",{time: 2000, icon:1});
	  layer.closeAll('loading');
  }
  //打开添加pnr页面
  	$(document).on("click",".PNRbtn",function(){
  		var xuqiuDiv = $(this).parent().parent().parent().parent().parent().parent();
		  var needid = xuqiuDiv.find('[name=customneedid]').val();
		  //先保存需求信息
		 if(!needid){
			var row1 = {};
			var leavecity = xuqiuDiv.find('[name=leavecity]').val();
			//出发城市
			if (leavecity) {
				leavecity = leavecity.join(',');
			}
			row1.leavecity = leavecity;
			//抵达城市
			var arrivecity = xuqiuDiv.find('[name=arrivecity]').val();
			if (arrivecity) {
				arrivecity = arrivecity.join(',');
			}
			row1.arrivecity = arrivecity;
			row1.orderid = '${obj.orderinfo.id }';
			row1.customneedid = xuqiuDiv.find('[name=customneedid]').val();
			row1.leavedate = xuqiuDiv.find('[name=leavedate]').val();
			row1.peoplecount = xuqiuDiv.find('[name=peoplecount]').val();
			row1.tickettype = xuqiuDiv.find('[name=tickettype]').val();
			row1.realtimexrate = xuqiuDiv.find('[name=realtimexrate]').val();
			row1.avgexrate = xuqiuDiv.find('[name=avgexrate]').val();
			row1.paycurrency = xuqiuDiv.find('[name=paycurrency]').val();
			row1.paymethod = xuqiuDiv.find('[name=paymethod]').val();
			row1.remark = xuqiuDiv.find('[name=remark]').val();
			var airrows = [];
			$(this).find('[name=airlineinfo]').each(function(i){
				var airrow = {};
				var aircom = $(this).find('[name=aircom]').val();
				if (aircom) {
					aircom = aircom.join(',');
	  			}
				airrow.aircom = aircom;
				var ailinenum = $(this).find('[name=ailinenum]').val();
				if (ailinenum) {
					ailinenum = ailinenum.join(',');
	  			}
				airrow.ailinenum = ailinenum;
				airrow.airlineid = $(this).find('[name=airlineid]').val();
				airrow.leavetime = $(this).find('[name=leavetime]').val();
				airrow.arrivetime = $(this).find('[name=arrivetime]').val();
				airrow.formprice = $(this).find('[name=formprice]').val();
				airrow.price = $(this).find('[name=price]').val();
				airrows.push(airrow);
			});
			row1.airinfo = airrows;
			//保存客户需求信息
			$.ajax({ 
				type: 'POST', 
				data: {data:JSON.stringify(row1)}, 
				url: '${base}/admin/inland/saveCustomeneedInfo.html',
	            success: function (data) { 
	            	xuqiuDiv.find('[name=customneedid]').val(data.id);
	            	layer.open({
	   		         type: 2,
	   		         title:false,
	   		         skin: false, //加上边框
	   		         closeBtn:false,//默认 右上角关闭按钮 是否显示
	   		         shadeClose:true,
	   		         scrollbar: false,
	   		         area: ['880px', '425px'],
	   		         content: '${base}/admin/inland/addPnr.html?dingdanid=${obj.orderinfo.id}&needid='+data.id,
	   		         end:function(){
	   		        	 //设置财务信息
	   		        	 setFinanceInfo();
	   		         }
	   		       });
		         },
		         error: function (xhr) {
		          	layer.msg("保存失败","",3000);
		         } 
	      });
		 }else{
			 needid = xuqiuDiv.find('[name=customneedid]').val();
			 layer.open({
		         type: 2,
		         title:false,
		         skin: false, //加上边框
		         closeBtn:false,//默认 右上角关闭按钮 是否显示
		         shadeClose:true,
		         scrollbar: false,
		         area: ['880px', '425px'],
		         content: '${base}/admin/inland/addPnr.html?dingdanid=${obj.orderinfo.id}&needid='+needid,
		         end:function(){
		        	 //设置财务信息
		        	 setFinanceInfo();
		         }
		       });
		 }
    });
  function setFinanceInfo(){
	//设置财务信息
 	 $.ajax({ 
			type: 'POST', 
			data: {orderid:'${obj.orderinfo.id }'}, 
			url: '${base}/admin/inland/setFinanceInfo.html',
          success: function (data) { 
          	//成本合计
          	$('#costtotal').val(data.chengbensum.toFixed(2));
          	//应收
          	$('#receivable').val(data.yingshousum.toFixed(2));
          	$('#personcount').val(data.renshusum);
          	var relief = $('#relief').val();
	       	 	var incometotal  = '';
	       	 	if(relief){
	       	 		incometotal  = parseFloat(data.yingshousum) - parseFloat(relief);
	       	 	}else{
	       	 		incometotal = data.yingshousum;
	       	 	}
	       	 	
	       	 	if(!isNaN(incometotal)){
     		 		$('#incometotal').val(incometotal.toFixed(2));
	       	 	}
	       	 	var returntotal = 0;
	 	       	//应返合计
	 	       	if($('#returntotal').val()){
		 	   	 	returntotal = $('#returntotal').val();
	 	       	}
	 	   	    //利润合计
	 	   	 	var profittotal  = parseFloat(incometotal) - parseFloat(data.chengbensum) - parseFloat(returntotal);
	 	   	 	if(!isNaN(profittotal)){
	 	   		 	$('#profittotal').val(profittotal.toFixed(2));
	 	   	 	}
	         },
	         error: function (xhr) {
	         } 
       });
  }
  //其他页面回调
 function successCallback(id){
	 loadPNRdata();
	  if(id == '1'){
		  layer.msg("添加成功",{time: 2000});
	  }else if(id == '2'){
		  layer.msg("修改成功",{time: 2000});
	  }else if(id == '3'){
		  layer.msg("删除成功",{time: 2000});
	  }else if(id == '4'){
		  layer.msg("提交成功",{time: 2000});
	  }
 }
  
//oninput事件
 $(document).on('input', '.costPrice', function(e) {
 	$(this).val($(this).val().replace(/[^.\d]/g,''));
 	//成本价
 	var fromprice = $(this).val();
 	//票价折扣
	var discountFare = 0;
	var countfare = '${obj.custominfo.discountFare}';
	if(countfare){
		discountFare = countfare;
	}
	//手续费
	var fees = 0;
	var feescount = '${obj.custominfo.fees}'; 
	if(feescount){
		fees = feescount;
	}
 	//alert("值："+fromprice + " 折扣："+discountFare + " 手续费：" + fees);
 	var price = parseFloat(fromprice * discountFare / 100) + parseFloat(fees);
 	//是否可以修改的标志	
 	var ispriceempty = $(this).parent().parent().find('[name=ispriceempty]').val();
 	if(ispriceempty){
	 	if(fromprice){
	 		if(isNaN(price) && price > 0){
	 			$(this).parent().parent().find('[name=price]').val('');
	 		}else{
		 		$(this).parent().parent().find('[name=price]').val(price.toFixed(2));
	 		}
	 	}else{
	 		$(this).parent().parent().find('[name=price]').val('');
	 	}
 	}
 	/* //成本合计
 	var sumformprice = 0;
 	//应收
 	var sumsale = 0;
 	$('.airlineinfo').each(function(i){
 		var chengben = $(this).find('[name=formprice]').val();
 		var xiaoshou = $(this).find('[name=price]').val();
 		if(chengben){
 			sumformprice = parseFloat(sumformprice) + parseFloat(chengben);
 		}
 		if(xiaoshou){
 			sumsale = parseFloat(sumsale) + parseFloat(xiaoshou);
 		}
 	});
 	//成本合计
 	if(sumformprice > 0){
	 	$('#costtotal').val(sumformprice);
 	}
 	//应收
 	if(sumsale > 0 ){
 		$('#receivable').val(sumsale);
 	} */
 });
 //销售价自动统计应收
 $(document).on('input', '.xiaoShouCls', function(e) {
	 var sumformprice = 0;
	 	var sumsale = 0;
	 	$('.airlineinfo').each(function(i){
	 		var xiaoshou = $(this).find('[name=price]').val();
	 		if(xiaoshou){
	 			sumsale = parseFloat(sumsale) + parseFloat(xiaoshou);
	 		}
	 	});
	 	//应收
	 	if(sumsale > 0 ){
	 		$('#receivable').val(sumsale.toFixed(2));
	 	}
	 });

 //应收自动加载实收合计
  $(document).on('input', '#receivable', function(e) {
	 	var yingshou = $(this).val();
	 	var relief = $('#relief').val();
	 	var incometotal  = '';
	 	if(relief){
	 		incometotal  = parseFloat(yingshou) - parseFloat(relief);
	 	}else{
	 		incometotal = yingshou;
	 	}
	 	if(!isNaN(incometotal)){
		 	$('#incometotal').val(incometotal.toFixed(2));
	 	}
	 });
 //自动加载利润合计
  $(document).on('input', '.loadprofit', function(e) {
	    //实收合计
	 	var incometotal = 0;
	    if($('#incometotal').val()){
	    	incometotal = $('#incometotal').val();
	    }
	    //成本合计
	 	var costtotal = 0;
	    if($('#costtotal').val()){
	    	costtotal = $('#costtotal').val();
	    }
	    //应返合计
	 	var returntotal = 0;
	    if($('#returntotal').val()){
	    	returntotal = $('#returntotal').val();
	    }
	    //利润合计
	 	var profittotal  = parseFloat(incometotal) - parseFloat(costtotal) - parseFloat(returntotal);
	 	if(!isNaN(profittotal)){
		 	$('#profittotal').val(profittotal.toFixed(2));
	 	}
	 });
 //加载日志
 loadOrderLog('${obj.orderinfo.id }');
 //加载减免信息
 loadJianMianAccount('${obj.orderinfo.id }');
 function loadJianMianAccount(orderid){
	 $.ajax({ 
			type: 'POST', 
			data: {orderid:orderid}, 
			dataType:'json',
			url: BASE_PATH + '/admin/inland/loadJianMianAccount.html',
         success: function (data) { 
        	 if(data.account){
	         	$('#relief').val(data.account.toFixed(2));
        	 }
         },
         error: function (xhr) {
       		
         } 
      });
 }
 //设置财务信息
 setFinanceInfo();
  </script>
</body>
</html>
