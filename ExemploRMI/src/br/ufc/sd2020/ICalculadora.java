package br.ufc.sd2020;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICalculadora extends Remote{
	/*
	 * essa interface define os métodos abstratos que serão fornecidos ao clinte, e implementados no servidor
	 * os métodos são: Soma, Subtração, Multiplicação e Divisão.
	 * 
	 * Esta interface possui algumas diferenças em relação as interfaces comuns em java. 
	 * Esta interface herda de Remote do pacote java.rmi. E todo método desta interface deve
	 * declarar que a exceção RemoteException pode ser gerada na execução do método.
	 */
	
	/*public int sum(int a, int b) throws RemoteException;
	public int sub(int a, int b) throws RemoteException;
	public int mult(int a, int b) throws RemoteException;
	public int div(int a, int b) throws RemoteException;*/
	public double calculaExpressao(String expressao) throws RemoteException;
	
}
