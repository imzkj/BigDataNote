package predict;

import Util.ToolUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 根据历史统计概率，依靠上一个数字预测下一个数字
 */
public class ProbabilityPredict {
	private static Connection root = null;
	private static Statement statement = null;
	private static Statement statement1 = null;
	private static String testTableName = "lottery_record";

	public static void main( String[] args ) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			root = DriverManager.getConnection("jdbc:mysql://192.168.58.28:3306/lottery", "root", "123456");
			statement = root.createStatement();
			statement1 = root.createStatement();
			ArrayList<String> weiLists = new ArrayList<String>() {{
				add("wan");
				add("qian");
				add("bai");
				add("shi");
				add("ge");
			}};
			int num = 4;
			for (String wei : weiLists) {
				Map<Integer, String> predict = predict(wei, num);
				writeToDatabase(predict, wei);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				root.close();
				statement.close();
				statement1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static Map<Integer, String> predict( String wei, int num ) throws SQLException {
		HashMap<Integer, String> predictValueMaps = new HashMap<Integer, String>();
		try {
			ResultSet resultSet = statement.executeQuery("select issue," + wei + " from " + testTableName + " order by issue asc");
			int lastIssue = 0;
			int lastValue = 0;
			while(resultSet.next()) {
				int thisIssue = resultSet.getInt("issue");
				int value = resultSet.getInt(wei);
				if (ToolUtil.isSeries(lastIssue, thisIssue)) {
					ResultSet resultSet1 = statement1.executeQuery("select value from result where wei='" + wei +
							"' and `key`=" + lastValue + " order by probability desc limit " + num);
					String predictValue = "";
					while(resultSet1.next()) {
						predictValue += resultSet1.getString(1);
					}
					predictValueMaps.put(thisIssue, predictValue);
				}
				lastIssue = thisIssue;
				lastValue = value;
			}
			resultSet.close();
		} catch (Exception e) {
		}
		return predictValueMaps;
	}

	private static void writeToDatabase( Map<Integer, String> predict, String wei ) {
		for (int issue : predict.keySet()) {
			try {
				statement.execute("update " + testTableName + " set predict" + wei + "='" + predict.get(issue) + "' where issue=" + issue);
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
