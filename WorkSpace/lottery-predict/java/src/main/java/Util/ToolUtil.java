package Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ToolUtil {
	public static boolean isSeries( int lastV, int thisV ) {
		if (thisV - lastV == 1) {
			return true;
		}
		int lastDay = getLastDay(thisV) * 1000 + 288;
		if (lastDay == lastV) {
			return true;
		}
		return false;
	}

	public static int getLastDay( int date ) {
		int re = 0;
		try {
			String s = String.valueOf(date);
			String substring = s.substring(0, 6);
			String nian = "20" + substring.substring(0, 2);
			String yue = substring.substring(2, 4);
			String ri = substring.substring(4);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date parse = sdf.parse(nian + yue + ri);
			Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
			ca.setTime(parse); //设置时间为当前时间
			ca.add(Calendar.DATE, -1); //日期减1
			Date lastDay = ca.getTime(); //结果
			String format = sdf.format(lastDay).substring(2);
			re = Integer.valueOf(format);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public static void main( String[] args ) {

	}

}

