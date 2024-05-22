package andres.art_connect.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable; 
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import andres.art_connect.model.ChatMessage;


@Controller
public class WebSocketController {

	@MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage chat(@DestinationVariable String roomId, ChatMessage message) {
        // Aquí podrías guardar el mensaje en la base de datos antes de enviarlo a través del websocket
        return new ChatMessage(message.getMessage(), message.getUsuarioId());
    }
}

