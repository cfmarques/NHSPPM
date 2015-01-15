package Exercicio1Semana;

import java.util.ArrayList;

public class Vertice {
	/** Declaração de variáveis
	 *  @id - Variável de identificação
	 *  @x - Variável int que representa a posição x em um plano cartesiano
	 *  @y - Variável int que representa a posição y em um plano cartesiano
	 *  @dist - Variável int que representa a distância da vertice até a mediana
	 *  @ned - Variável boolean que representa se aquela vertice é ou não mediana
	 *  @refMelhor - Variável Vertice que representa a mediana a qual ele está conectado
	 */
	private int id, x, y;
	private double dist;
	private boolean med;
	private Vertice refMelhor;
	
	public Vertice(){
		this.med = false;
		
	}
	
	public Vertice(int id, int x, int y){
		this.id = id;
		this.med = false;
		this.x = x;
		this.y = y;
	}
	
	public void setMed(boolean med){
		this.med = med;
	}
	public void setDistancia(double dist){
		this.dist = dist;
	}
	public void setRefMelhor(Vertice refMelhor){
		this.refMelhor = refMelhor;
	}
	
	public boolean getMed(){
		return this.med;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public double getDist(){
		return this.dist;
	}	
	public int getId(){
		return this.id;		
	}
	public Vertice getRefMelhor(){
		return this.refMelhor;
	}
	
	public void escolheMediana(ArrayList<Mediana> vetMediana){
		double distancia = 0, distanciaTemp = 0;
		Vertice refMelhor = null;
		Mediana medianaEscolhida = null;
		boolean primeiroValor = true;
		
		for(Mediana mediana : vetMediana){
			Vertice vertice = mediana.getVertice();
			distanciaTemp = 0;
			
			int MedX = vertice.getX();
			int MedY = vertice.getY();
			
			double difX = this.x - MedX;
			double xAoQuadrado = Math.pow(difX, 2);
			
			double difY = this.y - MedY;
			double yAoQuadrado = Math.pow(difY, 2);
					
			distanciaTemp = Math.sqrt(xAoQuadrado + yAoQuadrado);

			if(primeiroValor){
				distancia = distanciaTemp;
				refMelhor = vertice;
				medianaEscolhida = mediana;
				primeiroValor = false;
				
			}else if(distanciaTemp < distancia){
				distancia = distanciaTemp;
				refMelhor = vertice;
				medianaEscolhida = mediana;
				
			}
		}
		
		this.refMelhor = refMelhor;
		this.dist = distancia;
		
		if(!medianaEscolhida.getVertices().contains(this)){
			medianaEscolhida.addVertice(this);
		}
	}
}
