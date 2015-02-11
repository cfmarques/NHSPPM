package solucao;

import java.util.ArrayList;
import java.util.Arrays;

import pmediana.Mediana;
import pmediana.PMediana;
import pmediana.Vertice;
import app.Resultado;

public class SolucaoFM implements Solucao{
	private String variacao; //Maior ou Menor

	public SolucaoFM(){
		super();
	}
	
	public SolucaoFM(String critDesempate){
		super();
		
		this.variacao = critDesempate;
	}

	@Override
	public String getVariacao(){
		return this.variacao;
	}
	
	@Override
	public void setVariacao(String variacao) {
		if(variacao.equalsIgnoreCase("maior") || variacao.equalsIgnoreCase("menor")){
			this.variacao = variacao.toUpperCase();
		}
	}
	
	@Override
	public void distribuirMedianas(PMediana PM) {
		double distancia, menorDistancia;
		Vertice verticeMelhorRef;
		Mediana melhorRef;
		boolean primeiroValor;
		
		/*System.out.println("Número total de vertices: " + PM.getVertices().length);*/

		for(Vertice vertice : PM.getVertices()){			
			menorDistancia = 0;
			primeiroValor = true;
			melhorRef = null;
			verticeMelhorRef = null;

			if(!vertice.getMed() && vertice.getRefMelhor() == null){
				for(Vertice possivelMediana : PM.getVertices()){
					distancia = 0;

					if(possivelMediana.getId() != vertice.getId()) {					
						double difX = possivelMediana.getX() - vertice.getX();
						double xAoQuadrado = Math.pow(difX, 2);

						double difY = possivelMediana.getY() - vertice.getY();
						double yAoQuadrado = Math.pow(difY, 2);

						distancia = Math.sqrt(xAoQuadrado + yAoQuadrado);

						if(primeiroValor){
							verticeMelhorRef = possivelMediana;
							menorDistancia = distancia;
							
							vertice.setDistancia(menorDistancia);
							
							primeiroValor = false;
							
						}else if(distancia < vertice.getDist()){
							verticeMelhorRef = possivelMediana;
							menorDistancia = distancia;
							
							vertice.setDistancia(menorDistancia);
						}
					}
				}
				
				if(verticeMelhorRef.getMed()){					
					melhorRef = verticeMelhorRef.getRefMelhor();
					melhorRef.addVertice(vertice);

				}else {
					if(verticeMelhorRef.getRefMelhor() != null){
					PM.removerVerticeDaMedianaConectada(verticeMelhorRef);
					}
					
					melhorRef = new Mediana(verticeMelhorRef);
					melhorRef.addVertice(vertice);
					
					PM.addMediana(melhorRef);
				}
				
				/*ArrayList<HashMap<Mediana, Vertice>> inconsistencias = PM.verificaInconsistencia();
				
				if(inconsistencias.size() > 0){
					System.out.println("Inconsistências encontradas! ");
					
					for(HashMap<Mediana, Vertice> inconsistencia : inconsistencias){
						System.out.println("Inconsistências encontradas! ");
						for(Mediana key : inconsistencia.keySet()){
							ArrayList<Mediana> arrayListMedianas = (ArrayList<Mediana>) Arrays.asList(PM.getMedianas());
							ArrayList<Vertice> arrayListVertices = (ArrayList<Vertice>) Arrays.asList(key.getVertices());
							
							int indiceMediana = arrayListMedianas.indexOf(key);
							int indiceVertice = arrayListVertices.indexOf(inconsistencia.get(key));
							
							System.out.println(indiceMediana + "º Mediana!");
							System.out.println(indiceVertice + "º Vertice!");
						}
					}
					System.out.println("PAUSE PARA ANÁLISE!");
				}*/
			}
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////// TESTE //////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		/*int quantidadeVertices = 0;
		for(Mediana mediana : PM.getMedianas()){
			quantidadeVertices += mediana.getVertices().length;
		}*/
		
		/*System.out.println("Quantidade de vertices: " + quantidadeVertices);*/
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////// TESTE //////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
		/*System.out.println("Fitness Global Inicial da PM: " + PM.getFitness());*/

		/*double somaFitnessMedianasPM = 0;
		
		for(Mediana mediana : PM.getMedianas()){
			somaFitnessMedianasPM += mediana.getFitness();
		}*/
		
		/*System.out.println("Fitness da PM contando os fitness de cada Mediana: " + somaFitnessMedianasPM);*/
		
	}

	@Override
	public Resultado[] gerarSolucao(Vertice[] conjuntoVertices, int[] medianasDesejadas) {
		long tempoInicial = System.currentTimeMillis();
		double tempoExecucao;
		
		/*System.out.println("Gerando solução para o conjunto de vertices de " + conjuntoVertices.length + " vertices.");*/

		if(variacao.equalsIgnoreCase("maior") || variacao.equalsIgnoreCase("menor")){
			ArrayList<Resultado> arrayListResultados = new ArrayList<Resultado>();

			PMediana PM = new PMediana(conjuntoVertices);
			Mediana medianaEscolhida = null;

			distribuirMedianas(PM);
			
			int posicaoMedianaDesejada = medianasDesejadas.length - 1;

			while(PM.getNumMedianas() > medianasDesejadas[0]){
				/*System.out.println("Posição atual da mediana desejada: " + posicaoMedianaDesejada);
				System.out.println("Mediana desejada atual: " + medianasDesejadas[posicaoMedianaDesejada]);*/
				
				if(variacao.equalsIgnoreCase("menor")){
					medianaEscolhida = pegarMenorFitnessMedio(PM);
					
				}else if(variacao.equalsIgnoreCase("maior")){
					medianaEscolhida = pegarMaiorFitnessMedio(PM);
					
				}
				
				Vertice[] verticesMedianaEscolhida = medianaEscolhida.getVertices();
				PM.removerMediana(medianaEscolhida);
				
				for(Vertice vertice : verticesMedianaEscolhida){
					ArrayList<Mediana> arrayListMedianasPM = new ArrayList<Mediana>();
					arrayListMedianasPM.addAll(Arrays.asList(PM.getMedianas()));
										
					vertice.escolherMediana(arrayListMedianasPM);
				}
				
				if(medianasDesejadas[posicaoMedianaDesejada] == PM.getNumMedianas()){
					tempoExecucao = System.currentTimeMillis() - tempoInicial;
					tempoExecucao /= 1000;
					
					
					posicaoMedianaDesejada--;
					
					PM.validarResultado();
					
					/*System.out.println("Inserido a p-mediana de " + PM.getNumMedianas() + " medianas");*/
					
					Resultado resultado = new Resultado();
					resultado.setFitness(PM.getFitness());
					resultado.setNumMedianas(PM.getNumMedianas());
					resultado.setNumVertices(PM.getNumVertices());
					resultado.setTempoExecucao(tempoExecucao);
					resultado.setSolucaoAplicada(this);

					arrayListResultados.add(resultado);
				}
			}
			
			int tamanhoVetor = arrayListResultados.size();
			
			Resultado[] resultados = new Resultado[tamanhoVetor];
			resultados = arrayListResultados.toArray(resultados);
			
			return resultados;
		}

		System.out.println("Você não escolheu a variação (Menor ou Maior). Por favor escolha!");
		return null;
	}

	private Mediana pegarMaiorFitnessMedio(PMediana PM){
		Mediana medianaMaiorFitnessMedio = null;

		for(Mediana mediana : PM.getMedianas()){
			if(medianaMaiorFitnessMedio == null){
				medianaMaiorFitnessMedio = mediana;

			}else {
				if(calcularFitnessMedio(mediana) > calcularFitnessMedio(medianaMaiorFitnessMedio)){
					medianaMaiorFitnessMedio = mediana;
				}					
			}
		}

		return medianaMaiorFitnessMedio;
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