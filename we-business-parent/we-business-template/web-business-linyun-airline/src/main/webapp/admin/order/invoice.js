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
          "url": BASE_PATH + "/admin/inland/listKaiInvoiceData.html",
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
                				result += '<li style="list-style:none;"><span data-toggle="tooltip" data-placement="left" title="'+value.invoicenum+'">'+value.invoicenum+'<span></li>';
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
                  {"data": "incometotal", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = 0;
                  		$.each(row.invoicedetail, function(name, value) {
                  			if(value && value.invoicebalance != undefined){
                  				result = parseFloat(result) + parseFloat(value.invoicebalance);
                  			}
                  		});
                  		return result.toFixed(2);
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
                  		var result = row.paymentunit;
                  		var result = '<span data-toggle="tooltip" data-placement="left" title="'+result+'">'+result+'<span>';
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
                    		var remark = "";
                    		var remark = '<span data-toggle="tooltip" data-placement="left" title="'+row.remark+'">'+row.remark+'<span>';
                    		return remark;
                    	}
                  }
          ],
      columnDefs: [{
    	//   指定第一列，从0开始，0表示第一列，1表示第二列……
          targets: 10,
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
$('.openinvoicesearch').click(function(){
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
        content: BASE_PATH + '/admin/inland/kaiInvoice.html?id='+id,
        end:function(){
        	kaiInvoiceTable.ajax.reload(function(json){
        		autoHighLoad($('#kaiInvoiceTable'));
        	});
        }
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
    "language": {
        "url": BASE_PATH + "/public/plugins/datatables/cn.json"
    },
    "ajax": {
        "url": BASE_PATH + "/admin/inland/listShouInvoiceData.html",
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
                {"data": "fullname", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.fullname && row.fullname != undefined) {
                			result =row.fullname;
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
                  		var result = '';
                  		if(row.remark){
                  			result = row.remark;
                  			var result = '<span data-toggle="tooltip" data-placement="left" title="'+result+'">'+result+'<span>';
                  		}
                  		return result;
                  	}
                }
        ],
    columnDefs: [{
  	//   指定第一列，从0开始，0表示第一列，1表示第二列……
        targets: 11,
        render: function(data, type, row, meta) {
            return '<a style="cursor:pointer;" onclick="openshouInvoiceEdit('+row.invoiceid+');">编辑</a>'
        }
    }]
});
}
function shouInvoiceLoad(){
	shouInvoiceTable.ajax.reload();
}
$('.receiveinvoicesearch').click(function(){
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
	shouInvoiceTable.settings()[0].ajax.data = param;
	shouInvoiceTable.ajax.reload();
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
        content: BASE_PATH + '/admin/inland/shouInvoice.html?id='+id,
        end:function(){
        	shouInvoiceTable.ajax.reload();
        }
    });
}
/*//收发票 搜索按钮
$("#shouSearchInvoiceBtn").on('click', function () {
	alert(111);
	var status = $("#shouInvoiceSelect").val();
	var username = $("#username").val();
	var shouInvoiceBeginDate = $("#shouInvoiceBeginDate").val();
	var shouInvoiceEndDate = $("#shouInvoiceEndDate").val();
	var PNR = $("#paymentunitId").val();
	var paymentunit = $("#paymentunitId").val();
    var param = {
		        "status":status,
		        "username":username,
		        "shouInvoiceBeginDate":shouInvoiceBeginDate,
		        "shouInvoiceEndDate":shouInvoiceEndDate,
				"PNR": PNR,
				"paymentunit": paymentunit
		    };
	alert(status);
    if(status==3 || status==4){
    	shouInvoiceTable.settings()[0].ajax.data = param;
    	shouInvoiceTable.ajax.reload(
    			function(json){
    				autoHighLoad($('#shouInvoiceTable'));
    			}
    	);
    }
    
});


清除 开发票   检索项
$('#shouEmptyBtn').click(function(){
	clearSearchTxt("shouInvoiceSelect","username", "shouInvoiceBeginDate", "shouInvoiceEndDate", "paymentunitId");
});

//清空搜索项函数
function clearSearchTxt(selectId,selectUsername ,beginDateId, endDateId, inputId){
	$("#"+selectId+" option:first").prop("selected", 'selected');  
	$("#"+selectUsername+" option:first").prop("selected", 'selected');  
	$("#"+beginDateId).val("");
	$("#"+endDateId).val("");
	$("#"+inputId).val("");
}*/
