package api;

import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class WSClient {
	String responseMessage = "";

	public String connectWSClient(String uri, String requestMessage) {
		
		/*try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();			
			Session userSession = container.connectToServer(this, URI.create(uri));
			userSession.getBasicRemote().sendText(message);
			System.out.println(userSession.getBasicRemote().getSendWriter().toString());
			userSession.close();		
		}catch(Exception e) {
			e.printStackTrace();
		}	*/
		try {
            // open websocket
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI(uri));

            // add listener
            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {                	
                	responseMessage = message;
                	//System.out.println(message);
                	//System.out.println("------------");
                	//System.out.println(responseMessage);
                }
            });

            // send message to websocket
            clientEndPoint.sendMessage(requestMessage);

            // wait 5 seconds for messages from websocket
            Thread.sleep(5000);

        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
		
		return responseMessage;		
	}	
    
}
