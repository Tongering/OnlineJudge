package com.tongering.backend.service.impl.invitation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.InvitationTypeMapper;
import com.tongering.backend.pojo.InvitationType;
import com.tongering.backend.service.CheckIsTeacherService;
import com.tongering.backend.service.CheckUserService;
import com.tongering.backend.service.admin.CheckAdminService;
import com.tongering.backend.service.invitation.GetInvitationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GetInvitationTypeServiceImpl implements GetInvitationTypeService {
    @Autowired
    private InvitationTypeMapper mapper;
    @Autowired
    private CheckUserService checkUserService;
    @Autowired
    private CheckIsTeacherService checkIsTeacherService;
    @Autowired
    private CheckAdminService checkAdminService;
    @Override
    public List<InvitationType> gettype() {
        if(checkAdminService.check().get("error_message").equals("admin_true")){
            return mapper.selectList(null);
        }
        if(checkIsTeacherService.check().get("error_message").equals("teacher_true")){
            QueryWrapper<InvitationType> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("create_id", checkUserService.check().getId());
            return mapper.selectList(queryWrapper);
        }
        return mapper.selectList(null);
    }
}
