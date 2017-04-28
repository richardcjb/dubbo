<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=7" />
	<meta http-equiv="Access-Control-Allow-Origin" content="*" />
	<meta name="alexaVerifyID" content="" />
    <title>编辑计划</title>
	<link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base }/public/plugins/select2/select2.css">
	<link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
	<link rel="stylesheet" href="${base }/public/dist/css/font-awesome.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/ionicons.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/skins/_all-skins.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
	<link rel="stylesheet" href="${base }/public/css/pikaday.css">
	<link rel="stylesheet" type="text/css" href="${base }/public/dist/css/airlinesModule.css">
	<!-- style -->
    <link rel="stylesheet" href="${base }/public/css/style.css">
	<style type="text/css">
		.wu-example .statusBar .btns .uploadBtn {background: #3c8dbc !important;color: #fff;border-color: transparent;position: relative;top: -122px;height: 40px;border-radius: 5px;}
		html, body {min-height: 0;min-width: 0;overflow-x: auto !important;}
		.select2-container--default .select2-search--inline .select2-search__field{margin-left: 0px;}
		.select2-container--default .select2-selection--multiple .select2-selection__choice{background-color: rgba(60, 141, 188, 0);border-color: rgba(54, 127, 169, 0);color: #555555;padding: 0px;}
		.select2-container--default .select2-selection--multiple .select2-selection__choice__remove{display:none;}
		.addAirPlan,.removeAirPlan {position: relative;top: -10px;left: -5px;}
		.hangduan div.form-group:nth-child(2) label{width: 80px;padding-right: 0;position: relative;left:-5px;}
		.hangduan div.form-group:nth-child(3) label{width:80px;padding-right:0;position: relative;left: -15px;}
		.hangduan div.form-group:nth-child(4) label{width:70px;padding-right:0;position: relative;left:-15px;}
		.hangduan div.form-group:nth-child(5) label{width:50px;padding-right:0;position: relative;left:-15px;}
		.hangduan div.form-group:nth-child(1) div{width: 160px !important;}
		.hangduan div.form-group:nth-child(2) div{width: 160px !important;position: relative;left:-5px;}
		.hangduan div.form-group:nth-child(3) div{position: relative;left: -15px;}
		.hangduan div.form-group:nth-child(4) div{width:120px;padding-right:0;position: relative;left:-20px;}
		input#setoffdate {position: relative;left:5px;}
		input#setofftime {position: relative;left:-15px;}
		.hangduan div.form-group:nth-child(1) div .select2-container{width:155px !important;}
		.hangduan div.form-group:nth-child(2) div .select2-container{width:155px !important;}
		.hangduan div.form-group:nth-child(3) div .select2-container{width:85px !important;}
	</style>
</head>
<body>
	<div class="modal-top">
     <div class="modal-header boderButt">
        <button type="button" class="btn btn-primary right btn-sm" data-dismiss="modal" onclick="closewindow();">取消</button>
        <!-- <input id="submit" class="btn btn-primary right btn-sm" onclick="saveEditPlan();" value="保存"/> -->
        <input type="submit" id="submit" class="btn btn-primary right btn-sm" data-dismiss="modal" onclick="saveEditPlan();" value="保存"/>
        <c:choose>
        	<c:when test="${obj.planinfo.isclose eq 0}">
		        <button type="button" class="btn right btn-sm" onclick="closeEditPlan();" data-dismiss="modal">关闭</button>
        	</c:when>
        	<c:otherwise>
		        <button type="button" class="btn right btn-sm" onclick="enableEditPlan(${obj.planinfo.id});" data-dismiss="modal">启用</button>
        	</c:otherwise>
        </c:choose>
        <h4>编辑计划详情</h4>
     </div>
          <div class="modal-body">
            <div class="tab-content">
                <div class="EditTitle">
                  <p class="EditTitle_p">系列团：</p>
                </div>
                <form id="editPlanForm">
                <div class="row"><!--航空公司/旅行社-->
                	<div class="form-group">
	                	<input id="id" name="id" type="hidden" value="${obj.planinfo.id}">
	                  	 <c:choose>
	                  		<c:when test="${empty obj.ordernum}">
	                  			<label class="col-sm-2 text-right padding customerEdit"></label>
	                  			<div class="col-sm-2 padding">	
			                   	 　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="generateOrder" type="checkbox" class="inputEdit" value="1"><span class="spanEdit">生成订单</span>
			                    </div>
	                  		</c:when>
	                  		<c:otherwise>
			                  <label class="col-sm-2 text-right padding customerEdit">订单号：</label>
				              <div class="col-sm-2 padding">	
				                 <label class="col-sm-2 padding customerEdit">${obj.ordernum }</label>
				              </div>
	                  		</c:otherwise>
	                  	</c:choose> 
                	</div>
                	<%-- <div class="form-group">
	                  <label class="col-sm-2 text-right padding">航空公司：</label>
	                  <div class="col-sm-2 padding">
	                    <select id="airlinename" name="airlinename" type="text" class="form-control input-sm select2" multiple="multiple"
	                      placeholder="首航-CA" >
	                      	<c:forEach var="one" items="${obj.aircom }">
								<option value="${one.dictCode }">${one.dictName}</option>
							</c:forEach>
	                      </select>
	                  </div>
                	</div> --%>
                	<div class="form-group form-group1">
	                  <label class="col-sm-2 text-right padding">旅行社：</label>
	                  <div class="col-sm-2 padding">
	                    <select id="travelname" name="travelname" type="text" class="form-control input-sm select2" placeholder="" multiple="multiple">
	                    	<c:forEach var="one" items="${obj.travel }">
								<option value="${one.shortName }">${one.shortName}</option>
							</c:forEach>
	                    </select>
	                  </div>
                	</div>
                </div><!--end 航空公司/旅行社-->

                <div class="row"><!--人数/天数/联运要求-->
                	<div class="form-group">
	                  <label class="col-sm-2 text-right padding customerEdit">人数：</label>
	                  <div class="col-sm-2 padding">	
	                    <input id="peoplecount" name="peoplecount" type="text" class="form-control input-sm" placeholder="" value="${obj.planinfo.peoplecount }"/>
	                  </div>
                	</div>
                	<div class="form-group form-group1">
	                  <label class="col-sm-2 text-right padding">天数：</label>
	                  <div class="col-sm-2 padding">
	                    <input id="dayscount" name="dayscount" type="text" class="form-control input-sm" placeholder=" " value="${obj.planinfo.dayscount }"/>
	                  </div>
                	</div>
                	<div class="form-group form-group1">
	                  <label class="col-sm-2 text-right padding customerEdit">联运要求：</label>
	                  <div class="col-sm-2 padding">
	                    <select id="unioncity" name="unioncity" type="text" class="form-control input-sm select2" multiple="multiple" placeholder=" " >
	                    	<c:forEach var="one" items="${obj.union }">
	                    		<c:choose>
	                    			<c:when test="${one.id eq 0}">
										<option value="${one.dictCode }" selected="selected">${one.dictCode}</option>
	                    			</c:when>
	                    			<c:otherwise>
										<option value="${one.dictCode }">${one.dictCode}-${one.englishName }-${one.countryName }</option>
	                    			</c:otherwise>
	                    		</c:choose>
							</c:forEach>
	                    </select>
	                  </div>
                	</div>
                </div><!--end 人数/天数/联运要求-->
                </form>
                <c:forEach var="airline" items="${obj.airlineinfo }" varStatus="status">
	                <div class="row hangduan"><!--去程日期/出发城市/出发航班-->
	                	<div class="form-group">
		                  <label class="col-sm-1 text-right padding customerEdit">出发城市：</label>
		                  <div class="col-sm-1 padding">
		                    <select id="leavescity" name="leavescity" type="text" class="form-control input-sm select2" multiple="multiple" placeholder="" >
		                    	<c:forEach var="one" items="${obj.city }">
		                    		<c:choose>
		                    			<c:when test="${airline.leavecity eq one.dictCode }">
											<option value="${one.dictCode }" selected="selected">${one.dictCode}-${one.englishName }-${one.countryName }</option>
		                    			</c:when>
		                    			<c:otherwise>
											<option value="${one.dictCode }">${one.dictCode}-${one.englishName }-${one.countryName }</option>
		                    			</c:otherwise>
		                    		</c:choose>
								</c:forEach>
		                    </select>
		                  </div>
	                	</div>
	                	<div class="form-group form-group1">
		                  <label class="col-sm-1 text-right padding">抵达城市：</label>
		                  <div class="col-sm-1 padding">
		                     <select id="backscity" name="backscity" type="text" class="form-control input-sm select2" multiple="multiple" placeholder="">
		                    	<c:forEach var="one" items="${obj.city }">
									<c:choose>
		                    			<c:when test="${airline.arrvicity eq one.dictCode }">
											<option value="${one.dictCode }" selected="selected">${one.dictCode}-${one.englishName }-${one.countryName }</option>
		                    			</c:when>
		                    			<c:otherwise>
											<option value="${one.dictCode }">${one.dictCode}-${one.englishName }-${one.countryName }</option>
		                    			</c:otherwise>
		                    		</c:choose>
								</c:forEach>
		                    </select>
		                  </div>
	                	</div>
	                	<div class="form-group form-group1">
		                  <label class="col-sm-1 text-right padding customerEdit">去程航班：</label>
		                  <div class="col-sm-1 padding">
		                  	<select id="leaveairline" name="leaveairline" type="text" class="form-control input-sm select2" multiple="multiple" placeholder=" " >
		                    	<c:forEach var="one" items="${obj.airline }">
		                    		<c:choose>
		                    			<c:when test="${airline.ailinenum eq one.dictName }">
											<option value="${one.dictName }" selected="selected">${one.dictName}</option>
		                    			</c:when>
		                    			<c:otherwise>
											<option value="${one.dictName }">${one.dictName}</option>
		                    			</c:otherwise>
		                    		</c:choose>
								</c:forEach>
		                    </select>
		                    
		                  </div>
	                	</div>
	                	<div class="form-group form-group1">
		                  <label class="col-sm-1 text-right padding customerEdit">出发日期：</label>
		                  <div class="col-sm-1 padding">
		                    <input id="setoffdate" name="setoffdate" type="text" class="form-control input-sm" placeholder="2016-12-01" onFocus="WdatePicker({minDate:'%y-%M-%d'})" value="<fmt:formatDate value="${airline.leavedate }" pattern="yyyy-MM-dd" />"/>
		                  </div>
	                	</div>
	                	<div class="form-group form-group1">
		                  <label class="col-sm-1 text-right padding">时间：</label>
		                  <div class="col-sm-1 padding">
		                    <input id="setofftime" name="setofftime" type="text" class="form-control input-sm inputtime" placeholder="" value="${airline.leavetime }/${airline.arrivetime }"/>
		                  </div>
	                	</div>
	                	<c:choose>
                   			<c:when test="${status.index eq 0}">
								<a href="javascript:;" name="addButton" class="glyphicon glyphicon-plus addAirPlan"></a><!--添加div按钮-->
                   			</c:when>
                   			<c:otherwise>
								<a href="javascript:;" name="closeButton" class="glyphicon glyphicon-minus removeAirPlan"></a><!--删除div按钮-->
                   			</c:otherwise>
                   		</c:choose>
	                </div><!--end 回程日期/返回城市/回程航班-->
				</c:forEach>
            </div>
          </div>
	</div>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var airlinename = '${obj.planinfo.airlinename }';
		var travelname = '${obj.planinfo.travelname }';
		var unioncity = '${obj.planinfo.unioncity }';
		var leavescity = '${obj.planinfo.leavescity }';
		var leaveairline = '${obj.planinfo.leaveairline }';
		var backscity = '${obj.planinfo.backscity }';
		var backairline = '${obj.planinfo.backairline }';
	</script>
	<!-- jQuery 2.2.3 -->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<!--layer -->
	<script src="${base}/common/js/layer/layer.js"></script>
	<!-- Select2 -->
	<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
	<script src="${base}/admin/airline/editplanselect2.js"></script>
	<!--pikaday -->
	<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

	if(travelname){
		$('#generateOrder').attr('checked','checked');
	}
	//关闭弹框,关闭当前窗口
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	//保存编辑计划
	function saveEditPlan(){
		//循环出发城市、抵达城市、航班号
		var cityairlinejson = '[';
		$('.hangduan').each(function(j){
			//航班号
			var leaveairline = $(this).find('[name=leaveairline]').val();
			if (leaveairline) {
				leaveairline = leaveairline.join(',');
			}
			//出发城市
			var leavescity = $(this).find('[name=leavescity]').val();
			if (leavescity) {
				leavescity = leavescity.join(',');
			}
			//抵达城市
			var backscity = $(this).find('[name=backscity]').val();
			if (backscity) {
				backscity = backscity.join(',');
			}
			//出发日期
			var setoffdate = $(this).find('[name=setoffdate]').val();
			//出发时间
			var setofftime = $(this).find('[name=setofftime]').val();
			cityairlinejson += '{"leaveairline":"'+leaveairline+'","leavescity":"'+leavescity+'","backscity":"'+backscity+'","setoffdate":"'+setoffdate+'","setofftime":"'+setofftime+'"},';
		});
		cityairlinejson = cityairlinejson.substring(0,cityairlinejson.length-1);
		cityairlinejson += ']';
		//校验
		$('#editPlanForm').bootstrapValidator('validate');
		var bootstrapValidator = $("#editPlanForm").data('bootstrapValidator');
		//如果校验通过则提交更新数据
		if(bootstrapValidator.isValid()){
			var generateOrder = $('#generateOrder').is(':checked');
			//生成订单
			if(generateOrder){
				$.ajax({ 
					type: 'POST', 
					data: {planids:'${obj.planinfo.id}'}, 
					url: '${base}/admin/customneeds/generateOrderNum.html',
		            success: function (data) { 
		            	$.ajax({ 
		    				type: 'POST', 
		    				data: $("#editPlanForm").serialize()+'&cityairlinejson='+cityairlinejson, 
		    				url: '${base}/admin/customneeds/updateEditPlan.html',
		    	            success: function (data) { 
		    	            	closewindow();
		    	            	window.parent.successCallback('2');
		    	            },
		    	            error: function (xhr) {
		    	            	layer.alert("编辑失败","",3000);
		    	            } 
		    	        });
		            },
		            error: function (xhr) {
		            } 
		        });
			}else{
				$.ajax({ 
    				type: 'POST', 
    				data: $("#editPlanForm").serialize()+'&cityairlinejson='+cityairlinejson, 
    				url: '${base}/admin/customneeds/updateEditPlan.html',
    	            success: function (data) { 
    	            	closewindow();
    	            	window.parent.successCallback('2');
    	            },
    	            error: function (xhr) {
    	            	layer.alert("编辑失败","",3000);
    	            } 
    	        });
			}
			
		}
	}
	//关闭编辑计划
	function closeEditPlan(){
		layer.confirm('确定要关闭该计划吗?', {icon: 3, title:'提示'}, function(){
			$.ajax({ 
				type: 'POST', 
				data: {id:'${obj.planinfo.id}'}, 
				url: '${base}/admin/customneeds/closeEditPlan.html',
	            success: function (data) { 
	            	closewindow();
	            	window.parent.successCallback('3');
	            },
	            error: function (xhr) {
	            	layer.alert("关闭失败",{time: 2000, icon:1});
	            } 
	        });
		});
	}
	//启用计划
	function enableEditPlan(id){
		layer.confirm('确定要启用该计划吗?', {icon: 3, title:'提示'}, function(){
			$.ajax({ 
				type: 'POST', 
				data: {id:id}, 
				url: BASE_PATH + '/admin/customneeds/enableEditPlan.html',
	            success: function (data) { 
	            	closewindow();
	            	window.parent.successCallback('4');
	            },
	            error: function (xhr) {
	            	layer.alert("启用失败",{time: 2000, icon:1});
	            } 
	        });
		});
	}
	//点击编辑的时候验证
	$('#submit').click(function() {
        $('#editPlanForm').bootstrapValidator('validate');
    });
	//表单验证
	$('#editPlanForm').bootstrapValidator({
		 message: '验证不通过',
       feedbackIcons: {
           valid: 'glyphicon glyphicon-ok',
           invalid: 'glyphicon glyphicon-remove',
           validating: 'glyphicon glyphicon-refresh'
       },
       fields: {
    	   airlinename: {
               validators: {
                   notEmpty: {
                       message: '航空公司不能为空'
                   }
               }
           },
           peoplecount: {
           	validators: {
           		notEmpty: {
                       message: '人数不能为空'
                   },
           		regexp: {
                       regexp: /^[0-9]+$/,
                       message: '人数只能为数字'
                   }
               }
           },
           dayscount: {
           	validators: {
           		notEmpty: {
                       message: '天数不能为空'
                   },
           		regexp: {
                       regexp: /^[0-9]+$/,
                       message: '天数只能为数字'
                   }
               }
           }
       }
	});
	//选择旅行社之后选中复选框
	$('#travelname').change(function(){
		var airlinename = $('#airlinename').val();
		if(airlinename){
			$('#generateOrder').attr('checked','checked');
		}
	}); 
</script>
</body>
</html>	
