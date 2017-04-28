<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>员工管理</title>
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
	              <div class="form-group row marginBott cf marDiv" style="margin-top: 10px;margin-bottom: 5px;">
	                <div class="col-md-2"><!--部门 下拉框-->
	                  <select id="deptName" name="deptName" onchange="selectDeptName();" class="form-control selePadd5">
	                    <option value="">==请选择==</option>
	                    <c:forEach items="${obj.deplist}" var="one" varStatus="indexs">
	                    	<option value="${one.deptName }">
                            	${one.deptName }
                            </option>
							<%--<c:choose>
								<c:when test="${indexs.index eq 0}">
	                               	<option value="${one.deptName }" selected="selected">
	                               		${one.deptName }
	                               	</option>
								</c:when>
								<c:otherwise>
	                               	<option value="${one.deptName }">
	                               		${one.deptName }
	                               	</option>
								</c:otherwise>
							</c:choose> --%>
						</c:forEach>
	                  </select>
	                </div>
	                <div class="col-md-3 col-padding"><!--姓名/联系电话 搜索框-->
	                  <input id="fullName" name="fullName" type="text"  onkeypress="onkeyEnter();" class="form-control" placeholder="姓名/联系电话">
	                </div>
	                <div class="col-md-1 col-padding"><!--搜索 按钮-->
	                  <button id="searchBtn" type="button" class="btn btn-primary btn-sm">搜索</button>
	                </div>
	              
	                <div class="col-md-1 col-md-offset-5">
	                  <a id="addUserBtn" class="btn btn-primary btn-sm" onclick="addUser();">添加</a>
	                </div>
	
	              </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="empTable" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>用户姓名</th>
                  <th>联系电话</th>
                  <th>部门</th>
                  <th>职位</th>
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
<!-- ./wrapper -->

<!-- REQUIRED JS SCRIPTS -->
<!-- page script -->
<script type="text/javascript">
	//添加基本资料
	function addUser(){
      layer.open({
    	    type: 2,
    	    title:false,
    	    closeBtn:false,
    	    fix: false,
    	    maxmin: false,
    	    shadeClose: false,
    	    area: ['900px', '400px'],
    	    content: '${base}/admin/user/add.html',
    	    end: function(){//添加完页面点击返回的时候自动加载表格数据
    	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    			parent.layer.close(index);
    	    }
   	 	 });
	}
	//编辑
	function editUser(id){
	  layer.open({
  	    type: 2,
  	    title: false,
  	  	closeBtn:false,
  	    fix: false,
  	    maxmin: false,
  	    shadeClose: false,
  	  	scrollbar: false,
  	    area: ['900px', '400px'],
  	    content: '${base}/admin/user/update.html?id='+id
  	  });
	}
	//事件提示
	function successCallback(id){
		empTable.ajax.reload(null,false);
		  if(id == '1'){
			  layer.msg("添加成功",{time:2000});
		  }else if(id == '2'){
			  layer.msg("修改成功",{time:2000});
		  }else if(id == '3'){
			  layer.msg("删除成功",{time:2000});
		  }else if(id == '4'){
			  layer.msg("初始化密码成功",{time:2000});
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
			var url = '${base}/admin/user/updateDeleteStatus.html';
			$.ajax({
				type : 'POST',
				data : {
					id : did,
					status : status
				},
				dataType : 'json',
				url : url,
				success : function(data) {
					if ("200" == data.status) {
						layer.msg("操作成功!", "", 3000);
						window.location.reload(true);
					} else {
						layer.msg("操作失败!", "", 3000);
					}
				},
				error : function(xhr) {
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
	var empTable;
	function initDatatable() {
		empTable = $('#empTable').DataTable({
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
                   "url": "${base}/admin/user/listData.html",
                   "type": "post",
                   "data": function (d) {
                	   
                	}
	        },
	        "columns": [
	                    {"data": "fullname", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var fullname = row.fullname;
	                    		if(null==fullname || ""==fullname){
	                    			return "";
	                    		}
	                    		return fullname;
	                    	}
	                    },
	                    {"data": "telephone", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var telephone = row.telephone;
	                    		if(null==telephone || ""==telephone){
	                    			return "";
	                    		}
	                    		return telephone;
	                    	}	
	                    },
	                    {"data": "deptname", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var deptname = row.deptname;
	                    		if(null==deptname || ""==deptname){
	                    			return "";
	                    		}
	                    		return deptname;
	                    	}		
	                    },
	                    {"data": "jobname", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var jobname = row.jobname;
	                    		if(null==jobname || ""==jobname){
	                    			return "";
	                    		}
	                    		return jobname;
	                    	}		
	                    }
	            ],
            columnDefs: [{
                //   指定第一列，从0开始，0表示第一列，1表示第二列……
                targets: 4,
                render: function(data, type, row, meta) {
                	var modify = '<a style="cursor:pointer;" onclick="editUser('+row.userid+');">编辑</a>';
                    return modify;
                }
            }]
		});
	}
	$(function() {
		initDatatable();
		//selectDeptName();
	});
	//搜索
	$("#searchBtn").on('click', function () {
		var fullName = $("#fullName").val();
		var deptName = $('#deptName').val();
		var param = {
	        "fullName": fullName,
			"deptName":deptName
	    };
	    empTable.settings()[0].ajax.data = param;
	    empTable.ajax.reload();
	});
	//搜索回车事件
	function onkeyEnter(){
		 if(event.keyCode==13){
			 $("#searchBtn").click();
		 }
	}
	
	//筛选条件自动切换
	function selectDeptName(){
		$("#searchBtn").click();
	}
</script>
</body>
</html>