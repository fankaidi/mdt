package co.kensure.sms;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import co.kensure.exception.BusinessExceptionUtil;
import co.kensure.mem.Utils;

import com.alibaba.fastjson.JSONObject;

public class SMSClient {

	private static final String accountSid = "bdc33045964e49a9bedfd2c7e5ca8faf";
	private static final String baseUrl = "https://api.miaodiyun.com/20150822/industrySMS/sendSMS";
	private static final String smsToken = "00235df98cea4872b52edbc0d2ccf48a";
	private static final String contentTemplate = "【试呗网】您的验证码为{1}，请于{2}分钟内正确输入，如非本人操作，请忽略此短信。";
	private static final String contentTemplate2 = "【试呗网】您申请的{1}已审核通过，请在{2}内下单";
	private static final String contentTemplate3 = "【试呗网】您绑定的淘宝账号有误，请重新绑定，如有问题请联系客服！";
	
	
	/**
	 * 发送淘宝账号绑定错误提示短信
	 * @param phoneNumber
	 *            电话号码
	 */
	public static void sendSMSTaobao(String phoneNumber) throws Exception {
		sendSMSMessage(phoneNumber, contentTemplate3);
	}
	
	/**
	 * 验证码发送
	 * @param phoneNumber
	 *            电话号码
	 * @param code
	 *            验证码
	 * @param durationMinute
	 *            时间
	 * @throws Exception
	 */
	public static void sendSMSMessage(String phoneNumber, String code, int durationMinute) throws Exception {
		String smsContent = contentTemplate;
		smsContent = smsContent.replaceFirst("\\{1\\}", code);
		smsContent = smsContent.replaceFirst("\\{2\\}", durationMinute + "");
		sendSMSMessage(phoneNumber, smsContent);
	}
	
	/**
	 * 中奖短信
	 * @param phoneNumber
	 *            电话号码
	 * @param code
	 *            上传名称
	 * @param durationMinute
	 *            时长
	 * @throws Exception
	 */
	public static void sendZhongJiang(String phoneNumber, String name, String content) throws Exception {
		String smsContent = contentTemplate2;
		smsContent = smsContent.replaceFirst("\\{1\\}", name);
		smsContent = smsContent.replaceFirst("\\{2\\}", content);
		sendSMSMessage(phoneNumber, smsContent);
	}
	
	/**
	 * 
	 * @param phoneNumber
	 *            电话号码
	 * @param code
	 *            验证码
	 * @param durationMinute
	 *            时间
	 * @throws Exception
	 */
	public static void sendSMSMessage(String phoneNumber, String smsContent) throws Exception {
		String timeStamp = Utils.getCurrentTime("yyyyMMddHHmmss");
		String sig = Utils.generateMD5(accountSid + smsToken + timeStamp);
		// 参数
		Map<String, String> propsMap = new HashMap<String, String>();
		propsMap.put("accountSid", accountSid);
		propsMap.put("smsContent", smsContent);
		propsMap.put("to", phoneNumber);
		propsMap.put("timestamp", timeStamp);
		propsMap.put("sig", sig);
		propsMap.put("respDataType", "JSON");
		// header
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-type", "application/x-www-form-urlencoded;");

		String result = sendPostRequest(baseUrl, propsMap, headers);
		if (result == null) {
			 BusinessExceptionUtil.threwException("发送失败1");;
		} else {
			JSONObject resultJson = JSONObject.parseObject(result);
			String respCode = resultJson.getString("respCode");
			int respCodeInt = Integer.parseInt(respCode);
			if (respCodeInt > 0) {
				BusinessExceptionUtil.threwException("发送失败2");;
			}
		}
	}
	
	

	/**
     * 发送HTTP请求
     * @param url
     * @param propsMap 发送的参数
     */
    public static String sendPostRequest(String url,Map<String,String> propsMap, Map<String, String> headersMap){

       
        HttpPost httpPost = new HttpPost(url);
        //设置header
        if(headersMap != null && !headersMap.isEmpty()){
        	Set<String> keys = headersMap.keySet();
            for(String key:keys){
            	String value = headersMap.get(key);
            	httpPost.setHeader(key, value);
            }
        }
  
        //设置参数
        List<NameValuePair> list = new ArrayList<>();
        if(propsMap != null && !propsMap.isEmpty()){
        	Set<String> keys = propsMap.keySet();
        	for(String key:keys){
        		String value = propsMap.get(key);
                list.add(new BasicNameValuePair(key,value));
            }
        }
        HttpClient httpClient=null;
        try {
        	 httpClient=new DefaultHttpClient();
            if(list.size() > 0){
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"utf-8");
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    String result = EntityUtils.toString(resEntity);
                    return result;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	httpPost.releaseConnection();
        }
        return null;
    }
    
    
	public static void main(String[] args) throws Exception {
		sendSMSMessage("13606816944","111111",30);
		
	}
}
