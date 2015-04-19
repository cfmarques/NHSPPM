package solucao;

import java.util.ArrayList;
import java.util.Arrays;

import pmediana.Mediana;
import pmediana.PMediana;
import pmediana.Vertice;
import app.Resultado;
import distribuicaoMedianasIniciais.DistribuidorDeMedianasIniciais;

public class SolucaoMenorFitnessMedio implements Solucao {
	private String nome = "Explosão por menor fitness médio";
	private String abreviacao = "E<FM";
	private String descricao;

	@Override
	public String getDescricao() {
		return this.descricao;
	}

	@Override
	public String getNome() {
		return this.nome;
	}
	
	@Override
	public String getAbreviacao() {
		return this.abreviacao;
	}

	@Override
	public Resultado[] gerarSolucao(Vertice[] conjuntoVertices, int[] medianasDesejadas, DistribuidorDeMedianasIniciais distribuidorDeMedianasIniciais) {
		long tempoInicial = System.currentTimeMillis();
		double tempoExecucao;

		ArrayList<Resultado> arrayListResultados = new ArrayList<Resultado>();

		PMediana PM = new PMediana(conjuntoVertices);
		Mediana medianaEscolhida = null;

		PM = distribuidorDeMedianasIniciais.distribuirMedianas(PM);

		int posicaoMedianaDesejada = medianasDesejadas.length - 1;

		while(PM.getNumMedianas() > medianasDesejadas[0]){
			medianaEscolhida = pegarMenorFitnessMedio(PM);

			Vertice[] verticesMedianaEscolhida = medianaEscolhida.getVertices();
			PM.removerMediana(medianaEscolhida);
			
			ArrayList<Mediana> arrayListMedianasPM = new ArrayList<Mediana>();
			arrayListMedianasPM.addAll(Arrays.asList(PM.getMedianas()));

			for(Vertice vertice : verticesMedianaEscolhida){
				vertice.escolherMediana(arrayListMedianasPM);
			}

			if(medianasDesejadas[posicaoMedianaDesejada] == PM.getNumMedianas()){
				tempoExecucao = System.currentTimeMillis() - tempoInicial;
				tempoExecucao /= 1000;

				posicaoMedianaDesejada--;

				PM.validarResultado();

				Resultado resultado = new Resultado();
				resultado.setFitness(PM.getFitness());
				resultado.setNumMedianas(PM.getNumMedianas());
				resultado.setNumVertices(PM.getNumVertices());
				resultado.setTempoExecucao(tempoExecucao);
				resultado.setSolucaoAplicada(this);
				resultado.setMedianas(arrayListMedianasPM);

				arrayListResultados.add(resultado);
			}
		}

		int tamanhoVetor = arrayListResultados.size();

		Resultado[] resultados = new Resultado[tamanhoVetor];
		resultados = arrayListResultados.toArray(resultados);

		return resultados;
	}

	private Mediana pegarMenorFitnessMedio(PMediana PM){
		Mediana medianaMenorFitnessMedio = null;

		for(Mediana mediana : PM.getMedianas()){
			if(medianaMenorFitnessMedio == null){
				medianaMenorFitnessMedio = mediana;

			}else {
				if(calcularFitnessMedio(mediana) < calcularFitnessMedio(medianaMenorFitnessMedio)){
					medianaMenorFitnessMedio = mediana;
				}
			}
		}

		return medianaMenorFitnessMedio;
	}

	private double calcularFitnessMedio(Mediana mediana){
		return mediana.getFitness() / mediana.getNumConectados();
	}
}