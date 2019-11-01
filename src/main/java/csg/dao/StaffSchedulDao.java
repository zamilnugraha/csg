package csg.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import csg.entity.StaffSchedul;
import csg.entity.StaffSchedulPK;

public interface StaffSchedulDao extends JpaRepository<StaffSchedul, StaffSchedulPK> {

	@Query(value = "SELECT * FROM M_STAFF_SCHEDUL", nativeQuery = true)
	public List<StaffSchedul> findAllData();

	public StaffSchedul findOneById(String id);

	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM M_STAFF_SCHEDUL WHERE ID = ?", nativeQuery = true)
	public Integer deleteStaff(String id);

	@Modifying(clearAutomatically = true)
	@Query(value = "SELECT M_CLEANING_SCHEDUL.DAYS, M_CLEANING.CLEANING_LOCATION, M_CLEANING.CLEANING_TYPE FROM M_CLEANING_SCHEDUL\r\n"
			+ "INNER JOIN M_CLEANING ON M_CLEANING.KODE_CLEAN = M_CLEANING_SCHEDUL.KODE_CLEAN", nativeQuery = true)
	public List<Object[]> findAllDataJoin();

	@Modifying(clearAutomatically = true)
	@Query(value = "SELECT M_CREW.NAMA,mst.CREW_ID,mst.START_DATE,mst.END_DATE,mst.DATE_WORK FROM M_STAFF_SCHEDUL mst\r\n"
			+ "INNER JOIN M_CREW ON M_CREW.NIK = mst.CREW_ID", nativeQuery = true)
	public List<Object[]> findAllDataJoinStaff();
}
