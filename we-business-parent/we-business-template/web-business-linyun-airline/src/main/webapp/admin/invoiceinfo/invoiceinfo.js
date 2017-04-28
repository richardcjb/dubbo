var KaiInvoiceTable1;
//初始化表格
initKaiInvoiceTable1();
kaiInvoiceSelectData();
function initKaiInvoiceTable1() {
	KaiInvoiceTable1 = $('#KaiInvoiceTable1').DataTable({
  	"searching":false,
  	"bLengthChange": false,
      "processing": true,
      "serverSide": true,
      "stripeClasses": [ 'strip1','strip2' ],
      "initComplete": function( settings, json ) {
       	 autoHighLoad($(this));
       },
       "infoCallback": function( settings, start, end, max, total, pre ) {
         	autoHighLoad($(this));
 			return '显示第 '+start+' 至 '+end+' 条结果，共 '+total+' 条 (每页显示 '+max+' 条)';
       },
      "language": {
          "url": BASE_PATH + "/public/plugins/datatables/cn.json"
      },
      "ajax": {
          "url": BASE_PATH + "/admin/invoicemanage/invoiceinfo/listData.html",
          "type": "post",
          "data": function (d) {
        	  
          }
      },
      "columns": [
                  {"data": "ordersnum", "bSortable": false,
                	  render:function(data, type, row, meta) {
                    		var result = '<ul> ';
                    		$.each(row.orders, function(name, value) {
                    			if(value){
                    				result += '<li style="list-style:none;">'+value.ordersnum+'</li>';
                    			}
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                  },
                  {"data": "invoicenum", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = '<ul> ';
                		$.each(row.invoicedetail, function(name, value) {
                			if(value && value.invoicenum != undefined){
                				if(value.invoicenum !=null && value.invoicenum != ""){
                					result += '<li style="list-style:none;">'+value.invoicenum+'</li>';
                				}else{
                					return "";
                				}
                			}
                		});
                		result += '</ul>';
                		return result;
                  	}
                  },
                  {"data": "invoicebalance", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = '<ul>';
                  		$.each(row.invoicedetail, function(name, value) {
                  			if(value && value.invoicebalance != undefined){
                  				result += '<li style="list-style:none;">'+value.invoicebalance.toFixed(2)+'</li>';
                  			}
                  		});
                  		result += '</ul>';
                  		return result;
                  	}
                  },
                  {"data": "invoicetotal", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = 0;
                  		$.each(row.invoicedetail, function(name, value) {
                  			if(value && value.invoicebalance != undefined){
                  				result = (parseFloat(result) + parseFloat(value.invoicebalance)).toFixed(2);
                  			}
                  		});
                  		return result;
                  	}
                  },
                  {"data": "invoicedate", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var result = '';
                  		if(row.invoicedate){
                  			result = row.invoicedate;
                  		}
                  		return result;
                  	}  
                  },
                  {"data": "invoiceitem", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var result = '';
                  		$.each(row.ytselect, function(name, value) {
                  			if(value.id === row.invoiceitem){
                  				result = value.comDictName;
                  			}
                  		});
                  		return result;
                  	}
                  },
                  {"data": "paymentunit", "bSortable": false,
                	  render:function(data, type, row, meta) {
                    		var paymentunit = row.paymentunit;
                    		if(paymentunit == "" || paymentunit == null){
                    			return "";
                    		}
                    		return paymentunit;
                    	}
                  },
                  {"data": "username", "bSortable": false,
                	  render:function(data, type, row, meta) {
	                  		var result = '';
	                  		if(row.username){
	                  			result = row.username;
	                  		}
	                  		return result;
                	  }
                  },
                  {"data": "status", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var status = row.status;
                  		if(status ==null || status==""){
                  			return "";
                  		}else{
                  			if(status===1){
                  				return "开发票中";
                  			}else if(status===2){
                  				return "已开发票";
                  			}
                  		}
                  	}
                  },
                  {"data": "remark", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		//var remark = row.remark;
                		var remark = '<span data-toggle="tooltip" data-placement="left" title="'+row.remark+'">'+row.remark+'<span>';
                  		if(remark == "" || remark == null){
                  			return "";
                  		}
                  		return remark;
                  	}
                  },
                  {"data": " ", "bSortable": false,
                	  render: function(data, type, row, meta) {
                          return '<a style="cursor:pointer;" onclick="openkaiInvoiceEdit('+row.id+');">开发票</a>'
                      }
                  }
          ],
      columnDefs: [/*{
    	//   指定第一列，从0开始，0表示第一列，1表示第二列……
          targets: 10,
          render: function(data, type, row, meta) {
              return '<a style="cursor:pointer;" onclick="openkaiInvoiceEdit('+row.id+');">开发票</a>'
          }
      }*/]
  });
}
function kaiInvoiceLoad(){
	KaiInvoiceTable1.ajax.reload(
		function(json){
			autoHighLoad($('#KaiInvoiceTable1'));
		}
	);
}
//datatable行点击事件
$("tbody",$('#KaiInvoiceTable1')).on("dblclick","tr",function(event){
	//获取当前行的数据
	var row = KaiInvoiceTable1.row($(this).closest('tr')).data();
	openkaiInvoiceEdit(row.id);
});
//打开开发票页面
function openkaiInvoiceEdit(id){
	layer.open({
        type: 2,
        title:false,
        skin: false, //加上边框
        closeBtn:false,//默认 右上角关闭按钮 是否显示
        shadeClose:true,
        scrollbar: false,
        area: ['987px', '620px'],
        content: BASE_PATH + '/admin/invoicemanage/invoiceinfo/kaiOpenInvoice.html?id='+id
      });
}
//开发票 搜索按钮
$("#kaiSearchInvoiceBtn").on('click',kaiInvoiceSelectData());
function kaiInvoiceSelectData() {
	var status = $("#kaiInvoiceSelect").val();
	var billuserid = $("#kaibilluserid").val();
	var kaiInvoiceBeginDate = $("#kaiInvoiceBeginDate").val();
	var kaiInvoiceEndDate = $("#kaiInvoiceEndDate").val();
	var invoicenum = $("#invoicenumId").val();
	var paymentunit = $("#invoicenumId").val();
	
    var param = {
		        "status":status,
		        "billuserid":billuserid,
		        "kaiInvoiceBeginDate":kaiInvoiceBeginDate,
		        "kaiInvoiceEndDate":kaiInvoiceEndDate,
				"invoicenum": invoicenum,
				"paymentunit": paymentunit
		    };
    	KaiInvoiceTable1.settings()[0].ajax.data = param;
    	KaiInvoiceTable1.ajax.reload(
			function(json){
				autoHighLoad($('#KaiInvoiceTable1'));
			}
    	);
}
//开发票状态选择按钮
$("#kaiInvoiceSelect").change(function(){
	kaiInvoiceSelectData();
});
//开发票开票人选择按钮
$("#kaibilluserid").change(function(){
	kaiInvoiceSelectData();
});
/*清除 开发票   检索项*/
$('#kaiEmptyBtn').click(function(){
	clearSearchTxt("kaiInvoiceSelect","kaibilluserid", "kaiInvoiceBeginDate", "kaiInvoiceEndDate", "invoicenumId");
	kaiInvoiceSelectData();
});

