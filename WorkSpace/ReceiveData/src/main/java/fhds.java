import java.beans.PropertyVetoException;
import java.io.*;
import java.sql.*;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.model.DataModel;
import com.service.serviceImpl.serviceImpl;

public class fhds {
    public static void main(String[] args) throws IOException {
//        for(int x = 0; x < 2000 ; x++) {
//            new MyThread("线程"+String.valueOf(x)).start();
//        }

        File file = new File("../logs/saveData/user_mess.txt");
        InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
        BufferedReader bf = new BufferedReader(inputReader);
        // 按行读取字符串
        String str;
        for (int i=1;(str = bf.readLine()) != null;i++) {
            System.out.println(str);
            String[] aa = str.split("\t");
            //行
            String a=aa[0];
            a=aa[1];
            a=aa[2];
            a=aa[3];
        }
    }
}

class MyThread extends Thread {//线程主体类
    private String title;
    public MyThread(String title) {
        this.title = title;
    }
    @Override
    public void run() {//线程的主体方法
        serviceImpl s = new serviceImpl();
        for(int x = 0; x < 500 ; x++) {
            DataModel aa =new DataModel();
            aa.setChannel("a");
            aa.setName(title);
            aa.setPhone(String.valueOf(x));
            s.save(aa);
//            System.out.println(this.title + "运行，x = " + x);
        }
    }
}
