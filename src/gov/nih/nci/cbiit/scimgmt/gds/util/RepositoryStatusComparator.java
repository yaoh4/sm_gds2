package gov.nih.nci.cbiit.scimgmt.gds.util;


import java.util.Comparator;

import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
/**
 *  A Comparator to order the Repository Status.
 *  
 *  @author tembharend
 */
@SuppressWarnings("rawtypes")
public class RepositoryStatusComparator implements Comparator
{ 		
	@Override
	public int compare(Object obj1, Object obj2) {

		RepositoryStatus status1 = (RepositoryStatus)obj1;
		RepositoryStatus status2 = (RepositoryStatus)obj2;		

		Long cntr1 = status1.getPlanAnswerSelectionTByRepositoryId().getPlanQuestionsAnswer().getDisplayOrderNum();
		Long cntr2 = status2.getPlanAnswerSelectionTByRepositoryId().getPlanQuestionsAnswer().getDisplayOrderNum();

		int diff =  cntr1.compareTo(cntr2);
		if (diff != 0) {
			if(diff > 0) {
				return 1;} 
			else { return -1;}
		}

		return  (status1.getPlanAnswerSelectionTByRepositoryId().getOtherText() == null? -1: status1.getPlanAnswerSelectionTByRepositoryId().getOtherText().compareTo(status2.getPlanAnswerSelectionTByRepositoryId().getOtherText()));

	}
}