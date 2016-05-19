import java.awt.Canvas;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.TexturePaint;

public class WojnaSwiatow extends Canvas implements Stage, KeyListener {
	public long usedTime;
	public void gameOver() { gameEnded = true;}
	private boolean gameEnded=false;
	private SoundCache soundCache;
	public BufferStrategy strategia;
	private SpriteCache spriteCache;
	private ArrayList actors;
	private Player player;
	private BufferedImage ocean;
	private int t;
	public Player getPlayer() { return player;}
public WojnaSwiatow() {
	spriteCache = new SpriteCache();
	soundCache = new SoundCache();
	JFrame okno = new JFrame(".: Wojna Swiatow :.");
	JPanel panel = (JPanel)okno.getContentPane();
	setBounds(0,0,Stage.SZEROKOSC,Stage.WYSOKOSC);
	panel.setPreferredSize(new Dimension(Stage.SZEROKOSC,Stage.WYSOKOSC));
	panel.setLayout(null);
	panel.add(this);
	okno.setBounds(0,0,Stage.SZEROKOSC,Stage.WYSOKOSC);
	okno.setVisible(true);
	okno.addWindowListener( new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	});
	okno.setResizable(false);
	createBufferStrategy(2);
	strategia = getBufferStrategy();
	requestFocus();
	addKeyListener(this);
}

public SoundCache getSoundCache() {
return soundCache;
}

public void keyPressed(KeyEvent e) {
	player. keyPressed(e);
}

public void keyReleased(KeyEvent e) {
	player. keyReleased(e);
}

public void keyTyped(KeyEvent e) {}

public void initWorld() {
	actors = new ArrayList();
	for (int i = 0; i < 10; i++){
		Monster m = new Monster(this);
		m.setX( (int)(Math.random()*Stage.SZEROKOSC) );
		m.setY( i*20 );
		m.setVx( (int)(Math.random()*3)+1 );
		actors.add(m);
	}
	player = new Player(this);
	player.setX(Stage.SZEROKOSC/2);
	player.setY(Stage.WYSOKOSC - 2*player.getHeight());
}
public void paintWorld() {
	Graphics2D g = (Graphics2D)strategia. getDrawGraphics();
	ocean = spriteCache. getSprite("blue.jpg");
	g. setPaint(new TexturePaint(ocean, new Rectangle(0,t,ocean. getWidth(),ocean.getHeight())));
	g. fillRect(0,0, getWidth(), getHeight());
	for (int i = 0; i < actors.size(); i++) {
		Actor m = (Actor)actors. get(i);
		m. paint(g);
	}
	player. paint(g);
	paintStatus(g);
	strategia. show();
}


public void addActor(Actor a) {
	actors. add(a);
}
public void updateWorld() {
	int i = 0;
	while (i < actors.size()) {
		Actor m = (Actor)actors. get(i);
		if (m. isMarkedForRemoval()) {
			actors. remove(i);
}
		else {
			m. act();
			i++;
		}
	}
	player. act();
}
public SpriteCache getSpriteCache() {
	return spriteCache;
}
public void checkCollisions() {
	Rectangle playerBounds = player. getBounds();
	for (int i = 0; i < actors. size(); i++) {
		Actor a1 = (Actor)actors. get(i);
		Rectangle r1 = a1. getBounds();
		if (r1. intersects(playerBounds)) {
		player. collision(a1);
		a1. collision(player);
		}
		for (int j = i+1; j < actors.size(); j++) {
		Actor a2 = (Actor)actors. get(j);
		Rectangle r2 = a2. getBounds();
		if (r1. intersects(r2)) {
		a1. collision(a2);
		a2. collision(a1);
		}
		}
		}
		}
public void game() {
	usedTime=1000;
	t = 0;
	initWorld();

	while (isVisible() && !gameEnded) {
		t++;
		long startTime = System.currentTimeMillis();
		updateWorld();
		checkCollisions();
		paintWorld();
		usedTime = System.currentTimeMillis()-startTime;
		try {
			Thread.sleep(10);
		} 
		catch (InterruptedException e) {}
	}
	paintGameOver();
}
public static void main(String[] args) {
			WojnaSwiatow inv = new WojnaSwiatow();
			inv.game();
}

public void paintGameOver() {
	Graphics2D g = (Graphics2D)strategia. getDrawGraphics();
	g. setColor(Color.white);
	g. setFont(new Font("Arial",Font.BOLD,20));
	g. drawString("GAME OVER",Stage.SZEROKOSC/2-50,Stage.WYSOKOSC/2) ;
	strategia. show();
}

public void paintShields(Graphics2D g) {
	g. setPaint(Color.red);
	g. fillRect(280,Stage.WYSOKOSC_GRY,Player.MAX_SHIELDS,30);
	g. setPaint(Color.blue);
	g. fillRect(280+Player.MAX_SHIELDS-player. getShields(),Stage.WYSOKOSC_GRY,player.getShields(),30);
	g. setFont(new Font("Arial",Font.BOLD,20));
	g. setPaint(Color.green);
	g. drawString("Shields",170,Stage.WYSOKOSC_GRY+20);
}

public void paintScore(Graphics2D g) {
	g. setFont(new Font("Arial",Font.BOLD,20));
	g. setPaint(Color.green);
	g. drawString("Score:",20,Stage.WYSOKOSC_GRY + 20);
	g. setPaint(Color.red);
	g. drawString(player. getScore()+"",100,Stage.WYSOKOSC_GRY + 20);
}

public void paintAmmo(Graphics2D g) {
	int xBase = 280+Player.MAX_SHIELDS+10;
	for (int i = 0; i < player.getClusterBombs();i++) {
		BufferedImage bomb = spriteCache. getSprite("UL.png");
		g. drawImage( bomb ,xBase+i*bomb. getWidth(),Stage.WYSOKOSC_GRY, this);
	}
}

public void paintfps(Graphics2D g) {
	g. setFont( new Font("Arial",Font.BOLD,12));
	g. setColor(Color.white);
	if (usedTime > 0)
		g. drawString(String. valueOf(1000/usedTime)+" fps",Stage.SZEROKOSC-50,Stage.WYSOKOSC_GRY);
	else
		g. drawString("--- fps",Stage.WIDTH-50,Stage.WYSOKOSC_GRY);
}

public void paintStatus(Graphics2D g) {
	paintScore(g);
	paintShields(g);
	paintAmmo(g);
	paintfps(g);
}
}
//hello