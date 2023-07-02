package table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class EmpModel extends AbstractTableModel{
	List<Emp2> list = new ArrayList<Emp2>();
	String[] column= {"EMPNO","ENAME","JOB","MGR","HIREDATE","SAL","COMM", "DEPTNO"};
	TableMain tableMain;
	
	public EmpModel(TableMain tableMain) {
		this.tableMain = tableMain;
	}
	
	  public int getRowCount() {
	      return list.size();
	   }

	   public int getColumnCount() {
	      return column.length;
	   }
	   
	   public String getColumnName(int col) {
	      return column[col];
	   }

	   //모델에 대한 데이터를 가져가는 메서드
	   public Object getValueAt(int row, int col) {
	      Emp2 emp = list.get(row);
	      
	      String value ="";

		switch(col) {
		 case 0:value= Integer.toString(emp.getEmpno());break;
         case 1:value= emp.getEname();break;
         case 2:value= emp.getJob();break;
         case 3:value= Integer.toString(emp.getMgr());break;
         case 4:value= emp.getHiredate();break;
         case 5:value= Integer.toString(emp.getSal());break;
         case 6:value= Integer.toString(emp.getComm());break;
         case 7:value= Integer.toString(emp.getDeptno());break;
		
		}
		return value;
	}

	//테이블을 구성하고 있는 각 셀을 편집할 수 있는지 여부 
	public boolean isCellEditable(int row, int col) {
		boolean flag = false;
		
		switch(col) {
		case 1:flag=true;break;
		case 2:flag=true;break;
		case 4:flag=true;break;
		case 5:flag=true;break;
		case 6:flag=true;break;
		}
		
		return flag;
	}
	
	public void setValueAt(Object value, int row, int col) {
		System.out.println(row+","+col+"의 값을 "+value+"로 변경 할게요!");
		
		Emp2 emp = list.get(row);//층 
		
		
		switch(col) {
	      case 1:emp.setEname((String)value);break;//이름변경 
	      case 2:emp.setJob((String)value);break;//업무변경 
	      case 4:emp.setHiredate((String)value);break;//입사일 변경 
	      case 5:emp.setSal(Integer.parseInt((String)value)); break; //급여 변경 
	      case 6:emp.setComm(Integer.parseInt((String)value));break;   //커미션 변겅 
		}
		
		//스위치문 밑에서 이미 DTO변경이 완료되었으므로, 이 시점에 db update 
		tableMain.dao.update(emp);
	}
}
