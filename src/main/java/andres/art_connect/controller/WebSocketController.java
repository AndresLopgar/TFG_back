package andres.art_connect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable; 
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import andres.art_connect.domain.ChatMessageModel;
import andres.art_connect.model.ChatMessage;
import andres.art_connect.repos.IChatSocketRepository;


@Controller
@CrossOrigin("*")
public class WebSocketController {

	@Autowired
	private IChatSocketRepository chatSocketRepository;
	
	@MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage chat(@DestinationVariable String roomId, ChatMessage message) {
		
		ChatMessageModel chatMessageModel = new ChatMessageModel();
		chatMessageModel.setMessage(message.getMessage());
		chatMessageModel.setRoom_id(roomId);
		chatMessageModel.setUser_name(message.getUsuarioId());
		chatSocketRepository.save(chatMessageModel);
        // Aquí podrías guardar el mensaje en la base de datos antes de enviarlo a través del websocket
        return new ChatMessage(message.getMessage(), message.getUsuarioId());
    }
	
	@GetMapping("/api/chat/{roomId}")
	@CrossOrigin("*")
	public ResponseEntity<List<ChatMessageModel>> getAllChatMessages(@PathVariable String roomId){
		List<ChatMessageModel> result = chatSocketRepository.findByRoomId(roomId);
		return ResponseEntity.ok(result);
	}
}

