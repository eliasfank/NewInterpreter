import java.util.*;
public class Funcao{
	private int inicio;
	private int fim;
	private String nome;
	private String retorno;
	private ArrayList<Var> argumentos = new ArrayList<Var>();
	private Stack <Variaveis> variaveis = new Stack <Variaveis>();
	private Stack <Fluxo> pilhaFluxo = new Stack <Fluxo>();
	
	public Variaveis getListaVariaveis(){
		if(!variaveis.empty())
			return variaveis.peek();
		else
		return null;
	}
	
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
	
	public void adicionaFluxo(String linha){
		String[] separa={};
		Fluxo o = new Fluxo();
		
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
		pilhaFluxo.push(o);
	}
	
	public void removeFluxo(){
		if(!pilhaFluxo.empty())
		pilhaFluxo.pop();
	}
	
	public void addArgumento(String argumento){
		String[] separa = argumento.split(" ");
		Var a = new Var();
		
		if(separa[0].equals("int")){
			a = new Inteiro();
			a.setNome(separa[1]);
			a.setTipo("int");
		}
		
		if(separa[0].equals("double")){
			a = new Duplo();
			a.setNome(separa[1]);
			a.setTipo("double");
		}
		
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
	
	public Fluxo getFluxo(){
		if(pilhaFluxo.empty())
		return null;
		return pilhaFluxo.peek();
	}
	
	public boolean temVar(String vari){
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
	
	public boolean testaFluxo(){
		if(pilhaFluxo.empty())
			return false;
			
		double ad, bd;
		Fluxo o = new Fluxo();
		o = getFluxo();
		String a = o.getA();
		String b = o.getB();
		
		if(temVar(a)){
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
		
		if(temVar(b)){
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
			
		if(getFluxo().getComp().equals("==")){
			if(ad==bd)
				return true;
		}
		
		if(getFluxo().getComp().equals("!=")){
			if(ad!=bd)
				return true;
		}
		
		if(getFluxo().getComp().equals(">")){
			if(ad>bd)
				return true;
		}
		
		if(getFluxo().getComp().equals("<")){
			if(ad<bd)
				return true;
		}
			
		return false;
	}
	
}
