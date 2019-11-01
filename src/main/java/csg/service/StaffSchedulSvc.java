package csg.service;

import java.util.List;

import csg.dto.StaffSchedulDto;

public interface StaffSchedulSvc {

	public void save(StaffSchedulDto staffSchedulDto);

	public void update(StaffSchedulDto staffSchedulDto, String id);

	public StaffSchedulDto findOneStaffSchedulById(String id);

	public List<StaffSchedulDto> findAllStaffSchedul();

	public String deleteStaffSchedul(String id);
	
	public List<Object[]> findAllDataJoin();
	
	public List<Object[]> findAllDataJoinStaff();
}
