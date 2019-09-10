package com.example.khht.service.chat;

import com.example.khht.dto.chat.ChatReturnDTO;
import com.example.khht.dto.chat.ChatSelectInputDTO;
import com.example.khht.dto.entity.chat.Chat;
import com.example.khht.mapper.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    ChatMapper chatMapper;

    public int insertChat(ChatReturnDTO chatReturnDTO){
        return chatMapper.insertChat(chatReturnDTO);
    }

    public List<Chat> selectAll(){
        return chatMapper.selectAll();
    }

    public List<ChatReturnDTO> selectChat(ChatSelectInputDTO chatSelectInputDTO){
        return chatMapper.selectChat(chatSelectInputDTO);
    }
}
