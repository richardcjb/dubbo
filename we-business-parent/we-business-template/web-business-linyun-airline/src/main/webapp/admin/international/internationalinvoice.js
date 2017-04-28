var kaiInvoiceTable;
//初始化表格
initkaiInvoiceTable();
function initkaiInvoiceTable() {
	kaiInvoiceTable = $('#kaiInvoiceTable').DataTable({
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
          "url": BASE_PATH + "/admin/international/invoice/listKaiInvoiceData.html",
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
                				result += '<li style="list-style:none;">'+value.invoicenum+'</li>';
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
                  				result += '<li style="list-style:none;">'+value.invoicebalance+'</li>';
                  			}
                  		});
                  		result += '</ul>';
                  		return result;
                  	}
                  },
                  {"data": "incometotal", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = 0;
                  		$.each(row.invoicedetail, function(name, value) {
                  			if(value && value.invoicebalance != undefined){
                  				result = parseFloat(result) + parseFloat(value.invoicebalance);
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
                  {"data": "paymentunit", "bSortable": false},
                  {"data": "username", "bSortable": false,
                	  render:function(data, type, row, meta) {
                    		var result = '';
                			if(row.username && row.username!=undefined){
                				result = row.username;
                			}
                    		return result;
                    	} 
                  },
                  {"data": "orderstatus", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var result = '';
              			if(row.orderstatus && row.orderstatus!=undefined){
              				result = row.orderstatus;
              			}
                  		return result;
                  	} 
                  },
                  {"data": "status", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var result = '';
                  		$.each(row.invoiceinfoenum, function(name, value) {
                  			if(row.status == name){
                  				result = value;
                  			}
                  		});
                  		return result;
                  	}
                  },
                  {"data": "remark", "bSortable": false,
                	  render:function(data, type, row, meta) {
                		   var result = '<span data-toggle="tooltip" data-placement="left" title="'+row.remark+'">'+row.remark+'<span>';
	                  	  return result;
	                  }
                  }
          ],
      columnDefs: [{
    	//   指定第一列，从0开始，0表示第一列，1表示第二列……
          targets: 11,
          render: function(data, type, row, meta) {
              return '<a style="cursor:pointer;" onclick="openkaiInvoiceEdit('+row.id+');">编辑</a>'
          }
      }]
  });
}
function kaiInvoiceLoad(){
	kaiInvoiceTable.ajax.reload(function(json){
		autoHighLoad($('#kaiInvoiceTable'));
	});
}
$('#kaiInvoiceSearch').click(function(){
	var div = $(this).parent().parent();
	var startdate = div.find('[name=startdate]').val();
	var enddate = div.find('[name=enddate]').val();
	var searchInfo = div.find('[name=searchInfo]').val();
	var status = $('#status').val();
	var kaiinvoicestatus = $('#kaiinvoicestatus').val();
	var param = {
			ordersstatus:status,
			startdate:startdate,
			enddate:enddate,
			searchInfo:searchInfo,
			kaiinvoicestatus:kaiinvoicestatus
	};
	kaiInvoiceTable.settings()[0].ajax.data = param;
	kaiInvoiceTable.ajax.reload(function(json){
		autoHighLoad($('#kaiInvoiceTable'));
	});
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
        content: BASE_PATH + '/admin/international/invoice/kaiInvoice.html?id='+id
      });
}
//付款表格
var shouInvoiceTable;
//初始化表格
initshouInvoiceTable();
function initshouInvoiceTable() {
	shouInvoiceTable = $('#shouInvoiceTable').DataTable({
	"searching":false,
	"bLengthChange": false,
    "processing": true,
    "serverSide": true,
    "stripeClasses": [ 'strip1','strip2' ],
    "infoCallback": function( settings, start, end, max, total, pre ) {
      	autoHighLoad($(this));
			return '显示第 '+start+' 至 '+end+' 条结果，共 '+total+' 条 (每页显示 '+max+' 条)';
    },
    "language": {
        "url": BASE_PATH + "/public/plugins/datatables/cn.json"
    },
    "ajax": {
        "url": BASE_PATH + "/admin/international/invoice/listShouInvoiceData.html",
        "type": "post",
        "data": function (d) {
        	
        }
    },
    "columns": [
                {"data": "ordersnum", "bSortable": false,
                	  render:function(data, type, row, meta) {
                    		var result = '';
                      		if(row.ordersnum && row.ordersnum != undefined){
                      			result = row.ordersnum;
                      		}
                      		return result;
                    	}
                  },
                  {"data": "invoicenum", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = '<ul> ';
                		$.each(row.invoicedetail, function(name, value) {
                			if(value && value.invoicenum != undefined){
                				result += '<li style="list-style:none;">'+value.invoicenum+'</li>';
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
                  				result += '<li style="list-style:none;">'+value.invoicebalance+'</li>';
                  			}
                  		});
                  		result += '</ul>';
                  		return result;
                  	}
                  },
                  {"data": "incometotal", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = 0;
                  		$.each(row.invoicedetail, function(name, value) {
                  			if(value && value.invoicebalance != undefined){
                  				result = parseFloat(result) + parseFloat(value.invoicebalance);
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
                  {"data": "paymentunit", "bSortable": false},
                  {"data": "username", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var result = '';
              			if(row.username && row.username!=undefined){
              				result = row.username;
              			}
                  		return result;
                  	} 
                  },
                  {"data": "orderstatus", "bSortable": false,
                	  render:function(data, type, row, meta) {
                    		var result = '';
                    		$.each(row.internationalstatusenum, function(name, value) {
                    			if(row.orderstatus!=undefined && row.orderstatus == name){
                    				result = value;
                    			}
                    		});
                    		return result;
                    	}
                  },
                  {"data": "status", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var result = '';
                  		$.each(row.invoiceinfoenum, function(name, value) {
                  			if(row.status == name){
                  				result = value;
                  			}
                  		});
                  		return result;
                  	}
                  },
                  {"data": "remark", "bSortable": false,
                	  render:function(data, type, row, meta) {
               		   var result = '<span data-toggle="tooltip" data-placement="left" title="'+row.remark+'">'+row.remark+'<span>';
	                  	  return result;
	                  }
                  }
        ],
    columnDefs: [{
  	//   指定第一列，从0开始，0表示第一列，1表示第二列……
        targets: 11,
        render: function(data, type, row, meta) {
            return '<a style="cursor:pointer;" onclick="openshouInvoiceEdit('+row.id+');">编辑</a>'
        }
    }]
});
}
function shouInvoiceLoad(){
	shouInvoiceTable.ajax.reload();
}

function openshouInvoiceEdit(id){
	layer.open({
        type: 2,
        title:false,
        skin: false, //加上边框
        closeBtn:false,//默认 右上角关闭按钮 是否显示
        shadeClose:true,
        scrollbar: false,
        area: ['987px', '620px'],
        content: BASE_PATH + '/admin/international/invoice/shouInvoice.html?id='+id
    });
}
$('#shouInvoiceSearch').click(function(){
	var div = $(this).parent().parent();
	var startdate = div.find('[name=startdate]').val();
	var enddate = div.find('[name=enddate]').val();
	var searchInfo = div.find('[name=searchInfo]').val();
	var status = $('#status').val();
	var shouinvoicestatus = $('#shouinvoicestatus').val();
	var param = {
			ordersstatus:status,
			startdate:startdate,
			enddate:enddate,
			searchInfo:searchInfo,
			shouinvoicestatus:shouinvoicestatus
	};
	shouInvoiceTable.settings()[0].ajax.data = param;
	shouInvoiceTable.ajax.reload();
});
