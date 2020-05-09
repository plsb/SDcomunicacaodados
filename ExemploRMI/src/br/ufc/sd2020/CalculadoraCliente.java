package br.ufc.sd2020;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;


public class CalculadoraCliente {
	
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
		
		//transforma a árvore em XML
		 XStream xstream= new XStream();
         String representacao= xstream.toXML(raiz);
         //System.out.println(representacao);
		
		Registry reg = null;
		ICalculadora calc;		
		try {//trata exeções que podem ocorrer
			reg = LocateRegistry.getRegistry(1099); //acessa o serviço de nomes do Java RMI
			calc = (ICalculadora) reg.lookup("calculadora"); //obtem uma referência remota para o objeto servidor
			System.out.println("Resultado "+ calc.calculaExpressao(representacao)); //chama o método calculaExpressao, passa o Json e fica no aguardo
		} catch (RemoteException | NotBoundException e) {
				System.out.println(e);
				System.exit(0);
		}
	}		

}
