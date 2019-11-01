package csg.service;

import java.util.List;

import csg.dto.CleaningDto;

public interface CleaningSvc {

	public void save(CleaningDto cleaningDto);
	
	public void update(CleaningDto cleaningDto, String kodeClean);
	
	public CleaningDto findOneCleaningById(String kodeClean);
	
	public List<CleaningDto> findAllCleaning();
	
	public String deleteCleaning(String kodeClean);
}
