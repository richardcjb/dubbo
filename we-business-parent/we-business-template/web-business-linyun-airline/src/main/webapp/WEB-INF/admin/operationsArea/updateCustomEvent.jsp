<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=7" />
	<meta http-equiv="Access-Control-Allow-Origin" content="*" />
	<meta name="alexaVerifyID" content="" />
<title>更新</title>

<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet" href="${base }/public/dist/css/font-awesome.min.css">
<link rel="stylesheet" href="${base }/public/dist/css/ionicons.min.css">
<link rel="stylesheet" href="${base }/public/dist/css/skins/_all-skins.min.css">
<link rel="stylesheet" type="text/css" href="${base}/public/dist/css/desktop.css">
<link rel="stylesheet" href="${base}/public/dist/css/bootstrapValidator.css" />
</head>

<body class="bodyOne">
	<div class="EventBody">
		<form id="customEventForm">
		 	<div class="col-sm-3 padding">
				<input id="msgContent" name="msgContent" type="text" class="form-control input-sm eventText" value="${obj.message.msgContent }" placeholder="请输入新提示事件"/>
			</div>
			<div class="col-sm-3 padding">
				<div class="evevtTool cf">
					<label>提醒：</label> 
					<input id="datepicker" name="generateTimeString" type="text" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${obj.message.generateTime}" />" class="form-control input-sm" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${obj.message.generateTime }"  placeholder="2016-12-05 13:20"/>
				</div>
			</div>
			<input type="hidden" id="id" name="id" value="${obj.message.id}"/>
			<div class="evevtBttton">
				<button type="button" class="btn right" onclick="deleteEvent()">删除</button>
				<input id="submitBtn" type="button" class="btn btn-primary" onclick="update();" value="更新"/>
				<button type="button" class="btn btn-primary right" onclick="closewindow();">取消</button>
			</div>
		</form>
	</div>

	<!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>

	<script type="text/javascript">
		//表单校验
		$('#customEventForm').bootstrapValidator({
			message : '验证不通过',
			feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
			fields : {
				msgContent : {
					validators : {
						notEmpty : {
							message : '提醒事件不能为空'
						}
					}
				}
			}
		});

		// Validate the form manually
		$('#submitBtn').click(function() {
			$('#customEventForm').bootstrapValidator('validate');
		});
		
		//更新数据
		function update() {
			$('#customEventForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#customEventForm").data('bootstrapValidator');
			if (bootstrapValidator.isValid()) {
				$.ajax({
					type : 'POST',
					dataType:'json',
					url : '${base}/admin/operationsArea/updateCustom.html',
					data : $("#customEventForm").serialize(),
					success : function(data) {
						//日志
						console.log(data);
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						parent.layer.close(index);
						//添加成功 刷新各个模块
						window.parent.taskBarFunctions();
						window.parent.reload();
						window.parent.checkBoxShow();
						window.parent.createMinCanlender();
						window.parent.minCalendarbackground();
						window.parent.removeClass();
						window.parent.removeMinCalendar();
						window.parent.getTimeStr();
						window.parent.minCalendarInit();
						window.parent.backgroundMonth();
						window.parent.layer.msg("更新成功", "", 2000);
					},
					error : function(xhr) {
						window.parent.layer.msg("更新失败", "", 2000);
					}
				});
			}
		}

		//关闭窗口
		function closewindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
		
		//获得当前时间
		function FormatDate () {
		    var date = new Date();
		    return zeroize(date.getHours(),2)+":"+zeroize(date.getMinutes(),2)+":"+zeroize(date.getSeconds(),2);
		}
		function zeroize(value, length) {
		    if (!length) length = 2;
		    value = String(value);
		    for (var i = 0, zeros = ''; i < (length - value.length); i++) {
		        zeros += '0';
		    }
		    return zeros + value;
		}

		//删除自定义事件
		function deleteEvent(){
			layer.confirm("您确认删除信息吗？", {
			    btn: ["是","否"], //按钮
			    shade: false //不显示遮罩
			}, function(){
				// 点击确定之后
				$.ajax({
					type : 'POST',
					dataType:'json',
					url : '${base}/admin/operationsArea/deleteCustom.html',
					data :  $("#customEventForm").serialize(),
					success : function(data) {
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						parent.layer.close(index);
						//添加成功 刷新各个模块
						window.parent.taskEventList();
						window.parent.reload();
						window.parent.checkBoxShow();
						window.parent.createMinCanlender();
						window.parent.minCalendarbackground();
						window.parent.removeClass();
						window.parent.removeMinCalendar();
						window.parent.backgroundMonth();
						window.parent.minCalendarInit();
						window.parent.getTimeStr();
						window.parent.layer.msg("删除成功", "", 2000);
					},
					error : function() {
						window.parent.layer.msg("删除失败", "", 3000);
					}
				});
			}, function(){
			    // 取消之后不用处理
			});
		}
		
	</script>
</body>
</html>










