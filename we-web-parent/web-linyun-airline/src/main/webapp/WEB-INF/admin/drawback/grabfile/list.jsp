<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>退税</title>
	<link href="${base }/public/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${base}/public/dist/css/grabMail.css"><!--本页面样式-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!--内容-->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
          <div class="row">
            <div class="col-md-12">
              <!-- Custom Tabs -->
              <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                      <li class="active"><a href="#tab_1" data-toggle="tab">邮件抓取</a></li>
                      <li><a href="#tab_2" data-toggle="tab">报表</a></li>
                </ul>
                <div class="tab-content">
                  <!--邮件抓取-->
                  <div class="tab-pane pane-content active" id="tab_1">
                   <div class="rebatesBtn">
                     <button type="button" onclick="batchDelete();" class="btn btn-primary btn-sm right noneBtn none">批量删除</button>
                     <!-- <button type="button" class="btn btn-primary btn-sm right noneBtn none">移动到</button> -->
                     <button type="button" class="btn btn-primary btn-sm right batchBtn">批量操作</button>
                     <button id="folderId" name="createFolder" onclick='newFolder();' type="button" class="btn btn-primary btn-sm right carrynews">新建文件夹</button>
                     <button id="grabMailId" name="grabMailName" type="button" class="btn btn-primary btn-sm right">邮件抓取</button>
                     <button id="uploadFile" onclick="uploadFile();" name="fileID" type="file" class="btn btn-primary btn-sm right">上传</button>
                     <button type="button" class="btn btn-primary btn-sm right returnBtn none">返回上一级</button>
                     <button type="button" class="btn btn-primary btn-sm right indexBtn none">返回首页</button>
                   </div>
                   
                   <input id="currentDirId" type="hidden" value="0"/>
                   <input type="hidden" name="fileName" id="fileName" />
				   <input type="hidden" id="url">
                   <ol class="breadcrumb">
                        <li><a href="${base}/admin/drawback/grabfile/list.html"><i class="fa fa-folder-open"></i> 全部文件</a></li>
                   </ol>
                   <table id="rebatesEamilTable" class="table table-bordered table-hover">
                     <thead>
                      <tr>
                       <th><input  class="checkTh" id="fileCheckbox" name="fileCheckbox" type="checkbox"></th>
	                       <th>文件名</th>
	                       <th>时间</th>
	                       <th>大小</th>
	                       <th>操作</th>
                      </tr>
                     </thead>
                     <form id="folderForm" method="post">
                     	<tbody>
                     		<div  class="alldom">
								<ul id="divall">
								</ul>
							</div>
                     	</tbody>
                     </form>
                   </table>
                   <input id="checkedboxval" name="checkedboxval" type="hidden">
                  </div><!--end 邮件抓取-->

                  <!------------------------------------------报表开始---------------------------------------------->
                  <div class="tab-pane pane-content" id="tab_2">
                        <table id="rebatesReportTable" class="table table-bordered table-hover">
                          <thead>
                          <tr>
                          	<th>PNR</th>
                            <th>汇款</th>
                            <th>备用金余额</th>
                            <th>票价</th>
                            <th>刷卡费</th>
                            <th>税金/杂项</th>
                            <th>消费税</th>
                            <th>代理费</th>
                            <th>税返点</th>
                            <th>退税状态</th>
                            <th>实收单价</th>
                            <th>实收合计</th>
                            <th>代理费</th>
                            <th>入澳时间</th>
                            <th>出澳时间</th>
                            <!-- <th>操作</th> -->
                          </tr>
                          </thead>
                          <tbody>
                          </tbody>
                        </table>
                  </div><!--end 报表-->

                </div><!-- end tab-content -->
              </div><!-- end nav-tabs-custom -->
            </div><!-- end col-md-12 -->
          </div><!-- end row -->
   </section>
    <!-- /.content -->
  </div>
  <!--end 内容-->
  <!-- Main footer -->
  <%@include file="/WEB-INF/public/footer.jsp"%>
  <!--end footer-->
