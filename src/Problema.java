import java.util.ArrayList;

import pmediana.Vertice;
import solucoes.Solucao;


public class Problema {
	private int numVertices;
	private String solucaoAplicada;
	private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
	private ArrayList<Resultado> resultados = new ArrayList<Resultado>();
	private int[] medianasDesejadas;
	
	public Problema(ArrayList<Vertice> vertices, int[] medianasDesejadas){
		this.vertices = vertices;
		this.medianasDesejadas = medianasDesejadas;
	}
	
	public ArrayList<Resultado> solucionarProblema(Solucao solucao){
		solucao.gerarSolucao(this.vertices, medianasDesejadas);
		
		return this.resultados;
	}

}
