package csg.service;

import java.util.List;

import csg.dto.CrewDto;

public interface CrewSvc {

	public void save(CrewDto crewDto);

	public void update(CrewDto crewDto, String nik);

	public CrewDto findOneCleaningByNik(String nik);

	public List<CrewDto> findAllCrew();

	public String deleteCrew(String nik);
}
