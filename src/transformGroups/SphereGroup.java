package transformGroups;
import java.util.Random;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.GeometryUpdater;
import javax.media.j3d.IndexedTriangleArray;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.PositionInterpolator;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.ScaleInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.media.j3d.WakeupOnElapsedFrames;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Point4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4d;

import utils.TextureList;
import behaviors.RandomRotationInterpolator;
import behaviors.TextureInterpolator;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.Sphere;

public class SphereGroup extends BranchGroup {

	TransformGroup randomRotGroup;
	TransformGroup positionGroup;
	TransformGroup scaleGroup;
	TransformGroup rotateGroup;
	TransformGroup shadowGroup;
	ShadowUpdater updater;
	GeometryArray geom;
	GeometryArray shadowGeom;
	Transform3D shadowProj;
	ScaleInterpolator scaler;
	Alpha rotateAlpha;
	Alpha sphereTextureAlpha;
	Alpha scaleAlpha;
	Alpha positionAlpha;
	Alpha randomRotateAlpha;
	
	Appearance shadowAp;
	
	public SphereGroup(int slideTime, BoundingSphere bounds, TextureList textures, Point3f lightPos, Point3f wallPos, int delay) {
		
		updater = new ShadowUpdater();
		
		this.setCapability(BranchGroup.ALLOW_DETACH);
		
		shadowGroup = new TransformGroup();
		addChild(shadowGroup);
		
		//randomRotGroup
		randomRotGroup = new TransformGroup();
		randomRotGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		randomRotGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		addChild(randomRotGroup);
		
		//positionGroup
		positionGroup = new TransformGroup();
		positionGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		positionGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		randomRotGroup.addChild(positionGroup);

		//scaleGroup
		scaleGroup = new TransformGroup();
		scaleGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		scaleGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		positionGroup.addChild(scaleGroup);

		//rotateGroup
		rotateGroup = new TransformGroup();
		rotateGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		rotateGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		scaleGroup.addChild(rotateGroup);
		
		// Sphere
		Appearance sphereAp = new Appearance();
		sphereAp.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
		Sphere sphere = new Sphere(0.4f, Sphere.GENERATE_TEXTURE_COORDS, sphereAp);
		geom = (GeometryArray) sphere.getShape().getGeometry();
		geom.setCapability(GeometryArray.ALLOW_COORDINATE_READ);
//		Box asf = new Box(0.2f, 0.2f, 0.2f, Primitive.GENERATE_TEXTURE_COORDS, sphereAp);
		rotateGroup.addChild(sphere);
		
		shadowAp = new Appearance();
		shadowAp.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
		shadowGeom = createShadow(geom, lightPos, wallPos);
		ColoringAttributes colorAttr = new ColoringAttributes(0.1f, 0.1f, 0.1f, 
				ColoringAttributes.FASTEST);
		shadowAp.setColoringAttributes(colorAttr);
		TransparencyAttributes transAttr = new TransparencyAttributes(
				TransparencyAttributes.BLENDED,0.35f);
		shadowAp.setTransparencyAttributes(transAttr);
		PolygonAttributes polyAttr = new PolygonAttributes();
		polyAttr.setCullFace(PolygonAttributes.CULL_NONE);
		shadowAp.setPolygonAttributes(polyAttr);
		Shape3D shape = new Shape3D(shadowGeom, shadowAp);
		shape.setPickable(false);
		shadowGroup.addChild(shape);
		
		//texturizer
		sphereTextureAlpha = new Alpha();
		TextureInterpolator sphereTexturizer = new TextureInterpolator(sphereTextureAlpha, sphereAp, textures);
		sphereTextureAlpha.setMode(Alpha.INCREASING_ENABLE);
		sphereTextureAlpha.setIncreasingAlphaDuration(slideTime);
		sphereTextureAlpha.setTriggerTime(delay);
		sphereTexturizer.setSchedulingBounds(bounds);
		rotateGroup.addChild(sphereTexturizer);

		//positioner
		positionAlpha = new Alpha();
		positionAlpha.setMode(Alpha.DECREASING_ENABLE | Alpha.INCREASING_ENABLE);
		positionAlpha.setIncreasingAlphaDuration(slideTime);
		positionAlpha.setDecreasingAlphaDuration(slideTime);
		positionAlpha.setTriggerTime(delay);
		Transform3D positionTransform = new Transform3D();
		Random rnd = new Random();
		positionTransform.rotZ(rnd.nextDouble()*(2*Math.PI));
		PositionInterpolator positioner = new PositionInterpolator(positionAlpha, positionGroup, positionTransform, 2f, -2f);
		positioner.setSchedulingBounds(bounds);
		positionGroup.addChild(positioner);
		
		//randomRotator
		randomRotateAlpha = new Alpha();
		randomRotateAlpha.setMode(Alpha.DECREASING_ENABLE | Alpha.INCREASING_ENABLE);
		randomRotateAlpha.setDecreasingAlphaDuration(slideTime);
		randomRotateAlpha.setIncreasingAlphaDuration(slideTime);
		randomRotateAlpha.setTriggerTime(delay);
		RandomRotationInterpolator randomRotator = new RandomRotationInterpolator(randomRotateAlpha, positioner);
		randomRotator.setSchedulingBounds(bounds);
		randomRotGroup.addChild(randomRotator);

		//scaler
		scaleAlpha = new Alpha();
		scaleAlpha.setMode(Alpha.DECREASING_ENABLE | Alpha.INCREASING_ENABLE);
		scaleAlpha.setIncreasingAlphaDuration(slideTime/2);
		scaleAlpha.setDecreasingAlphaDuration(slideTime/2);
		scaleAlpha.setIncreasingAlphaRampDuration(slideTime/10);
		scaleAlpha.setDecreasingAlphaRampDuration(slideTime/10);
		scaleAlpha.setTriggerTime(delay);
		scaler = new ScaleInterpolator(scaleAlpha, scaleGroup);
		scaler.setSchedulingBounds(bounds);
		scaler.setMinimumScale(0.1f);
		scaler.setMaximumScale(0.8f);
		scaleGroup.addChild(scaler);

		//rotator
	    rotateAlpha = new Alpha();
		rotateAlpha.setMode(Alpha.DECREASING_ENABLE | Alpha.INCREASING_ENABLE);
		rotateAlpha.setDecreasingAlphaDuration(slideTime);
		rotateAlpha.setIncreasingAlphaDuration(slideTime);
		rotateAlpha.setTriggerTime(delay);
		RotationInterpolator rotator = new RotationInterpolator(rotateAlpha, rotateGroup);
		rotator.setSchedulingBounds(bounds);
		rotateGroup.addChild(rotator);
		
		ShadowBehavior sb = new ShadowBehavior();
		sb.setSchedulingBounds(bounds);
		shadowGroup.addChild(sb);
		
	}
	
