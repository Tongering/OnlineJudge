package com.tongering.backend.service.impl.exercise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.CodeRecordMapper;
import com.tongering.backend.mapper.ExerciseMapper;
import com.tongering.backend.mapper.ExerciseTypeMapper;
import com.tongering.backend.pojo.CodeRecord;
import com.tongering.backend.pojo.Exercise;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.exercise.GetExerciseCountBaseService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GetExerciseCountBaseServiceImpl implements GetExerciseCountBaseService {
    @Autowired
    private ExerciseMapper mapper;
    @Autowired
    private CodeRecordMapper recordMapper;
    @Autowired
    private ExerciseMapper exerciseMapper;
    @Override
    public Map<String, Integer> getCount() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();
        Integer userId = user.getId();

        QueryWrapper<CodeRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<CodeRecord> lists = recordMapper.selectList(queryWrapper);

        Integer userPrimary = 0;
        Integer userIntermediate = 0;
        Integer userSenior = 0;



        Set<CodeRecord> userSet = new HashSet<>(lists);
        List<CodeRecord> list = new ArrayList<>(userSet);
        for(CodeRecord i : list){
            Integer type = exerciseMapper.selectById(i.getExerciseId()).getTypeId();
            if(type.equals(1)){
                userPrimary ++;
            } else if (type.equals(2)){
                userIntermediate ++;
            } else if (type.equals(3)){
                userSenior ++;
            }
        }

        Map<String ,Integer> result = new HashMap<>();
        QueryWrapper<Exercise> queryPrimary = new QueryWrapper<>();
        QueryWrapper<Exercise> queryIntermediate = new QueryWrapper<>();
        QueryWrapper<Exercise> querySenior = new QueryWrapper<>();
        queryPrimary.eq("type_id", 1);
        queryIntermediate.eq("type_id", 2);
        querySenior.eq("type_id", 3);
        Integer primary = Math.toIntExact(mapper.selectCount(queryPrimary));
        Integer intermediate = Math.toIntExact(mapper.selectCount(queryIntermediate));
        Integer senior = Math.toIntExact(mapper.selectCount(querySenior));

        if(primary.equals(0)){
            result.put("primary", 0);
        } else{
            result.put("primary", 100 * userPrimary/primary);
        }
        if(intermediate.equals(0)){
            result.put("intermediate", 0);
        } else {
            result.put("intermediate", 100 * userIntermediate/intermediate);
        }
        if(senior.equals(0)){
            result.put("senior", 0);
        } else {
            result.put("senior", 100 * userSenior/senior);
        }

        return result;
    }
}
