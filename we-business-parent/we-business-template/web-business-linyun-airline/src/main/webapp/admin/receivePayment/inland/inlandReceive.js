//内陆跨海 收款 弹框
var inlandRecTable;
function initRecDataTable() {
	inlandRecTable = $('#inlandRecTable').DataTable({
		"searching":false,
		"bLengthChange": false,
		"processing": true,
		"serverSide": true,
		"stripeClasses": [ 'strip1','strip2' ],
		"language": {
			"url": BASE_PATH + "/public/plugins/datatables/cn.json"
		},
		"infoCallback": function( settings, start, end, max, total, pre ) {
          	autoHighLoad($(this));
  			return '显示第 '+start+' 至 '+end+' 条结果，共 '+total+' 条 (每页显示 '+max+' 条)';
        },
		"ajax": {
			"url": BASE_PATH + "/admin/receivePay/inland/inlandRecList.html",
			"type": "post",
			"data": function (d) {

			}
		},
		"columns": [
		            {"data": "ordersnum", "bSortable": false,
		            	render:function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		var orderNum = "";
		            		$.each(row.orders, function(name, value) {
		            			var num = value.ordersnum;
		            			if(value && (num!=orderNum)){
		            				result += '<li style="list-style:none;">'+value.ordersnum+'</li>';
		            				orderNum = num;
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
		            			if(value && value.leavetdate != undefined ){
		            				var date = value.leavetdate;
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
		            {"data": "personcount", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		var orderNum = "";
		            		$.each(row.orders, function(name, value) {
		            			var num = value.ordersnum;
		            			if(value && (num!=orderNum)){
		            				var pCount = value.personcount;
		            				if(pCount == null || pCount == undefined || pCount==""){
		            					pCount = '';
		            				}else{
		            					result += '<li style="list-style:none;">'+pCount+'</li>';
		            				}
		            				orderNum = num;
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "incometotal", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		var orderNum = "";
		            		$.each(row.orders, function(name, value) {
		            			var num = value.ordersnum;
		            			if(value && value.incometotal!=undefined && (num!=orderNum)){
		            				result += '<li style="list-style:none;">'+(value.incometotal).toFixed(2)+'</li>';
		            			}
		            			orderNum = num;
		            		});
		            		result += '</ul>';
		            		return result;
		            		
		            	}
		            },
		            {"data": "sum", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var sum = row.sum;
		            		if(null == sum || ""== sum){
		            			return "";
		            		}
		            		return sum.toFixed(2);
		            	}
		            },
		            {"data": "shortname", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var result = '';
		            		$.each(row.orders, function(name, value) {
		            			if(value && value.shortname!=undefined){
		            				result = value.shortname;
		            			}
		            		});
		            		return result;
		            	}
		            },
		            {"data": "username", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		var orderNum = "";
		            		$.each(row.orders, function(name, value) {
		            			var num = value.ordersnum;
		            			if(value && value.issuer!=undefined && value.issuer!=null && value.issuer!="" && (num!=orderNum)){
		            				result += '<li style="list-style:none;">'+value.issuer+'</li>';
		            			}
		            			orderNum = num;
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "orderstatus", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var s = row.status;
		            		if(s == 1){
		            			s = '收款中';
		            		}
		            		if(s == 2 ){
		            			s = '已收款';
		            		}
		            		return s;
		            	}
		            },
		            {"data": "notes", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var notes = row.notes;
		            		if(null == notes || ""== notes){
		            			return "";
		            		}
		            		return notes;
		            	}
		            }

		            ],
		            columnDefs: [{
		            	//   指定第一列，从0开始，0表示第一列，1表示第二列……
		            	targets: 4,
		            	render: function(data, type, row, meta) {
		            		/*var modify = '<a style="cursor:pointer;" onclick="editUser('+row.userid+');">编辑</a>';
                    return modify;*/
		            		return "";
		            	}
		            }]
	});
}

//datatable行点击事件
$("tbody",$('#inlandRecTable')).on("dblclick","tr",function(event){
	//获取当前行的数据
	var row = inlandRecTable.row($(this).closest('tr')).data();
	confirmReceive(row.id);
});

//確認收款
$("#confirmRecClick").click(function(){
	var recStatus = $("#inlandRecSelect option:selected",window.parent.document).val();
	if(recStatus==1){
		$.ajax({
			type : 'POST',
			data : {
				id:$("#recIds").val()
			},
			async: false,
			url: BASE_PATH + '/admin/receivePay/inland/saveInlandRec.html',
			success : function(data) {
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				parent.layer.close(index);
				parent.layer.msg("收款成功", "", 2000);
				parent.inlandRecTable.ajax.reload();
				$("#recIds").val("");
			},
			error: function () {
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				parent.layer.close(index);
				parent.layer.msg("收款失败", "", 2000);
				$("#recIds").val("");
			}
		});
	}else{
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
		parent.layer.msg("该订单已收款，请勿重复收款", "", 2000);
	}
	
});

//关闭确认付款窗口
$("#closeRecWindow").click(function(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
});

//打开确认付款页面
function confirmReceive(id){
	layer.open({
		type: 2,
		title:false,
		skin: false, //加上边框
		closeBtn:false,//默认 右上角关闭按钮 是否显示
		shadeClose:false,
		scrollbar: false,
		area: ['850px', '650px'],
		content: ['confirmReceive.html?inlandRecId='+ id,'no'],
	});
}

//付款检索
$("#inlandRecSelect").change(function(){
	$("#inlandRecSearchBtn").click();
});


//内陆跨海收款  搜索按钮
$("#inlandRecSearchBtn").on('click', function () {
	var orderStatus = $("#inlandRecSelect").val();
	var inlandRecBeginDate = $("#inlandRecBeginDate").val();
	var inlandRecEndDate = $("#inlandRecEndDate").val();
	var inlandRecInput = $("#inlandRecInput").val();
    var param = {
		"orderStatus":orderStatus,
		"leaveBeginDate":inlandRecBeginDate,
		"leaveEndDate":inlandRecEndDate,
		"name": inlandRecInput
	};
	inlandRecTable.settings()[0].ajax.data = param;
	inlandRecTable.ajax.reload(
			function(json){
				autoHighLoad($('#inlandRecTable'));
			}
	);
});

//回车搜索
function payOnkeyEnter(){
	 if(event.keyCode==13){
		 $("#inlandPaySearchBtn").click();
	 }
}