package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.bot.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddServiceImpl implements AddService {

    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> add(Map<String, String> data) {
        System.out.println("1111");
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        String tittle = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        Map<String, String> map = new HashMap<>();
        if(tittle == null || tittle.length() == 0){
            map.put("error_message", "tittle is null");
            return map;
        }

        if(tittle.length() > 100){
            map.put("error_message", "tittle is too long");
            return map;
        }

        if(description == null || description.length() == 0){
            description = "这个用户很懒，什么也没写~";
        }

        if(description.length()  > 300){
            map.put("error_message", "description is too long");
            return map;
        }

        if(content == null || content.length() == 0){
            map.put("error_message", "content is null");
            return map;
        }

        if(content.length() > 10000){
            map.put("error_message", "content is too long");
            return map;
        }
        Date now = new Date();
        Bot bot = new Bot(null, user.getId(), tittle, description, content, 1500, now, now);

        botMapper.insert(bot);
        map.put("error_message", "success");

        return map;
    }


}
