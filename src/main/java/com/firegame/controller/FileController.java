package com.firegame.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import fileUtils.Progress;
import service.FileService;

@Controller
public class FileController {
	@Autowired
	FileService fileService;
	@RequestMapping("/upload")
	public void upload(HttpSession session,HttpServletRequest request, HttpServletResponse response,
            @RequestParam("file") CommonsMultipartFile file) throws IOException {
        PrintWriter out;
        boolean flag = false;
        if (file.getSize() > 0) {
            String uploadPath = fileService.getPath1();
           // String uploadPath = request.getSession().getServletContext().getRealPath(path);
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(),
                        new File(uploadPath, session.getAttribute("userid")+".png"));
                flag = true;
            } catch (Exception e) {
            }

        }
        out = response.getWriter();
        if (flag == true) {
            out.print("1");
        } else {
            out.print("2");
        }
    }
	@RequestMapping("/uploadState")
	public void uploadState(HttpSession session,HttpServletResponse response) throws IOException{
		Progress process=(Progress) session.getAttribute("uploadstate");
		
		JSONObject jsonObject=new JSONObject();
		if(process==null){
			jsonObject.element("have", 0);
			response.getWriter().print(jsonObject.toString());
		}
		jsonObject.element("have", 1);
		jsonObject.element("now", process.getBytesRead());
		jsonObject.element("max", process.getContentLength());
		response.getWriter().print(jsonObject.toString());
		
		
		
	}
}
