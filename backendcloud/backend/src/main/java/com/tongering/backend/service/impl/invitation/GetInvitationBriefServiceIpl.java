package com.tongering.backend.service.impl.invitation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.InvitationMapper;
import com.tongering.backend.mapper.InvitationTypeMapper;
import com.tongering.backend.mapper.UserProfileMapper;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.*;
import com.tongering.backend.service.invitation.GetInvitationBriefService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Service
public class GetInvitationBriefServiceIpl implements GetInvitationBriefService {
    @Autowired
    private InvitationMapper invitationMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserProfileMapper userProfileMapper;
    @Autowired
    private InvitationTypeMapper invitationTypeMapper;

    public List<MultiInvitationBrief> getBrief(Map<String, String> data){
        Integer typeId = Integer.parseInt(data.get("typeId"));
        String isStrain = data.get("isStrain");
        Integer contentBrief = 100;

        QueryWrapper<Invitation> queryWrapper = new QueryWrapper<>();

        if(typeId.equals(0)){ //没有类型限制
            queryWrapper.orderByDesc("create_time"); //限制个数；.last("limit 10")
        } else { // 指定类型
            queryWrapper.eq("type_id", typeId).orderByDesc("create_time"); //限制个数；.last("limit 10")
        }
        List<Invitation> invitations = invitationMapper.selectList(queryWrapper);
        List<MultiInvitationBrief> result = new ArrayList<>();

        if(isStrain.equals("yes")){ // 获取部分内容
            for(Invitation i : invitations){
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
        } else if (isStrain.equals("none")) { // 获取全文
            for(Invitation i : invitations){
                InvitationType invitationType = invitationTypeMapper.selectById(i.getTypeId());
                UserProfile userProfile = userProfileMapper.selectById(i.getCreateId());
                Users users = usersMapper.selectById(i.getCreateId());

                MultiInvitationBrief multiInvitationBrief = new MultiInvitationBrief(
                        i.getId(), users.getUsername(), userProfile.getPhoto(),
                        i.getCreateId(), invitationType.getTypename(), i.getTitle(),
                        i.getContent(), i.getCreateTime());
                result.add(multiInvitationBrief);
            }
        } else if (isStrain.equals("like")){
            Integer count = invitations.size();
            if(count > 3){
                Random r = new Random();
                int[] arr = new int[3];
                arr[0] = -1;
                arr[1] = -2;
                arr[2] = -3;
                int index = 0;
                int ttt = 0;
                while (index < 3) {
                    if(ttt >= 100) break;
                    ttt ++;
                    int a = r.nextInt(count );
                    // 判断arr有没有这个a
                    // 如果没有这个num，就放进去。
                    int k = 0;
                    for(int iii = 0; iii < 3; iii ++){
                        if(arr[iii] == a){
                            k = 1;
                            break;
                        }
                    }
                    if(k == 0){
                        arr[index++] = a;
                    }
                }

                if(ttt >= 100){
                    arr[0] = 1;
                    arr[1] = 2;
                    arr[2] = 3;
                }
                for(int o = 0; o < 3; o++ ){
                    Invitation i = invitations.get(arr[o]);
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

            } else {
                for(Invitation i : invitations){
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
            }

        }

        return result;
    }
}
