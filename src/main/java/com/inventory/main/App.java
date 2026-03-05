package com.inventory.main;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.inventory.entity.Product;
import com.inventory.util.HibernateUtil;

public class App {

    public static void main(String[] args) {

        Session session;
        Transaction tx;

        // ================= INSERT =================
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();

        Product p1 = new Product("Laptop", "Dell Inspiron", 55000, 10);
        Product p2 = new Product("Mouse", "Wireless Mouse", 800, 50);

        session.save(p1);
        session.save(p2);

        tx.commit();
        session.close();

        System.out.println("Products Inserted Successfully");

        // ================= RETRIEVE =================
        session = HibernateUtil.getSessionFactory().openSession();

        Product product = session.get(Product.class, 1);

        if (product != null) {
            System.out.println("Product Details:");
            System.out.println("ID: " + product.getId());
            System.out.println("Name: " + product.getName());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Quantity: " + product.getQuantity());
        }

        session.close();

        // ================= UPDATE =================
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();

        Product updateProduct = session.get(Product.class, 1);

        if (updateProduct != null) {
            updateProduct.setPrice(60000);
            updateProduct.setQuantity(8);

            session.update(updateProduct);
            System.out.println("Product Updated Successfully");
        }

        tx.commit();
        session.close();

        // ================= DELETE =================
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();

        Product deleteProduct = session.get(Product.class, 2);

        if (deleteProduct != null) {
            session.delete(deleteProduct);
            System.out.println("Product Deleted Successfully");
        }

        tx.commit();
        session.close();

        HibernateUtil.getSessionFactory().close();

        System.out.println("CRUD Operations Completed");
    }
}