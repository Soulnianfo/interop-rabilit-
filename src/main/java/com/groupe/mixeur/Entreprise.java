/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groupe.mixeur;

import java.util.ArrayList;

/**
 *
 * @author maham
 */
public class Entreprise {
    public String nom;
    public String typeActs[];
    public String present;
    public String gerants[];
    public String date_creat;
    public String site_web;
    public String e_mail;
    public String tel;
    public String fbk;
    public String twt;
    public String ggl;
    public String local;

    public Entreprise() {
        
    }

    public Entreprise(String nom, String[] typeActs, String present, String[] gerants, String date_creat, String site_web, String e_mail, String tel, String fbk, String twt, String ggl, String local) {
        this.nom = nom;
        this.typeActs = typeActs;
        this.present = present;
        this.gerants = gerants;
        this.date_creat = date_creat;
        this.site_web = site_web;
        this.e_mail = e_mail;
        this.tel = tel;
        this.fbk = fbk;
        this.twt = twt;
        this.ggl = ggl;
        this.local = local;
    }
    
    

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String[] getTypeActs() {
        return typeActs;
    }

    public void setTypeActs(String[] typeActs) {
        this.typeActs = typeActs;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String[] getGerants() {
        return gerants;
    }

    public void setGerants(String[] gerants) {
        this.gerants = gerants;
    }

    public String getDate_creat() {
        return date_creat;
    }

    public void setDate_creat(String date_creat) {
        this.date_creat = date_creat;
    }

    public String getSite_web() {
        return site_web;
    }

    public void setSite_web(String site_web) {
        this.site_web = site_web;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFbk() {
        return fbk;
    }

    public void setFbk(String fbk) {
        this.fbk = fbk;
    }

    public String getTwt() {
        return twt;
    }

    public void setTwt(String twt) {
        this.twt = twt;
    }

    public String getGgl() {
        return ggl;
    }

    public void setGgl(String ggl) {
        this.ggl = ggl;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
    
    
}
