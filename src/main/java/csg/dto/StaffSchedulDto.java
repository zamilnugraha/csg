package csg.dto;

public class StaffSchedulDto {

	private String id;
	private String crewId;
	private String startDate;
	private String endDate;
	private String dateWork;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCrewId() {
		return crewId;
	}

	public void setCrewId(String crewId) {
		this.crewId = crewId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

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
