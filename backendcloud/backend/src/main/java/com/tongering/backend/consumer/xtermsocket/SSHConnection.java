package com.tongering.backend.consumer.xtermsocket;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.tongering.backend.pojo.UserTerminal;

import com.jcraft.jsch.Session;
import com.tongering.backend.pojo.WebSession;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SSHConnection {
    public static Map<Integer, Channel> channels = new HashMap<>();
    public static Map<Integer, Session> sessions = new HashMap<>();
    private ExecutorService executorService = Executors.newCachedThreadPool();// 线程池
    private static UserTerminal userTerminal;
    public Channel Init(Integer userId, WebSession webSession) throws JSchException {
        if(channels.get(userId) != null ){
            return channels.get(userId);
        }

        Session terminalSession = null;
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");

        JSch jSch = new JSch();

        terminalSession = jSch.getSession(userTerminal.getTerminalUsername(), userTerminal.getTerminalIp(), userTerminal.getTerminalPort());
        terminalSession.setConfig(config);
        terminalSession.setPassword(userTerminal.getTerminalPassword());
        terminalSession.connect(30000);

        Channel channel = terminalSession.openChannel("shell");

        channel.connect(3000);

        System.out.println(channel);
        channels.put(userId, channel);
        sessions.put(userId, terminalSession);
        return channel;
    }// 初始化链接或执行操作的共同代码
    public void SSHConnectionInit(Integer userId, String command, WebSession webSession, UserTerminal userTerminal) throws JSchException, IOException {
        this.userTerminal = userTerminal;
        Channel channel = Init(userId, webSession);

        Session terminalSession = sessions.get(userId);;

        executorService.execute(new Runnable() { // 创建线程，维持链接在线
            @Override
            public void run() {
                try {
                    connect(channel, terminalSession, webSession);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }// 初始化链接并创建线程

    public void SSHCommand(Integer userId, String command, WebSession webSession, UserTerminal userTerminal) throws JSchException, IOException {
        this.userTerminal = userTerminal;
        Channel channel = Init(userId, webSession);
        Session terminalSession = sessions.get(userId);
        transToSSH(channel, command);
    }// 链接执行操作

    public void connect (Channel channel, Session terminalSession, WebSession webSession) throws IOException {
        transToSSH(channel, "\r");
        InputStream inputStream = channel.getInputStream();
        try {
            byte[] buffer = new byte[1024];
            int i = 0;
            while((i = inputStream.read(buffer)) != -1){
                System.out.println("------执行buffer-------");
                sendMessage(Arrays.copyOfRange(buffer, 0, i), webSession);
            }
        } finally {
            System.out.println("terminal disconnect");
            terminalSession.disconnect();
            channel.disconnect();
            if(inputStream != null){
                inputStream.close();
            }
        }
    }// 初始化链接

    public static void close(Integer userId){
        Channel channel = channels.get(userId);
        Session session = sessions.get(userId);
        channels.remove(userId);
        sessions.remove(userId);
//        channel.disconnect();
    }// 移除User

    public void transToSSH(Channel channel, String command) throws IOException {
        if (channel != null) {
            OutputStream outputStream = channel.getOutputStream();
            outputStream.write(command.getBytes());
            outputStream.flush();
        }
    }// 发送信息给服务器Terminal

    public void sendMessage(byte[] message, WebSession webSession){
        synchronized (webSession.getSession()){
            try{
                String result = new TextMessage(message).getPayload();
                webSession.getSession().getBasicRemote().sendText(result);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }// 发送信息给前端

    public static void show(){
        System.out.println("channels中有：" + channels.size());
        System.out.println(channels);
        System.out.println("sessions中有：" + sessions.size());
        System.out.println(sessions);
    }// 输出当前Map中所存在的Terminal连接数
}
