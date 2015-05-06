/**
	* Interpretador da Linguagem New
	* 
 * 
 */

import java.util.Scanner;
import java.io.*;

class New{
    public static void main(String args[]) throws Exception {
        FileReader fileReader = new FileReader(args[0]);
        Interpretador b;
        String linhas[] = new String[2000]; // arquivo pode ter, no máximo, 2000 linhas.

        b = new Interpretador();

        String charArray="";
        int i = 0;
        int ch;
        char c;
        while ((ch = fileReader.read()) != -1) {
			c = ((char)ch);
			if(c == '\n' || c=='	')
				c=' ';
				
			if(charArray != "" && charArray.charAt(charArray.length()-1) == ' ' && c== ' '){
			}
			else
			if(charArray == "" && c==' ')
				charArray = "";
			else{
			if(c == '{'){
				charArray+=c;
				linhas[i++] = charArray;
				charArray = "";
			}else
			if(c == '}'){
				charArray+=c;
				linhas[i++] = charArray;
				charArray = "";
			}else
			if(c == ';'){
				charArray+=c;
				linhas[i++] = charArray;
				charArray = "";
			}else
			charArray+=c;
			
			}
		}

        b.interpreta(linhas);
        Object a = new Object();
        a = b.executaFuncao("main");
    }
}
