<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="${base}/public/dist/css/dict.css">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>字典类型</title>
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Main content -->
			<section class="content">
				<div class="row row-top">
					<div class="col-xs-12">
						<div class="box" style="padding-top:10px;">
							<form id="listForm"
								action="${base}/admin/dictionary/dirtype/list.html"
								method="post" onsubmit="return navTabSearch(this);">
								<div class="col-md-2">
									<!--状态名称 搜索框-->
									<div class="col-sm-12 padding">
										<select id="status" name="status"
											class="form-control input-sm" onchange="defaultSelect();">
											<c:forEach var="map" items="${obj.dataStatusEnum}">
												<c:choose>
													<c:when test="${map.key == obj.queryForm.status}">
														<option value="${map.key}" selected="selected">${map.value}</option>
													</c:when>
													<c:otherwise>
														<option value="${map.key}">${map.value}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="col-md-3 dictInfoSousuo">
									<!--字典类别名称 搜索框-->
									<input type="text" id="typeName" name="typeName"
										value="${obj.queryForm.typeName}" onkeypress="onkeyEnter();" class="form-control"
										placeholder="字典类别名称">
								</div>
								<div class="col-md-2 col-padding">
									<!--搜索 按钮-->
									<button id="searchBtn" type="submit" class="btn btn-primary btn-sm">搜索</button>
								</div>
							</form>
							<div class="col-md-1 col-md-offset-4">
								<a class="btn btn-primary btn-sm" onclick="add();">添加</a>
							</div>
							<!-- /.box-header -->
							<div class="box-body">
								<table id="datatableType" class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>字典类别编码</th>
											<th>字典类别名称</th>
											<th>描述</th>
											<th>状态</th>
											<th>创建时间</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${obj.list}" var="one">
											<tr>
												<td>${one.typeCode}</td>
												<td>${one.typeName }</td>
												<td>
													<span data-toggle="tooltip" data-placement="right" title="${one.description }">
														${one.description }
													<span>
												</td>
												<td><we:enum key="${one.status }"
														className="com.linyun.airline.common.enums.DataStatusEnum" /></td>
												<td><fmt:formatDate value="${one.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
												<td>
													<p class="a_p">
													<a onclick='edit(${one.id});' class='btn_mini btn_modify' style='cursor:pointer'>编辑</a>
													<!-- 这里如果有写title，则需要确认才会操作  -->
													<c:choose>
														<c:when test="${1 == one.status}">
															<a href='javascript:physicalDelete(${one.id},2);'
																class='btn_mini btn_modify'><font color="#CCCCCC">删除</font></a>
														</c:when>
														<c:otherwise>
															<a href='javascript:physicalDelete(${one.id},1);'
																class='btn btn_mini btn_modify'>启用</a>
														</c:otherwise>
													</c:choose>
													</p>
												</td>
											</tr>
										</c:forEach>
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
	<script type="text/javascript">
	//添加
	function add(){
      layer.open({
    	    type: 2,
    	    title:false,
    	    closeBtn:false,
    	    fix: false,
    	    maxmin: false,
    	    shadeClose: false,
    	    scrollbar: false,
    	    area: ['900px', '390px'],
    	    content: '${base}/admin/dictionary/dirtype/add.html',
    	    end: function(){//添加完页面点击返回的时候自动加载表格数据
    	    	parent.location.reload();
    	    }
	 	 });
	}
	//编辑
	function edit(id){
		  layer.open({
	  	    type: 2,
	  	    title: false,
	  	  	closeBtn:false,
	  	    fix: false,
	  	    maxmin: false,
	  	    shadeClose: false,
	  	    scrollbar: false,
	  	    area: ['900px', '400px'],
	  	    content: '${base}/admin/dictionary/dirtype/update.html?id='+id
	  	  });
	  }
	//事件提示
	function successCallback(id){
		  datatableType.ajax.reload();
		  if(id == '1'){
			  layer.msg("添加成功",{time:2000});
		  }else if(id == '2'){
			  layer.msg("修改成功",{time:2000});
		  }else if(id == '3'){
			  layer.msg("删除成功",{time:2000});
		  }
	  }
		//删除提示
		function physicalDelete(did, status) {
			if(2==status){
				var zt = "删除";
			}else if(1==status){
				var zt = "启用";
			}
			layer.confirm("您确认"+zt+"信息吗？", {
			    btn: ["是","否"], //按钮
			    shade: false //不显示遮罩
			}, function(){
				// 点击确定之后
				var url = '${base}/admin/dictionary/dirtype/updateDeleteStatus.html';
				$.ajax({
					url : url,
					type : 'POST',
					dataType : 'json',
					data : {
						id : did,
						status : status
					},
					success :function(data) {
						if ("200" == data.status) {
							layer.msg("操作成功!",{time:2000});
							// 重新加载
							setTimeout("location.reload()",1000);
						} else {
							layer.msg("操作失败!",{time:2000});
						}
					},error : function(xhr) {
						layer.msg("操作失败",{time:2000});
					}
				});
			}, function(){
			    // 取消之后不用处理
			});
		}
		//描述提示信息弹出层Tooltip 
		$(function() {
			$("[data-toggle='tooltip']").tooltip();
		});
	</script>
	<script type="text/javascript">
		var datatableType;
		function initDatatable() {
			datatableType = $('#datatableType').DataTable({
				"searching" : false,
				"processing" : true,
				"serverSide" : false,
				"bLengthChange" : false,
				"stripeClasses": [ 'strip1','strip2' ],//斑马线
				"bSort": true, //排序功能 
				"bAutoWidth":true,
				"bJQueryUI": true,
				"aoColumnDefs": [
						{ "sWidth": "11%",  "aTargets": [0] },
						{ "sWidth": "11%", "aTargets": [1] },
						{ "sWidth": "22%", "aTargets": [2] },
						{ "sWidth": "7%", "aTargets": [3] },
						{ "sWidth": "16%", "aTargets": [4] },
						{ "sWidth": "10%", "aTargets": [5] },
					 ],
				"language" : {
					"url" : "${base}/public/plugins/datatables/cn.json"
				}
			});
		}
		/* $(document).ready(function(){
			$('#datatable').dataTable({
			"aoColumns": [
				null,
			{"asSorting":["desc"]}
			]
			});
			}); */
		$(function() {
			initDatatable();
			$('.menu-ul:eq(0)').hide();//隐藏收付款的二级菜单
			$('.menu-ul li a:eq(2)').css("color","rgb(245, 245, 245)");//二级菜单 数据字典 高亮style
		});
	//搜索
	$("#searchBtn").on('click', function () {
		var typeName = $("#typeName").val();
		var status = $('#status').val();
	    var param = {
	        "typeName": typeName,
			"status":status
	    };
	    datatableInfo.settings()[0].ajax.data = param;
	    datatableInfo.ajax.reload();
	});
		//搜索回车事件
		function onkeyEnter(){
			 if(event.keyCode==13){
				 $("#searchBtn").click();
			 }
		}
		//状态默认选中
		function defaultSelect(){
			//$("#searchBtn").click();
			document.getElementById("listForm").submit();
		}
	</script>
</body>
</html>
