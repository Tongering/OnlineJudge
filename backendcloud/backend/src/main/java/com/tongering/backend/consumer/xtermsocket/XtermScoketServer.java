package com.tongering.backend.consumer.xtermsocket;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jcraft.jsch.JSchException;
import com.tongering.backend.consumer.utils.JwtAuthentication;
import com.tongering.backend.mapper.UserTerminalMapper;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.UserTerminal;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.pojo.WebSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.websocket.*;

import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/xterm/{token}")
public class XtermScoketServer {
    final public static ConcurrentHashMap<Integer, XtermScoketServer> users = new ConcurrentHashMap<>();
    private Users user;
    private Session session = null;
    private static UsersMapper usersMapper;
    private static UserTerminalMapper userTerminalMapper;
    private static UserTerminal userTerminal;
    SSHConnection sshConnection = new SSHConnection();

    @Autowired
    public void setUsersMapper(UsersMapper usersMapper) {
        XtermScoketServer.usersMapper = usersMapper;
    }
    @Autowired
    public void setUserTerminalMapper(UserTerminalMapper userTerminalMapper){
            XtermScoketServer.userTerminalMapper = userTerminalMapper;
    }
    @OnOpen
    public void onOpen (Session session, @PathParam("token") String token) throws IOException {
        this.session = session;
        Integer userId = JwtAuthentication.getUserId(token);
        this.user = usersMapper.selectById(userId);

        System.out.println(this.user.getUsername() + "正在访问terminal");

        QueryWrapper<UserTerminal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        this.userTerminal = userTerminalMapper.selectOne(queryWrapper);

        if(this.user != null && userTerminal != null){
            users.put(userId, this);
        } else {
            sendMessage("您未授权使用Terminal",session);
            this.session.close();
        }
        System.out.println(users);
    }
    @OnClose
    public void onClose(){
        System.out.println("disconnected!");
        if(this.user != null){
            users.remove(this.user.getId());
            if(userTerminal != null){
                SSHConnection.close(this.user.getId());
            }
            System.out.println(this.user.getUsername() + "退出terminal");
        }
        SSHConnection.show();
    }
    @OnMessage
    public void onMessage(String message, Session session) throws JSchException, IOException {
        WebSession webSession = new WebSession();
        webSession.setSession(session);
        SSHConnection.show();
        if(message.equals("init") && userTerminal != null){
            System.out.println("init ing...");
            sshConnection.SSHConnectionInit(this.user.getId(),"l", webSession, userTerminal);
        } else if (userTerminal == null){
            sendMessage("您未授权使用Terminal",session);
            this.session.close();
        } else {
            System.out.println("command ing...");
            sshConnection.SSHCommand(this.user.getId(), message, webSession, userTerminal);
        }

    }
    @OnError
    public void onError(Session session, Throwable error){
        error.printStackTrace();
    }
    public void sendMessage(String message, Session session){
        synchronized (session){
            try{
                session.getBasicRemote().sendText(message);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
