/* 
 * Customer.java - stores the important information for a single customer. 
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
import java.util.Date;

public class Customer implements Serializable {
    private static final long serialVersionUID = -7297414189618511748L;
    
    private int id;
    private String uname;
    private String passwd;
    private String fname;
    private String lname;
    private String phone;
    private String email;
    private Date since;
    private Date lastVisit;
    private Date login;
    private Date expiration;
    private double discount;
    private double balance;
    private double ytdPmt;
    private Date birthdate;
    private String data;
    private Address address;
    private Order mostRecentOrder;

    public Customer(int id, String uname, String passwd, String fname,
            String lname, String phone, String email, Date since,
            Date lastVisit, Date login, Date expiration, double discount,
            double balance, double ytdPmt, Date birthdate, String data,
            Address address) {
        this.id = id;
        this.uname = uname;
        this.passwd = passwd;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.email = email;
        this.since = since;
        this.lastVisit = lastVisit;
        this.login = login;
        this.expiration = expiration;
        this.discount = discount;
        this.balance = balance;
        this.ytdPmt = ytdPmt;
        this.birthdate = birthdate;
        this.data = data;
        this.address = address;
        mostRecentOrder = null;
    }

    public void setLogin(Date login) {
        this.login = login;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public int getId() {
        return id;
    }

    public String getPasswd() {
        return passwd;
    }

    public double getDiscount() {
        return discount;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getUname() {
        return uname;
    }

    public void logOrder(Order order) {
        mostRecentOrder = order;
    }
    
    public Order getMostRecentOrder() {
        return mostRecentOrder;
    }

    public Date getSince() {
        return since;
    }

    public Date getLastVisit() {
        return lastVisit;
    }

    public Date getLogin() {
        return login;
    }

    public Date getExpiration() {
        return expiration;
    }

    public double getBalance() {
        return balance;
    }

    public double getYtdPmt() {
        return ytdPmt;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getData() {
        return data;
    }
    
}
