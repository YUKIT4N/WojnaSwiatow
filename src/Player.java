import java.awt.Graphics2D;

public class Player extends Actor {
	protected int vx;
	protected int vy;
	
public Player(Stage stage) {
		super(stage);
		setSpriteNames( new String[] {"potworek.gif"});
}
public void act() {
	super.act();
	x+=vx;
	y+=vy;
	if (x < 0 || x > Stage.SZEROKOSC)
		vx = -vx;
	if (y < 0 || y > Stage.WYSOKOSC)
		vy = -vy;
}

public int getVx() { return vx; }
public void setVx(int i) {vx = i; }
public int getVy() { return vy; }
public void setVy(int i) {vy = i; }

}