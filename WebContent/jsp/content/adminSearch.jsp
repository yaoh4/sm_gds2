<html lang="en" xmlns="http://www.w3.org/1999/xhtml"><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8">
<title>GDS Tracking System</title>
<script src="../../scripts/jquery-1.12.3.min.js"></script>
<script src="../../scripts/bootstrap-3.3.6.min.js"></script>
<script src="../../scripts/bootbox-4.4.0.min.js"></script>
<script src="../../scripts/bootstrap-datepicker-1.6.1.min.js"></script>
<script src="../../scripts/datatables-1.10.12.min.js"></script>
<script src="../../scripts/jquery.validate-1.15.0.min.js"></script>
<script src="../../scripts/jquery.are-you-sure-1.9.0.js"></script>
<script src="../../scripts/theme.js"></script>

<script src="../../controllers/gds.js"></script>

<link href="../../stylesheets/bootstrap-3.3.6.min.css" rel="stylesheet" type="text/css" media="screen">
<!-- <link href="../../stylesheets/non-responsive.css" rel="stylesheet" type="text/css" media="screen" /> -->
<link href="../../stylesheets/bootstrap-datepicker-1.6.1.min.css" rel="stylesheet" type="text/css" media="screen">
<link href="../../stylesheets/datatables-1.10.12.min.css" rel="stylesheet" type="text/css" media="screen">
<link href="../../stylesheets/font-awesome-4.6.3.min.css" rel="stylesheet" type="text/css" media="screen">
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600,600italic,700,700italic,900,900italic,400italic" rel="stylesheet" type="text/css">
<link href="../../stylesheets/custom.css" rel="stylesheet" type="text/css" media="screen">
<link href="../../stylesheets/datatable.css" rel="stylesheet" type="text/css" media="screen">
<link href="../../stylesheets/styles.css" rel="stylesheet" type="text/css" media="screen">

<script src="../../struts/utils.js" type="text/javascript"></script>

</head>

