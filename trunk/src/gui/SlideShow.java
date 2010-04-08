package gui;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class SlideShow extends JFrame {

	private SlideShowPanel slideShowPanel;

	public SlideShow() {

		slideShowPanel = new SlideShowPanel();
		add(slideShowPanel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}		} catch (Exception ignored) {}
			new SlideShow();
	}

}
