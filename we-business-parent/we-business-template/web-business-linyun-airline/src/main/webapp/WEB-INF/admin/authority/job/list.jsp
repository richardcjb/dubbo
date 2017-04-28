<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>职位</title>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <%@include file="/WEB-INF/public/header.jsp"%>
  <%@include file="/WEB-INF/public/aside.jsp"%>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row row-top">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">&nbsp;&nbsp;<i class="fa fa-user-secret"></i> 职位</h3>
            </div>
            <form action="${base}/admin/authority/job/list.html" method="post" onsubmit="return navTabSearch(this);">
				 <div class="col-md-3"><!--职位名称 搜索框-->
            		  <input type="text" name="name" value="${obj.queryForm.name}" class="form-control" placeholder="职位名称">
          		  </div>
				 <div class="col-md-2 col-padding"><!--搜索 按钮-->
              		<button type="submit" class="btn btn-primary btn-sm">搜索</button>
           		 </div>
			</form>
            <div class="col-md-1 col-md-offset-6">
              <button type="button" onclick="javascript:window.open('${base}/admin/authority/job/add.html');" class="btn btn-primary btn-sm" data-toggle="modal" data-target=".Mymodal-lg">添加</button>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example2" class="table table-bordered table-hover">
                <thead>
                <tr>
                  	<th>职位名称</th>
					<th>创建时间</th>
					<th>备注</th>
					<th>操作</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${obj.list}" var="one" >
					<tr>
						<td>${one.name}</td>
						<td><fmt:formatDate value="${one.createTime}" pattern="yyyy-MM-dd"/></td>
						<td>${one.remark}</td>
						<td>
							<a target="dialog" rel="user_update" href="${base}/admin/authority/job/update.html?id=${one.id}" class="btn btn_mini btn_modify">修改</a>
							<%--
								这里如果有写title，则需要确认才会操作
							 --%>
							<a target="ajaxTodo" rel="dlgId1" href="${base}/admin/authority/job/delete?id=${one.id}" title='是否要删除' class='btn btn_mini btn_del'>删除</a>
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
<!-- page script -->
<script>
  $(function () {
    $("#example1").DataTable();
    $('#example2').DataTable({
      "paging": true,
      "lengthChange": false,
      "searching": false,
      "ordering": true,
      "info": true,
      "autoWidth": false
    });
  });
</script>
<!-- 点击添加按钮弹出add添加页面 -->
<script type="text/javascript">
	
</script>
</body>
</html>
