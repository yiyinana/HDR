//饼状图
function Chart(data) {
	this.elementId = data.elementId;
	this.title = data.title;
	this.name = data.name;
	this.data = data.data;
}

Chart.prototype.init = function() {
	$(this.elementId)
			.highcharts(
					{
						credits : {
							text : '',
							href : ''
						},
						chart : {
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
						},
						title : {
							text : this.title
						},
						subtitle : {
							text : this.title
						},
						tooltip : {
							pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
						},
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								dataLabels : {
									enabled : true,
									format : '<b>{point.name}</b>: {point.percentage:.1f} %',
									style : {
										color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
												|| 'black'
									}
								}
							}
						},
						series : [ {
							type : 'pie',
							name : this.name,
							data : this.data
						} ]
					});
}
// 折线图
function LineChart(data) {
	this.elementId = data.elementId;
	this.title = data.title;
	this.subTitle = data.subTitle;
	this.data = data.data;
	this.x_Axis = data.lx_Axis;
	this.y_title = data.ly_title;

}
LineChart.prototype.init = function() {
	$(this.elementId).highcharts({
		credits : {
			text : '',
			href : ''
		},
		// 标题
		title : {
			text : this.title
		},
		// 副标题
		subtitle : {
			text : this.subTitle
		},
		// x轴的内容
		xAxis : {
			categories : this.x_Axis
		},
		// Y轴标题z
		yAxis : {
			title : {
				text : this.y_title
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		},
		legend : {
			layout : 'vertical',
			align : 'right',
			verticalAlign : 'middle',
			borderWidth : 0
		},
		series : this.data
	});
}
function Histogram(data) {
	this.elementId = data.elementId;
	this.data = data.data;
	this.title = data.title;
	this.subTitle = data.subTitle;
	this.x_Axis = data.hx_Axis;
	this.y_title = data.hy_title;
}
Histogram.prototype.init = function() {
	$(this.elementId)
			.highcharts(
					{
						chart : {
							type : 'column'
						},
						title : {
							text : this.title
						},
						subtitle : {
							text : this.subTitle
						},
						xAxis : {
							categories : this.x_Axis,
							crosshair : true
						},
						yAxis : {
							min : 0,
							title : {
								text : this.y_title
							}
						},
						tooltip : {
							headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
							pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
									+ '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
							footerFormat : '</table>',
							shared : true,
							useHTML : true
						},
						plotOptions : {
							column : {
								pointPadding : 0.2,
								borderWidth : 0
							}
						},
						credits : {
							enabled : false
						},
						series : this.data
					});
}