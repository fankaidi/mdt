package co.kensure.websocket;

/**
 * 如果这里报错，需要引入tomcat下的websocket包
 */
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * websocket框架类，用户和客户端建立时时通讯
 *
 * @author fankd
 */
@ServerEndpoint("/ktlweb")
public class JSWebSocket {

	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static CopyOnWriteArraySet<JSWebSocket> webSocketSet = new CopyOnWriteArraySet<JSWebSocket>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	/**
	 * 新的WebSocket请求开启
	 */
	@OnOpen
	public void onOpen(Session session) {
		try {
			this.session = session;
			webSocketSet.add(this);
			session.getBasicRemote().sendText("onOpen");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * WebSocket请求关闭
	 */
	@OnClose
	public void onClose() {
		try {
			webSocketSet.remove(this);
			System.out.println("onClose");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@OnError
	public void onError(Throwable thr) {
		System.out.println("onError");	
	}

	
    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message+webSocketSet.size());

       
    }

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * 
	 * @param message
	 * @throws IOException
	 */
	public  static void sendMessage(String message) {
		for (JSWebSocket ws : webSocketSet) {
			try {
				ws.session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// this.session.getAsyncRemote().sendText(message);
	}
}
