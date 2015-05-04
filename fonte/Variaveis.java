import java.util.*;

public class Variaveis{
	private Vector <Var> variaveis = new Vector <Var>();
	
	public void addVar(Var v){
		variaveis.add(v);
	}
	
	public Var getElement(int a){
		if (a >= 0 && a < variaveis.size())
		return variaveis.elementAt(a);
		else
		return null;
	}

}
