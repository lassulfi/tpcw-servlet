
/**
 * C02T03
 * 
 * Classe de avaliação de dados
 * 
 * @author esoft35
 *
 */
public class Evaluation {

	private int id;
	private Customer customer;
	private Book book;
	private int rate;

	public Evaluation(int id, Customer customer, Book book, int rate) {
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

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getEvaluationData() {
		return customer.getId() + "," + book.getId() + "," + rate;
	}

}
