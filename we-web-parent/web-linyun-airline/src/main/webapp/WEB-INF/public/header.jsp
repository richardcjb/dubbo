<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>航空票务系统</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
 <%--  <link rel="stylesheet" href="${base }/public/dist/css/font-awesome.min.css"> --%>
  <link rel="stylesheet" href="${base }/public/font-awesome/css/font-awesome.min.css">
  <!-- style -->
  <link rel="stylesheet" href="${base }/public/css/style.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="${base }/public/dist/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${base}/public/plugins/datatables/dataTables.bootstrap.css">
  <link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
  <link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
  <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
        page. However, you can choose any other skin. Make sure you
        apply the skin class to the body tag so the changes take effect.
  -->
  <link rel="stylesheet" href="${base}/public/dist/css/skins/skin-blue.min.css">
  <link rel="stylesheet" href="${base}/public/dist/css/skins/_all-skins.min.css">
  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <!-- <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script> -->
  <script src="${base}/public/dist/js/html5shiv/html5shiv.js"></script>
  <script src="${base}/public/dist/js/respond/respond.min.js"></script>
  
  <![endif]-->
  <link rel="stylesheet" href="${base}/public/css/pikaday.css">
	
  <!-- jQuery 2.2.3 -->
  <script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>

</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <!-- Main Header -->
  <header class="main-header">

    <!-- Logo -->
    <a href="${base}/" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini">航空</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg">航空票务系统</span>
    </a>

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">

      <!-- Navbar Right Menu -->
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <!-- Messages: style can be found in dropdown.less-->
           <li class="dropdown messages-menu">
            <!-- Menu toggle button -->
              <!-- <a href="#" class="dropdown-toggle" data-toggle="dropdown">小工具</a> -->
            <ul class="dropdown-menu">
              <li class="header">You have 4 messages</li>
              <li>
                <!-- inner menu: contains the messages -->
                <ul class="menu">
                  <li><!-- start message -->
                    <a href="#">
                      <div class="pull-left">
                        <!-- User Image -->
                      </div>
                      <!-- Message title and timestamp -->
                      <h4>
                        Support Team
                        <small><i class="fa fa-clock-o"></i> 5 mins</small>
                      </h4>
                      <!-- The message -->
                      <p>Why not buy a new awesome theme?</p>
                    </a>
                  </li>
                  <!-- end message -->
                </ul>
                <!-- /.menu -->
              </li>
              <li class="footer"><a href="#">See All Messages</a></li>
            </ul>
          </li>
          <!-- /.messages-menu -->

          <!-- Notifications Menu -->
          <li class="dropdown notifications-menu">
            <!-- Menu toggle button -->
            <!-- <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <i class="fa fa-bell-o"></i>
              	<span class="label label-warning">10</span>
            </a> -->
            <ul class="dropdown-menu">
              <!-- <li class="header">You have 10 notifications</li> -->
              <li>
                <!-- Inner Menu: contains the notifications -->
                <ul class="menu">
                  <li><!-- start notification -->
                    <a href="#">
                      <i class="fa fa-users text-aqua"></i> 5 new members joined today
                    </a>
                  </li>
                  <!-- end notification -->
                </ul>
              </li>
              <li class="footer"><a href="#">View all</a></li>
            </ul>
          </li>
          <!-- User Account Menu -->
          <li class="dropdown user user-menu">
             <a href="#" class="dropdown-toggle" style= "cursor:default">
              <!-- 登录的当前用户-->
               <span class="hidden-xs">${loginuser.fullName}</span>
            </a>
          </li>
          <li>
             <a href="${base}/admin/logout.html">退出</a>
          </li>
        </ul>
      </div>
    </nav>
  </header>

