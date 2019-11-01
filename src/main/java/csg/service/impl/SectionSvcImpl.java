package csg.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import csg.dao.SectionDao;
import csg.dto.SectionDto;
import csg.entity.Section;
import csg.service.SectionSvc;

@Service("sectionSvc")
@Transactional
public class SectionSvcImpl implements SectionSvc {

	@Autowired
	SectionDao sectionDao;

	@Override
	public void save(SectionDto sectionDto) {
		Section section = new Section();

		section.setKodeSection(sectionDto.getKodeSection());
		section.setSectionName(sectionDto.getSectionName());

		sectionDao.save(section);

	}

	@Override
	public void update(SectionDto sectionDto, String kodeSection) {
		Section section = new Section();

		section.setKodeSection(kodeSection);
		section.setSectionName(sectionDto.getSectionName());

		sectionDao.save(section);

	}

	@Override
	public SectionDto findOneSectionByKodeSection(String kodeSection) {
		Section section = sectionDao.findOneByKodeSection(kodeSection);
		SectionDto sectionDto = new SectionDto();
		
		if(section != null) {
			sectionDto.setKodeSection(section.getKodeSection());
			sectionDto.setSectionName(section.getSectionName());
			
		}
		return sectionDto;
	}

	@Override
	public List<SectionDto> findAllSection() {
		List<SectionDto> sectionDtos = new ArrayList<SectionDto>();
		List<Section> sections = sectionDao.findAllData();
		
		for(Section section : sections) {
			SectionDto sectionDto = new SectionDto();
			
			sectionDto.setKodeSection(section.getKodeSection());
			sectionDto.setSectionName(section.getSectionName());
			
			sectionDtos.add(sectionDto);
		}
		return sectionDtos;
	}

	@Override
	public String deleteSection(String kodeSection) {

		String resultDelete = "";
		try {
			resultDelete = String.valueOf(sectionDao.deleteSection(kodeSection));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Delete : " + e.getMessage());
		}
		return resultDelete;
	}

}
