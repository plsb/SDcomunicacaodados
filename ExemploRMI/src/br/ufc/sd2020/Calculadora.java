package br.ufc.sd2020;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

public class Calculadora  implements ICalculadora {

	private static final long serialVersionUID = 1L;
	
	private static int chamadasSum = 0;
	private static int chamadasSub = 0;
	private static int chamadasMult = 0;
	private static int chamadasDiv = 0;
	
	public double calculaExpressao(String expressao) {
		Gson gson = new Gson();
        //ExpressaoArvore arvore = gson.fromJson(expressao, ExpressaoArvore.class);  //recupera o objeto a partir do JSON
        XStream xstream= new XStream();
        ExpressaoArvore arvore = (ExpressaoArvore) xstream.fromXML(expressao);
        
        System.out.println(arvore.operacao);
        
		return calcula(arvore);
	}
	
	public double calcula(ExpressaoArvore no) {
    	//percorre a árvore realizando as operações
    	double resultado = 0;
        if(no != null)
        {
  
        	Double n1 = no.o1;
        	Double n2 = no.o2;
        	if(n1 == null) {
        		n1 = calcula(no.left);
        	}
        	if(n2 == null) {
        		n2 = calcula(no.right);
        	}
        	
        	try {
        		switch (no.operacao) {
				case '+':
					resultado = n1 + n2;
					break;
				case '-':
					resultado = n1 - n2;
					break;
				case '*':
					resultado = n1 * n2;
					break;
				case '/':
						resultado = n1 / n2;
					break;

				default:
					break;
				}
    		} catch (Exception e) {
				resultado = 0;
			}
        	
        }
        return resultado;
    }

	public static void main(String[] args) throws AccessException, RemoteException, AlreadyBoundException  {
		Calculadora calculadora = new Calculadora();		
		Registry reg = null; // a classe Registry é responsável por registrar um determinado serviço no Java RMI.
		ICalculadora stub = (ICalculadora) UnicastRemoteObject.
				exportObject(calculadora, 1100); //oferece as interfaces que os objetos da aplicação usam para interagir entre si.
		try {
			System.out.println("Creating registry...");
			reg = LocateRegistry.createRegistry(1099); 
		} catch (Exception e) {
			try {
				reg = LocateRegistry.getRegistry(1099);
			} catch (Exception e1) {
				System.exit(0);
			}
		}
		reg.rebind("calculadora", stub); /*
											o nome do serviço a ser disponibilizado é calculadora. Esse serviço será recuperado
											no cliente para poder executar as funções sum, sub, mult e/ou div que estão nesta classe.
											o objeto stub é uma referência para o serviço que será disponibilizado.
											*/ 
	}

	
}
