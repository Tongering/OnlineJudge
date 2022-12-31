package com.tongering.backend.service.impl.admin;

import com.tongering.backend.mapper.UserProfileMapper;
import com.tongering.backend.mapper.UserTerminalMapper;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.TerminalAndUserAndProfile;
import com.tongering.backend.pojo.UserProfile;
import com.tongering.backend.pojo.UserTerminal;
import com.tongering.backend.service.admin.GetTerminalListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetTerminalListServiceImpl implements GetTerminalListService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserProfileMapper profileMapper;
    @Autowired
    private UserTerminalMapper terminalMapper;

    @Override
    public List<TerminalAndUserAndProfile> getList() {
        List<TerminalAndUserAndProfile> result = new ArrayList<>();
        List<UserTerminal> terminals = terminalMapper.selectList(null);

        for(UserTerminal i : terminals){
            result.add(new TerminalAndUserAndProfile(i.getUserId(), i.getTerminalIp(), i.getTerminalPort(),
                    i.getTerminalUsername(), i.getTerminalPassword(), profileMapper.selectById(i.getUserId()).getPhoto(),
                    usersMapper.selectById(i.getUserId()).getUsername()));
        }


        return result;
    }
}
