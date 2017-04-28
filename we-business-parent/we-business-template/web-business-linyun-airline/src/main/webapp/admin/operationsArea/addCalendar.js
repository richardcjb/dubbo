$(document).ready(function(){
	var picker = new Pikaday(
			{
				field: document.getElementById('datepicker'),
				firstDay: 1,
				minDate: new Date('2000-01-01'),
				maxDate: new Date('2030-12-31'),
				yearRange: [2000,2099],
				onSelect: 	function() {
					var date = document.createTextNode(this.getMoment().format('YYYY-MM-DD hh:mm:ss') + ' '); //生成的时间格式化成 2013-09-25
					$('#datepicker').appendChild(date);
				}
			});
});
