<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<c:set var="url" value="${base}/admin/customer" />

<!DOCTYPE html>
<html lang="en-US">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>客户管理</title>
<!-- daterange picker -->
<link rel="stylesheet" href="${base}/public/plugins/daterangepicker/daterangepicker.css">
<!-- bootstrap datepicker -->
<link rel="stylesheet" href="${base}/public/plugins/datepicker/datepicker3.css">
<!-- iCheck for checkboxes and radio inputs -->
<link rel="stylesheet" href="${base}/public/plugins/iCheck/all.css">
<link rel="stylesheet" href="${base}/public/dist/css/customer.css">

</head>
<!-- REQUIRED JS SCRIPTS -->

<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
<!-- Select2 -->
<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
<!-- InputMask -->
<script src="${base}/public/plugins/input-mask/jquery.inputmask.js"></script>
<script src="${base}/public/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="${base}/public/plugins/input-mask/jquery.inputmask.extensions.js"></script>
<!-- date-range-picker -->
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.2/moment.js"></script> -->
<script src="${base}/admin/customer/moment.js"></script>
<script src="${base}/public/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap datepicker -->
<script src="${base}/public/plugins/datepicker/bootstrap-datepicker.js"></script>
<!-- bootstrap color picker -->
<script src="${base}/public/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
<!-- bootstrap time picker -->
<script src="${base}/public/plugins/timepicker/bootstrap-timepicker.min.js"></script>
<!-- DataTables -->
<script src="${base}/public/plugins/datatables/jquery.dataTables.min.js"></script>
<script  src="${base}/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- iCheck 1.0.1 -->
<script src="${base}/public/plugins/iCheck/icheck.min.js"></script>
<!-- FastClick -->
<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
<!-- FastClick -->
<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="${base}/public/dist/js/app.min.js"></script>
<script src="${base}/common/js/layer/layer.js"></script>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Main content -->
			<section class="content">
					<div class="col-xs-12">
						<div class="box">
							<div class="box-header">
								<!-- <h3 class="box-title">
									&nbsp;&nbsp;<i class="fa fa-user-secret"></i> 客户管理
								</h3> -->
									<div class="form-group row form-right">
										<div class="col-md-2">
											<!--是否签约 下拉框-->
											<select id="contract" class="form-control select"
												name="contract" onchange="searchOpt();"
												<!-- onchange="alert($(this).val())" -->>
												<option value="">是否签约</option>
												<option value="1"
													<c:if test="${'1' eq obj.queryForm.contract}">selected</c:if>>已签约</option>
												<option value="0"
													<c:if test="${'0' eq obj.queryForm.contract}">selected</c:if>>未签约</option>
												<option value="2"
													<c:if test="${'2' eq obj.queryForm.contract}">selected</c:if>>禁止合作</option>
											</select>
										</div>
										<div class="col-md-2 col-padding">
											<!--是否禁用 下拉框-->
											<select id="forbid" class="form-control select" name="forbid" onchange="searchOpt();">
												<option value="">是否禁用</option>
												<option value="0"
													<c:if test="${'0' eq obj.queryForm.forbid}">selected</c:if>>否</option>
												<option value="1"
													<c:if test="${'1' eq obj.queryForm.forbid}">selected</c:if>>是</option>
											</select>
										</div>
										<div class="col-md-3 col-padding">
											<!--公司名称/负责人/电话 搜索框-->
											<input type="text" id="sname" name="name"
												 class="form-control"  placeholder="公司名称/负责人/电话" onkeypress="onkeyEnter();">
										</div>
										<div class="col-md-3 col-padding">
											<!--搜索 恢复默认 按钮-->
											<button id="searchBtn" type="submit" class="btn btn-primary btn-sm">搜索</button>
											<button type="button" class="btn btn-primary btn-sm btn-left"
												onclick="makeDefault()">恢复默认</button>
										</div>


										<div class="col-md-1 col-md-offset-1">
											<a class="btn btn-primary btn-sm" onclick="add();" id="addBtn">添加</a>
										</div>

									</div>
							</div>
							<!-- /.box-header -->
							<div class="box-body">
								<table id="datatableCustomerId" class="table table-bordered table-hover" style="width:100%;">
									<thead>
										<tr>
											<th>序号</th>
											<th>公司名称</th>
											<th>公司简称</th>
											<th>负责人</th>
											<th>联系人</th>
											<th>联系电话</th>
											<th>签约状态</th>
											<th>签约到期日</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div><!-- /.col -->
			</section><!-- /.content -->
		</div><!-- /.content-wrapper -->

		<!-- Main Footer -->
		<%@include file="/WEB-INF/public/footer.jsp"%>
		<div class="control-sidebar-bg"></div>
	</div>
	<!-- ./wrapper -->

	<!--添加弹框div-->
	<div class="modal fade Mymodal-lg" id="addModal" role="dialog" tabindex="-1"
		aria-labelledby="myLargeModalLabel" aria-hidden="true" id="addTabs"
		style="width: auto; height: 1000px;">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header"></div>
				<div class="modal-body"></div>
			</div>
		</div>
	</div>
	<!--更新弹框 div-->
	<div class="modal fade Mymodal-lg" id="updateModal" role="dialog" tabindex="-1"
		aria-labelledby="myLargeModalLabel" aria-hidden="true" id="addTabs"
		style="width: auto; height: 1000px;">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header"></div>
				<div class="modal-body"></div>
			</div>
		</div>
	</div>
	<!-- page script -->
