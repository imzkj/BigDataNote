package pinduoduo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RecordGoods {
    public static void start(String content, String url) {
        if (content.equals("")) {
            content = getGoodsId(url);
            analyticContent(content);
        } else {
            analyticContent(content);
        }
    }
    public static String getGoodsId(String url){

        HttpURLConnection conn;
        try {
            URL realUrl = new URL(url);
            conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setReadTimeout(8000);
            conn.setConnectTimeout(8000);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1");
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = in.readLine()) != null){
                    buffer.append(line);
                }
                String result = buffer.toString();
                return result;
            }
        }catch (Exception e){

        }
        return "";
    }

    public static void analyticContent(String content){
        String pattern = "\\{\"key\":.*?\"goodsID\":(.*?),\".*?\"price\":\"(.*?)\".*?\"salesTip\":\"已拼(.*?)件\".*?\"line\":.*?\\}";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(content);
        int i = 0;
        // m.find 是否找到正则表达式中符合条件的字符串
        while (m.find( )) {
            // 拿到上面匹配到的数据
            String goodsID = m.group(1);
            String price = m.group(2);
            String salesTip = m.group(3);
            if (salesTip.equals("10万+")) {
                continue;
            } else if (salesTip.contains("万")) {
                salesTip = salesTip.replace("万", "")
                        .replace(".", "").concat("000");

            }
            System.out.println("----i="+i);
            System.out.println("goodsID: " + goodsID);
            System.out.println("price: " + price);
            System.out.println("salesTip: " + salesTip);
            i++;
            System.out.println("|||||||");
            System.out.println("");
            saveToDb(goodsID, price, salesTip);
        }
        System.out.println(content);
    }

    private static void saveToDb( String goodsID, String price, String salesTip ) {
        try {
            Class.forName("org.sqlite.JDBC");
            String db = "D:\\jiayuan.db";
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
            Statement state = conn.createStatement();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            String dateStringParse = sdf.format(date);
            String sql = "REPLACE INTO pinduoduo(ymd,id,sale_num,price) VALUES(\"" + dateStringParse + "\"," + goodsID + ","
            + salesTip + ",\"" + price + "\");";
            state.execute(sql);
            state.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