	private GeometryArray createShadow(GeometryArray ga, Point3f light, Point3f plane) {
		GeometryInfo gi = new GeometryInfo(ga);
		gi.convertToIndexedTriangles();
		IndexedTriangleArray ita = (IndexedTriangleArray)gi.getIndexedGeometryArray();
		Vector3f v = new Vector3f();
		v.sub(plane, light);
		double[] mat = new double[16];
		for (int i = 0; i < 16; i++) {
			mat[i] = 0;
		}
		mat[0] = 1;
		mat[5] = 1;
		mat[10] = 1-0.001;
		mat[14] = -1/v.length();
		Transform3D proj = new Transform3D();
		proj.set(mat);
		Transform3D u = new Transform3D();
		u.lookAt(new Point3d(light), new Point3d(plane), new Vector3d(0,1,0));
		proj.mul(u);
		shadowProj = new Transform3D();
		u.invert();
		shadowProj.mul(u, proj);
		int n = ita.getVertexCount();
		int count = ita.getIndexCount();
		IndexedTriangleArray shadow = new IndexedTriangleArray(n,
				GeometryArray.COORDINATES | GeometryArray.BY_REFERENCE, count);
		shadow.setCapability(GeometryArray.ALLOW_REF_DATA_READ);
		shadow.setCapability(GeometryArray.ALLOW_REF_DATA_WRITE);
		double[] vert = new double[3*n];
		Point3d p = new Point3d();
		for (int i = 0; i < n; i++) {
			ga.getCoordinate(i, p);
			Vector4d v4 = new Vector4d(p);
			v4.w = 1;
			shadowProj.transform(v4);
			Point4d p4 = new Point4d(v4);
			p.project(p4);
			vert[3*i] = p.x;
			vert[3*i+1] = p.y;
			vert[3*i+2] = p.z;
		}
		shadow.setCoordRefDouble(vert);
		int[] indices = new int[count];
		ita.getCoordinateIndices(0, indices);
		shadow.setCoordinateIndices(0, indices);
		return shadow;
	}
	
