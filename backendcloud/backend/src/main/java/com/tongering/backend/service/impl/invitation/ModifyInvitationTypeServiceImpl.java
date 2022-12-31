package com.tongering.backend.service.impl.invitation;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongering.backend.mapper.InvitationTypeMapper;
import com.tongering.backend.pojo.InvitationType;
import com.tongering.backend.service.CheckUserService;
import com.tongering.backend.service.invitation.ModifyInvitationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ModifyInvitationTypeServiceImpl implements ModifyInvitationTypeService {
    @Autowired
    private InvitationTypeMapper invitationTypeMapper;
    @Autowired
    private CheckUserService checkUserService;
    @Override
    public Map<String, String> modify(String typeName, Integer id) {
        Map<String, String> result = new HashMap<>();
        try{
            UpdateWrapper<InvitationType> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id)
                    .eq("create_id", checkUserService.check().getId())
                    .set("typename", typeName);
            invitationTypeMapper.update(null, updateWrapper);
            result.put("error_message", "success");
            return result;
        } catch (Exception e){
            result.put("error_message", "error");
            return result;
        }
    }
}
