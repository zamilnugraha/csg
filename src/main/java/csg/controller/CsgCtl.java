package csg.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import csg.common.RestResponse;
import csg.common.SecurityUtils;
import csg.dto.CleaningDto;
import csg.dto.CleaningSchedulDto;
import csg.dto.CrewDto;
import csg.dto.FactSalesAggDaiyDto;
import csg.dto.ProfileDto;
import csg.dto.ProfileEatInDto;
import csg.dto.ProfileItemDto;
import csg.dto.ProfileTransDto;
import csg.dto.ResultByHourDto;
import csg.dto.SectionDto;
import csg.dto.StaffSchedulDto;
import csg.dto.TransByHoursDto;
import csg.service.CleaningSchedulSvc;
import csg.service.CleaningSvc;
import csg.service.CrewSvc;
import csg.service.ProfileSvc;
import csg.service.SectionSvc;
import csg.service.StaffSchedulSvc;

/**
 * @author Indocyber Global Teknologi/IGLO KFC Team ( Zamil Nugraha )
 */

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CsgCtl {

	@Autowired
	CleaningSvc cleaningSvc;

	@Autowired
	CleaningSchedulSvc cleaningSchedulSvc;

	@Autowired
	CrewSvc crewSvc;

	@Autowired
	ProfileSvc profileSvc;

	@Autowired
	SectionSvc sectionSvc;

	@Autowired
	StaffSchedulSvc staffSchedulSvc;

	static Logger logger = (Logger) LoggerFactory.getLogger(CsgCtl.class);

	@RequestMapping(value = "targetStore/{datePeriod}/{orderType}", method = RequestMethod.GET)
	public RestResponse getDataTargetStore(@PathVariable("datePeriod") String datePeriod,
			@PathVariable("orderType") String orderType, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();

		try {
			List<FactSalesAggDaiyDto> results = new ArrayList<>();

			Connection c = null;
			Statement stmt = null;
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://192.168.10.70:5432/postgres", "ffi", "qwerty");

			stmt = c.createStatement();
			String sql = "SELECT SUM (nrows) AS nrows, SUM (CAST (total_sales AS INTEGER)) AS total_sales,dd.cdate,dou.outlet_code\r\n"
					+ "FROM dwhp.fact_sales_agg_daily_01 fsad\r\n"
					+ "JOIN dwhp.dim_date dd ON dd.date_sk = fsad.date_sk\r\n"
					+ "JOIN dwhp.dim_outlet dou ON dou.outlet_sk = fsad.outlet_sk\r\n" + "WHERE dd.month_period = '"
					+ datePeriod + "'\r\n" + "AND dou.outlet_code = '0204'\r\n"
					+ "AND CASE WHEN fsad.order_type = 'HMD' THEN 'HMD'\r\n"
					+ "WHEN fsad.order_type = 'CSP' THEN 'CSP'\r\n" + "WHEN fsad.order_type = 'DRT' THEN 'DRT'\r\n"
					+ "ELSE 'ETA' END = '" + orderType + "'\r\n"
					+ "GROUP BY dd.month_period,dou.outlet_code,dd.cdate,fsad.date_sk\r\n" + "ORDER BY fsad.date_sk ASC";

			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				FactSalesAggDaiyDto sales = new FactSalesAggDaiyDto();
				sales.setcDate(rs.getString("cdate"));
				sales.setnRows(rs.getString("nrows"));
				sales.setOutletCode(rs.getString("outlet_code"));
				sales.setTotalSales(rs.getString("total_sales"));
				
				results.add(sales);
			}

			rs.close();
			stmt.close();
			c.close();

			restResponse.setDatas(results);
			restResponse.setStatus(100);
			restResponse.setMessage("Success");
		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "resultByHour/{datePeriod1}/{datePeriod2}", method = RequestMethod.GET)
	public RestResponse getDataByHours(@PathVariable("datePeriod1") String datePeriod1,
			@PathVariable("datePeriod2") String datePeriod2, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();

		try {

			List<ResultByHourDto> results = new ArrayList<>();
			Connection c = null;
			Statement stmt = null;
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://192.168.10.70:5432/postgres", "ffi", "qwerty");

			stmt = c.createStatement();
			String sql = "SELECT dou.outlet_code, fsah.date_sk, fsah.time_sk, SUM(fsah.nrows) as nrows,dd.cdate\r\n"
					+ "FROM dwhp.fact_sales_agg_hourly_01 fsah\r\n"
					+ "JOIN dwhp.dim_outlet dou ON fsah.outlet_sk = dou.outlet_sk\r\n"
					+ "JOIN dwhp.dim_date dd ON fsah.date_sk = dd.date_sk\r\n" + "WHERE dd.cdate BETWEEN '"
					+ datePeriod1 + "' AND '" + datePeriod2 + "' AND outlet_code = '0204'\r\n"
					+ "GROUP BY fsah.date_sk,fsah.time_sk,dou.outlet_code,dd.cdate\r\n"
					+ "ORDER BY dou.outlet_code,dd.cdate, fsah.time_sk ASC;";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				ResultByHourDto resultByHour = new ResultByHourDto();
				resultByHour.setDateSk(rs.getString("date_sk"));
				resultByHour.setTimeSk(rs.getString("time_sk"));
				resultByHour.setnRows(rs.getString("nrows"));
				resultByHour.setOutletCode(rs.getString("outlet_code"));
				resultByHour.setcDate(rs.getString("cdate"));

				results.add(resultByHour);
			}

			rs.close();
			stmt.close();
			c.close();

			restResponse.setDatas(results);
			restResponse.setStatus(100);
			restResponse.setMessage("Success");

		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "transByHour/{datePeriod}", method = RequestMethod.GET)
	public RestResponse getDataTransByHours(@PathVariable("datePeriod") String datePeriod, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();

		try {

			List<TransByHoursDto> results = new ArrayList<>();
			Connection c = null;
			Statement stmt = null;
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://192.168.10.70:5432/postgres", "ffi", "qwerty");

			stmt = c.createStatement();
			String sql = "SELECT fsad.time_sk,fsad.nrows,fsad.date_sk,dd.cdate,dot.outlet_code FROM dwhp.fact_sales_agg_hourly_01 fsad\r\n"
					+ "JOIN dwhp.dim_date dd ON dd.date_sk = fsad.date_sk\r\n"
					+ "JOIN dwhp.dim_outlet dot ON dot.outlet_sk = fsad.outlet_sk\r\n"
					+ "WHERE dd.cdate BETWEEN (date(' " + datePeriod + " ') - INTERVAL '3 week') AND date('"
					+ datePeriod + "') ORDER BY date_sk ASC";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				TransByHoursDto transByHoursDto = new TransByHoursDto();
				transByHoursDto.setDateSk(rs.getString("date_sk"));
				transByHoursDto.setTimeSk(rs.getString("time_sk"));
				transByHoursDto.setnRows(rs.getString("nrows"));
				transByHoursDto.setOutletCode(rs.getString("outlet_code"));
				transByHoursDto.setcDate(rs.getString("cdate"));

				results.add(transByHoursDto);
			}

			rs.close();
			stmt.close();
			c.close();

			restResponse.setDatas(results);
			restResponse.setStatus(100);
			restResponse.setMessage("Success");

		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	// ------------------------------------------------------START CLEANING
	// METHOD----------------------------------------------------------//
	@RequestMapping(value = "cleanAll", method = RequestMethod.GET)
	public RestResponse findAllCleaning(HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			List<CleaningDto> cleaningDto = cleaningSvc.findAllCleaning();
			if (cleaningDto.size() > 0) {
				restResponse.setDatas(cleaningDto);
				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Data Not Found");
			}
		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "cleanById/{kodeClean}", method = RequestMethod.GET)
	public RestResponse findOneById(@PathVariable("kodeClean") String kodeClean, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {
			CleaningDto cleaningDto = (CleaningDto) cleaningSvc.findOneCleaningById(kodeClean);

			if (cleaningDto != null) {
				restResponse.setStatus(100);
				restResponse.setMessage("Success");
				restResponse.setDatas(cleaningDto);
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Data Not Found");
			}
		} catch (Exception ex) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "cleanSave", method = RequestMethod.POST)
	public RestResponse saveCleaning(@RequestBody CleaningDto cleaningDto, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			cleaningSvc.save(cleaningDto);
			restResponse.setStatus(100);
			restResponse.setMessage("Success");

		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "cleanUpdate/{kodeClean}", method = RequestMethod.POST)
	public RestResponse updateCleaning(@RequestBody CleaningDto cleaningDto,
			@PathVariable("kodeClean") String kodeClean, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {
			CleaningDto cleaningDtoOld = cleaningSvc.findOneCleaningById(kodeClean);

			if (cleaningDtoOld.getKodeClean() != null) {

				cleaningDto.setKodeClean(kodeClean);
				cleaningSvc.update(cleaningDto, kodeClean);

				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Failed Update");
			}

		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "cleanDelete/{kodeClean}", method = RequestMethod.GET)
	public RestResponse deleteCleaning(@PathVariable("kodeClean") String kodeClean, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			String cleaningDtoOld = cleaningSvc.deleteCleaning(kodeClean);

			if (cleaningDtoOld.equals("1")) {

				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Failed Delete");
			}

		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	// -------------------------------------------------START CLEANING SCHEDUL
	// METHOD-----------------------------------------------------//
	@RequestMapping(value = "cleanSchedulAll", method = RequestMethod.GET)
	public RestResponse findAllCleanSchedul(HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			List<CleaningSchedulDto> cleaningSchedulDto = cleaningSchedulSvc.findAllCleaningSchedul();
			if (cleaningSchedulDto.size() > 0) {
				restResponse.setDatas(cleaningSchedulDto);
				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Data Not Found");
			}
		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "cleanSchedulById/{id}", method = RequestMethod.GET)
	public RestResponse findOneCleanSchedulById(@PathVariable("id") String id, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {
			CleaningSchedulDto cleaningSchedulDto = (CleaningSchedulDto) cleaningSchedulSvc
					.findOneCleaningSchedulById(id);

			if (cleaningSchedulDto != null) {
				restResponse.setStatus(100);
				restResponse.setMessage("Success");
				restResponse.setDatas(cleaningSchedulDto);
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Data Not Found");
			}
		} catch (Exception ex) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "cleanSchedulSave", method = RequestMethod.POST)
	public RestResponse saveCleanSchedul(@RequestBody CleaningSchedulDto cleaningSchedulDto,
			HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			cleaningSchedulSvc.save(cleaningSchedulDto);
			restResponse.setStatus(100);
			restResponse.setMessage("Success");

		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "cleanSchedulUpdate/{id}", method = RequestMethod.POST)
	public RestResponse updateCleanSchedul(@RequestBody CleaningSchedulDto cleaningSchedulDto,
			@PathVariable("id") String id, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {
			CleaningSchedulDto cleaningSchedulDtoOld = cleaningSchedulSvc.findOneCleaningSchedulById(id);

			if (cleaningSchedulDtoOld.getId() != null) {

				cleaningSchedulDto.setId(id);
				cleaningSchedulSvc.update(cleaningSchedulDto, id);

				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Failed Update");
			}

		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "cleanSchedulDelete/{id}", method = RequestMethod.GET)
	public RestResponse deleteCleanSchedul(@PathVariable("id") String id, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			String cleaningSchedulDtoOld = cleaningSchedulSvc.deleteCleaning(id);

			if (cleaningSchedulDtoOld.equals("1")) {

				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Failed Delete");
			}

		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	// -----------------------------------------------------START CREW
	// METHOD---------------------------------------------------------//
	@RequestMapping(value = "crewAll", method = RequestMethod.GET)
	public RestResponse findAllCrew(HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			List<CrewDto> CrewDto = crewSvc.findAllCrew();
			if (CrewDto.size() > 0) {
				restResponse.setDatas(CrewDto);
				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Data Not Found");
			}
		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "crewById/{nik}", method = RequestMethod.GET)
	public RestResponse findOneCrewById(@PathVariable("nik") String nik, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {
			CrewDto crewDto = (CrewDto) crewSvc.findOneCleaningByNik(nik);

			if (crewDto != null) {
				restResponse.setDatas(crewDto);
				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Data Not Found");
			}
		} catch (Exception ex) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "crewSave", method = RequestMethod.POST)
	public RestResponse saveCrew(@RequestBody CrewDto crewDto, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			crewSvc.save(crewDto);
			restResponse.setStatus(100);
			restResponse.setMessage("Success");

		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "crewUpdate/{nik}", method = RequestMethod.POST)
	public RestResponse updateCrew(@RequestBody CrewDto crewDto, @PathVariable("nik") String nik,
			HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {
			CrewDto crewDtold = crewSvc.findOneCleaningByNik(nik);

			if (crewDtold.getNik() != null) {

				crewDto.setNik(nik);
				crewSvc.update(crewDto, nik);

				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Failed Update");
			}

		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "crewDelete/{nik}", method = RequestMethod.GET)
	public RestResponse deleteCrew(@PathVariable("nik") String nik, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			String crewDtoOld = crewSvc.deleteCrew(nik);

			if (crewDtoOld.equals("1")) {

				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Failed Delete");
			}

		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	// -----------------------------------------------------START PROFILE
	// METHOD---------------------------------------------------------//
	@RequestMapping(value = "profileAll", method = RequestMethod.GET)
	public RestResponse findAllProfile(HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			List<ProfileDto> profileDtos = profileSvc.findAllProfile();

			List<ProfileTransDto> resultsTrans = new ArrayList<>();
			List<ProfileItemDto> resultsItem = new ArrayList<>();
			List<ProfileEatInDto> resultsEatIn = new ArrayList<>();

			Connection c = null;
			Statement stmt = null;
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://192.168.10.70:5432/postgres", "ffi", "qwerty");

			stmt = c.createStatement();
			String sql = "SELECT sum(fsad.nrows) as totalTrans,dot.outlet_code,dot.outlet_name FROM dwhp.fact_sales_agg_daily_01 fsad \r\n"
					+ "JOIN dwhp.dim_date dd ON dd.date_sk = fsad.date_sk \r\n"
					+ "JOIN dwhp.dim_outlet dot ON dot.outlet_sk = fsad.outlet_sk \r\n"
					+ "WHERE dd.cdate BETWEEN (DATE (current_date) - INTERVAL '1 week') AND DATE (current_date)\r\n"
					+ "AND outlet_code = '0204' GROUP BY outlet_code, outlet_name;";

			String sql2 = "SELECT CAST (sum(fsad.total_item) AS INTEGER) as totalItem,dot.outlet_code,dot.outlet_name FROM dwhp.fact_sales_agg_daily_01 fsad \r\n"
					+ "JOIN dwhp.dim_date dd ON dd.date_sk = fsad.date_sk \r\n"
					+ "JOIN dwhp.dim_outlet dot ON dot.outlet_sk = fsad.outlet_sk \r\n"
					+ "WHERE dd.cdate BETWEEN (DATE (current_date) - INTERVAL '1 week') AND DATE (current_date)\r\n"
					+ "AND outlet_code = '0204' GROUP BY outlet_code, outlet_name;";

			String sql3 = "SELECT sum(fsad.nrows) as totalEatIn,dot.outlet_code,dot.outlet_name FROM dwhp.fact_sales_agg_daily_01 fsad \r\n"
					+ "JOIN dwhp.dim_date dd ON dd.date_sk = fsad.date_sk \r\n"
					+ "JOIN dwhp.dim_outlet dot ON dot.outlet_sk = fsad.outlet_sk \r\n"
					+ "WHERE dd.cdate BETWEEN (DATE (current_date) - INTERVAL '1 week') AND DATE (current_date)\r\n"
					+ "AND outlet_code = '0204' AND trans_type='EI' GROUP BY outlet_code, outlet_name;";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ProfileTransDto dataTrans = new ProfileTransDto();
				dataTrans.setTotalTrans(rs.getString("totalTrans"));
				dataTrans.setOutletCode(rs.getString("outlet_code"));
				dataTrans.setOutletName(rs.getString("outlet_name"));

				resultsTrans.add(dataTrans);
			}

			ResultSet rs2 = stmt.executeQuery(sql2);
			while (rs2.next()) {
				ProfileItemDto dataItem = new ProfileItemDto();
				dataItem.setTotalItem(rs2.getString("totalItem"));
				dataItem.setOutletCode(rs2.getString("outlet_code"));
				dataItem.setOutletName(rs2.getString("outlet_name"));

				resultsItem.add(dataItem);
			}

			ResultSet rs3 = stmt.executeQuery(sql3);
			while (rs3.next()) {
				ProfileEatInDto dataEatIn = new ProfileEatInDto();
				dataEatIn.setTotalEatIn(rs3.getString("totalEatIn"));
				dataEatIn.setOutletCode(rs3.getString("outlet_code"));
				dataEatIn.setOutletName(rs3.getString("outlet_name"));

				resultsEatIn.add(dataEatIn);
			}

			rs.close();
			rs2.close();
			rs3.close();
			stmt.close();
			c.close();

			HashMap<Object, Object> mapTemp = new HashMap<>();
			mapTemp.put("trans", resultsTrans);
			mapTemp.put("item", resultsItem);
			mapTemp.put("eatIn", resultsEatIn);
			mapTemp.put("content", profileDtos);

			if (profileDtos.size() > 0) {
				restResponse.setDatas(mapTemp);
				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Data Not Found");
			}
		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "profileSave", method = RequestMethod.POST)
	public RestResponse saveProfile(@RequestBody ProfileDto profileDto, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

			profileDto.setCreatedDate(formatter.parse(SecurityUtils.createdDate()));
			profileSvc.save(profileDto);
			restResponse.setStatus(100);
			restResponse.setMessage("Success");

		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	// -----------------------------------------------------START SECTION
	// METHOD---------------------------------------------------------//
	@RequestMapping(value = "sectionAll", method = RequestMethod.GET)
	public RestResponse findAllSection(HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			List<SectionDto> sectionDtos = sectionSvc.findAllSection();
			if (sectionDtos.size() > 0) {
				restResponse.setDatas(sectionDtos);
				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Data Not Found");
			}
		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "sectionById/{kodeSection}", method = RequestMethod.GET)
	public RestResponse findOneSectionById(@PathVariable("kodeSection") String kodeSection,
			HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {
			SectionDto sectionDto = (SectionDto) sectionSvc.findOneSectionByKodeSection(kodeSection);

			if (sectionDto != null) {
				restResponse.setDatas(sectionDto);
				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Data Not Found");
			}
		} catch (Exception ex) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "sectionSave", method = RequestMethod.POST)
	public RestResponse saveSection(@RequestBody SectionDto sectionDto, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			sectionSvc.save(sectionDto);
			restResponse.setStatus(100);
			restResponse.setMessage("Success");

		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "sectionUpdate/{kodeSection}", method = RequestMethod.POST)
	public RestResponse updateSection(@RequestBody SectionDto sectionDto,
			@PathVariable("kodeSection") String kodeSection, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {
			SectionDto sectionDtoold = sectionSvc.findOneSectionByKodeSection(kodeSection);

			if (sectionDtoold.getKodeSection() != null) {

				sectionDto.setKodeSection(kodeSection);
				sectionSvc.update(sectionDto, kodeSection);

				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Failed Update");
			}

		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "sectionDelete/{kodeSection}", method = RequestMethod.GET)
	public RestResponse sectionProfile(@PathVariable("kodeSection") String kodeSection, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			String sectionDto = sectionSvc.deleteSection(kodeSection);

			if (sectionDto.equals("1")) {

				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Failed Delete");
			}

		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	// -----------------------------------------------------START STAFF
	// METHOD---------------------------------------------------------//
	@RequestMapping(value = "staffAll", method = RequestMethod.GET)
	public RestResponse findAllStaffSchedul(HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			List<StaffSchedulDto> staffSchedulDtos = staffSchedulSvc.findAllStaffSchedul();
			if (staffSchedulDtos.size() > 0) {
				restResponse.setDatas(staffSchedulDtos);
				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Data Not Found");
			}
		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "staffById/{id}", method = RequestMethod.GET)
	public RestResponse findOneStaffSchedulById(@PathVariable("id") String id, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {
			StaffSchedulDto staffSchedulDto = (StaffSchedulDto) staffSchedulSvc.findOneStaffSchedulById(id);

			if (staffSchedulDto != null) {
				restResponse.setDatas(staffSchedulDto);
				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Data Not Found");
			}
		} catch (Exception ex) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "staffSave", method = RequestMethod.POST)
	public RestResponse saveStaffSchedul(@RequestBody StaffSchedulDto staffSchedulDto, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			staffSchedulSvc.save(staffSchedulDto);
			restResponse.setStatus(100);
			restResponse.setMessage("Success");

		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "staffUpdate/{id}", method = RequestMethod.POST)
	public RestResponse updateStaffSchedul(@RequestBody StaffSchedulDto staffSchedulDto, @PathVariable("id") String id,
			HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {
			StaffSchedulDto staffSchedulDtoold = staffSchedulSvc.findOneStaffSchedulById(id);

			if (staffSchedulDtoold.getId() != null) {

				staffSchedulDto.setId(id);
				staffSchedulSvc.update(staffSchedulDto, id);

				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Failed Update");
			}

		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "staffDelete/{id}", method = RequestMethod.GET)
	public RestResponse StaffSchedulDelete(@PathVariable("id") String id, HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			String staffSchedulDto = staffSchedulSvc.deleteStaffSchedul(id);

			if (staffSchedulDto.equals("1")) {

				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Failed Delete");
			}

		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

	@RequestMapping(value = "staff", method = RequestMethod.GET)
	public RestResponse findAllStaffSchedulJoin(HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			List<Object[]> staffObject = (List<Object[]>) staffSchedulSvc.findAllDataJoin();

			if (staffObject.size() > 0) {
				restResponse.setDatas(staffObject);
				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Data Not Found");
			}
		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}
	
	@RequestMapping(value = "detailStaff", method = RequestMethod.GET)
	public RestResponse findAllStaffSchedulJoinStaff(HttpServletRequest request) {
		RestResponse restResponse = new RestResponse();
		try {

			List<Object[]> staffObject = (List<Object[]>) staffSchedulSvc.findAllDataJoinStaff();

			if (staffObject.size() > 0) {
				restResponse.setDatas(staffObject);
				restResponse.setStatus(100);
				restResponse.setMessage("Success");
			} else {
				restResponse.setStatus(10);
				restResponse.setMessage("Data Not Found");
			}
		} catch (Exception e) {
			restResponse.setStatus(0);
			restResponse.setMessage("Process Failed");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
		return restResponse;
	}

}
