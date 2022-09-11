package org.drinkless.tdlib.Service;

import java.util.ArrayList;
import java.util.List;

import org.drinkless.tdlib.Dao.BingXDao;
import org.drinkless.tdlib.Dto.TDlibDto;
import org.drinkless.tdlib.Repository.TdCommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TdCommonService {

	@Autowired
	TdCommonRepository tdCommonRepository;
	
	public TDlibDto.registerRes register(TDlibDto.registerReq req){
		
		TDlibDto.registerRes res = new TDlibDto.registerRes();
		
		if(req == null || req.userId.equals("")  || req.apiKey.equals("") || req.secretKey.equals("")) {
			res.status = "fail";
			res.message = "輸入有誤或有空值";
		}
		else {
			
			BingXDao.autoInvest user = tdCommonRepository.findByUserid(req.userId);
			
			//若用戶已經在資料庫的自動交易的table 就 update，反之則insert一筆
			if(user != null) {
				tdCommonRepository.updateUser(req.apiKey, req.userId,req.secretKey);;
			}
			else {
				List<String> valueList = new ArrayList<>();
				valueList.add(req.userId);
				valueList.add(req.apiKey);
				valueList.add(req.secretKey);
				tdCommonRepository.insertUser(valueList);
			}
			
			res.status = "success";
			res.message = "註冊成功";
		}
		
		return res;
	}
	
	//page 分頁
//	public Page<TDlibDao.autoInvest> investListBingX(){
//		int page = 0;
//		int size = 10;
//		PageRequest pageRequest = PageRequest.of(page, size);
//		Page<TDlibDao.autoInvest> invests = tdCommonRepository.findInvests(pageRequest);
//		
//		for(int i=0;i<invests.getContent().size();i++) {
//			System.out.println(invests.getContent().get(i).userId);
//			System.out.println(invests.getContent().get(i).apiKey);
//			System.out.println(invests.getContent().get(i).secretKey);
//			System.out.println("----");
//		}
//		
//		return invests;
//	}
	
	public List<BingXDao.autoInvest> investListBingX(){
	return tdCommonRepository.findAll();
}
	
	public Integer investListBingXLength(){
		return tdCommonRepository.findAll().size();
	}
}
