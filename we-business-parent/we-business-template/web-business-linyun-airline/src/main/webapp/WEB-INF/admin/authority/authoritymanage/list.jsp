<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%> 
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>权限管理</title>
  </head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
  <!--内容-->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
      <div class="row row-top">
        <div class="col-xs-12">
             <!-- <h4 class="page-header"><i class="fa fa-eye"></i> 权限管理</h4> -->
              <div class="row">
                <div class="col-md-12">
                  <!-- Custom Tabs -->
                  <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                      <li class="active" id="dept_button">
                      	<a href="#tab_1" onclick="setPageStatus('0');" data-toggle="tab">部门职位</a>
                      </li>
                      <input id="pagerStatus" type="hidden" value="0"/>
                      <li id="area_button">
                      	<a href="#tab_2" data-toggle="tab" onclick="setPageStatus('1');">区域</a>
                      </li>
                    </ul>
                    <div class="tab-content">
                      <!--部门职位-->
                      <div class="tab-pane pane-content active" id="tab_1">
                         <div class="col-md-1 col-md-offset-11">
							<a id="addDeptBtn" class="btn btn-primary btn-sm" onclick="addDept();">添加</a>
						 </div>
                         <table id="deptDatatable" class="table table-bordered table-hover">
                          <thead>
                          <tr>
                            <th>部门名称</th>
                            <th>模块</th>
                            <th>职位名称</th>
                            <th>操作</th>
                          </tr>
                          </thead>
                          <tbody>
                          </tbody>
                        </table>
                      </div><!--end 部门职位-->
                      
                      <!-----------区域------------->
                      <div class="tab-pane pane-content" id="tab_2">
	                    <div class="col-md-1 col-md-offset-11">
	                        <a id="addAreaBtn" class="btn btn-primary btn-sm" onclick="addArea();">添加</a>
						</div>
                        <table id="areaDatatable" class="table table-bordered table-hover">
                          <thead>
                          <tr>
                            <th>区域名称</th>
                            <th>操作</th>
                          </tr>
                          </thead>
                          <tbody>
                          </tbody>
                        </table>
                      </div><!--end 区域-->

                    </div><!-- end tab-content -->
                  </div><!-- end nav-tabs-custom -->
                </div><!-- end col-md-12 -->
              </div><!-- end row -->
        </div>
      </div>

    </section>
    <!-- /.content -->
  </div>
  <!--end 内容-->
  <!-- Main footer -->
  <%@include file="/WEB-INF/public/footer.jsp"%>
  <!--end footer-->
</div>
<!-- ./wrapper -->
<script type="text/javascript">
	//添加部门职位
	function addDept(){
      layer.open({
    	    type: 2,
    	    title:false,
    	    closeBtn:false,
    	    fix: false,
    	    maxmin: false,
    	    shadeClose: false,
    	    scrollbar: false,
    	    area: ['900px', '500px'],
    	    content: '${base}/admin/authority/authoritymanage/add.html',
    	    end: function(){//添加完页面点击返回的时候自动加载表格数据
    	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    			parent.layer.close(index);
    	    }
   	 	 });
	 }
	//编辑
	function editDept(id){
	  layer.open({
  	    type: 2,
  	    title: false,
  	  	closeBtn:false,
  	    fix: false,
  	    maxmin: false,
  	    shadeClose: false,
  	 	scrollbar: false,
  	    area: ['900px', '500px'],
  	    content: '${base}/admin/authority/authoritymanage/update.html?id='+id
  	  });
	}
	//事件提示
	function successCallback(id,inputId){
		deptDatatable.ajax.reload(null,false);
		areaDatatable.ajax.reload(null,false);
		  if(id == '1'){
			  layer.msg("添加成功",{time:2000});
		  }else if(id == '2'){
			  layer.msg("修改成功",{time:2000});
		  }else if(id == '3'){
			  layer.msg("删除成功",{time:2000});
		  }
	  }
	//描述提示信息弹出层Tooltip
	$(function() {
		$("[data-toggle='tooltip']").tooltip();
	});
