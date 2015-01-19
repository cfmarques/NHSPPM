package solucoes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import pmediana.PMediana;
import pmediana.Vertice;

public abstract class Solucao {
	protected ArrayList<ArrayList<PMediana>> resultados = new ArrayList<ArrayList<PMediana>>();
	//protected Map<Integer, HashMap<Integer, PMediana>> = new HashMap<Integer, HashMap<Integer, PMediana>>;
	protected ArrayList<ArrayList<Vertice>> conjuntosVertices = new ArrayList<ArrayList<Vertice>>();
	protected Map<Integer, Integer[]> medianasDesejadas = new HashMap<Integer, Integer[]>();
	protected File[] arquivosCoordenadas;

	public abstract ArrayList<PMediana> gerarSolucao(ArrayList<Vertice> conjuntoVertices, int[] medianas);
	public abstract void distribuirMedianas(PMediana PM);
	public abstract void setVariacao(String variacao);

	public final void setArquivosCoordenadas(){
		String dir = Paths.get("Coordenadas").toAbsolutePath().toString();
		this.arquivosCoordenadas = new File(dir).listFiles();
	}

	public final void setArquivosCoordenadas(File[] arquivos){
		this.arquivosCoordenadas = arquivos;
	}

	public final void setArquivosCoordenadas(File arquivo){
		this.arquivosCoordenadas = new File[1];
		this.arquivosCoordenadas[0] = arquivo;
	}

	public final void setMedianasDesejadas(int numVertices, int[] medianasDesejadasTemp){
		numVertices = (Integer)numVertices;
		Integer[] medianasDesejadas = new Integer[medianasDesejadasTemp.length];

		int i = 0;
		for(int medianaDesejada : medianasDesejadasTemp){
			medianasDesejadas[i] = Integer.valueOf(medianaDesejada);
			i++;
		}

		this.medianasDesejadas.put(numVertices, medianasDesejadas);

	}
	
	public final ArrayList<ArrayList<PMediana>> getResultado(){
		return this.resultados;
	}

	public final void start(){
		carregarConjuntosVertices();
		
		System.out.println("Iniciando a geração de solução com " + conjuntosVertices.size() + " conjuntos de vertices");

		for(ArrayList<Vertice> conjuntoVertices : conjuntosVertices){
			System.out.println("Iniciando a geração de solução com o conjunto de vertices de " + conjuntoVertices.size() + " vertices");
			
			Integer[] medianasDesejadasTemp = this.medianasDesejadas.get(conjuntoVertices.size());
			Arrays.sort(medianasDesejadasTemp);

			if(medianasDesejadasTemp != null){
				int[] medianasDesejadas = new int[medianasDesejadasTemp.length];

				int i = 0;
				for(Integer medianaDesejadaTemp : medianasDesejadasTemp){
					medianasDesejadas[i] = medianaDesejadaTemp.intValue();
					i++;
				}

				resultados.add(gerarSolucao(conjuntoVertices, medianasDesejadas));
			}
		}
	}

	public final void carregarConjuntosVertices(){
		ArrayList<Vertice> vertices = new ArrayList<Vertice>();
		BufferedReader buffRead = null;

		if(this.arquivosCoordenadas == null){
			System.out.println("Carregando arquivos de coordenadas!");
			setArquivosCoordenadas();
		}

		for(File arquivo : this.arquivosCoordenadas){
			System.out.println(arquivo.getName());
			vertices.clear();

			// Realiza o Buffer do arquivo de coordenadas informado logo acima
			try {
				buffRead = new BufferedReader(new FileReader(arquivo));

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
				System.out.println("Adicionando vertice com " + vertices.size() + " vertices");
				this.conjuntosVertices.add((ArrayList<Vertice>)vertices.clone());

			} catch (IOException e) {
				e.printStackTrace();
				System.out.print("Erro na leitura do arquivo: " + e.getMessage());
			}
		}
	}
}
