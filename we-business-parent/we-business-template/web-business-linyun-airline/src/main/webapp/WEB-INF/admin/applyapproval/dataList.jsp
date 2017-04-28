<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
   <link rel="stylesheet" href="${base}/public/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="${base}/public/ionicons/css/ionicons.min.css">
  <link rel="stylesheet" href="${base}/public/dist/css/applyApproval.css">
  <link rel="stylesheet" href="${base}/public/dist/css/iosSelect.css">
  <title>审批手机-审批列表</title>
  <script>
        /*var _hmt = _hmt || [];
        (function() {
        var hm = document.createElement("script");
        hm.src = "//hm.baidu.com/hm.js?b25bf95dd99f58452db28b1e99a1a46f";
        var s = document.getElementsByTagName("script")[0]; 
        s.parentNode.insertBefore(hm, s);
        })();*/
  </script>
</head>
<body>
  <header class="header">
	<ul class="list-ul">
		<li><a href="${base}/admin/applyapproval/list.html"><i class="fa fa-angle-left"></i>返回</a></li>
		<li><h3>审批列表</h3></li>
		<!-- <li><a href="javascript:;" id="selectDate">筛选</a></li> -->
		<li>
		<input id="date" value="" type="hidden">
		<div class="form-item item-line" id="selectDate">                 
		   <!--  <label>时间选择器</label> -->                 
		    <div class="pc-box">                     
		        <span data-year="" data-month="" data-date="" id="showDate">筛选</span>  
		    </div>             
		</div>
		</li>
	</ul>
	
  </header>
  	<input type="hidden" id="operation" value="${obj.operation}">
  	
  <content class="content-list">
  
  	<c:if test="${obj.operation=='international' }">
  		<c:forEach items="${obj.query }" var="each">
		  	<a href="${base}/admin/applyapproval/detailList.html?id=${each.id}&operation=${obj.operation}&date=${obj.date}&reduce=${each.isReduce}">
				<ul class="content-a-ul">
					<li>
						<span>${each.shortName }</span>
						
						<span><fmt:formatNumber type="number" value="${each.amount }" pattern="0.00" maxFractionDigits="2"></fmt:formatNumber></span>
							
					<%-- 	<c:if test="${obj.operation=='international'}">
						
							<span>${each.amount }</span>
						</c:if> --%>
					</li>
					<li>
						<span>${each.ordersnum }</span>
						<%-- <c:choose>
							<c:when test="${each.isReduce == 'YES'}">
							
								<span>减免</span>
							</c:when>
							<c:otherwise> --%>
								<span>${each.purposeStr }</span>
							
						<%-- 	</c:otherwise>
						</c:choose> --%>
					</li>
					<li>
						
						<span>${each.orderstatus }</span>
						
						<span>${each.fullName }</span>
					</li>
					<%-- <li>
						<span>${each.purpose }</span>
						<span>${each.proposer }</span>
					</li> --%>
					<li>
							<c:if test="${each.paystatus==1 }">
								<span class="color1">
									待审批
								</span>
								
							</c:if>
							<c:if test="${each.paystatus==2 }">
								<span class="color2">
									同意
								</span>
							</c:if>
							<c:if test="${each.paystatus==4 }">
								<span class="color1">
									拒绝
								</span>
								
							</c:if>
							<c:if test="${each.paystatus==3}">
								<span class="color2">
									同意
								</span>
								
							</c:if>
		
							<span><fmt:formatDate value="${each.orderstime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
							
						
					</li>
				</ul>
			</a>
	  </c:forEach>
  	</c:if>
  	<c:if test="${obj.operation=='inlandNum' }">
 		<c:forEach items="${obj.query }" var="each">
		  	<a href="${base}/admin/applyapproval/detailList.html?id=${each.id}&operation=${obj.operation}&date=${obj.date}&reduce=${each.isReduce}">
				<ul class="content-a-ul">
					<li>
						<span>${each.shortName }</span>
						
						<span><fmt:formatNumber type="number" value="${each.costpricesum }" pattern="0.00" maxFractionDigits="2"></fmt:formatNumber></span>
							
					<%-- 	<c:if test="${obj.operation=='international'}">
						
							<span>${each.amount }</span>
						</c:if> --%>
					</li>
					<li>
						<span>${each.ordersnum }</span>
						<%-- <c:choose>
							<c:when test="${each.isReduce == 'YES'}">
							
								<span>减免</span>
							</c:when>
							<c:otherwise> --%>
								<span>${each.purposeStr }</span>
							
						<%-- 	</c:otherwise>
						</c:choose> --%>
					</li>
					<li>
						
							<span>${each.PNR }</span>
						
						<span>${each.fullName }</span>
					</li>
					<%-- <li>
						<span>${each.purpose }</span>
						<span>${each.proposer }</span>
					</li> --%>
					<li>
							<c:if test="${each.orderPnrStatus==1 }">
								<span class="color1">
									待审批
								</span>
								
							</c:if>
							<c:if test="${each.orderPnrStatus==2 }">
								<span class="color2">
									同意
								</span>
							</c:if>
							<c:if test="${each.orderPnrStatus==4 }">
								<span class="color1">
									拒绝
								</span>
								
							</c:if>
							<c:if test="${each.orderPnrStatus==3}">
								<span class="color2">
									同意
								</span>
								
							</c:if>
		
								<span><fmt:formatDate value="${each.optime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
							
						
					</li>
				</ul>
			</a>
	  </c:forEach>
  	</c:if>
  
  
  
  
  
  
  <!-- ***************************内陆处理开始****************************** -->
 <%--  <c:if test="${obj.operation=='inlandNum' }">
  
	  <c:forEach items="${obj.reduceInlandList }" var="each">
  		<a href="${base}/admin/applyapproval/detailList.html?id=${each.id}&operation=${obj.operation}&date=${obj.date}&reduce=reduce">
			<ul class="content-a-ul">
				<li>
					<span>${each.customname }</span>
					
					<span>${each.account }</span>
				</li>
				<li>
					<span>${each.ordersnum }</span>
					<span>减免申请</span>
				</li>
				<li>
						<span></span>
				
					<span>${each.userName }</span>
				</li>
				<li>
					<span>${each.purpose }</span>
					<span>${each.proposer }</span>
				</li>
				<li>
						<c:if test="${each.applyResult==1}">
							<span class="color1">
								待审批
							</span>
							
						</c:if>
						<c:if test="${each.applyResult==2}">
							<span class="color2">
								同意
							</span>
						</c:if>
						<c:if test="${each.applyResult==3}">
							<span class="color1">
								拒绝
							</span>
							
						</c:if>
						<span><fmt:formatDate value="${each.optime }" pattern="yyyy:MM:dd HH:mm:ss"/></span>
						
					
				</li>
			</ul>
		</a>
	  </c:forEach>
  </c:if>
  <!-- ***************************内陆处理结束****************************** -->
	  <!-- ***************************国际处理开始****************************** -->
	  <c:if test="${obj.operation=='international' }">
	  	<c:forEach items="${obj.reduceInteList }" var="each">
	  		<a href="${base}/admin/applyapproval/detailList.html?id=${each.id}&operation=${obj.operation}&date=${obj.date}&reduce=reduce">
				<ul class="content-a-ul">
					<li>
						<span>${each.customname }</span>
						
						<span>${each.account }</span>
					</li>
					<li>
						<span>${each.ordersnum }</span>
						<span>减免申请</span>
					</li>
					<li>
							<span></span>
					
						<span>${each.userName }</span>
					</li>
					<li>
						<span>${each.purpose }</span>
						<span>${each.proposer }</span>
					</li>
					<li>
							<c:if test="${each.applyResult==1}">
								<span class="color1">
									待审批
								</span>
								
							</c:if>
							<c:if test="${each.applyResult==2}">
								<span class="color2">
									同意
								</span>
							</c:if>
							<c:if test="${each.applyResult==3}">
								<span class="color1">
									拒绝
								</span>
								
							</c:if>
							<span><fmt:formatDate value="${each.optime }" pattern="yyyy:MM:dd HH:mm:ss"/></span>
							
						
					</li>
				</ul>
			</a>
	  	</c:forEach>
	  </c:if> --%>
	  <!-- ***************************国际处理结束****************************** -->
  </content>
  	
  <%-- <content class="content-list">
  	
	<a href="${base}/admin/applyapproval/detailList.html">
		<ul class="content-a-ul">
			<li>
				<span>北京直立人科技有限公司</span>
				<span>￥69880</span>
			</li>
			<li>
				<span>代订机票</span>
				<span>王五</span>
			</li>
			<li>
				<span class="color1">同意</span>
				<span>2017-03-09 12:22</span>
			</li>
		</ul>
	</a>
  </content> --%>
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${base}/public/dist/js/zepto.js"></script>
<script src="${base}/public/dist/js/iscroll.js"></script>
<script src="${base}/public/dist/js/iosSelect.js"></script>
<script type="text/javascript">
	/* $(function(){
		var aa=$('.color1').html();
		if(aa==' 同意 '){
			$(this).css('color','green !important');
		}else{
			alert('拒绝');
		}
	}); */
	var selectDateDom = $('#selectDate');
    var showDateDom = $('#showDate');
    // 初始化时间
    var now = new Date();
    var nowYear = now.getFullYear();
    var nowMonth = now.getMonth() + 1;
    var nowDate = now.getDate();
    showDateDom.attr('data-year', nowYear);
    showDateDom.attr('data-month', nowMonth);
    showDateDom.attr('data-date', nowDate);
    // 数据初始化
    function formatYear (nowYear) {
        var arr = new Array();
        for (var i = nowYear - 5; i <= nowYear + 5; i++) {
            arr.push({
                id: i + '',
                value: i + '年'
            });
        }
        return arr;
    }
    function formatMonth () {
        var arr = new Array();
        for (var i = 1; i <= 12; i++) {
            arr.push({
                id: i + '',
                value: i + '月'
            });
        }
        return arr;
    }
    function formatDate (count) {
        var arr = new Array();
        for (var i = 1; i <= count; i++) {
            arr.push({
                id: i + '',
                value: i + '日'
            });
        }
        return arr;
    }
     function yearData(callback) {
        callback(formatYear(nowYear));
    } 
 /*    var monthData1 = function(year,callback) {
        //callback(formatMonth());
    }; */
    function monthData(year,callback){
    	callback(formatMonth())
    }
     function dateData(year, month, callback) {
        if (/^(1|3|5|7|8|10|12)$/.test(month)) {
            callback(formatDate(31));
        }
        else if (/^(4|6|9|11)$/.test(month)) {
            callback(formatDate(30));
        }
        else if (/^2$/.test(month)) {
            if (year % 4 === 0 && year % 100 !==0 || year % 400 === 0) {
                callback(formatDate(29));
            }
            else {
                callback(formatDate(28));
            }
        }
        else {
            throw new Error('month is illegal');
        }
    };
    
    selectDateDom.bind('click', function () {
        var oneLevelId = showDateDom.attr('data-year');
        var twoLevelId = showDateDom.attr('data-month');
        var threeLevelId = showDateDom.attr('data-date');

        var iosSelect = new IosSelect(3, 
            [yearData, monthData, dateData],
            {
                title: '时间选择',
                itemHeight: 35,
                relation: [1, 1, 0, 0],
                itemShowCount: 9,
                oneLevelId: oneLevelId,
                twoLevelId: twoLevelId,
                threeLevelId: threeLevelId,
                
                callback: function (selectOneObj, selectTwoObj, selectThreeObj) {
                    showDateDom.attr('data-year', selectOneObj.id);
                    showDateDom.attr('data-month', selectTwoObj.id);
                    showDateDom.attr('data-date', selectThreeObj.id);
                    var date=selectOneObj.id+"-"+selectTwoObj.id+"-"+selectThreeObj.id;
                    $("#date").val(date);
                    window.location.href="${base}/admin/applyapproval/dataList.html?operation=${obj.operation}&date="+date;
                    //alert(selectOneObj.id);
                    //showDateDom.html(selectOneObj.value + ' ' + selectTwoObj.value + ' ' + selectThreeObj.value );
               		//selectByDate(selectOneObj.id,selectTwoObj.id,selectThreeObj.id);
                }
        });
    });
    
    //根据日期进行筛选
    function selectByDate(year,month,day){
    	var url = "${base}/admin/applyapproval/dataList.html";
    	$.ajax({
			type : 'POST',
			data : {
				date:year+"-"+month+"-"+day,
				operation:$('#operation').val()
			},
			dataType : 'json',
			url : url,
			success : function(data) {
				alert(111);
			},
			error : function(xhr) {
				//layer.msg("审核失败", "", 3000);
			}
		});
    }
    
