/* 
 * Bookstore.java - holds all the data and operations of the bookstore.
 *
 ************************************************************************
 *
 * This is part of the the Java TPC-W distribution,
 * written by Harold Cain, Tim Heil, Milo Martin, Eric Weglarz, and Todd
 * Bezenek.  University of Wisconsin - Madison, Computer Sciences
 * Dept. and Dept. of Electrical and Computer Engineering, as a part of
 * Prof. Mikko Lipasti's Fall 1999 ECE 902 course.
 *
 * Copyright © 2008 Gustavo Maciel Dias Vieira
 *
 * This source code is distributed "as is" in the hope that it will be
 * useful.  It comes with no warranty, and no author or distributor
 * accepts any responsibility for the consequences of its use.
 *
 * Everyone is granted permission to copy, modify and redistribute
 * this code under the following conditions:
 *
 * This code is distributed for non-commercial use only.
 * Please contact the maintainer for restrictions applying to 
 * commercial use of these tools.
 *
 * Permission is granted to anyone to make or distribute copies
 * of this code, either as received or modified, in any
 * medium, provided that all copyright notices, permission and
 * nonwarranty notices are preserved, and that the distributor
 * grants the recipient permission for further redistribution as
 * permitted by this document.
 *
 * Permission is granted to distribute this code in compiled
 * or executable form under the same conditions that apply for
 * source code, provided that either:
 *
 * A. it is accompanied by the corresponding machine-readable
 *    source code,
 * B. it is accompanied by a written offer, with no time limit,
 *    to give anyone a machine-readable copy of the corresponding
 *    source code in return for reimbursement of the cost of
 *    distribution.  This written offer must permit verbatim
 *    duplication by anyone, or
 * C. it is distributed by someone who received only the
 *    executable form, and is accompanied by a copy of the
 *    written offer of source code that they received concurrently.
 *
 * In other words, you are welcome to use, share and improve this codes.
 * You are forbidden to forbid anyone else to use, share and improve what
 * you give them.
 *
 ************************************************************************/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class Bookstore implements Serializable {
    private static final long serialVersionUID = -3099048826035606338L;

    private boolean populated;
    private ArrayList<Country> countryById;
    private HashMap<String, Country> countryByName;
    private ArrayList<Address> addressById;
    private HashMap<Address, Address> addressByAll;
    private ArrayList<Customer> customersById;
    private HashMap<String, Customer> customersByUsername;
    private ArrayList<Author> authorsById;
    private ArrayList<Book> booksById;
    private ArrayList<Cart> cartsById;
    private ArrayList<Order> ordersById;
    private LinkedList<Order> ordersByCreation;

    public Bookstore() {
        populated = false;
        countryById = new ArrayList<Country>();
        countryByName = new HashMap<String, Country>();
        addressById = new ArrayList<Address>();
        addressByAll = new HashMap<Address, Address>();
        customersById = new ArrayList<Customer>();
        customersByUsername = new HashMap<String, Customer>();
        authorsById = new ArrayList<Author>();
        booksById = new ArrayList<Book>();
        cartsById = new ArrayList<Cart>();
        ordersById = new ArrayList<Order>();
        ordersByCreation = new LinkedList<Order>();
    }
    
    public boolean isPopulated() {
        return populated;
    }

    private Country alwaysGetCountry(String name) {
        Country country = countryByName.get(name);
        if (country == null) {
            country = createCountry(name, "", 0);
        }
        return country;
    }

    private Country getACountryAnyCountry(Random random) {
        return countryById.get(random.nextInt(countryById.size()));
    }

    private Country createCountry(String name, String currency, double exchange) {
        int id = countryById.size();
        Country country = new Country(id, name, currency, exchange);
        countryById.add(country);
        countryByName.put(name, country);
        return country;
    }

    public Address alwaysGetAddress(String street1, String street2,
            String city, String state, String zip, String countryName) {
        Country country = alwaysGetCountry(countryName);
        Address key = new Address(0, street1, street2, city, state, zip, country);
        Address address = addressByAll.get(key);
        if (address == null) {
            address = createAddress(street1, street2, city, state, zip,
                    country);
        }
        return address;
    }

    private Address getAnAddressAnyAddress(Random random) {
        return addressById.get(random.nextInt(addressById.size()));
    }

    private Address createAddress(String street1, String street2,
            String city, String state, String zip, Country country) {
        int id = addressById.size();
        Address address = new Address(id, street1, street2, city, state, zip,
                country);
        addressById.add(address);
        addressByAll.put(address, address);
        return address;
    }

    public Customer getCustomer(int cId) {
        return customersById.get(cId);
    }

    public Customer getCustomer(String username) {
        return customersByUsername.get(username);
    }
    
    private Customer getACustomerAnyCustomer(Random random) {
        return customersById.get(random.nextInt(customersById.size()));
    }

    public Customer createCustomer(String fname, String lname, String street1,
            String street2, String city, String state, String zip,
            String countryName, String phone, String email, double discount,
            Date birthdate, String data, long now) {
        Address address = alwaysGetAddress(street1, street2, city, state, zip,
                countryName);
        return createCustomer(fname, lname, address, phone, email,
                new Date(now), new Date(now), new Date(now),
                new Date(now + 7200000 /* 2 hours */), discount, birthdate,
                data);
    }

    private Customer createCustomer(String fname, String lname, Address address,
            String phone, String email, Date since, Date lastVisit,
            Date login, Date expiration, double discount, Date birthdate,
            String data) {
        int id = customersById.size();
        String uname = TPCW_Util.DigSyl(id, 0);
        Customer customer = new Customer(id, uname, uname.toLowerCase(), fname,
                lname, phone, email, since, lastVisit, login, expiration,
                discount, 0, 0, birthdate, data, address);
        customersById.add(customer);
        customersByUsername.put(uname, customer);
        return customer;
    }
    
    public void refreshCustomerSession(int cId, long now) {
        Customer customer = getCustomer(cId);
        if (customer != null) {
            customer.setLogin(new Date(now));
            customer.setExpiration(new Date(now + 7200000 /* 2 hours */));
        }
    }
    
    private Author getAnAuthorAnyAuthor(Random random) {
        return authorsById.get(random.nextInt(authorsById.size()));
    }
    
    private Author createAuthor(String fname, String mname, String lname,
            Date birthdate, String bio) {
        Author author = new Author(fname, mname, lname, birthdate, bio);
        authorsById.add(author);
        return author;
    }

    public Book getBook(int bId) {
        return booksById.get(bId);
    }

    public Book getABookAnyBook(Random random) {
        return booksById.get(random.nextInt(booksById.size()));
    }

    public List<Book> getBooksBySubject(String subject) {
        ArrayList<Book> books = new ArrayList<Book>();
        for (Book book : booksById) {
            if (subject.equals(book.getSubject())) {
                books.add(book);
                if (books.size() > 50) {
                    break;
                }
            }
        }
        Collections.sort(books, new Comparator<Book>() {
            public int compare(Book a, Book b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });
        return books;
    }

    public List<Book> getBooksByTitle(String title) {
        Pattern regex = Pattern.compile("^" + title);
        ArrayList<Book> books = new ArrayList<Book>();
        for (Book book : booksById) {
            if (regex.matcher(book.getTitle()).matches()) {
                books.add(book);
                if (books.size() > 50) {
                    break;
                }
            }
        }
        Collections.sort(books, new Comparator<Book>() {
            public int compare(Book a, Book b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });
        return books;
    }

    public List<Book> getBooksByAuthor(String author) {
        Pattern regex = Pattern.compile("^" + author);
        ArrayList<Book> books = new ArrayList<Book>();
        for (Book book : booksById) {
            if (regex.matcher(book.getAuthor().getLname()).matches()) {
                books.add(book);
                if (books.size() > 50) {
                    break;
                }
            }
        }
        Collections.sort(books, new Comparator<Book>() {
            public int compare(Book a, Book b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });
        return books;
    }

    public List<Book> getNewBooks(String subject) {
        ArrayList<Book> books = new ArrayList<Book>();
        for (Book book : booksById) {
            if (subject.equals(book.getSubject())) {
                books.add(book);
            }
        }
        Collections.sort(books, new Comparator<Book>() {
            public int compare(Book a, Book b) {
                int result = b.getPubDate().compareTo(a.getPubDate());
                if (result == 0) {
                    result = a.getTitle().compareTo(b.getTitle());
                }
                return result;
            }
        });
        return books.subList(0, books.size() >= 50 ? 50 : books.size());
    }
    

    protected static class Counter {
        public Book book;
        public int count;
    }


    private Book createBook(String title, Date pubDate, String publisher,
            String subject, String desc, String thumbnail,
            String image, double srp, double cost, Date avail, String isbn,
            int page, String backing, String dimensions, Author author,
            int stock) {
        int id = booksById.size();
        Book book = new Book(id, title, pubDate, publisher, subject, desc,
                thumbnail, image, srp, cost, avail, isbn, page, backing,
                dimensions, author, stock);
        booksById.add(book);
        return book;
    }

    public void updateBook(int bId, double cost, String image,
            String thumbnail, long now) {
        Book book = getBook(bId);
        book.setCost(cost);
        book.setImage(image);
        book.setThumbnail(thumbnail);
        book.setPubDate(new Date(now));
        updateRelatedBooks(book);
    }

    /** Método de retorno da lista dos Best Sellers com tamanho fixo */
    public List<Book> getBestSellers(String subject){
    	return getBestSellers(subject, 50);
    }
    
    /** Método de retorno da lista dos Best Sellers */
    public List<Book> getBestSellers(String subject, int number){

        Iterator<Order> i = ordersByCreation.iterator();
        HashMap<Integer, Counter> counters = new HashMap<Integer, Counter>();
        i = ordersByCreation.iterator();
        while (i.hasNext()) {
            Order order = i.next();
                for (OrderLine orderList : order.getLines()) {
                    Book Obook = orderList.getBook();
                    if (subject.equals(Obook.getSubject())) {
                        Counter counter = counters.get(Obook.getId());
                        if (counter == null) {
                            counter = new Counter();
                            counter.book = Obook;
                            counter.count = 0;
                            counters.put(Obook.getId(), counter);
                        }
                        counter.count += orderList.getQty();
                    }
                }
        }
        Counter[] sorted = counters.values().toArray(new Counter[] {});
        Arrays.sort(sorted, new Comparator<Counter>() {
            public int compare(Counter a, Counter b) {
                if (b.count > a.count) {
                    return 1;
                }
                return b.count < a.count ? -1 : 0;
            }
        });
        ArrayList<Book> related = new ArrayList<Book>();
        if (number < 1) {
        	number = 50;
        }
        for (int j = 0; j < number && j < sorted.length; j++) {
            related.add(sorted[j].book);
        }
        return related;
    }
    
    
    /**
     * For all the clients that bought this book in the last 10000 orders, what
     * are the five most sold books except this one.
     */
    //TODO: Refactor in order to search 50 best sellers
    private void updateRelatedBooks(Book targetBook) {
        HashSet<Integer> clientIds = new HashSet<Integer>();
        int j = 0;
        Iterator<Order> i = ordersByCreation.iterator();
        while (i.hasNext() && j <= 10000) {
            Order order = i.next();
            for (OrderLine line : order.getLines()) {
                Book book = line.getBook();
                if (targetBook.getId() == book.getId()) {
                    clientIds.add(order.getCustomer().getId());
                    break;
                }
            }
            j++;
        }
        HashMap<Integer, Counter> counters = new HashMap<Integer, Counter>();
        i = ordersByCreation.iterator();
        while (i.hasNext()) {
            Order order = i.next();
            if (clientIds.contains(order.getCustomer().getId())) {
                for (OrderLine line : order.getLines()) {
                    Book book = line.getBook();
                    if (targetBook.getId() != book.getId()) {
                        Counter counter = counters.get(book.getId());
                        if (counter == null) {
                            counter = new Counter();
                            counter.book = book;
                            counter.count = 0;
                            counters.put(book.getId(), counter);
                        }
                        counter.count += line.getQty();
                    }
                }
            }
        }
        Counter[] sorted = counters.values().toArray(new Counter[] {});
        Arrays.sort(sorted, new Comparator<Counter>() {
            public int compare(Counter a, Counter b) {
                if (b.count > a.count) {
                    return 1;
                }
                return b.count < a.count ? -1 : 0;
            }
        });
        Book[] related = new Book[] { targetBook, targetBook, targetBook,
                targetBook, targetBook };
        for (j = 0; j < 5 && j < sorted.length; j++) {
            related[j] = sorted[j].book;
        }
        targetBook.setRelated1(related[0]);
        targetBook.setRelated2(related[1]);
        targetBook.setRelated3(related[2]);
        targetBook.setRelated4(related[3]);
        targetBook.setRelated5(related[4]);
    }

    public Cart getCart(int id) {
        return cartsById.get(id);
    }

    public Cart createCart(long now) {
        int id = cartsById.size();
        Cart cart = new Cart(id, new Date(now));
        cartsById.add(cart);
        return cart;
    }

    public Cart cartUpdate(int cId, Integer bId, List<Integer> bIds,
            List<Integer> quantities, long now) {
        Cart cart = getCart(cId);

        if (bId != null) {
            cart.increaseLine(getBook(bId), 1);
        }

        for (int i = 0; i < bIds.size(); i++) {
            cart.changeLine(booksById.get(bIds.get(i)), quantities.get(i));
        }

        cart.setTime(new Date(now));

        return cart;
    }

    public Order confirmBuy(int customerId, int cartId, String comment,
            String ccType, long ccNumber, String ccName, Date ccExpiry,
            String shipping, Date shippingDate, int addressId, long now) {
        Customer customer = getCustomer(customerId);
        Cart cart = getCart(cartId);
        Address shippingAddress = customer.getAddress();
        if (addressId != -1) {
            shippingAddress = addressById.get(addressId);
        }
        for (CartLine cartLine : cart.getLines()) {
            Book book = cartLine.getBook();
            book.addStock(-cartLine.getQty());
            if (book.getStock() < 10) {
                book.addStock(21);
            }
        }
        CCTransaction ccTransact = new CCTransaction(ccType, ccNumber, ccName,
                ccExpiry, "", cart.total(customer.getDiscount()),
                new Date(now), shippingAddress.getCountry());
        return createOrder(customer, new Date(now), cart, comment, shipping,
                shippingDate, "Pending", customer.getAddress(),
                shippingAddress, ccTransact);
    }

    private Order createOrder(Customer customer, Date date, Cart cart,
            String comment, String shipType, Date shipDate,
            String status, Address billingAddress, Address shippingAddress,
            CCTransaction cc) {
        int id = ordersById.size();
        Order order = new Order(id, customer, date, cart, comment, shipType,
                shipDate, status, billingAddress, shippingAddress, cc);
        ordersById.add(order);
        ordersByCreation.addFirst(order);
        customer.logOrder(order);
        cart.clear();
        return order;
    }
    
    public boolean populate(long seed, long now, int items, int customers,
            int addresses, int authors, int orders) {
        if (populated) {
            return false;
        }
        System.out.println("Beginning TPCW population.");
        Random rand = new Random(seed);
        populateCountries();
        populateAddresses(addresses, rand);
        populateCustomers(customers, rand, now);
        populateAuthorTable(authors, rand);
        populateBooks(items, rand);
        populateOrders(orders, rand, now);
        populated = true;
        System.out.println("Finished TPCW population.");
        return true;
    }
    
    private void populateCountries() {
        String[] countries = {"United States","United Kingdom","Canada",
                              "Germany","France","Japan","Netherlands",
                              "Italy","Switzerland", "Australia","Algeria",
                              "Argentina","Armenia","Austria","Azerbaijan",
                              "Bahamas","Bahrain","Bangla Desh","Barbados",
                              "Belarus","Belgium","Bermuda", "Bolivia",
                              "Botswana","Brazil","Bulgaria","Cayman Islands",
                              "Chad","Chile", "China","Christmas Island",
                              "Colombia","Croatia","Cuba","Cyprus",
                              "Czech Republic","Denmark","Dominican Republic",
                              "Eastern Caribbean","Ecuador", "Egypt",
                              "El Salvador","Estonia","Ethiopia",
                              "Falkland Island","Faroe Island", "Fiji", 
                              "Finland","Gabon","Gibraltar","Greece","Guam",
                              "Hong Kong","Hungary", "Iceland","India",
                              "Indonesia","Iran","Iraq","Ireland","Israel",
                              "Jamaica", "Jordan","Kazakhstan","Kuwait",
                              "Lebanon","Luxembourg","Malaysia","Mexico", 
                              "Mauritius", "New Zealand","Norway","Pakistan",
                              "Philippines","Poland","Portugal","Romania", 
                              "Russia","Saudi Arabia","Singapore","Slovakia",
                              "South Africa","South Korea", "Spain","Sudan",
                              "Sweden","Taiwan","Thailand","Trinidad",
                              "Turkey","Venezuela", "Zambia"};

        double[] exchanges = { 1, .625461, 1.46712, 1.86125, 6.24238, 121.907,
                               2.09715, 1842.64, 1.51645, 1.54208, 65.3851,
                               0.998, 540.92, 13.0949, 3977, 1, .3757, 
                               48.65, 2, 248000, 38.3892, 1, 5.74, 4.7304,
                               1.71, 1846, .8282, 627.1999, 494.2, 8.278,
                               1.5391, 1677, 7.3044, 23, .543, 36.0127, 
                               7.0707, 15.8, 2.7, 9600, 3.33771, 8.7,
                               14.9912, 7.7, .6255, 7.124, 1.9724, 5.65822,
                               627.1999, .6255, 309.214, 1, 7.75473, 237.23, 
                               74.147, 42.75, 8100, 3000, .3083, .749481,
                               4.12, 37.4, 0.708, 150, .3062, 1502, 38.3892,
                               3.8, 9.6287, 25.245, 1.87539, 7.83101,
                               52, 37.8501, 3.9525, 190.788, 15180.2, 
                               24.43, 3.7501, 1.72929, 43.9642, 6.25845, 
                               1190.15, 158.34, 5.282, 8.54477, 32.77, 37.1414,
                               6.1764, 401500, 596, 2447.7 };

        String[] currencies = { "Dollars","Pounds","Dollars","Deutsche Marks",
                                "Francs","Yen","Guilders","Lira","Francs",
                                "Dollars","Dinars","Pesos", "Dram",
                                "Schillings","Manat","Dollars","Dinar","Taka",
                                "Dollars","Rouble","Francs","Dollars", 
                                "Boliviano", "Pula", "Real", "Lev","Dollars",
                                "Franc","Pesos","Yuan Renmimbi","Dollars",
                                "Pesos","Kuna","Pesos","Pounds","Koruna",
                                "Kroner","Pesos","Dollars","Sucre","Pounds",
                                "Colon","Kroon","Birr","Pound","Krone",
                                "Dollars","Markka","Franc","Pound","Drachmas",
                                "Dollars","Dollars","Forint","Krona","Rupees",
                                "Rupiah","Rial","Dinar","Punt","Shekels",
                                "Dollars","Dinar","Tenge","Dinar","Pounds",
                                "Francs","Ringgit","Pesos","Rupees","Dollars",
                                "Kroner","Rupees","Pesos","Zloty","Escudo",
                                "Leu","Rubles","Riyal","Dollars","Koruna",
                                "Rand","Won","Pesetas","Dinar","Krona",
                                "Dollars","Baht","Dollars","Lira","Bolivar", 
                                "Kwacha"};
        
        System.out.print("Creating " + countries.length + " countries...");
        
        for(int i = 0; i < countries.length; i++) {
            createCountry(countries[i], currencies[i], exchanges[i]);
        }
        
        System.out.println(" Done");
    }

    private void populateAddresses(int number, Random rand) {
        System.out.print("Creating " + number + " addresses...");
        
        for (int i = 0; i < number; i++) {
            if (i % 10000 == 0) System.out.print(".");
            Country country = getACountryAnyCountry(rand);
            createAddress(
                    TPCW_Util.getRandomString(rand, 15,40),
                    TPCW_Util.getRandomString(rand, 15,40), 
                    TPCW_Util.getRandomString(rand, 4,30),
                    TPCW_Util.getRandomString(rand, 2,20),
                    TPCW_Util.getRandomString(rand, 5,10),
                    country);
        }

        System.out.println(" Done");
    }
    
    private void populateCustomers(int number, Random rand, long now) {
        System.out.print("Creating " + number + " customers...");
        
        for (int i = 0; i < number; i++) {
            if (i % 10000 == 0) System.out.print(".");
            Address address = getAnAddressAnyAddress(rand);
            long since = now - TPCW_Util.getRandomInt(rand, 1, 730) * 86400000 /* a day */;
            long lastLogin = since + TPCW_Util.getRandomInt(rand, 0, 60) * 86400000 /* a day */;
            createCustomer(
                    TPCW_Util.getRandomString(rand, 8, 15),
                    TPCW_Util.getRandomString(rand, 8, 15),
                    address, 
                    TPCW_Util.getRandomString(rand, 9, 16),
                    TPCW_Util.getRandomString(rand, 2, 9) + "@" + 
                      TPCW_Util.getRandomString(rand, 2, 9) + ".com",
                    new Date(since),
                    new Date(lastLogin),
                    new Date(now),
                    new Date(now + 7200000 /* 2 hours */),
                    rand.nextInt(51),
                    TPCW_Util.getRandomBirthdate(rand),
                    TPCW_Util.getRandomString(rand, 100, 500));
        }

        System.out.println(" Done");
    }
    
    private void populateAuthorTable(int number, Random rand) {
        System.out.print("Creating " + number + " authors...");
        
        for (int i = 0; i < number; i++) {
            if (i % 10000 == 0) System.out.print(".");
            createAuthor(
                    TPCW_Util.getRandomString(rand, 3, 20),
                    TPCW_Util.getRandomString(rand, 1, 20),
                    TPCW_Util.getRandomString(rand, 1, 20),
                    TPCW_Util.getRandomBirthdate(rand),
                    TPCW_Util.getRandomString(rand, 125, 500));
        }

        System.out.println(" Done");
    }


    private void populateBooks(int number, Random rand) {
        String[] SUBJECTS = { "ARTS", "BIOGRAPHIES", "BUSINESS", "CHILDREN",
                              "COMPUTERS", "COOKING", "HEALTH", "HISTORY",
                              "HOME", "HUMOR", "LITERATURE", "MYSTERY",
                              "NON-FICTION", "PARENTING", "POLITICS",
                              "REFERENCE", "RELIGION", "ROMANCE", 
                              "SELF-HELP", "SCIENCE-NATURE", "SCIENCE_FICTION",
                              "SPORTS", "YOUTH", "TRAVEL"};
        String[] BACKINGS = { "HARDBACK", "PAPERBACK", "USED", "AUDIO",
                              "LIMITED-EDITION"};
        
        System.out.print("Creating " + number + " books...");
        
        for (int i = 0; i < number; i++) {
            if (i % 10000 == 0) System.out.print(".");
            Author author = getAnAuthorAnyAuthor(rand);
            Date pubdate = TPCW_Util.getRandomPublishdate(rand);
            double srp = TPCW_Util.getRandomInt(rand, 100, 99999) / 100.0;
            double cost = srp * TPCW_Util.getRandomInt(rand, 50, 100) / 100.0;
            createBook(
                    TPCW_Util.getRandomString(rand, 14, 60),
                    pubdate,
                    TPCW_Util.getRandomString(rand, 14, 60),
                    SUBJECTS[rand.nextInt(SUBJECTS.length)],
                    TPCW_Util.getRandomString(rand, 100, 500),
                    "img" + i % 100 + "/thumb_" + i + ".gif",
                    "img" + i % 100 + "/image_" + i + ".gif",
                    srp,
                    cost,
                    new Date(pubdate.getTime() + 
                            TPCW_Util.getRandomInt(rand, 1, 30) * 86400000 /* a day */),
                    TPCW_Util.getRandomString(rand, 13, 13),
                    TPCW_Util.getRandomInt(rand, 20, 9999),
                    BACKINGS[rand.nextInt(BACKINGS.length)],
                    (TPCW_Util.getRandomInt(rand, 1, 9999) / 100.0) + "x" +
                            (TPCW_Util.getRandomInt(rand, 1, 9999) / 100.0) + "x" +
                            (TPCW_Util.getRandomInt(rand, 1, 9999) / 100.0),
                    author,
                    TPCW_Util.getRandomInt(rand, 10, 30));
        }
        
        for (int i = 0; i < number; i++) {
            Book book = booksById.get(i);
            HashSet<Book> related = new HashSet<Book>();
            while (related.size() < 5) {
                Book relatedBook = getABookAnyBook(rand);
                if (relatedBook.getId() != i) {
                    related.add(relatedBook);
                }
            }
            Book[] relatedArray = new Book[] { booksById.get(TPCW_Util.getRandomInt(rand, 0, number - 1)), 
                booksById.get(TPCW_Util.getRandomInt(rand, 0, number - 1)), 
                booksById.get(TPCW_Util.getRandomInt(rand, 0, number - 1)), 
                booksById.get(TPCW_Util.getRandomInt(rand, 0, number - 1)), 
                booksById.get(TPCW_Util.getRandomInt(rand, 0, number - 1)) };
            relatedArray = related.toArray(relatedArray);
            book.setRelated1(relatedArray[0]);
            book.setRelated2(relatedArray[1]);
            book.setRelated3(relatedArray[2]);
            book.setRelated4(relatedArray[3]);
            book.setRelated5(relatedArray[4]);
        }
            
        System.out.println(" Done");
    }

    private void populateOrders(int number, Random rand, long now) {
        String[] credit_cards = {"VISA", "MASTERCARD", "DISCOVER", 
                                  "AMEX", "DINERS"};
        String[] ship_types = {"AIR", "UPS", "FEDEX", "SHIP", "COURIER",
                               "MAIL"};
        String[] status_types = {"PROCESSING", "SHIPPED", "PENDING", 
                                 "DENIED"};

        System.out.print("Creating " + number + " orders...");
        
        for (int i = 0; i < number; i++) {
            if (i % 10000 == 0) System.out.print(".");
            int nBooks = TPCW_Util.getRandomInt(rand, 1, 5);
            Cart cart = new Cart(-1, null);
            String comment = TPCW_Util.getRandomString(rand, 20, 100);
            for (int j = 0; j < nBooks; j++) {
                Book book = getABookAnyBook(rand);
                int quantity = TPCW_Util.getRandomInt(rand, 1, 300);
                cart.changeLine(book, quantity);
            }

            Customer customer = getACustomerAnyCustomer(rand);
            CCTransaction ccTransact = new CCTransaction(
                    credit_cards[rand.nextInt(credit_cards.length)],
                    TPCW_Util.getRandomLong(rand, 1000000000000000L, 9999999999999999L),
                    TPCW_Util.getRandomString(rand, 14, 30),
                    new Date(now + TPCW_Util.getRandomInt(rand, 10, 730) * 86400000 /* a day */),
                    TPCW_Util.getRandomString(rand, 15, 15),
                    cart.total(customer.getDiscount()),
                    new Date(now),
                    getACountryAnyCountry(rand));
            long orderDate = now - TPCW_Util.getRandomInt(rand, 53, 60) * 86400000 /* a day */;
            long shipDate = orderDate + TPCW_Util.getRandomInt(rand, 0, 7) * 86400000 /* a day */;
            createOrder(
                    customer, 
                    new Date(orderDate),
                    cart, comment,
                    ship_types[rand.nextInt(ship_types.length)],
                    new Date(shipDate),
                    status_types[rand.nextInt(status_types.length)],
                    getAnAddressAnyAddress(rand),
                    getAnAddressAnyAddress(rand),
                    ccTransact);
        }
        
        System.out.println(" Done");
    }

}
