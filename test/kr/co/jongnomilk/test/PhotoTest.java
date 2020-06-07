package kr.co.jongnomilk.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

public class PhotoTest {

	public static void main(String[] args) {
        URL url = null;
        URLConnection urlConnection = null;
        HttpURLConnection connec = null;

        // URL 주소
        String sUrl = "http://192.168.0.10:5000/camera/";

        String paramValue = "0";
        

        try {
            // Get방식으로 전송 하기
            url = new URL(sUrl + paramValue);
            urlConnection = (HttpURLConnection)url.openConnection();
            byte[] buffer = printByInputStream(urlConnection.getInputStream());
            System.out.println(new String(buffer));

        } catch(Exception e) {
           e.printStackTrace();
        }
    }

    // 웹 서버로 부터 받은 웹 페이지 결과를 콘솔에 출력하는 메소드
    public static byte[] printByInputStream(InputStream is) {
        byte[] buf = new byte[1024];
        int len = -1;
        try {
            while((len = is.read(buf, 0, buf.length)) != -1) {
                System.out.write(buf, 0, len);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return buf;
    }

}
