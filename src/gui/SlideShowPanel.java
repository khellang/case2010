package gui;
import interfaceCASE.CASE_VÅR_2010_Interface;
import interfaceCASE.Case_Abstract;
import interfaceCASE.GUI_Case;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JToggleButton;
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

public class SlideShowPanel extends Applet implements MouseListener,CASE_VÅR_2010_Interface {
	protected File images;
	  protected Alpha alpha;
	  protected TransformGroup scaleTransGroup;
	  protected SphereGroup sphereGroup;
	  protected BoxGroup boxGroup;
	  protected Background background;
	  protected PickCanvas pc;
	  protected Appearance wallAp;
	  protected TextureList textures;
 
	public static void main(String[] args) {
		  
	    new GUI_Case(new SlideShowPanel(), 640, 480);
	  }

	public void init(){
		images = new File("C:/Users/Terje/Pictures/LifeCam Files");
		textures = new TextureList(images);
		System.out.println("INIT");
		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
	    Canvas3D cv = new Canvas3D(gc);
	    setLayout(new BorderLayout());
	    setPreferredSize(new Dimension(1024, 768));
	    add(cv, BorderLayout.CENTER);
	    BranchGroup bg = createSceneGraph();
	    bg.compile();
	    pc = new PickCanvas(cv, bg);
	    pc.setMode(PickTool.GEOMETRY);
	    SimpleUniverse su = new SimpleUniverse(cv);
	    su.getViewingPlatform().setNominalViewingTransform();
	    su.addBranchGraph(bg);
	    cv.addMouseListener(this);
	
	}

	private BranchGroup createSceneGraph() {
		BranchGroup root = new BranchGroup();

		BoundingSphere bounds = new BoundingSphere();
		
		Point3f lightPos = new Point3f(0f,0f,25f);
		Point3f wallPos = new Point3f(0f, 0f, -2f);
		int slideTime = 7000;
		
		TransformGroup wallGroup = new TransformGroup();
		root.addChild(wallGroup);
		
		wallAp = new Appearance();
		wallAp.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
		Shape3D wall = new Shape3D(new Wall(new Color3f(1f, 1f, 1f)), wallAp);
		wall.setPickable(false);
		wallGroup.addChild(wall);

		boxGroup = new BoxGroup(slideTime, bounds, textures, lightPos, wallPos, 0);
		root.addChild(boxGroup);

		sphereGroup = new SphereGroup(slideTime, bounds, textures, lightPos, wallPos, slideTime/2);
		root.addChild(sphereGroup);

		//background
		Background background = new Background(0.0f, 0.0f, 0.0f);
		background.setApplicationBounds(bounds);
		root.addChild(background);
		return root;
	}

	public void mouseClicked(MouseEvent evt) { }

	public void mouseEntered(MouseEvent arg0) {	}

	public void mouseExited(MouseEvent arg0) { }

	public void mousePressed(MouseEvent evt) {
		System.out.println("Pick");
		pc.setShapeLocation(evt);
		PickResult pick = pc.pickClosest();
		if (pick != null) {
			Node node = pick.getObject();
			if (node instanceof Shape3D) {
				Shape3D shape = (Shape3D)node;
				wallAp.setTexture(shape.getAppearance().getTexture());
			}
		}
	}

	public void mouseReleased(MouseEvent arg0) { }

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setImages(File images) {
		
		try {
			textures.setTextureList(images);
		} catch (NullPointerException e) {
			
		}
		
	}

	@Override
	public void setScale(float max, float min) {
		boxGroup.setScale(max,min );
		sphereGroup.setScale(max, min);
		
	}




}
