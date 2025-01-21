
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
				$("#fiat_wallet_stats_div").html('');
				let htmlData='';
				console.table(transactionList);
		 		fnGeMonthlytxns(relno) 
				if (data.error == 'false') {
					if(transactionList !=null){
						if(transactionList.length >0){			
							htmlData=`
								<div class="card">
									<div class="card-header custom-header">
										<div>
											<h3 class="card-title">Fiat Wallet Statistics</h3>
											<h6 class="card-subtitle">Last 15 days of Transactions</h6>
										</div>
									</div>
									<div class="card-body">
									<div id="echart1" class="chartsh chart-dropshadow"></div>
 										
									</div>
								</div>
							`;
							// Form the div here
							$("#fiat_wallet_stats_div").append(htmlData);	
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

// TODO:- This method needs to be revisted. 
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
      data:[day1value,day2value,day3value, day4value, day5value,day6value,day7value,day8value,day9value,day10value,day11value,day12value,day13value,day14value,day15value]
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
          color: '#27A89D' 
        }
      },
      axisLabel: {
        fontSize: 10,
        color: '#fff',
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
          color: '#fff'
        }
      },
      axisLine: {
        lineStyle: {
          color: '#fff'
        }
      },
      axisLabel: {
        fontSize: 10,
        name: 'USD',
        color: '#fff'
      }
    },
    series: chartdata,
    color:[ '#27A89D ', '#27A89D',]
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
					color: '#27A89D',
				}
			},
			title: {
				text: 'USD',
				style: {
					color: '#27A89D',
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
			color: '#27A89D', 

		}/*, {
			name: 'Revenue',
			type: 'spline',
			data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6],
			color: '#ec2d38',

		}*/
	});
		
}

var portexaxis = []; var porteyaxis = [];
var xlmxaxis = []; var xlmyaxis = [];
var usdcxaxis = []; var usdcyaxis = [];
var vesselxaxis = []; var vesselxaxis = [];

