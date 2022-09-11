package org.drinkless.tdlib.Controller;

import java.util.List;
import java.util.Map;

import org.drinkless.tdlib.Dao.BingXDao;
import org.drinkless.tdlib.Dto.TDlibDto;
import org.drinkless.tdlib.Properties.YamlFileProperties;
import org.drinkless.tdlib.Service.TdCommonService;
import org.drinkless.tdlib.Service.TdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/td/")
public class TdController {
	
	@Autowired
	YamlFileProperties yamlProperties;
	
	@Autowired
	TdService tdService;
	
	@Autowired
	TdCommonService tdCommonService;
	
	@GetMapping("start")
	public String startTDlib(){
		
		tdService.start();
		
		return "start TDLib thread";
	}
	
//	@GetMapping("end")
//	public String endTDlib(){
//		
//		tdService.end();
////		tdService.currentThread().interrupt();
//		
//		return "end TDLib thread";
//	}
	
	//指定聊天室最新訊息
	@GetMapping("gc/info")
	public ResponseEntity<TDlibDto.newMessageRes> getNewInfo(){
		
		return ResponseEntity.ok(tdService.getMessage());
	}

	//設定指定聊天室id
	@GetMapping("gc")
	public ResponseEntity<TDlibDto.newMessageRes> setCommand(){
		
		StringBuffer command = new StringBuffer("");
		command.append("gc ");
		command.append(yamlProperties.getChatRoomCode());
//		System.out.println(command.toString());
		tdService.setTDCommand(command.toString());
		
		return ResponseEntity.ok(tdService.getMessage());
	}
	
	//聊天室列表
	@GetMapping("gcs")
	public ResponseEntity<TDlibDto.newMessageRes> getchatRoomInfo(){
		
		tdService.setTDCommand("gcs");
		
		return ResponseEntity.ok(tdService.getMessage());
	}
	
	//BingX用戶註冊自動交易
	@PostMapping("/register/bingx")
	public ResponseEntity<TDlibDto.registerRes> registerBingX(@RequestBody TDlibDto.registerReq req) {
		return ResponseEntity.ok(tdCommonService.register(req));
	}
	
	//BingX用戶自動交易註冊列表
	@GetMapping("/invest_list/bingx")
	public ResponseEntity<List<BingXDao.autoInvest>> investListBingX() {
		return ResponseEntity.ok(tdCommonService.investListBingX());
	}
	
	//BingX用戶自動交易註冊列表總數量
	@GetMapping("/invest_list/length/bingx")
	public ResponseEntity<Integer> investListBingXLength() {
		return ResponseEntity.ok(tdCommonService.investListBingXLength());
	}
	
//	@GetMapping("sm/{command}")
//	public ResponseEntity<TDlibDto.newMessageRes> sendMessage(@PathVariable("command") String command){
//		
//		tdService.setTDCommand(command);
//		
//		return ResponseEntity.ok(tdService.getMessage());
//	}
}
