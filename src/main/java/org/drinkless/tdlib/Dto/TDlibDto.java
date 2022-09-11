package org.drinkless.tdlib.Dto;

import java.util.Date;

public class TDlibDto {

	public static class newMessageRes{
		public String message;
		public String title;
		public String currency;
		public Double price;
		public Double currentProfit;
		public Double currentStopLoss;
		public Double hedging;
		public Date hedgingTime;
		@Override
		public String toString() {
			return "newMessageRes [message=" + message + ", title=" + title + ", currency=" + currency + ", price="
					+ price + ", currentProfit=" + currentProfit + ", currentStopLoss=" + currentStopLoss + ", hedging="
					+ hedging + ", hedgingTime=" + hedgingTime + "]";
		}
	}

	public static class registerReq{
		public String userId;
		public String apiKey;
		public String secretKey;
	}
	
	public static class registerRes{
		public String status;
		public String message;
	}
	
	public static class isDifferentInfoRes{
		public Boolean isDefferentInfo;
	}
}
