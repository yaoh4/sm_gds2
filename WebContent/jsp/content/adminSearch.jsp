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
	<!-- Header -->
	






<div class="logo-header"> </div>



<div id="wrap"> 
	<div class="navbar ">
		<div class="row">
			<div class="container">
				<div id="logo" class="logoImage">
				    <img src="../../images/nci-logo-full.svg" width="450px;" alt="National Cancer Institute">
				    <div style="float: right; padding-top: 15px; padding-bottom: 15px;">
					    <div class="nav navbar-nav" style="display:inline-block;">
					  	    Welcome:
						    <div id="loginID" class="login">
							    
								    Janene Nusraty
							    
						    </div>
					    </div><br>  
					    <div style="display:inline-block; padding-right: 10px;">
					        
					        Env.: <span class="question" style="font-size: 14px;">Development</span>
					    </div>
					    <div style="display:inline-block; margin-right: 10px;">
					        <img src="../../images/version-divider.gif" alt="">
					        &nbsp;&nbsp;&nbsp;Version: <span class="question" style="font-size: 14px;">1.0</span>
					    </div>
				   <img src="../../images/version-divider.gif" alt="">
				  <div id="popup_nav" style="clear:right; display:inline-block; margin-left: 10px;">
				   	
				    <ul>
				      <li>
				        <a href="#">Send Comments&nbsp;  <i class="fa fa-caret-down" aria-hidden="true"></i>&nbsp;&nbsp;</a>
		                <ul>
	               			<li><a href="mailto:ncicbiitsciencemanagementteam@mail.nih.gov?subject=GDS Tracking System">Business Policy Questions</a></li>
	               			<li><a href="mailto:ncicbiitsciencemanagementteam@mail.nih.gov?subject=GDS Tracking System">Technical Issues</a></li>												
	               		</ul>
	               	  </li>
	               	</ul>
				  </div>
				   </div>
				    <div class="GDS"><h3 style="padding: 0px; margin:0px; display:inline;">Genomic Data Sharing Tracking System</h3></div>
				</div>
			</div>
			<!-- Fixed navbar -->
			<div class="stickyDiv" style="background: #fff;">
			



<!--Main Navigation -->

<div id="primNav" class="row">
	<div class="container">
		<nav>
			<div id="mainNav" class="navbar-header" style="display:block;">
				<ul class="nav navbar-nav navbar-main">
			<!-- 	
				
					<li><a href="../../search/home.action">Home</a></li>
				 -->
				
				
					<li ><a href="javascript: void(0)">Create New Submission</a></li>
				
				
				
						
				
				
					<li><a href="../../search/home.action">Search</a></li>

					<li class="active"><a href="../../administration/home.action">Administration</a></li>
				
				</ul>
			</div>
		</nav>
	</div>
</div>

		
		 

    		<!--Page Header -->
    		
    		
    			
    				<div class="pageHeader" id="pageHeader"><div class="titleWrapper container"><h3>Administration</h3></div></div>
    			
    			
      		<div class="navbar navbar-default2">  
<div id="secNav" class="row">
<div class="container">
	<div class="navbar-collapse main-nav subnav">
		<ul class="nav navbar-nav ">
			
			
				
				
					<li class="active"><a href="javascript: void(0)">Search User Accounts</a></li>
				
				
				<li class="divider-vertical"></li>
				
				
				  
				  
					
						
					
					<li><a href="adminEdit.htm">Edit/Create User Accounts</a></li>
				  
				  <li class="divider-vertical"></li>
			
				
				
				
				
					
					
						
							
								 
			
		</ul>
	</div>
</div>
</div>
</div>
      		
			
		</div>
	</div>
	</div>


	<!-- end Header -->

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
					<label for="Intramural(Z01)/Grant/Contract #:">First Name:</label> 
					<input type="text" name="criteria.grantContractNum" maxlength="30" value="" id="grantNumber" class="form-control">
				</div>

				<div class="col-xs-5">
					<label for="Intramural(Z01)/Grant/Contract #:">Last Name:</label> 
					<input type="text" name="criteria.grantContractNum" maxlength="30" value="" id="grantNumber" class="form-control">
				</div>


				
   
   


			<div class="col-xs-5">
					<label for="Intramural(Z01)/Grant/Contract #:">Email:</label> 
					<input type="text" name="criteria.grantContractNum" maxlength="30" value="" id="grantNumber" class="form-control">
				</div>
					<div class="col-xs-5">
					<label for="Program Director">User Role:</label>
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
		</div> <!--end panel-->
		
		
           
     </div> <!--  end Panel  -->
	
	  </div>
		
	  




<!-- /container -->

<script type="text/javascript" src="../../controllers/gds.js"></script>
<script type="text/javascript" src="../../controllers/grantSearch.js"></script>
<script type="text/javascript" src="../../controllers/general.js"></script>
	</div>
	<!-- end Content -->

	<!-- Footer -->
	

<!-- Footer -->
<footer class="site-footer">
	<div class="container">
		<div id="nvcgSlFooter">
			<div class="contentid-911586 slot-item only-SI">
				<div class="row">
				</div>
				<div class="row">
					<div class="large-12 small-centered columns agencies">
						<ul>
							<li><a href="http://www.cancer.gov/policies/accessibility" target="_blank">Accessibility</a></li>
							<li><a href="http://www.cancer.gov/policies/disclaimer" target="_blank">Disclaimer</a></li>
							<li><a href="http://www.cancer.gov/policies/foia" target="_blank">FOIA</a></li>
							<li class="last"><a href="http://www.cancer.gov/policies/privacy-security" target="_blank">Privacy &amp; Security</a></li>

						</ul>
						<ul>
							<li><a href="http://www.hhs.gov" target="_blank">U.S. Department of
									Health and Human Services</a></li>
							<li><a href="http://www.nih.gov" target="_blank">National Institutes of
									Health</a></li>
							<li><a href="http://www.cancer.gov/" target="_blank">National Cancer Institute</a></li>
							<li class="last"><a href="http://usa.gov" target="_blank">USA.gov</a></li>
						</ul>
					</div>
				</div>
				<div class="row">
					<div class="large-12 small-centered columns tagline">
						NIH ... Turning Discovery Into Health<sup>®</sup>
					</div>
				</div>
				<!--back to top arrow-->
				<a href="#" class="back-to-top" style="display: none;">TOP</a>
			</div>
		</div>
	</div>
</footer>
<!-- end Footer -->
</div> 

	<!-- end Footer -->



</body></html>