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
  <title>航空公司政策管理</title>
  <link href="${base }/public/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="${base}/public/dist/css/policyManage.css"><!--本页面Style-->
</head>
<c:set var="url" value="${base}/admin/airlinepolicy" />
<!-- <body class="hold-transition skin-blue sidebar-mini"> -->
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
                <div class="form-group row mar cf">
                  <div class="col-md-2 textPadding">
                    <select class="form-control input-sm" name="selectCompany" id="selectCompany" onchange="pickedFunc();">
                      <!-- <option>VR</option>
                      <option>JQ</option> -->
                      <option value="">航空公司</option>
                      <c:forEach items="${obj.airlineCompanyList }" var="each">
                      	<option value="${each.airlineCompanyId }">${each.airlineCompanyName }</option>
                      </c:forEach>
                    </select>
                  </div>
                  <div class="col-md-2 textPadding paddingLeft10">
                     <select class="form-control input-sm" name="selectArea" id="selectArea" onchange="pickedFunc();">
                      <!-- <option>澳洲</option>
                      <option>亚洲</option> -->
                       <option value="">地区</option>
                      <c:forEach items="${obj.areaList }" var="each">
                      	<option value="${each.areaName}">${each.areaName }</option>
                      </c:forEach>
                    </select>
                  </div> 
                  <div class="col-md-1 textPadding paddingLeft10">
                     <select class="form-control input-sm" name="selectType" id="selectType" onchange="pickedFunc();">
                     
                      <option value="">请选择</option>
                      <option>团</option>
                      <option>散</option>
                    </select>
                  </div>
                  <div class="col-md-1 textPadding paddingLeft10">
                     
                  	 <input type="text" class="form-control input-sm"  name="beginTime" id="beginTime" value="" onFocus="WdatePicker({onpicked:pickedFunc,oncleared:pickedFunc,el:'beginTime',dateFmt:'yyyy-MM-dd'})"  >
                  </div>
                  <div class="col-md-1 textPadding paddingLeft10">
                  	  <input type="text" class="form-control input-sm"  name="endTime" id="endTime" value="" onFocus="WdatePicker({onpicked:pickedFunc,oncleared:pickedFunc,dateFmt:'yyyy-MM-dd'})"  >
                  </div>
                  <div class="col-md-1 textPadding paddingLeft10">
                  	  <button type="button" class="btn btn-primary btn-sm suBtn" onclick="clearSelect();pickedFunc();">清空</button>
                  </div>
                  <div class="col-md-3 padding right">
                   <%-- <a href="${base}/admin/airlinepolicy/add.html" class="btn btn-primary btn-sm right">上传</a> --%>
                    <!--  <button id="file" name="file" type="file" class="btn btn-primary btn-sm right" >上传</button> -->
                     <button id="addFile" name="add" type="button" class="btn btn-primary btn-sm right" >添加</button>
                  	<%-- <form action="${base}/admin/airlinepolicy/uploadFile.html" method="post" id="uploadForm" enctype="multipart/form-data">
                  		<input name="file" id="file"  type="file"/>
                  		<input type="submit" onclick="onfileChange();" value="提交">
                  	</form> --%>
                  </div>
                </div>
              </form>
            </div>
            <div class="box-body">
              <table id="poilcyManageTable" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>文件名</th>
                  <th>航空公司</th>
                  <th>地区</th>
                  <th>修改日期</th>
                  <th>类型</th>
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

 
  <!-- <div class="control-sidebar-bg"></div> -->
</div>
<!-- ./wrapper -->

<%@include file="/WEB-INF/public/footer.jsp"%>
<!-- REQUIRED JS SCRIPTS -->
<script type="text/javascript" src="${base }/public/plugins/uploadify/jquery.uploadify.min.js"></script>
<!-- jQuery 2.2.3 -->
<script type="text/javascript" src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
<script>
 /*  $(function () {
    $('#poilcyManageTable').dataTable({//航空公司政策管理 table
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "columnDefs": [
            {"sWidth": "70%","aTargets": [0] },
            {"sWidth": "30%","aTargets": [1] }],
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
  }); */
