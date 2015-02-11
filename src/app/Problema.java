package app;
import java.util.ArrayList;
import java.util.Arrays;

import pmediana.Vertice;
import solucao.Solucao;


public class Problema {
	private int numVertices;
	private Solucao solucaoAplicada;
	private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
	private ArrayList<Resultado> resultados = new ArrayList<Resultado>();
	private int[] medianasDesejadas;
	
	public Problema(ArrayList<Vertice> vertices, int[] medianasDesejadas){
		Arrays.sort(medianasDesejadas);

		this.numVertices = vertices.size();
		this.vertices = vertices;
		this.medianasDesejadas = medianasDesejadas;
	}
	
	public Resultado[] solucionarProblema(Solucao solucao){		
		this.solucaoAplicada = solucao;
		
		System.out.println("Solucionando o problema... Por favor, aguarde!\n\n");
	
		Resultado[] resultados = solucao.gerarSolucao(getVertices(), medianasDesejadas);

		this.resultados.clear();
		this.resultados.addAll(Arrays.asList(resultados));

		return resultados;
	}

	public Vertice[] getVertices(){
		Vertice vertices[] = new Vertice[this.vertices.size()];
		return this.vertices.toArray(vertices);
	}
	
	public void exibirResultados(){
		String cabecalho =	"Resultado para conjunto de vertices de " + this.numVertices + " vertices" + "\n" +
							"------------------------------------------------------------" + "\n" +
							"Nome da Solução aplicada: " + this.solucaoAplicada.getClass().getSimpleName() + " (" + this.solucaoAplicada.getCritDesempate() + ")";
		
		System.out.println(cabecalho);
		
		for(Resultado resultado : this.resultados){
			System.out.println(resultado);
		}
	}
}
