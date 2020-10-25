import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SintaksniAnalizator {
	String [] ulaz = new String [10000];
	String ispis = new String();
	int vel;
	int uvlaka;
	int i;
	SintaksniAnalizator () {
		uvlaka = 0;
		ispis = "";
		i = 0;
	}
	public void setVel (int broj) {
		vel = broj;
	}
	public static void main (String [] args) {
		SintaksniAnalizator sin = new SintaksniAnalizator();
		int broj = 0;
		String jedno;
		try (BufferedReader buf=new BufferedReader(new InputStreamReader(
				//new BufferedInputStream(new FileInputStream("C:\\testovi2\\test30\\test.in")),"UTF-8"))) {
				 System.in))) {
			while ((jedno = buf.readLine()) !=null) {
				sin.ulaz[broj++] = jedno;
			}
			sin.setVel(broj);
			sin.analiziraj();
		} catch (IOException exc) {}
		System.out.print(sin.ispis);
	}
	public void analiziraj () {
		ispis += "<program>\n";
		++uvlaka;
		lista_naredbi();
	}
	public void lista_naredbi() {
		String red = new String();
		String [] odvojeno;
		urediIspis();
		ispis += "<lista_naredbi>\n";
		if (i<(vel-1)) {
			if (i!=0) ++i;
			red = ulaz[i];
			odvojeno = red.split(" ");
			if (odvojeno[0].equals("IDN") || odvojeno[0].equals("KR_ZA")) {
				++uvlaka;
				naredba(red,odvojeno);
				lista_naredbi();
			} else if (odvojeno[0].equals("KR_AZ")) {
				++uvlaka;
				urediIspis();
				ispis += "$\n";
				--i;
			} else {
				System.out.print("err "+red+"\n");
				System.exit(0);
			}
		} else {
			++uvlaka;
			urediIspis();
			ispis += "$\n";
		}
		--uvlaka;
	}
	public void naredba(String red, String [] odvojeno) {
		urediIspis();
		ispis += "<naredba>\n";
		++uvlaka;
		if (odvojeno[0].equals("IDN")) {
			naredba_pridruzivanja(red,odvojeno);
		} else if (odvojeno[0].equals("KR_ZA")) {
			za_petlja(red,odvojeno);
		} else {
			System.out.print("err "+red+"\n");
			System.exit(0);
		}
		--uvlaka;
	}
	public void naredba_pridruzivanja (String red, String [] odvojeno) {
		urediIspis();
		ispis += "<naredba_pridruzivanja>\n";
		++uvlaka;
		urediIspis();
		ispis += red+"\n";
		if (i<(vel-1)) {
			++i;
			red = ulaz[i];
			odvojeno = red.split(" ");
			if (odvojeno[0].equals("OP_PRIDRUZI")) {
				urediIspis();
				ispis += red+"\n";
			} else {
				System.out.print("err "+red+"\n");
				System.exit(0);
			}
		} else {
			System.out.print("err kraj\n");
			System.exit(0);
		} 
		E();
		--uvlaka;
	}
	public void za_petlja (String red, String [] odvojeno) {
		urediIspis();
		ispis += "<za_petlja>\n";
		++uvlaka;
		urediIspis();
		ispis += red+"\n";
		if (i<(vel-1)) {
			++i;
			red = ulaz[i];
			odvojeno = red.split(" ");
			if (odvojeno[0].equals("IDN")) {
				urediIspis();
				ispis += red+"\n";
			} else {
				System.out.print("err "+red+"\n");
				System.exit(0);
			}
		} else {
			System.out.print("err kraj\n");
			System.exit(0);
		}
		if (i<(vel-1)) {
			++i;
			red = ulaz[i];
			odvojeno = red.split(" ");
			if (odvojeno[0].equals("KR_OD")) {
				urediIspis();
				ispis += red+"\n";
			} else {
				System.out.print("err "+red+"\n");
				System.exit(0);
			}
		} else {
			System.out.print("err kraj\n");
			System.exit(0);
		} 
		E();
		if (i<(vel-1)) {
			++i;
			red = ulaz[i];
			odvojeno = red.split(" ");
			if (odvojeno[0].equals("KR_DO")) {
				urediIspis();
				ispis += red+"\n";
			} else {
				System.out.print("err "+red+"\n");
				System.exit(0);
			}
		} else {
			System.out.print("err kraj\n");
			System.exit(0);
		} 
		E(); 
		lista_naredbi();
		if (i<(vel-1)) {
			++i;
			red = ulaz[i];
			odvojeno = red.split(" ");
			if (odvojeno[0].equals("KR_AZ")) {
				urediIspis();
				ispis += red+"\n";
			} else {
				System.out.print("err "+red+"\n");
				System.exit(0);
			}
		} else {
			System.out.print("err kraj\n");
			System.exit(0);
		}
		--uvlaka;
	}
	public void E() {
		urediIspis();
		ispis += "<E>\n";
		++uvlaka;
		T();
		ELista();
		--uvlaka;
	}
	public void T() {
		urediIspis();
		ispis += "<T>\n";
		++uvlaka;
		P();
		TLista();
		--uvlaka;
	}
	public void P () {
		urediIspis();
		ispis += "<P>\n";
		++uvlaka;
		String red = new String();
		String odvojeno [];
		if (i<(vel-1)) {
			++i;
			red = ulaz[i];
			odvojeno = red.split(" ");
			if (odvojeno[0].equals("OP_PLUS")) {
				urediIspis();
				ispis += red+"\n";
				P();
			} else if (odvojeno[0].equals("OP_MINUS"))  {
				urediIspis();
				ispis += red+"\n";
				P();
			} else if (odvojeno[0].equals("L_ZAGRADA")) {
				urediIspis();
				ispis += red+"\n";
				E();
				if (i<(vel-1)) {
					++i;
					red = ulaz[i];
					odvojeno = red.split(" ");
					if (odvojeno[0].equals("D_ZAGRADA")) {
						urediIspis();
						ispis += red+"\n";
					} else {
						System.out.print("err "+red+"\n");
						System.exit(0);
					}
				} else System.out.print("err kraj\n");
			} else if (odvojeno[0].equals("IDN")) {
				urediIspis();
				ispis += red+"\n";
			} else if (odvojeno[0].equals("BROJ")) {
				urediIspis();
				ispis += red+"\n";
			} else {
				System.out.print("err "+red+"\n");
				System.exit(0);
			}
		} else {
			System.out.print("err kraj\n");
			System.exit(0);
		}
		--uvlaka;
	}
	public void TLista () {
		String red = new String();
		String odvojeno [];
		urediIspis();
		ispis+="<T_lista>\n";
		++uvlaka;
		if (i<(vel-1)) {
			++i;
			red = ulaz[i];
			odvojeno = red.split(" ");
			if (odvojeno[0].equals("OP_PUTA")) {
				urediIspis();
				ispis+=red+"\n";
				T();
			} else if (odvojeno[0].equals("OP_DIJELI")) {
				urediIspis();
				ispis+=red+"\n";
				T();
			}  else {
				urediIspis();
				--i;
				ispis+="$\n";
			}
		} else {
			urediIspis();
			ispis+="$\n";
		}
		--uvlaka;
	}
	public void ELista () {
		String red = new String();
		String odvojeno [];
		urediIspis();
		ispis+="<E_lista>\n";
		++uvlaka;
		if (i<(vel-1)) {
			++i;
			red = ulaz[i];
			odvojeno = red.split(" ");
			if (odvojeno[0].equals("OP_PLUS")) {
				urediIspis();
				ispis+=red+"\n";
				E(); 
			} else if (odvojeno[0].equals("OP_MINUS")) {
				urediIspis();
				ispis+=red+"\n";
				E();
			}  else {
				--i;
				urediIspis();
				ispis+="$\n";
			}
		} else {
			urediIspis();
			ispis+="$\n";
		}
		--uvlaka;
	}
	public void urediIspis () {
		for (int j=0; j<uvlaka; ++j) {
			ispis+=" ";
		}
	}
	public int velicina (String [] ulaz) {
		int size=0;
		for (String jedno : ulaz) {
			if (jedno.equals(null) && !jedno.equals("")) ++size;
			else return size;
		}
		return size;
	}
}