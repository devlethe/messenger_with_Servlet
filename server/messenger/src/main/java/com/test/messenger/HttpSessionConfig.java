package com.test.messenger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
public class HttpSessionConfig extends ServerEndpointConfig.Configurator {
	@Override

	public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
	       try{
	    	   HttpSession session = (HttpSession) request.getHttpSession();
	    	   ServletContext ctx = session.getServletContext();

	    	   config.getUserProperties().put(HttpSession.class.getName(), session);
	    	   config.getUserProperties().put(ServletContext.class.getName(), ctx);
	       }catch(Exception e) {
	    	   
	       }
	   }
}
