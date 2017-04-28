<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>航空公司政策管理-编辑</title>
    <link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
	<link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="${base }/public/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
	<link href="${base }/public/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
  <link rel="stylesheet" href="${base }/public/dist/css/policyManage.css"><!--本页面Style-->
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">取消</button>
            <input type="submit" id="submit" class="btn btn-primary right btn-sm" value="保存" onclick="addFileInfo('${obj.airlinePolicy.id}');"/>
            <h4 class="invoiceH4">编辑</h4>
    </div>
      <div class="modal-body" style="height:200px;overflow-y: auto;">
      <form id="updateFileInfoForm">
      		<div class="row">
      			<div class="form-group">
      				<label class="col-sm-3 text-right padding">航空公司：</label>
      				<div class="col-sm-4 padding">
      					<select id="findAirlineCompany" name="findAirlineCompany"  onchange="setSelectedAirlineCompanyId()" class="form-control select2 inpImpWid" multiple="multiple" >
			              	<c:if test="${obj.dictInfo.id !=null}">
			              	
								<option value="${obj.dictInfo.id }" selected="selected">${obj.dictInfo.dictCode }</option>
			              	</c:if>
			            </select>
			            <input id="airlineCompanyId" type="hidden" class="form-control input-sm" placeholder="请输入要添加的航空公司名称" name="airlineCompanyId" value="${obj.airlinePolicy.airlineCompanyId }">
			            <span  class="red">*</span>
					</div>
      			</div>
      			<!-- 设置已选中的项 -->
				<script type="text/javascript">
					function setSelectedAirlineCompanyId() {
						var _selectedAreaIds = $("#findAirlineCompany").select2("val");
						$("#airlineCompanyId").val(_selectedAreaIds);
					}
				</script>
      			<div class="form-group iconStyle">
      				<label class="col-sm-1 text-right padding">类型：</label>
      				<div class="col-sm-3 padding">
      				  <select class="form-control wi input-sm" name="type">
		              	<option value="">请选择</option>
		                <c:if test="${obj.airlinePolicy.type == '团'}">
			                <option selected="selected">团</option>
			                <option >散</option>
		                </c:if>
		                <c:if test="${obj.airlinePolicy.type == '散'}">
			                <option >团</option>
			                <option selected="selected">散</option>
		                </c:if>
			         </select>
			            <span class="red">*</span>
					</div>
      			</div>
      		</div>
      		<div class="row">
      			<div class="form-group">
      				<label class="col-sm-3 text-right padding">地区：</label>
      				<div class="col-sm-4 padding">
      					<select id="findArea" name="findArea"  onchange="setSelectedfindArea()" class="form-control select2 inpImpWid" multiple="multiple" >
			              	<c:if test="${obj.airlinePolicy.areaName != null}">
			              	
				              	<option value="${obj.airlinePolicy.areaName }" selected="selected">${obj.airlinePolicy.areaName }</option>
			              	</c:if>
			           </select>
			           	<input type="hidden" class="form-control input-sm" placeholder="请输入要添加地区名称" name="areaName" id="areaId" value="${obj.airlinePolicy.areaName }">
			           <span  class="red">*</span>
					</div>
      			</div>
      			<!-- 设置已选中的项 -->
				<script type="text/javascript">
					function setSelectedfindArea() {
						var _selectedAreaIds = $("#findArea").select2("val");
						$("#areaId").val(_selectedAreaIds);
					}
				</script>
      			<div class="form-group iconStyle">
      				<label class="col-sm-1 text-right padding">文件名：</label>
      				<div class="col-sm-3 padding">
      				 	<input type="text" class="form-control input-sm filetext" placeholder="请输入文件名称" name="fileRealName">
					</div>
      			</div>
      		</div>
      		<div class="row">
      			<div class="form-group">
      				<label class="col-sm-3 text-right padding"></label>
      				<div class="col-sm-8 padding">
      					<!-- <button id="file" name="file" type="file" class="btn btn-primary btn-sm" >上传</button> -->
              			<input type="file" name="file" id="file" class="btn btn-primary btn-sm" /> 
              			<span id="completeFileName"></span>
      				</div>
      				
      			</div>
      		</div>
        <input type="hidden" name="url" value="" id="url"/>
        <input type="hidden" name="fileName" value="" id="fileName"/>
        </form>
      </div>
	</div>
   <!--JS 文件-->
	<script src="${base }/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base }/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base }/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base }/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base }/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<script src="${base}/common/js/layer/layer.js"></script>
	<script type="text/javascript" src="${base }/public/plugins/uploadify/jquery.uploadify.min.js"></script>
	<!-- Select2 -->
	<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
		<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
		<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script type="text/javascript">
	 //验证输入内容不能为空
	  $(document).ready(function (){
		
		
		formValidator();  
		
	});
	/* $(formValidator()); */
	//表单验证
	function formValidator(){
		$('#updateFileInfoForm').bootstrapValidator({
			message: '验证不通过!',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	           type: {
	            	validators: {
	                    notEmpty: {
	                        message: '类型不能为空!'
	                    }
	                }
	            },
	            findAirlineCompany: {
	            	validators: {
	                    notEmpty: {
	                        message: '航空公司不能为空!'
	                    }
	                }
	            },
	            findArea: {
	            	validators: {
	                    notEmpty: {
	                        message: '地区不能为空!'
	                    }
	                }
	            }
	         } 
		});
	}
	$(formValidator());
	
	
		//点击取消
		function closewindow(){
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
		/* $(function init(){
			var airlineCompanyId=$("#airlineCompanyId").val();
			var areaId=$("#areaId").val();
			alert(airlineCompanyId);
			alert(airlineCompanyId);
			$("#findAirlineCompany").val("中国云南航空");
			$("#findArea").val(areaId);
		}); */
		 $(uploadFile()); 
		//文件上传
		 function uploadFile(){
			 var index=null;
			$.fileupload1 = $('#file').uploadify({
				'auto' : true,//选择文件后自动上传
				'formData' : {
					'fcharset' : 'uft-8',
					'action' : 'uploadimage'
				},
				'buttonText' : '上传',//按钮显示的文字
				'fileSizeLimit' : '3000MB',
				'fileTypeDesc' : '文件',//在浏览窗口底部的文件类型下拉菜单中显示的文本
				'fileTypeExts' : '*.doc; *.xls; *.xlsx;*.docx;',//上传文件的类型
				'swf' : '${base}/public/plugins/uploadify/uploadify.swf',//指定swf文件
				'multi' : false,//multi设置为true将允许多文件上传
				'successTimeout' : 1800,
				'queueSizeLimit' : 100,
				'uploader' : '${base}/admin/airlinepolicy/uploadFile.html;jsessionid=${pageContext.session.id}',//后台处理的页面
				//onUploadSuccess为上传完视频之后回调的方法，视频json数据data返回，
				//下面的例子演示如何获取到vid
				'onUploadStart':function(file){
					 index = layer.load(1, {shade: [0.1,'#fff']});//0.1透明度的白色背景 
				 },
				 'onUploadSuccess' : function(file, data, response) {
					 
					var jsonobj = eval('(' + data + ')');
					var url  = jsonobj;//地址
					var fileName = file.name;//文件名称
					$("#url").val(url);
					$("#fileName").val(fileName);
					if(index!=null){
						
						layer.close(index);
					}
					/* 解决办法，上传成功后，将文件名字和路径添加到form表单的隐藏域中，点击保存的时候将其一起提交到后台进行保存，
					保存的时候判断文件名字是否存在从而判断需不需要再次进行预览格式的转换*/
					//var id = $("input#currentDirId").val();//文件pid
					/*  $.ajax({
						cache : false,
						type : "POST",
						data : {
							url : url,
							fileName : fileName
						
						},
						dataType : 'json',
						url : '${base}/admin/airlinepolicy/saveUploadFile.html',
						error : function(request) {
							layer.msg('上传失败!');
						},
						success : function(data) {
						     window.parent.successCallback('6');
							var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						    parent.layer.close(index); 
						}
					});  */
					fileName = encodeURI(fileName);
					fileName = encodeURI(fileName);  //需要通过两次编码
					var innerHtml = "";
                    if (response) {
                        innerHtml = "<div><a id='downloadA' href='${base}/admin/airlinepolicy/download.html?url="+url+"&fileName="+fileName+"'>"
                                + file.name
                                + "</a>&nbsp;&nbsp;<span>上传成功</span>&nbsp;&nbsp;&nbsp;&nbsp;"
                                + "<input type='button' class='delete' onclick='deleteFile();' value='删除'>";
                    } else {
                        innerHtml = "<div>该附件上传失败，请重新上传</div>";
                    }
                    $("#completeFileName").html("");
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
		
		//执行添加的操作、
		
		function addFileInfo(id){
			$('#updateFileInfoForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#updateFileInfoForm").data('bootstrapValidator');
			if(bootstrapValidator.isValid()){
				var index = layer.load(1, {shade: [0.1,'#fff']});//0.1透明度的白色背景 
				$.ajax({
					cache : false,
					type : "POST",
					url : '${base}/admin/airlinepolicy/update.html?id='+id,
					data : $('#updateFileInfoForm').serialize(),// 你的formid
					error : function(request) {
						layer.msg('编辑失败!');
					},
					success : function(data) {
						layer.close(index);
						
						 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					    parent.layer.close(index);
					    window.parent.successCallback('2'); 
						
					    
					}
				});
			}
		}
		
		//select2(航空公司)
		$("#findAirlineCompany").select2({
			ajax : {
				url : BASE_PATH + "/admin/airlinepolicy/selectAirlineCompanys.html",
				dataType : 'json',
				delay : 250,
				type : 'post',
				data : function(params) {
					return {
						p : params.term, // search term
						companyName:$("#airlineCompanyId").val(),
						page : params.page
					};
				},
				processResults : function(data, params) {
					params.page = params.page || 1;

					return {
						results : data
					};
				},
				cache : false
			},
			escapeMarkup : function(markup) {
				return markup;
			}, // let our custom formatter work
			minimumInputLength : 1,
			maximumInputLength : 20,
			language : "zh-CN", //设置 提示语言
			maximumSelectionLength : 1, //设置最多可以选择多少项
			tags : false, //设置必须存在的选项 才能选中
		});
		//select2(地区)
		$("#findArea").select2({
			ajax : {
				url : BASE_PATH + "/admin/airlinepolicy/selectArea.html",
				dataType : 'json',
				delay : 250,
				type : 'post',
				data : function(params) {
					return {
						p : params.term, // search term
						companyName:$("#areaId").val(),
						page : params.page
					};
				},
				processResults : function(data, params) {
					params.page = params.page || 1;

					return {
						results : data
					};
				},
				cache : false
			},
			escapeMarkup : function(markup) {
				return markup;
			}, // let our custom formatter work
			minimumInputLength : 1,
			maximumInputLength : 20,
			language : "zh-CN", //设置 提示语言
			maximumSelectionLength : 1, //设置最多可以选择多少项
			tags : true, //设置必须存在的选项 才能选中
		});
		
		function deleteFile(){
			$("#url").val("");
			$("#fileName").val("");
			$("#completeFileName").html("");
		}
	</script>
</body>
</html>	