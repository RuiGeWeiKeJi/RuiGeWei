package com.huotu.scrm.web.GetUserInfo;

import org.springframework.util.FileCopyUtils;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class DownLoad {

    public static void DownLoadInfo(String fileName,HttpServletResponse response,HttpServletRequest request){
        response.setHeader("content-type","application/octet-stream");
        response.setContentType("application/force-stream");
        String encodedFileName = null;
        String userAgentString = request.getHeader("User-Agent");
        if(userAgentString.contains("Chrome") || userAgentString.contains("Internet Exploer") || userAgentString.contains("Safari")) {
            try {
                encodedFileName = URLEncoder.encode(fileName,"utf-8").replaceAll("\\+", "%20");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                encodedFileName = MimeUtility.encodeWord(fileName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        try {
            response.setHeader("Content-Disposition","attachment;filename=\"" +encodedFileName+"\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        DownLoad.  DownLoadFile(fileName,response,request);
    }

    public static void DownLoadFile(String fileName, HttpServletResponse response){
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File("C://模板//"+ fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void  DownLoadFile(String fileName, HttpServletResponse response, HttpServletRequest request){
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(new File("C://模板//"+ fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            FileCopyUtils.copy(in, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
