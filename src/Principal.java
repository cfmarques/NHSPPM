import java.util.ArrayList;

import pmediana.Mediana;
import solucoes.*;

public class Principal {
	private static String[] classesSolucoes = {"SolucaoFM"/*, "SolucaoNC"*/};
	private static ArrayList<Solucao> solucoes = new ArrayList<Solucao>();
	
	/** Declares variables
	 *  @PM - variable PMediana
	 *  @vetVertices - ArrayList Vertices
	 */
	
	
	
	public static void main (String args[]){
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
		Solucao objSolucao = new SolucaoFM();
		
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
		}
		
		/*
		
		PMediana PM = new PMediana();

		//informarArquivo(PM);
		PM.carregarCoordenadas("D:\\Documents\\Trabalhos e Projetos\\Projetos Vinculados ao IFES\\Projeto AG PBIC\\Arquivos de Posicionamentos Aleatorios\\818.txt");
		//informarQntMedianasDesejadas(PM);
		
		PM.distribuirMedianasIniciais();
		
		System.out.println("N�mero de medianas: " + PM.getNumMed() + "\t" + "N�mero de medianas desejadas: " + PM.getMedDesejado());
				
		System.out.println("\n\n\nGerando solu��o aleat�ria... Aguarde!\n");
		
		System.out.print("\n\n\n\n\n");
		
		while(PM.getNumMed() != PM.getMedDesejado()){
			PM.gerarSolucao();
			
			System.out.println("N�mero de medianas: " + PM.getNumMed() + "\t" + "N�mero de medianas desejadas: " + PM.getMedDesejado());
			System.out.println("Solu��o aleat�ria gerada! FITNESS da P-Mediana: " + PM.getFitness());
			
			
			if(PM.getNumMed() == 5){
				System.out.println("\n\n\nGerando solu��o aleat�ria... Aguarde!\n");
				
				PM.exibirMedianas();
				
				System.out.println("Solu��o aleat�ria gerada! FITNESS da P-Mediana: " + PM.getFitness());
			}			
		}
	}
	
	public static void gerarRelatorio(){
		
	}
	
	public static void informarArquivo(){
		System.out.println("Informe o arquivo de coordenadas (Ex.: \"C:/Arquivos de Programas/teste.txt\"): ");
		entrada.nextLine();
	}
	
	/*
	public static void informarQntMedianasDesejadas(PMediana PM){
		boolean condNumMedDesejada = true;

		while(condNumMedDesejada){
			PM.limparVetMediana();
			System.out.print("Informe a quantidade de medianas desejada: ");
			int numMedDesejadas = entrada.nextInt();
			entrada.nextLine();
			
			if(numMedDesejadas <= PM.getVetVertice().size()){
				PM.setMedDesejado(numMedDesejadas);
				condNumMedDesejada = false;
			}else {
				System.out.println(	"Valor de medianas inv�lidas, por favor informe um valor v�lido! "
						+			"\nN�mero de vertices: " + PM.getVetVertice().size() +"\tN�mero m�ximo de medianas permitidas: " + PM.getVetVertice().size());
			}
			
			System.out.println("\n");
		}
	}
	*/
	}
}
