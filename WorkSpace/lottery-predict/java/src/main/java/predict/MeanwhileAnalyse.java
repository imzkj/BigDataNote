package predict;

import Util.ToolUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 统计数字在5个记录内同时出现的概率
 */
public class MeanwhileAnalyse {
	private static Connection root = null;
	private static Statement statement = null;
	private static Map<Integer, Integer> countNum = new HashMap<Integer, Integer>();
	static int allCount = 0;

	public static void main( String[] args ) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			root = DriverManager.getConnection("jdbc:mysql://192.168.58.28:3306/lottery", "root", "123456");
			statement = root.createStatement();
			for (int i = 0; i < 10; i++) {
				countNum.put(i, 0);
			}
			ArrayList<String> weiLists = new ArrayList<String>() {{
				add("wan");
//				add("qian");
//				add("bai");
//				add("shi");
//				add("ge");
			}};
			int num = 5;
			for (String wei : weiLists) {
				Map<Integer, String> predict = predict(wei, num);
				System.out.println(countNum);
				System.out.println(allCount);

//				writeToDatabase(predict, wei);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				root.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static Map<Integer, String> predict( String wei, int num ) throws SQLException {
		HashMap<Integer, String> predictValueMaps = new HashMap<Integer, String>();
		try {
			ResultSet resultSet = statement.executeQuery("select issue," + wei + " from lottery_record order by issue asc");
			ArrayList<Integer> lastNumValues = new ArrayList<Integer>();
			int lastIssue = 0;
			while(resultSet.next()) {
				int thisIssue = resultSet.getInt("issue");
				int value = resultSet.getInt(wei);
				if (lastIssue != 0 && !ToolUtil.isSeries(lastIssue, thisIssue)) {
					lastNumValues.clear();
					lastIssue = thisIssue;
					continue;
				}
				if (lastNumValues.contains(value)) {
					int i = lastNumValues.indexOf(value);
					lastNumValues.set(i, -1);
					countNum.put(value, countNum.get(value) + 1);
				}
				lastNumValues.add(value);
				if (lastNumValues.size() == num) {
					allCount++;
					lastNumValues.remove(0);
				}
				lastIssue = thisIssue;
			}
			resultSet.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return predictValueMaps;
	}

	private static void writeToDatabase( Map<Integer, String> predict, String wei ) {
		for (int issue : predict.keySet()) {
			try {
				statement.execute("update lottery_record set predict" + wei + "='" + predict.get(issue) + "' where issue=" + issue);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static String getPredictValue( ArrayList<Integer> lastNumValues ) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			if (!lastNumValues.contains(i)) {
				stringBuilder.append(i);
			}
		}
		return stringBuilder.toString();
	}
}
