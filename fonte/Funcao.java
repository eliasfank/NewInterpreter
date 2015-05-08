import java.util.*;
public class Funcao{
	private int inicio;
	private int fim;
	private String nome;
	private String retorno;
	private ArrayList<Var> argumentos = new ArrayList<Var>();
	public Stack <Variaveis> variaveis = new Stack <Variaveis>();
	public void setInicio(int ini){
		inicio = ini;
	}
	public void setFim(int f){
		fim = f;
	}
	public void setNome(String n){
		nome = n;
	}
	public void adicionaListaVariaveis(){
		Variaveis v = new Variaveis();
		variaveis.push(v);
	}
	public void removeListaVariaveis(){
		variaveis.pop();
	}
	public void addArgumento(String argumento){
		String[] separa = argumento.split(" ");
		Var a = new Var();
		if(separa[0].equals("int"))
		a = new Inteiro();
		a.setNome(separa[1]);
		a.setTipo("int");
		argumentos.add(a);
	}
	public void setRetorno(String n){
		retorno = n;
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
	public String getRetorno(){
		return retorno;
	}
	public ArrayList<Var> getArgumentos(){
		return argumentos;
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
		a = variaveis.peek().getVarNome(vari);
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
		variaveis.peek().addVar(v);
	}
	public void addDoubleFuncao(String vari, double valor){
		Var v = new Duplo();
		v.setNome(vari);
		v.setTipo("double");
		v.setValor(valor);
		variaveis.peek().addVar(v);
	}
	public void addStringFuncao(String vari, String valor){
		Var v = new Stringue();
		v.setNome(vari);
		v.setTipo("string");
		v.setValor(valor);
		variaveis.peek().addVar(v);
	}
	public void mostraV(){
		variaveis.peek().mostraVars();
	}
	public void mostraArgumentos(){
		for(int i = 0;i<argumentos.size();i++)
			System.out.println(argumentos.get(i).getNome());
	}
	
}
