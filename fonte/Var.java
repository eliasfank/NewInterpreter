abstract public class Var{
	private String nome;
	private String tipo;
	public void setNome(String n){
		nome = n;
	}
	public void setTipo(String t){
		tipo = t;
	}
	public String getNome(){
		return nome;
	}
	public String getTipo(){
		return tipo;
	}
	abstract public void setValor(Object a);
	abstract public Object getValor();
}
