package Main;
/**
 * 课程表边栏(Chinese Simplified/English)
 * English Version Coming Soon
 * 作者：杨易凡
 * @author YangYifan
 * Copyright(c) 2019 Ethan Yang
 * 
 * Version 2.0
 * 2019.07.13.
 */


import java.awt.*;
//import java.
import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * The Core Class of this Project
 * 
 * @author yangyifan
 *
 */
public class Schedule {
	static Frame frame;
	static Panel mainPanel, titlePanel;
	static Label[] labelArray;
	static ArrayList<String> data;
	//日程信息：可修改
	static String[] Mon = new String[]{"物理","化学","数学","政治","体育","英语","语文","班会","心理"};
	static String[] Tue = new String[]{"地理","化学","英语","英语","数学","语文","物理","数竞","数竞"};
	static String[] Wed = new String[]{"数学","物理","语文","英语","体育","政治","历史","物竞","物竞"};
	static String[] Thu = new String[]{"地理","物理","语文","语文","数学","历史","化学","数竞","数竞"};
	static String[] Fri = new String[]{"地理","历史","数学","英语","化学","微机","阅览","物竞","物竞"};
	static String[] Sat = new String[]{"物理","物理","生物","语文","语文","数学","数学"};
	static String[] Sun = new String[]{"英语","数学","语文","化学","物理","OR","晚","自","习"};
	//Constants:
	static final int MONDAY 	= 1;
	static final int TUESDAY 	= 2;
	static final int WEDNESDAY 	= 3;
	static final int THURSDAY 	= 4;
	static final int FRIDAY 	= 5;
	static final int SATURDAY 	= 6;
	static final int SUNDAY 	= 7;
	static final String notiTitle = "下课小精灵";
	static final String notiMessage = "老师，您辛苦了！同学们提醒您：下课时间到了！您该去休息啦！谢谢老师您的付出！我们饶过彼此可好？";
	static final String notiOptOne = "好的，我马上下课";
	static final String notiOptTwo = "OK, 我现在下课";
	
	//Functional Variables:
	static ArrayList<Integer> index = new ArrayList<Integer>();
	static boolean isChangeable = false;
	static boolean classOverNotify = true;
	static ArrayList<String> notifications = new ArrayList<String>();
	static String inputString = "";
	static String time = "";
	static boolean survivalMode = true;
	
