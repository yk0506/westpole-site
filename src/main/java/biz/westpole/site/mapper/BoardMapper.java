package biz.westpole.site.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import biz.westpole.site.domain.BoardDTO;

@Repository
@Mapper
public interface BoardMapper {

	public ArrayList<BoardDTO> selectAllBulletin() throws Exception;
	
	public ArrayList<BoardDTO> selectBulletinList(BoardDTO params) throws Exception;
	
	public BoardDTO	getBulletin(int bltnNo) throws Exception;
	
	public int getBulletinTotalCount() throws Exception;
	
	public void insertBulletin(BoardDTO params) throws Exception;
	
	public void updateBulletin(BoardDTO params) throws Exception;
	
	public void deleteBulletin(BoardDTO params) throws Exception;
	
}
