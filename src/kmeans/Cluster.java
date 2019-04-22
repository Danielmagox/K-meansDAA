package kmeans;

import java.util.*;

public class Cluster {
	private List<Point> puntos = new ArrayList<Point>();
	private Point centroide;
	
	public Cluster(Point puntoCluster) {
		centroide = new Point(puntoCluster);
	}
	
	public Cluster(Cluster cluster) {
		centroide = cluster.getCentroide();
		puntos = new ArrayList<Point>(cluster.getPuntos());
	}
	
	public void addPoint(Point b) {
		puntos.add(b);
	}

	public List<Point> getPuntos() {
		return puntos;
	}

	public void setPuntos(List<Point> puntos) {
		this.puntos = puntos;
	}

	public Point getCentroide() {
		return centroide;
	}

	public void setCentroide(Point centroide) {
		this.centroide = centroide;
	}
	
	public Boolean equals(Cluster b) {
		if(!centroide.equals(b.getCentroide()))	//Mismo punto de centroide
			return false;
		if(puntos.size() != b.getPuntos().size())	//Mismo numero de puntos asociados
			return false;
		for(int i = 0 ; i < puntos.size() ; i++) {	//Mismos puntos
			Boolean encontrado = false;
			for(int j = 0; j < puntos.size(); j++) {
				if(puntos.get(i).equals(b.getPuntos().get(j)))
					encontrado = true;
			}
			if(!encontrado)
				return false;
		}
		
		return true;
		
	}
	public String toString() {
		String str = "";
		
		str += "Centroide = " + centroide + "\n";
		str += "Puntos => \n";
		for(int i = 0; i < puntos.size(); i++) {
			str += puntos.get(i) + "\n";
		}
		
		return str;
	}
}
