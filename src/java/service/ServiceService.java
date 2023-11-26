/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Employe;
import entities.Service;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author Lachgar
 */
public class ServiceService extends AbstractFacade<Service> {

    @Override
    protected Class<Service> getEntityClass() {
        return Service.class;
    }

    public List<Employe> getCollaborateursDuService(int serviceId) {
        List<Employe> collaborateurs = null;

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        try {
            Query query = (Query) session.createQuery("SELECT e FROM Employe e JOIN e.services s WHERE s.id = :serviceId");
            query.setParameter("serviceId", serviceId);

            collaborateurs = query.getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return collaborateurs;
    }

}
