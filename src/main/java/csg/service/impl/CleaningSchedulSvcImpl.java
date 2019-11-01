package csg.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import csg.dao.CleaningSchedulDao;
import csg.dto.CleaningSchedulDto;
import csg.entity.CleaningSchedul;
import csg.service.CleaningSchedulSvc;

@Service("cleaningSchedulSvc")
@Transactional
public class CleaningSchedulSvcImpl implements CleaningSchedulSvc {

	@Autowired
	CleaningSchedulDao cleaningSchedulDao;

	@Override
	public void save(CleaningSchedulDto cleaningSchedulDto) {

		CleaningSchedul cleaningSchedul = new CleaningSchedul();
		cleaningSchedul.setId(cleaningSchedulDto.getId());
		cleaningSchedul.setDays(cleaningSchedulDto.getDays());
		cleaningSchedul.setKodeClean(cleaningSchedulDto.getKodeClean());

		cleaningSchedulDao.save(cleaningSchedul);

	}

	@Override
	public void update(CleaningSchedulDto cleaningSchedulDto, String id) {
		CleaningSchedul cleaningSchedul = new CleaningSchedul();
		cleaningSchedul.setId(id);
		cleaningSchedul.setDays(cleaningSchedulDto.getDays());
		cleaningSchedul.setKodeClean(cleaningSchedulDto.getKodeClean());
		
		cleaningSchedulDao.save(cleaningSchedul);

	}

	@Override
	public CleaningSchedulDto findOneCleaningSchedulById(String id) {
		CleaningSchedul cleaningSchedul = cleaningSchedulDao.findOneById(id);
		CleaningSchedulDto cleaningSchedulDto = new CleaningSchedulDto();

		if (cleaningSchedul != null) {
			cleaningSchedulDto.setId(cleaningSchedul.getId());
			cleaningSchedulDto.setDays(cleaningSchedul.getDays());
			cleaningSchedulDto.setKodeClean(cleaningSchedul.getKodeClean());
		}
		return cleaningSchedulDto;
	}

	@Override
	public List<CleaningSchedulDto> findAllCleaningSchedul() {
		List<CleaningSchedulDto> cleaningSchedulDtos = new ArrayList<CleaningSchedulDto>();
		List<CleaningSchedul> cleaningScheduls = cleaningSchedulDao.findAllData();

		for (CleaningSchedul cleaningSchedul : cleaningScheduls) {
			CleaningSchedulDto cleaningSchedulDto = new CleaningSchedulDto();

			cleaningSchedulDto.setId(cleaningSchedul.getId());
			cleaningSchedulDto.setDays(cleaningSchedul.getDays());
			cleaningSchedulDto.setKodeClean(cleaningSchedul.getKodeClean());
			
			cleaningSchedulDtos.add(cleaningSchedulDto);
		}
		return cleaningSchedulDtos;
	}

	@Override
	public String deleteCleaning(String id) {
		String resultDelete = "";
		try {
			resultDelete = String.valueOf(cleaningSchedulDao.deleteCleaning(id));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Delete : " + e.getMessage());
		}
		return resultDelete;
	}

}
