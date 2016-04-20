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

public class WojnaSwiatow extends Canvas implements Stage, KeyListener {
	public long usedTime;
	public BufferStrategy strategia;
	private SpriteCache spriteCache;
	private ArrayList actors;
	private Player player;
public WojnaSwiatow() {
	spriteCache = new SpriteCache();
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
	Graphics2D g = (Graphics2D)strategia.getDrawGraphics();
	g.setColor(Color.black);
	g.fillRect(0,0,getWidth(),getHeight());
	for (int i = 0; i < actors.size(); i++) {
		Actor m = (Actor)actors.get(i);
		m.paint(g);
	}
	player.paint(g);
	g.setColor(Color.white);
	if (usedTime > 0)
		g.drawString(String.valueOf(1000/usedTime)+" fps",0,Stage.WYSOKOSC-50);
	else
		g.drawString("--- fps",0,Stage.WYSOKOSC-50);
		strategia.show();
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
	initWorld();

	while (isVisible()) {
		long startTime = System.currentTimeMillis();
		updateWorld();
		checkCollisions();
		paintWorld();
		usedTime = System.currentTimeMillis()-startTime;
		try {
			Thread.sleep(Stage.SZYBKOSC);
		} 
		catch (InterruptedException e) {}
	}
}
public static void main(String[] args) {
			WojnaSwiatow inv = new WojnaSwiatow();
			inv.game();
		}
}
//hello