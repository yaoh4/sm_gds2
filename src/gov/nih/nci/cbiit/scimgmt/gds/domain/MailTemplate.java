package gov.nih.nci.cbiit.scimgmt.gds.domain;

import java.util.Date;

/**
 * MailTemplate
 */
public class MailTemplate implements java.io.Serializable {

	private static final long serialVersionUID = -3651822832089859964L;
	private Integer id;
	private String shortIdentifier;
	private String description;
	private String subject;
	private String body;
	private Date createdDate;
	private String createdBy;
	private Date lastChangedDate;
	private String lastChangedBy;
	private Boolean active;

	public MailTemplate() {
	}

	public MailTemplate(Integer id, String subject, Date createdDate, String createdBy, Boolean active) {
		this.id = id;
		this.subject = subject;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.active = active;
	}

	public MailTemplate(Integer id, String shortIdentifier, String description, String subject, String body,
			Date createdDate, String createdBy, Date lastChangedDate, String lastChangedBy, Boolean send,
			Boolean active) {
		this.id = id;
		this.shortIdentifier = shortIdentifier;
		this.description = description;
		this.subject = subject;
		this.body = body;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.lastChangedDate = lastChangedDate;
		this.lastChangedBy = lastChangedBy;
		this.active = active;
	}

	public Boolean getActive() {
		return active;
	}

	public String getBody() {
		return body;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getDescription() {
		return description;
	}

	public Integer getId() {
		return id;
	}

	public String getLastChangedBy() {
		return lastChangedBy;
	}

	public Date getLastChangedDate() {
		return lastChangedDate;
	}

	public String getShortIdentifier() {
		return shortIdentifier;
	}

	public String getSubject() {
		return subject;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLastChangedBy(String lastChangedBy) {
		this.lastChangedBy = lastChangedBy;
	}

	public void setLastChangedDate(Date lastChangedDate) {
		this.lastChangedDate = lastChangedDate;
	}

	public void setShortIdentifier(String shortIdentifier) {
		this.shortIdentifier = shortIdentifier;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
