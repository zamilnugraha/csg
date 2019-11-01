package csg.dto;

public class ResultByHourDto {

	private String dateSk;
	private String timeSk;
	private String outletCode;
	private String nRows;
	private String cDate;

	public String getDateSk() {
		return dateSk;
	}

	public void setDateSk(String dateSk) {
		this.dateSk = dateSk;
	}

	public String getTimeSk() {
		return timeSk;
	}

	public void setTimeSk(String timeSk) {
		this.timeSk = timeSk;
	}

	public String getOutletCode() {
		return outletCode;
	}

	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}

	public String getnRows() {
		return nRows;
	}

	public void setnRows(String nRows) {
		this.nRows = nRows;
	}

	public String getcDate() {
		return cDate;
	}

	public void setcDate(String cDate) {
		this.cDate = cDate;
	}

}
