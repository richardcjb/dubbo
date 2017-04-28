<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!--小日历-->
<link rel="stylesheet" type="text/css" href="${base }/public/build/kalendae.css">
<link rel="stylesheet" type="text/css" href="${base }/public/dist/css/airlinesModule.css">
<c:set var="url" value="${base}/admin/customneeds" />
  <!-- Content Wrapper. Contains page content -->
	<!--内容-->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
      <div class="row row-top">
        <div class="col-xs-12">
             <!-- <h4 class="page-header"><i class="fa fa-plane"></i> 航空公司模块</h4> -->
              <div class="row">
                <div class="col-md-12">
                  <!-- Custom Tabs -->
                  <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                      <li class="active"><a href="#tab_1" data-toggle="tab">客户需求</a></li>
                      <!-- <li><a href="#tab_2" data-toggle="tab">计划制作</a></li>
                      <li><a href="#tab_3" data-toggle="tab">编辑计划</a></li> -->
                    </ul>
                    <div class="tab-content">

                      <!--客户需求-->
                      <div class="tab-pane pane-content active labelMar" id="tab_1">
                       
                        <!--默认/关闭 下拉框 and 按钮（添加、导入、导出....）-->
                        <div class="form-group row">
                          <div class="col-md-12 btnTop textPadding">
                            <button type="button" class="btn btn-primary btn-sm right" onclick="add();">添加</button>
                            <a class="btn btn-primary btn-sm right" id="exportExcelId" target="hidden_frame" href="${base}/admin/customneeds/exportCustomNeedsExcel.html">导出excel</a>
                            <form id="uploadExcelForm" action="${url }/inportExcelData.html" name="form3" enctype="multipart/form-data" method="post" target="hidden_frame" style="display: inline;">
	                            <p class="flie_A right">导入excel<input name="excelFile" id="excelFile" onchange="javascript:onfileChange();" type="file"/></p>
								<iframe name='hidden_frame' id='hidden_frame' style="display: none"></iframe>
							</form>
                            <a class="btn btn-primary btn-sm right" target="hidden_frame" href="${url }/downloadTemplate.html">模板</a>
                          </div>
                        </div><!--end 默认/关闭 下拉框 and 按钮（添加、导入、导出....）-->
                        
					<form id="searchForm">
                        <!--航空公司/旅行社/人数/天数 文本框-->
                        <div class="form-group row">
                          <label class="col-sm-1 text-right padding">状态：</label>
                          <div class="col-md-1 padding">
                            <select class="form-control" id="isclose" onchange="selectDept();">
                              <option value="0">默认</option>
                              <option value="1">关闭</option>
                            </select>
                          </div>
                          <label class="col-sm-1 text-right padding">航空公司：</label>
                          <div class="col-sm-1 padding">
                            <input type="text" name="airline" id="airline" onblur="this.value=this.value.toUpperCase().replace(/(^\s*)|(\s*$)/g, '')" class="form-control input-sm" placeholder="" onkeypress="onkeyEnter();">
                          </div>
                          <label class="col-sm-1 text-right padding">旅行社：</label>
                          <div class="col-sm-1 padding">
                            <input type="text" class="form-control input-sm" name="travel" id="travel"  onkeypress="onkeyEnter();" placeholder="">
                          </div>
                          <label class="col-sm-1 text-right padding">人数：</label>
                          <div class="col-sm-1 padding">
                            <input type="text" class="form-control input-sm" name="totalcount" id="totalcount" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeypress="onkeyEnter();" placeholder="">
                          </div>
                          <label class="col-sm-1 text-right padding">天数：</label>
                          <div class="col-sm-1 padding">
                            <input type="text" class="form-control input-sm" name="totalday" id="totalday" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeypress="onkeyEnter();" placeholder="">
                          </div>
                        </div><!--end 航空公司/旅行社/人数/天数 文本框-->
                        
                        <!--去程日期/去程航段/回程日期/回程航段 文本框-->
                        <div class="form-group row">
                          <label class="col-sm-1 text-right padding">去程日期：</label>
                          <div class="col-sm-1 padding">
                            <input type="text" class="form-control input-sm inputdatestr" name="leavedate" id="leavedate" onkeypress="onkeyEnter();" onFocus="WdatePicker({minDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'backdate\')}'})" placeholder="">
                          </div>
                          <label class="col-sm-1 text-right padding">起飞城市：</label>
                          <div class="col-sm-1 padding">
                            <input type="text" class="form-control input-sm" name="leavecity" id="leavecity"  placeholder="" onkeypress="onkeyEnter();">
                          </div>
                          <label class="col-sm-1 text-right padding inputdatestr">回程日期：</label>
                          <div class="col-sm-1 padding">
                            <input type="text" class="form-control input-sm" name="backdate" id="backdate" onkeypress="onkeyEnter();" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'leavedate\')}'})" placeholder="">
                          </div>
                          <label class="col-sm-1 text-right padding">降落城市：</label>
                          <div class="col-sm-1 padding">
                            <input type="text" class="form-control input-sm" name="backcity" id="backcity"  placeholder="" onkeypress="onkeyEnter();">
                          </div>
                          <div class="col-md-3">
                            <button id="searchBtn" type="button" class="btn btn-primary btn-sm">搜索</button>
                            <button id="resetBtn" type="button" class="btn btn-primary btn-sm btn-left">恢复默认</button>
                          </div>
                        </div><!--end 去程日期/去程航段/回程日期/回程航段 文本框-->
                       </form>
                         <table id="datatables" class="table table-bordered table-hover">
                          <thead>
                          <tr>
                            <th>序号</th>
                            <th>航空公司名称</th>
                            <th>去程日期</th>
                            <th>起飞城市</th>
                            <th>回程日期</th>
                            <th>降落城市</th>
                            <th>人数</th>
                            <th>天数</th>
                            <th>旅行社名称</th> 
                            <th>联运要求</th>
                            <th>操作</th>
                          </tr>
                          </thead>
                          <tbody>
                          </tbody>
                        </table>
                      </div><!--end 客户需求-->

                      <!--计划制作-->
                      <%-- <div class="tab-pane pane-content labelMar" id="tab_2">
                        
                        <!--系列团/临时团 select and 按钮（导出、保存计划）-->
                        <div class="form-group row">
                          <label class="col-sm-1 text-right padding marTop5">状态：</label>
                          <div class="col-md-1 padding">
                            <select class="form-control select1" id="teamtype" onchange="changeType();">
                              <option value="1">系列团</option>
                              <option value="2">临时团</option>
                            </select>
                          </div>
                          <div class="col-md-10">
                            <button type="button" onclick="makePlan();" class="btn btn-primary btn-sm right">制作</button>
                            <button type="button" class="btn btn-primary btn-sm right" onclick="savePlan();">保存计划</button>
                            </p>  
                            <div class="dropdown dropdoImport">
                                <button class="btn btn-primary dropdown-toggle btn-sm right" type="button" data-toggle="dropdown">导出excel</button>
                                    <ul class="dropdown-menu dropdown-menu-left" role="menu" aria-labelledby="dropdownMenu1">
                                       <li role="presentation">
                                          <a class="flie_A flie_import" target="hidden_frame" href="${url}/exportDongHangTemplate.html">东航</a>
                                       </li>
                                       <li role="presentation">
                                          <a class="flie_A flie_import" target="hidden_frame" href="${url}/exportNanHangTemplate.html">南航</a>
                                       </li>
                                       <li role="presentation">
                                          <a class="flie_A flie_import" target="hidden_frame" href="${url}/exportXinHangTemplate.html">新航</a>
                                       </li>
                                       <li role="presentation">
                                          <a class="flie_A flie_import" target="hidden_frame" href="${url}/exportGuoTaiTemplate.html">国泰</a>
                                       </li>
                                       <li role="presentation">
                                          <a class="flie_A flie_import" target="hidden_frame" href="${url}/exportLingYunTemplate.html">凌云</a>
                                       </li>
                                    </ul>
                            </div>
                          </div>
                        </div><!--end 系列团/临时团 select and 按钮（导出、保存计划）-->
                        
                        <div class="addMake aa">
                          <a href="javascript:;" name="addButton" class="glyphicon glyphicon-plus addIcon removAddMake"></a><!--添加div按钮-->
                          <!--旅行社名称/人数/天数/去程航班/回程航班 text-->
                          <div class="form-group row">
                            <label class="col-sm-1 text-right padding">旅行社：</label>
                            <div class="col-sm-1 padding">
                              <!-- <select id="tranvelname" onclick="loadTranvel();" class="form-control input-sm chzn-select" data-placeholder="旅行社名称" tabindex="7">
                              </select> -->
                              <select id="travelname0" name="travelname" class="form-control select2" multiple="multiple" data-placeholder="旅行社名称">
								</select>
                            </div>
                            <label class="col-sm-1 text-right padding">人数：</label>
                            <div class="col-sm-1 padding">
                              <input type="text" name="peoplecount" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" class="form-control input-sm" placeholder="">
                            </div>
                            <label class="col-sm-1 text-right padding">天数：</label>
                            <div class="col-sm-1 padding">
                              <input type="text" name="dayscount" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" class="form-control input-sm" placeholder="">
                            </div>
                            <label class="col-sm-1 text-right padding cf">联运城市：</label>
                            <div class="col-sm-1 padding">
                              <select id="unioncity0" name="unioncity" class="form-control input-sm js-data-example-ajax" multiple="multiple" placeholder=""></select>
                            </div>
                            <!-- <div class="col-sm-1">
                              <a href="javascript:;" class="glyphicon glyphicon-plus addIcon"></a>
                            </div> -->
                          </div><!--end 旅行社名称/人数/天数/去程航班/回程航班 text-->
                          

                          <!--起飞城市/降落城市/联运城市 text-->
                          <div class="form-group row addCityAirline">
                            <label class="col-sm-1 text-right padding cf">出发城市：</label>
                            <div class="col-sm-1 padding">
                              <select id="leavescity0" name="leavescity" class="form-control input-sm select2" multiple="multiple" placeholder=""></select>
                            </div>
                            <label class="col-sm-1 text-right padding cf">抵达城市：</label>
                            <div class="col-sm-1 padding">
                              <select id="backscity0" name="backscity" class="form-control input-sm select2" multiple="multiple" placeholder=""></select>
                            </div>
                             <label class="col-sm-1 text-right padding">去程航班：</label>
                            <div class="col-sm-1 padding">
                              <select id="leaveairline0" name="leaveairline" class="form-control input-sm select2" multiple="multiple" placeholder=""></select>
                            </div>
                            <div name="teamtypehide">
	                            <label class="col-sm-1 text-right padding">出发日期：</label>
	                            <div class="col-sm-1 padding">
	                              <input id="setoffdate0" name="setoffdate" type="text" onFocus="WdatePicker({minDate:'%y-%M-%d'})" class="form-control input-sm inputdatestr" placeholder=""/>
	                            </div>
	                            <label class="col-sm-1 text-right padding">时间：</label>
	                            <div class="col-sm-1 padding">
	                              <input id="setofftime" name="setofftime" type="text" class="form-control input-sm inputtime" placeholder=""/>
	                            </div>
	                            <a href="javascript:;" name="addButton" class="glyphicon glyphicon-plus addNeeds"></a><!--添加div按钮-->
                            </div>
                          </div><!--end 起飞城市/降落城市/联运城市 text-->

                          <!--time 选择-->
                          <div class="form-group row">
                          	<label class="col-sm-1 text-right padding cf">FOC：</label>
                            <div class="col-sm-1 padding">
                              <select id="foc" name="foc" class="form-control select2" placeholder="">
                              	<option value="0">否</option>
                              	<option value="1">是</option>
                              </select>
                            </div>
                            <div name="congcong">
	                            <label class="col-sm-1 text-right padding cf">从：</label>
	                            <div class="col-sm-3 padding" name="startenddate">
	                              <input id="startdate0" name="startdate" type="text" onFocus="WdatePicker({minDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'enddate0\')}'})" class="form-control input-sm timeWid inputdatestr startdatestr" placeholder="2016-11-05"> 
	                              - <input id="enddate0" name="enddate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startdate0\')}'})" class="form-control input-sm timeWid inputdatestr enddatestr" placeholder="2016-12-01">
	                            </div>
                            </div>
                            <div class="col-sm-2 padding cf">
                              <select class="form-control selectMargin cf dateAddHeng" id="weekSelect" name="weekSelect" onchange="select_change(this)">
                                <option value="1">每周</option>
                                <option value="2">自由</option>
                              </select>
                            </div>
                            <div class="col-sm-4 padding cf checkWeek">
                              <input type="checkbox" name="weekday" value="7"><span>周日</span>
                              <input type="checkbox" name="weekday" value="1"><span>周一</span>
                              <input type="checkbox" name="weekday" value="2"><span>周二</span>
                              <input type="checkbox" name="weekday" value="3"><span>周三</span>
                              <input type="checkbox" name="weekday" value="4"><span>周四</span>
                              <input type="checkbox" name="weekday" value="5"><span>周五</span>
                              <input type="checkbox" name="weekday" value="6"><span>周六</span>
                            </div>
                            <button type="button" id="hidebutton0" name="hidebutton" class="btn btn-primary btn-sm none hidnBtn">隐藏日历</button>
                            <button type="button" id="showbutton0" name="showbutton" class="btn btn-primary btn-sm none showBtn">显示日历</button>
                          </div><!--end time 选择-->
                          
                          <!-- select 自由 日历-->
                          <div class="hidnCalendar none" id="minCalender0" name="minCalender">
                          </div><!--end select 自由 日历-->
                           <input id="calenderdate0" name="calenderdate" type="hidden">
                        </div>

                        <table id="example3" class="table table-bordered table-hover	">
                          <thead>
                          <tr>
                            <th>序号</th>
                           <!--  <th>航空公司名称</th> -->
                            <th>日期</th>
                            <th>航班号</th>
                            <th>航段</th>
                            <th>时间</th>
                            <th>人数</th>
                            <th>FOC</th>
                            <th>天数</th>
                            <th>旅行社名称</th> 
                            <th>联运要求</th>
                          </tr>
                          </thead>
                          <tbody>
                          </tbody>
                        </table>
                      </div><!--end 计划制作-->

                      <!--编辑计划-->
                      <div class="tab-pane pane-content labelMar" id="tab_3">
                      <form id="editPlanForm">
                       <!--团状态/单号状态 下拉框 and 按钮（导出、批量生成订单、批量关闭....）-->
                        <div class="form-group row">
                          <label class="col-sm-1 text-right padding marTop5">状态：</label>
                          <div class="col-md-1 padding">
                            <select class="form-control select1" id="teamtype1" name="teamtype" onchange="editPlanListSearch();">
                              <option value="1">系列团</option>
                              <option value="2">临时团</option>
                              <option value="3">关闭</option>
                            </select>
                          </div>
                          <div class="col-md-2 padding">
                            <!-- <select class="form-control select1" id="idordernum" name="idordernum" onchange="editPlanListSearch();">
                              <option value="0">无订单号</option>
                              <option value="1">有订单号</option>
                            </select> -->
                          </div>
                          <div class="col-md-8">
                            <button type="button" class="btn btn-primary btn-sm right" onclick="batchClosePlan();">批量关闭</button>
                            <button type="button" class="btn btn-primary btn-sm right" onclick="generateOrderNum();">批量生成订单</button>
                            <a class="btn btn-primary btn-sm right" id="exportEditPlanId" target="hidden_frame" href="${base}/admin/customneeds/exportEditPlanExcel.html">导出excel</a>
                            <!-- <p class="flie_A right">
                              导出excel
                              <input name="appendix" type="file"/>
                            </p> -->
                            
                          </div>
                        </div><!--end 默认/关闭 下拉框 and 按钮（添加、导入、导出....）-->
                        

                        <!--航空公司/旅行社/人数/天数 文本框-->
                        <div class="form-group row">
                          <label class="col-sm-1 text-right padding">旅行社：</label>
                          <div class="col-sm-1 padding">
                            <input type="text" class="form-control input-sm" placeholder="" id="travelname1" name="travelname1" onkeypress="onEnterSearch();">
                          </div>
                          <label class="col-sm-1 text-right padding">人数：</label>
                          <div class="col-sm-1 padding">
                            <input type="text" class="form-control input-sm" placeholder="" id="peoplecount1" name="peoplecount1" onkeypress="onEnterSearch();">
                          </div>
                          <label class="col-sm-1 text-right padding">天数：</label>
                          <div class="col-sm-1 padding">
                            <input type="text" class="form-control input-sm" placeholder="" id="dayscount1" name="dayscount1" onkeypress="onEnterSearch();">
                          </div>
                          <label class="col-sm-1 text-right padding inputdatestr">去程航班：</label>
                          <div class="col-sm-1 padding">
                            <input type="text" class="form-control input-sm" placeholder="" id="leaveairline1" name="leaveairline1" onkeypress="onEnterSearch();">
                          </div>
                          <label class="col-sm-1 text-right padding">回程航班：</label>
                          <div class="col-sm-1 padding">
                            <input type="text" class="form-control input-sm" placeholder="" id="backairline1" name="backairline1" onkeypress="onEnterSearch();">
                          </div>
                          <label class="col-sm-1 text-right padding">联运城市：</label>
                          <div class="col-sm-1 padding">
                            <input type="text" class="form-control input-sm" placeholder="" id="unioncity1" name="unioncity1" onkeypress="onEnterSearch();">
                          </div>
                        </div><!--end 航空公司/旅行社/人数/天数 文本框-->
                        

                        <!--去程日期/去程航段/回程日期/回程航段 文本框-->
                        <div class="form-group row">
                          
                          <label class="col-sm-1 text-right padding cf">从：</label>
                            <div class="col-sm-3 padding">
                              <input type="text" class="form-control input-sm timeWid inputdatestr" id="startdate1" name="startdate1" placeholder="2016-11-05" onkeypress="onEnterSearch();" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'enddate1\')}'})"> - <input type="text" id="enddate1" name="enddate1" class="form-control input-sm timeWid inputdatestr" placeholder="2016-12-01" onkeypress="onEnterSearch();" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startdate1\')}'})">
                            </div>
                          <label class="col-sm-1 text-right padding">起飞城市：</label>
                          <div class="col-sm-1 padding">
                            <input type="text" class="form-control input-sm" placeholder="" id="leavescity1" name="leavescity1" onkeypress="onEnterSearch();">
                          </div>
                          <label class="col-sm-1 text-right padding">降落城市：</label>
                          <div class="col-sm-1 padding">
                            <input type="text" class="form-control input-sm" placeholder="" id="backscity1" name="backscity1" onkeypress="onEnterSearch();">
                          </div>
                          <div class="col-md-3">
                            <button type="button" id="editPlanSearch" class="btn btn-primary btn-sm">搜索</button>
                            <button type="button" id="resetPlanBtn" class="btn btn-primary btn-sm btn-left">恢复默认</button>
                          </div>
                        </div><!--end 去程日期/去程航段/回程日期/回程航段 文本框-->
                        </form>
                         <table id="datatable2" class="table table-bordered table-hover">
                          <thead>
                          <tr>
                            <th><input type="checkbox" class="checkall" /></th>
                            <th>序号</th>
                            <th>日期</th>
                            <th>航班号</th>
                            <th>航段</th>
                            <th>时间</th>
                            <th>人数</th>
                            <th>FOC</th>
                            <th>天数</th>
                            <th>旅行社名称</th> 
                            <th>联运要求</th>
                            <th>操作</th>
                          </tr>
                          </thead>
                          <tbody>
                          </tbody>
                        </table>
                        <input id="checkedboxval" name="checkedboxval" type="hidden">
                      </div><!--end 编辑计划--> --%>

                    </div><!-- end tab-content -->
                  </div><!-- end nav-tabs-custom -->
                </div><!-- end col-md-12 -->
              </div><!-- end row -->
        </div>
      </div>

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

  <%@include file="/WEB-INF/public/footer.jsp"%>
