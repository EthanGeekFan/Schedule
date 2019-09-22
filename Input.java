package Main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * User Input
 * 用户输入窗口
 * @author yangyifan
 *
 */
public class Input {
	static int width = 550; 
	static int height = 200;
	static Frame frame;
	static Panel buttonP;
	static Label textLabel;
	JTextArea input;
	static Button ok;
	static Font f = new Font("黑体", Font.PLAIN, 25);
	
	/**
	 * Constructor of the Input window to get User Inputs
	 * 输入窗口的构造器
	 * 
	 * @param name Title 窗口标题
	 * @param strings Information 提示信息
	 */
	public Input(String name, String... strings) {
		frame = new Frame();
		frame.setTitle(name);
		frame.setSize(width, height);
		frame.setLayout(new BorderLayout());
		textLabel = new Label(strings[0]);
		textLabel.setFont(f);
		frame.add(textLabel, BorderLayout.NORTH);
		input = new JTextArea(1,5);
		input.setRequestFocusEnabled(true);
//		input.setPreferredSize(new Dimension(0,1));
//		input.setBounds(10, 10, 500, 50);
		input.setFont(f);
		Panel p = new Panel(new FlowLayout());
		p.add(input);
		frame.add(p, BorderLayout.CENTER);
		buttonP = new Panel(new FlowLayout());
		buttonP.setFont(f);
		if(strings.length > 1) {
			ok = new Button(strings[1]);
		} else {
			ok = new Button("OK");
		}
		buttonP.add(ok);
		frame.add(buttonP,BorderLayout.SOUTH);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		setCenter(frame);
		frame.setVisible(true);
	}
	
	/**
	 * Get the input
	 * 获取用户输入
	 * @return User Input 用户输入
	 */
	public String getInput() {
		return this.input.getText();
	}
	
	/**
	 * Dispose the window
	 * 销毁窗口
	 */
	public void close() {
		frame.dispose();
	}
	
	/**
	 * Set the frame in the center of the screen
	 * 设置窗口到屏幕中心
	 * 
	 * @param window The frame you want to manipulate 你要操作的窗口
	 */
	public static void setCenter(Frame window) {
		int windowWidth = window.getWidth();
		int windowHeight = window.getHeight();
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		window.setLocation((screenWidth - windowWidth) / 2, (screenHeight - windowHeight) / 2);
	}
}
