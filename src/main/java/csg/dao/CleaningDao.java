package csg.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import csg.entity.Cleaning;
import csg.entity.CleaningPK;

@Repository
public interface CleaningDao extends JpaRepository<Cleaning, CleaningPK> {

	@Query(value = "SELECT * FROM M_CLEANING", nativeQuery = true)
	public List<Cleaning> findAllCleaning();

	public Cleaning findOneByKodeClean(String kodeClean);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM M_CLEANING WHERE KODE_CLEAN = ?",nativeQuery = true)
	public Integer deleteCleaning(String kodeClean);

}
