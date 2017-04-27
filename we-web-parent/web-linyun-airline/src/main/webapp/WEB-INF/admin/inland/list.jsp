<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<link rel="stylesheet" type="text/css" href="${base }/public/dist/css/inlandCross.css">
<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row row-top">
        <div class="col-xs-12">
         <div class="nav-tabs-custom">
              <ul class="nav nav-tabs">
                <li class="active"><a href="#tab_1" onclick="loadDataTable(0)" data-toggle="tab">全部</a></li>
                <li><a href="#tab_1" onclick="loadDataTable(1)" data-toggle="tab">查询</a></li>
                <li><a href="#tab_1" onclick="loadDataTable(2)" data-toggle="tab">预订</a></li>
                <li><a href="#tab_1" onclick="loadDataTable(3)" data-toggle="tab">开票</a></li>
                <li><a href="#tab_5" onclick="loadTicking()" data-toggle="tab">出票</a></li>
                <li><a href="#tab_6" onclick="loadshouFuKuanGatheringTable()" data-toggle="tab">收/付款</a></li>
                <li><a href="#tab_7" data-toggle="tab" onclick="kaiInvoiceLoad();">发票</a></li>
                <li><a href="#tab_1" onclick="loadDataTable(5)" data-toggle="tab">关闭</a></li>
                <li class="orderLi"><button type="button" id="addOrderGJ" class="btn btn-primary btn-sm right">添加订单</button></li>
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
                      <table id="inlandCrossTable" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                          <th>订单</th>
                          <th>PNR</th>
                          <th>日期</th>
                          <th>航班号</th>
                          <th>航段</th>
                          <th>时间</th>
                          <th>销售单价</th>
                          <th>销售总价</th>
                          <th>人头数</th>
                          <th>状态</th>
                          <th>联系人</th>
                          <th>电话</th>
                          <!-- <th>操作</th> -->
                        </tr>
                        </thead>
                        <tbody class="tableTbody">
                        </tbody>
                      </table>
                    </div>
                  </div><!--end 全部-->

                  <!--出票-->
                  <div class="tab-pane pane-content" id="tab_5">
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs nlkhUL">
                          <li class="active"><a href="#tab1_1" data-toggle="tab">收款</a></li>
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
                              	<input type="hidden" id="checkedboxval" name="checkedboxval">
                                <table id="drawerPayTable" class="table table-bordered table-hover">
                                  <thead>
                                  <tr>
                                    <th class="checkTh"><input class="checkall" type="checkbox"></th>
                                    <th>订单号</th>
                                    <th>PNR</th>
                                    <th>日期</th>
                                    <th>航班号</th>
                                    <th>航段</th>
                                    <th>时间</th>
                                    <th>销售单价</th>
                                    <th>实收合计</th>
                                    <th>人头数</th>
                                    <th>状态</th>
                                    <th>联系人</th>
                                    <th>电话</th>
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
                                      <input type="text" name="startdate" class="form-control TimeInput" onFocus="WdatePicker()" placeholder="2017-02-20"> 
                                    </div>
                                    <label class="col-md-1 labelClas">至</label>
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="enddate" class="form-control TimeInput" onFocus="WdatePicker()" placeholder="2017-02-22">
                                    </div>
                                    <div class="col-md-3 textPadding"><!-- 客户名称/订单号/联系人/PNR 搜索框 -->
                                      <input type="text" name="searchInfo" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
                                    </div>
                                    <div class="col-md-2"><!-- 搜索 按钮 -->
                                      <button type="button" class="btn btn-primary btn-sm ticketpaysearch">搜索</button>
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
                                    <th>订单号</th>
                                    <th>PNR</th>
                                    <th>日期</th>
                                    <th>航班号</th>
                                    <th>航段</th>
                                    <th>时间</th>
                                    <th>成本单价</th>
                                    <th>成本总价</th>
                                    <th>人头数</th>
                                    <th>状态</th>
                                    <th>联系人</th>
                                    <th>电话</th>
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
                          <li class="active"><a href="#tab1_a" data-toggle="tab" onclick="loadshouFuKuanGatheringTable()">收款</a></li>
                          <li><a href="#tab1_b" data-toggle="tab" onclick="shoufukuanPay();">付款</a></li>
                        </ul>
                        <div class="tab-content padding0">
                            <div class="tab-pane pane-content active" id="tab1_a"><!--收/付款 收款-->
                              <div class="box-header">
                                 <form role="form" class="form-horizontal">
                                  <div class="form-group row marginBott5 cf">
                                    <div class="col-md-1 textPadding">
                                      <select class="form-control TimeInput" name="status">
                                          <option value="">全部</option>
                                          <option value="1">收款中</option>
                                          <option value="2">已收款</option>
                                      </select>
                                    </div>
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="startdate" class="form-control TimeInput" onFocus="WdatePicker()" placeholder="2017-02-20"> 
                                    </div>
                                    <label class="col-md-1 labelClas">至</label>
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="enddate" class="form-control TimeInput" onFocus="WdatePicker()" placeholder="2017-02-22">
                                    </div>
                                    <div class="col-md-3 textPadding"><!-- 客户名称/订单号/联系人/PNR 搜索框 -->
                                      <input type="text" name="searchInfo" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
                                    </div>
                                    <div class="col-md-2"><!-- 搜索 按钮 -->
                                      <button type="button" class="btn btn-primary btn-sm shoukuansearch">搜索</button>
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
                                    <th>出票人</th>
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
                                      <select class="form-control TimeInput" name="status">
                                          <option value="">全部</option>
                                          <option value="1">审批中</option>
                                          <option value="2">付款中</option>
                                          <option value="3">已付款</option>
                                      </select>
                                    </div>
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="startdate" class="form-control TimeInput" onFocus="WdatePicker()" placeholder="2017-02-20"> 
                                    </div>
                                    <label class="col-md-1 labelClas">至</label>
                                    <div class="col-md-1 textPadding">
                                      <input name="enddate" type="text" class="form-control TimeInput" onFocus="WdatePicker()" placeholder="2017-02-22">
                                    </div>
                                    <div class="col-md-3 textPadding"><!-- 客户名称/订单号/联系人/PNR 搜索框 -->
                                      <input type="text" name="searchInfo" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
                                    </div>
                                    <div class="col-md-2"><!-- 搜索 按钮 -->
                                      <button type="button" class="btn btn-primary btn-sm fukuansearch">搜索</button>
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
                                      <th>PNR</th>
                                      <th>航班号</th>
                                      <th>航段</th>
                                      <th>日期</th>
                                      <th>时间</th>
                                      <th>收款单位</th>
                                      <th>成本总价</th>
                                      <th>人数</th>
                                      <th>状态</th>
                                      <th>出票人</th>
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
                                      <select class="form-control TimeInput" name="status">
                                          <option value="">全部</option>
                                          <option value="1">开发票中</option>
                                          <option value="2">已开发票</option>
                                      </select>
                                    </div>
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="startdate" class="form-control TimeInput" onFocus="WdatePicker()" placeholder="2017-02-20"> 
                                    </div>
                                    <label class="col-md-1 labelClas">至</label>
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="enddate" class="form-control TimeInput" onFocus="WdatePicker()" placeholder="2017-02-22">
                                    </div>
                                    <div class="col-md-3 textPadding"><!-- 客户名称/订单号/联系人/PNR 搜索框 -->
                                      <input type="text" name="searchInfo" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
                                    </div>
                                    <div class="col-md-2"><!-- 搜索 按钮 -->
                                      <button type="button" class="btn btn-primary btn-sm openinvoicesearch">搜索</button>
                                      <button type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
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
                                    <th width="100px">开发票日期</th>
                                    <th>发票开具项目</th>
                                    <th>付款单位</th>
                                    <th>开票人</th>
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
                                      <select class="form-control TimeInput" name="status">
                                          <option value="">全部</option>
                                          <option value="3">收发票中</option>
                                          <option value="4">已收发票</option>
                                      </select>
                                    </div>
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="startdate" class="form-control TimeInput" onFocus="WdatePicker()" placeholder="2017-02-20"> 
                                    </div>
                                    <label class="col-md-1 labelClas">至</label>
                                    <div class="col-md-1 textPadding">
                                      <input type="text" name="enddate" class="form-control TimeInput" onFocus="WdatePicker()" placeholder="2017-02-22">
                                    </div>
                                    <div class="col-md-3 textPadding"><!-- 客户名称/订单号/联系人/PNR 搜索框 -->
                                      <input type="text" name="searchInfo" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
                                    </div>
                                    <div class="col-md-2"><!-- 搜索 按钮 -->
                                      <button type="button" class="btn btn-primary btn-sm receiveinvoicesearch">搜索</button>
                                      <button type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
                                    </div>
                                  </div>
                                </form>
                              </div>
                              <div class="box-body">
                                <table id="shouInvoiceTable" class="table table-bordered table-hover">
                                  <thead>
                                    <tr>
                                      <th>订单号</th>
                                      <th>PNR</th>
                                      <th>人数</th>
                                      <th>发票数</th>
                                      <th>总金额</th>
                                      <th>收发票日期</th>
                                      <th>发票开具项目</th>
                                      <th>收款单位</th>
                                      <th>开票人</th>
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
<script src="${base }/admin/order/ordercommon.js"></script>
<script src="${base}/admin/order/inland.js"></script>
<script src="${base}/admin/order/ticketing.js"></script>
<script src="${base}/admin/order/ticketingpay.js"></script>
<script src="${base}/admin/order/shouFuKuan.js"></script>
<script src="${base}/admin/order/invoice.js"></script>
<script type="text/javascript"> 
//添加订单 弹框
	$('#addOrderGJ').click(function(){
	    layer.open({
	        type: 2,
	        title:false,
	        skin: false, //加上边框
	        closeBtn:false,//默认 右上角关闭按钮 是否显示
	        shadeClose:true,
	        scrollbar: false,
	        area: ['1137px', '553px'],
	        content: '${base}/admin/inland/addOrder.html'
	      });
	  });
	  
	//他页面回调
	  function successCallback(id){
		  inlandCrossTable.ajax.reload(function(json){
				autoHighLoad($('#inlandCrossTable'));
			},false);
		  shouFuKuanPayTable.ajax.reload(function(json){
				autoHighLoad($('#shouFuKuanPayTable'));
			});
		  shouFuKuanGatheringTable.ajax.reload(function(json){
				autoHighLoad($('#shouFuKuanGatheringTable'));
			});
		  if(id == '1'){
			  layer.msg("添加成功",{time: 2000});
		  }else if(id == '2'){
			  layer.msg("修改成功",{time: 2000});
		  }else if(id == '3'){
			  layer.msg("关闭成功",{time: 2000});
		  }else if(id == '4'){
			  layer.msg("启用成功",{time: 2000});
		  }else if(id == '5'){
			  layer.msg("提交成功",{time: 2000});
		  }
	  }
