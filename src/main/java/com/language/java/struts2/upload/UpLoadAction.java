package com.language.java.struts2.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class UpLoadAction extends ActionSupport {

    private File uploadfile;
    private String uploadfileFileName;

    public File getUploadfile() {
        return uploadfile;
    }

    public void setUploadfile(File uploadfile) {
        this.uploadfile = uploadfile;
    }

    public String getUploadfileFileName() {
        return uploadfileFileName;
    }

    public void setUploadfileFileName(String uploadfileFileName) {
        this.uploadfileFileName = uploadfileFileName;
    }

    /**
     * @return
     * @throws Exception
     */
    public String upLoad() throws Exception {
        System.out.println("upload");

        String basepath = ServletActionContext.getServletContext().getRealPath("/");

        System.out.println("s=" + basepath);
        StringBuffer path = new StringBuffer();
        path.append(basepath).append("upload\\").append(uploadfileFileName);
        System.out.println("path=" + path);
        OutputStream fos = new BufferedOutputStream(new FileOutputStream(path.toString()));
        InputStream fis = new BufferedInputStream(new FileInputStream(uploadfile));

        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = fis.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }

        fos.close();
        fis.close();

        return this.downLoad();
    }

    public String downLoad() throws Exception {
        return "download";
    }

}
