import java.util.*;
public class Funcao{
	private int inicio;
	private int fim;
	private String nome;
	private String retorno;
	private ArrayList<Var> argumentos = new ArrayList<Var>();
	public Stack <Variaveis> variaveis = new Stack <Variaveis>();
	private Stack <Operacao> pilhaOperacao = new Stack <Operacao>();
	
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
	public void adicionaOperacao(String linha){
		String[] separa={};
		Operacao o = new Operacao();
		if(linha.contains("if"))
			o.setTipo("if");
		else
			o.setTipo("while");
		linha = linha.substring(linha.indexOf("(")+1,linha.indexOf(")"));
		if(linha.contains("==")){
			separa = linha.split("==");
			o.setComp("==");
		}
		if(linha.contains("!=")){
			separa = linha.split("!=");
			o.setComp("!=");
		}
		if(linha.contains(">")){
			separa = linha.split(">");
			o.setComp(">");
		}
		if(linha.contains("<")){
			separa = linha.split("<");
			o.setComp("<");
		}
		
		o.setA(separa[0].trim());
		o.setB(separa[1].trim());
		//System.out.println("|"+o.getA()+"|");
		//System.out.println("|"+o.getComp()+"|");
		//System.out.println("|"+o.getB()+"|");
		pilhaOperacao.push(o);
	}
	public void removeOperacao(){
		if(!pilhaOperacao.empty())
		pilhaOperacao.pop();
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
	public Operacao getOperacao(){
		if(pilhaOperacao.empty())
		return null;
		return pilhaOperacao.peek();
	}
	public boolean tem_var(String vari){
		if(variaveis.empty())
		return false;
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
	public boolean testaOperacao(){
		if(pilhaOperacao.empty()){
			//System.out.println("pilha vazia");
			return false;}
		double ad, bd;
		Operacao o = new Operacao();
		o = getOperacao();
		String a = o.getA();
		String b = o.getB();
		
		if(tem_var(a)){
			if(variaveis.peek().getVarNome(a).getTipo().equals("int"))
				ad = ((Inteiro)(variaveis.peek().getVarNome(a))).getValor();
			else
				ad = ((Duplo)(variaveis.peek().getVarNome(a))).getValor();
		}else{
			try{
				ad = Integer.parseInt(a);
			}catch(Exception e){
				ad = Double.parseDouble(a);
			}
		}
		if(tem_var(b)){
			if(variaveis.peek().getVarNome(a).getTipo().equals("int"))
				bd = ((Inteiro)(variaveis.peek().getVarNome(b))).getValor();
			else
				bd = ((Duplo)(variaveis.peek().getVarNome(b))).getValor();
		}else{
			try{
				bd = Integer.parseInt(b);
			}catch(Exception e){
				bd = Double.parseDouble(b);
			}
		}
			
		//System.out.println("|"+ai+"|");
		//System.out.println("|"+o.getComp()+"|");
		//System.out.println("|"+bi+"|");
		if(getOperacao().getComp().equals("==")){
			if(ad==bd)
				return true;
		}
		if(getOperacao().getComp().equals("!=")){
			if(ad!=bd)
				return true;
		}
		if(getOperacao().getComp().equals(">")){
			if(ad>bd)
				return true;
		}
		if(getOperacao().getComp().equals("<")){
			if(ad<bd)
				return true;
		}
			

		return false;
	}
	public void mostraOperacoes(){
		if(pilhaOperacao.empty()){
			System.out.println("pilha vazia!");
			return;
		}
		while(!pilhaOperacao.empty()){
			System.out.println(getOperacao().getA()+getOperacao().getComp()+getOperacao().getB()+" "+getOperacao().getTipo());
			pilhaOperacao.pop();
		}
	}
}
