package com.mycgv.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.mycgv.vo.CgvNoticeVO;

public class CgvNoticeDAO extends DBConn{
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private static String namespace = "mapper.notice";
	
	/**
	 * ���� ����
	 */
	public int getSelectDelete(String[] dellist) {
		return sqlSession.delete(namespace+".deletelist", dellist);
	}
	
	
	/**
	 * ��ü ����Ʈ ī��Ʈ
	 */
	public int getListCount() {
		return sqlSession.selectOne(namespace+".listcount");		
	}
	
	
	
	/**
	 * Update : ��ȸ�� ������Ʈ
	 */
	public void getUpdateHits(String nid) {
		sqlSession.update(namespace+".updatehits", nid);		
	}
	
	
	
	/**
	 * Delete : ����
	 */
	public boolean getDelete(String nid) {
		boolean result = false;
		int value = sqlSession.delete(namespace+".delete", nid);
		if(value !=0) result = true;
		return result;		
	}
	
	
	/**
	 * Update : ���� ���� - nfile,nsfile
	 */
	public boolean getUpdate(CgvNoticeVO vo) {
		boolean result = false;
		int value = sqlSession.update(namespace+".update",vo);
		if(value !=0) result = true;
		return result;		
	}
	
	
	/**
	 * Select : ������ ���
	 */
	public CgvNoticeVO getContent(String nid) {
		return sqlSession.selectOne(namespace+".content",nid);		
	}
	
	
	/**
	 * Select : ��ü ����Ʈ ��� 
	*/ 
	public ArrayList<CgvNoticeVO> getList(int start, int end){
		Map<String,String> param = new HashMap<String,String>();
		param.put("start", String.valueOf(start));
		param.put("end", String.valueOf(end));
		List<CgvNoticeVO> list = sqlSession.selectList(namespace+".list",param);
		return (ArrayList<CgvNoticeVO>)list;
	}
	
	/**
	 * Insert : �������� �۾���
	 */
	public boolean getInsert(CgvNoticeVO vo) {
		boolean result = false;
		int value = sqlSession.insert(namespace+".insert",vo);
		if(value !=0 ) result = true;
		return result;		
	}
	
	
	/**
	 * Select : ��ü ����Ʈ ��� 
	 
	public ArrayList<CgvNoticeVO> getList(){
		ArrayList<CgvNoticeVO> list = new ArrayList<CgvNoticeVO>();
		
		try {
			String sql = "select rownum rno, nid, ntitle, to_char(ndate,'yyyy.mm.dd'), nhits" + 
					" from (select * from cgvnotice order by ndate desc)";
			
			getPreparedStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				CgvNoticeVO vo = new CgvNoticeVO();
				vo.setRno(rs.getInt(1));
				vo.setNid(rs.getString(2));
				vo.setNtitle(rs.getString(3));
				vo.setNdate(rs.getString(4));
				vo.setNhits(rs.getInt(5));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	*/
	
	
}//class





