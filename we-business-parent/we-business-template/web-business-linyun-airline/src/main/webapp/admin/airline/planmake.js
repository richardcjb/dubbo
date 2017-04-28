//计划制作表格初始化
var datatable1;
function initDatatable1() {
    datatable1 = $('#example3').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
        "stripeClasses": [ 'strip1','strip2' ],
        "language": {
            "url": BASE_PATH + "/public/plugins/datatables/cn.json"
        },
        "ajax": {
            "url": BASE_PATH + "/admin/customneeds/listPlanMakeData.html",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "fnDrawCallback"    : function(){
        	var api = this.api();
        	var startIndex= api.context[0]._iDisplayStart;
   	       　　  api.column(0).nodes().each(function(cell, i) {
   	       　　　　cell.innerHTML = startIndex + i + 1; 
   	       　　});
      	},
        "columns": [
                    {"data": "xuhao", "bSortable": false},
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
                    		/*var leavesdate = new Date(row.leavesdate);
                    		var backsdate = new Date(row.backsdate);
                    		var MM = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec'][leavesdate.getMonth()];
                    		var week = ['MO','TU','WE','TH','FR','SA','SU'][leavesdate.getUTCDay()]
                    		var MM2 = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec'][backsdate.getMonth()];
                    		var week2 = ['MO','TU','WE','TH','FR','SA','SU'][backsdate.getUTCDay()]
                    		var result = '<ul><li style="list-style:none;">'+(week+leavesdate.getDate() + MM)+'</li>'
                    		+'<li style="list-style:none;">'+(week2+backsdate.getDate() + MM2)+'</li>'
                    		+'</ul>';*/
                    		return result;
                    	}
                    },
                    {"data": "leavescity", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			result += '<li style="list-style:none;">'+value.ailinenum+'</li>';
                    		});
                    		result += '</ul>';
                    		/*var result = '<ul>'
                        		+'<li style="list-style:none;">'+row.leaveairline+'</li>'
                        		+'<li style="list-style:none;">'+row.backairline+'</li>'
                        		+'</ul>';*/
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
                    		/*var result = '<ul>' 
                        		+'<li style="list-style:none;">'+(row.leavescity +'/'+ row.backscity)+'</li>'
                        		+'<li style="list-style:none;">'+(backleavecity +'/'+ backbackcity)+'</li>'
                        		+'</ul>';*/
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
                    		/*var result = '<ul>' 
                        		+'<li style="list-style:none;">'+(row.lleavetime +'/'+ row.lbacktime)+'</li>'
                        		+'<li style="list-style:none;">'+(row.bleavetime +'/'+ row.bbacktime)+'</li>'
                        		+'</ul>';*/
                    		return result;
                    	}	
                    },
                    {"data": "peoplecount", "bSortable": false},
                    {"data": "foc", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '否';
                    		if(row.foc == 1){
                    			result = '16/1';
                    		}
                    		return result;
                    	}
                    },
                    {"data": "dayscount", "bSortable": false},
                    {"data": "travelname", "bSortable": false},
                    {"data": "unioncity", "bSortable": false}
            ],
        columnDefs: [{
    		targets: 0,
            render: function(data, type, row, meta) {
                return null
            }
    	}]
    });
}
//加载计划制作列表
initDatatable1();
//加载所有的select2
initSelect2();
//加载旅行社智能下拉
function initSelect2(){
	$('.addMake').each(function(i){
		if($('.addMake').length - 1 == i){
			$("#travelname"+i).select2({
				ajax : {
					url : BASE_PATH + "/admin/customneeds/getTravelNameSelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							travelname : params.term, // search term
							page : params.page
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						var selectdata = $.map(data, function (obj) {
							  obj.id =  obj.shortName; // replace pk with your identifier
							  obj.text =  obj.shortName; // replace pk with your identifier
							  return obj;
						});
						return {
							results : selectdata
						};
					},
					cache : false
				},
				
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", //设置 提示语言
				maximumSelectionLength : 1, //设置最多可以选择多少项
				tags : false //设置必须存在的选项 才能选中
			});
			//加载出发航班号下拉
			$("#leaveairline" + i).select2({
				ajax : {
					url : BASE_PATH + "/admin/customneeds/getAirLineSelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						var backairline = $('#backairline'+i).val();
						if(backairline){
							backairline = backairline.join(',');
						}
						return {
							exname : backairline,
							airlinename : params.term, // search term
							page : params.page
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						var selectdata = $.map(data, function (obj) {
							  obj.id = obj.airlinenum; // replace pk with your identifier
							  obj.text = obj.airlinenum; // replace pk with your identifier
							  return obj;
						});
						return {
							results : selectdata
						};
					},
					cache : false
				},
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", //设置 提示语言
				maximumSelectionLength : 1, //设置最多可以选择多少项
				tags : false //设置必须存在的选项 才能选中
			});
			//加载返程航班下拉
			$("#backairline" + i).select2({
				ajax : {
					url : BASE_PATH + "/admin/customneeds/getAirLineSelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						var leaveairline = $('#leaveairline'+i).val();
						if(leaveairline){
							leaveairline = leaveairline.join(',');
						}
						return {
							exname : leaveairline,
							airlinename : params.term, // search term
							page : params.page
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						var selectdata = $.map(data, function (obj) {
							  obj.id = obj.airlinenum; // replace pk with your identifier
							  obj.text = obj.airlinenum; // replace pk with your identifier
							  return obj;
						});
						return {
							results : selectdata
						};
					},
					cache : false
				},
				
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", //设置 提示语言
				maximumSelectionLength : 1, //设置最多可以选择多少项
				tags : false //设置必须存在的选项 才能选中
			});
			//加载起飞城市下拉
			$("#leavescity" + i).select2({
				ajax : {
					url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						var backscity = $('#backscity'+i).val();
						if(backscity){
							backscity = backscity.join(',');
						}
						return {
							exname : backscity,
							cityname : params.term, // search term
							page : params.page
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						var selectdata = $.map(data, function (obj) {
							  obj.id = obj.dictCode; // replace pk with your identifier
							  obj.text = obj.dictCode+'-'+obj.englishName+'-'+obj.countryName; // replace pk with your identifier
							  return obj;
						});
						return {
							results : selectdata
						};
					},
					cache : false
				},
				
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", //设置 提示语言
				maximumSelectionLength : 1, //设置最多可以选择多少项
				tags : false //设置必须存在的选项 才能选中
			});
			//加载降落城市下拉
			$("#backscity" + i).select2({
				ajax : {
					url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						var leavescity = $('#leavescity'+i).val();
						if(leavescity){
							leavescity = leavescity.join(',');
						}
						return {
							exname : leavescity,
							cityname : params.term, // search term
							page : params.page
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						var selectdata = $.map(data, function (obj) {
							  obj.id = obj.dictCode; // replace pk with your identifier
							  obj.text = obj.dictCode+'-'+obj.englishName+'-'+obj.countryName; // replace pk with your identifier
							  return obj;
						});
						return {
							results : selectdata
						};
					},
					cache : false
				},
				
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", //设置 提示语言
				maximumSelectionLength : 1, //设置最多可以选择多少项
				tags : false //设置必须存在的选项 才能选中
			});
			//回程出发城市下拉
			$("#backleavecity" + i).select2({
				ajax : {
					url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						var backbackcity = $('#backbackcity'+i).val();
						if(backbackcity){
							backbackcity = backbackcity.join(',');
						}
						return {
							exname : backbackcity,
							cityname : params.term, // search term
							page : params.page
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						var selectdata = $.map(data, function (obj) {
							obj.id = obj.dictCode; // replace pk with your identifier
							obj.text = obj.dictCode+'-'+obj.englishName+'-'+obj.countryName; // replace pk with your identifier
							return obj;
						});
						return {
							results : selectdata
						};
					},
					cache : false
				},
				
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", //设置 提示语言
				maximumSelectionLength : 1, //设置最多可以选择多少项
				tags : false //设置必须存在的选项 才能选中
			});
			//回程抵达城市下拉
			$("#backbackcity" + i).select2({
				ajax : {
					url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						var backleavecity = $('#backleavecity'+i).val();
						if(backleavecity){
							backleavecity = backleavecity.join(',');
						}
						return {
							exname : backleavecity,
							cityname : params.term, // search term
							page : params.page
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						var selectdata = $.map(data, function (obj) {
							obj.id = obj.dictCode; // replace pk with your identifier
							obj.text = obj.dictCode+'-'+obj.englishName+'-'+obj.countryName; // replace pk with your identifier
							return obj;
						});
						return {
							results : selectdata
						};
					},
					cache : false
				},
				
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", //设置 提示语言
				maximumSelectionLength : 1, //设置最多可以选择多少项
				tags : false //设置必须存在的选项 才能选中
			});
			//加载联运城市下拉
			$("#unioncity" + i).select2({
				ajax : {
					url : BASE_PATH + "/admin/customneeds/getUnionCitySelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							cityname : params.term, // search term
							page : params.page
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						var selectdata = $.map(data, function (obj) {
							  obj.id = obj.dictCode; // replace pk with your identifier
							  var text = obj.dictCode;
							  if(obj.englishName){
								  text = obj.dictCode+'-'+obj.englishName+'-'+obj.countryName;
							  }
							  obj.text = text; // replace pk with your identifier
							  return obj;
						});
						return {
							results : selectdata
						};
					},
					cache : false
				},
				
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", //设置 提示语言
				maximumSelectionLength : 1, //设置最多可以选择多少项
				tags : false, //设置必须存在的选项 才能选中
			});
			//初始化起始日期
			/*var startdatepicker = new Pikaday({
			    field: document.getElementById('startdate' + i),
			    firstDay: 1,
			    minDate: new Date(),
			    maxDate: new Date('2120-12-31'),
			    yearRange: [2000,2020]
			});
			//初始化结束日期
			var enddatepicker = new Pikaday({
			    field: document.getElementById('enddate' + i),
			    firstDay: 1,
			    minDate: new Date('2000-01-01'),
			    maxDate: new Date('2120-12-31'),
			    yearRange: [2000,2020]
			});*/
			//初始化小日历
			new Kalendae({
			    attachTo:document.getElementById('minCalender' + i),
			    months:3,
			    direction:'today-future',
			    mode:'multiple',
			    subscribe: {
			         'date-clicked': function (date) {
			             console.log(date, this.getSelected());
			             var selectdate = this.getSelected();
			             if(selectdate){
			            	 if(selectdate.indexOf(FormatDate(date))!=-1){
			            		 selectdate = selectdate.substr(0,selectdate.indexOf(FormatDate(date))-2);
			            	 }else{
			            		 selectdate += ',' + FormatDate(date);
			            	 }
			             }else{
			            	 selectdate += FormatDate(date);
			             }
			             console.log(selectdate);
			             $('#calenderdate' + i).val(selectdate);
			             // return false;
			         }
			     }//点击 获取到当天的时间
			     
			    //selected:[Kalendae.moment().subtract({M:1}), Kalendae.moment().add({M:1})]
			});
			 //$('.kalendae').append('<input type="button" class="btn btn-sm btn-primary celenderBtn" value="确定">');
			//计划执着 隐藏日历按钮
		    $('#hidebutton'+i).click(function(){
		      $('#minCalender'+i).slideUp("slow");
		      $(this).hide();
		      $('#showbutton'+i).slideDown("slow");
		    });

		    //计划执着 隐藏日历按钮
		    $('#showbutton'+i).click(function(){
		      $('#minCalender'+i).slideDown("slow");
		      $(this).hide();
		      $('#hidebutton'+i).slideDown("slow");
		    });
		}
	});
	
}

