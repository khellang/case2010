package utils;

import java.io.File;
import java.net.MalformedURLException;

import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;

import com.sun.j3d.utils.image.TextureLoader;

/**
 * Liste med teksturer som kan deles mellom flere <p>TextureInterpolator</p>-objekt.
 * @author Kristian Hellang
 * @version 1.0
 */
public class TextureList {

	private Texture2D[] textures;
	private int lastTexture;

	/**
	 * Oppretter en <p>TextureList</p> ut fra et array med <p>Texture2D</p>.
	 * @param textures array med teksturer.
	 */
	public TextureList(Texture2D[] textures) {
		this.textures = textures;
		this.lastTexture = -1;
	}

	/**
	 * Oppretter en <p>TextureList</p> ut fra ei gitt mappe.
	 * Bruker metoden <p>fileToTexture(File)</p> for å lage <p>Texture2D</p>
	 * fra filene i mappen.
	 * @param folder ei mappe med bildefiler.
	 */
	public TextureList(File folder) {
		if (!folder.isDirectory())
			throw new IllegalArgumentException(folder + " er ikke ei mappe!");
		else {
			File[] files = folder.listFiles();
			this.textures = new Texture2D[files.length];
			for (int i = 0; i < textures.length; i++) {
				textures[i] = fileToTexture(files[i]);
			}
		}
		this.lastTexture = -1;
	}

	/**
	 * Oppretter en <p>TextureList</p> ut fra et array med bildefiler.
	 * Bruker metoden <p>fileToTexture(File)</p> for å lage <p>Texture2D</p>
	 * fra en fil.
	 * @param files array med bildefiler.
	 */
	public TextureList(File[] files) {
		this.textures = new Texture2D[files.length];
		for (int i = 0; i < textures.length; i++) {
			textures[i] = fileToTexture(files[i]);
		}
		this.lastTexture = -1;
	}

	public Texture2D[] getTextures() {
		return textures;
	}

	public Texture2D nextTexture() {
		if (lastTexture == -1) {
			lastTexture = 0;
			return textures[lastTexture];
		} else {
			lastTexture++;
			lastTexture %= textures.length;
			return textures[lastTexture];
		}
	}

	public int size() {
		return textures.length;
	}

	private Texture2D fileToTexture(File file) {
		try {
//		BufferedImage bImage = new BufferedImage(200, 110, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D g = bImage.createGraphics();
//		g.setColor(Color.red);
//		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
//		g.drawString("KUK", 10, 40);
//		g.drawString("I KANO!", 10, 80);
//			TextureLoader loader = new TextureLoader(bImage);
			TextureLoader loader = new TextureLoader(file.toURI().toURL(), null);
			ImageComponent2D image = loader.getImage();
			Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
					image.getWidth(), image.getHeight());
			texture.setImage(0, image);
			texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
			texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
			return texture;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
