package com.pinyougou.admin.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pinyougou.mapper.TbContentMapper;
import com.pinyougou.pojo.TbContent;

import entity.Result;


@RestController
@RequestMapping("/file")
public class UploadController {
	@Autowired
	private TbContentMapper tbContentMapper;
	
	@RequestMapping(value="/{formName}")
    public String loginForm(@PathVariable String formName){
		System.out.println("formName:   "+formName);
        return formName;

    }

    @RequestMapping(value="/uploadFile",method=RequestMethod.POST)
    public Result upload(@RequestParam("file") MultipartFile file,
            HttpServletRequest request){
        if (!file.isEmpty()) {
            String contextPath = request.getContextPath();//"/SpringMvcFileUpload"
            String servletPath = request.getServletPath();//"/gotoAction"
            String scheme = request.getScheme();//"http"
            //System.out.println("contextPath:   "+contextPath);
            //System.out.println("servletPath:   "+servletPath);
            //System.out.println("scheme:   "+scheme);
            //String storePath= "D:\\20152203\\tts9\\workbases\\SSM-pinyougou\\src\\main\\webapp\\WEB-INF\\images";//存放我们上传的文件路径
           
            String fileName = file.getOriginalFilename();
            //System.out.println("fileName:   "+fileName);
            
            String extName=fileName.substring(fileName.lastIndexOf("."));
            //System.out.println("extName:   "+extName);
            
            
            String newFileName =getRandomString()+extName;
            System.out.println("newFileName:   "+newFileName);
            
            String realpath=request.getSession().getServletContext().getRealPath("images/");
           // System.out.println("realpath:   "+realpath.toString());
            

            
            File filepath = new File(realpath, newFileName);
            //System.out.println("!filepath.getParentFile().exists():   "+!filepath.getParentFile().exists());
           
            String url=null;
            try {
	            if (!filepath.getParentFile().exists()) {
	                filepath.getParentFile().mkdir();//如果目录不存在，创建目录
	               // System.out.println("filepath:   "+filepath.toString());
	            }
	            if (!filepath.exists()) {
	            	boolean boo=filepath.createNewFile();//如果目录不存在，创建目录
	                ///System.out.println("boo:   "+boo);
	            }

            	url=filepath.toURL().toString();
                file.transferTo(filepath);//把文件写入目标文件地址
                System.out.println("URL:   "+url);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, "上传失败！");

            }
            return new Result(true, "../../images/"+newFileName);//返回到成功页面

        }else {
        	return new Result(false, "上传失败！");//返回到失败的页面
        }

    }
    
    

    @RequestMapping(value = "downPhotoById")  
    public void downPhotoByStudentId(long id, final HttpServletResponse response){  
    	TbContent content = tbContentMapper.selectByPrimaryKey(id);  
    	System.out.println("content:   "+content);
//        byte[] data = entity.getPhotoData();  
//        String fileName = entity.getFileName()== null ? "照片.png" : entity.getFileName();  
    	
		File file = new File("E:\\images\\20190330145739169QnuW.jpg");
		Long filelength = file.length();
		byte[] data = new byte[filelength.intValue()];
    	String fileName=content.getPic().substring(content.getPic().lastIndexOf(""+File.separator));
    	System.out.println("fileName: "+fileName);
        try {
			fileName = URLEncoder.encode(fileName, "UTF-8");
	 
        response.reset();  
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");  
        response.addHeader("Content-Length", "" + data.length);  
        response.setContentType("application/octet-stream;charset=UTF-8");  
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());  
        outputStream.write(data);  
        outputStream.flush();  
        outputStream.close();  
    	} catch (Exception e) {
    		System.out.println("下载失败！ ");
			e.printStackTrace();
		} 
    }  

    
    public static String getRandomString(){
    	int length=4;
        String str="abc67nopSTUVWqrstuGHIJK345LMNOghijklPQRvwxyzABCDEFdefmXYZ01289";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
          int number=random.nextInt(62);
          sb.append(str.charAt(number));
        }
        SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddHHmmssSSS" );
        Date d= new Date();
        String str1 = sdf.format(d);
        System.out.println("当前时间通过 yyyyMMddHHmmssSSS 格式化后的输出: "+str1);
        return str1+sb.toString();
    }    
}