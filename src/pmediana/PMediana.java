package pmediana;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PMediana {
	/** Declaração de variáveis
	 *  @fitness - Variável double que representa a potuação da PMediana
	 *  @numMed - Variável int que representa a quantidade de medianas da PMediana
	 *  @medDesejado - Variável int que representa a quantidade de medianas desejadas na PMediana
	 *  @vetMed - ArrayList Mediana que representa todas as medianas daquela PMediana
	 */
	private double fitness;
	private int numMedianas, numVertices;
	private ArrayList<Vertice> vertices;	
	private ArrayList<Mediana> medianas;

	public PMediana(Vertice[] vertices){
		/*System.out.println(vertices.length);*/
		
		ArrayList<Vertice> arrayListVertices = new ArrayList<Vertice>();
		arrayListVertices.addAll(Arrays.asList(vertices));
		
		this.vertices = arrayListVertices;
		this.numVertices = this.vertices.size();
		
		this.medianas = new ArrayList<Mediana>();
		this.numMedianas = 0;
		
		this.fitness = 0;
	}

	/*public void setVertices(ArrayList<Vertice> vertices){
		this.vertices = vertices;
	}*/

	public Vertice[] getVertices(){
		Vertice[] vertices = new Vertice[this.vertices.size()];
		return this.vertices.toArray(vertices);
	}

	/*public void setMedianas(ArrayList<Mediana> mediana){
		this.medianas = mediana;
	}*/

	public void addMediana(Mediana mediana){
		if(!medianas.contains(mediana)){
			mediana.setPMedianaPertencente(this);
			medianas.add(mediana);
			this.numMedianas++;
			this.fitness += mediana.getFitness();
		}
	}

	public void removerMediana(Mediana mediana){
		if(medianas.contains(mediana)){
			mediana.setPMedianaPertencente(null);
			
			medianas.remove(mediana);
			this.numMedianas--;
			this.fitness -= mediana.getFitness();
		}
	}

	public Mediana[] getMedianas(){	
		Mediana[] medianas = new Mediana[this.medianas.size()];
		return this.medianas.toArray(medianas);
	}

	public int getNumVertices(){
		return this.numVertices;
	}
	
	public int getNumMedianas(){
		return this.numMedianas;
	}

	public double getFitness(){
		return this.fitness;
	}

	//Função que exibi as medianas e os vertices conectados a mediana, a exibição dos vertices conectados a mediana se da pelo metodo toString na classe Mediana
	public void exibirMedianas(){
		/*System.out.print("Quantidade de medianas: " +medianas.size() + "\n");*/
		for(Mediana mediana : medianas){
			System.out.print(mediana);
		}
	}

	public void removerVerticeDaMedianaConectada(Vertice vertice){
		Mediana mediana = vertice.getRefMelhor();

		/*System.out.println("Vertice " + vertice.getId() + " excluida da mediana " + mediana.getVertice().getId());*/

		mediana.removerVertice(vertice);
		
		verificaConexoesMediana(mediana);
	}

	public void removerVerticeDaMedianaConectada(Vertice vertice, Mediana mediana){
		/*System.out.println("Vertice " + vertice.getId() + " excluida da mediana " + mediana.getVertice().getId());*/

		mediana.removerVertice(vertice);
		
		verificaConexoesMediana(mediana);
	}

	private void verificaConexoesMediana(Mediana mediana){
		Vertice verticeMediana = mediana.getVertice();

		if(mediana.getVertices().length == 1){
			mediana.removerVertice(verticeMediana);
			this.removerMediana(mediana);

			verticeMediana.setMed(false);
			verticeMediana.escolherMediana(this.medianas);
		}
	}

	private ArrayList<HashMap<Mediana, Vertice>> verificaInconsistencia(){
		ArrayList<HashMap<Mediana, Vertice>> inconsistencias = new ArrayList<HashMap<Mediana, Vertice>>();
		ArrayList<Mediana> medianas = new ArrayList<Mediana>();

		for(Vertice vertice : this.getVertices()){
			int vezes = 0;
			medianas.clear();
			
			for(Mediana mediana : this.getMedianas()){
				ArrayList<Vertice> arrayListVerticesMediana = new ArrayList<Vertice>();
				arrayListVerticesMediana.addAll(Arrays.asList(mediana.getVertices()));
				
				if(arrayListVerticesMediana.contains(vertice)){
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
	
	public void validarResultado(){
		ArrayList<HashMap<Mediana, Vertice>> inconsistencias = this.verificaInconsistencia();
		
		if(inconsistencias.size() > 0){
			System.out.println("Inconsistências encontradas! ");
			
			for(HashMap<Mediana, Vertice> inconsistencia : inconsistencias){
				System.out.println("Inconsistências encontradas! ");
				for(Mediana key : inconsistencia.keySet()){
					ArrayList<Mediana> arrayListMedianas = (ArrayList<Mediana>) Arrays.asList(this.getMedianas());
					ArrayList<Vertice> arrayListVertices = (ArrayList<Vertice>) Arrays.asList(key.getVertices());
					
					int indiceMediana = arrayListMedianas.indexOf(key);
					int indiceVertice = arrayListVertices.indexOf(inconsistencia.get(key));
					
					System.out.println(indiceMediana + "º Mediana!");
					System.out.println(indiceVertice + "º Vertice!");
				}
			}
			System.out.println("PAUSE PARA ANÁLISE!");
		}
		
		int numMedianas, numVertices = 0;
		double fitness = 0;
		
		numMedianas = this.medianas.size();
		
		for(Mediana mediana : medianas){
			numVertices += mediana.getVertices().length;
			fitness += mediana.calcularFitness();
		}
		
		String saida = 	"Teste unitário nas PMedianas com " + numMedianas + " medianas e " + numVertices + " vertices. \n" +
						"-------------------------------------------------------------------------\n" +
						"Nº vertices: " + numVertices + "\n" +
						"Nº medianas: " + numMedianas + "\n" +
						"Fitness total: " + fitness;
		
		System.out.println(saida + "\n");
		
	}
	
	public void atualizarFitness(double antigoFitness, double novoFitness){
		this.fitness -= antigoFitness;
		this.fitness += novoFitness;
	}

}
