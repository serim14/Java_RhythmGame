package dynamic_beat_4;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Music extends Thread {	// Tread는 하나의 작은 프로그램
	private Player player;	// 다운받은 라이브러리의 클래스
	private boolean isLoop;	// 곡을 무한반복 또는 한 번 재생 설정
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
	
	public Music(String name, boolean isLoop) {
		try {
			this.isLoop = isLoop;
			file = new File(Main.class.getResource("../music/" + name).toURI());	// 파일 해당 위치 가져옴
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);	// 해당 파일을 버퍼에 담아 읽어옴
			player = new Player(bis);	// 해당 파일을 담음
		} catch (Exception e) {	// 오류가 난다면 catch문 수행
			System.out.println(e.getMessage());
		}
	}
	
	public int getTime() {	// 현재 실행 음악 어떤 위치인지 반환, 음악에 맞춰 노트를 떨어뜨릴 때 사용, 0.001초 단위 계산
		if (player == null)
			return 0;
		return player.getPosition();
	}
	
	public void close() {	// 음악 위치 상관 없이 종료시키는 함수, 뒤로가기 버튼 눌렀을 때 안정적으로 종료되도록
		isLoop = false;
		player.close();
		this.interrupt();	// 곡을 재생하는 작은 프로그램 따로 존재, 그 프로그램을 종료시킴
	}
	
	@Override
	public void run() {	// Tread를 상속받으면 무조건 사용해야하는 함수
		try {
			do {
				player.play();	// 곡 재생
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				player = new Player(bis);
			} while(isLoop);	// 무한반복
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
