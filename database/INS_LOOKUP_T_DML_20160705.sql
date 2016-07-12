Insert into GDS.LOOKUP_T
   (ID, CODE, DISPLAY_NAME, DESCRIPTION, CREATED_DATE, CREATED_BY, DISCRIMINATOR)
 Values
   (42, 'ALL', 'All', 'All', sysdate, 'GDS', 'SEARCH_FROM');
Insert into GDS.LOOKUP_T
   (ID, CODE, DISPLAY_NAME, DESCRIPTION, CREATED_DATE, CREATED_BY, DISCRIMINATOR)
 Values
   (41, 'MYDOC', 'Project Submissions from my DOC', 'Project Submissions from my DOC', sysdate, 'GDS', 'SEARCH_FROM');
Insert into GDS.LOOKUP_T
   (ID, CODE, DISPLAY_NAME, DESCRIPTION, CREATED_DATE, CREATED_BY, DISCRIMINATOR)
 Values
   (40, 'MYSUB', 'My Project Submissions', 'My Project Submissions', sysdate, 'GDS', 'SEARCH_FROM');
Insert into GDS.LOOKUP_T
   (ID, CODE, DISPLAY_NAME, DESCRIPTION, CREATED_DATE, CREATED_BY, DISCRIMINATOR)
 Values
   (46, 'NOTSTARTED', 'Not Started', 'Not Started', sysdate, 'GDS', 'PAGE_STATUS');
Insert into GDS.LOOKUP_T
   (ID, CODE, DISPLAY_NAME, DESCRIPTION, CREATED_DATE, CREATED_BY, DISCRIMINATOR)
 Values
   (47, 'INPROGRESS', 'In Progress', 'In Progress', sysdate, 'GDS', 'PAGE_STATUS');
Insert into GDS.LOOKUP_T
   (ID, CODE, DISPLAY_NAME, DESCRIPTION, CREATED_DATE, CREATED_BY, DISCRIMINATOR)
 Values
   (48, 'GENERAL', 'General Info.', 'General Info.', sysdate, 'GDS', 'PAGE');
Insert into GDS.LOOKUP_T
   (ID, CODE, DISPLAY_NAME, DESCRIPTION, CREATED_DATE, CREATED_BY, DISCRIMINATOR)
 Values
   (49, 'BSI', 'Basic Study Info.', 'Basic Study Info.', sysdate, 'GDS', 'PAGE');
Insert into GDS.LOOKUP_T
   (ID, CODE, DISPLAY_NAME, DESCRIPTION, CREATED_DATE, CREATED_BY, DISCRIMINATOR)
 Values
   (50, 'REPOSITORY', 'Submission Status', 'Submission Status', sysdate, 'GDS', 'PAGE');