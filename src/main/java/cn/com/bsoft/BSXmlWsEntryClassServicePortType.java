package cn.com.bsoft;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.2.5
 * 2019-08-26T13:31:23.801+08:00
 * Generated source version: 3.2.5
 *
 */
@WebService(targetNamespace = "http://www.bsoft.com.cn", name = "BSXmlWsEntryClassServicePortType")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface BSXmlWsEntryClassServicePortType {

    @WebMethod(action = "http://www.bsoft.com.cn/invoke")
    @WebResult(name = "invokeResponse", targetNamespace = "http://www.bsoft.com.cn", partName = "parameters")
    public InvokeResponse invoke(
        @WebParam(partName = "parameters", name = "invokeRequest", targetNamespace = "http://www.bsoft.com.cn")
        InvokeRequest parameters
    );
}
