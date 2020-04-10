package com.txy.client.controller;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.txy.client.domain.UploadFile;
import com.txy.client.utils.HttpUtil;


//import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;

@Controller
public class FileController {
    private String uuid = "";
    private String password1="";

    @GetMapping("/")
    @ResponseBody
    public String index1() {
        return "项目成功启动";
    }

    //文件上传页面
    @GetMapping("/file")
    public String index() {

        return "file.html";
    }

    //文件上传
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("result", "文件为空");
            return "file.html";
        }
        InputStream in = null;
        OutputStream out = null;
        InputStream in1 = null;
        OutputStream out1 = null;
        String name="";
        String size="";
        String address="";
        String type="";
        String time="";
        String password="";

        try {

            in = file.getInputStream();
            String fileName = file.getOriginalFilename();
            String s = HttpUtil.filePost("http://127.0.0.1:8899/upload", in, fileName);
            uuid=s;
            String message=HttpUtil.messageGet("http://127.0.0.1:8899/upload?UUID="+s);
            com.alibaba.fastjson.JSONObject obj = com.alibaba.fastjson.JSONObject.parseObject(message);
            name=obj.getString("name");
            size=obj.getString("size");
            address=obj.getString("address");
            type=obj.getString("type");
            time=obj.getString("time");
            password=obj.getString("password");
            password1=password;


            model.addAttribute("result", "上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("result", "上传失败");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in1 != null) {
                try {
                    in1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out1 != null) {
                try {
                    out1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            model.addAttribute("name", name);
            model.addAttribute("size", size);
            model.addAttribute("type", type);
            model.addAttribute("time", time);
            model.addAttribute("address", address);
            model.addAttribute("password", password);
            return "file.html";
        }

    }

    //上传页面的下载
    @GetMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "fileName", required = false) String fileName) throws IOException {

        if (!uuid.equals("")) {

            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                OutputStream os = response.getOutputStream();
                HttpUtil.fileGet("http://127.0.0.1:8899/download?UUID=" + uuid, os,password1);
            } catch (Exception e) {
                System.out.println("Download the song failed!");
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    //文件清单页面的下载
    @GetMapping("/download1")
    public void download1(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "uuid", required = false) String uuid,@RequestParam(value = "fileName", required = false) String fileName,@RequestParam(value = "password", required = false) String password) throws IOException {

        System.out.println("清单下载的password为"+password);
        if (!uuid.equals("")) {

            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                OutputStream os = response.getOutputStream();
                HttpUtil.fileGet("http://127.0.0.1:8899/download?UUID=" + uuid, os,password);
            } catch (Exception e) {
                System.out.println("Download the song failed!");
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    //文件清单展示
    @GetMapping("/fileList")
    public String fileList(Model model) throws IOException{
        String s=HttpUtil.listGet("http://127.0.0.1:8899/list");
        JSONArray jsonArray=JSONArray.parseArray(s);
        List<UploadFile> fileList=new ArrayList<UploadFile>();
        for(int i=0;i<jsonArray.size();i++){
            JSONObject obj=jsonArray.getJSONObject(i);
            UploadFile file=new UploadFile();
            file.setName(obj.getString("name"));
            file.setUuid(obj.getString("uuid"));
            file.setType(obj.getString("type"));
            file.setSize(obj.getString("size"));
            file.setAddress(obj.getString("address"));
            file.setPassword(obj.getString("password"));
            file.setTime(obj.getString("time"));
            fileList.add(file);
        }
        model.addAttribute("fileList",fileList);
        return "fileList.html";
    }


}