<body>
	
	<div id="messages" class="container"></div>

	<!-- Content start -->
	<div class="container">
		


	<!--Begin Form -->
	<form id="general_form" name="general_form" action="viewProject.action" method="post" class="dirty-check" data-toggle="validator" role="form">
		<!-- Page navbar -->
		<input type="hidden" name="projectId" value="" id="projectId">
		<input type="hidden" name="applId" value="" id="applId">
		<input type="hidden" name="project.applClassCode" value="G" id="applClassCode">
		<input type="hidden" name="project.parentProjectId" value="" id="parentId">
		<input type="hidden" name="project.projectGroupId" value="" id="general_form_project_projectGroupId">
	
	  <div id="searchGrantsContracts" style="">
	    

    
      
      
      <!-- Begin Panel -->
      <div class="col-md-12">           
        <div class="panel project-panel-primary" id="searchGrant">					
		  
		  <div class="panel-heading">
		    <div class="pheader">
			  <h4>Search for GDS User Accounts</h4>
			</div>
		  </div>

		  <!--end panel header-->
		  <div class="panel-body">					
		    <div class="form-group row">

		    	<div class="col-xs-5">
					<label for="First Name">First Name:</label> 
					<input type="text" name="criteria.grantContractNum" maxlength="30" value="" id="grantNumber" class="form-control">
				</div>

				<div class="col-xs-5">
					<label for="Last Name">Last Name:</label> 
					<input type="text" name="criteria.grantContractNum" maxlength="30" value="" id="grantNumber" class="form-control">
				</div>


				
   
   


			<div class="col-xs-5">
									<label for="Institutes/Centers">Institutes/Centers:</label>
									<select name="" id="Institutes/Centers" class="c-select form-control">
										<option value="">Select Institute/Center</option>
										<option value="21">CC - Warren Grant Magnuson Clinical Center</option>
										<option value="22">CIT - Center for Information Technology</option>
										<option value="26">CSR - Center for Scientific Review</option>
										<option value="25">FIC - John E Fogarty International Center</option>
										<option value="27">NCATS - National Center for Advancing Translational Sciences</option>
										<option value="23">NCCIH - National Center for Complementary and Integrative Health</option>
										<option value="2">NCI - National Cancer Institute</option>
										<option value="24">NCRR - National Center for Research Resources</option>
										<option value="3">NEI - National Eye Institute</option>
										<option value="5">NHGRI - National Human Genome Research Institute</option>
										<option value="4">NHLBI - National Heart Lung and Blood Institute</option>
										<option value="6">NIA - National Institute on Aging</option>
										<option value="7">NIAAA - National Institute on Alcohol Abuse and Alcoholism</option>
										<option value="8">NIAID - National Institute of Allergy and Infectious Diseases</option>
										<option value="9">NIAMS - National Inst of Arthritis and Musculoskeletal and Skin Diseases</option>
										<option value="84">NIBIB - National Institute of Biomedical Imaging and Bioengineering</option>
										<option value="10">NICHD - National Institute of Child Health and Human Development</option>
										<option value="14">NIDA - National Institute on Drug Abuse</option>
										<option value="11">NIDCD - National Institute on Deafness and Communication Disorders</option>
										<option value="12">NIDCR - National Institute of Dental and Craniofacial Research</option>
										<option value="13">NIDDK - National Institute of Diabetes and Digestive and Kidney Diseases</option>
										<option value="15">NIEHS - National Institute of Environmental Health Sciences</option>
										<option value="16">NIGMS - National Institute of General Medical Sciences</option>
										<option value="17">NIMH - National Institute of Mental Health</option>
										<option value="83">NIMHD - National Institute on Minority Health and Health Disparities</option>
										<option value="18">NINDS - National Institute of Neurological Disorders and Stroke</option>
										<option value="19">NINR - National Institute of Nursing Research</option>
										<option value="20">NLM - National Library of Medicine</option>
										<option value="1">OD - Office of the Director</option>
									</select>
								</div>
								
					<div class="col-xs-5">
					<label for="User Role">User Role:</label>
					<select name="criteria.pdNpnId" id="directorSelect" class="c-select form-control">
    <option value="">Select User Role</option>
     <option value="">GPA</option>
      <option value="">GPA Admin</option>
      <option value="">GDS User - Edit</option>
      <option value="">GDS User - Read Only</option>
   
   


			</select>
			</div>
				

				
			

		
            <div class="searchFormat col-xs-10" style="float:right; margin-top: 10px; padding-left: 70px;">
              <button type="button" class="btn btn-primary has-spinner" id="searchGrants" onclick="searchGrantsData()"><i class="fa fa-spinner fa-spin"></i> Search</button> 
              <button type="button" class="btn btn-default" id="reset" onclick="resetData()">Reset</button> 
              <p>&nbsp;</p>
            </div>

            </div>
							
			<!--Begin Search Results-->
            <div id="searchResults" style="margin-left: 10px;">
            	<h4>&nbsp;</h4>
            	<h4>Search Results</h4><br/>&nbsp;
              <table style="width: 95%;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
                <tbody><tr class="modalTheader">
                  <th class="tableHeader" align="center" scope="col">User Name</th>
                  <th class="tableHeader" style="whitespace: nowrap;" scope="col">Email Address</th>
                  <th class="tableHeader"  scope="col">Current Role(s)</th>
                  <th class="tableHeader" scope="col">DOC</th>
                  <th class="tableHeader" scope="col">Created/Last Updated On:</th>
                  <th class="tableHeader" scope="col">Created/Last Updated By:</th>
                   <th class="tableHeader" scope="col">Actions</th>

                </tr>
                <tr>
                	<td>Catherine Fishman</td>
                	<td><a href="mailto:Fishmanc@mail.nih.gov">Fishmanc@mail.nih.gov</a></td>
                	<td>GPA</td>
                	<td>DEA</td>
                	<td>09/15/2016</td>
                	<td>Charlisse Caga-Anan</td>
                	<td><div style="white-space: nowrap; font-size: 14px;"><a href="../manage/navigateToSubmissionDetail.action?projectId=29"><i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="Edit" title="Edit"></i></a>&nbsp;&nbsp;&nbsp;<a onclick="deleteSubmission(29)" href="javascript: void(0)"><i class="fa fa-trash fa-lg" aria-hidden="true" alt="Delete" title="Delete"></i></a></div></td>
				
					
				
				
				  
				
			  </tbody></table>
			  <div id="prevLinkedSubmissions" tabindex="-1" style="display: none;"></div>
			</div>	<!--end search results-->
		  </div> <!--end panel body-->

		
	  


     </div> <!--  end Panel  -->
	
	  </div>
		
	 


		
			
		
		

		</div>





<!-- /container -->


	</div>
	<!-- end Content -->

	</body></html>