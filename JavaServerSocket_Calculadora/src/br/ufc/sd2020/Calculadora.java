package br.ufc.sd2020;

public class Calculadora {

    public double calcula(ExpressaoArvore ex) {
    	//retorna o resultado através do método recursivo
    	return emOrdem(ex);
    }
    
    public double emOrdem(ExpressaoArvore no) {
    	//percorre a árvore realizando as operações
    	double resultado = 0;
        if(no != null)
        {
  
        	Double n1 = no.o1;
        	Double n2 = no.o2;
        	if(n1 == null) {
        		n1 = emOrdem(no.left);
        	}
        	if(n2 == null) {
        		n2 = emOrdem(no.right);
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
    
}