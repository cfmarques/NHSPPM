package pmediana;

import java.util.ArrayList;

public class Mediana {
	/** Declara��o de vari�veis
	 *  @vertice - Vari�vel Vertice
	 *  @verticess - ArrayList de Vertices
	 *  @fitness - Vari�vel double que representa a potua��o da mediana
	 *  @numConectados - Vari�vel int que representa o n�mero de conectados na mediana
	 */
	private Vertice vertice = new Vertice();
	private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
	private int numConectados;
	private double fitness;
	
	public Mediana (Vertice vertice){
		this.fitness = 0;
		this.numConectados = 0;
		this.vertice = vertice;
		this.vertices.clear();
	}
	
	public String toString(){
		String retorno;
		retorno = "Vertice: " + this.vertice.getId() + "\tFitness Local: " + this.fitness + "\tFitness Local Médio: " + (this.fitness / this.numConectados) + "\tmedX: " + this.vertice.getX() + "\tmedY: " + this.vertice.getY() + "\n"
				+ "\n"
				+ "Vertices conectadas (" + this.numConectados + "): \n";
		
		for(Vertice vertice : vertices){
			retorno += "Vertice " + vertice.getId() + "\tvetX: " + vertice.getX() + "\tvetY: " +vertice.getY() + "\tdist�ncia: " + vertice.getDist() + "\n";
		}
		
		retorno += "\n\n\n";
		
		return retorno;
	}
	
	public Vertice getVertice(){
		return this.vertice;		
	}
	
	public ArrayList<Vertice> getVertices(){
		return vertices;
	}
	
	public double getFitness(){
		calcularFitness();
		return this.fitness;		
	}
	
	public double getNumConectados(){
		return this.numConectados;		
	}
	
	public void addVertice(Vertice vertice){
		vertices.add(vertice);
		this.numConectados++;
		calcularFitness();
	}
	
	public void excluiVertice(Vertice vertice){
		vertices.remove(vertice);
		this.numConectados--;
		calcularFitness();
	}
	
	private void calcularFitness(){
		this.fitness = 0;
		
		for(Vertice vertice : this.vertices){
			this.fitness += vertice.getDist();
		}
	}
}
