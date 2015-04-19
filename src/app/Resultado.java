package app;

import java.util.ArrayList;

import pmediana.Mediana;
import solucao.Solucao;
import distribuicaoMedianasIniciais.DistribuidorDeMedianasIniciais;


public class Resultado {
	private int numMedianas, numVertices;
	private double fitness, tempoExecucao;
	private Solucao solucaoAplicada;
	private DistribuidorDeMedianasIniciais distribuidorDeMedianasIniciaisUtilizado;
	private ArrayList<Mediana> medianas = new ArrayList<Mediana>();

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
		String fitness_string = "" + this.fitness;
		String tempoExecucao_string = "" + this.tempoExecucao;
		
		fitness_string = fitness_string.replace(".", ",");
		tempoExecucao_string = tempoExecucao_string.replace(".", ",");
		
		texto = /*this.numMedianas + "\t" + */ fitness_string + "\t" + tempoExecucao_string; 
				
		/*texto = "Resultado para conjunto de vertices de " + this.numVertices + " vertices" + "\n" +
				"------------------------------------------------------------" + "\n" +
				"Nome da Solução aplicada: " + this.SolucaoAplicada.getClass().getSimpleName() + " (" + this.SolucaoAplicada.getVariacao() + ") \n" +
				"Nº Medianas: " + this.numMedianas + "\n" +
				"Fitness: " + this.fitness + "\n" +
				"Tempo de execução: " + this.tempoExecucao;*/ 
		
		return texto;
	}
	
	public String exibirMedianas(){
		String texto = "";

		int i = 0;
		
		for(Mediana mediana : medianas){
			i++;
			texto += mediana.getVertice().getId();
			
			if(i < medianas.size()){
				texto += ", ";
			}
		}
		
		return texto;
	}

	public void setMedianas(ArrayList<Mediana> medianas) {
		this.medianas = medianas;
	}
}
