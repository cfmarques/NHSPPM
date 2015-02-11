package solucao;

import java.util.ArrayList;

import pmediana.Mediana;
import pmediana.PMediana;
import pmediana.Vertice;

public class SolucaoNC implements Solucao{
	private String variacao; //Maior ou Menor

	public SolucaoNC(){
		super();
	}
	
	public SolucaoNC(String variacao){
		super();
		
		this.variacao = variacao;
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
	public ArrayList<PMediana> gerarSolucao(int[] medianas) {
		carregarConjuntosVertices();

		for(ArrayList<Vertice> vertices : conjuntosVertices){
			PMediana PM = new PMediana();
			PM.setVertices(vertices);

			distribuirMedianas(PM);
			
			
		}
		
		return null;
	}
}
