package Exercicio1Semana;

import java.util.Scanner;

public class Principal {
	/** Declares variables
	 *  @PM - variable PMediana
	 *  @vetVertices - ArrayList Vertices
	 */
	private static Scanner entrada = new Scanner(System.in);
	
	public static void main (String args[]){
		PMediana PM = new PMediana();

		//informarArquivo(PM);
		PM.carregarCoordenadas("D:\\Documents\\Trabalhos e Projetos\\Projetos Vinculados ao IFES\\Projeto AG PBIC\\Arquivos de Posicionamentos Aleatorios\\818.txt");
		informarQntMedianasDesejadas(PM);
		
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
	
	public static void informarArquivo(PMediana PM){
		PM.limparVetVertice();
		PM.limparVetMediana();
		System.out.println("Informe o arquivo de coordenadas (Ex.: \"C:/Arquivos de Programas/teste.txt\"): ");
		PM.carregarCoordenadas(entrada.nextLine());
		System.out.println("\n");
	}
	
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
}
