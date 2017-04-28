<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>日志</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
  <link rel="stylesheet" href="${base}/public/plugins/datatables/dataTables.bootstrap.css">
  <link rel="stylesheet" href="${base}/public/dist/css/internationOrderDetail.css"><!--本页面styleFlie-->
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm">取消</button>
            <h4>日志</h4>
          </div>
          <div class="modal-body" style="height:481px;overflow-y:auto; ">
              <table id="logTable" class="table table-bordered table-hover">
                     <thead>
                      <tr>
                       <th>编辑人</th>
                       <th>修改时间</th>
                       <th>修改内容</th>
                      </tr>
                     </thead>
                     <tbody>
                      <tr>
                       <td>万五</td>
                       <td>2017-03-09 15:36</td>
                       <td>该客户将北京到巴黎的原出发日期改签到2017-03-29日03：25（航班号为CA772号）</td>
                      </tr>
                     </tbody>
                   </table>
          </div>
	</div>
   <!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
  <!-- DataTables -->
  <script src="${base}/public/plugins/datatables/jquery.dataTables.min.js"></script>
  <script src="${base}/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
  <script type="text/javascript">
  $(function(){
      $('#logTable').dataTable({
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "columnDefs": [
            {"sWidth": "auto","aTargets": [0] },
            {"sWidth": "auto","aTargets": [1] },
            {"sWidth": "auto","aTargets": [2] }],
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
  });
  </script>
</body>
</html>