<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>编辑</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="${base}/public/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="${base}/public/ionicons/css/ionicons.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
</head>
<body>
	<div class="modal-top">
	<form id="editForm">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow()">取消</button>
            <input type="submit" id="submit" class="btn btn-primary right btn-sm" value="保存" onclick="saveData()"/>
            <input type="hidden" name="id" id="id" value="${obj.visitorinfo.id }">
            <h4 class="invoiceH4">编辑信息</h4>
    </div>
      <div class="modal-body" style="height:185px;overflow-y: auto;">
         <div class="form-group row"><!--姓名/证件类型-->
                  <label class="col-sm-2 text-right padding">姓名：</label>
                  <div class="col-sm-3 padding"><input id="visitorname" name="visitorname" type="text" class="form-control input-sm" value="${obj.visitorinfo.visitorname }"></div>
                  <label class="col-sm-2 text-right padding">证件类型：</label>
                  <div class="col-sm-3 padding">
                   <input type="text" id="cardtype" name="cardtype" class="form-control input-sm" value="${obj.visitorinfo.cardtype }">
                  </div>
         </div><!--end 姓名/证件类型-->
         <div class="form-group row"><!--性别/证件号码-->
                  <label class="col-sm-2 text-right padding">性别：</label>
                  <div class="col-sm-3 padding">
                   <input type="text" id="gender" name="gender" class="form-control input-sm" value="${obj.visitorinfo.gender }">
                  </div>
                  <label class="col-sm-2 text-right padding">证件号码：</label>
                  <div class="col-sm-3 padding"><input id="cardnum" name="cardnum" type="text" class="form-control input-sm" value="${obj.visitorinfo.cardnum }"></div>
         </div><!--end 性别/证件号码-->
         <div class="form-group row"><!--旅客类型-->
                  <label class="col-sm-2 text-right padding">旅客类型：<	/label>
                  <div class="col-sm-3 padding">
                    <input type="text" id="visitortype" name="visitortype" class="form-control input-sm" value="${obj.visitorinfo.visitortype }">
                  </div>
         </div><!--end 旅客类型-->
      </div>
      </form>
	</div>
   <!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
    <!--layer -->
  	<script src="${base}/common/js/layer/layer.js"></script>
  	<script type="text/javascript">
  //关闭窗口
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
  //保存记录
	function saveData(){
		layer.load(1);
		$.ajax({
			type: 'POST', 
			data: $("#editForm").serialize(), 
			url: '${base}/admin/international/saveEditVisitorInfo.html',
			async:false,
            success: function (data) {
            	layer.closeAll('loading');
            	closewindow();
            	window.parent.successCallback('2');
            },
            error: function (xhr) {
            	layer.msg("编辑失败",{time: 2000});
            } 
      });
	}
  	</script>
</body>
</html>