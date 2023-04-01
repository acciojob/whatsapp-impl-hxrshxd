package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private Map<String, User> userDb;
    private Map<Integer, Message> messageMap;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.userDb = new HashMap<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String createUser(String name, String mobile) throws Exception{
        // if that user exist in db
        if (userDb.containsKey(mobile)) throw new Exception("User already exists");

        // create new user in db
        User user = new User(name, mobile);
        userDb.put(mobile,user);
        return "SUCCESS";
    }

    public Group groupChat(List<User> users) {
        this.customGroupCount++;

        User user = users.get(0);
        String groupName = "Group " + this.customGroupCount;
        Group group = new Group(groupName, users.size());

        groupUserMap.put(group, users);
        adminMap.put(group, user);
        return group;
    }

    public Group personalChat(List<User> users) {
        User user1 = users.get(0);
        User user2 = users.get(1);

        String groupName = user2.getName();
        Group group = new Group(groupName, 2);

        groupUserMap.put(group, users);
        adminMap.put(group, user1);
        return group;
    }

    public int createMessage(String content) {
        this.messageId++;
        Message message = new Message(this.messageId, content);

        messageMap.put(this.messageId, message);
        return this.messageId;
    }

    public boolean checkGroup(Group group) {
        String groupName = group.getName();

        for (Group g: groupUserMap.keySet()) {
            String gName = g.getName();
            if (gName.equals(groupName)) return true;
        }

        return false;
    }

    public List<User> getUserList(Group group) {
        String groupName = group.getName();

        for (Group g: groupUserMap.keySet()) {
            String gName = g.getName();
            if (gName.equals(groupName)) return groupUserMap.get(g);
        }

        return null;
    }

    public boolean checkSenderInGroup(Group group, User sender) {
        List<User> userList = getUserList(group);

        String senderName = sender.getName();

        for (User user: userList) {
            if (user.getName().equals(senderName)) return true;
        }

        return false;
    }

//    public List<Message> getMessageList(Group group) {
//
//    }

//    public int sendMessage(Message message, User sender, Group group) {
//
//        groupMessageMap.put(group, messageList);
//    }
}
