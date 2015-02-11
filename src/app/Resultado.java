package app;

import solucao.Solucao;


public class Resultado {
	private int numMedianas, numVertices;
	private double fitness, tempoExecucao;
	private Solucao solucaoAplicada;

	public int getNumMedianas() {
		return numMedianas;
	}
	
	public void setNumMedianas(int numMedianas) {
		this.numMedianas = numMedianas;
	}
	
	public int getNumVertices() {
		return numVertices;
	}
	
	public void setNumVertices(int numVertices) {
		this.numVertices = numVertices;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public double getTempoExecucao() {
		return tempoExecucao;
	}
	
	public void setTempoExecucao(double tempoExecucao) {
		this.tempoExecucao = tempoExecucao;
	}

	public void setSolucaoAplicada(Solucao SolucaoAplicada) {
		this.solucaoAplicada = SolucaoAplicada;
	}
	
	public String toString(){
		String texto = "";
		
		texto = this.numMedianas + "\t" + this.fitness + "\t" + this.tempoExecucao; 
		
		/*texto = "Resultado para conjunto de vertices de " + this.numVertices + " vertices" + "\n" +
				"------------------------------------------------------------" + "\n" +
				"Nome da Solução aplicada: " + this.SolucaoAplicada.getClass().getSimpleName() + " (" + this.SolucaoAplicada.getVariacao() + ") \n" +
				"Nº Medianas: " + this.numMedianas + "\n" +
				"Fitness: " + this.fitness + "\n" +
				"Tempo de execução: " + this.tempoExecucao;*/ 
		
		return texto;
	}
}
