package solucoes;

import java.util.ArrayList;
import pmediana.PMediana;
import pmediana.Vertice;

public interface Solucao {
	public abstract ArrayList<PMediana> gerarSolucao(ArrayList<Vertice> conjuntoVertices, int[] medianas);
	public abstract void distribuirMedianas(PMediana PM);
	public abstract void setCritDesempate(String variacao);
}
