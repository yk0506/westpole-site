package biz.westpole.site.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import biz.westpole.site.domain.BoardDTO;
import biz.westpole.site.domain.LoginDTO;

@Repository
@Mapper
public interface LoginMapper {
	
	
	public LoginDTO readAccount(String username) throws Exception;
	public int validUserInfo(LoginDTO params) throws Exception;
	public void createAccount(LoginDTO params) throws Exception;
}
