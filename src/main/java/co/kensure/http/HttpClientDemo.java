package co.kensure.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpClientDemo {
	private static final String APPLICATION_JSON = "application/json";

	private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

//	public static void main(String[] args) throws Exception {
//		// SMSClient smsClient = new SMSClient();
//		// smsClient.sendSMSMessage("13606816944", "123456",
//		// Constants.SMS_DURATION_TIME);
//		
//		  String accountSid = "bdc33045964e49a9bedfd2c7e5ca8faf"; 
//		  String  smsToken = "00235df98cea4872b52edbc0d2ccf48a";
//		  String baseUrl = "https://api.miaodiyun.com/20150822/industrySMS/sendSMS"; 
//		  String timeStamp = Utils.getCurrentTime("yyyyMMddHHmmss"); 
//		  String sig =  Utils.generateMD5(accountSid + smsToken + timeStamp);
//		  
//		 HttpClient httpClient = new HttpClient(); PostMethod method = new
//		  PostMethod(baseUrl); NameValuePair[] postData = new NameValuePair[6];
//		  postData[0] = new NameValuePair("accountSid", accountSid);
//		  postData[1] = new NameValuePair("smsContent",
//		  "【触联科技】您的智能锁账号验证码为123456，请于111分钟内正确输入验证码。"); postData[2] = new
//		  NameValuePair("to", "13606816944"); postData[3] = new
//		  NameValuePair("timestamp", timeStamp); postData[4] = new
//		  NameValuePair("sig", sig); postData[5] = new
//		  NameValuePair("respDataType", "JSON"); try {
//		  method.addParameters(postData);
//		  method.addRequestHeader("Content-type",
//		  "application/x-www-form-urlencoded;charset=utf-8"); int dd
//		  =httpClient.executeMethod(method); String aa =
//		  method.getResponseBodyAsString(); System.out.println("aa"+aa); }
//		  catch (IOException e) { e.printStackTrace(); }
//		
//		JSONObject ss = new JSONObject();
//		String body = ss.toString();
//		String url = "https://192.168.3.89:8443/api/lock/version_list";
//				String result = post(body,url);
//		System.out.println(result);
//	}
//
//
//
//	
//	public static String sendsms() throws Exception {
//		String body = "{\"name\": \"linrenjun\",\"password\": \"testpwd\",\"phone\": \"13606816944\",\"email\": \"linrenjun@foxmail.com\",\"phone_verify_code\": \"132845\"}";
//		JSONObject ss = new JSONObject();
//		ss.put("name", "linrenjun");
//		ss.put("password", "testpwd");
//		ss.put("email", "linrenjun@foxmail.com");
//		ss.put("phone", "13606816944");
//		ss.put("phone_verify_code", "132845");
//		String url = "https://192.168.3.89:8443/api/user/register";
//				String result = post(body,url);
//		System.out.println(result);
//		return result;
//	}
	
	
}
