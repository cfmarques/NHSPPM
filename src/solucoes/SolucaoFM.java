package solucoes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import pmediana.Mediana;
import pmediana.PMediana;
import pmediana.Vertice;

public class SolucaoFM extends Solucao{
	private String variacao = null; //Maior ou Menor

	public SolucaoFM(){
		super();
	}

	public String getVariacao(){
		return this.variacao;
	}

	public void setVariacao(String variacao){
		if(variacao.equalsIgnoreCase("maior") || variacao.equalsIgnoreCase("menor")){
			this.variacao = variacao.toUpperCase();
		}
	}

	@Override
	public void distribuirMedianas(PMediana PM) {
		double distancia, menorDistancia;
		Vertice melhorRef;
		boolean primeiroValor;
		
		System.out.println("Número total de vertices: " + PM.getVertices().size());

		for(Vertice vertice : PM.getVertices()){
			menorDistancia = 0;
			melhorRef = null;
			primeiroValor = true;

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
							menorDistancia = distancia;
							vertice.setDistancia(menorDistancia);
							vertice.setRefMelhor(possivelMediana);
							melhorRef = possivelMediana;
							
							primeiroValor = false;
							
						}else if(distancia < vertice.getDist()){
							menorDistancia = distancia;
							vertice.setDistancia(menorDistancia);
							vertice.setRefMelhor(possivelMediana);
							melhorRef = possivelMediana;
						}
					}
				}
				
				if(melhorRef.getMed()){
					for(Mediana mediana : PM.getMedianas()){
						if(mediana.getVertice().getId() == melhorRef.getId()){
							mediana.addVertice(vertice);
						}					
					}
				}else {
					melhorRef.setMed(true);
					melhorRef.setRefMelhor(melhorRef);
					melhorRef.setDistancia(0);
					
					Mediana medianaMelhorRef = new Mediana(melhorRef);
					medianaMelhorRef.addVertice(vertice);
					
					ArrayList<Mediana> listaMedianas = PM.getMedianas();
					listaMedianas.add(medianaMelhorRef);
										
					Mediana medianaComVerticeExcluida = PM.excluirVerticeDaMedianaConectada(melhorRef);
					if(medianaComVerticeExcluida != null && medianaComVerticeExcluida.getVertices().size() == 1){
						PM.getMedianas().remove(medianaComVerticeExcluida);
						
						medianaComVerticeExcluida.getVertice().setMed(false);
						medianaComVerticeExcluida.getVertice().setRefMelhor(null);
						medianaComVerticeExcluida.getVertices().remove(medianaComVerticeExcluida.getVertice());
						medianaComVerticeExcluida.getVertice().escolheMediana(PM.getMedianas());
						medianaComVerticeExcluida.setVertice(null);
					}
				}
				
				ArrayList<HashMap<Mediana, Vertice>> inconsistencias = verificaInconsistencia(PM);
				if(inconsistencias.size() > 0){
					System.out.println("Inconsistências encontradas! ");
					
					for(HashMap<Mediana, Vertice> inconsistencia : inconsistencias){
						System.out.println("Inconsistências encontradas! ");
						for(Mediana key : inconsistencia.keySet()){
							int indiceMediana = PM.getMedianas().indexOf(key);
							int indiceVertice = key.getVertices().indexOf(inconsistencia.get(key));
							
							System.out.println(indiceMediana + "º Mediana!");
							System.out.println(indiceVertice + "º Vertice!");
						}
					}
					System.out.println("PAUSE PARA ANÁLISE!");
				}
			}
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////// TESTE //////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		PM.setNumMed(PM.getMedianas().size());
		
		int quantidadeVertices = 0;
		for(Mediana mediana : PM.getMedianas()){
			quantidadeVertices += mediana.getVertices().size();
		}
		
		System.out.println("Quantidade de vertices: " + quantidadeVertices);
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////// TESTE //////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}

	@Override
	public ArrayList<PMediana> gerarSolucao(ArrayList<Vertice> conjuntoVertices, int[] medianas) {
		System.out.println("Gerando solução para o conjunto de vertices de " + conjuntoVertices.size() + " vertices.");

		if(variacao.equalsIgnoreCase("maior") || variacao.equalsIgnoreCase("menor")){
			ArrayList<PMediana> resultado = new ArrayList<PMediana>();

			PMediana PM = new PMediana();
			Mediana medianaEscolhida = null;
			PM.setVertices(conjuntoVertices);

			distribuirMedianas(PM);

			while(PM.getNumMed() > medianas[0]){
				switch(variacao){
				case "MENOR":
					medianaEscolhida = pegarMenorFitnessMedio(PM);
					break;

				case "MAIOR":
					medianaEscolhida = pegarMaiorFitnessMedio(PM);
					break;
				}

				ArrayList<Mediana> medianasAux = (ArrayList<Mediana>)PM.getMedianas().clone(); //Novo arrayList mas as vertices são todas referencias da PM.medianas
				Vertice antigaMediana = medianaEscolhida.getVertice(); //antigaMediana é um referencia da PM.mediana com o menor fitness medio

				for(Mediana mediana : medianasAux){
					if(mediana.getVertice().getId() == antigaMediana.getId()){
						PM.getMedianas().remove(medianaEscolhida);
						PM.setNumMed(PM.getMedianas().size());

						antigaMediana.setMed(false);
					}
				}

				antigaMediana.escolheMediana(PM.getMedianas());		

				for(Vertice vertice : medianaEscolhida.getVertices()){
					vertice.escolheMediana(PM.getMedianas());
				}

				System.out.println("Processando " + conjuntoVertices.size() +" vertices... Número de medianas: " + PM.getNumMed());

				for(int numMediana : medianas){
					if(numMediana == PM.getNumMed()){
						System.out.println("Inserido a p-mediana de " + PM.getNumMed() + " medianas");

						PMediana PMClone = PM.copiar();

						resultado.add(PMClone);
					}
				}
			}			
			return resultado;
		}

		System.out.println("Você não escolheu a variação (Menor ou Maior). Por favor escolha!");
		return null;
	}

	public Mediana pegarMaiorFitnessMedio(PMediana PM){
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

	public Mediana pegarMenorFitnessMedio(PMediana PM){
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

	public double calcularFitnessMedio(Mediana mediana){
		return mediana.getFitness() / mediana.getNumConectados();
	}

	public double calcularFitnessMedio(PMediana pmediana){
		return pmediana.getFitness() / pmediana.getNumMed();
	}
	
	public ArrayList<HashMap<Mediana, Vertice>> verificaInconsistencia(PMediana PM){
		ArrayList<HashMap<Mediana, Vertice>> inconsistencias = new ArrayList<HashMap<Mediana, Vertice>>();
		ArrayList<Mediana> medianas = new ArrayList<Mediana>();
		
		for(Vertice vertice : PM.getVertices()){
			int vezes = 0;
			medianas.clear();
			for(Mediana mediana : PM.getMedianas()){
				if(mediana.getVertices().contains(vertice)){
					medianas.add(mediana);
					vezes++;
				}
			}
			if(vezes > 1){
				HashMap<Mediana, Vertice> inconsistencia = new HashMap<Mediana, Vertice>();
				for(Mediana mediana : medianas){
					inconsistencia.put(mediana, vertice);
				}
				inconsistencias.add(inconsistencia);
			}
		}
		return inconsistencias;
	}
}