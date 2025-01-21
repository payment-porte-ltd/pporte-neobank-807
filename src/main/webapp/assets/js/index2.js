function fnGetWeeklyTxns(relno) {
		$('#dispute-form input[name="qs"]').val('custdspt');
		$('#dispute-form input[name="rules"]').val('getfiatweeklyforgraph');
		$('#dispute-form input[name="relno"]').val(relno);
	
		var formData = new FormData($('#dispute-form')[0]);
		$.ajaxSetup({
			beforeSend: function(xhr) {
				xhr.setRequestHeader('x-api-key', getAPIKey());
			}
		});
		$.ajax({
			url: 'ms',
			data: formData,
			processData: false,
			contentType: false,
			type: 'POST',
			success: function(result) {
				//wallet_txn
				var data = JSON.parse(result);
				var transactionList = data.data;
				$('#cust-disputes').html('');
				$('#card_disp_header').html('');
				$('#card_disp_header').html('Recent Disputes')
				if (data.error == 'false') {
					if(transactionList !=null){
						if(transactionList.length >0){					
						   LaodChartd(transactionList);
						   //LaodHighChartd(transactionList);
						 
						}else{
						 //console.log(' no transactions present')
						}
					}else{
						// console.log(' no transactions available')
					}
					
	
				} else {
					Swal.fire({
						icon: 'error',
						title: 'Oops',
						text: 'Failed to get Recent Disputes',
						showConfirmButton: true,
						confirmButtonText: "Ok",
						closeOnConfirm: true,
					}).then(function() {
	
					});
				}
			},
			error: function() {
				Swal.fire({
					icon: 'error',
					title: 'Oops',
					text: 'Problem with connection',
					showConfirmButton: true,
					confirmButtonText: "Ok",
				}).then(function() {
	
				});
	
			}
		});
	}


