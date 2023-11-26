/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domaines;

import entities.Employe;
import entities.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import service.EmployeService;
import service.ServiceService;

import org.primefaces.event.RowEditEvent;
import util.HibernateUtil;

/**
 *
 * @author Lachgar
 */
@ManagedBean
@RequestScoped
public class ServiceBean {

    private Employe employe;
    private Service service;
    private ServiceService serviceService;
    private List<Service> services;
    private List<Employe> employes;
    private EmployeService employeService;
    private Service selectedService;
    private List<Employe> collaborators;
    private List<Employe> subordonnes;

    public Service getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(Service selectedService) {
        this.selectedService = selectedService;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public ServiceService getServiceService() {
        return serviceService;
    }

    public void setServiceService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    public EmployeService getEmployeService() {
        return employeService;
    }

    public void setEmployeService(EmployeService employeService) {
        this.employeService = employeService;
    }

    public List<Employe> getSubordonnes() {
        return subordonnes;
    }

    public void setSubordonnes(List<Employe> subordonnes) {
        this.subordonnes = subordonnes;
    }

    public ServiceBean() {
        service = new Service();
        serviceService = new ServiceService();
        employe = new Employe();
        employeService = new EmployeService();
    }

    public List<Employe> getEmployes() {
        if (employes == null) {
            employes = employeService.getAll();
        }
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public List<Service> getservices() {
        if (services == null) {
            services = serviceService.getAll();

        }
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String onCreateAction() {
        serviceService.create(service);
        service = new Service();
        return null;
    }

    public void onDeleteAction() {
        service.setEmployes(null);
        serviceService.delete(service);
    }

    public void onEdit(RowEditEvent event) {
        service = (Service) event.getObject();
        service.setEmployes(null);
        serviceService.update(service);
    }

    public void load() {
        System.out.println(service.getNom());
        service = serviceService.getById(service.getId());
        getEmployes();
    }

    public void onCancel(RowEditEvent event) {
    }

    public void onEditm(RowEditEvent event) {
        employe = (Employe) event.getObject();
        service = serviceService.getById(this.employe.getService().getId());
        employe.setService(service);
        employe.getService().setNom(service.getNom());
        employeService.update(employe);
    }

    public String onDeleteActionm() {
        employeService.delete(employeService.getById(employe.getId()));
        return null;
    }

    public List<Employe> serviceLoad() {
        for (Employe m : employeService.getAll()) {
            if (m.getService().equals(service)) {
                employes.add(m);
            }
        }
        return employes;

    }

    public void onCancelm(RowEditEvent event) {
    }

    public List<Employe> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Employe> collaborators) {
        this.collaborators = collaborators;
    }

    public List<Employe> getCollaborateursDuService() {
        if (selectedService != null) {
            collaborators = serviceService.getCollaborateursDuService(selectedService.getId());
        }
        return collaborators;
    }

    public void showCollaborateurs(Service service) {
        System.out.println("SERVICE : " + service);
        selectedService = service;
    }

    public Employe getChefService(Service service) {
        List<Employe> employees = service.getEmployes();

        for (Employe employee : employees) {
            if (employee.getChef() == null) {
                return employee;
            }
        }
        return null;
    }

    public List<Employe> getSubordinates() {
        if (selectedService != null) {
            Employe chefService = getChefService(selectedService);
            if (chefService != null) {
                return getSubordinates(chefService);
            }
        }
        return Collections.emptyList();
    }

    private List<Employe> getSubordinates(Employe chef) {
        List<Employe> subordinates = new ArrayList<>();

        for (Employe employee : employes) {
            if (chef.getId() == employee.getChef().getId() || chef.getId() != employee.getId()) {
                subordinates.add(employee);
            }
        }
        return subordinates;
    }

    public List<Employe> getSubordinatesOfChefService() {
        if (selectedService != null) {
            Employe chefService = getChefService(selectedService);
            if (chefService != null) {
                subordonnes = getSubordinates(chefService);
                System.out.println("Subordinates => " + subordonnes);
                return subordonnes;
            }
        }
        return Collections.emptyList();
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
