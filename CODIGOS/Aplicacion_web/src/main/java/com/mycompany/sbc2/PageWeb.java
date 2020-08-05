/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sbc2;;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import spark.ModelAndView;
import static spark.Spark.get;
import static spark.Spark.staticFileLocation;
import spark.template.freemarker.FreeMarkerEngine;
/**
 *
 * @author Danilo Alejandro Ochoa Hidalgo
 */
public class PageWeb {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /* RUTA PARA ENCONTRAR RECURSOS ***************************************/
        staticFileLocation("/public");
        // Inicio
  
        get("/", (req, res) -> {
            String vista = "index.ftl";
            Map<String, Object> modelo = new HashMap<>();
//            Conexion objConsultorRDF = new Conexion();
            RepositoryConnection repositoryConnection = Conexion.getRepositoryConnection();
            String datos = "";
            int contador = 0;
            for (HashMap<String, String> dato : Conexion.query(repositoryConnection)) {
                System.out.println(contador);
                datos += "<tr><td>" + dato.get("paper") + "</td><td>" + dato.get("autor") + "</tr>";
                contador++;
            }
            modelo.put("datos", datos);
            return new ModelAndView(modelo, vista);
        }, new FreeMarkerEngine());
    }
}