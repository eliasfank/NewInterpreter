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
	String nomeFuncao, retornoFuncao;
	
    public void interpreta(String l[]) {
        this.linhas = l;

        for(int i = 0; i < this.linhas.length; i++) {
            if(this.linhas[i] != null) {
                //System.out.println("Linha " + (i + 1) + ": " + this.linhas[i]);
                if(linhas[i].contains("main") || linhas[i].contains("function")){
					if(linhas[i].contains("function")){
						String[] separa = linhas[i].split(" ");
						retornoFuncao = separa[0];
					}
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
						novafuncao.setRetorno(retornoFuncao);
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

	public void printa(String linha, String nomeFuncao){
		int inicio, fim;
		String result = "";
		if(linha.contains("(")){
			inicio = linha.indexOf("(")+1;
			fim = linha.indexOf(")");
			result = linha.substring(inicio, fim);
			result= result.trim();
			if(funcoes.existeVar(result,nomeFuncao))
			System.out.println(funcoes.getFuncaoNome(nomeFuncao).variaveis.getVarNome(result).getValor());
			else
			System.out.println("|"+result+"|");
		}
		if(linha.contains("[")){
			inicio = linha.indexOf("[")+1;
			fim = linha.indexOf("]");
			result = linha.substring(inicio, fim);
			System.out.println("|"+result+"|");
		}
		
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
	
	public Var retorna(String linha, String funcao){
		Var v = new Var();
		String[] separa = linha.split(" ");
		String a;
		separa[0].trim();
		if(separa.length>2){
			a = separa[1];
		}
		else{
			separa = separa[1].split(";");
			a = separa[0];
		}
		if(funcoes.existeVar(a,funcao)){
			
			if(funcoes.getFuncaoNome(funcao).variaveis.getVarNome(a).getTipo().equals("int")){
				v = new Inteiro();
				v.setValor(funcoes.getFuncaoNome(funcao).variaveis.getVarNome(a).getValor());
			}
			if(funcoes.getFuncaoNome(funcao).variaveis.getVarNome(a).getTipo().equals("double")){
				v = new Duplo();
				v.setValor(funcoes.getFuncaoNome(funcao).variaveis.getVarNome(a).getValor());
			}
			if(funcoes.getFuncaoNome(funcao).variaveis.getVarNome(a).getTipo().equals("string")){
				v = new Stringue();
				v.setValor(funcoes.getFuncaoNome(funcao).variaveis.getVarNome(a).getValor());
			}

			v.setNome(funcoes.getFuncaoNome(funcao).variaveis.getVarNome(a).getNome());
			v.setTipo(funcoes.getFuncaoNome(funcao).variaveis.getVarNome(a).getTipo());
		}
		else{
			if(funcoes.getFuncaoNome(funcao).getRetorno().equals("int")){
				v = new Inteiro();
				v.setValor(Integer.parseInt(a));
			}
			if(funcoes.getFuncaoNome(funcao).getRetorno().equals("double")){
				v = new Duplo();
				v.setValor(Double.parseDouble(a));
			}
			if(funcoes.getFuncaoNome(funcao).getRetorno().equals("string")){
				v = new Stringue();
				v.setValor(a);
			}
		}
		return v;
	}
	
	public boolean linha_comecacom_funcao(String linha){
		int fim;
		fim = linha.indexOf("(");
		if(fim<0) return false;
		linha = linha.substring(0,fim);
		if(funcoes.getFuncaoNome(linha) != null){
			return true;
		}
			return false;
	}
	
	public String pega_nome_funcao(String linha){
		int fim;
		fim = linha.indexOf("(");
		linha = linha.substring(0,fim);
		return linha;
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
	
    public Var executaFuncao(String nome){
		int i, inicio, fim;
		Var ret = new Var();
		Funcao funcao = new Funcao();
		funcao = funcoes.getFuncaoNome(nome);
		inicio = funcao.getInicio()+1;
		fim = funcao.getFim();
		for(i=inicio;i<fim;i++){
			if(linhas[i].startsWith("int")){
				String a = var_da_linha(linhas[i]);
				if(linhas[i].contains("="))
					funcao.addIntFuncao(a,Integer.parseInt(valor_da_linha(linhas[i])));
				else
					funcao.addIntFuncao(a,0);
			}
			if(linhas[i].startsWith("double")){
				String a = var_da_linha(linhas[i]);
				if(linhas[i].contains("="))
					funcao.addDoubleFuncao(a,Double.parseDouble(valor_da_linha(linhas[i])));
				else
					funcao.addDoubleFuncao(a,0);
			}
			if(linhas[i].startsWith("string")){
				String a = var_da_linha(linhas[i]);
				if(linhas[i].contains("="))
					funcao.addStringFuncao(a,valor_da_linha(linhas[i]));
				else
					funcao.addStringFuncao(a,"");
			}
			if(linhas[i].startsWith("printf")){
				printa(linhas[i],funcao.getNome());
			}
			if(linha_comecacom_var(linhas[i],nome)){
				atribuicao(linhas[i]);
			}
			if(linha_comecacom_funcao(linhas[i])){		
				executaFuncao(pega_nome_funcao(linhas[i]));
			}
			if(linhas[i].startsWith("return")){
				ret = retorna(linhas[i], nome);
				if(ret instanceof Inteiro)
				System.out.println("Retorno da "+nome+": "+((Inteiro)ret).getValor());
				if(ret instanceof Duplo)
				System.out.println("Retorno da "+nome+": "+((Duplo)ret).getValor());
				if(ret instanceof Stringue)
				System.out.println("Retorno da "+nome+": "+((Stringue)ret).getValor());
			}
			
		}
		
		System.out.println("\nVariaveis da "+funcao.getNome());
		funcao.mostraV();
		System.out.println();
		
		return ret;
		
	}

}
