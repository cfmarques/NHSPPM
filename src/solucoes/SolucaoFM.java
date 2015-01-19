package solucoes;

import java.util.ArrayList;
import java.util.Arrays;

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
		Mediana refMelhor;
		boolean primeiroValor;

		for(Vertice vertice : PM.getVertices()){
			menorDistancia = 0;
			refMelhor = null;
			primeiroValor = true;

			if(vertice.getMed()){
				vertice.setDistancia(0);
				vertice.setRefMelhor(vertice);

			}else {
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
							primeiroValor = false;

						}else if(distancia < vertice.getDist()){
							menorDistancia = distancia;
							vertice.setDistancia(menorDistancia);
							vertice.setRefMelhor(possivelMediana);
						}
					}
				}
			}

			if(!vertice.getRefMelhor().getMed()){
				vertice.getRefMelhor().setMed(true);
				refMelhor = new Mediana(vertice.getRefMelhor());
				refMelhor.addVertice(vertice);
				PM.getMedianas().add(refMelhor);

			}else {
				for(Mediana mediana : PM.getMedianas()){
					if(mediana.getVertice().getId() == vertice.getRefMelhor().getId()){
						refMelhor = mediana;
						refMelhor.addVertice(vertice);
					}					
				}				
			}
		}
		PM.setNumMed(PM.getMedianas().size());
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

				ArrayList<Mediana> medianasAux = (ArrayList<Mediana>)PM.getMedianas().clone();
				Vertice antigaMediana = medianaEscolhida.getVertice();

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
}
