package com.language.java.struts2.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class DownLoadAction extends ActionSupport {

	@Override
	public String execute() throws Exception {
		
		HttpServletResponse response=(HttpServletResponse) ServletActionContext.getResponse();
		HttpServletRequest request=(HttpServletRequest) ServletActionContext.getRequest();
		
		String realPath =ServletActionContext.getServletContext().getRealPath("/");
		
		String filename = new String(request.getParameter("name").getBytes("ISO-8859-1"),"gbk") ;
		System.out.println("filename="+filename);
		String path = realPath + "upload\\" + filename ;
		
		String filepath = path;
		
		File downloadFile = new File(filepath);
		
		BufferedInputStream bis = null;
		bis = new BufferedInputStream( new FileInputStream(downloadFile));
		
		BufferedOutputStream bos = null;
		bos = new BufferedOutputStream(new FileOutputStream(filename));
		  
		response.setHeader("Content-disposition", "attachment;filename="
				+ URLEncoder.encode(path, "utf-8"));
		int bytesRead = 0; 
		byte[] buffer = new byte[8192];
		while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
			bos.write(buffer, 0, bytesRead);
		} 
	 
		bis.close();
		bos.close();
		return "success";
	}

	
}
