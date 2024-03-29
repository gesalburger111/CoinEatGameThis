package CoinEat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class CoinEat extends JFrame{
	private	Image bufferImage;			// 버퍼 이미지 객체
	private Graphics screenGraphic;		// 화면의 이미지를 얻어 올 그래픽 객체
	
	private Clip clip;					// 음악 클립
	
	private Image backgroundImage = new ImageIcon("src/images/mainScreen.png").getImage();
	private Image player = new ImageIcon("src/images/player.png").getImage();
	private Image coin = new ImageIcon("src/images/coin.png").getImage();
	
	// 플레이어 좌표
	private int playerX, playerY;
	private int playerWidth = player.getWidth(null);	// player 넓이
	private int playerHeight = player.getHeight(null);	// player 높이
	
	// 코인 좌표
	private int coinX, coinY;	
	private int coinWidth = coin.getWidth(null);		// coin 넓이
	private int coinHeight = coin.getHeight(null);		// coin 높이
	
	// 점수 : 변수 score 선언 
	private int score;
	
	private boolean up, down, left, right;					// 키보드 움직임을 받을 변수 (초기값: false)
	
	public CoinEat() {
		setTitle("동전 먹기 게임");							// 제목
		setVisible(true);									// 보이기 여부
		setSize(500, 500);									// 창 크기
		setLocationRelativeTo(null); 						// null : 실행 시 창 화면 가운데
		setResizable(false); 								// 창 크기 조절 가능 여부
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		// x 클릭 시 창 닫기
		addKeyListener(new KeyAdapter() {
			// keyPressed() : 키를 눌렀을 때 실행할 메소드
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_W:				// 위
					up = true;
					break;	
				case KeyEvent.VK_S:				// 왼쪽
					down = true;
					break;
				case KeyEvent.VK_A:				// 아래
					left = true;
					break;
				case KeyEvent.VK_D:				// 오른쪽
					right = true;
					break;
				}
			}
			
			// keyReleased() : 키를 뗏을 때 실행할 메소드
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_W:
					up = false;
					break;
				case KeyEvent.VK_S:
					down = false;
					break;
				case KeyEvent.VK_A:
					left = false;
					break;
				case KeyEvent.VK_D:
					right = false;
					break;
				}
			}
		});
		
		Init();
		
		// 만들어 놓은 메서드 반복
		while(true) {
			try{
				Thread.sleep(20);				// 대기시간 : 스레드의 실행 잠깐 멈추기
			} catch(InterruptedException e) {	// interrupt :스레드를 종료하기 위한 메커니즘
				e.printStackTrace();
			}
			keyProcess();
			crashCheck();
		}
	}

	// 게임 시작 시 초기화메서드 : Init()
	public void Init() {
		score = 0;											// score 0
		playerX = (500 - playerWidth)/2; 					// player의 x, y 좌표(중앙)
		playerY = (500 - playerHeight)/2; 					
		
		// z코인의 위치 : 랜덤 -> Math.random
		coinX = (int)(Math.random()*(501-playerHeight));
		coinY = (int)(Math.random()*(501-playerWidth-30))+30;		// 프레임 크기 빼주기 : 30 
		
		playSound("src/audio/play.wav", true);				// 백그라운드 음악
	}
	
	// keyProcess() : up, down, left, right의 boolean 값으로 플레이어를 이동시킬 메소드
	// 플레이어의 가로, 세로 길이, 이동 거리 고려할 것
	// 플레이어의 가로 : 50  |  플레이어의 세로 : 50  |  이동 거리 : 10
	public void keyProcess() {
		if(up && (playerY - 10) > 30) { playerY -= 10; }					  // up 이고, playerY - 10(좌표) 가 30보다 클 때
		// 왜 50보다 클때?
		if(down && (playerY + playerHeight + 10) < 500) { playerY += 10; }  // down 이고, playerY + playerHeight + 10 가 500보다 작을 때
		if(left && (playerX - 10) > 0) { playerX -= 10; }					  // left이고, playerX -10 가 0보다 클 때
		if(right && (playerX + playerWidth + 10) < 500) { playerX += 10; }
	}
	
	// crashCheck() : 플레이어와 코인이 닿았을 때 점수 획득
	public void crashCheck() {
		// 충돌 범위 설정
		if(playerX+playerWidth > coinX && coinX + coinWidth > playerX && playerY + playerHeight > coinY && coinY + coinHeight > playerY) {
			score += 100; // 닿았을 때 점수 +100 	
			playSound("src/audio/coin.wav", false);	
			coinX = (int)(Math.random()*(501-playerHeight));			// 코인 위치 옮겨주기 : 랜덤
			coinY = (int)(Math.random()*(501-playerWidth-30))+30;	
			
		}
	}
	
	// playSound() 메소드 : 오디오 재생 메소드 - 오디오 재생, 무한 반복 여부 설정
	public void playSound(String pathName, boolean isLoop) {
		try {
			clip = AudioSystem.getClip();
			File audioFile = new File(pathName);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip.open(audioStream);
			clip.start();
			if(isLoop)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch(LineUnavailableException e){		// 다른 응용프로그램에서 이미 사용중일 때 사용할 수 없는 예외
			e.printStackTrace();
		} catch(UnsupportedAudioFileException e){   // 파일 타입, 형식 사용할 수 없는 예외
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	// 더블 버퍼링 : 버퍼 이미지를 통해 화면의 깜빡임 최소화
	// 화면 크기의 버퍼 이미지 생성, getGraphics() 통해 그래픽 받아오기
	// 버퍼 : 속도차가 큰 두 대상이 입출력을 수행할 때 효율성을 위해 사용하는 임시 저장공간
	public void paint(Graphics g) {
		bufferImage = createImage(500, 500);
		screenGraphic = bufferImage.getGraphics();
		screenDraw(screenGraphic);				// screenDraw 호출, 해당 버퍼 이미지 화면에 그려주기
		g.drawImage(bufferImage, 0, 0, null);	// drawImage(img x 좌표, img y 좌표, img width, img height)
	}
	
	// 이미지 출력 메소드 : paint()
	public void screenDraw(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, null);			// 배경이미지
		g.drawImage(player, playerX, playerY, null);		// 플레이어
		g.drawImage(coin, coinX, coinY, null);				// 코인
		g.setColor(Color.WHITE);							// score
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("SCORE : " + score, 30, 80);
		this.repaint();
	}
	
	public static void main(String[] args) {
		// 메인 메서드에 CoinEat 객체 생성 : 생성자 호출 
		new CoinEat();
	}
}