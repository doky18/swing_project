package network;

import java.util.List;

public interface LogMemeberDAO {
	public int insert(LogMember logMember); // insert
	public int update(LogMember logMember); // update
	public int delete(int member_idx); // update
	public List selectAll(); // 모든 레코드 가져오기
	public LogMember select(int member_idx); // 한 건 가져오기(select문)
	public LogMember select(LogMember logMember); // 하위 객체들이 구현을 강제 받음
}
