<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>

<c:set var="url" value="${base}/admin/Company" />
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row row-top">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <div class="form-group row form-right">
                <div class="col-md-3"><!--公司名称/负责人/电话 搜索框-->
                  <input type="text" name="companyName" id="companyName" class="form-control" placeholder="公司名称/负责人/电话" onkeypress="onkeyEnter();">
                </div>
                <div class="col-md-2">
                  <select class="form-control input-sm inpImportant" name="comType" id="comType" onchange="selectListData();">
                     <option value="">==请选择==</option>
                   	 <c:forEach var="map" items="${obj.companyTypeEnum}" >
			   		    <option value="${map.key}">${map.value}</option>
				     </c:forEach>
                  </select>
                </div>
                <div class="col-md-7 col-padding"><!--搜索 恢复默认 按钮-->
                  <button id="searchBtn" type="button" class="btn btn-primary btn-sm">搜索</button>
                  <a class="btn btn-primary btn-sm right" onclick="add();">添加</a>
                </div>
              
               <%--  <div class="col-md-1 col-md-offset-6 paddiLeAdd">
                  <!-- <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" >添加</button> -->
                  <button type="button" class="btn btn-primary btn-sm" onClick="window.open('${url}/add.html', 'newwindow', 'height=500, width=800, top=120, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');">添加</button>
                  <a data-toggle="modal" href="${url }/add.html" data-target="#addTabs">添加</a>
                </div> --%>
				
              </div>
            </div>
            <h4 class="padLeft" id="companyCount"></h4>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="datatables" class="table table-bordered table-hover" style="width: 100%;">
                <thead>
                <tr>
                  <th>　 公司名称</th>
                  <th>　 联系人</th>
                  <th>　 联系方式</th>
                  <th>　 员工人数</th>
                  <th>　 公司类型</th>
                  <th>　 操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
              </table>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
        <!-- /.col -->
      </div>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

  <%@include file="/WEB-INF/public/footer.jsp"%>
</div>
<!-- ./wrapper -->

<!--弹框 div-->
<div class="modal fade Mymodal-lg" role="dialog" tabindex="-1" aria-labelledby="myLargeModalLabel" aria-hidden="true" id="addTabs" style="width:auto;height:1000px;"> 
      <div class="modal-dialog modal-lg">
          <div class="modal-content">
              <div class="modal-header">
              </div>
                <div class="modal-body">
                </div>
            </div>
        </div>
    </div>

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
            "url": "${base}/admin/Company/listData.html",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "columns": [
                    {"data": "comname", "bSortable": false},
                    {"data": "connect", "bSortable": false},
                    {"data": "mobile", "bSortable": false},
                    {"data": "renshu", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var s = '';
                    		if(row.renshu <= '0'){
                    			s = '0';
                    		}else{
                    			s = '<a  style="cursor:pointer;" onclick="userlist('+row.id+')">'+row.renshu+'</a>';
                    		}
                            return s
                        }
                    },
                    {"data": "comtype", "bSortable": false}
            ],
        columnDefs: [{
            //   指定第一列，从0开始，0表示第一列，1表示第二列……
            targets: 5,
            render: function(data, type, row, meta) {
                return '<a style="cursor:pointer;" onclick="edit('+row.id+');">编辑</a>'
            }
        }]
    });
}

	$("#searchBtn").on('click', function () {
		var companyName = $("#companyName").val();
		var comType = $('#comType').val();
	    var param = {
	        "companyName": companyName,
			"comType" : comType
	    };
	    datatable.settings()[0].ajax.data = param;
	    datatable.ajax.reload();
	});

$(function () {
    loadCompanyCount();
    initDatatable();
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
    	    area: ['880px', '585px'],
    	    content: '${url}/add.html'
    	    
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
  	    area: ['880px', '585px'],
  	    content: '${url}/update.html?id='+id
  	  });
  }
  function userlist(id){
	  layer.open({
  	    type: 2,
  	    title: false,
  	  	closeBtn:false,
  	    fix: false,
  	    maxmin: false,
  	    shadeClose: true,
  	    area: ['900px', '600px'],
  	    content: '${url}/userList.html?id='+id,
  	  	end:function(){
	    	datatable.ajax.reload(null,false);
	    }
  	  });
  }
  function onkeyEnter(){
	     var e = window.event || arguments.callee.caller.arguments[0];
	     if(e && e.keyCode == 13){
			 $("#searchBtn").click();
		 }
	}
  function selectListData(){
	  $("#searchBtn").click();
  }
  function successCallback(id){
	  datatable.ajax.reload(null,false);
	  loadCompanyCount();
	  if(id == '1'){
		  layer.msg("添加成功",{time: 2000, icon:1});
	  }else if(id == '2'){
		  layer.msg("修改成功",{time: 2000, icon:1});
	  }else if(id == '3'){
		  layer.msg("删除成功",{time: 2000, icon:1});
	  }
  }
  
  function loadCompanyCount(){
	  $.ajax({ 
		  type: 'POST', 
		  data: {}, 
		  async:false,
		  url: '${base}/admin/Company/loadCompanyCount.html',
          success: function (data) { 
        	  var str = "全部公司：" + data.totalcompany + "&nbsp;&nbsp;&nbsp;&nbsp;上游公司：" + data.upconpany + "&nbsp;&nbsp;&nbsp;&nbsp;代理商：" + data.agent;
        	  //$('#companyCount').html(str);
        	  document.getElementById("companyCount").innerHTML= str;
          },
          error: function (xhr) {
          } 
      });
  }
</script>

