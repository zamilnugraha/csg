package csg.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SecurityUtils {

	public static String createdDate() {
		SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdfFormat.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Bangkok"));
		String createDate = sdfFormat.format(new Date());
		return createDate;
	}
}
