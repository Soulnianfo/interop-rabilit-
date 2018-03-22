/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groupe.mixeur;

import java.io.IOException;
import java.text.ParseException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Adel
 */
public class Interop {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ParseException, org.xml.sax.SAXException {

        System.out.println("Lancement");
        String file = "C:\\Users\\Nianfo\\Documents\\NetBeansProjects\\GroupeMixeur\\src\\main\\resources\\static\\stage.xml";

        ParserDom parser = new ParserDom(file);
        parser.lecture();

    }

}
