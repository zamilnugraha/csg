package csg.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "M_PROFILE")
@IdClass(ProfilePK.class)
public class Profile {

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

	@Id
	@Column(name ="ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name ="KODE_STORE")
	public String getKodeStore() {
		return kodeStore;
	}

	public void setKodeStore(String kodeStore) {
		this.kodeStore = kodeStore;
	}

	@Column(name ="NAMA_STORE")
	public String getNamaStore() {
		return namaStore;
	}

	public void setNamaStore(String namaStore) {
		this.namaStore = namaStore;
	}

	@Column(name ="LOCATION")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name ="ITEM_PER_TRANSACTION")
	public String getItemPerTransaction() {
		return itemPerTransaction;
	}

	public void setItemPerTransaction(String itemPerTransaction) {
		this.itemPerTransaction = itemPerTransaction;
	}

	@Column(name ="STORE_TYPE")
	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	@Column(name ="NUMBER_OF_FLOORS")
	public String getNumberOfFloors() {
		return numberOfFloors;
	}

	public void setNumberOfFloors(String numberOfFloors) {
		this.numberOfFloors = numberOfFloors;
	}

	@Column(name ="SEATING_CAPACITY")
	public String getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(String seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

	@Column(name ="EAT_IN")
	public String getEatIn() {
		return eatIn;
	}

	public void setEatIn(String eatIn) {
		this.eatIn = eatIn;
	}

	@Column(name ="TOTAL_CREW")
	public String getTotalCrew() {
		return totalCrew;
	}

	public void setTotalCrew(String totalCrew) {
		this.totalCrew = totalCrew;
	}

	@Column(name ="MINIMUM_CREW")
	public String getMinimumCrew() {
		return minimumCrew;
	}

	public void setMinimumCrew(String minimumCrew) {
		this.minimumCrew = minimumCrew;
	}

	@Column(name ="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
