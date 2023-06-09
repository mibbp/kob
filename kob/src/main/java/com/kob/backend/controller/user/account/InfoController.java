package com.kob.backend.controller.user.account;

import com.kob.backend.service.user.account.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

public class InfoServiceController {
    @Autowired
    private InfoService infoService;

    @GetMapping("/user/account/info/")
    public Map<String, String> getInfo(){
        return infoService.getInfo();
    }
}
