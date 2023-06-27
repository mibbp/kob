package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.bot.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private BotMapper botMapper;


    @Override
    public Map<String, String> update(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        int bot_id = Integer.parseInt(data.get("bot_id"));

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
        Bot bot = botMapper.selectById(bot_id);
        if(bot == null){
            map.put("error_message", "bot is not exist");
            return map;
        }

        if(!bot.getUserId().equals(user.getId())){
            map.put("error_message", "bot is not belong to you");
            return map;
        }

        Bot new_bot = new Bot(
                bot.getId(),
                user.getId(),
                tittle,
                description,
                content,
                bot.getRating(),
                bot.getCreatetime(),
                new Date()
        );

        botMapper.updateById(new_bot);
        map.put("success_message", "update success");

        return map;
    }
}
