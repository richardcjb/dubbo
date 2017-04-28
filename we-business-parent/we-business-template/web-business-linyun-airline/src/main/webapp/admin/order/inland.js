var inlandCrossTable;
function initDatatable() {
	inlandCrossTable = $('#inlandCrossTable').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
        "autoWidth": false,
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
            "url": BASE_PATH + "/admin/inland/listData.html",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "columns": [
                    {"data": "ordersnum", "bSortable": false},
                    {"data": "pnr", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul id="tableUl"> ';
                    		$.each(row.pnrinfo, function(name, value) {
                    			if(value && value.pNR != undefined){
                    				//result += '<li style="list-style:none;">'+value.pNR+'</li>';
                    				//result += '<li style="list-style:none;"><span data-toggle="tooltip" data-placement="left" title="'+value.pNR+'">'+value.pNR+'<span></li>';
                    				result += '<li style="list-style:none;"><span data-toggle="tooltip" data-placement="right" title="'+value.pNR+'">'+value.pNR+'<span></li>';
                    			}
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "date", "bSortable": false,
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
                    {"data": "airnum", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			if(value && value.ailinenum != undefined){
                    				result += '<li style="list-style:none;">'+value.ailinenum+'</li>';
                    			}
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "airsag", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.customerinfo, function(name, value) {
                    			if(value){
                    				result += '<li style="list-style:none;">';
                    				if(value.leavecity != undefined){
                    					result += value.leavecity;
                    				}
                    				result += '-';
                    				if(value.arrivecity != undefined){
                    					result += value.arrivecity;
                    				}
                    				result += '</li>';
                    			}
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "airtime", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			if(value){
                    				result += '<li style="list-style:none;">';
                    				if(value.leavetime != undefined){
                    					result += value.leavetime;
                    				}
                    				result += '-';
                    				if(value.arrivetime != undefined){
                    					result += value.arrivetime;
                    				}
                    				result += '</li>';
                    			}
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "salesprice", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.pnrinfo, function(name, value) {
                    			if(value && value.salespricesum != undefined){
                    				//result += '<li style="list-style:none;">'+value.salespricesum.toFixed(2)+'</li>';
                    				result += '<li style="list-style:none;"><span data-toggle="tooltip" data-placement="right" title="'+value.salespricesum.toFixed(2)+'">'+value.salespricesum.toFixed(2)+'<span></li>';
                    			}
                    		});
                    		result += '</ul>';
                    		return result; 
                    	}
                    },
                    {"data": "receivable", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '';
                    		if(row.receivable){
                    			//result = row.receivable.toFixed(2);
                    			result += '<span data-toggle="tooltip" data-placement="right" title="'+row.receivable.toFixed(2)+'">'+row.receivable.toFixed(2)+'<span>';
                    		}
                    		//result += '<span data-toggle="tooltip" data-placement="right" title="'+result+'">'+result+'<span>';
                    		return result; 
                    	}
                    },
                    {"data": "personcount", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '';
                    		if(row.personcount){
                    			result = row.personcount;
                    		}
                    		return result; 
                    	}
                    },
                    {"data": "ordersstatus", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '';
                    		$.ajax({ 
                    			type: 'POST', 
                    			data: {status:row.ordersstatus}, 
                    			async:false,
                    			url: BASE_PATH + '/admin/inland/formatOrderStatus.html',
                                success: function (data) { 
                                	result = data;
                                },
                                error: function (xhr) {
                                } 
                            });
                    		return result; 
                    	}
                    },
                    {"data": "username", "bSortable": false},
                    {"data": "telephone", "bSortable": false}
                    /*{"data": "action", "bSortable": false,
                    	render: function(data, type, row, meta) {
                        return '<a style="cursor:pointer;" onclick="edit('+row.id+','+row.ordersstatus+');">编辑</a>'
                    }}*/
            ],
        "columnDefs": [{ "sWidth": "10.33%",  "targets": [0] },
					   { "sWidth": "7.33%",  "targets": [1] },
					   { "sWidth": "8.33%",  "targets": [2] },
					   { "sWidth": "6.33%",  "targets": [3] },
					   { "sWidth": "8.33%",  "targets": [4] },
					   { "sWidth": "8.33%",  "targets": [5] },
					   { "sWidth": "8.33%",  "targets": [6] },
					   { "sWidth": "8.33%",  "targets": [7] },
					   { "sWidth": "8.33%",  "targets": [8] },
					   { "sWidth": "5.33%",  "targets": [9] },
					   { "sWidth": "6.33%",  "targets": [10] },
					   { "sWidth": "9.33%",  "targets": [11] }
						//{ "sWidth": "10.33%",  "targets": [12] }
                        /*{
            //   指定第一列，从0开始，0表示第一列，1表示第二列……
            targets: 12,
            render: function(data, type, row, meta) {
                return '<a style="cursor:pointer;" onclick="edit('+row.id+','+row.ordersstatus+');">编辑</a>'
            }
        }*/]
    });
}

	function loadDataTable(status){
		var param = {
				ordersstatus:status
		};
		inlandCrossTable.settings()[0].ajax.data = param;
		inlandCrossTable.ajax.reload(function(json){
			autoHighLoad($('#inlandCrossTable'));
		});
		$('#status').val(status);
	}
	$("#searchBtn").on('click', function () {
		var companyName = $("#companyName").val();
		var comType = $('#comType').val();
	    var param = {
	        "companyName": companyName,
			"comType" : comType
	    };
	    inlandCrossTable.settings()[0].ajax.data = param;
		inlandCrossTable.ajax.reload();
	});

$(function () {
    initDatatable();
});
$("tbody",$('#inlandCrossTable')).on("dblclick","tr",function(event) {
	var item = inlandCrossTable.row($(this).closest('tr')).data();
	var url = BASE_PATH;
	$.ajax({
		type : 'POST',
		data : {
			"id":item.id
		},
		async:false,
		dataType:'json',
		url : BASE_PATH+'/admin/inland/getOrderInfoById.html',
		success : function(data) {
			if(data.ordersstatus == 1){
				url += '/admin/inland/queryDetail.html?id='+item.id;
			}else{
				url = '/admin/inland/bookingDetail.html?id='+item.id;
			}
			window.open(url);
		},
		error : function() {
		}
	});
	
});
function edit(id,status){ 
	var url = BASE_PATH;
	if(status == 1){
		url += '/admin/inland/queryDetail.html?id='+id;
	}else{
		url = '/admin/inland/bookingDetail.html?id='+id;
	}
	window.open(url);
}
