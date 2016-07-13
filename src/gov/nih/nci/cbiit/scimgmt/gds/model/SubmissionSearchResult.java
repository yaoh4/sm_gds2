package gov.nih.nci.cbiit.scimgmt.gds.model;

import java.util.List;


public class SubmissionSearchResult {

	private int draw = 0;
	private int recordsTotal = 0;
	private int recordsFiltered = 0;
	private String error;
	private List<Submission> data;

	public SubmissionSearchResult(){}

	public SubmissionSearchResult(List<Submission> data) {
		this.setData(data);
	}

	public List<Submission> getData() {
		return data;
	}


	public void setData(List<Submission> data) {
		this.data = data;
		this.recordsFiltered = data.size();
		this.recordsTotal= data.size();
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
