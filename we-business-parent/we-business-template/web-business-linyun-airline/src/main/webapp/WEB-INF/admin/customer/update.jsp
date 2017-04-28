<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<!DOCTYPE HTML>
<html lang="en-US" id="updateHtml">
<head>
	<meta charset="UTF-8">
	<title>更新</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/public/dist/css/customer.css">
	<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css" />
	<link href="${base }/public/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		.select2-container {width: 95.5% !important;display: inline-block;}
		.seleSpanWid .select2-container {width: 98.5% !important;display: inline-block;}
		.modal-content{box-shadow: 0 0 0 #fff;}
	</style>
</head>
<body>
	<div class="modal-content">
		<form id="customerUpdateForm">
			<div class="modal-header">
				<button type="button" onclick="closeWindow()" class="btn btn-primary right btn-sm" data-dismiss="modal">返回</button>
				<input id="updateBtn" type="button" class="btn btn-primary right btn-sm" value="保存" onclick="updateCustomerInfo();" />
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tabs_1" data-toggle="tab">基本信息</a></li>
					<li><a href="#tabs_2" data-toggle="tab">线路权限</a></li>
					<li><a href="#tabs_3" data-toggle="tab">附件管理</a></li>
					<li><a href="#tabs_4" data-toggle="tab">业务范围</a></li>
					<li><a href="#tabs_5" data-toggle="tab">财务信息</a></li>
				</ul>
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<div class="tab-pane active" id="tabs_1">
						<!--基本信息-->
						<div class="row">
							<div class="form-group">
								<input name="id" type="hidden" value="${obj.customer.id }">
								<label class="col-sm-3 text-right padding">公司名称：</label>
								<div id="comDiv" class="col-sm-7 padding inpNone seleSpanWid">
									<select id="companyId" name="companyId"  onchange="editInput()" class="form-control select2 inpImpWid" multiple="multiple"  data-placeholder="请输入公司名称">
										<option></option>
										<c:forEach var="one" items="${obj.comEntity }">
											<option value="${one.id }">${one.text}</option>
										</c:forEach>
									</select><span class="prompt">*</span>
									<!-- 公司ID -->
									<input id="agentId" type="hidden" name="agentId" />
									<!-- 公司名称 -->
									<input id="comName" type="hidden" name="name" />
									<!-- 公司类型 -->
									<input id="comType" type="hidden" value="${obj.comType}" name="comType" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group">
								<label class="col-sm-3 text-right padding">公司简称：</label>
								<div id="shortNameDiv" class="col-sm-3 padding">
									<input id="shortName" name="shortName" type="tel" class="form-control input-sm inpImportant"
										value="${obj.customer.shortName}" placeholder="请输入公司简称" /><span class="prompt">*</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-1 text-right padding">负责人：</label>
								<div class="col-sm-3 padding">
									<!-- 负责人下拉列表 -->
									<select id="agent" name="responsibleId" class="form-control input-sm inpImportant">
										<c:forEach var="one" items="${obj.userlist }">
											<option value="${one.id }" <c:if test="${one.id eq obj.customer.responsibleId}">selected</c:if>>${one.fullName}</option>
										</c:forEach>
									</select><span class="prompt">*</span>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group">
								<label class="col-sm-3 text-right padding">联系人：</label>
								<div class="col-sm-3 padding">
									<input id="linkMan" name="linkMan" type="tel" class="form-control input-sm inpImportant"
										value="${obj.customer.linkMan}" placeholder="请输入联系人" /><span class="prompt">*</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-1 text-right padding">联系电话：</label>
								<div id="phoneDiv" class="col-sm-3 padding">
									<input id="telephone" name="telephone" type="tel" class="form-control input-sm inpImportant"
										value="${obj.customer.telephone}" placeholder="请输入联系电话" /><span class="prompt">*</span>
								</div>
							</div>
						</div>
						<div class="row sitefax">
							<label class="col-sm-3 text-right padding">网址：</label>
							<div class="col-sm-3 padding">
								<input name="siteUrl" type="tel" class="form-control input-sm inpImportant"
									value="${obj.customer.siteUrl}" placeholder="请输入网址" />
							</div>
							<div class="form-group fax">
								<label class="col-sm-1 text-right padding">传真：</label>
								<div class="col-sm-3 padding">
									<input name="fax" type="tel" class="form-control input-sm inpImportant"
										value="${obj.customer.fax}" placeholder="请输入传真" />
								</div>
							</div>
						</div>
						<div class="row address">
							<div class="form-group">
								<label class="col-sm-3 text-right padding">地址：</label>
								<div class="col-sm-7 padding">
									<input id="address" name="address" type="tel" class="form-control input-sm inpImpWid"
										value="${obj.customer.address}" placeholder="请输入详细地址" /><span class="prompt">*</span>
								</div>
							</div>
						</div>
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">旅行社类型：</label>
							<div class="col-sm-3 padding">
								<select id="travelType" name="travelType"
									class="form-control input-sm inpImportant">
									<option value="1"
										<c:if test="${'1' eq obj.customer.travelType}">selected</c:if>>出境社</option>
									<option value="2"
										<c:if test="${'2' eq obj.customer.travelType}">selected</c:if>>国内社</option>
									<option value="3"
										<c:if test="${'3' eq obj.customer.travelType}">selected</c:if>>综合</option>
								</select>
							</div>
							<label class="col-sm-1 text-right padding">是否禁用：</label>
							<div class="col-sm-3 padding">
								<select id="forbid" name="forbid" class="form-control input-sm inpImportant">
									<option value="0"
										<c:if test="${'0' eq obj.customer.forbid}">selected</c:if>>否</option>
									<option value="1"
										<c:if test="${'1' eq obj.customer.forbid}">selected</c:if>>是</option>
								</select>
							</div>
						</div>
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">出发城市：</label>
							
							<div class="col-sm-7 padding seleSpanWid">
								<select id="city" class="form-control select2" onchange="cityOpt()"  multiple="multiple"  data-placeholder="请输入出发城市">
									<option></option>
									<c:forEach var="one" items="${obj.outcitylist }">
										<option value="${one.id }">${one.text}</option>
									</c:forEach>
								</select>
								<!-- 出发城市ID -->
								<input id="outcity" type="hidden" name="outcityname"/>
							</div>
						</div>
					</div>
					<div class="tab-pane" id="tabs_2">
						<!--路线权限-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">国境内陆：</label>
							<div class="col-sm-3 padding">
								<%-- <select id="isLine" class="form-control select2 inpImportant" onchange="inLine()"  multiple="multiple"  data-placeholder="请输入国境内陆">
									<option></option>
									<c:forEach var="one" items="${obj.innerlinelist }">
										<option value="${one.id }">${one.text}</option>
									</c:forEach>
								</select>
								<!-- 国境内陆ID -->
								<input id="sLine1ID" type="hidden" name="sLine1"/> --%>
								<input id="inlandLine" name="inlandLine" value="${obj.customer.inlandLine}" type="text" class="form-control input-sm inpImportant" placeholder="请输入国境内陆" />
							</div>

							<label class="col-sm-1 text-right padding">国际：</label>
							<div class="col-sm-3 padding">
								<%-- <select id="sLine2ID" class="form-control select2 inpImportant" onchange="outLine()" multiple="multiple"  data-placeholder="请输入国际线路">
									<option></option>
									<c:forEach var="one" items="${obj.interlinelist }">
										<option value="${one.id }">${one.text}</option>
									</c:forEach>
								</select>
								<!-- 国际线路ID -->
								<input id="line2ID" type="hidden" name="internationLine" /> --%>
								<input id="interLine" name="interLine" value="${obj.customer.interLine}" type="text" class="form-control input-sm" placeholder="请输入国际线路" />
							</div>
						</div>
					</div>
					<div class="tab-pane" id="tabs_3">
						<!--附件管理-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">附件列表：</label>
							<div class="col-sm-3 padding">
								<input type="file" name="fileID" id="uploadify" />
								<input type="hidden" name="appendix" id="appendix" />
								<input type="hidden" id="fileUrl">
								<input type="hidden" id="appendixName" name="appendixName">
								<span id="completeFileName">
									<c:if test="${not empty obj.customer.appendixName}">
										<div>
											<a id='downloadA' href='${base}/admin/airlinepolicy/download.html?url=${obj.customer.appendix}&fileName=${obj.customer.appendixName}'>
		                                		${obj.customer.appendixName}
			                                </a>
			                                &nbsp;&nbsp;<span>上传成功</span>&nbsp;&nbsp;&nbsp;&nbsp;
			                                <input type='button' class='delete' onclick='deleteFile();' value='删除'>
		                                </div>
									</c:if>
								</span>
							</div>
						</div>
					</div>
					<div class="tab-pane" id="tabs_4">
						<!--业务范围-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">业务范围：</label>
							<div class="col-sm-7 padding">
								<textarea name="business" class="form-control textar-hei" >${obj.customer.business }</textarea>
							</div>
						</div>
					</div>
					<div class="tab-pane tab-pane1" id="tabs_5">
						<!--财务信息-->
						<div class="form-group row">
							<label class="col-sm-2 text-right padding">签约状态：</label>
							<div class="col-sm-2 padding">
								<select id="contract" name="contract"
									class="form-control input-sm">
									<option value="0"
										<c:if test="${'0' eq obj.customer.contract}">selected</c:if>>未签约</option>
									<option value="1"
										<c:if test="${'1' eq obj.customer.contract}">selected</c:if>>已签约</option>
									<option value="2"
										<c:if test="${'2' eq obj.customer.contract}">selected</c:if>>禁止合作</option>
								</select>
							</div>

							<label class="col-sm-1 text-right padding">合作时间：</label>
							<div class="col-sm-5 padding">
								<input id="datepicker1" name="contractTimeString" type="text"
									class="form-control input-sm input-wid"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'datepicker2\')}'})"
									value="${obj.customer.contractTimeString}"/> 
								至 <input id="datepicker2"
									name="contractDueTimeString" type="text"
									class="form-control input-sm input-wid"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'datepicker1\')}'})"
									value="${obj.customer.contractDueTimeString}"/>
							</div>
						</div>
						<div class="form-group row">
							<label class="col-sm-2 text-right padding">付款方式：</label>
							<div class="col-sm-2 padding">
								<select id="payWay" name="payWay" class="form-control input-sm paySele" onchange="paywaySelect_change(this)">
									<option value="1"
										<c:if test="${'1' eq obj.customer.payWay}">selected</c:if>>现金</option>
									<option value="2"
										<c:if test="${'2' eq obj.customer.payWay}">selected</c:if>>支票</option>
									<option value="3"
										<c:if test="${'3' eq obj.customer.payWay}">selected</c:if>>银行汇款</option>
									<option value="4"
										<c:if test="${'4' eq obj.customer.payWay}">selected</c:if>>第三方</option>
									<option value="5"
										<c:if test="${'5' eq obj.customer.payWay}">selected</c:if>>其他</option>
								</select>
							</div>
							<div class="col-sm-8" style="display: none;" id="paywayDivId">
								<div class="col-sm-12 padding payInp">
									<input type="text" id="paywayId" name="paywayName" value="${obj.customer.paywayName}" class="paytext form-control input-sm" placeholder="请输入付款方式">
								</div>
							</div>
						</div>
						<div class="form-group row">
							<label class="col-sm-2 text-right padding">结算方式：</label>
							<div class="col-sm-2 padding">
								<select id="payType" name="payType"
								class="form-control input-sm sele"
									onchange="paytypeSelect_change(this)">
									<option value="1"
										<c:if test="${'1' eq obj.customer.payType}">selected</c:if>>月结</option>
									<option value="2"
										<c:if test="${'2' eq obj.customer.payType}">selected</c:if>>周结</option>
									<option value="3"
										<c:if test="${'3' eq obj.customer.payType}">selected</c:if>>单结</option>
									<option value="4"
										<c:if test="${'4' eq obj.customer.payType}">selected</c:if>>其他</option>
								</select>
							</div>
							<div class="col-sm-8" style="display: none;" id="paytypeDivId">
								<div class="col-sm-12 padding inpAdd">
									<input type="text" id="paytypeName" name="paytypeName" value="${obj.customer.paytypeName}"  class="paytext form-control input-sm" placeholder="请输入结算方式">
								</div>
							</div>
						</div>
						<div class="form-group row">
							<label class="col-sm-2 text-right padding">提供发票：</label>
							<div class="col-sm-2 padding">
								<select id="invoiceId" name="invoice"
									class="form-control input-sm" onchange="gaveInvioce()">
									<option value="0"
										<c:if test="${0 eq obj.customer.invoice}">selected</c:if>>否</option>
									<option value="1"
										<c:if test="${1 eq obj.customer.invoice}">selected</c:if>>是</option>
								</select>
							</div>
							<!-- 发票项  -->
							<div class="col-sm-8" style="display: none;" id="invioceTypeId">
								<div class="col-sm-12 padding">
									<select id="sInvID" class="form-control select2" onchange="sInvioce()" multiple="multiple" data-placeholder="请输入发票项">
										<option></option>
										<c:forEach var="one" items="${obj.invoicelist }">
											<option value="${one.id }">${one.text}</option>
										</c:forEach>
									</select>
									<!-- 发票项ID -->
									<input id="sInvName" type="hidden" name="sInvName" />
								</div>
							</div>
						</div>
						
