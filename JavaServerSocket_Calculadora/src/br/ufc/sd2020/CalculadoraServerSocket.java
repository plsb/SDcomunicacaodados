package br.ufc.sd2020;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

public class CalculadoraServerSocket {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket welcomeSocket; //a classe ServerSocket é responsável por atender pedidos via rede e em determinada porta
		DataOutputStream socketOutput;     	
	    DataInputStream socketInput;
	    BufferedReader socketEntrada;
	    Calculadora calc = new Calculadora();
		try {
			welcomeSocket = new ServerSocket(9091); //o socket irá atender pedidos nesta porta, a 9090. Se a porta já estiver em uso será lançada uma exceção.
			  int i=0; //n�mero de clientes
		  
		      System.out.println ("Servidor no ar");
		      while(true) { //o while é para ficar executando infinitamente esperando por conexões de clientes
		  
		           Socket connectionSocket = welcomeSocket.accept(); /*
		           														aceita as conexões com os clientes. O método accept()
		           														 bloqueia a execução até que o servidor receba um pedido de conexão
		           														 */
		           System.out.println("Cliente conectado: " + connectionSocket.getInetAddress().getHostAddress());
		           
		           
		           i++; //adiciona + 1 na variável i, que está destinada a quantidade de cliente conectados
		           
		           /*
		            * Por meio do código abaixo
		            * é possível recuperar os dados que o cliente enviou. É utilizado o readLine, 
		            * porque os dados enviados pelo cliente tem quebras de linha (\n)
		            */
		           socketEntrada = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
	               String json=socketEntrada.readLine(); // pega o JSON em formato de String
	              
	               
	               Gson gson = new Gson();
	               ExpressaoArvore expressao = gson.fromJson(json, ExpressaoArvore.class);  //recupera o objeto a partir do JSON
	               
	               Calculadora c = new Calculadora();
	               String result= String.valueOf(c.calcula(expressao));
	               
	               socketOutput= new DataOutputStream(connectionSocket.getOutputStream());     	
		           socketOutput.writeBytes(result+ '\n');
		           System.out.println (result);	           
		           socketOutput.flush();
		           socketOutput.close();
	
		                    
		      }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
	}

}
