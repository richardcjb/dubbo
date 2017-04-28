<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
  <link rel="stylesheet" href="${base}/public/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="${base}/public/ionicons/css/ionicons.min.css">
  <link rel="stylesheet" href="${base}/public/dist/css/applyApproval.css">
  <title>审批手机-分类</title>
</head>
<body>
  <header class="header">
	  <ul class="list-ul">
	  	<c:if test="${param.phone != 'phone'  }">
	  	
		 	<li><a href="${base}/admin/operationsArea/desktop.html"><i class="fa fa-angle-left"></i>返回</a></li> 
	  	</c:if>
		 <li><h3>分类</h3></li> 
		 <li></li> 
	 </ul>
  </header>
  <content class="content-body">
  
	<ul>
	  <li>
	  	<a href="${base}/admin/applyapproval/dataList.html?operation=international">
			&nbsp;&nbsp;国际
			<span><font>${obj.internationalNum }</font><i class="fa fa-angle-right"></i></span>
	  	</a>
	  </li>
	  <li>
		<a href="${base}/admin/applyapproval/dataList.html?operation=inlandNum">
			&nbsp;&nbsp;内陆
			<span><font>${obj.inlandNum }</font><i class="fa fa-angle-right"></i></span>
	  	</a>
	  </li>
	  <li>
		<a href="${base}/admin/applyapproval/dataList.html?operation=others">
			&nbsp;&nbsp;其他
			<span><font>0</font><i class="fa fa-angle-right"></i></span>
	  	</a>
	  </li>
	</ul>
  </content>
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
</body>
</html>