<!--------------------------------------------新编辑 内容   财务信息 start-------------------------------------------------->
						<div class="row">
							<div class="form-group">
								<label class="col-sm-2 text-right padding">信用额度：</label>
	                            <div class="col-sm-2 padding">
	                              <input name="creditLine" value="<fmt:formatNumber type="number" value="${obj.customer.creditLine}" pattern="0.00" maxFractionDigits="2"/>" type="text" class="form-control input-sm" onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"
										onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]">
	                            </div>
							</div>
                            <label class="col-sm-1 text-right padding">已欠款：</label>
                            <div class="col-sm-2 padding">
                            	<input name="arrears" value="<fmt:formatNumber type="number" value="${obj.customer.arrears}" pattern="0.00" maxFractionDigits="2"/>" type="text" readonly="readonly" class="form-control input-sm" onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"
										onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]">
                            </div>
							<div class="form-group">
                          		 <label class="col-sm-1 text-right padding">预收款：</label>
	                            <div class="col-sm-1 padding">
	                              <input name="preDeposit" value="<fmt:formatNumber type="number" value="${obj.customer.preDeposit}" pattern="0.00" maxFractionDigits="2"/>" type="text" class="form-control input-sm" onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"
										onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]">
	                            </div>
                          	</div>
                        </div>
						<div class="row">
							<div class="form-group">
								<label class="col-sm-2 text-right padding">票价折扣：</label>
	                            <div class="col-sm-2 padding">
	                              <input name="discountFare" value="<fmt:formatNumber type="number" value="${obj.customer.discountFare}" pattern="0.00" maxFractionDigits="2"/>" type="text" class="form-control input-sm discountText" onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"
										onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]">
	                              <span>%</span>
	                            </div>
							</div>
                            <div class="form-group">
                            	<label class="col-sm-1 text-right padding">手续费：</label>
	                            <div class="col-sm-2 padding">
	                            	<input name="fees" value="<fmt:formatNumber type="number" value="${obj.customer.fees}" pattern="0.00" maxFractionDigits="2"/>" type="text" class="form-control input-sm discountText" placeholder="每张票" onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"
										onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]">
	                            	<span>￥</span>
	                            </div>
                          	</div>
							<div class="form-group">
								<label class="col-sm-1 text-right padding">汇率：</label>
	                            <div class="col-sm-1 padding">
	                              <input name="exchangeRates" value="<fmt:formatNumber type="number" value="${obj.customer.exchangeRates}" pattern="0.00" maxFractionDigits="2"/>" type="text" class="form-control input-sm" onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"
										onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]">
	                            </div>
							</div>
							<div class="form-group">
								<label class="col-sm-1 text-right padding">退税：</label>
	                            <div class="col-sm-2 padding">
	                              <input name="taxRefund" value="<fmt:formatNumber type="number" value="${obj.customer.taxRefund}" pattern="0.00" maxFractionDigits="2"/>" type="text" class="form-control input-sm taxText" placeholder="每张票" onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"
										onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]">
	                            </div>
							</div>
                        </div>
