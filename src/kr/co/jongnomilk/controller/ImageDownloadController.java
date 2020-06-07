package kr.co.jongnomilk.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.jongnomilk.util.ImageDownloadThread;

public class ImageDownloadController implements Controller{

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
//		try {
//			String realPath = request.getServletContext().getRealPath("/img/");
//			System.out.println("realpath: " + realPath);
//        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); 
//        	Date date = new Date();
//            URL url = new URL("http://192.168.0.10:5000/cam/");
//            BufferedImage img = ImageIO.read(url);
//            String downloadURL = realPath + dateFormat.format(date) + ".jpg";
//            System.out.println("down: " + downloadURL);
//            File file=new File(downloadURL);
//            ImageIO.write(img, "jpg", file);
//        } catch (IOException e) {
//         e.printStackTrace();
//        }
		return null;
	}

}
