package behaviors;

import java.util.Enumeration;
import java.util.Random;

import javax.media.j3d.Alpha;
import javax.media.j3d.Interpolator;
import javax.media.j3d.PositionInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnElapsedFrames;

public class RandomRotationInterpolator extends Interpolator {
	
	TransformGroup target;
	PositionInterpolator posTarget;
	
	public RandomRotationInterpolator(Alpha alpha, TransformGroup target) {
		super(alpha);
		this.target = target;
	}
	
	public RandomRotationInterpolator(Alpha alpha, PositionInterpolator target) {
		super(alpha);
		this.posTarget = target;
	}
	
	public void initialize() {
		wakeupOn(new WakeupOnElapsedFrames(0));
	}

	@SuppressWarnings("unchecked")
	public void processStimulus(Enumeration arg0) {
		if (getAlpha().value() >= 0.95 || getAlpha().value() <= 0.05) {
			try {
				Transform3D rotate = new Transform3D();
				Random rnd = new Random();
				rotate.rotZ(rnd.nextDouble()*2*Math.PI);
//			target.setTransform(rotate);
				posTarget.setTransformAxis(rotate);
			} catch (NullPointerException e) {
//				e.printStackTrace();
			}
		}
		wakeupOn(new WakeupOnElapsedFrames(10));
	}

}
