package co.kensure.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import co.kensure.frame.ResultType;
import co.kensure.thread.LocalThreadUtils;

import com.alibaba.fastjson.JSONObject;


/**
 * com.kensure.exception
 *
 * @author Yingjie yao
 */
public class ExceptionResolver implements HandlerExceptionResolver{

    private final static Logger LOGGER = Logger.getLogger( ExceptionResolver.class );

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        LOGGER.error( e );
        LOGGER.error( o );
        try {
            httpServletResponse.setContentType( "application/json;charset=UTF-8" );
            PrintWriter writer = httpServletResponse.getWriter();
            JSONObject map = new JSONObject();
            map.put( "type", ResultType.ERROR );
            if( e instanceof BusinessException ){       
                map.put( "message", e.getMessage() );
            }else{
            	map.put( "message", "参数错误！！！" );
            }
          
            writer.write( map.toJSONString() );
            writer.flush();
            writer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
            LOGGER.error( e1 );
        }finally{
        	LocalThreadUtils.clear();
        }
        return null;
    }
}