package pmediana;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PMediana {
	/** Declaração de variáveis
	 *  @fitness - Variável double que representa a potuação da PMediana
	 *  @numMed - Variável int que representa a quantidade de medianas da PMediana
	 *  @medDesejado - Variável int que representa a quantidade de medianas desejadas na PMediana
	 *  @vetMed - ArrayList Mediana que representa todas as medianas daquela PMediana
	 */
	private double fitness;
	private int numMed, medDesejado;
	private ArrayList<Vertice> vetVertice = new ArrayList<Vertice>();	
	private ArrayList<Mediana> vetMediana = new ArrayList<Mediana>();
	private Mediana medianaMenorFitnessLocalMedio, medianaMaiorFitnessLocalMedio;
	
	public PMediana(){
		this.vetVertice.clear();
		this.vetMediana.clear();
		this.numMed = 0;
		this.medDesejado = 0;
		this.fitness = 0;
	}
	
	public void setMedDesejado(int medDesejado){
		this.medDesejado = medDesejado;		
	}
	
	public void setNumMed(int numMed){
		this.numMed = numMed;
	}
	
	public int getNumMed(){
		return this.numMed;
	}
	
	public int getMedDesejado(){
		return this.medDesejado;
	}
	
	public double getFitness(){
		return this.fitness;
	}
	public ArrayList<Vertice> getVetVertice(){
		return this.vetVertice;
	}
	
	//Lê um arquivo txt com as coordenadas e carrega as coordenadas para o vetor de vertices da PMediana
	public void carregarCoordenadas(String arquivo){
		BufferedReader buffRead = null;
		
		/**
		 * @arquivo é uma variavel do tipo String que contem a localização do arquivo e seu nome. Ex.: "C:/arquivos de programas/exemplo.txt",
		 * caso o caminho não seja declarado, o sistema tentará buscar na pasta de coordenadas. 
	 	*/
		
		// Realiza o Buffer do arquivo de coordenadas informado logo acima
		try {
			buffRead = new BufferedReader(new FileReader(arquivo));
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.out.print("Erro na localização do arquivo: " + e1.getMessage());
			
		}
		
		// Realiza a Leitura das linhas do arquivo de coordenadas
		try {
			String linha = "", coordenadas[];
			boolean primeiraLinha = true;
			int id = 0;
			
			// Verifica se o BuffRead esta pronto (Verifica se o ponteiro do arquivo não chegou ao final)
			while(buffRead.ready()){
				linha = buffRead.readLine();
								
				// Validador para não pegar a primeira linha do arquivo. A primeira linha contem o nome. Ex.: "324.txt"
				if(primeiraLinha){
					primeiraLinha = false;
					
				}else {
					// Quebra as linhas do arquivo em um array de String contendo as coordenadas
					coordenadas = linha.split(" ");
					int coordenadaX = 0;
					int coordenadaY = 0;
					
					// Realiza a conversão das coordenadas que vinheram String do arquivo txt para int
					try{
						coordenadaX = Integer.parseInt(coordenadas[0]);
						coordenadaY = Integer.parseInt(coordenadas[1]);
						
					} catch(Exception e){
						e.printStackTrace();
						System.out.print("Erro na conversão de coordenadas String para INT: " + e.getMessage());
						
					}
					this.vetVertice.add(new Vertice(id, coordenadaX, coordenadaY));
					id++;
				}
			}
			
			setNumMed(this.vetVertice.size());
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("Erro na leitura do arquivo: " + e.getMessage());
			
		}
	}
		
	//Lê um arquivo txt com as coordenadas e carrega as coordenadas para o vetor de vertices da PMediana
	public void distribuirMedianasIniciais(){
		double distancia, menorDistancia;
		Mediana refMelhor;
		boolean primeiroValor;
		
		for(Vertice vertice : this.vetVertice){
			menorDistancia = 0;
			refMelhor = null;
			primeiroValor = true;
			
			if(vertice.getMed()){
				vertice.setDistancia(0);
				vertice.setRefMelhor(vertice);
				
			}else {
				for(Vertice possivelMediana : vetVertice){
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
				this.vetMediana.add(refMelhor);
				
			}else {
				for(Mediana mediana : vetMediana){
					if(mediana.getVertice().getId() == vertice.getRefMelhor().getId()){
						refMelhor = mediana;
						refMelhor.addVertice(vertice);
					}					
				}				
			}
		}
		this.setNumMed(vetMediana.size());
	}
	
	//Função que "Gera a solução" esta função é responsavel para fazer com que a vertice detecte outra vertice mais proxima e se conecte a ela, tornando-a mediana.
	public void gerarSolucao(){
		avaliarSolucao();
		
		//System.out.println(this.medianaMaiorFitnessLocalMedio.getFitnessLocalMedio());
		
		ArrayList<Mediana> vetMedianaAux = (ArrayList<Mediana>) this.vetMediana.clone();
		
		//Vertice antigaMediana = this.medianaMaiorFitnessLocalMedio.getVertice();
		//ArrayList<Vertice> vertices = this.medianaMaiorFitnessLocalMedio.getVertices();
		
		Vertice antigaMediana = this.medianaMenorFitnessLocalMedio.getVertice();
		ArrayList<Vertice> vertices = this.medianaMenorFitnessLocalMedio.getVertices();
		
		for(Mediana mediana : vetMedianaAux){
			//System.out.println(mediana.getFitnessLocalMedio());
			if(mediana.getVertice().getId() == antigaMediana.getId()){
				this.vetMediana.remove(mediana);
				this.numMed = this.vetMediana.size();
			}
		}
		
		antigaMediana.escolheMediana(vetMediana);		
		
		for(Vertice vertice : vertices){
			vertice.escolheMediana(vetMediana);			
		}		
		avaliarSolucao();
	}
	
	//Função para pegar o fitness da PMediana
	public void avaliarSolucao(){
		this.medianaMenorFitnessLocalMedio = null;
		this.medianaMaiorFitnessLocalMedio = null;
		this.fitness = 0;
		
		for(Mediana mediana : this.vetMediana){
			this.fitness += mediana.getFitnessLocal();
			
			if(this.medianaMenorFitnessLocalMedio == null && this.medianaMaiorFitnessLocalMedio == null){
				this.medianaMenorFitnessLocalMedio = mediana;
				this.medianaMaiorFitnessLocalMedio = mediana;
				
			}else {
				if(mediana.getFitnessLocalMedio() < this.medianaMenorFitnessLocalMedio.getFitnessLocalMedio()){
					this.medianaMenorFitnessLocalMedio = mediana;
				}
				
				if(mediana.getFitnessLocalMedio() > this.medianaMaiorFitnessLocalMedio.getFitnessLocalMedio()){
					this.medianaMaiorFitnessLocalMedio = mediana;
					
				}					
			}
		}
	}
		
	//Função para limpar o vetor de vertice, queira ser gerado uma nova PMediana com coordenadas diferentes
	public void limparVetVertice(){
		this.vetVertice.clear();
	}
	
	//Função para limpar o vetor de medianas, caso queira utilizar novas medianas na PMediana
	public void limparVetMediana(){
		this.vetMediana.clear();
		this.fitness = 0;
		
		for(Vertice vertice : this.vetVertice){
			vertice.setMed(false);
			
		}
	}
	
	//Função que exibi as medianas e os vertices conectados a mediana, a exibição dos vertices conectados a mediana se da pelo metodo toString na classe Mediana
	public void exibirMedianas(){
		System.out.print("Quantidade de medianas: " +vetMediana.size() + "\n");
		for(Mediana mediana : vetMediana){
			System.out.print(mediana);
		}
	}
}
