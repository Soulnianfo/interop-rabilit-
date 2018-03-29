/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groupe.mixeur;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nianfo
 */
public class ReponseRecherche {
    String titre;
    String description;
    public List<UnStatement> statements = new ArrayList();

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
