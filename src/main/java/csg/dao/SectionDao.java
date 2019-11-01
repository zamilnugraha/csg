package csg.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import csg.entity.Section;
import csg.entity.SectionPK;

public interface SectionDao extends JpaRepository<Section, SectionPK>{

	@Query(value = "SELECT * FROM M_SECTION", nativeQuery = true)
	public List<Section> findAllData();

	public Section findOneByKodeSection(String kodeSection);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM M_SECTION WHERE KODE_SECTION = ?",nativeQuery = true)
	public Integer deleteSection(String kodeSection);
}
