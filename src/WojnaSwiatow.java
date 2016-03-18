import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JPanel;
import java.util.HashMap;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
public class WojnaSwiatow extends Canvas{
	
	public static final int SZEROKOSC = 800;
	public static final int WYSOKOSC = 600;
	public HashMap sprites;

	public WojnaSwiatow() {
		sprites = new HashMap();
		JFrame okno = new JFrame(".:Wojna Swiatow:.");
		JPanel panel = (JPanel)okno.getContentPane();
		setBounds(0,0,SZEROKOSC,WYSOKOSC);
		panel.setPreferredSize(new Dimension(SZEROKOSC,WYSOKOSC));
		panel.setLayout(null);
		panel.add(this);
		okno.setBounds(0,0,SZEROKOSC,WYSOKOSC);
		okno.setVisible(true);
		okno.addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	public void paint(Graphics g){
		g.drawImage(getSprite("potworek.jpg"), 40, 40,this);
	}
	
	public static void main(String[] args) {
		WojnaSwiatow inv = new WojnaSwiatow();
	}
	
	public BufferedImage loadImage(String url) {
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