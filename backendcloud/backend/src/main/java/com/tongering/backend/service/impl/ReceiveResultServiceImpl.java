package com.tongering.backend.service.impl;

import com.tongering.backend.consumer.coderunningsocket.CodeRunningSocketServer;
import com.tongering.backend.service.ReceiveResultService;
import org.springframework.stereotype.Service;

@Service
public class ReceiveResultServiceImpl implements ReceiveResultService {
    @Override
    public String receiveresult(Integer userId, String result, Integer errorId) {
        System.out.println(userId);
        System.out.println(result);
        System.out.println(errorId);

        if(CodeRunningSocketServer.users.get(userId) != null){
            CodeRunningSocketServer.receiveresult(userId, result, errorId);
        }

        return null;
    }
}
