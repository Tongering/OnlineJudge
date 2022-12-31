package com.tongering.backend.service.exercise;

import com.tongering.backend.pojo.SingleCodeRecord;

import java.util.List;

public interface GetRecordService {
    public List<SingleCodeRecord> getRecord(Integer userId);
}
