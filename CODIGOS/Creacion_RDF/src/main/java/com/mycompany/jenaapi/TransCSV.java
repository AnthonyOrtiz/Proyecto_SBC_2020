package com.mycompany.jenaapi;


import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jheys
 */
public class TransCSV {

    public String autores;
    public String titulo;
    public String fecha;
    public String subtitulo;
    public String volume;
    public String issu;
    public String citaciones;
    public String doi;
    public String url;
    public String resumen;
    public String keywords;
    public String publicado;
    public String lenguaje;
    public String tipoDocumento;
    public String ied;
    public String foundinDetails;
    public String foundingText;
    
    public TransCSV() {

    }

    public TransCSV(String autores, String titulo, String fecha, String subtitulo, String volume, String issu, String citaciones, String doi, String url, String resumen, String keywords, String publicado, String lenguaje, String tipoDocumento, String ied, String foundinDetails, String foundingText) {
        this.autores = autores;
        this.titulo = titulo;
        this.fecha = fecha;
        this.subtitulo = subtitulo;
        this.volume = volume;
        this.issu = issu;
        this.citaciones = citaciones;
        this.doi = doi;
        this.url = url;
        this.resumen = resumen;
        this.keywords = keywords;
        this.publicado = publicado;
        this.lenguaje = lenguaje;
        this.tipoDocumento = tipoDocumento;
        this.ied = ied;
        this.foundinDetails = foundinDetails;
        this.foundingText = foundingText;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getIssu() {
        return issu;
    }

    public void setIssu(String issu) {
        this.issu = issu;
    }

    public String getCitaciones() {
        return citaciones;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCitaciones(String citaciones) {
        this.citaciones = citaciones;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getPublicado() {
        return publicado;
    }

    public void setPublicado(String publicado) {
        this.publicado = publicado;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getIed() {
        return ied;
    }

    public void setIed(String ied) {
        this.ied = ied;
    }

    public String getFoundinDetails() {
        return foundinDetails;
    }

    public void setFoundinDetails(String foundinDetails) {
        this.foundinDetails = foundinDetails;
    }

    public String getFoundingText() {
        return foundingText;
    }

    public void setFoundingText(String foundingText) {
        this.foundingText = foundingText;
    }

    @Override
    public String toString() {
        return "TransCSV{" + "autores=" + autores + ", titulo=" + titulo + ", fecha=" + fecha + ", subtitulo=" + subtitulo + ", volume=" + volume + ", issu=" + issu + ", citaciones=" + citaciones + ", doi=" + doi + ", url=" + url + ", resumen=" + resumen + ", keywords=" + keywords + ", publicado=" + publicado + ", lenguaje=" + lenguaje + ", tipoDocumento=" + tipoDocumento + ", ied=" + ied + ", foundinDetails=" + foundinDetails + ", foundingText=" + foundingText + '}';
    }    
    
    public List<TransCSV> leer(String csv) {
         
        List<TransCSV> datosPubli = new ArrayList<>();
        TransCSV dataPaper;
        String foundText = "";
        String[] foundDetails;
        String[] aux1;
        String[] aux2;
        String[] linea = null;
        int inicio = 0; 
        int cont = 1;
        
                
        try {
            CSVReader lector = new CSVReader(new FileReader(csv), ';');
            linea = lector.readNext();

            while ((linea = lector.readNext()) != null) {
                

                dataPaper = new TransCSV();
                
                dataPaper.setAutores(linea[0]);
                dataPaper.setTitulo(linea[2]);
                dataPaper.setFecha(linea[3]);
                dataPaper.setSubtitulo(linea[4]);
                dataPaper.setVolume(linea[5]);
                dataPaper.setIssu(linea[6]);
                dataPaper.setCitaciones(linea[11]);
                dataPaper.setDoi(linea[12]);
                dataPaper.setUrl(linea[13]);
                dataPaper.setResumen(linea[16]);
                dataPaper.setKeywords(linea[18]);
                dataPaper.setPublicado(linea[30]);
                dataPaper.setLenguaje(linea[39]);
                dataPaper.setTipoDocumento(linea[41]);
                dataPaper.setIed(linea[45]);
//                dataPaper.setFoundinDetails(linea[46].replace(", ", " ").split(";"));
                
                dataPaper.setFoundinDetails(linea[46]);
                if (linea[24] != null) {
                    foundText =linea[24]+"\n";
                }
                if (linea[25] != null) {
                    foundText += linea[25];
                }
                dataPaper.setFoundingText(foundText);
//                System.out.println(dataPaper.toString());
                datosPubli.add(dataPaper);
            }
            
            
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException io) {
            System.err.println(io.getMessage());
        }
        
        return datosPubli;
         
     }
    
   
    
    
}
