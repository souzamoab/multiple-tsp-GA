package heuristica;

import cidade.Cidade;

/**
 * The main Evaluation class for the TSP. It's pretty simple -- given an
 * Individual (ie, a chromosome) and a list of canonical cities, calculate the
 * total distance required to travel to the cities in the specified order. The
 * result returned by getDistance() is used by GeneticAlgorithm.calcFitness.
 * 
 * @author bkanber
 *
 */

public class Rota {
	private Cidade rota[];
	private double distancia = 0;

	public Rota(Individuo individuo, Cidade cidades[]) {
		//Cromossomos do individuo
		int cromossomos[] = individuo.getCromossomos();

		//Criando rota
		this.rota = new Cidade[cidades.length];
		for (int geneIndex = 0; geneIndex < cromossomos.length; geneIndex++) {
			this.rota[geneIndex] = cidades[cromossomos[geneIndex]];
		}
		//this.rota[cromossomos.length-1] = cidades[cromossomos[0]];
	}

	public double getDistancia() {
		if (this.distancia > 0) {
			return this.distancia;
		}

		//Calculando distância da rota com retorno a cidade inicial
		double distanciaTotal = 0;
		for (int i = 0; i + 1 < this.rota.length; i++) {
			distanciaTotal += this.rota[i].calcularDist(this.rota[i + 1]);
		}

		distanciaTotal += this.rota[this.rota.length - 1].calcularDist(this.rota[0]);
		this.distancia = distanciaTotal;

		return distanciaTotal;
	}

	public Cidade[] getRota() {
		return rota;
	}

	public void setRota(Cidade[] rota) {
		this.rota = rota;
	}
	
	public Cidade getCidade(int index) {
		return this.rota[index];
	}
	
	public void addCidade(int index, Cidade cidade) {
		this.rota[index] = cidade;
	}
	
}