<script type="text/javascript">
var datatable;
function initDatatable() {
    datatable = $('#datatableCustomerId').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
        "stripeClasses": [ 'strip1','strip2' ],
        "language": {
            "url": "${base}/public/plugins/datatables/cn.json"
        },
        "ajax": {
            "url": "${base}/admin/customer/listData.html",
            "type": "post",
            /* "data": function (d) {
            	
            } */
        },
        /* 列表序号 */
        "fnDrawCallback"    : function(){
        	var api = this.api();
        	var startIndex= api.context[0]._iDisplayStart;
   	       　　       api.column(0).nodes().each(function(cell, i) {
   	       　　　　cell.innerHTML = startIndex + i + 1;
   	       　　});
      	},

        "columns": [
                    {"data": "id", "bSortable": false},
                    {"data": "name", "bSortable": false},
                    {"data": "shortname", "bSortable": false},
                    {"data": "username", "bSortable": false},
                    {"data": "linkman", "bSortable": false},
                    {"data": "telephone", "bSortable": false},
                    {"data": "contract", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var s = '';
                    		if(row.contract == '1'){
                    			s = '已签约';
                    		}else if(row.contract == '0'){
                    			s = '未签约';
                    		}else{
                    			s = '禁止合作';
                    		}
                            return s;
                        }
                    },
                    {"data": "contractDueTime", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var s = '';
                    		if(row.contractduetime == null){
                    			s = '';
                    		}else{
                    			//日期需要格式化
                    			var javascriptDate = new Date(row.contractduetime);
                				javascriptDate = javascriptDate.getFullYear()+"-"+(javascriptDate.getMonth()+1)+"-"+javascriptDate.getDate();
                    			s = javascriptDate;
                    		}
                            return s;
                        }
                    }
            ],
        columnDefs: [{
            //   指定第一列，从0开始，0表示第一列，1表示第二列……
            targets: 8,
            render: function(data, type, row, meta) {
                return '<a onclick="edit('+row.id+')" id="updateBtn" class="btn_mini btn_modify eti_a">编辑</a>';
            }
        }]
    });
}

	$("#searchBtn").on('click', function () {
		var snameVal = $("#sname").val();
		var contractVal = $("#contract").val();
		var forbidVal = $("#forbid").val();
	    var param = {
	        "name": snameVal,"contract":contractVal,"forbid":forbidVal
	    };
	    datatable.settings()[0].ajax.data = param;
	    datatable.ajax.reload();
	});

	$(function () {
	    initDatatable();
	});

	//设置默认
	function makeDefault() {
		$("#sname").val("");
		$("#contract").val("");
		$("#forbid").val("");
	}

	$('#addBtn').click(function() {
		$("#updateModal").removeData("modal");
		$("#addModal").on("hidden", function() {
			$(this).removeData("modal");
		});
	});

	/* layer添加 */
	function add(){
	      layer.open({
	    	    type: 2,
	    	    title: false,
	    	    closeBtn:false,
	    	    fix: false,
	    	    maxmin: false,
	    	    shadeClose: false,
	    	    scrollbar: false,
	    	    area: ['900px', '550px'],
	    	    content: '${base}/admin/customer/add.html'
	    	  });
	  }
	/* layer编辑 */
	function edit(id){
	      layer.open({
	    	    type: 2,
	    	    title: false,
	    	    closeBtn:false,
	    	    fix: false,
	    	    maxmin: false,
	    	    shadeClose: false,
	    	    scrollbar: false,
	    	    area: ['900px', '550px'],
	    	    content: '${base}/admin/customer/update.html?id='+id
	    	  });
	  }
	
	$('#updateBtn').click(function() {
		$("#addModal").removeData("modal");
		$("#updateModal").on("hidden", function() {
			$(this).removeData("modal");
		});
	});
	
	/* 保存按钮事件 */
	function successCallback(id){
	  datatable.ajax.reload(null,false);
	  if(id == '1'){
		  layer.msg("添加成功", "", 2000);
	  }else if(id == '2'){
		  layer.msg("修改成功", "", 2000);
	  }else if(id == '3'){
		  layer.msg("删除成功", "", 2000);
	  }
  }

	
	//回车查询
	function onkeyEnter(){
		 if(event.keyCode==13){
			 $("#searchBtn").click();
		 }
	}
	//下拉查询
	function searchOpt(){
		$("#searchBtn").click();
	}
	
</script>
</body>
</html>
