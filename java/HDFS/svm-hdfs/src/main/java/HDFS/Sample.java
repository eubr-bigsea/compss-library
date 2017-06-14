package HDFS;


/**
 *
 * Created by Lucas Miguel S Ponce.
 * Student of master degree in Computer's Science
 * UFMG - 1/2017
 * Algorithm:  SVM using data from HDFS
 *
 */



import java.io.Serializable;
import java.util.ArrayList;

public class Sample implements Serializable{

    public ArrayList<double[]> Features = new ArrayList<double[]>();
    public ArrayList<Integer> Labels = new ArrayList<Integer>();
    public int id;

    public Sample() {
    }
    public void setID(int id){this.id = id;}
    public ArrayList<double[]> getFeatures() {
        return Features;
    }
    public double   getFeature(int m,int d){
        return Features.get(m)[d];
    }
    public double[] getFeatureAll(int m){
        return Features.get(m);
    }
    public int      getLabel(int d){
        return Labels.get(d);
    }
    public void setFeatures(ArrayList<double[]> features) {
        Features = features;
    }

    public ArrayList<Integer> getLabels() {
        return Labels;
    }

    public void setLabels(ArrayList<Integer> labels) {
        Labels = labels;
    }

    public int getSize(){

        return Labels.size();

    }
}
