package app;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import pmediana.Vertice;
import solucao.Solucao;
import distribuicaoMedianasIniciais.DistribuidorDeMedianasIniciais;

public class Principal {
	private static String[] solucoes = {"solucao.SolucaoMenorFitnessMedio", "solucao.SolucaoMaiorFitnessMedio",
		"solucao.SolucaoMenorNumeroConexoes", "solucao.SolucaoMaiorNumeroConexoes"};
	private static String[] VariacoesSolucoes = {"menorFitness", "maiorFitness"};
	private static String[] distribuicoesIniciais = {"distribuicaoMedianasIniciais.VizinhoMaisProximoSequencial", "distribuicaoMedianasIniciais.VizinhoMaisProximoAleatorio"};
	private static int[][] medianasDesejadas = {{108, 50, 20, 10, 5},{272, 150, 100, 50, 20, 10, 5}};
	private static int posicaoColunaXLS = 3;
	private static Map<String, String[]> medianasDoMelhorResultadoAleatorio = new HashMap<String, String[]>();

	/** Declares variables
	 *  @PM - variable PMediana
	 *  @vetVertices - ArrayList Vertices
	 */

	public static void main (String args[]) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		/* 
		 * Pega os nomes dos arquivos de coordenadas que estão na pasta "coordenadas/"
		 */
		String dirArquivosCorredenadas = Paths.get("Coordenadas/").toAbsolutePath().toString();
		File[] arquivosCoordenadas = new File(dirArquivosCorredenadas).listFiles();
		
		/* 
		 * Para cada arquivo de coordenadas irá realizar os calculos para solucionar o problema da PMediana em diversos cenários 
		 * (Distribuições de medianas iniciais, soluções e variações de soluções) 
		 */
		for(int i = 0; i < arquivosCoordenadas.length; i++){		
			for(String distribuicaoInicial : distribuicoesIniciais){
				DistribuidorDeMedianasIniciais distribuidorDeMedianasIniciaisUtilizado =  (DistribuidorDeMedianasIniciais) Class.forName(distribuicaoInicial).newInstance();
								
				if(distribuicaoInicial.equalsIgnoreCase("distribuicaoMedianasIniciais.VizinhoMaisProximoAleatorio")){
					exeuctarSolucoes(arquivosCoordenadas[i], medianasDesejadas[i], distribuidorDeMedianasIniciaisUtilizado, 10);
					
				}else {
					exeuctarSolucoes(arquivosCoordenadas[i], medianasDesejadas[i], distribuidorDeMedianasIniciaisUtilizado, 1);
				}
			}
		}
		
		salvarMelhoresResultados();
	}

	private static void exeuctarSolucoes(File arquivoCoordenada, int[] medianasDesejadas, 
			DistribuidorDeMedianasIniciais distribuidorDeMedianasIniciaisUtilizado, int qntVezes) {
		
		for(String solucao : solucoes){
			for(int i = 0; i < qntVezes; i++){
				
				if(solucao.equalsIgnoreCase("solucao.SolucaoMenorNumeroConexoes") ||
						solucao.equalsIgnoreCase("solucao.SolucaoMaiorNumeroConexoes")){

					for(String variacao : VariacoesSolucoes){
						exeuctarSolucao(arquivoCoordenada, medianasDesejadas, distribuidorDeMedianasIniciaisUtilizado, solucao, variacao);
					}
				}else {
					exeuctarSolucao(arquivoCoordenada, medianasDesejadas, distribuidorDeMedianasIniciaisUtilizado, solucao, "");
				}
			}
		}
	}
	
	private static void exeuctarSolucao(File arquivosCoordenadas, int[] medianasDesejadas, 
			DistribuidorDeMedianasIniciais distribuidorDeMedianasIniciaisUtilizado, String solucao, String variacao) {

		/* 
		 * Carrega as vertices do arquivo de coordenadas.
		 */
		ArrayList<Vertice> vertices = carregarArquivoVertices(arquivosCoordenadas);		
		Problema problema = new Problema(vertices, medianasDesejadas);

		try {
			@SuppressWarnings("unchecked")
			Class<Solucao> clazz = (Class<Solucao>) Class.forName(solucao);
			Solucao solucaoAplicada = null;
			Constructor<Solucao> constructor = null;

			if(variacao != null && !variacao.equalsIgnoreCase("")){
				constructor = clazz.getConstructor(String.class);
				solucaoAplicada = constructor.newInstance(variacao);
			}else {
				constructor = clazz.getConstructor();
				solucaoAplicada = constructor.newInstance();
			}

			problema.solucionarProblema(solucaoAplicada, distribuidorDeMedianasIniciaisUtilizado);
			
			if(distribuidorDeMedianasIniciaisUtilizado.getClass().getName().equalsIgnoreCase("distribuicaoMedianasIniciais.VizinhoMaisProximoAleatorio")){
				for(Resultado resultado : problema.getResultados()){
					String dirResultadosTxt = Paths.get("Resultados TXT/").toAbsolutePath().toString();
					String nomeArquivoResultadoTxt = solucaoAplicada.getAbreviacao() + " " + resultado.getNumVertices() + "x" + resultado.getNumMedianas();
					File resultadoTxt = new File(dirResultadosTxt, nomeArquivoResultadoTxt + ".txt");
					PrintWriter saida = new PrintWriter(new FileWriter(resultadoTxt, true));
					
					saida.println(resultado);
					
					saida.close();
					
					if(medianasDoMelhorResultadoAleatorio.get(nomeArquivoResultadoTxt) == null){
						String[] resultados = {resultado.exibirMedianas(), "" + resultado.getFitness()};
						medianasDoMelhorResultadoAleatorio.put(nomeArquivoResultadoTxt, resultados);
						
					}else {
						String[] resultados = medianasDoMelhorResultadoAleatorio.get(nomeArquivoResultadoTxt);
						
						if(Double.parseDouble(resultados[1]) > resultado.getFitness()){
							resultados[0] = resultado.exibirMedianas();
							resultados[1] = "" + resultado.getFitness();
							
							medianasDoMelhorResultadoAleatorio.put(nomeArquivoResultadoTxt, resultados);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void salvarMelhoresResultados() {
		Set<String> arquivosComResultados = medianasDoMelhorResultadoAleatorio.keySet();
		
		for(String arquivoComResultado : arquivosComResultados){
			try {
				String[] resultados = medianasDoMelhorResultadoAleatorio.get(arquivoComResultado);
				
				String dirResultadosTxt = Paths.get("Resultados TXT/").toAbsolutePath().toString();
				String nomeArquivoResultadoTxt = arquivoComResultado;
				File resultadoTxt = new File(dirResultadosTxt, nomeArquivoResultadoTxt + ".txt");
				PrintWriter saida = new PrintWriter(new FileWriter(resultadoTxt, true));
				
				String fitness = resultados[1].replace(".", ",");
				saida.print("\n" + resultados[0] + "\n" + fitness);
				
				saida.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static ArrayList<Vertice> carregarArquivoVertices(File arquivo){
		ArrayList<Vertice> vertices = new ArrayList<Vertice>();
		BufferedReader buffRead = null;

		vertices.clear();

		try {
			buffRead = new BufferedReader(new FileReader(arquivo)); // Realiza o Buffer do arquivo de coordenadas informado logo acima

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.print("Erro na localização do arquivo: " + e.getMessage());
		}

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
}
