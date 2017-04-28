<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<style type="text/css">
	.sidebar-mini .wrapper .wrapper footer:nth-child(even){display:none;} 
</style>
<!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar Menu -->
      <ul class="sidebar-menu">
        <c:forEach items="${menus}" var="menu" varStatus="stat">
	  	<c:if test="${menu.level == 1 }">
	  		<li id="${stat.index}">
	  			 <c:choose>
				   <c:when test="${menu.url != null && '' != menu.url}">
				   		<a href="${base}${menu.url}?currentPageIndex=${stat.index}">
				   				<i class="${menu.portrait }"></i>
				   			<span>${menu.name}</span>
				   		</a>
				   </c:when>
				   <c:otherwise>
				   		<a href="javascript:;" class="menu1">
				   			<i class="fa fa-th-large"></i>
				   			<span>${menu.name}</span>
				   			<span class="pull-right-container">
				              <i class="fa fa-angle-right pull-right" style="font-size:12px;margin: 2px 10px;"></i>
				            </span>
				   		</a>
				   		<%--子菜单 --%>
				   		<ul class="none menu-ul" style="padding-left: 22px;">
							<c:forEach var="function" items="${functionMap[menu.id]}">
								<li style="line-height: 30px;">
									<a href="${base}${function.url}?currentPageIndex=${stat.index}" style="margin-left: 15px;"><i class="fa fa-circle-o"></i> ${function.name}</a>
								</li>
							</c:forEach>
						</ul>
						
						
				   </c:otherwise>
			    </c:choose>
			    
				<!-- <ul class="treeview-menu">
				</ul> -->
			</li>
		  	</c:if>
	  	</c:forEach>
      </ul>
      <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
  </aside>
	<script>
	
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
		 
	  //二级菜单 显示隐藏
		if($('.menu1').parent().is('.active')){
			$(this).find('.menu-ul').show();
		}else{
			$(this).find('.menu-ul').hide();
		}
		
		$('.menu1').click(function(){//点击一级菜单 二级显示/隐藏
			$(this).next('ul').toggle();
		}); 
		
	});
	</script>