<script type="text/javascript" src="${base }/public/plugins/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript">
$(function () {
	//批量管理 点击操作
    $(".batchBtn").click(function(){
        $('.noneBtn').toggle();
        //$('.checkTh').toggle();
        $('.checkTd').toggle();
    });
  	//复选框选择点击操作
//控制复选框
$(".checkTh").click(function () {
    var check = $(this).prop("checked");
    $(".checkchild").prop("checked", check);
    //隐藏域的值
    var hiddenval = $('#checkedboxval').val();
	if(check){
		var splits = hiddenval.split(',');
		$(".checkchild:checked").each(function(){
			var thisvals = $(this).val();
			var flag = false;
			for(var i=0;i<splits.length;i++){
				if(splits[i] == thisvals){
					flag = true;
				}
			}
			//如果隐藏域值为空
			if(hiddenval){
				if(!flag){
					hiddenval += ',' + thisvals;
				}
			}else{
				hiddenval = thisvals;
			}
		});
	}else{
		$(".checkchild").each(function(){
			var thisval = $(this).val();
			var flag = false;
			var splits = hiddenval.split(',');
			for(var i=0;i<splits.length;i++){
				if(splits[i] == thisval){
					flag = true;
				}
			}
			//如果隐藏域值为空
			if(flag){
				var ids = [];
				for(var i=0;i<splits.length;i++){
					if(splits[i] != thisval){
						ids.push(splits[i]);
					}
				}
				ids = ids.join(',');
				hiddenval = ids;
			}
		});
	}
	$('#checkedboxval').val(hiddenval);
});
//点击之后给隐藏域赋值
$(document).on('click', '.checkchild', function(e) {
	var hiddenval = $('#checkedboxval').val();
	var thisval = $(this).val();
	var check = $(this).prop("checked");
	if(check){
		if(!hiddenval){
			$('#checkedboxval').val(thisval);
		}else{
			$('#checkedboxval').val(hiddenval+','+thisval);
		}
	}else{
		var splits = hiddenval.split(',');
		var flag = false;
		for(var i=0;i<splits.length;i++){
			if(splits[i] == thisval){
				flag = true;
			}
		}
		//如果存在则删掉当前值
		if(flag){
			var ids = [];
			for(var i=0;i<splits.length;i++){
				if(splits[i] != thisval){
					ids.push(splits[i]);
				}
			}
			ids = ids.join(',');
			$('#checkedboxval').val(ids);
		}else{
			$('#checkedboxval').val(hiddenval);
		}
	}
	var length = $(".checkchild:checked").length;
	if(rebatesEamilTable.page.len() == length){
		$(".checkTh").prop("checked", true);
	}else{
		$(".checkTh").prop("checked", false);
	}
});
    //文件上传
    $(uploadFile());
    function uploadFile(){
    	$.fileupload1 = $('#uploadFile').uploadify({
    		'auto' : true,//选择文件后自动上传
    		'formData' : {
    			'fcharset' : 'uft-8',
    			'action' : 'uploadimage'
    		},
    		'buttonText' : '上传',//按钮显示的文字
    		'fileSizeLimit' : '3000MB',
    		'fileTypeDesc' : '文件',//在浏览窗口底部的文件类型下拉菜单中显示的文本
    		'fileTypeExts' : '*.png; *.txt; *.doc; *.pdf; *.xls; *.jpg; *.docx; *.xlsx;',//上传文件的类型
    		'swf' : '${base}/public/plugins/uploadify/uploadify.swf',//指定swf文件
    		'multi' : false,//multi设置为true将允许多文件上传
    		'successTimeout' : 1800,
    		'queueSizeLimit' : 100,
    		'uploader' : '${base}/admin/drawback/grabfile/uploadFile.html',//后台处理的页面
    		//onUploadSuccess为上传完视频之后回调的方法，视频json数据data返回，
    		//下面的例子演示如何获取到vid
    		'onUploadSuccess' : function(file, data, response) {
    			var jsonobj = eval('(' + data + ')');
    			var url  = jsonobj;//地址
    			var fileName = file.name;//文件名称
    			var id = $("input#currentDirId").val();//文件pid
    			$.ajax({
    				cache : false,
    				type : "POST",
    				data : {
    					url : url,
    					fileName : fileName,
    					id:id
    				},
    				dataType : 'json',
    				url : '${base}/admin/drawback/grabfile/saveUploadFile.html',
    				error : function(request) {
    					layer.msg('上传失败!',{time:2000});
    				},
    				success : function(data) {
    				    window.parent.successCallback('6');
    					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    				    parent.layer.close(index);
    				}
    			});
    			
    			var innerHtml = "";
                if (response) {
                    innerHtml = "<div><a id='downloadA' href='#' download='"+file.name+"' onclick='downloadFile("
                            + data
                            + ");' >"
                            + file.name
                            + "</a>&nbsp;&nbsp;<span>上传成功</span>&nbsp;&nbsp;&nbsp;&nbsp;"
                            + data + "'></div>";
                } else {
                    innerHtml = "<div>该附件上传失败，请重新上传</div>";
                }
                $("#completeFileName").html($("#completeFileName").html() + innerHtml);
    		},
            //加上此句会重写onSelectError方法【需要重写的事件】
            'overrideEvents': ['onSelectError', 'onDialogClose'],
            //返回一个错误，选择文件的时候触发
            'onSelectError':function(file, errorCode, errorMsg){
                switch(errorCode) {
                    case -110:
                        alert("文件 ["+file.name+"] 大小超出系统限制！");
                        break;
                    case -120:
                        alert("文件 ["+file.name+"] 大小异常！");
                        break;
                    case -130:
                        alert("文件 ["+file.name+"] 类型不正确！");
                        break;
                }
            }
    	});
    }
    /* $('#uploadFile').click(function(){
    	
    }); */
});
</script>
<script type="text/javascript">
//单条删除
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
		var url = '${base}/admin/drawback/grabfile/delete.html';
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
					window.parent.successCallback('3');
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
//批量删除
function batchDelete(){
	var ids = $('#checkedboxval').val();
	var length = $(".checkchild:checked").length;
	if(!ids){
		layer.msg("请至少选中一条记录","", 1000);
	}else{
		layer.confirm("确定要批量删除吗?", {
		    btn: ["是","否"], //按钮
		    shade: false //不显示遮罩
		}, function(){
			$.ajax({
				type: 'POST', 
				data: {ids:ids}, 
				url: '${base}/admin/drawback/grabfile/batchDelete.html',
				success: function (data) { 
					layer.msg("批量删除成功!", "", 1000);
					$('#checkedboxval').val('');
					rebatesEamilTable.ajax.reload(null,false);
					$('.checkTh').attr('checked',false);
				},
				error: function (xhr) {
					layer.msg("批量删除失败!", "", 1000);
				} 
			});
		});
	}
}
//备注提示信息弹出层Tooltip
$(function() {
	$("[data-toggle='tooltip']").tooltip();
});

