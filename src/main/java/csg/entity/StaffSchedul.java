package csg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "M_STAFF_SCHEDUL")
@IdClass(StaffSchedulPK.class)
public class StaffSchedul {

	private String id;
	private String crewId;
	private String startDate;
	private String endDate;
	private String dateWork;

	@Id
	@Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "CREW_ID")
	public String getCrewId() {
		return crewId;
	}

	public void setCrewId(String crewId) {
		this.crewId = crewId;
	}

	@Column(name = "START_DATE")
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@Column(name = "END_DATE")
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDateWork() {
		return dateWork;
	}

	public void setDateWork(String dateWork) {
		this.dateWork = dateWork;
	}

}
