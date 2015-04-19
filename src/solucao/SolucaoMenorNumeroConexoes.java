package solucao;

import java.util.ArrayList;
import java.util.Arrays;

import pmediana.Mediana;
import pmediana.PMediana;
import pmediana.Vertice;
import app.Resultado;
import distribuicaoMedianasIniciais.DistribuidorDeMedianasIniciais;

public class SolucaoMenorNumeroConexoes implements Solucao {
	private String nome = "Explosão por menor número de conexões";
	private String abreviacao;
	private String descricao = "";
	private String critDesempate; /*MenorFitness ou MaiorFitness*/
	
	public SolucaoMenorNumeroConexoes(String critDesempate){
		String critDesempate_string = "Desempate por ";
		
		this.critDesempate = critDesempate;
		
		if(critDesempate.equalsIgnoreCase("menorFitness")){
			critDesempate_string += "mediana com menor fitness";
			this.abreviacao = "E<NCD<F";
			
		}else if(critDesempate.equalsIgnoreCase("maiorFitness")){
			critDesempate_string += "mediana com maior fitness";
			this.abreviacao = "E<NCD>F";
		}
		
		this.nome += " (" + critDesempate_string + ")";
	}

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
			medianaEscolhida = pegarMenorNumeroConexoes(PM);

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

	private Mediana pegarMenorNumeroConexoes(PMediana PM){
		Mediana medianaMenorNumeroConexoes = null;

		for(Mediana mediana : PM.getMedianas()){
			if(medianaMenorNumeroConexoes == null){
				medianaMenorNumeroConexoes = mediana;

			}else {
				if(mediana.getNumConectados() < medianaMenorNumeroConexoes.getNumConectados()){
					medianaMenorNumeroConexoes = mediana;

				}else if(mediana.getNumConectados() == medianaMenorNumeroConexoes.getNumConectados()){
					boolean menorFitness, maiorFitness;

					menorFitness = this.critDesempate.equalsIgnoreCase("menorFitness") && 
							mediana.getFitness() < medianaMenorNumeroConexoes.getFitness();

					maiorFitness = this.critDesempate.equalsIgnoreCase("maiorFitness") && 
							mediana.getFitness() > medianaMenorNumeroConexoes.getFitness();

					if(menorFitness || maiorFitness){
						medianaMenorNumeroConexoes = mediana;
					}
				}
			}
		}
		return medianaMenorNumeroConexoes;
	}
}