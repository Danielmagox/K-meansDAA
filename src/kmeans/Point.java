package kmeans;

import java.util.*;

public class Point {
	private List<Float> points = new ArrayList<Float>();
	private int nDimensions;
	
	public Point(Point b) {
		points = new ArrayList<Float>(b.getPoints());
		nDimensions = b.getnDimensions();
	}
	
	public Point(String sDimensions, int np) {
		String[] puntos = sDimensions.replaceAll("\\s+", " ").split(" ");
		if(puntos.length != np) {
			System.out.println("Fichero mal redactado        " + sDimensions);
		}
		for(String p : puntos) {
			points.add(Float.parseFloat(p.replace(",", ".")));
		}
		nDimensions = np;
	}
	public Point(List<Float> points, int nDimensions) {
		this.points = new ArrayList<Float>(points);
		this.nDimensions = nDimensions;
	}
	

	public List<Float> getPoints() {
		return points;
	}

	public void setPoints(List<Float> points) {
		this.points = points;
	}

	public int getnDimensions() {
		return nDimensions;
	}

	public void setnDimensions(int nDimensions) {
		this.nDimensions = nDimensions;
	}
	
	public Float getN(int n) {
		return points.get(n);
	}
	
	public double distance(Point b) {
		double dummy = 0.0;
		for(int i = 0; i < nDimensions ; i++) {
			dummy += Math.pow(this.getN(i)-b.getN(i), 2);
		}
		
		return Math.sqrt(dummy);
	}
	
	public Boolean equals(Point b) {
		if(nDimensions != b.nDimensions)
			return false;
		for(int i = 0; i < nDimensions ; i++) {
			if(!points.get(i).equals(b.getPoints().get(i)))
				return false;
		}
		
		return true;
	}
	
	public float substract(Point b) {
		float resultado = 0;
		if(nDimensions != b.nDimensions) {
			System.out.println("Distintas dimensiones");
		return -1;
		}else {
			for(int i =0 ; i < nDimensions; i++) {
				resultado += points.get(i) - b.getPoints().get(i);
			}
		}
		return resultado;
	
	}
	public String toString() {
		String str = " ";
		str += "(";
		for(int i = 0; i < nDimensions; i++) {
			str += " "+ points.get(i);
		}
		str += ")";
		return str;
	}
}
