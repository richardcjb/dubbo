<%@ page contentType="text/html; charset=UTF-8" language="java"	pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>公司字典信息</title>
<link rel="stylesheet" href="${base}/public/dist/css/dict.css">
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
								<div class="col-md-1 textPadding">
									<!--状态名称 搜索框-->
									<select id="statusId" name="status" onchange="defaultSelect();" class="form-control Padding3 input-sm">
										<c:forEach var="map" items="${obj.dataStatusEnum}">
											<c:choose>
												<c:when test="${map.key == obj.dataStatusEnum.status}">
													<option value="${map.key}" selected="selected">${map.value}</option>
												</c:when>
												<c:otherwise>
													<option value="${map.key}">${map.value}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
								<div class="col-md-3 col-padding">
									<!--字典类别名称 搜索下拉框-->
                                    <label class="col-sm-4 text-right padding"">类别名称：</label>
		                            <div class="col-sm-8 padding">
		                            	<select id="comTypeCode" name="comTypeName" class="form-control input-sm inpImpWid">
											<!-- <option value=" ">==请选择==</option> -->
		                            		<%-- <c:forEach var="map" items="${obj.dicttypelist}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach> --%>
											<c:forEach items="${obj.dicttypelist}" var="map" varStatus="indexs">
												<c:choose>
													<c:when test="${indexs.index eq 2}">
					                                 	<option value="${map.key }" selected="selected">
					                                 		${map.value }
					                                 	</option>
													</c:when>
													<c:otherwise>
					                                 	<option value="${map.key }">
					                                 		${map.value }
					                                 	</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
		                    			</select>
		                            </div>
								</div>
                                <div class="col-md-3 col-padding">
									<!--字典信息名称 搜索框-->
									<input type="text" id="comDictNameId" name="comDictName" onkeypress="onkeyEnter();" class="form-control"
										placeholder="字典信息/航空公司/第三方公司">
								</div>
								<div class="col-md-2 col-padding">
									<!--搜索 按钮-->
									<button id="comDictInfoSearchBtn" type="button" class="btn btn-primary btn-sm">搜索</button>
								</div>
							<div class="col-md-3 textPadding">
								<a class="btn btn-primary btn-sm right" onclick="add();">添加</a>
							</div>
							<!-- /.box-header -->
							<div id="datatableInfo_container" class="box-body none">
								<table id="comcomdatatableInfo" class="table table-bordered table-hover">
									<thead>
										<tr>
											<!-- <th>公司名称</th> -->
											<th>类别名称</th>
											<!-- <th>字典代码</th> -->
											<th>字典信息</th>
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
							<div id="loginNumTable_container" class="box-body">
								<table id="loginNumTable" class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>网站地址</th>
											<th>登录账号</th>
											<th>航空公司</th>
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
							<div id="thirdPayMentTable_container" class="box-body">
								<table id="thirdPayMentTable" class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>第三方公司</th>
											<th>银行卡名称</th>
											<th>卡号</th>
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
function add(){
	var comTypeCode = $("#comTypeCode").val();
	if("DLZH"==comTypeCode){
		//添加登录账号
		layer.open({
	   	    type: 2,
	   	    title:false,
	   	    closeBtn:false,
	   	    fix: false,
	   	    maxmin: false,
	   	    shadeClose: false,
	   	 	scrollbar: false,
	   	    area: ['900px', '435px'],
	   	    content: '${base}/admin/companydict/comdictinfo/addLoginNum.html',
	   	    end: function(){//添加完页面点击返回的时候自动加载表格数据
	   	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	   			parent.layer.close(index);
	   	    }
	   	 });
	}else if("DSFZF"==comTypeCode){
		//添加登录账号
		layer.open({
	   	    type: 2,
	   	    title:false,
	   	    closeBtn:false,
	   	    fix: false,
	   	    maxmin: false,
	   	    shadeClose: false,
	   	    area: ['900px', '435px'],
	   	    content: '${base}/admin/companydict/comdictinfo/addThirdPayMent.html',
	   	    end: function(){//添加完页面点击返回的时候自动加载表格数据
	   	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	   			parent.layer.close(index);
	   	    }
	   	 });
	}else{
		//添加
		layer.open({
	   	    type: 2,
	   	    title:false,
	   	    closeBtn:false,
	   	    fix: false,
	   	    maxmin: false,
	   	    shadeClose: false,
	   	    area: ['900px', '435px'],
	   	    content: '${base}/admin/companydict/comdictinfo/add.html',
	   	    end: function(){//添加完页面点击返回的时候自动加载表格数据
	   	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	   			parent.layer.close(index);
	   	    }
	   	 });
	}
}

