<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- REQUIRED JS SCRIPTS -->
<!-- Bootstrap 3.3.6 -->
<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
<!-- DataTables -->
<script src="${base}/public/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${base}/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="${base}/public/dist/js/app.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${base}/public/dist/js/demo.js"></script>
<!--layer -->
<script src="${base}/common/js/layer/layer.js"></script>
<!-- My97DatePicker --> 
<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
 <!-- Main Footer -->
  <footer class="main-footer">
    <!-- To the right -->
    <div class="pull-right hidden-xs">
      <!-- Anything you want -->
    </div>
    <!-- Default to the left -->
    <strong>版权 &copy; 2016 <a>聚优国际旅行社（北京）有限公司</a>.</strong> 保留所有权利.
  </footer>
  <div class="control-sidebar-bg"></div>
  
  </div>
  </body>
<script type="text/javascript">
$(document).ready(function(){
	var _index = '${currentPageIndex}';//当前下标
	$(".sidebar-menu > li").click(function(){
		 $(".sidebar-menu > li").removeClass('active');
         $(this).addClass("active");//同时 添加记录样式
      });
	 //cookie记录已点击的index
    if(_index!=null && undefined != _index && _index != ""){
    	
    	$(".sidebar-menu > li").eq(_index).siblings().removeClass('active');
    	$(".sidebar-menu > li").eq(_index).addClass("active");//当前下标的元素添加样式
    }else{
    	$(".sidebar-menu > li").eq(0).siblings().removeClass('active');
    	$(".sidebar-menu > li").eq(0).addClass("active");//当前下标的元素添加样式
    }
	
});
</script>
</html>
