package andres.art_connect.repos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import andres.art_connect.domain.ChatMessageModel;

@Repository
public class ChatSocketRepository  implements IChatSocketRepository{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int save(ChatMessageModel chatMessageModel) {
		String SQL = "INSERT INTO chat_message_model (message, room_id, user_name) values(?,?,?)";
		return jdbcTemplate.update(SQL, new Object[] {
				 chatMessageModel.getMessage(),chatMessageModel.getRoom_id(), chatMessageModel.getUser_name(),
		});
	}

	@Override
	public List<ChatMessageModel> findByRoomId(String roomId) {
		String SQL = "SELECT * FROM chat_message_model WHERE room_id = ?";
		return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(
				ChatMessageModel.class
				), roomId);
	}
}
