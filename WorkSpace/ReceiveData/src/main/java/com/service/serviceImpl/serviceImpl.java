package com.service.serviceImpl;

import com.model.DataModel;
import com.service.service;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

@Service("serviceImpl")
public class serviceImpl implements service {

    private static Logger log = Logger.getLogger(serviceImpl.class.getClass());
//    @Resource
//    private DataSource dataSource;

    public void save( DataModel dataModel ) {
        writeFile(dataModel);
//        Connection conn = null;
//        Statement statement = null;
//        try {
//            log.info(getYmd() + dataModel);
//            conn = dataSource.getConnection();
//            statement = conn.createStatement();
//            String sql = getSql(dataModel);
//            statement.execute(sql);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                conn.close();
//                statement.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        System.out.println("serviceImpl");
    }

    private String getSql( DataModel dataModel ) {
        String name = dataModel.getName();
        String phone = dataModel.getPhone();
        String channel = dataModel.getChannel();
        String ymd = getYmd();
        String sql = "INSERT INTO user_mess (ymd,name,phone,channel) VALUES (\"" +
                ymd + "\",\"" + name + "\",\"" + phone + "\",\"" + channel + "\")";
        return sql;
    }

    private String getYmd() {
        Date date = new Date();//获取当前的日期
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String str = df.format(date);//获取String类型的时间
        return str;
    }

    private synchronized void  writeFile(DataModel dataModel) {
//        System.out.println("start");
        FileWriter fw = null;
        try {
        //如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f=new File("../logs/saveData/user_mess.txt");
//            System.out.println(f.getAbsolutePath());
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(getYmd() + "\t" + dataModel.getName()+ "\t" + dataModel.getPhone()+ "\t" + dataModel.getChannel());
        pw.flush();
//        System.out.println("writed");
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getFile(OutputStream ops) {
        //下面给出文件和输出流，然后把excel数据写入
//        File file=new File("save//用户"+user.getUserName()+"的消息记录表.xls");
//		if(!file.exists())file.createNewFile();
        try {
            //创建Workbook类
            Workbook wb=new HSSFWorkbook();
            Sheet sheet=wb.createSheet("用户信息表");//sheet是一张表，创建时可以传入表名字
            Row row1=sheet.createRow(0);//由表创建行，需要传入行标，由0开始
            row1.createCell(0).setCellValue("日期");//得到行对象后，按列写入值
            row1.createCell(1).setCellValue("姓名");
            row1.createCell(2).setCellValue("电话");
            row1.createCell(3).setCellValue("渠道");
            File file = new File("../logs/saveData/user_mess.txt");
            InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
            BufferedReader bf = new BufferedReader(inputReader);
            // 按行读取字符串
            String str;
            for (int i=1;(str = bf.readLine()) != null;i++) {
                String[] aa = str.split("\t");
                //行
                Row row=sheet.createRow(i);
                row.createCell(0).setCellValue(aa[0]);
                row.createCell(1).setCellValue(aa[1]);
                row.createCell(2).setCellValue(aa[2]);
                row.createCell(3).setCellValue(aa[3]);
            }
            bf.close();
            inputReader.close();
            wb.write(ops);
            wb.close();
            ops.close();//这里关闭Workbook或者关闭OutputStream都可以，应该是Workbook关闭的时候顺带关闭了OutputStream
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
