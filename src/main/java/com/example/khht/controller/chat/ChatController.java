package com.example.khht.controller.chat;

import com.alibaba.fastjson.JSONObject;
import com.example.khht.dto.chat.ChatReturnDTO;
import com.example.khht.dto.chat.ChatSelectInputDTO;
import com.example.khht.dto.entity.JSONResult;
import com.example.khht.service.chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/chat/*")
public class ChatController {

    @SuppressWarnings("all")
    @Autowired
    ChatService chatService;

    @PostMapping("record")
    public JSONResult selectRecord(@RequestBody JSONObject jsonObject) {
        JSONResult jsonResult = new JSONResult();
        ChatSelectInputDTO chatSelectInputDTO = JSONObject.parseObject(jsonObject.toJSONString(),ChatSelectInputDTO.class);
        List<ChatReturnDTO> list = chatService.selectChat(chatSelectInputDTO);
        jsonResult.setData(list);
        return jsonResult;
    }
}
