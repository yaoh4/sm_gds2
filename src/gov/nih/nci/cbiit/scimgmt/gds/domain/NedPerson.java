package gov.nih.nci.cbiit.scimgmt.gds.domain;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * The Class NedPerson.
 */
@Component
public class NedPerson implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Boolean nihuidquality;
	private Date createdDate;
	private Date inactiveDate;
	private Date nihcreatetimestamp;
	private Date nihdirentryeffectivedate;
	private Date nihdirentryexpirationdate;
	private Date nihidbadgeexpdate;
	private Date nihidbadgereqdate;
	private Date nihidbadgereqexpdt;
	private Date nihmodifytimestamp;
	private Date nihuidvalidationts;
	private Integer id;
	private String adTestUserId;
	private String adUserId;
	private String building;
	private String c;
	private String description;
	private String email;
	private String facsimiletelephone;
	private String firstName;
	private String generationqualif;
	private String initials;
	private String l;
	private String lastName;
	private String manager;
	private String middleName;
	private String mobiletelephonenum;
	private String nedId;
	private String nihSac;
	private String nihbadgetitle;
	private String nihcommongenqualif;
	private String nihcommonmiddlenam;
	private String nihcommonsn;
	private String nihcreatorsname;
	private String nihdeliveryaddress;
	private String nihdirentrynoprint;
	private String nihdirentryunlist;
	private String nihdupuid;
	private String nihhhsuniquemail;
	private String nihidbadgeissuerea;
	private String nihidbadgeless6mos;
	private String nihidbadgereqtype;
	private String nihipd;
	private String nihlibraryauth;
	private String nihmailstop;
	private String nihmdslinktohelp;
	private String nihmdslinktoph;
	private String nihmixcasecommonsn;
	private String nihmixcasesn;
	private String nihmodifiersname;
	private String nihnomiddlename;
	private String nihorgacronym;
	private String nihorgname;
	private String nihouacronym;
	private String nihouname;
	private String nihphysicaladdress;
	private String nihphyspostalcode;
	private String nihpoc;
	private String nihprimarysmtp;
	private String nihsite;
	private String nihssodomain;
	private String nihssousername;
	private String nihsuffixqualifier;
	private String nihsummerstatus;
	private String nihtty;
	private String nihuidvalidator;
	private String nihuniquemail;
	private String orgPath;
	private String orgStatus;
	private String pagertelephonenum;
	private String personaltitle;
	private String phone;
	private String postaladdress;
	private String postalcode;
	private String preferredName;
	private String room;
	private String secretary;
	private String st;
	private String street;
	private String title;

	private String roles;
	private Boolean dummy;

	/**
	 * Gets the ad test user id.
	 * 
	 * @return the ad test user id
	 */
	public String getAdTestUserId() {
		return adTestUserId;
	}

	/**
	 * Gets the ad user id.
	 * 
	 * @return the ad user id
	 */
	public String getAdUserId() {
		return adUserId;
	}

	/**
	 * Gets the building.
	 * 
	 * @return the building
	 */
	public String getBuilding() {
		return building;
	}

	/**
	 * Gets the c.
	 * 
	 * @return the c
	 */
	public String getC() {
		return c;
	}

	/**
	 * Gets the created date.
	 * 
	 * @return the created date
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	public Boolean getDummy() {
		return dummy;
	}

	/**
	 * Gets the email.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Gets the facsimiletelephone.
	 * 
	 * @return the facsimiletelephone
	 */
	public String getFacsimiletelephone() {
		return facsimiletelephone;
	}

	/**
	 * Gets the first name.
	 * 
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the full name.
	 * 
	 * @return the full name
	 */
	public String getFullName() {

		final StringBuffer sb = new StringBuffer(40);

		if (StringUtils.isNotBlank(getPreferredName())) {
			sb.append(getPreferredName());
		} else {
			sb.append(getFirstName());
		}
		sb.append(" ");

		sb.append(getLastName());
		return sb.toString();
	}

	/**
	 * Gets the full name.
	 * 
	 * @return the full name
	 */
	public String getFullNameLF() {

		final StringBuffer sb = new StringBuffer(40);

		sb.append(getLastName());
		sb.append(", ");
		if (StringUtils.isNotBlank(getPreferredName())) {
			sb.append(getPreferredName());
		} else {
			sb.append(getFirstName());
		}

		return sb.toString();
	}

	/**
	 * Gets the generationqualif.
	 * 
	 * @return the generationqualif
	 */
	public String getGenerationqualif() {
		return generationqualif;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Gets the inactive date.
	 * 
	 * @return the inactive date
	 */
	public Date getInactiveDate() {
		return inactiveDate;
	}

	/**
	 * Gets the initials.
	 * 
	 * @return the initials
	 */
	public String getInitials() {
		return initials;
	}

	/**
	 * Gets the l.
	 * 
	 * @return the l
	 */
	public String getL() {
		return l;
	}

	/**
	 * Gets the last name.
	 * 
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the manager.
	 * 
	 * @return the manager
	 */
	public String getManager() {
		return manager;
	}

	/**
	 * Gets the middle name.
	 * 
	 * @return the middle name
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * Gets the mobiletelephonenum.
	 * 
	 * @return the mobiletelephonenum
	 */
	public String getMobiletelephonenum() {
		return mobiletelephonenum;
	}

	/**
	 * Gets the ned id.
	 * 
	 * @return the ned id
	 */
	public String getNedId() {
		return nedId;
	}

	/**
	 * Gets the nihbadgetitle.
	 * 
	 * @return the nihbadgetitle
	 */
	public String getNihbadgetitle() {
		return nihbadgetitle;
	}

	/**
	 * Gets the nihcommongenqualif.
	 * 
	 * @return the nihcommongenqualif
	 */
	public String getNihcommongenqualif() {
		return nihcommongenqualif;
	}

	/**
	 * Gets the nihcommonmiddlenam.
	 * 
	 * @return the nihcommonmiddlenam
	 */
	public String getNihcommonmiddlenam() {
		return nihcommonmiddlenam;
	}

	/**
	 * Gets the nihcommonsn.
	 * 
	 * @return the nihcommonsn
	 */
	public String getNihcommonsn() {
		return nihcommonsn;
	}

	/**
	 * Gets the nihcreatetimestamp.
	 * 
	 * @return the nihcreatetimestamp
	 */
	public Date getNihcreatetimestamp() {
		return nihcreatetimestamp;
	}

	/**
	 * Gets the nihcreatorsname.
	 * 
	 * @return the nihcreatorsname
	 */
	public String getNihcreatorsname() {
		return nihcreatorsname;
	}

	/**
	 * Gets the nihdeliveryaddress.
	 * 
	 * @return the nihdeliveryaddress
	 */
	public String getNihdeliveryaddress() {
		return nihdeliveryaddress;
	}

	/**
	 * Gets the nihdirentryeffectivedate.
	 * 
	 * @return the nihdirentryeffectivedate
	 */
	public Date getNihdirentryeffectivedate() {
		return nihdirentryeffectivedate;
	}

	/**
	 * Gets the nihdirentryexpirationdate.
	 * 
	 * @return the nihdirentryexpirationdate
	 */
	public Date getNihdirentryexpirationdate() {
		return nihdirentryexpirationdate;
	}

	/**
	 * Gets the nihdirentrynoprint.
	 * 
	 * @return the nihdirentrynoprint
	 */
	public String getNihdirentrynoprint() {
		return nihdirentrynoprint;
	}

	/**
	 * Gets the nihdirentryunlist.
	 * 
	 * @return the nihdirentryunlist
	 */
	public String getNihdirentryunlist() {
		return nihdirentryunlist;
	}

	/**
	 * Gets the nihdupuid.
	 * 
	 * @return the nihdupuid
	 */
	public String getNihdupuid() {
		return nihdupuid;
	}

	/**
	 * Gets the nihhhsuniquemail.
	 * 
	 * @return the nihhhsuniquemail
	 */
	public String getNihhhsuniquemail() {
		return nihhhsuniquemail;
	}

	/**
	 * Gets the nihidbadgeexpdate.
	 * 
	 * @return the nihidbadgeexpdate
	 */
	public Date getNihidbadgeexpdate() {
		return nihidbadgeexpdate;
	}

	/**
	 * Gets the nihidbadgeissuerea.
	 * 
	 * @return the nihidbadgeissuerea
	 */
	public String getNihidbadgeissuerea() {
		return nihidbadgeissuerea;
	}

	/**
	 * Gets the nihidbadgeless6mos.
	 * 
	 * @return the nihidbadgeless6mos
	 */
	public String getNihidbadgeless6mos() {
		return nihidbadgeless6mos;
	}

	/**
	 * Gets the nihidbadgereqdate.
	 * 
	 * @return the nihidbadgereqdate
	 */
	public Date getNihidbadgereqdate() {
		return nihidbadgereqdate;
	}

	/**
	 * Gets the nihidbadgereqexpdt.
	 * 
	 * @return the nihidbadgereqexpdt
	 */
	public Date getNihidbadgereqexpdt() {
		return nihidbadgereqexpdt;
	}

	/**
	 * Gets the nihidbadgereqtype.
	 * 
	 * @return the nihidbadgereqtype
	 */
	public String getNihidbadgereqtype() {
		return nihidbadgereqtype;
	}

	/**
	 * Gets the nihipd.
	 * 
	 * @return the nihipd
	 */
	public String getNihipd() {
		return nihipd;
	}

	/**
	 * Gets the nihlibraryauth.
	 * 
	 * @return the nihlibraryauth
	 */
	public String getNihlibraryauth() {
		return nihlibraryauth;
	}

	/**
	 * Gets the nihmailstop.
	 * 
	 * @return the nihmailstop
	 */
	public String getNihmailstop() {
		return nihmailstop;
	}

	/**
	 * Gets the nihmdslinktohelp.
	 * 
	 * @return the nihmdslinktohelp
	 */
	public String getNihmdslinktohelp() {
		return nihmdslinktohelp;
	}

	/**
	 * Gets the nihmdslinktoph.
	 * 
	 * @return the nihmdslinktoph
	 */
	public String getNihmdslinktoph() {
		return nihmdslinktoph;
	}

	/**
	 * Gets the nihmixcasecommonsn.
	 * 
	 * @return the nihmixcasecommonsn
	 */
	public String getNihmixcasecommonsn() {
		return nihmixcasecommonsn;
	}

	/**
	 * Gets the nihmixcasesn.
	 * 
	 * @return the nihmixcasesn
	 */
	public String getNihmixcasesn() {
		return nihmixcasesn;
	}

	/**
	 * Gets the nihmodifiersname.
	 * 
	 * @return the nihmodifiersname
	 */
	public String getNihmodifiersname() {
		return nihmodifiersname;
	}

	/**
	 * Gets the nihmodifytimestamp.
	 * 
	 * @return the nihmodifytimestamp
	 */
	public Date getNihmodifytimestamp() {
		return nihmodifytimestamp;
	}

	/**
	 * Gets the nihnomiddlename.
	 * 
	 * @return the nihnomiddlename
	 */
	public String getNihnomiddlename() {
		return nihnomiddlename;
	}

	/**
	 * Gets the nihorgacronym.
	 * 
	 * @return the nihorgacronym
	 */
	public String getNihorgacronym() {
		return nihorgacronym;
	}

	/**
	 * Gets the nihorgname.
	 * 
	 * @return the nihorgname
	 */
	public String getNihorgname() {
		return nihorgname;
	}

	/**
	 * Gets the nihouacronym.
	 * 
	 * @return the nihouacronym
	 */
	public String getNihouacronym() {
		return nihouacronym;
	}

	/**
	 * Gets the nihouname.
	 * 
	 * @return the nihouname
	 */
	public String getNihouname() {
		return nihouname;
	}

	/**
	 * Gets the nihphysicaladdress.
	 * 
	 * @return the nihphysicaladdress
	 */
	public String getNihphysicaladdress() {
		return nihphysicaladdress;
	}

	/**
	 * Gets the nihphyspostalcode.
	 * 
	 * @return the nihphyspostalcode
	 */
	public String getNihphyspostalcode() {
		return nihphyspostalcode;
	}

	/**
	 * Gets the nihpoc.
	 * 
	 * @return the nihpoc
	 */
	public String getNihpoc() {
		return nihpoc;
	}

	/**
	 * Gets the nihprimarysmtp.
	 * 
	 * @return the nihprimarysmtp
	 */
	public String getNihprimarysmtp() {
		return nihprimarysmtp;
	}

	/**
	 * Gets the nih sac.
	 * 
	 * @return the nih sac
	 */
	public String getNihSac() {
		return nihSac;
	}

	/**
	 * Gets the nihsite.
	 * 
	 * @return the nihsite
	 */
	public String getNihsite() {
		return nihsite;
	}

	/**
	 * Gets the nihssodomain.
	 * 
	 * @return the nihssodomain
	 */
	public String getNihssodomain() {
		return nihssodomain;
	}

	/**
	 * Gets the nihssousername.
	 * 
	 * @return the nihssousername
	 */
	public String getNihssousername() {
		return nihssousername;
	}

	/**
	 * Gets the nihsuffixqualifier.
	 * 
	 * @return the nihsuffixqualifier
	 */
	public String getNihsuffixqualifier() {
		return nihsuffixqualifier;
	}

	/**
	 * Gets the nihsummerstatus.
	 * 
	 * @return the nihsummerstatus
	 */
	public String getNihsummerstatus() {
		return nihsummerstatus;
	}

	/**
	 * Gets the nihtty.
	 * 
	 * @return the nihtty
	 */
	public String getNihtty() {
		return nihtty;
	}

	/**
	 * Gets the nihuidquality.
	 * 
	 * @return the nihuidquality
	 */
	public Boolean getNihuidquality() {
		return nihuidquality;
	}

	/**
	 * Gets the nihuidvalidationts.
	 * 
	 * @return the nihuidvalidationts
	 */
	public Date getNihuidvalidationts() {
		return nihuidvalidationts;
	}

	/**
	 * Gets the nihuidvalidator.
	 * 
	 * @return the nihuidvalidator
	 */
	public String getNihuidvalidator() {
		return nihuidvalidator;
	}

	/**
	 * Gets the nihuniquemail.
	 * 
	 * @return the nihuniquemail
	 */
	public String getNihuniquemail() {
		return nihuniquemail;
	}

	/**
	 * Gets the org path.
	 * 
	 * @return the org path
	 */
	public String getOrgPath() {
		return orgPath;
	}

	/**
	 * Gets the org status.
	 * 
	 * @return the org status
	 */
	public String getOrgStatus() {
		return orgStatus;
	}

	/**
	 * Gets the pagertelephonenum.
	 * 
	 * @return the pagertelephonenum
	 */
	public String getPagertelephonenum() {
		return pagertelephonenum;
	}

	/**
	 * Gets the personaltitle.
	 * 
	 * @return the personaltitle
	 */
	public String getPersonaltitle() {
		return personaltitle;
	}

	/**
	 * Gets the phone.
	 * 
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Gets the postaladdress.
	 * 
	 * @return the postaladdress
	 */
	public String getPostaladdress() {
		return postaladdress;
	}

	/**
	 * Gets the postalcode.
	 * 
	 * @return the postalcode
	 */
	public String getPostalcode() {
		return postalcode;
	}

	/**
	 * Gets the preferred name.
	 * 
	 * @return the preferred name
	 */
	public String getPreferredName() {
		return preferredName != null ? preferredName : "";
	}

	public String getRoles() {
		return roles;
	}

	/**
	 * Gets the room.
	 * 
	 * @return the room
	 */
	public String getRoom() {
		return room;
	}

	/**
	 * Gets the secretary.
	 * 
	 * @return the secretary
	 */
	public String getSecretary() {
		return secretary;
	}

	/**
	 * Gets the st.
	 * 
	 * @return the st
	 */
	public String getSt() {
		return st;
	}

	/**
	 * Gets the street.
	 * 
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Gets the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the ad test user id.
	 * 
	 * @param adTestUserId
	 *            the new ad test user id
	 */
	public void setAdTestUserId(String adTestUserId) {
		this.adTestUserId = adTestUserId;
	}

	/**
	 * Sets the ad user id.
	 * 
	 * @param adUserId
	 *            the new ad user id
	 */
	public void setAdUserId(String adUserId) {
		this.adUserId = adUserId;
	}

	/**
	 * Sets the building.
	 * 
	 * @param building
	 *            the new building
	 */
	public void setBuilding(String building) {
		this.building = building;
	}

	/**
	 * Sets the c.
	 * 
	 * @param c
	 *            the new c
	 */
	public void setC(String c) {
		this.c = c;
	}

	/**
	 * Sets the created date.
	 * 
	 * @param createdDate
	 *            the new created date
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public void setDummy(Boolean dummy) {
		this.dummy = dummy;
	}

	/**
	 * Sets the email.
	 * 
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Sets the facsimiletelephone.
	 * 
	 * @param facsimiletelephone
	 *            the new facsimiletelephone
	 */
	public void setFacsimiletelephone(String facsimiletelephone) {
		this.facsimiletelephone = facsimiletelephone;
	}

	/**
	 * Sets the first name.
	 * 
	 * @param firstName
	 *            the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Sets the generationqualif.
	 * 
	 * @param generationqualif
	 *            the new generationqualif
	 */
	public void setGenerationqualif(String generationqualif) {
		this.generationqualif = generationqualif;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Sets the inactive date.
	 * 
	 * @param inactiveDate
	 *            the new inactive date
	 */
	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}

	/**
	 * Sets the initials.
	 * 
	 * @param initials
	 *            the new initials
	 */
	public void setInitials(String initials) {
		this.initials = initials;
	}

	/**
	 * Sets the l.
	 * 
	 * @param l
	 *            the new l
	 */
	public void setL(String l) {
		this.l = l;
	}

	/**
	 * Sets the last name.
	 * 
	 * @param lastName
	 *            the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Sets the manager.
	 * 
	 * @param manager
	 *            the new manager
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}

	/**
	 * Sets the middle name.
	 * 
	 * @param middleName
	 *            the new middle name
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * Sets the mobiletelephonenum.
	 * 
	 * @param mobiletelephonenum
	 *            the new mobiletelephonenum
	 */
	public void setMobiletelephonenum(String mobiletelephonenum) {
		this.mobiletelephonenum = mobiletelephonenum;
	}

	/**
	 * Sets the ned id.
	 * 
	 * @param nedId
	 *            the new ned id
	 */
	public void setNedId(String nedId) {
		this.nedId = nedId;
	}

	/**
	 * Sets the nihbadgetitle.
	 * 
	 * @param nihbadgetitle
	 *            the new nihbadgetitle
	 */
	public void setNihbadgetitle(String nihbadgetitle) {
		this.nihbadgetitle = nihbadgetitle;
	}

	/**
	 * Sets the nihcommongenqualif.
	 * 
	 * @param nihcommongenqualif
	 *            the new nihcommongenqualif
	 */
	public void setNihcommongenqualif(String nihcommongenqualif) {
		this.nihcommongenqualif = nihcommongenqualif;
	}

	/**
	 * Sets the nihcommonmiddlenam.
	 * 
	 * @param nihcommonmiddlenam
	 *            the new nihcommonmiddlenam
	 */
	public void setNihcommonmiddlenam(String nihcommonmiddlenam) {
		this.nihcommonmiddlenam = nihcommonmiddlenam;
	}

	/**
	 * Sets the nihcommonsn.
	 * 
	 * @param nihcommonsn
	 *            the new nihcommonsn
	 */
	public void setNihcommonsn(String nihcommonsn) {
		this.nihcommonsn = nihcommonsn;
	}

	/**
	 * Sets the nihcreatetimestamp.
	 * 
	 * @param nihcreatetimestamp
	 *            the new nihcreatetimestamp
	 */
	public void setNihcreatetimestamp(Date nihcreatetimestamp) {
		this.nihcreatetimestamp = nihcreatetimestamp;
	}

	/**
	 * Sets the nihcreatorsname.
	 * 
	 * @param nihcreatorsname
	 *            the new nihcreatorsname
	 */
	public void setNihcreatorsname(String nihcreatorsname) {
		this.nihcreatorsname = nihcreatorsname;
	}

	/**
	 * Sets the nihdeliveryaddress.
	 * 
	 * @param nihdeliveryaddress
	 *            the new nihdeliveryaddress
	 */
	public void setNihdeliveryaddress(String nihdeliveryaddress) {
		this.nihdeliveryaddress = nihdeliveryaddress;
	}

	/**
	 * Sets the nihdirentryeffectivedate.
	 * 
	 * @param nihdirentryeffectivedate
	 *            the new nihdirentryeffectivedate
	 */
	public void setNihdirentryeffectivedate(Date nihdirentryeffectivedate) {
		this.nihdirentryeffectivedate = nihdirentryeffectivedate;
	}

	/**
	 * Sets the nihdirentryexpirationdate.
	 * 
	 * @param nihdirentryexpirationdate
	 *            the new nihdirentryexpirationdate
	 */
	public void setNihdirentryexpirationdate(Date nihdirentryexpirationdate) {
		this.nihdirentryexpirationdate = nihdirentryexpirationdate;
	}

	/**
	 * Sets the nihdirentrynoprint.
	 * 
	 * @param nihdirentrynoprint
	 *            the new nihdirentrynoprint
	 */
	public void setNihdirentrynoprint(String nihdirentrynoprint) {
		this.nihdirentrynoprint = nihdirentrynoprint;
	}

	/**
	 * Sets the nihdirentryunlist.
	 * 
	 * @param nihdirentryunlist
	 *            the new nihdirentryunlist
	 */
	public void setNihdirentryunlist(String nihdirentryunlist) {
		this.nihdirentryunlist = nihdirentryunlist;
	}

	/**
	 * Sets the nihdupuid.
	 * 
	 * @param nihdupuid
	 *            the new nihdupuid
	 */
	public void setNihdupuid(String nihdupuid) {
		this.nihdupuid = nihdupuid;
	}

	/**
	 * Sets the nihhhsuniquemail.
	 * 
	 * @param nihhhsuniquemail
	 *            the new nihhhsuniquemail
	 */
	public void setNihhhsuniquemail(String nihhhsuniquemail) {
		this.nihhhsuniquemail = nihhhsuniquemail;
	}

	/**
	 * Sets the nihidbadgeexpdate.
	 * 
	 * @param nihidbadgeexpdate
	 *            the new nihidbadgeexpdate
	 */
	public void setNihidbadgeexpdate(Date nihidbadgeexpdate) {
		this.nihidbadgeexpdate = nihidbadgeexpdate;
	}

	/**
	 * Sets the nihidbadgeissuerea.
	 * 
	 * @param nihidbadgeissuerea
	 *            the new nihidbadgeissuerea
	 */
	public void setNihidbadgeissuerea(String nihidbadgeissuerea) {
		this.nihidbadgeissuerea = nihidbadgeissuerea;
	}

	/**
	 * Sets the nihidbadgeless6mos.
	 * 
	 * @param nihidbadgeless6mos
	 *            the new nihidbadgeless6mos
	 */
	public void setNihidbadgeless6mos(String nihidbadgeless6mos) {
		this.nihidbadgeless6mos = nihidbadgeless6mos;
	}

	/**
	 * Sets the nihidbadgereqdate.
	 * 
	 * @param nihidbadgereqdate
	 *            the new nihidbadgereqdate
	 */
	public void setNihidbadgereqdate(Date nihidbadgereqdate) {
		this.nihidbadgereqdate = nihidbadgereqdate;
	}

	/**
	 * Sets the nihidbadgereqexpdt.
	 * 
	 * @param nihidbadgereqexpdt
	 *            the new nihidbadgereqexpdt
	 */
	public void setNihidbadgereqexpdt(Date nihidbadgereqexpdt) {
		this.nihidbadgereqexpdt = nihidbadgereqexpdt;
	}

	/**
	 * Sets the nihidbadgereqtype.
	 * 
	 * @param nihidbadgereqtype
	 *            the new nihidbadgereqtype
	 */
	public void setNihidbadgereqtype(String nihidbadgereqtype) {
		this.nihidbadgereqtype = nihidbadgereqtype;
	}

	/**
	 * Sets the nihipd.
	 * 
	 * @param nihipd
	 *            the new nihipd
	 */
	public void setNihipd(String nihipd) {
		this.nihipd = nihipd;
	}

	/**
	 * Sets the nihlibraryauth.
	 * 
	 * @param nihlibraryauth
	 *            the new nihlibraryauth
	 */
	public void setNihlibraryauth(String nihlibraryauth) {
		this.nihlibraryauth = nihlibraryauth;
	}

	/**
	 * Sets the nihmailstop.
	 * 
	 * @param nihmailstop
	 *            the new nihmailstop
	 */
	public void setNihmailstop(String nihmailstop) {
		this.nihmailstop = nihmailstop;
	}

	/**
	 * Sets the nihmdslinktohelp.
	 * 
	 * @param nihmdslinktohelp
	 *            the new nihmdslinktohelp
	 */
	public void setNihmdslinktohelp(String nihmdslinktohelp) {
		this.nihmdslinktohelp = nihmdslinktohelp;
	}

	/**
	 * Sets the nihmdslinktoph.
	 * 
	 * @param nihmdslinktoph
	 *            the new nihmdslinktoph
	 */
	public void setNihmdslinktoph(String nihmdslinktoph) {
		this.nihmdslinktoph = nihmdslinktoph;
	}

	/**
	 * Sets the nihmixcasecommonsn.
	 * 
	 * @param nihmixcasecommonsn
	 *            the new nihmixcasecommonsn
	 */
	public void setNihmixcasecommonsn(String nihmixcasecommonsn) {
		this.nihmixcasecommonsn = nihmixcasecommonsn;
	}

	/**
	 * Sets the nihmixcasesn.
	 * 
	 * @param nihmixcasesn
	 *            the new nihmixcasesn
	 */
	public void setNihmixcasesn(String nihmixcasesn) {
		this.nihmixcasesn = nihmixcasesn;
	}

	/**
	 * Sets the nihmodifiersname.
	 * 
	 * @param nihmodifiersname
	 *            the new nihmodifiersname
	 */
	public void setNihmodifiersname(String nihmodifiersname) {
		this.nihmodifiersname = nihmodifiersname;
	}

	/**
	 * Sets the nihmodifytimestamp.
	 * 
	 * @param nihmodifytimestamp
	 *            the new nihmodifytimestamp
	 */
	public void setNihmodifytimestamp(Date nihmodifytimestamp) {
		this.nihmodifytimestamp = nihmodifytimestamp;
	}

	/**
	 * Sets the nihnomiddlename.
	 * 
	 * @param nihnomiddlename
	 *            the new nihnomiddlename
	 */
	public void setNihnomiddlename(String nihnomiddlename) {
		this.nihnomiddlename = nihnomiddlename;
	}

	/**
	 * Sets the nihorgacronym.
	 * 
	 * @param nihorgacronym
	 *            the new nihorgacronym
	 */
	public void setNihorgacronym(String nihorgacronym) {
		this.nihorgacronym = nihorgacronym;
	}

	/**
	 * Sets the nihorgname.
	 * 
	 * @param nihorgname
	 *            the new nihorgname
	 */
	public void setNihorgname(String nihorgname) {
		this.nihorgname = nihorgname;
	}

	/**
	 * Sets the nihouacronym.
	 * 
	 * @param nihouacronym
	 *            the new nihouacronym
	 */
	public void setNihouacronym(String nihouacronym) {
		this.nihouacronym = nihouacronym;
	}

	/**
	 * Sets the nihouname.
	 * 
	 * @param nihouname
	 *            the new nihouname
	 */
	public void setNihouname(String nihouname) {
		this.nihouname = nihouname;
	}

	/**
	 * Sets the nihphysicaladdress.
	 * 
	 * @param nihphysicaladdress
	 *            the new nihphysicaladdress
	 */
	public void setNihphysicaladdress(String nihphysicaladdress) {
		this.nihphysicaladdress = nihphysicaladdress;
	}

	/**
	 * Sets the nihphyspostalcode.
	 * 
	 * @param nihphyspostalcode
	 *            the new nihphyspostalcode
	 */
	public void setNihphyspostalcode(String nihphyspostalcode) {
		this.nihphyspostalcode = nihphyspostalcode;
	}

	/**
	 * Sets the nihpoc.
	 * 
	 * @param nihpoc
	 *            the new nihpoc
	 */
	public void setNihpoc(String nihpoc) {
		this.nihpoc = nihpoc;
	}

	/**
	 * Sets the nihprimarysmtp.
	 * 
	 * @param nihprimarysmtp
	 *            the new nihprimarysmtp
	 */
	public void setNihprimarysmtp(String nihprimarysmtp) {
		this.nihprimarysmtp = nihprimarysmtp;
	}

	/**
	 * Sets the nih sac.
	 * 
	 * @param nihSac
	 *            the new nih sac
	 */
	public void setNihSac(String nihSac) {
		this.nihSac = nihSac;
	}

	/**
	 * Sets the nihsite.
	 * 
	 * @param nihsite
	 *            the new nihsite
	 */
	public void setNihsite(String nihsite) {
		this.nihsite = nihsite;
	}

	/**
	 * Sets the nihssodomain.
	 * 
	 * @param nihssodomain
	 *            the new nihssodomain
	 */
	public void setNihssodomain(String nihssodomain) {
		this.nihssodomain = nihssodomain;
	}

	/**
	 * Sets the nihssousername.
	 * 
	 * @param nihssousername
	 *            the new nihssousername
	 */
	public void setNihssousername(String nihssousername) {
		this.nihssousername = nihssousername;
	}

	/**
	 * Sets the nihsuffixqualifier.
	 * 
	 * @param nihsuffixqualifier
	 *            the new nihsuffixqualifier
	 */
	public void setNihsuffixqualifier(String nihsuffixqualifier) {
		this.nihsuffixqualifier = nihsuffixqualifier;
	}

	/**
	 * Sets the nihsummerstatus.
	 * 
	 * @param nihsummerstatus
	 *            the new nihsummerstatus
	 */
	public void setNihsummerstatus(String nihsummerstatus) {
		this.nihsummerstatus = nihsummerstatus;
	}

	/**
	 * Sets the nihtty.
	 * 
	 * @param nihtty
	 *            the new nihtty
	 */
	public void setNihtty(String nihtty) {
		this.nihtty = nihtty;
	}

	/**
	 * Sets the nihuidquality.
	 * 
	 * @param nihuidquality
	 *            the new nihuidquality
	 */
	public void setNihuidquality(Boolean nihuidquality) {
		this.nihuidquality = nihuidquality;
	}

	/**
	 * Sets the nihuidvalidationts.
	 * 
	 * @param nihuidvalidationts
	 *            the new nihuidvalidationts
	 */
	public void setNihuidvalidationts(Date nihuidvalidationts) {
		this.nihuidvalidationts = nihuidvalidationts;
	}

	/**
	 * Sets the nihuidvalidator.
	 * 
	 * @param nihuidvalidator
	 *            the new nihuidvalidator
	 */
	public void setNihuidvalidator(String nihuidvalidator) {
		this.nihuidvalidator = nihuidvalidator;
	}

	/**
	 * Sets the nihuniquemail.
	 * 
	 * @param nihuniquemail
	 *            the new nihuniquemail
	 */
	public void setNihuniquemail(String nihuniquemail) {
		this.nihuniquemail = nihuniquemail;
	}

	/**
	 * Sets the org path.
	 * 
	 * @param orgPath
	 *            the new org path
	 */
	public void setOrgPath(String orgPath) {
		this.orgPath = orgPath;
	}

	/**
	 * Sets the org status.
	 * 
	 * @param orgStatus
	 *            the new org status
	 */
	public void setOrgStatus(String orgStatus) {
		this.orgStatus = orgStatus;
	}

	/**
	 * Sets the pagertelephonenum.
	 * 
	 * @param pagertelephonenum
	 *            the new pagertelephonenum
	 */
	public void setPagertelephonenum(String pagertelephonenum) {
		this.pagertelephonenum = pagertelephonenum;
	}

	/**
	 * Sets the personaltitle.
	 * 
	 * @param personaltitle
	 *            the new personaltitle
	 */
	public void setPersonaltitle(String personaltitle) {
		this.personaltitle = personaltitle;
	}

	/**
	 * Sets the phone.
	 * 
	 * @param phone
	 *            the new phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Sets the postaladdress.
	 * 
	 * @param postaladdress
	 *            the new postaladdress
	 */
	public void setPostaladdress(String postaladdress) {
		this.postaladdress = postaladdress;
	}

	/**
	 * Sets the postalcode.
	 * 
	 * @param postalcode
	 *            the new postalcode
	 */
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	/**
	 * Sets the preferred name.
	 * 
	 * @param preferredName
	 *            the new preferred name
	 */
	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	/**
	 * Sets the room.
	 * 
	 * @param room
	 *            the new room
	 */
	public void setRoom(String room) {
		this.room = room;
	}

	/**
	 * Sets the secretary.
	 * 
	 * @param secretary
	 *            the new secretary
	 */
	public void setSecretary(String secretary) {
		this.secretary = secretary;
	}

	/**
	 * Sets the st.
	 * 
	 * @param st
	 *            the new st
	 */
	public void setSt(String st) {
		this.st = st;
	}

	/**
	 * Sets the street.
	 * 
	 * @param street
	 *            the new street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "NedPerson [adUserId=" + adUserId + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}
