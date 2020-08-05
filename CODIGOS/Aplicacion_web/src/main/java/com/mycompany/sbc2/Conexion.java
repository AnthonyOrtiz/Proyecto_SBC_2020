/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sbc2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.eclipse.rdf4j.model.impl.SimpleIRI;
import org.eclipse.rdf4j.model.impl.SimpleLiteral;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryEvaluationException;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 *
 * @author Danilo Alejandro Ochoa Hidalgo
 */
public class Conexion {

    private static Logger logger = (Logger) LoggerFactory.getLogger(Conexion.class);
// Why This Failure marker
    private static final Marker WTF_MARKER = MarkerFactory.getMarker("WTF");
// GraphDB 
    private static final String GRAPHDB_SERVER = "http://localhost:7200/";
    private static final String REPOSITORY_ID = "sbc2";

    private static String query;

    public static RepositoryConnection getRepositoryConnection() {
        Repository repository = new HTTPRepository(GRAPHDB_SERVER, REPOSITORY_ID);
        repository.init();
        RepositoryConnection repositoryConnection = repository.getConnection();
        return repositoryConnection;
    }

    static {
        query = "PREFIX schema: <http://schema.org/>\n"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "PREFIX dc: <http://purl.org/dc/terms/>\n"
                + "SELECT  * \n"
                + "WHERE {\n"
                + "	?paper rdf:type dc:BibliographicResource;\n"
                + "     dc:creator ?autor.\n"
                + "} ";
        
    }
    
//    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
//PREFIX schema: <http://schema.org/>
//PREFIX dbc: <http://dbpedia.org/resource/>
//PREFIX myOnto: <http://utpl.edu.ec/lod/publicCOVID/myOnto/>
//PREFIX dc: <http://purl.org/dc/terms/>
//select DISTINCT ?organizacion (COUNT(*) AS ?financiadores) where { 
//	?paper rdf:type dc:BibliographicResource;
//        myOnto:fundinDetails ?organizacion.
//} GROUP BY ?organizacion
//    static {
//        query = "PREFIX schema: <http://schema.org/>\n"
//                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
//                + "PREFIX dc: <http://purl.org/dc/terms/>\n"
//                + "SELECT  * \n"
//                + "select DISTINCT ?organizacion (COUNT(*) AS ?financiadores) where { \n"
//                + "	?paper rdf:type dc:BibliographicResource;\n"
//                + "     myOnto:fundinDetails ?organizacion.\n"
//                + "} GROUP BY ?organizacion ";
//        
//    }
    
    public static List<HashMap<String, String>> query(RepositoryConnection repositoryConnection) {
        TupleQuery tupleQuery = repositoryConnection
                .prepareTupleQuery(QueryLanguage.SPARQL, query);
        TupleQueryResult result = null;

        List<HashMap<String, String>> respuesta = new ArrayList<>();
        try {
            result = tupleQuery.evaluate();
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleIRI paper = (SimpleIRI) bindingSet.getValue("paper");
                SimpleLiteral autor = (SimpleLiteral) bindingSet.getValue("autor");

                HashMap<String, String> resultado = new HashMap<String, String>();

                resultado.put("paper", paper.stringValue());
                resultado.put("autor", autor.stringValue());

                respuesta.add(resultado);
                
            }
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    qee.getStackTrace().toString(), qee);
        } finally {
            result.close();
        }
        return respuesta;
    }

}
