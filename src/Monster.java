public class Monster extends Actor {
protected int vx;
public Monster(Stage stage) {
	super(stage);
	setSpriteNames( new String[] {"potworek1.gif","potworek0.gif"});
	setFrameSpeed(25);
	}
public void act() {
	super.act();
	x+=vx;
	if (x < 0 || x > Stage.SZEROKOSC)
		vx = -vx;
}

	public int getVx() { return vx; }
	
	public void setVx(int i) {vx = i; }
}