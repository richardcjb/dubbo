<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>添加银行卡</title>
	<link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
    <%-- <link rel="stylesheet" href="${base }/public/dist/css/bankcardManage.css"> --%>
	<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
	<%-- <link rel="stylesheet" href="${base}/public/dist/css/user.css"> --%>
	<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
	<link rel="stylesheet" href="${base}/public/dist/css/bankcardManage.css"><!-- 本页面style -->
</head>
<body>
	<div class="modal-top">
	<form id="addBankCard" method="post">
	
	    	<div class="modal-header boderButt">
	            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">取消</button>
	            <input type="submit" id="submit" class="btn btn-primary right btn-sm" value="保存"/>
	            <!-- <button type="button" class="btn right btn-sm">删除</button> -->
	            <h4>添加银行卡</h4>
	          </div>
	          <div class="modal-body" style="height:252px; overflow-y: auto;">
	            <div class="tab-content backcard">
	            	<div class="row">
		                <div class="form-group"><!--银行卡名称/卡号/类型-->
			                  <label class="col-sm-2 text-right padding">银行卡名称：</label>
			                  <div class="col-sm-2 padding">
			                    <input name="cardName" type="text" class="form-control input-sm" onblur="formValidator();"/>
			                  </div>
			            </div>
			            <div class="form-group form-group1">
		                  <label class="col-sm-1 text-right padding">卡号：</label>
		                  <div class="col-sm-3 padding">
		                    <input name="cardNum" type="text" class="form-control input-sm" id="cardNum" onkeyup="this.value=this.value.replace(/\s/g,'').replace(/....(?!$)/g,'$& ')" maxlength="32" />
		                  </div>
		                </div>
		             
		                <div class="form-group form-group1">
		                  <label class="col-sm-1 text-right padding">类型：</label>
		                  <div class="col-sm-2 padding">
		                    <select class="form-control input-sm" name="bankCardType" id="findBankCardType">
		                    	<option value="">请选择</option>
		                      <c:forEach items="${obj.bankCardTypeList }" var="each">
		               				<option value="${each.dictName }">${each.dictName }</option>
		               			</c:forEach>
		                    </select>
		                  </div>
		               </div>   
		                </div>
	                </div>
	                <!--end 银行卡名称/卡号/类型-->
					
	                <!-- <div class="form-group"> --><!--银行/币种-->
		                <div class="row"> 
			                <div class="form-group">
			                
			                  <label class="col-sm-2 text-right padding">银行：</label>
			                  <div class="col-sm-2 padding">
			                    <%-- <select id="findBank" class="form-control input-sm" onchange="selectBankName();" name="bankName">
			               			<option value="">请选择</option>

			               			<c:forEach items="${obj.bankList }" var="each">
			               				<option value="${each.dictName }">${each.dictName }</option>
			               			</c:forEach>
			                    </select> --%>
			                    <select id="findBank" name="findBank" onchange="setSelectedAreaIds()" class="form-control select2 inpImpWid" multiple="multiple" ></select>
				               	<input name="bankName" id="bankNameId"  type="hidden" placeholder="单位名称" />
			                  </div>
			                </div>
			                <!-- 设置已选中的项 -->
							<script type="text/javascript">
								function setSelectedAreaIds() {
									var _selectedAreaIds = $("#findBank").select2("val");
									
									$("#bankNameId").val(_selectedAreaIds);
								}
							</script>
			                <div class="form-group form-group1">
			                  <label class="col-sm-1 text-right padding">币种：</label>
			                  <div class="col-sm-3 padding">
			                    <select class="form-control input-sm" name="currency" id="findCurrency">
			                     	<option value="">请选择</option>
			                      <c:forEach items="${obj.moneyTypeList }" var="each">
			               				<option value="${each.dictCode }">${each.dictCode }</option>
			               			</c:forEach>
			                    </select>
			                  </div>
			                  </div>
			                  <div class="form-group form-group1">
			                 <label class="col-sm-1 text-right padding">初始金额：</label>
			                  <div class="col-sm-2 padding">
			                    <input type="text" class="form-control input-sm" name="balance" oninput="checkLength(this);">
			                  </div>
			                  </div>
		                  </div>
	               <!--  </div> --><!--end 银行/币种/初始金额-->
	                
	                <div class="form-group row"><!--备注-->
	                  <label class="col-sm-2 text-right padding">备注：</label>
	                  <div class="col-sm-9 padding">
	                    <textarea class="form-control input-sm textareaHeight" name="remark"></textarea>
	                  </div>
	                </div><!--end 备注-->
	            </div>
	          <!-- </div> -->
		</div>
	</form>
   <!--JS 文件-->
	<script src="${base }/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base }/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
	<script src="${base }/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base }/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base }/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
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
	/*  !function () {
	        $('#cardNum').on('keyup',function(){
	            var $this = $(this),
	                v = $this.val();
	            /\S{5}/.test(v) && $this.val(v.replace(/\s/g,'').replace(/(.{4})/g, "$1 "));
	        });
	    }();	 */ 
  /*      $(function() {

           $('#cardNum').on('keyup', function(e) {
            //只对输入数字时进行处理
              if((e.which >= 48 && e.which <= 57) ||
                      (e.which >= 96 && e.which <= 105 )||(e.which >= 78 && e.which <= 90 )){
                   //获取当前光标的位置
                   var caret = this.selectionStart;
                   //获取当前的value
                   var value = this.value;
                   //从左边沿到坐标之间的空格数
                   var sp =  (value.slice(0, caret).match(/\s/g) || []).length;
                   //去掉所有空格
                  var nospace = value.replace(/\s/g, '');
                  //重新插入空格
                  var curVal = this.value = nospace.replace(/([0-9a-zA-Z]{4})/g, "$1 ").trim();
                  //从左边沿到原坐标之间的空格数
                  var curSp = (curVal.slice(0, caret).match(/\s/g) || []).length;
                 //修正光标位置
                this.selectionEnd = this.selectionStart = caret + curSp - sp;

               }
           })
       });
        */
  
       
	
	
	
	
	
	//表单验证
	function formValidator(){
		$('#addBankCard').bootstrapValidator({
			message: '验证不通过!',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	cardName: {
	                validators: {
	                    notEmpty: {
	                        message: '银行卡名不能为空!'
	                    },
	                     stringLength: {/*长度提示*/
	                   	    min: 1,
	                   	    max: 8,
	                   	    message: '银行卡名长度不得超出8个汉字!'
	                   	  }
	                }
	            },
	            bankCardType: {
	            	validators: {
	            		notEmpty: {
	                        message: '请选择银行卡类型!'
	                    }
	                }
	            },
	            findBank: {
	            	validators: {
	            		notEmpty: {
	                        message: '银行不能为空!'
	                    }
	                }
	            },
	            currency: {
	            	validators: {
	                    notEmpty: {
	                        message: '请选择币种!'
	                    }
	                }
	            },
	            balance: {
	            	validators: {
	                    notEmpty: {
	                        message: '金额不能为空!'
	                    },
	                    regexp: {
	                	 	regexp: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
	                        message: '金额必须为数字!'
	                    }
	                }
	            },
	            cardNum: {
	            	validators: {
	                    notEmpty: {
	                        message: '银行卡号不能为空!'
	                    },
	                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
	                         url: '${base}/admin/bankcard/checkBankCardNumExist.html',//验证地址
	                         message: '银行卡号已存在，请重新输入!',//提示消息
	                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
	                         type: 'POST',//请求方式
	                         //自定义提交数据，默认值提交当前input value
	                         data: function(validator) {
	                            return {
	                            	cardNum:$('input[name="cardNum"]').val()
	                            };
	                         }
	                     }
	                }
	            }
	        }
		});
	}
	
	//添加成功提示
	$("#submit").click(function() {
		 $('#addBankCard').bootstrapValidator('validate');
		var bootstrapValidator = $("#addBankCard").data('bootstrapValidator'); 
		if(bootstrapValidator.isValid()){
			$.ajax({
				cache : false,
				type : "POST",
				url : '${base}/admin/bankcard/add.html',
				data : $('#addBankCard').serialize(),// 你的formid
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
	});
	//提交时开始验证
	/* $('#submit').click(function() {
	    $('#addBankCard').bootstrapValidator('validate');
	    //checkCardNum();
	}); */
	
	function checkCardNum(){
		

		$('#addBankCard').bootstrapValidator({
			message: '验证不通过!',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	cardNum: {
	            	validators: {
	                    notEmpty: {
	                        message: '银行卡号不能为空!'
	                    } ,
	                    stringLength: {/*长度提示*/
	                   	    min: 19,
	                   	    max: 23,
	                   	    message: '银行卡号长度为19-23!'
	                   	  } ,
	                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
	                         url: '${base}/admin/bankcard/checkBankCardNumExist.html',//验证地址
	                         message: '银行卡号已存在，请重新输入!',//提示消息
	                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
	                         type: 'POST',//请求方式
	                         //自定义提交数据，默认值提交当前input value
	                         data: function(validator) {
	                            return {
	                            	cardNum:$('input[name="cardNum"]').val()
	                            };
	                         }
	                     }
	                }
	            }
	        	
	        }
		});
	}
		
	//银行名称select2
		$("#findBank").select2({
				ajax : {
					url : BASE_PATH + "/admin/bankcard/selectBankCardNames.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							p : params.term, // search term
							companyName:$("#bankNameId").val(),
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
	
	
	
	
	
	
	
	
	
	
	
