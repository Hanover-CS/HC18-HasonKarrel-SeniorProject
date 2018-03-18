package com.example.jasonk20.morsecodemessenger;

import android.content.SharedPreferences;
import android.location.LocationListener;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jasonk20 on 10/31/17.
 */


public class Translation {

    private HashMap<String,ArrayList<String>>letters = new HashMap<String, ArrayList<String>>();

    private ArrayList<String> a = new ArrayList<String>();
    private ArrayList<String> b = new ArrayList<String>();
    private ArrayList<String> c = new ArrayList<String>();
    private ArrayList<String> d = new ArrayList<String>();
    private ArrayList<String> e = new ArrayList<String>();
    private ArrayList<String> f = new ArrayList<String>();
    private ArrayList<String> g = new ArrayList<String>();
    private ArrayList<String> h = new ArrayList<String>();
    private ArrayList<String> i = new ArrayList<String>();
    private ArrayList<String> j = new ArrayList<String>();
    private ArrayList<String> k = new ArrayList<String>();
    private ArrayList<String> l = new ArrayList<String>();
    private ArrayList<String> m = new ArrayList<String>();
    private ArrayList<String> n = new ArrayList<String>();
    private ArrayList<String> o = new ArrayList<String>();
    private ArrayList<String> p = new ArrayList<String>();
    private ArrayList<String> q = new ArrayList<String>();
    private ArrayList<String> r = new ArrayList<String>();
    private ArrayList<String> s = new ArrayList<String>();
    private ArrayList<String> t = new ArrayList<String>();
    private ArrayList<String> u = new ArrayList<String>();
    private ArrayList<String> v = new ArrayList<String>();
    private ArrayList<String> w = new ArrayList<String>();
    private ArrayList<String> x = new ArrayList<String>();
    private ArrayList<String> y = new ArrayList<String>();
    private ArrayList<String> z = new ArrayList<String>();
    private ArrayList<String> one = new ArrayList<String>();
    private ArrayList<String> two = new ArrayList<String>();
    private ArrayList<String> three = new ArrayList<String>();
    private ArrayList<String> four = new ArrayList<String>();
    private ArrayList<String> five = new ArrayList<String>();


    private ArrayList<String> letterArr = new ArrayList<>();
    private ArrayList<String> englishArr = new ArrayList<>();
    String message = "";
    

   public Translation() {

       a.add("short");
       a.add("long");

       b.add("long");
       b.add("short");
       b.add("short");
       b.add("short");

       c.add("long");
       c.add("short");
       c.add("long");
       c.add("short");

       d.add("long");
       d.add("short");
       d.add("short");

       e.add("short");

       f.add("short");
       f.add("short");
       f.add("long");
       f.add("short");

       g.add("long");
       g.add("long");
       g.add("short");

       h.add("short");
       h.add("short");
       h.add("short");
       h.add("short");

       i.add("short");
       i.add("short");

       j.add("short");
       j.add("long");
       j.add("long");
       j.add("long");

       k.add("long");
       k.add("short");
       k.add("long");

       l.add("short");
       l.add("long");
       l.add("short");
       l.add("short");

       m.add("long");
       m.add("long");

       n.add("long");
       n.add("short");

       o.add("long");
       o.add("long");
       o.add("long");

       p.add("short");
       p.add("long");
       p.add("long");
       p.add("short");

       q.add("long");
       q.add("long");
       q.add("short");
       q.add("long");

       r.add("short");
       r.add("long");
       r.add("short");

       s.add("short");
       s.add("short");
       s.add("short");

       t.add("long");

       u.add("short");
       u.add("short");
       u.add("long");

       v.add("short");
       v.add("short");
       v.add("short");
       v.add("long");

       w.add("short");
       w.add("long");
       w.add("long");

       x.add("long");
       x.add("short");
       x.add("short");
       x.add("long");

       y.add("long");
       y.add("short");
       y.add("long");
       y.add("long");

       z.add("long");
       z.add("long");
       z.add("short");
       z.add("short");

       one.add("short");
       one.add("long");
       one.add("long");
       one.add("long");
       one.add("long");

       two.add("short");
       two.add("short");
       two.add("long");
       two.add("long");
       two.add("long");

       three.add("short");
       three.add("short");
       three.add("short");
       three.add("long");
       three.add("long");

       four.add("short");
       four.add("short");
       four.add("short");
       four.add("short");
       four.add("long");

       five.add("short");
       five.add("short");
       five.add("short");
       five.add("short");
       five.add("short");

       letters.put("A", a);
       letters.put("B", b);
       letters.put("C", c);
       letters.put("D", d);
       letters.put("E", e);
       letters.put("F", f);
       letters.put("G", g);
       letters.put("H", h);
       letters.put("I", i);
       letters.put("J", j);
       letters.put("K", k);
       letters.put("L", l);
       letters.put("M", m);
       letters.put("N", n);
       letters.put("O", o);
       letters.put("P", p);
       letters.put("Q", q);
       letters.put("R", r);
       letters.put("S", s);
       letters.put("T", t);
       letters.put("U", u);
       letters.put("V", v);
       letters.put("W", w);
       letters.put("X", x);
       letters.put("Y", y);
       letters.put("Z", z);
       letters.put("1", one);
       letters.put("2", two);
       letters.put("3", three);
       letters.put("4", four);
       letters.put("5", five);
   }

    public String Translate(ArrayList<String> arrayList) {

       for (int i = 0; i < arrayList.size(); i++) {
           if (!(arrayList.get(i).equals(" "))) {
               letterArr.add(arrayList.get(i));
           }
           else {
               englishArr.add(translation(letterArr));
               englishArr.add(" ");
               letterArr.clear();
           }
       }

       englishArr.add(translation(letterArr));
       letterArr.clear();

       message = arrayToMessage(englishArr);
       englishArr.clear();

        return message;
    }

    private String arrayToMessage(ArrayList<String> englishArr) {

       String englishMessage = "";
       String temp;
        for (int i = 0; i < englishArr.size(); i++) {

            temp = englishMessage;
            englishMessage = temp + englishArr.get(i);
        }
       return englishMessage;
    }

    private String translation(ArrayList<String> arrayList) {

       String letter = "";
       
        for(Map.Entry m:letters.entrySet()){

            if (m.getValue().equals(arrayList)) {
                letter = (String) m.getKey();
                break;
            }
        }
        return letter;
    }

}