//移动到
function move(id){
     layer.open({
   	    type: 2,
   	    title:false,
   	    closeBtn:false,
   	    fix: false,
   	    maxmin: false,
   	    shadeClose: false,
   	    area: ['800px', '500px'],
   	    content: '${base}/admin/drawback/grabfile/move.html?id='+id,
   	    end: function(){//添加完页面点击返回的时候自动加载表格数据
   	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
   			parent.layer.close(index);
   	    }
  	});
}
//邮件抓取入口
$('#grabMailId').click(function(){
	$.ajax({
		cache : false,
		type : "POST",
		url : '${base}/admin/drawback/grabfile/grabMail.html',
		error : function(request) {
			layer.msg('抓取失败!');
		},
		success : function(data) {
			layer.load(1, {
				 shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
		    window.parent.successCallback('5');
		    parent.location.reload(); // 父页面刷新
		}
	});
});
//事件提示
function successCallback(id){
	  rebatesEamilTable.ajax.reload(null,false);
	  rebatesReportTable.ajax.reload(null,false);
	  if(id == '1'){
		  layer.msg("新建文件夹成功!",{time:2000});
	  }else if(id == '2'){
		  layer.msg("修改文件夹名称成功!",{time:2000});
	  }else if(id == '3'){
		  layer.msg("删除成功!",{time:2000});
	  }else if(id == '4'){
		  layer.msg("移动成功!",{time:2000});
	  }else if(id == '5'){
		  layer.msg("抓取成功!",{time:2000});
	  }else if(id == '6'){
		  layer.msg("上传成功!",{time:2000});
	  }else if(id == '7'){
		  layer.msg("文件下载成功!",{time:2000});
	  }else if(id == '8'){
		  layer.msg("添加成功!",{time:2000});
	  }else if(id == '9'){
		  layer.msg("编辑成功!",{time:2000});
	  }
  }
</script>
<!---------------------------------------------------- 邮件抓取分页显示 ----------------------------------------------------->
<script type="text/javascript">
	//初始化表格
	$(function() {
		initDatatable();
	});
	var rebatesEamilTable;
	var options = {
			"searching" : false,
			"processing" : true,
			"serverSide" : true,//左下角括号中页数的显示
			"bPaginate":true,//左下角分页显示
			"info":true,//右下角分页显示
			"bLengthChange" : false,
			"stripeClasses": [ 'strip1','strip2' ],//斑马线
			"bJQueryUI": true,
			"language" : {
				"url" : "${base}/public/plugins/datatables/cn.json"
			},
	        "ajax": {
	            "url": "${base}/admin/drawback/grabfile/listData.html",
	            "type": "post",
	            "data": function (d) {
	            	return {parentId:0}
	            }
	        },
	        "columns": [
						/*  */
						{"data": "id", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var result = '';
	                    		var hiddenval = $('#checkedboxval').val();
	                    		var splits = hiddenval.split(',');
	                    		var flag = false;
	                    		for(var i=0;i<splits.length;i++){
	                    			if(splits[i] == row.id){
	                    				flag = true;
	                    			}
	                    		}	
	                    		if(flag){
	                    			result = '<input type="checkbox"  class="checkchild" checked="true" value="' + row.id + '" />';
	                    		}else{
	                    			result = '<input type="checkbox"  class="checkchild" value="' + row.id + '" />';
	                    		}
	                            return result;
	                    	}
						},
	                    {"data": "filename", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var filename = row.filename;
	                    		var fileId  = row.id;
	                    		var filetype = row.type;
	                    		var fileExtension = filename.substring(filename.lastIndexOf('.') + 1);
	                    		if(null==filename || ""==filename){
	                    			return null;
	                    		}
	                    		if(filetype===1){
	                    			return '<a id="'+fileId+'" href="javascript:createFodler('+row.id+',\''+filename+'\','+filetype+',1);">'+filename+'</a>';
	                    		}else if(filetype===2 && fileExtension==="pdf"){
	                    			return  '<a id="'+fileId+'" class="fa fa-file-pdf-o" href="javascript:createFodler('+row.id+',\''+filename+'\','+filetype+',1);">'+filename+'</a>';
	                    		}else if(filetype===2 && fileExtension==="PNG"){
	                    			return  '<a id="'+fileId+'" class="fa fa-area-chart" href="javascript:createFodler('+row.id+',\''+filename+'\','+filetype+',1);">'+filename+'</a>';
	                    		}else{
	                    			return  '<a id="'+fileId+'" class="fa fa-file-text" href="javascript:createFodler('+row.id+',\''+filename+'\','+filetype+',1);">'+filename+'</a>';
	                    		}
	                    	}
	                    },
	                    {"data": "createtime", "bSortable": true,
	                    	 render: function(data, type, row, meta) {
	                    		 var createtime = row.createtime;
	                    		 if(null==createtime || ""==createtime){
	                    			 return "";
	                    		 }
	                    		return createtime;
	                        } 	
	                    },
	                    {"data": "filesize", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var filesize = row.filesize;
	                    		if(null==filesize || ""==filesize){
	                    			return null;
	                    		}
	                    		return filesize+"k";
	                    	}	
	                    },
	            ],
	            "columnDefs": [
							   {"sWidth": "5%","aTargets": [0] },
							   {"sWidth": "50%","aTargets": [1] },
							   {"sWidth": "17%","aTargets": [2] },
							   {"sWidth": "9%","aTargets": [3] },
							   {"sWidth": "19%","aTargets": [4] },
	                           {
	                //   指定第一列，从0开始，0表示第一列，1表示第二列……
	                targets: 4,
	                render: function(data, type, row, meta) {
	                	var editFolder = '<a href="javascript:editFolder('+row.id+');" style="cursor:pointer;">编辑&nbsp;&nbsp;</a>';
	                	var download = '<a href="${base}/admin/drawback/grabfile/downLoadZipFile.html?parentId='+row.id+'" style="cursor:pointer;">下载&nbsp;&nbsp;</a>';
	                	var move  = '<a href="javascript:move('+row.id+');" style="cursor:pointer;">移动到&nbsp;&nbsp;</a>';
                   		if(1==row.status){
                   			var judge = '<a href="javascript:physicalDelete('+row.id+',2);" class="btn_mini btn_modify"><font color="#CCCCCC">删除</font></a>';
                   		}else{
                   			var judge = '<a href="javascript:physicalDelete('+row.id+',1);" class="btn_mini btn_modify">启用</a>';
                   		}
	                    return editFolder+download+move+judge;
	                }
	            }]
		};
	function initDatatable() {
		rebatesEamilTable = $('#rebatesEamilTable').DataTable(options);
	}
	//当点击进入下一级的时候重新加载表格
	var clickFlag = 1;
	function createFodler(pid,filename,filetype,clickFlag){
		if(clickFlag===1 && filetype===2){
			$.ajax({
				cache : false,
				type : "POST",
				url : '${base}/admin/drawback/grabfile/filePreview.html',
				data : {
					id : pid
				},
				success : function(data) {
					if(filetype == 2){
						layer.open({
					  	    type: 2,
					  	    title:false,
					  	    closeBtn:false,
					  	    fix: false,
					  	    maxmin: false,
					  	    shadeClose: false,
					  	    scrollbar: false,
					  	    area: ['1200px', '700px'],
					  	    content: '${base}/admin/drawback/grabreport/filePreview.html?id='+pid
					 	});
					} 
						
				},
				error : function(request) {
					layer.msg("操作失败", "", 3000);
				}
			});
		}else{
			options.ajax.data.parentId=pid;
			var param = {parentId:pid};
			rebatesEamilTable.settings()[0].ajax.data = param;
			rebatesEamilTable.ajax.reload();
			var exist=false;
			$("ol.breadcrumb").find("li").each(function(index){
				var currenuId = $(this).attr("id");
				if(currenuId == pid){
					exist = true ;
					return false;
				}
			});
			
			if(!exist){
				if(filetype == 1){
					$("ol.breadcrumb").find("li").each(function(){
						$(this).removeClass("active");
					});
					$("ol.breadcrumb").append('<li id=\''+pid+'\' class="active"><a class="fa fa-folder-open" onclick="javascript:createFodler(\''+pid+'\',\''+filename+'\');"  href="#">'+filename+'</a></li>');
				}
			}else{
				//找到指定元素的下标
				var selectIndex = 0;
				$("ol.breadcrumb").find("li").each(function(index){
					var currenuId = $(this).attr("id");
					if(currenuId == pid){
						selectIndex=index;
						return false;
					}
				});
				
				//删除大于该下标的其他元素
				$("ol.breadcrumb").find("li").each(function(index){
					if(index > selectIndex){
						$(this).remove(); 
					}
				});
				var length = $("ol.breadcrumb").find("li").length;
				$("ol.breadcrumb").find("li").each(function(index){
					if(index != (length-1)){
						$(this).removeClass("active");
					}
				});
			}
			//修改当前所在文件夹id
			$("input#currentDirId").val(pid);
		}
	}
	
	//新建子文件夹
	function newFolder(){
		var pid = $("input#currentDirId").val();
		//rebatesEamilTable.destroy();
		layer.open({
	  	    type: 2,
	  	    title:false,
	  	    closeBtn:false,
	  	    fix: false,
	  	    maxmin: false,
	  	    shadeClose: false,
	  	    scrollbar: false,
	  	    area: ['400px', '170px'],
	  	    content: '${base}/admin/drawback/grabfile/add.html?parentId='+pid,
	  	    end: function(){//添加完页面点击返回的时候自动加载表格数据
	  	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	  			parent.layer.close(index);
	  			var param = {parentId:pid};
				rebatesEamilTable.settings()[0].ajax.data = param;
				rebatesEamilTable.ajax.reload();
	  	    }
	 	});
	}
	
	//修改文件夹名称
	function editFolder(id){
		layer.open({
	  	    type: 2,
	  	    title:false,
	  	    closeBtn:false,
	  	    fix: false,
	  	    maxmin: false,
	  	    shadeClose: false,
	  	    scrollbar: false,
	  	    area: ['400px', '170px'],
	  	    content: '${base}/admin/drawback/grabfile/update.html?id='+id,
	  	    end: function(){//添加完页面点击返回的时候自动加载表格数据
	  	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	  			parent.layer.close(index);
	  	    }
	 	});
	}
