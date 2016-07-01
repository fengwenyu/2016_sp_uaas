package com.shangpin.uaas.sort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.shangpin.uaas.api.admin.user.UserDTO;

/**
 * Created by Percy on 14-7-24.
 */
public class UserComparator implements Comparator<UserDTO> {

	@Override
	public int compare(UserDTO o1, UserDTO o2) {
		SimpleDateFormat sdfyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sd1 = o1.getCreatedTime();
		Date d1 = new Date();
		if (sd1.length() > 10) {
			try {
				d1 = sdfHHmmss.parse(sd1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				d1 = sdfyyyyMMdd.parse(sd1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String sd2 = o2.getCreatedTime();
		Date d2 = new Date();
		if (sd1.length() > 10) {
			try {
				d2 = sdfHHmmss.parse(sd2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				d2 = sdfyyyyMMdd.parse(sd2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (d1.before(d2)) {
			return 1;
		} else if (d1.after(d2)) {
			return -1;
		} else {
			return 0;
		}
	}
}
