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
    

    private int d;


    public ColorHistogram( int d ) {

        this.d = d;

        int numberOfBins = (int) Math.pow(2, d * 3 );
        histogram = new double[ numberOfBins ];

    }


    public ColorHistogram( String imageFileName ) {

        image = new ColorImage( imageFileName );


        int numberOfBins = (int) Math.pow(2, this.d * 3 );
        histogram = new double[ numberOfBins ];

    }


    public void setImage( ColorImage image ) {
        this.image = image;
        image.reduceColour( d );
    }


    public double[] getHistogram() {
        return histogram;
    }


    public double compare( ColorHistogram other ) {
        

        
        return 0; // temp
    } 


    public void save( String fileName ) {

    }





}