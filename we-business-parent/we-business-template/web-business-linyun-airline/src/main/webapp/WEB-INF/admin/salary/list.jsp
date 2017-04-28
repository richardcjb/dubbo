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
  <title>工资</title>
<!--   <link rel="stylesheet" href="../bootstrap/css/bootstrap.css">
  <link rel="stylesheet" href="../font-awesome-4.4.0/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="../ionicons-2.0.1/css/ionicons.min.css">
  <link rel="stylesheet" href="../plugins/datatables/dataTables.bootstrap.css">
  <link rel="stylesheet" href="../dist/css/AdminLTE.css">
  <link rel="stylesheet" href="../dist/css/skins/skin-blue.min.css">
  <link rel="stylesheet" href="../dist/css/skins/_all-skins.min.css"> -->
  <link rel="stylesheet" href="${base}/public/dist/css/salary.css"><!--本页面Style-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row row-top">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
               <form role="form" class="form-horizontal">
                <div class="form-group row mar cf">
                  <div class="col-md-2">
                    <select class="form-control input-sm" name="drawer" id="drawer" onchange="select();">
                      <option value="">开票人</option>
	                      <c:forEach items="${obj.drawerList }" var="each">
	                      	<option value="${each.drawer }">${each.drawer }</option>
	                      </c:forEach>
                    </select>
                  </div>
                  <div class="col-md-1 padding">
                     <select class="form-control input-sm" id="year" onchange="select();">
                      <option value="">年份</option>
                       <c:forEach items="${obj.yearsList }" var="each">
                      	<option value="${each.years }">${each.years }</option>
                      </c:forEach>
                    </select>
                  </div>
                  <div class="col-md-1 padding">
                     <select class="form-control input-sm" id="month" onchange="select();">
                      <option value="">月份${monthsList1}</option>
                      <%-- <c:choose>
                      <c:when test="${obj.monthsList1 !=null && obj.monthsList1 !='' }">
	                      
	                       
		                       <c:forEach items="${obj.monthsList1 }" var="each">
		                      	<option value="${each.months }">${each.months }</option>
		                      </c:forEach>
	                      
                      
                      </c:when>
                      <c:otherwise> --%>
	                       <c:forEach items="${obj.monthsList }" var="each">
	                      	<option value="${each.months }">${each.months }</option>
	                      </c:forEach>
                     <%--  
                      </c:otherwise>
                      </c:choose>  --%>
                    </select>
                  </div>
                  <div class="col-md-2 padding">
                   <!--  <button type="button" class="btn btn-primary btn-sm" onclick="select();">搜索</button> -->
                  </div>
                  <div class="col-md-6 padding">
                  	<button type="button" class="btn btn-primary btn-sm right" style="margin-left:6px;" onclick="add();">添加</button>
                  </div>
                </div>
              </form>
            </div>
            <div class="box-body">
              <table id="salaryTable" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>开票人</th>
                  <th>团数</th>
                  <th>人头数</th>
                  <th>时间</th>
                  <th>成本合计</th>
                  <th>实收合计</th>
                  <th>提成</th>
                  <th>实发提成</th>
                  <th>基本工资</th>
                  <th>工资合计</th>
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
  <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->
<%@include file="/WEB-INF/public/footer.jsp"%>

<!-- REQUIRED JS SCRIPTS -->

<script>
  /* $(function () {
    $('#salaryTable').dataTable({//工资 table
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "columnDefs": [
            /*{"sWidth": "11.33%","aTargets": [0] },
            {"sWidth": "11.33%","aTargets": [1] },
            {"sWidth": "8.33%","aTargets": [2] },
            {"sWidth": "8.33%","aTargets": [3] },
            {"sWidth": "8.33%","aTargets": [4] },
            {"sWidth": "8.33%","aTargets": [5] },
            {"sWidth": "5.33%","aTargets": [6] }],
        "oLanguage": {                          //汉化   
          "sSearch": ["one","two"],  
          "oPaginate":{   
                "sFirst": "首页",   
                "sPrevious": "上一页",   
                "sNext": "下一页",   
                "sLast": "尾页"  
              }   
            }    
        });
  }); */
</script>
<script type="text/javascript">
var empTable;
function initDatatable() {
	empTable = $('#salaryTable').DataTable({
		"searching" : false,
		"processing" : true,
		"serverSide" : true,
		"stripeClasses": [ 'strip1','strip2' ],//斑马线
		"bLengthChange" : false,
		"bSort": true, //排序功能 
		"language" : {
			"url" : "${base}/public/plugins/datatables/cn.json"
		},
       	"ajax": {
               "url": "${base}/admin/salary/listData.html",
               "type": "post",
               "data": function (d) {
            	   
            	}
        },
        "columns": [
                    {"data": "drawer", "bSortable": false},//开票人
                    {"data": "groupnumber", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var depositBalance = row.groupnumber;
                    		if(depositBalance==null||depositBalance==''){
                    			return 0;
                    		}
                    		return depositBalance;
                    	}			
                    },//团数
                    {"data": "headcount", "bSortable": false},//人头数
                    {"data": "updatetime", "bSortable": false},//时间
                    {"data": "costtotal", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var depositBalance = row.costtotal;
                    		
                    		return depositBalance.toFixed(2);
                    	}		
                    },//成本合计
                    {"data": "incometotal", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var depositBalance = row.incometotal;
                    		
                    		return depositBalance.toFixed(2);
                    	}	
                    },//实收合计
                    {"data": "commission", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var depositBalance = row.commission;
                    		
                    		return depositBalance.toFixed(2)+"%";
                    	}		
                    },//提成
                    {"data": "actualcommission", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var depositBalance = row.actualcommission;
                    		
                    		return depositBalance.toFixed(2);
                    	}	
                    },//实发提成
                    {"data": "basepay", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var depositBalance = row.basepay;
                    		
                    		return depositBalance.toFixed(2);
                    	}		
                    },//基本工资
                    {"data": "salarytotal", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var depositBalance = row.salarytotal;
                    		
                    		return depositBalance.toFixed(2);
                    	}		
                    }//工资合计
            ]
	});
}
$(function() {
	initDatatable();
	//selectDeptName();
});
//根据不同的条件进行搜索
function select()
{
	var drawer = $('#drawer').val();
	var year = $('#year').val();
	var month = $('#month').val();
	
	
    var param = {
        "drawer": drawer,
        "year": year,
        "month": month
    };
		
    empTable.settings()[0].ajax.data = param;
	empTable.ajax.reload();
}
function add(){
	$.ajax({
		cache : false,
		type : "POST",
		url : '${base}/admin/salary/add.html',
		data : function(){
			
		},// 你的formid
		error : function(request) {
			layer.msg('添加失败!');
		},
		success : function(data) {
			/* layer.load(1, {
				 shade: [0.1,'#fff'] //0.1透明度的白色背景
			}); */
			/* formValidator(); */
		/* 	 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		    parent.layer.close(index);
		    window.parent.successCallback('1');  */
		    location=location;
			
		    
		}
	});
}
</script>
</body>
</html>
