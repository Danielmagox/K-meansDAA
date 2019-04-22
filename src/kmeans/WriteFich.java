package kmeans;
import java.io.*;
import java.util.List;

/**
Clase que sirve para escribir sobre el fichero todas los escenarios que se le proporciones por consola.
 */
public class WriteFich {
	
	List<Cluster> lista;
	String fich;
	Integer ejecucion;
	float SSE;
	long tiempo;

	public WriteFich(List<Cluster> list,float SSE,long tiempo,String fich,Integer ejecucion) {
		lista = list;
		this.fich = fich;
		this.ejecucion = ejecucion;
		this.SSE = SSE;
		this.tiempo = tiempo;
	}
	
	public void Write() {
		FileWriter fichero = null;
        PrintWriter pw = null;
        try {
        	fichero = new FileWriter(fich,true);
            pw = new PrintWriter(fichero);
                pw.println(lista);
                pw.println(" -------------------- SSE ---------------------");
                pw.println(SSE);
                pw.println("--------------------- TIEMPO EN MICROSEGUNDOS 10^-6 ------------------ ");
                pw.println(tiempo);
                pw.println("------------------------------------------------ EJECUCION Nº  " + ejecucion + " --------");
        }catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	try {
                if (null != fichero)
                   fichero.close();
                } catch (Exception e2) {
                   e2.printStackTrace();
             }
        }
	}
}
