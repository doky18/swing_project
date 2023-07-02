package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {
		/*-------------------------------------------------
	 * 				    비밀번호 암호화 하기
	 * 자바의 보안과 관련된 기능을 지원하는 api들이 모여있는 패키지가 
	 * java.security이다
	 -----------------S----------------------------------*/
	public static String getConvertedPassword(String pass) {
		// 암호화 객체
		 StringBuffer sb = new StringBuffer();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(pass.getBytes("UTF-8")); // 반환형 byte[] 받을이름

			/*
			 * String은 불변(상수)이다. 따라서 그 값이 변경될 수 없다~!~! 따라서 String 객체는 반복문 등에서 반복문 횟수가 클때는 절대
			 * 누적식을 사용해서는 안됨 해결책-> 변경가능한 문자열 객체를 지원하는 StringBuffer StringBuilder 등을 활용하자~!
			 */
			//hexString = new StringBuffer(); // StringBuffer는 String 객체가 아님.
			// String hexString = ""; // "" 가 null 과 같음.
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]); // 자리수 옮겨서 16진수로 만들어 주는 것
				System.out.println(hex);
				if (hex.length() == 1) sb.append('0');
				sb.append(0);// 객체 누적을 방지하는 것
				}
				// hexString += hex; //지우고 다시 누적을 하는게 아니라 컴퓨터는 새로운 객체가 계속 생기는 것으로 인식함
				
			System.out.println(sb.toString());
		} catch (NoSuchAlgorithmException e) { // 존재하지 않는 알고리즘 일수도 있지만 그대로 진행하겠다
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
/*
	public static void main(String[] args) {		//실행부가 없으면 실행이 안되겠지?????
		getConvertedPassword("도경");
		System.out.println(result.length());
	}
*/
}