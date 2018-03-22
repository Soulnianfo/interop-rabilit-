/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groupe.mixeur;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.ItemDocumentBuilder;
import org.wikidata.wdtk.datamodel.helpers.StatementBuilder;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.util.WebResourceFetcherImpl;
import org.wikidata.wdtk.wikibaseapi.ApiConnection;
import org.wikidata.wdtk.wikibaseapi.LoginFailedException;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataEditor;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;
import org.xml.sax.SAXException;

/**
 *
 * @author Nianfo
 */
public class InsertionDonneesWikidata {
    public  InsertionDonneesWikidata(){}
    
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ParseException, org.xml.sax.SAXException, MediaWikiApiErrorException {

        System.out.println("Lancement");
        String file = "C:\\Users\\Nianfo\\Documents\\NetBeansProjects\\GroupeMixeur\\src\\main\\resources\\static\\stage.xml";

        ParserDom parser = new ParserDom(file);
        List<Stage> list = new ArrayList();
        list = parser.lecture();
        
        InsertionDonneesWikidata wiki = new InsertionDonneesWikidata();
        wiki.insertStage( list.get(0));

    }
    
    public void insertStage(Stage stg) throws SAXException, IOException, ParserConfigurationException, MediaWikiApiErrorException{
        
        
        String siteIri = "https://wdaqua-biennale-design.univ-st-etienne.fr/wikibase/index.php/";
        WebResourceFetcherImpl.setUserAgent("Wikidata Toolkit EditOnlineDataExample");

        ApiConnection con = new ApiConnection("https://wdaqua-biennale-design.univ-st-etienne.fr/wikibase/api.php");
        
       try {
            //Put in the first place the user with which you created the bot account
            //Put as password what you get when you create the bot account
             con.login("Souleymane", "robotsolo@69bdaeabeoaef55j0i6i5994ose641hq");
        } catch (LoginFailedException e) {
            e.printStackTrace();
        }
      
       // stg.entreprise
                    String qxx = recherche(stg.entreprise);
                    //System.out.println("Mixeur nom "+stg.entreprise);
                    System.out.println("Woline "+qxx);
                    if(qxx.equals("null")){
                       // creerEntreprise(stg.entreprise);
                    }
       
                     WikibaseDataFetcher wbdf = new WikibaseDataFetcher(con, siteIri);
                     ItemDocument mixeur = (ItemDocument) wbdf.getEntityDocument("Q892");
                    /**
                     * Ecriture des donnees sur wikidata
                     */
                    WikibaseDataEditor wbde = new WikibaseDataEditor(con, siteIri);

                    PropertyDocument description = (PropertyDocument) wbdf.getEntityDocument("P306");
                   // System.out.println(description.getLabels());
                    PropertyDocument propose = (PropertyDocument) wbdf.getEntityDocument("P295");
                   // System.out.println(propose.getLabels());
                    PropertyDocument intitule = (PropertyDocument) wbdf.getEntityDocument("P233");
                    //System.out.println(intitule.getLabels());
                    PropertyDocument compete = (PropertyDocument) wbdf.getEntityDocument("P260");
                                    //    System.out.println(compete.getLabels());

                    PropertyDocument adres = (PropertyDocument) wbdf.getEntityDocument("P257");
                           //             System.out.println(adres.getLabels());

                    PropertyDocument gratifica = (PropertyDocument) wbdf.getEntityDocument("P282");
                             //           System.out.println(gratifica.getLabels());

                    PropertyDocument duree = (PropertyDocument) wbdf.getEntityDocument("P305");
                           //             System.out.println(duree.getLabels());

                    
                   
                    


                    
                    ItemIdValue noid = ItemIdValue.NULL; // used when creating new items
                    Statement statement1 = StatementBuilder
                            .forSubjectAndProperty(noid, description.getPropertyId())
                            .withValue(Datamodel.makeStringValue(stg.Desc)).build();

                    Statement statement2 = StatementBuilder
                            .forSubjectAndProperty(noid, propose.getPropertyId())
                            .withValue(mixeur.getItemId()).build();
                    
                    /*Statement statement3 = StatementBuilder.
                            forSubjectAndProperty(noid, intitule.getPropertyId())
                            .withValue(Datamodel.makeStringValue(stg.Intituler)).build();*/
                    
                   Statement statement4 = StatementBuilder.
                            forSubjectAndProperty(noid, compete.getPropertyId())
                            .withValue(Datamodel.makeStringValue(stg.Competences)).build();
                    
                    Statement statement7 = StatementBuilder.
                            forSubjectAndProperty(noid, duree.getPropertyId())
                            .withValue(Datamodel.makeStringValue("6 mois ")).build();
                    
                    
                   /* Statement statement5 = StatementBuilder.
                            forSubjectAndProperty(noid, adres.getPropertyId())
                            .withValue(Datamodel.makeStringValue(stg.Localisation)).build();*/
                    
                    Statement statement6 = StatementBuilder.
                            forSubjectAndProperty(noid, gratifica.getPropertyId())
                            .withValue(Datamodel.makeStringValue("800 eur")).build();

                    ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid)
                            .withLabel(stg.Intituler, "en")
                            .withLabel(stg.Intituler, "fr")
                            .withStatement(statement1)
                            .withStatement(statement2)
                            //.withStatement(statement3)
                           // .withStatement(statement4)
                           // .withStatement(statement7)
                           // .withStatement(statement5)
                            //.withStatement(statement6)
                            .build();
                    try {
                        ItemDocument newItemDocument = wbde.createItemDocument(itemDocument,"Statement created by our roboSolo");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
              }
              
    public String recherche( String rech) throws MalformedURLException, IOException, MediaWikiApiErrorException{
        
        URL url = new URL(" https://wdaqua-qanary.univ-st-etienne.fr/gerbil-execute/wdaqua-core1,%20QueryExecuter/");
    
        String data = "query="+rech+"&lang=en&kb=dbpedia";
        HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setInstanceFollowRedirects(true);
        conn.setRequestProperty("Content-length", String.valueOf(data.length()));
        
        conn.setDoOutput(true);
        conn.setDoInput(true);

        DataOutputStream output = new DataOutputStream(conn.getOutputStream());
        output.writeBytes(data);
        output.close();

        // "Post data send ... waiting for reply");
        int code = conn.getResponseCode(); // 200 = HTTP_OK
       // System.out.println("Response    (Code):" + code);
        //System.out.println("Response (Message):" + conn.getResponseMessage());
        
        DataInputStream input = new DataInputStream(conn.getInputStream());
        String c;
        StringBuilder resultBuf = new StringBuilder();
        while ((c = input.readLine())!=null) {
            resultBuf.append(c);
            //resultBuf.append("\n");
        }
        input.close();
        System.out.println(resultBuf.toString());
        String txt = resultBuf.toString();
        txt = txt.replace("{", " ");
        txt = txt.replace("}", " ");
        txt = txt.replace("\\" ," ");
        txt = txt.replace(">"," ");
        txt = txt.replace("<"," ");
                String[] tab = txt.split(" ");
                String txt2;
                String[] qxxx;
                int i;
                String ht = "http://www.wikidata.org/entity/" ;
                int htsize = ht.length();
                for(i=0;i<tab.length;i++){
                    if(htsize < tab[i].length()){
                        if(ht.equals(tab[i].substring(0, htsize))){
                        txt2 = tab[i].replace("/", " ");
                        qxxx = txt2.split(" ");
                        System.out.println(qxxx[qxxx.length-1]);
                        return qxxx[qxxx.length-1];
                        }
                    }
                }
        System.out.println(resultBuf.toString());
        return "null";

    }
    
    
}
