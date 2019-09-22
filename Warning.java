package Main;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Warning and Notification Windows
 * 警告和提示窗口
 * 
 * @author yangyifan
 *
 */
public class Warning {
	private static Frame frame;
	private static Label textLabel;
	private static Panel buttons;
	public Button yes, no;
	public static String text;
	
	/**
	 * Constructor of the warning window
	 * 警告提示窗口构造器
	 * 
	 * @param name Title 窗口标题
	 * @param param Other Information 其他信息
	 */
	public Warning(String name, String... param) {
		frame = new Frame();
		frame.setLayout(new BorderLayout());
		frame.setSize(1000, 250);
		frame.setTitle(name);
		setText(param[0]);
		textLabel = new Label(text);
		textLabel.setAlignment(Label.CENTER);
		textLabel.setFont(Resources.text_20);
		if(param.length > 2) {
			yes = new Button(param[1]);
			no = new Button(param[2]);
		} else {
			yes = new Button("Yes");
			no = new Button("No");
		}
		yes.setFont(Resources.text_20);
		no.setFont(Resources.text_20);
		buttons = new Panel(new FlowLayout());
		buttons.add(no);
		buttons.add(yes);
		frame.add(textLabel, BorderLayout.CENTER);
		frame.add(buttons, BorderLayout.SOUTH);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		setCenter(frame);
		frame.setVisible(true);
	}
	
	/**
	 * Set the value of the property
	 * 设置属性值
	 * 
	 * @param str the string you want to set
	 */
	private static void setText(String str) {
		text = str;
	}
	
	public void close() {
		frame.dispose();
	}
	
	/**
	 * Set the frame in the center of the screen
	 * 设置窗口到屏幕正中央
	 * 
	 * @param window the window you want to manipulate
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
