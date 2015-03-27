package co.edu.uniandes.useritem.server;

public interface SimilarityMeasure extends java.io.Serializable{
    
    public double similarity(String[] x, String[] y);

}
