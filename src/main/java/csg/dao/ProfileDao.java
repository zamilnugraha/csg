package csg.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import csg.entity.Profile;
import csg.entity.ProfilePK;

public interface ProfileDao extends JpaRepository<Profile, ProfilePK>{

	@Query(value = "SELECT * FROM M_PROFILE WHERE ID = ( SELECT MAX(ID) FROM M_PROFILE )", nativeQuery = true)
	public List<Profile> findAllData();

	public Profile findOneById(String id);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM M_PROFILE WHERE id = ?",nativeQuery = true)
	public Integer deleteProfile(String id);
}
