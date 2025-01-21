function fnGetAddBranchPage(){
	$('#get-add-branch-form').attr('action', 'ws');
	$('input[name="qs"]').val('merchprf');
	$('input[name="rules"]').val('getaddbranchpage');
	$("#get-add-branch-form").submit();
}