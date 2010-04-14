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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.j3d.utils.applet.MainFrame;



public class GUI_Case extends MainFrame implements ActionListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Frame fileChooserPanel;
	Panel panel;
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
	
	CASE_VÅR_2010_Interface asdf;
	
	
	
	public GUI_Case(Applet applet,int arg1,int arg2 ){
		super(applet, arg1, arg2);
		asdf = (CASE_VÅR_2010_Interface)applet;
		setLayout(new BorderLayout());
		setSize(new Dimension(640, 550));
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
		fileChooserPanel = new Frame();
		
		//
		panel = new Panel();
		panel.setBackground(new Color(0f,0f,0));
	
		Label rotC_Label = new Label("Kube rotasjon");
		rotC_Label.setForeground(Color.white);
		boxRotSlider = new JSlider(500, 20000, 4000);
		boxRotSlider.setInverted(true);
		boxRotSlider.setBackground(Color.white);
		boxRotSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				alpha = boxRotSlider.getValue();
				
				asdf.setAlpha(alpha);
				
			}
		});
		Label rotS_Label = new Label("Sphere rotasjon");
		rotS_Label.setForeground(Color.white);
		sphereRotSlider = new JSlider(500, 20000, 4000);
		sphereRotSlider.setInverted(true);
		sphereRotSlider.setBackground(Color.white);
		sphereRotSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				alpha = sphereRotSlider.getValue();
				
				asdf.setAlpha(alpha);
				
			}
		});
		sphereRotSlider.setOpaque(false);
		Box rotBox = Box.createVerticalBox();
		Box itemBox = Box.createHorizontalBox();
		itemBox.add(rotC_Label);
		itemBox.add(boxRotSlider);
		Box itemBox_2 = Box.createHorizontalBox();
		itemBox_2.add(rotS_Label);
		itemBox_2.add(sphereRotSlider);
		rotBox.add(itemBox);
		rotBox.add(itemBox_2);
		panel.add(rotBox);
		//panel.add(rotC_Label);
		//panel.add(boxRotSlider);
		
		scaleBoxSlider = new JSlider(1,15,5);
		scaleBoxSlider.setName("Scale");
		scaleBoxSlider.setBackground(Color.white);
		scaleBoxSlider.setOpaque(false);
		scaleBoxSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				scaleMax = (float)scaleBoxSlider.getValue()/10;
				scaleMin = (float)scaleBoxSlider.getValue()/100;
				
				asdf.setScale(scaleMax, scaleMin);
			
			}
			
		});
		Label label = new Label("Scale");
		label.setForeground(Color.white);
		panel.add(label);
		panel.add(scaleBoxSlider);
		
		
		thisFile = null;
		
		this.setMenuBar(mb);
		this.add(panel, BorderLayout.SOUTH);
		add(new SlideShowPanel(),BorderLayout.CENTER);
		
		
		
		
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
			int valg =  fc.showOpenDialog(fileChooserPanel);
			if(valg == JFileChooser.APPROVE_OPTION){
				thisFile = fc.getSelectedFile();
				
			}
		
			Component[] componentene = 	this.getComponents();
			for(Component c:componentene){
				if(c instanceof Applet){
					((CASE_VÅR_2010_Interface) c).setImages(thisFile);
					
				}
			}
			
		}

		
	}
	
}
