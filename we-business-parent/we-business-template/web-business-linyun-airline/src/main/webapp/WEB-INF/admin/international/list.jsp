<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!--小日历-->
<link rel="stylesheet" type="text/css" href="${base }/public/build/kalendae.css">
<link rel="stylesheet" type="text/css" href="${base }/public/dist/css/airlinesModule.css">
<link rel="stylesheet" type="text/css" href="${base }/public/dist/css/inlandCross.css">
<link rel="stylesheet" type="text/css" href="${base }/public/dist/css/internation.css"><!-- 本页面style -->
<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row row-top">
        <div class="col-xs-12">
         <div class="nav-tabs-custom">
              <ul class="nav nav-tabs">
                <li><a href="#tab_2" data-toggle="tab">团队制作</a></li>
                <li><a href="#tab_3" data-toggle="tab">订单管理</a></li>
                <li class="active"><a href="#tab_1" onclick="loadDataTable(0)" data-toggle="tab">全部</a></li>
                <li><a href="#tab_1" onclick="loadDataTable(1)" data-toggle="tab">查询</a></li>
                <li><a href="#tab_1" onclick="loadDataTable(2)" data-toggle="tab">预订</a></li>
                <li><a href="#tab_5" onclick="loadTicking(3)" data-toggle="tab">一订</a></li>
                <li><a href="#tab_5" onclick="loadTicking(4)" data-toggle="tab">二订</a></li>
                <li><a href="#tab_5" onclick="loadTicking(5)" data-toggle="tab">三订</a></li>
                <li><a href="#tab_5" onclick="loadTicking(6)" data-toggle="tab">全款</a></li>
                <li><a href="#tab_5" onclick="loadTicking(7)" data-toggle="tab">尾款</a></li>
                <li><a href="#tab_5" onclick="loadTicking(8)" data-toggle="tab">出票</a></li>
                <li><a href="#tab_6" data-toggle="tab" onclick="loadreceivedata();">收/付款</a></li>
                <li><a href="#tab_7" data-toggle="tab" onclick="kaiInvoiceLoad();">发票</a></li>
                <li><a href="#tab_1" onclick="loadDataTable(9)" data-toggle="tab">关闭</a></li>
                <li class="orderLi"><button type="button" id="addOrder" class="btn btn-primary btn-sm right">添加订单</button></li>
              </ul>
                <!-- 当前所在的tab页 --> 
                <input type="hidden" id="status" name="status" value="">
              <div class="tab-content">
                  <div class="tab-pane pane-content active" id="tab_1"><!--全部-->
                    <div class="box-header">
                       <form role="form" class="form-horizontal">
                        <div class="form-group row marginBott cf">
                          <div class="col-md-1">
                            <input type="text" name="startdate" class="form-control TimeInput" placeholder="2017-02-20" onFocus="WdatePicker()"> 
                          </div>
                          <label class="col-md-1 labelClas">至</label>
                          <div class="col-md-1">
                            <input type="text" name="enddate" class="form-control TimeInput" placeholder="2017-02-22" onFocus="WdatePicker()">
                          </div>
                          <div class="col-md-3"><!-- 客户名称/订单号/联系人/PNR 搜索框 -->
                            <input type="text" name="searchInfo" class="form-control" placeholder="客户名称/订单号/联系人/PNR" onkeypress="onkeyEnter();">
                          </div>
                          <div class="col-md-1"><!-- 搜索 按钮 -->
                            <button id="searchOrder" type="button" class="btn btn-primary btn-sm">搜索</button>
                          </div>
                          
                        </div>
                      </form>
                    </div>
                    <div class="box-body">
                      <table id="internationalTable" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                          <th>序号</th>
                          <th>订单号</th>
                          <th>日期</th>
                          <th>航班号</th>
                          <th>航段</th>
                          <th>时间</th>
                          <th>人数</th>
                          <th>FOC</th>
                          <th>天数</th>
                          <th>状态</th>
                          <th>旅行社名称</th>
                          <th>联运要求</th>
                        </tr>
                        </thead>
                        <tbody class="tableTbody">
                        </tbody>
                      </table>
                    </div>
                  </div><!--end 全部-->
				<%@include file="/WEB-INF/admin/international/list/airlinetab.jspf"%>
                  <!--出票-->
                  <div class="tab-pane pane-content" id="tab_5">
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs nlkhUL">
                          <li class="active"><a href="#tab1_1" data-toggle="tab" onclick="loadTicking()">收款</a></li>
                          <li><a href="#tab1_2" data-toggle="tab" onclick="loadFukuanTable();">付款</a></li>
                        </ul>
                        <div class="tab-content padding0">
                            <div class="tab-pane pane-content active" id="tab1_1"><!--出票 收款-->
                              <div class="box-header">
                                 <form role="form" class="form-horizontal">
                                  <div class="form-group row marginBott5 cf">
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="startdate" class="form-control TimeInput" placeholder="2017-02-20" onFocus="WdatePicker()" onkeypress="onkeyTicketingEnter()"> 
                                    </div>
                                    <label class="col-md-1 labelClas">至</label>
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="enddate" class="form-control TimeInput" placeholder="2017-02-22" onFocus="WdatePicker()" onkeypress="onkeyTicketingEnter()">
                                    </div>
                                    <div class="col-md-3 textPadding"><!-- 客户名称/订单号/联系人/PNR 搜索框 -->
                                      <input type="text" name="searchInfo" class="form-control" placeholder="客户名称/订单号/联系人/PNR" onkeypress="onkeyTicketingEnter()">
                                    </div>
                                    <div class="col-md-2"><!-- 搜索 按钮 -->
                                      <button type="button" id="ticketingSearch" class="btn btn-primary btn-sm">搜索</button>
                                      <button type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
                                    </div>
                                    <div class="col-md-4"><!-- 收款 按钮 -->
                                      <button type="button" class="btn btn-primary btn-sm right fuKuanBtn">收款</button>
                                    </div>
                                  </div>
                                </form>
                              </div>
                              <div class="box-body">
                              	<input type="hidden" id="checkedboxval2" name="checkedboxval2" value="">
                                <table id="drawerPayTable" class="table table-bordered table-hover">
                                  <thead>
                                  <tr>
                                      <th class="checkTh"><input class="checkall2" type="checkbox"></th>
                                   	  <th>序号</th>
			                          <th>订单号</th>
			                          <th>日期</th>
			                          <th>航班号</th>
			                          <th>航段</th>
			                          <th>时间</th>
			                          <th>人数</th>
			                          <th>FOC</th>
			                          <th>天数</th>
			                          <th>状态</th>
			                          <th>旅行社名称</th>
			                          <th>联运要求</th>
                                  </tr>
                                  </thead>
                                  <tbody>
                                  </tbody>
                                </table>
                              </div>
                            </div><!--end 出票 收款-->
                            <div class="tab-pane pane-content" id="tab1_2"><!-- 出票 付款-->
                              <div class="box-header">
                                 <form role="form" class="form-horizontal">
                                  <div class="form-group row marginBott5 cf">
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="startdate" class="form-control TimeInput" placeholder="2017-02-20" onFocus="WdatePicker()">
                                    </div>
                                    <label class="col-md-1 labelClas">至</label>
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="enddate" class="form-control TimeInput" placeholder="2017-02-22" onFocus="WdatePicker()" onkeypress="onkeyTicketingEnter()">
                                    </div>
                                    <div class="col-md-3 textPadding"><!-- 客户名称/订单号/联系人/PNR 搜索框 -->
                                      <input type="text" name="searchInfo" class="form-control" placeholder="客户名称/订单号/联系人/PNR" onkeypress="onkeyTicketingEnter()">
                                    </div>
                                    <div class="col-md-2"><!-- 搜索 按钮 -->
                                      <button type="button" id="ticketingPaySearch" class="btn btn-primary btn-sm">搜索</button>
                                      <button type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
                                    </div>
                                    <div class="col-md-4"><!-- 付款 按钮 -->
                                      <button type="button" class="btn btn-primary btn-sm right fuKuanBtn1">付款</button>
                                    </div>
                                  </div>
                                </form>
                              </div>
                              <div class="box-body">
                              	<input type="hidden" id="checkedboxval1" name="checkedboxval1">
                                <table id="payTable" class="table table-bordered table-hover">
                                  <thead>
                                  <tr>
                                    <td class="FukuanCheckTh"><input class="checkall1" type="checkbox"></td>
                                      <th>序号</th>
			                          <th>订单号</th>
			                          <th>日期</th>
			                          <th>航班号</th>
			                          <th>航段</th>
			                          <th>时间</th>
			                          <th>人数</th>
			                          <th>FOC</th>
			                          <th>天数</th>
			                          <th>状态</th>
			                          <th>旅行社名称</th>
			                          <th>联运要求</th>
                                  </tr>
                                  </thead>
                                  <tbody>
                                  </tbody>
                                </table>
                              </div>
                            </div><!--end 出票 付款-->
                        </div>
                    </div>
                  </div><!--end 出票-->

                  <!--收/付款-->
                  <div class="tab-pane pane-content" id="tab_6">
                     <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs nlkhUL">
                          <li class="active"><a href="#tab1_a" data-toggle="tab" onclick="loadreceivedata();">收款</a></li>
                          <li><a href="#tab1_b" data-toggle="tab" onclick="shoufukuanPay();">付款</a></li>
                        </ul>
                        <div class="tab-content padding0">
                            <div class="tab-pane pane-content active" id="tab1_a"><!--收/付款 收款-->
                              <div class="box-header">
                                 <form role="form" class="form-horizontal">
                                  <div class="form-group row marginBott5 cf">
                                    <div class="col-md-1 textPadding">
                                      <select id="receivestatus" class="form-control TimeInput">
                                          <option value="">全部</option>
                                          <option value="1">收款中</option>
                                          <option value="2">已收款</option>
                                      </select>
                                    </div>
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="startdate" class="form-control TimeInput" placeholder="2017-02-20" onFocus="WdatePicker()">
                                    </div>
                                    <label class="col-md-1 labelClas">至</label>
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="enddate" class="form-control TimeInput" placeholder="2017-02-22" onFocus="WdatePicker()">
                                    </div>
                                    <div class="col-md-3 textPadding"><!-- 客户名称/订单号/联系人/PNR 搜索框 -->
                                      <input type="text" name="searchInfo" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
                                    </div>
                                    <div class="col-md-2"><!-- 搜索 按钮 -->
                                      <button type="button" id="receiveSearch" class="btn btn-primary btn-sm">搜索</button>
                                      <button type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
                                    </div>
                                  </div>
                                </form>
                              </div>
                              <div class="box-body">
                                <table id="shouFuKuanGatheringTable" class="table table-bordered table-hover">
                                  <thead>
                                  <tr>
                                    <th>订单号</th>
                                    <th>出发日期</th>
                                    <th>人数</th>
                                    <th>销售金额</th>
                                    <th>总额</th>
                                    <th>客户名称</th>
                                    <th>开票人</th>
                                    <th>订单状态</th>
                                    <th>状态</th>
                                    <th>备注</th>
                                    <th>操作</th>
                                  </tr>
                                  </thead>
                                  <tbody>
                                  </tbody>
                                </table>
                              </div>
                            </div><!--end 收/付款 收款-->
                            <div class="tab-pane pane-content" id="tab1_b"><!-- 收/付款 付款-->
                              <div class="box-header">
                                 <form role="form" class="form-horizontal">
                                  <div class="form-group row marginBott5 cf">
                                     <div class="col-md-1 textPadding">
                                      <select id="paystatus" class="form-control TimeInput">
                                          <option value="">全部</option>
                                          <option value="1">审批中</option>
                                          <option value="2">付款中</option>
                                          <option value="3">已付款</option>
                                      </select>
                                    </div>
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="startdate" class="form-control TimeInput" placeholder="2017-02-20" onFocus="WdatePicker()"> 
                                    </div>
                                    <label class="col-md-1 labelClas">至</label>
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="enddate" class="form-control TimeInput" placeholder="2017-02-22" onFocus="WdatePicker()">
                                    </div>
                                    <div class="col-md-3 textPadding"><!-- 客户名称/订单号/联系人/PNR 搜索框 -->
                                      <input type="text" name="searchInfo" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
                                    </div>
                                    <div class="col-md-2"><!-- 搜索 按钮 -->
                                      <button type="button" id="paySearch" class="btn btn-primary btn-sm">搜索</button>
                                      <button type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
                                    </div>
                                  </div>
                                </form>
                              </div>
                              <div class="box-body">
                                <table id="shouFuKuanPayTable" class="table table-bordered table-hover">
                                  <thead>
                                    <tr>
	                                    <th>订单号</th>
	                                    <th>付款日期</th>
	                                    <th>人数</th>
	                                    <th>金额</th>
	                                    <th>客户名称</th>
	                                    <th>开票人</th>
	                                    <th>订单状态</th>
	                                    <th>状态</th>
	                                    <th>备注</th>
	                                    <th>操作</th>
                                    </tr>
                                  </thead>
                                  <tbody>
                                  </tbody>
                                </table>
                              </div>
                            </div><!--end 收/付款 付款-->
                        </div>
                    </div>
                  </div><!--end 收/付款-->
                  
                  <!--发票-->
                  <div class="tab-pane pane-content" id="tab_7">
                     <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs nlkhUL">
                          <li class="active"><a href="#tab7_a" data-toggle="tab" onclick="kaiInvoiceLoad()">开发票</a></li>
                          <li><a href="#tab7_b" data-toggle="tab" onclick="shouInvoiceLoad()">收发票</a></li>
                        </ul>
                        <div class="tab-content padding0">
                            <div class="tab-pane pane-content active" id="tab7_a"><!--发票 开发票-->
                              <div class="box-header">
                                 <form role="form" class="form-horizontal">
                                  <div class="form-group row marginBott5 cf">
                                    <div class="col-md-1 textPadding">
                                      <select id="kaiinvoicestatus" class="form-control TimeInput">
                                          <option value="">全部</option>
                                          <option value="1">开发票中</option>
                                          <option value="2">已开发票</option>
                                      </select>
                                    </div>
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="startdate" class="form-control TimeInput" placeholder="2017-02-20" onFocus="WdatePicker()"> 
                                    </div>
                                    <label class="col-md-1 labelClas">至</label>
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="enddate" class="form-control TimeInput" placeholder="2017-02-22" onFocus="WdatePicker()">
                                    </div>
                                    <div class="col-md-3 textPadding"><!-- 客户名称/订单号/联系人/PNR 搜索框 -->
                                      <input type="text" name="searchInfo" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
                                    </div>
                                    <div class="col-md-2"><!-- 搜索 按钮 -->
                                      <button type="button" id="kaiInvoiceSearch" class="btn btn-primary btn-sm">搜索</button>
                                      <!-- <button type="button" class="btn btn-primary btn-sm ckBtn">清空</button> -->
                                    </div>
                                  </div>
                                </form>
                              </div>
                              <div class="box-body">
                                <table id="kaiInvoiceTable" class="table table-bordered table-hover">
                                  <thead>
                                  <tr>
                                    <th>订单号</th>
                                    <th>发票号</th>
                                    <th>发票金额</th>
                                    <th>总额</th>
                                    <th>开发票日期</th>
                                    <th>发票开具项目</th>
                                    <th>付款单位</th>
                                    <th>开票人</th>
                                    <th>订单状态</th>
                                    <th>状态</th>
                                    <th>备注</th>
                                    <th>操作</th>
                                  </tr>
                                  </thead>
                                  <tbody>
                                  </tbody>
                                </table>
                              </div>
                            </div><!--end 发票 开发票-->
                            <div class="tab-pane pane-content" id="tab7_b"><!--发票 收发票-->
                              <div class="box-header">
                                 <form role="form" class="form-horizontal">
                                  <div class="form-group row marginBott5 cf"> 
                                     <div class="col-md-1 textPadding">
                                      <select id="shouinvoicestatus" class="form-control TimeInput">
                                          <option value="">全部</option>
                                          <option value="3">收发票中</option>
                                          <option value="4">已收发票</option>
                                      </select>
                                    </div>
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="startdate" class="form-control TimeInput" placeholder="2017-02-20" onFocus="WdatePicker()">
                                    </div>
                                    <label class="col-md-1 labelClas">至</label>
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="enddate" class="form-control TimeInput" placeholder="2017-02-22" onFocus="WdatePicker()">
                                    </div>
                                    <div class="col-md-3 textPadding"><!-- 客户名称/订单号/联系人/PNR 搜索框 -->
                                      <input type="text" name="searchInfo" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
                                    </div>
                                    <div class="col-md-2"><!-- 搜索 按钮 -->
                                      <button type="button" id="shouInvoiceSearch" class="btn btn-primary btn-sm">搜索</button>
