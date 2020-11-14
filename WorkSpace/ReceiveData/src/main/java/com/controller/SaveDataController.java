package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.model.DataModel;
import com.service.service;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


@Controller("Vanke")
@RequestMapping("/")
public class SaveDataController {

    @Resource
    private service serviceImpl;

    @RequestMapping(value = "saveData")
    public ModelAndView submitTask( @RequestParam String data ) {
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
        try {
//            System.out.println(data);
            DataModel dataModel = JSONArray.parseObject(data, DataModel.class);
            System.out.println("canshu " + dataModel);
            serviceImpl.save(dataModel);
            mav.addObject("status", "0");
            mav.addObject("mess", "success");
        } catch (Exception e) {
            mav.addObject("status", "-1");
            mav.addObject("mess", e.getMessage());
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/getData")//请求路径
    public void downloadResource(// @RequestParam(value = "fileName", required = true) String fileName,
                                 HttpServletResponse response) {
        String dataDirectory = "../logs/saveData";//文件所在目录
        Path file = Paths.get(dataDirectory, "user_mess.txt");//文件对象
        if (Files.exists(file)) {
            response.setContentType("application/x-gzip");
            try {
                response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("user_mess.txt", "UTF-8"));
                serviceImpl.getFile(response.getOutputStream());
//                Files.copy(file, response.getOutputStream());//以输出流的形式对外输出提供下载
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @RequestMapping(value="/downloadData")
    //方法二：可以采用POI导出excel，但是比较麻烦
    public void downloadExcel(HttpServletResponse response, HttpServletRequest request) {
        try {
            Workbook workbook = new HSSFWorkbook();
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");


            String filedisplay = "用户信息数据.xls";

            filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);

            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            Sheet sheet = workbook.createSheet("用户信息表");
            // 第三步，在sheet中添加表头第0行
            Row row = sheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

            Cell cell = row.createCell(0);
            cell.setCellValue("日期");
            cell.setCellStyle(style);
            sheet.setColumnWidth(0, (25 * 256));  //设置列宽，50个字符宽

            cell = row.createCell(1);
            cell.setCellValue("姓名");
            cell.setCellStyle(style);
            sheet.setColumnWidth(1, (20 * 256));  //设置列宽，50个字符宽

            cell = row.createCell(2);
            cell.setCellValue("电话");
            cell.setCellStyle(style);
            sheet.setColumnWidth(2, (15 * 256));  //设置列宽，50个字符宽

            cell = row.createCell(3);
            cell.setCellValue("渠道");
            cell.setCellStyle(style);
            sheet.setColumnWidth(3, (15 * 256));  //设置列宽，50个字符宽

            // 第五步，写入实体数据 实际应用中这些数据从数据库得到
            File file = new File("../logs/saveData/user_mess.txt");
            InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
            BufferedReader bf = new BufferedReader(inputReader);
            // 按行读取字符串
            String str;
            for (int i=1;(str = bf.readLine()) != null;i++) {
                try {
                    String[] aa = str.split("\t");
                    //行
                    row=sheet.createRow(i);
                    row.createCell(0).setCellValue(aa[0]);
                    row.createCell(1).setCellValue(aa[1]);
                    row.createCell(2).setCellValue(aa[2]);
                    row.createCell(3).setCellValue(aa[3]);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            bf.close();
            inputReader.close();
//            row = sheet.createRow(1);
//            row.createCell(0, Cell.CELL_TYPE_STRING).setCellValue(1);
//            row.createCell(1, Cell.CELL_TYPE_STRING).setCellValue(2);
//            row.createCell(2, Cell.CELL_TYPE_STRING).setCellValue(3);   //商品价格
//            row.createCell(3, Cell.CELL_TYPE_STRING).setCellValue(4);  //规格

            // 第六步，将文件存到指定位置
            try
            {
                OutputStream out = response.getOutputStream();
                workbook.write(out);
                out.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main( String[] args ) throws UnsupportedEncodingException {
        JSONObject json = new JSONObject();//new一个json对象
//        Map<String,Object> map = new HashMap<String, Object>();//new一个map集合
        json.put("phone","13890909023");
        json.put("name","124526376");
        json.put("channel", "123");
//        json.put("ymd","1000908900");
//        json.put("params", map);//将数据放入json中   注：若将此json数据放入url地址栏中会，程序运行时会报错，所以需进行操做

        String tojsonstring=json.toJSONString();//将json格式的数据转换为字符格式
//        System.out.println(tojsonstring);
        tojsonstring= URLEncoder.encode(tojsonstring,"utf-8");//将数据进行编码
        String url ="http://localhost:8080/saveData.do?data="+tojsonstring;
        System.out.println(url);

    }
}
