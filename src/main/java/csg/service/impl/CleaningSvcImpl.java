package csg.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import csg.dao.CleaningDao;
import csg.dto.CleaningDto;
import csg.entity.Cleaning;
import csg.service.CleaningSvc;

@Service("cleaningSvc")
@Transactional
public class CleaningSvcImpl implements CleaningSvc {

	@Autowired
	CleaningDao cleaningDao;

	@Override
	public void save(CleaningDto cleaningDto) {

		Cleaning cleaning = new Cleaning();

		cleaning.setKodeClean(cleaningDto.getKodeClean());
		cleaning.setPosisi(cleaningDto.getPosisi());
		cleaning.setCleaningLocation(cleaningDto.getCleaningLocation());
		cleaning.setCleaningType(cleaningDto.getCleaningType());

		cleaningDao.save(cleaning);
	}

	@Override
	public void update(CleaningDto cleaningDto, String kodeClean) {

		Cleaning cleaning = new Cleaning();

		cleaning.setKodeClean(kodeClean);
		cleaning.setPosisi(cleaningDto.getPosisi());
		cleaning.setCleaningLocation(cleaningDto.getCleaningLocation());
		cleaning.setCleaningType(cleaningDto.getCleaningType());

		cleaningDao.save(cleaning);
	}

	@Override
	public CleaningDto findOneCleaningById(String kodeClean) {

		Cleaning cleaning = cleaningDao.findOneByKodeClean(kodeClean);
		CleaningDto cleaningDto = new CleaningDto();

		if (cleaning != null) {
			cleaningDto.setKodeClean(cleaning.getKodeClean());
			cleaningDto.setPosisi(cleaning.getPosisi());
			cleaningDto.setCleaningLocation(cleaning.getCleaningLocation());
			cleaningDto.setCleaningType(cleaning.getCleaningType());

		}
		return cleaningDto;
	}

	@Override
	public List<CleaningDto> findAllCleaning() {
		List<CleaningDto> cleaningDtos = new ArrayList<>();
		List<Cleaning> cleanings = cleaningDao.findAll();

		for (Cleaning cleaning : cleanings) {
			CleaningDto cleaningDto = new CleaningDto();

			cleaningDto.setKodeClean(cleaning.getKodeClean());
			cleaningDto.setPosisi(cleaning.getPosisi());
			cleaningDto.setCleaningLocation(cleaning.getCleaningLocation());
			cleaningDto.setCleaningType(cleaning.getCleaningType());

			cleaningDtos.add(cleaningDto);
		}
		return cleaningDtos;
	}

	@Override
	public String deleteCleaning(String kodeClean) {

		String resultDelete = "";
		try {
			resultDelete = String.valueOf(cleaningDao.deleteCleaning(kodeClean));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Delete : " + e.getMessage());
		}
		return resultDelete;
	}

}