//编辑
function edit(id){
	var comTypeCode = $("#comTypeCode").val();
	if("DLZH"==comTypeCode){
		//编辑登录账号
		layer.open({
	  	    type: 2,
	  	    title: false,
	  	  	closeBtn:false,
	  	    fix: false,
	  	    maxmin: false,
	  	    shadeClose: false,
	  	    scrollbar: false,
	  	    area: ['900px', '435px'],
	  	    content: '${base}/admin/companydict/comdictinfo/updateLoginNum.html?id='+id
	  	  });
	}else if("DSFZF"==comTypeCode){
		//编辑第三方支付
		layer.open({
	  	    type: 2,
	  	    title: false,
	  	  	closeBtn:false,
	  	    fix: false,
	  	    maxmin: false,
	  	    shadeClose: false,
	  	    area: ['900px', '435px'],
	  	    content: '${base}/admin/companydict/comdictinfo/updateThirdPayMent.html?id='+id
	  	  });
	}
	else{
		layer.open({
	  	    type: 2,
	  	    title: false,
	  	  	closeBtn:false,
	  	    fix: false,
	  	    maxmin: false,
	  	    shadeClose: false,
	  	    scrollbar: false,
	  	    area: ['900px', '435px'],
	  	    content: '${base}/admin/companydict/comdictinfo/update.html?id='+id
	  	  });
	}
  }
