package kr.co.jongnomilk.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import kr.co.jongnomilk.model.OnAirData;

public class ImageDownloadThread extends Thread{
	
	private HttpServletRequest request;
	private String serialNumber;

	
	public void setData(HttpServletRequest request, String serialNumber) {
		this.request = request;
		this.serialNumber = serialNumber;
	}
	
	@Override
	public void run() {
		ServletContext application =request.getServletContext(); 
		if(application.getAttribute(serialNumber + "onAir") == null) {
			application.setAttribute(serialNumber + "onAir", false);
		}
		while((boolean)application.getAttribute(serialNumber + "onAir")) {
			downloadImage(this.request);
		}
	}

	
	public void downloadImage(HttpServletRequest request) {
		try {
			String realPath = request.getServletContext().getRealPath("/img/");
			System.out.println("realpath: " + realPath);
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); 
        	Date date = new Date();
            URL url = new URL("http://192.168.0.10:5000/cam/");
            BufferedImage img = ImageIO.read(url);
            String downloadURL = realPath + dateFormat.format(date) + ".jpg";
            System.out.println("down: " + downloadURL);
            File file=new File(downloadURL);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
         e.printStackTrace();
        }
	}
	

}
