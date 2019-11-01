package csg.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "M_TARGET_STORE")
@IdClass(TargetStorePK.class)
public class TargetStore {

	private String id;
	private Date datePeriod;
	private String salesLastYear;
	private String transactionLastYear;
	private String targetSales;
	private String targetTransaction;
	private String ticketAverage;

	@Id
	@Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "DATE_PERIOD")
	public Date getDatePeriod() {
		return datePeriod;
	}

	public void setDatePeriod(Date datePeriod) {
		this.datePeriod = datePeriod;
	}

	@Column(name = "SALES_LAST_YEAR")
	public String getSalesLastYear() {
		return salesLastYear;
	}

	public void setSalesLastYear(String salesLastYear) {
		this.salesLastYear = salesLastYear;
	}

	@Column(name = "TRANSACTION_LAST_YEAR")
	public String getTransactionLastYear() {
		return transactionLastYear;
	}

	public void setTransactionLastYear(String transactionLastYear) {
		this.transactionLastYear = transactionLastYear;
	}

	@Column(name = "TARGER_SALES")
	public String getTargetSales() {
		return targetSales;
	}

	public void setTargetSales(String targetSales) {
		this.targetSales = targetSales;
	}

	@Column(name = "TARGET_TRANSACTION")
	public String getTargetTransaction() {
		return targetTransaction;
	}

	public void setTargetTransaction(String targetTransaction) {
		this.targetTransaction = targetTransaction;
	}

	@Column(name = "TICKET_AVERAGE")
	public String getTicketAverage() {
		return ticketAverage;
	}

	public void setTicketAverage(String ticketAverage) {
		this.ticketAverage = ticketAverage;
	}

}