//事件提示
function successCallback(id){
	comcomdatatableInfo.ajax.reload(null,false);
	loginNumTable.ajax.reload(null,false);
	thirdPayMentTable.ajax.reload(null,false);
	  if(id == '1'){
		  layer.msg("添加成功",{time:2000});
	  }else if(id == '2'){
		  layer.msg("修改成功",{time:2000});
	  }else if(id == '3'){
		  layer.msg("删除成功",{time:2000});
	  }else if(id == '4'){
		  layer.msg("启用成功",{time:2000});
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
		var url = '${base}/admin/companydict/comdictinfo/updateDeleteStatus.html';
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
					if("1"==status){
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					    window.parent.successCallback('4');
					}else if("2"==status){
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					    window.parent.successCallback('3');
					}
				} else {
					layer.msg("操作失败!",{time:2000});
				}
			},
			error : function(xhr) {
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
<!-- 分页显示 -->
<script type="text/javascript">
var flag=$('#comTypeCode').val();
var comcomdatatableInfo;//登录账号Table
var thirdPayMentTable;//第三方支付Table
//初始化
$(function() {
	initDatatable();
	$('.menu-ul:eq(0)').hide();//隐藏收付款的二级菜单
	$('.menu-ul li a:eq(3)').css("color","rgb(245, 245, 245)");//二级菜单 数据字典 高亮style
	defaultSelect();
	$('#thirdPayMentTable_container').hide();
});
//判断如果是出发城市则隐藏掉其他内容
$('#comTypeCode').change(function(){
	var seleID= $(this).find('option:selected').attr("value");
	if(seleID=="DLZH"){
		$('#loginNumTable_container').show();
		$('#datatableInfo_container').hide();
		$('#thirdPayMentTable_container').hide();
	}else if(seleID=="DSFZF"){
		$('#loginNumTable_container').hide();
		$('#datatableInfo_container').hide();
		$('#thirdPayMentTable_container').show();
	}
	else{
		$('#datatableInfo_container').show();
		$('#loginNumTable_container').hide();
		$('#thirdPayMentTable_container').hide();
	}
});
//流水项目、项目用途、资金种类
function initDatatable() {
	comcomdatatableInfo = $('#comcomdatatableInfo').DataTable({
		"searching" : false,
		"processing" : true,
		"serverSide" : true,
		"stripeClasses": [ 'strip1','strip2' ],//斑马线
		"bLengthChange": false,
		"autoWidth": false,
		"language": {
			"url" : "${base}/public/plugins/datatables/cn.json"
		},
        "ajax": {
            "url": "${base}/admin/companydict/comdictinfo/comInfoListData.html",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "columns": [
                    /* {"data": "comname", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var comname = row.comname;
                    		if(null==comname || ""==comname){
                    			return "";
                    		}
                    		return comname;
                    	}	
                    }, */
                    {"data": "comtypename", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		//var comtypename = row.comtypename;
                    		var result = '';
                      		$.each(row.comdicttypeenum, function(name, value) {
                      			if(row.comtypecode == name){
                      				result = value;
                      			}
                      		});
                    		return result;
                    	}		
                    },
                    /* {"data": "comddictcode", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var comddictcode = row.comddictcode;
                    		if(null==comddictcode || ""==comddictcode){
                    			return "";
                    		}
                    		return comddictcode;
                    	}		
                    }, */
                    {"data": "comdictname", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var comdictname = row.comdictname;
                    		if(null==comdictname || ""==comdictname){
                    			return "";
                    		}
                    		return comdictname;
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
                    		 /* var createtimestr = createtime.getFullYear() + "-" + createtime.getMonth() + "-" + createtime.getDate() + " "
                    		 + createtime.getHours() + ":" + createtime.getMinutes() + ":" + createtime.getSeconds(); */
                    		return createtime;
                        } 	
                    },
                    {"data": "remark", "bSortable": false,"width":"35%",
                    	render: function(data, type, row, meta) {
                    		 var remark = row.remark;
	                   		 if(null==remark || ""==remark){
	                   			 return "";
	                   		 }
                    		 var result = '<span data-toggle="tooltip" data-placement="bottom" title="'+remark+'">'+remark+'<span>';
                    		 return result;
                    	}	
                    } ,
                    {"data": " ", "bSortable": false,
                    	render: function(data, type, row, meta) {
                        	var modify = '<a style="cursor:pointer;" onclick="edit('+row.id+');">编辑</a>';
                          		if(1==row.status){
                          			var judge = '<a href="javascript:physicalDelete('+row.id+',2)" class="btn_mini btn_modify"><font color="#CCCCCC">删除</font></a>';
                          		}else{
                          			var judge = '<a href="javascript:physicalDelete('+row.id+',1)" class="btn_mini btn_modify">启用</a>';
                          		}
                            return modify+judge;
                        }	
                    } 
            ],
            "columnDefs": [  /* {
                //   指定第一列，从0开始，0表示第一列，1表示第二列……
                targets: 5,
                render: function(data, type, row, meta) {
                	var modify = '<a style="cursor:pointer;" onclick="edit('+row.id+');">编辑</a>';
                  		if(1==row.status){
                  			var judge = '<a href="javascript:physicalDelete('+row.id+',2)" class="btn_mini btn_modify"><font color="#CCCCCC">删除</font></a>';
                  		}else{
                  			var judge = '<a href="javascript:physicalDelete('+row.id+',1)" class="btn_mini btn_modify">启用</a>';
                  		}
                    return modify+judge;
                }
            } */
            			   /* {"sWidth": "10%",Targets:0 },
                           {"sWidth": "20%",Targets:1 },
                           {"sWidth": "10%",Targets:2 },
                           {"sWidth": "20%",Targets:3 },
                           {"sWidth": "20%",Targets:4 },
                           {"sWidth": "20%",Targets:5 } */
            ]
	});
	//登录账号列表
	loginNumTable = $('#loginNumTable').DataTable({
		"searching" : false,
		"processing" : true,
		"serverSide" : true,
		"stripeClasses": [ 'strip1','strip2' ],//斑马线
		"bLengthChange" : false,
		"autoWidth": false,
		"language" : {
			"url" : "${base}/public/plugins/datatables/cn.json"
		},
        "ajax": {
            "url": "${base}/admin/companydict/comdictinfo/loginNumData.html",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "columns": [
                    {"data": "weburl", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var weburl = row.weburl;
                    		var weburl = '<span data-toggle="tooltip" data-placement="right" title="'+weburl+'">'+weburl+'<span>';
                    		if(null==weburl || ""==weburl){
                    			return "";
                    		}
                    		return weburl;
                    	}
                    },
                    {"data": "loginnumname", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var loginnumname = row.loginnumname;
                    		var loginnumname = '<span data-toggle="tooltip" data-placement="right" title="'+loginnumname+'">'+loginnumname+'<span>';
                    		if(null==loginnumname || ""==loginnumname){
                    			return "";
                    		}
                    		return loginnumname;
                    	}
                    },
                    {"data": "airlinename", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var airlinename = row.dictname;
                    		if(null==airlinename || ""==airlinename){
                    			return "";
                    		}
                    		return airlinename; 
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
                    		 var result = '<span data-toggle="tooltip" data-placement="bottom" title="'+remark+'">'+remark+'<span>';
                    		 return result;
                    	}	
                    },
                    {"data": " ", "bSortable": false,
                    	render: function(data, type, row, meta) {
                        	var modify = '<a style="cursor:pointer;" onclick="edit('+row.id+');">编辑</a>';
                    		if(1==row.status){
                    			var judge = '<a href="javascript:physicalDelete('+row.id+',2)" class="btn_mini btn_modify"><font color="#CCCCCC">删除</font></a>';
                    		}else{
                    			var judge = '<a href="javascript:physicalDelete('+row.id+',1)" class="btn_mini btn_modify">启用</a>';
                    		}
                            return modify+judge;
                        }
                    }
            ],
            "columnDefs": [/* {
                //   指定第一列，从0开始，0表示第一列，1表示第二列……
                targets: 6,
                render: function(data, type, row, meta) {
                	var modify = '<a style="cursor:pointer;" onclick="edit('+row.id+');">编辑</a>';
            		if(1==row.status){
            			var judge = '<a href="javascript:physicalDelete('+row.id+',2)" class="btn_mini btn_modify"><font color="#CCCCCC">删除</font></a>';
            		}else{
            			var judge = '<a href="javascript:physicalDelete('+row.id+',1)" class="btn_mini btn_modify">启用</a>';
            		}
                    return modify+judge;
                }
            } */
            {"sWidth": "20%",Targets:0 },
            {"sWidth": "14%",Targets:1 },
            {"sWidth": "14%",Targets:2 },
            {"sWidth": "9%",Targets:3 },
            {"sWidth": "15%",Targets:4 },
            {"sWidth": "14%",Targets:5 },
            {"sWidth": "12%",Targets:6 }
            ]
	});
	//第三方支付列表
	thirdPayMentTable = $('#thirdPayMentTable').DataTable({
		"searching" : false,
		"processing" : true,
		"serverSide" : true,
		"stripeClasses": [ 'strip1','strip2' ],//斑马线
		"bLengthChange" : false,
		"autoWidth": false,
		"language" : {
			"url" : "${base}/public/plugins/datatables/cn.json"
		},
        "ajax": {
            "url": "${base}/admin/companydict/comdictinfo/thirdPayMentData.html",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "columns": [
                    {"data": "thirdcompanyname", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var thirdcompanyname = row.thirdcompanyname;
                    		if(null==thirdcompanyname || ""==thirdcompanyname){
                    			return "";
                    		}
                    		return thirdcompanyname;
                    	}
                    },
                    {"data": "bankcardname", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var bankcardname = row.bankcardname;
                    		if(null==bankcardname || ""==bankcardname){
                    			return "";
                    		}
                    		return bankcardname;
                    	}
                    },
                    {"data": "bankcardnum", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var bankcardnum = row.bankcardnum;
                    		if(null==bankcardnum || ""==bankcardnum){
                    			return "";
                    		}
                    		return bankcardnum;
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
                    		 var result = '<span data-toggle="tooltip" data-placement="bottom" title="'+remark+'">'+remark+'<span>';
                    		 return result;
                    	}	
                    }
            ],
            "columnDefs": [{
                //   指定第一列，从0开始，0表示第一列，1表示第二列……
                targets: 6,
                render: function(data, type, row, meta) {
                	var modify = '<a style="cursor:pointer;" onclick="edit('+row.id+');">编辑</a>';
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

//公司字典信息搜索
$("#comDictInfoSearchBtn").on('click', function () {
	var status = $("#statusId").val();
	var comTypeCode = $("#comTypeCode").val();
	var comDictName = $("#comDictNameId").val();
	var airlineName = $("#comDictNameId").val();//航空公司
	var thirdCompanyName = $("#comDictNameId").val();//航空公司
    var param = {
		        "status":status,
		        "comTypeCode":comTypeCode,
		        "comDictName":comDictName,
		        "airlineName":airlineName,
		        "thirdCompanyName":thirdCompanyName
		    };
    if(status==1 ||　status==2){
    	comcomdatatableInfo.settings()[0].ajax.data = param;
    	comcomdatatableInfo.ajax.reload(
    			function(json){
    				autoHighLoad($('#comcomdatatableInfo'));
    			}
    	);
    	loginNumTable.settings()[0].ajax.data = param;
		loginNumTable.ajax.reload();
		thirdPayMentTable.settings()[0].ajax.data = param;
		thirdPayMentTable.ajax.reload();
    }
});

/* //状态选择按钮
$("#statusId").change(function(){
	$('#comDictInfoSearchBtn').click();
}); */
//状态默认选中
function defaultSelect(){
	$('#comDictInfoSearchBtn').click();
}
//公司字典类别名称选择按钮
$("#comTypeCode").change(function(){
	$('#comDictInfoSearchBtn').click();
});
//搜索回车事件
function onkeyEnter(){
	 if(event.keyCode==13){
		 $("#comDictInfoSearchBtn").click();
	 }
}
</script>
</body>
</html>
