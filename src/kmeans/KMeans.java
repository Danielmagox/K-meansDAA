package kmeans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KMeans {

	private List<Point> points= new ArrayList<Point>();
	private List<Cluster> newClusters = new ArrayList<Cluster>();
	private List<Cluster> oldClusters = new ArrayList<Cluster>();
	private int nCluster;
	private int nDimensions;
	
	public KMeans(String file, int nCluster) {
		this.nCluster = nCluster;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			int nPoints = Integer.parseInt(reader.readLine());
			nDimensions = Integer.parseInt(reader.readLine());
			for(int i = 0; i < nPoints ; i++) {
				points.add(new Point(reader.readLine(),nDimensions));
			}
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void selecInitialCluster() {	//Seleccion del primer cluster
		
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		List<Integer> dummy = new ArrayList<Integer>();
		for(int i = 0; i < nCluster ; i++) {//El numero de clusters
			int n = Math.abs(random.nextInt()) % points.size();
			if(!dummy.contains(n)) {	//Si ese punto ya fue escogido que escoja otro
				dummy.add(n);
				newClusters.add(new Cluster(new Point(points.get(n))));
				oldClusters.add(new Cluster(new Point(points.get(n))));
			}else
				i--;	//Le resto uno para que vuelva a repetir, seria mejor con while but i dont care
		}
	}
	
	public void updateCluster() {	//Actualizo los cluster pasando los nuevos a viejos y calculando los nuevos
		oldClusters.clear();
		for(int i = 0; i < nCluster ; i++) {
			oldClusters.add(new Cluster(newClusters.get(i)));	//De los nuevos a los viejos
		}
		newClusters.clear();
		calculateNewCentroides();	//Calculo los centroides de los nuevos
	}
	
	public void calculateNewCentroides() {
		for(int i = 0; i < nCluster ; i++) {
			List<Float> dCentroide = new ArrayList<Float>(); //DummyCentroide
			for(int j = 0; j < nDimensions ; j++) {
				dCentroide.add(0F);	//Inicializo el punto
			}
			
			for(int j = 0 ; j < oldClusters.get(i).getPuntos().size(); j++) {	//Recorro todos los puntos en el cluster viejo
				for(int k = 0 ; k < nDimensions ; k++) {	//Recorro todas las dimensiones
					dCentroide.set(k,oldClusters.get(i).getPuntos().get(j).getPoints().get(k) + dCentroide.get(k));	//Voy sumando dimension a dimension
				}
			}
			
			for(int j = 0; j < nDimensions ; j++) {	//Hago la media
				dCentroide.set(j, dCentroide.get(j)/oldClusters.get(i).getPuntos().size());
			}
			
			newClusters.add(new Cluster(new Point(dCentroide,nDimensions)));	//Creo el nuevo cluster con su nuevo centroide
		}
	}
	
	public void fillClusters() {	//Lleno elñ cluster
		for(int i = 0; i < points.size() ; i++) { //Recorro todos los puntos
			int clusterIndex = 0;
			double minDistance = points.get(i).distance(newClusters.get(0).getCentroide());	//Calculo el primero para que sea mi minimo y no tener que declarar un mas infinito
			for(int j = 1 ; j < nCluster ; j++) {
				double distance = points.get(i).distance(newClusters.get(j).getCentroide());	//Recorro el resto de puntos, si es menor que la distancia minima guardo su indice y su distancia
				if(minDistance > distance) {
					minDistance = distance;
					clusterIndex = j;
				}
			}
			newClusters.get(clusterIndex).addPoint(points.get(i));	//El mas pequeño lo añado al cluster correspondiente
		}
	}
	
	public Boolean haveClustersChange() {	//Compruebo que no haya cambiado
		Boolean change = false;
		for(int i = 0; i < nCluster ; i++) {
			if(!newClusters.get(i).equals(oldClusters.get(i))) {
				change = true;
			}
		}
		return change;
		
	}
	
	public void calculateClusters() {
		selecInitialCluster();	//Selecciono los iniciales
		Boolean change = true;
		while(change) {
			
			fillClusters();	//relleno
			change = haveClustersChange();	//veo si ha cambiado
			updateCluster();	//Actualizo

			
		}
	}
	
	public float calculateSSE() {
		float suma = 0;
		float resultado = 0;
		for(Cluster clust : oldClusters) {
//			//System.out.println(clust);
//			System.out.println("\\\\\\\\\\\\\\\\\\");
//			System.out.println(clust.getCentroide());
//			System.out.println("*********");
//			System.out.println(clust.getPuntos());
//			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//			System.out.println(clust.getCentroide().getN(0)); //pillar centroide
//			System.out.println("ÇÇÇÇÇÇÇÇÇÇÇÇÇÇÇÇÇÇÇÇÇÇÇÇÇ");
//			System.out.println(clust.getPuntos().get(0).getN(0)); //pillar numero del punto
			
			suma += DistEuclidiana(clust,clust.getPuntos());
			
		}
		//return resultado = suma/points.size();
		return resultado = suma;
	}
		
	private float DistEuclidiana(Cluster cent, List<Point> puntos) {
	float distancia = 0;
		for ( int i = 0; i < puntos.size(); i++) {		
				distancia += Math.pow(puntos.get(i).distance(cent.getCentroide()), 2);			
				//distancia += puntos.get(i).distance(cent.getCentroide()); //El centroide se cambia con el bucle del otro método
				//distancia += Math.sqrt(Math.pow(puntos.get(i).substract(cent.getCentroide()), 2));
				//System.out.println(puntos.get(i));
				//System.out.println(cent.getCentroide());				
		}			
	return distancia;
	}

		
	
	public List<Cluster> getOldClusters() {
		return oldClusters;
	}

	public void setOldClusters(List<Cluster> oldClusters) {
		this.oldClusters = oldClusters;
	}

	public List<Point> getPoints() {
		return points;
	}


	public void setPoints(List<Point> points) {
		this.points = points;
	}


	public List<Cluster> getNewClusters() {
		return newClusters;
	}


	public void setNewClusters(List<Cluster> newClusters) {
		this.newClusters = newClusters;
	}
		//ARGS
		//Fichero del problema, nº clusteres, archvo solucion , bucles (escenarios aleatorios)
	public static void main(String[] args) {
		long TInicio,TFin,tiempo;
		File fichero = new File(args[2]);
		fichero.delete();
		int escenarios = Integer.parseInt(args[3]);
		
		for(int i=0; i< escenarios ; i++) {
			TInicio = System.nanoTime();
		KMeans k = new KMeans(args[0], Integer.parseInt(args[1]));
		k.calculateClusters();
		System.out.println("--------------------------------------");
		System.out.println(k.getOldClusters());
		System.out.println(k.calculateSSE());
		TFin = System.nanoTime();
		tiempo = TFin - TInicio;
		System.out.println("Tiempo de ejecución en milisegundos " + tiempo/1000);
		WriteFich a = new WriteFich(k.getOldClusters(),k.calculateSSE(),tiempo/1000,args[2],i);
		a.Write();
		
		}
	}


}
