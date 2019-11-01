package csg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import csg.dao.StaffSchedulDao;
import csg.dto.StaffSchedulDto;
import csg.entity.StaffSchedul;
import csg.service.StaffSchedulSvc;

@Service("staffSchedulSvc")
@Transactional
public class StaffSchedulSvcImpl implements StaffSchedulSvc {

	@Autowired
	StaffSchedulDao staffSchedulDao;

	@Override
	public void save(StaffSchedulDto staffSchedulDto) {
		StaffSchedul staffSchedul = new StaffSchedul();

		staffSchedul.setId(staffSchedulDto.getId());
		staffSchedul.setCrewId(staffSchedulDto.getCrewId());
		staffSchedul.setStartDate(staffSchedulDto.getStartDate());
		staffSchedul.setEndDate(staffSchedulDto.getEndDate());
		staffSchedul.setDateWork(staffSchedulDto.getDateWork());

		staffSchedulDao.save(staffSchedul);

	}

	@Override
	public void update(StaffSchedulDto staffSchedulDto, String id) {
		StaffSchedul staffSchedul = new StaffSchedul();

		staffSchedul.setId(id);
		staffSchedul.setCrewId(staffSchedulDto.getCrewId());
		staffSchedul.setStartDate(staffSchedulDto.getStartDate());
		staffSchedul.setEndDate(staffSchedulDto.getEndDate());
		staffSchedul.setDateWork(staffSchedulDto.getDateWork());

		staffSchedulDao.save(staffSchedul);

	}

	@Override
	public StaffSchedulDto findOneStaffSchedulById(String id) {
		StaffSchedul staffSchedul = staffSchedulDao.findOneById(id);
		StaffSchedulDto staffSchedulDto = new StaffSchedulDto();

		if (staffSchedul != null) {
			staffSchedulDto.setId(staffSchedul.getId());
			staffSchedulDto.setCrewId(staffSchedul.getCrewId());
			staffSchedulDto.setStartDate(staffSchedul.getStartDate());
			staffSchedulDto.setEndDate(staffSchedul.getEndDate());
			staffSchedulDto.setDateWork(staffSchedul.getDateWork());
		}

		return staffSchedulDto;
	}

	@Override
	public List<StaffSchedulDto> findAllStaffSchedul() {
		List<StaffSchedulDto> staffSchedulDtos = new ArrayList<StaffSchedulDto>();
		List<StaffSchedul> staffScheduls = staffSchedulDao.findAllData();

		for (StaffSchedul staffSchedul : staffScheduls) {
			StaffSchedulDto staffSchedulDto = new StaffSchedulDto();

			staffSchedulDto.setId(staffSchedul.getId());
			staffSchedulDto.setCrewId(staffSchedul.getCrewId());
			staffSchedulDto.setStartDate(staffSchedul.getStartDate());
			staffSchedulDto.setEndDate(staffSchedul.getEndDate());
			staffSchedulDto.setDateWork(staffSchedul.getDateWork());
			
			staffSchedulDtos.add(staffSchedulDto);

		}
		return staffSchedulDtos;
	}

	@Override
	public String deleteStaffSchedul(String id) {

		String resultDelete = "";
		try {
			resultDelete = String.valueOf(staffSchedulDao.deleteStaff(id));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Delete : " + e.getMessage());
		}
		return resultDelete;
	}

	@Override
	public List<Object[]> findAllDataJoin() {
		List listArray = new ArrayList<>();
		List<Object[]> staff = staffSchedulDao.findAllDataJoin();

		for (int j = 0; j < staff.size(); j++) {
			Object[] staffs = staff.get(j);
			HashMap map = new HashMap<>();

			map.put("days", staffs[0]);
			map.put("cleanLocation", staffs[1]);
			map.put("cleanType", staffs[2]);

			listArray.add(map);
		}
		return listArray;
	}

	@Override
	public List<Object[]> findAllDataJoinStaff() {
		List listArray = new ArrayList<>();
		List<Object[]> staff = staffSchedulDao.findAllDataJoinStaff();

		for (int j = 0; j < staff.size(); j++) {
			Object[] staffs = staff.get(j);
			HashMap map = new HashMap<>();

			map.put("nama", staffs[0]);
			map.put("crewId", staffs[1]);
			map.put("startDate", staffs[2]);
			map.put("endDate", staffs[3]);
			map.put("dateWork", staffs[4]);

			listArray.add(map);
		}
		return listArray;
	}

}