</script>
<!-- 分页显示 部门职位权限设置 -->
<script type="text/javascript">
	var deptDatatable;
	function initDatatable() {
		deptDatatable = $('#deptDatatable').DataTable({
			"searching" : false,
			"processing" : true,
			"serverSide" : true,
			"bLengthChange" : false,
			"stripeClasses": [ 'strip1','strip2' ],//斑马线
			"bSort": true, //排序功能 
			"language" : {
				"url" : "${base}/public/plugins/datatables/cn.json"
			},
           	"ajax": {
                   "url": "${base}/admin/authority/authoritymanage/listData.html",
                   "type": "post",
                   "data": function (d) {
                	   
                	}
	        },
	        "columns": [
	                    {"data": "deptname", "bSortable": false},
	                    {"data": "modulename", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		if(row.modulename==null){
	                    			return "";
	                    		}else{
	                    			var result = '<span data-toggle="tooltip" data-placement="right" title="'+row.modulename+'">'+row.modulename+'<span>';
		                    		return result;
	                    		}
	                    	}
	                    },
	                    {"data": "jobname", "bSortable": false}
	            ],
            "columnDefs": [
					{ "sWidth": "15%",  "targets": [0] },
					{ "sWidth": "35%",  "targets": [1] },
					{ "sWidth": "35%",  "targets": [2] },
					{ "sWidth": "15%",  "targets": [3] },
                    {
                //   指定第一列，从0开始，0表示第一列，1表示第二列……
                targets: 3,
                render: function(data, type, row, meta) {
                	var modify = '<a style="cursor:pointer;" onclick="editDept('+row.deptid+');">编辑</a>';
                    return modify;
                },
            	
            }]
		});
	}
	$(function() {
		initDatatable();
	});
</script>
<!-- 区域的添加 -->
<script type="text/javascript">
//添加区域
function addArea(){
  layer.open({
	    type: 2,
	    title:false,
	    closeBtn:false,
	    fix: false,
	    maxmin: false,
	    shadeClose: false,
	    area: ['450px', '200px'],
	    content: '${base}/admin/area/add.html',
	    end: function(){//添加完页面点击返回的时候自动加载表格数据
	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
	    }
	});
}
//编辑区域
function editArea(id){
  layer.open({
	    type: 2,
	    title: false,
	  	closeBtn:false,
	    fix: false,
	    maxmin: false,
	    shadeClose: false,
	    area: ['450px', '200px'],
	    content: '${base}/admin/area/update.html?id='+id
	  });
}
</script>
<!-- 区域分页显示  -->
<script type="text/javascript">
	var areaDatatable;
	function initareaDatatable() {
		areaDatatable = $('#areaDatatable').DataTable({
			"searching" : false,
			"processing" : true,
			"serverSide" : true,
			"stripeClasses": [ 'strip1','strip2' ],//斑马线
			"bLengthChange" : false,
			"language" : {
				"url" : "${base}/public/plugins/datatables/cn.json"
			},
           	"ajax": {
                   "url": "${base}/admin/area/listAreaData.html",
                   "type": "post",
                   "data": function (d) {
                	   
                	}
	        },
	        "columns": [
	               	{"data": "areaname", "bSortable": false}
	            ],
            columnDefs: [{
                //   指定第一列，从0开始，0表示第一列，1表示第二列……
                targets: 1,
                render: function(data, type, row, meta) {
                	var modify = '<a style="cursor:pointer;" onclick="editArea('+row.id+');">编辑</a>';
                    return modify;
                }
            }]
		});
	}
	$(function() {
		initareaDatatable();
	});
</script>
<script type="text/javascript">
	function setPageStatus(status){
		$("input#pagerStatus").val(status) ;
	}
	
	$(function() {
		var pageStatus = $("input#pagerStatus").val();
		if("0" == pageStatus){
			$("li#dept_button").addClass("active");
			$("li#area_button").removeClass("active");
		}else{
			$("li#dept_button").removeClass("active");
			$("li#area_button").addClass("active");
		}
	});
</script>
</body>
</html>
