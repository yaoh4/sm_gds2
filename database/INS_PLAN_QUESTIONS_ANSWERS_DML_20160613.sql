insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (1, null, 'Is there a data sharing exception requested for this project?', 1, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (2, 1, 'Yes', 1, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (3, 1, 'No', 2, 'Y', sysdate, 'dinhys');

insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (4, null, 'Was this exception approved?', 1, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (5, 4, 'Yes', 1, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (6, 4, 'No', 2, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (7, 4, 'Pending', 3, 'Y', sysdate, 'dinhys');

insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (8, null, 'Will there be any data submitted?', 1, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (9, 8, 'Yes', 1, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (10, 8, 'No', 2, 'Y', sysdate, 'dinhys');

insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (11, null, 'What specimen type does the data submission pertain to?', 1, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (12, 11, 'Human', 1, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (13, 11, 'Non-human', 2, 'Y', sysdate, 'dinhys');

insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (14, null, 'What type of data will be submitted?', 1, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (15, 14, 'Individual', 1, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (16, 14, 'Aggregate', 2, 'Y', sysdate, 'dinhys');

insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (17, null, 'What type of access is the data to be made available through?', 1, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (18, 17, 'Controlled', 1, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (19, 17, 'Unrestricted', 2, 'Y', sysdate, 'dinhys');

insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (20, null, 'What repository will the data be submitted to?', 1, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (21, 20, 'Database of Genotypes and Phenotypes (dbGaP)', 1, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (22, 20, 'Sequence Read Archive (SRA)', 2, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (23, 20, 'NCI Genomic Data Commons (GDC)', 3, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (24, 20, 'Gene Expression Omnibus (GEO)', 4, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (25, 20, 'Other', 5, 'Y', sysdate, 'dinhys');

insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (26, null, 'Has the GPA reviewed the Data Sharing Plan?', 1, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (27, 26, 'Yes', 1, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (28, 26, 'No', 2, 'Y', sysdate, 'dinhys');

insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (29, null, 'How would you like to submit the Data Sharing Plan?', 1, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (30, 29, 'Upload a document', 1, 'Y', sysdate, 'dinhys');
insert into GDS.PLAN_QUESTIONS_ANSWERS_T (ID, QUESTION_ID, DISPLAY_TEXT, DISPLAY_ORDER_NUM, ACTIVE_FLAG, CREATED_DATE, CREATED_BY) values (31, 29, 'Copy/paste into a text box', 2, 'Y', sysdate, 'dinhys');

