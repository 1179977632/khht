package com.example.khht.service;

import com.alibaba.fastjson.JSONObject;
import com.example.khht.dto.chat.ChatInputDTO;
import com.example.khht.dto.chat.ChatReturnDTO;
import com.example.khht.dto.entity.chat.Chat;
import com.example.khht.service.chat.ChatService;
import com.example.khht.service.user.UserService;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket/{chatInfo}")
public class WebSocketServer {

    private Session session;
    private String sort;
    private String sendId;
    private String receiveId;
    private String schoolId;
    private String courseId;
    private static CopyOnWriteArraySet<WebSocketServer> websocketlist;
    private static HashMap<String,HashMap> groupMap = new HashMap<>();
    private static HashMap<String,CopyOnWriteArraySet> personal = new HashMap<>();
    public static ChatService chatService;
    public static UserService userService;

    /**
     * 建立连接的时候调用
     */
    @OnOpen
    public  void onOpen(@PathParam(value = "chatInfo") String chatInfo, Session session){
        this.session = session;
        String[] split = chatInfo.split("_");
        this.sort = split[0];
        this.sendId = split[1];
        this.receiveId = split[2];
        this.schoolId = split[3];
        this.courseId = split[4];
        if (sort.equals("group")){
            intoSchool();
        }else {
        }
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接时调用
     */
    @OnClose
    public void onClose(){
        if(sort.equals("group")){
            websocketlist = ((HashMap<String,CopyOnWriteArraySet>)(groupMap.get(schoolId))).get(courseId);
            websocketlist.remove(this);
            System.out.println("当前"+this.courseId+"在线人数："+websocketlist.size());
        }else {
        }
    }

    /**
     * 接收到客户端的信息
     * @param message
     */
    @OnMessage
    public  void onMessage(String message){
        ChatInputDTO chatInputDTO = JSONObject.parseObject(message,ChatInputDTO.class);
        ChatReturnDTO chatReturnDTO= setChat(chatInputDTO);
        chatService.insertChat(chatReturnDTO);
        String returnMessage = JSONObject.toJSONString(chatReturnDTO);
        if (sort.equals("group")){
            websocketlist = ((HashMap<String,CopyOnWriteArraySet>)(groupMap.get(schoolId))).get(courseId);
            for (WebSocketServer item : websocketlist) {
                try {
                    item.sendMessage(returnMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
        }
    }

    /**
     * 发生错误时调用
     * */
    @OnError
    public void onError(Throwable error) {
        //System.out.println("发生错误");
        //error.printStackTrace();
    }

    /**
     * 发送单人信息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String schoolcourse,String message) throws IOException {
        /*if(sortMap.containsKey(schoolCourse)){
            for (WebSocketServer item : (CopyOnWriteArraySet<WebSocketServer>)(sortMap.get(schoolCourse))) {
                try {
                    item.sendMessage(message);
                } catch (IOException e) {
                    continue;
                }
            }
        }*/
        /*for (WebSocketServer item : websocketlist) {
            if(item.getSchoolCourse().equals(schoolcourse)){
                try {
                    item.sendMessage(message);
                } catch (IOException e) {
                    continue;
                }
            }
        }*/
    }

    public void intoSchool(){
        if(!groupMap.containsKey(schoolId)) {
            HashMap<String,CopyOnWriteArraySet> group = new HashMap<>();
            groupMap.put(schoolId,group);
        }
        intoCourse(groupMap.get(schoolId));
    }

    public void intoCourse(HashMap<String,CopyOnWriteArraySet> group){
        if(!group.containsKey(courseId)){
            websocketlist = new CopyOnWriteArraySet<WebSocketServer>();
            group.put(courseId,websocketlist);
        }
        group.get(courseId).add(this);
        System.out.println("当前"+schoolId+"课程"+courseId+"在线人数："+group.get(courseId).size());


        /*if(sortMap.containsKey(courseName)) {
            sortMap.get(courseName).add(this);
            System.out.println("当前"+courseName+"在线人数："+sortMap.get(courseName).size());
            *//*if(!sortMap.isEmpty()){
                for(WebSocketServer o:(CopyOnWriteArraySet<WebSocketServer>)sortMap.get("大厅")){
                    if(o.userEmail.equals(userEmail)){
                        sortMap.get(courseName).add(o);
                        sortMap.get("大厅").remove(o);
                        System.out.println("当前"+courseName+"在线人数："+sortMap.get(courseName).size());
                        System.out.println("当前大厅在线人数："+sortMap.get("大厅").size());
                        break;
                    }
                }
            }*//*
        }else {
            websocketlist = new CopyOnWriteArraySet<WebSocketServer>();
            websocketlist.add(this);
            sortMap.put(courseName,websocketlist);
            System.out.println("当前"+courseName+"在线人数："+sortMap.get(courseName).size());
            *//*if(!sortMap.isEmpty()){
                for(WebSocketServer o:(CopyOnWriteArraySet<WebSocketServer>)sortMap.get("大厅")){
                    if(o.userEmail.equals(userEmail)){
                        websocketlist = new CopyOnWriteArraySet<WebSocketServer>();
                        websocketlist.add(o);
                        sortMap.put(courseName,websocketlist);
                        sortMap.get("大厅").remove(o);
                        System.out.println("当前"+courseName+"在线人数："+sortMap.get(courseName).size());
                        System.out.println("当前大厅在线人数："+sortMap.get("大厅").size());
                        break;
                    }
                }
            }*//*
        }*/
    }

    public ChatReturnDTO setChat(ChatInputDTO chatInputDTO){
        ChatReturnDTO chatReturnDTO = new ChatReturnDTO();
        chatReturnDTO.setSendId(Integer.parseInt(sendId));
        chatReturnDTO.setSendName(userService.selectUserNameByUserId(Integer.parseInt(sendId)));
        chatReturnDTO.setReceiveId(Integer.parseInt(receiveId));
        chatReturnDTO.setSchoolId(Integer.parseInt(schoolId));
        chatReturnDTO.setCourseId(Integer.parseInt(courseId));
        chatReturnDTO.setChatContent(chatInputDTO.getChatContent());
        if(chatInputDTO.getFileId()==null){
            chatReturnDTO.setFileId(null);
        }else {
            chatReturnDTO.setFileId(chatInputDTO.getFileId());
        }
        chatReturnDTO.setCreateTime(chatInputDTO.getCreateTime());
        return chatReturnDTO;
    }

}