</script>
<script type="text/javascript">
	//列表展示
	var empTable;
	function initDatatable() {
		empTable = $('#poilcyManageTable').DataTable({
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
	               "url": "${base}/admin/airlinepolicy/listData.html",
	               "type": "post",
	               "data": function (d) {
	            	   
	            	}
	        },
	        "columns": [
	                    {"data": "filename", "bSortable": false},
	                    {"data": "airlinecompanyname", "bSortable": false},
	                    {"data": "areaname", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.areaname;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance;
	                    	}		
	                    },
	                    {"data": "updatetime", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.updatetime;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance.substring(0,depositBalance.lastIndexOf(' '));
	                    	}	
	                    },
	                    {"data": "type", "bSortable": false}
	                    ],
	        "columnDefs": [{ "sWidth": "34.66%",  "targets": [0] },
	     				{ "sWidth": "8.66%",  "targets": [1] },
	    				{ "sWidth": "16.66%",  "targets": [2] },
	    				{ "sWidth": "11.66%",  "targets": [3] },
	    				{ "sWidth": "7.66%",  "targets": [4] },
	    				{ "sWidth": "26.66%",  "targets": [5] },
	                    {
	            //   指定第一列，从0开始，0表示第一列，1表示第二列……
	            targets: 5,
	            render: function(data, type, row, meta) {
	            	
	            	var modify1 = '<a style="cursor:pointer;" href="'+row.pdfurl+'" target="_blank">预览</a>';
	            	var modify2 = '<a style="cursor:pointer;" onclick="update('+row.id+');">编辑</a>';
	            	if(row.filename ==null || row.filename == ""){
	            		
	            		var modify3 = '<a style="cursor:pointer;" href="#">下载</a>';
	            	}else{
	            		var modify3 = '<a style="cursor:pointer;" href="${base}/admin/airlinepolicy/download.html?id='+row.id+'">下载</a>';
	            		
	            	}
	            	var modify4 = '<a style="cursor:pointer;" onclick="deleteFile('+row.id+');">删除</a>';
	                return modify1+"&nbsp; &nbsp; &nbsp;"+modify2+"&nbsp; &nbsp; &nbsp;"+modify3+"&nbsp; &nbsp; &nbsp;"+modify4;
	            }
	        }]
		});
	}
	$(function() {
		initDatatable();
		//selectDeptName();
		/* uploadFile(); */
	});
	
	function successCallback(id){
		 // rebatesEamilTable.ajax.reload(null,false);
		  //rebatesReportTable.ajax.reload(null,false);
		  /* empTable.ajax.reload(null,false); */
		  window.location.href="${base}/admin/airlinepolicy/list.html"; 
		  location=location;
		  if(id == '1'){
			  layer.msg("添加成功!",{time: 1000});
		  }else if(id == '2'){
			  layer.msg("编辑成功!",{time: 1000});
		  }else if(id == '3'){
			  layer.msg("删除成功!",{time: 1000});
		  }else if(id == '4'){
			  layer.msg("移动成功!",{time: 1000});
		  }else if(id == '5'){
			  layer.msg("抓取成功!",{time: 1000});
		  }else if(id == '6'){
			  layer.msg("上传成功!",{time: 1000});
		  }else if(id == '7'){
			  layer.msg("文件下载成功!",{time: 1000});
		  }else if(id == '8'){
			  layer.msg("添加成功!",{time: 1000});
		  }else if(id == '9'){
			  layer.msg("编辑成功!",{time: 1000});
		  }
	  }
	
	//删除
	function deleteFile(id){
		layer.confirm("您确认删除信息吗？", {
		    btn: ["是","否"], //按钮
		    shade: false //不显示遮罩
		}, function(){
			// 点击确定之后
			var url = '${base}/admin/airlinepolicy/delete.html';
			$.ajax({
				type : 'POST',
				data : {
					id : id
				},
				dataType : 'json',
				url : url,
				success : function(data) {
					if ("200" == data.status) {
						layer.msg("删除成功!", "", 3000);
						window.parent.successCallback('3');
						/* window.location.reload();  */
						
					} else {
						layer.msg("删除失败!", "", 3000);
					}
				},
				error : function(xhr) {
					layer.msg("删除失败!", "", 3000);
				}
			});
		}, function(){
		    // 取消之后不用处理
		});
	}
	
	
	 $(function add() {
		    //添加订单 弹框
		    $('#addFile').click(function(){
		        layer.open({
		            type: 2,
		            title:false,
		            skin: false, //加上边框
		            closeBtn:false,//默认 右上角关闭按钮 是否显示
		            shadeClose:true,
		            scrollbar: false,
		            area: ['870px', '320px'],
		            content: ['${url}/add.html','no']
		          });
		      });
		  }
		  );
	 function update(id) {
		    //更新 弹框
		        layer.open({
		            type: 2,
		            title:false,
		            skin: false, //加上边框
		            closeBtn:false,//默认 右上角关闭按钮 是否显示
		            shadeClose:true,
		            scrollbar: false,
		            area: ['870px', '270px'],
		            content: '${url}/update.html?id='+id
		          });
		      
		  }
	 function downloadFile(id) {
		    //更新 弹框
				var url = '${base}/admin/airlinepolicy/download.html';
				$.ajax({
					type : 'POST',
					data : {
						id : id
					},
					dataType : 'json',
					url : url,
					success : function(data) {
						if ("200" == data.status) {
							layer.msg("下载成功!", "", 3000);
							window.parent.successCallback('3');
							/* window.location.reload();  */
							
						} else {
							layer.msg("下载失败!", "", 3000);
						}
					},
					error : function(xhr) {
						layer.msg("下载失败!", "", 3000);
					}
				});
			
		      
		  }
	 
	//根据不同的条件进行搜索
	function pickedFunc(){
		
		var airlineCompanyId = $('#selectCompany').val();
		var areaName = $('#selectArea').val();
		var type = $('#selectType').val();
		var beginTime = $('#beginTime').val();
		var endTime = $('#endTime').val();
		/* var bankCardName = $('#bankCardName').val();
		var projectName = $('#projectName').val();
		var currency = $('#currency').val();
		var companyName = $('#companyName').val(); */
	    var param = {
	        "airlineCompanyId": airlineCompanyId,
	        "areaName": areaName,
	        "type": type,
			"endTime":endTime,
			"beginTime":beginTime
			
	    };
	    empTable.settings()[0].ajax.data = param;
		empTable.ajax.reload();
		/* alert("==========="); */
	}
	function clearSelect(){
		$('#selectCompany').val("");
		$('#selectArea').val("");
		$('#selectType').val("");
		$('#beginTime').val("");
		$('#endTime').val("");
		
	}
</script>
<!-- </body> -->
</html>
