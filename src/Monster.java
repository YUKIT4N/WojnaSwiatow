public class Monster extends Actor {
protected int vx;
public Monster(Stage stage) {
	super(stage);
	//setSpriteNames( new String[] {"potworek1.gif","potworek0.gif"});
	setSpriteNames( new String[] {"dzik1.png","stalag1.png"});
	setFrameSpeed(50);
	}
public void act() {
	super.act();
	x+=vx;
	if (x < 0 || x > Stage.SZEROKOSC)
		vx = -vx;
}

	public int getVx() { return vx; }
	
	public void setVx(int i) {vx = i; }
	
	public void collision(Actor a) {
		if (a instanceof Bullet || a instanceof Bomb)
		remove();
		}
}