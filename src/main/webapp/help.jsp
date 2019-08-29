<%@page import="cn.com.bsoft.BSXmlWsEntryClassServicePortType"%>
<%@page import="cn.com.bsoft.BSXmlWsEntryClassService"%>
<%@page import="java.util.Vector"%>
<%@page import="javax.xml.namespace.QName"%>
<%@page import="org.apache.axis.client.Call"%>
<%@page import="org.apache.axis.client.Service"%>
<%@page import="co.kensure.http.WSUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
 String wsurl = "http://172.18.18.38/prmInterfaceHIS2008/prmInterfaceHIS2008.asmx?wsdl";
 String wsmethod = "SentMessageToPat";
String[] paramName = {"phone","content"};
Object[] data = new Object[] {"13606816944","打的费答复水电费"};
String sss = WSUtils.getWS(wsurl, wsmethod, "http://tempuri.org/", paramName, data);

	/* 		String endpoint = "http://172.16.80.168:8089/webserviceEntry?wsdl";
	 Service service = new Service();
	 Call call = (Call) service.createCall();// 通过service创建call对象
	 // 设置service所在URL
	 call.setTargetEndpointAddress(new java.net.URL(endpoint));
	 call.setOperationName("invoke");
	 call.addParameter(new QName("http://www.bsoft.com.cn", "service"), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
	 call.addParameter(new QName("http://www.bsoft.com.cn", "urid"), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
	 call.addParameter(new QName("http://www.bsoft.com.cn", "pwd"), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
	 call.addParameter(new QName("http://www.bsoft.com.cn", "parameter"), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
	 call.setUseSOAPAction(true);
	 call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_STRING); // 返回参数的类型(不能用Array，否则报错)
	 call.setSOAPActionURI("http://www.bsoft.com.cn/invoke");
	 String ret = (String) call.invoke(new Object[] {"DepatmentQueryAll","","",""});
	 System.out.println("--------" + ret); */
%>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="format-detection" content="telephone=no">
	
    <title>帮助测试</title>
</head>
<body>
<div>
<%=sss %>
</div>
</body>
</html>

