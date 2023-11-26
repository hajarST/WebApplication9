/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Employe;
import entities.Service;
import java.util.List;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author pc
 */
public class EmployeService extends AbstractFacade<Employe> {

    @Override
    protected Class<Employe> getEntityClass() {
        return Employe.class;
    }

    @Override
    public List<Employe> getAll() {
        List<Employe> employes = null;

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employes = session.createQuery("from Employe").list();
        session.getTransaction().commit();
        return employes;
    }

    public List<Employe> getByChef(Employe chef) {
        List<Employe> employes = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Use HQL (Hibernate Query Language) to get employees with the specified supervisor
        employes = session.createQuery("FROM Employe e WHERE e.chef = :chef")
                .setParameter("chef", chef)
                .list();

        session.getTransaction().commit();
        return employes;
    }

}
