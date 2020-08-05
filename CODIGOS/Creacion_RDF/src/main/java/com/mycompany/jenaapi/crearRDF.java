/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jenaapi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFWriter;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.DC;

/**
 *
 * @author antho
 */
public class crearRDF {
    public static void main(String[] args) {
        String ied ="";
        String subTitulo ="";
        
        String [] foundDetails;
        String [] aux1;
        String [] aux2;
        String [] aux3;
        String [] aux4;
        String [] autores;
        String [] keywords;
        
        TransCSV ejecutar = new TransCSV();
        List<TransCSV> csv = new ArrayList();
        
        csv = ejecutar.leer("C:/Users/antho/OneDrive/Escritorio/script-python/scopus.csv");
        
        Model model = ModelFactory.createDefaultModel();
        
        int n = 0;
        
        try {
            File f = new File("Keywords_Graphs_2.rdf");
            
            FileOutputStream os = new FileOutputStream(f);
            
            //prefijo para CLASES creadas en la ontología
            String dataPrefix = "http://utpl.edu.ec/lod/publicCOVID/data/";
            model.setNsPrefix("data", dataPrefix);
            //prefijo para PROPIEDADES creadas
            String propdesPrefix = "http://utpl.edu.ec/lod/publicCOVID/myOnto/";
            model.setNsPrefix("myOnto", propdesPrefix);
            Model myOntoModel = ModelFactory.createDefaultModel();

            //prefijo para dublin
            String dublincore = "http://purl.org/dc/terms/";
            model.setNsPrefix("dc", dublincore);
            Model dcModel = ModelFactory.createDefaultModel();
            //prefijo para schema
            String schema = "http://schema.org/";
            model.setNsPrefix("schema", schema);
            Model schemaModel = ModelFactory.createDefaultModel();
            //prefijo para fabio
            String fabio = "http://purl.org/spar/fabio/";
            model.setNsPrefix("fabio", fabio);
            Model fabioModel = ModelFactory.createDefaultModel();
            //Fijar Prefijo para otros vocabularios como DBPedia que no están directamente incorporados en Jena
            String rdfs = "https://www.w3.org/2000/01/rdf-schema/";
            model.setNsPrefix("rdfs", rdfs);
            Model rdfsModel = ModelFactory.createDefaultModel();
            //prefijo bibo
            String bibo = "http://purl.org/ontology/bibo/";
            model.setNsPrefix("bibo", bibo);
            Model biboModel = ModelFactory.createDefaultModel();
            
            String dbPedia = "http://dbpedia.org/resource/";
            model.setNsPrefix("dbc", dbPedia);
            Model dbPediaModel = ModelFactory.createDefaultModel();
            
            
            for (TransCSV objeto : csv) {
                
                Resource referencs;
                
                ied = "";
                subTitulo = "";
                
                ied = objeto.getIed().replace("-", "");
                ied.replaceAll(".", "");
                
                subTitulo = objeto.getSubtitulo().replaceAll(" ", "_");

                Resource source = model.createResource(dataPrefix + subTitulo)
                        .addProperty(RDF.type, fabioModel.getResource(fabio + "Journal"))
                        .addProperty(FOAF.name, objeto.getSubtitulo());
                
                Resource article = model.createResource(dataPrefix + ied)
                        .addProperty(RDF.type, dcModel.getResource(dublincore + "BibliographicResource"))
                        //.addProperty(dcModel.getProperty(dublincore + "creator"),objeto.getAutores())
                        .addProperty(DCTerms.title, objeto.getTitulo())
                        .addProperty(dcModel.getProperty(dublincore + "date"), objeto.getFecha())
                        .addProperty(DCTerms.source, source)
                        .addProperty(myOntoModel.getProperty(propdesPrefix + "volume"), objeto.getVolume())
                        .addProperty(myOntoModel.getProperty(propdesPrefix + "issue"), objeto.getIssu())
                        .addProperty(myOntoModel.getProperty(propdesPrefix + "citeyBy"), objeto.getCitaciones())
                        .addProperty(DCTerms.identifier, objeto.getDoi())
                        .addProperty(schemaModel.getProperty(schema + "url"), "<"+objeto.getUrl()+">")
                        .addProperty(rdfsModel.getProperty(rdfs + "comment"), objeto.getResumen())
                        //.addProperty(schemaModel.getProperty(schema + "keywords"), objeto.getKeywords())
                        .addProperty(DCTerms.publisher, objeto.getPublicado())
                        .addProperty(dcModel.getProperty(dublincore + "language"), objeto.getLenguaje())
                        .addProperty(DCTerms.type, objeto.getTipoDocumento())
                        .addProperty(DCTerms.identifier, objeto.getIed())                       
//                        .addProperty(myOntoModel.getProperty(propdesPrefix + "fundinDetails"), "objeto.getFoundinDetails()")
                        .addProperty(myOntoModel.getProperty(propdesPrefix + "fundinText"), objeto.getFoundingText());
                
                autores = objeto.getAutores().split(",");
                for (int i = 0; i < autores.length; i++) {
                    article.addProperty(dcModel.getProperty(dublincore + "creator"),autores[i]);
                }
                
                keywords = objeto.getKeywords().split(";");
                for (int i = 0; i < keywords.length; i++) {
                    article.addProperty(schemaModel.getProperty(schema + "keywords"),keywords[i]);
                }
                
                if (!"x".equals(objeto.getFoundinDetails())) {
               
                    foundDetails = objeto.getFoundinDetails().split(";");
                    
                    for (int i = 0; i < foundDetails.length; i++) {
                        aux1 = foundDetails[i].split("=");
                        if(aux1.length > 1){
                        referencs = model.createResource(dataPrefix + aux1[0]);
                        referencs.addProperty(RDF.type, dcModel.getResource(schema + "Organization"));
                            System.out.println(aux1.length);
                        
//                        if (aux1.length != 1) {
//                            aux2 = aux1[1].split(",");
//                            
//                            for (int j = 0; j < aux2.length; j++) {
//                                referencs.addProperty(DCTerms.subject, "<"+aux2[j]+">");
//                            }
//                        }
//                        
//                        if (aux1.length > 2) {
//                            aux3 = aux1[2].split(",");
//                            
//                        }
//                        
//                        for (int j = 0; j < aux1.length; j++) {
//                                referencs.addProperty(DCTerms.subject, "<"+aux2[j]+">");
//                        }
                        if (!"[]".equals(aux1[1])) {
                            aux2 = aux1[1].split(",");
                            for (int j = 0; j < aux2.length; j++) {
                                referencs.addProperty(dbPediaModel.getProperty(dbPedia + "location"), "<"+aux2[j]+">");
                            }
                        }
                        
                        if (!"[]".equals(aux1[2])) {
                            aux3 = aux1[2].split(",");
                            for (int j = 0; j < aux3.length; j++) {
                                referencs.addProperty(RDF.type, "<"+aux3[j]+">");
                            }
                        }
                        
                        if (!"[]".equals(aux1[3])) {
                            aux4 = aux1[2].split(",");
                            for (int j = 0; j < aux4.length; j++) {
                                referencs.addProperty(DCTerms.subject, "<"+aux4[j]+">");
                            }
                        }
                        
                    article.addProperty(myOntoModel.getProperty(propdesPrefix + "fundinDetails"), referencs);
                        }
                    }
                    
                }
//                System.out.println("fila: "+n);
                n++;
            }
            model.write(System.out);
            model.write(System.out, "N3");
            RDFWriter writer = model.getWriter("RDF/XML");
            writer.write(model, os, "");
            //Close model
            model.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