function fnGeMonthlytxns(relno) {
		var formData = new FormData();
			formData.append("qs","dash")
			formData.append("rules","getmonthlytxnforgraph")
			formData.append("relno",relno)
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
				var porteTransactions = data.portetxns.reverse();
				var xlmTransactions = data.xlmtxns.reverse();
				var usdcTransactions = data.usdctxns.reverse();
				var veslTransactions = data.vesltxns.reverse();
				
				if (data.error == 'false') {
						if(porteTransactions.length >0){	
							var portetemparray = [];
							var pcount= porteTransactions.length;
							for(var i=0; i< pcount; i++){
								portetemparray = porteTransactions[i].split(',');
			            		portexaxis[i] =  portetemparray[0];//day
			            		porteyaxis[i] = portetemparray[1];//amount
							 }
			            	fnLoadPorteGraph(portexaxis,porteyaxis)
						 $("#porte-graph").removeClass("hidden");
						}else{
						  $("#porte-graph").addClass("hidden");
						}
						if(xlmTransactions.length >0){	
							var xlmtemparray = [];
							var xcount= xlmTransactions.length;
							for(var j=0; j< xcount; j++){
								xlmtemparray = xlmTransactions[j].split(',');
			            		xlmxaxis[j] =  xlmtemparray[0];//day
			            		xlmyaxis[j] = xlmtemparray[1];//amount
							 }
			            	fnLoadXLMGraph(xlmxaxis,xlmyaxis)
						 $("#xlm-graph").removeClass("hidden");
						}else{
						  $("#xlm-graph").addClass("hidden");
						}
						if(usdcTransactions.length >0){	
							var usdctemparray = [];
							var ucount= usdcTransactions.length;
							for(var k=0; k< ucount; k++){
								usdctemparray = usdcTransactions[k].split(',');
			            		usdcxaxis[k] =  usdctemparray[0];//day
			            		usdcyaxis[k] = usdctemparray[1];//amount
							 }
			            	fnLoadUSDCGraph(usdcxaxis,usdcyaxis)
						 $("#usdc-graph").removeClass("hidden");
						}else{
						  $("#usdc-graph").addClass("hidden");
						}
						if(veslTransactions.length >0){	
							var vesseltemparray = [];
							var vcount= veslTransactions.length;
							for(var l=0; l< vcount; l++){
								vesseltemparray = veslTransactions[l].split(',');
			            		vesselxaxis[l] =  vesseltemparray[0];//day
			            		vesselyaxis[l] = vesseltemparray[1];//amount
							 }
			            	fnLoadVesselGraph(vesselxaxis,vesselyaxis)
						 $("#vessel-graph").removeClass("hidden");
						}else{
						  $("#vessel-graph").addClass("hidden");
						}
				} else {
					Swal.fire({
						icon: 'error',
						title: 'Oops',
						text: 'Failed to get Graph Transactions',
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

function fnLoadXLMGraph(xlmxaxis,xlmyaxis) {
	var ctx = document.getElementById("xlm-chart");
	var myChart = new Chart(ctx, {
		type: 'line',
		data: {
			labels: xlmxaxis,
			type: 'line',
			defaultFontFamily: 'Montserrat',
			datasets: [{
				data: xlmyaxis,
				label: "XLM Coin",
				backgroundColor: '#00a89d',
				borderColor: '#222d65',
				borderWidth: 3.5,
				pointStyle: 'circle',
				pointRadius: 5,
				pointBorderColor: 'transparent',
				pointBackgroundColor: '#222d65',
			}, ]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			tooltips: {
				mode: 'index',
				titleFontSize: 12,
				titleFontColor: '#000',
				bodyFontColor: '#000',
				backgroundColor: '#fff',
				titleFontFamily: 'Montserrat',
				bodyFontFamily: 'Montserrat',
				cornerRadius: 3,
				intersect: false,
			},
			legend: {
				display: false,
				position: 'top',
				labels: {
					usePointStyle: true,
					fontFamily: 'Montserrat',
				},
			},
			scales: {
				xAxes: [{
					ticks: {
						fontColor: "#bbc1ca",
					 },
					display: true,
					gridLines: {
						display: false,
						drawBorder: false,
						color: 'rgba(0,0,0,0.03)'
					},
					scaleLabel: {
						display: false,
						labelString: 'Day of the Month',
						fontColor: 'rgba(0,0,0,0.61)'
					}
				}],
				yAxes: [{
					ticks: {
						fontColor: "#bbc1ca",
					 },
					display: true,
					gridLines: {
						display: false,
						drawBorder: false,
						color: 'rgba(0,0,0,0.03)'
					},
					scaleLabel: {
						display: true,
						labelString: 'Value',
						fontColor: 'rgba(0,0,0,0.61)'
					}
				}]
			},
			title: {
				display: false,
			}
		}
	});
}

function fnLoadUSDCGraph(usdcxaxis,usdcyaxis){
	var ctx = document.getElementById("usdc-chart");
	var myChart = new Chart(ctx, {
		type: 'line',
		data: {
			labels: usdcxaxis,
			type: 'line',
			defaultFontFamily: 'Montserrat',
			datasets: [{
				data: usdcyaxis,
				label: "USDC Coin",
				backgroundColor: '#00a89d',
				borderColor: '#27A89D',
				borderWidth: 3.5,
				pointStyle: 'circle',
				pointRadius: 5,
				pointBorderColor: 'transparent',
				pointBackgroundColor: '#222d65',
			}, ]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			tooltips: {
				mode: 'index',
				titleFontSize: 12,
				titleFontColor: '#000',
				bodyFontColor: '#000',
				backgroundColor: '#fff',
				titleFontFamily: 'Montserrat',
				bodyFontFamily: 'Montserrat',
				cornerRadius: 3,
				intersect: false,
			},
			legend: {
				display: false,
				position: 'top',
				labels: {
					usePointStyle: true,
					fontFamily: 'Montserrat',
				},
			},
			scales: {
				xAxes: [{
					ticks: {
						fontColor: "#bbc1ca",
					 },
					display: true,
					gridLines: {
						display: false,
						drawBorder: false,
						color: 'rgba(0,0,0,0.03)'
					},
					scaleLabel: {
						display: false,
						labelString: 'Day of the Month',
						fontColor: 'rgba(0,0,0,0.61)'
					}
				}],
				yAxes: [{
					ticks: {
						fontColor: "#bbc1ca",
					 },
					display: true,
					gridLines: {
						display: false,
						drawBorder: false,
						color: 'rgba(0,0,0,0.03)'
					},
					scaleLabel: {
						display: true,
						labelString: 'Value',
						fontColor: 'rgba(0,0,0,0.61)'
					}
				}]
			},
			title: {
				display: false,
			}
		}
	});
}
function fnLoadVesselGraph(vesselxaxis,vesselyaxis){
	var ctx = document.getElementById("vessel-chart");
	var myChart = new Chart(ctx, {
		type: 'line',
		data: {
			labels: vesselxaxis,
			type: 'line',
			defaultFontFamily: 'Montserrat',
			datasets: [{
				data: vesselyaxis,
				label: "Vessel Coin",
				backgroundColor: '#00a89d',
				borderColor: '#222d65',
				borderWidth: 3.5,
				pointStyle: 'circle',
				pointRadius: 5,
				pointBorderColor: 'transparent',
				pointBackgroundColor: '#222d65',
			}, ]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			tooltips: {
				mode: 'index',
				titleFontSize: 12,
				titleFontColor: '#000',
				bodyFontColor: '#000',
				backgroundColor: '#fff',
				titleFontFamily: 'Montserrat',
				bodyFontFamily: 'Montserrat',
				cornerRadius: 3,
				intersect: false,
			},
			legend: {
				display: false,
				position: 'top',
				labels: {
					usePointStyle: true,
					fontFamily: 'Montserrat',
				},
			},
			scales: {
				xAxes: [{
					ticks: {
						fontColor: "#bbc1ca",
					 },
					display: true,
					gridLines: {
						display: false,
						drawBorder: false,
						color: 'rgba(0,0,0,0.03)'
					},
					scaleLabel: {
						display: false,
						labelString: 'Day of the Month',
						fontColor: 'rgba(0,0,0,0.61)'
					}
				}],
				yAxes: [{
					ticks: {
						fontColor: "#bbc1ca",
					 },
					display: true,
					gridLines: {
						display: false,
						drawBorder: false,
						color: 'rgba(0,0,0,0.03)'
					},
					scaleLabel: {
						display: true,
						labelString: 'Value',
						fontColor: 'rgba(0,0,0,0.61)'
					}
				}]
			},
			title: {
				display: false,
			}
		}
	});
}
function fnLoadPorteGraph(portexaxis,porteyaxis){
	var ctx = document.getElementById("porte-chart");
	var myChart = new Chart(ctx, {
		type: 'line',
		data: {
			labels: portexaxis,
			type: 'line',
			defaultFontFamily: 'Montserrat',
			datasets: [{
				data: porteyaxis,
				label: "Porte Coin",
				backgroundColor: '#00a89d',
				borderColor: '#222d65',
				borderWidth: 3.5,
				pointStyle: 'circle',
				pointRadius: 5,
				pointBorderColor: 'transparent',
				pointBackgroundColor: '#222d65',
			}, ]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			tooltips: {
				mode: 'index',
				titleFontSize: 12,
				titleFontColor: '#000',
				bodyFontColor: '#000',
				backgroundColor: '#fff',
				titleFontFamily: 'Montserrat',
				bodyFontFamily: 'Montserrat',
				cornerRadius: 3,
				intersect: false,
			},
			legend: {
				display: false,
				position: 'top',
				labels: {
					usePointStyle: true,
					fontFamily: 'Montserrat',
				},
			},
			scales: {
				xAxes: [{
					ticks: {
						fontColor: "#bbc1ca",
					 },
					display: true,
					gridLines: {
						display: false,
						drawBorder: false,
						color: 'rgba(0,0,0,0.03)'
					},
					scaleLabel: {
						display: false,
						labelString: 'Day of the Month',
						fontColor: 'rgba(0,0,0,0.61)'
					}
				}],
				yAxes: [{
					ticks: {
						fontColor: "#bbc1ca",
					 },
					display: true,
					gridLines: {
						display: false,
						drawBorder: false,
						color: 'rgba(0,0,0,0.03)'
					},
					scaleLabel: {
						display: true,
						labelString: 'Value',
						fontColor: 'rgba(0,0,0,0.61)'
					}
				}]
			},
			title: {
				display: false,
			}
		}
	});
}


	

