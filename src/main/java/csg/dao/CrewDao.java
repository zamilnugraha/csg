package csg.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import csg.entity.Crew;
import csg.entity.CrewPK;

public interface CrewDao extends JpaRepository<Crew, CrewPK>{

	@Query(value = "SELECT * FROM M_CREW", nativeQuery = true)
	public List<Crew> findAllData();

	public Crew findOneByNik(String nik);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM M_CREW WHERE NIK = ?",nativeQuery = true)
	public Integer deleteCrew(String id);
}
