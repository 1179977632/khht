package com.example.khht.mapper;

import com.example.khht.dto.chat.ChatReturnDTO;
import com.example.khht.dto.chat.ChatSelectInputDTO;
import com.example.khht.dto.entity.chat.Chat;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatMapper {

    int insertChat(ChatReturnDTO chatReturnDTO);

    List<Chat> selectAll();

    List<ChatReturnDTO> selectChat(ChatSelectInputDTO chatSelectInputDTO);
}
