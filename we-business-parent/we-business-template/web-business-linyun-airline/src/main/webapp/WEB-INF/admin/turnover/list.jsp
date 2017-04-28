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
  <title>流水</title>
 <!--  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport"> -->
  <!-- Bootstrap 3.3.6 -->
  <c:set var="url" value="${base}/admin/turnover" />
  <link rel="stylesheet" href="${base}/public/dist/css/swiftNumber.css"><!--本页style-->
</head>
<!-- <body class="hold-transition skin-blue sidebar-mini"> -->
<div class="wrapper">
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header LiuSuiNum">
               <form role="form" class="form-horizontal" id="selectTurnOver">
	                <div class="form-group row marginBott cf">
	                  <div class="col-md-1 paddingRight5">
	                    <select class="form-control input-sm" name="status" id="status" onchange="pickedFunc();">
	                      <option value="1">启用中</option>
	                      <option value="0">关闭</option>
	                    </select>
	                  </div>
	                  <div class="col-md-1 padding">
	                     <select class="form-control input-sm" name="bankCardName" id="bankCardName" onchange="pickedFunc();">
	                      <!-- <option>银行卡名称</option>
	                      <option>国际段专用</option>-->
	                      <option value="">银行卡名称</option> 
	                      <c:forEach items="${obj.bankNameList }" var="each" >
	                    		<option value="${each.cardName }">${each.cardName }</option>
		                  </c:forEach>	        
	                    </select>
	                  </div>
	                  <div class="col-md-1 padding">
	                    <select class="form-control input-sm" name="operation" id="operation" onchange="pickedFunc();">
	                      <option value="">用途</option>
	                      <option value="支出">支出</option>
	                      <option value="收入">收入</option>
	                    </select>
	                  </div>
	                  <div class="col-md-1 padding">
	                     <select class="form-control input-sm" name="projectName" id="projectName" onchange="pickedFunc();">
	                     <!--  <option>机票款</option>
	                      <option>预付机票款</option>-->
	                      <option value="">项目</option> 
	                       <c:forEach items="${obj.projectList }" var="each">
	                    		<option value="${each.comDictName }">${each.comDictName }</option>
		                  </c:forEach>	        
	                    </select>
	                  </div>
	                  <div class="col-md-1 padding">
	                     <select class="form-control input-sm" name="currency" id="currency" onchange="pickedFunc();">
	                      <option value="">币种</option>
	                     <!--  <option>人民币</option>
	                      <option>澳币</option> -->
	                      <c:forEach items="${obj.currencyList }" var="each">
	                    		<option value="${each.dictCode }">${each.dictCode }</option>
		                  </c:forEach>
	                    </select>
	                  </div>
	                  <div class="col-md-1 padding">
	                     <input type="text" class="form-control"  name="tradeDate" id="tradeDate" value="" onFocus="WdatePicker({onpicked:pickedFunc,oncleared:pickedFunc,dateFmt:'yyyy-MM-dd'})" >
	                  </div>
	                  <div class="col-md-1 padding">
	                     <input type="text" class="form-control"  name="companyName" id="companyName" value=""  >
	                  </div>
	                  <div class="col-md-2 padding">
	                    <button type="button" class="btn btn-primary btn-sm suBtn" onclick="pickedFunc();" >搜索</button>
	                    <button type="button" class="btn btn-primary btn-sm suBtn" onclick="clearSelect();pickedFunc();">清空</button>
	                  </div>
	                  <div class="col-md-1col-md-offset-3">
	                    <button type="button" class="btn btn-primary btn-sm right jyb" id="addTurnOver">记一笔</button>
	                  </div>
	                </div>
              </form>
            </div>
            <!-- /.box-header -->
            <div class="box-body" style="margin-top:-5px;">
              <table id="turnOverTable" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>交易日期</th>
                  <th>银行卡名称</th>
                  <th>项目</th> 
                  <th>金额</th>
                  <th>用途</th>
                  <th>账户余额</th>
                  <th>币种</th>
                  <th>平均汇率</th>
                  <th>单位</th>
                  <th>收/开发票</th>
                  <th>备注</th>
                  <th>操作</th>
                </tr>
                </thead>
                <tbody>
                  <!-- <tr>
                    <td>2017-02-22</td>
                    <td> </td>
                    <td>  </td>
                    <td>9362.32</td>
                    <td> </td>
                    <td> </td>
                    <td> </td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td><a href="javascript:;">关闭</a></td>
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

  
</div>
<!-- ./wrapper -->
<%@include file="/WEB-INF/public/footer.jsp"%>
<!-- My97DatePicker -->
<!-- REQUIRED JS SCRIPTS -->
<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
<script type="text/javascript" src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>

