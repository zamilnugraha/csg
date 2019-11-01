package csg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "M_CREW")
@IdClass(CrewPK.class)
public class Crew {

	private String kodeStore;
	private String nik;
	private String nama;
	private String jabatan;
	private String status;

	@Column(name ="KODE_STORE")
	public String getKodeStore() {
		return kodeStore;
	}

	public void setKodeStore(String kodeStore) {
		this.kodeStore = kodeStore;
	}

	@Id
	@Column(name ="NIK")
	public String getNik() {
		return nik;
	}

	public void setNik(String nik) {
		this.nik = nik;
	}

	@Column(name ="NAMA")
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@Column(name ="JABATAN")
	public String getJabatan() {
		return jabatan;
	}

	public void setJabatan(String jabatan) {
		this.jabatan = jabatan;
	}

	@Column(name ="STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
