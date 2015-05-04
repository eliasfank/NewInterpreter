import java.util.*;
public class Funcao{
	private int inicio;
	private int fim;
	private String nome;
	private Stack <Operacao> pilhaOperacoes = new Stack <Operacao>();
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
	public void addOperacao(Object op){
		pilhaOperacoes.push((Operacao)op);
	}
	public void rmOperacao(){
		if(! pilhaOperacoes.empty())
		pilhaOperacoes.pop();
	}
}
