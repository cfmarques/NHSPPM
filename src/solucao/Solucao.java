package solucao;

import pmediana.PMediana;
import pmediana.Vertice;
import app.Resultado;

public interface Solucao {
	public abstract Resultado[] gerarSolucao(Vertice[] conjuntoVertices, int[] medianasDesejadas);
	public abstract void distribuirMedianas(PMediana PM);
	public abstract void setVariacao(String variacao);
	public abstract String getVariacao();
}
