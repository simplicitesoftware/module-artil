/* Specific client script */

(function($) {
	$(document).on("ui.loaded", function() {
		$ui.view.menu.menuMin();
	});

	$(document).on("ui.ready", function() {
		// customize UI here
	});
	
	$(document).on("ui.beforeunload", function() {
		// window will be unloaded
	});
	
	$(document).on("ui.unload", function() {
		// window is unloaded
	});
})(jQuery);