<script>
 /*  $(function () {
    $('#turnOverTable').dataTable({//邮件抓取 table
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "columnDefs": [
            {"sWidth": "11.33%","aTargets": [0] },
            {"sWidth": "11.33%","aTargets": [1] },
            {"sWidth": "8.33%","aTargets": [2] },
            {"sWidth": "8.33%","aTargets": [3] },
            {"sWidth": "8.33%","aTargets": [4] },
            {"sWidth": "8.33%","aTargets": [5] },
            {"sWidth": "5.33%","aTargets": [6] },
            {"sWidth": "9.33%","aTargets": [7] },
            {"sWidth": "5.33%","aTargets": [8] },
            {"sWidth": "9.33%","aTargets": [9] },
            {"sWidth": "9.33%","aTargets": [10] },
            {"sWidth": "5.33%","aTargets": [11] }],
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

    $(".TabUL li").click(function(){
      $(this).addClass("tabStyle").siblings().removeClass('tabStyle');
    });*/
    //添加订单 弹框
    $('#addTurnOver').click(function(){
        layer.open({
            type: 2,
            title:false,
            skin: false, //加上边框
            closeBtn:false,//默认 右上角关闭按钮 是否显示
            shadeClose:true,
            scrollbar: false,
            area: ['1170px', '325px'],
            content: ['${url}/add.html','no']
          });
      }); 
  //});
