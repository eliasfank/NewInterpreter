import java.util.*;

public class Variaveis{
	private Vector <Var> variaveis = new Vector <Var>();
	
	public void addVar(Var v){
		variaveis.add(v);
	}
	
	public Var getVarIndice(int a){
		if (a >= 0 && a < variaveis.size())
		return variaveis.elementAt(a);
		else
		return null;
	}
	
	public Var getVarNome(String a){
		if (a == null)
		return null;
		for(int i = 0;i<variaveis.size();i++)
			if(a.equals(getVarIndice(i).getNome()))
				return variaveis.elementAt(i);
		return null;
	}
	
	public void mostraVars(){
		for(int i = 0;i<variaveis.size();i++)
			System.out.println(getVarIndice(i).getNome()+": "+getVarIndice(i).getTipo()+" - "+getVarIndice(i).getValor());
			
	}

}
