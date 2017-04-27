var shouFuKuanGatheringTable;
//初始化表格
initshouFuKuanGatheringTable();
function initshouFuKuanGatheringTable() {
	shouFuKuanGatheringTable = $('#shouFuKuanGatheringTable').DataTable({
  	"searching":false,
  	"bLengthChange": false,
      "processing": true,
      "serverSide": true,
      "initComplete": function( settings, json ) {
      	 autoHighLoad($(this));
       },
       "infoCallback": function( settings, start, end, max, total, pre ) {
         	autoHighLoad($(this));
 			return '显示第 '+start+' 至 '+end+' 条结果，共 '+total+' 条 (每页显示 '+max+' 条)';
       },
      "stripeClasses": [ 'strip1','strip2' ],
      "language": {
          "url": BASE_PATH + "/public/plugins/datatables/cn.json"
      },
      "ajax": {
          "url": BASE_PATH + "/admin/international/payreceive/listShouFuKuanData.html",
          "type": "post",
          "data": function (d) {
          	
          }
      },
      "columns": [
                  {"data": "ordersnum", "bSortable": false,
                	  render:function(data, type, row, meta) {
                    		var result = '<ul> ';
                    		$.each(row.orders, function(name, value) {
                    			if(value.ordersnum && value.ordersnum != undefined){
                    				result += '<li style="list-style:none;">'+value.ordersnum+'</li>';
                    			}
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                  },
                  {"data": "leavedate", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = '<ul> ';
                		$.each(row.orders, function(name, value) {
                			if(value.leavesdate && value.leavesdate != undefined){
                				result += '<li style="list-style:none;">'+value.leavesdate+'</li>';
                			}
                		});
                		result += '</ul>';
                		return result;
                  	}
                  },
                  {"data": "personcount", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = '<ul>';
                  		$.each(row.orders, function(name, value) {
                  			if(value && value.actualnumber != undefined){
                  				result += '<li style="list-style:none;">'+value.actualnumber+'</li>';
                  			}
                  		});
                  		result += '</ul>';
                  		return result;
                  	}
                  },
                  {"data": "incometotal", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = '<ul>';
                  		$.each(row.orders, function(name, value) {
                  			if(value && value.currentpay != undefined){
                  				result += '<li style="list-style:none;">'+value.currentpay+'</li>';
                  			}
                  		});
                  		result += '</ul>';
                  		return result;
                  	}
                  },
                  {"data": "sum", "bSortable": false,
                	  render:function(data, type, row, meta) {
                    		var result = 0;
                    		$.each(row.orders, function(name, value) {
                    			if(value && value.currentpay != undefined){
                    				result += value.currentpay;
                    			}
                    		});
                    		return result;
                    	}
                  },
                  {"data": "customename", "bSortable": false,
                	  render:function(data, type, row, meta) {
                    		var result = '';
                    		if(row.customename){
                    			result = row.customename;
                    		}
                    		return result;
                    	}
                  },
                  {"data": "fullname", "bSortable": false},
                  {"data": "orderstatus", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var result = '';
                  		$.each(row.internationalstatusenum, function(name, value) {
                  			if(row.orderstatus && row.orderstatus != undefined && row.orderstatus == name){
                  				result = value;
                  			}
                  		});
                  		return result;
                  	  }
                  },
                  {"data": "status", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var result = '';
                  		$.each(row.receiveenum, function(name, value) {
                  			if(row.status == name){
                  				result = value;
                  			}
                  		});
                  		return result;
                  	  }
                  },{"data": "remark", "bSortable": false,
                	  render:function(data, type, row, meta) {
                		  return '';
                	  }
                  }
          ],
      columnDefs: [{
    	//   指定第一列，从0开始，0表示第一列，1表示第二列……
          targets: 10,
          render: function(data, type, row, meta) {
              return '<a style="cursor:pointer;" onclick="openInvoice('+row.id+','+row.invoiceid+');">开发票</a>'
          }
      }],
  });
}

