package solucao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pmediana.Mediana;
import pmediana.PMediana;
import pmediana.Vertice;
import app.Resultado;
import distribuicaoMedianasIniciais.DistribuidorDeMedianasIniciais;

public class SolucaoMenorNumeroConexoes3 implements Solucao {
	private String nome = "Explosão por menor número de conexões 3";
	private String abreviacao;
	private String descricao = "";
	private String critDesempate; /*MenorFitness ou MaiorFitness*/
	
	public SolucaoMenorNumeroConexoes3(String critDesempate){
		String critDesempate_string = "Desempate por ";
		
		this.critDesempate = critDesempate;
		
		if(critDesempate.equalsIgnoreCase("menorFitness")){
			critDesempate_string += "mediana com menor fitness";
			this.abreviacao = "E<NCD<F3";
			
		}else if(critDesempate.equalsIgnoreCase("maiorFitness")){
			critDesempate_string += "mediana com maior fitness";
			this.abreviacao = "E<NCD>F3";
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
		long tempoInicial = System.currentTimeMillis(); //Inicia contador de tempo
		double tempoExecucao;

		ArrayList<Resultado> arrayListResultados = new ArrayList<Resultado>();

		PMediana PM = new PMediana(conjuntoVertices); //Constroi a PMediana com o conjunto de vertices do arquivo
		Mediana medianaEscolhida = null;

		PM = distribuidorDeMedianasIniciais.distribuirMedianas(PM); //Distribui as medianas iniciais de acordo com o distribuido (Sequencial ou aleatório)

		int posicaoMedianaDesejada = medianasDesejadas.length - 1;

		while(PM.getNumMedianas() > medianasDesejadas[0]){ //Itera enquanto for maior que a menor mediana desejada (Ex.: 5)
			medianaEscolhida = pegarMenorNumeroConexoes(PM);
			Mediana medianaMaisProxima = pegarMedianaMaisProxima(medianaEscolhida, PM);
			
			Mediana medianaPerfeitaEntreVerticesDaPMediana = pegarMedianaPerfeitaEntreVerticesDaPMediana(PM);
			
			List<Vertice> vertices = new ArrayList<Vertice>();
			vertices.addAll(Arrays.asList(medianaEscolhida.getVertices()));
			vertices.addAll(Arrays.asList(medianaMaisProxima.getVertices()));
						
			PM.removerMediana(medianaEscolhida);
			PM.removerMediana(medianaMaisProxima);
			
			PM.addMediana(medianaPerfeitaEntreVerticesDaPMediana);
			
			ArrayList<Mediana> arrayListMedianasPM = new ArrayList<Mediana>();
			arrayListMedianasPM.addAll(Arrays.asList(PM.getMedianas()));
			
			for(Mediana mediana : arrayListMedianasPM){
				vertices.addAll(Arrays.asList(mediana.getVertices()));
			}
			
			for(Mediana mediana : arrayListMedianasPM){
				for(Vertice vertice : vertices){
					if(vertice != mediana.getVertice()){
						mediana.removerVertice(vertice);
						vertice.escolherMediana(arrayListMedianasPM);
					}
				}
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
	
	/** Função que retorna a mediana perfeita entre um universe de 2 medianas e suas vertices.
	 * 	A função torna as medianas em vertices e dentre todas as vertices é visto qual é a 
	 * 	possível mediana com o menor fitness dentre elas, elegendo esta como a mediana perfeita.
	 * @param mediana1
	 * @param mediana2
	 * @return
	 */
	private Mediana pegarMedianaPerfeitaEntreVerticesDaPMediana(PMediana PM) {
		Vertice verticeDaMedianaPerfeita = null;
		double menorFitness = 0;
		
		ArrayList<Vertice> conjuntoVertices = new ArrayList<Vertice>();
		
		for(Vertice vertice : PM.getVertices()){
			if(!vertice.getMed()){
				conjuntoVertices.add(vertice);
			}
		}
		
		for(Vertice verticeDaPossivelMediana : conjuntoVertices){
			double somaDosFitness = 0;
			
			for(Vertice vertice : conjuntoVertices){
				somaDosFitness += distanciaEntreVertices(verticeDaPossivelMediana, vertice);
			}
			
			if(verticeDaMedianaPerfeita == null){
				verticeDaMedianaPerfeita = verticeDaPossivelMediana;
				menorFitness = somaDosFitness;
				
			}else if(somaDosFitness < menorFitness) {
				verticeDaMedianaPerfeita = verticeDaPossivelMediana;
				menorFitness = somaDosFitness;
			}
		}
		
		Mediana medianaPerfeita = new Mediana(verticeDaMedianaPerfeita);
		
		/*for(Vertice vertice : conjuntoVertices){
			vertice.escolherMediana(medianaPerfeita);
		}*/

		return medianaPerfeita;
	}

	/** Função que retorna a mediana com o menor número de conexões
	 * @param PM
	 * @return Mediana
	 */
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
	
	/** Função que retorna a mediana mais próxima
	 * @param medianaPrincipal
	 * @param PM
	 * @return Mediana
	 */
	private Mediana pegarMedianaMaisProxima(Mediana medianaPrincipal, PMediana PM){
		Mediana medianaMaisProxima = null;
		double melhorDistancia = 0;
				
		for(Mediana mediana : PM.getMedianas()){
			if(mediana != medianaPrincipal){
				if(medianaMaisProxima == null){
					medianaMaisProxima = mediana;
					melhorDistancia = distanciaEntreMedianas(medianaPrincipal, mediana);
	
				}else {
					double distanciaEntreMedianas = distanciaEntreMedianas(medianaPrincipal, mediana);
					
					if(distanciaEntreMedianas < melhorDistancia){
						medianaMaisProxima = mediana;
						melhorDistancia = distanciaEntreMedianas;
					}
				}
			}
		}
		return medianaMaisProxima;
	}
	
	/** Função que retorna a distancia entre duas medianas
	 * @param mediana1
	 * @param mediana2
	 * @return double
	 */
	private double distanciaEntreMedianas(Mediana mediana1, Mediana mediana2){
		Vertice verticeMediana1 = mediana1.getVertice();
		Vertice verticeMediana2 = mediana2.getVertice();
		
		int verticeMediana1X = verticeMediana1.getX();
		int verticeMediana1Y = verticeMediana1.getY();
		
		int verticeMediana2X = verticeMediana2.getX();
		int verticeMediana2Y = verticeMediana2.getY();
		
		double diffX = verticeMediana1X - verticeMediana2X;
		double diffY = verticeMediana1Y - verticeMediana2Y;
		
		double diffXAoQuadrado = Math.pow(diffX, 2);
		double diffYAoQuadrado = Math.pow(diffY, 2);
		
		double distancia = Math.sqrt(diffXAoQuadrado + diffYAoQuadrado);
		
		return distancia;
	}
	
	/** Função que retorna a distancia entre duas medianas
	 * @param vertice1
	 * @param vertice2
	 * @return double
	 */
	private double distanciaEntreVertices(Vertice vertice1, Vertice vertice2){
		int vertice1X = vertice1.getX();
		int vertice1Y = vertice1.getY();
		
		int vertice2X = vertice2.getX();
		int vertice2Y = vertice2.getY();
		
		double diffX = vertice1X - vertice2X;
		double diffY = vertice1Y - vertice2Y;
		
		double diffXAoQuadrado = Math.pow(diffX, 2);
		double diffYAoQuadrado = Math.pow(diffY, 2);
				
		return Math.sqrt(diffXAoQuadrado + diffYAoQuadrado);
	}
}