$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})

function fnShowRequestDetail(disputeId){
    console.log('disputeId is ', disputeId);

    /* $('#view-dispute-form').submit(function() {
        $('#view-dispute-form input[name="qs"]').val('merchdspt');
		$('#view-dispute-form input[name="rules"]').val('view_specific_dispute');
		$('#view-dispute-form input[name="hdnreqid"]').val(disputeId);
        $('#view-dispute-form').ajaxSubmit({
            url: "ws",
            data: post,
            type: "POST",
            headers: {
                "foo": "bar"
            }
        });
        return false;
    }); */
    $('#view-dispute-form').attr('action', 'ws');
   $('input[name="qs"]').val('merchdspt');
   $('input[name="rules"]').val('view_specific_dispute');
   $('input[name="hdnreqid"]').val(disputeId);
   $("#view-dispute-form").submit();
}