</script>

<!------------------------------------------------------------ 报表分页显示 ---------------------------------------------------->
<script type="text/javascript">
	var rebatesReportTable;
	function initDatatable2() {
		rebatesReportTable = $('#rebatesReportTable').DataTable({
			"searching" : false,
			"processing" : true,
			"serverSide" : true,
			"bLengthChange" : false,
			"stripeClasses": [ 'strip1','strip2' ],//斑马线
			"bJQueryUI": true,
			"language" : {
				"url" : "${base}/public/plugins/datatables/cn.json"
			},
	        "ajax": {
	            "url": "${base}/admin/drawback/grabreport/listData.html",
	            "type": "post",
	            "data": function (d) {
	            	
	            }
	        },
	        "columns": [
	                    {"data": "pnr", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var pnr = row.pnr;
	                    		var pnr = '<span data-toggle="tooltip" data-placement="right" title="'+pnr+'">'+pnr+'<span>';
	                    		if(null==pnr || ""==pnr){
	                    			return "";
	                    		}
	                    		// var pnr = '<span data-toggle="tooltip" data-placement="right" title="'+row.pnr+'">'+row.pnr+'<span>';
	                    		return pnr;
	                    	}
	                    },
	                    {"data": "remit", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var remit = (row.remit).toFixed(2);
	                    		var remit = '<span data-toggle="tooltip" data-placement="right" title="'+remit+'">'+remit+'<span>';
	                    		if(null==remit || ""==remit){
	                    			return "";
	                    		}
	                    		return remit;
	                    	}
	                    },
	                    {"data": "depositbalance", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = (row.depositbalance).toFixed(2);
	                    		var depositBalance = '<span data-toggle="tooltip" data-placement="right" title="'+depositBalance+'">'+depositBalance+'<span>';
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance;
	                    	}	
	                    },
	                    {"data": "ticketprice", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var ticketPrice = (row.ticketprice).toFixed(2);
	                    		if(null==ticketPrice || ""==ticketPrice){
	                    			return "";
	                    		}
	                    		return ticketPrice;
	                    	}
	                    },
	                    {"data": "swipe", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var swipe = (row.swipe).toFixed(2);
	                    		if(null==swipe || ""==swipe){
	                    			return "";
	                    		}
	                    		return swipe;
	                    	}
	                    },
	                    {"data": "tax", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var tax = (row.tax).toFixed(2);
	                    		if(null==tax || ""==tax){
	                    			return "";
	                    		}
	                    		return tax;
	                    	}
	                    },
	                    {"data": "excisetax1", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var exciseTax1 = (row.excisetax1).toFixed(2);
	                    		if(null==exciseTax1 || ""==exciseTax1){
	                    			return "";
	                    		}
	                    		return exciseTax1;
	                    	}
	                    },
	                    {"data": "agencyfee", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var agencyFee = (row.agencyfee).toFixed(2);
	                    		var agencyFee = '<span data-toggle="tooltip" data-placement="right" title="'+agencyFee+'">'+agencyFee+'<span>';
	                    		if(null==agencyFee || ""==agencyFee){
	                    			return "";
	                    		}
	                    		return agencyFee;
	                    	}
	                    },
	                    {"data": "taxrebate", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var taxRebate = (row.taxrebate).toFixed(2);
	                    		var taxRebate = '<span data-toggle="tooltip" data-placement="right" title="'+taxRebate+'">'+taxRebate+'<span>';
	                    		if(null==taxRebate || ""==taxRebate){
	                    			return "";
	                    		}
	                    		return taxRebate;
	                    	}
	                    },
	                    {"data": "backstatus", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var backStatus = row.backstatus;
	                    		if(null==backStatus || ""==backStatus){
	                    			return "";
	                    		}else if(backStatus==0){
	                    			return "已退"
	                    		}
	                    		return "未退";
	                    	}
	                    },
	                    {"data": "realincome", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var realIncome = (row.realincome).toFixed(2);
	                    		var realIncome = '<span data-toggle="tooltip" data-placement="right" title="'+realIncome+'">'+realIncome+'<span>';
	                    		if(null==realIncome || ""==realIncome){
	                    			return "";
	                    		}
	                    		return realIncome;
	                    	}
	                    },
	                    {"data": "realtotal", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var realTotal = (row.realtotal).toFixed(2);
	                    		var realTotal = '<span data-toggle="tooltip" data-placement="right" title="'+realTotal+'">'+realTotal+'<span>';
	                    		if(null==realTotal || ""==realTotal){
	                    			return "";
	                    		}
	                    		return realTotal;
	                    	}
	                    },
	                    {"data": "agencyfee2", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var agencyFee2 = (row.agencyfee2).toFixed(2);
	                    		var agencyFee2 = '<span data-toggle="tooltip" data-placement="right" title="'+agencyFee2+'">'+agencyFee2+'<span>';
	                    		if(null==agencyFee2 || ""==agencyFee2){
	                    			return "";
	                    		}
	                    		return agencyFee2;
	                    	}
	                    },
	                    {"data": "inaustraliantime", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var inAustralianTime = row.inaustraliantime;
	                    		if(null==inAustralianTime || ""==inAustralianTime){
	                    			return "";
	                    		}
	                    		var inAustralianTime = '<span data-toggle="tooltip" data-placement="left" title="'+inAustralianTime+'">'+inAustralianTime+'<span>';
	                    		return inAustralianTime;
	                    	}
	                    },
	                    {"data": "outaustraliantime", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var outAustralianTime = row.outaustraliantime;
	                    		if(null==outAustralianTime || ""==outAustralianTime){
	                    			return "";
	                    		}
	                    		var outAustralianTime = '<span data-toggle="tooltip" data-placement="left" title="'+outAustralianTime+'">'+outAustralianTime+'<span>';
	                    		return outAustralianTime;
	                    	}
	                    }
	            ],
	            "columnDefs": [
							   {"sWidth": "7%","aTargets": [10] },
							   {
					                //   指定第一列，从0开始，0表示第一列，1表示第二列……
					                targets: 14,
					                render: function(data, type, row, meta) {
					                	/* var details = '<a style="cursor:pointer;" onclick="editPreview('+row.id+');">编辑</a>'; */
					                    return details;
					                }
				               }]
		});
	}
	//报表详情
	function editPreview(id){
		layer.open({
	  	    type: 2,
	  	    title: false,
	  	  	closeBtn:false,
	  	    fix: false,
	  	    maxmin: false,
	  	    shadeClose: false,
	  	    scrollbar: false,
	  	  	area: ['1000px', '600px'],
	  	    content: '${base}/admin/drawback/grabreport/update.html?id='+id
	  	  });
	}
	
	$(function() {
		initDatatable2();
	});
</script>
</body>
</html>
