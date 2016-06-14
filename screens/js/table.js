var idCounter = 1;

function addTable() {
    var divContainer = document.getElementsByTagName("DIV")[11];
    var clone = divContainer.cloneNode (true);
    var elmnt = divContainer; //document.getElementById("table");
    console.log(elmnt);
    var cln = elmnt.cloneNode(true);
    var clnInputs = cln.getElementsByTagName('input.type =="text"');
    var clnSelects = cln.getElementsByTagName('select');
    var clnTextArea = cln.getElementsByTagName('textarea');
    var clnRadio = cln.getElementsByTagName('input[@type="radio"]');
    var clnFormElements = [clnInputs, clnSelects, clnTextArea, clnRadio];

    for (var i = 0; i < clnFormElements.length; i++) {

    	var clnArray = clnFormElements[i];
    	
    	for (var j = 0; j < clnArray.length; j++) {
  			var elem = clnArray[j];
  			var id = elem.getAttribute('id');

  			elem.setAttribute('id', id + '_' + idCounter);
  			elem.value = '';
    	}
    }

    console.log(clnInputs, clnSelects, clnTextArea, clnRadio);

    document.getElementsByTagName("DIV")[12].appendChild(cln); 
    idCounter += 1;

}



