package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TestTableFrame extends Frame implements ActionListener{


		private TextArea textArea;
		private Button button1;
		private Panel panel;

		private final String imagePath = "C:\\pleiades\\workspace\\Mar-jangg\\image\\pai-images\\pai-images\\";
		private final String imageCommonSuffix = "-66-90-s.png";

	    public TestTableFrame(String title) {
	      //フレームのタイトルを設定
	      setTitle(title);

	      //ウィンドウを閉じる時の動作
	      addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent e) {
	          System.exit(0);
	        }
	      });

	    //背景色「緑」のPanelを準備
	    panel = new Panel();
	    panel.setLayout(new GridLayout(1,3));

	    //牌の画像を取り込み、ImageIconに格納
//	    ImageIcon icon1_ton = new ImageIcon(imagePath + "ji1" + imageCommonSuffix);
//	    ImageIcon icon2_nan = new ImageIcon(imagePath + "ji2" + imageCommonSuffix);
//
//	    JLabel label1 = new JLabel(icon1_ton);
//      	JLabel label2 = new JLabel(icon2_nan);
//
//      	panel.add(label1);
//      	panel.add(label2);

	    //action系

	    //Label label = new Label("click !  ", Label.CENTER);
	    //add(label, BorderLayout.NORTH);

      	//テキストエリアの表示
	    //textArea = new TextArea();
	    //add(textArea, BorderLayout.CENTER);

	    button1 = new Button("display");
	    button1.addActionListener(this);

	    panel.add(button1);

	    add(panel, BorderLayout.SOUTH);

	    }

	    public void addImage(ImageIcon image) {
	    	JLabel label = new JLabel(image);
	    	panel.add(label);
	    }


		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == button1) {
				display();
			}else {
				System.exit(0);
			}
		}

		private void display() {
//			StringBuffer buffer = new StringBuffer();
//			buffer.append("display method: ");
//			textArea.setText(buffer.toString());

		}
}
