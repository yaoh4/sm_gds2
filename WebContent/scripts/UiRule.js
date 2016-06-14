var prevRadio;
function applyUiRule() {
	var element = arguments[0];
	for(var i=1; i<arguments.length; i++) {
		var reverse = true;
		var value = arguments[i];
		var id = '#' + arguments[i+1];
		var op = arguments[i+2];
		if(element.type == 'checkbox' && element.checked || 
				element.type != 'checkbox' && element.value == value) {
			reverse = false;
		}
		if(op == "warn") {
			if(!reverse) {
				if(!confirm(arguments[i+1])) {
					if(element.type == 'checkbox' && element.checked) {
						element.checked = false;
					}
					if(element.type == 'radio') {
						element.checked = "";
						$('#' + prevRadio).prop("checked", true);
					}
					return;
				}
			}
			prevRadio = arguments[0].id;
			i = i + 3;
			id = '#' + arguments[i+1];
			op = arguments[i+2];
		}
		alert("elementId: " + arguments[i+1] + " operation: " + op + " reverse: " + reverse);
		if (op == "show") {
			if(reverse) {
				$(id).hide();
			}
			else {
				$(id).show();
			}
		}
		if (op == "hide") {
			if(reverse) {
				$(id).show();
			}
			else {
				$(id).hide();
			}
		}
		if (op == "disable") {
			if(reverse) {
				$(id).prop('disabled', false);
			}
			else {
				$(id).prop('disabled', true);
			}
		}
		if (op == "enable") {
			if(reverse) {
				$(id).prop('disabled', true);
			} else {
				$(id).prop('disabled', false);
			}
		}
		
		i = i + 2;
	}
	
}