//计划制作的 时间选择  obj当前div对象   status  是否显示过渡效果
function select_change(obj,status){
   var seleValue=$(obj).find("option:selected").attr("value");
   var addMake_aa = $(obj).closest(".aa") ; 
   if (seleValue==2) {
        addMake_aa.find('.checkWeek').slideUp("slow");//checked hide
        addMake_aa.find('.hidnCalendar').slideDown("slow");//div show
        addMake_aa.find('.hidnBtn').slideDown("slow");//show 隐藏日历 按钮
        addMake_aa.find('[name=congcong]').hide();//show 隐藏开始日期
        //addMake_aa.find('[name=startenddate]').hide();//show 隐藏结束日期
     }else{
    	 if(status){
    		 addMake_aa.find('.checkWeek').show();//checked show
    		 addMake_aa.find('.hidnCalendar').hide();//div hide
    		 addMake_aa.find('.hidnBtn').hide();//hide 隐藏日历 按钮
    		 addMake_aa.find('.showBtn').hide();
    		 addMake_aa.find('[name=congcong]').show();//show 显示开始日期
    	     //addMake_aa.find('[name=startenddate]').show();//show 显示结束日期
    	 }else{
    		 addMake_aa.find('.checkWeek').slideDown("slow");//checked show
    		 addMake_aa.find('.hidnCalendar').slideUp("slow");//div hide
    		 addMake_aa.find('.hidnBtn').slideUp("slow");//hide 隐藏日历 按钮
    		 addMake_aa.find('.showBtn').slideUp("slow");
    		 addMake_aa.find('[name=congcong]').show();//show 显示开始日期
    	     //addMake_aa.find('[name=startenddate]').show();//show 显示结束日期
    	 }
  };
}

