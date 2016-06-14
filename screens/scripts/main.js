function disableForm() {
if (document.getElementById("DivisionOfficeCenter").value == "DCEG") {
document.getElementById("nihFunded").disabled = true;
document.getElementById("nihFunded").style.opacity = "0.3";
document.getElementById("nihFundedlabel").style.opacity = "0.3";

document.getElementById("programDirector").disabled = true;
document.getElementById("programDirector").style.opacity = "0.3";
document.getElementById("programDirectorlabel").style.opacity = "0.3";


document.getElementById("advocateActivitiesStartDate_0").disabled = true;
document.getElementById("advocateActivitiesStartDate_0").style.opacity = "0.3";
document.getElementById("startDatelabel").style.opacity = "0.3";
document.getElementById("startCalendar").style.opacity = "0.3";

document.getElementById("advocateActivitiesEndDate_0").disabled = true;
document.getElementById("advocateActivitiesEndDate_0").style.opacity = "0.3";
document.getElementById("endDatelabel").style.opacity = "0.3";
document.getElementById("endCalendar").style.opacity = "0.3";

document.getElementById("review").disabled = false;

}

else if (document.getElementById("DivisionOfficeCenter").value == "CCR") {
document.getElementById("nihFunded").disabled = true;
document.getElementById("nihFunded").style.opacity = "0.3";
document.getElementById("nihFundedlabel").style.opacity = "0.3";

document.getElementById("programDirector").disabled = true;
document.getElementById("programDirector").style.opacity = "0.3";
document.getElementById("programDirectorlabel").style.opacity = "0.3";


document.getElementById("advocateActivitiesStartDate_0").disabled = true;
document.getElementById("advocateActivitiesStartDate_0").style.opacity = "0.3";
document.getElementById("startDatelabel").style.opacity = "0.3";
document.getElementById("startCalendar").style.opacity = "0.3";

document.getElementById("advocateActivitiesEndDate_0").disabled = true;
document.getElementById("advocateActivitiesEndDate_0").style.opacity = "0.3";
document.getElementById("endDatelabel").style.opacity = "0.3";
document.getElementById("endCalendar").style.opacity = "0.3";

document.getElementById("review").disabled = false;
document.getElementById("review").style.opacity = "1";
document.getElementById("reviewlabel").style.opacity = "1";
document.getElementById("reviewCalendar").style.opacity = "1";
}



else {
document.getElementById("nihFunded").disabled = false;
document.getElementById("nihFunded").style.opacity = "1";
document.getElementById("nihFundedlabel").style.opacity = "1";


document.getElementById("programDirector").disabled = true;
document.getElementById("programDirector").style.opacity = "1";
document.getElementById("programDirectorlabel").style.opacity = "1";


document.getElementById("advocateActivitiesStartDate_0").disabled = true;
document.getElementById("advocateActivitiesStartDate_0").style.opacity = "1";
document.getElementById("startDatelabel").style.opacity = "1";
document.getElementById("startCalendar").style.opacity = "1";

document.getElementById("advocateActivitiesEndDate_0").disabled = true;
document.getElementById("advocateActivitiesEndDate_0").style.opacity = "1";
document.getElementById("endDatelabel").style.opacity = "1";
document.getElementById("endCalendar").style.opacity = "1";

document.getElementById("review").disabled = true;
document.getElementById("review").style.opacity = "0.3";
document.getElementById("reviewlabel").style.opacity = "0.3";
document.getElementById("reviewCalendar").style.opacity = "0.3";
}
}

function subStudy() {
	if (document.getElementById("parent").value == "Parent") {
	document.getElementById("parentAccessionNumber").disabled = true;
	document.getElementById("parentAccessionNumber").style.opacity = "0.3";
	document.getElementById("parentAccessionNumberlabel").style.opacity = "0.3";
}
else {
	document.getElementById("parentAccessionNumber").disabled = false;
document.getElementById("parentAccessionNumber").style.opacity = "1";
document.getElementById("parentAccessionNumberlabel").style.opacity = "1";
}
}

function displayText() {
        
            document.getElementById('submitted').style.display = 'block';
  
}

function disable_cert() {
    if ((document.getElementById('nonhuman').checked == true) && (document.getElementById('human').checked == false)) {
        document.getElementById("step3").style.display = 'none';
         document.getElementById("nostep3").style.display = 'inline';
        document.getElementById("nocert").style.display = 'block';
        document.getElementById("nextcert").style.display = 'none';

       } 


       else {
         document.getElementById("step3").style.display = 'inline';
         document.getElementById("nostep3").style.display = 'none';
       	document.getElementById("nocert").style.display = 'none';
       	document.getElementById("nextcert").style.display = 'block';

       }
}

function otherText() {
    if ((document.getElementById('other').checked == true) && (document.getElementById('dbGaP').checked == false) && (document.getElementById('SRA').checked == false) && (document.getElementById('GDC').checked == false) && (document.getElementById('GEO').checked == false)) {
        document.getElementById("otherBox").style.display = 'inline';

       }

       else {
     document.getElementById("otherBox").style.display = 'none';

       }
}


