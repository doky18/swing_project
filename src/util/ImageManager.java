package util;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageManager {
	//이미지에 대한 경로를 전달하면, 이미지 객체를 반환하는 메서드 정의
	public Image[] createImages(String[] imgName) {
		Class myClass = this.getClass();
		Image[] images = new Image[imgName.length];
		for(int i=0;i<imgName.length;i++) {
			URL url = myClass.getClassLoader().getResource(imgName[i]);
			//URL url = null;//자원의 위치를 의미,패키지경로도 처리 가능
			try {
				images[i] = ImageIO.read(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return images;
	}
	
	
	//아이콘 생성하기
	public Icon getIcon(String path,int width, int height) {
		Icon icon = null;
		Class myClass = this.getClass();
		ClassLoader loader = myClass.getClassLoader();
		URL url = loader.getResource(path);
		Image image = null;
		try {
			image = ImageIO.read(url).getScaledInstance(width, height, Image.SCALE_SMOOTH);
			icon = new ImageIcon(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return icon;
	}
	
	//아이콘 주소로 생성하기
	public static Icon getURLIcon(String path,int width, int height) {
		Icon icon = null;
		Image image = null;
		try {
			URL url = new URL(path);
			image = ImageIO.read(url).getScaledInstance(width, height, Image.SCALE_SMOOTH);
			icon = new ImageIcon(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return icon;
	}
	
	public Image getImage(String imgName,int width,int height) {
		Class myClass = this.getClass();
		Image image = null;
		URL url = myClass.getClassLoader().getResource(imgName);
		//URL url = null;//자원의 위치를 의미,패키지경로도 처리 가능
		try {
			image = ImageIO.read(url).getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	
	
}