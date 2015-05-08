import java.util.*;
public class ControladorFluxo{
	private Stack<Operacao> pilhaFluxo = new Stack <Operacao>();
	public void addOperacao(Operacao op){
		pilhaFluxo.push(op);
	}
	public void removeOperacao(){
		pilhaFluxo.pop();
	}

}
