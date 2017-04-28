<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>游客信息</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
  <link rel="stylesheet" href="${base}/public/plugins/datatables/dataTables.bootstrap.css">
  <link rel="stylesheet" href="${base}/public/dist/css/internationOrderDetail.css"><!--本页面styleFlie-->
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow()">关闭</button>
            <!-- <input type="submit" id="submit" class="btn btn-primary right btn-sm" value="保存"/> -->
            <h4>游客信息</h4>
          </div>
          <div class="modal-body" style="height:481px;overflow-y:auto; ">
              <table id="logTable" class="table table-bordered table-hover">
                     <thead>
                      <tr>
                       <th>序号</th>
                       <th>姓名</th>
                       <th>性别</th>
                       <th>游客类型</th>
                       <th>证件类型</th>
                       <th>证件号码</th>
                       <th>操作</th>
                      </tr>
                     </thead>
                     <tbody>
                      <c:forEach items="${obj.visitors }" var="visitor" varStatus="status">
	                      <tr>
	                       <td>${status.index + 1 }</td>
	                       <td>${visitor.visitorname }</td>
	                       <td>${visitor.gender }</td>
	                       <td>${visitor.visitortype }</td>
	                       <td>${visitor.cardtype }</td>
	                       <td>${visitor.cardnum }</td>
	                       <td>
	                          <a href="javascript:editVisitor(${visitor.id });">编辑</a>
	                          <a href="javascript:backTicket(${visitor.id });" class="refund">退票</a>
	                       </td>
	                      </tr>
                      </c:forEach>
                     </tbody>
                   </table>
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
  $(function(){
  });
   //点击 退票 弹框
   function editVisitor(id){
       layer.open({
           type: 2,
           title:false,
           skin: false, //加上边框
           closeBtn:false,//默认 右上角关闭按钮 是否显示
           shadeClose:true,
           area: ['900px', '254px'],
           content: '${base}/admin/international/editVisitorInfo.html?visitorid='+id
         });
   }
   //退票
   function backTicket(id){
	   layer.open({
           type: 2,
           title:false,
           skin: false, //加上边框
           closeBtn:false,//默认 右上角关闭按钮 是否显示
           shadeClose:true,
           area: ['850px', '254px'],
           content: '${base}/admin/international/backTicket.html?visitorid='+id
         });
   }
//关闭窗口
  function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
  }
  
  function successCallback(id){
	  if(id == '1'){
		  layer.msg("添加成功",{time: 2000});
	  }else if(id == '2'){
		  layer.msg("修改成功",{time: 2000});
	  }
	}
  </script>
</body>
</html>	