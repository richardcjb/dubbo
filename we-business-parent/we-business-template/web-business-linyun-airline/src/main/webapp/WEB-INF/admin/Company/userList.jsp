<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="${base }/public/dist/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="${base }/public/dist/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${base}/public/plugins/datatables/dataTables.bootstrap.css">
  <link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
  <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
        page. However, you can choose any other skin. Make sure you
        apply the skin class to the body tag so the changes take effect.
  -->
  <link rel="stylesheet" href="${base}/public/dist/css/skins/skin-blue.min.css">
  <link rel="stylesheet" href="${base}/public/dist/css/skins/_all-skins.min.css">
</head>
<body>
		<div class="modal-top">
                 <div class="modal-header boderButt marHead">
                     <button type="button" onclick="closewindow()" class="btn btn-primary right btn-sm returnMargi" data-dismiss="modal">返回</button>
                         
                         <div class="form-group row">
                           <label class="radio-inline SelectWid">
                             <!--部门 下拉框-->
                               <select class="form-control select" id="depid" onchange="selectDept();">
                                 <option value="">==请选择部门==</option>
                                 <c:forEach items="${obj.deplist}" var="one" >
                                 	<option value="${one.id }">${one.deptname }</option>
								</c:forEach>
                               </select>
                           </label>
                             <label class="radio-inline textWidth marWid">
                                 <input class="form-control" id="userName" name="userName" type="text" onkeypress="onkeyEnter();" placeholder="员工姓名/电话" />
                             </label>
                             <label class="radio-inline marWid">
                                 <button id="searchBtn" type="button" class="btn btn-primary btn-sm">搜索</button>
                             </label>
                         </div>
                 </div>

                 <div class="modal-body">
                     <table id="datatable" class="table table-bordered table-hover">
                        <thead>
                          <tr>
                            <th>员工姓名</th>
                            <th>部门</th>
                            <th>职位</th>
                            <th>电话</th>
                            <th>是否从本公司移除</th>
                          </tr>
                        </thead>
                        <tbody>
                        </tbody>
                      </table>
                </div>
          </div>

	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<!-- DataTables -->
	<script src="${base}/public/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="${base}/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<!-- SlimScroll -->
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- FastClick -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
	<!-- AdminLTE App -->
	<script src="${base}/public/dist/js/app.min.js"></script>
	<!--layer -->
	<script src="${base}/common/js/layer/layer.js"></script>
	<!-- AdminLTE for demo purposes -->
	<script src="${base}/public/dist/js/demo.js"></script>
<!-- page script -->
<script>
var datatable;
function initDatatable() {
    datatable = $('#datatable').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
        "language": {
            "url": "${base}/public/plugins/datatables/cn.json"
        },
        "ajax": {
            "url": "${base}/admin/Company/userListData.html?id=${obj.companyuser.id}",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "columns": [
                    {"data": "fullname", "bSortable": true},
                    {"data": "department", "bSortable": true},
                    {"data": "zhiwei", "bSortable": true},
                    {"data": "telephone", "bSortable": false}
            ],
        columnDefs: [{
            //   指定第一列，从0开始，0表示第一列，1表示第二列……
            targets: 4,
            render: function(data, type, row, meta) {
                return '<a style="cursor:pointer;" onclick="removeUser('+row.id+')">是</a>'
            }
        }]
    });
}

	$("#searchBtn").on('click', function () {
		var depid = $("#depid").val();
		var userName = $("#userName").val();
	    var param = {
	        "userName": userName,
			"depid" : depid
	    };
	    datatable.settings()[0].ajax.data = param;
	    datatable.ajax.reload();
	});

	$(function () {
	    initDatatable();
	});
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}

	function selectDept(){
		$("#searchBtn").click();
	}
	
	function onkeyEnter(){
		 if(event.keyCode==13){
			 $("#searchBtn").click();
		 }
	}
  	
	function removeUser(id){
		layer.confirm('确定要移除该员工吗?', {icon: 3, title:'提示'}, function(){
			$.ajax({ 
				type: 'POST', 
				data: {id:id}, 
				url: '${base}/admin/Company/removeUser.html',
	            success: function (data) { 
            		layer.msg("移除成功","",3000);
	            	datatable.ajax.reload(null,false);
	            },
	            error: function (xhr) {
	            	layer.msg("移除失败","",3000);
	            } 
	        });
		});
	}
</script>
</body>
</html>
