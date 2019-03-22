var Air = (function() {
	function fire(){
		render();
	}
	
	function render(){
		/*var r = $(Mustache.render($('#tmplAir').html(), data));
		$('#air').html(r);*/
		
		var $form = $("#air-form");
		dragAndDropForm($form);
		$form.on("submit", onSubmitForm);
	}
	
	function dragAndDropForm($form){
		if (isAdvancedUpload) {
			$form.addClass('has-advanced-upload');
			
			$form.on('drag dragstart dragend dragover dragenter dragleave drop', function(e) {
				e.preventDefault();
				e.stopPropagation();
			})
			.on('dragover dragenter', function() {
				$form.addClass('is-dragover');
			})
			.on('dragleave dragend drop', function() {
				$form.removeClass('is-dragover');
			})
			.on('drop', function(e) {
				droppedFiles = e.originalEvent.dataTransfer.files;
			});
		}
	}
	
	function onSubmitForm(e){
		// 1. call artimagerecognition service
		// 2. param => redirect to List
		// 3. if param, search ArtPiece with search spec, add Scores, view banettes
		e.preventDefault();
		callRecognition();
		return false;
	}
	
	function callRecognition(){
		console.log("====================");
		var formData = new FormData();
		var imageInput = document.getElementsByName("air-file")[0];
		
		formData.append('air-file', imageInput.files[0]);
		
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