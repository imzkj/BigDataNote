package predict;

import Util.ToolUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 根据前n次连续开的数字得出没开的数字预测
 */
public class HistoryPredict {
	private static Connection root = null;
	private static Statement statement = null;

	public static void main( String[] args ) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			root = DriverManager.getConnection("jdbc:mysql://192.168.58.28:3306/lottery", "root", "123456");
			statement = root.createStatement();
			ArrayList<String> weiLists = new ArrayList<String>() {{
				add("wan");
				add("qian");
				add("bai");
				add("shi");
				add("ge");
			}};
			int num = 6;
			for (String wei : weiLists) {
				Map<Integer, String> predict = predict(wei, num);
				writeToDatabase(predict,wei);
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
				}
				if (lastNumValues.size() == num) {
					String predictValue = getPredictValue(lastNumValues);
					predictValueMaps.put(thisIssue,predictValue);
				}
				if (lastNumValues.contains(value)) {
					lastNumValues.remove(Integer.valueOf(value));
				}
				lastNumValues.add(value);
				if (lastNumValues.size() > num) {
					lastNumValues.remove(0);
				}
				lastIssue = thisIssue;
			}
			resultSet.close();
		}catch (Exception e){
		}
		return predictValueMaps;
	}

	private static void writeToDatabase( Map<Integer,String> predict ,String wei) {
		for (int issue:predict.keySet()){
			try {
				statement.execute("update lottery_record set predict"+wei+"='"+predict.get(issue)+"' where issue="+issue);
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
