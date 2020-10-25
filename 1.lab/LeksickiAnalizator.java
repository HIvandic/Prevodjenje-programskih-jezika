import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LeksickiAnalizator {
	String ispis = new String();
	public String getIspis() {
		return this.ispis;
	}
	public void setIspis (String ispis) {
		this.ispis=ispis;
	}
	public static void main (String [] args) {
		LeksickiAnalizator lex = new LeksickiAnalizator();
		int linija = 0;
		String jedno;
		String [] odvojeno;
		try (BufferedReader buf=new BufferedReader(new InputStreamReader(
				//new BufferedInputStream(new FileInputStream("C:\\testovi2\\test01\\test.in")),"UTF-8"))) {
				 System.in))) {
			while ((jedno=buf.readLine())!=null) {
				linija++;
				odvojeno = jedno.split(" ");
				lex.analiziraj(odvojeno,linija);
			}
		} catch (IOException exc) {}
		System.out.print(lex.ispis);
	}
	public void analiziraj (String [] odvojeno, int linija) {
		char [] kasnije;
		int kraj = 0;
		for (String analiza : odvojeno) {
			if (analiza.equals("//") || kraj==1) {
				kraj = 0;
				break;
			}
			
			else if (analiza.equals("za")) {
				ispis+= "KR_ZA "+linija+" "+analiza+"\n";
			}
			else if (analiza.equals("od")) {
				ispis+= "KR_OD "+linija+" "+analiza+"\n";
			}
			else if (analiza.equals("do")) {
				ispis+= "KR_DO "+linija+" "+analiza+"\n";
			}
			else if (analiza.equals("az")) {
				ispis+= "KR_AZ "+linija+" "+analiza+"\n";
			}
			else {
				kasnije = analiza.toCharArray();
				for (int i=0; i<analiza.length(); ++i) {
					if (kasnije[i] == '/') {
						if ((i<analiza.length()-1) && kasnije[i+1] == '/') {
							kraj = 1;
							break;
						}
						else  {
							ispis+= "OP_DIJELI "+linija+" "+kasnije[i]+"\n";
						}
					}
					else if (kasnije[i] == '=') ispis+= "OP_PRIDRUZI "+linija+" "+kasnije[i]+"\n";
					else if (kasnije[i] == '+') ispis+= "OP_PLUS "+linija+" "+kasnije[i]+"\n";
					else if (kasnije[i] == '-') ispis+= "OP_MINUS "+linija+" "+kasnije[i]+"\n";
					else if (kasnije[i] == '*') ispis+= "OP_PUTA "+linija+" "+kasnije[i]+"\n";
					else if (kasnije[i]=='(') ispis+= "L_ZAGRADA "+linija+" "+kasnije[i]+"\n";
					else if (kasnije[i] == ')') ispis+= "D_ZAGRADA "+linija+" "+kasnije[i]+"\n";
					else if ((isInteger(kasnije[i]))) {
						ispis+= "BROJ "+linija+" "+kasnije[i];
						while ((i<(analiza.length()-1)) && (isInteger(kasnije[i+1]))) {
							++i;
							ispis+=kasnije[i];
						}
						ispis+="\n";
					}
					else if (isIdn(kasnije[i])) {
						String var=""+kasnije[i];
						while ((i<(analiza.length()-1)) && (isIdn(kasnije[i+1]) || isInteger(kasnije[i+1]))) {
							++i;
							var+=kasnije[i];
						}
						ispis+= "IDN "+linija+" "+var+"\n";
					}			
				}
			}
		}
	}
	public static boolean isInteger (char analizaU) {
		String analiza = "" + analizaU;
		try {
			if ((Integer.parseInt(analiza))==((Integer.parseInt(analiza)))) return true;
		} catch (NumberFormatException exc){
			return false;
		}
		return true;
	}
	public static boolean isIdn (char analiza) {
		if (analiza=='\t') return false;
		if (analiza != '+' && analiza!= '-' && analiza != '=' && analiza!='*' && analiza != '/' 
				&& analiza != '(' && analiza != ')') {
			return true;
		}
		return false;
	}
}
