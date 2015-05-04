import java.util.*;
public class Funcoes{
	
	private Vector <Funcao> funcoes = new Vector <Funcao>();
	
	public void addFuncao(Object f){
		if(f instanceof Funcao)
			funcoes.add((Funcao)f);
	}
	
	public Funcao getFuncaoIndice(int a){
		if (a >= 0 && a < funcoes.size())
		return funcoes.elementAt(a);
		else
		return null;
	}
	public Funcao getFuncaoNome(String a){
		for(int i = 0;i<funcoes.size();i++)
			if(a.equals(getFuncaoIndice(i).getNome()))
				return funcoes.elementAt(i);
		return null;
	}
	
	public void mostraFuncoes(){
		for(int i = 0;i<funcoes.size();i++)
			System.out.println(getFuncaoIndice(i).getNome()+": "+getFuncaoIndice(i).getInicio()+" - "+getFuncaoIndice(i).getFim());
			
	}
	
}
