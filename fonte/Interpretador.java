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
                //System.out.println("Linha " + (i + 1) + ": " + this.linhas[i]);
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
	
	public String var_da_linha(String linha){
		String[] separa = linha.split(" ");
		separa[0].trim();
		if(separa.length>2){
			return separa[1];
		}
		else{
			separa = separa[1].split(";");
			return separa[0];
		}
	}

	public void printa(String linha){
		return;
	}
	
	public void atribuicao(String funcao){
		return;
	}
	
	public boolean linha_comecacom_var(String linha, String funcao){
		String[] separa = linha.split("=");
		separa[0].trim();
		if(funcoes.existeVar(separa[0],funcao))
			return true;
		else
			return false;
	}
	
	public String valor_da_linha(String linha){
		if(linha.startsWith("string")){
			int inicio, fim;
			inicio = linha.indexOf('"')+1;
			fim = linha.indexOf('"',inicio);
			return linha.substring(inicio,fim);
		}
		String[] separa = linha.split("=");
		separa = separa[1].split(" ");
		separa[1].trim();
		if(separa.length>2){
			return separa[1];
		}
		else{
			separa = separa[1].split(";");
			return separa[0];
		}
	}
	
    public Inteiro executaFuncao(String nome){
		int i, inicio, fim;
		Funcao funcao = new Funcao();
		funcao = funcoes.getFuncaoNome(nome);
		inicio = funcao.getInicio();
		fim = funcao.getFim();
		for(i=inicio;i<fim;i++){
			if(linhas[i].startsWith("int")){
				//System.out.println("declaracao int");
				String a = var_da_linha(linhas[i]);
				//System.out.println("|"+a+"|");
				if(linhas[i].contains("="))
					funcao.addIntFuncao(a,Integer.parseInt(valor_da_linha(linhas[i])));
				else
					funcao.addIntFuncao(a,0);
			}
			if(linhas[i].startsWith("double")){
				//System.out.println("declaracao double");
				String a = var_da_linha(linhas[i]);
				//System.out.println("|"+a+"|");
				if(linhas[i].contains("="))
					funcao.addDoubleFuncao(a,Double.parseDouble(valor_da_linha(linhas[i])));
				else
					funcao.addDoubleFuncao(a,0);
			}
			if(linhas[i].startsWith("string")){
				//System.out.println("declaracao string");
				String a = var_da_linha(linhas[i]);
				//System.out.println("|"+a+"|");
				if(linhas[i].contains("="))
					funcao.addStringFuncao(a,valor_da_linha(linhas[i]));
				else
					funcao.addStringFuncao(a,"");
			}
			if(linhas[i].startsWith("printf")){
				//System.out.println("printando");
				printa(linhas[i]);
			}
			if(linha_comecacom_var(linhas[i],nome)){
				//System.out.println("atribuicao");
				atribuicao(linhas[i]);
			}
			
		}
		
		System.out.println("Variaveis da "+funcao.getNome());
		funcao.mostraV();
	
		Inteiro v = new Inteiro();
		return v;
		
	}

    
}
