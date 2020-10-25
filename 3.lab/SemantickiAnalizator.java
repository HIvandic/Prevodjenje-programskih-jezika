import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class SemantickiAnalizator {
	String [] ulaz = new String [10000];
	String ispis = new String();
	String red = new String();
	String odvojeno [];
	int vel;
	int i;
	HashMap<String, String> mapa= new HashMap <String, String>();
	SemantickiAnalizator () {
		ispis = "";
		i = 0;
	}
	public void setVel (int broj) {
		vel = broj;
	}
	public static void main (String [] args) {
		SemantickiAnalizator sem = new SemantickiAnalizator();
		int broj = 0;
		String jedno;
		try (BufferedReader buf=new BufferedReader(new InputStreamReader(
				//new BufferedInputStream(new FileInputStream("C:\\testovi2\\test01\\test.in")),"UTF-8"))) {
			 System.in))) {
			while ((jedno = buf.readLine()) !=null) {
				sem.ulaz[broj++] = jedno;
			}
			sem.setVel(broj);
			sem.analiziraj();
		} catch (IOException exc) {}
		System.out.print(sem.ispis);
	}
	public void analiziraj () {
		for (i=0; i<vel; ++i) {
			red = ulaz[i];
			odvojeno = red.split(" ");
			odvojeno = uredi(odvojeno);
			if (odvojeno[0].equals("<naredba>")) {
				obradi(mapa);
			}
		}
	}
	public HashMap<String, String>  obradi (HashMap<String, String> mapa) {
		++i;
		red = ulaz[i];
		odvojeno = red.split(" ");
		odvojeno = uredi(odvojeno);
		if (odvojeno[0].equals("<naredba_pridruzivanja>")) {
			mapa = pridruzi(mapa);
		} else za(mapa);
		return mapa;
	}
	public HashMap<String, String> pridruzi (HashMap<String, String> mapa) {
		String rezerva1 = new String();
		String rezerva2 = new String();
		++i;
		red = ulaz[i];
		odvojeno = red.split(" ");
		odvojeno = uredi(odvojeno);
		//if (!mapa.containsKey(odvojeno[2])) mapa.put(odvojeno[2], odvojeno[1]);
		rezerva1 = odvojeno[2];
		rezerva2 = odvojeno[1];
		i = i + 2;
		red = ulaz[i];
		odvojeno = red.split(" ");
		odvojeno = uredi(odvojeno);
		while (!(odvojeno[0].equals("<lista_naredbi>") || odvojeno[0].equals("<naredba>"))) {
			if (odvojeno[0].equals("IDN")) {
				if (mapa.containsKey(odvojeno[2])) {
					ispis += odvojeno[1] + " " + mapa.get(odvojeno[2]) + " " + odvojeno[2] + "\n";
				} else {
					System.out.print(ispis);
					System.out.print("err " + odvojeno[1] + " " + odvojeno[2] + "\n");
					System.exit(0);
				}
			}
			++i;
			red = ulaz[i];
			odvojeno = red.split(" ");
			odvojeno = uredi(odvojeno);
		}
		if (!mapa.containsKey(rezerva1)) mapa.put(rezerva1, rezerva2);
		--i;
		return mapa;
	}
	public HashMap<String, String> za(HashMap<String, String> mapa) {
		HashMap<String, String> mapa2 = new HashMap<String, String> ();
		String rezerva1 = new String();
		String rezerva2 = new String();
		mapa2.putAll(mapa);
		i = i + 2;
		red = ulaz[i];
		odvojeno = red.split(" ");	
		odvojeno = uredi(odvojeno);
		//mapa2.put(odvojeno[2], odvojeno[1]);
		rezerva1 = odvojeno[2];
		rezerva2 = odvojeno[1];
		++i;
		red = ulaz[i];
		odvojeno = red.split(" ");	
		odvojeno = uredi(odvojeno);
		while (!odvojeno[0].equals("KR_DO")) {
			if (odvojeno[0].equals("IDN")) {
				if (mapa2.containsKey(odvojeno[2]) && !odvojeno[2].equals(rezerva1)) {
					ispis += odvojeno[1] + " " + mapa2.get(odvojeno[2]) + " " + odvojeno[2] + "\n";
				} else {
					System.out.print(ispis);
					System.out.print("err " + odvojeno[1] + " " + odvojeno[2] + "\n");
					System.exit(0);
				}
			}
			++i;
			red = ulaz[i];
			odvojeno = red.split(" ");
			odvojeno = uredi(odvojeno);
		}
		while (!(odvojeno[0].equals("<naredba>") || odvojeno[0].equals("KR_AZ"))) {
			if (odvojeno[0].equals("IDN")) {
				if (mapa.containsKey(odvojeno[2]) && !odvojeno[2].equals(rezerva1)) {
					ispis += odvojeno[1] + " " + mapa2.get(odvojeno[2]) + " " + odvojeno[2] + "\n";
				} else {
					System.out.print(ispis);
					System.out.print("err " + odvojeno[1] + " " + odvojeno[2] + "\n");
					System.exit(0);
				}
			}
			++i;
			red = ulaz[i];
			odvojeno = red.split(" ");
			odvojeno = uredi(odvojeno);
		}
		mapa2.put(rezerva1, rezerva2);
		while (!odvojeno[0].equals("KR_AZ")) {
			if (odvojeno[0].equals("<naredba>")) {
				mapa2 = obradi(mapa2);
			}
			++i;
			red = ulaz[i];
			odvojeno = red.split(" ");
			odvojeno = uredi(odvojeno);
		}
		return mapa;
	}
	public String [] uredi (String [] odvojeno) {
		String novo [] = new String [3];
		int k, l;
		int z = 0;
		k = odvojeno.length;
		for (l = 0; l<k; ++l) {
			if (!odvojeno[l].equals("")) novo[z++] = odvojeno[l];
		}
		return novo;
	}
}
