package csg.service;

import java.util.List;

import csg.dto.CleaningSchedulDto;

public interface CleaningSchedulSvc {

	public void save(CleaningSchedulDto cleaningSchedulDto);

	public void update(CleaningSchedulDto cleaningSchedulDto, String id);

	public CleaningSchedulDto findOneCleaningSchedulById(String id);

	public List<CleaningSchedulDto> findAllCleaningSchedul();

	public String deleteCleaning(String id);
}
