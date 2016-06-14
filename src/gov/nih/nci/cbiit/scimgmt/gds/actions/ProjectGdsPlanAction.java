package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.json.JSONUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.nih.nci.cbiit.scimgmt.gds.constants.PlanQuestionList;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanQuestionsAnswer;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.model.UIList;
import gov.nih.nci.cbiit.scimgmt.gds.util.UIRuleUtil;

/**
 * Manages Submission creation, updates and deletion.
 * 
 * @author dinhys
 *
 */
@SuppressWarnings("serial")
public class ProjectGdsPlanAction extends BaseAction {
	
	static Logger logger = LogManager.getLogger(ProjectGdsPlanAction.class);
	
	private Project project = new Project();
	private Map<String, UIList> map = new HashMap<String, UIList> ();
	protected PlanQuestionList questionList;
	
	/**
	 * Execute method, for now used for navigation
	 * 
	 * @return forward string
	 */
	public String execute() throws Exception {
		logger.debug("execute");
        
		ObjectMapper mapper = new ObjectMapper();
		map = UIRuleUtil.getUiRuleMap(project);
		logger.info(JSONUtil.serialize(map));
		
		logger.info(questionList.getQuestionById(1L).getDisplayText());
		
        return SUCCESS;
	}

	/**
	 * Save General Information
	 * 
	 * @return forward string
	 */
	public String save() throws Exception {
		
		logger.debug("saveGeneralInfo");
		
        return SUCCESS;
	}
	
	/**
	 * Validate Save General Info
	 */
	public void validate() {
		
		logger.debug("validateSaveGeneralInfo");
		
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Map<String, UIList> getMap() {
		return map;
	}

	public void setMap(Map<String, UIList> map) {
		this.map = map;
	}

	public List<PlanQuestionsAnswer> getAnswerListByQuestionId(Long qid) {
		return questionList.getAnswerListByQuestionId(qid);
	}

	public PlanQuestionsAnswer getQuestionById(Long qid) {
		return questionList.getQuestionById(qid);
	}

}
