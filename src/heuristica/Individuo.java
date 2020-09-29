package heuristica;

public class Individuo {
	
	private int[] cromossomos;
	private double fitness = -1;

	public Individuo(int[] cromossomos) {
		this.cromossomos = cromossomos;
	}

	public Individuo(int tamCromossomos) {
		int[] individuos;
		individuos = new int[tamCromossomos];
		
		for (int gene = 0; gene < tamCromossomos; gene++) {
			individuos[gene] = gene;
		}
		
		this.cromossomos = individuos;
	}

	public int[] getCromossomos() {
		return this.cromossomos;
	}

	public int getTamanhoCromossomos() {
		return this.cromossomos.length;
	}

	public void setGene(int index, int gene) {
		this.cromossomos[index] = gene;
	}

	public int getGene(int index) {
		return this.cromossomos[index];
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public double getFitness() {
		return this.fitness;
	}

	public boolean containsGene(int gene) {
		for (int i = 0; i < this.cromossomos.length; i++) {
			if (this.cromossomos[i] == gene) {
				return true;
			}
		}
		return false;
	}
}
