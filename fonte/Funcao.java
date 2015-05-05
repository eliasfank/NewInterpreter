import java.util.*;
public class Funcao{
	private int inicio;
	private int fim;
	private String nome;
	Variaveis variaveis = new Variaveis();
	public void setInicio(int ini){
		inicio = ini;
	}
	public void setFim(int f){
		fim = f;
	}
	public void setNome(String n){
		nome = n;
	}
	public int getInicio(){
		return inicio;
	}
	public int getFim(){
		return fim;
	}
	public String getNome(){
		return nome;
	}
	//public void addOperacao(Object op){
		//pilhaOperacoes.push((Operacao)op);
	//}
	//public void rmOperacao(){
		//if(! pilhaOperacoes.empty())
		//pilhaOperacoes.pop();
	//}
	public boolean tem_var(String vari){
		Var a = new Var();
		a = variaveis.getVarNome(vari);
		if(a == null)
			return false;
		else
			return true;
	}
	
	public void addIntFuncao(String vari, int valor){
		Var v = new Inteiro();
		v.setNome(vari);
		v.setTipo("int");
		v.setValor(valor);
		variaveis.addVar(v);
	}
	public void addDoubleFuncao(String vari, double valor){
		Var v = new Duplo();
		v.setNome(vari);
		v.setTipo("double");
		v.setValor(valor);
		variaveis.addVar(v);
	}
	public void addStringFuncao(String vari, String valor){
		Var v = new Stringue();
		v.setNome(vari);
		v.setTipo("string");
		v.setValor(valor);
		variaveis.addVar(v);
	}
	public void mostraV(){
		variaveis.mostraVars();
	}
	
}
