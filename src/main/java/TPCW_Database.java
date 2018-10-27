/* 
 * TPCW_Database.java - Facade for all the code involved with data
 *                      accesses. These
 *                      functions are called by many of the servlets.
 *
 ************************************************************************
 *
 * This is part of the the Java TPC-W distribution,
 * written by Harold Cain, Tim Heil, Milo Martin, Eric Weglarz, and Todd
 * Bezenek.  University of Wisconsin - Madison, Computer Sciences
 * Dept. and Dept. of Electrical and Computer Engineering, as a part of
 * Prof. Mikko Lipasti's Fall 1999 ECE 902 course.
 *
 * Copyright (C) 1999, 2000 by Harold Cain, Timothy Heil, Milo Martin, 
 *                             Eric Weglarz, Todd Bezenek.
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
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TPCW_Database {

    private interface Action {

        Object executeOn(Object sm);
    }

    private static class StateMachine {

        private final Object state;

        public StateMachine(final Object object) {
            this.state = object;
        }

        Object execute(Action action) {
            return action.executeOn(state);
        }

        void checkpoint() {

        }

        public Object getState() {
            return state;
        }

        private static StateMachine create(Object state) {
            return new StateMachine(state);
        }

    }

    private class TreplicaException extends RuntimeException {

    }
    private static Random random;
    private static StateMachine stateMachine;

    static {
        random = new Random();
        try {
            stateMachine = StateMachine.create(new Bookstore());

        } catch (TreplicaException e) {
            throw new RuntimeException(e);
        }
    }

    private static Bookstore getBookstore() {
        return (Bookstore) stateMachine.getState();
    }

    public static Customer getCustomer(String UNAME) {
        Bookstore bookstore = getBookstore();
        synchronized (bookstore) {
            return bookstore.getCustomer(UNAME);
        }
    }

    public static String[] getName(int c_id) {
        Bookstore bookstore = getBookstore();
        Customer customer = null;
        synchronized (bookstore) {
            customer = bookstore.getCustomer(c_id);
        }
        String name[] = new String[3];
        name[0] = customer.getFname();
        name[1] = customer.getLname();
        name[2] = customer.getUname();
        return name;
    }

    public static String getUserName(int C_ID) {
        Bookstore bookstore = getBookstore();
        synchronized (bookstore) {
            return bookstore.getCustomer(C_ID).getUname();
        }
    }

    public static String getPassword(String C_UNAME) {
        Bookstore bookstore = getBookstore();
        synchronized (bookstore) {
            return bookstore.getCustomer(C_UNAME).getPasswd();
        }
    }

    public static Order getMostRecentOrder(String c_uname) {
        Bookstore bookstore = getBookstore();
        synchronized (bookstore) {
            return bookstore.getCustomer(c_uname).getMostRecentOrder();
        }
    }

    public static Customer createNewCustomer(String fname, String lname,
            String street1, String street2, String city, String state,
            String zip, String countryName, String phone, String email,
            Date birthdate, String data) {
        double discount = (int) (Math.random() * 51);
        long now = System.currentTimeMillis();
        try {
            return (Customer) stateMachine.execute(new CreateCustomerAction(
                    fname, lname, street1, street2, city, state, zip,
                    countryName, phone, email, discount, birthdate, data, now));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void refreshSession(int cId) {
        try {
            stateMachine.execute(new RefreshCustomerSessionAction(cId,
                    System.currentTimeMillis()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Book getBook(int i_id) {
        Bookstore bookstore = getBookstore();
        synchronized (bookstore) {
            return bookstore.getBook(i_id);
        }
    }

    public static Book getABookAnyBook() {
        Bookstore bookstore = getBookstore();
        synchronized (bookstore) {
            return bookstore.getABookAnyBook(random);
        }
    }

    public static List<Book> doSubjectSearch(String search_key) {
        Bookstore bookstore = getBookstore();
        synchronized (bookstore) {
            return bookstore.getBooksBySubject(search_key);
        }
    }

    public static List<Book> doTitleSearch(String search_key) {
        Bookstore bookstore = getBookstore();
        synchronized (bookstore) {
            return bookstore.getBooksByTitle(search_key);
        }
    }

    public static List<Book> doAuthorSearch(String search_key) {
        Bookstore bookstore = getBookstore();
        synchronized (bookstore) {
            return bookstore.getBooksByAuthor(search_key);
        }
    }

    public static List<Book> getNewProducts(String subject) {
        Bookstore bookstore = getBookstore();
        synchronized (bookstore) {
            return bookstore.getNewBooks(subject);
        }
    }

    public static List<Book> getRelated(int i_id) {
        Bookstore bookstore = getBookstore();
        Book book = null;
        synchronized (bookstore) {
            book = bookstore.getBook(i_id);
        }
        ArrayList<Book> related = new ArrayList<>(5);
        related.add(book.getRelated1());
        related.add(book.getRelated2());
        related.add(book.getRelated3());
        related.add(book.getRelated4());
        related.add(book.getRelated5());
        return related;
    }

    public static void adminUpdate(int iId, double cost, String image,
            String thumbnail) {
        try {
            stateMachine.execute(new UpdateBookAction(iId, cost, image,
                    thumbnail, System.currentTimeMillis()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int createEmptyCart() {
        try {
            return ((Cart) stateMachine.execute(new CreateCartAction(
                    System.currentTimeMillis()))).getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Cart doCart(int SHOPPING_ID, Integer I_ID, List<Integer> ids,
            List<Integer> quantities) {
        try {
            Cart cart = (Cart) stateMachine.execute(new CartUpdateAction(
                    SHOPPING_ID, I_ID, ids, quantities,
                    System.currentTimeMillis()));
            if (cart.getLines().isEmpty()) {
                Book book = getBookstore().getABookAnyBook(random);
                cart = (Cart) stateMachine.execute(new CartUpdateAction(
                        SHOPPING_ID, book.getId(), new ArrayList<Integer>(),
                        new ArrayList<Integer>(), System.currentTimeMillis()));
            }
            return cart;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Cart getCart(int SHOPPING_ID) {
        Bookstore bookstore = getBookstore();
        synchronized (bookstore) {
            return bookstore.getCart(SHOPPING_ID);
        }
    }

    public static Order doBuyConfirm(int shopping_id, int customer_id,
            String cc_type, long cc_number, String cc_name, Date cc_expiry,
            String shipping) {
        long now = System.currentTimeMillis();
        try {
            return (Order) stateMachine.execute(new ConfirmBuyAction(
                    customer_id, shopping_id, randomComment(),
                    cc_type, cc_number, cc_name, cc_expiry, shipping,
                    randomShippingDate(now), -1, now));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Order doBuyConfirm(int shopping_id, int customer_id,
            String cc_type, long cc_number, String cc_name, Date cc_expiry,
            String shipping, String street_1, String street_2, String city,
            String state, String zip, String country) {
        Address address = getBookstore().alwaysGetAddress(street_1, street_2,
                city, state, zip, country);
        long now = System.currentTimeMillis();
        try {
            return (Order) stateMachine.execute(new ConfirmBuyAction(
                    customer_id, shopping_id, randomComment(),
                    cc_type, cc_number, cc_name, cc_expiry, shipping,
                    randomShippingDate(now), address.getId(), now));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String randomComment() {
        return TPCW_Util.getRandomString(random, 20, 100);
    }

    private static Date randomShippingDate(long now) {
        return new Date(now + 86400000 /* a day */ * (random.nextInt(7) + 1));
    }

    public static boolean populate(int items, int customers, int addresses,
            int authors, int orders) {
        try {
            return (Boolean) stateMachine.execute(new PopulateAction(random.nextLong(),
                    System.currentTimeMillis(), items, customers, addresses,
                    authors, orders));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkpoint() {
        try {
            stateMachine.checkpoint();
        } catch (TreplicaException e) {
            throw new RuntimeException(e);
        }
    }

    protected static abstract class BookstoreAction implements Action,
            Serializable {

        @Override
        public Object executeOn(Object sm) {
            return executeOnBookstore((Bookstore) sm);
        }

        public abstract Object executeOnBookstore(Bookstore bookstore);
    }

    protected static class CreateCustomerAction extends BookstoreAction {

        private static final long serialVersionUID = 6039962163348790677L;

        String fname;
        String lname;
        String street1;
        String street2;
        String city;
        String state;
        String zip;
        String countryName;
        String phone;
        String email;
        double discount;
        Date birthdate;
        String data;
        long now;

        public CreateCustomerAction(String fname, String lname, String street1,
                String street2, String city, String state, String zip,
                String countryName, String phone, String email,
                double discount, Date birthdate, String data, long now) {
            this.fname = fname;
            this.lname = lname;
            this.street1 = street1;
            this.street2 = street2;
            this.city = city;
            this.state = state;
            this.zip = zip;
            this.countryName = countryName;
            this.phone = phone;
            this.email = email;
            this.discount = discount;
            this.birthdate = birthdate;
            this.data = data;
            this.now = now;
        }

        @Override
        public Object executeOnBookstore(Bookstore bookstore) {
            return bookstore.createCustomer(fname, lname, street1, street2,
                    city, state, zip, countryName, phone, email, discount,
                    birthdate, data, now);
        }
    }

    protected static class RefreshCustomerSessionAction extends BookstoreAction {

        private static final long serialVersionUID = -5391031909452321478L;

        int cId;
        long now;

        public RefreshCustomerSessionAction(int id, long now) {
            cId = id;
            this.now = now;
        }

        @Override
        public Object executeOnBookstore(Bookstore bookstore) {
            bookstore.refreshCustomerSession(cId, now);
            return null;
        }
    }

    protected static class UpdateBookAction extends BookstoreAction {

        private static final long serialVersionUID = -745697943594643776L;

        int bId;
        double cost;
        String image;
        String thumbnail;
        long now;

        public UpdateBookAction(int id, double cost, String image,
                String thumbnail, long now) {
            bId = id;
            this.cost = cost;
            this.image = image;
            this.thumbnail = thumbnail;
            this.now = now;
        }

        @Override
        public Object executeOnBookstore(Bookstore bookstore) {
            bookstore.updateBook(bId, cost, image, thumbnail, now);
            return null;
        }
    }

    protected static class CreateCartAction extends BookstoreAction {

        private static final long serialVersionUID = 8255648428785854052L;

        long now;

        public CreateCartAction(long now) {
            this.now = now;
        }

        @Override
        public Object executeOnBookstore(Bookstore bookstore) {
            return bookstore.createCart(now);
        }
    }

    protected static class CartUpdateAction extends BookstoreAction {

        private static final long serialVersionUID = -6062032194650262105L;

        int cId;
        Integer bId;
        List<Integer> bIds;
        List<Integer> quantities;
        long now;

        public CartUpdateAction(int id, Integer id2, List<Integer> ids,
                List<Integer> quantities, long now) {
            cId = id;
            bId = id2;
            bIds = ids;
            this.quantities = quantities;
            this.now = now;
        }

        @Override
        public Object executeOnBookstore(Bookstore bookstore) {
            return bookstore.cartUpdate(cId, bId, bIds, quantities, now);
        }
    }

    protected static class ConfirmBuyAction extends BookstoreAction {

        private static final long serialVersionUID = -6180290851118139002L;

        int customerId;
        int cartId;
        String comment;
        String ccType;
        long ccNumber;
        String ccName;
        Date ccExpiry;
        String shipping;
        Date shippingDate;
        int addressId;
        long now;

        public ConfirmBuyAction(int customerId, int cartId,
                String comment, String ccType, long ccNumber,
                String ccName, Date ccExpiry, String shipping,
                Date shippingDate, int addressId, long now) {
            this.customerId = customerId;
            this.cartId = cartId;
            this.comment = comment;
            this.ccType = ccType;
            this.ccNumber = ccNumber;
            this.ccName = ccName;
            this.ccExpiry = ccExpiry;
            this.shipping = shipping;
            this.shippingDate = shippingDate;
            this.addressId = addressId;
            this.now = now;
        }

        @Override
        public Object executeOnBookstore(Bookstore bookstore) {
            return bookstore.confirmBuy(customerId, cartId, comment, ccType,
                    ccNumber, ccName, ccExpiry, shipping, shippingDate,
                    addressId, now);
        }
    }

    protected static class PopulateAction extends BookstoreAction {

        private static final long serialVersionUID = -5240430799502573886L;

        long seed;
        long now;
        int items;
        int customers;
        int addresses;
        int authors;
        int orders;

        public PopulateAction(long seed, long now, int items, int customers,
                int addresses, int authors, int orders) {
            this.seed = seed;
            this.now = now;
            this.items = items;
            this.customers = customers;
            this.addresses = addresses;
            this.authors = authors;
            this.orders = orders;
        }

        @Override
        public Object executeOnBookstore(Bookstore bookstore) {
            return bookstore.populate(seed, now, items, customers, addresses,
                    authors, orders);
        }
    }

}