$(function () {
	//添加 addMake
	$('.addIcon').click(function(){
	   var clonediv = $('.addMake').first();
	   var divTest =  $('.addMake').last(); 
	   var newDiv = clonediv.clone(false,true);
	   divTest.after(newDiv);
	   $('.addMake').each(function(i){
		   if($('.addMake').length - 1 == i){
			   $(this).find('.addIcon').remove();
			   $(this).find('.removIcon').remove();
			   $('.addMake').first().find('.removIcon').hide();
			   $(this).children().first().before('<a href="javascript:;" name="closeButton" class="removeBtn glyphicon glyphicon-minus removIcon removAddMake"></a><!--删除div按钮->');
			   //设置新的旅行社Id
			   var travelname = $(this).find('[name=travelname]');
			   travelname.attr("id","travelname"+i);
			   $('#travelname'+i).next().remove();
			   //人数置空
			   $(this).find('[name=peoplecount]').val('');
			   //天数置空
			   $(this).find('[name=dayscount]').val('');
			   //设置新的返回航班下拉框ID
			   var backairline = $(this).find('[name=backairline]');
			   backairline.attr("id","backairline"+i);
			   $('#backairline'+i).next().remove();
			   //只显示一条
			   $(this).find('.addCityAirline').each(function(j){
				   if(j == 0){
					   //设置新的出发航班Id
					   var leaveairline = $(this).find('[name=leaveairline]');
					   leaveairline.attr("id","leaveairline"+i);
					   $('#leaveairline'+i).next().remove();
					   //设置新的去程出发城市下拉ID
					   var leavescity = $(this).find('[name=leavescity]');
					   leavescity.attr("id","leavescity"+i);
					   $('#leavescity'+i).next().remove();
					   //设置新的去程抵达城市下拉框ID
					   var backscity = $(this).find('[name=backscity]');
					   backscity.attr("id","backscity"+i);
					   $('#backscity'+i).next().remove();
				   }else{
					   $(this).remove();
				   }
			   });
			   //出发日期置空
			   $(this).find('[name=setoffdate]').val('');
			   $(this).find('[name=setoffdate]').attr("id","setoffdate"+i);
			   //出发时间置空
			   $(this).find('[name=setofftime]').val('');
			   //设置新的返程出发城市下拉ID
			   var backleavecity = $(this).find('[name=backleavecity]');
			   backleavecity.attr("id","backleavecity"+i);
			   $('#backleavecity'+i).next().remove();
			   //设置新的返程抵达城市下拉框ID
			   var backbackcity = $(this).find('[name=backbackcity]');
			   backbackcity.attr("id","backbackcity"+i);
			   $('#backbackcity'+i).next().remove();
			   //设置新的联运城市下拉框ID
			   var unioncity = $(this).find('[name=unioncity]');
			   unioncity.attr("id","unioncity"+i);
			   $('#unioncity'+i).next().remove();
			   
			   var startenddate = $(this).find('[name=startenddate]');
			   startenddate.empty();
			   var startenddatestr = '<input id="startdate'+i+'" name="startdate" type="text" onFocus='+"WdatePicker({maxDate:'#F{$dp.$D(\\'enddate"+i+"\\')}'})"+' class="form-control input-sm timeWid inputdatestr startdatestr" placeholder="2016-11-05">'+ 
                   '- <input id="enddate'+i+'" name="enddate" type="text" onFocus='+"WdatePicker({minDate:'#F{$dp.$D(\\'startdate"+i+"\\')}'})" +' class="form-control input-sm timeWid inputdatestr enddatestr" placeholder="2016-12-01">';
			   startenddate.html(startenddatestr);
			   //设置新的开始日期控件ID
			   /*var startdate = $(this).find('[name=startdate]');
			   startdate.attr("id","startdate"+i);
			   $('#startdate'+i).val('');
			   //设置新的结束日期控件ID
			   var enddate = $(this).find('[name=enddate]');
			   enddate.attr("id","enddate"+i);*/
			   //startdate.attr("onFocus","WdatePicker({maxDate:'#F{$dp.$D(\\'enddate"+i+"\\')}'})");
			   //enddate.attr("onFocus","WdatePicker({minDate:'#F{$dp.$D(\\'startdate"+i+"\\')}'})");
			   //显示按钮 
			   var showbutton = $(this).find('[name=showbutton]');
			   showbutton.attr("id","showbutton"+i);
			   //隐藏按钮
			   var hidebutton = $(this).find('[name=hidebutton]');
			   hidebutton.attr("id","hidebutton"+i);
			   $('#enddate'+i).val('');
			   //清空选择每周
			   $(this).find('input[name=weekday]').each(function() { 
				   $(this).attr("checked", false); 
			  }); 
			   //设置新的小日历ID
			   var minCalender = $(this).find('[name=minCalender]');
			   minCalender.attr("id","minCalender"+i);
			   $('#minCalender'+i).empty();
			   //设置新的小日历隐藏域ID
			   var calenderdate = $(this).find('[name=calenderdate]');
			   calenderdate.attr("id","calenderdate"+i);
		   }
	   });
	   initSelect2();
	   select_change(newDiv.find('[id=weekSelect]'),1);
	   var No = parseInt(divTest.find("p").html())+1;   //假设你用p标签显示序号
	   newDiv.find("p").html(No);  
	   changeType();
	});
	//删除 addMake
	$(".removIcon").click(function() {
		if($('.addMake').length > 1){
			$(this).parent().remove();
		}else{
			layer.msg("最后一个不能删",{time: 2000});
		}
	});
});

