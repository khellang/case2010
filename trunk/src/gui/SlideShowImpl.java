package gui;

import java.io.File;

public interface SlideShowImpl {
	public void setAlphaBox(int alpha);
	public void setAlphaSpere(int alpha);
	public void setScaleBox(float max, float min);
	public void setScaleSphere(float max, float min);
	public void setImages(File  images);
	public void setShapes(int numberOfShapes);
	public void setSpeed(int speed);
}
