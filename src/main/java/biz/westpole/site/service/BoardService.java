package biz.westpole.site.service;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import biz.westpole.site.mapper.BoardMapper;
import biz.westpole.site.controller.BoardController;
import biz.westpole.site.domain.BoardDTO;
import biz.westpole.site.paging.PaginationInfo;

@Service
@Transactional
public class BoardService {
	
	private Logger log = LoggerFactory.getLogger(BoardService.class);
	
	@Autowired
	BoardMapper boardMapper;
	
	public ArrayList<BoardDTO> selectAllBulletin() throws Exception {
		return boardMapper.selectAllBulletin();
	}
	
	public ArrayList<BoardDTO> selectBulletinList(BoardDTO params) throws Exception {
		
		ArrayList<BoardDTO> bltnList = new ArrayList<BoardDTO>();
		int boardTotalCount = boardMapper.getBulletinTotalCount();
		
		try {
			
			PaginationInfo paginationInfo = new PaginationInfo(params);
			paginationInfo.setTotalRecordCount(boardTotalCount);

			params.setPaginationInfo(paginationInfo);

			if (boardTotalCount > 0) {
				
				bltnList = boardMapper.selectBulletinList(params);
			}
			
		} catch(Exception e) {
			log.error(e.toString());
		}
		
		return bltnList;
	}
	
	
	public BoardDTO getBulletin(@Param("bltnNo") int bltnNo) throws Exception {
		
		return boardMapper.getBulletin(bltnNo);
	}
	
	
	public void insertBulletin(BoardDTO params) throws Exception {
	
		try {
			
			boardMapper.insertBulletin(params);
			
		} catch(Exception e) {
			log.error(e.toString());
		}
		
	}
	
	public void updateBulletin(BoardDTO params) throws Exception {
		
		try {
			
			boardMapper.updateBulletin(params);
			
		} catch(Exception e) {
			log.error(e.toString());
		}
		
	}
	
	public void deleteBulletin(BoardDTO params) throws Exception {
		
		try {
			
			boardMapper.deleteBulletin(params);
			
		} catch(Exception e) {
			log.error(e.toString());
		}
		
	}
}
