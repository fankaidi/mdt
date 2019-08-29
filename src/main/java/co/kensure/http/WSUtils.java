package co.kensure.http;

import java.util.Vector;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

/**
 * webservice调用
 * 
 * @author Administrator
 *
 */
public class WSUtils {

	/**
	 * 调用webservice接口
	 * 
	 * @param url
	 * @param method
	 * @param paramName
	 * @param data
	 * @return
	 */
	public static String getWS(String url, String method,String urn, String[] paramName, Object[] data) {
		String result = null;
		try {
			// 直接引用远程的wsdl文件
			// 以下都是套路
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(url));
			call.setOperationName(new QName(urn, method));// WSDL里面描述的接口名称

			if (paramName != null) {
				for (String name : paramName) {
					call.addParameter(new QName(urn, name), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// 接口的参数
				}
			}
			call.setUseSOAPAction(true);
			call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_STRING);// 设置返回类型
			if (data == null) {
				data = new Object[] {};
			}
			String uri = urn;
			if(!urn.endsWith("/")){
				uri+="/";
			}
			uri+=method;
			call.setSOAPActionURI(uri);
			result = (String) call.invoke(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public static void main(String[] args) {
		try {
			String endpoint = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?wsdl";
			Service service = new Service();
			Call call = (Call) service.createCall();// 通过service创建call对象
			// 设置service所在URL
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(new QName("http://WebXml.com.cn/", "getWeatherbyCityName"));
			call.addParameter(new QName("http://WebXml.com.cn/", "theCityName"), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
			call.setUseSOAPAction(true);
			call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_VECTOR); // 返回参数的类型(不能用Array，否则报错)
			call.setSOAPActionURI("http://WebXml.com.cn/getWeatherbyCityName");
			Vector ret = (Vector) call.invoke(new Object[] { "大庆" });
			System.out.println("--------" + ret);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
