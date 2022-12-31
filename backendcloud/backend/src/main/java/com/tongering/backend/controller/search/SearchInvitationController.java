package com.tongering.backend.controller.search;

import com.tongering.backend.pojo.MultiInvitationBrief;
import com.tongering.backend.service.invitation.SearchInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SearchInvitationController {
    @Autowired
    private SearchInvitationService searchInvitationService;
    @PostMapping("/api/invitation/search/")
    public List<MultiInvitationBrief> search (@RequestParam Map<String, String> data){
        return searchInvitationService.search(data);
    }

}
