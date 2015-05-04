/**
	* Programa principal que gerencia as linhas da linguagem New
 * 
 */

class Interpretador {
    private String linhas[];
    Operacao op = new Operacao();
    Funcao novafuncao = new Funcao();
	Funcoes funcoes = new Funcoes();
	boolean funcao_iniciada=false;
	int contador_chaves=0,inicioFuncao;
	String nomeFuncao;
	
    public void interpreta(String l[]) {
        this.linhas = l;

        for(int i = 0; i < this.linhas.length; i++) {
            if(this.linhas[i] != null) {
                // TODO: interpretar a linha
                System.out.println("Linha " + (i + 1) + ": " + this.linhas[i]);
                if(linhas[i].contains("main") || linhas[i].contains("function")){
					int j = linhas[i].indexOf("(");
					int x=j;
					while(linhas[i].charAt(x) != ' ') x--;
					nomeFuncao = linhas[i].substring(x+1,j);
					inicioFuncao = i;
					funcao_iniciada = true;
					contador_chaves++;
				}
				if(funcao_iniciada){
					if(linhas[i].contains("}")){
						contador_chaves--;
					}
					if(linhas[i].contains("if")){
						contador_chaves++;
					}
					if(linhas[i].contains("while")){
						contador_chaves++;
					}
					if(linhas[i].contains("else")){
						contador_chaves++;
					}
					if(contador_chaves==0){
						Funcao novafuncao = new Funcao();
						novafuncao.setInicio(inicioFuncao);
						novafuncao.setFim(i);
						novafuncao.setNome(nomeFuncao);
						funcoes.addFuncao(novafuncao);
					}
				}
					
            }
        }
	
		funcoes.mostraFuncoes();
	}
    public Inteiro executaFuncao(String nome){
		Funcao funcao = new Funcao();
		funcao = funcoes.getFuncaoNome(nome);
	
		
	
	
		Inteiro v = new Inteiro();
		return v;
		
	}

    
}
