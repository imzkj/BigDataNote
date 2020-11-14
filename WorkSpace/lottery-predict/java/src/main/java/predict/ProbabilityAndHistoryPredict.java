package predict;

import Util.ToolUtil;

import java.sql.*;
import java.util.*;

/**
 * 根据历史统计概率和前几次出现数字降权，依靠上一个数字预测下一个数字
 */
public class ProbabilityAndHistoryPredict {
	private static Connection root = null;
	private static Statement statement = null;
	private static Statement statement1 = null;
	private static String testTableName = "lottery_record_test";

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
		ArrayList<Integer> lastNumValues = new ArrayList<Integer>();
		try {
			ResultSet resultSet = statement.executeQuery("select issue," + wei + " from " + testTableName + " order by issue asc");
			int lastIssue = 0;
			int lastValue = 0;
			while(resultSet.next()) {
				int thisIssue = resultSet.getInt("issue");
				int value = resultSet.getInt(wei);
				if (lastIssue != 0 && !ToolUtil.isSeries(lastIssue, thisIssue)) {
					lastNumValues.clear();
				}
				if (lastNumValues.size() == 5) {
					List<Integer> noPresent = getNoPresent(lastNumValues);
					HashMap<Integer, Double> integerDoubleHashMap = new HashMap<Integer, Double>();
					ResultSet resultSet1 = statement1.executeQuery("select value,probability from result where wei='" + wei +
							"' and `key`=" + lastValue);
					while(resultSet1.next()) {
						int anInt = resultSet1.getInt(1);
						double aDouble = resultSet1.getDouble(2);
						if (noPresent.contains(anInt)) {
							aDouble = aDouble * 1.4;
						}
						integerDoubleHashMap.put(anInt, aDouble);
					}
					String predictValue = getResult(integerDoubleHashMap, num);
					predictValueMaps.put(thisIssue, predictValue);
				}
				if (lastNumValues.contains(value)) {
					lastNumValues.remove(Integer.valueOf(value));
				}
				lastNumValues.add(value);
				if (lastNumValues.size() > 5) {
					lastNumValues.remove(0);
				}
				lastIssue = thisIssue;
				lastValue = value;
			}
			resultSet.close();
		} catch (Exception e) {
		}
		return predictValueMaps;
	}

	private static String getResult( HashMap<Integer, Double> integerDoubleHashMap, int num ) {
		String a = "";
		for (int i = 0; i < num; i++) {
			Iterator<Map.Entry<Integer, Double>> iterator = integerDoubleHashMap.entrySet().iterator();
			int maxkey = 0;
			double maxva = 0.0;
			while(iterator.hasNext()) {
				Map.Entry<Integer, Double> next = iterator.next();
				Integer key = next.getKey();
				Double value = next.getValue();
				if (value > maxva) {
					maxva = value;
					maxkey = key;
				}
			}
			a += maxkey;
			integerDoubleHashMap.remove(maxkey);
		}
		return a;
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

	private static List<Integer> getNoPresent( ArrayList<Integer> lastNumValues ) {
		ArrayList<Integer> integers = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			if (!lastNumValues.contains(i)) {
				integers.add(i);
			}
		}
		return integers;
	}
}
