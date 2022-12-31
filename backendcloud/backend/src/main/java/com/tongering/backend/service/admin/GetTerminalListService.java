package com.tongering.backend.service.admin;

import com.tongering.backend.pojo.TerminalAndUserAndProfile;

import java.util.List;

public interface GetTerminalListService {
    public List<TerminalAndUserAndProfile> getList();
}
