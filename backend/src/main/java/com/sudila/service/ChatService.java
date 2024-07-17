package com.sudila.service;

import com.sudila.modal.Chat;

public interface ChatService {
    Chat createChat(Chat chat);
    Chat getChat(Long chatId);
}
