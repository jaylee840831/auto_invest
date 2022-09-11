package org.drinkless.tdlib.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class YamlFileProperties {

	@Value("${TDlib.chat_room_code}")
	private String chatRoomCode;
	
	public String getChatRoomCode() {
		return this.chatRoomCode;
	}
}
