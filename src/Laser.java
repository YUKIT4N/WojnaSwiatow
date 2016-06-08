public class Laser extends Actor {
	protected static final int BULLET_SPEED=3;
public Laser(Stage stage) {
	super(stage);
	setSpriteNames( new String[] {"p³atek1.png","p³atek2.png","p³atek3.png","p³atek4.png"});
	setFrameSpeed(10);
}
public void act() {
	super. act();
	y+=BULLET_SPEED;
	if (y > Stage.WYSOKOSC_GRY)
		remove();
}
}