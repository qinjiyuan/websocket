package top.mixedinfos.websocket.controller;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value="/websocket")
@Component
public class DemoWebSocket {
    private static int onlineCount = 0 ;
    private static CopyOnWriteArraySet<DemoWebSocket> demoWebSocket = new CopyOnWriteArraySet<>();
    private Session session;
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        demoWebSocket.add(this);
        onlineCount++;
        try{
            this.sendMessage("当前在线人数为"+onlineCount);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    @OnClose
    public void onClose() {
        demoWebSocket.remove(this);  //从set中删除
        onlineCount--;        //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + onlineCount);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);

        //群发消息
        for (DemoWebSocket item : demoWebSocket) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


}
