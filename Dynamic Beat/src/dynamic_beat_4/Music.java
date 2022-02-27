package dynamic_beat_4;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Music extends Thread {	// Tread�� �ϳ��� ���� ���α׷�
	private Player player;	// �ٿ���� ���̺귯���� Ŭ����
	private boolean isLoop;	// ���� ���ѹݺ� �Ǵ� �� �� ��� ����
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
	
	public Music(String name, boolean isLoop) {
		try {
			this.isLoop = isLoop;
			file = new File(Main.class.getResource("../music/" + name).toURI());	// ���� �ش� ��ġ ������
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);	// �ش� ������ ���ۿ� ��� �о��
			player = new Player(bis);	// �ش� ������ ����
		} catch (Exception e) {	// ������ ���ٸ� catch�� ����
			System.out.println(e.getMessage());
		}
	}
	
	public int getTime() {	// ���� ���� ���� � ��ġ���� ��ȯ, ���ǿ� ���� ��Ʈ�� ����߸� �� ���, 0.001�� ���� ���
		if (player == null)
			return 0;
		return player.getPosition();
	}
	
	public void close() {	// ���� ��ġ ��� ���� �����Ű�� �Լ�, �ڷΰ��� ��ư ������ �� ���������� ����ǵ���
		isLoop = false;
		player.close();
		this.interrupt();	// ���� ����ϴ� ���� ���α׷� ���� ����, �� ���α׷��� �����Ŵ
	}
	
	@Override
	public void run() {	// Tread�� ��ӹ����� ������ ����ؾ��ϴ� �Լ�
		try {
			do {
				player.play();	// �� ���
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				player = new Player(bis);
			} while(isLoop);	// ���ѹݺ�
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
