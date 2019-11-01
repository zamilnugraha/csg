package csg.dto;

public class CleaningDto {

	private String kodeClean;
	private String posisi;
	private String cleaningLocation;
	private String cleaningType;

	public String getKodeClean() {
		return kodeClean;
	}

	public void setKodeClean(String kodeClean) {
		this.kodeClean = kodeClean;
	}

	public String getPosisi() {
		return posisi;
	}

	public void setPosisi(String posisi) {
		this.posisi = posisi;
	}

	public String getCleaningLocation() {
		return cleaningLocation;
	}

	public void setCleaningLocation(String cleaningLocation) {
		this.cleaningLocation = cleaningLocation;
	}

	public String getCleaningType() {
		return cleaningType;
	}

	public void setCleaningType(String cleaningType) {
		this.cleaningType = cleaningType;
	}
}
