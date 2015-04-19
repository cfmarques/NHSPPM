package app;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import pmediana.Vertice;
import solucao.Solucao;
import distribuicaoMedianasIniciais.DistribuidorDeMedianasIniciais;

public class Problema {
	private int numVertices;
	private Solucao solucaoAplicada;
	private DistribuidorDeMedianasIniciais distribuidorDeMedianasIniciais;
	private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
	private ArrayList<Resultado> resultados = new ArrayList<Resultado>();
	private int[] medianasDesejadas;
	
	public Problema(ArrayList<Vertice> vertices, int[] medianasDesejadas){
		Arrays.sort(medianasDesejadas);

		this.numVertices = vertices.size();
		this.vertices = vertices;
		this.medianasDesejadas = medianasDesejadas;
	}
	
	public ArrayList<Resultado> getResultados() {
		return resultados;
	}
	
	public Solucao getSolucaoAplicada() {
		return solucaoAplicada;
	}
	
	public DistribuidorDeMedianasIniciais getDistribuidorDeMedianasIniciais() {
		return distribuidorDeMedianasIniciais;
	}
	
	public Resultado[] solucionarProblema(Solucao solucao, DistribuidorDeMedianasIniciais distribuidorDeMedianasIniciais){		
		this.solucaoAplicada = solucao;
		this.distribuidorDeMedianasIniciais = distribuidorDeMedianasIniciais;
		
		System.out.println("Solucionando o problema... Por favor, aguarde!\n\n");
	
		Resultado[] resultados = solucao.gerarSolucao(getVertices(), medianasDesejadas, distribuidorDeMedianasIniciais);

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
							"Nome da Solução aplicada: " + this.solucaoAplicada.getNome() + ")";
		
		System.out.println(cabecalho);
		
		for(Resultado resultado : this.resultados){
			System.out.println(resultado);
		}
	}
	
	public void exibirResultados(PrintWriter saida){
		String cabecalho =	"Resultado para conjunto de vertices de " + this.numVertices + " vertices" + "\n" +
							"------------------------------------------------------------" + "\n" +
							"Nome da Solução aplicada: " + this.solucaoAplicada.getNome() + ")";
		
		saida.println(cabecalho);
		
		for(Resultado resultado : this.resultados){
			saida.println(resultado);
			/*saida.println(resultado.exibirMedianas());*/
		}
		
		saida.print("\n");
	}
}
