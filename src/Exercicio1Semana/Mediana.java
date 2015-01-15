package Exercicio1Semana;

import java.util.ArrayList;

public class Mediana {
	/** Declaração de variáveis
	 *  @vertice - Variável Vertice
	 *  @vetVertices - ArrayList de Vertices
	 *  @fitnessLocal - Variável double que representa a potuação da mediana
	 *  @numConectados - Variável int que representa o número de conectados na mediana
	 */
	private Vertice vertice = new Vertice();
	private ArrayList<Vertice> vetVertice = new ArrayList<Vertice>();
	private int numConectados;
	private double fitnessLocal, fitnessLocalMedio;
	
	public Mediana (Vertice vertice){
		this.fitnessLocal = 0;
		this.numConectados = 0;
		this.vertice = vertice;
		this.vetVertice.clear();
	}
	
	public String toString(){
		String retorno;
		retorno = "Vertice: " + this.vertice.getId() + "\tFitness Local: " + this.fitnessLocal + "\tFitness Local Médio: " + this.fitnessLocalMedio + "\tmedX: " + this.vertice.getX() + "\tmedY: " + this.vertice.getY() + "\n"
				+ "\n"
				+ "Vertices conectadas (" + this.numConectados + "): \n";
		
		for(Vertice vertice : vetVertice){
			retorno += "Vertice " + vertice.getId() + "\tvetX: " + vertice.getX() + "\tvetY: " +vertice.getY() + "\tdistância: " + vertice.getDist() + "\n";
		}
		
		retorno += "\n\n\n";
		
		return retorno;
	}
	
	public Vertice getVertice(){
		return this.vertice;		
	}
	public double getFitnessLocal(){
		return this.fitnessLocal;		
	}
	public double getFitnessLocalMedio(){
		return this.fitnessLocalMedio;		
	}
	public double getNumConectados(){
		return this.numConectados;		
	}
	
	public void addVertice(Vertice vertice){
		vetVertice.add(vertice);
		this.numConectados++;
		calcularFitnessLocal();
		this.fitnessLocalMedio = this.fitnessLocal / this.numConectados;
	}
	
	public void excluiVertice(Vertice vertice){
		vetVertice.remove(vertice);
		this.numConectados--;
		calcularFitnessLocal();
		this.fitnessLocalMedio = this.fitnessLocal / this.numConectados;
	}
	
	public void calcularFitnessLocal(){
		this.fitnessLocal = 0;
		for(Vertice vertice : this.vetVertice){
			this.fitnessLocal += vertice.getDist();
		}
	}
	
	public ArrayList<Vertice> getVertices(){
		return vetVertice;
	}
}
