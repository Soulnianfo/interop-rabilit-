/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groupe.mixeur;

import com.groupe.mixeur.Entreprise;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author maham
 */
public class ParserCSV {
    
    File fic;

    public ParserCSV(String chemin) {
        fic = new File(chemin);
    }
    
    public ArrayList<Entreprise> getEntrepise() throws FileNotFoundException, IOException{
        ArrayList<Entreprise> list = new ArrayList<>();
        
        BufferedReader br = new BufferedReader(new FileReader(this.fic));
        String line;
        
        while((line = br.readLine()) != null){
            String tab[] = line.split(";");
            
            String acts[] = tab[1].split(":");
            
            String present = tab[2].replaceAll("_", ",");
            
            String gerants[] = tab[3].split(":");
            
            for (int i = 5; i <= 10; i++) {
                if(tab[i].equals(""))
                    tab[i]="";
            }
            
            list.add(new Entreprise(tab[0],acts,present,gerants,tab[4],tab[5],tab[6],tab[7],tab[8],tab[9],tab[10],tab[11]));
        }
        
        return list;
    }
    
}
