<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>银行卡管理</title>
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${base}/public/dist/css/bankcardManage.css"><!-- 本页面style -->
</head>
<!-- <body class="hold-transition skin-blue sidebar-mini"> -->
<c:set var="url" value="${base}/admin/bankcard" />
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
                <div class="form-group row marginBott cf">
                  <div class="col-md-1 col-md-offset-11">
                    <button type="button" id="addBackCard" class="btn btn-primary btn-sm addBackCard">添加</button>
                  </div>
              </form>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="bankCardTable" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>银行卡名称</th>
                  <th>卡号</th>
                  <th>卡类型</th>
                  <th>银行</th>
                  <th>余额</th>
                  <th>初始化金额</th>
                  <th>币种</th>
                  <th>备注</th>
                  <th>操作</th>
                </tr>
                </thead>
                <tbody>
                  <!-- <tr>
                    <td>622 4566 2323 2121211</td>
                    <td>1336662606</td>
                    <td>信用卡</td>
                    <td>招商银行</td>
                    <td>99999.99</td>
                    <td>港币</td>
                    <td>澳洲收款卡</td>
                    <td><a href="#addTabs">编辑</a></td>
                  </tr> -->
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

  <!-- Main Footer -->
</div>
<!-- ./wrapper -->
 <%@include file="/WEB-INF/public/footer.jsp"%>
<!-- REQUIRED JS SCRIPTS -->


<script>
  $(function () {
    //添加订单 弹框
    $('#addBackCard').click(function(){
        layer.open({
            type: 2,
            title:false,
            skin: false, //加上边框
            closeBtn:false,//默认 右上角关闭按钮 是否显示
            shadeClose:true,
            scrollbar: false,
            area: ['950px', '320px'],
            content: ['${url}/add.html','no']
          });
      });
  });
</script>
<script type="text/javascript">
	var empTable;
	function initDatatable() {
		empTable = $('#bankCardTable').DataTable({
			"searching" : false,
			"processing" : true,
			"serverSide" : true,
			"bLengthChange" : false,
			"stripeClasses": [ 'strip1','strip2' ],//斑马线
			"bSort": true, //排序功能
			"autoWidth": false,
			"language" : {
				"url" : "${base}/public/plugins/datatables/cn.json"
			},
	       	"ajax": {
	               "url": "${base}/admin/bankcard/listData.html",
	               "type": "post",
	               "data": function (d) {
	            	   
	            	}
	        },
	        "columns": [
	                    {"data": "cardname", "bSortable": false},
	                    {"data": "cardnum", "bSortable": false},
	                    {"data": "bankcardtype", "bSortable": false},
	                    {"data": "bankname", "bSortable": false},
	                    {"data": "balance", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.balance;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "0.00";
	                    		}
	                    		return depositBalance.toFixed(2);
	                    	}
						},
						{"data": "initialamount", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.initialamount;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "0.00";
	                    		}
	                    		return depositBalance.toFixed(2);
	                    	}	
						},
	                    {"data": "currency", "bSortable": false},
	                    {"data": "remark", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.remark;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		var depositBalance = '<span data-toggle="tooltip" data-placement="left" title="'+depositBalance+'">'+depositBalance+'<span>';
	                    		return depositBalance;
	                    	}	
	                    }
	            ],
	        "columnDefs": [ 
						/* {"sWidth": "11%", "targets": [0] },
						{"sWidth": "20%", "targets": [1] },
						{"sWidth": "7%", "targets": [2] },
						{"sWidth": "13%", "aTargets": [3] },
						{"sWidth": "10%", "aTargets": [4] },
						{"sWidth": "10%", "targets": [5] },
						{"sWidth": "6%", "targets": [6] },
						{"sWidth": "14%", "targets": [7] },
						{"sWidth": "10%", "targets": [8] },  */
	                    {
	            //   指定第一列，从0开始，0表示第一列，1表示第二列……
	            targets: 8,
	            render: function(data, type, row, meta) {
	            	var modify = '<a style="cursor:pointer;" onclick="editUser('+row.id+');">编辑</a>';
	                return modify;
	            }
	        }]
		});
	}
	$(function() {
		initDatatable();
		//selectDeptName();
		
	});

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
		    area: ['850px', '320px'],
		    content: '${base}/admin/bankcard/update.html?id='+id
		  });
	}
	function successCallback(id){
		empTable.ajax.reload(null,false);
		  if(id == '1'){
			  layer.msg("添加成功",{time: 2000});
		  }else if(id == '2'){
			  layer.msg("修改成功",{time: 2000});
		  }else if(id == '3'){
			  layer.msg("删除成功",{time: 2000});
		  }
	  }
	/* $(function(){
		var a=215434131.21313135351;
		var b=a.substring(0, a.lastIndexOf(".")+3)
		alert(b);
	}); */
</script>
<!-- </body> -->
</html>
