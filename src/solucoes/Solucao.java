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

	public abstract ArrayList<PMediana> gerarSolucao(ArrayList<Vertice> conjuntoVertices, int[] medianas);
	public abstract void distribuirMedianas(PMediana PM);
	public abstract void setVariacao(String variacao);

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
}