function studyLinks() {
    document.getElementById('studyV').style.display = 'block';

       }


function toggleStudy(showHideDiv, switchImgTag) {
        var ele = document.getElementById(showHideDiv);
        var imageEle = document.getElementById(switchImgTag);
        if(ele.style.display == "block") {
                ele.style.display = "none";
    imageEle.innerHTML = '<img src="images/plus_icon.gif" height="10px" width="10px">';
        }
        else {
                ele.style.display = "block";
                imageEle.innerHTML = '<img src="images/minus_icon.gif" height="10px" width="10px">';
        }
}  



function toggleStudy2(showHideDiv, switchImgTag) {
        var ele = document.getElementById(showHideDiv);
        var imageEle = document.getElementById(switchImgTag);
        if(ele.style.display == "none") {
                ele.style.display = "block";
    imageEle.innerHTML = '<img src="images/minus_icon_bl.jpg" height="12px" width="12px">';
        }
        else {
                ele.style.display = "none";
                imageEle.innerHTML = '<img src="images/plus_icon_bl.jpg" height="12px" width="12px">';
        }
} 

function toggleStudy3(showHideDiv, switchImgTag) {
        var ele = document.getElementById(showHideDiv);
        var imageEle = document.getElementById(switchImgTag);
        if(ele.style.display == "block") {
                ele.style.display = "none";
    imageEle.innerHTML = '<img src="images/plus_icon.gif" height="10px" width="10px">';
        }
        else {
                ele.style.display = "block";
                imageEle.innerHTML = '<img src="images/minus_icon.gif" height="10px" width="10px">';
        }
}




function Clone() {
            var srcTable = document.getElementById ("DULdiv");
            var clonedTable = srcTable.cloneNode (true);
            clonedTable.id = "";    // clear the id property of the cloned table

            var container = document.getElementById ("DULdiv");
            container.appendChild (clonedTable);
        }


function toggle_visibility() {
    if (document.getElementById('generalR').checked) {
        document.getElementById('General').style.display = 'block';

       } 
      else {
         document.getElementById('General').style.display = 'none';
      }

       if (document.getElementById('HMB').checked) {
        document.getElementById('Health').style.display = 'block';

       }

         else   {
        document.getElementById('Health').style.display = 'none';
       

       }

             if (document.getElementById('dspecific').checked) {
        document.getElementById('Disease').style.display = 'block';
  document.getElementById('specText').style.display = 'inline';
       }

         else   {
        document.getElementById('Disease').style.display = 'none';
         document.getElementById('specText').style.display = 'none';
       

       }

       if (document.getElementById('other').checked) {
        document.getElementById('otherBox').style.display = 'inline';

       }

         else   {
        document.getElementById('otherBox').style.display = 'none';
       

       }

}   

function addStudy() {
       
        document.getElementById('study').style.display = 'block';

       } 


 function addStudy() {
       
        document.getElementById('study').style.display = 'block';

       } 


 function addRow(repositoryTablecb) {
    var table = document.getElementById("repositoryTable");
    var rowCount = table.rows.length;
            var row = table.insertRow(rowCount);
 
            var cell1 = row.insertCell(0);
            var element1 = document.createElement("select");
            element1.setAttribute ("name", "repository");
            element1.setAttribute ("id", "repository")
            element1.style.width = "98%";
              
  
         var option;
  
  // function to create options //
  function createOption(val,innerHtml) {
    var option = document.createElement("option");
    option.setAttribute("value", val);
    if (innerHtml==undefined) {
      innerHtml = val;
    }
    option.innerHTML = innerHtml;
    return option
  }

  function createElement(type,attributes) {
    var element = document.createElement(type);
    for (var key in attributes) {
      element[key] = attributes[key];
    };
    return element;
  }  


  /* create options elements */
  element1.appendChild(createOption("first",""));
  element1.appendChild(createOption("Database of Genotypes and Phenotypes (dbGaP)"));
  element1.appendChild(createOption("Sequence Read Archive (SRA)"));
  element1.appendChild(createOption("NCI Genomic Data Commons (GDC)"));
  element1.appendChild(createOption("Gene Expression Omnibus (GEO)"));
  element1.appendChild(createOption("Other (specify in comments)"));

  cell1.appendChild(element1);

  var cell2 = row.insertCell(1);

  var element2 = document.createElement("select");
  element2.setAttribute ("name", "submission");
  element2.setAttribute ("id", "submission")
  element2.style.width = "98%";

  /* create options elements for Submission*/

  element2.appendChild(createOption("first",""));
  element2.appendChild(createOption("Not Started"));
  element2.appendChild(createOption("In Process"));
  element2.appendChild(createOption("Completed"));

  cell2.appendChild(element2);

    var cell3 = row.insertCell(2);

  var element3 = document.createElement("select");
  element3.setAttribute ("name", "submission");
  element3.setAttribute ("id", "submission")
  element3.style.width = "98%";

  /* create options elements for Submission*/

  element3.appendChild(createOption("first",""));
  element3.appendChild(createOption("Not Started"));
  element3.appendChild(createOption("In Process"));
  element3.appendChild(createOption("Completed"));

  cell3.appendChild(element3);

  var cell4 = row.insertCell(3);
  cell4.style.width = "100px";
  cell4.appendChild(createElement("input",{"type":"text","size":"12"}));
  cell4.appendChild(createElement("img",{"src":"images/calendar_icon.gif","size":"15"}));

  var cell5 = row.insertCell(4);
    var element5 = document.createElement("select");
  element5.setAttribute ("name", "study");
  element5.setAttribute ("id", "study")
  element5.style.width = "98%";

  /* create options elements for Submission*/

  element5.appendChild(createOption("first",""));
  element5.appendChild(createOption("Yes"));
  element5.appendChild(createOption("No"));

  cell5.appendChild(element5);

var cell6 = row.insertCell(5);
  cell6.appendChild(createElement("input",{"type":"text"}));
   

  var cell7 = row.insertCell(6);
  cell7.style.width = "125px";
  cell7.appendChild(createElement("textarea",{"type":"textarea", "rows": "2"}));


  var cell8 = row.insertCell(7);
  var elmnt = document.getElementById("actions");
    var cln = elmnt.cloneNode(true);
    cell8.appendChild(cln);

}  



