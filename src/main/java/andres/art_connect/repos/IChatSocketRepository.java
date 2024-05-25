package andres.art_connect.repos;

import java.util.List;

import andres.art_connect.domain.ChatMessageModel;

public interface IChatSocketRepository {
	public int save(ChatMessageModel chatMessageModel);
	public List<ChatMessageModel> findByRoomId(String roomId);
}
