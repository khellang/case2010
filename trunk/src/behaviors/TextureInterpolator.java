package behaviors;

import java.util.Enumeration;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.Interpolator;
import javax.media.j3d.WakeupOnElapsedFrames;

import utils.TextureList;
import utils.Timer;

/**
 * En <p>Interpolator</p> som veksler mellom forskjellige teksturer
 * hver gang dens alpha-verdi blir <b>lik 1.0</b>.
 * @author Kristian Hellang
 * @version 1.0
 */
public class TextureInterpolator extends Interpolator {

	private TextureList textures;
	private Appearance app;

	private Timer changeTimer;

	/**
	 * Oppretter en <p>TextureInterpolator</p> med oppgitt liste over teksturer.
	 * @param alpha interpolatorens alpha-objekt.
	 * @param app appearance som teksturen skal endres på.
	 * @param files textures liste med teksturer.
	 */
	public TextureInterpolator(Alpha alpha, Appearance app, TextureList textures) {
		super(alpha);
		this.app = app;
		this.textures = textures;
		changeTimer = new Timer(1000);
	}

	public TextureList getTextures() {
		return textures;
	}

	public void setTextures(TextureList textures) {
		this.textures = textures;
	}

	public Appearance getAppearance() {
		return app;
	}

	public void setAppearance(Appearance app) {
		this.app = app;
	}


	/**
	 * Overkjøring av metoden <p>initialize()</p> arvet fra
	 * <p>Behavior</p>. Denne metoden setter teksturen til den
	 * første på lista før den starter.
	 */
	public void initialize() {
		app.setTexture(textures.nextTexture());
		wakeupOn(new WakeupOnElapsedFrames(0));
	}

	/**
	 * Implementasjon av metoden <p>processStimulus(Enumeration)</p>
	 * arvet fra <p>Behavior</p>.
	 */
	@SuppressWarnings("unchecked")
	public void processStimulus(Enumeration arg) {
		if (getAlpha().value() >= 0.96) {
			if (!changeTimer.isAlive()) {
				app.setTexture(textures.nextTexture());
				changeTimer = new Timer(1000);
				changeTimer.start();
			}
		}
		wakeupOn(new WakeupOnElapsedFrames(10));
	}

}
