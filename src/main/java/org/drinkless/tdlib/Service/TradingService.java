package org.drinkless.tdlib.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drinkless.tdlib.Dao.BingXDao;
import org.drinkless.tdlib.Dto.TDlibDto;
import org.drinkless.tdlib.Repository.TdCommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradingService {

	@Autowired
	TdCommonRepository tdCommonRepository;
	
	@Autowired
	TdService tdService;
	
	public List<Map<String,TDlibDto.newMessageRes>> trading(){
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<Map<String,TDlibDto.newMessageRes>> result = new ArrayList<>();
		List<BingXDao.autoInvest> invests = tdCommonRepository.findAll();
		
		TDlibDto.newMessageRes messageInfo = tdService.getMessage();
		
		if(invests != null && messageInfo != null) {	
			
			for(BingXDao.autoInvest invest: invests) {
				
				StringBuffer threadName = new StringBuffer("");
				threadName.append(invest.userId);
				threadName.append("_");
				threadName.append(formatter.format(date));
				
				new Thread(()->{
					BingXTrading bingxT = new BingXTrading(invest.apiKey,invest.secretKey);
					boolean isTrading = bingxT.placeOrder(null, null, null, null, null, null,threadName.toString(),messageInfo);
					Map<String,TDlibDto.newMessageRes> resultMap = new HashMap<>();
					
					if(isTrading) {
						resultMap.put(threadName.toString(),messageInfo);
						result.add(resultMap);
					}
				},threadName.toString()).start();
			}
		}
		return result;
	}
}
