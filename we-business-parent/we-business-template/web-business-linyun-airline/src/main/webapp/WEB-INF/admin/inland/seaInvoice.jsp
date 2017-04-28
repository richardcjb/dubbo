<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>收款</title>
	<link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
	<link href="${base }/public/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="${base }/public/dist/css/inlandCross.css"><!--本页style-->
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow()">取消</button>
            <input type="submit" id="submit" class="btn btn-primary right btn-sm" onclick="commitInvoice();" value="提交"/>
            <h4>收款</h4>
          </div>
          <div class="modal-body" style="height:500px;overflow-y:auto; ">
          	<input type="hidden" id="ids"  name="ids" value="${obj.ids }" >
              <table id="receivablesTable" class="table table-bordered table-hover">
                <thead>
                  <tr>
                    <th>订单号</th>
                    <th>开票日期</th>
                    <th>客户团号</th>
                    <th>客户</th>
                    <th>联系人</th>
                    <th>开票人</th>
                    <th>金额</th>
                  </tr>
                </thead>
                <tbody>
                	<c:forEach var="one" items="${obj.orders }">
                		<tr ondblclick="toOrderDetail(${one.id});">
                			<td>${one.ordersnum }</td>
                			<td><fmt:formatDate value="${one.billingdate }" pattern="yyyy-MM-dd" /></td>
                			<td>${one.cusgroupnum }</td>
                			<td>${one.shortName }</td>
                			<td>${one.linkMan }</td>
                			<td>${one.issuer }</td>
                			<td><fmt:formatNumber type="number" value="${one.incometotal }" pattern="0.00" maxFractionDigits="2"/></td>
                		</tr>
                	</c:forEach>
                </tbody>
              </table>
              <table border="0" class="selectTable">
                <tr>
                  <td><label>银行：</label></td>
                  <td>
                    <select id="bankcardid" name="bankcardid" class="form-control input-sm" onchange="loadbankcardname();">
                    	<option value="">请选择</option>
                        <c:forEach var="one" items="${obj.yhkSelect }">
                        	<option value="${one.id }">${one.dictName }</option>
                        </c:forEach>
                    </select>
                  </td>
                  <td><label>银行卡名称：</label></td>
                  <td>
                    <select id="bankcardname" name="bankcardname" class="form-control input-sm" onchange="loadbankcardnum();">
                    </select>
                  </td>
                  <td><label>卡号：</label></td>
                  <td>
                     <select id="bankcardnum" name="bankcardnum" class="form-control input-sm">
                     </select>
                  </td>
                  <td><label>合计：</label></td>
                  <td id="heji"><fmt:formatNumber type="number" value="${obj.sumincome }" pattern="0.00" maxFractionDigits="2"/></td>
                </tr>
              </table>
              <input type="hidden" id="sumincome" name="sumincome" value="${obj.sumincome }">
              <button type="button" id="uploadFile" class="btn btn-primary btn-sm bankSlipBtn">上传水单</button>
              <input type="hidden" id="billurl" name="billurl" value="">
              <div class="bankSlipImg" align="center"><img id="shuidanimg" alt="" src="" style="width:100%;height:305px;overflow: hidden;"></div>
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
    <script type="text/javascript" src="${base }/public/plugins/uploadify/jquery.uploadify.min.js"></script>
	<script type="text/javascript">
	//关闭窗口
    function closewindow(){
		var index = parent.parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	//提交订单
	function commitInvoice(){
		var ids = $('#ids').val();
		var bankcardid = $('#bankcardid').val();
		var bankcardname = $('#bankcardname').find("option:selected").text();
		var bankcardnameid = $('#bankcardname').val();
		var bankcardnum = $('#bankcardnum').val();
		var billurl = $('#billurl').val();
		var sumincome = $('#sumincome').val();
		if(bankcardnameid){
			$.ajax({
		        type: "post",
		        url: '${base}/admin/inland/saveSeaInvoice.html',
		        data: {ids:ids,bankcardid:bankcardid,bankcardnameid:bankcardnameid,bankcardname:bankcardname,bankcardnum:bankcardnum,billurl:billurl,sumincome:sumincome},
		        cache: false,
		        async : false,
		        success: function (data ,textStatus, jqXHR){
		        	layer.msg("提交成功！",{time: 2000});
		        	closewindow();
		        	window.parent.successCallback('5');
		        },
		        error:function (XMLHttpRequest, textStatus, errorThrown) {      
		            layer.msg("请求失败！",{time: 2000});
		        }
		     });
		}else{
			layer.msg("请选择银行！",{time: 2000});
		}
	}
	
	//文件上传
    	$.fileupload1 = $('#uploadFile').uploadify({
    		'auto' : true,//选择文件后自动上传
    		'formData' : {
    			'fcharset' : 'uft-8',
    			'action' : 'uploadimage'
    		},
    		'buttonText' : '上传水单',//按钮显示的文字
    		'fileSizeLimit' : '3000MB',
    		'fileTypeDesc' : '文件',//在浏览窗口底部的文件类型下拉菜单中显示的文本
    		'fileTypeExts' : '*.png; *.jpg; *.bmp; *.gif; *.jpeg;',//上传文件的类型
    		'swf' : '${base}/public/plugins/uploadify/uploadify.swf',//指定swf文件
    		'multi' : false,//multi设置为true将允许多文件上传
    		'successTimeout' : 1800,
    		'queueSizeLimit' : 100,
    		'uploader' : '${base}/admin/drawback/grabfile/uploadFile.html',//后台处理的页面
    		'onUploadStart' : function(file) {
				$("#submit").attr('disabled',true);
			},
    		//onUploadSuccess为上传完视频之后回调的方法，视频json数据data返回，
    		//下面的例子演示如何获取到vid
    		'onUploadSuccess' : function(file, data, response) {
    			var jsonobj = eval('(' + data + ')');
    			var url  = jsonobj;//地址
    			var fileName = file.name;//文件名称
    			$('#billurl').val(url);
    			$('#shuidanimg').attr('src',url);
    			$("#submit").attr('disabled',false);
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
				$("#submit").attr('disabled',false);
	        }
    	});
	//加载银行卡名称下拉
	function loadbankcardname(){
		var bankcardid = $('#bankcardid').val();
		if(bankcardid){
			$.ajax({
		        type: "post",
		        url: '${base}/admin/inland/loadBankCardNameSelect.html',
		        data: {bankcardid:bankcardid},
		        cache: false,
		        async : false,
		        success: function (data ,textStatus, jqXHR){
		        	var html = '';
		        	$.each(data, function(name, value) {
		        		html += '<option value="'+value.id+'">'+value.cardName+'</option>';
		        	});
		        	$('#bankcardname').html(html);
		        },
		        error:function (XMLHttpRequest, textStatus, errorThrown) {      
		        }
		     });
			loadbankcardnum();
		}
	}
	//加载银行卡号
	function loadbankcardnum(){
		var bankcardname = $('#bankcardname').val();
		if(bankcardname){
			$.ajax({
		        type: "post",
		        url: '${base}/admin/inland/loadBankCardNumSelect.html',
		        data: {bankcardname:bankcardname},
		        cache: false,
		        async : false,
		        success: function (data ,textStatus, jqXHR){
		        	var html = '<option>'+data.cardNum+'</option>';
		        	$('#bankcardnum').html(html);
		        },
		        error:function (XMLHttpRequest, textStatus, errorThrown) {      
		        }
		     });
		}
	}
	loadbankcardname();
	loadbankcardnum();
	
	function toOrderDetail(id){
		var url = '${base}/admin/inland/bookingDetail.html?id=' + id;
		window.open(url);
	}
	</script>
</body>
</html>	