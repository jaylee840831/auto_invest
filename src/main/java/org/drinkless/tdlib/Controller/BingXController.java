package org.drinkless.tdlib.Controller;

import java.util.List;
import java.util.Map;

import org.drinkless.tdlib.Dao.BingXDao;
import org.drinkless.tdlib.Dto.TDlibDto;
import org.drinkless.tdlib.Service.TdService;
import org.drinkless.tdlib.Service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bingx")
public class BingXController {

	private TDlibDto.newMessageRes message = new TDlibDto.newMessageRes();
	
	@Autowired
	TradingService tradingService;
	
	@Autowired
	TdService tdService;
	
	@Scheduled(cron="0 1 * * * ?")
	@GetMapping("/trading")
	public List<Map<String,TDlibDto.newMessageRes>> trading(){
		return tradingService.trading();
	}
}