</div>
<!-- ./wrapper -->
<script type="text/javascript">
	var BASE_PATH = '${base}';
</script>
<script src="${base}/public/dist/js/pikaday.js"></script>
<script src="${base}/public/dist/js/laydate/laydate.dev.js"></script>
<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
<!--小日历-->
<script src="${base}/public/build/kalendae.standalone.js" type="text/javascript" charset="utf-8"></script>
<%-- <script src="${base}/public/build/calendar.js" type="text/javascript"></script> --%>
<!-- select2 --> 
<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
<script src="${base}/admin/airline/planmake.js"></script>
<script src="${base}/admin/airline/editplan.js"></script>
<!-- DataTables -->
<script src="${base}/public/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${base}/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
<!-- page script -->


<script>
var datatable;
function initDatatable() {
    datatable = $('#datatables').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
        "stripeClasses": [ 'strip1','strip2' ],
        "language": {
            "url": "${base}/public/plugins/datatables/cn.json"
        },
        "ajax": {
            "url": "${base}/admin/customneeds/listData.html",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "fnDrawCallback"    : function(){
        	var api = this.api();
        	var startIndex= api.context[0]._iDisplayStart;
   	       　　  api.column(0).nodes().each(function(cell, i) {
   	       　　　　cell.innerHTML = startIndex + i + 1;
   	       　　});
      	},
        "columns": [
                    {"data": "xuhao", "bSortable": false},
                    {"data": "airline", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '<span data-toggle="tooltip" data-placement="right" title="'+row.airline+'">'+row.airline+'<span>';
                    		return result;
                    	}		
                    },
                    {"data": "leavedate", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var leavedate = new Date(row.leavedate);
                    		var MM = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][leavedate.getMonth()];
                    		return leavedate.getDate() + "/" + MM;
                    	}
                    },
                    {"data": "leavecity", "bSortable": false},
                    {"data": "backdate", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var backdate = new Date(row.backdate);
                    		var MM = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][backdate.getMonth()];
                    		return backdate.getDate() + "/" + MM;
                    	}
                    },
                    {"data": "backcity", "bSortable": false},
                    {"data": "totalcount", "bSortable": false},
                    {"data": "totalday", "bSortable": false},
                    {"data": "travel", "bSortable": false},
                    {"data": "uniontransport", "bSortable": false}
            ],
        columnDefs: [{
	            //   指定第一列，从0开始，0表示第一列，1表示第二列……
	            targets: 10,
	            render: function(data, type, row, meta) {
	            	var s = '<a style="cursor:pointer;" onclick="edit('+row.id+');">编辑</a>';
	            	if(row.isclose == 0){
	            		s += '&nbsp;&nbsp;&nbsp;<a style="cursor:pointer;" onclick="closeCustomNeeds('+row.id+');">关闭</a>';
	            	}else{
	            		s += '&nbsp;&nbsp;&nbsp;<a style="cursor:pointer;" onclick="enableCustomNeeds('+row.id+');">启用</a>';
	            	}
	                return s
	            }
        	},{
        		targets: 0,
	            render: function(data, type, row, meta) {
	                return null
	            }
        	}]
    });
}
	//按钮点击搜索
	$("#searchBtn").on('click', function () {
		//为Excel导出提供方法
		document.getElementById('exportExcelId').href= "${base}/admin/customneeds/exportCustomNeedsExcel.html?" + $("#searchForm").serialize();
	    var param = getSearchInfo();
	    datatable.settings()[0].ajax.data = param;
	    datatable.ajax.reload();
	});
	//获取搜索数据
	function getSearchInfo(){
		var isclose = $("#isclose").val();
		var airline = $("#airline").val();
		var travel = $("#travel").val();
		var totalcount = $("#totalcount").val();
		var totalday = $("#totalday").val();
		var leavedate = $("#leavedate").val();
		var leavecity = $("#leavecity").val();
		var backdate = $("#backdate").val();
		var backcity = $("#backcity").val();
	    var param = {
	        "isclose": isclose,
	        "airline": airline,
	        "travel": travel,
	        "totalcount": totalcount,
	        "totalday": totalday,
	        "leavedate": leavedate,
	        "leavecity": leavecity,
	        "backdate": backdate,
	        "backcity": backcity
	    };
		return param;
	}
