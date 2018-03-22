/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groupe.mixeur;

/**
 *
 * @author Nianfo
 */
public class Entreprises {

    
    private String nom;
    private String description;
    private String adresse;
    private String dateCreation;
    private Contact contact;
    private String site;
    private Activites activites;
    private Employes employes;
    
    public Entreprises(){
        contact = new Contact();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(String telep, String mail) {
        this.contact.setTelephone(telep);
        this.contact.setMail(mail);
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Activites getActivites() {
        return activites;
    }

    public void setActivites(Activites activites) {
        this.activites = activites;
    }

    public Employes getEmployes() {
        return employes;
    }

    public void setEmployes(Employes employes) {
        this.employes = employes;
    }

    
    
    
    private static class Activites {
        String activite;

        public String getActivite() {
            return activite;
        }

        public void setActivite(String activite) {
            this.activite = activite;
        }
        
        public Activites() {
        }
    }

    private static class Employes {

        public Employes() {
        }
    }
        
    public class Contact{
        public String telephone;
        public String mail;

        public Contact() {
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }
        
    }
    
}
