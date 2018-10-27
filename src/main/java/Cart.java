/*
 * Cart.java - Class stores the necessary components of a shopping cart.
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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

public class Cart implements Serializable {
    private static final long serialVersionUID = -4194553499937996531L;
    
    private int id;
    private Date time;
    private HashMap<Integer, CartLine> linesByBookId;
    private double aggregateCost;
    private int aggregateQuantity;

    public Cart(int id, Date time) {
        this.id = id;
        this.time = time;
        clear();
    }
    
    public int getId() {
        return id;
    }
    
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void clear() {
        linesByBookId = new HashMap<Integer, CartLine>();
        aggregateCost = 0;
        aggregateQuantity = 0;
    }
    
    public Collection<CartLine> getLines() {
        return linesByBookId.values();
    }
    
    public void increaseLine(Book book, int quantity) {
        CartLine line = linesByBookId.get(book.getId());
        if (line == null) {
            line = new CartLine(0, book);
            linesByBookId.put(book.getId(), line);
        }
        aggregateCost += book.getCost() * quantity;
        aggregateQuantity += quantity;
        line.setQty(line.getQty() + quantity);
        if (quantity == 0) {
            linesByBookId.remove(book.getId());
        }
    }
    
    public void changeLine(Book book, int quantity) {
        CartLine line = linesByBookId.get(book.getId());
        if (line == null) {
            line = new CartLine(0, book);
            linesByBookId.put(book.getId(), line);
        }
        aggregateCost += book.getCost() * (quantity - line.getQty());
        aggregateQuantity += (quantity - line.getQty());
        line.setQty(quantity);
        if (quantity == 0) {
            linesByBookId.remove(book.getId());
        }
    }
    
    public double subTotal(double discount) {
        return aggregateCost * ((100 - discount) * 0.01);
    }
    
    public double tax(double discount) {
        return subTotal(discount) * 0.0825;
    }
    
    public double shipCost() {
        return 3.00 + (1.00 * aggregateQuantity);
    }
    
    public double total(double discount) {
        return subTotal(discount) + shipCost() + tax(discount);
    }

}
