/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groupe.mixeur;

/**
 *
 * @author Adel
 */
public class Stage {
    String entreprise;
    String Desc;
    String Intituler;
    String Competences;
    String Localisation;
    String Duree;
    String Gratification;
    public Stage(){
        
    }
    
    public Stage(String entreprise,String Description,String Intituler,String Competences,String Localisation,String Duree,String Gratification){
        this.entreprise=entreprise;
        this.Desc=Description;
        this.Intituler=Intituler;
        this.Competences=Competences;
        this.Localisation=Localisation;
        this.Duree=Duree;
        this.Gratification=Gratification;
    
    }

    
    
}