function LaodChartd(transactionList){
	/*for (i = 0; i < transactionList.length; i++) {
	 transactionList = transactionList[i].split(',');
    alert('txn size ',transactionList.length)
	}*/
	var day1,day2,day3,day4,day5,day6,day7,day8,day9,day10,day11,day12,day13,day14,day15;
	let day2value,day3value,day4value,day5value,day6value,day7value,day8value,day9value,day10value,
	    day11value,day12value,day13value,day14value,day15value;
	let day1value = 0;

//let arrdays = new Array(transactionList.lenth);
//let arrdaysvalue = new Array(transactionList.lenth);

	for (i = 0; i < transactionList.length; i++) {
		
		var dayOne = transactionList[i].dayOne;
		var dayTwo = transactionList[i].dayTwo;
		var dayThree = transactionList[i].dayThree;
		var dayFour = transactionList[i].dayFour;
		var dayFive = transactionList[i].dayFive;
		var daySix = transactionList[i].daySix;
		var daySeven = transactionList[i].daySeven;
		var dayEight = transactionList[i].dayEight;
		var dayNine = transactionList[i].dayNine;
		var dayTen = transactionList[i].dayTen;
		var dayEleven = transactionList[i].dayEleven;
		var dayTwelve = transactionList[i].dayTwelve;
		var dayThirteen = transactionList[i].dayThirteen;
		var dayFourteen = transactionList[i].dayFourteen;
		var dayFifteen = transactionList[i].dayFifteen;

		dayOne = dayOne.split(",");day1 = dayOne[0];day1value = dayOne[1];
		dayTwo = dayTwo.split(","); day2 = dayTwo[0];day2value = dayTwo[1];
		dayThree = dayThree.split(",");day3 = dayThree[0];day3value = dayThree[1];
		dayFour = dayFour.split(",");day4 = dayFour[0];day4value = dayFour[1];
		dayFive = dayFive.split(",");day5 = dayFive[0];day5value = dayFive[1];
		daySix = daySix.split(",");day6 = daySix[0];day6value = daySix[1];
		daySeven = daySeven.split(",");day7 = daySeven[0];day7value = daySeven[1];
		dayEight = dayEight.split(",");day8 = dayEight[0];day8value = dayEight[1];
		dayNine = dayNine.split(",");day9 = dayNine[0];day9value = dayNine[1];
		dayTen = dayTen.split(",");day10 = dayNine[0];day10value = dayTen[1];
		dayEleven = dayEleven.split(",");day11 = dayEleven[0];day11value = dayEleven[1];
		dayTwelve = dayTwelve.split(",");day12 = dayTwelve[0];day12value = dayTwelve[1];
		dayThirteen = dayThirteen.split(",");day13 = dayThirteen[0];day13value = dayThirteen[1];
		dayFourteen = dayFourteen.split(",");day14 = dayFourteen[0];day14value = dayFourteen[1];
		dayFifteen = dayFifteen.split(",");day15 = dayFifteen[0];day15value = dayFifteen[1];
				
		//arrdays.push(dayOne[0]);
		//arrdaysvalue.push(dayOne[1]);
	}
	var test = 14.5;
	var test2 =day1value;
	
	//alert(test)
	//alert(test2)
	
	var xaxis = [];
	var yaxis = []
	
	for (i=0; i<15; i++){
		//alert('day'.concat(i))
		xaxis[i] = 'day'+i;
	    yaxis = [i] = 'day'+i+'value';
       //alert(yaxis[i])
       //alert(xaxis[i])
	}
	/*echart2*/
  var chartdata = 
    {
      name: 'Transactions',
      type: 'bar',
      data:[day1value,day2value,day3value, 1500, day5value,day6value,day7value,day8value,day9value,day10value,day11value,day12value,day13value,day14value,day15value]
    }
  ;

  var chart = document.getElementById('echart1');
  var barChart = echarts.init(chart);

  var option = {
    grid: {
      top: '6',
      right: '0',
      bottom: '20',
      left: '100',
    },
    xAxis: {
      data: [day1, day2, day3, day4, day5, day6, day7, day8, day9, day10, day11, day12, day13, day14, day15],
	  crosshair: true,
      axisLine: {
        lineStyle: {
          color: '#222d65'

        }
      },
      axisLabel: {
        fontSize: 10,
        color: '#bbc1ca',
        name: 'Days',
      }
    },
	tooltip: {
		show: true,
		showContent: true,
		alwaysShowContent: true,
		triggerOn: 'mousemove',
		trigger: 'axis',
		axisPointer:
		{
			label: {
				show: true,
			}
		}

	},
    yAxis: {
      splitLine: {
        lineStyle: {
          color: 'rgba(0,0,0,0.03)'
        }
      },
      axisLine: {
        lineStyle: {
          color: 'rgba(0,0,0,0.03)'
        }
      },
      axisLabel: {
        fontSize: 10,
        name: 'Transactions',
        color: '#bbc1ca'
      }
    },
    series: chartdata,
    color:[ '#1753fc ', '#9258f1',]
  };

  barChart.setOption(option);

  /*--echart-1---*/
		
}


