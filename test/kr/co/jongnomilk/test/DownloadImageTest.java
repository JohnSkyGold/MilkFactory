package kr.co.jongnomilk.test;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

public class DownloadImageTest {

	public DownloadImageTest() {
		 Image image = null;
	        try {
	        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); 
	        	Date date = new Date();
	            URL url = new URL("http://192.168.5.1:8181/milkfactory/photo.do");
	            BufferedImage img = ImageIO.read(url);
	            String downloadURL = "C:/image/download/arang" + dateFormat.format(date) + ".jpeg";
	            File file=new File("C:/image/download/");
	            File[] files = file.listFiles();
	            for ( int i = 0; i < files.length; i++ ) {
	                if ( files[i].isFile()){ 
	                System.out.println(files[i].getName());
	                }
	             }
	            ImageIO.write(img, "jpeg", file);
	        } catch (IOException e) {
	         e.printStackTrace();
	        }
	}

	public static void main(String[] args) {
		
		new DownloadImageTest();
	}

}