	/**
	 * Constructor of the Window
	 */
	public Schedule() {
		//录入课表信息，根据当天时间自动选择
		//!!!!!!!!!Attention!!!!!!!!
		//Fatal:
		//Please be careful that the weekdays below are obtained from the system, the language is identical to that of your system
		int Day = 0;
		if(getWeek().equals("Monday") || getWeek().equals("星期一")) {
			Day = MONDAY;
		}else if(getWeek().equals("Tuesday") || getWeek().equals("星期二")){
			Day = TUESDAY;
		}else if(getWeek().equals("Wednesday") || getWeek().equals("星期三")) {
			Day = WEDNESDAY;
		}else if(getWeek().equals("Thursday") || getWeek().equals("星期四")) {
			Day = THURSDAY;
		}else if(getWeek().equals("Friday") || getWeek().equals("星期五")) {
			Day = FRIDAY;
		}else if(getWeek().equals("Saturday") || getWeek().equals("星期六")) {
			Day = SATURDAY;
		}else if(getWeek().equals("Sunday") || getWeek().equals("星期日")) {
			Day = SUNDAY;
		}
		switch(Day) {
			case MONDAY:
				data = fitArray(Mon);
				break;
			case TUESDAY:
				data = fitArray(Tue);
				break;
			case WEDNESDAY:
				data = fitArray(Wed);
				break;
			case THURSDAY:
				data = fitArray(Thu);
				break;
			case FRIDAY:
				data = fitArray(Fri);
				break;
			case SATURDAY:
				data = fitArray(Sat);
				break;
			case SUNDAY:
				data = fitArray(Sun);
				break;
			default:
				break;
		}
		
		
		//初始化图形窗口
		frame = new Frame();
		//frame.setBounds(1500, 200, 500, 800);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.setAlwaysOnTop(true);
		//frame.setUndecorated(true);
		//frame.setOpacity(0.5f);
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int input = e.getKeyCode();
				if(input == KeyEvent.VK_F6) {
					System.exit(0);
				}
				/**
				 * MARK:
				 * 
				 * Switch the is Changeable property
				 */
				if(input == KeyEvent.VK_F9) {
					isChangeable = !isChangeable;
					if(isChangeable) {
						System.out.println("Schedule is now Changeable!");
					}else {
						System.out.println("Schedule is now Unchangeable!");
					}
				}
				if(input == KeyEvent.VK_F2) {
					/*
					 * If F2 is presses:
					 * Call an Notification Adder Window
					 */
					Input newNote = new Input("添加下课提醒", "请输入提醒的时间，以空格分隔（HH MM SS)");
					newNote.frame.addKeyListener(new KeyAdapter() {
						@Override
						public void keyPressed(KeyEvent e) {
							int key = e.getKeyCode();
							if(key == KeyEvent.VK_ENTER) {
								addNotification(newNote);
							}
							if(key == KeyEvent.VK_ESCAPE) {
								newNote.close();
							}
						}
					});
					
					newNote.ok.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							addNotification(newNote);
						}
					});
				}
			}
		});
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (survivalMode == false) {
					System.exit(0);
				} else if (survivalMode == true) { // 彩蛋一枚
					frame.setVisible(false);
					String[] deathMessage = new String[] {"内", "置", "AI:","你", "尽", "可","以", "消", "灭","我","可","你","就","是","关","不","掉","我"};
					ArrayList<String> deathMesArr = fitArray(deathMessage);
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					Label[] deathLabelArray = createLabelArray(deathMesArr);
					Font font = new Font("楷体", Font.BOLD, 40);
					formatFont(deathLabelArray, font);
					frame.removeAll();
					Panel deathPanel = new Panel();
					deathPanel.setLayout(new GridLayout(deathMesArr.size() + 1, 1, 30, 10));
					addComponent(deathLabelArray, deathPanel);
					addListener(deathLabelArray);
					frame.add(deathPanel, BorderLayout.CENTER);
					frame.add(titlePanel, BorderLayout.NORTH);
					//设置文字对其方式：水平中心对齐
					setAlignment(deathLabelArray, Label.CENTER);
					frame.pack();
					setRightSide(frame);
					frame.setVisible(true);
				}
			}
		});
		
		//初始化组件
		mainPanel = new Panel();
		mainPanel.setLayout(new GridLayout(data.size() + 1, 1, 30, 50));
		titlePanel = new Panel();
		titlePanel.setLayout(new GridLayout(4,1));
		
		/*
		 * 表头日期显示模块：
		 */
		//三种日期时间格式：
		SimpleDateFormat year 	= new SimpleDateFormat("YYYY年");
		SimpleDateFormat monDay = new SimpleDateFormat("MM月dd日");
		SimpleDateFormat time 	= new SimpleDateFormat("HH:mm:ss");
		//格式化日期时间显示：
		Date today 	= new Date();
		Label[] dates = new Label[4]; // 年 月日 星期 时间
		dates[0] 	= new Label(year.format(today));
		dates[1] 	= new Label(monDay.format(today));
		dates[2] 	= new Label(getWeek());
		dates[3] 	= new Label(time.format(today));
		//将日期时间标签添加到组件中：
		addComponent(dates, titlePanel);
		//这里的t是程序运行初始化时的时刻，实时更新时间模块在后面
		/*
		 * 日期时间显示模块结束
		 */
		
		/*
		 * 课表显示模块：
		 */
		//初始化课表标签：
		labelArray = createLabelArray(data);
		
		//设置字体：字体，效果，字号
		Font font = new Font("黑体", Font.BOLD, 50);//方便统一修改
		formatFont(labelArray, font);
		
		//时间显示模块的字体也在这里进行初始化设置
		Font titleFont = new Font("黑体", Font.BOLD, 20);//方便统一修改
		formatFont(dates, titleFont);
		//将课表显示标签加入面板组件中：
		addComponent(labelArray, mainPanel);
		addListener(labelArray);
		
		//设置文字对其方式：水平中心对齐
		setAlignment(labelArray, Label.CENTER);
		//日期也一起搞掉：
		setAlignment(dates, Label.CENTER);
		/*
		 * 课表显示模块结束
		 */
		
		//Status Report:
		System.out.println("data = " + data);
		System.out.println("notifications = "+ notifications);
		
		/*
		 * 图形窗口界面设置：
		 */
		//将两个面板添加进窗口：
		//使用边界布局，课表主面板在CENTER区域；时间日期在NORTH区域；
		frame.add(mainPanel, BorderLayout.CENTER);
		frame.add(titlePanel, BorderLayout.NORTH);
		//设置窗口大小：适当大小，包含所有组件：
		frame.pack();
		
		//靠右边模块，可选，注释去除
		setRightSide(frame);
		
		//使窗口可见：
		frame.setVisible(true);
		/*
		 * 图形窗口界面设置结束
		 */
		
		/*
		 * 时间实时更新模块：
		 */
		//实时，所以使用无穷循环：
		while(true) {
			try {//try...catch...保护程序健康运行
				Thread.sleep(1000);
				//线程暂停1000ms
				//为防止内存占用，每隔一秒更新一次
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			//将时间标签信息设置为实时获取的时间
			dates[3].setText(time.format(new Date()));
			//下课播报功能，请谨慎使用看人下菜，见风使舵方能使得万年船
			if(classOverNotify) {
				if(inList(dates[3].getText(), notifications)) {
					System.out.println("Notifying...");
					Warning classOver = new Warning(notiTitle, notiMessage, notiOptOne, notiOptTwo);
					classOver.yes.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							classOver.close();
						}
					});
				}
			}
		}
		/*
		 * 时间实时更新模块结束；
		 */
	}
	
	
	
	/*
	 * BLOCK
	 * 
	 * 封装的静态方法们
	 * 提供复用代码支持
	 * 方便修改优化功能实现
	 */
	
	/**
	 * Set a Frame to the right side of the screen
	 * 设置窗口紧贴屏幕右侧
	 * 
	 * @param window The frame you are manipulating
	 */
	public static void setRightSide(Frame window) {
		int windowWidth = window.getWidth();
		int windowHeight = window.getHeight();
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		//填充高度：可选
		window.setSize(windowWidth, screenHeight);
		window.setLocation(screenWidth-windowWidth, 0);
	}
	
	/**
	 * Set a Frame to the left side of the screen
	 * 设置窗口紧贴屏幕左侧
	 * 
	 * @param window The frame you are manipulating
	 */
	public static void setLeftSide(Frame window) {
		int windowWidth = window.getWidth();
		int windowHeight = window.getHeight();
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		//填充高度：可选
		window.setSize(windowWidth, screenHeight);
		window.setLocation(0, 0);
	}
	
	/**
	 * Set a Frame to the center of the screen
	 * 设置窗口到屏幕正中
	 * 
	 * @param window The frame you are manipulating
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
	
	/**
	 * Get the current Weekday of the day
	 * 获取当天的日期与星期
	 * 
	 * @return The name of the weekday in the System Language
	 */
	public static String getWeek(){ 
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		String week = sdf.format(new Date());
		return week;
		}
	
	/**
	 * Fit the array into an ArrayList
	 * 从输入的字符串数组中生成一个列表
	 * 
	 * @param arr The String array
	 * @return The ArrayList Generated
	 */
	public static ArrayList<String> fitArray(String[] arr) {
		ArrayList<String> fit = new ArrayList<String>();
		for(int i = 0; i < arr.length; i++) {
			fit.add(arr[i]);
		}
		return fit;
	}
	
	/**
	 * Generate a lot of Labels from a ArrayList of Strings
	 * 从输入的字符串列表中生成一个标签数组
	 * 
	 * @param l The ArrayList
	 * @return The array that contains the generated labels
	 */
	public static Label[] createLabelArray(ArrayList<String> l) {
		Label[] arr = new Label[l.size()];
		for(int i = 0; i < l.size(); i++) {
			arr[i] = new Label(l.get(i));
		}
		return arr;
	}
	
	/**
	 * Add a components to a Panel/Frame
	 * 将组件添加进容器中
	 * 
	 * @param ele component
	 * @param tar container
	 */
	public static void addComponent(Component ele, Panel tar) {
		tar.add(ele);
	}
	
	/**
	 * Add a components to a Panel/Frame
	 * 将组件添加进容器中
	 * 
	 * @param ele component
	 * @param frame container
	 */
	public static void addComponent(Component ele, Frame frame) {
		frame.add(ele);
	}
	
	/**
	 * Add an array of components to a Panel/Frame
	 * 将数组中的组件添加进容器中
	 * 
	 * @param elist component list
	 * @param tar container
	 */
	public static void addComponent(Component[] elist, Panel tar) {
		for(int i = 0; i < elist.length; i++) {
			addComponent(elist[i], tar);
		}
	}
	
	/**
	 * Add an array of components to a Panel/Frame
	 * 将数组中的组件添加进容器中
	 * 
	 * @param elist component list
	 * @param tar container
	 */
	public static void addComponent(Component[] elist, Frame tar) {
		for(int i = 0; i < elist.length; i++) {
			addComponent(elist[i], tar);
		}
	}
	
	/**
	 * Set Fonts of the labels in the array
	 * 给传入数组中的标签统一设置字体
	 * 
	 * @param arr The array that contains the labels you want to manipulate
	 * @param font The Font you want to set
	 */
	public static void formatFont(Label[] arr, Font font) {
		for(int i = 0; i < arr.length; i++) {
			arr[i].setFont(font);
		}
	}
	
	/**
	 * Set the alignment of the labels in the array
	 * 给传入数组中的标签统一设置对齐标准
	 * 
	 * @param arr The array that contains the labels you want to manipulate
	 * @param Alignment The Alignment you want to set
	 */
	public static void setAlignment(Label[] arr, int Alignment) {
		for(int i = 0; i < arr.length; i++) {
			arr[i].setAlignment(Alignment);
		}
	}
	
	/**
	 * Change the order of classes
	 * 换课实现
	 * 
	 * @param i The first index of the 2 Labels you want to change
	 * @param j The other index of the 2 Labels you want to change
	 */
	public static void exchange(int i, int j) {
		Label temp = labelArray[i];
		labelArray[i] = labelArray[j];
		labelArray[j] = temp;
		addComponent(labelArray, mainPanel);
		addComponent(mainPanel, frame);
		frame.pack();
		setRightSide(frame);
	}
	
	/**
	 * The method that customize the Listener added to each button
	 * 设置给标签/按钮添加的监听器
	 * 
	 * @param lab The Label object to be added listener
	 */
	public static void addListener(Label lab) {
		lab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(isChangeable) {
					if(index.size() < 2) {
						index.add(indexOf(lab, labelArray));
						System.out.println(lab.getText());
					}
					if(index.size() == 2) {
						exchange(index.remove(0), index.remove(0));
						System.out.println("Classes Arrangement has Changed!");
						System.out.println("");
					}
				} else {
					System.out.println("Schedule is Unchangeable, please press F9 to turn on the switch!");
				}
				
			}
		});
	}
	
	/**
	 * Iterate the array of Labels and call the addListener() to add a listener to it
	 * 遍历数组中的每一个标签并将监听器添加给它
	 * 
	 * @param arr The arr of buttons which contains those target Labels
	 */
	public static void addListener(Label[] arr) {
		for(int i = 0; i < arr.length; i++) {
			addListener(arr[i]);
		}
	}
	
	/**
	 * A finder method to find the index of any Object in the target Object array
	 * 找到对象数组中的某一个对象并返回它的位置
	 * 
	 * @param a The Object you want to find
	 * @param arr The target Object array where you expect the Object
	 * @return The index of the Object in the given array OR -1 Object Not Found
	 */
	public static int indexOf(Object a, Object[] arr) {
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == a) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * The Method is called when the user is adding a notification for class over
	 * The method reads the user input from the JTextArea of the Input Object
	 * 读取用户输入并添加提醒
	 * 
	 * @param newNote The Input window where the user enter the information of the notification
	 * 
	 */
	public static void addNotification(Input newNote) {
		inputString = newNote.getInput();
		String[] split = inputString.split(" ");
		boolean valid = true;
		try {
			if(Integer.parseInt(split[0]) > 24 || Integer.parseInt(split[0]) < 0) {
				valid = false;
			}
			if(Integer.parseInt(split[1]) > 60 || Integer.parseInt(split[1]) < 0) {
				valid = false;
			}
			if(Integer.parseInt(split[2]) > 60 || Integer.parseInt(split[2]) < 0) {
				valid = false;
			}
			if(valid) {
				time = inputString.replaceAll(" ", ":");
				notifications.add(time);
				System.out.println("New Notification Added at " + time);
				newNote.close();
			}
		} catch(NumberFormatException nfe){
			valid = false;
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			valid = false;
		} finally {
			if(!valid) {
				System.out.println("Input time is not valid. Please try again.");
				newNote.textLabel.setText("Input time is not valid. Please try again.");
			}
		}
	}
	
	/**
	 * Switch the Notification is/isn't available
	 * 控制通知功能是否开启
	 */
	public static void switchNote() {
		classOverNotify = !classOverNotify;
	}
	
	/**
	 * Check if a String Object is in the ArrayList
	 * 检查传入的字符串对象是否在传入的字符串列表中
	 * 
	 * @param o: The String you want to check
	 * @param list: The target ArrayList in which you expected the String
	 * @return True: InTheList; False: NotInTheList
	 */
	public static boolean inList(String o, ArrayList<String> list) {
		for(int i = 0; i < list.size(); i++) {
			if(o.equals(list.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	
	
	//Main Method
	// the program starts here:
	/**
	 * Main Method
	 * 程序入口
	 * 
	 * @param args command line arguments 命令行参数
	 * @throws Exception throws exceptions such as IOException
	 */
	public static void main(String[] args) throws Exception {
		Schedule schedule = new Schedule();
	}
}

/*
 * Version 2.0
 * 
 * yangyifan529@gmail.com
 * 
 */


