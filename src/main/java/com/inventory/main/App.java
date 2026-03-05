package com.inventory.main;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.inventory.entity.Product;
import com.inventory.util.HibernateUtil;

public class App {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        // ================= SORT BY PRICE ASC =================
        String hql1 = "FROM Product p ORDER BY p.price ASC";
        Query<Product> q1 = session.createQuery(hql1, Product.class);
        List<Product> list1 = q1.list();

        System.out.println("\nProducts Sorted By Price (ASC)");
        for (Product p : list1) {
            System.out.println(p.getName() + " - " + p.getPrice());
        }

        // ================= SORT BY PRICE DESC =================
        String hql2 = "FROM Product p ORDER BY p.price DESC";
        Query<Product> q2 = session.createQuery(hql2, Product.class);
        List<Product> list2 = q2.list();

        System.out.println("\nProducts Sorted By Price (DESC)");
        for (Product p : list2) {
            System.out.println(p.getName() + " - " + p.getPrice());
        }

        // ================= SORT BY QUANTITY =================
        String hql3 = "FROM Product p ORDER BY p.quantity DESC";
        Query<Product> q3 = session.createQuery(hql3, Product.class);
        List<Product> list3 = q3.list();

        System.out.println("\nProducts Sorted By Quantity");
        for (Product p : list3) {
            System.out.println(p.getName() + " - Qty: " + p.getQuantity());
        }

        // ================= PAGINATION FIRST 3 =================
        String hql4 = "FROM Product";
        Query<Product> q4 = session.createQuery(hql4, Product.class);

        q4.setFirstResult(0);
        q4.setMaxResults(3);

        List<Product> list4 = q4.list();

        System.out.println("\nFirst 3 Products");
        for (Product p : list4) {
            System.out.println(p.getName());
        }

        // ================= NEXT 3 PRODUCTS =================
        Query<Product> q5 = session.createQuery(hql4, Product.class);

        q5.setFirstResult(3);
        q5.setMaxResults(3);

        List<Product> list5 = q5.list();

        System.out.println("\nNext 3 Products");
        for (Product p : list5) {
            System.out.println(p.getName());
        }

        // ================= COUNT TOTAL PRODUCTS =================
        String hql6 = "SELECT COUNT(p) FROM Product p";
        Query<Long> q6 = session.createQuery(hql6, Long.class);

        Long total = q6.uniqueResult();
        System.out.println("\nTotal Products: " + total);

        // ================= COUNT PRODUCTS IN STOCK =================
        String hql7 = "SELECT COUNT(p) FROM Product p WHERE p.quantity > 0";
        Query<Long> q7 = session.createQuery(hql7, Long.class);

        Long stock = q7.uniqueResult();
        System.out.println("Products In Stock: " + stock);

        // ================= GROUP BY DESCRIPTION =================
        String hql8 = "SELECT p.description, COUNT(p) FROM Product p GROUP BY p.description";
        Query<Object[]> q8 = session.createQuery(hql8, Object[].class);

        List<Object[]> groupList = q8.list();

        System.out.println("\nProducts Grouped By Description");
        for (Object[] row : groupList) {
            System.out.println(row[0] + " : " + row[1]);
        }

        // ================= MIN MAX PRICE =================
        String hql9 = "SELECT MIN(p.price), MAX(p.price) FROM Product p";
        Query<Object[]> q9 = session.createQuery(hql9, Object[].class);

        Object[] result = q9.uniqueResult();

        System.out.println("\nMinimum Price: " + result[0]);
        System.out.println("Maximum Price: " + result[1]);

        // ================= FILTER BY PRICE RANGE =================
        String hql10 = "FROM Product p WHERE p.price BETWEEN :min AND :max";
        Query<Product> q10 = session.createQuery(hql10, Product.class);

        q10.setParameter("min", 1000.0);
        q10.setParameter("max", 50000.0);

        List<Product> list10 = q10.list();

        System.out.println("\nProducts Between Price Range");
        for (Product p : list10) {
            System.out.println(p.getName() + " - " + p.getPrice());
        }

        // ================= LIKE STARTING WITH =================
        String hql11 = "FROM Product p WHERE p.name LIKE :pattern";
        Query<Product> q11 = session.createQuery(hql11, Product.class);

        q11.setParameter("pattern", "D%");

        List<Product> list11 = q11.list();

        System.out.println("\nProducts Starting With D");
        for (Product p : list11) {
            System.out.println(p.getName());
        }

        // ================= LIKE ENDING WITH =================
        Query<Product> q12 = session.createQuery(hql11, Product.class);

        q12.setParameter("pattern", "%p");

        List<Product> list12 = q12.list();

        System.out.println("\nProducts Ending With p");
        for (Product p : list12) {
            System.out.println(p.getName());
        }

        // ================= LIKE CONTAINING =================
        Query<Product> q13 = session.createQuery(hql11, Product.class);

        q13.setParameter("pattern", "%Desk%");

        List<Product> list13 = q13.list();

        System.out.println("\nProducts Containing 'Desk'");
        for (Product p : list13) {
            System.out.println(p.getName());
        }

        // ================= NAME LENGTH =================
        String hql14 = "FROM Product p WHERE LENGTH(p.name)=:len";
        Query<Product> q14 = session.createQuery(hql14, Product.class);

        q14.setParameter("len", 5);

        List<Product> list14 = q14.list();

        System.out.println("\nProducts With Name Length 5");
        for (Product p : list14) {
            System.out.println(p.getName());
        }

        session.close();
        HibernateUtil.getSessionFactory().close();

        System.out.println("\nAll HQL Queries Executed Successfully");
    }
}