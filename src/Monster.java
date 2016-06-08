public class Monster extends Actor {
protected int vx;
protected static final double FIRING_FREQUENCY = 0.01;
public Monster(Stage stage) {
	super(stage);
	//setSpriteNames( new String[] {"potworek1.gif","potworek0.gif"});
	setSpriteNames( new String[] {"dzik1.png","stalag1.png"});
	setFrameSpeed(50);
	}
public void fire() {
	Laser m = new Laser(stage);
	m. setX(x+getWidth()/2);
	m. setY(y + getHeight());
	stage. addActor(m);
}
public void act() {
	super. act();
	x+=vx;
	if (x < 0 || x > Stage.SZEROKOSC)
		vx = -vx;
	if (Math. random()<FIRING_FREQUENCY)
		fire();
}

	public int getVx() { return vx; }
	
	public void setVx(int i) {vx = i; }
	
public void spawn() {
	Monster m = new Monster(stage);
	m. setX( (int)(Math. random()*Stage.SZEROKOSC) );
	m. setY( (int)(Math. random()*Stage.WYSOKOSC_GRY/2) );
	m. setVx( (int)(Math. random()*15-10)+1);
	stage. addActor(m);
}
	
public void collision(Actor a) {
	if (a instanceof Bullet || a instanceof Bomb){
	remove();
	spawn();
	stage. getPlayer(). addScore(20);
	}
}
}