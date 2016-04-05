import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;
public class SpriteCache {
public HashMap sprites;
public SpriteCache() {
sprites = new HashMap();
}
private BufferedImage loadImage(String url) {
	try {
		return ImageIO.read(new File(url));
	}
	catch (Exception e) {
		System.out.println("Przy otwieraniu " +" jako " + url);
		System.out.println("Wystapil blad : "+e.getClass().getName()+" "+e.getMessage());
		System.exit(0);
		return null;
	}
}

public BufferedImage getSprite(String sciezka) {
	BufferedImage img = (BufferedImage)sprites.get(sciezka);
	if (img == null) {
		img = loadImage("img/"+sciezka);
		sprites.put(sciezka,img);
	}
	return img;
}
//yo
}