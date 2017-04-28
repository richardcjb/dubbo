function checkBoxShow(){
	var taskShow = ${obj.checkBox.taskShow};
	var maxCShow = ${obj.checkBox.maxCShow};
	var minCShow = ${obj.checkBox.minCShow};
	if(taskShow){
		$("#taskId").css('display','block');
		$("#taskBoxId").attr('checked','checked');
		taskEventList();
	}
	if(maxCShow){
		$("#maxCId").css('display','block');
		$("#maxCalenderId").attr('checked','checked');
		$('#calendar').empty();
		calendarInit();
	}
	if(minCShow){
		$("#minCId").css('display','block');
		$("#minCalenderId").attr('checked','checked');
		minCalendarInit();
	}
}