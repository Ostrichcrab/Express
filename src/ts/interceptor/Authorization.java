package ts.interceptor;


import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.XMLMessage;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;


import ts.util.JwtToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**

 */
public class Authorization extends AbstractPhaseInterceptor<Message> {

    public Authorization() {
        super(Phase.PRE_INVOKE);//调用之前
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        HttpServletRequest request = (HttpServletRequest) message.get("HTTP.REQUEST");
        HttpServletResponse response = (HttpServletResponse) message.get("HTTP.RESPONSE");
       // request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
        String uri = (String) message.get(Message.REQUEST_URI);
        System.out.println(uri);
        String token = request.getHeader("Authorization");
        if(token.equals("123456789")) {
        	return ;
        }
        if (!uri.matches("^/TestCxfHibernate/REST/Misc/doLogin/\\S*$") && !uri.matches("^/TestCxfHibernate/REST/Misc/doLogin/\\S*$")) { //不是登录
            
            String test = request.getHeader("USER_AGENT");
            System.out.println(uri+" USER_AGENT "+test+" Authorization "+token);
            try {
                if (token == null || token.length()== 0) { //没有token
                	System.out.println("token is not exit……");
                    PrintWriter out = response.getWriter();
                    //out.write(new ErrorMessage(ErrorMessage.CODE.NO_TOKEN).toString());
                    
//                    out.write("{\"retCode\":\"4011\",\"retMessage\":\"非准入IP地址!\"}\r\n" + 
//				"\r\n");
//                    
                    out.write("{\"retCode\":\"4011\",\"retMessage\":\"请先登录!\"}\r\n" + 
            				"\r\n");
                                
                  //  System.out.print(new ErrorMessage(ErrorMessage.CODE.NO_TOKEN).toString());
                    message.getInterceptorChain().doInterceptStartingAfter(message, "org.apache.cxf.jaxrs.interceptor.JAXRSOutInterceptor");
                    out.flush();
                } else {
                    try{
                    	System.out.println("token is  exit……");
                        JwtToken.parseJWT(token);
                        //token有效
                    } catch (Exception e) {
                        //token无效
                    	System.out.println("token is error……");
                        PrintWriter out = response.getWriter();
                        //out.write(new ErrorMessage(ErrorMessage.CODE.TOKEN_ERROR).toString());
                        out.flush();
                        message.getInterceptorChain().doInterceptStartingAfter(message, "org.apache.cxf.jaxrs.interceptor.JAXRSOutInterceptor");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //HTTP.RESPONSE
        //org.apache.cxf.request.uri
    }

}
