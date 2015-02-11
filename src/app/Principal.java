package app;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import pmediana.Vertice;
import solucao.SolucaoFM;

public class Principal {
	/*private static String[] classesSolucoes = {"SolucaoFM", "SolucaoNC"};*/
	/*private static ArrayList<Solucao> solucoes = new ArrayList<Solucao>();*/
	private static ArrayList<Problema> problemas = new ArrayList<Problema>();

	/** Declares variables
	 *  @PM - variable PMediana
	 *  @vetVertices - ArrayList Vertices
	 */

	public static void main (String args[]){
		String dir = Paths.get("Coordenadas/").toAbsolutePath().toString();
		/*File[] arquivosCoordenadas = new File(dir).listFiles();*/
		File arquivoCoordenadas = new File(dir + "/324.txt"); /*818.txt, 324.txt*/
		int[] medianasDesejadas = {108, 50, 20, 10, 5};
		
		ArrayList<Vertice> vertices = carregarArquivoVertices(arquivoCoordenadas);

		Problema problema = new Problema(vertices, medianasDesejadas);
		problema.solucionarProblema(new SolucaoFM("Maior"));

		System.out.print("\n");
		problema.exibirResultados();

		/*for(File arquivo : arquivosCoordenadas){
			if(arquivo.getName().equalsIgnoreCase("818.txt")){
				ArrayList<Vertice> vertices = carregarArquivoVertices(arquivo);
				int[] medianasDesejadas = {272, 150, 100, 50, 20, 10, 5};

				Problema problema = new Problema(vertices, medianasDesejadas);
				problema.solucionarProblema(new SolucaoFM("Menor"));

				problema.exibirResultados();

				Principal.problemas.add(problema);
			}
		}*/

		/*
		for(String solucao : classesSolucoes){
			try {
				Solucao objSolucao = (Solucao)Class.forName(solucao).newInstance();
				objSolucao.start();

				solucoes.add(objSolucao);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		 */
		/*Solucao objSolucao = new SolucaoFM();

		int[] medianasDesejadas1 = {108, 50, 20, 10, 5};
		objSolucao.setMedianasDesejadas(324, medianasDesejadas1);

		int[] medianasDesejadas2 = {272, 150, 100, 50, 20, 10, 5};
		objSolucao.setMedianasDesejadas(818, medianasDesejadas2);

		int[] medianasDesejadas3 = {10, 5};
		objSolucao.setMedianasDesejadas(12, medianasDesejadas3);

		objSolucao.setVariacao("Menor");

		objSolucao.start();

		System.out.println("Foram encontrado " + objSolucao.getResultado().size() + " resultados");

		for(int i = 1; i <= objSolucao.getResultado().size(); i++){
			System.out.println("Foram encontrados no " + i + "º resultado " + objSolucao.getResultado().get(i - 1).size() + " resultados");

			for(int j = 1; j <= objSolucao.getResultado().get(i - 1).size(); j++){
				System.out.println("Fitness: " + objSolucao.getResultado().get(i - 1).get(j - 1).getFitness());
				System.out.println("Número de medianas: " + objSolucao.getResultado().get(i - 1).get(j - 1).getNumMed());
			}
		}*/
	}

	private static ArrayList<Vertice> carregarArquivoVertices(File arquivo){
		ArrayList<Vertice> vertices = new ArrayList<Vertice>();
		BufferedReader buffRead = null;

		/*System.out.println(arquivo.getName());*/
		vertices.clear();

		try {
			buffRead = new BufferedReader(new FileReader(arquivo)); // Realiza o Buffer do arquivo de coordenadas informado logo acima

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.print("Erro na localização do arquivo: " + e.getMessage());
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
					vertices.add(new Vertice(id, coordenadaX, coordenadaY));
					id++;
				}				
			}
			buffRead.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("Erro na leitura do arquivo: " + e.getMessage());
		}
		return vertices;
	}

	private static void gerarRelatorio(){

	}
}
