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
public class Evenement {
    String nom;
    String description;
    String date;
    String type_evenmt;
    String organiseur;
    String tarif;
    String lieu;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType_evenmt() {
        return type_evenmt;
    }

    public void setType_evenmt(String type_evenmt) {
        this.type_evenmt = type_evenmt;
    }

    public String getOrganiseur() {
        return organiseur;
    }

    public void setOrganiseur(String organiseur) {
        this.organiseur = organiseur;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }
    
    
}
