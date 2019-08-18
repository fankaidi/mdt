package co.kensure.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

import co.kensure.exception.BusinessExceptionUtil;
import co.kensure.io.FileUtils;

@SuppressWarnings("deprecation")
public class HttpUtils {

	private static HttpClient httpClient = null;
	static {
		httpClient = new DefaultHttpClient();
	}

	public static HttpResponse get(String personalUrl) {
		// 获取响应文件，即html，采用get方法获取响应数据
		HttpGet getMethod = null;
		HttpResponse response = null;
		try {
			getMethod = new HttpGet(personalUrl);
			response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
			// 执行get方法
			response = httpClient.execute(getMethod);
		} catch (Exception e) {
			BusinessExceptionUtil.threwException(e);
		} finally {

		}
		return response;
	}

	/**
	 * 获取内容
	 * 
	 * @param personalUrl
	 * @return
	 * @throws IOException
	 */
	public static String getBody(String personalUrl) {
		String html = getBody(personalUrl, null);
		return html;
	}

	/**
	 * 获取内容
	 * 
	 * @param personalUrl
	 * @return
	 * @throws IOException
	 */
	public static String getBody(String personalUrl, Map<String, String> headers) {
		// 获取响应文件，即html，采用get方法获取响应数据
		HttpGet getMethod = null;
		HttpResponse response = null;
		String html = null;
		try {
			getMethod = new HttpGet(personalUrl);
			response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					getMethod.addHeader(entry.getKey(), entry.getValue());
				}
			}

			// 执行get方法
			response = httpClient.execute(getMethod);

			int StatusCode = response.getStatusLine().getStatusCode();
			if (StatusCode == 200) {
				html = EntityUtils.toString(response.getEntity(), "utf-8");
			} else {
				BusinessExceptionUtil.threwException("get data StatusCode=" + StatusCode);
			}
		} catch (Exception e) {
			BusinessExceptionUtil.threwException(e);
		} finally {
			getMethod.releaseConnection();
		}
		// 获取响应状态码

		return html;
	}

	/**
	 * 发送post请求，参数用map接收
	 * 
	 * @param url
	 *            地址
	 * @param map
	 *            参数
	 * @return 返回值
	 */
	public static String postParams(String url, Map<String, String> params, Map<String, String> headers) {
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		if(params != null){
			for (Map.Entry<String, String> entry : params.entrySet()) {
				pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		
		if (headers != null) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				post.addHeader(entry.getKey(), entry.getValue());
			}
		}

		CloseableHttpResponse response = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
			response = httpClient.execute(post);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, "utf-8");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				httpClient.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * post请求,提交json
	 * 
	 * @param url
	 * @param json
	 * @return
	 */
	public static String doPost(String url, JSONObject json) {
		DefaultHttpClient client = new DefaultHttpClient();
		String result = null;
		try {
			HttpPost post = new HttpPost(url);
			StringEntity s = new StringEntity(json.toJSONString(),"UTF-8");
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");// 发送json数据需要设置contentType
			post.setEntity(s);
			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				result = EntityUtils.toString(entity);// 返回json格式：
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			client.close();
		}
		return result;
	}

	/**
	 * 获取内容,变成二进制流
	 * 
	 * @param personalUrl
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytes(String personalUrl) {
		HttpResponse response = get(personalUrl);
		// 获取响应状态码
		int StatusCode = response.getStatusLine().getStatusCode();
		byte[] bytes = null;
		if (StatusCode == 200) {
			try {
				bytes = EntityUtils.toByteArray(response.getEntity());
			} catch (Exception e) {
				BusinessExceptionUtil.threwException(e);
			}
		}
		return bytes;
	}

	public static String encoderURLString(String str) {
		String result = "";
		if (null == str) {
			return "";
		}
		try {
			result = java.net.URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String decoderURLString(String str) {
		String result = "";
		if (null == str) {
			return "";
		}
		try {
			result = java.net.URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	 //发送响应流方法
    public static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
        	fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	

	// 测试jspoup
	// public static void main(String[] args) throws Exception {
	// String url = "";
	// HttpResponse response = get(url);
	// // 获取响应状态码
	// int StatusCode = response.getStatusLine().getStatusCode();
	// // 如果状态响应码为200，则获取html实体内容或者json文件
	// if (StatusCode == 200) {
	// String html = EntityUtils.toString(response.getEntity(), "utf-8");
	// Document doc = Jsoup.parse(html);
	//
	// // 获取html标签中的内容
	// Elements elements =
	// doc.select("ul[class=gl-warp clearfix]").select("li[class=gl-item]");
	// for (Element ele : elements) {
	// String bookID = ele.attr("data-sku");
	// String bookPrice =
	// ele.select("div[class=p-price]").select("strong").select("i").text();
	// String bookName = ele.select("div[class=p-name]").select("em").text();
	// // 创建一个对象，这里可以看出，使用Model的优势，直接进行封装
	//
	// }
	// }
	// EntityUtils.consume(response.getEntity());
	// }

	public static void main(String[] args) throws Exception {
		// 下载一个图片的测试
		String picurl = "http://127.0.0.1/1.jpg";
		FileUtils.write(getBytes(picurl), "E://", "1.jpg");
	}
}
