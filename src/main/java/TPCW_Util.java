/* 
 * TPCW_Util.java - Some random utility functions needed by the servlets.
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

import java.util.*;

public class TPCW_Util {
    
    public static String getRandomString(Random rand, int min, int max) {
	StringBuilder newstring = new StringBuilder();
	final char[] chars = {'a','b','c','d','e','f','g','h','i','j','k',
			      'l','m','n','o','p','q','r','s','t','u','v',
			      'w','x','y','z','A','B','C','D','E','F','G',
			      'H','I','J','K','L','M','N','O','P','Q','R',
			      'S','T','U','V','W','X','Y','Z','!','@','#',
			      '$','%','^','&','*','(',')','_','-','=','+',
			      '{','}','[',']','|',':',';',',','.','?','/',
			      '~',' '}; //79 characters
	int strlen = getRandomInt(rand, min, max);
	for (int i = 0; i < strlen; i++) {
	    newstring.append(chars[rand.nextInt(chars.length)]);
	}
	return newstring.toString();
    }

    public static int getRandomInt(Random rand, int lower, int upper) {
        return rand.nextInt(upper - lower + 1) + lower;
    }
    
    public static long getRandomLong(Random rand, long lower, long upper) {
        return (long) (rand.nextDouble() * (upper - lower + 1) + lower);
    }
    
    public static Date getRandomBirthdate(Random rand) {
        return new GregorianCalendar(
                TPCW_Util.getRandomInt(rand, 1880, 2000),
                TPCW_Util.getRandomInt(rand, 0, 11),
                TPCW_Util.getRandomInt(rand, 1, 30)).getTime();
    }

    public static Date getRandomPublishdate(Random rand) {
        return new GregorianCalendar(
                TPCW_Util.getRandomInt(rand, 1930, 2000),
                TPCW_Util.getRandomInt(rand, 0, 11),
                TPCW_Util.getRandomInt(rand, 1, 30)).getTime();
    }

    public static String DigSyl(int d, int n) {
        StringBuilder resultString = new StringBuilder();
        String digits = Integer.toString(d);

        if (n > digits.length()) {
            int padding = n - digits.length();
            for (int i = 0; i < padding; i++)
                resultString = resultString.append("BA");
        }

        for (int i = 0; i < digits.length(); i++) {
            if (digits.charAt(i) == '0')
                resultString = resultString.append("BA");
            else if (digits.charAt(i) == '1')
                resultString = resultString.append("OG");
            else if (digits.charAt(i) == '2')
                resultString = resultString.append("AL");
            else if (digits.charAt(i) == '3')
                resultString = resultString.append("RI");
            else if (digits.charAt(i) == '4')
                resultString = resultString.append("RE");
            else if (digits.charAt(i) == '5')
                resultString = resultString.append("SE");
            else if (digits.charAt(i) == '6')
                resultString = resultString.append("AT");
            else if (digits.charAt(i) == '7')
                resultString = resultString.append("UL");
            else if (digits.charAt(i) == '8')
                resultString = resultString.append("IN");
            else if (digits.charAt(i) == '9')
                resultString = resultString.append("NG");
        }

        return resultString.toString();
    }
    
}
