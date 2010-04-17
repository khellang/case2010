package interfaceCASE;

import gui.SlideShowPanel;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.applet.MainFrame;



public class GUI_Case extends JFrame implements ActionListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame chooserPanel;
	JPanel panel;
	JFileChooser fc;
	JColorChooser cc;
	MenuBar mb;
	Menu mn;
	MenuItem mi;
	File thisFile;
	JSlider boxRotSlider;
	JSlider sphereRotSlider;
	JSlider scaleBoxSlider;
	JSlider scaleSphereSlider;
	int alpha;
	float scaleMax;
	float scaleMin;
	Color color = new Color(0f,0f,0f);
	Label rotC_Label;
	Label rotS_Label;
	Label scaleC_Label;
	Label scaleS_Label;
	
	
	CASE_VÅR_2010_Interface asdf;
	
	
	
	public GUI_Case(JPanel casePanel,int arg1,int arg2 ){
		super("CASE 2010");
		asdf = (CASE_VÅR_2010_Interface)casePanel;
		setSize(new Dimension(arg1, arg2));
		//Setter opp menyen
		mb = new MenuBar();
		mn = new Menu("Fil");
		mi = new MenuItem("Åpne");
		mi.addActionListener(this);
		mn.add(mi);
		mb.add(mn);
		mn = new Menu("Ledig meny");
		mi = new MenuItem("Endre bakgrunn");
		mi.addActionListener(this);
		mn.add(mi);
		mi = new MenuItem("Item m/lytter");
		mi.addActionListener(this);
		mn.add(mi);
		mb.add(mn);
		//Panel til fileChooser
		chooserPanel = new JFrame();
		//
		panel = new JPanel();
		panel.setBackground(color);
		panel.setOpaque(true);
		rotC_Label = new Label("Kube rotasjon");
		rotC_Label.setForeground(Color.white);
		rotC_Label.setBackground(color);
		boxRotSlider = new JSlider(1000,10000 , 4500);
		boxRotSlider.setMajorTickSpacing(500);
		boxRotSlider.setInverted(true);
		boxRotSlider.setBackground(Color.white);
		boxRotSlider.setOpaque(false);
		boxRotSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				alpha = boxRotSlider.getValue();
				
				asdf.setAlphaBox(alpha);
				
			}
		});
		rotS_Label = new Label("Sphere rotasjon");
		rotS_Label.setForeground(Color.white);
		rotS_Label.setBackground(color);
		sphereRotSlider = new JSlider(500, 20000, 4000);
		sphereRotSlider.setInverted(true);
		sphereRotSlider.setBackground(Color.white);
		sphereRotSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				alpha = sphereRotSlider.getValue();
				
				asdf.setAlphaSpere(alpha);
				
			}
		});
		sphereRotSlider.setOpaque(false);
		scaleC_Label = new Label("Cube scale");
		scaleC_Label.setForeground(Color.white);
		scaleC_Label.setBackground(color);
		
		scaleBoxSlider = new JSlider(1,15,5);
		scaleBoxSlider.setName("Scale");
		scaleBoxSlider.setBackground(Color.white);
		scaleBoxSlider.setOpaque(false);
		scaleBoxSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				scaleMax = (float)scaleBoxSlider.getValue()/10;
				scaleMin = (float)scaleBoxSlider.getValue()/100;
				
				asdf.setScaleBox(scaleMax, scaleMin);
				
			}
			
		});
		scaleS_Label = new Label("Sphere scale");
		scaleS_Label.setForeground(Color.white);
		scaleS_Label.setBackground(color);
		scaleSphereSlider = new JSlider(1,15,5);
		scaleSphereSlider.setName("Scale");
		scaleSphereSlider.setBackground(Color.white);
		scaleSphereSlider.setOpaque(false);
		scaleSphereSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				scaleMax = (float)scaleSphereSlider.getValue()/10;
				scaleMin = (float)scaleSphereSlider.getValue()/100;
				
				asdf.setScaleSphere(scaleMax, scaleMin);
			
			}
			
		});
		
		thisFile = null;
		Box rotBox = Box.createVerticalBox();
		Box itemBox = Box.createHorizontalBox();
		itemBox.add(rotC_Label);
		itemBox.add(boxRotSlider);
		Box itemBox_2 = Box.createHorizontalBox();
		itemBox_2.add(rotS_Label);
		itemBox_2.add(sphereRotSlider);
		rotBox.add(itemBox);
		rotBox.add(itemBox_2);
		
		Box scaleBox = Box.createVerticalBox();
		Box anotherItemBox = Box.createHorizontalBox();
		anotherItemBox.add(scaleC_Label);
		anotherItemBox.add(scaleBoxSlider);
		Box anotherItemBox_2 = Box.createHorizontalBox();
		anotherItemBox_2.add(scaleS_Label);
		anotherItemBox_2.add(scaleSphereSlider);
		scaleBox.add(anotherItemBox);
		scaleBox.add(anotherItemBox_2);
		panel.add(rotBox);
		panel.add(scaleBox);
		
		setMenuBar(mb);
		setLayout(new BorderLayout());
		add(panel,BorderLayout.SOUTH);
		add((Component) asdf,BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
		
		
		
	}
	private void setColors(Color color){
	//	Color3f color3f = new Color3f(color.getRed(),color.getGreen(),color.getBlue());
	//	asdf.setBackGroundColor3F(color3f);
		panel.setBackground(color);
		rotC_Label.setBackground(color);
		rotS_Label.setBackground(color);
		scaleS_Label.setBackground(color);
		scaleC_Label.setBackground(color);
		validate();
	}
	

	public File getFile(){
		return thisFile;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if("Åpne".equals(command)){
			fc = new JFileChooser();
	
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if(!(thisFile == null))
				fc.setCurrentDirectory(thisFile);
			int valg =  fc.showOpenDialog(chooserPanel);
			if(valg == JFileChooser.APPROVE_OPTION){
				thisFile = fc.getSelectedFile();
				
			}
		

					asdf.setImages(thisFile);
					
				}
			
			
		
		else if("Endre bakgrunn".equals(command)){
			color = JColorChooser.showDialog(this, "Velg en farge", color);
			if(color == null){
				color = new Color(0,0,0);
			}
			setColors(color);
			
		}

		
	}
	
}
