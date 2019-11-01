package csg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "M_CLEANING")
@IdClass(CleaningPK.class)
public class Cleaning {

	private String kodeClean;
	private String posisi;
	private String cleaningLocation;
	private String cleaningType;
	

	@Id
	@Column(name = "KODE_CLEAN")
	public String getKodeClean() {
		return kodeClean;
	}

	public void setKodeClean(String kodeClean) {
		this.kodeClean = kodeClean;
	}

	@Column(name = "POSISI")
	public String getPosisi() {
		return posisi;
	}

	public void setPosisi(String posisi) {
		this.posisi = posisi;
	}

	@Column(name = "CLEANING_LOCATION")
	public String getCleaningLocation() {
		return cleaningLocation;
	}

	public void setCleaningLocation(String cleaningLocation) {
		this.cleaningLocation = cleaningLocation;
	}

	@Column(name = "CLEANING_TYPE")
	public String getCleaningType() {
		return cleaningType;
	}

	public void setCleaningType(String cleaningType) {
		this.cleaningType = cleaningType;
	}

}
