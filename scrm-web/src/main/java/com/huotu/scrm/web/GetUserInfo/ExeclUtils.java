package com.huotu.scrm.web.GetUserInfo;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExeclUtils {

    private final static String excel2003L =".xls";    //2003- 版本的excel
    private final static String excel2007U =".xlsx";   //2007+ 版本的excel

    public static List<List<Object>> getBankListByExecl(InputStream in, String fileName){
        List<List<Object>> list=null;

        Workbook workbook=getWorkbook(in,fileName);
        if(null==workbook)
            return null;
        Sheet sheet=null;
        Row row=null;
        Cell cell=null;

        list=new ArrayList<List<Object>>();
        //遍历Execl中的所有sheet
        for(int i=0;i<workbook.getNumberOfSheets();i++){
            sheet=workbook.getSheetAt(i);
            if(sheet==null){continue;}

            int sx=sheet.getLastRowNum();
            //遍历sheet的所有行
            for(int j=sheet.getFirstRowNum();j<=sx;j++){
                    row=sheet.getRow(j);
                    if(row==null || row.getFirstCellNum()==j){continue;}

                    List<Object> li=new ArrayList<Object>();
                    //遍历所有列
                    for (int k=row.getFirstCellNum();k<row.getLastCellNum();k++){
                        cell=row.getCell(k);
                        li.add(getCellValue(cell));
                    }
                    list.add(li);
            }

        }
        return list;
    }

    public static Workbook getWorkbook(InputStream inputStream,String fileName) {
        Workbook workbook = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        try {
            if (excel2003L.equals(fileType)) {
                workbook = new HSSFWorkbook(inputStream);
            } else if (excel2007U.equals(fileType)) {
                workbook = new XSSFWorkbook(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    public static  Object getCellValue(Cell cell) {
        Object value = null;
        //格式化number string 字符
        DecimalFormat df = new DecimalFormat("0");
        //日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //格式化数字
        DecimalFormat df2 = new DecimalFormat("0.00");

        if(cell==null)
            return "";

        switch (cell.getCellTypeEnum()) {
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            default:
                break;
        }
        return value;
    }

}
