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
		//alert("elementId: " + arguments[i+1] + " operation: " + op + " reverse: " + reverse);
		if (op == "show") {
			if(reverse) {
				clearFormElements(id);
				$(id).find(':input').prop('disabled', false);
				$(id).hide();
			}
			else {
				$(id).show();
				//Editor replace
				if(id == "#textEditorDiv") {
					CKEDITOR.replace( 'editor1' );
				}
			}
		}
		if (op == "hide") {
			if(reverse) {
				$(id).show();
			}
			else {
				clearFormElements(id);
				$(id).find(':input').prop('disabled', false);
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

function clearFormElements(id) {
	$(id).find(':input').each(function() {
		switch (this.type) {
		case 'password':
		case 'text':
		case 'textarea':
		case 'file':
		case 'select-one':
		case 'select-multiple':
			$(this).val('');
			break;
		case 'checkbox':
		case 'radio':
			this.checked = false;
		}
	});
}