function loadreceivedata(){
	shouFuKuanGatheringTable.ajax.reload(function(json){
		autoHighLoad($('#shouFuKuanGatheringTable'));
	});
}
$('#receiveSearch').click(function(){
	var div = $(this).parent().parent();
	var startdate = div.find('[name=startdate]').val();
	var enddate = div.find('[name=enddate]').val();
	var searchInfo = div.find('[name=searchInfo]').val();
	var status = $('#status').val();
	var receivestatus = $('#receivestatus').val();
	var param = {
			ordersstatus:status,
			startdate:startdate,
			enddate:enddate,
			searchInfo:searchInfo,
			receivestatus:receivestatus
	};
	shouFuKuanGatheringTable.settings()[0].ajax.data = param;
	shouFuKuanGatheringTable.ajax.reload(function(json){
		autoHighLoad($('#shouFuKuanGatheringTable'));
	});
});
//打开开发票页面
function openInvoice(id,invoiceid){
	if(invoiceid){
		layer.open({
	        type: 2,
	        title:false,
	        skin: false, //加上边框
	        closeBtn:false,//默认 右上角关闭按钮 是否显示
	        shadeClose:true,
	        scrollbar: false,
	        area: ['987px', '620px'],
	        content: BASE_PATH + '/admin/international/invoice/kaiInvoice.html?id='+invoiceid,
	        end:function(){
	        	shouFuKuanGatheringTable.ajax.reload(function(json){
	        		autoHighLoad($('#shouFuKuanGatheringTable'));
	        	});
	        }
	      });
	}else{
		layer.open({
			type: 2,
			title:false,
			skin: false, //加上边框
			closeBtn:false,//默认 右上角关闭按钮 是否显示
			shadeClose:true,
			scrollbar: false,
			area: ['987px', '620px'],
			content: BASE_PATH + '/admin/international/payreceive/openInvoice.html?id='+id,
			end:function(){
				shouFuKuanGatheringTable.ajax.reload(function(json){
					autoHighLoad($('#shouFuKuanGatheringTable'));
				});
			}
		});
	}
}
//付款表格
var shouFuKuanPayTable;
//初始化表格
initshouFuKuanPayTable();
function initshouFuKuanPayTable() {
	shouFuKuanPayTable = $('#shouFuKuanPayTable').DataTable({
	"searching":false,
	"bLengthChange": false,
    "processing": true,
    "serverSide": true,
    "stripeClasses": [ 'strip1','strip2' ],
    "language": {
        "url": BASE_PATH + "/public/plugins/datatables/cn.json"
    },
    "ajax": {
        "url": BASE_PATH + "/admin/international/payreceive/listFuKuanData.html",
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
                  {"data": "leavedate", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = '';
                		if(row.paydate && row.paydate != undefined){
                			result = row.paydate;
                		}
                  		return result;
                  	}
                  },
                  {"data": "peoplecount", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = '';
                		if(row.actualnumber && row.actualnumber != undefined){
                			result = row.actualnumber;
                		}
                  		return result;
                  	}
                  },
                  {"data": "incometotal", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = '';
                		if(row.currentpay && row.currentpay != undefined){
                			result = row.currentpay;
                		}
                  		return result;
                  	}
                  },
                  /*{"data": "sum", "bSortable": false,
                	  render:function(data, type, row, meta) {
                    		var result = 0;
                    		$.each(row.orders, function(name, value) {
                    			if(value && value.incometotal != undefined){
                    				result += value.incometotal;
                    			}
                    		});
                    		return result;
                    	}
                  },*/
                  {"data": "customename", "bSortable": false,
                	  render:function(data, type, row, meta) {
                    		var result = '';
                    		if(row.customename && row.customename != undefined){
                    			result = row.customename;
                    		}
                    		return result;
                    	}
                  },
                  {"data": "username", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var result = '';
                  		if(row.username && row.username != undefined){
                  			result = row.username;
                  		}
                  		return result;
                  	}
                  },
                  {"data": "orderstatus", "bSortable": false,
                	  render:function(data, type, row, meta) {
                    		var result = '';
                    		$.each(row.internationalstatusenum, function(name, value) {
                    			if(row.orderstatus == name){
                    				result = value;
                    			}
                    		});
                    		return result;
                	  }
                  },
                  {"data": "paystatus", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var result = '';
                  		$.each(row.receiveenum, function(name, value) {
                  			if(row.paystauts == name){
                  				result = value;
                  			}
                  		});
                  		return result;
                  	  }
                  },{"data": "remark", "bSortable": false,
                	  render:function(data, type, row, meta) {
                		  var result = '';
                  		  if(row.remark && row.remark != undefined){
                  		  	 result = row.remark;
                  		  }
                  		  return result;
                	  }
                  }
        ],
    columnDefs: [{
  	//   指定第一列，从0开始，0表示第一列，1表示第二列……
        targets: 9,
        render: function(data, type, row, meta) {
            return '<a style="cursor:pointer;" onclick="receiveInvoice('+row.id+','+row.invoiceid+');">收发票</a>'
        }
    }],
});
}
function shoufukuanPay(){
	shouFuKuanPayTable.ajax.reload();
}
$('#paySearch').click(function(){
	var div = $(this).parent().parent();
	var startdate = div.find('[name=startdate]').val();
	var enddate = div.find('[name=enddate]').val();
	var searchInfo = div.find('[name=searchInfo]').val();
	var status = $('#status').val();
	var paystatus = $('#paystatus').val();
	var param = {
			ordersstatus:status,
			startdate:startdate,
			enddate:enddate,
			searchInfo:searchInfo,
			paystatus:paystatus
	};
	shouFuKuanPayTable.settings()[0].ajax.data = param;
	shouFuKuanPayTable.ajax.reload(function(json){
		autoHighLoad($('#shouFuKuanPayTable'));
	});
});
function receiveInvoice(id,invoiceid){
	if(invoiceid){
		layer.open({
	        type: 2,
	        title:false,
	        skin: false, //加上边框
	        closeBtn:false,//默认 右上角关闭按钮 是否显示
	        shadeClose:true,
	        scrollbar: false,
	        area: ['987px', '620px'],
	        content: BASE_PATH + '/admin/international/invoice/shouInvoice.html?id='+invoiceid,
	        end:function(){
	        	shouFuKuanPayTable.ajax.reload(function(json){
	        		autoHighLoad($('#shouFuKuanPayTable'));
	        	});
	        }
	    });
	}else {
		layer.open({
			type: 2,
			title:false,
			skin: false, //加上边框
			closeBtn:false,//默认 右上角关闭按钮 是否显示
			shadeClose:true,
			scrollbar: false,
			area: ['987px', '620px'],
			content: BASE_PATH + '/admin/international/payreceive/receiveInvoice.html?id='+id,
			end:function(){
				shouFuKuanPayTable.ajax.reload(function(json){
					autoHighLoad($('#shouFuKuanPayTable'));
				});
			}
		});
	}
}
