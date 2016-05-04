import java.awt.image.ImageObserver;

public interface Stage extends ImageObserver {
	public static final int SZEROKOSC = 800;
	public static final int WYSOKOSC = 600;
	public static final int SZYBKOSC = 10;
	public static final int WYSOKOSC_GRY = 500;
	public SpriteCache getSpriteCache();
	public void gameOver();
	public void addActor(Actor a);
	public Player getPlayer();
}