


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groupe.mixeur;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import static org.apache.commons.lang3.StringUtils.split;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.ItemDocumentBuilder;
import org.wikidata.wdtk.datamodel.helpers.StatementBuilder;
import org.wikidata.wdtk.datamodel.interfaces.EntityDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.util.WebResourceFetcherImpl;
import org.wikidata.wdtk.wikibaseapi.ApiConnection;
import org.wikidata.wdtk.wikibaseapi.LoginFailedException;
import org.wikidata.wdtk.wikibaseapi.WbSearchEntitiesResult;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataEditor;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;
import org.xml.sax.SAXException;


/**
 *
 * @author Nianfo
 */
@Controller
public class ResultController {

    final static String siteIri = "http://www.test.wikidata.org/entity/";

    @RequestMapping("/")
    public String home(){


        return "pageAccueil";
    }

    @RequestMapping("/resultats")
    public String Resultats(Model model,HttpServletRequest req, HttpServletResponse resp) throws MediaWikiApiErrorException, IOException{
        String text =(String) req.getParameter("resultat");


        model.addAttribute("listRech", requette(text));
        model.addAttribute("text",text);
        System.out.println("Text found :"+recherche(text));

    return "ResultRech";  
    }

    public List<ReponseRecherche> requette(String str) throws MediaWikiApiErrorException{
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


        List<ReponseRecherche> list = new ArrayList<>();
        WikibaseDataFetcher wbdf = new WikibaseDataFetcher(con, siteIri);
        String[] arr = str.split(" ");
        for (int j = 0; j < arr.length; j++) {


        List<WbSearchEntitiesResult> entities = wbdf.searchEntities(arr[j]);
        for (WbSearchEntitiesResult entity : entities) {
            ReponseRecherche rep = new ReponseRecherche();
            ItemDocument laboratoireHC = (ItemDocument) wbdf.getEntityDocument(entity.getEntityId());
            String[] labs = laboratoireHC.getLabels().toString().split("\"");
            System.out.println("Stat :"+laboratoireHC.getStatementGroups());

            if(labs.length>=2){

                rep.setTitre(labs[1]);
            }

            int i ;
            String h = "";
            for(i=0;i<laboratoireHC.getStatementGroups().size();i++){
                //System.out.println(laboratoireHC.getStatementGroups().get(i).toString());
                String[] g = laboratoireHC.getStatementGroups().get(i).toString().split("\"");
                if(g.length>=2){
                    UnStatement st = new UnStatement();
                    // Recuperer le identifiant qxxx d'une proprieté.
                    String[] qx = g[0].split(" ");
                    String[] qxxxx = qx[4].split("/"); 
                    h +=g[1]+"\n";
                    if(!qxxxx[5].equals(null)){
                        System.out.println("qxxxxxxxxxxxx nom: "+qxxxx[5]);
                    PropertyDocument property = (PropertyDocument) wbdf.getEntityDocument(qxxxx[5]);
                    //System.out.println("qxxxxxxxxxxxx nom: "+property.getLabels().toString());
                    if(!property.getLabels().toString().equals(null)){
                        String[] nom = property.getLabels().toString().split("\"");
                        st.setNom(nom[1]);
                    }else{
                        st.setNom("text indisponible");
                    }

                    st.setDescription(g[1]);
                    rep.statements.add(st);
                    }
                   // System.out.println("value nom: "+nom[1]);
                }
            }
            //rep.setDescription(h);
             list.add(rep);

        }
        }
        return list;
    }



    public String recherche( String rech) throws MalformedURLException, IOException, MediaWikiApiErrorException{

        URL url = new URL(" https://wdaqua-qanary.univ-st-etienne.fr/gerbil-execute/wdaqua-core1,%20QueryExecuter/");
       URL u1 = new URL("https://wdaqua-core1.univ-st-etienne.fr/gerbil/") ;
               String data1= "query="+rech+"query&lang=fr&kb=student";
        String data = "query="+rech+"&lang=fr&kb=etudiant";
        HttpURLConnection conn =  (HttpURLConnection) u1.openConnection();
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
        System.out.println("url : "+resultBuf.toString());
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
       // System.out.println(resultBuf.toString());
        return null;

    }

