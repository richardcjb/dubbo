<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>退票</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="${base}/public/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="${base}/public/ionicons/css/ionicons.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow()">取消</button>
            <input type="submit" id="submit" class="btn btn-primary right btn-sm" value="保存"/>
            <h4 class="invoiceH4">退票</h4>
    </div>
      <div class="modal-body" style="height:185px;overflow-y: auto;">
         <div class="form-group row"><!--退票人/电话/申请日期-->
                  <label class="col-sm-2 text-right padding">退票人：</label>
                  <div class="col-sm-2 padding"><input type="text" class="form-control input-sm"></div>
                  <label class="col-sm-1 text-right padding">电话：</label>
                  <div class="col-sm-2 padding"><input type="text" class="form-control input-sm"></div>
                  <label class="col-sm-1 text-right padding">申请日期：</label>
                  <div class="col-sm-2 padding"><input type="text" class="form-control input-sm"></div>
         </div><!--end 退票人/电话/申请日期-->
         <div class="form-group row"><!--金额/原因/退票状态-->
                  <label class="col-sm-2 text-right padding">金额：</label>
                  <div class="col-sm-2 padding"><input type="text" class="form-control input-sm"></div>
                  <label class="col-sm-1 text-right padding">原因：</label>
                  <div class="col-sm-2 padding">
                    <select class="form-control input-sm">
                      <option>拒签</option>
                      <option>伤病</option>
                      <option>废票</option>
                    </select>
                  </div>
                  <label class="col-sm-1 text-right padding">退票状态：</label>
                  <div class="col-sm-2 padding">
                    <select class="form-control input-sm">
                      <option>退款中</option>
                      <option>完成退款</option>
                    </select>
                  </div>
         </div><!--end 金额/原因/退票状态-->
         <div class="form-group row"><!--备注-->
                  <label class="col-sm-2 text-right padding">备注：</label>
                  <div class="col-sm-8 padding"><input type="text" class="form-control input-sm"></div>
         </div><!--end 备注-->
      </div>
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
	</script>
</body>
</html>