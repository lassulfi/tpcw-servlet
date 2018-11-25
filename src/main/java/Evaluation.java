import java.util.Locale;

/**
 * C02T03
 * 
 * Classe de avaliação de dados
 * 
 * @author esoft35
 *
 */
public class Evaluation implements Comparable<Evaluation> {

	private int id;
	private Customer customer;
	private Book book;
	private double rate;

	public Evaluation(int id, Customer customer, Book book, double rate) {
		this.id = id;
		this.customer = customer;
		this.book = book;
		this.rate = rate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getEvaluationData() {
		return customer.getId() + "," + book.getId() + "," + String.format(Locale.ROOT, "%.1f", rate);
	}

	@Override
	public int compareTo(Evaluation eval) {
		if (this.customer.getId() > eval.customer.getId()) {
			return 1;
		} else if (this.customer.getId() < eval.customer.getId()) {
			return -1;
		} else {
			return 0;
		}
	}

}
