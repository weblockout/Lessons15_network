package Lessons15_network;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Main extends JFrame implements Runnable{  // extends JFrame -понадобится интерфейс  
	//implements Runnable (создаем public void run() - который позволяеи сделать поток
	
	static private Socket connection; // Содаем переменную с именем connection для хранения соединения сокет\
	static private ObjectOutputStream output; // Создаем потоки output  input
	static private ObjectInputStream input; 
	// Вних мы сложим ссылки на потоке из сокета и будем через них отправлять нужную инфу
	
	
	public static void main ( String[] args) {
		new Thread(new Main("Test")).start();  // Поскольку класс main инкрементирует еще и Runnable(значит вся основная логика прописана в run)
		//   Чтобы запустить как поток - создаем новый поток
		new Thread(new Server()).start(); // запускаем поток с классом Server
		
		
	}
	//Создадим конструктор
	public Main(String name) {
		// сделаем типа оболочки для нашего клиента (Это нам позволяет - JFrame)
		
		super(name); //ПЕРЕДАЕМ В title строку name
		setLayout(new FlowLayout()); // Теперь все добавленные нами элементы будет он сам расспологать друг рядом с другом
		setSize(300, 300);  // Размер окна
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Активный крестик в окне
		setLocationRelativeTo(null);  // Окно в середине экрана
		final JTextField t1 = new JTextField(10); //Передаем размер поля ввода 10 символов
		final JButton b1 = new JButton("Send");  // Кнопка
		setVisible(true);
		// Слушатель на кнопку
		b1.addActionListener(new ActionListener() {  // Все добавляем прямо здесь, т.к. всего одна кнопка и не плодим новые переменные

			public void actionPerformed(ActionEvent arg0) {
				if (arg0.getSource() == b1) {
					senData(t1.getText()); // отправляем из метода senData из TextField t1 на сервер
				}				
			}			
		});   
		//Добавляем элеменыт
		add(t1);
		add(b1);
	}
	

	public void run() { 			// конструктор
		try {
			
		while(true) {  // определим эти потоки в бесконечном цикле, потому,что поток после отправки закрывается
			//и в цикле мы сможем открыть его заново
			connection = new Socket(InetAddress.getByName("127.0.0.1"), 5678); // передаем в конструктор ип и порт на котором программа висит
			// getByName - помогает получать ип из String
			output = new ObjectOutputStream(connection.getOutputStream()); // исходящий поток берем из сокета (connection)
			input = new ObjectInputStream(connection.getInputStream());
			JOptionPane.showMessageDialog(null, (String)input.readObject()); //это окошко будет активироваться, когда из input что-то можно прочитать
				}				
			}catch(UnknownHostException e) {}catch(IOException e) {} catch(HeadlessException e) {}catch(ClassNotFoundException e) {}
		
	}
	
	
	private static void senData(Object obj) { //будет принимать класс типа объект . С помощью метода senData иожем отправлять запросы на сервер
		// с помощью этошо метода мы сможем реализовать  отправку наших объектов 
		try {
			output.flush();   //выкинуть из потока все, что в нем было - чтобы не смешивались пакеты
			output.writeObject(obj);  // будем записывать наш объект
		} catch (IOException e) {} 
		
	}
 
}