<!--------------------------------------------新编辑 内容   财务信息  end-------------------------------------------------->
						
					</div>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<!-- jQuery 2.2.3 -->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- 时间控件 -->
	<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
	<!-- Select2 -->
	<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
	<script src="${base}/public/plugins/iCheck/icheck.min.js"></script>
	<!-- FastClick 快 点击-->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
	<!-- 文件上传 -->
	<link href="${base }/public/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css" />  
	<script type="text/javascript" src="${base }/public/plugins/uploadify/jquery.uploadify.min.js"></script>
	
	<!-- 页面js -->
	<script src="${base}/admin/customer/baseinfo.js"></script>
	<script src="${base}/admin/customer/line.js"></script>
	<script src="${base}/admin/customer/upload.js"></script>
	<script src="${base}/admin/customer/caiwu.js"></script>
	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			
			$.fileupload1 = $('#uploadify').uploadify({
	            'auto' : true,
	            'formData' : {
	            	'fcharset' : 'uft-8',
	                'action' : 'uploadimage'
	            },
	            'buttonText': '上传',
	            'fileSizeLimit' : '3000MB',
	            'fileTypeDesc' : '文件',
	            'fileTypeExts' : '*.png; *.txt; *.doc; *.pdf; *.xls; *.jpg; *.docx; *.xlsx;',//文件类型过滤
	            'swf'      : '${base}/public/plugins/uploadify/uploadify.swf',
	            'multi':false,
	            'successTimeout':1800,
	            'queueSizeLimit':100,
	            'uploader' : '${base}/admin/customer/uploadFile.html',
	            'onUploadStart' : function(file) {
					$("#updateBtn").attr('disabled',true);
				},
	            'onUploadSuccess':function(file,data,response){
	            	$("#completeFileName").html("");
					var jsonobj = eval('(' + data + ')');
					$('#appendix').val(jsonobj);
					$("#fileUrl").val(jsonobj);
					$("#appendixName").val(file.name);
					var innerHtml = "";
                    if (response) {
                    	innerHtml = "<div><a id='downloadA' href='${base}/admin/airlinepolicy/download.html?url="+jsonobj+"&fileName="+file.name+"'>"
                                + file.name
                                + "</a>&nbsp;&nbsp;<span>上传成功</span>&nbsp;&nbsp;&nbsp;&nbsp;"
                                + "<input type='button' class='delete' onclick='deleteFile();' value='删除'><input type='hidden' name='${attachIds}' value='"
                                + jsonobj + "'></div>";
                    } else {
                        innerHtml = "<div>该附件上传失败，请重新上传</div>";
                    }
                    $("#completeFileName").html($("#completeFileName").html() + innerHtml);
                    $("#updateBtn").attr('disabled',false);
	            },
                //加上此句会重写onSelectError方法【需要重写的事件】
                'overrideEvents': ['onSelectError', 'onDialogClose'],
                //返回一个错误，选择文件的时候触发
                'onSelectError':function(file, errorCode, errorMsg){
                    switch(errorCode) {
                        case -110:
                            alert("文件 ["+file.name+"] 大小超出系统限制！");
                            break;
                        case -120:
                            alert("文件 ["+file.name+"] 大小异常！");
                            break;
                        case -130:
                            alert("文件 ["+file.name+"] 类型不正确！");
                            break;
                    }
                },
				onError: function(event, queueID, fileObj) {　
					$("#updateBtn").attr('disabled',false);
		        }
	        });
			
			//页面加载时 执行
			angentList();
			
			//页面加载 显示文本框
			if($('#payWay option:selected').val() == "5"){
				$("#paywayDivId").css('display','block'); 
			}
			if($('#payType option:selected').val() == "4"){
				$("#paytypeDivId").css('display','block'); 
			}
			if($('#invoiceId option:selected').val() == "1"){
				$("#invioceTypeId").css('display','block'); 
			}
			
			
			//公司名称回显companyId
			var _comSelect = $("#companyId").select2({
				ajax : {
					url : BASE_PATH  + "/admin/customer/company.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							q : params.term, 
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
			_comSelect.val([${obj.comIds}]).trigger("change");
			
			//出发城市回显
			var _citySelect = $("#city").select2({
				ajax : {
					url : BASE_PATH  + "/admin/customer/goCity.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							q : params.term, // search term
							ids:$('#outcity').val(),
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
				maximumSelectionLength : 5, //设置最多可以选择多少项
				tags : false, //设置必须存在的选项 才能选中
			});
			_citySelect.val([${obj.outcityIds}]).trigger("change");
			
			//国境内陆回显
			var _innerLineSelect = $("#isLine").select2({
				ajax : {
					url : BASE_PATH  + "/admin/customer/isLine.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							q : params.term, // search term
							ids:$('#sLine1ID').val(),
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
				maximumSelectionLength : 5, //设置最多可以选择多少项
				tags : false, //设置必须存在的选项 才能选中
			});
			_innerLineSelect.val([${obj.innerCityIds}]).trigger("change");
			
			//国际线路回显
			var _interLineSelect = $("#sLine2ID").select2({
				ajax : {
					url : BASE_PATH  + "/admin/customer/international.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							q : params.term, // search term
							ids:$('#line2ID').val(),
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
				maximumSelectionLength : 5, //设置最多可以选择多少项
				tags : false, //设置必须存在的选项 才能选中
			});
			_interLineSelect.val([${obj.interLineIds}]).trigger("change");
			
			//发票项回显
			var _invioceSelect = $("#sInvID").select2({
				ajax : {
					url : BASE_PATH  + "/admin/customer/isInvioce.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							q : params.term, // search term
							ids:$('#sInvName').val(),
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
				maximumSelectionLength : 5, //设置最多可以选择多少项
				tags : false, //设置必须存在的选项 才能选中
			});
			_invioceSelect.val([${obj.invioceIds}]).trigger("change");
			
			
			

		});
		function initvalidate(){
			//校验
			$('#customerUpdateForm').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					companyId : {
						validators : {
							notEmpty : {
								message : '公司名称不能为空'
							}/* ,
		                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
		                         url: '${base}/admin/customer/checkComNameExist.html',//验证地址
		                         message: '公司名称已存在，请重新输入!',//提示消息
		                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
		                         type: 'POST',//请求方式
		                         //自定义提交数据，默认值提交当前input value
		                         data: function(validator) {
		                            return {
		                            	name:$('#companyId').find("option:selected").val(),
		                            	cid:'${obj.customer.id}'
		                            };
		                         }
		                     } */
						}
					},
					shortName : {
						validators : {
							notEmpty : {
								message : '公司简称不能为空'
							},
							regexp : {
								regexp : /^[0-9a-zA-Z\u4e00-\u9fa5]{1,6}$/,
								message : '公司简称长度为6',
							}/* ,
							remote : {
								url : '${base}/admin/customer/checkShortNameExist.html',
								message : '公司简称已存在，请重新输入!',
								delay : 2000,
								type : 'POST',
								data : function(validator) {
									return {
										shortName : $('#shortName').val(),
										aId : '${obj.customer.id}'
									};
								}
							} */
						}
					},
					linkMan : {
						validators : {
							notEmpty : {
								message : '联系人不能为空'
							}
						}
					},
					telephone : {
						validators : {
							notEmpty : {
								message : '联系电话不能为空'
							},
							/* remote: {
		                         url: '${base}/admin/customer/checkTelephoneExist.html',
		                         message: '联系电话已存在，请重新输入!',
		                         delay :  2000,
		                         type: 'POST',
		                         data: function(validator) {
		                            return {
		                            	telephone:$('#telephone').val(),
		                            	aId:'${obj.customer.id }'
		                            };
		                         }
		                     },  */
							regexp : {
								/* regexp : /^[1][34578][0-9]{9}$/, */
								regexp : /^((0\d{2,3}-\d{7,8})|(1[35874]\d{9}))$/,
								message : '联系电话格式错误'
							}
						}
					},
					address : {
						validators : {
							notEmpty : {
								message : '公司地址不能为空'
							}
						}
					},
					fax:{
						validators : {
							regexp : {
								regexp : /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/,
								message : '传真格式错误'
							}
						}
					}/* ,
					creditLine : {
						validators : {
							regexp : {
								regexp : /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
								message : '信用额度格式错误'
							}
						}
					},
					preDeposit  : {
						validators : {
							regexp : {
								regexp : /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
								message : '预收款格式错误'
							}
						}
					},
					discountFare : {
						validators : {
							regexp : {
								regexp : /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
								message : '票价折扣格式错误'
							}
						}
					},
					fees : {
						validators : {
							regexp : {
								regexp : /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
								message : '手续费格式错误'
							}
						}
					},
					exchangeRates : {
						validators : {
							regexp : {
								regexp : /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
								message : '汇率格式错误'
							}
						}
					},
					taxRefund : {
						validators : {
							regexp : {
								regexp : /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
								message : '退税格式错误'
							}
						}
					} */
				}
			});
		}
		//更新时刷新页面
		function update() {
			window.location.reload();
		}
	</script >
	
	<!-- 下拉列表 -->
	<script type="text/javascript">
	/* 负责人名称 下拉列表*/
	function angentList() {
		$.ajax({
			type : 'POST',
			dataType : 'json',
			url : '${base}/admin/customer/agent.html',
			success : function(data) {
				var content = "";
				for (var i = 0; i < data.length; i++) {
					var name = data[i].name;
					var id = data[i].id;
					content += "<option value='" + id
							+ "' onclick='optAgent(this)'>" + name
							+ "</option>";
				}
				$("#agentID").html(content);
			}
		});
	}
	</script>
	
	<!-- 隐藏 显示 方式 -->
	<script type="text/javascript">
		
		//显示或隐藏发票项
		function gaveInvioce() {
			var s = document.getElementById("invoiceId").value;
			if (s == 1) {
				document.getElementById("invioceTypeId").style.display = "";
			}
			if (s == 0) {
				document.getElementById("invioceTypeId").style.display = "none";
			}
		}
	
		//结算方式 add input
		function paytypeSelect_change(obj) {
			var seleValue = $(".sele").find("option:selected").attr("value");
			if (seleValue == 4) {
				document.getElementById("paytypeDivId").style.display = "block";
			}else{
				document.getElementById("paytypeDivId").style.display = "none";	
				$("#paytypeName").val("");
			}
		}
		//付款方式 add input
		function paywaySelect_change(obj) {
			var payValue = $(".paySele").find("option:selected").attr("value");
			if (payValue == 5) {
				document.getElementById("paywayDivId").style.display = "block";
			}else{
				document.getElementById("paywayDivId").style.display = "none";	
				$("#paywayId").val("");
			}
		}
	</script>
	
	
	<!-- Select2 Onchange事件 -->
	<script type="text/javascript">
		/* 客户公司名称和Id */
		function editInput(){
			//代理商公司ID
			var selectedcompanyId = $("#companyId").select2("val");
			$("#agentId").val(selectedcompanyId);
			//公司名称
			var selectedcompanyName = $('#companyId').find("option:selected").text();
			$("#comName").val(selectedcompanyName);
			//公司类型
			var res = $("#companyId").select2("data");
			$("#comType").val(res[0].comType);
		}
		/* 出发城市 */
		function cityOpt(){
			//出发城市Id
			var selectedCityId = $("#city").select2("val");
			$("#outcity").val(selectedCityId);
		}
		/* 国内线路 */
		function inLine(){
			//国内线路Id
			var selectedisLine = $("#isLine").select2("val");
			$("#sLine1ID").val(selectedisLine);
		}
		/* 国际线路 */
		function outLine(){
			//国际线路Id
			var selectedsLine2ID = $("#sLine2ID").select2("val");
			$("#line2ID").val(selectedsLine2ID);
		}
		/*发票项*/
		function sInvioce(){
			//发票项Id
			var selectedsInvID = $("#sInvID").select2("val");
			$("#sInvName").val(selectedsInvID);
		}
	 	//提交时开始验证
		/* $('#updateBtn').click(function() {
			$('#customerUpdateForm').bootstrapValidator('validate');
		});  */
	</script>
	
	<!-- 更新 -->
	<script type="text/javascript">
	    initvalidate();
		$('#customerUpdateForm').bootstrapValidator('validate');
		function updateCustomerInfo() {
			$('#customerUpdateForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#customerUpdateForm").data('bootstrapValidator');
			if (bootstrapValidator.isValid()) {
				var selectedcompany = $('#companyId').find("option:selected").text();
				var shortName = $("#shortName").val();
				var linkMan = $("#linkMan").val();
				var telephone = $("#telephone").val();
				var address = $("#address").val();
				if(selectedcompany==""){
					layer.msg('公司名称不能为空');
					return;
				}
				if(shortName==""){
					layer.msg('公司简称不能为空');
					return;
				}
				if(linkMan==""){
					layer.msg('联系人不能为空');
					return;
				}
				if(telephone==""){
					layer.msg('联系电话不能为空');
					return;
				}
				if(address==""){
					layer.msg('地址不能为空');
					return;
				}
				if(!$('small[data-bv-for="companyId"]').attr("style")=='display: none;'){
					var comMsg = $('small[data-bv-for="companyId"]').text();
					comMsg = comMsg.substring(8, comMsg.length);
					if(comMsg != ""){
						layer.msg(comMsg);
						return;
					}
				}
				var small2 = $('#comDiv small:eq(1)').attr("style");
				if(small2 == "" || small2=='display: block;'){
					var comMsg = $('#comDiv small:eq(1)').text();
					if(comMsg != ""){
						layer.msg(comMsg);
						return;
					}
				}
				if(!($('small[data-bv-for="telephone"]').attr("style"))=='display: none;'){
					var phoneMsg = $('small[data-bv-for="telephone"]').text();
					phoneMsg = phoneMsg.substring(8, 22);
					if(phoneMsg != ""){
						layer.msg(phoneMsg);
						return;
					}
				}
				var small2 = $('#phoneDiv small:eq(1)').attr("style");
				if(small2 == "" || small2=='display: block;'){
					var comMsg = $('#phoneDiv small:eq(1)').text();
					if(comMsg != ""){
						layer.msg(comMsg);
						return;
					}
				}
				var small2 = $('#phoneDiv small:eq(2)').attr("style");
				if(small2 == "" || small2=='display: block;'){
					var comMsg = $('#phoneDiv small:eq(2)').text();
					if(comMsg != ""){
						layer.msg(comMsg);
						return;
					}
				}
				var small2 = $('#phoneDiv small:eq(3)').attr("style");
				if(small2 == "" || small2=='display: block;'){
					var comMsg = $('#phoneDiv small:eq(3)').text();
					if(comMsg != ""){
						layer.msg(comMsg);
						return;
					}
				}
				var small2 = $('#shortNameDiv small:eq(1)').attr("style");
				if(small2 == "" || small2=='display: block;'){
					var comMsg = $('#shortNameDiv small:eq(1)').text();
					if(comMsg != ""){
						layer.msg(comMsg);
						return;
					}
				}
				var small2 = $('#shortNameDiv small:eq(2)').attr("style");
				if(small2 == "" || small2=='display: block;'){
					var comMsg = $('#shortNameDiv small:eq(2)').text();
					if(comMsg != ""){
						layer.msg(comMsg);
						return;
					}
				}
				var small2 = $('#shortNameDiv small:eq(3)').attr("style");
				if(small2 == "" || small2=='display: block;'){
					var comMsg = $('#shortNameDiv small:eq(3)').text();
					if(comMsg != ""){
						layer.msg(comMsg);
						return;
					}
				}
				$.ajax({
					type : 'POST',
					data : $("#customerUpdateForm").serialize(),
					url : '${base}/admin/customer/update.html',
					success : function(data) {
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						if ("200" == data.status) {
							layer.close(index);
							window.parent.successCallback('2');
							parent.layer.close(index);
						} else {
							layer.msg("编辑失败","", 3000);
						}
	
					},
					error : function(xhr) {
						layer.msg("编辑失败", "", 3000);
					}
				});
			}
		}
	
		//返回刷新页面 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        	window.parent.successCallback('4');
        	parent.layer.close(index);
		}
		
		//删除上传文件
		function deleteFile(){
			$("#completeFileName").html("");
			$("#appendix").val("");
		}
		//下载文件
		function downloadFile(url) {  
			$("#downloadA").attr("href", url);
	    }
	</script>
	
	
</body>
</html>

