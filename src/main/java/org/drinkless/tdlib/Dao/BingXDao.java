package org.drinkless.tdlib.Dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class BingXDao {

	@Entity
	@Table(name="auto_invest")
	public static class autoInvest{
		@Id
		@Column(name="user_id")
		public String userId;
		
		@Column(name="api_key")
		public String apiKey;
		
		@Column(name="secret_key")
		public String secretKey;

	}
}
