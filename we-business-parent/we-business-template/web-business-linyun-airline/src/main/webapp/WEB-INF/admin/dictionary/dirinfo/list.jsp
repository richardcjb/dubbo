<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>字典信息</title>
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
								<div class="col-md-3 dictInfoSousuo" style="float:left;">
									<!--字典类别名称 搜索下拉框-->
                                    <label class="col-sm-4 text-right padding">类别名称：</label>
		                            <div class="col-sm-8 padding">
		                            	<select id="typeCode" name="typeCode" onchange="selectTypeName();" class="form-control input-sm inpImpWid">
											<c:forEach items="${obj.deplist}" var="one" varStatus="indexs">
												<c:choose>
													<c:when test="${indexs.index eq 0}">
					                                 	<option value="${one.typecode }" selected="selected">
					                                 		${one.typename }
					                                 	</option>
													</c:when>
													<c:otherwise>
					                                 	<option value="${one.typecode }">
					                                 		${one.typename }
					                                 	</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
		                    			</select>
		                            </div>
								</div>
                                <div class="col-md-3 dictInfoSousuo" style="float:left;">
									<!--字典信息名称 搜索框-->
									<input type="text" id="dictName" name="dictName" onkeypress="onkeyEnter();" class="form-control"
										placeholder="字典信息/中文名称">
								</div>
								<div class="col-md-2 col-padding">
									<!--搜索 按钮-->
									<button id="searchBtn" type="button" class="btn btn-primary btn-sm">搜索</button>
								</div>
							<div class="col-md-1 col-md-offset-1">
								<a class="btn btn-primary btn-sm" onclick="add();">添加</a>
							</div>
							<!-- /.box-header -->
							<div id="datatableInfo_container" class="box-body none">
								<table id="datatableInfo" class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>类别名称</th>
											<th>字典代码</th>
											<th>字典信息</th>
											<th>描述</th>
											<th>状态</th>
											<th>创建时间</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
							<div id="departurecityTable_container" class="box-body">
								<table id="departurecityTable" class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>类别名称</th>
											<th>三字代码</th>
											<th>国家</th>
											<th>州</th>
											<th>英文名称</th>
											<th>中文名称</th>
											<th>拼音</th>
											<th>状态</th>
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
	<script type="text/javascript">
		function add(){
			var typeCode = $("#typeCode").val();
			if("CFCS"==typeCode){
				//添加出发城市
			    layer.open({
		    	    type: 2,
		    	    title:false,
		    	    closeBtn:false,
		    	    fix: false,
		    	    maxmin: false,
		    	    shadeClose: false,
		    	    scrollbar: false,
		    	    area: ['920px', '500px'],
		    	    content: '${base}/admin/dictionary/dirinfo/addDepartureCity.html',
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
		    	    scrollbar: false,
		    	    area: ['900px', '435px'],
		    	    content: '${base}/admin/dictionary/dirinfo/add.html',
		    	    end: function(){//添加完页面点击返回的时候自动加载表格数据
		    	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		    			parent.layer.close(index);
		    	    }
		    	 });
			}
		}
		
		//编辑
		function edit(id){
			var typeCode = $("#typeCode").val();
			if("CFCS"==typeCode){
				layer.open({
			  	    type: 2,
			  	    title: false,
			  	  	closeBtn:false,
			  	    fix: false,
			  	    maxmin: false,
			  	    shadeClose: false,
			  	    scrollbar: false,
			  	  	area: ['920px', '500px'],
			  	    content: '${base}/admin/dictionary/dirinfo/updateDepartureCity.html?id='+id
			  	  });
			}else{
				layer.open({
			  	    type: 2,
			  	    title: false,
			  	  	closeBtn:false,
			  	    fix: false,
			  	    maxmin: false,
			  	    shadeClose: false,
			  	    scrollbar: false,
			  	    area: ['900px', '435px'],
			  	    content: '${base}/admin/dictionary/dirinfo/update.html?id='+id
			  	  });
			}
		  }
		//事件提示
		function successCallback(id){
			  datatableInfo.ajax.reload(null,false);
			  departurecityTable.ajax.reload(null,false);
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
				var url = '${base}/admin/dictionary/dirinfo/updateDeleteStatus.html';
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
		var flag=$('#typeCode').val();
		var datatableInfo;
		var departurecityTable;
		//初始化
		$(function() {
			initDatatable();
			selectTypeName();
			$('.menu-ul:eq(0)').hide();//隐藏收付款的二级菜单
			$('.menu-ul li a:eq(3)').css("color","rgb(245, 245, 245)");//二级菜单 数据字典 高亮style
		});
		//判断如果是出发城市则隐藏掉其他内容
		$('#typeCode').change(function(){
			var seleID= $(this).find('option:selected').attr("value");
			if(seleID=="CFCS"){
				$('#departurecityTable_container').show();
				$('#datatableInfo_container').hide();
			}else{
				$('#datatableInfo_container').show();
				$('#departurecityTable_container').hide();
			}
		});
		
		//出发城市dataTable
		function initDatatable() {
			datatableInfo = $('#datatableInfo').DataTable({
				"searching" : false,
				"processing" : true,
				"serverSide" : true,
				"stripeClasses": [ 'strip1','strip2' ],//斑马线
				"bLengthChange" : false,
				"language" : {
					"url" : "${base}/public/plugins/datatables/cn.json"
				},
		        "ajax": {
		            "url": "${base}/admin/dictionary/dirinfo/listData.html",
		            "type": "post",
		            "data": function (d) {
		            }
		        },
		        "columns": [
		                    {"data": "typename", "bSortable": false,
		                    	render: function(data, type, row, meta) {
		                    		var typename = row.typename;
		                    		if(null==typename || ""==typename){
		                    			return "";
		                    		}
		                    		return typename;
		                    	}	
		                    },
		                    {"data": "dictcode", "bSortable": false,
		                    	render: function(data, type, row, meta) {
		                    		var dictcode = row.dictcode;
		                    		if(null==dictcode || ""==dictcode){
		                    			return "";
		                    		}
		                    		return dictcode;
		                    	}	
		                    },
		                    {"data": "dictname", "bSortable": false,
		                    	render: function(data, type, row, meta) {
		                    		var dictname = row.dictname;
		                    		if(null==dictname || ""==dictname){
		                    			return "";
		                    		}
		                    		return dictname;
		                    	}
		                    },
		                    {"data": "description", "bSortable": false,
			                    render: function(data, type, row, meta) {
		                    		 var description = row.description;
			                   		 if(null==description || ""==description){
			                   			 return "";
			                   		 }
		                    		 var result = '<span data-toggle="tooltip" data-placement="left" title="'+description+'">'+description+'<span>';
		                    		 return result;
		                    	}
		                    },
		                    {"data": "statusname", "bSortable": true,
		                    	render: function(data, type, row, meta) {
		                    		var statusname = row.statusname;
		                    		if(null==statusname || ""==statusname){
		                    			return "";
		                    		}
		                    		return statusname;
		                    	}	
		                    }, 
		                    {"data": "createtime", "bSortable": true,
		                    	 render: function(data, type, row, meta) {
		                    		 var createtime =row.createtime;
		                    		 if(null==createtime || ""==createtime){
		                    			 return "";
		                    		 }
		                    		return createtime;
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
		            }/* ,
					{ "sWidth": "14%", "aTargets": [0] },
					{ "sWidth": "9%", "aTargets": [1] },
					{ "sWidth": "9%", "aTargets": [2] },
					{ "sWidth": "16%", "aTargets": [3] },
					{ "sWidth": "6%", "aTargets": [4] },
					{ "sWidth": "12%", "aTargets": [5] },
					{ "sWidth": "10%", "aTargets": [6] } */
		            ]
			});
			departurecityTable = $('#departurecityTable').DataTable({
				"searching" : false,
				"processing" : true,
				"serverSide" : true,
				"stripeClasses": [ 'strip1','strip2' ],//斑马线
				"bLengthChange" : false,
				"language" : {
					"url" : "${base}/public/plugins/datatables/cn.json"
				},
		        "ajax": {
		            "url": "${base}/admin/dictionary/dirinfo/departureCityData.html",
		            "type": "post",
		            "data": function (d) {
		            	
		            }
		        },
		        "columns": [
		                    {"data": "typename", "bSortable": false,
		                    	render: function(data, type, row, meta) {
		                    		var typename = row.typename;
		                    		if(typename=="" || null==typename){
		                    			return "";
		                    		}
		                    		return typename;
		                        } 
		                    },
		                    {"data": "dictcode", "bSortable": false,
		                    	render: function(data, type, row, meta) {
		                    		var dictcode = row.dictcode;
		                    		if(dictcode=="" || null==dictcode){
		                    			return "";
		                    		}
		                    		return dictcode;
		                        } 	
		                    },
		                    {"data": "countryname", "bSortable": false,
		                    	render: function(data, type, row, meta) {
		                    		var countryname = row.countryname;
		                    		if(countryname=="" || null==countryname){
		                    			return "";
		                    		}
		                    		return countryname;
		                        }
		                    },
		                    {"data": "statename", "bSortable": false,
		                    	render: function(data, type, row, meta) {
		                    		var statename = row.statename;
		                    		if(statename=="" || null==statename || undefined==statename){
		                    			return "";
		                    		}
		                    		return statename;
		                        } 
		                    },
		                    {"data": "englishname", "bSortable": false,
		                    	render: function(data, type, row, meta) {
		                    		var englishname = row.englishname;
		                    		if(englishname=="" || englishname==null || undefined==englishname){
		                    			return "";
		                    		}
		                    		return englishname;
		                        } 	
		                    },
		                    {"data": "chinesename", "bSortable": false,
		                    	render: function(data, type, row, meta) {
		                    		var chinesename = row.chinesename;
		                    		if(chinesename=="" || chinesename==null || undefined==chinesename){
		                    			return "";
		                    		}
		                    		return chinesename;
		                        }	
		                    },
		                    {"data": "pinyin", "bSortable": false,
		                    	render: function(data, type, row, meta) {
		                    		var pinyin = row.pinyin;
		                    		if(pinyin=="" || pinyin==null || undefined==pinyin){
		                    			return "";
		                    		}
		                    		return pinyin;
		                        }
		                    },
		                    {"data": "statusname", "bSortable": true}
		            ],
		            "columnDefs": [{
		                //   指定第一列，从0开始，0表示第一列，1表示第二列……
		                targets: 8,
		                render: function(data, type, row, meta) {
		                	var modify = '<a style="cursor:pointer;" onclick="edit('+row.id+');">编辑</a>';
                    		if(1==row.status){
                    			var judge = '<a href="javascript:physicalDelete('+row.id+',2)" class="btn_mini btn_modify"><font color="#CCCCCC">删除</font></a>';
                    		}else{
                    			var judge = '<a href="javascript:physicalDelete('+row.id+',1)" class="btn_mini btn_modify">启用</a>';
                    		}
		                    return modify+judge;
		                }
		            }/* ,
					{ "sWidth": "8%", "aTargets": [0] },
					{ "sWidth": "8%", "aTargets": [1] },
					{ "sWidth": "9%", "aTargets": [2] },
					{ "sWidth": "12%", "aTargets": [3] },
					{ "sWidth": "12%", "aTargets": [4] },
					{ "sWidth": "12%", "aTargets": [5] },
					{ "sWidth": "6%", "aTargets": [6] },
					{ "sWidth": "5%", "aTargets": [7] },
					{ "sWidth": "8%", "aTargets": [8] } */
		            ]
			});
		}
		//搜索
		$("#searchBtn").on('click', function () {
			var dictName = $("#dictName").val();
			var status = $('#status').val();
			var typeCode = $('#typeCode').val();
		    var param = {
		        "dictName": dictName,
				"status":status,
				"typeCode":typeCode,
				"chineseName": dictName
		    };
				departurecityTable.settings()[0].ajax.data = param;
				departurecityTable.ajax.reload();
				datatableInfo.settings()[0].ajax.data = param;
			    datatableInfo.ajax.reload();
		});
		//搜索回车事件
		function onkeyEnter(){
			 if(event.keyCode==13){
				 $("#searchBtn").click();
			 }
		}
		//筛选条件自动切换
		function selectTypeName(){
			$("#searchBtn").click();
		}
		//状态默认选中
		function defaultSelect(){
			$("#searchBtn").click();
		}
	</script>
</body>
</html>