</script>

<script type="text/javascript">
//table 内容  垂直居中
  $(function(){
	 /* $.each($(".tableTbody tr"),function(){
		 alert("ljashgdfb");
	 }) */
    

   $('.tableTbody tr').each(function () {//出票 table      
       $(this).children('td').each(function(){
          var liLength = $(this).children('ul').find("li").length;
          if(liLength==1){
            $(this).children('ul').find("li").addClass('eq');
          }else if(liLength==2){
            $(this).children('ul').find("li").eq(1).addClass('eq1');
            $(this).children('ul').find("li").eq(0).addClass('eq0');
          }else if(liLength==2){
            $(this).children('ul').find("li").eq(2).addClass('eq2');
          }
       });
    });

  });
  $('#searchOrder').click(function(){
		var div = $(this).parent().parent();
		var startdate = div.find('[name=startdate]').val();
		var enddate = div.find('[name=enddate]').val();
		var searchInfo = div.find('[name=searchInfo]').val();
		var status = $('#status').val();
		var param = {
				ordersstatus:status,
				startdate:startdate,
				enddate:enddate,
				searchInfo:searchInfo
		};
		inlandCrossTable.settings()[0].ajax.data = param;
		inlandCrossTable.ajax.reload(function(json){
			autoHighLoad($('#inlandCrossTable'));
		});
  });
  
  function onkeyEnter(){
	 var e = window.event || arguments.callee.caller.arguments[0];
     if(e && e.keyCode == 13){
  		$('#searchOrder').click();
     }
  }
</script>
