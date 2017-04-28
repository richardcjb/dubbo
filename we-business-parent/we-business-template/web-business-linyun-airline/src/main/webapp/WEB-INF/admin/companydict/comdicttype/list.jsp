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
<title>公司字典类型</title>
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
								<div class="col-md-2">
									<!--状态名称 搜索框-->
									<div class="col-sm-12 padding">
										<select id="statusId" name="status"
											class="form-control input-sm">
											<option value="1">启用中</option>
                                   			<option value="2">已删除</option>
										</select>
									</div>
								</div>

								<div class="col-md-3 dictInfoSousuo">
									<!--字典类别名称 搜索框-->
									<input type="text" id="comTypeNameId" name="comTypeName"
										onkeypress="onkeyEnter();" class="form-control"
										placeholder="字典类别名称">
								</div>
								<div class="col-md-2 col-padding">
									<!--搜索 按钮-->
									<button id="comTypeSearchBtn" type="submit" class="btn btn-primary btn-sm">搜索</button>
								</div>
							<div class="col-md-1 col-md-offset-4">
								<a class="btn btn-primary btn-sm" onclick="add();">添加</a>
							</div>
							<!-- /.box-header -->
							<div class="box-body">
								<table id="comDictTypeTable" class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>公司名称</th>
											<th>字典类别编码</th>
											<th>字典类别名称</th>
											<th>状态</th>
											<th>创建时间</th>
											<th>备注</th>
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
<script src="${base }/admin/order/ordercommon.js"></script>
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
   	    content: '${base}/admin/companydict/comdicttype/add.html',
   	    end: function(){//添加完页面点击返回的时候自动加载表格数据
   	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
   	    }
 	 });
}
//编辑
function editComDictType(id){
	  layer.open({
  	    type: 2,
  	    title: false,
  	  	closeBtn:false,
  	    fix: false,
  	    maxmin: false,
  	    shadeClose: false,
  	    scrollbar: false,
  	    area: ['900px', '400px'],
  	    content: '${base}/admin/companydict/comdicttype/update.html?id='+id
  	  });
  }
//事件提示
function successCallback(id){
	comDictTypeTable.ajax.reload();
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
		var url = '${base}/admin/companydict/comdicttype/updateDeleteStatus.html';
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
$(function() {
	$('.menu-ul:eq(0)').hide();//隐藏收付款的二级菜单
	$('.menu-ul li a:eq(2)').css("color","rgb(245, 245, 245)");//二级菜单 数据字典 高亮style
});
$(function() {
	initDatatable();
});
var comDictTypeTable;
function initDatatable() {
	comDictTypeTable = $('#comDictTypeTable').DataTable({
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
                  "url": "${base}/admin/companydict/comdicttype/comListData.html",
                  "type": "post",
                  "data": function (d) {
               	   
               	}
        },
        "columns": [
                    {"data": "comname", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var comname = row.comname;
                    		if(null==comname || ""==comname){
                    			return "";
                    		}
                    		return comname;
                    	}	
                    },
                    {"data": "comtypecode", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var comtypecode = row.comtypecode;
                    		if(null==comtypecode || ""==comtypecode){
                    			return "";
                    		}
                    		return comtypecode;
                    	}		
                    },
                    {"data": "comtypename", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var comtypename = row.comtypename;
                    		if(null==comtypename || ""==comtypename){
                    			return "";
                    		}
                    		return comtypename;
                    	}	
                    },
                    {"data": "status", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var status = row.status;
                    		if(null==status || ""==status){
                    			return "";
                    		}
                    		if(status===1){
                    			return "已启用";
                    		}else if(status===2){
                    			return "已删除";
                    		}
                    	}		
                    },
                    {"data": "createtime", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var createtime = row.createtime;
                    		if(null==createtime || ""==createtime){
                    			return "";
                    		}
                    		return createtime;
                    	}		
                    },
                    {"data": "remark", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var remark = row.remark;
                    		if(null==remark || ""==remark){
                    			return "";
                    		}
                    		var result = '<span data-toggle="tooltip" data-placement="left" title="'+remark+'">'+remark+'<span>';
                    		return result;
                    	}		
                    },
            ],
           columnDefs: [{
               //   指定第一列，从0开始，0表示第一列，1表示第二列……
               targets: 6,
	           render: function(data, type, row, meta) {
		           	var modify = '<a style="cursor:pointer;" onclick="editComDictType('+row.id+');">编辑</a>';
		       		if(1==row.status){
		       			var judge = '<a href="javascript:physicalDelete('+row.id+',2)" class="btn_mini btn_modify"><font color="#CCCCCC">删除</font></a>';
		       		}else{
		       			var judge = '<a href="javascript:physicalDelete('+row.id+',1)" class="btn_mini btn_modify">启用</a>';
		       		}
		               return modify+judge;
	           }
           }]
	});
}
//公司字典类别搜索
$("#comTypeSearchBtn").on('click', function () {
	var status = $("#statusId").val();
	var comTypeName = $("#comTypeNameId").val();
    var param = {
		        "status":status,
		        "comTypeName":comTypeName,
		    };
    if(status==1 ||　status==2){
    	comDictTypeTable.settings()[0].ajax.data = param;
    	comDictTypeTable.ajax.reload(
    			function(json){
    				autoHighLoad($('#comDictTypeTable'));
    			}
    	);
    }
});

//状态选择按钮
$("#statusId").change(function(){
	$('#comTypeSearchBtn').click();
});

//搜索回车事件
function onkeyEnter(){
	 if(event.keyCode==13){
		 $("#comTypeSearchBtn").click();
	 }
}
</script>
</body>
</html>