    public void stockageDonnee() throws MediaWikiApiErrorException, SAXException, IOException, ParserConfigurationException{
        String siteIri = "https://wdaqua-biennale-design.univ-st-etienne.fr/wikibase/index.php/";
        WebResourceFetcherImpl.setUserAgent("Wikidata Toolkit EditOnlineDataExample");

        ApiConnection con = new ApiConnection("https://wdaqua-biennale-design.univ-st-etienne.fr/wikibase/api.php");

        /**
         * recuperation de la liste des stages
         */
        String file = "C:\\Users\\Nianfo\\Documents\\NetBeansProjects\\GroupeMixeur\\src\\main\\resources\\static\\stage.xml";

        ParserDom parser = new ParserDom(file);
        List<Stage> list = new ArrayList();
        //list =  parser.lecture();

        try {
            //Put in the first place the user with which you created the bot account
            //Put as password what you get when you create the bot account
            con.login("Souleymane", "robotsolo@69bdaeabeoaef55j0i6i5994ose641hq");
        } catch (LoginFailedException e) {
            e.printStackTrace();
        }
         WikibaseDataFetcher wbdf = new WikibaseDataFetcher(con, siteIri);
         ItemDocument mixeur = (ItemDocument) wbdf.getEntityDocument("Q889");
        /**
         * Ecriture des donnees sur wikidata
         */
        WikibaseDataEditor wbde = new WikibaseDataEditor(con, siteIri);

        PropertyDocument description = (PropertyDocument) wbdf.getEntityDocument("P245");
        PropertyDocument propose = (PropertyDocument) wbdf.getEntityDocument("P295");


        System.out.println(description.getLabels());
        ItemIdValue noid = ItemIdValue.NULL; // used when creating new items
        Statement statement1 = StatementBuilder
                .forSubjectAndProperty(noid, description.getPropertyId())
                .withValue(Datamodel.makeStringValue("Stage proposé par le Mixeur de saint-Etienne")).build();

        Statement statement2 = StatementBuilder
                .forSubjectAndProperty(noid, propose.getPropertyId())
                .withValue(mixeur.getItemId()).build();

        ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid)
                .withLabel("Intership 1", "en")
                .withLabel("Stage 1", "fr")
                .withStatement(statement1)
                .withStatement(statement2)
                .build();
        try {
            ItemDocument newItemDocument = wbde.createItemDocument(itemDocument,"Statement created by our roboSolo");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("Created Florence Garrelie");
        //System.out.println("Created statement: Florence Garrelie travaille Laboratoire Huber Curien");
        System.out.println(itemDocument.getItemId().getId());
    }

    public void recupererDonnees() throws MediaWikiApiErrorException, MediaWikiApiErrorException{

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

        WikibaseDataFetcher wbdf = new WikibaseDataFetcher(con, siteIri);
        ItemDocument laboratoireHC = (ItemDocument) wbdf.getEntityDocument("Q939");
        System.out.println(laboratoireHC.getEntityId());
        System.out.println(laboratoireHC.getLabels());
       // System.out.println(laboratoireHC.getStatementGroups().get(0));
        //System.out.println(laboratoireHC.getDescriptions());

    }
    public String QXXX(String rech) throws MalformedURLException, ProtocolException, IOException{

        URL url = new URL(" https://wdaqua-qanary.univ-st-etienne.fr/gerbil-execute/wdaqua-core1,%20QueryExecuter/");
       //URL u1 = new URL("http://www.wikidata.org/entity/");
        String data = "query="+rech+"&lang=fr&kb=etudiants";
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

                String[] tab = resultBuf.toString().split(":");
                int i;
                for(i=0;i<tab.length;i++){
                    System.out.println(tab[i]+":");
                }
       // System.out.println(resultBuf.toString());
        return resultBuf.toString();
    }