</script>
<script type="text/javascript">
	/* var empTable;
	function initDatatable() {
		empTable = $('#bankCardTable').DataTable({
			"searching" : false,
			"processing" : true,
			"serverSide" : true,
			"stripeClasses": [ 'strip1','strip2' ],//斑马线
			"bLengthChange" : false,
			"bSort": true, //排序功能 
			"language" : {
				"url" : "${base}/public/plugins/datatables/cn.json"
			},
	       	"ajax": {
	               "url": "${base}/admin/bankcard/listData.html",
	               "type": "post",
	               "data": function (d) {
	            	   
	            	}
	        },
	        "columns": [
	                    {"data": "cardname", "bSortable": false},
	                    {"data": "cardnum", "bSortable": false},
	                    {"data": "bankcardtype", "bSortable": false},
	                    {"data": "bankname", "bSortable": false},
	                    {"data": "balance", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.balance;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "0";
	                    		}
	                    		return depositBalance;
	                    	}
						},
						{"data": "initialamount", "bSortable": false},
	                    {"data": "currency", "bSortable": false},
	                    {"data": "remark", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.remark;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		if(depositBalance.length>4){
	                    			var res=depositBalance.substring(0,4); 
	                    			return res+"...";
	                    		}
	                    		return depositBalance;
	                    	}	
	                    }
	            ],
	        columnDefs: [{
	            //   指定第一列，从0开始，0表示第一列，1表示第二列……
	            targets: 8,
	            render: function(data, type, row, meta) {
	            	var modify = '<a style="cursor:pointer;" onclick="editUser('+row.id+');">编辑</a>';
	                return modify;
	            }
	        }]
		});
	}
	$(function() {
		initDatatable();
		//selectDeptName();
	}); */
</script>
</body>
</html>
