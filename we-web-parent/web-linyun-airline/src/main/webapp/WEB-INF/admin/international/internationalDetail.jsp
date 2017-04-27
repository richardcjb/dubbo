<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>国际订单详情</title>
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
  <link rel="stylesheet" href="${base }/public/dist/css/internationOrderDetail.css"><!--本页面styleFlie-->
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
        <div class="row col-sm-12 padding0">
          <div class="customerInfo"><!--客户信息-->
               <div class="infoTop">
                 <p>客户信息</p>
                 <div class="infoTopContent">
                   <span>${obj.orderinfo.ordersnum }</span>
                   <select id="orderType" name="orderType" disabled="disabled" class="form-control input-sm conSelect cf">
                   	 <c:forEach var="map" items="${obj.internationalstatusenum}" >
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
                   <button type="button" class="btn btn-primary input-sm btnRemind none">提醒设置</button>
                   <button type="button" class="btn btn-primary input-sm btnLog none">日志</button>
                   <button type="button" class="btn btn-primary input-sm btnSave none" onclick="saveInternationalDetail()">保存</button> 
                   <button type="button" class="btn btn-primary input-sm btnCancel none">取消</button>
                   <button type="button" class="btn btn-primary input-sm editBtn right">编辑</button>
                   <input id="mainaircount" name="mainaircount" type="hidden">
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


          <div class="customerInfo"><!--航程信息-->
               <div class="infoTop">
                 <p>航程信息</p>
               </div>
               <div class="infofooter">
                  <button type="button" class="btn btn-primary right addHD none">添加航段</button>
                  <table class="HCinfoInp">
                   <tr>
                     <td><label>航空公司：</label></td>
                     <td class="hkgsTd"><select id="airlinecom" name="airlinecom" disabled="disabled" class="form-control input-sm disab" multiple="multiple">
                     		<c:forEach items="${obj.aircomselect }" var="aircom">
                     			<c:choose>
                     				<c:when test="${obj.orderinfo.airlinecom eq aircom.dictCode}">
			                     		<option value="${aircom.dictCode }" selected="selected">${aircom.dictCode }-${aircom.dictName }</option>
                     				</c:when>
                     				<c:otherwise>
			                     		<option value="${aircom.dictCode }">${aircom.dictCode }-${aircom.dictName }</option>
                     				</c:otherwise>
                     			</c:choose>
                     		</c:forEach>
                     </select></td>
                     <td class="renshuTd"><label>人数：</label></td>
                     <td><input id="peoplecount" name="peoplecount" disabled="disabled" type="text" class="form-control input-sm disab mustNumber" value="${obj.orderinfo.peoplecount }"></td>
                     <td><label>成本单价：</label></td>
                     <td><input id="costsingleprice" name="costsingleprice" disabled="disabled" type="text" class="form-control input-sm disab mustNumberPoint" value="<fmt:formatNumber type="number" value="${obj.orderinfo.costsingleprice }" pattern="0.00" maxFractionDigits="2"/>"></td>
                   </tr>
                 </table>
                 <div class="tableDuan"><!--主段-->
                  <span>主段</span>
                  <table class="table table-bordered table-hover main">
                    <thead>
                      <tr>
                        <th>记录编号</th>
                        <th>航段</th>
                        <th>航班号</th>
                        <th>出发日期</th>
                        <th>时间</th>
                        <th>人数</th>
                        <th colspan="2">操作</th>
                      </tr>
                    </thead>
                    <tbody id="mainsection">
                    </tbody>
                  </table>
                 </div><!--end 主段-->
                 <div class="tableDuan"><!--子段-->
                  <span>子段</span>
                  <table class="table table-bordered table-hover main">
                    <thead>
                      <tr>
                        <th>记录编号</th>
                        <th>航段</th>
                        <th>航班号</th>
                        <th>出发日期</th>
                        <th>时间</th>
                        <th>人数</th>
                        <th colspan="2">操作</th>
                      </tr>
                    </thead>
                    <tbody id="zisection">
                    </tbody>
                  </table>
                 </div><!--end 子段-->
               </div>
          </div><!--end 航程信息-->
          
    	  <div class="listInfo"><!-- 预收款记录/预付款记录 -->
			<div class="nav-tabs-custom">
				<ul class="nav nav-tabs query-style">
					<li class="active"><a href="#tab_2" data-toggle="tab">预付款记录</a></li>
					<li><a href="#tab_1" data-toggle="tab">预收款记录</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane" id="tab_1">
						<!-- <button type="button" class="btn btn-primary right recordBtn addYSK none">添加记录</button> -->
		                <table class="table table-bordered table-hover main">
		                    <thead>
		                      <tr>
		                        <th></th>
		                        <th>预收款比例</th>
		                        <th>免罚金可减人数</th>
		                        <th>实际减少人数</th>
		                        <th>本期罚金</th>
		                        <th>本期应付</th>
		                        <th>本期税金</th>
		                        <th>本期实付</th>
		                        <th>操作</th>
		                      </tr>
		                    </thead>
		                    <tbody id="receiverecord">
		
		                    </tbody>
		                </table>
					</div>
					<div class="tab-pane active" id="tab_2">
						<button type="button" class="btn btn-primary right recordBtn addYFK none">添加记录</button>
		                <table class="table table-bordered table-hover main">
		                    <thead>
		                      <tr>
		                        <th></th>
		                        <th>预付款比例</th>
		                        <th>免罚金可减人数</th>
		                        <th>实际减少人数</th>
		                        <th>本期罚金</th>
		                        <th>本期应付</th>
		                        <th>本期税金</th>
		                        <th>本期实付</th>
		                        <th>操作</th>
		                      </tr>
		                    </thead>
		                    <tbody id="payrecord">
		                    </tbody>
		                 </table>		
					</div>
				</div>
			</div>
		 </div><!--end 预收款记录/预付款记录 -->

          <div class="customerInfo"><!--信息-->
            <div class="infoTop">
                 <p>信息</p>
            </div>
            <div class="infofooter">
            	<form id="financeForm">
            		<input id="id" name="id" type="hidden" value="${obj.finance.id }" >
            		<input id="orderid" name="orderid" type="hidden" value="${obj.orderinfo.id }"/>
                 <table class="">
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
                     <td><label>付款币种：</label></td>
                     <td>
                        <select id="paycurrency" name="paycurrency" class="form-control input-sm">
                            <option value="">请选择</option>
                            <c:forEach items="${obj.bzcode }" var="one"> 
                            	<c:choose>
                            		<c:when test="${obj.finance.paycurrency eq one.id }">
					                     <option value="${one.id }" selected="selected">${one.dictCode }</option>
                            		</c:when>
                            		<c:otherwise>
					                     <option value="${one.id }">${one.dictCode }</option>
                            		</c:otherwise>
                            	</c:choose>
	                     	</c:forEach>
                        </select>
                     </td>
                     <td><label>付款方式：</label></td>
                     <td>
                        <select id="paymethod" name="paymethod" class="form-control input-sm">
                            <option value="">请选择</option>
                            <c:forEach var="map" items="${obj.paymethod}" >
                            	<c:choose>
                            		<c:when test="${obj.finance.paymethod eq map.id }">
                            			<option value="${map.id}" selected="selected">${map.cardName}</option>
                            		</c:when>
                            		<c:otherwise>
								   		<option value="${map.id}">${map.cardName}</option>
                            		</c:otherwise>
                            	</c:choose>
							 </c:forEach>
                        </select>
                     </td>
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
					 <%--<td><label>进澳时间：</label></td>
                     <td><input id="enterausdate" name="enterausdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${obj.finance.enterausdate }" pattern="yyyy-MM-dd" />" type="text" class="form-control input-sm disab" disabled="disabled"></td>
                     <td><label>出澳时间：</label></td>
                     <td><input id="outausdate" name="outausdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${obj.finance.outausdate }" pattern="yyyy-MM-dd" />" type="text" class="form-control input-sm disab" disabled="disabled"></td> --%>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>进澳时间：</label></td>
                     <td><input id="enterausdate" name="enterausdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${obj.finance.enterausdate }" pattern="yyyy-MM-dd" />" type="text" class="form-control input-sm disab" disabled="disabled"></td>
                   	 <td><label>航空公司：</label></td>
                     <td><select id="enteraircom" name="enteraircom" value="" type="text" class="form-control input-sm disab aircomselect" disabled="disabled" multiple="multiple">
                     	<c:forEach items="${obj.aircomselect }" var="one"> 
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
                     <td><select id="outaircom" name="outaircom" value="" type="text" class="form-control input-sm disab aircomselect" disabled="disabled" multiple="multiple">
                     	<c:forEach items="${obj.aircomselect }" var="one"> 
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
                     <td><input id="receivable" name="receivable" type="text" class="form-control input-sm disab mustNumberPoint" value="${obj.finance.receivable }" disabled="disabled"></td>
                     <td><label><a href="javascript:;" class="jianMian">减免</a>：</label></td>
                     <td><input id="relief" name="relief" type="text" class="form-control input-sm" disabled="disabled" value="${obj.finance.relief }"></td>
                     <td><label>实收合计：</label></td>
                     <td><input id="incometotal" name="incometotal" type="text" class="form-control input-sm disab loadprofit mustNumberPoint" disabled="disabled" value="${obj.finance.incometotal }"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>成本合计：</label></td>
                     <td><input id="costtotal" name="costtotal" type="text" class="form-control input-sm disab loadprofit mustNumberPoint" disabled="disabled" value="${obj.finance.costtotal }"></td>
                     <td><label>应返：</label></td>
                     <td><input id="returntotal" name="returntotal" type="text" class="form-control input-sm disab loadprofit mustNumberPoint" disabled="disabled" value="${obj.finance.returntotal }"></td>
                     <td><label>利润合计：</label></td>
                     <td><input id="profittotal" name="profittotal" type="text" class="form-control input-sm disab mustNumberPoint" disabled="disabled" value="${obj.finance.profittotal }"></td>
                   </tr>
                 </table>
            </div>
            </form>
          </div><!--end 信息-->
          
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
  <script src="${base }/admin/order/ordercommon.js"></script>
  <script src="${base }/admin/international/internationaldetail.js"></script>
  <script type="text/javascript">
    $(function(){
        //编辑按钮 click事件
        $('.editBtn').click(function(){
          $(this).addClass('none');
          $('.btnSave').toggle();//保存按钮 显示
          $('.btnCancel').toggle();//取消按钮 显示
          $(".btnRemind").toggle();//提醒设置按钮 显示
          $(".btnLog").toggle();//日志按钮 显示
          $(".addHD").toggle();//添加航段 显示
          $(".addYSK").toggle();//添加预收款 显示
          $(".addYFK").toggle();//添加预付款 显示
          //$(".listInfo").toggle();//选项卡 显示
          $('.disab').removeAttr("disabled");//信息模块 input 禁止编辑的状态
          $('#orderType').removeAttr("disabled");//信息模块 input 禁止编辑的状态
          loadAirlineInfo();
          loadJianMianAccount('${obj.orderinfo.id }');
        });
        //取消按钮 click事件
        $('.btnCancel').click(function(){
          $('.editBtn').removeClass('none');//显示 编辑 按钮
          $('.btnSave').toggle();//保存 按钮 隐藏
          $('.btnCancel').toggle();//取消 按钮 隐藏
          $('.btnRemind').toggle();//提醒设置 按钮 隐藏
          $('.btnLog').toggle();//日志 按钮 隐藏
          $('.addHD').toggle();//添加航段 隐藏
          $('.addYSK').toggle();//添加预收款 隐藏
          $('.addYFK').toggle();//添加付款 隐藏
          //$(".listInfo").toggle();//选项卡 隐藏
          $('.disab').attr("disabled",'disabled');//信息模块 input 添加 不可编辑属性
          $('#orderType').attr("disabled",'disabled');//信息模块 input 添加 不可编辑属性
          loadAirlineInfo(1);
          loadJianMianAccount('${obj.orderinfo.id }',1);
        });

        $('.UnderIcon').on('click',function(){//客户信息 显示/隐藏
          $('.hideTable').toggle(); 
        });

        //点击 提醒设置 弹框
        $('.btnRemind').click(function(){
            layer.open({
                type: 2,
                title:false,
                skin: false, //加上边框
                closeBtn:false,//默认 右上角关闭按钮 是否显示
                shadeClose:true,
                scrollbar: false,
                area: ['400px', '450px'],
                content: '${base}/admin/international/orderRemind.html?orderid=${obj.orderinfo.id }'
              });
        });

        //点击 日志 弹框
        $('.btnLog').click(function(){
            layer.open({
                type: 2,
                title:false,
                skin: false, //加上边框
                closeBtn:false,//默认 右上角关闭按钮 是否显示
                shadeClose:true,
                scrollbar: false,
                area: ['800px', '550px'],
                content: '${base}/admin/international/orderLog.html?orderid=${obj.orderinfo.id }'
              });
        });

        //点击 添加航段 弹框
        $('.addHD').click(function(){
            layer.open({
                type: 2,
                title:false,
                skin: false, //加上边框
                closeBtn:false,//默认 右上角关闭按钮 是否显示
                shadeClose:true,
                scrollbar: false,
                area: ['1100px', '550px'],
                content: '${base}/admin/international/addAirinfo.html?orderid=${obj.orderinfo.id }'
              });
        });
        //点击 添加记录-预收款 弹框
        $('.addYSK').click(function(){
        	var orderType = $('#orderType').val();
        	var peoplecount = $('#peoplecount').val();
        	var costsingleprice = $('#costsingleprice').val();
        	var customerId = $('#customerId').val();
        	if(orderType > 2){
	        	$.ajax({ 
	        		type: 'POST', 
	        		data: {orderid:'${obj.orderinfo.id }',ordersstatus:orderType}, 
	        		url: BASE_PATH + '/admin/intervalidate/checkRecordIsExist.html',
	              	success: function (data) { 
	              		if(data){
				            layer.open({
				                type: 2,
				                title:false,
				                skin: false, //加上边框
				                closeBtn:false,//默认 右上角关闭按钮 是否显示
				                shadeClose:true,
				                scrollbar: false,
				                area: ['900px', '200px'],
				                content: '${base}/admin/international/addReceiveRecord.html?orderid=${obj.orderinfo.id }&payreceivestatus=${obj.receivestatus}&ordersstatus='+orderType+'&peoplecount='+peoplecount+'&costsingleprice='+costsingleprice+'&customerId='+customerId
				              });
	              		}else{
	              			layer.msg("该状态已经添加收款记录","",3000);
	              		}
	                },
	                error: function (xhr) {
	                } 
	          });
        	}else{
        		layer.msg("一订以后才能添加记录","",3000);
        	}
       });

        //点击 添加记录-预付款 弹框
        $('.addYFK').click(function(){
        	var orderType = $('#orderType').val();
        	var peoplecount = $('#peoplecount').val();
        	var costsingleprice = $('#costsingleprice').val();
        	if(orderType > 2){
	        	$.ajax({ 
	        		type: 'POST', 
	        		data: {orderid:'${obj.orderinfo.id }',ordersstatus:orderType}, 
	        		url: BASE_PATH + '/admin/intervalidate/checkPayRecordIsExist.html',
	              	success: function (data) { 
	              		if(data){
				            layer.open({
				                type: 2,
				                title:false,
				                skin: false, //加上边框
				                closeBtn:false,//默认 右上角关闭按钮 是否显示
				                shadeClose:true,
				                scrollbar: false,
				                area: ['900px', '200px'],
				                content: '${base}/admin/international/addPayRecord.html?orderid=${obj.orderinfo.id }&payreceivestatus=${obj.paystatus}&ordersstatus='+orderType+'&peoplecount='+peoplecount+'&costsingleprice='+costsingleprice
				             });
	              		}else{
	              			layer.msg("该状态已经添加付款记录","",3000);
	              		}
	                },
	                error: function (xhr) {
	                } 
	          });
        	}else{
        		layer.msg("一订以后才能添加记录","",3000);
        	}
        });
        $('.recordParent p:eq(0)').click(function(){//预收款记录 切换tab
          $(this).addClass('recStyle').siblings().removeClass('recStyle');
          $('.recordDiu:eq(0)').show();
          $('.recordDiu:eq(1)').hide();
        });

        $('.recordParent p:eq(1)').click(function(){//预付款记录 切换tab
          $(this).addClass('recStyle').siblings().removeClass('recStyle');
          $('.recordDiu:eq(1)').show();
          $('.recordDiu:eq(0)').hide();
        });
    });
    //加载航段信息
    loadAirlineInfo(1);
    function loadAirlineInfo(status){
    	
    	$.ajax({
			type: 'POST', 
			data: {orderid:'${obj.orderinfo.id}'}, 
			url: '${base}/admin/international/loadAirlineInfo.html',
			async:false,
			dataType:'json',
            success: function (data) { 
            	var mainhtml = '';
            	var zihtml = '';
            	for(var i = 0 ;i < data.length ; i++){
            		if(data[i].pnrinfo.mainsection === 1){
            			//设置主航段信息
            			mainhtml += '<tr><td>';
            			if(data[i].pnrinfo.pNR && data[i].pnrinfo.pNR != undefined){
            				mainhtml += data[i].pnrinfo.pNR;
            			}
            			mainhtml += '</td>';
            			mainhtml += '<td><ul>';
            			var mainaircount = 0;
            			$.each(data[i].airinfo, function(name, value) {
            				mainaircount += 1;
               				//mainhtml += '<li>'+value.leavecity+'/'+value.arrvicity+'</li>';
               				mainhtml += '<li>';
               				if(value.leavecity && value.leavecity != undefined){
               					mainhtml += value.leavecity;
               				}
               				mainhtml += '/';
               				if(value.arrvicity && value.arrvicity != undefined){
               					mainhtml += value.arrvicity;
               				}
               				mainhtml += '</li>';
                		});
            			mainhtml += '</ul></td>'; 
            			mainhtml += '<td><ul>';
            			$.each(data[i].airinfo, function(name, value) {
            				mainhtml += '<li>';
               				if(value.ailinenum && value.ailinenum != undefined){
               					mainhtml += value.ailinenum;
               				}
               				mainhtml += '</li>';
               				//mainhtml += '<li>'+value.ailinenum+'</li>';
                		});
            			mainhtml += '</ul></td>'; 
            			mainhtml += '<td><ul>';
            			$.each(data[i].airinfo, function(name, value) {
               				//mainhtml += '<li>'+value.leavedate+'</li>';
               				mainhtml += '<li>';
               				if(value.currencyCode && value.currencyCode != undefined){
               					mainhtml += value.currencyCode;
               				}
               				mainhtml += '</li>';
                		});
            			mainhtml += '</ul></td>'; 
            			mainhtml += '<td><ul>';
            			$.each(data[i].airinfo, function(name, value) {
            				mainhtml += '<li>';
               				if(value.leavetime && value.leavetime != undefined){
               					mainhtml += value.leavetime;
               				}
               				mainhtml += '/';
               				if(value.arrivetime && value.arrivetime != undefined){
               					mainhtml += value.arrivetime;
               				}
               				mainhtml += '</li>';
                		});
            			mainhtml += '</ul></td>'; 
            			//人数
            			mainhtml += '<td>';
            			if(data[i].pnrinfo.peoplecount && data[i].pnrinfo.peoplecount != undefined){
            				mainhtml += data[i].pnrinfo.peoplecount;
            			}
            			mainhtml += '</td>';
            			if(status){
            				mainhtml += '<td></td>';
            			}else{
	            			mainhtml += '<td><a href="javascript:editAirlineInfo('+data[i].pnrinfo.id+');">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;';
	            			mainhtml += '<a href="javascript:visitorInfo('+data[i].pnrinfo.id+');" class="YKinfo">游客信息</a>';
	            			mainhtml += '<a href="javascript:;" class="FileDiv">上传游客<input type="file" class="uploadVisitors"><input type="hidden" id="pnrid" name="pnrid" value="'+data[i].pnrinfo.id+'"></a></td>';
            			}
            		}else{
            			zihtml += '<tr><td>';
            			if(data[i].pnrinfo.pNR && data[i].pnrinfo.pNR != undefined){
            				zihtml += data[i].pnrinfo.pNR;
            			}
            			zihtml += '</td>';
            			zihtml += '<td><ul>';
            			$.each(data[i].airinfo, function(name, value) {
               				//zihtml += '<li>'+value.leavecity+'/'+value.arrvicity+'</li>';
               				zihtml += '<li>';
               				if(value.leavecity && value.leavecity != undefined){
               					zihtml += value.leavecity;
               				}
               				zihtml += '/';
               				if(value.arrvicity && value.arrvicity != undefined){
               					zihtml += value.arrvicity;
               				}
               				zihtml += '</li>';
                		});
            			zihtml += '</ul></td>'; 
            			zihtml += '<td><ul>';
            			$.each(data[i].airinfo, function(name, value) {
            				zihtml += '<li>';
               				if(value.ailinenum && value.ailinenum != undefined){
               					zihtml += value.ailinenum;
               				}
               				zihtml += '</li>';
               				//zihtml += '<li>'+value.ailinenum+'</li>';
                		});
            			zihtml += '</ul></td>'; 
            			zihtml += '<td><ul>';
            			$.each(data[i].airinfo, function(name, value) {
               				//zihtml += '<li>'+value.leavedate+'</li>';
               				zihtml += '<li>';
               				if(value.currencyCode && value.currencyCode != undefined){
               					zihtml += value.currencyCode;
               				}
               				zihtml += '</li>';
                		});
            			zihtml += '</ul></td>'; 
            			zihtml += '<td><ul>';
            			$.each(data[i].airinfo, function(name, value) {
               				zihtml += '<li>';
               				if(value.leavetime && value.leavetime != undefined){
               					zihtml += value.leavetime;
               				}
               				zihtml += '/';
               				if(value.arrivetime && value.arrivetime != undefined){
               					zihtml += value.arrivetime;
               				}
               				zihtml += '</li>';
                		});
            			zihtml += '</ul></td>'; 
            			zihtml += '<td>';
            			if(data[i].pnrinfo.peoplecount && data[i].pnrinfo.peoplecount != undefined){
            				zihtml += data[i].pnrinfo.peoplecount;
            			}
            			zihtml += '</td>';
            			if(status){
            				zihtml += '<td></td>';
            			}else{
	            			zihtml += '<td><a href="javascript:editAirlineInfo('+data[i].pnrinfo.id+');">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;';
	            			zihtml += '<a href="javascript:visitorInfo('+data[i].pnrinfo.id+');" class="YKinfo">游客信息</a>';
	            			zihtml += '<a href="javascript:;" class="FileDiv">上传游客<input type="file" class="uploadVisitors"><input type="hidden" id="pnrid" name="pnrid" value="'+data[i].pnrinfo.id+'"></a></td>';
            			}
            		}
            	}
            	//设置主航段数
            	$('#mainaircount').val(mainaircount);
            	$('#mainsection').html(mainhtml);
            	autoHighLoad($('#mainsection'));
            	$('#zisection').html(zihtml);
            	autoHighLoad($('#zisection'))
            },
            error: function (xhr) {
            } 
      });
    }
  //加载减免信息
    loadJianMianAccount('${obj.orderinfo.id }',1); 
    function loadpayAndReceiveRecord(status){
    	$.ajax({
			type: 'POST', 
			data: {orderid:'${obj.orderinfo.id}'}, 
			url: '${base}/admin/international/loadPayReceiveRecord.html',
			async:false,
			dataType:'json',
            success: function (data) { 
            	var receivestatus = data.receivestatus;
            	var paystatus = data.paystatus;
            	var receivehtml = '';
            	var payhtml = '';
            	//合计收款罚金
            	var receivefinesum = 0;
            	//合计收款应付
            	var receiveyingsum = 0;
            	//合计收款税金
            	var receiveshuisum = 0;
            	//合计收款实付
            	var receiveshisum = 0;
            	//合计付款罚金
            	var payfinesum = 0;
            	//合计付款应付
            	var payyingsum = 0;
            	//合计付款税金
            	var payshuisum = 0;
            	//合计付款实付
            	var payshisum = 0;
            	//人头数
            	var actualnumber = 0;
            	$.each(data.record, function(name, value) {
            		if(value.recordtype === receivestatus){
            			receivehtml += '<tr>';
            			if(value.orderstatus != undefined){
	            			receivehtml += '<td>'+value.orderstatus+'</td>';
            			}else{
            				receivehtml += '<td></td>';
            			}
            			if(value.prepayratio != undefined){
	                        receivehtml += '<td>'+value.prepayratio+'%</td>';
            			}else{
            				receivehtml += '<td></td>';
            			}
            			if(value.freenumber != undefined){
	                        receivehtml += '<td>'+value.freenumber+'</td>';
            			}else{
            				receivehtml += '<td></td>';
            			} 
            			if(value.actualyreduce != undefined){
	                        receivehtml += '<td>'+value.actualyreduce+'</td>';
            			}else{
            				receivehtml += '<td></td>';
            			} 
            			if(value.currentfine != undefined){
	                        receivehtml += '<td>'+value.currentfine.toFixed(2)+'</td>';
	                        receivefinesum += parseFloat(value.currentfine);
            			}else{
            				receivehtml += '<td></td>';
            			}
            			if(value.currentdue != undefined){
	                        receivehtml += '<td>'+value.currentdue.toFixed(2)+'</td>';
	                        receiveyingsum += parseFloat(value.currentdue);
            			}else{
            				receivehtml += '<td></td>';
            			} 
            			if(value.ataxprice != undefined){
	                        receivehtml += '<td>'+value.ataxprice.toFixed(2)+'</td>';
	                        receiveshuisum += parseFloat(value.ataxprice);
            			}else{
            				receivehtml += '<td></td>';
            			} 
            			if(value.currentpay != undefined){
	                        receivehtml += '<td>'+value.currentpay.toFixed(2)+'</td>';
	                        receiveshisum += parseFloat(value.currentpay);
            			}else{
            				receivehtml += '<td></td>';
            			} 
                        receivehtml += '<td>';
                        if(!status){
	                        receivehtml += '<a href="javascript:editRecord('+value.id+','+value.recordtype+');">编辑</a>';
                        }
                        receivehtml += '</td>';
                        receivehtml += '</tr>';
            		}else{
            			if(value.actualnumber != undefined){
            				actualnumber = value.actualnumber;
            			}
            			payhtml += '<tr>';
            			if(value.orderstatus != undefined){
	            			payhtml += '<td>'+value.orderstatus+'</td>';
            			}else{
            				payhtml += '<td></td>';
            			}
            			if(value.prepayratio != undefined){
	                        payhtml += '<td>'+value.prepayratio+'%</td>';
            			}else{
            				payhtml += '<td></td>';
            			}
            			if(value.freenumber != undefined){
	                        payhtml += '<td>'+value.freenumber+'</td>';
            			}else{
            				payhtml += '<td></td>';
            			} 
            			if(value.actualyreduce != undefined){
	                        payhtml += '<td>'+value.actualyreduce+'</td>';
            			}else{
            				payhtml += '<td></td>';
            			} 
            			if(value.currentfine != undefined){
	                        payhtml += '<td>'+value.currentfine.toFixed(2)+'</td>';
	                        payfinesum += parseFloat(value.currentfine);
            			}else{
            				payhtml += '<td></td>';
            			}
            			if(value.currentdue != undefined){
	                        payhtml += '<td>'+value.currentdue.toFixed(2)+'</td>';
	                        payyingsum += parseFloat(value.currentdue);
            			}else{
            				payhtml += '<td></td>';
            			} 
            			if(value.ataxprice != undefined){
	                        payhtml += '<td>'+value.ataxprice.toFixed(2)+'</td>';
	                        payshuisum += parseFloat(value.ataxprice);
            			}else{
            				payhtml += '<td></td>';
            			} 
            			if(value.currentpay != undefined){
	                        payhtml += '<td>'+value.currentpay.toFixed(2)+'</td>';
	                        payshisum += parseFloat(value.currentpay);
            			}else{
            				payhtml += '<td></td>';
            			} 
                        payhtml += '<td>';
                        if(!status){
	                        payhtml += '<a href="javascript:editRecord('+value.id+','+value.recordtype+');">编辑</a>';
                        }
                        payhtml += '</td>';
                        payhtml += '</tr>';
            		}
        		});
            	receivehtml += '<tr><td>合计</td> <td></td> <td></td> <td></td> <td>'+receivefinesum.toFixed(2)+'</td> <td>'+receiveyingsum.toFixed(2)+'</td> <td>'+receiveshuisum.toFixed(2)+'</td> <td>'+receiveshisum.toFixed(2)+'</td> <td></td>';
            	payhtml += '<tr><td>合计</td> <td></td> <td></td> <td></td> <td>'+payfinesum.toFixed(2)+'</td> <td>'+payyingsum.toFixed(2)+'</td> <td>'+payshuisum.toFixed(2)+'</td> <td>'+payshisum.toFixed(2)+'</td> <td></td>';
            	$('#receiverecord').html(receivehtml);
            	$('#payrecord').html(payhtml);
            	$('#receivable').val(receiveshisum.toFixed(2));
            	$('#costtotal').val(payshisum.toFixed(2));
            	var mainaircount = $('#mainaircount').val();
            	$('#personcount').val(actualnumber * mainaircount);
            	setFinanceInfo();
            	/* var incometotal  = '';
            	var relief = $('#relief').val();
	       	 	if(relief){
	       	 		incometotal  = parseFloat(receiveshisum) - parseFloat(relief);
	       	 	}else{
	       	 		incometotal = receiveshisum;
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
	 	   	 	var profittotal  = parseFloat(incometotal) - parseFloat(payshisum) - parseFloat(returntotal);
	 	   	 	if(!isNaN(profittotal)){
	 	   		 	$('#profittotal').val(profittotal.toFixed(2));
	 	   	 	} */
            },
            error: function (xhr) {
            	
            } 
      });
    }
    function successCallback(id){
	  loadAirlineInfo();
	  loadpayAndReceiveRecord();
	  if(id == '1'){
		  layer.msg("添加成功",{time: 2000});
	  }else if(id == '2'){
		  layer.msg("修改成功",{time: 2000});
	  }else if(id=='3'){
		  layer.msg("提醒成功",{time: 2000});
	  }
	}
    //编辑航段
    function editAirlineInfo(pnrid){
    	layer.open({
            type: 2,
            title:false,
            skin: false, //加上边框
            closeBtn:false,//默认 右上角关闭按钮 是否显示
            shadeClose:true,
            scrollbar: false,
            area: ['1100px', '550px'],
            content: '${base}/admin/international/editAirinfo.html?pnrid='+pnrid
          });
    }
    //点击 游客信息 弹框
    function visitorInfo(pnrid){
        layer.open({
            type: 2,
            title:false,
            skin: false, //加上边框
            closeBtn:false,//默认 右上角关闭按钮 是否显示
            shadeClose:true,
            scrollbar: false,
            area: ['900px', '550px'],
            content: '${base}/admin/international/visitorInfo.html?pnrid='+pnrid
          });
    }
    //编辑预收款、付款
    function editRecord(id,type){
    	var url = '';
    	if(type == '${obj.receivestatus}'){
    		url = '${base}/admin/international/editReceiveRecord.html?recordid='+id
    	}else{
    		url = '${base}/admin/international/editPayRecord.html?recordid='+id
    	}
       layer.open({
           type: 2,
           title:false,
           skin: false, //加上边框
           closeBtn:false,//默认 右上角关闭按钮 是否显示
           shadeClose:true,
           scrollbar: false,
           area: ['900px', '200px'],
           content: url
         });
    }
    
  //点击 减免 弹框
    $(document).on("click",".jianMian",function(){
        layer.open({
            type: 2,
            title:false,
            skin: false, //加上边框
            closeBtn:false,//默认 右上角关闭按钮 是否显示
            shadeClose:true,
            scrollbar: false,
            area: ['770px', '240px'],
            content: '${base}/admin/inland/mitigate.html?id=${obj.orderinfo.id }&customeid=${obj.custominfo.id }',
            end:function(){
            	loadJianMianAccount('${obj.orderinfo.id }');
            }
          });
    });
  	//加载减免信息
    function loadJianMianAccount(orderid,status){
   	 	$.ajax({ 
   			type: 'POST', 
   			data: {orderid:orderid}, 
   			dataType:'json',
   			url: BASE_PATH + '/admin/inland/loadJianMianAccount.html',
            success: function (data) { 
           	 if(data.account){
   	         	$('#relief').val(data.account.toFixed(2));
           	 }
           	loadpayAndReceiveRecord(status);
            },
            error: function (xhr) {
            } 
         });
    }
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
   	 	var returntotal =0;
   	    if($('#returntotal').val()){
   	    	returntotal = $('#returntotal').val();
   	    }
   	    //利润合计
   	 	var profittotal  = parseFloat(incometotal) - parseFloat(costtotal) - parseFloat(returntotal);
   	 	if(!isNaN(profittotal)){
   		 	$('#profittotal').val(profittotal.toFixed(2));
   	 	}
   	 });
    
	 //设置财务信息
     function setFinanceInfo(){
         	//成本合计
         	var costtotal = 0;
         	if($('#costtotal').val()){
         		costtotal = $('#costtotal').val();
         	}
         	//应收
         	var receivable = 0;
         	if($('#receivable').val()){
         		receivable = $('#receivable').val();
         	}
         	var relief = 0;
         	if($('#relief').val()){
         		relief = $('#relief').val();
         	}
      	 	var incometotal  = '';
      	 	if(relief){
      	 		incometotal  = parseFloat(receivable) - parseFloat(relief);
      	 	}else{
      	 		incometotal = receivable;
      	 	}
      	 	if(!isNaN(incometotal)){
  		 		$('#incometotal').val(incometotal);
      	 	}
      	 	var returntotal = 0;
	       	//应返合计
	       	if($('#returntotal').val()){
 	   	 	returntotal = $('#returntotal').val();
	       	}
	   	    //利润合计
	   	 	var profittotal  = parseFloat(incometotal) - parseFloat(costtotal) - parseFloat(returntotal);
	   	 	if(!isNaN(profittotal)){
	   		 	$('#profittotal').val(profittotal.toFixed(2));
	   	 	}
   	  }
  </script>
</body>
</html>
