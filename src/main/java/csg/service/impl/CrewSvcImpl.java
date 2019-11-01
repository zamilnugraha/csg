package csg.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import csg.dao.CrewDao;
import csg.dto.CrewDto;
import csg.entity.Crew;
import csg.service.CrewSvc;

@Service("crewSvc")
@Transactional
public class CrewSvcImpl implements CrewSvc {

	@Autowired
	CrewDao crewDao;

	@Override
	public void save(CrewDto crewDto) {

		Crew crew = new Crew();

		crew.setNik(crewDto.getNik());
		crew.setKodeStore(crewDto.getKodeStore());
		crew.setNama(crewDto.getNama());
		crew.setJabatan(crewDto.getJabatan());
		crew.setStatus(crewDto.getStatus());

		crewDao.save(crew);
	}

	@Override
	public void update(CrewDto crewDto, String nik) {
		Crew crew = new Crew();

		crew.setNik(nik);
		crew.setKodeStore(crewDto.getKodeStore());
		crew.setNama(crewDto.getNama());
		crew.setJabatan(crewDto.getJabatan());
		crew.setStatus(crewDto.getStatus());

		crewDao.save(crew);

	}

	@Override
	public CrewDto findOneCleaningByNik(String nik) {

		Crew crew = crewDao.findOneByNik(nik);
		CrewDto crewDto = new CrewDto();

		if (crew != null) {
			crewDto.setNik(crew.getNik());
			crewDto.setKodeStore(crew.getKodeStore());
			crewDto.setNama(crew.getNama());
			crewDto.setJabatan(crew.getJabatan());
			crewDto.setStatus(crew.getStatus());
		}
		return crewDto;
	}

	@Override
	public List<CrewDto> findAllCrew() {
		List<CrewDto> crewDtos = new ArrayList<CrewDto>();
		List<Crew> crews = crewDao.findAllData();

		for (Crew crew : crews) {
			CrewDto crewDto = new CrewDto();

			crewDto.setNik(crew.getNik());
			crewDto.setKodeStore(crew.getKodeStore());
			crewDto.setNama(crew.getNama());
			crewDto.setJabatan(crew.getJabatan());
			crewDto.setStatus(crew.getStatus());

			crewDtos.add(crewDto);
		}
		return crewDtos;
	}

	@Override
	public String deleteCrew(String nik) {
		String resultDelete = "";
		try {
			resultDelete = String.valueOf(crewDao.deleteCrew(nik));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Delete : " + e.getMessage());
		}
		return resultDelete;
	}

}
