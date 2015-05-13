/**
	* Interpretador da Linguagem New
	* Trata-se de um trabalho da Matéria ProgramaçãoI da UFFS
	* 
	* Criado por:
	* Elias Fank  // email: "eliasfank@hotmail.com"
	* Leonardo Mantovani  // email: "mantovani28@gmail.com"
 * 
 */

import java.util.Scanner;
import java.io.*;

class New{
    public static void main(String args[]) throws Exception {
		System.out.println();
        FileReader fileReader = new FileReader(args[0]);
        Interpretador b;
        String linhas[] = new String[2000]; // arquivo pode ter, no máximo, 2000 linhas.
        b = new Interpretador();

        String charArray="";
        int i = 0;
        int ch;
        char c;
        while ((ch = fileReader.read()) != -1) {   // le o arquivo caracter por caracter
			c = ((char)ch);
			
			if(c == '\n' || c=='	') //tira todos enter e tabs do código
				c=' ';
	
			if(charArray != "" && charArray.charAt(charArray.length()-1) == ' ' && c== ' '){ // caso o ultimo caracter da linha ja tiver um espaço
				continue;																	 // nao adiciona espaço novamente
			}																				 
			else
			if(charArray == "" && c==' ')// impede que uma linha comece com espaço
				charArray = "";				
			else{
			if(c == '{'){
				charArray+=c;				//quando encontra um abre chaves
				linhas[i++] = charArray;	//guarda a linha
				charArray = "";
			}else
			if(c == '}'){
				charArray+=c;				//quando encontra um fecha chaves
				linhas[i++] = charArray;	//guarda a linha
				charArray = "";
			}else
			if(c == ';'){
				charArray+=c;				//quando encontra um ;
				linhas[i++] = charArray;	//guarda a linha
				charArray = "";
			}else
			charArray+=c;					//se nenhuma das opçoes acima
											//concatena o caracter no final da linha
			}
		}

        b.achaFuncoes(linhas);
        Object a = new Object();
        String[] argss = null;
        a = b.executaFuncao("main", argss,"");
        System.out.println();
    }
}
