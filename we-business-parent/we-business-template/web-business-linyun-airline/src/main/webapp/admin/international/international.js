var internationalTable;
function initInternationalTable(){
	internationalTable = $('#internationalTable').DataTable({
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
            "url": BASE_PATH + "/admin/international/internationalListData.html",
            "type": "post",
            "data": function (d) {
            }
        },
        "fnDrawCallback" : function(){
        	var api = this.api();
        	var startIndex= api.context[0]._iDisplayStart;
   	       　　  api.column(0).nodes().each(function(cell, i) {
   	       　　　　cell.innerHTML = startIndex + i + 1; 
   	       　　});
      	},
        "columns": [
                    {"data": "xuhao", "bSortable": false},
                    {"data": "ordersnum", "bSortable": false},
                    {"data": "leavesdate", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '<ul>';
                    		var MM = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEPT', 'OCT', 'NOV', 'DEC'];
                    		var week = ['MO','TU','WE','TH','FR','SA','SU'];
                    		$.each(row.airinfo, function(name, value) {
                    			var leavedate = new Date(value.leavedate);
                    			result += '<li style="list-style:none;">'+(week[leavedate.getUTCDay()]+leavedate.getDate() + MM[leavedate.getMonth()])+'</li>';
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "leavescity", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			if(value.ailinenum && value.ailinenum != undefined){
                    				result += '<li style="list-style:none;">'+value.ailinenum+'</li>';
                    			}else{
                    				result += '<li style="list-style:none;"></li>';
                    			}
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "backsdate", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			result += '<li style="list-style:none;">'+(value.leavecity+'/'+value.arrvicity)+'</li>';
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "backscity", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			result += '<li style="list-style:none;">';
                    			if(value.leavetime && value.leavetime != undefined){
                    				result += value.leavetime;
                    			}
                    			result += '/';
                    			if(value.arrivetime && value.arrivetime != undefined){
                    				result += value.arrivetime;
                    			}
                    			result += '</li>';
                    		});
                    		result += '</ul>';
                    		return result;
                    	}	
                    },
                    {"data": "peoplecount", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result='';
                    		if(row.peoplecount && row.peoplecount != undefined){
                    			result = row.peoplecount;
                    		}
                    		return result;
                    	}
                    },
                    {"data": "foc", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '否';
                    		if(row.foc == 1){
                    			result = '16/1';
                    		}
                    		return result;
                    	}
                    },
                    {"data": "dayscount", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result='';
                    		if(row.dayscount && row.dayscount != undefined){
                    			result = row.dayscount;
                    		}
                    		return result;
                    	}
                    },
                    {"data": "ordersstatus", "bSortable": false,
                    	render:function(data, type, row, meta) {
                      		var result = '';
                      		$.each(row.orderstatusenum, function(name, value) {
                      			if(row.ordersstatus == name){
                      				result = value;
                      			}
                      		});
                      		return result;
                      	  }
                    },
                    {"data": "travelname", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result='';
                    		if(row.travelname){
                    			result = row.travelname;
                    		}
                    		return result;
                    	}
                    },
                    {"data": "unioncity", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result='';
                    		if(row.unioncity){
                    			result = row.unioncity;
                    		}
                    		return result;
                    	}
                    }
            ],
        columnDefs: [{
    		targets: 0,
            render: function(data, type, row, meta) {
                return null
            }
    	}]
    });
}
initInternationalTable();
//加载列表数据
function loadDataTable(status){
	$('#status').val(status);
	var param = {
			ordersstatus:status
	};
	internationalTable.settings()[0].ajax.data = param;
	internationalTable.ajax.reload();
	
}
//点击行跳转到详情页
$("tbody",$('#internationalTable')).on("dblclick","tr",function(event) {
	var item = internationalTable.row($(this).closest('tr')).data();
	var url = BASE_PATH + '/admin/international/internationalDetail.html?orderid='+item.ordernumber;
	window.open(url);
});
$('#searchOrder').click(function(i){
	var div = $(this).parent().parent();
	var startdate = div.find('[name=startdate]').val();
	var enddate = div.find('[name=enddate]').val();
	var searchInfo = div.find('[name=searchInfo]').val();
	var status = $('#status').val();
	var param = {
			ordersstatus:status,
			startdate:startdate,
			enddate:enddate,
			searchInfo:searchInfo
	};
	internationalTable.settings()[0].ajax.data = param;
	internationalTable.ajax.reload(function(json){
		autoHighLoad($('#internationalTable'));
	});
});