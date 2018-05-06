package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

//VS4E -- DO NOT REMOVE THIS LINE!
public class TableFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel0;
	private JPanel PaiPane1;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JLabel jLabel10;
	private JLabel jLabel11;
	private JLabel jLabel12;
	private JLabel jLabel13;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	public TableFrame() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Bilateral(12, 12, 6), new Trailing(12, 140, 10, 10)));
		add(getJPanel2(), new Constraints(new Leading(15, 558, 10, 10), new Leading(12, 68, 10, 10)));
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}
		});
		setSize(799, 346);
	}

	private JLabel getJLabel13() {
		if (jLabel13 == null) {
			jLabel13 = new JLabel();
			jLabel13.setText("13");
			jLabel13.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		}
		return jLabel13;
	}

	private JLabel getJLabel12() {
		if (jLabel12 == null) {
			jLabel12 = new JLabel();
			jLabel12.setText("12");
			jLabel12.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		}
		return jLabel12;
	}

	private JLabel getJLabel11() {
		if (jLabel11 == null) {
			jLabel11 = new JLabel();
			jLabel11.setText("11");
			jLabel11.setBorder(new LineBorder(Color.black, 1, false));
		}
		return jLabel11;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("10");
			jLabel10.setBorder(new LineBorder(Color.black, 1, false));
		}
		return jLabel10;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("9");
			jLabel9.setBorder(new LineBorder(Color.black, 1, false));
		}
		return jLabel9;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("8");
			jLabel8.setBorder(new LineBorder(Color.black, 1, false));
		}
		return jLabel8;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("7");
			jLabel7.setBorder(new LineBorder(Color.black, 1, false));
		}
		return jLabel7;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("6");
			jLabel6.setBorder(new LineBorder(Color.black, 1, false));
		}
		return jLabel6;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("5");
			jLabel5.setBorder(new LineBorder(Color.black, 1, false));
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("4");
			jLabel4.setBorder(new LineBorder(Color.black, 1, false));
		}
		return jLabel4;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("3");
			jLabel3.setBorder(new LineBorder(Color.black, 1, false));
		}
		return jLabel3;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("2");
			jLabel2.setBorder(new LineBorder(Color.black, 1, false));
		}
		return jLabel2;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("1");
			jLabel1.setBorder(new LineBorder(Color.black, 1, false));
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("0");
			jLabel0.setBorder(new LineBorder(Color.black, 1, false));
		}
		return jLabel0;
	}

	private JPanel getJPanel2() {
		if (PaiPane1 == null) {
			PaiPane1 = new JPanel();
			PaiPane1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			PaiPane1.setLayout(new GridLayout(1, 3));
			PaiPane1.add(getJLabel0());
			PaiPane1.add(getJLabel1());
			PaiPane1.add(getJLabel2());
			PaiPane1.add(getJLabel3());
			PaiPane1.add(getJLabel4());
			PaiPane1.add(getJLabel5());
			PaiPane1.add(getJLabel6());
			PaiPane1.add(getJLabel7());
			PaiPane1.add(getJLabel8());
			PaiPane1.add(getJLabel9());
			PaiPane1.add(getJLabel10());
			PaiPane1.add(getJLabel11());
			PaiPane1.add(getJLabel12());
			PaiPane1.add(getJLabel13());
		}
		return PaiPane1;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
			jPanel0.setLayout(new GridLayout(2, 2));
		}
		return jPanel0;
	}


	//ここからMaunulコード。
	public void setImage(int index, Icon image) {
			JLabel label = getJLabelFromIndex(index);
			if(label != null) {
				label.setIcon(image);
			}
	}

	public JLabel getJLabelFromIndex(int index) {
		if( 0 <= index && index <=12 ) {
			switch(index) {
			case 0:
				return getJLabel0();
			case 1:
				return getJLabel1();
			case 2:
				return getJLabel2();
			case 3:
				return getJLabel3();
			case 4:
				return getJLabel4();
			case 5:
				return getJLabel5();
			case 6:
				return getJLabel6();
			case 7:
				return getJLabel7();
			case 8:
				return getJLabel8();
			case 9:
				return getJLabel9();
			case 10:
				return getJLabel10();
			case 11:
				return getJLabel11();
			case 12:
				return getJLabel12();
			case 13:
				return getJLabel13();
			default:
				break;
			}
		}
		return null;
	}



}
