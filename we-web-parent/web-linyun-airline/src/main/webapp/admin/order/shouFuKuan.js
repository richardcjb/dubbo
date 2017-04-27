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
          "url": BASE_PATH + "/admin/inland/listShouFuKuanData.html",
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
                  {"data": "leavedate", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = '<ul>';
                  		$.each(row.leavedates, function(name, value) {
                			if(value && value != undefined){
                				result += '<li style="list-style:none;">'+value+'</li>';
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
                  			if(value && value.personcount != undefined){
                  				result += '<li style="list-style:none;">'+value.personcount+'</li>';
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
                  			if(value && value.incometotal != undefined){
                  				result += '<li style="list-style:none;">'+value.incometotal.toFixed(2)+'</li>';
                  			}
                  		});
                  		result += '</ul>';
                  		return result;
                  	}
                  },
                  {"data": "sum", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var result = '';
                  		if(row.sum && row.sum != undefined){
                  			result = row.sum.toFixed(2);
                  		}
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
                  {"data": "issuer", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var result = '';
                  		if(row.issuer){
                  			result = row.issuer;
                  		}
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
                  		$.each(row.invoiceenum, function(name, value) {
                  			if(row.invoicestatus == name){
                  				result += '/'+value;
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
          targets: 9,
          render: function(data, type, row, meta) {
              return '<a style="cursor:pointer;" onclick="openInvoice('+row.id+','+row.invoiceid+');">开发票</a>'
          }
      }],
  });
}
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
	        content: BASE_PATH + '/admin/inland/kaiInvoice.html?id='+invoiceid,
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
			content: BASE_PATH + '/admin/inland/openInvoice.html?id='+id,
			end:function(){
				shouFuKuanGatheringTable.ajax.reload(function(json){
					autoHighLoad($('#shouFuKuanGatheringTable'));
				});
			}
		});
	}
}
function loadshouFuKuanGatheringTable(){
	shouFuKuanGatheringTable.ajax.reload(function(json){
		autoHighLoad($('#shouFuKuanGatheringTable'));
	});
}
$('.shoukuansearch').click(function(){
	var div = $(this).parent().parent();
	var status = div.find('[name=status]').val();
	var startdate = div.find('[name=startdate]').val();
	var enddate = div.find('[name=enddate]').val();
	var searchInfo = div.find('[name=searchInfo]').val();
	var param = {
			status:status,
			startdate:startdate,
			enddate:enddate,
			searchInfo:searchInfo
	};
	shouFuKuanGatheringTable.settings()[0].ajax.data = param;
	shouFuKuanGatheringTable.ajax.reload(function(json){
		autoHighLoad($('#shouFuKuanGatheringTable'));
	});
});
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
    "autoWidth": false,
    "stripeClasses": [ 'strip1','strip2' ],
    "language": {
        "url": BASE_PATH + "/public/plugins/datatables/cn.json"
    },
    "ajax": {
        "url": BASE_PATH + "/admin/inland/listFuKuanData.html",
        "type": "post",
        "data": function (d) {
        	
        }
    },
    "columns": [
                {"data": "ordersnum", "bSortable": false},
                {"data": "pnr", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.pnr && row.pnr != undefined){
                			result = row.pnr;
                		}
                		return result;
                	}
                },
                {"data": "personcount", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.ailinenum && row.ailinenum != undefined){
                			result = row.ailinenum;
                		}
                		return result;
                	}
                },
                {"data": "hangduan", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
        				if(row.leavecity != undefined){
        					result += row.leavecity;
        				}
        				result += '/';
        				if(row.arrivecity != undefined){
        					result += row.arrivecity;
        				}
                		return result;
                	}
                },
                {"data": "leavetdate", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.leavetdate && row.leavetdate != undefined){
                			result = row.leavetdate;
                		}
                		return result;
                	}
                },
                {"data": "time", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
        				if(row.leavetime != undefined){
        					result += row.leavetime;
        				}
        				result += '/';
        				if(row.arrivetime != undefined){
        					result += row.arrivetime;
        				}
                		return result;
                	}
                },
                {"data": "customename", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.customename && row.customename != undefined){
                			result = row.customename;
                			result = '<span data-toggle="tooltip" data-placement="right" title="'+result+'">'+result+'<span>';
                		}
                		return result;
                	}
                },
                {"data": "costpricesum", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.costpricesum && row.costpricesum != undefined){
                			result = row.costpricesum.toFixed(2);
                			result = '<span data-toggle="tooltip" data-placement="right" title="'+result+'">'+result+'<span>';
                		}
                		return result;
                	}
                },
                {"data": "peoplecount", "bSortable": false,
                	render:function(data, type, row, meta) {
                  		var result = '';
                  		if(row.peoplecount){
                  			result = row.peoplecount;
                  		}
                  		return result;
                  	}
                },
                {"data": "status", "bSortable": false,
                	render:function(data, type, row, meta) {
                  		var result = '';
                  		$.each(row.paystatusenum, function(name, value) {
                  			if(row.orderpnrstatus == name){
                  				result = value;
                  			}
                  		});
                  		$.each(row.invoiceenum, function(name, value) {
                  			if(row.invoicestatus == name){
                  				result += '/'+value;
                  			}
                  		});
                  		return result;
                  	}
                },
                {"data": "issuer","bSortable": false,
                	render:function(data, type, row, meta) {
                  		var result = '';
                  		if(row.issuer){
                  			result = row.issuer;
                  		}
                  		return result;
                  	}
                },
                {"data":"active","bSortable":false,
                	render: function(data, type, row, meta) {
                        return '<a style="cursor:pointer;" onclick="receiveInvoice('+row.id+','+row.invoiceid+');">收发票</a>'
                    }
                }
        ]/*,
    "columnDefs": [
				{ "sWidth": "7.33%", "targets": [0] },
				{ "sWidth": "5.33%", "targets": [1] },
				{ "sWidth": "6.33%", "targets": [2] },
				{ "sWidth": "5.33%", "targets": [3] },
				{ "sWidth": "8.33%", "targets": [4] },
				{ "sWidth": "7.33%", "targets": [5] }, 
				{ "sWidth": "12.33%", "targets": [6] },
				{ "sWidth": "8.33%", "targets": [7] },
				{ "sWidth": "5.33%", "targets": [8] },
				{ "sWidth": "14.33%", "targets": [9] },
				{ "sWidth": "8.33%", "targets": [10] },
				{ "sWidth": "11.33%", "targets": [11] }
	]*/
});
}
function shoufukuanPay(){
	shouFuKuanPayTable.ajax.reload();
}
$('.fukuansearch').click(function(){
	var div = $(this).parent().parent();
	var status = div.find('[name=status]').val();
	var startdate = div.find('[name=startdate]').val();
	var enddate = div.find('[name=enddate]').val();
	var searchInfo = div.find('[name=searchInfo]').val();
	var param = {
			status:status,
			startdate:startdate,
			enddate:enddate,
			searchInfo:searchInfo
	};
	shouFuKuanPayTable.settings()[0].ajax.data = param;
	shouFuKuanPayTable.ajax.reload();
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
	        content: BASE_PATH + '/admin/inland/shouInvoice.html?id='+invoiceid,
	        end:function(){
	        	shouFuKuanPayTable.ajax.reload();
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
			content: BASE_PATH + '/admin/inland/receiveInvoice.html?id='+id,
			end:function(){
				shouFuKuanPayTable.ajax.reload();
			}
		});
	}
}
