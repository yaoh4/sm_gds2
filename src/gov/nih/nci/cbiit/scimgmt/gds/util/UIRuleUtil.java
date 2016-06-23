package gov.nih.nci.cbiit.scimgmt.gds.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.json.JSONUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.nih.nci.cbiit.scimgmt.gds.constants.PlanQuestionList;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanQuestionsAnswer;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.model.UIElement;
import gov.nih.nci.cbiit.scimgmt.gds.model.UIList;

@SuppressWarnings("serial")
@Component
@Scope("singleton")
public class UIRuleUtil {

	private static final Logger logger = LogManager.getLogger(UIRuleUtil.class);

	private static Map<String, UIList> ruleMap = new HashMap<String, UIList>();


	@EventListener
	public void handleContextRefresh(ContextRefreshedEvent event) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			ruleMap = mapper.readValue(this.getClass().getResourceAsStream("/UIRule.json"),
					new TypeReference<Map<String, UIList>>() {
					});

			logger.info(JSONUtil.serialize(ruleMap));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public Map<String, UIList> getUiRuleMap(Project project) {
		
		// Based on Plan Answer Selection, compute the initial state of UI.
		List<String> selectedAnswers = new ArrayList<String> ();
		if(project != null) {
			for (PlanAnswerSelection selectedAnswer: project.getPlanAnswerSelection()) {
				selectedAnswers.add(selectedAnswer.getPlanQuestionsAnswer().getId().toString());
			}
		}
				
		Map<String, UIList> overRideMap = new HashMap<String, UIList> ();
		
		//Deep copy
		for (Entry<String, UIList> e : ruleMap.entrySet()) {
			UIList newList = new UIList(e.getValue());
			overRideMap.put(e.getKey(), newList);
		}

		// Iterate through the questions and apply UI rules if applicable
		for (Entry<Long, List<PlanQuestionsAnswer>> e : PlanQuestionList.getListMap().entrySet()) {
			Long qId = e.getKey();
			for(Entry<String, UIList> rule: ruleMap.entrySet()) {
				for(UIElement element: rule.getValue().getList()) {
					if(StringUtils.equals(element.getElementId(), qId.toString())) {
						if(selectedAnswers.contains(element.getValue())) {
							UIList newElement = new UIList();
							if(element.getOperation().equalsIgnoreCase("hide"))
								newElement.setStyle("display: none");
							if(element.getOperation().equalsIgnoreCase("show"))
								newElement.setStyle("display: block");
							if(ruleMap.containsKey(qId.toString()))
								newElement.setList(ruleMap.get(qId.toString()).getList());
							overRideMap.put(qId.toString(), newElement);
						}
					}
				}
			}
		}
		
		// If the answer to "Why is this project being submitted?" is NOT
		// "Required by GDS Policy" or "Required by GWAS Policy", then start from question
		// "What specimen type does the data submission pertain to?".
		if(project.getSubmissionReasonId() != 26 && project.getSubmissionReasonId() != 27) {
			UIList newElement = new UIList();
			newElement.setStyle("display: none");
			overRideMap.put("1", newElement); // Is there a data sharing exception requested for this project?
			overRideMap.put("4", newElement); // Was this exception approved?
			overRideMap.put("exceptionMemoDiv", newElement);
			overRideMap.put("8", newElement); // Will there be any data submitted?
			overRideMap.get("11").setStyle("display: block");
			overRideMap.get("14").setStyle("display: block");
			overRideMap.get("17").setStyle("display: block");
			overRideMap.get("20").setStyle("display: block");
			overRideMap.get("26").setStyle("display: block");
			overRideMap.get("29").setStyle("display: block");
			overRideMap.get("dataSharingPlanDiv").setStyle("display: block");
			overRideMap.put("textEditorDiv", newElement);
		}
		
		return overRideMap;
	}
	
}
