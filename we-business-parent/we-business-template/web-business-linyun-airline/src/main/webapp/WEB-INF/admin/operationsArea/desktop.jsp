<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>

<!DOCTYPE html>
<html lang="en-US">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>桌面</title>
<!-- Tell the browser to be responsive to screen width -->
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.css">
<!-- 图标 -->
<link rel="stylesheet"
	href="${base}/public/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="${base}/public/ionicons/css/ionicons.min.css">

<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet"
	href="${base}/public/dist/css/skins/_all-skins.min.css">
<!-- 大日历 -->
<link rel="stylesheet" type="text/css"
	href="${base}/public/plugins/fullcalendar/css/main.css">
<link rel="stylesheet" type="text/css"
	href="${base}/public/plugins/fullcalendar/css/fullcalendar.css">
<link rel="stylesheet" type="text/css"
	href="${base}/public/plugins/fullcalendar/css/fancybox.css">
<!--小日历 css-->
<link rel="stylesheet" href="${base}/public/build/kalendae.css"
	type="text/css" charset="utf-8">
<link rel="stylesheet" type="text/css"
	href="${base}/public/dist/css/desktop.css">
</head>
<body class="hold-transition skin-blue sidebar-mini bodyOne">
	<div class="wrapper">
		<!--right Content-->
		<div class="content-wrapper">
			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-md-9">
						<!--任务and大日历-->
						<a href="javascript:;" class="customInterface">自定义界面</a>
						<div class="nav-tabs-custom" id="taskId" style="display: none">
						 	<ul class="nav nav-tabs custome">
			                  <li id="searchLi" class="active"><a href="#tab_1" data-toggle="tab">询单(<span id="searchOrderNum"></span>)</a></li>
			                  <li id="bookLi"><a href="#tab_2" data-toggle="tab">订单(<span id="bookOrderMsgNum"></span>)</a></li>
			                  <li id="taskLi"><a id="taskAClick" href="#tab_5" data-toggle="tab">任务(<span id="taskNoticeMsgNum"></span>)</a></li>
			                  <li id="remindLi"><a id="myRemindClick" href="#tab_3" data-toggle="tab">我的提醒(<span id="remindMsgNum"></span>)</a></li>
			                  <li id="accountLI"><a href="#tab_4" data-toggle="tab">账期(<span id="accountPayTypeMsgNum"></span>)</a></li>
			                </ul>
			                <div class="tab-content">
				                  <div class="tab-pane active" id="tab_1"><!-- 询单 -->
				                    <ul id="queryOrders" class="taskInfo">
				                      
				                    </ul>
				                  </div>
				                  <div class="tab-pane" id="tab_2"><!-- 订单 -->
				                    <ul id="bookOrders" class="taskInfo">
				                      
				                    </ul>
				                  </div>
				                  <div class="tab-pane" id="tab_5"><!-- 通知 任务 -->
				                   	<ul id="taskNoticeList" class="taskInfo">
				                    </ul>
				                  </div>
				                  <div class="tab-pane" id="tab_3"><!-- 我的提醒 -->
				                    <ul id="taskListId" class="taskInfo">
				                    
				                    </ul>
				                  </div>
				                  <div class="tab-pane" id="tab_4"><!-- 账期 -->
				                    <ul id="accountPayType" class="taskInfo">
				                      
				                    </ul>
				                  </div>
				                  
				            </div>
						 </div>

						<div id="maxCId" style="display: none"
							class="box box-primary maxCalender">
							<!--大日历-->
							<div class="box-body no-padding">
								<!-- 大日历具体展示 -->
								<div id='calendar'></div>
							</div>
						</div>
						<!--end 大日历-->
					</div>
					<!--end 任务and大日历-->

					<div id="minCId" style="display: none" class="col-md-3 minCelender">
						<!--小日历-->
						<div class="box box-primary" id="box-min">
							<!-- <p>
								<input id="checkShow" type="checkbox" class="checkShow" checked="checked">
								显示提醒
							</p> -->
						</div>
					</div>
					<!--end 小日历-->
					<input id="minCalId" type="hidden" >
				</div>
			</section>
			<!-- /.content -->
		</div>
		<!--end right Content-->

		<!--footer-->
		<%@include file="/WEB-INF/public/footer.jsp"%>
		<!--end footer-->

		<!--自定义界面 弹框-->
		<div class="layer-content none" id="layer-diy">
			<form id="checkboxform" method="post">
				<div class="layer-header">
					<button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">
						取消
					</button>
					<button type="button" id="saveCustom" class="btn btn-primary right btn-sm" onclick="checkboxSave()">
						保存
					</button>
					<h4>自定义界面</h4>
				</div>
				<div class="modal-body">
					<div class="layer-check">
						<p>
							<input id="taskBoxId" name="checkboxname" type="checkbox" value="task" class="checkNum" onchange="checkBoxChange(this)" /> 
							<span>任务</span>
						</p>
						<p>
							<input id="maxCalenderId" name="checkboxname" type="checkbox" value="maxC" class="checkNum" onchange="checkBoxChange(this)"/> 
							<span>大日历</span>
						</p>
						<p>
							<input id="minCalenderId" name="checkboxname" type="checkbox" value="minC" class="checkNum" onchange="checkBoxChange(this)"/> 
							<span>小日历</span>
						</p>
					</div>
				</div>
			</form>
		</div>
		<!--end 自定义界面 弹框-->
	</div>

	<!-- jQuery 2.2.3 -->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<!-- Slimscroll -->
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- FastClick -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
	<!-- AdminLTE App -->
	<script src="${base}/public/dist/js/app.min.js"></script>
	<!--大日历 js-->
	<%-- <script src='${base}/public/plugins/fullcalendar/js/jquery-ui.css'></script> --%>
	<script src='${base}/public/plugins/fullcalendar/js/fullcalendar.min.js'></script>
	<!--小日历 js-->
	<script src="${base }/public/build/kalendae.standalone.js" type="text/javascript" charset="utf-8"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
	<script type="text/javascript">
	   function createMinCanlender(){
		  // $('#box-min').html('<p><input id="checkShow" type="checkbox" class="checkShow" checked="checked">显示提醒</p>');
		   new Kalendae({//小日历 创建
		        attachTo:document.getElementById("box-min"),
		        months:4,
		        mode:'single',
		        dayAttributeFormat:"YYYY-MM-DD",
		        subscribe: {
		             'date-clicked': function (date) {//点击时，取到当前当天的时间
		                //console.log(date, this.getSelected());
		                return false;
		              }
		        },
		        //dateClassMap:classMap,
		        selected:[Kalendae.moment().subtract({M:1}), Kalendae.moment().add({M:1})]
			});
	   	}
		createMinCanlender();
		//end 小日历 js
	</script>
	<!-- end 小日历js -->
	<script type="text/javascript">
		$(function() {
			/* 小日历背景*/
			minCalendarbackground();
			/* 大日历 */
			calendarInit();
			/* 任务栏事件 */
			taskBarFunctions();
			/*自定义界面选择*/
			customInterfaces();
			/*模块展示页面*/
			checkBoxShow();
			/*小日历*/
			minCalendarInit();
			/* 定时刷新任务栏 */
			setInterval("taskBarFunctions()",1000*59);
		});
	</script>

	<script type="text/javascript">
	  	function checkBoxShow(){
	  		var taskShow = ${obj.checkBox.taskShow};
	  		var maxCShow = ${obj.checkBox.maxCShow};
	  		var minCShow = ${obj.checkBox.minCShow};
	  		var taskTagShow = ${obj.funNums};
	  		if(taskShow){
	  			$("#taskId").css('display','block');
	  			$("#taskBoxId").attr('checked','checked');
	  			if(taskTagShow == true){
	  				//隐藏任务标签
	  				$("#taskLi").remove();
	  				$("#tab_5").remove();
	  			}else{
	  				//只显示 我的提醒 + 任务
	  				$("#searchLi").remove();
	  				$("#tab_1").remove();
	  				$("#bookLi").remove();
	  				$("#tab_2").remove();
	  				$("#accountLI").remove();
	  				$("#tab_4").remove();
	  				$("#tab_5").addClass("active");
	  				$("#taskLi").addClass("active");
	  			}
	  			ataskNoticeList();
	  		}
	  		if(maxCShow){
	  			$("#maxCId").css('display','block');
	  			$("#maxCalenderId").attr('checked','checked');
	  			$('#calendar').empty();
	  			calendarInit();
	  		}
	  		if(minCShow){
	  			$("#minCId").css('display','block');
	  			$("#minCalenderId").attr('checked','checked');
	  			minCalendarInit();
	  		}
	  	}
	  	
	  	//移除任务 active样式
	  	function removeClass(){
	  		if($("#taskLi").attr("class")=="active"){
	  			$("#tab_5").addClass("active");
  				$("#taskLi").addClass("active");
  				$("#tab_3").removeClass("active");
  				$("#remindLi").removeClass("active");
	  		}else{
	  			$("#tab_5").removeClass("active");
				$("#taskLi").removeClass("active");
				if($("#remindLi").attr("class")=="active"){
					$("#tab_3").addClass("active");
	  				$("#remindLi").addClass("active");
				}
				/* $("#tab_3").addClass("active");
  				$("#remindLi").addClass("active"); */
	  		}
	  		
	  	}
	  </script>

	<!-- 任务栏事件提醒 -->
	<script type="text/javascript">
		
		//任务栏事件
		function taskBarFunctions(){
			searchOrderMsgs();
			bookOrderMsgs();
			taskEventList();
			accountPayTypeList();
			ataskNoticeList();
		}
	
		//询单
		function searchOrderMsgs(){
			var urlStr = '${base}/admin/operationsArea/getOrderMsgs.html';
			remindMsgList("queryOrders", $("#searchOrderNum"), $("#queryOrders"),urlStr);
		}
		//订单
		function bookOrderMsgs(){
			var urlStr = '${base}/admin/operationsArea/getOrderMsgs.html';
			remindMsgList("bookOrders", $("#bookOrderMsgNum"), $("#bookOrders"),urlStr);
		}
		//我的提醒
		function taskEventList() {
			var urlStr = '${base}/admin/operationsArea/getTaskEvents.html';
			remindMsgList("", $("#remindMsgNum"), $("#taskListId"),urlStr);
		}
		//账期
		function accountPayTypeList() {
			var urlStr = '${base}/admin/operationsArea/getPayTypeTerm.html';
			remindMsgList("", $("#accountPayTypeMsgNum"), $("#accountPayType"),urlStr);
		}
		//任务
		function ataskNoticeList() {
			var urlStr = '${base}/admin/operationsArea/getOrderMsgs.html';
			remindMsgList("taskNotice", $("#taskNoticeMsgNum"), $("#taskNoticeList"),urlStr);
		} 
		
		//typeStr: queryOrders(询单)  bookOrders(订单) taskNoticeId(任务)
		//msgNumObj:$("#accountPayTypeMsg")
		//msgContentObj: $("#accountPayType")
		function remindMsgList(typeStr, msgNumObj, msgContentObj, urlStr) {
			//获取当前日期
			var d = new Date();
			var month = d.getMonth();
			var day = d.getDate();
			if(month < 10){
				if(day<10){
					var dateStr = month+1 +"-0"+ day;
					var yesterdayStr = month+1 +"-0"+ (day-1);
				}else{
					var dateStr = month+1 +"-"+ day;
					var yesterdayStr = month+1 +"-"+ (day-1);
				}
				dateStr = "0" + dateStr;
				yesterdayStr = "0" + yesterdayStr;
			}else{
				var dateStr = month+1 +"-"+ day;
				var yesterdayStr = month+1 +"-"+ (day-1);
			} 
			//获取当前时间
			var timeStr = d.getHours() +":"+ d.getMinutes();
			$.ajax({
				type : 'POST',
				dataType : 'json',
				url : urlStr,
				data: {
					"data": typeStr
				},
				success : function(data){
					var content = "";
					var num = "";
					$.each(eval(data),function(index, element){
	                	var datetimeStr = element.generatetime;
	                	var dStr = datetimeStr.substr(5, 5);
	                	var tStr = datetimeStr.substr(11, 5);
	                	if(dStr == dateStr){
	                		dStr="今天";
	                	}
	                	if(dStr == yesterdayStr){
	                		dStr="昨天";
	                	}
	                	num = element.num;
	                	var cName = element.comname;
	                	var agent = element.username;
	                	var msgC = element.msgcontent;
	                	var msgT = element.msgtype;
	                	//表示国际1或内陆2
	                	var orderT = element.ordertype; 
	                	var orderId = element.uporderid;
	                	var userMsgId = element.umid;
	                	var orderType = msgT;
	                	if(msgT == 3){
	                		msgT = "";
	                		/* msgT = "自定义事件："; */
	                		cName = "";
	                		agent = "";
	                		content += '<li>&nbsp;&nbsp;&nbsp;&nbsp;<span>'+dStr +'&nbsp;&nbsp;&nbsp;&nbsp;</span><span>'+tStr+'</span>'+cName+'&nbsp;&nbsp;'+agent+'&nbsp;&nbsp;'+ msgT +''+msgC+'</li>'; 
	                	}else if(msgT==2){
	                		msgT = "系统提醒：";
	                		content += '<li><a href="javascript:;" onclick="openOrderById('+orderId+','+orderType+','+userMsgId+','+orderT+');"><span>'+dStr+'</span><span>'+tStr+'</span>'+cName+'&nbsp;&nbsp;'+agent+'&nbsp;&nbsp;'+ msgT +''+msgC+'</a></li>'; 
	                	}else{
	                		msgT = "";
	                		content += '<li><a href="javascript:;" onclick="openOrderById('+orderId+','+orderType+','+userMsgId+','+orderT+');"><span>'+dStr+'</span><span>'+tStr+'</span>'+cName+'&nbsp;&nbsp;'+agent+'&nbsp;&nbsp;'+ msgT +''+msgC+'</a></li>'; 
	                	}
	                	/* content += '<li><a href="#"><span>'+dStr+'</span><span>'+tStr+'</span>'+''+msgC+'</a></li>'; */
	                	
		            });
					if(num){
						msgNumObj.html(num);
					}else{
						msgNumObj.html(0);
					}
		            
					msgContentObj.html(content);
				}
			});
		}
		
		//打开新页面
		function openOrderById(orderId, orderType, userMsgId, orderT){
			var url = "";
			if(orderType==4){
				//查询详情跳转
				if(orderT==2){
					url = '${base}/admin/inland/queryDetail.html?id='+orderId; //内陆
				}else{
					url = '${base}/admin/international/internationalDetail.html?orderid='+orderId; //国际
				}
				
			}
			if(orderType==5 || orderType==8 || orderType==9 || orderType==10 || orderType==11 || orderType==12 || orderType==14 || orderType==15 || orderType==16 || orderType==17 || orderType==18 || orderType==19 || orderType==20 || orderType==21 || orderType==22 || orderType==23){
				//预订订单详情跳转
				if(orderT==2){
					url = '${base}/admin/inland/bookingDetail.html?id='+orderId; //内陆
				}else{
					url = '${base}/admin/international/internationalDetail.html?orderid='+orderId; //国际
				}
				
			}
			window.open(url);
			
			//后台更新上次读取时间， 更改消息为已读状态
			$.ajax({
	            url: '/admin/operationsArea/updateMsgStatus.html',
	            dataType: 'json',
	            data: {
	            	userMsgId: userMsgId
	            },
	            success: function(data) {
	            	taskBarFunctions();
	            	layer.msg("消息已查看", "", 2000);
	            }
	        });
		}
		
	</script>

	<!-- 大日历 -->
	<script type="text/javascript">
	function calendarInit(){
		  $('#calendar').fullCalendar({
			    header: {
			      left: 'prevYear,prev,today',
			      center: 'title',
			      right: 'next,nextYear'
			    },
			    events: function(start, end,callback) {
			    	
			    	var fStart = $.fullCalendar.formatDate(start,"yyyy-MM-dd hh:mm:ss"); 
			    	var fEnd = $.fullCalendar.formatDate(end,"yyyy-MM-dd hh:mm:ss"); 
			    	
			        $.ajax({
			            url: '/admin/operationsArea/getCustomEvents.html',
			            dataType: 'json',
			            data: {
			                start: fStart,
			                end: fEnd
			            },
			            success: function(data) {
			                var events = [];
			                $.each(eval(data),function(index, element){
			                	events.push(element) ;
			                });
			                callback(events);
			            }
			        });
			    },
			    //點擊事件
			    dayClick: function(date, allDay, jsEvent, view) {
			     	  /* 自定义事件 弹框日期 */
			      	  var selDate =$.fullCalendar.formatDate(date,'yyyy-MM-dd');
			      	  
			          layer.open({
			              type: 2,
			              title:false,
			              shadeClose:false,
			              shade:0.6,
			              maxmin: false, 
			              area: ['400px', '210px'],
			              closeBtn: false,
			              scrollbar: false,
			              content: '${base}/admin/operationsArea/customEvent.html?selDate='+selDate,
			              end: function () {
			            	  /* $.fancybox.close();  */
			                  $('#calendar').fullCalendar('refetchEvents');
			              }
			          }); 
			      },
			      eventClick: function(calEvent, jsEvent, view) {
			         /* 自定义事件 弹框日期 */
			      	  var msgId = calEvent.id;
			          layer.open({
			              type: 2,
			              title:false,
			              shadeClose:false,
			              shade:0.6,
			              maxmin: false, 
			              area: ['400px', '275px'],
			              closeBtn: false,
			              content: '${base}/admin/operationsArea/updateCustomEvent.html?msgId='+ msgId
			          }); 
			          $('#calendar').fullCalendar('updateEvent', events);
			      },
			      eventMouseover:function( event, jsEvent, view ) {
			    	  $('.fc-event-title').css('display','block');
					  $('.fc-event-title').attr('title',event.title);
			      }
			  });
		  
	    }
	
		function reload(){
			$('#calendar').fullCalendar( 'refetchEvents' );
		}
	
	</script>

	<!-- 自定义界面 -->
	<script type="text/javascript">
		function customInterfaces(){
			 /*-----自定义界面 js-----*/
		    $('.customInterface').on('click',function(){
		    	$.ajax({
	                type: "POST",
	                url:'${base}/admin/operationsArea/getCheckBox.html',
	                success: function(data) {
	                	var taskShow = ${obj.checkBox.taskShow};
	        	  		var maxCShow = ${obj.checkBox.maxCShow};
	        	  		var minCShow = ${obj.checkBox.minCShow};
	        	  		if(taskShow){
	        	  			$("#taskBoxId").prop('checked',true);
	        	  		}else{
	        	  			$("#taskBoxId").prop('checked',false);
	        	  		}
	        	  		if(maxCShow){
	        	  			$("#maxCalenderId").prop('checked',true);
	        	  		}else{
	        	  			$("#maxCalenderId").prop('checked',false);
	        	  		}
	        	  		if(minCShow){
	        	  			$("#minCalenderId").prop('checked',true);
	        	  		}else{
	        	  			$("#minCalenderId").prop('checked',false);
	        	  		}
	                },
					error: function(request) {
	                    
	                }
	            });
		        layer.open({
		            type: 1, //Page层类型
		            area: ['350px', '125px'],
		            title: false,
		            shade: 0.6 ,//遮罩透明度
		            maxmin: false, //允许全屏最小化
		            anim: 0, //0-6的动画形式，-1不开启
		            skin: 'layui-layer-demo', //样式类名
		            closeBtn: 0, //不显示关闭按钮
		            shadeClose: true, //开启遮罩关闭
		            content: $('#layer-diy')
		         }); 
		     });
		}
		
		/* 复选框必须选一个 */
		function checkBoxChange(obj){
			if($(".checkNum:checked").size()==0){
				obj.checked = true;
   			}
		}
	
		/* 保存自定义界面 */
   		function checkboxSave(){
   			$.ajax({
                type: "POST",
                url:'${base}/admin/operationsArea/setCheckBox.html',
                data:$('#checkboxform').serialize(),
                async: false,
                success: function(data) {
                	window.parent.successCallback('1');
                },
				error: function(request) {
					window.parent.successCallback('2');
                }
            }); 
   		}
		
   		//事件提示
   		function successCallback(id){
   			window.location.reload("${base}/admin/operationsArea/desktop.html");
			if(id == '1'){
				layer.msg("设置成功", "", 2000);
			}else{
				layer.msg("设置失败", "", 2000);
			}
		}
		
		/* 关闭自定义界面 */
	    function closewindow(){
	    	layer.closeAll();
	    }
	</script>
	<!-- end 自定义界面 -->

	<!-- 小日历 -->
	<script type="text/javascript">
	
		/* 小日历背景*/
		function minCalendarbackground(){
			$('div[data-cal-index="2"]').prepend('<i class="month-i1"></i>');//小日历 top 显示月份
			$('div[data-cal-index="1"]').prepend('<i class="month-i2"></i>');//小日历 center 显示月份
			$('div[data-cal-index="0"]').prepend('<i class="month-i3"></i>');//小日历 buttom 显示月份
			$('.k-out-of-month').click(function(){
		      return false;
		    });
		}
		/* 小日历*/
		function minCalendarInit(){
			$('#box-min .kalendae').attr('id','minCalen');//给小日历添加ID
		    /*---------------------------------小日历 节假日------------------------------------*/
		    var CalendarData=new Array(20);
		    var madd=new Array(12);
		    var TheDate=new Date();
		    init();
		    showHoliday();//页面加载时，显示出阳历的节假日
		    function init(){
		        CalendarData[0]=0x41A95;
		        CalendarData[1]=0xD4A;
		        CalendarData[2]=0xDA5;
		        CalendarData[3]=0x20B55;
		        CalendarData[4]=0x56A;
		        CalendarData[5]=0x7155B;
		        CalendarData[6]=0x25D;
		        CalendarData[7]=0x92D;
		        CalendarData[8]=0x5192B;
		        CalendarData[9]=0xA95;
		        CalendarData[10]=0xB4A;
		        CalendarData[11]=0x416AA;
		        CalendarData[12]=0xAD5;
		        CalendarData[13]=0x90AB5;
		        CalendarData[14]=0x4BA;
		        CalendarData[15]=0xA5B;
		        CalendarData[16]=0x60A57;
		        CalendarData[17]=0x52B;
		        CalendarData[18]=0xA93;
		        CalendarData[19]=0x40E95;
		        madd[0]=0;
		        madd[1]=31;
		        madd[2]=59;
		        madd[3]=90;
		        madd[4]=120;
		        madd[5]=151;
		        madd[6]=181;
		        madd[7]=212;
		        madd[8]=243;
		        madd[9]=273;
		        madd[10]=304;
		        madd[11]=334;
		    }
		    function GetBit(m,n){
		        return (m>>n)&1;
		    }
		    function showHoliday(){
		      var sFtv = new Array(//阳历节日
		                      "01-01 元旦",
		                      "02-14 情人",
		                      "03-08 妇女",
		                      "03-12 植树",
		                      "04-01 愚人",
		                      "05-01 劳动",
		                      "06-01 儿童",
		                      "09-10 教师",
		                      "10-01 国庆",
		                      "12-25 圣诞");
		      var lFtv = new Array(//农历节日
		                      "01-01 春节",
		                      "01-15 元宵",
		                      "05-05 端午",
		                      "07-07 七夕",
		                      "07-15 中元",
		                      "08-15 中秋",
		                      "12-08 腊八",
		                      "12-24 小年");
		      //var solarTerm = new Array("立春","春分","清明","立夏","立秋","立冬","冬至");
		      for(var j=0;j<sFtv.length;j++){//获取阳历的节日--------------------------------------------------------------------
		          var JJRMonth=sFtv[j].substr(0,2);//获取阳历 节假日 月
		          var JJRDay=sFtv[j].substr(3,2);//获取阳历 节假日 日
		          var JJRName=sFtv[j].substr(5);//获取阳历 节假日 name
		          var SpanNumber=document.getElementById('minCalen').getElementsByTagName('span');//获取页面里放有多少天数
		          
		          for(var i=0;i<SpanNumber.length;i++){
		              var spanVal=SpanNumber[i].getAttribute("data-date");//获取span标签的data-date属性值
		              if (spanVal!=null) {//当期日不为null时
		                  //阳历 节假日-----------------------------------------------//
		                  SpanNumber[i].style.color="#000";
		                  var thisYear=spanVal.substr(0,4);//获取页面上的年 时间
		                  var thisMonth=spanVal.substr(5,2);//获取页面上的月 时间
		                  var thisDay=spanVal.substr(8,2);//获取页面上的日 时间
		                  var cYear;
		                  var cMonth;
		                  var cDay;
		                  if(JJRMonth==thisMonth){
		                      if(JJRDay==thisDay){
		                          SpanNumber[i].innerHTML=JJRName;
		                          SpanNumber[i].style.color="#e04174";
		                      }
		                  }
		                  //清明节
		                  var myYear=spanVal.substr(2,2);//获取当前年份后两位
		                  var QMJdata1=parseInt(myYear*0.2422+4.81);
		                  var QMJdata2=parseInt(myYear/4);
		                  var QMJday=QMJdata1-QMJdata2;
		                  if(thisMonth==04){
		                	  if(thisDay==QMJday){
		                		  SpanNumber[i].innerHTML="清明";
		                		  SpanNumber[i].style.color="#e04174";
		                	  }
		                  }//end 清明节
		                  //end 阳历 节假日-------------------------------------------//
		                  //阴历 节假日-----------------------------------------------//
		                  var   isEnd=false;
		                  thisMonth=thisMonth-1;
		                  if   (thisYear<1900){thisYear+=1900;}
		                  total=(thisYear-2001)*365
		                         +Math.floor((thisYear-2001)/4)//页面 年
		                         +madd[thisMonth]//页面 月
		                         +parseInt(thisDay)//页面 日
		                         -23;
		                  if(TheDate.getFullYear()%4==0&&thisMonth>1){total++;}
		                  for(m=0;;m++){
		                      k=(CalendarData[m]<0xfff)?11:12;
		                      for(n=k;n>=0;n--){
		                          if(total<=29+GetBit(CalendarData[m],n)){
		                              isEnd=true;
		                              break;
		                          }
		                          total=total-29-GetBit(CalendarData[m],n);
		                      }
		                      if(isEnd)break;
		                  }
		                  cYear=2001+m;//获取页面的 农历 年
		                  cMonth=k-n+1;//获取页面的 农历 月
		                  cDay=total;//获取页面的 农历 日
		                  if(k==12){
		                      if(cMonth==Math.floor(CalendarData[m]/0x10000)+1){
		                    	  cMonth=1-cMonth;
		                      } 
		                      if(cMonth>Math.floor(CalendarData[m]/0x10000)+1){
		                    	  cMonth--;
		                      }
		                  }
		                  for(var q=0;q<lFtv.length;q++){
		                      var NjjrMonth=lFtv[q].substr(0,2);//获取农历的节假日 月
		                      var NjjrDay=lFtv[q].substr(3,2);//获取农历的节假日 日
		                      var NjjrName=lFtv[q].substr(5);//获取农历的节假日 name
		                      if(cMonth==NjjrMonth){
		                          if(cDay==NjjrDay){
		                               SpanNumber[i].innerHTML=NjjrName;
		                               SpanNumber[i].style.color="#e04174";
		                          }
		                      }
		                  }
		                  //end 阴历 节假日-------------------------------------------//
		              }
		          } 
		      }
		    }
		    
		    $(document).on('click','.k-btn-next-month',function(){//点击小日历 向右切换月箭头时，加载节假日
		          showHoliday();
		          getTimeStr();
		          backgroundMonth();
		    });
		    $(document).on('click','.k-btn-previous-month',function(){//点击小日历 向左切换月箭头时，加载节假日
		          showHoliday();
		          getTimeStr();
		          backgroundMonth();
		    });
		    $(document).on('click','.k-btn-next-year',function(){//点击小日历 向右切换年箭头时，加载节假日
		          showHoliday();
		          getTimeStr();
		          backgroundMonth();
		    });
		    $(document).on('click','.k-btn-previous-year',function(){//点击小日历 向左切换年箭头时，加载节假日
		          showHoliday();
		          getTimeStr();
		          backgroundMonth();
		    });
		    /*---------------------------------end 小日历 节假日------------------------------------*/
		    //获取当前3个月事件
			getTimeStr();
			$('.checkShow').click(function(){//显示提醒 显示/隐藏
			    if($(this).prop('checked')){
			          $('.dot').css('display','block');
			    }else{
			          $('.dot').css('display','none');
			    }
			});//end 显示提醒 显示/隐藏
			
			/* 小日历 背景显示月份 */
			backgroundMonth();
		}
		
		/*删除多余的小日历*/		
		function removeMinCalendar(){
			/* $("div#minCalen").next().remove(); */
			$("div#minCalen").remove();
			$(".kalendae").attr("id","minCalen");
		}
	
		
		//-------------------小日历背景 添加 月份显示-----------------------
		function backgroundMonth(){
		    var monthVal1= $('div[data-cal-index="2"] .k-title .k-caption').text();//获取top月份
		    var DXmonth1=monthVal1.substring((monthVal1.length-1),(monthVal1.length-3));//倒序截取月份
		    $('.month-i1').text(DXmonth1);

		    var monthVal2= $('div[data-cal-index="1"] .k-title .k-caption').text();//获取center月份
		    var DXmonth2=monthVal2.substring((monthVal2.length-1),(monthVal2.length-3));//倒序截取月份
		    $('.month-i2').text(DXmonth2);

		    var monthVal3= $('div[data-cal-index="0"] .k-title .k-caption').text();//获取buttom月份
		    var DXmonth3=monthVal3.substring((monthVal3.length-1),(monthVal3.length-3));//倒序截取月份
		    $('.month-i3').text(DXmonth3);
		}
		//-------------------小日历背景 添加 月份显示-----------------------
	</script>
	<input id="redDivDate" name="redDivDate" type="hidden">
	<script type="text/javascript">
	    $('.checkShow').click(function(){//显示提醒 显示/隐藏
		      if($(this).prop('checked')){
		          $('.dot').css('display','block');
		          $("#checkShow").prop('checked',true);
		      }else{
		          $('.dot').css('display','none');
		          $("#checkShow").prop('checked',false);
		      }
	    });//end 显示提醒 显示/隐藏
	
	    /* 小日历加载小红点事件 */
		function getTimeStr(){
		  //setTimeout(getTimeStr,100);
		  /* 获取当前月  格式化为：2016-12的形式 */
		  var dateValue=document.getElementById('box-min').getElementsByClassName('k-caption');//获取 小日历 的/年/月
    	  var d = dateValue[1].innerHTML;
    	  var length = d.length;
    	  var y = d.substring(0,4);
    	  var m = "";
    	  var timeStr = "";
    	  if(length>=11){
    		  m = d.substring(8,10);
    		  timeStr = y +"-"+ m;
    	  }else{
    		  m = d.substring(8,9);
    		  timeStr = y +"-0"+ m;
    	  }
	      $.ajax({
	            url: '/admin/operationsArea/getMinCalList.html',
	            dataType: 'json',
	            type: 'POST',
	            data: {
	            	timeStr: timeStr
	            },
	            success: function(data) {
	            	$.each(eval(data), function (index, element) {
            			//显示小红点
            			if($('span[data-date="'+element.gtime+'"]').children().find('i')){
            				$('span[data-date="'+element.gtime+'"]').find('i').remove();
            			}
		            	$('span[data-date="'+element.gtime+'"]').append('<i class="dot"></i>');
		            	$('span[data-date="'+element.gtime+'"]').attr("name", "redDotSpanMsg");
	                }); 
	            }
	       });
		}
	    
	    
	    /* 小日历点击事件 */
	    $(document).click(function (e) { 
	    	var redDotSpan = $(e.target).attr('name'); 
			var redDotStr = redDotSpan.indexOf("redDotSpanMsg");
			var iLength = $(e.target).find("i").length;
			if(redDotStr==0 && iLength>0){
				var redDate = $(e.target).attr('data-date');
				$("#redDivDate").val(redDate);
    			/* if($("#checkShow").prop('checked')){
    				$.ajax({
    		            url: '/admin/operationsArea/getMinCal.html',
    		            dataType: 'json',
    		            type: 'POST',
    		            async:false,
    		            data: {
    		            	gtime: redDate
    		            },
    		            success: function(data) {
    		            	$.each(eval(data), function (index, element) {
    		            		$("#minCalId").val("");
    		            		$("#minCalId").val(element.msgcontent);
    		                }); 
    		            }
    		        });
    				//弹框提示信息
    				var msg = $("#minCalId").val();
        			layer.tips(
        				 msg, 
        				 $(e.target),
  			    		 {
  					        tips: [3, 'rgb(90, 90, 90)'],
  					        time: 1500
  					     }
      			    );
    			} */
				$.ajax({
		            url: '/admin/operationsArea/getMinCal.html',
		            dataType: 'json',
		            type: 'POST',
		            async:false,
		            data: {
		            	gtime: redDate
		            },
		            success: function(data) {
		            	$.each(eval(data), function (index, element) {
		            		$("#minCalId").val("");
		            		$("#minCalId").val(element.msgcontent);
		                }); 
		            }
		        });
				//弹框提示信息
				var msg = $("#minCalId").val();
    			layer.tips(
    				 msg, 
    				 $(e.target),
		    		 {
				        tips: [3, 'rgb(90, 90, 90)'],
				        time: 1500
				     }
  			    );
			}
	    });
	    
		//小日历上一个按钮
		$(".k-btn-previous-month").click(function(){
			getTimeStr();
			backgroundMonth();
		});
		//小日历下一个按钮
		$(".k-btn-next-month").click(function(){
			getTimeStr(); 
			backgroundMonth();
		});
	</script>

</body>
</html>