function LaodHighChartd(transactionList){
	/*for (i = 0; i < transactionList.length; i++) {
	 transactionList = transactionList[i].split(',');
    alert('txn size ',transactionList.length)
	}*/
	var day1,day2,day3,day4,day5,day6,day7,day8,day9,day10,day11,day12,day13,day14,day15;
	let day2value,day3value,day4value,day5value,day6value,day7value,day8value,day9value,day10value,
	    day11value,day12value,day13value,day14value,day15value;
	let day1value = 0;

//let arrdays = new Array(transactionList.lenth);
//let arrdaysvalue = new Array(transactionList.lenth);

	for (i = 0; i < transactionList.length; i++) {
		
		var dayOne = transactionList[i].dayOne;
		var dayTwo = transactionList[i].dayTwo;
		var dayThree = transactionList[i].dayThree;
		var dayFour = transactionList[i].dayFour;
		var dayFive = transactionList[i].dayFive;
		var daySix = transactionList[i].daySix;
		var daySeven = transactionList[i].daySeven;
		var dayEight = transactionList[i].dayEight;
		var dayNine = transactionList[i].dayNine;
		var dayTen = transactionList[i].dayTen;
		var dayEleven = transactionList[i].dayEleven;
		var dayTwelve = transactionList[i].dayTwelve;
		var dayThirteen = transactionList[i].dayThirteen;
		var dayFourteen = transactionList[i].dayFourteen;
		var dayFifteen = transactionList[i].dayFifteen;

		dayOne = dayOne.split(",");day1 = dayOne[0];day1value = dayOne[1];
		dayTwo = dayTwo.split(","); day2 = dayTwo[0];day2value = dayTwo[1];
		dayThree = dayThree.split(",");day3 = dayThree[0];day3value = dayThree[1];
		dayFour = dayFour.split(",");day4 = dayFour[0];day4value = dayFour[1];
		dayFive = dayFive.split(",");day5 = dayFive[0];day5value = dayFive[1];
		daySix = daySix.split(",");day6 = daySix[0];day6value = daySix[1];
		daySeven = daySeven.split(",");day7 = daySeven[0];day7value = daySeven[1];
		dayEight = dayEight.split(",");day8 = dayEight[0];day8value = dayEight[1];
		dayNine = dayNine.split(",");day9 = dayNine[0];day9value = dayNine[1];
		dayTen = dayTen.split(",");day10 = dayNine[0];day10value = dayTen[1];
		dayEleven = dayEleven.split(",");day11 = dayEleven[0];day11value = dayEleven[1];
		dayTwelve = dayTwelve.split(",");day12 = dayTwelve[0];day12value = dayTwelve[1];
		dayThirteen = dayThirteen.split(",");day13 = dayThirteen[0];day13value = dayThirteen[1];
		dayFourteen = dayFourteen.split(",");day14 = dayFourteen[0];day14value = dayFourteen[1];
		dayFifteen = dayFifteen.split(",");day15 = dayFifteen[0];day15value = dayFifteen[1];
				
		//arrdays.push(dayOne[0]);
		//arrdaysvalue.push(dayOne[1]);
	}	
	
	var xaxis = [];
	var yaxis = []
	
	for (i=0; i<15; i++){
		//alert('day'.concat(i))
		xaxis[i] = 'day'+i;
	    yaxis = [i] = 'day'+i+'value';
       //alert(yaxis[i])
       //alert(xaxis[i])
	}
		
  /*----Hightchat9-----*/
	Highcharts.chart('Highchart9', {
		chart: {
			backgroundColor: 'transparent',
			zoomType: 'xy'
		},
		title: {
			text: ''
		},
		subtitle: {
			text: ''
		},
		exporting: {
			enabled: false
		},
		credits: {
			enabled: false
		},
		xAxis: {
			gridLineColor: 'rgba(0,0,0,0.03)',
			//categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
			categories: [day1, day2, day3, day4, day5, day6, day7, day8, day9, day10, day11, day12, day13, day14, day15],

			crosshair: true,
			labels: {
				style: {
					color: '#bbc1ca',
				}
			}
		},
		yAxis: { // Primary yAxis
			gridLineColor: 'rgba(0,0,0,0.03)',
			labels: {
				format: '{value}',
				style: {
					color: '#bbc1ca',
				}
			},
			title: {
				text: 'Revenue',
				style: {
					color: '#bbc1ca',
				}
			}
		}/*, { // Secondary yAxis
			gridLineColor: 'rgba(0,0,0,0.03)',
			title: {
				text: 'Cost',
				style: {
					color: '#bbc1ca',
				}
			},
			labels: {
				format: '{value} ',
				style: {
					color: '#bbc1ca',
				}
			},
			opposite: true
		}*/,
		tooltip: {
			shared: true
		},
		legend: {
			layout: 'vertical',
			align: 'left',
			x: 120,
			verticalAlign: 'top',
			y: 100,
			floating: true,
			backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || 'rgba(255,255,255,0.25)'
		},
		series: {
			name: 'Cost',
			type: 'column',
			yAxis: 1,
            data:[day1value,day2value,day3value, day4value, day5value,day6value,day7value,day8value,day9value,day10value,day11value,day12value,day13value,day14value,day15value],
			color: '#1753fc',

		}/*, {
			name: 'Revenue',
			type: 'spline',
			data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6],
			color: '#ec2d38',

		}*/
	});
		
}

