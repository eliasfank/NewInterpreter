import java.util.*;
public class Operacoes{
	private Stack <Operacao> operacoes = new Stack <Operacao>();
	public void addOperacao(Operacao o){
		operacoes.push(o);
	}
	public Operacao getOperacao(){
		return operacoes.pop();
	}
	public void popOperacao(){
		operacoes.pop();
	}

}
