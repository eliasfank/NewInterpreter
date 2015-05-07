/**
	* Programa principal que gerencia as linhas da linguagem New
 * 
 */
import java.util.*;
class Interpretador {
    private String linhas[];
    Operacao op = new Operacao();
    Funcao novafuncao = new Funcao();
	Funcoes funcoes = new Funcoes();
	boolean funcao_iniciada=false;
	int contador_chaves=0,inicioFuncao;
	String nomeFuncao, retornoFuncao;
	
	public void salvaParametros(String parametros, String nomeFuncao){
		int atual = 0;
		if(parametros.indexOf(",") < 0){
			//System.out.println(parametros);
			funcoes.getFuncaoNome(nomeFuncao).addArgumento(parametros);
		}
		else{
			atual = parametros.indexOf(",");
			while(atual != -1){
				funcoes.getFuncaoNome(nomeFuncao).addArgumento(parametros.substring(0,atual));
				if(parametros.indexOf(",",atual+1)!=-1)
				atual = parametros.indexOf(",",atual+1);
				else
				break;
			}
			funcoes.getFuncaoNome(nomeFuncao).addArgumento(parametros.substring(atual+1,parametros.length()).trim());
		}
	
		
	}
	
    public void achaFuncoes(String l[]) {
        this.linhas = l;
		String parametros = "";
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
					if((j+1)!=linhas[i].indexOf(")")) parametros = (linhas[i].substring(j+1,linhas[i].indexOf(")")));
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
						if(parametros != "")
						salvaParametros(parametros,nomeFuncao);
						parametros = "";
					}
				}
					
            }
        }
	
		//funcoes.mostraFuncoes();
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
			System.out.println(result);
		}
		if(linha.contains("[")){
			inicio = linha.indexOf("[")+1;
			fim = linha.indexOf("]");
			result = linha.substring(inicio, fim);
			System.out.println(result);
		}
		
		return;
	}
	
	public void atribuicao(String linha, String funcao){
		//System.out.println("=================================");
		int i,parcial=0;
		char operador='i';
		String[] separa = linha.split("=");
		String varDestino, processar;
		varDestino = separa[0].trim();
		processar = separa[1].trim();
		processar = processar.substring(0,processar.indexOf(";"));
		if(processar.contains("(")){
			Var v = new Var();
			System.out.println("funaaaaao");
			v = executaFuncao(pega_nome_funcao(processar), valor_parametros_da_linha(processar, funcao));
			funcoes.getFuncaoNome(funcao).variaveis.getVarNome(varDestino).setValor(((Inteiro)(v)).getValor());
			return;
		}
		//System.out.println(processar);
		separa = processar.split(" ");
		
		for(i=(separa.length-1);i>=0;i--){
			if(i%2==0){ 
				try{  
					Integer.parseInt(separa[i]); 
					//System.out.println("numero: "+separa[i]);
					switch (operador){
						case 'i':
							parcial = Integer.parseInt(separa[i]); break;
						case '+':
							parcial = parcial + Integer.parseInt(separa[i]);break;
						case '-':
							parcial = parcial - Integer.parseInt(separa[i]);break;
						case '/':
							parcial = parcial / Integer.parseInt(separa[i]);break;
						case '*':
							parcial = parcial * Integer.parseInt(separa[i]);break;
					}
					
					}catch(Exception e){ 
						//System.out.println("variavel: |"+separa[i]+"|");
						//funcoes.getFuncaoNome(funcao).mostraV();
						//System.out.println(((Inteiro)(funcoes.getFuncaoNome(funcao).variaveis.getVarNome(separa[i]))).getValor());
						
						switch (operador){
							case 'i':
								parcial = ((Inteiro)(funcoes.getFuncaoNome(funcao).variaveis.getVarNome(separa[i]))).getValor(); break;
							case '+':
								parcial = parcial + ((Inteiro)(funcoes.getFuncaoNome(funcao).variaveis.getVarNome(separa[i]))).getValor();break;
							case '-':
								parcial = parcial - ((Inteiro)(funcoes.getFuncaoNome(funcao).variaveis.getVarNome(separa[i]))).getValor();break;
							case '/':
								parcial = parcial / ((Inteiro)(funcoes.getFuncaoNome(funcao).variaveis.getVarNome(separa[i]))).getValor();break;
							case '*':
								parcial = parcial * ((Inteiro)(funcoes.getFuncaoNome(funcao).variaveis.getVarNome(separa[i]))).getValor();break;
					}
							
						}
			}else{ 
					//System.out.println("operador: "+separa[i]);
					operador = separa[i].charAt(0);
			}
		}
		//System.out.println(parcial);
		funcoes.getFuncaoNome(funcao).variaveis.getVarNome(varDestino).setValor(parcial);
		//System.out.println("=================================");
		return;
	}
	
	public boolean linha_comecacom_var(String linha, String funcao){
		String[] separa = linha.split("=");
		separa[0] = separa[0].trim();
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
	
	public String[] pegaArgumentos(String nomeFuncao){
		ArrayList<Var> argumentos = new ArrayList<Var>();
		argumentos = funcoes.getFuncaoNome(nomeFuncao).getArgumentos();
		String[] args = new String[argumentos.size()];
		for (int i = 0; i < argumentos.size(); i++) {
			args[i] = argumentos.get(i).getNome();
		}
		return args;
	}
	public String[] valor_parametros_da_linha(String linha, String funcao){
		ArrayList<String> ar = new ArrayList<String>();
		//System.out.println(linha);
		int inicio, fim;
		inicio = linha.indexOf("(")+1;
		fim = linha.indexOf(")");
		linha = linha.substring(inicio,fim);
		if(inicio == fim)
			return null;
		int atual = 0;
		if(linha.indexOf(",") < 0){
			//System.out.println(linha);
			ar.add(linha.trim());		}
		else{
			atual = linha.indexOf(",");
			while(atual != -1){
				ar.add(linha.substring(0,atual).trim());
				if(linha.indexOf(",",atual+1)!=-1)
				atual = linha.indexOf(",",atual+1);
				else
				break;
			}
			ar.add(linha.substring(atual+1,linha.length()).trim());
		}
		String[] a = new String[ar.size()];
		for(int i=0;i<ar.size();i++){
			if(funcoes.existeVar(ar.get(i),funcao))
				a[i] = Integer.toString(((Inteiro)(funcoes.getFuncaoNome(funcao).variaveis.getVarNome(ar.get(i)))).getValor());
			else
				a[i] = ar.get(i);
            //System.out.println(a[i]);
        }
		return a;
	}
	
    public Var executaFuncao(String nome, String argsPassados[]){
		Var ret = new Var();
		Funcao funcao = new Funcao();
		System.out.println("\nFUNÇÃO: "+nome);
		int i, inicio, fim;
		
		funcao = funcoes.getFuncaoNome(nome);
		inicio = funcao.getInicio()+1;
		fim = funcao.getFim();
		
		String[] argumentosFuncao = pegaArgumentos(nome);
		if(argsPassados != null)
		for(int aux = 0; aux < argsPassados.length ; aux++){
			//System.out.println("!"+argumentosFuncao[aux]+"!");
			//System.out.println(argsPassados[aux]);
			funcao.addIntFuncao(argumentosFuncao[aux],Integer.parseInt(argsPassados[aux]));
		}
		//funcoes.getFuncaoNome(nome).mostraV();
		
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
			if(linhas[i].contains("if")){
					
			}
			if(linha_comecacom_var(linhas[i],nome)){
				atribuicao(linhas[i],nome);
			}
			if(linha_comecacom_funcao(linhas[i])){		
				executaFuncao(pega_nome_funcao(linhas[i]), valor_parametros_da_linha(linhas[i], nome));
			}
			if(linhas[i].startsWith("return")){
				ret = retorna(linhas[i], nome);
				return ret;
				/*
				if(ret instanceof Inteiro)
				System.out.println("Retorno da "+nome+": "+((Inteiro)ret).getValor());
				if(ret instanceof Duplo)
				System.out.println("Retorno da "+nome+": "+((Duplo)ret).getValor());
				if(ret instanceof Stringue)
				System.out.println("Retorno da "+nome+": "+((Stringue)ret).getValor());
			*/
			}
			
		}
		
		//System.out.println("\nVariaveis da "+funcao.getNome());
		//funcao.mostraV();
		//System.out.println();
		
		return ret;
		
	}

}
