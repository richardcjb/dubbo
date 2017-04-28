<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>减免</title>
	<link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
    <%-- <link rel="stylesheet" href="${base }/public/dist/css/swiftNumber.css"> --%><!--本页style-->
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow()">取消</button>
            <input type="submit" id="submit" class="btn btn-primary right btn-sm" onclick="saveData()" value="提交"/>
            <h4>减免申请</h4>
          </div>
          <div class="modal-body" style="padding:0;">
            <div class="tab-content backcard">
            	<form id="mitigateForm">
                <div class="form-group row">
                	<input type="hidden" id="orderid" name="orderid" value="${obj.orderid }">
                  <label class="col-sm-2 text-right padding">减免对象：</label>
                  <div class="col-sm-5 padding">
                  	<input id="customeid" name="customeid" type="hidden" value="${obj.customeinfo.id }">
                    <input name="customname" type="text" class="form-control input-sm" value="${obj.customeinfo.name }"/>
                  </div>
                  <label class="col-sm-2 text-right padding">用途：</label>
                  <div class="col-sm-2 padding">
                    <input id="application" name="application" type="text" class="form-control input-sm" value="${obj.mitigate.application }"/>
                  </div>
                </div>

                <div class="form-group row">
                  <label class="col-sm-2 text-right padding">金额：</label>
                  <div class="col-sm-2 padding">
                    <input id="account" name="account" type="text" class="form-control input-sm account mustNumberPoint" value="${obj.mitigate.account }"/>
                  </div>
                  <div class="col-sm-3 padding">
                      <input id="accountupper" name="accountupper" type="text" class="form-control input-sm" value="${obj.mitigate.accountupper }" disabled="disabled" />
                  </div>
                  <label class="col-sm-2 text-right padding">币种：</label>
                  <div class="col-sm-2 padding">
                    <select id="currency" name="currency" class="form-control input-sm">
                    	<option value="">请选择</option>
                      <c:forEach items="${obj.bzcode }" var="one"> 
                      	 <c:choose>
                      	 	<c:when test="${one.dictCode eq obj.mitigate.currency}">
			                     <option value="${one.dictCode }" selected="selected">${one.dictCode }</option>
                      	 	</c:when>
                      	 	<c:otherwise>
			                     <option value="${one.dictCode }">${one.dictCode }</option>
                      	 	</c:otherwise>
                      	 </c:choose>
	                  </c:forEach>
                    </select>
                  </div>
                </div><!--end 银行/币种-->

                <div class="form-group row">
                  <label class="col-sm-2 text-right padding">申请人：</label>
                  <div class="col-sm-2 padding"><input id="applyid" name="applyid" type="text" class="form-control input-sm" disabled="disabled" value="${empty obj.mitigate.applyid ? obj.user.fullName : obj.applyuser.fullName }"/></div>
                  <label class="col-sm-1 text-right padding">审批人：</label>
                  <div class="col-sm-2 padding"><input id="approvelid" name="approvelid" type="text" class="form-control input-sm" disabled="disabled" value="候小凌"/></div>
                  <label class="col-sm-2 text-right padding">申请结果：</label>
                  <div class="col-sm-2 padding"><input id="applyResult" name="applyResult" type="text" class="form-control input-sm" disabled="disabled" value="${obj.applyresult }"/></div>
                </div><!--end 银行/币种-->
                </form>
            </div>
          </div>
	</div>
   <!--JS 文件-->
	<script src="${base }/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base }/public/bootstrap/js/bootstrap.min.js"></script>

	<script src="${base }/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base }/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base }/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<!--layer -->
	<script src="${base}/common/js/layer/layer.js"></script>
	<script src="${base }/admin/order/ordercommon.js"></script>
	<script type="text/javascript">
	//金额转换为大写
	function changeNumMoneyToChinese(money) {  
		　　var cnNums = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"); //汉字的数字  
		　　var cnIntRadice = new Array("", "拾", "佰", "仟"); //基本单位  
		　　var cnIntUnits = new Array("", "万", "亿", "兆"); //对应整数部分扩展单位  
		　　var cnDecUnits = new Array("角", "分", "毫", "厘"); //对应小数部分单位  
		　　var cnInteger = "整"; //整数金额时后面跟的字符  
		　　var cnIntLast = "元"; //整型完以后的单位  
		　　var maxNum = 999999999999999.9999; //最大处理的数字  
		　　var IntegerNum; //金额整数部分  
		　　var DecimalNum; //金额小数部分  
		　　var ChineseStr = ""; //输出的中文金额字符串  
		　　var parts; //分离金额后用的数组，预订义  
		　　if (money == "") {  
		　　return "";  
		　　}  
		　　money = parseFloat(money);  
		　　if (money >= maxNum) {  
		　　alert('超出最大处理数字');  
		　　return "";  
		　　}  
		　　if (money == 0) {  
		　　ChineseStr = cnNums[0] + cnIntLast + cnInteger;  
		　　return ChineseStr;  
		　　}  
		　　money = money.toString(); //转换为字符串  
		　　if (money.indexOf(".") == -1) {  
		　　IntegerNum = money;  
		　　DecimalNum = '';  
		　　} else {  
		　　parts = money.split(".");  
		　　IntegerNum = parts[0];  
		　　DecimalNum = parts[1].substr(0, 4);  
		　　}  
		　　if (parseInt(IntegerNum, 10) > 0) { //获取整型部分转换  
		　　var zeroCount = 0;  
		　　var IntLen = IntegerNum.length;  
		　　for (var i = 0; i < IntLen; i++) {  
		　　var n = IntegerNum.substr(i, 1);  
		　　var p = IntLen - i - 1;  
		　　var q = p / 4;  
		　　var m = p % 4;  
		　　if (n == "0") {  
		　　zeroCount++;  
		　　} else {  
		　　if (zeroCount > 0) {  
		　　ChineseStr += cnNums[0];  
		　　}  
		　　zeroCount = 0; //归零  
		　　ChineseStr += cnNums[parseInt(n)] + cnIntRadice[m];  
		　　}  
		　　if (m == 0 && zeroCount < 4) {  
		　　ChineseStr += cnIntUnits[q];  
		　　}  
		　　}  
		　　ChineseStr += cnIntLast;  
		　　//整型部分处理完毕  
		　　}  
		　　if (DecimalNum != '') { //小数部分  
		　　var decLen = DecimalNum.length;  
		　　for (var i = 0; i < decLen; i++) {  
		　　var n = DecimalNum.substr(i, 1);  
		　　if (n != '0') {  
		　　ChineseStr += cnNums[Number(n)] + cnDecUnits[i];  
		　　}  
		　　}  
		　　}  
		　　if (ChineseStr == '') {  
		　　ChineseStr += cnNums[0] + cnIntLast + cnInteger;  
		　　} else if (DecimalNum == '') {  
		　　ChineseStr += cnInteger;  
		　　}  
		　　return ChineseStr;  
		} 
	 $(document).on('input', '.account', function(e) {
	    	$(this).val($(this).val().replace(/[^.\d]/g,''));
	    	var account = $(this).val();
	    	var accountUpper = changeNumMoneyToChinese(account);
	    	if(account){
	    		$('#accountupper').val(accountUpper);
	    	}else{
	    		$('#accountupper').val('');
	    	}
	    });
	 //关闭弹层
	 function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	
	 function saveData(){
		 var accountupper = $('#accountupper').val();
		 $.ajax({ 
			type: 'POST', 
			data: $("#mitigateForm").serialize()+'&accountupper='+accountupper, 
			url: '${base}/admin/inland/saveMitigateData.html',
            success: function (data) { 
            	layer.msg("提交成功",{time: 2000});
            	closewindow();
            	window.parent.successCallback('4');
            },
            error: function (xhr) {
            	layer.msg("提交失败","",3000);
            } 
        });
	 }
	 isShowSave();
	 function isShowSave(){
		 var mitigateid = '${obj.mitigate.id}';
		 if(mitigateid){
			 $('#submit').hide();
		 }
	 }
	</script>
</body>
</html>	