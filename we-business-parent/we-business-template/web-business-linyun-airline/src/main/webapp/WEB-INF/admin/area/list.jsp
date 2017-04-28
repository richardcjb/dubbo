<%@ page contentType="text/html; charset=UTF-8" language="java"	pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>区域</title>
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
						<div class="box">
							<div class="box-header">
								<h3 class="box-title">
									&nbsp;&nbsp;<i class="fa fa-user-secret">区域</i>
								</h3>
							</div>
							<form id="listForm" method="post" >
								<div class="col-md-2">
									<!--状态名称 搜索框-->
									<div class="col-sm-12 padding">
										<select id="status" name="status"
											class="form-control input-sm" onchange="defaultSelect()">
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
									<input type="text" name="typeName"
										value="${obj.queryForm.typeName}" class="form-control"
										placeholder="字典类别名称">
								</div>
								<div class="col-md-2 col-padding">
									<!--搜索 按钮-->
									<button type="submit" class="btn btn-primary btn-sm">搜索</button>
								</div>
							</form>
							<div class="col-md-1 col-md-offset-4">
								<a href="${base}/admin/dictionary/dirtype/add.html"
									data-toggle="modal" class="btn btn-primary btn-sm" id="addBtn"
									data-target="#addTabs">添加</a>
							</div>
							<!-- /.box-header -->
							<div class="box-body">
								<table id="datatable" class="table table-bordered table-hover">
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
												<td><span data-toggle="tooltip" data-placement="right"
													title="${one.description }">${one.description }<span></td>
												<td><we:enum key="${one.status }"
														className="com.linyun.airline.common.enums.DataStatusEnum" /></td>
												<td><fmt:formatDate value="${one.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
												<td>
												<p class="a_p">
												<a href="${base}/admin/dictionary/dirtype/update.html?id=${one.id}"
													data-toggle="modal" id="editBtn"
													class="btn btn_mini btn_modify" data-target="#editTabs">编辑</a>
													<!-- 这里如果有写title，则需要确认才会操作  -->
													<c:choose>
														<c:when test="${1 == one.status}">
															<a href='javascript:physicalDelete(${one.id},2);'
																class='btn btn_mini btn_modify'><font color="#CCCCCC">删除</font></a>
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
	<!-- ./wrapper -->
	<!-- 添加弹框 -->
	<div class="modal fade Mymodal-lg" role="dialog" tabindex="-1"
		aria-labelledby="myLargeModalLabel" aria-hidden="true" id="addTabs">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header"></div>
				<div class="modal-body"></div>
			</div>
		</div>
	</div>
	<!-- 编辑弹框 -->
	<div class="modal fade Mymodal-lg" role="dialog" tabindex="-1"
		aria-labelledby="myLargeModalLabel" aria-hidden="true" id="editTabs">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header"></div>
				<div class="modal-body"></div>
			</div>
		</div>
	</div>
	<!-- REQUIRED JS SCRIPTS -->

	<!-- jQuery 2.2.3 -->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<!-- DataTables -->
	<script
		src="${base}/public/plugins/datatables/jquery.dataTables.min.js"></script>
	<script
		src="${base}/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<!-- SlimScroll -->
	<script
		src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- FastClick -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
	<!-- AdminLTE App -->
	<script src="${base}/public/dist/js/app.min.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
	<!-- page script -->
	<script type="text/javascript">
		//添加
		$('#addBtn').click(function() {
			$(".Mymodal-lg").on("hidden", function() {
				$(this).removeData("modal");
			});
		});
		//删除提示
		function physicalDelete(did, status) {
			layer.confirm("您确认删除(启用)信息吗？", {
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
							layer.msg("操作成功!", "", 3000);
							// 重新加载
							setTimeout("location.reload()",1000);
						} else {
							layer.msg("操作失败!", "", 3000);
						}
					},error : function(xhr) {
						layer.msg("操作失败", "", 3000);
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
		var datatable;
		function initDatatable() {
			datatable = $('#datatable').DataTable({
				"searching" : false,
				"processing" : true,
				"serverSide" : false,
				"bLengthChange" : false,
				"bSort": true, //排序功能 
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
		});
	</script>
	<script type="text/javascript">
		//状态默认选中
		function defaultSelect(){
			document.getElementById("listForm").submit();
		}
	</script>
</body>
</html>
