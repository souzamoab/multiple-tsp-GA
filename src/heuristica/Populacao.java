package heuristica;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Populacao {
	private Individuo populacao[];
	private double populacaoFitness = -1;

	public Populacao(int tamanhoPopulacao) {
		this.populacao = new Individuo[tamanhoPopulacao];
	}
	
	public Populacao(int tamanhoPopulacao, int tamanhoCromossomos) {
		this.populacao = new Individuo[tamanhoPopulacao];

		for (int individualCount = 0; individualCount < tamanhoPopulacao; individualCount++) {
			Individuo individuo = new Individuo(tamanhoCromossomos);
			this.populacao[individualCount] = individuo;
		}
	}

	public Individuo[] getIndividuos() {
		return this.populacao;
	}

	public Individuo getFittest(int index) {
		Arrays.sort(this.populacao, new Comparator<Individuo>() {
			@Override
			public int compare(Individuo o1, Individuo o2) {
				if (o1.getFitness() > o2.getFitness()) {
					return -1;
				} else if (o1.getFitness() < o2.getFitness()) {
					return 1;
				}
				return 0;
			}
		});

		return this.populacao[index];
	}

	public void setPopulacaoFitness(double fitness) {
		this.populacaoFitness = fitness;
	}
	
	public double getPopulacaoFitness() {
		return this.populacaoFitness;
	}

	public int size() {
		return this.populacao.length;
	}

	public Individuo setIndividuo(int index, Individuo individuo) {
		return populacao[index] = individuo;
	}

	public Individuo getIndividuo(int index) {
		return populacao[index];
	}

	public void shuffle() {
		Random rnd = new Random();
		for (int i = populacao.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			Individuo a = populacao[index];
			populacao[index] = populacao[i];
			populacao[i] = a;
		}
	}

}