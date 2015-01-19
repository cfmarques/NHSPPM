package pmediana;

import java.util.ArrayList;

public class PMediana implements Cloneable{
	/** Declara��o de vari�veis
	 *  @fitness - Vari�vel double que representa a potua��o da PMediana
	 *  @numMed - Vari�vel int que representa a quantidade de medianas da PMediana
	 *  @medDesejado - Vari�vel int que representa a quantidade de medianas desejadas na PMediana
	 *  @vetMed - ArrayList Mediana que representa todas as medianas daquela PMediana
	 */
	private double fitness;
	private int numMed;
	private ArrayList<Vertice> vertices = new ArrayList<Vertice>();	
	private ArrayList<Mediana> medianas = new ArrayList<Mediana>();
	
	public PMediana(){
		this.vertices.clear();
		this.medianas.clear();
		this.numMed = 0;
		this.fitness = 0;
	}
	
	public void setVertices(ArrayList<Vertice> vertices){
		this.vertices = vertices;
	}
	
	public ArrayList<Vertice> getVertices(){
		return this.vertices;
	}
	
	public void setMedianas(ArrayList<Mediana> mediana){
		this.medianas = mediana;
	}
	
	public ArrayList<Mediana> getMedianas(){
		return this.medianas;
	}
	
	public void setNumMed(int numMed){
		this.numMed = numMed;
	}
	
	public int getNumMed(){
		return this.numMed;
	}
	
	public double getFitness(){
		calcularFitness();
		return this.fitness;
	}
	
	private void calcularFitness(){
		this.fitness = 0;
		for(Mediana mediana : medianas){
			this.fitness += mediana.getFitness();
		}
	}
	
	//Fun��o que exibi as medianas e os vertices conectados a mediana, a exibi��o dos vertices conectados a mediana se da pelo metodo toString na classe Mediana
	public void exibirMedianas(){
		System.out.print("Quantidade de medianas: " +medianas.size() + "\n");
		for(Mediana mediana : medianas){
			System.out.print(mediana);
		}
	}
	
	public Object clone(){
	    try{
	        return super.clone();  
	    }catch(Exception e){ 
	        return null; 
	    }
	}
	
	public PMediana copiar(){
		PMediana pmediana = (PMediana) this.clone();
				
		pmediana.setMedianas((ArrayList<Mediana>) this.getMedianas().clone());
		pmediana.setVertices((ArrayList<Vertice>) this.getVertices().clone());
		
		return pmediana;
	}
	
}