function myUpload(){
    var x = document.getElementById("myFile");
    var txt = "";
    


    if ('files' in x) {
        if (x.files.length == 0) {
            txt = "Select one or more files.";
        } else {
            for (var i = 0; i < x.files.length; i++) {
                
                var file = x.files[i];
                if ('name' in file) {
                    txt += "<strong style='vertical-align: bottom'>Name:</strong> " + file.name + "&nbsp;|&nbsp;";
                }
                if ('lastModifiedDate' in file) {
                    txt += "<strong style='vertical-align: bottom'>Date:</strong> " + file.lastModifiedDate + "&nbsp;|&nbsp;";
                }
                
                if ('size' in file) {
                    txt += "<strong style='vertical-align: bottom'>Size:</strong> " + file.size + " bytes &nbsp; <img src='images/system-delete-icon.png' height='12px' width='12x' align='absbottom' style='PADDING-BOTTOM: 1px'> <a href='#a' onClick='deleteUpload()' style='vertical-align: bottom';>delete</a>";

             }
  
            
            }
        }
    } 
    else {
        if (x.value == "") {
            txt += "Select one or more files.";
        } else {
            txt += "The files property is not supported by your browser!";
            txt  += "<br>The path of the selected file: " + x.value; // If the browser does not support the files property, it will return the path of the selected file instead. 
        }
    }
    document.getElementById("demo").innerHTML = txt;
}



function deleteUpload() {
  var inputL = document.getElementById("myFile");
if (document.getElementById("demo").style.display = 'inline'){
  document.getElementById("demo").style.display = 'none';
}
else if (inputL.clicked == true){
  document.getElementById("demo").style.display = 'inline';
}

else {
  document.getElementById("demo").style.display = 'inline';
}
}


function addStudyTable() {
  var studyTable = document.getElementById("studyTable");
  var cln = studyTable.cloneNode(true);

  document.getElementById("addContent").appendChild(cln);
}

function prov() {
    if (document.getElementById('finalProv').value == "provisional") {
        document.getElementById('verified').style.display = 'none';
        document.getElementById('verifiedList').style.display = 'none';
        document.getElementById('DULs').style.display = 'none';
        document.getElementById('sections').style.display = 'none';
        document.getElementById('add1').style.display = 'none';
       } 
      else {
         document.getElementById('verified').style.display = 'inline';
         document.getElementById('verifiedList').style.display = 'inline';
         document.getElementById('DULs').style.display = 'inline';
         document.getElementById('sections').style.display = 'block';
         document.getElementById('add1').style.display = 'block';
      }
}

function submitPlan(rad) {
var rads=document.getElementsByName(rad.name);
document.getElementById('uploader').style.display=(rads[0].checked)?'block':'none' ;
document.getElementById('textEditor').style.display=(rads[1].checked)?'block':'none' ;
}


function yesnoCheck() {

    if (document.getElementById('exceptionYes').checked) {
        document.getElementById('exceptionUpload').style.display = 'block';
        document.getElementById('questions').style.display = 'none';

    }

    else {
      document.getElementById('exceptionUpload').style.display = 'none';

    }

    if (document.getElementById('exceptionNo').checked)  {

        document.getElementById('questions').style.display = 'block';
         document.getElementById('exceptionUpload').style.display = 'none';
   }

   else {
    document.getElementById('questions').style.display = 'none';
   }
}

function disableIC() {  
  if (document.getElementById('yesIC').checked) {
        document.getElementById('addIC').style.display = 'none';
        document.getElementById('reviewedY').style.display = 'block';
}
 else {
    document.getElementById('addIC').style.display = 'inline';
    document.getElementById('reviewedY').style.display = 'none';
   }
}

