package com.tongering.backend.service.impl.invitation;

import com.tongering.backend.mapper.InvitationTypeMapper;
import com.tongering.backend.pojo.InvitationType;
import com.tongering.backend.service.CheckUserService;
import com.tongering.backend.service.invitation.AddInvitationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddInvitationTypeServiceImpl implements AddInvitationTypeService {
    @Autowired
    private InvitationTypeMapper invitationTypeMapper;
    @Autowired
    private CheckUserService checkUserService;
    @Override
    public Map<String, String> add(String typeName) {
        Map<String, String> result = new HashMap<>();

        try{
            invitationTypeMapper.insert(new InvitationType(null,checkUserService.check().getId(), typeName, null));
            result.put("error_message", "success");
            return result;
        } catch (Exception e){
            result.put("error_message", "error");
            return result;
        }
    }
}
