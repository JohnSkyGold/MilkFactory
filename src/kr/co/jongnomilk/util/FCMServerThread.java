package kr.co.jongnomilk.util;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.Gson;

import kr.co.jongnomilk.model.Data;
import kr.co.jongnomilk.model.FCMData;
import kr.co.jongnomilk.model.FCMMsgData;

import java.security.cert.X509Certificate;
import java.util.ArrayList;

public class FCMServerThread extends Thread{
	Data data;
	
	
	public void setData(Data data) {
		this.data = data;
	}
	
	
	
	
	@Override
	public void run() {
		String url = "https://fcm.googleapis.com/fcm/send"; 
		FCMData fcmData = new FCMData();
		fcmData.setData(data);
		
		//받을 사람키
		fcmData.setTo("erf75Kzs2MY:APA91bEtxppSDd-8_nDhS0ZSTjyMcMMrbJOscrgvq171rPlbx8DANDKDXP72UAPEGEuoo5Z1eHawfYDWTTatbnySCdZb6f00nKgdjev5CUiZDa6CD2ZLJEOdUWWH_KGo6248E5cnoEqN");
		
		System.out.println(data);
		Gson gson = new Gson();
		
		String params = gson.toJson(fcmData);
		System.out.println(params);
		try {
			String returnData = sendPost(url, params);
			System.out.println(returnData);
		}catch(Exception e) {
			System.out.println("e : " + e);
		}		
	}

	
	
	public String sendPost(String url, String parameters) throws Exception { 
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){  //https라서 보안인증 과정이 필요
                public X509Certificate[] getAcceptedIssuers(){return new X509Certificate[0];}
                public void checkClientTrusted(X509Certificate[] certs, String authType){}
                public void checkServerTrusted(X509Certificate[] certs, String authType){}
        }};
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
   
       URL obj = new URL(url);
       HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
   
       //request header
       con.setRequestMethod("POST");
       con.setRequestProperty("Content-Type", "application/json");
       //서버키
//       con.setRequestProperty("Authorization", "key=AAAAMpgSjko:APA91bFhYS835kinNTDzYrbNhLOQWEzOuWEV1dswXRbgH7p_IdphSMc1FQff7TQiAtVrw2DvTtEKk6PwUoq714Sn-9ql6meizFpiRveN34BEXp1wEZocTqNPssRdSBvrWwi_eMp7O-LW");
       con.setRequestProperty("Authorization", "key=AAAAhGL9pa8:APA91bHSybBfDizEIjEji9THkIAsCIpJEQUQqa98RE4h_FVy54_AuFb5pYsFEpKn3dVhhm1PCb0Ej1yLSoFffQrqifeLwCOuiErOaZwIlEHbBhIg1DxwQUAlrqf6PNdc-2IdN82NOo6r");
       String urlParameters = parameters;
   
       //post request
       con.setDoOutput(true);
       DataOutputStream wr = new DataOutputStream(con.getOutputStream());
       wr.write(urlParameters.getBytes("UTF-8"));
       wr.flush();
       wr.close();

       int responseCode = con.getResponseCode();     
       System.out.println("Post parameters : " + urlParameters);
       System.out.println("Response Code : " + responseCode);
   
       StringBuffer response = new StringBuffer();
   
       if(responseCode == 200){   
              BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
              String inputLine;
              while ((inputLine = in.readLine()) != null) {
                     response.append(inputLine);
              }
              in.close();   
       }
       //result
       System.out.println(response.toString());
       return response.toString();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FCMServerThread fcmThread = new FCMServerThread();
		FCMMsgData fcmMsgData = new FCMMsgData();
		Data data = new Data();
//		알림창
		data.setTitle("화재 경보");
		data.setContent("주위에서 화재가 발생했습니다.");
		
//		메세지
		fcmMsgData.setSerialNumber("MS01");
		fcmMsgData.setMsg("화재발생");
		
		data.setFcmMsgData(fcmMsgData);
		fcmThread.setData(data);
		
		fcmThread.start();
	}

}
