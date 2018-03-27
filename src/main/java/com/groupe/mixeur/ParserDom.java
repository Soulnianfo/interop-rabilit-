/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groupe.mixeur;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static javafx.scene.input.KeyCode.O;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author Adel
 */
public class ParserDom {

    Document Doc;
    String NomEntreprise;
    String Description;
    String Intituler;
    String Competences;
    String Localisation;
    String Duree;
    String Gratification;

//    List<Stage> ListStages = new ArrayList<>();
    List<Stage> List= new ArrayList();
    public ParserDom(String file) throws org.xml.sax.SAXException, IOException, ParserConfigurationException {

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Doc = dBuilder.parse(file);

            System.out.println("The file is opened !");

        } catch (ParserConfigurationException | IOException e) {
            System.err.println(" Error !");
            e.printStackTrace();
        }

    }

    public List<Stage> lecture() {

        System.out.println("Lancer");
        NodeList list = Doc.getElementsByTagName("file").item(0).getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {

            Node courantBody = list.item(i);
            if (courantBody.getNodeType() == 1 && courantBody.getNodeName().equals("body")) {
                //la liste les stages
                NodeList enfant = courantBody.getChildNodes();
                int n = 0;
                for (int k = 0; k < enfant.getLength(); k++) {
                    if (enfant.item(k).getNodeType() == 1) {
                        n++;
                    }
                }
                System.out.println("Le nombre de stage existant est : " + n);
                int g = 0;
                for (int j = 0; j < enfant.getLength(); j++) {
                    System.out.println("*****************************************************************************************************");

                    if (enfant.item(j).getNodeType() == 1) {
                        // un stage  
                        
                        Node stage = enfant.item(j);
                        Competences = "";
                        g = g + 1;
                        System.out.println("le stage numero  : " + g);
                        // les enfants d un stage
                        NodeList enfantStage = stage.getChildNodes();
                        for (int o = 0; o < enfantStage.getLength(); o++) {
                            if (enfantStage.item(o).getNodeType() == 1) {
                                if (enfantStage.item(o).getNodeName().equals("entreprise")) {
                                    Element object = (Element) enfantStage.item(o);
                                    NomEntreprise = object.getTextContent();
                                   
                                    System.out.println(" le nom de l'entreprise est : " + NomEntreprise);
                                } else {

                                    if (enfantStage.item(o).getNodeName().equals("Description")) {
                                        Element object = (Element) enfantStage.item(o);
                                        Description = object.getTextContent();
                                        
                                        System.out.println(" la Description de l'entreprise est :" + Description);
                                    } else {
                                        if (enfantStage.item(o).getNodeName().equals("intitule")) {
                                            Element object = (Element) enfantStage.item(o);
                                            Intituler = object.getTextContent();
                                            
                                            System.out.println(" l'intituler du Stage est :" + Intituler);
                                        } else {
                                            if (enfantStage.item(o).getNodeName().equals("Competences")) {
                                                NodeList ListCompetences = enfantStage.item(o).getChildNodes();
                                                for (int m = 0; m < ListCompetences.getLength(); m++) {
                                                    if (ListCompetences.item(m).getNodeType() == 1) {
                                                        Competences += ListCompetences.item(m).getTextContent() + "\n";
                                                    }

                                                }
                                                System.out.println("les competences exiger sont  :" + Competences);

                                            } else {
                                                if (enfantStage.item(o).getNodeName().equals("Localisation")) {
                                                    Element object = (Element) enfantStage.item(o);
                                                    Localisation = object.getTextContent();
                                                    System.out.println(" La Localisation du stage  :" + Localisation);
                                                } else {

                                                    if (enfantStage.item(o).getNodeName().equals("Duree")) {
                                                        Element object = (Element) enfantStage.item(o);
                                                        Duree = object.getTextContent();
                                                        System.out.println(" La Duree du Stage  :" + Duree);
                                                    } else {
                                                        if (enfantStage.item(o).getNodeName().equals("Gratification")) {
                                                            Element object = (Element) enfantStage.item(o);
                                                            Gratification = object.getTextContent();
                                                            System.out.println(" La Gratification du Stage  :" + Gratification);

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
                        List.add(new Stage(NomEntreprise, Description, Intituler, Competences, Localisation, Duree, Gratification));
                     
                        System.out.println("Le nombre de stage dans la Liste est :" + List.size());
                    }

                }
            }
        }
        return List;
    }

}
