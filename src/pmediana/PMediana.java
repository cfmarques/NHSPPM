package pmediana;

import java.util.ArrayList;

public class PMediana implements Cloneable{
	/** Declaração de variáveis
	 *  @fitness - Variável double que representa a potuação da PMediana
	 *  @numMed - Variável int que representa a quantidade de medianas da PMediana
	 *  @medDesejado - Variável int que representa a quantidade de medianas desejadas na PMediana
	 *  @vetMed - ArrayList Mediana que representa todas as medianas daquela PMediana
	 */
	private double fitness;
	private int numMedianas;
	private ArrayList<Vertice> vertices;	
	private ArrayList<Mediana> medianas;
	
	public PMediana(ArrayList<Vertice> vertices){
		this.vertices = vertices;
		this.medianas = new ArrayList<Mediana>();
		this.numMedianas = 0;
		this.fitness = 0;
	}
	
	/*public void setVertices(ArrayList<Vertice> vertices){
		this.vertices = vertices;
	}*/
	
	public ArrayList<Vertice> getVertices(){
		return this.vertices;
	}
	
	/*public void setMedianas(ArrayList<Mediana> mediana){
		this.medianas = mediana;
	}*/
	
	public void addMediana(Mediana mediana){
		if(!medianas.contains(mediana)){
			medianas.add(mediana);
			this.numMedianas++;
			this.fitness += mediana.getFitness();
		}
	}
	
	public void excluiMediana(Mediana mediana){
		if(medianas.contains(mediana)){
			medianas.remove(mediana);
			this.numMedianas--;
			this.fitness -= mediana.getFitness();
		}
	}
	
	public ArrayList<Mediana> getMedianas(){
		return this.medianas;
	}
	
	public void setNumMed(int numMedianas){
		this.numMedianas = numMedianas;
	}
	
	public int getNumMed(){
		return this.numMedianas;
	}
	
	public double getFitness(){
		/*calcularFitness();*/
		return this.fitness;
	}
	
	/*private void calcularFitness(){
		this.fitness = 0;
		for(Mediana mediana : medianas){
			this.fitness += mediana.getFitness();
		}
	}*/
	
	//Função que exibi as medianas e os vertices conectados a mediana, a exibição dos vertices conectados a mediana se da pelo metodo toString na classe Mediana
	public void exibirMedianas(){
		System.out.print("Quantidade de medianas: " +medianas.size() + "\n");
		for(Mediana mediana : medianas){
			System.out.print(mediana);
		}
	}
	
	/*public Object clone(){
	    try{
	        return super.clone();  
	    }catch(Exception e){
	    	return null;
	    }
	}*/
	
	/*public PMediana copiar(){
		PMediana pmediana = (PMediana) this.clone();
				
		pmediana.setMedianas((ArrayList<Mediana>) this.getMedianas().clone());
		for(Mediana mediana : pmediana.getMedianas()){
			mediana.setVertices((ArrayList<Vertice>) mediana.getVertices().clone());
		}
		
		pmediana.setVertices((ArrayList<Vertice>) this.getVertices().clone());
		
		return pmediana;
	}*/
	
	public Mediana excluirVerticeDaMedianaConectada(Vertice vertice){
		for(Mediana mediana : this.medianas){
			if(mediana.getVertice().getId() == vertice.getId()){
				System.out.println("Você não pode excluir uma vertice mediana");
				
			}else if(mediana.getVertices().contains(vertice)){
				System.out.println("Vertice " + vertice.getId() + " excluida da mediana " + mediana.getVertice().getId());
				mediana.excluiVertice(vertice);
				return mediana;
			}
		}
		return null;
	}
	
	public Mediana excluirVerticeDaMedianaConectada(Vertice vertice, Mediana mediana){
		System.out.println("Vertice " + vertice.getId() + " excluida da mediana " + mediana.getVertice().getId());
		mediana.excluiVertice(vertice);
		return mediana;
	}
	
}