</script>
<script type="text/javascript">
	var empTable;
	function initDatatable() {
		empTable = $('#turnOverTable').DataTable({
			"searching" : false,
			"processing" : true,
			"serverSide" : true,
			"stripeClasses": [ 'strip1','strip2' ],//斑马线
			"bLengthChange" : false,
			"bSort": true, //排序功能 
			"autoWidth": false,
			"language" : {
				"url" : "${base}/public/plugins/datatables/cn.json"
			},
	       	"ajax": {
	               "url": "${base}/admin/turnover/listData.html",
	               "type": "post",
	               "data": $('#selectTurnOver').serialize()
	        },
	        "columns": [
	                    {"data": "modifydate", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.modifydate;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance;
	                    	}			
	                    },
	                    {"data": "cardname", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.cardname;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		var depositBalance = '<span data-toggle="tooltip" data-placement="left" title="'+depositBalance+'">'+depositBalance+'<span>';
	                    		return depositBalance;
	                    	}			
	                    },
	                    {"data": "projectname", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.projectname;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance;
	                    	}			
	                    },//项目
	                    {"data": "money", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.money;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "0.00";
	                    		}
	                    		return depositBalance.toFixed(2);
	                    	}		
	                    },
	                    {"data": "purpose", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.purpose;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance;
	                    	}
	                    
	                    },
	                    {"data": "historymoney", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.historymoney;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "0.00";
	                    		}
	                    		return depositBalance.toFixed(2);
	                    	}		
	                    },
	                    {"data": "currency", "bSortable": false,
	                    	
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.currency;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance;
	                    	}	
	                    },
	                    {"data": "averagerate", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.averagerate;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance;
	                    	}	
	                    },
	                    {"data": "companyname", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.companyname;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance;
	                    	}	
	                    },//单位
	                    {"data": "invoicestatus", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.invoicestatus;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance;
	                    	}		
	                    },//发票
	                    {"data": "remark", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.remark;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		/* if(depositBalance.length>4){
	                    			var res=depositBalance.substring(0,4); 
	                    			return res+"...";
	                    		} */
	                    		var depositBalance = '<span data-toggle="tooltip" data-placement="left" title="'+depositBalance+'">'+depositBalance+'<span>';
	                    		return depositBalance;
	                    	}	
	                    }
	            ],
	        columnDefs: [{
	            //   指定第一列，从0开始，0表示第一列，1表示第二列……
	            targets: 11,
	            render: function(data, type, row, meta) {
	            	
	            	if(row.status==1){
	            		
		            	var modify = '<a style="cursor:pointer;" onclick="selectClose('+row.id+');">关闭</a>';
	            	}else{
	            		
		            	var modify = '<a style="cursor:pointer;" onclick="selectOpen('+row.id+');">启用</a>';
	            	}
	                return modify;
	            }
	        }]
		});
	}
	$(function() {
		initDatatable();
		//selectDeptName();
	});
	function successCallback(id){
		empTable.ajax.reload(null,false);
		  if(id == '1'){
			  layer.msg("添加成功",{time: 2000});
		  }else if(id == '2'){
			  layer.msg("关闭成功",{time: 2000});
		  }else if(id == '3'){
			  layer.msg("删除成功",{time: 2000});
		  }else if(id == '4'){
			  layer.msg("开启成功",{time: 2000});
		  }
	  }
	//根据不同的条件进行搜索
	function pickedFunc()
	{
		var tradeDate = $('#tradeDate').val();
		var status = $('#status').val();
		var operation = $('#operation').val();
		var bankCardName = $('#bankCardName').val();
		var projectName = $('#projectName').val();
		var currency = $('#currency').val();
		var companyName = $('#companyName').val();
	    var param = {
	        "status": status,
	        "operation": operation,
	        "bankCardName": bankCardName,
	        "projectName": projectName,
	        "currency": currency,
			"tradeDate" : tradeDate,
			"companyName" : companyName
			
	    };
	    empTable.settings()[0].ajax.data = param;
		empTable.ajax.reload();
	}
	function selectClose(id){
		
		
		layer.confirm("您确认关闭吗？", {
		    btn: ["是","否"], //按钮
		    shade: false //不显示遮罩
		}, function(){
			// 点击确定之后
			var url = '${base}/admin/bankcard/delete.html';
			$.ajax({
				type : "POST",
				url : '${base}/admin/turnover/update.html',
				data :{
					id : id
				},
				error : function(request) {
					layer.msg('关闭失败!');
				},
				success : function(data) {
					if ("200" == data.status) {
						
						layer.msg("关闭成功!", "", 3000);
						 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					     parent.layer.close(index);
					     window.parent.successCallback('2');
				 	} else {
						layer.msg("关闭失败!", "", 3000);
					} 
				}
			});
		}, function(){
		    // 取消之后不用处理
		});
		
		
		
	}
	function selectOpen(id){
		
		
		layer.confirm("您确认开启吗？", {
		    btn: ["是","否"], //按钮
		    shade: false //不显示遮罩
		}, function(){
			// 点击确定之后
			$.ajax({
				type : "POST",
				url : '${base}/admin/turnover/open.html',
				data :{
					id : id
				},
				error : function(request) {
					layer.msg('开启失败!');
				},
				success : function(data) {
					if ("200" == data.status) {
						
						layer.msg("开启成功!", "", 3000);
						 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					     parent.layer.close(index);
					     window.parent.successCallback('4');
				 	} else {
						layer.msg("开启失败!", "", 3000);
					} 
				}
			});
		}, function(){
		    // 取消之后不用处理
		});
		
		
		
	}
	function clearSelect(){
		$('#tradeDate').val("");
		$('#status').val(1);
		$('#operation').val("");
		$('#bankCardName').val("");
		$('#projectName').val("");
		$('#currency').val("");
		$('#companyName').val("");
	}
</script>
</html>