	public void setSpeed(int speed) {
		sphereTextureAlpha.setIncreasingAlphaDuration(speed);
		positionAlpha.setIncreasingAlphaDuration(speed);
		positionAlpha.setDecreasingAlphaDuration(speed);
		randomRotateAlpha.setDecreasingAlphaDuration(speed);
		randomRotateAlpha.setIncreasingAlphaDuration(speed);
		scaleAlpha.setIncreasingAlphaDuration(speed/2);
		scaleAlpha.setDecreasingAlphaDuration(speed/2);
		scaleAlpha.setIncreasingAlphaRampDuration(speed/10);
		scaleAlpha.setDecreasingAlphaRampDuration(speed/10);
	}
	
	public void showShadow(boolean show) {
		if (show) {
			shadowAp.setTransparencyAttributes(new TransparencyAttributes(
					TransparencyAttributes.BLENDED,0.35f));
		} else
			shadowAp.setTransparencyAttributes(new TransparencyAttributes(
					TransparencyAttributes.BLENDED,1.0f));
	}
	
	class ShadowUpdater implements GeometryUpdater {    
		public void updateData(Geometry geometry) {
			double[] vert = ((GeometryArray)geometry).getCoordRefDouble();
			int n = vert.length/3;
			Transform3D randomRot = new Transform3D();
			randomRotGroup.getTransform(randomRot);
			Transform3D position = new Transform3D();
			positionGroup.getTransform(position);
			Transform3D scale = new Transform3D();
			scaleGroup.getTransform(scale);
			Transform3D rot = new Transform3D();
			rotateGroup.getTransform(rot);
			Transform3D tr = new Transform3D(shadowProj);
//			tr.mul(randomRot);
			tr.mul(position);
			tr.mul(scale);
			tr.mul(rot);
			Point3d p = new Point3d();
			for (int i = 0; i < n; i++) {
				geom.getCoordinate(i, p);
				Vector4d v4 = new Vector4d(p);
				v4.w = 1;
				tr.transform(v4);
				Point4d p4 = new Point4d(v4);
				p.project(p4);
				if (p.x > 1.5)
					vert[3*i] = 1.5;
				else if (p.x < -1.5)
					vert[3*i] = -1.5;
				else
					vert[3*i] = p.x;
				if (p.y > 1)
					vert[3*i+1] = 1;
				else if (p.y < -1)
					vert[3*i+1] = -1;
				else
					vert[3*i+1] = p.y;
				vert[3*i+2] = p.z;
			}
		}
	}
	
	class ShadowBehavior extends Behavior {
		WakeupOnElapsedFrames wakeup = null;

		public ShadowBehavior() {
			wakeup = new WakeupOnElapsedFrames(0);
		}

		public void initialize() {
			wakeupOn(wakeup);
		}

		@SuppressWarnings("unchecked")
		public void processStimulus(java.util.Enumeration enumeration) {
			shadowGeom.updateData(updater);
			wakeupOn(wakeup);
		}    
	}

	public void setScale(float max, float min) {
		scaler.setMaximumScale(max);
		scaler.setMinimumScale(min);
		
	}

	public void setRotTime(int rotTime) {
		rotateAlpha.setIncreasingAlphaDuration(rotTime);
		rotateAlpha.setDecreasingAlphaDuration(-rotTime);
		
	}
	
}
