var prevRadio;
var element;
var params;
function applyUiRule() {
	element = arguments[0];
	params = arguments;
	var exception = false;
	if((element.id == 2 || element.id == 3 || element.id == 5 || element.id == 6 || element.id == 7)
			&& $("#11").is(":visible")) {
		exception = true;
	}
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
				bootbox.confirm(arguments[i+1], function(ans) {
					if (ans) {
						prevRadio = element.id;
						var newParam = [element];
						for(var j = i + 3; j < params.length; j++) {
							newParam.push(params[j]);
						}
						applyUiRule.apply(this,newParam);
					} else {
						if(element.type == 'checkbox' && element.checked) {
							element.checked = false;
						}
						if(element.type == 'radio') {
							element.checked = "";
							$('#' + prevRadio).prop("checked", true);
						}
					}
				});
				return false;
			}
			prevRadio = arguments[0].id;
			i = i + 3;
			value = arguments[i];
			id = '#' + arguments[i+1];
			op = arguments[i+2];
			reverse = true;
			if(element.type == 'checkbox' && element.checked || 
					element.type != 'checkbox' && element.value == value) {
				reverse = false;
			}
		}
		//alert("elementId: " + arguments[i+1] + " operation: " + op + " reverse: " + reverse);
		if (op == "show") {
			if(reverse) {
				if(!exception || exception && (id != "#11" && id != "#14" && id != "#17" && id != "#20" && id != "#26" && id != "#29")) {
					clearFormElements(id);
					$(id).find(':input').prop('disabled', false);
					$(id).hide();
				}
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
				if(!exception || exception && (id != "#11" && id != "#14" && id != "#17" && id != "#20" && id != "#26" && id != "#29")) {
					$(id).show();
				}
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
