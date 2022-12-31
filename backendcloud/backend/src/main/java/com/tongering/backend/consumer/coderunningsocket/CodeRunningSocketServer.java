package com.tongering.backend.consumer.coderunningsocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongering.backend.consumer.utils.JwtAuthentication;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/code/{token}")
public class CodeRunningSocketServer {
    final public static ConcurrentHashMap<Integer, CodeRunningSocketServer> users = new ConcurrentHashMap<>();
    private Users user;
    private Session session = null;
    private static RestTemplate restTemplate;
    private static UsersMapper usersMapper;
    private final static String addCodeUrl = "http://127.0.0.1:5001/api/code/add/";

    @Autowired
    public void setUsersMapper(UsersMapper usersMapper) {
        CodeRunningSocketServer.usersMapper = usersMapper;
    }

    @Autowired
    public void setRestTemplate (RestTemplate restTemplate) {
        CodeRunningSocketServer.restTemplate = restTemplate;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException{
        this.session = session;
        System.out.println("connected!");

        Integer userId = JwtAuthentication.getUserId(token);
        this.user = usersMapper.selectById(userId);

        if(this.user != null){
            users.put(userId, this);
        } else {
            this.session.close();
        }

        System.out.println(users);
    }

    @OnClose
    public void onClose() {
        System.out.println("disconnected!");
        if(this.user != null){
            users.remove(this.user.getId());
        }
    }

    @OnMessage
    public void onMessage(String message,Session session){
        System.out.println("received message");
        JSONObject data = JSON.parseObject(message);

        String event = data.getString("event");
        String code = data.getString("code");
        Integer typeId = data.getInteger("type_id");

        Map<String, String> commit = new HashMap<>();
        commit.put("code", code);
        commit.put("typeId", typeId.toString());

        if("commit-code".equals(event)){
            commitcode(commit);
        }
    }

    @OnError
    public void onError(Session session, Throwable error){
        error.printStackTrace();
    }

    private void commitcode(Map<String, String> commit){
        System.out.println("commitcode");
        System.out.println(commit);

        String userid = user.getId().toString();
        String code = commit.get("code");
        String typeId = commit.get("typeId");

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", userid);
        data.add("code", code);
        data.add("typeId", typeId);

        System.out.println(userid + code);
        CodeRunningSocketServer.restTemplate.postForObject(addCodeUrl, data, String.class);

    }

    public void sendMessage(String message){
        synchronized (this.session){
            try{
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void receiveresult(Integer userId, String result, Integer errorId){
        JSONObject coderesult = new JSONObject();
        coderesult.put("error_message", "success");
        coderesult.put("result", result);
        coderesult.put("error_id", errorId);
        users.get(userId).sendMessage(coderesult.toJSONString());
    }


}
