package csg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "M_SECTION")
@IdClass(SectionPK.class)
public class Section {

	private String kodeSection;
	private String sectionName;
	
	@Id
	@Column(name ="KODE_SECTION")
	public String getKodeSection() {
		return kodeSection;
	}

	public void setKodeSection(String kodeSection) {
		this.kodeSection = kodeSection;
	}

	@Column(name ="SECTION_NAME")
	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

}
