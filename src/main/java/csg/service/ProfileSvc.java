package csg.service;

import java.util.List;

import csg.dto.ProfileDto;

public interface ProfileSvc {

	public void save(ProfileDto profileDto);

	public void update(ProfileDto profileDto, String id);

	public ProfileDto findOneProfileById(String id);

	public List<ProfileDto> findAllProfile();

	public String deleteProfile(String id);
}
