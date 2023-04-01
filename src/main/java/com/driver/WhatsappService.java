package com.driver;

import java.util.List;

public class WhatsappService {

    WhatsappRepository whatsappRepository = new WhatsappRepository();

    public String createUser(String name, String mobile) throws Exception {
        return whatsappRepository.createUser(name, mobile);
    }

    public Group createGroup(List<User> users) {
        int size = users.size();
        if (size == 2) return whatsappRepository.personalChat(users);
        return whatsappRepository.groupChat(users);
    }

    public int createMessage(String content) {
        return whatsappRepository.createMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        if (!whatsappRepository.checkGroup(group)) throw new Exception("Group does not exist");

        if (!whatsappRepository.checkSenderInGroup(group, sender)) throw new Exception("You are not allowed to send message");

        int messages = whatsappRepository.sendMessage(message, sender, group);
        return messages;
    }
}











