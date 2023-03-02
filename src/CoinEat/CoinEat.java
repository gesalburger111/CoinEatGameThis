package CoinEat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class CoinEat extends JFrame{
	private Image backgroundImage = new ImageIcon("src/images/mainScreen.png").getImage();
	private Image player = new ImageIcon("src/images/player.png").getImage();
	private Image coin = new ImageIcon("src/images/coin.png").getImage();
	
	// 플레이어 좌표
	private int playerX, playerY;
	private int playerWidth = player.getWidth(null);
	private int playerHeight = player.getHeight(null);
	
	// 코인 좌표
	private int coinX, coinY;
	private int coinWidth = coin.getWidth(null);
	private int coinHeight = coin.getHeight(null);
	
	// 점수 : 변수 score 선언 
	private int score;
	
	public CoinEat() {
		setTitle("동전 먹기 게임");							// 제목
		setVisible(true);									// 보이기 여부
		setSize(500, 500);									// 창 크기
		setLocationRelativeTo(null); 						// null : 실행 시 창 화면 가운데
		setResizable(false); 								// 창 크기 조절 가능 여부
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		// x 클릭 시 창 닫기
		Init();
	}

	// 게임 시작 시 초기화메서드 : Init()
	public void Init() {
		score = 0;											// score 0
		playerX = (500 - playerWidth)/2; 					// player의 x, y 좌표(중앙)
		playerY = (500 - playerHeight)/2; 					
		
		// z코인의 위치 : 랜덤 -> Math.random
		coinX = (int)(Math.random()*(501-playerHeight));
		coinY = (int)(Math.random()*(501-playerWidth+30))-30;		// 프레임 크기 빼주기 : 30 
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