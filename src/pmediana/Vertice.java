package pmediana;

import java.util.ArrayList;
import java.util.Arrays;

public class Vertice {
	/** Declaração de variáveis
	 *  @id - int que representa a identificação
	 *  @x - int que representa a posição x em um plano cartesiano
	 *  @y - int que representa a posição y em um plano cartesiano
	 *  @dist - int que representa a distância da vertice até a mediana
	 *  @ned - boolean que representa se aquela vertice é ou não mediana
	 *  @refMelhor - Vertice que representa a mediana a qual ele está conectado
	 */
	private int id, x, y;
	private double dist;
	private boolean med;
	/*private Vertice refMelhor;*/
	private Mediana melhorRef;
	
	public Vertice(){
		this.x = 0;
		this.y = 0;
		this.id = 0;
		this.med = false;
		this.melhorRef = null;
	}
	
	public Vertice(int id, int x, int y){
		this.x = x;
		this.y = y;
		this.id = id;
		this.med = false;
		this.melhorRef = null;
	}
	
	public void setMed(boolean med){
		this.med = med;
	}
	
	public void setDistancia(double dist){
		this.dist = dist;
	}
	
	public void setMelhorRef(Mediana refMelhor){
		this.melhorRef = refMelhor;
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
	
	public Mediana getRefMelhor(){
		return this.melhorRef;
	}
	
	public void escolherMediana(ArrayList<Mediana> vetMediana){
		/*System.out.println("Quantidade de medianas: " + vetMediana.size());*/
		
		double distancia = 0, distanciaTemp = 0;
/*		Vertice refMelhor = null;*/
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
				/*refMelhor = vertice;*/
				medianaEscolhida = mediana;
				primeiroValor = false;
				
			}else if(distanciaTemp < distancia){
				distancia = distanciaTemp;
				/*refMelhor = vertice;*/
				medianaEscolhida = mediana;
				
			}
		}
		
		this.melhorRef = medianaEscolhida;
		this.dist = distancia;
		
		Vertice[] medianaEscolhidaVertices = medianaEscolhida.getVertices();
		
		ArrayList<Vertice> arrayListVerticesMedianaEscolhida = new ArrayList<Vertice>();
		arrayListVerticesMedianaEscolhida.addAll(Arrays.asList(medianaEscolhidaVertices));
		
		if(!arrayListVerticesMedianaEscolhida.contains(this)){
			medianaEscolhida.addVertice(this);
		}
	}
}