//恢复默认
$('#resetBtn').on('click', function () {
	$("#searchForm")[0].reset();
	$("#searchBtn").click();
});
$(function () {
    initDatatable();
   	//初始化日期控件
    /* var picker = new Pikaday({
        field: document.getElementById('leavedate'),
        firstDay: 1,
        minDate: new Date('2000-01-01'),
        maxDate: new Date('2120-12-31'),
        yearRange: [2000,2020]
    });
    var picker = new Pikaday({
        field: document.getElementById('backdate'),
        firstDay: 1,
        minDate: new Date('2000-01-01'),
        maxDate: new Date('2120-12-31'),
        yearRange: [2000,2020]
    }); */

});
   function add(){
      layer.open({
    	    type: 2,
    	    title: false,
    	    closeBtn:false,
    	    fix: false,
    	    maxmin: false,
    	    shadeClose: false,
    	    scrollbar: false,
    	    area: ['900px', '500px'],
    	    content: '${url}/add.html',
    	    end:function(){
      	    	datatable.ajax.reload();
      	    }
    	  });
  }
  
  function edit(id){
	  layer.open({
  	    type: 2,
  	    title: false,
  	  	closeBtn:false,
  	    fix: false,
  	    maxmin: false,
  	    shadeClose: false,
  	  	scrollbar: false,
  	    area: ['900px', '500px'],
  	    content: '${url}/update.html?id='+id,
  	    end:function(){
  	    	datatable.ajax.reload(null,false);
  	    }
  	  });
  }
 
  function closeCustomNeeds(id){
	  layer.confirm('确定要关闭该需求吗?', {icon: 3, title:'提示'}, function(){
			$.ajax({ 
				type: 'POST', 
				data: {id:id}, 
				url: '${base}/admin/customneeds/closeCustomNeeds.html',
	            success: function (data) { 
	            	layer.alert("关闭成功",{time: 2000, icon:1});
	            	datatable.ajax.reload(null,false);
	            },
	            error: function (xhr) {
	            	layer.alert("关闭失败",{time: 2000, icon:1});
	            } 
	        });
		});
  }
  function enableCustomNeeds(id){
	  layer.confirm('确定要启用该需求吗?', {icon: 3, title:'提示'}, function(){
			$.ajax({ 
				type: 'POST', 
				data: {id:id}, 
				url: '${base}/admin/customneeds/enableCustomNeeds.html',
	            success: function (data) { 
	            	layer.alert("启用成功",{time: 2000, icon:1});
	            	datatable.ajax.reload(null,false);
	            },
	            error: function (xhr) {
	            	layer.alert("启用失败",{time: 2000, icon:1});
	            } 
	        });
		});
  }
    function onkeyEnter(){
       var e = window.event || arguments.callee.caller.arguments[0];
	   if(e && e.keyCode == 13){
		 $("#searchBtn").click();
	   }
	}
    
    function selectDept(){
    	$("#searchBtn").click();
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
  //其他页面回调
  function successCallback(id){
	  datatable.ajax.reload(null,false);
	  if(id == '1'){
		  layer.alert("添加成功",{time: 2000, icon:1});
	  }else if(id == '2'){
		  layer.alert("修改成功",{time: 2000, icon:1});
	  }else if(id == '3'){
		  layer.alert("关闭成功",{time: 2000, icon:1});
	  }else if(id == '4'){
		  layer.alert("启用成功",{time: 2000, icon:1});
	  }
  }
  
  function callback(){
	  layer.alert("导入成功",{time: 2000, icon:1});
	  $('#uploadExcelForm')[0].reset();
	  layer.closeAll('loading');
	  datatable.ajax.reload();
  }
  //输入格式必须为yyyy-MM-dd
  $(".inputdatestr").keyup(function(){
	  var values = $(this).val();
	  if(values.length <= 4){
		  values = values.replace(/\D/g,'');
		  if(values.length == 4){
			  values += '-';
		  }
	  }else if(values.length <= 7){
		  var temp1 = values.substr(0,5);
		  var temp2 = values.substr(5,values.length);
		  temp2 = temp2.replace(/\D/g,'');
		  values = temp1 + temp2;
		  if(values.length == 7){
			  values += '-';
		  }
	  }else if(values.length > 7){
		  var temp3 = values.substr(0,8);
		  var temp4 = values.substr(8,values.length);
		  temp4 = temp4.replace(/\D/g,'');
		  values = temp3 + temp4;
		  if(values.length >10){
			  values = values.substr(0,10);
		  }
	  }
	  $(this).val(values);
  });
</script>

