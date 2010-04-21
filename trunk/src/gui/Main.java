package gui;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Main extends JFrame implements ActionListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame chooserPanel;
	JPanel panel;
	JFileChooser fc;
	JColorChooser cc;
	JMenuBar mb;
	JMenu mn;
	JMenuItem mi;
	File thisFile;
	JSlider boxRotSlider;
	JSlider sphereRotSlider;
	JSlider scaleBoxSlider;
	JSlider scaleSphereSlider;
	JSlider shapesSlider;
	JSlider speedSlider;
	int alpha;
	float scaleMax;
	float scaleMin;
	Color color;
	JLabel roteringLabel;
	JLabel skaleringLabel;

	
	
	SlideShowImpl asdf;
	
	
	
	public Main(JPanel casePanel,int arg1,int arg2 ){
		super("CASE 2010");
		asdf = (SlideShowImpl)casePanel;
		setSize(new Dimension(arg1, arg2));
		color = new Color(0,0,0);
		//Setter opp menyen
		mb = new JMenuBar();
		mn = new JMenu("Fil");
		mi = new JMenuItem("Åpne");
		mi.addActionListener(this);
		mn.add(mi);
		mb.add(mn);
		mn = new JMenu("Valg");
		mi = new JMenuItem("Kontrollpanel farge");
		mi.addActionListener(this);
		mn.add(mi);
		mb.add(mn);
		chooserPanel = new JFrame();
		//
		panel = new JPanel();
		panel.setBackground(color);
		skaleringLabel = new JLabel("          Skalering");
		skaleringLabel.setForeground(Color.white);		
		roteringLabel = new JLabel("          Rotasjon");
		roteringLabel.setForeground(Color.white);
		shapesSlider = new JSlider(1,10,1);
		shapesSlider.setValue(2);
		shapesSlider.setMajorTickSpacing(1);
		shapesSlider.setInverted(false);
		shapesSlider.setPaintTicks(true);
//		shapesSlider.setPaintTrack(false);
		shapesSlider.setSnapToTicks(true);
		shapesSlider.setOpaque(false);
		shapesSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				
				asdf.setShapes(shapesSlider.getValue());
				scaleBoxSlider.setMaximum(11-shapesSlider.getValue());
				scaleSphereSlider.setMaximum(11-shapesSlider.getValue());
				
			}
		});
		speedSlider = new JSlider(1,10,7);
		speedSlider.setMajorTickSpacing(1);
		speedSlider.setInverted(true);
		speedSlider.setPaintTicks(true);
//		speedSlider.setPaintTrack(false);
		speedSlider.setSnapToTicks(true);
		speedSlider.setOpaque(false);
		speedSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				
				asdf.setSpeed(speedSlider.getValue()*1000);
				
			}
		});
		boxRotSlider = new JSlider(1,16 , 5);
		boxRotSlider.setMajorTickSpacing(5);
		boxRotSlider.setMinorTickSpacing(1);
		boxRotSlider.setInverted(true);
		boxRotSlider.setPaintTicks(true);
//		boxRotSlider.setPaintTrack(false);
		boxRotSlider.setSnapToTicks(true);
		boxRotSlider.setOpaque(false);
		boxRotSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				alpha = boxRotSlider.getValue()*1000;
				
				asdf.setAlphaBox(alpha);
				
			}
		});
		sphereRotSlider = new JSlider(1, 16, 5);
		sphereRotSlider.setMajorTickSpacing(5);
		sphereRotSlider.setMinorTickSpacing(1);
		sphereRotSlider.setPaintTicks(true);
//		sphereRotSlider.setPaintTrack(false);
		sphereRotSlider.setInverted(true);
		sphereRotSlider.setSnapToTicks(true);
		sphereRotSlider.setOpaque(false);
		sphereRotSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				alpha = sphereRotSlider.getValue()*1000;
				
				asdf.setAlphaSpere(alpha);
				
			}
		});
		scaleBoxSlider = new JSlider(1,9,3);
		scaleBoxSlider.setMajorTickSpacing(3);
		scaleBoxSlider.setMinorTickSpacing(1);
		scaleBoxSlider.setPaintTicks(true);
//		scaleBoxSlider.setPaintTrack(false);
		scaleBoxSlider.setOpaque(false);
		scaleBoxSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				scaleMax = (float)scaleBoxSlider.getValue()/10;
				scaleMin = (float)scaleBoxSlider.getValue()/1000;
				asdf.setScaleBox(scaleMax, scaleMin);
			}
		});
		scaleSphereSlider = new JSlider(1,9,3);
		scaleSphereSlider.setMajorTickSpacing(3);
		scaleSphereSlider.setMinorTickSpacing(1);
		scaleSphereSlider.setPaintTicks(true);
//		scaleSphereSlider.setPaintTrack(false);
		scaleSphereSlider.setOpaque(false);
		scaleSphereSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				scaleMax = (float)scaleSphereSlider.getValue()/10;
				scaleMin = (float)scaleSphereSlider.getValue()/1000;
				asdf.setScaleSphere(scaleMax, scaleMin);
			}
		});
		thisFile = null;
		JPanel helperPanel = new JPanel(new BorderLayout());
		helperPanel.setOpaque(false);
		Box rotBox = Box.createVerticalBox();
		rotBox.add(roteringLabel);
		rotBox.add(sphereRotSlider);
		rotBox.add(boxRotSlider);
		Box scaleBox = Box.createVerticalBox();
		scaleBox.add(skaleringLabel);
		scaleBox.add(scaleSphereSlider);
		scaleBox.add(scaleBoxSlider);
		helperPanel.add(rotBox, BorderLayout.WEST);
		helperPanel.add(scaleBox, BorderLayout.EAST);
		Box shapesBox = Box.createHorizontalBox();
		shapesBox.add(shapesSlider);
		shapesBox.add(speedSlider);
		helperPanel.add(shapesBox, BorderLayout.SOUTH);
		panel.add(helperPanel);
		
		setJMenuBar(mb);
		setLayout(new BorderLayout());
		add(panel,BorderLayout.SOUTH);
		add((Component) asdf,BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
		
		
	}
	private void setColors(Color color){
		panel.setBackground(color);
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
			
			
		
		else if("Kontrollpanel farge".equals(command)){
			color = JColorChooser.showDialog(this, "Velg en farge", color);
			if(color == null){
				color = new Color(0,0,0);
			}
			setColors(color);
			
		}

		
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new Main(new SlideShowPanel(), 1024, 768);
	}
	
}