var xaxis = []; var yaxis = [];
function fnGeMonthlytxns(relno) {
		$('#dispute-form input[name="qs"]').val('custdspt');
		$('#dispute-form input[name="rules"]').val('getfiatmonthlyforgraph');
		$('#dispute-form input[name="relno"]').val(relno);
	
		var formData = new FormData($('#dispute-form')[0]);
		$.ajaxSetup({
			beforeSend: function(xhr) {
				xhr.setRequestHeader('x-api-key', getAPIKey());
			}
		});
		$.ajax({
			url: 'ms',
			data: formData,
			processData: false,
			contentType: false,
			type: 'POST',
			success: function(result) {
				//wallet_txn
				var data = JSON.parse(result);
				var transactionList = data.data;
				$('#cust-disputes').html('');
				$('#card_disp_header').html('');
				$('#card_disp_header').html('Montly Transactions')
				if (data.error == 'false') {
					if(transactionList !=null){
						if(transactionList.length >0){	
							var temparray = [];
							for(i=0; i< transactionList.length; i++){
								temparray = transactionList[i].split(',');
			            		xaxis[i] =  temparray[0];//month
			            		yaxis[i] = temparray[1];//amount
							 }
						
						    console.log(xaxis);
			            	console.log(yaxis);
			            	LaodMonthlyCharjs(xaxis,yaxis)
							LaodMonthlyPieCharjs(xaxis,yaxis)
						 
						}else{
						 console.log(' no transactions present')
						}
					}else{
						 console.log(' no transactions available')
					}
					
	
				} else {
					Swal.fire({
						icon: 'error',
						title: 'Oops',
						text: 'Failed to get Recent Disputes',
						showConfirmButton: true,
						confirmButtonText: "Ok",
						closeOnConfirm: true,
					}).then(function() {
	
					});
				}
			},
			error: function() {
				Swal.fire({
					icon: 'error',
					title: 'Oops',
					text: 'Problem with connection',
					showConfirmButton: true,
					confirmButtonText: "Ok",
				}).then(function() {
	
				});
	
			}
		});
}


function LaodMonthlyCharjs(xaxis,yaxis){
	/*Chartjs*/
	var ctx = document.getElementById("Chart").getContext('2d');
	ctx.height = 100;
	var myChart = new Chart(ctx, {
		type: 'line',
		data: {
			//labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","Aug","Sep","Nov","Dec"],
			labels: xaxis,
			datasets: [{
				label: 'Earnings',
				//data: [20, 420, 210, 354, 580, 320, 480,210, 354, 580, 320, 480],
				data: yaxis,
				borderWidth: 2,
				backgroundColor: 'rgba(216, 211, 248,0.15)',
				borderColor: '#1753fc',
				borderWidth: 4,
				pointBackgroundColor: '#ffffff',
				pointRadius: 4
			}]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,

			scales: {
				xAxes: [{
					ticks: {
						fontColor: "#bbc1ca",
						reverse: true,
					 },
					display: true,
					gridLines: {
						color: 'rgba(0,0,0,0.03)'
					}
				}],
				yAxes: [{
					ticks: {
						fontColor: "#bbc1ca",
					 },
					display: true,
					gridLines: {
						color: 'rgba(0,0,0,0.03)'
					},
					scaleLabel: {
						display: false,
						labelString: 'Thousands',
						fontColor: 'rgba(0,0,0,0.03)'
					}
				}]
			},
			legend: {
				labels: {
					fontColor: "#bbc1ca"
				},
			},
		}
	});

	/*Chartjs*/
	
}



function LaodMonthlyPieCharjs(xaxis,yaxis){
  'use strict'

//pie chart
	var ctx = document.getElementById("pieChart");
	ctx.height = 250;
	var myChart = new Chart(ctx, {
		type: 'pie',
		data: {
			datasets: [{
				data: yaxis,
				backgroundColor: ['#00008B', '#FFE13C', '#00C36A'],
				hoverBackgroundColor: ['#FB00FF', '#AEFF00', '#50FFF6'],
				borderColor:'transparent',
			}],
			labels: xaxis
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			legend: {
				labels: {
					fontColor: "#bbc1ca"
				},
			},
		}
	});
  
}



