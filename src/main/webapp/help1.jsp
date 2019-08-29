<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="java.net.URL"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.util.Map"%>
<%@page import="co.kensure.mem.MapUtils"%>
<%@page import="co.kensure.http.HttpUtils"%>
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

JSONObject jsonParam = new JSONObject();
jsonParam.put("service", "searchByYf");
jsonParam.put("organization", "79649060-6");
String urls = "http://172.16.80.85:9020/ez/InformationSearch";
String aa = HttpUtils.getJsonData(jsonParam, urls);
	System.out.print("aa=="+aa);
%>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="format-detection" content="telephone=no">
	
    <title>帮助测试</title>
</head>
<body>
<div>
<%=aa %>
</div>
</body>
</html>

