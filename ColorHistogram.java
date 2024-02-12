import java.io.File;
import java.lang.Math;


/***************************
 * CSI 2120 - Assignment 1 *
 *                         *
 * Ben Miller - 300297574  *
 * Arin Barak - 300280812  *
 *                         *
 ***************************/

public class ColorHistogram {

    private double[] histogram;
    private ColorImage image;
    

    private int d = 3;

    private int numberOfBins;

    private String filename;


    public ColorHistogram( int d ) {

        this.d = d;

        numberOfBins = (int) Math.pow(2, d * 3 );
        histogram = new double[ numberOfBins ];

    }


    //this constructor read a text file of pre-computed histogram and puts it in an ArrayList.
    public ColorHistogram( String imageFileName )throws Exception{

        image = new ColorImage( imageFileName );
        this.filename = imageFileName;

        d = image.getDepth();
        numberOfBins = (int) Math.pow(2, (d * 3) );
        histogram = new double[  numberOfBins];

        createHistogram();
    }




    public void setImage( ColorImage image ) {
        this.image = image;
        this.filename = image.getFilename();
        image.reduceColour( d );
    }


    public double[] getHistogram() {
        return histogram;
    }


    //the comparison of two histograms
    public double compare( ColorHistogram other ) {
        double result_num = 0;
        double[] other_hist = other.getHistogram();

        for(int i=0; i<numberOfBins; i++){
            result_num += Math.min(histogram[i], other_hist[i]);
        }
        return result_num;

    } 


    public void save( String fileName ) {

    }


    
    //building the histogram array
    public double[] createHistogram(){
    	int num_of_row = image.getHeight();
    	int num_of_column = image.getWidth();
    	int[] rgb_values = new int[3];
    	
    	//creating the frequency of the pixels
    	for(int r=0; r<num_of_row; r++){
    		for(int c=0; c<num_of_column; c++){
    			rgb_values = image.getPixel(r,c);
    			int index = (rgb_values[0] << (2*d)) + ((rgb_values[1] << d) + rgb_values[2]);
    			histogram[index] += 1;
    		}
    	}


    	//normalizing the bins in order to have the sum of all bins equal to 1.
    	for(int k=0; k<numberOfBins; k++){
    		histogram[k] = (histogram[k]/d);
    	}

    	return histogram;
    }

    public String getFilename(){
        return new File( filename ).getName();
    }


}