package com.tongering.backend.service.impl.invitation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.InvitationMapper;
import com.tongering.backend.mapper.InvitationTypeMapper;
import com.tongering.backend.mapper.UserProfileMapper;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.*;
import com.tongering.backend.service.invitation.SearchInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchInvitationServiceImpl implements SearchInvitationService {
    @Autowired
    private InvitationMapper invitationMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserProfileMapper userProfileMapper;
    @Autowired
    private InvitationTypeMapper invitationTypeMapper;
    @Override
    public List<MultiInvitationBrief> search(Map<String, String> data) {
        Integer contentBrief = 100;
        String search = data.get("search");
        QueryWrapper<Invitation> titleQueryWrapper = new QueryWrapper<>();
        titleQueryWrapper.like("title", search).orderByDesc("create_time");
        QueryWrapper<Invitation> contentQueryWrapper = new QueryWrapper<>();
        contentQueryWrapper.like("content", search).orderByDesc("create_time");
        List<Invitation> rawTitles = invitationMapper.selectList(titleQueryWrapper);
        List<Invitation> rawContents = invitationMapper.selectList(contentQueryWrapper);

        List<MultiInvitationBrief> result = new ArrayList<>();
        List<Invitation> titles = new ArrayList<>();
        List<Invitation> contents = new ArrayList<>();

        if(rawTitles.size() + rawContents.size() > 10){
            if(rawTitles.size() < 5){
                Integer countTitle = rawTitles.size();
                titles = rawTitles;
                contents = rawContents.subList(0, countTitle);
            } else {
                Integer countContent = rawContents.size();
                titles = rawTitles.subList(0, countContent);
                contents = rawContents;
            }
        } else {
            titles = rawTitles;
            contents = rawContents;
        }
        List<Integer> dup = new ArrayList<>();
        for(Invitation i : titles){
            for(Invitation j :contents){
                if (i.getId().equals(j.getId())){
                    dup.add(i.getId());
                }
            }
        }
        for(Invitation i : titles){
            InvitationType invitationType = invitationTypeMapper.selectById(i.getTypeId());
            UserProfile userProfile = userProfileMapper.selectById(i.getCreateId());
            Users users = usersMapper.selectById(i.getCreateId());

            Integer temp = contentBrief;
            if(i.getContent().length() <= contentBrief){
                temp = i.getContent().length();
            } // 获取文本长度并预处理
            MultiInvitationBrief multiInvitationBrief = new MultiInvitationBrief(
                    i.getId(), users.getUsername(), userProfile.getPhoto(),
                    i.getCreateId(), invitationType.getTypename(), i.getTitle(),
                    i.getContent().substring(0,temp), i.getCreateTime());
            result.add(multiInvitationBrief);
        }
        for(Invitation i : contents){
            int k = 0;
            for(Integer j : dup ){
                if(j.equals(i.getId())){
                    k = 1;
                    break;
                }
            }
            if(k == 1){
                break;
            }
            InvitationType invitationType = invitationTypeMapper.selectById(i.getTypeId());
            UserProfile userProfile = userProfileMapper.selectById(i.getCreateId());
            Users users = usersMapper.selectById(i.getCreateId());

            Integer temp = contentBrief;
            if(i.getContent().length() <= contentBrief){
                temp = i.getContent().length();
            } // 获取文本长度并预处理
            MultiInvitationBrief multiInvitationBrief = new MultiInvitationBrief(
                    i.getId(), users.getUsername(), userProfile.getPhoto(),
                    i.getCreateId(), invitationType.getTypename(), i.getTitle(),
                    i.getContent().substring(0,temp), i.getCreateTime());
            result.add(multiInvitationBrief);
        }


        return result;
    }
}
