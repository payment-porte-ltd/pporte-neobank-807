$(document).ready(function () {
    var owl = $('.owl-carousel2');
	owl.owlCarousel({
		loop:true,
		margin:16,
		autoplay:true,
		nav: false,
		dots: false,
		slideTransition: 'linear',
		autoplayTimeout: 70000,
		smartSpeed: 10000,
		animateIn: 'linear',
		animateOut: 'linear',
		autoplayHoverPause:true,
		responsive: {
				0: {
					items: 1,
					nav: true
				},
				600: {
					items: 2,
					nav: true
				},
				1300: {
					items: 4,
					nav: true
				},
				1600: {
					items:5,
					nav: true
				}
			}
	});
	
	
	
    var owl = $('.profiles');
	owl.owlCarousel({
		loop:true,
		margin:16,
		autoplay:true,
		nav: false,
		dots: false,
		slideTransition: 'linear',
		autoplayTimeout: 6000,
		smartSpeed: 3000,
		animateIn: 'linear',
		animateOut: 'linear',
		autoplayHoverPause:true,
		responsive: {
				0: {
					items: 1,
					nav: true
				},
				600: {
					items: 2,
					nav: true
				},
				1300: {
					items: 4,
					nav: true
				}
			}
	});

});