    public void testxml(){

        /*
        String textrech[] = text.split(" ");
         System.out.println("entreprise :"+textrech[0]);
                  System.out.println("entreprise :"+textrech[1]);


        String entreprisess[] = {"La Fabrique","17a7"};
        String localisation[] = {"adresse","lieu","localisation","emplacement"};
        String entrepristrouver = "" ;
        String adresse="";
        int k = 0;
        int k2 = 0;
        int l = 0;
        int k1 = 0;
        for(k=0;k<textrech.length;k++){
            System.out.println("entreprise : trouver 1");
            for(k1=0;k1<entreprisess.length;k1++){
                System.out.println("entreprise : trouver 2");
                System.out.println("entreprisetex :"+textrech[k]);
                System.out.println("entreprise :"+entreprisess[k1]);
                if(entreprisess[k1].equals(textrech[k])){
                    System.out.println("entreprise : trouver 3");
                    entrepristrouver = textrech[k];
                    for(k2=0;k2<textrech.length;k2++){
                        System.out.println("entreprise : trouver 4");
                        for(l=0;l<localisation.length;l++){
                            System.out.println("entreprise : trouver 5");
                            if(textrech[k2].equals(localisation[l])){
                                    adresse = textrech[k2];
                                   System.out.println("entreprise : trouver");
                            }
                        }
                    }

                }

            }
        }

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = factory.newDocumentBuilder();
        final org.w3c.dom.Document document= builder.parse(new File("C:\\Users\\Nianfo\\Documents\\NetBeansProjects\\GroupeMixeur\\src\\main\\resources\\templates\\entreprise.xml"));

        final org.w3c.dom.Element racine = document.getDocumentElement();

        List <Entreprises> lesEntreprises = new ArrayList();

        NodeList lesEntreprisexml = racine.getChildNodes();
        //System.out.println("rancine name :"+racine.getNodeName());
       int i;
       for(i=0;i<lesEntreprisexml.getLength();i++){
        //System.out.println("enfant nom :"+lesEntreprisexml.item(i).getNodeName());

           if(lesEntreprisexml.item(i).getNodeType() == Node.ELEMENT_NODE) {

                Entreprises entreprises = new Entreprises();

                Node unEntreprise = lesEntreprisexml.item(i);
                //System.out.println("entreprise :"+unEntreprise.getNodeName());

                NodeList contentEntreprise = unEntreprise.getChildNodes();
                int j;
                for(j=0; j<contentEntreprise.getLength();j++){


                    if(contentEntreprise.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        String nom = contentEntreprise.item(j).getNodeName();
                        //System.out.println("nom :"+nom);
                        switch(nom){

                            case "nom":
                                entreprises.setNom(contentEntreprise.item(j).getTextContent());
                                //System.out.println("nom :"+entreprises.getNom());
                            break;
                            case "description":
                                entreprises.setDescription(contentEntreprise.item(j).getTextContent());
                            break;
                            case "adresse":
                                entreprises.setAdresse(contentEntreprise.item(j).getTextContent());
                            break;
                            case "site":
                                entreprises.setSite(contentEntreprise.item(j).getTextContent());
                                break;
                            case "contact":
                               /* NodeList nodes = contentEntreprise.item(j).getChildNodes();
                                Node node = nodes.item(1);

                                Node node1 = nodes.item(3);

                                entreprises.setContact(node.getTextContent(),node1.getTextContent());*/

                              /*  break;

                        }

                    }
                }

            lesEntreprises.add(entreprises);
            }	
       }*/

      /* for(int j=0;j<lesEntreprises.size();j++){
           if(lesEntreprises.get(j).getNom().equals(entrepristrouver)){
               if(!adresse.equals("")){ 
                   model.addAttribute("votreRecherche",lesEntreprises.get(j).getAdresse() );
                   model.addAttribute("Nom",lesEntreprises.get(j).getNom() );
                   System.out.println("entreprise : trouver encore");
               }
           }
       }
     */
    }
}
