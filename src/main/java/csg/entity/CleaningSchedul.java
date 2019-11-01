package csg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "M_CLEANING_SCHEDUL")
@IdClass(CleaningSchedulPK.class)
public class CleaningSchedul {

	private String id;
	private String days;
	private String kodeClean;

	@Id
	@Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "DAYS")
	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	@Column(name = "KODE_CLEAN")
	public String getKodeClean() {
		return kodeClean;
	}

	public void setKodeClean(String kodeClean) {
		this.kodeClean = kodeClean;
	}

}