/*******************************************************收发票表格*****************************************************************/
//收发票表格
var shouInvoiceTable1;
//初始化表格
initshouInvoiceTable1();
shouInvoiceSelectData();
function initshouInvoiceTable1() {
	shouInvoiceTable1 = $('#shouInvoiceTable1').DataTable({
	"searching":false,
	"bLengthChange": false,
    "processing": true,
    "serverSide": true,
    "stripeClasses": [ 'strip1','strip2' ],
    "language": {
        "url": BASE_PATH + "/public/plugins/datatables/cn.json"
    },
    "ajax": {
        "url": BASE_PATH + "/admin/invoicemanage/invoiceinfo/listShouInvoiceData.html",
        "type": "post",
        "data": function (d) {
        	
        }
    },
    "columns": [
                {"data": "ordersnum", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.ordersnum && row.ordersnum!= undefined){
                			result = row.ordersnum;
                		}
                		return result;
                	}
                },
                {"data": "pNR", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.pnr && row.pnr!= undefined){
                			result = row.pnr;
                		}
                		return result;
                	}
                },
                {"data": "peoplecount", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.peoplecount && row.peoplecount != undefined) {
                			result =row.peoplecount;
                		}
                		return result;
                	}
                },
                {"data": "invoicecount", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.invoicecount && row.invoicecount != undefined) {
                			result =row.invoicecount;
                		}
                		return result;
                	}
                },
                {"data": "costpricesum", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.costpricesum && row.costpricesum != undefined) {
                			result =row.costpricesum.toFixed(2);
                		}
                		return result;
                	}
                },
                {"data": "invoicedate", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.invoicedate && row.invoicedate != undefined) {
                			result =row.invoicedate;
                		}
                		return result;
                	}
                },
                {"data": "invoiceitem", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		$.each(row.ytselect, function(name, value) {
                			if(value.id === row.invoiceitem){
                				result = value.comDictName;
                			}
                		});
                		return result;
                	}
                },
                {"data": "paymentunit", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.paymentunit && row.paymentunit != undefined) {
                			result =row.paymentunit;
                		}
                		return result;
                	}
                },
                {"data": "username", "bSortable": false,
                	render:function(data, type, row, meta) {
                  		var result = '';
                  		if(row.username){
                  			result = row.username;
                  		}
                  		return result;
            	   }
                },
                {"data": "status", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var status = row.status;
                  		if(status ==null || status==""){
                  			return "";
                  		}else{
                  			if(status===3){
                  				return "收发票中";
                  			}else if(status===4){
                  				return "已收发票";
                  			}
                  		}
                  	}
                },
                {"data": "remark", "bSortable": false,
                	render:function(data, type, row, meta) {
                  		var result = '';
                  		if(row.remark){
                  			//result = row.remark;
                  			var result = '<span class="tooltipSpan" data-toggle="tooltip" data-placement="left" title="'+row.remark+'">'+row.remark+'<span>';
                  		}
                  		return result;
                  	}
                },
                {"data": " ", "bSortable": false,
                	render: function(data, type, row, meta) {
                        return '<a style="cursor:pointer;" onclick="openshouInvoiceEdit('+row.invoiceid+');">收发票</a>'
                    }
                }
        ],
    columnDefs: [/*{
  	//   指定第一列，从0开始，0表示第一列，1表示第二列……
        targets: 11,
        render: function(data, type, row, meta) {
            return '<a style="cursor:pointer;" onclick="openshouInvoiceEdit('+row.invoiceid+');">收发票</a>'
        }
    }*/
    /*{ "sWidth": "5%",  "targets": [0] },
	{ "sWidth": "0.5%",  "targets": [1] }
	/*{ "sWidth": "4%",  "targets": [2] },
	{ "sWidth": "5.1%",  "targets": [3] }
	{ "sWidth": "8.33%",  "targets": [4] },
	{ "sWidth": "7.33%",  "targets": [5] },
	{ "sWidth": "10.66%",  "targets": [6] },
	{ "sWidth": "21.33%",  "targets": [7] },
	{ "sWidth": "8.33%",  "targets": [8] },
	{ "sWidth": "8.33%",  "targets": [9] },
	{ "sWidth": "6.33%",  "targets": [10] },
	{ "sWidth": "8.33%",  "targets": [11] }*/
    ]
});
}
function shouInvoiceLoad(){
	shouInvoiceTable1.ajax.reload(
		function(json){
			autoHighLoad($('#shouInvoiceTable1'));
		}
	);
}
//datatable行点击事件
/*$("tbody",$('#shouInvoiceTable1')).on("dblclick","tr",function(event){
	//获取当前行的数据
	var row = shouInvoiceTable1.row($(this).closest('tr')).data();
	var url = BASE_PATH + '/admin/inland/bookingDetail.html?id='+row.orderids;
	window.open(url,'_black');
});*/
$("tbody",$('#shouInvoiceTable1')).on("dblclick","tr",function(event){
	//获取当前行的数据
	var row = shouInvoiceTable1.row($(this).closest('tr')).data();
	openshouInvoiceEdit(row.invoiceid);
});
function openshouInvoiceEdit(id){
	layer.open({
        type: 2,
        title:false,
        skin: false, //加上边框
        closeBtn:false,//默认 右上角关闭按钮 是否显示
        shadeClose:true,
        scrollbar: false,
        area: ['987px', '620px'],
        content: BASE_PATH + '/admin/invoicemanage/invoiceinfo/shouOpenInvoice.html?id='+id
    });
}
//收发票 搜索按钮
$("#shouSearchInvoiceBtn").on('click',shouInvoiceSelectData());
function shouInvoiceSelectData() {
	var status = $("#shouInvoiceSelect").val();
	var billuserid = $("#shoubilluserid").val();
	var shouInvoiceBeginDate = $("#shouInvoiceBeginDate").val();
	var shouInvoiceEndDate = $("#shouInvoiceEndDate").val();
	var PNR = $("#paymentunitId").val();
	var paymentunit = $("#paymentunitId").val();
    var param = {
		        "status":status,
		        "billuserid":billuserid,
		        "shouInvoiceBeginDate":shouInvoiceBeginDate,
		        "shouInvoiceEndDate":shouInvoiceEndDate,
				"PNR": PNR,
				"paymentunit": paymentunit
		    };
    	shouInvoiceTable1.settings()[0].ajax.data = param;
    	shouInvoiceTable1.ajax.reload(
    			function(json){
    				autoHighLoad($('#shouInvoiceTable1'));
    			}
    	);
}

/*清除 收发票   检索项*/
$('#shouEmptyBtn').click(function(){
	clearSearchTxt("shouInvoiceSelect","shoubilluserid", "shouInvoiceBeginDate", "shouInvoiceEndDate", "paymentunitId");
	shouInvoiceSelectData();
});

//清空搜索项函数
function clearSearchTxt(selectId,selectUsername ,beginDateId, endDateId, inputId){
	$("#"+selectId+" option:first").prop("selected", 'selected');  
	$("#"+selectUsername+" option:first").prop("selected", 'selected');  
	$("#"+beginDateId).val("");
	$("#"+endDateId).val("");
	$("#"+inputId).val("");
}
//收发票状态选择按钮
$("#shouInvoiceSelect").change(function(){
	shouInvoiceSelectData();
});
//根据开票人进行筛选
$("#shoubilluserid").change(function(){
	shouInvoiceSelectData();
});
