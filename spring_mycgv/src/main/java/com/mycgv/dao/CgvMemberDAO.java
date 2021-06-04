package com.mycgv.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.mycgv.vo.CgvMemberVO;
import com.mycgv.vo.SessionVO;

public class CgvMemberDAO extends DBConn{
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private static String namespace="mapper.member";

	
	/**
	 * login 
	 */
	public SessionVO getLogin(CgvMemberVO vo) {
		return sqlSession.selectOne(namespace+".login",vo);
	}	
	

	/**
	 * Insert : ȸ������
	 */
	public boolean getInsert(CgvMemberVO vo) {
		boolean result = false;
		int value = sqlSession.insert(namespace+".join", vo);
		if(value != 0) result = true;
		return result;  		
	}
	
	/**
	 * ȸ������ - ���̵� �ߺ�üũ
	 */
	public int getIdCheck(String id) {
		return sqlSession.selectOne(namespace+".idcheck",id);		
	}
	
	
	/**
	 * List : ��ü ȸ�� ����Ʈ
	 */
	public ArrayList<CgvMemberVO> getList(){
		List<CgvMemberVO> list = sqlSession.selectList(namespace+".list");
		return (ArrayList<CgvMemberVO>)list;		
	}
	
	/**
	 * ��ü ����Ʈ ī��Ʈ
	 */
	public int getListCount() {
		System.out.println("------------>> listcount");
		return sqlSession.selectOne(namespace+".listcount");	
	}
	
	/**
	 * Content : ȸ�� �� ����
	 */
	public CgvMemberVO getContent(String id) {
		return sqlSession.selectOne(namespace+".content", id);		
	}
	
	
	/**
	 * List : ��ü ȸ�� ����Ʈ
	 */
	public ArrayList<CgvMemberVO> getList(int start, int end){
		//�Ķ���Ͱ� 1�� �̻��� ���� Map<k,v>������ ��ü�� ��Ƽ� ����		
		Map<String,String> param = new HashMap<String,String>();
		param.put("start", String.valueOf(start));
		param.put("end", String.valueOf(end));
		
		List<CgvMemberVO> list = sqlSession.selectList(namespace+".plist",param);
		return (ArrayList<CgvMemberVO>)list;		
	}
	
	
	/**
	 * �˻� ���
	 */
	public ArrayList<CgvMemberVO> getSearchList(String sname, String svalue,
													int start, int end){
		ArrayList<CgvMemberVO> list = new ArrayList<CgvMemberVO>();
		
		try {
			String str="";
			if(sname.equals("total")) {
				str="";
			}else {
				str = " where " + sname +"='"+svalue+"'";
			}
			
			String sql = " select * from (select rownum rno, id, name, cp, gender, to_char(mdate,'yyyy.mm.dd') mdate " + 
					" from (select * from cgvmember order by mdate desc) " 
					+ str + ") where rno between "+ start + " and " + end ;
			
			getStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				CgvMemberVO vo = new CgvMemberVO();
				vo.setRno(rs.getInt(1));
				vo.setId(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setCp(rs.getString(4));
				vo.setGender(rs.getString(5));
				vo.setMdate(rs.getString(6));
				
				list.add(vo);
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}	
	
	

	
	
	/**
	 * �˻���� �� ����Ʈ ī��Ʈ
	 */
	public int getListCount(String sname, String svalue) {
		int result = 0;
		
		try {
			String str="";
			if(!sname.equals("total")) {
				str = " where "+sname+"="+"'" + svalue+"'";
			}
			
			String sql = "select count(*) from cgvmember " + str;
			getStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) result = rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	


}
