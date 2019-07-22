package co.kensure.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.kensure.thread.LocalThreadUtils;
import co.kensure.thread.Session;

/**
 * @author fankaidi
 */
public class KettleFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		String url = req.getRequestURI();
		
		initSession(req);

		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		filterChain.doFilter(servletRequest, servletResponse);
		LocalThreadUtils.clear();
	}

	/**
	 * 设置本地会话变量
	 * 
	 * @param req
	 */
	private void initSession(HttpServletRequest req) {
		LocalThreadUtils.clear();
		Session se = new Session(req);
		LocalThreadUtils.putSession(se);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
}
