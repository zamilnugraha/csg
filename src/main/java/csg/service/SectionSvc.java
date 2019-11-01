package csg.service;

import java.util.List;

import csg.dto.SectionDto;

public interface SectionSvc {

	public void save(SectionDto sectionDto);

	public void update(SectionDto sectionDto, String kodeSection);

	public SectionDto findOneSectionByKodeSection(String kodeSection);

	public List<SectionDto> findAllSection();

	public String deleteSection(String kodeSection);
}
