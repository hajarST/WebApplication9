/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domaines;

import entities.Employe;
import entities.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import org.primefaces.event.RowEditEvent;
import service.EmployeService;
import service.ServiceService;

/**
 *
 * @author pc
 */
@ManagedBean
@RequestScoped
public class EmployeBean {

    private Employe employe;
    private Service service;
    private List<Employe> employes;

    private EmployeService employeService;
    private ServiceService serviceService;
    private String file;
    private String fileName;
    private Employe chef;

    private Part uploadedFile;

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public EmployeBean() {
        employe = new Employe();
        chef = new Employe();

        employeService = new EmployeService();
        serviceService = new ServiceService();

    }

    public List<Employe> getEmployes() {
        if (employes == null) {
            employes = employeService.getAll();
        }
        return employes;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public void setEmployeService(EmployeService employeService) {
        this.employeService = employeService;
    }

    public EmployeService getEmployeService() {
        return employeService;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String onCreateAction() {

        chef = employeService.getById(chef.getId());
        employe.setChef(chef);

        employeService.create(employe);
        employe = new Employe();
        chef = new Employe();
        return null;
        /*
         // Récupérer le chef
         chef = employeService.getById(chef.getId());
         employe.setChef(chef);

         // Traitement du fichier téléchargé
         if (uploadedFile != null) {
         try (InputStream input = uploadedFile.getInputStream()) {
         // Vérifier si le fichier est vide
         if (input.available() > 0) {
         byte[] photoData = new byte[input.available()];
         input.read(photoData);

         // Encode the byte array to Base64
         String encodedPhoto = Base64.getEncoder().encodeToString(photoData);
         employe.setPhoto(encodedPhoto);
         } else {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le fichier est vide", null));
         return null;
         }
         } catch (IOException e) {
         e.printStackTrace(); // Gérer les exceptions de manière appropriée
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors du téléchargement de la photo", null));
         return null;
         }
         } else {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Veuillez sélectionner un fichier", null));
         return null;
         }

         // Créer l'employé
         employeService.create(employe);

         // Réinitialiser les champs
         employe = new Employe();
         chef = new Employe();

         // Ajouter un message de succès
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Employé ajouté avec succès", null));

         return null;
         */

    }

    public String onDeleteAction() {

        List<Employe> employeesToUpdate = employeService.getByChef(employe);
        for (Employe employee : employeesToUpdate) {
            employee.setChef(null);
            employeService.update(employee);
        }
        employe.setChef(null);
        employe.setService(null);
        employeService.delete(employeService.getById(employe.getId()));

        return null;
    }

    public void onEdit(RowEditEvent event) {
        chef = employeService.getById(chef.getId());
        employe = (Employe) event.getObject();
        employe.setChef(chef);
        service = serviceService.getById(this.employe.getService().getId());
        employe.setService(service);
        employe.getService().setNom(service.getNom());
        employeService.update(employe);
        chef = new Employe();
    }

    public void onCancel(RowEditEvent event) {
    }

    public List<Employe> serviceLoad() {
        for (Employe m : employeService.getAll()) {
            if (m.getService().equals(service)) {
                employes.add(m);
            }
        }
        return employes;
    }

    public void load() {
        System.out.println(service.getNom());
        service = serviceService.getById(service.getId());
        getEmployes();
    }

    public void createEmploye() {
        employeService.create(employe);
        employes = employeService.getAll();
        employe = new Employe();
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Employe getChef() {
        return chef;
    }

    public void setSuperviseur(Employe chef) {
        this.chef = chef;
    }

    public List<Employe> getBySup(Employe supervisor) {
        List<Employe> employeesWithSupervisor = new ArrayList<>();

        for (Employe employee : employes) {
            if (Objects.equals(supervisor.getId(), employee.getChef().getId())) {
                employeesWithSupervisor.add(employee);
                System.out.println("employee => " + employee);
            }
        }
        return employeesWithSupervisor;
    }

}
