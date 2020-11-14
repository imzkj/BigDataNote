package pinduoduo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Monitor {
    public static void start() {

//        Class.forName("org.sqlite.JDBC");
//        String db = "D:\\jiayuan.db";
//        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
//        Statement state = conn.createStatement();
//        ResultSet rs = state.executeQuery("select * from info;"); //查询数据
//        while (rs.next()) { //将查询到的数据打印出来
//            System.out.print("name = " + rs.getString("uid") + " "); //列属性一
//            System.out.println("age = " + rs.getString("age")); //列属性二
//        }
//        rs.close();
//        conn.close();

//        getContent();
        a();
    }

    public static void getContent() {
        try {
            String url = "http://mobile.yangkeduo.com/search_result.html?search_key=手表";

            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);

            HttpResponse response = client.execute(request);
            System.out.println("Response Code: " +
                    response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while((line = rd.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void a(){

        HttpURLConnection conn = null;
        String url = "http://mobile.yangkeduo.com/search_result.html?search_key=%E6%89%8B%E8%A1%A8%E5%A5%B3&search_src=new&search_met=btn_sort&search_met_track=manual&refer_page_name=search_result&refer_page_id=10015_1574296657542_pQ1lRbLZa0&refer_page_sn=10015";
        try {
            URL realUrl = new URL(url);
            conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setReadTimeout(8000);
            conn.setConnectTimeout(8000);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
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
                System.out.println(result);
                //subscriber是观察者，在本代码中可以理解成发送数据给activity
//                subscriber.onNext(result);
            }
        }catch (Exception e){
//            subscriber.onError(e);

        }}
}
