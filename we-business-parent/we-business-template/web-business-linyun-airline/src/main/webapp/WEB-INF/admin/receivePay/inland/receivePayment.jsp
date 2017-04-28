<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>收付款-内陆跨海</title>
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!--本页面 CSS样式-->
<link rel="stylesheet" type="text/css" href="${base}/public/dist/css/receivePayment.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<!-- Content-->
		<div class="content-wrapper">
			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-xs-12">

						<div class="nav-tabs-custom">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#tab_1"  onclick="toConfirmRecPage();" data-toggle="tab">收款</a></li>
								<li><a href="#tab_2" onclick="toConfirmPayPage();" data-toggle="tab">付款</a></li>
							</ul>
							<div class="tab-content">
								<!-- ---------------------------start 收款------------------------------- -->
								<div class="tab-pane pane-content active" id="tab_1">
									<div class="box-header">
										<form role="form" class="form-horizontal">
											<div class="form-group row marginBott5 cf">
												<div class="col-md-1">
													<select id="inlandRecSelect" class="form-control TimeInput">
														<option value=1>收款中</option>
														<option value=2>已收款</option>
													</select>
												</div>
												<div class="col-md-1 padding">
													<input id="inlandRecBeginDate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'inlandRecEndDate\')}'})" class="form-control TimeInput" placeholder="2017-02-20">
												</div>
												<label class="col-md-1 labelClas">至</label>
												<div class="col-md-1 padding">
													<input id="inlandRecEndDate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'inlandRecBeginDate\')}'})" class="form-control TimeInput" placeholder="2017-02-22">
												</div>
												<div class="col-md-3 textPadding">
													<!-- 客户名称/订单号/联系人/PNR 搜索框 -->
													<input id="inlandRecInput" onkeypress="recOnkeyEnter();" type="text" class="form-control" placeholder="客户名称/订单号/联系人">
												</div>
												<div class="col-md-2">
													<!-- 搜索 按钮 -->
													<button id="inlandRecSearchBtn" type="button" class="btn btn-primary btn-sm">搜索</button>
													<button id="inlandRecClearBtn" type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
												</div>
											</div>
										</form>
									</div>
									<div class="box-body">
										<table id="inlandRecTable" class="table table-bordered table-hover">
											<thead>
												<tr>
													<th>订单号</th>
													<th>出发日期</th>
													<th>人数</th>
													<th>销售金额</th>
													<th>总价</th>
													<th>客户名称</th>
													<th>开票人</th>
													<th>状态</th>
													<th>备注</th>
												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>
									</div>
								</div>
								<!-- ---------------------------end 收款------------------------------- -->

								<!-- --------------------------start 付款------------------------------- -->
								<div class="tab-pane pane-content" id="tab_2">
									<div class="box-header">
										<form role="form" class="form-horizontal">
											<div class="form-group row marginBott5 cf">
												<div class="col-md-1">
													<select id="inlandPaySelect" class="form-control TimeInput">
														<option value=2>付款中</option>
														<option value=3>已付款</option>
													</select>
												</div>
												<div class="col-md-1 padding">
													<input id="inlandPayBeginDate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'inlandPayEndDate\')}'})" class="form-control TimeInput" placeholder="2017-02-20">
												</div>
												<label class="col-md-1 labelClas">至</label>
												<div class="col-md-1 padding">
													<input id="inlandPayEndDate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'inlandPayBeginDate\')}'})" class="form-control TimeInput" placeholder="2017-02-22">
												</div>
												<div class="col-md-3 textPadding">
													<!-- 客户名称/订单号/联系人/PNR 搜索框 -->
													<input id="inlandPayInput" type="text" onkeypress="payOnkeyEnter();" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
												</div>
												<div class="col-md-4">
													<!-- 搜索 按钮 -->
													<button id="inlandPaySearchBtn" type="button" class="btn btn-primary btn-sm">搜索</button>
													<button id="inlandPayClearBtn" type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
													<button id="inlandPayCancelBtn" type="button" class="btn btn-primary btn-sm ckBtn">取消所有勾选</button>
												</div>
												<div class="col-md-1">
													<!-- 付款 按钮 -->
													<button id="inlandPayClick" type="button" class="btn btn-primary btn-sm fuKuanBtn1">付款</button>
												</div>
											</div>
										</form>
									</div>
									<div class="box-body">
										<!-- ---------------------------------- 付款中   列表 ------------------------------------- -->
										<table id="inlandPayTable" class="table table-bordered table-hover">
											<input id="checkedboxPayValue" name="checkedboxPayValue" type="hidden">
											<thead id="inlandPayThead">
												<tr>
													<td class="checkTh"><input type="checkbox" class="checkBoxPayAll"></td>
													<th>订单号</th>
													<th>PNR</th>
													<th>出发日期</th>
													<th>人数</th>
													<th>应付金额</th>
													<th>币种</th>
													<th>收款单位</th>
													<th>状态</th>
													<th>开票人</th>
												</tr>
											</thead>
											<tbody id="inlandPayTbody">

											</tbody>
										</table>
										<!-- ---------------------------------- 已收款    列表 ------------------------------------ -->
										<table id="inlandPayEdTable" style="display: none" class="table table-bordered table-hover">
											<thead id="inlandPayEdThead">
												<tr>
													<th>订单号</th>
													<th>PNR</th>
													<th>出发日期</th>
													<th>人数</th>
													<th>应付金额</th>
													<th>币种</th>
													<th>总额</th>
													<th>收款单位</th>
													<th>状态</th>
													<th>开票人</th>
													<th>备注</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody id="inlandPayEdTbody">
											</tbody>
										</table>

									</div>
								</div>
								<!-- --------------------------end 付款------------------------------- -->

							</div>
							<!-- end tab-content -->
						</div>
						<!-- end nav-tabs-custom -->
					</div>
				</div>
			</section>
		</div>

		<!-- Footer -->
		<%@include file="/WEB-INF/public/footer.jsp"%>
	</div>

	<!-- REQUIRED JS SCRIPTS -->
	<!-- 引入js文件 -->
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base }/admin/order/ordercommon.js"></script>
	<!-- My97DatePicker -->
	<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<!-- 收付款Common js -->
	<script src="${base}/admin/receivePayment/recPayCommon.js"></script>
	<!-- 内陆跨海js -->
	<script src="${base}/admin/receivePayment/inland/inlandPay.js"></script>
	<script src="${base}/admin/receivePayment/inland/inlandReceive.js"></script>
	
	<!-- 国际js -->
	<%-- <script src="${base}/admin/receivePayment/internationPayment.js"></script> --%>
	<script type="text/javascript">
		$(function(){
			$('.menu-ul:eq(1)').hide();//隐藏数据字典的二级菜单
			$('.menu-ul li:eq(0) a').css("color","rgb(245, 245, 245)");//二级菜单 高亮style
			recPageFirst();		
		});
	</script>
	
</body>
</html>

