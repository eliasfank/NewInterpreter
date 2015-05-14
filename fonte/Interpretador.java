/**
	* Programa principal que gerencia as linhas da linguagem New
 * 
 */
 
import java.util.*;

class Interpretador {
    private String linhas[];
    private Funcao novafuncao = new Funcao();
	private Vector <Funcao> funcoes = new Vector <Funcao>();
	
	
	public void setLinhas(String l[]){
		linhas = l;
	}
	
	
	public void addFuncao(Object f){
		if(f instanceof Funcao){
			funcoes.add((Funcao)f);
			((Funcao)f).adicionaListaVariaveis();
		}
	}
	
	
	public Funcao getFuncaoIndice(int a){
		if (a >= 0 && a < funcoes.size())
			return funcoes.elementAt(a);
		else
			return null;
	}
	
	
	public Funcao getFuncaoNome(String a){
		for(int i = 0;i<funcoes.size();i++)
			if(a.equals(getFuncaoIndice(i).getNome()))
				return funcoes.elementAt(i);
		return null;
	}
	
	
	public boolean existeVar(String vari, String funcao){
		Funcao a = new Funcao();
		a = getFuncaoNome(funcao);
		
		if(a.temVar(vari))
			return true;
		else
			return false;
	}
	
	
	public void salvaParametros(String parametros, String nomeFuncao){
		if(parametros.indexOf(",") < 0){
			getFuncaoNome(nomeFuncao).addArgumento(parametros);
		}else{
			String[] separa = parametros.split(",");
			for (int i =0; i<separa.length;i++){
				getFuncaoNome(nomeFuncao).addArgumento(separa[i].trim());
			}
		}
	}
	
	
    public void achaFuncoes() {
        boolean funcao_iniciada=false;
        int contador_chaves=0,inicioFuncao=0;
		String parametros = "", nomeFuncao="", retornoFuncao="";
		
        for(int i = 0; i < this.linhas.length; i++) {
			
            if(this.linhas[i] != null) {
				
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
						addFuncao(novafuncao);
						if(parametros != "")
							salvaParametros(parametros,nomeFuncao);
						parametros = "";
					}
				}	
				
            }
            
        }
        
	}
	
	
	public String varDaLinha(String linha){
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
			if(existeVar(result,nomeFuncao))
				System.out.println(getFuncaoNome(nomeFuncao).getListaVariaveis().getVarNome(result).getValor());
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
		int i;
		double parcial=0, num;
		char operador='i';
		
		String[] separa = linha.split("=");
		String varDestino, processar;
		varDestino = separa[0].trim();
		processar = separa[1].trim();
		processar = processar.substring(0,processar.indexOf(";"));
		processar = processar.replaceAll("\\+"," + " );
		processar = processar.replaceAll("\\-"," - " );
		processar = processar.replaceAll("\\/"," / " );
		processar = processar.replaceAll("\\*"," * " );
		processar = processar.replaceAll("\\%"," % " );
		
		if(processar.contains("(")){
			Var v = new Var();
			v = executaFuncao(pegaNomeFuncao(processar), valorParametroDaLinha(processar, funcao), funcao);
			if(v instanceof Inteiro)
				getFuncaoNome(funcao).getListaVariaveis().getVarNome(varDestino).setValor(((Inteiro)(v)).getValor());
			if(v instanceof Duplo)
				getFuncaoNome(funcao).getListaVariaveis().getVarNome(varDestino).setValor(((Duplo)(v)).getValor());
			return;
		}
		
		separa = processar.split(" ( )?");
		for(i=(separa.length-1);i>=0;i--){
			if(i%2==0){ 
				try{
					num = Double.parseDouble(separa[i]); 			
					}catch(Exception f){
						if(getFuncaoNome(funcao).getListaVariaveis().getVarNome(separa[i]).getTipo().equals("int"))
							num = ((Inteiro)(getFuncaoNome(funcao).getListaVariaveis().getVarNome(separa[i]))).getValor();
						else
							num = ((Duplo)(getFuncaoNome(funcao).getListaVariaveis().getVarNome(separa[i]))).getValor();
					}	
				switch(operador){
					case 'i':
						parcial = num; break;
					case '+':
						parcial = parcial + num;break;
					case '-':
						parcial = parcial - num;break;
					case '/':
						parcial = parcial / num;break;
					case '*':
						parcial = parcial * num;break;
					case '%':
						parcial = parcial % num;break;
				}	
			}else{ 
				operador = separa[i].charAt(0);
			}
		}
		
		if(getFuncaoNome(funcao).getListaVariaveis().getVarNome(varDestino).getTipo().equals("int"))
			getFuncaoNome(funcao).getListaVariaveis().getVarNome(varDestino).setValor(((Double)(parcial)).intValue());
		else
			getFuncaoNome(funcao).getListaVariaveis().getVarNome(varDestino).setValor(parcial);
		return;
		
	}
	
	
	public boolean linha_comecacom_var(String linha, String funcao){
		String[] separa = linha.split("=");
		separa[0] = separa[0].trim();
		if(existeVar(separa[0],funcao))
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
		}else{
			separa = separa[1].split(";");
			a = separa[0];
		}
		
		if(existeVar(a,funcao)){
			if(getFuncaoNome(funcao).getListaVariaveis().getVarNome(a).getTipo().equals("int")){
				v = new Inteiro();
				v.setValor(getFuncaoNome(funcao).getListaVariaveis().getVarNome(a).getValor());
			}
			if(getFuncaoNome(funcao).getListaVariaveis().getVarNome(a).getTipo().equals("double")){
				v = new Duplo();
				v.setValor(getFuncaoNome(funcao).getListaVariaveis().getVarNome(a).getValor());
			}
			if(getFuncaoNome(funcao).getListaVariaveis().getVarNome(a).getTipo().equals("string")){
				v = new Stringue();
				v.setValor(getFuncaoNome(funcao).getListaVariaveis().getVarNome(a).getValor());
			}
			v.setNome(getFuncaoNome(funcao).getListaVariaveis().getVarNome(a).getNome());
			v.setTipo(getFuncaoNome(funcao).getListaVariaveis().getVarNome(a).getTipo());
		}
		else{
			if(getFuncaoNome(funcao).getRetorno().equals("int")){
				v = new Inteiro();
				v.setValor(Integer.parseInt(a));
			}
			if(getFuncaoNome(funcao).getRetorno().equals("double")){
				v = new Duplo();
				v.setValor(Double.parseDouble(a));
			}
			if(getFuncaoNome(funcao).getRetorno().equals("string")){
				v = new Stringue();
				v.setValor(a);
			}
		}
		
		return v;
	}
	
	
	public boolean linhaComecaComFuncao(String linha){
		int fim;
		fim = linha.indexOf("(");
		
		if(fim<0) return false;
		
		linha = linha.substring(0,fim);
		if(getFuncaoNome(linha) != null){
			return true;
		}
			return false;
			
	}
	
	
	public String pegaNomeFuncao(String linha){
		int fim;
		fim = linha.indexOf("(");
		linha = linha.substring(0,fim);
		return linha;
	}
	
	
	public String valorDaLinha(String linha){
		
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
		argumentos = getFuncaoNome(nomeFuncao).getArgumentos();
		String[] args = new String[argumentos.size()];
		
		for (int i = 0; i < argumentos.size(); i++) {
			args[i] = argumentos.get(i).getNome();
		}
		return args;
	}
	
	
	public String[] valorParametroDaLinha(String linha, String funcao){
		int inicio, fim;
		ArrayList<String> ar = new ArrayList<String>();
		
		inicio = linha.indexOf("(")+1;
		fim = linha.indexOf(")");
		linha = linha.substring(inicio,fim);
		
		if(inicio == fim)
			return null;
			
		if(linha.indexOf(",") < 0){
			ar.add(linha.trim());		
		}else{
			String[] separa = linha.split(",");
			for(int i =0; i < separa.length ; i++){
				ar.add(separa[i].trim());
			}
		}
		
		String[] a = new String[ar.size()];
		for(int i=0;i<ar.size();i++){
			if(existeVar(ar.get(i),funcao)){
				if(getFuncaoNome(funcao).getListaVariaveis().getVarNome(ar.get(i)).getTipo().equals("int"))
					a[i] = Integer.toString(((Inteiro)(getFuncaoNome(funcao).getListaVariaveis().getVarNome(ar.get(i)))).getValor());
				else
					a[i] = Double.toString(((Duplo)(getFuncaoNome(funcao).getListaVariaveis().getVarNome(ar.get(i)))).getValor());
			}else
				a[i] = ar.get(i);
        }
		return a;
	}
	
	
    public Var executaFuncao(String nome, String argsPassados[] , String quemChamou){
		boolean bloqueio;
		int i, inicio, fim;
		Var ret = new Var();
		Funcao funcao = new Funcao();
		funcao = getFuncaoNome(nome);
		inicio = funcao.getInicio()+1;
		fim = funcao.getFim();
		
		if(quemChamou.equals(nome))
			getFuncaoNome(nome).adicionaListaVariaveis();
			
		String[] argumentosFuncao = pegaArgumentos(nome);
		
		if(argsPassados != null)
		for(int aux = 0; aux < argsPassados.length ; aux++){
			if(existeVar(argumentosFuncao[aux],nome))
				if(getFuncaoNome(nome).getListaVariaveis().getVarNome(argumentosFuncao[aux]).getTipo().equals("int"))
					((Inteiro)(getFuncaoNome(nome).getListaVariaveis().getVarNome(argumentosFuncao[aux]))).setValor(Integer.parseInt(argsPassados[aux]));
				else
					((Duplo)(getFuncaoNome(nome).getListaVariaveis().getVarNome(argumentosFuncao[aux]))).setValor(Double.parseDouble(argsPassados[aux]));
			else{
				try{
					funcao.addIntFuncao(argumentosFuncao[aux],Integer.parseInt(argsPassados[aux]));
				}catch(Exception e){
					funcao.addDoubleFuncao(argumentosFuncao[aux],Double.parseDouble(argsPassados[aux]));
				}
			}
		}
		
		bloqueio = false;
		for(i=inicio;i<fim;i++){
			
			if(linhas[i].contains("while")){
				getFuncaoNome(nome).adicionaOperacao(linhas[i]);
				getFuncaoNome(nome).getOperacao().setNLinha(i);
				if(getFuncaoNome(nome).testaOperacao())
					bloqueio = false;
				else
					bloqueio = true;
			}
			
			if(linhas[i].contains("if")){
				getFuncaoNome(nome).adicionaOperacao(linhas[i]);
				
				if(getFuncaoNome(nome).testaOperacao()){
					getFuncaoNome(nome).getOperacao().setExecutado(true);
					bloqueio = false;
				}
				else{
					getFuncaoNome(nome).getOperacao().setExecutado(false);
					bloqueio = true;
				}
				
			}
			
			if(linhas[i].contains("}")){
				if(getFuncaoNome(nome).getOperacao().getTipo().equals("if")){
					if(!linhas[i+1].contains("else")){
						getFuncaoNome(nome).removeOperacao();
						bloqueio = false;
					}else{
						if(getFuncaoNome(nome).getOperacao().getExecutado())
							bloqueio = true;
						else
							bloqueio = false;
						i++;
					}
				}else
				if(getFuncaoNome(nome).getOperacao().getTipo().equals("while")){
					if(getFuncaoNome(nome).testaOperacao()){
						i = getFuncaoNome(nome).getOperacao().getNLinha();
					}else
					getFuncaoNome(nome).removeOperacao();
				}
			}
			
			if(bloqueio == true) continue;
			
			if(linhas[i].startsWith("int")){
				String a = varDaLinha(linhas[i]);
				if(linhas[i].contains("="))
					funcao.addIntFuncao(a,Integer.parseInt(valorDaLinha(linhas[i])));
				else
					funcao.addIntFuncao(a,0);
			}
			
			if(linhas[i].startsWith("double")){
				String a = varDaLinha(linhas[i]);
				if(linhas[i].contains("="))
					funcao.addDoubleFuncao(a,Double.parseDouble(valorDaLinha(linhas[i])));
				else
					funcao.addDoubleFuncao(a,0);
			}
			
			if(linhas[i].startsWith("string")){
				String a = varDaLinha(linhas[i]);
				if(linhas[i].contains("="))
					funcao.addStringFuncao(a,valorDaLinha(linhas[i]));
				else
					funcao.addStringFuncao(a,"");
			}
			
			if(linhas[i].startsWith("printf")){
				printa(linhas[i],funcao.getNome());
			}
			
			if(linha_comecacom_var(linhas[i],nome)){
				atribuicao(linhas[i],nome);
			}
			
			if(linhaComecaComFuncao(linhas[i])){		
				executaFuncao(pegaNomeFuncao(linhas[i]), valorParametroDaLinha(linhas[i], nome), nome);
			}
			
			if(linhas[i].startsWith("return")){
				ret = retorna(linhas[i], nome);
				getFuncaoNome(nome).removeListaVariaveis();
				if(!nome.equals(quemChamou))
					getFuncaoNome(nome).adicionaListaVariaveis();
				return ret;
			}
				
		}
			
		return ret;	
	}
	
}
