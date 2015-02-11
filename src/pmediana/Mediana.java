package pmediana;

import java.util.ArrayList;

public class Mediana {
	/** Declaração de variáveis
	 *  @vertice - Variável Vertice
	 *  @verticess - ArrayList de Vertices
	 *  @fitness - Variável double que representa a potuação da mediana
	 *  @numConectados - Variável int que representa o número de conectados na mediana
	 */
	private PMediana PMedianaPertencente;
	private Vertice vertice;
	private ArrayList<Vertice> vertices;
	private int numConectados;
	private double fitness;
	
	public Mediana (Vertice vertice){
		this.fitness = 0;
		this.numConectados = 0;
		
		this.vertice = vertice;
		this.vertice.setMed(true);
		this.vertice.setMelhorRef(this);
		this.vertice.setDistancia(0);
		
		this.vertices = new ArrayList<Vertice>();
		this.addVertice(vertice);
	}
	
	public Vertice getVertice(){
		return this.vertice;
	}
	
	/*public void setVertice(Vertice vertice){
		this.vertice = vertice;		
	}*/
	
	public Vertice[] getVertices(){
		Vertice[] vertices = new Vertice[this.vertices.size()]; 
		return this.vertices.toArray(vertices);
	}
	
	/*public void setVertices(ArrayList<Vertice> vertices){
		this.vertices = vertices;
	}*/
	
	public double getFitness(){
		/*calcularFitness();*/
		return this.fitness;		
	}
	
	public double getNumConectados(){
		return this.numConectados;		
	}
	
	public void addVertice(Vertice vertice){		
		if(!vertices.contains(vertice)){
			vertices.add(vertice);
			this.numConectados++;
			
			double antigoFitness = this.fitness;
			double novoFitness = this.fitness + vertice.getDist();
			
			/*Gambiarra para atualizar o fitness*/
			if(this.PMedianaPertencente != null){
				this.PMedianaPertencente.atualizarFitness(antigoFitness, novoFitness);
			}
			
			this.fitness = novoFitness;
			
			vertice.setMelhorRef(this);
		}
	}
	
	public void removerVertice(Vertice vertice){
		if(vertices.contains(vertice)){
			vertices.remove(vertice);
			this.numConectados--;

			double antigoFitness = this.fitness;
			double novoFitness = this.fitness - vertice.getDist();
			
			/*Gambiarra para atualizar o fitness*/
			if(this.PMedianaPertencente != null){
				this.PMedianaPertencente.atualizarFitness(antigoFitness, novoFitness);
			}
			
			this.fitness = novoFitness;
			
			vertice.setMelhorRef(null);
			
			if(vertice.getId() == this.vertice.getId()){
				this.vertice = null;
			}
		}
	}
	
	public double calcularFitness(){
		double fitness = 0;
		
		for(Vertice vertice : this.vertices){
			fitness += vertice.getDist();
		}
		
		return fitness;
	}
	
	public void setPMedianaPertencente(PMediana pmediana){
		this.PMedianaPertencente = pmediana;
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
}
