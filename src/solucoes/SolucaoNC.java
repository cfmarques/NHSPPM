package solucoes;

import java.util.ArrayList;

import pmediana.Mediana;
import pmediana.PMediana;
import pmediana.Vertice;

public class SolucaoNC extends Solucao{
	public SolucaoNC(){
		super();
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
