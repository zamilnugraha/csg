package csg.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import csg.dao.ProfileDao;
import csg.dto.ProfileDto;
import csg.entity.Profile;
import csg.service.ProfileSvc;

@Service("profileSvc")
@Transactional
public class ProfileSvcImpl implements ProfileSvc {

	@Autowired
	ProfileDao profileDao;

	@Override
	public void save(ProfileDto profileDto) {

		Profile profile = new Profile();

		profile.setId(profileDto.getId());
		profile.setKodeStore(profileDto.getKodeStore());
		profile.setNamaStore(profileDto.getNamaStore());
		profile.setLocation(profileDto.getLocation());
		profile.setItemPerTransaction(profileDto.getItemPerTransaction());
		profile.setStoreType(profileDto.getStoreType());
		profile.setNumberOfFloors(profileDto.getNumberOfFloors());
		profile.setSeatingCapacity(profileDto.getSeatingCapacity());
		profile.setEatIn(profileDto.getEatIn());
		profile.setTotalCrew(profileDto.getTotalCrew());
		profile.setMinimumCrew(profileDto.getMinimumCrew());
		profile.setCreatedDate(profileDto.getCreatedDate());

		profileDao.save(profile);

	}

	@Override
	public void update(ProfileDto profileDto, String id) {
		Profile profile = new Profile();

		profile.setId(id);
		profile.setKodeStore(profileDto.getKodeStore());
		profile.setNamaStore(profileDto.getNamaStore());
		profile.setLocation(profileDto.getLocation());
		profile.setItemPerTransaction(profileDto.getItemPerTransaction());
		profile.setStoreType(profileDto.getStoreType());
		profile.setNumberOfFloors(profileDto.getNumberOfFloors());
		profile.setSeatingCapacity(profileDto.getSeatingCapacity());
		profile.setEatIn(profileDto.getEatIn());
		profile.setTotalCrew(profileDto.getTotalCrew());
		profile.setMinimumCrew(profileDto.getMinimumCrew());

		profileDao.save(profile);

	}

	@Override
	public ProfileDto findOneProfileById(String id) {

		Profile profile = profileDao.findOneById(id);
		ProfileDto profileDto = new ProfileDto();

		if (profile != null) {
			profileDto.setId(profile.getId());
			profileDto.setKodeStore(profile.getKodeStore());
			profileDto.setNamaStore(profile.getNamaStore());
			profileDto.setLocation(profile.getLocation());
			profileDto.setItemPerTransaction(profile.getItemPerTransaction());
			profileDto.setStoreType(profile.getStoreType());
			profileDto.setNumberOfFloors(profile.getNumberOfFloors());
			profileDto.setSeatingCapacity(profile.getSeatingCapacity());
			profileDto.setEatIn(profile.getEatIn());
			profileDto.setTotalCrew(profile.getTotalCrew());
			profileDto.setMinimumCrew(profile.getMinimumCrew());
			profileDto.setCreatedDate(profile.getCreatedDate());
		}
		return profileDto;
	}

	@Override
	public List<ProfileDto> findAllProfile() {
		List<ProfileDto> profileDtos = new ArrayList<ProfileDto>();
		List<Profile> profiles = profileDao.findAllData();

		for (Profile profile : profiles) {
			ProfileDto profileDto = new ProfileDto();
			
			profileDto.setId(profile.getId());
			profileDto.setKodeStore(profile.getKodeStore());
			profileDto.setNamaStore(profile.getNamaStore());
			profileDto.setLocation(profile.getLocation());
			profileDto.setItemPerTransaction(profile.getItemPerTransaction());
			profileDto.setStoreType(profile.getStoreType());
			profileDto.setNumberOfFloors(profile.getNumberOfFloors());
			profileDto.setSeatingCapacity(profile.getSeatingCapacity());
			profileDto.setEatIn(profile.getEatIn());
			profileDto.setTotalCrew(profile.getTotalCrew());
			profileDto.setMinimumCrew(profile.getMinimumCrew());
			profileDto.setCreatedDate(profile.getCreatedDate());
			
			profileDtos.add(profileDto);
		}
		return profileDtos;
	}

	@Override
	public String deleteProfile(String id) {

		String resultDelete = "";
		try {
			resultDelete = String.valueOf(profileDao.deleteProfile(id));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Delete : " + e.getMessage());
		}
		return resultDelete;
	}

}
