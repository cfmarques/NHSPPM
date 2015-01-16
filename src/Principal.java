

import java.util.ArrayList;
import java.util.Scanner;

import pmediana.PMediana;
import solucoes.Solucao;

public class Principal {
	private static Scanner entrada = new Scanner(System.in);
	private static String[] classesSolucoes = {"SolucaoFM", "SolucaoNC"};
	private static ArrayList<Solucao> solucoes = new ArrayList<Solucao>();
	
	/** Declares variables
	 *  @PM - variable PMediana
	 *  @vetVertices - ArrayList Vertices
	 */
	
	public static void main (String args[]){
		for(String solucao : classesSolucoes){
			try {
				Solucao objSolucao = (Solucao)Class.forName(solucao).newInstance();
				objSolucao.gerarSolucao();
				
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
		
		PMediana PM = new PMediana();

		//informarArquivo(PM);
		PM.carregarCoordenadas("D:\\Documents\\Trabalhos e Projetos\\Projetos Vinculados ao IFES\\Projeto AG PBIC\\Arquivos de Posicionamentos Aleatorios\\818.txt");
		//informarQntMedianasDesejadas(PM);
		
		PM.distribuirMedianasIniciais();
		
		System.out.println("Número de medianas: " + PM.getNumMed() + "\t" + "Número de medianas desejadas: " + PM.getMedDesejado());
				
		System.out.println("\n\n\nGerando solução aleatória... Aguarde!\n");
		
		System.out.print("\n\n\n\n\n");
		
		while(PM.getNumMed() != PM.getMedDesejado()){
			PM.gerarSolucao();
			
			System.out.println("Número de medianas: " + PM.getNumMed() + "\t" + "Número de medianas desejadas: " + PM.getMedDesejado());
			System.out.println("Solução aleatória gerada! FITNESS da P-Mediana: " + PM.getFitness());
			
			
			if(PM.getNumMed() == 5){
				System.out.println("\n\n\nGerando solução aleatória... Aguarde!\n");
				
				PM.exibirMedianas();
				
				System.out.println("Solução aleatória gerada! FITNESS da P-Mediana: " + PM.getFitness());
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
				System.out.println(	"Valor de medianas inválidas, por favor informe um valor válido! "
						+			"\nNúmero de vertices: " + PM.getVetVertice().size() +"\tNúmero máximo de medianas permitidas: " + PM.getVetVertice().size());
			}
			
			System.out.println("\n");
		}
	}
	*/
}
