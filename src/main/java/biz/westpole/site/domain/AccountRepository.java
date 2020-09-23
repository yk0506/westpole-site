package biz.westpole.site.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import biz.westpole.site.mapper.LoginMapper;
import biz.westpole.site.service.LoginService;

@Repository
public class AccountRepository {

	@Autowired
	LoginMapper loginMapper;
	
	private Logger log = LoggerFactory.getLogger(AccountRepository.class);
	
	public LoginDTO findById(String username) {
		
		LoginDTO loginDTO = new LoginDTO();
		
		try {
			loginDTO = loginMapper.readAccount(username);
			
		} catch(Exception e) {
			log.error(e.toString());
		}
		
		return loginDTO;
	}
}
