import java.util.*;
public class Operacao{
	private String A;
	private String B;
	private String comp;
	private String tipo;
	private int nLinha;
	private boolean ifExecutado;
	public void setExecutado(boolean a){
		ifExecutado = a;
	}
	public void setA(String a){
		A = a;
	}
	public void setB(String a){
		B = a;
	}
	public void setComp(String a){
		comp = a;
	}
	public void setTipo(String a){
		tipo = a;
	}
	public void setNLinha(int a){
		nLinha = a;
	}
	public String getA(){
			return A;
	}
	public String getB(){
			return B;
	}
	public String getComp(){
			return comp;
	}
	public String getTipo(){
			return tipo;
	}
	public int getNLinha(){
		return nLinha;
	}
	public boolean getExecutado(){
		return ifExecutado;
	}
	
	
}
