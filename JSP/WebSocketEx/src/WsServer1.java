import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocketendpoint")
public class WsServer1 {

	@OnOpen
	public void onOpen() {
		System.out.println("Open onnection ...");
	}
	
	@OnClose
	public void onClose() {
		System.out.println("Close onnection ...");
	}
	
	@OnMessage
	public String onMessage(String message) {
		System.out.println("Message from the client: " + message);
		String echoMsg = "Echo from the server : " + message;
		return echoMsg;
	}
	
	@OnError
	public void onError(Throwable e) {
		e.printStackTrace();
	}
}
