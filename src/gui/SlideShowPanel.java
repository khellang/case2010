package gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import shapes.Wall;
import transformGroups.BoxGroup;
import transformGroups.SphereGroup;
import utils.TextureList;

import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class SlideShowPanel extends JPanel implements MouseListener,
		CASE_VÅR_2010_Interface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected File images;
	protected Alpha alpha;
	protected TransformGroup scaleTransGroup;
	protected SphereGroup sphereGroup;
	protected BranchGroup[] shapeGroups;
	protected BoxGroup boxGroup;
	protected Background background;
	protected PickCanvas pc;
	protected Appearance wallAp;
	protected TextureList textures;
	protected Canvas3D cv;
	protected SimpleUniverse su;
	protected GraphicsConfiguration gc;
	protected BranchGroup root;
	protected BoundingSphere bounds;
	protected Point3f lightPos;
	protected Point3f wallPos;
	protected int slideTime;
	
	private int alphaBox;
	private int alphaSphere;
	private float minScaleBox;
	private float maxScaleBox;
	private float minScaleSphere;
	private float maxScaleSphere;

	public SlideShowPanel() {
		images = new File("C:/Users/Kristian/Pictures/galleri");
		textures = new TextureList(images);
		gc = SimpleUniverse.getPreferredConfiguration();
		cv = new Canvas3D(gc);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(1024, 600));
		add(cv, BorderLayout.CENTER);
		shapeGroups = new BranchGroup[2];
		BranchGroup bg = createSceneGraph();
//		bg.compile();
		pc = new PickCanvas(cv, bg);
		pc.setMode(PickTool.GEOMETRY);
		su = new SimpleUniverse(cv);
		su.getViewingPlatform().setNominalViewingTransform();

		su.addBranchGraph(bg);
		cv.addMouseListener(this);

	}

	private BranchGroup createSceneGraph() {
		root = new BranchGroup();
		root.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		root.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		bounds = new BoundingSphere();

		lightPos = new Point3f(0f, 0f, 25f);
		wallPos = new Point3f(0f, 0f, -2f);
		slideTime = 7000;

		TransformGroup wallGroup = new TransformGroup();
		root.addChild(wallGroup);

		wallAp = new Appearance();
		wallAp.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
		Shape3D wall = new Shape3D(new Wall(new Color3f(1f, 1f, 1f)), wallAp);
		wall.setPickable(false);
		wallGroup.addChild(wall);

//		boxGroup = new BoxGroup(slideTime, bounds, textures, lightPos, wallPos,
//				0);
//		root.addChild(boxGroup);
//
//		sphereGroup = new SphereGroup(slideTime, bounds, textures, lightPos,
//				wallPos, slideTime / 2);
//		root.addChild(sphereGroup);
		
		for (int i = 0; i < shapeGroups.length; i++) {
			if (i%2 == 0) {
				sphereGroup = new SphereGroup(slideTime, bounds, textures, lightPos, wallPos, i * (slideTime / shapeGroups.length));
				shapeGroups[i] = sphereGroup;
			} else {
				boxGroup = new BoxGroup(slideTime, bounds, textures, lightPos, wallPos, i * (slideTime / shapeGroups.length));
				shapeGroups[i] = boxGroup;
			}
		}
		
		sphereGroup.setCapability(BranchGroup.ALLOW_DETACH);
		boxGroup.setCapability(BranchGroup.ALLOW_DETACH);
		
		for (int i = 0; i < shapeGroups.length; i++) {
			root.addChild(shapeGroups[i]);
		}

		// background
		background = new Background(0f, 0f, 0f);
		background.setCapability(Background.ALLOW_COLOR_WRITE
				| Background.ALLOW_COLOR_READ);
		background.setApplicationBounds(bounds);
		root.addChild(background);
		return root;
	}

	public void mouseClicked(MouseEvent evt) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent evt) {
		pc.setShapeLocation(evt);
		PickResult pick = pc.pickClosest();
		if (pick != null) {
			Node node = pick.getObject();
			if (node instanceof Shape3D) {
				Shape3D shape = (Shape3D) node;
				wallAp.setTexture(shape.getAppearance().getTexture());
			}
		}
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void setImages(File images) {

		try {
			textures.setTextureList(images);
		} catch (NullPointerException e) {

		}

	}

	@Override
	public void setAlphaBox(int alpha) {
		alphaBox = alpha;
		for (int i = 0; i < shapeGroups.length; i++) {
			if (shapeGroups[i] instanceof BoxGroup) {
				BoxGroup bg = (BoxGroup)shapeGroups[i];
				bg.setRotTime(alpha);
			}	
		}
	}

	@Override
	public void setAlphaSpere(int alpha) {
		alphaSphere = alpha;
		for (int i = 0; i < shapeGroups.length; i++) {
			if (shapeGroups[i] instanceof SphereGroup) {
				SphereGroup bg = (SphereGroup)shapeGroups[i];
				bg.setRotTime(alpha);
			}	
		}
	}

	@Override
	public void setScaleBox(float max, float min) {
		minScaleBox = min;
		maxScaleBox = max;
		for (int i = 0; i < shapeGroups.length; i++) {
			if (shapeGroups[i] instanceof BoxGroup) {
				BoxGroup bg = (BoxGroup)shapeGroups[i];
				bg.setScale(max, min);
			}	
		}
	}

	@Override
	public void setScaleSphere(float max, float min) {
		minScaleSphere = min;
		maxScaleSphere = max;
		for (int i = 0; i < shapeGroups.length; i++) {
			if (shapeGroups[i] instanceof SphereGroup) {
				SphereGroup bg = (SphereGroup)shapeGroups[i];
				bg.setScale(max, min);
			}	
		}
	}

	public void setShapes(int numberOfShapes) {
		for (int i = 0; i < shapeGroups.length; i++) {
			shapeGroups[i].detach();
		}
		shapeGroups = new BranchGroup[numberOfShapes];
		for (int i = 0; i < shapeGroups.length; i++) {
			if (i%2 == 0) {
				SphereGroup sphereGroup = new SphereGroup(slideTime, bounds, textures, lightPos, wallPos, i * (slideTime / numberOfShapes));
				sphereGroup.setCapability(BranchGroup.ALLOW_DETACH);
				sphereGroup.setRotTime(alphaSphere);
				sphereGroup.setScale(maxScaleSphere, minScaleSphere);
				shapeGroups[i] = sphereGroup;
			} else {
				BoxGroup boxGroup = new BoxGroup(slideTime, bounds, textures, lightPos, wallPos, i * (slideTime / numberOfShapes));
				boxGroup.setCapability(BranchGroup.ALLOW_DETACH);
				boxGroup.setRotTime(alphaBox);
				boxGroup.setScale(maxScaleBox, minScaleBox);
				shapeGroups[i] = boxGroup;
			}
		}
		for (int i = 0; i < shapeGroups.length; i++) {
			root.addChild(shapeGroups[i]);
		}
	}

}
