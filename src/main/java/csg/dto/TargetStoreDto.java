package csg.dto;

import java.util.Date;

public class TargetStoreDto {

	private String id;
	private Date datePeriod;
	private String salesLastYear;
	private String transactionLastYear;
	private String targetSales;
	private String targetTransaction;
	private String ticketAverage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDatePeriod() {
		return datePeriod;
	}

	public void setDatePeriod(Date datePeriod) {
		this.datePeriod = datePeriod;
	}

	public String getSalesLastYear() {
		return salesLastYear;
	}

	public void setSalesLastYear(String salesLastYear) {
		this.salesLastYear = salesLastYear;
	}

	public String getTransactionLastYear() {
		return transactionLastYear;
	}

	public void setTransactionLastYear(String transactionLastYear) {
		this.transactionLastYear = transactionLastYear;
	}

	public String getTargetSales() {
		return targetSales;
	}

	public void setTargetSales(String targetSales) {
		this.targetSales = targetSales;
	}

	public String getTargetTransaction() {
		return targetTransaction;
	}

	public void setTargetTransaction(String targetTransaction) {
		this.targetTransaction = targetTransaction;
	}

	public String getTicketAverage() {
		return ticketAverage;
	}

	public void setTicketAverage(String ticketAverage) {
		this.ticketAverage = ticketAverage;
	}

}
