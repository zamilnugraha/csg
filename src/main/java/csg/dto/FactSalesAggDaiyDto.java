package csg.dto;

public class FactSalesAggDaiyDto {

	private String cDate;
	private String totalSales;
	private String nRows;
	private String outletCode;

	public String getcDate() {
		return cDate;
	}

	public void setcDate(String cDate) {
		this.cDate = cDate;
	}

	public String getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(String totalSales) {
		this.totalSales = totalSales;
	}

	public String getnRows() {
		return nRows;
	}

	public void setnRows(String nRows) {
		this.nRows = nRows;
	}

	public String getOutletCode() {
		return outletCode;
	}

	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}

}