$(document).on('click', '.removeBtn', function(e) {
	if($('.addMake').length > 1){
		if($('.addMake').length == 2){
			$('.addMake').first().find('.removIcon').show();
		}
		$(this).parent().remove();
	}
});

$(document).on('click', '.removeNeeds', function(e) {
	$(this).parent().remove();
});
//航班时间输入框绑定事件
$(document).on('keyup','.inputtime',function(e) {
	  var values = $(this).val();
	  if(values.length <= 4){
		  values = values.replace(/\D/g,'');
		  if(values.length == 4){
			  values += '/';
		  }
	  }else if(values.length <= 9){
		  var temp1 = values.substr(0,5);
		  var temp2 = values.substr(5,values.length);
		  temp2 = temp2.replace(/\D/g,'');
		  values = temp1 + temp2;
	  }else{
		  values = values.substr(0,9);
	  }
	  $(this).val(values);
});
$(document).on('click', '.addNeeds', function(e) {
	var addNeedDiv = $(this).parent().parent();
	var plandiv = addNeedDiv.parent();
	//获取最后一个div 将新克隆的放在其后
	var lastNeedDiv = plandiv.find('.addCityAirline').last();
	//获取首个div  将获取其ID
	var firstNeedDiv = plandiv.find('.addCityAirline').first();
	var setoffdate = firstNeedDiv.find('[name=setoffdate]').first();
	var setoffid = setoffdate.attr('id');
	setoffid = setoffid.charAt(setoffid.length-1);
	var newDiv = addNeedDiv.clone(false,true);
	lastNeedDiv.after(newDiv);
	//设置新的出发航班Id
    var leaveairline = newDiv.find('[name=leaveairline]');
    leaveairline.next().remove();
    //设置新的去程出发城市下拉ID
    var leavescity = newDiv.find('[name=leavescity]');
    leavescity.next().remove();
    //设置新的去程抵达城市下拉框ID
    var backscity = newDiv.find('[name=backscity]');
    backscity.next().remove();
    //出发日期置空
    newDiv.find('[name=setoffdate]').attr("onFocus","WdatePicker({minDate:'#F{$dp.$D(\\'setoffdate"+setoffid+"\\')}'})");
    newDiv.find('[name=setoffdate]').val('');
    //出发时间置空
    newDiv.find('[name=setofftime]').val('');
	newDiv.find('[name=leavescity]').select2({
		ajax : {
			url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				var backscity = newDiv.find('[name=backscity]').val();
				if(backscity){
					backscity = backscity.join(',');
				}
				return {
					exname : backscity,
					cityname : params.term, // search term
					page : params.page
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;
				var selectdata = $.map(data, function (obj) {
					  obj.id = obj.dictCode; // replace pk with your identifier
					  obj.text = obj.dictCode+'-'+obj.englishName+'-'+obj.countryName; // replace pk with your identifier
					  return obj;
				});
				return {
					results : selectdata
				};
			},
			cache : false
		},
		
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		minimumInputLength : 1,
		maximumInputLength : 20,
		language : "zh-CN", //设置 提示语言
		maximumSelectionLength : 1, //设置最多可以选择多少项
		tags : false //设置必须存在的选项 才能选中
	});
	//加载降落城市下拉
	newDiv.find('[name=backscity]').select2({
		ajax : {
			url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				var leavescity = newDiv.find('[name=leavescity]').val();
				if(leavescity){
					leavescity = leavescity.join(',');
				}
				return {
					exname : leavescity,
					cityname : params.term, // search term
					page : params.page
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;
				var selectdata = $.map(data, function (obj) {
					  obj.id = obj.dictCode; // replace pk with your identifier
					  obj.text = obj.dictCode+'-'+obj.englishName+'-'+obj.countryName; // replace pk with your identifier
					  return obj;
				});
				return {
					results : selectdata
				};
			},
			cache : false
		},
		
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		minimumInputLength : 1,
		maximumInputLength : 20,
		language : "zh-CN", //设置 提示语言
		maximumSelectionLength : 1, //设置最多可以选择多少项
		tags : false //设置必须存在的选项 才能选中
	});
	//加载出发航班号下拉
	newDiv.find('[name=leaveairline]').select2({
		ajax : {
			url : BASE_PATH + "/admin/customneeds/getAirLineSelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				return {
					airlinename : params.term, // search term
					page : params.page
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;
				var selectdata = $.map(data, function (obj) {
					  obj.id = obj.airlinenum; // replace pk with your identifier
					  obj.text = obj.airlinenum; // replace pk with your identifier
					  return obj;
				});
				return {
					results : selectdata
				};
			},
			cache : false
		},
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		minimumInputLength : 1,
		maximumInputLength : 20,
		language : "zh-CN", //设置 提示语言
		maximumSelectionLength : 1, //设置最多可以选择多少项
		tags : false //设置必须存在的选项 才能选中
	});
	newDiv.find('[name=addButton]').remove();
	var newdivlast = newDiv.children().last().after('<a href="javascript:;" name="closeButton" class="removeBtn glyphicon glyphicon-minus removAddMake removeNeeds"></a><!--删除div按钮->');
});
/*$(document).on('focus', '.startdatestr', function(e) {
	var enddateid = $(this).next(".inputdatestr").attr("id");
	return "WdatePicker({maxDate:'#F{$dp.$D(\'"+enddateid+"\')}'})";
});
$(document).on('focus', '.enddatestr', function(e) {
	var startdateid = $(this).prev(".inputdatestr").attr("id");
	return "WdatePicker({minDate:'#F{$dp.$D(\'"+startdateid+"\')}'})";
});*/
//制作计划 
function makePlan(){
	var divlength = $('.addMake').length;
	var teamtype = $('#teamtype').val();
	if(checkIsNull()){
		layer.load(2);
		//删除已制作的临时数据
		$.ajax({ 
			type: 'POST', 
			data: {}, 
			async:false,
			url: BASE_PATH + '/admin/customneeds/deleteMakePlanData.html',
	        success: function (data) { 
	        },
	        error: function (xhr) {
	        } 
	    });
		$('.addMake').each(function(i){
			var travelname = $(this).find('[name=travelname]').val();
			if(travelname){
				travelname = travelname.join(',');
			}
			var peoplecount = $(this).find('[name=peoplecount]').val();
			var dayscount = $(this).find('[name=dayscount]').val();
			//循环出发城市、抵达城市、航班号
			var cityairlinejson = '[';
			$(this).find('.addCityAirline').each(function(j){
				//航班号
				var leaveairline = $(this).find('[name=leaveairline]').val();
				if (leaveairline) {
					leaveairline = leaveairline.join(',');
				}
				//出发城市
				var leavescity = $(this).find('[name=leavescity]').val();
				if (leavescity) {
					leavescity = leavescity.join(',');
				}
				//抵达城市
				var backscity = $(this).find('[name=backscity]').val();
				if (backscity) {
					backscity = backscity.join(',');
				}
				//出发日期
				var setoffdate = $(this).find('[name=setoffdate]').val();
				//出发时间
				var setofftime = $(this).find('[name=setofftime]').val();
				cityairlinejson += '{"leaveairline":"'+leaveairline+'","leavescity":"'+leavescity+'","backscity":"'+backscity+'","setoffdate":"'+setoffdate+'","setofftime":"'+setofftime+'"},';
			});
			cityairlinejson = cityairlinejson.substring(0,cityairlinejson.length-1);
			cityairlinejson += ']';
			var unioncity = $(this).find('[name=unioncity]').val();
			if (unioncity) {
				unioncity = unioncity.join(',');
			}
			var foc = $(this).find('[name=foc]').val();
			//时间类型
			var weekSelect = $(this).find('[id=weekSelect]').val();
			var startdate = $(this).find('[name=startdate]').val();
			var enddate = $(this).find('[name=enddate]').val();
			var calenderdate = $(this).find('[name=calenderdate]').val();
			var weekday =[];    
			$(this).find('input[name="weekday"]:checked').each(function(){    
				weekday.push($(this).val());    
			});   
			if (weekday) {
				weekday = weekday.join(',');
			}
			if(weekSelect == 1){
				calenderdate = '';
			}else if(weekSelect == 2){
				weekday = '';
			}
			var param = {teamtype:teamtype,
						 travelname:travelname,
						 peoplecount:peoplecount,
						 dayscount:dayscount,
						 cityairlinejson:cityairlinejson,
						 foc:foc,
						 unioncity:unioncity,
						 startdate:startdate,
						 enddate:enddate,
						 timetype:weekSelect,
						 calenderdate:calenderdate,
						 weekday:weekday};
			$.ajax({ 
				type: 'POST', 
				data: param, 
				url: BASE_PATH + '/admin/customneeds/airlineMakePlan.html',
	            success: function (data) {
	            	datatable1.ajax.reload();
	            	if(divlength-1 == i){
	            		layer.closeAll('loading');
	            		layer.msg("制作成功",{time: 2000});
	            	}
	            },
	            error: function (xhr) {
	            	layer.msg("制作失败",{time: 2000});
	            } 
	        });
		});
	}
}
//判断填写是否正确
function checkIsNull(){
	var result = true;
	$('.addMake').each(function(i){
		/*var travelname = $(this).find('[name=travelname]').val();
		if(travelname){
			travelname = travelname.join(',');
		}else{
			layer.msg("请填写第"+(i+1)+"个旅行社",{time: 2000, icon:1});
			result = false;
			return false;
		}*/
		var teamtype = $('#teamtype').val();
		var peoplecount = $(this).find('[name=peoplecount]').val();
		if(!peoplecount){
			layer.msg("请填写第"+(i+1)+"个人数",{time: 2000});
			result = false;
			return false;
		}
		var dayscount = $(this).find('[name=dayscount]').val();
		if(!dayscount){
			layer.msg("请填写第"+(i+1)+"个天数",{time: 2000});
			result = false;
			return false;
		}
		/*var backairline = $(this).find('[name=backairline]').val();
		if (backairline) {
			backairline = backairline.join(',');
		}else{
			layer.msg("请填写第"+(i+1)+"个回程航班",{time: 2000, icon:1});
			result = false;
			return false;
		}*/
		var leavescity = $(this).find('[name=leavescity]').first().val();
		if (leavescity) {
			leavescity = leavescity.join(',');
		}else{
			layer.msg("请填写第"+(i+1)+"个去程出发城市",{time: 2000});
			result = false;
			return false;
		}
		var backscity = $(this).find('[name=backscity]').first().val();
		if (backscity) {
			backscity = backscity.join(',');
		}else{
			layer.msg("请填写第"+(i+1)+"个去程抵达城市",{time: 2000});
			result = false;
			return false;
		}
		var leaveairline = $(this).find('[name=leaveairline]').first().val();
		if (leaveairline) {
			leaveairline = leaveairline.join(',');
		}else{
			layer.msg("请填写第"+(i+1)+"个去程航班",{time: 2000});
			result = false;
			return false;
		}
		if(teamtype == 2){
			var setoffdate = $(this).find('[name=setoffdate]').first().val();
			if (!setoffdate) {
				layer.msg("请填写第"+(i+1)+"个出发日期",{time: 2000});
				result = false;
				return false;
			}
			var setofftime = $(this).find('[name=setofftime]').first().val();
			if (!setofftime) {
				layer.msg("请填写第"+(i+1)+"个时间",{time: 2000});
				result = false;
				return false;
			}
		}
		/*var backleavecity = $(this).find('[name=backleavecity]').val();
		if (backleavecity) {
			backleavecity = backleavecity.join(',');
		}else{
			layer.msg("请填写第"+(i+1)+"个返程出发城市",{time: 2000, icon:1});
			result = false;
			return false;
		}
		var backbackcity = $(this).find('[name=backbackcity]').val();
		if (backbackcity) {
			backbackcity = backbackcity.join(',');
		}else{
			layer.msg("请填写第"+(i+1)+"个返程抵达城市",{time: 2000, icon:1});
			result = false;
			return false;
		}*/
		/*var unioncity = $(this).find('[name=unioncity]').val();
		if (unioncity) {
			unioncity = unioncity.join(',');
		}else{
			layer.msg("请填写第"+(i+1)+"个联运城市",{time: 2000, icon:1});
			result = false;
			return false;
		}*/
		var weekSelect = $(this).find('[id=weekSelect]').val();
		var startdate = $(this).find('[name=startdate]').val();
		if(teamtype == 1){
			if(weekSelect==2){
				var calenderdate = $(this).find('[name=calenderdate]').val();
				if(!calenderdate){
					layer.msg("请选择第"+(i+1)+"个自由日期",{time: 2000});
					result = false;
					return false;
				}
			}else{
				if(!startdate){
					layer.msg("请填写第"+(i+1)+"个起始日期",{time: 2000});
					result = false;
					return false;
				}
				var enddate = $(this).find('[name=enddate]').val();
				if(!enddate){
					layer.msg("请填写第"+(i+1)+"个截止日期",{time: 2000});
					result = false;
					return false;
				}
				/*var weekday =[];    
			$(this).find('input[name="weekday"]:checked').each(function(){    
				weekday.push($(this).val());    
			});   
			if (weekday) {
				weekday = weekday.join(',');
			}
			if(!weekday){
				layer.msg("请选择第"+(i+1)+"个每周",{time: 2000, icon:1});
				result = false;
				return false;
			}*/
			}
		}
		/*var calenderdate = $(this).find('[name=calenderdate]').val();
		var weekday =[];    
		$(this).find('input[name="weekday"]:checked').each(function(){    
			weekday.push($(this).val());    
		});   
		if (weekday) {
			weekday = weekday.join(',');
		}
		if(!weekday && !calenderdate){
			layer.msg("请选择第"+(i+1)+"个每周或自由中的一种",{time: 2000, icon:1});
			result = false;
			return false;
		}*/
	});
	return result;
}
//保存计划
function savePlan(){
	var isplan = false;
	$.ajax({ 
		type: 'POST', 
		data: {}, 
		async:false,
		url: BASE_PATH + '/admin/customneeds/isHavePlanData.html',
        success: function (data) { 
        	if(data){
        		isplan = true;
        	}
        },
        error: function (xhr) {
        } 
    });
	if(isplan){
		layer.confirm('确定要保存计划吗?', {icon: 3, title:'提示'}, function(){
			$.ajax({ 
				type: 'POST', 
				data: {}, 
				url: BASE_PATH + '/admin/customneeds/savePlanData.html',
				success: function (data) { 
					layer.msg("保存成功",{time: 2000});
					datatable1.ajax.reload();
					datatable2.ajax.reload();
				},
				error: function (xhr) {
					layer.msg("保存失败",{time: 2000});
				} 
			});
		});
	}else{
		layer.msg("没有需要保存的计划",{time: 2000});
	}
}
//提示是否保存已经制作的计划
window.onbeforeunload = function(event) {
	var isplan = false;
	$.ajax({ 
		type: 'POST', 
		data: {}, 
		async:false,
		url: BASE_PATH + '/admin/customneeds/isHavePlanData.html',
        success: function (data) { 
        	if(data){
        		isplan = true;
        	}
        },
        error: function (xhr) {
        } 
    });
	if(isplan){
		return "计划制作内容尚未保存，确定离开此页面吗？";
	}
}
//关闭页面删除临时计划制作数据
window.onunload = function(event){
	$.ajax({ 
		type: 'POST', 
		data: {}, 
		async:false,
		url: BASE_PATH + '/admin/customneeds/deleteMakePlanData.html',
        success: function (data) { 
        },
        error: function (xhr) {
        } 
    });
}
//格式化日期
function FormatDate (strTime) {
    var date = new Date(strTime);
    return date.getFullYear()+"-"+zeroize((date.getMonth()+1),2)+"-"+zeroize(date.getDate(),2);
}
//位数不足  补0
function zeroize(value, length) {
    if (!length) length = 2;
    value = String(value);
    for (var i = 0, zeros = ''; i < (length - value.length); i++) {
        zeros += '0';
    }
    return zeros + value;
}
changeType();
//系列团、临时团选中事件
function changeType(){
	var teamtype = $('#teamtype').val();
	//系列团
	if(teamtype == '1'){
		$('.addMake').each(function(i){
			if($('.addMake').length-1 == i){
				$(this).find('[name=congcong]').show();
				$(this).find('[name=weekSelect]').show();
				$(this).find('.checkWeek').show();
				$(this).find('[name=teamtypehide]').hide();
				var clonediv = $(this).find('.addCityAirline').first();
				var newdiv = clonediv.clone();
				//设置新的出发航班Id
			    var leaveairline = newdiv.find('[name=leaveairline]');
			    leaveairline.next().remove();
			    //设置新的去程出发城市下拉ID
			    var leavescity = newdiv.find('[name=leavescity]');
			    leavescity.next().remove();
			    //设置新的去程抵达城市下拉框ID
			    var backscity = newdiv.find('[name=backscity]');
			    backscity.next().remove();
				$(this).find('.addCityAirline').each(function(i){
					if(i > 0){
						$(this).remove();
					}
				});
				newdiv.find('[name=leavescity]').select2({
					ajax : {
						url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
						dataType : 'json',
						delay : 250,
						type : 'post',
						data : function(params) {
							var backscity = newdiv.find('[name=backscity]').val();
							if(backscity){
								backscity = backscity.join(',');
							}
							return {
								exname : backscity,
								cityname : params.term, // search term
								page : params.page
							};
						},
						processResults : function(data, params) {
							params.page = params.page || 1;
							var selectdata = $.map(data, function (obj) {
								  obj.id = obj.dictCode; // replace pk with your identifier
								  obj.text = obj.dictCode+'-'+obj.englishName+'-'+obj.countryName; // replace pk with your identifier
								  return obj;
							});
							return {
								results : selectdata
							};
						},
						cache : false
					},
					
					escapeMarkup : function(markup) {
						return markup;
					}, // let our custom formatter work
					minimumInputLength : 1,
					maximumInputLength : 20,
					language : "zh-CN", //设置 提示语言
					maximumSelectionLength : 1, //设置最多可以选择多少项
					tags : false //设置必须存在的选项 才能选中
				});
				//加载降落城市下拉
				newdiv.find('[name=backscity]').select2({
					ajax : {
						url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
						dataType : 'json',
						delay : 250,
						type : 'post',
						data : function(params) {
							var leavescity = newdiv.find('[name=leavescity]').val();
							if(leavescity){
								leavescity = leavescity.join(',');
							}
							return {
								exname : leavescity,
								cityname : params.term, // search term
								page : params.page
							};
						},
						processResults : function(data, params) {
							params.page = params.page || 1;
							var selectdata = $.map(data, function (obj) {
								  obj.id = obj.dictCode; // replace pk with your identifier
								  obj.text = obj.dictCode+'-'+obj.englishName+'-'+obj.countryName; // replace pk with your identifier
								  return obj;
							});
							return {
								results : selectdata
							};
						},
						cache : false
					},
					
					escapeMarkup : function(markup) {
						return markup;
					}, // let our custom formatter work
					minimumInputLength : 1,
					maximumInputLength : 20,
					language : "zh-CN", //设置 提示语言
					maximumSelectionLength : 1, //设置最多可以选择多少项
					tags : false //设置必须存在的选项 才能选中
				});
				//加载出发航班号下拉
				newdiv.find('[name=leaveairline]').select2({
					ajax : {
						url : BASE_PATH + "/admin/customneeds/getAirLineSelect.html",
						dataType : 'json',
						delay : 250,
						type : 'post',
						data : function(params) {
							return {
								airlinename : params.term, // search term
								page : params.page
							};
						},
						processResults : function(data, params) {
							params.page = params.page || 1;
							var selectdata = $.map(data, function (obj) {
								  obj.id = obj.airlinenum; // replace pk with your identifier
								  obj.text = obj.airlinenum; // replace pk with your identifier
								  return obj;
							});
							return {
								results : selectdata
							};
						},
						cache : false
					},
					escapeMarkup : function(markup) {
						return markup;
					}, // let our custom formatter work
					minimumInputLength : 1,
					maximumInputLength : 20,
					language : "zh-CN", //设置 提示语言
					maximumSelectionLength : 1, //设置最多可以选择多少项
					tags : false //设置必须存在的选项 才能选中
				});
				clonediv.after(newdiv);
			}
		});
	//临时团
	}else{
		$('.addMake').each(function(){
			$(this).find('[name=teamtypehide]').show();
			$(this).find('[name=congcong]').hide();
			$(this).find('[name=weekSelect]').hide();
			$(this).find('.checkWeek').hide();
			$(this).find('.addCityAirline').each(function(i){
				if(i > 0){
					$(this).remove();
				}
			});
		});
	}
}