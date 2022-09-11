package org.drinkless.tdlib.Repository;

import java.util.List;
import java.util.Map;

import org.drinkless.tdlib.Dao.BingXDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TdCommonRepository extends JpaRepository<BingXDao.autoInvest,String>{

	@Query(value="SELECT * FROM auto_invest WHERE user_id = :userId",nativeQuery = true)
	public BingXDao.autoInvest findByUserid(@Param("userId") String userId);
	
	@Transactional
	@Modifying
	@Query(value="INSERT INTO auto_invest VALUES ?1",nativeQuery = true)
	public void insertUser(List<String> valueList);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE auto_invest SET api_key = ?1 , secret_key = ?2 WHERE user_id = ?3",nativeQuery = true)
	public void updateUser(String api_key,String secret_key,String userId);
	
	@Query(value="SELECT * FROM auto_invest ORDER BY user_id ASC",nativeQuery = true)
	public Page<BingXDao.autoInvest> findInvests(PageRequest pageRequest);
}
