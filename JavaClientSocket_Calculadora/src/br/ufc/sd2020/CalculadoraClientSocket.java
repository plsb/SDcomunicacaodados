package br.ufc.sd2020;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

public class CalculadoraClientSocket {

	public static void main(String[] args) {
		//constrói a árvore como o exemplo dado ((50 - 30)/ ((3 + 2) * 4)
		ExpressaoArvore ex1 = new ExpressaoArvore();
		ex1.o1 = 3.0;
		ex1.o2 = 2.0;
		ex1.operacao = '+';
		ex1.left = null;
		ex1.right = null;
		
		ExpressaoArvore ex2 = new ExpressaoArvore();
		ex2.o1 = null;
		ex2.o2 = 4.0;
		ex2.operacao = '*';
		ex2.left = ex1;
		ex2.right = null;
		
		ExpressaoArvore ex3 = new ExpressaoArvore();
		ex3.o1 = 50.0;
		ex3.o2 = 30.0;
		ex3.operacao = '-';
		ex3.left = null;
		ex3.right = null;
		
		ExpressaoArvore raiz = new ExpressaoArvore();
		raiz.o1 = null;
		raiz.o2 = null;
		raiz.operacao = '/';
		raiz.left = ex3;
		raiz.right = ex2;
		
		//transforma a árvore em JSON
		Gson gson = new Gson();
		String json = gson.toJson(raiz);

		String result="";
		/*
		 * o try catch é usado para tratar possíveis exceções
		 */
        try {

        	/*
        	 * conecta o cliente ao servidor no locahost porta 9091
        	 */
            Socket clientSocket = new Socket("localhost", 9091);
            /*
             * DataOutpuStrem  permite que o cliente grave tipos de dados Java primitivos em um fluxo de saída
             */
            DataOutputStream socketSaidaServer = new DataOutputStream(clientSocket.getOutputStream());
            System.out.println("Cliente conectado");
            
            socketSaidaServer.writeBytes( gson.toJson(raiz)+ "\n");
            socketSaidaServer.flush(); //libera o fluxo de saida de dados

            /*
             * BufferedReader lê o texto de um fluxo de entrada de caracteres, 
             * armazenando em buffer para fornecer uma leitura eficiente dos caracteres.
             */
            BufferedReader messageFromServer = new BufferedReader
                    (new InputStreamReader(clientSocket.getInputStream()));
            result=messageFromServer.readLine();
            
            //JOptionPane.showMessageDialog(null, result); //apresenta o resultado para o usuário
            System.out.println(result);
            
            clientSocket.close(); //encerra a conexão do cliente

        } catch (IOException e) {
            e.printStackTrace();
        }


	}

}
