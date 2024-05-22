package andres.art_connect.model;

import lombok.AllArgsConstructor; 
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessage {

	private String message;
	private  int usuarioId;

}

