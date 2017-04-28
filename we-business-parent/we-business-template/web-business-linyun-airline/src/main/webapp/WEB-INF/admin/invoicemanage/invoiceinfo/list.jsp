<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<link rel="stylesheet" type="text/css" href="${base }/public/dist/css/invoiceInterManage.css">
 <!-- Content-->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row row-top">
        <div class="col-xs-12">
          
          <div class="nav-tabs-custom">
              <ul class="nav nav-tabs">
                <li class="active"><a href="#tab_1" data-toggle="tab">开发票</a></li>
                <li><a href="#tab_2" data-toggle="tab">收发票</a></li>
              </ul>
              <div class="tab-content">
                  <div class="tab-pane pane-content active" id="tab_1"><!--开发票-->
                    <div class="box-header">
                          <form role="form" class="form-horizontal">
                           <div class="form-group row marginBott5 cf">
                             <div class="col-md-1 textPadding">
                               <select id="kaiInvoiceSelect" name="status" class="form-control TimeInput">
                                   <option value=" ">全部</option>
                                   <option value="1">开发票中</option>
                                   <option value="2">已开发票</option>
                               </select>
                             </div>
                             <div class="col-md-1 textPadding">
                               <select id="kaibilluserid" name="billuserid" class="form-control TimeInput">
                                   <option value="">开票人</option>
				                    <c:forEach items="${obj.listIssuer}" var="one">
				                    	<option value="${one.billuserid }">
			                            	${one.fullName }
			                            </option>
			                        </c:forEach>
                               </select>
                             </div>
                             <div class="col-md-1 textPadding">
                               <input id="kaiInvoiceBeginDate" name="invoicedate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'kaiInvoiceEndDate\')}'})" class="form-control TimeInput" placeholder="2017-02-20"> 
                             </div>
                             <label class="col-md-1 labelClas">至</label>
                             <div class="col-md-1 textPadding">
                               <input id="kaiInvoiceEndDate" name="invoicedate"  type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'kaiInvoiceBeginDate\')}'})" class="form-control TimeInput" placeholder="2017-02-22">
                             </div>
                             <div class="col-md-3 textPadding"><!-- 发票号/单位 搜索框 -->
                               <input id="invoicenumId" name="invoicenum" type="text" class="form-control" placeholder="发票号/单位">
                             </div>
                             <div class="col-md-2"><!-- 搜索 按钮 -->
                               <button id="kaiSearchInvoiceBtn" onclick="kaiInvoiceSelectData();" type="button" class="btn btn-primary btn-sm">搜索</button>
                               <button id="kaiEmptyBtn" type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
                             </div>
                           </div>
                         </form>
                    </div>
                    <div class="box-body">
                         <table id="KaiInvoiceTable1" class="table table-bordered table-hover">
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
                             <th>状态</th>
                             <th>备注</th>
                             <th>操作</th>
                           </tr>
                           </thead>
                           <tbody>
                           </tbody>
                         </table>
                    </div>
                  </div><!--end 开发票-->
                  
 <!-----------------------------------------------------------------------收发票----------------------------------------------------------------------------------------------------------->
                  <div class="tab-pane pane-content" id="tab_2">
                    <div class="box-header">
                        <form role="form" class="form-horizontal">
                          <div class="form-group row marginBott5 cf">
                            <div class="col-md-1 textPadding">
                              <select id="shouInvoiceSelect" class="form-control TimeInput">
                                  <option value=" ">全部</option>
                                  <option value="3">收发票中</option>
                                  <option value="4">已收发票</option>
                              </select>
                            </div>
                            <div class="col-md-1 textPadding">
                              <select id="shoubilluserid" name="billuserid" class="form-control TimeInput">
                                  <option value="">收票人</option>
				                    <c:forEach items="${obj.listIssuer}" var="one" varStatus="indexs">
				                    	<option value="${one.billuserid }">
			                            	${one.fullName }
			                            </option>
			                        </c:forEach>
                              </select>
                            </div>
                            <div class="col-md-1 textPadding">
                              <input id="shouInvoiceBeginDate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'shouInvoiceEndDate\')}'})" class="form-control TimeInput" placeholder="2017-02-20"> 
                            </div>
                            <label class="col-md-1 labelClas">至</label>
                            <div class="col-md-1 textPadding">
                              <input id="shouInvoiceEndDate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'shouInvoiceBeginDate\')}'})"  class="form-control TimeInput" placeholder="2017-02-22">
                            </div>
                            <div class="col-md-3 textPadding"><!-- PNR/单位 搜索框 -->
                              <input id="paymentunitId" type="text" class="form-control" placeholder="PNR/单位">
                            </div>
                            <div class="col-md-2"><!-- 搜索 按钮 -->
                              <button id="shouSearchInvoiceBtn" type="button" class="btn btn-primary btn-sm">搜索</button>
                              <button id="shouEmptyBtn" type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
                            </div>
                          </div>
                        </form>
                    </div>
                    <div class="box-body">
                         <table id="shouInvoiceTable1" class="table table-bordered table-hover">
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
                             <th>收票人</th>
                             <th>状态</th>
                             <th>备注</th>
                             <th>操作</th>
                           </tr>
                           </thead>
                           <tbody>
                           </tbody>
                         </table>
                    </div>
                  </div><!--end 收发票-->
              </div><!-- end tab-content -->
          </div><!-- end nav-tabs-custom -->
        </div>
      </div>
    </section>
  </div>
<!-- Footer -->
<%@include file="/WEB-INF/public/footer.jsp"%>
<!--end footer-->
<script type="text/javascript">
	var BASE_PATH = '${base}';
</script>
<script src="${base }/admin/order/ordercommon.js"></script>
<script src="${base}/admin/invoiceinfo/invoiceinfo.js"></script>
<!-- My97DatePicker -->
<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
//他页面回调
function successCallback(id){
	  KaiInvoiceTable1.ajax.reload(function(json){
			autoHighLoad($('#KaiInvoiceTable1'));
		},false);
	  shouInvoiceTable1.ajax.reload(function(json){
			autoHighLoad($('#shouInvoiceTable1'));
		},false);
	  if(id == '1'){
		  layer.msg("确认开发票成功",{time: 2000});
	  }else if(id == '2'){
		  layer.msg("确认收发票成功",{time: 2000});
	  }
}
$(function(){
	$('.menu-ul:eq(0)').hide(); 
	$('.menu-ul:eq(1) li:eq(0) a').css("color","rgb(245, 245, 245)");
});
</script>