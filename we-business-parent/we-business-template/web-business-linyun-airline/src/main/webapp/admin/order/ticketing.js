var drawerPayTable;
//初始化表格
initdrawerPayTable();
function initdrawerPayTable() {
	drawerPayTable = $('#drawerPayTable').DataTable({
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
            "url": BASE_PATH + "/admin/inland/listData.html",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "columns": [{"data": "id", "bSortable": false,
			        	"render": function (data, type, row, meta) {
			        		var result = '';
			        		var hiddenval = $('#checkedboxval').val();
			        		var splits = hiddenval.split(',');
			        		var flag = false;
			        		for(var i=0;i<splits.length;i++){
			        			if(splits[i] == row.id){
			        				flag = true;
			        			}
			        		}	
			        		if(flag){
			        			result = '<input type="checkbox"  class="checkchild" checked="true" value="' + row.id + '" />';
			        		}else{
			        			result = '<input type="checkbox"  class="checkchild" value="' + row.id + '" />';
			        		}
			                return result;
			            }
			        },
                    {"data": "ordersnum", "bSortable": false},
                    {"data": "pnr", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul id="tableUl"> ';
                    		$.each(row.pnrinfo, function(name, value) {
                    			if(value && value.pNR != undefined){
                    				result += '<li style="list-style:none;">'+value.pNR+'</li>';
                    			}
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "date", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul id="tableUl">';
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
                    		var result = '<ul id="tableUl">';
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
                    		var result = '<ul id="tableUl">';
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
                    		var result = '<ul id="tableUl">';
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
                    		var result = '<ul id="tableUl">';
                    		$.each(row.pnrinfo, function(name, value) {
                    			if(value && value.salespricesum != undefined){
                    				result += '<li style="list-style:none;">'+value.salespricesum.toFixed(2)+'</li>';
                    			}
                    		});
                    		result += '</ul>';
                    		return result; 
                    	}
                    },
                    {"data": "receivable", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '';
                    		if(row.incometotal){
                    			result = row.incometotal.toFixed(2);
                    		}
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
            ],
        columnDefs: [{
            //   指定第一列，从0开始，0表示第一列，1表示第二列……
        }],
        "infoCallback": function (settings, start, end, max, total, pre) {
    		var length = $(".checkchild:checked").length;
    		if(drawerPayTable.page.len() == length){
    			$(".checkall").prop("checked", true);
    		}else{
    			$(".checkall").prop("checked", false);
    			
    		}
    		return '显示第 '+start+' 至 '+end+' 条结果，共'+total+' 条 (每页显示 '+max+' 条)'
    	}
    });
}
$("tbody",$('#drawerPayTable')).on("dblclick","tr",function(event) {
	var item = drawerPayTable.row($(this).closest('tr')).data();
	var url = BASE_PATH;
	if(item.ordersstatus == 1){
		url += '/admin/inland/queryDetail.html?id='+item.id;
	}else{
		url = '/admin/inland/bookingDetail.html?id='+item.id;
	}
	window.open(url);
});

//控制复选框
$(".checkall").click(function () {
    var check = $(this).prop("checked");
    $(".checkchild").prop("checked", check);
    //隐藏域的值
    var hiddenval = $('#checkedboxval').val();
	if(check){
		var splits = hiddenval.split(',');
		$(".checkchild:checked").each(function(){
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
		$(".checkchild").each(function(){
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
	$('#checkedboxval').val(hiddenval);
});
//点击之后给隐藏域赋值
$(document).on('click', '.checkchild', function(e) {
	var hiddenval = $('#checkedboxval').val();
	var thisval = $(this).val();
	var check = $(this).prop("checked");
	if(check){
		if(!hiddenval){
			$('#checkedboxval').val(thisval);
		}else{
			$('#checkedboxval').val(hiddenval+','+thisval);
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
			$('#checkedboxval').val(ids);
		}else{
			$('#checkedboxval').val(hiddenval);
		}
	}
});
//点击出票加载出票表格
function loadTicking(){
	var param = {
			ordersstatus:4,
			ticketing:1
	};
	drawerPayTable.settings()[0].ajax.data = param;
	drawerPayTable.ajax.reload(function(json){
		autoHighLoad($('#drawerPayTable'));
	});
}
$('.fuKuanBtn').click(function(){
	var ids = $('#checkedboxval').val();
	if(!ids){
		layer.msg("请至少选中一条记录",{time: 2000});
	}else{
		$.ajax({ 
			type: 'POST', 
			data: {ids:ids}, 
			url: BASE_PATH + '/admin/inland/checkIsCommonCompany.html',
           success: function (data) { 
        	   if(data){
        		   layer.open({
        				type: 2,
        				title:false,
        				skin: false, //加上边框
        				closeBtn:false,//默认 右上角关闭按钮 是否显示
        				shadeClose:true,
        				scrollbar: false,
        				area: ['850px', '570px'],
        				content: BASE_PATH + '/admin/inland/seaInvoice.html?ids='+ids,
        				end:function(){
        					drawerPayTable.ajax.reload(function(json){
        						autoHighLoad($('#drawerPayTable'));
        					},false);
        					$('#checkedboxval').val('');
        		  	    }
        			});
        	   }else{
        		   layer.msg("请选择同一个客户的订单","",3000);
        	   }
           },
           error: function (xhr) {
           	layer.msg("提交失败","",3000);
           } 
       });
		
	}
});

$('.ticketingSearch').click(function(){
	var div = $(this).parent().parent();
	var startdate = div.find('[name=startdate]').val();
	var enddate = div.find('[name=enddate]').val();
	var searchInfo = div.find('[name=searchInfo]').val();
	var status = 4;
	var param = {
			ordersstatus:status,
			startdate:startdate,
			enddate:enddate,
			searchInfo:searchInfo,
			ticketing:1
	};
	drawerPayTable.settings()[0].ajax.data = param;
	drawerPayTable.ajax.reload(function(json){
		autoHighLoad($('#drawerPayTable'));
	});
});
function onkeyTicketingEnter(){
	var e = window.event || arguments.callee.caller.arguments[0];
    if(e && e.keyCode == 13){
 		$('#ticketingSearch').click();
    }
}
