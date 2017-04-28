//会计   未付款datatable
var interPayTable;
function initPayDataTable(){
	interPayTable = $("#interPayTable").DataTable({
		"searching":false,
		"lengthChange": false,
		"processing": true,
		"serverSide": true,
		"stripeClasses": [ 'strip1','strip2' ],
		"language": {
			"url": BASE_PATH + "/public/plugins/datatables/cn.json"
		},
		"ajax": {
			"url": BASE_PATH + "/admin/receivePay/inter/interPayList.html",
			"type": "post",
			"data": function (d) {

			}
		},
		"columns": [
		            {"data": "id", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var result = '';
		            		var hiddenValue = $('#checkedboxPayValue').val();
		            		var splits = hiddenValue.split(',');
		            		var flag = false;
		            		for(var i=0;i<splits.length;i++){
		            			if(splits[i] == data){
		            				flag = true;
		            			}
		            		}	
		            		if(flag){
		            			result = '<input type="checkbox"  class="checkBoxPayChild" checked="true" value="' + data + '" />';
		            		}else{
		            			result = '<input type="checkbox"  class="checkBoxPayChild" value="' + data + '" />';
		            		}
		            		return result;
		            	}
		            },
		            {"data": "ordersnum", "bSortable": false},
		            {"data": "pnrnum", "bSortable": false,
		            	render:function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		$.each(row.orders, function(name, value) {
		            			if(value && value.pnrnum!=undefined){
		            				result += '<li style="list-style:none;">'+value.pnrnum+'</li>';
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "leavesdate", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var MM = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEPT', 'OCT', 'NOV', 'DEC'];
		            		var week = ['MO','TU','WE','TH','FR','SA','SU'];
		            		var result = '<ul> ';
		            		$.each(row.orders, function(name, value) {
		            			if(value && value.leavesdate!=undefined){
		            				var formatDate = "";
		            				var ldate = new Date(value.leavesdate);
		            				if(ldate != undefined){
		            					formatDate = week[ldate.getUTCDay()]+ldate.getDate() + MM[ldate.getMonth()];
		            				}
		            				result += '<li style="list-style:none;">'+formatDate+'</li>';
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "peoplecount", "bSortable": false,
		            	render:function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		var oNum = "";
		            		$.each(row.orders, function(name, value) {
		            			if(value && value.peoplecount!=undefined){
		            				var ordernum = value.ordersnum;
		            				if(oNum!=ordernum){
		            					result += '<li style="list-style:none;">'+value.peoplecount+'</li>';
		            				}
		            				oNum = ordernum;
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "actualnumber", "bSortable": false,
		            	render:function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		var oNum = "";
		            		$.each(row.orders, function(name, value) {
		            			if(value && value.actualnumber!=undefined){
		            				var ordernum = value.ordersnum;
		            				if(oNum!=ordernum){
		            					result += '<li style="list-style:none;">'+value.actualnumber+'</li>';
		            				}
		            				oNum = ordernum;
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}	
		            },
		            {"data": "ataxprice", "bSortable": false,
		            	render:function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		var oNum = "";
		            		$.each(row.orders, function(name, value) {
		            			if(value && value.ataxprice!=undefined){
		            				var ordernum = value.ordersnum;
		            				if(oNum!=ordernum){
		            					result += '<li style="list-style:none;">'+(value.ataxprice).toFixed(2)+'</li>';
		            				}
		            				oNum = ordernum;
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "currentpay", "bSortable": false,
		            	render:function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		var oNum = "";
		            		$.each(row.orders, function(name, value) {
		            			if(value && value.currentpay!=undefined){
		            				var ordernum = value.ordersnum;
		            				if(oNum!=ordernum){
		            					result += '<li style="list-style:none;">'+(value.currentpay).toFixed(2)+'</li>';
		            				}
		            				oNum = ordernum;
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "paycurrency", "bSortable": false,
		            	render:function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		var oNum = "";
		            		$.each(row.orders, function(name, value) {
		            			if(value && value.paycurrency!=undefined){
		            				var ordernum = value.ordersnum;
		            				if(oNum!=ordernum){
		            					result += '<li style="list-style:none;">'+value.paycurrency+'</li>';
		            				}
		            				oNum = ordernum;
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "shortname", "bSortable": false,
		            	render:function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		var oNum = "";
		            		$.each(row.orders, function(name, value) {
		            			if(value && value.shortname!=undefined){
		            				var ordernum = value.ordersnum;
		            				if(oNum!=ordernum){
		            					result += '<li style="list-style:none;">'+value.shortname+'</li>';
		            				}
		            				oNum = ordernum;
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "paystauts", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var s = '';
		            		if(data == '2'){
		            			s = '付款中';
		            		}
		            		if(data == '3'){
		            			s = '已付款';
		            		}
		            		return s;
		            	}
		            },
		            {"data": "issuer", "bSortable": false,
		            	render:function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		var oNum = "";
		            		$.each(row.orders, function(name, value) {
		            			if(value && value.issuer!=undefined){
		            				var ordernum = value.ordersnum;
		            				if(oNum!=ordernum){
		            					result += '<li style="list-style:none;">'+value.issuer+'</li>';
		            				}
		            				oNum = ordernum;
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            }
		            ],
		            "infoCallback": function (settings, start, end, max, total, pre) {
		            	var length = $(".checkBoxPayChild:checked").length;
		            	if(interPayTable.page.len() == length){
		            		$(".checkBoxPayAll").prop("checked", true);
		            	}else{
		            		$(".checkBoxPayAll").prop("checked", false);

		            	}
		            	autoHighLoad($(this));
		            	return '显示第 '+start+' 至 '+end+' 条结果，共'+total+' 条 (每页显示 '+max+' 条)'
		            }

	});
}
//datatable行点击事件
$("tbody",$('#interPayTable')).on("dblclick","tr",function(event){
	//获取当前行的数据
	var row = interPayTable.row($(this).closest('tr')).data();
	var ids = row.id;
	layer.open({
		type: 2,
		title:false,
		skin: false, //加上边框
		closeBtn:false,//默认 右上角关闭按钮 是否显示
		shadeClose:false,
		scrollbar: false,
		area: ['850px', '650px'],
		content: ['confirmPay.html?orderIds='+ ids,'no'],
	});
});
$("tbody",$('#interPayEdTable')).on("dblclick","tr",function(event){
	//获取当前行的数据
	var row = interPayEdTable.row($(this).closest('tr')).data();
	var prrids = row.prrids;
	var pid = row.pid;
	editPay(pid, prrids);
});

//会计   已付款datatable
var interPayEdTable;
function initPayEdDataTable(){
	interPayEdTable = $("#interPayEdTable").DataTable({
		"searching":false,
		"lengthChange": false,
		"processing": true,
		"serverSide": true,
		"stripeClasses": [ 'strip1','strip2' ],
		"language": {
			"url": BASE_PATH + "/public/plugins/datatables/cn.json"
		},
		"ajax": {
			"url": BASE_PATH + "/admin/receivePay/inter/interPayEdList.html",
			"type": "post",
			"data": function (d) {

			}
		},
		"columns": [
		            {"data": "ordersnum", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		var oNum = "";
		            		$.each(row.orders, function(name, value) {
		            			if(value){
		            				var ordernum = value.ordersnum;
		            				if(ordernum == null || ordernum == undefined || ordernum==""){
		            					ordernum = " ";
		            				}else{
		            					if(oNum!=ordernum){
		            						result += '<li style="list-style:none;">'+ordernum+'</li>';
		            					}
		            				}
		            				oNum = ordernum;
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "pnrnum", "bSortable": false,
		            	render:function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		$.each(row.orders, function(name, value) {
		            			if(value && value.pnrnum!=undefined){
		            				result += '<li style="list-style:none;">'+value.pnrnum+'</li>';
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "leavedate", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		$.each(row.orders, function(name, value) {
		            			if(value && value.leavesdate != undefined){
		            				var date = value.leavesdate;
		            				var MM = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEPT', 'OCT', 'NOV', 'DEC'];
		            				var week = ['MO','TU','WE','TH','FR','SA','SU'];
		            				var ldate = new Date(date);
		            				var dateFormat = week[ldate.getUTCDay()]+ldate.getDate() + MM[ldate.getMonth()];
		            				result += '<li style="list-style:none;">'+dateFormat+'</li>';
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "peoplecount", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		var oNum = "";
		            		$.each(row.orders, function(name, value) {
		            			if(value){
		            				var pCount = value.peoplecount;
		            				var ordernum = value.ordersnum;
		            				if(pCount == null || pCount == undefined || pCount==""){
		            					pCount = " ";
		            				}else{
		            					if(oNum!=ordernum){
		            						result += '<li style="list-style:none;">'+pCount+'</li>';
		            					}
		            				}
		            				oNum = ordernum;
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "currentpay", "bSortable": false,
		            	render:function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		var oNum = "";
		            		$.each(row.orders, function(name, value) {
		            			var ordernum = value.ordersnum;
		            			if(value && value.currentpay!=undefined){
		            				if(oNum!=ordernum){
		            					result += '<li style="list-style:none;">'+(value.currentpay).toFixed(2)+'</li>';
		            				}
		            			}
		            			oNum = ordernum;
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "currency", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		var oNum = "";
		            		$.each(row.orders, function(name, value) {
		            			if(value){
		            				var paycurrency = value.paycurrency;
		            				var ordernum = value.ordersnum;
		            				if(paycurrency == null || paycurrency == undefined || paycurrency==""){
		            					paycurrency = " ";
		            				}else{
		            					if(oNum!=ordernum){
		            						result += '<li style="list-style:none;">'+paycurrency+'</li>';
		            					}
		            				}
		            				oNum = ordernum;
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "totalmoney", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var totalmoney = row.totalmoney;
		            		if(null == totalmoney || ""== totalmoney){
		            			return "";
		            		}
		            		return totalmoney.toFixed(2);
		            	}
		            },
		            {"data": "shortname", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var shortname = row.shortname;
		            		if(null == shortname || ""== shortname){
		            			return "";
		            		}
		            		return shortname;
		            	}
		            },
		            {"data": "paystatus", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var s = '';
		            		var paystatus = row.paystatus;
		            		if(paystatus == '2'){
		            			s = '付款中';
		            		}
		            		if(paystatus == '3'){
		            			s = '已付款';
		            		}
		            		return s;
		            	}
		            },
		            {"data": "username", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var issuer = row.issuer;
		            		if(null == issuer || ""== issuer){
		            			return "";
		            		}
		            		return issuer;
		            	}
		            },
		            {"data": "remark", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var remark = row.remark;
		            		if(null == remark || ""== remark){
		            			return "";
		            		}
		            		return remark;
		            	}
		            }
		            ],
		            "infoCallback": function (settings, start, end, max, total, pre) {
		            	var length = $(".checkBoxPayChild:checked").length;
		            	if(interPayEdTable.page.len() == length){
		            		$(".checkBoxPayAll").prop("checked", true);
		            	}else{
		            		$(".checkBoxPayAll").prop("checked", false);

		            	}
		            	autoHighLoad($(this));
		            	return '显示第 '+start+' 至 '+end+' 条结果，共'+total+' 条 (每页显示 '+max+' 条)'
		            },
		            columnDefs: [{
		            	//   指定第一列，从0开始，0表示第一列，1表示第二列……
		            	targets: 11,
		            	render: function(data, type, row, meta) {
		            		var pid = row.pid;
		            		var prrIds = row.prrids;
		            		var modify = '<a style="cursor:pointer;" onclick="editPay('+ pid +','+ prrIds +');">编辑</a>';
		            		return modify;
		            	}
		            }]

	});
}



//付款页切换
function  toConfirmPayPage(){
	if($('ul.nlkhUL li').eq(1).attr("class") != "active"){
		var payStatus = $("#interPaySelect").val();
		destroyDatetable($("#interRecTable"));
		initPayDataTable();
		$('#interPaySearchBtn').click();
		$("#interRecSelect option:first").prop("selected", true);
	}
}
//收款页切换
function  toConfirmRecPage(){
	if($('ul.nlkhUL li').eq(0).attr("class") != "active"){
		destroyDatetable($("#interPayTable"));
		destroyDatetable($("#interPayEdTable"));
		initRecDataTable();
		$('#interRecSearchBtn').click();
		$("#interPayClick").show();
		$("#interPayTable").show();
		$("#interPayEdTable").hide();
		$("#interPaySelect option:first").prop("selected", true);
	}
}

//页面加载时调用
function  recPageFirst(){
	initRecDataTable();
	$('#interRecSearchBtn').click();
	$("#interPayClick").show();
	$("#interPayTable").show();
	$("#interPayEdTable").hide();
	$("#interPaySelect option:first").prop("selected", true);
}

//状态选择按钮
$("#interPaySelect").change(function(){
	var selectEd = $("#interPaySelect option:first").prop("selected");
	$("#box-body").html("");
	if(selectEd){
		destroyDatetable($("#interPayEdTable"));
		$("#interPayClick").show();
		$("#interPayCancelBtn").show();
		$("#interPayTable").show();
		$("#interPayEdTable").hide();
		initPayDataTable();
	}else{
		destroyDatetable($("#interPayTable"));
		$("#interPayClick").hide();
		$("#interPayCancelBtn").hide();
		$("#interPayTable").hide();
		$("#interPayEdTable").show();
		initPayEdDataTable();
	}
	$('#interPaySearchBtn').click();
});

//销毁datatable
function destroyDatetable(obj){
	var datatable = obj.dataTable();
	datatable.fnClearTable(); //清空一下table
	datatable.fnDestroy(); //还原初始化了的datatable
}


//付款 弹框
$('#interPayClick').click(function(){
	var ids = $('#checkedboxPayValue').val();
	$('#checkedboxPayValue').val("");
	var length = $(".checkBoxPayChild:checked").length;
	if(!ids){
		layer.msg("请至少选中一条记录", "", 2000);
	}else{
		layer.open({
			type: 2,
			title:false,
			skin: false, //加上边框
			closeBtn:false,//默认 右上角关闭按钮 是否显示
			shadeClose:false,
			area: ['850px', '650px'],
			content: ['confirmPay.html?orderIds='+ ids,'no'],
		});

	}
});

//国际付款 复选框 全选
$(".checkBoxPayAll").click(function () {
	var check = $(this).prop("checked");
	$(".checkBoxPayChild").prop("checked", check);
	//隐藏域的值
	var hiddenval = $('#checkedboxPayValue').val();
	if(check){
		var splits = hiddenval.split(',');
		$(".checkBoxPayChild:checked").each(function(){
			var thisvals = $(this).val();
			var flag = false;
			for(var i=0;i<splits.length;i++){
				if(splits[i] == thisvals){
					flag = true;
				}
			}
			//如果隐藏域值为空
			if(hiddenval){
				if(!flag){
					hiddenval += ',' + thisvals;
				}
			}else{
				hiddenval = thisvals;
			}
		});
	}else{
		$(".checkBoxPayChild").each(function(){
			var thisval = $(this).val();
			var flag = false;
			var splits = hiddenval.split(',');
			for(var i=0;i<splits.length;i++){
				if(splits[i] == thisval){
					flag = true;
				}
			}
			//如果隐藏域值为空
			if(flag){
				var ids = [];
				for(var i=0;i<splits.length;i++){
					if(splits[i] != thisval){
						ids.push(splits[i]);
					}
				}
				ids = ids.join(',');
				hiddenval = ids;
			}
		});
	}
	$('#checkedboxPayValue').val(hiddenval);
});

//会计 国际付款 复选框 单选
$(document).on('click', '.checkBoxPayChild', function(e) {
	var hiddenval = $('#checkedboxPayValue').val();
	var thisval = $(this).val();
	var check = $(this).prop("checked");
	if(check){
		if(!hiddenval){
			$('#checkedboxPayValue').val(thisval);
		}else{
			$('#checkedboxPayValue').val(hiddenval+','+thisval);
		}
	}else{
		var splits = hiddenval.split(',');
		var flag = false;
		for(var i=0;i<splits.length;i++){
			if(splits[i] == thisval){
				flag = true;
			}
		}
		//如果存在则删掉当前值
		if(flag){
			var ids = [];
			for(var i=0;i<splits.length;i++){
				if(splits[i] != thisval){
					ids.push(splits[i]);
				}
			}
			ids = ids.join(',');
			$('#checkedboxPayValue').val(ids);
		}else{
			$('#checkedboxPayValue').val(hiddenval);
		}
	}
	var length = $(".checkBoxPayChild:checked").length;
	if(interPayTable.page.len() == length){
		$(".checkBoxPayAll").prop("checked", true);
	}else{
		$(".checkBoxPayAll").prop("checked", false);
	}
});

//国际付款 搜索按钮
$("#interPaySearchBtn").on('click', function () {
	var interPayStatus = 2;   //付款中
	var interPayEdStatus = 3; //已付款
	var payStatus = $("#interPaySelect").val();
	var interPayBeginDate = $("#interPayBeginDate").val();
	var interPayEndDate = $("#interPayEndDate").val();
	var interPayInput = $("#interPayInput").val();
	var orderStatus = $(".osClass",$(".orderStatusClass")).attr("id");
	var param = {
			        "orderStatus":orderStatus,
			"payStatus":payStatus,
			        "leavetdate":interPayBeginDate,
			        "backdate":interPayEndDate,
			"name": interPayInput
			    };
	if(payStatus==interPayStatus){
		interPayTable.settings()[0].ajax.data = param;
		interPayTable.ajax.reload(
				function(json){
					autoHighLoad($('#interPayTable'));
				}
		);
	}
	if(payStatus==interPayEdStatus){
		interPayEdTable.settings()[0].ajax.data = param;
		interPayEdTable.ajax.reload(
				function(json){
					autoHighLoad($('#interPayEdTable'));
				}
		);
	}
	   
});


/*清除 国际收款的   检索项*/
$('#interRecClearBtn').click(function(){
	clearSearchTxt("interRecSelect", "interRecBeginDate", "interRecEndDate", "interRecInput");
});

/*清除 国际 付款的   检索项*/
$('#interPayClearBtn').click(function(){
	clearSearchTxt("interPaySelect", "interPayBeginDate", "interPayEndDate", "interPayInput");
});

//国际 取消所有勾选
function clearGou(){
	$('#interPayCancelBtn').click();
}
$('#interPayCancelBtn').click(function(){
	$('#checkedboxPayValue').val("");
	$(".checkBoxPayAll").prop("checked", false);
	$(".checkBoxPayChild").prop("checked", false);
});


//清空搜索项函数
function clearSearchTxt(selectId, beginDateId, endDateId, inputId){
	$("#"+selectId+" option:first").prop("selected", 'selected');  
	$("#"+beginDateId).val("");
	$("#"+endDateId).val("");
	$("#"+inputId).val("");
}

//回车搜索
function payOnkeyEnter(){
	if(event.keyCode==13){
		$("#interPaySearchBtn").click();
	}
}

function editPay(pid, prrIds){
	layer.open({
		type: 2,
		title:false,
		skin: false, //加上边框
		closeBtn:false,//默认 右上角关闭按钮 是否显示
		shadeClose:false,
		area: ['850px', '650px'],
		content: ['editConfirmPay.html?payid='+pid +'&prrIds='+prrIds,'no'],
	});
}


//文件上传
$('#uploadFile').click(function(){
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
		'uploader' : '${base}/admin/receivePay/inland/uploadFile.html',//后台处理的页面
		//onUploadSuccess为上传完视频之后回调的方法，视频json数据data返回，
		//下面的例子演示如何获取到vid
		'onUploadSuccess' : function(file, data, response) {
			var jsonobj = eval('(' + data + ')');
			var url  = jsonobj;//地址
			var fileName = file.name;//文件名称
			$('#billurl').val(url);
			$('#shuidanimg').attr('src',url);
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
		}
	});
});
