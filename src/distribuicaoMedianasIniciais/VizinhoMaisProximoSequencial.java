package distribuicaoMedianasIniciais;

import pmediana.Mediana;
import pmediana.PMediana;
import pmediana.Vertice;

public class VizinhoMaisProximoSequencial implements DistribuidorDeMedianasIniciais {
	@Override
	public PMediana distribuirMedianas(PMediana PM) {
		double distancia, menorDistancia;
		Vertice verticeMelhorRef;
		Mediana melhorRef;
		boolean primeiroValor;

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
			}
		}
		return PM;
	}
}
