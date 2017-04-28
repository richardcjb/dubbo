<%@ page contentType="text/html; charset=UTF-8" language="java"	pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>功能管理</title>
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
							<form id="functionForm" style="padding-top: 13px;" method="post">
								<div class="col-md-2"><!--上级功能 搜索框-->
										<select name="parentId" onchange="selectFunName();" class="form-control input-sm">
											<option value="-1">--不限--</option>
											<c:forEach items="${obj.functions}" var="pro">
												<c:choose>
													<c:when test="${pro.id == obj.parentId}">
														<option value="${pro.id}" selected="selected">${pro.name}</option>
													</c:when>
													<c:otherwise>
														<option value="${pro.id}">${pro.name}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
								</div>
								<div class="col-md-3 dictInfoSousuo" style="float: left;">
									<!--功能名称 搜索框-->
									<input type="text" id="name" name="name" 
										value="${obj.name}" onkeypress="onkeyEnter();" class="form-control"
										placeholder="功能名称">
								</div>
								<div class="col-md-2 col-padding">
									<!--搜索 按钮-->
									<button id="searchBtn" type="button" class="btn btn-primary btn-sm">搜索</button>
								</div>
							</form>
							<div class="col-md-5">
								<a id="addDeptBtn" class="btn btn-primary btn-sm right" onclick="addFunction();">添加功能</a>
						 	</div>
							<!-- /.box-header -->
							<div class="box-body">
								<table id="funDatatable"
									class="table table-bordered table-hover table-wid">
									<thead>
										<tr>
											<th>上级功能</th>
											<th>访问地止</th>
											<th>功能名称</th>
											<th>功能等级</th>
											<th>创建时间</th>
											<th>备注</th>
											<th>序号</th>
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
<!-- 分页显示 -->
<script type="text/javascript">
//添加部门职位
function addFunction(){
  layer.open({
	    type: 2,
	    title:false,
	    closeBtn:false,
	    fix: false,
	    maxmin: false,
	    shadeClose: false,
	    area: ['900px', '500px'],
	    content: '${base}/admin/authority/function/add.html',
	    end: function(){//添加完页面点击返回的时候自动加载表格数据
	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
	    }
	 	 });
 }
//编辑
function editFunction(id){
  layer.open({
	    type: 2,
	    title: false,
	  	closeBtn:false,
	    fix: false,
	    maxmin: false,
	    shadeClose: false,
	    area: ['900px', '500px'],
	    content: '${base}/admin/authority/function/update.html?id='+id
	  });
}

//事件提示
function successCallback(id){
	funDatatable.ajax.reload(null,false);
	  if(id == '1'){
		  layer.msg("添加成功",{time:2000});
	  }else if(id == '2'){
		  layer.msg("修改成功",{time:2000});
	  }else if(id == '3'){
		  layer.msg("删除成功",{time:2000});
	  }
  }
</script>
<!-- 分页显示-->
<script type="text/javascript">
var funDatatable;
function initDatatable() {
	funDatatable = $('#funDatatable').DataTable({
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
               "url": "${base}/admin/authority/function/listData.html",
               "type": "post",
               "data": function (d) {
            	   
            	}
        },
        "columns": [
                    {"data": "parentname", "bSortable": false},
                    {"data": "url", "bSortable": false},
                    {"data": "name", "bSortable": false},
                    {"data": "nlevel", "bSortable": false},
                    {"data": "createTime", "bSortable": true,
                    	render: function(data, type, row, meta) {
                   		 var createtime = new Date(row.createtime);
                   		 var createtimestr = createtime.getFullYear() + "-" + createtime.getMonth() + "-" + createtime.getDate() + " "
                   		 + createtime.getHours() + ":" + createtime.getMinutes() + ":" + createtime.getSeconds();
                   		return createtimestr;
                       }	
                    },
                    {"data": "remark", "bSortable": false},
                    {"data": "sort", "bSortable": false}
            ],
        columnDefs: [
					{ "sWidth": "8%", "aTargets": [0] },
					{ "sWidth": "12%", "aTargets": [1] },
					{ "sWidth": "9%", "aTargets": [2] },
					{ "sWidth": "12%", "aTargets": [3] },
					{ "sWidth": "12%", "aTargets": [4] },
					{ "sWidth": "12%", "aTargets": [5] },
					{ "sWidth": "11%", "aTargets": [6] },
					{ "sWidth": "10%", "aTargets": [7] },
                    {
            //   指定第一列，从0开始，0表示第一列，1表示第二列……
            targets: 7,
            render: function(data, type, row, meta) {
            	var modify = '<a style="cursor:pointer;" onclick="editFunction('+row.id+');">编辑</a>';
                return modify;
            }
        }]
	});
}
$(function() {
	initDatatable();
});
//搜索
$("#searchBtn").on('click', function () {
	var funName = $("#name").val();
	var param = {
        "name": funName
    };
    funDatatable.settings()[0].ajax.data = param;
    funDatatable.ajax.reload();
});
//搜索回车事件
function onkeyEnter(){
	 if(event.keyCode==13){
		 $("#searchBtn").click();
	 }
}
//筛选条件自动切换
function selectFunName(){
	$("#searchBtn").click();
}
</script>
</body>
</html>