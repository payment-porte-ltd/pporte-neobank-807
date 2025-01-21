$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})

function fnViewWalletsPage(relno){
	 $('#"post-form').attr('action', fnGetOpsServletPath());
	 $('input[name="qs"]').val('opswal');
	 $('input[name="rules"]').val('viewspesificcustomerwals');
	 $('input[name="relno"]').val(relno);
	 $("#post-form").submit();
}
