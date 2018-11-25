/*
 * Book.java - Class used to store all of the data associated with a single
 *             book. 
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

public class Book implements Serializable {
    private static final long serialVersionUID = 6505830715531808617L;

    private int id;
    private String title;
    private Date pubDate;
    private String publisher;
    private String subject;
    private String desc;
    private Book related1;
    private Book related2;
    private Book related3;
    private Book related4;
    private Book related5;
    private String thumbnail;
    private String image;
    private double srp;
    private double cost;
    private Date avail;
    private String isbn;
    private int page;
    private String backing;
    private String dimensions;
    private Author author;
    private int stock;
    
    /**
     * C02T03 - Média das avaliações 
     * @author esoft35
     */
    private float avgRate;
    
    public Book(int id, String title, Date pubDate, String publisher,
            String subject, String desc, String thumbnail,
            String image, double srp, double cost, Date avail, String isbn,
            int page, String backing, String dimensions, Author author,
            int stock) {
        this.id = id;
        this.title = title;
        this.pubDate = pubDate;
        this.publisher = publisher;
        this.subject = subject;
        this.desc = desc;
        this.related1 = null;
        this.related2 = null;
        this.related3 = null;
        this.related4 = null;
        this.related5 = null;
        this.thumbnail = thumbnail;
        this.image = image;
        this.srp = srp;
        this.cost = cost;
        this.avail = avail;
        this.isbn = isbn;
        this.page = page;
        this.backing = backing;
        this.dimensions = dimensions;
        this.author = author;
        this.stock = stock;
        //C02T03 - Adequação do construtor
        this.avgRate = 0.0f;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Author getAuthor() {
        return author;
    }

    public double getSrp() {
        return srp;
    }

    public double getCost() {
        return cost;
    }

    public String getDesc() {
        return desc;
    }

    public int getPage() {
        return page;
    }

    public String getBacking() {
        return backing;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getId() {
        return id;
    }

    public String getDimensions() {
        return dimensions;
    }

    public String getSubject() {
        return subject;
    }

    public Date getAvail() {
        return avail;
    }

    public Book getRelated1() {
        return related1;
    }

    public Book getRelated2() {
        return related2;
    }

    public Book getRelated3() {
        return related3;
    }

    public Book getRelated4() {
        return related4;
    }

    public Book getRelated5() {
        return related5;
    }

    public void setRelated1(Book related1) {
        this.related1 = related1;
    }

    public void setRelated2(Book related2) {
        this.related2 = related2;
    }

    public void setRelated3(Book related3) {
        this.related3 = related3;
    }

    public void setRelated4(Book related4) {
        this.related4 = related4;
    }

    public void setRelated5(Book related5) {
        this.related5 = related5;
    }

    public int getStock() {
        return stock;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public void addStock(int amount) {
        stock += amount;
    }
    
    /**
     * C02T03 - Recupera a média das avalições   
     * @return 
     * @author esoft35
     */
    public float getAvgRate() {
		return avgRate;
	}

    /**
     * C02T03 - Define a média das avalições 
     * @param avgRate
     * @author esoft35
     */
	public void setAvgRate(float avgRate) {
		this.avgRate = avgRate;
	}

	@Override
    public boolean equals(Object o) {
        if (o instanceof Book) {
            Book book = (Book) o;
            return  id == book.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
    
}



