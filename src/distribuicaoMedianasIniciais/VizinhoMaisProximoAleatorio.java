package distribuicaoMedianasIniciais;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pmediana.Mediana;
import pmediana.PMediana;
import pmediana.Vertice;

public class VizinhoMaisProximoAleatorio implements DistribuidorDeMedianasIniciais {
	@Override
	public PMediana distribuirMedianas(PMediana PM) {
		double distancia, menorDistancia;
		Vertice verticeMelhorRef = null;
		Mediana melhorRef;
		boolean primeiroValor;
		
		List<Vertice> listaVertices = Arrays.asList(PM.getVertices());
		List<Vertice> verticesCandidatas = new ArrayList<Vertice>(listaVertices);
		int qntVerticesCandidata = verticesCandidatas.size();
		Vertice verticeEscolhida = null;
		int posicaoEscolhida = 0;

		while(qntVerticesCandidata > 0){
			posicaoEscolhida = (int)(Math.random() * qntVerticesCandidata);
			verticeEscolhida = verticesCandidatas.get(posicaoEscolhida);
			primeiroValor = true;

			if(!verticeEscolhida.getMed() && verticeEscolhida.getRefMelhor() == null){
				for(Vertice possivelMediana : PM.getVertices()){
					distancia = 0;

					if(possivelMediana.getId() != verticeEscolhida.getId()) {					
						double difX = possivelMediana.getX() - verticeEscolhida.getX();
						double xAoQuadrado = Math.pow(difX, 2);

						double difY = possivelMediana.getY() - verticeEscolhida.getY();
						double yAoQuadrado = Math.pow(difY, 2);

						distancia = Math.sqrt(xAoQuadrado + yAoQuadrado);

						if(primeiroValor){
							verticeMelhorRef = possivelMediana;
							menorDistancia = distancia;

							verticeEscolhida.setDistancia(menorDistancia);

							primeiroValor = false;

						}else if(distancia < verticeEscolhida.getDist()){
							verticeMelhorRef = possivelMediana;
							menorDistancia = distancia;

							verticeEscolhida.setDistancia(menorDistancia);
						}
					}
				}

				if(verticeMelhorRef.getMed()){					
					melhorRef = verticeMelhorRef.getRefMelhor();
					melhorRef.addVertice(verticeEscolhida);

				}else {
					if(verticeMelhorRef.getRefMelhor() != null){
						PM.removerVerticeDaMedianaConectada(verticeMelhorRef);
					}

					melhorRef = new Mediana(verticeMelhorRef);
					melhorRef.addVertice(verticeEscolhida);

					PM.addMediana(melhorRef);
				}
			}
			
			verticesCandidatas.remove(verticeEscolhida);
			verticesCandidatas.remove(verticeMelhorRef);
			
			qntVerticesCandidata = verticesCandidatas.size();
		}
		return PM;
	}
}