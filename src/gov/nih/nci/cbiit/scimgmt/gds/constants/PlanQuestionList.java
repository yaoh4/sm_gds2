package gov.nih.nci.cbiit.scimgmt.gds.constants;

import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanQuestionsAnswer;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "singleton")
public class PlanQuestionList {
	@Autowired
	private LookupService lookupService;

	private final static Map<Long, List<PlanQuestionsAnswer>> listMap = new HashMap<Long, List<PlanQuestionsAnswer>>();

	/**
	 * Gets the answers list by question id.
	 * 
	 * @param question id
	 *            the question id
	 * @return the list by question id
	 */
	public static List<PlanQuestionsAnswer> getAnswerListByQuestionId(Long qid) {
		List<PlanQuestionsAnswer> list = new ArrayList<PlanQuestionsAnswer>();
		for (PlanQuestionsAnswer i : listMap.get(qid)) {
			if (i.getQuestionId() != null) {
				list.add(i);
			}
		}
		return list;
	}

	/**
	 * Gets the entire list map.
	 * 
	 * @return the list map
	 */
	public static Map<Long, List<PlanQuestionsAnswer>> getListMap() {
		return listMap;
	}

	/**
	 * Gets the Question object by qid
	 * 
	 * @param qid
	 * @return
	 */
	public static PlanQuestionsAnswer getQuestionById(Long qid) {
		for (PlanQuestionsAnswer i : listMap.get(qid)) {
			if (i.getQuestionId() == null) {
				return i;
			}
		}
		return null;
	}

	/**
	 * Gets the Answer object by qid and id
	 * 
	 * @param qid
	 * @param id
	 * @return
	 */
	public static PlanQuestionsAnswer getAnswerById(Long qid, Long id) {
		for (PlanQuestionsAnswer i : listMap.get(qid)) {
			if (i.getId().equals(id)) {
				return i;
			}
		}
		return null;
	}

	/**
	 * Gets the Answer object by answer id only
	 * 
	 * @param id
	 * @return
	 */
	public static PlanQuestionsAnswer getAnswerByAnswerId(Long id) {
		for (Entry<Long, List<PlanQuestionsAnswer>> e : listMap.entrySet()) {
			for (PlanQuestionsAnswer i : listMap.get(e.getKey())) {
				if (i.getId().equals(id)) {
					return i;
				}
			}
		}
		return null;
	}
	
	/**
	 * Instantiates a new Plan Questions Answers lists.
	 */
	public PlanQuestionList() {

	}

	/**
	 * Initialize.
	 */
	@PostConstruct
	public void initialize() {
		loadListMap();
	}

	/**
	 * Load list map.
	 */
	private void loadListMap() {
		
		List<PlanQuestionsAnswer> allPlanQuestionsAnswers = lookupService.getAllPlanQuestionsAnswers();
		
		PlanQuestionsAnswer question = null;
		List<PlanQuestionsAnswer> answers = null;
		for(PlanQuestionsAnswer item : allPlanQuestionsAnswers) {
			if(item.getQuestionId() == null) {
				if(answers != null) {
					listMap.put(question.getId(), answers);
				}
				question = item;
				answers = new ArrayList<PlanQuestionsAnswer>();
			}
			answers.add(item);
		}
		if(question != null) {
			listMap.put(question.getId(), answers);
		}
	}

	/**
	 * Re-initialize.
	 */
	public void reinit() {
		listMap.clear();
		loadListMap();
	}

}
