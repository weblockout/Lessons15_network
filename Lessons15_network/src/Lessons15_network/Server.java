package Lessons15_network;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Server implements Runnable{
	static private ServerSocket server;  //ServerSocket принемает соединение
	static private Socket connection; // Содаем переменную с именем connection для хранения соединения сокет\
	static private ObjectOutputStream output; // Создаем потоки output  input
	static private ObjectInputStream input; 

	public void run() {
		
		try {
			server = new ServerSocket(5678, 10);  // порт 5678 и количество макс.подключений - 10
			while(true) {  // определим эти потоки в бесконечном цикле, потому,что поток после отправки закрывается
			//и в цикле мы сможем открыть его заново
			connection = server.accept(); // Когда к ServerSocket кто хочет подключится, оно разрешает и Возвращает сокет, которую мы складываем
			//в переменную connection и потом из нее input и output для сервера 

			output = new ObjectOutputStream(connection.getOutputStream()); // исходящий поток берем из сокета (connection)
			input = new ObjectInputStream(connection.getInputStream());
			output.writeObject("Вы прислали: " + (String)input.readObject());  //
				}				
			}catch(UnknownHostException e) {}catch(IOException e) {} catch(HeadlessException e) {}catch(ClassNotFoundException e) {}


		
	}

}



