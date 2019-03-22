var Air = (function() {
	function fire(){
		render();
	}
	
	function render(){
		/*var r = $(Mustache.render($('#tmplAir').html(), data));
		$('#air').html(r);*/
		dragAndDropForm($("#air-form"));
	}
	
	function dispResults(){
		$ui.displayList($("#results"), "ArtPiece", {
			inst: "air_ArtPiece",
			actions: null,
			search: null,
			minified: true,
			minifiable: false
		});
	}
	
	function dragAndDropForm($form){
		var $input = $form.find('input[type="file"]'),
		$label		 = $form.find( 'label' ),
		$errorMsg	 = $form.find( '.box__error span' ),
		$restart	 = $form.find( '.box__restart' ),
		droppedFiles = false,
		showFiles	 = function( files )
			{
				$label.text( files.length > 1 ? ( $input.attr( 'data-multiple-caption' ) || '' ).replace( '{count}', files.length ) : files[ 0 ].name );
			};
		// drag&drop files if the feature is available
		if( isAdvancedUpload )
		{
			
			$form.addClass( 'has-advanced-upload' ) // letting the CSS part to know drag&drop is supported by the browser
			$form.on( 'drag dragstart dragend dragover dragenter dragleave drop', function( e )
			{
				// preventing the unwanted behaviours
				e.preventDefault();
				e.stopPropagation();
			})
			.on( 'dragover dragenter', function() //
			{
				$form.addClass( 'is-dragover' );
			})
			.on( 'dragleave dragend drop', function()
			{
				$form.removeClass( 'is-dragover' );
			})
			.on( 'drop', function( e )
			{
				droppedFiles = e.originalEvent.dataTransfer.files; // the files that were dropped
				showFiles( droppedFiles );


				$form.trigger( 'submit' ); // automatically submit the form on file drop


			});
		}
		
		$form.on( 'submit', function( e ){
			// preventing the duplicate submissions if the current one is in progress
			if( $form.hasClass( 'is-uploading' ) ) return false;

			$form.addClass( 'is-uploading' ).removeClass( 'is-error' );

			if( isAdvancedUpload ) // ajax file upload for modern browsers
			{
				e.preventDefault();

				// gathering the form data
				var ajaxData = new FormData( $form.get( 0 ) );
				if( droppedFiles )
				{
					$.each( droppedFiles, function( i, file )
					{
						ajaxData.append( $input.attr( 'name' ), file );
					});
				}

				// ajax request
				$.ajax(
				{
					url: 			$form.attr( 'action' ),
					type:			$form.attr( 'method' ),
					data: 			ajaxData,
					dataType:		'json',
					cache:			false,
					contentType:	false,
					processData:	false,
					complete: function()
					{
						$form.removeClass( 'is-uploading' );
					},
					success: function( data )
					{
						$form.addClass( data.success == true ? 'is-success' : 'is-error' );
						
						if(data.success ){
							dispResults();
						}
						else{
							$errorMsg.text( data.error );
						}
					},
					error: function()
					{
						alert( 'Error. Please, contact the webmaster!' );
					}
				});
			}
			else // fallback Ajax solution upload for older browsers
			{
				alert('Browser not supported')
			}
		});
		
		$restart.on( 'click', function( e ){
			e.preventDefault();
			$form.removeClass( 'is-error is-success' );
			$input.trigger( 'click' );
		});

		// Firefox focus bug fix for file input
		$input
		.on( 'focus', function(){ $input.addClass( 'has-focus' ); })
		.on( 'blur', function(){ $input.removeClass( 'has-focus' ); });
	}
	
	function onSubmitForm(e){
		// 1. call artimagerecognition service
		// 2. param => redirect to List
		// 3. if param, search ArtPiece with search spec, add Scores, view banettes
		e.preventDefault();
		
		var formData = new FormData();
		var imageInput = document.getElementsByName("air-file")[0];
		formData.append('air-file', imageInput.files[0]);
		
		callRecognition(formData);
		return false;
	}
	
	function callRecognition(formData){
		var req = new XMLHttpRequest();
		
		req.open("POST", "/ui/ext/ArtImageRecognition");
		req.onload = function(event) { alert(event.target.responseText); };
		req.send(formData);
	}
	
    return { fire: fire };
})();

var isAdvancedUpload = function() {
	//https://css-tricks.com/drag-and-drop-file-uploading/
	var div = document.createElement('div');
	return (('draggable' in div) || ('ondragstart' in div && 'ondrop' in div)) && 'FormData' in window && 'FileReader' in window;
}();