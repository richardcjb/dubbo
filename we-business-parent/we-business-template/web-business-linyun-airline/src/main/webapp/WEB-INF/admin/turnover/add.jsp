<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>


<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>记一笔</title>
   	 	<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
    	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
      	<link rel="stylesheet" href="${base}/public/dist/css/swiftNumber.css"><!--本页style-->
</head>
<body>
	<div class="modal-top">
          <form method="post" id="addTurnOver">
	   		  <div class="modal-header boderButt">
	            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">取消</button>
	            <input type="submit" id="submit" class="btn btn-primary right btn-sm" value="保存" onclick="addTurnOverOne();"/>
	            <h4>记一笔</h4>
	          </div>
	          <div class="modal-body" style="height: 257px;overflow-y: auto;">
	            <div class="tab-content backcard">
	                <div class="row">
	                	<div class="form-group">
	                	
			                  <label class="col-sm-2 text-right padding">交易日期：</label>
			                  <div class="col-sm-2 padding">
			                    <input name="tradeDate" id="tradeDate" type="text" class="form-control input-sm" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			                  </div>
	                	</div>
	                	<div class="form-group form-group1">
	                	
		                  <label class="col-sm-2 text-right padding">银行卡名称：</label>
		                  <div class="col-sm-2 padding">
		                    <select id="selectedBankName" class="form-control input-sm" name="bankName" onchange="findBankNum();">
		                      <!-- <option>国际段专用</option>
		                      <option>澳币专用</option> -->
		                      	<c:forEach items="${obj.bankNameList }" var="each">
		                      		<option value="${each.cardName }">${each.cardName }</option>
		                     	 </c:forEach>	                    
		                      </select>
		                  </div>
	                	</div>
	                	<div class="form-group form-group1">
	                	
		                  <label class="col-sm-1 text-right padding">卡号：</label>
		                  <div class="col-sm-2 padding">
		                    <select id="selectedBankNum" class="form-control input-sm" name="cardNum">
		                    	<%-- <c:forEach items="${obj.bankNameList }" var="each">
		                      		
				                      <option value="信用卡" <c:if test="${'信用卡' eq each.currency}">selected</c:if>>${each.currency }</option>
				                      <option value="存储卡" <c:if test="${'存储卡' eq each.currency}">selected</c:if>>储存卡</option>
		                     	 </c:forEach>	 --%>
		                     	 
		                     	 
		                    </select>
		                  </div>
	                	</div>
	                </div>
	
	               
	                <div class="row">
	                	<div class="form-group">
	                	
		                  <label class="col-sm-2 text-right padding">用途：</label>
		                  <div class="col-sm-2 padding">
		                      <select class="form-control input-sm" name="purpose" id="purpose" onchange="check();">
		                      <option value="">请选择</option>
		                      <option>收入</option>
		                      <option>支出</option>
		                    </select>
		                  </div>
	                	</div>
	                	<div class="form-group form-group1">
	                	
		                  <label class="col-sm-2 text-right padding">平均汇率：</label>
		                  <div class="col-sm-2 padding">
		                      <input name="averageRate" type="text" class="form-control input-sm"/>
		                  </div>
	                	</div>
	                	<div class="form-group form-group1">
		                  <label class="col-sm-1 text-right padding">金额：</label>
		                  <div class="col-sm-2 padding">
		                      <input name="money" type="text" class="form-control input-sm" id="money" oninput="checkLength(this);"/>
		                  </div>
	                	</div>
	                </div><!--end 银行/币种-->
	                <div class="row"><!--备注/币种/项目-->
	                	<div class="form-group">
	                	
		                	<label class="col-sm-2 text-right padding">币种：</label>
		                  <div class="col-sm-2 padding">
		                      <select class="form-control input-sm" name="currency">
		                      		<option value="">请选择</option>
			                      <c:forEach items="${obj.currencyList }" var="each">
			                    		<option value="${each.dictCode }">${each.dictCode }</option>
				                  </c:forEach>
		                    </select>
		                  </div>
	                	</div>
	                	<div class="form-group form-group1">
	                		
		                	<label class="col-sm-2 text-right padding">项目：</label>
		                  <div class="col-sm-2 padding">
		                      <select class="form-control input-sm" name="projectName">
		                      		<option value="">请选择</option>
			                      <c:forEach items="${obj.projectList }" var="each">
			                    		<option value="${each.comDictName }">${each.comDictName }</option>
				                  </c:forEach>
		                    </select>
		                  </div>
	                	</div>
	                	<div class="form-group form-group1">
		                  <label class="col-sm-1 text-right padding">备注：</label>
		                  <div class="col-sm-2 padding">
		                  	<input  type="text" class="form-control input-sm" name="remark"/>
		                    <!-- <textarea class="form-control input-sm textareaHeight" name="remark"></textarea> -->
		                  </div>
	                	</div>

	                </div><!--end 备注/币种/项目-->
	                <!-- <div>
	                	<label class="col-sm-3 text-right padding">开/收发票：</label>
	                	<select class="form-control input-sm" name="invoiceStatus" id="invoiceStatus" onchange="check();">
		                      <option value="">请选择</option>
		                      <option>已开发票</option>
		                      <option>已收发票</option>
		                 </select>
	                </div>
	                <div id="div1" >
	                <label class="col-sm-3 text-right padding">单位名称：</label>
	                	<select id="findCompany" name="findCompany" onchange="setSelectedAreaIds()" class="form-control select2 inpImpWid" multiple="multiple" ></select>
		               <input name="companyNameId" id="companyNameId"  type="hidden" placeholder="单位名称" />
	                </div> -->
	                <div class="row"><!--备注/币种/项目-->
	                	<div class="form-group">
		                	<label class="col-sm-2 text-right padding">开/收发票：</label>
		                	<div class="col-sm-2 padding">
			                	<select class="form-control input-sm" name="invoiceStatus" id="invoiceStatus" >
				                      <option value="">请选择</option>
				                      <option>已开发票</option>
				                      <option>已收发票</option>
				                 </select>
			                 </div>
	                	</div>
	                	<div id="div1" class="form-group form-group1">
	                		<label class="col-sm-2 text-right padding">单位名称：</label>
	                		<div class="col-sm-5 padding select2TextWidth">
			                	<select id="findCompany" name="findCompany" onchange="setSelectedAreaIds()" class="form-control select2 inpImpWid" multiple="multiple" ></select>
				               	<input name="companyNameId" id="companyNameId"  type="hidden" placeholder="单位名称" />
	                		</div>
	                	</div>
	                </div><!--end 备注/币种/项目-->
	            </div>
	            <!-- 设置已选中的项 -->
					<script type="text/javascript">
						function setSelectedAreaIds() {
							var _selectedAreaIds = $("#findCompany").select2("val");
							$("#companyNameId").val(_selectedAreaIds);
						}
					</script>
	          </div>
          </form>
	</div>
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script type="text/javascript" src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<!-- Select2 -->
	<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script type="text/javascript">
	//验证输入内容不能为空
	 $(document).ready(function(){
		formValidator();
	}); 
	
	//点击取消
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	
	//表单验证
	function formValidator(){
		$('#addTurnOver').bootstrapValidator({
			message: '验证不通过!',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	           bankName: {
	            	validators: {
	                    notEmpty: {
	                        message: '银行卡名称不能为空!'
	                    }
	                }
	            },
	            tradeDate: {
	            	validators: {
	                    notEmpty: {
	                        message: '交易日期不能为空!'
	                    }
	                }
	            },
	            projectName: {
	            	validators: {
	                    notEmpty: {
	                        message: '项目名称不能为空!'
	                    }
	                }
	            },
	            currency: {
	            	validators: {
	                    notEmpty: {
	                        message: '币种不能为空!'
	                    }
	                }
	            },
	            averageRate: {
	            	validators: {
	                    regexp: {
	                	 	regexp: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
	                        message: '平均汇率必须为数字!'
	                    }
	                }
	            },
	            cardNum: {
	            	validators: {
	                    notEmpty: {
	                        message: '银行卡号不能为空!'
	                    }
	                }
	            },
	            invoiceStatus: {
	            	validators: {
	                    notEmpty: {
	                        message: '发票状态不能为空!'
	                    }
	                }
	            },
	            purpose: {
	            	validators: {
	                    notEmpty: {
	                        message: '用途不能为空!'
	                    }
	                }
	            },
	            money: {
	            	validators: {
	            		notEmpty: {
	                        message: '金额不能为空'
	                    },
	                    regexp: {
	                	 	regexp: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
	                        message: '金额必须为数字!'
	                    },
	                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
	                         url: '${base}/admin/turnover/checkBankCardNumEnough.html',//验证地址
	                         message: '银行卡余额不足，请更换银行卡!',//提示消息
	                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
	                         type: 'POST',//请求方式
	                         //自定义提交数据，默认值提交当前input value
	                         data: function(validator) {
	                            return {
	                            	cardNum:$('#selectedBankNum').val(),
	                            	purpose:$('#purpose').val(),
	                            	money:$('input[name="cardNum"]').val()
	                            };
	                         }
	                     }
	                }
	            }
	         } 
		});
	}
	
	//添加流水
	function addTurnOverOne(){
		 $('#addTurnOver').bootstrapValidator('validate');
		var bootstrapValidator = $("#addTurnOver").data('bootstrapValidator');
		
		if(bootstrapValidator.isValid()){
			$.ajax({
				cache : false,
				type : "POST",
				url : '${base}/admin/turnover/add.html',
				data : $('#addTurnOver').serialize(),// 你的formid
				error : function(request) {
					layer.msg('添加失败!');
				},
				success : function(data) {
					layer.load(1, {
						 shade: [0.1,'#fff'] //0.1透明度的白色背景
					});
					formValidator();
					 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				    parent.layer.close(index);
				    window.parent.successCallback('1'); 
					
				    
				}
			});
		}
	};
	
	
	
	
	
	
	
	//根据银行卡名称查找对应的卡号
		$(findBankNum());	
		function findBankNum(){
			
		var text=$('#selectedBankName option:selected').text();//选中的文本
		 $.ajax({
			cache : true,
			type : "POST",
			url : '${base}/admin/turnover/selectCardNum.html',
			data : {
				bankName:text
			},
			success : function(data) {
				var str = '';
				for(var i=0;i< data.length;i++){
					
					str += '<option value="'+data[i].cardNum+'">'+data[i].cardNum+'</option>';
				}
				document.getElementById("selectedBankNum").innerHTML=str;
				formValidator();
			},
			error : function(request) {
				
			}
		});
	}
	
		//提交时开始验证
		$('#submit').click(function() {
		    $('#addTurnOver').bootstrapValidator('validate');
		});
		/* 控制单位的隐藏和金额 */
		$(check());
		function check(){
			var text=$("#purpose").val();
			if(text=="支出"){
				$("#div1").show();
			}else if(text=="收入"){
				$("#div1").hide();
			}
			// $("#div1").hide();
			 //$("#div1").show();
			$('#money').val("");
			
		}
		/* 设置公司的下拉框 */
		//initAddSelect2();
		//function initAddSelect2(){
			/* $("#findCompany").select2({
				ajax : {
					url : BASE_PATH + "/admin/turnover/selectCompanys.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							aircom : params.term, // search term
							page : params.page,
							companyName:$('#companyName').val()
							
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						var selectdata = $.map(data, function (obj) {
							obj.id =  obj.comName; // replace pk with your identifier
							obj.text =  obj.comName; // replace pk with your identifier
							return obj;
						});
						return {
							results : selectdata
						};
					},
					cache : false
				},
				
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", //设置 提示语言
				maximumSelectionLength : 1, //设置最多可以选择多少项
				tags : false //设置必须存在的选项 才能选中
			}); */
			$("#findCompany").select2({
				ajax : {
					url : BASE_PATH + "/admin/turnover/selectCompanys.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							p : params.term, // search term
							companyName:$("#companyNameId").val(),
							page : params.page
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;

						return {
							results : data
						};
					},
					cache : false
				},
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", //设置 提示语言
				maximumSelectionLength : 1, //设置最多可以选择多少项
				tags : false, //设置必须存在的选项 才能选中
			});
		//}
		/* $(editInput());
		function editInput() {
			var opt = $("#findCompany").html();
			//代理商公司ID
			var selectedcompanyId = $("#findCompany").select2("val");
			$("#companyName").val(selectedcompanyId);
			//公司名称
			var selectedcompanyName = $('#findCompany').find("option:selected").text();
			$("#companyName").val(selectedcompanyName);
			//$("#comName").val(selectedcompanyName);
		} */
		function checkLength(obj){
			obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
		    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
		    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$","."); 
		    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数  
		    if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额 
		        obj.value= parseFloat(obj.value); 
		    } 
			
		}
	</script>
</body>
</html>	
	
	
	
	
	
	
	
	
	
	
	