<!--                                       <button type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
 -->                                    </div>
                                  </div>
                                </form>
                              </div>
                              <div class="box-body">
                                <table id="shouInvoiceTable" class="table table-bordered table-hover">
                                  <thead>
                                    <tr>
	                                    <th>订单号</th>
	                                    <th>发票号</th>
	                                    <th>发票金额</th>
	                                    <th>总额</th>
	                                    <th>开发票日期</th>
	                                    <th>发票开具项目</th>
	                                    <th>付款单位</th>
	                                    <th>开票人</th>
	                                    <th>订单状态</th>
	                                    <th>状态</th>
	                                    <th>备注</th>
	                                    <th>操作</th>
                                    </tr>
                                  </thead>
                                  <tbody>
                                  </tbody>
                                </table>
                              </div>
                            </div><!--end 发票 收发票-->
                        </div>
                    </div>
                  </div><!--end 出票-->

              </div><!-- end tab-content -->
          </div><!-- end nav-tabs-custom -->
        </div>
      </div>
    </section>
  </div>
  <%@include file="/WEB-INF/public/footer.jsp"%>
</div>
<script type="text/javascript">
	var BASE_PATH = '${base}';
</script>
<!--小日历-->
<script src="${base}/public/build/kalendae.standalone.js" type="text/javascript" charset="utf-8"></script>
<script src="${base}/public/build/calendar.js" type="text/javascript"></script>
<!-- select2 -->
<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
<script src="${base }/admin/order/ordercommon.js"></script>
<script src="${base}/admin/airline/planmake.js"></script>
<script src="${base}/admin/airline/editplan.js"></script>
<script src="${base}/admin/international/international.js"></script>
<script src="${base}/admin/international/internationalticketing.js"></script>
<script src="${base}/admin/international/internationalticketingpay.js"></script>
<script src="${base}/admin/international/internationalpayreceive.js"></script>
<script src="${base}/admin/international/internationalinvoice.js"></script>
<script type="text/javascript"> 
//添加订单 弹框
	$('#addOrder').click(function(){
	    layer.open({
	        type: 2,
	        title:false,
	        skin: false, //加上边框
	        closeBtn:false,//默认 右上角关闭按钮 是否显示
	        shadeClose:true,
	        scrollbar: false,
	        area: ['1087px', '553px'],
	        content: '${base}/admin/international/addInterOrder.html'
	      });
	  });
	  
	//他页面回调
	  function successCallback(id){
		  internationalTable.ajax.reload();
		  if(id == '1'){
			  layer.msg("添加成功",{time: 2000});
		  }else if(id == '2'){
			  layer.msg("编辑成功",{time: 2000});
		  }else if(id == '3'){
			  layer.msg("关闭成功",{time: 2000});
		  }else if(id == '4'){
			  layer.msg("启用成功",{time: 2000});
		  }else if(id == '5'){
			  layer.msg("提交成功",{time: 2000});
		  }
	  }
</script>
