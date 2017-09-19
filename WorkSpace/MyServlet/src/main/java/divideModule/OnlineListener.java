package divideModule;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by ZKJ on 2017/8/29.
 */
public class OnlineListener implements ServletContextListener {
    //模拟保存用户名和最近的访问时间
    public static Map<String, Long> map = new HashMap<String, Long>();

    //超过该时间（10分钟）没有访问本站即认为用户已经离线
    public final int MAX_MILLIS = 10 * 60 * 1000;
    //这里只是一个加载配置文件的示例，只是为了说明配置文件应该放在那个目录
    private Properties properties = new Properties();

    public void contextInitialized( ServletContextEvent arg0 ) {

        try {
            //使用当前的Class的ResourceAsStream即可得到该文件
            properties.load(this.getClass().getResourceAsStream("/config.properties"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //第5秒检查一次
        new javax.swing.Timer(1000 * 5, new ActionListener() {


            public void actionPerformed( ActionEvent arg0 ) {
                System.out.println(properties.getProperty("username") + "==================");
                Set<String> keys = map.keySet();
                String username = "";
                Iterator<String> iter = keys.iterator();
                while(iter.hasNext()) {
                    username = iter.next();
                    //如果距离上交访问时间超过了指定时间
                    if (System.currentTimeMillis() - map.get(username) > MAX_MILLIS) {
                        //则超过的用户删除
                        map.remove(username);
                    }
                }

            }
        }).start();

    }

    public void contextDestroyed( ServletContextEvent servletContextEvent ) {

    }
}