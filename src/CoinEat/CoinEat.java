package CoinEat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class CoinEat extends JFrame{
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
	}

	// 게임 시작 시 초기화메서드 : Init()
	public void Init() {
		score = 0;											// score 0
		playerX = (500 - playerWidth)/2; 					// player의 x, y 좌표(중앙)
		playerY = (500 - playerHeight)/2; 					
		
		// z코인의 위치 : 랜덤 -> Math.random
		coinX = (int)(Math.random()*(501-playerHeight));
		coinY = (int)(Math.random()*(501-playerWidth-30))+30;		// 프레임 크기 빼주기 : 30 
	}
	
	
	
	
	
	
	
	
	
	
	// keyProcess() : up, down, left, right의 boolean 값으로 플레이어를 이동시킬 메소드
	// 플레이어의 가로, 세로 길이, 이동 거리 고려할 것
	// 플레이어의 가로 : 50  |  플레이어의 세로 : 50  |  이동 거리 : 3
	public void keyProcess() {
		if(up && (playerY - 3) > 50) { playerY -= 3; }					  // up 이고, playerY - 3 가 50보다 클 때
		// 왜 50보다 클때?
		if(down && (playerY + playerHeight + 3) < 500) { playerY += 3; }  // dowm 이고, playerY + playerHeight + 3 가 500보다 작을 때
		if(left && (playerX -3) > 0) { playerX -= 3; }					  // left이고, playerX -3 가 0보다 클 때
		if(right && (playerX + playerWidth + 3) < 500) { playerX += 3; }
	}
	
	
	
	
	// 이미지 출력 메소드 : paint()
	public void paint(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, null);			// 배경이미지
		g.drawImage(player, playerX, playerY, null);		// 플레이어
		g.drawImage(coin, coinX, coinY, null);				// 코인
		g.setColor(Color.WHITE);							// score
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("SCORE : " + score, 30, 80);
	}
	
	public static void main(String[] args) {
		// 메인 메서드에 CoinEat 객체 생성 : 생성자 호출 
		new CoinEat();
	}
}