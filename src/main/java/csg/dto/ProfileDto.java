package csg.dto;

import java.util.Date;

public class ProfileDto {

	private String id;
	private String kodeStore;
	private String namaStore;
	private String location;
	private String itemPerTransaction;
	private String storeType;
	private String numberOfFloors;
	private String seatingCapacity;
	private String eatIn;
	private String totalCrew;
	private String minimumCrew;
	private Date createdDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKodeStore() {
		return kodeStore;
	}

	public void setKodeStore(String kodeStore) {
		this.kodeStore = kodeStore;
	}

	public String getNamaStore() {
		return namaStore;
	}

	public void setNamaStore(String namaStore) {
		this.namaStore = namaStore;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getItemPerTransaction() {
		return itemPerTransaction;
	}

	public void setItemPerTransaction(String itemPerTransaction) {
		this.itemPerTransaction = itemPerTransaction;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getNumberOfFloors() {
		return numberOfFloors;
	}

	public void setNumberOfFloors(String numberOfFloors) {
		this.numberOfFloors = numberOfFloors;
	}

	public String getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(String seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

	public String getEatIn() {
		return eatIn;
	}

	public void setEatIn(String eatIn) {
		this.eatIn = eatIn;
	}

	public String getTotalCrew() {
		return totalCrew;
	}

	public void setTotalCrew(String totalCrew) {
		this.totalCrew = totalCrew;
	}

	public String getMinimumCrew() {
		return minimumCrew;
	}

	public void setMinimumCrew(String minimumCrew) {
		this.minimumCrew = minimumCrew;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	
}
