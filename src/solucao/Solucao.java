package solucao;

import distribuicaoMedianasIniciais.DistribuidorDeMedianasIniciais;
import pmediana.Vertice;
import app.Resultado;

public interface Solucao {
	public abstract Resultado[] gerarSolucao(Vertice[] conjuntoVertices, int[] medianasDesejadas, DistribuidorDeMedianasIniciais distribuidorMedianasIniciais);
	public abstract String getDescricao();
	public abstract String getNome();
	public abstract String getAbreviacao();	
}
