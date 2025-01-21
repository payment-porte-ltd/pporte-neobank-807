$(function(e) {
	$('#example').dataTable({ "bSort" : false } );
	$('#table1').DataTable({
		"aoColumnDefs": [
        { "bSortable": false, "aTargets": [ 0, 1, 2, 3 ] }
   	 ]
	});

			var table = $('#example1').DataTable();
			$('button').click( function() {
				var data = table.$('input, select').serialize();
				return false;
			});
			$('#example2').DataTable( {
				"scrollY":        "200px",
				"scrollCollapse": true,
				"paging":         true
			});
			 $('#example3').DataTable( {
				 "ordering": false,
				  "bSort" : false,	
				 "paging": true,
        responsive: {
            details: {
                display: $.fn.dataTable.Responsive.display.modal( {
                    header: function ( row ) {
                        var data = row.data();
                        return 'Details for '+data[0]+' '+data[1];
                    }
                } ),
                renderer: $.fn.dataTable.Responsive.renderer.tableAll( {
                    tableClass: 'table'
                } )
            }
        }
    } );
			 $('#stellar_txns').DataTable( {
				 "ordering": false,
				 "bSort" : false,	
				 "paging": true,
        responsive: {
            details: {
                display: $.fn.dataTable.Responsive.display.modal( {
                    header: function ( row ) {
                        var data = row.data();
                        return 'Details for '+data[0]+' '+data[1];
                    }
                } ),
                renderer: $.fn.dataTable.Responsive.renderer.tableAll( {
                    tableClass: 'table'
                } )
            }
        }
    } );

	$('#txn_datatable').dataTable({ "bSort" : false } ); // This id is used by multiple tables in different pages
} );