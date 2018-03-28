/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groupe.mixeur;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.ItemDocumentBuilder;
import org.wikidata.wdtk.datamodel.helpers.StatementBuilder;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.datamodel.interfaces.TimeValue;
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
public class InsertionDonneesWikidata {

    public InsertionDonneesWikidata() {
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ParseException, org.xml.sax.SAXException, MediaWikiApiErrorException {

        System.out.println("Lancement");

        String file = "C:\\Users\\Nianfo\\Documents\\NetBeansProjects\\Mixeur\\GroupeMixeur\\src\\main\\resources\\static\\stage.xml";
        ParserDom parser = new ParserDom(file);
        /*List<Stage> list = new ArrayList();
        list = parser.lecture();*/

        InsertionDonneesWikidata wiki = new InsertionDonneesWikidata();
        //wiki.insertStage(list.get(0));
        List<Evenement> events = wiki.getEvents();
        
       /* int i;
        for(i=0;i<events.size();i++){
        System.out.println("Nom :"+events.get(i).getNom());
        System.out.println("Date :"+events.get(i).getDate());
        System.out.println("Organisateur :"+events.get(i).getOrganisateur());
        System.out.println("Description :"+events.get(i).getDescription());
        System.out.println("Lieu :"+events.get(i).getLieu());
            System.out.println("num :"+i);
        System.out.println("******************************************************************");
        
        }
        wiki.insertEvent(events.get(0));*/
       
       String cheminCsv = "C:\\Users\\Nianfo\\Documents\\NetBeansProjects\\Mixeur\\GroupeMixeur\\src\\main\\resources\\static\\les_entreprises.csv";

              
        ParserCSV p = new ParserCSV(cheminCsv);
        List<Entreprise> list = p.getEntrepise();
        System.out.println("Nom :"+list.get(1).nom);
        System.out.println("Date :"+list.get(1).date_creat);
        System.out.println("Present :"+list.get(1).present);
        System.out.println("tel :"+list.get(1).tel);
        System.out.println("Lieu :"+list.get(1).local);
        System.out.println("act :"+list.get(1).typeActs[0]);
        System.out.println("act :"+list.get(1).typeActs[1]);
        System.out.println("gerant :"+list.get(1).getGerants());
            System.out.println("num :"+0);
        //wiki.insertEntreprise(list.get(1));
    }

    public void insertStage(Stage stg) throws SAXException, IOException, ParserConfigurationException, MediaWikiApiErrorException {

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
        /*String qxx = recherche(stg.entreprise);
                    //System.out.println("Mixeur nom "+stg.entreprise);
                    System.out.println("Woline "+qxx);
                    if(qxx.equals("null")){
                       // creerEntreprise(stg.entreprise);
                    }*/
        String idEntyti = "";
        WikibaseDataFetcher wbdf = new WikibaseDataFetcher(con, siteIri);
        List<WbSearchEntitiesResult> entities = wbdf.searchEntities(stg.entreprise);
        for (WbSearchEntitiesResult entity : entities) {
            System.out.println("Id Woline :" + entity.getEntityId());
            idEntyti = entity.getEntityId();
        }

        ItemDocument entreprise = (ItemDocument) wbdf.getEntityDocument(idEntyti);
        /**
         * Ecriture des donnees sur wikidata
         */
        WikibaseDataEditor wbde = new WikibaseDataEditor(con, siteIri);

        PropertyDocument description = (PropertyDocument) wbdf.getEntityDocument("P245");
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
                .withValue(Datamodel.makeStringValue(stg.Desc.toString())).build();
        System.err.println(" Description :" + description.getPropertyId());
        System.out.println("Stage Description " + stg.Desc);

        Statement statement2 = StatementBuilder
                .forSubjectAndProperty(noid, propose.getPropertyId())
                .withValue(entreprise.getItemId()).build();

        Statement statement3 = StatementBuilder.
                forSubjectAndProperty(noid, intitule.getPropertyId())
                .withValue(Datamodel.makeStringValue(stg.Intituler)).build();

        Statement statement4 = StatementBuilder.
                forSubjectAndProperty(noid, compete.getPropertyId())
                .withValue(Datamodel.makeStringValue(stg.Competences)).build();
        Statement statement5 = StatementBuilder.
                forSubjectAndProperty(noid, adres.getPropertyId())
                .withValue(Datamodel.makeStringValue(stg.Localisation)).build();
        System.err.println(" Adresse :" + adres.getPropertyId());

        Statement statement6 = StatementBuilder.
                forSubjectAndProperty(noid, gratifica.getPropertyId())
                .withValue(Datamodel.makeStringValue(stg.Gratification)).build();

        Statement statement7 = StatementBuilder.
                forSubjectAndProperty(noid, duree.getPropertyId())
                .withValue(Datamodel.makeStringValue(stg.Duree)).build();

        ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid)
                .withLabel(stg.Intituler, "en")
                .withLabel(stg.Intituler, "fr")
                .withStatement(statement1)
                .withStatement(statement2)
                .withStatement(statement3)
                //.withStatement(statement4)
                .withStatement(statement7)
                .withStatement(statement5)
                .withStatement(statement6)
                .build();
        try {
            ItemDocument newItemDocument = wbde.createItemDocument(itemDocument, "Statement created by our roboSolo");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertEvent(Evenement event) throws MediaWikiApiErrorException{
        
        
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

        
        String idEntyti = "";
        WikibaseDataFetcher wbdf = new WikibaseDataFetcher(con, siteIri);
        List<WbSearchEntitiesResult> entities = wbdf.searchEntities(event.getOrganisateur());
        for (WbSearchEntitiesResult entity : entities) {
            System.out.println("Id Woline :" + entity.getEntityId());
            idEntyti = entity.getEntityId();
            
        }

        ItemDocument entreprise = (ItemDocument) wbdf.getEntityDocument(idEntyti);
        /**
         * Ecriture des donnees sur wikidata
         */
        WikibaseDataEditor wbde = new WikibaseDataEditor(con, siteIri);

        PropertyDocument description = (PropertyDocument) wbdf.getEntityDocument("P245");
       
        PropertyDocument intitule = (PropertyDocument) wbdf.getEntityDocument("P233");
        
        PropertyDocument instancede = (PropertyDocument) wbdf.getEntityDocument("P186");
        
        ItemDocument Event = (ItemDocument) wbdf.getEntityDocument("Q885");
       
        PropertyDocument adres = (PropertyDocument) wbdf.getEntityDocument("P257");
      
        PropertyDocument date = (PropertyDocument) wbdf.getEntityDocument("P358");
        
        PropertyDocument organisateur = (PropertyDocument) wbdf.getEntityDocument("P381");
        
      
        ItemIdValue noid = ItemIdValue.NULL; // used when creating new items
        
        Statement statement1 = StatementBuilder
                .forSubjectAndProperty(noid, instancede.getPropertyId())
                .withValue(Event.getItemId()).build();
        
        Statement statement2 = StatementBuilder.
                forSubjectAndProperty(noid, intitule.getPropertyId())
                .withValue(Datamodel.makeStringValue(event.getNom())).build();
        
        Statement statement3 = StatementBuilder.
                forSubjectAndProperty(noid, organisateur.getPropertyId())
                .withValue(Datamodel.makeStringValue(event.getOrganisateur())).build();
        
        Statement statement4 = StatementBuilder.
                forSubjectAndProperty(noid, date.getPropertyId())
                .withValue(Datamodel.makeStringValue(event.getDate())).build();

        Statement statement5 = StatementBuilder.
                forSubjectAndProperty(noid, adres.getPropertyId())
                .withValue(Datamodel.makeStringValue(event.getLieu())).build();
        
        Statement statement6 = StatementBuilder
                .forSubjectAndProperty(noid, description.getPropertyId())
                .withValue(Datamodel.makeStringValue(event.getDescription())).build();
        System.err.println(" Description :" + description.getPropertyId());
        //System.out.println("Stage Description " + stg.Desc);

      

        

        ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid)
                .withLabel(event.getNom(), "en")
                .withLabel(event.getNom(), "fr")
                .withStatement(statement6)
                .withStatement(statement5)
                .withStatement(statement4)
                .withStatement(statement3)
                .withStatement(statement2)
                .withStatement(statement1)
                .build();
        try {
            ItemDocument newItemDocument = wbde.createItemDocument(itemDocument, "Statement created by our roboSolo");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public List<Evenement> getEvents() throws IOException {
        List<Evenement> list = new ArrayList();
        Document doc = (Document) Jsoup.connect("http://www.le-mixeur.org/agenda/").get();
        Elements Nodes = doc.select("div.ch-Grid_Mixeur_2");

        for (Element node : Nodes) {
            String url = node.select("div.back_img .ev-item .ev-data a").attr("href");

            Document docevent = (Document) Jsoup.connect(url).get();
            //System.out.println("URL: "+url);
            Elements events = docevent.select("div.ch-Grid_Mixeur_2");
            String jourMois = "";
            String jourMoiss = "";
            String organis = "";
            String phone = "";
            String titre = "";
            String heure = "";
            String categorie = "";
            String descript = "";
            String date = "";
            String lieu = "";
            String jopm = "";
            
            for (Element event : events) {
                Evenement evenement = new Evenement();
                jourMois = event.select("div.cont_box .sortable .start_date .start_date").text();
                date = event.select("div.full_date .full_date").text();
                titre = event.select("div.cont_box .sortable .event_title .event_title").text();
                organis = event.select("div.organizer_name .organizer_name a").text();
                categorie = event.select("div.categories .categories a").text();
                descript = event.select("div.event_description .event_description").text();
                lieu = event.select("div.place_direction .place_direction").text();
                //phone = event.select("div.organizer_phone .organizer_phone .ch-phone").text();
                jourMoiss = event.select("div.organizer_email .organizer_email a").text();
                heure = event.select("div.full_time .full_time").text();
                evenement.setDate(jourMois+" de "+heure);
                evenement.setNom(titre);
                evenement.setLieu(lieu);
                String[] org = organis.split(" ");
                int i;
                organis = "";
                for(i=2;i<org.length;i++){
                    if(i==org.length-1){
                        organis += org[i];
                    }else{
                    organis += org[i]+" ";
                    }
                }
                
                String[] descrp = descript.split(" ");
                
                int j;
               
                if(descript.length()>400){
                     descript = "";
                    for(j=0;j<15;j++){
                        if(j==14){
                           descript += descrp[j]+"...";
                        }else{
                        descript += descrp[j]+" ";
                        }
                    }
                }
                if(descript.equals("")) descript ="Absence de description";
                evenement.setOrganisateur(organis);
                evenement.setDescription(descript);
                jopm = jourMois + "\n" + titre + "\n" + organis + "\n" + phone + "\n de " + heure + " : " + descript;
                //System.out.println("JOURMOIS: "+organis);
                //System.out.println("JOURMOIS: "+JourMoiss);
                if(!evenement.getNom().equals("") && !evenement.getOrganisateur().equals("")){
                list.add(evenement);
                }
            }
        }
        return list;
    }

    
    
        public void insertEntreprise(Entreprise entreprise) throws SAXException, IOException, ParserConfigurationException, MediaWikiApiErrorException{
        
        String siteIri = "https://wdaqua-biennale-design.univ-st-etienne.fr/wikibase/index.php/";
        WebResourceFetcherImpl.setUserAgent("Wikidata Toolkit EditOnlineDataExample");

        ApiConnection con = new ApiConnection("https://wdaqua-biennale-design.univ-st-etienne.fr/wikibase/api.php");
        
        try{
            //Put in the first place the user with which you created the bot account
            //Put as password what you get when you create the bot account
             con.login("Souleymane", "robotsolo@69bdaeabeoaef55j0i6i5994ose641hq");
        } catch (LoginFailedException e) {
            e.printStackTrace();
        }
            WikibaseDataFetcher wbdf = new WikibaseDataFetcher(con, siteIri);
            /**
             * Ecriture des donnees sur wikidata
             */
            WikibaseDataEditor wbde = new WikibaseDataEditor(con, siteIri);
        String qxx = "";//recherche(entreprise.getNom());
        //System.out.println("Mixeur nom "+stg.entreprise);
        List<WbSearchEntitiesResult> entities = wbdf.searchEntities(entreprise.getNom());
        for (WbSearchEntitiesResult entity : entities) {
            System.out.println("Id Woline :" + entity.getEntityId());
            qxx = entity.getEntityId();
            
        }
        
        System.out.println("Woline "+qxx);
        
        if(qxx.equals("")){
           
            
            PropertyDocument instanceOf = (PropertyDocument) wbdf.getEntityDocument("P186");           

            ItemIdValue noid = ItemIdValue.NULL; // used when creating new items
            
            //Recuperation de la classe Entreprise
            ItemDocument ent = (ItemDocument) wbdf.getEntityDocument("Q887");
            
            Statement inst = StatementBuilder
                    .forSubjectAndProperty(noid, instanceOf.getPropertyId())
                    .withValue(ent.getItemId()).build();        
            
            ItemDocumentBuilder uneEnt = ItemDocumentBuilder.forItemId(noid);
            
            uneEnt.withLabel(entreprise.getNom(), "en")
                  .withLabel(entreprise.getNom(), "fr")
                  .withStatement(inst);
            
            //Ajout de la presentation de l'entreprise
            if(!entreprise.getPresent().equals("")){
                PropertyDocument presentation = (PropertyDocument) wbdf.getEntityDocument("P245");
                Statement present = StatementBuilder
                    .forSubjectAndProperty(noid, presentation.getPropertyId())
                    .withValue(Datamodel.makeStringValue(entreprise.getPresent())).build();
                
                uneEnt.withStatement(present);
            }
            
            //Ajout de(s) type(s) d'activités
            if( entreprise.getTypeActs().length > 0){
                PropertyDocument type_act = (PropertyDocument) wbdf.getEntityDocument("P237");
                        
                List<Statement> tab= new ArrayList();
                
                //On recupere l'item Activité
                ItemDocument activite = (ItemDocument) wbdf.getEntityDocument("Q888");
                
                //insertion des types d'activité
                for (int i = 0; i < entreprise.getTypeActs().length; i++) {
                    StatementBuilder typeAct = StatementBuilder
                        .forSubjectAndProperty(noid, type_act.getPropertyId());
                    
                    List<WbSearchEntitiesResult> entities2 = wbdf.searchEntities(entreprise.getTypeActs()[i]);
                    //si cette activité existe déjà en BC
                    if( entities2.size() > 0){
                        for (WbSearchEntitiesResult entity : entities2){
                            ItemDocument ger = (ItemDocument) wbdf.getEntityDocument(entity.getEntityId());
                            typeAct.withValue(ger.getItemId());
                        }   
                    }
                    else{
                        ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid)
                        .withLabel(entreprise.getTypeActs()[i], "en")
                        .withLabel(entreprise.getTypeActs()[i], "fr")
                        .withStatement((Statement) StatementBuilder.
                            forSubjectAndProperty(noid, instanceOf.getPropertyId())
                            .withValue(activite.getItemId())
                            .build())
                        .build();
                        try {
                            ItemDocument newItemDocument = wbde.createItemDocument(itemDocument,"Statement created by our roboSolo");
                            typeAct.withValue(newItemDocument.getItemId());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } 
                    }  
                    
                    tab.add(typeAct.build());
                }
                
                for (int i = 0; i < tab.size(); i++) {
                    Statement statement = tab.get(i);
                    uneEnt.withStatement(statement);
                }
            }
            
            //Ajout de(s) gerant(s)
            if(entreprise.gerants.length > 0){
                PropertyDocument gerants = (PropertyDocument) wbdf.getEntityDocument("P385");
                //on recupere l'item Personne
                ItemDocument personne = (ItemDocument) wbdf.getEntityDocument("Q886");

                
                
                List<Statement> tab = new ArrayList();

                //insertion des gerants
                for (int i = 0; i < entreprise.getGerants().length; i++) {
                    StatementBuilder listGerant = StatementBuilder.forSubjectAndProperty(noid, gerants.getPropertyId());
                    
                    List<WbSearchEntitiesResult> entities1 = wbdf.searchEntities(entreprise.getGerants()[i]);
                    //si cette activité existe déjà en BC
                    if( entities1.size() > 0){
                        for (WbSearchEntitiesResult entity : entities1){
                            ItemDocument ger = (ItemDocument) wbdf.getEntityDocument(entity.getEntityId());
                            listGerant.withValue(ger.getItemId());
                        }   
                    }
                    else{
                        ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid)
                        .withLabel(entreprise.getGerants()[i], "en")
                        .withLabel(entreprise.getGerants()[i], "fr")
                        .withStatement((Statement) StatementBuilder.
                            forSubjectAndProperty(noid, instanceOf.getPropertyId())
                            .withValue(personne.getItemId())
                            .build())
                        .build();

                        try {
                            ItemDocument newItemDocument = wbde.createItemDocument(itemDocument,"Statement created by our roboSolo");
                            listGerant.withValue(newItemDocument.getItemId());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } 
                    }
                    
                    tab.add(listGerant.build());
                }

                
                for (int i = 0; i < tab.size(); i++) {
                    Statement statement = tab.get(i);
                    uneEnt.withStatement(statement);
                }
               
            }
            
            //Ajout de la date de création
            if(!entreprise.getDate_creat().equals("")){
                PropertyDocument date_creat = (PropertyDocument) wbdf.getEntityDocument("P240");
                String tab[] = entreprise.getDate_creat().split("/");
                int jour = parseInt(tab[0]);
                int mois = parseInt(tab[1]);
                long annee = parseLong(tab[2]);
                Statement date = StatementBuilder
                    .forSubjectAndProperty(noid, date_creat.getPropertyId())
                    .withValue(Datamodel.makeTimeValue(annee, (byte) mois, (byte) jour, TimeValue.CM_GREGORIAN_PRO)).build();
                      
               uneEnt.withStatement(date);
            }
            
            //Ajout de site web
            if(!entreprise.getSite_web().equals("")){
                PropertyDocument site_web = (PropertyDocument) wbdf.getEntityDocument("P241");
                Statement site = StatementBuilder.
                    forSubjectAndProperty(noid, site_web.getPropertyId())
                    .withValue(Datamodel.makeStringValue(entreprise.getSite_web())).build();
                
                uneEnt.withStatement(site);
            }
            
            //Ajout d'adresse Email
            if(!entreprise.getE_mail().equals("")){
                PropertyDocument email = (PropertyDocument) wbdf.getEntityDocument("P253");
                Statement adMail = StatementBuilder.
                    forSubjectAndProperty(noid, email.getPropertyId())
                    .withValue(Datamodel.makeStringValue(entreprise.getE_mail())).build();
               uneEnt.withStatement(adMail);
            }
            
            //Ajout du numero de telephone
            if(!entreprise.getTel().equals("")){
                PropertyDocument tel = (PropertyDocument) wbdf.getEntityDocument("P255");
                Statement phone = StatementBuilder.
                    forSubjectAndProperty(noid, tel.getPropertyId())
                    .withValue(Datamodel.makeStringValue(entreprise.getTel())).build();
              
               uneEnt.withStatement(phone);
            }
            
            //Ajout du compte Facebook
            if(!entreprise.getFbk().equals("")){
                PropertyDocument fbk = (PropertyDocument) wbdf.getEntityDocument("P270");
                Statement face = StatementBuilder.
                    forSubjectAndProperty(noid, fbk.getPropertyId())
                    .withValue(Datamodel.makeStringValue(entreprise.getFbk())).build();
              uneEnt.withStatement(face);
            }
            
            //Ajout du compte Twitter
            if(!entreprise.getTwt().equals("")){
                PropertyDocument twt = (PropertyDocument) wbdf.getEntityDocument("P272");
                Statement twiter = StatementBuilder.
                    forSubjectAndProperty(noid, twt.getPropertyId())
                    .withValue(Datamodel.makeStringValue(entreprise.getTwt())).build();
              uneEnt.withStatement(twiter);
            }
            
            //Ajout du compte Google
            if(!entreprise.getGgl().equals("")){
                PropertyDocument ggl = (PropertyDocument) wbdf.getEntityDocument("P370");
                Statement google = StatementBuilder.
                    forSubjectAndProperty(noid, ggl.getPropertyId())
                    .withValue(Datamodel.makeStringValue(entreprise.getTwt())).build();
              uneEnt.withStatement(google);
            }
            
            //Ajout d'adresse Postal
            if(!entreprise.getLocal().equals("")){
                PropertyDocument adres = (PropertyDocument) wbdf.getEntityDocument("P257");
                Statement loc = StatementBuilder.
                    forSubjectAndProperty(noid, adres.getPropertyId())
                    .withValue(Datamodel.makeStringValue(entreprise.getLocal()))
                    .build();
               uneEnt.withStatement(loc);
            }
            
            ItemDocument entToAdd = uneEnt.build();
        
                try {
                    ItemDocument newItemDocument = wbde.createItemDocument(entToAdd,"Statement created by our roboSolo");
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        
        else {
            System.out.println("Cette Entreprise existe déjà en BC");
        }
  }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public String recherche(String rech) throws MalformedURLException, IOException, MediaWikiApiErrorException {

        URL url = new URL(" https://wdaqua-qanary.univ-st-etienne.fr/gerbil-execute/wdaqua-core1,%20QueryExecuter/");

        String data = "query=" + rech + "&lang=en&kb=dbpedia";
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setInstanceFollowRedirects(true);
        conn.setRequestProperty("Content-length", String.valueOf(data.length()));

        conn.setDoOutput(true);
        conn.setDoInput(true);

        try (DataOutputStream output = new DataOutputStream(conn.getOutputStream())) {
            output.writeBytes(data);
        }

        // "Post data send ... waiting for reply");
        int code = conn.getResponseCode(); // 200 = HTTP_OK
        // System.out.println("Response    (Code):" + code);
        //System.out.println("Response (Message):" + conn.getResponseMessage());

        DataInputStream input = new DataInputStream(conn.getInputStream());
        String c;
        StringBuilder resultBuf = new StringBuilder();
        while ((c = input.readLine()) != null) {
            resultBuf.append(c);
            //resultBuf.append("\n");
        }
        input.close();
        System.out.println(resultBuf.toString());
        String txt = resultBuf.toString();
        txt = txt.replace("{", " ");
        txt = txt.replace("}", " ");
        txt = txt.replace("\\", " ");
        txt = txt.replace(">", " ");
        txt = txt.replace("<", " ");
        String[] tab = txt.split(" ");
        String txt2;
        String[] qxxx;
        int i;
        String ht = "http://www.wikidata.org/entity/";
        int htsize = ht.length();
        for (i = 0; i < tab.length; i++) {
            if (htsize < tab[i].length()) {
                if (ht.equals(tab[i].substring(0, htsize))) {
                    txt2 = tab[i].replace("/", " ");
                    qxxx = txt2.split(" ");
                    System.out.println(qxxx[qxxx.length - 1]);
                    return qxxx[qxxx.length - 1];
                }
            }
        }
        System.out.println(resultBuf.toString());
        return "null";

    }

}
