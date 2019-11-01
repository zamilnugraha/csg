package csg.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import csg.entity.CleaningSchedul;
import csg.entity.CleaningSchedulPK;

public interface CleaningSchedulDao extends JpaRepository<CleaningSchedul, CleaningSchedulPK>{

	@Query(value = "SELECT * FROM M_CLEANING_SCHEDUL", nativeQuery = true)
	public List<CleaningSchedul> findAllData();

	public CleaningSchedul findOneById(String id);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM M_CLEANING_SCHEDUL WHERE ID = ?",nativeQuery = true)
	public Integer deleteCleaning(String id);
}
