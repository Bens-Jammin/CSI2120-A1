import java.io.File;
import java.util.PriorityQueue;

/***************************
 * CSI 2120 - Assignment 1 *
 *                         *
 * Ben Miller - 300297574  *
 * Arin Barak - 300280812  *
 *                         *
 ***************************/


public class SimilaritySearch {
    

	/*  - you must specify the image filename, the image dataset directory
		- java SimilaritySearch imageDataset2_15_20\q01.jpg imageDataset2_15_20
		- you can assume that the search is done on 3-bit color reduced images
		- The program must print the name of the 5 most similar images to the query image
    */


    public static void main(String[] args) throws Exception{
        ColorImage query_image = new ColorImage(args[0]);
        String dataset_directory = args[1];
        int num_of_pics = 5;


        //creating the histogram for the query image.
        ColorHistogram q_histogram = new ColorHistogram(3);
        q_histogram.setImage(query_image);
        q_histogram.createHistogram();

        System.out.println("Query image: "+ args[0]);
        System.out.println("Dataset directory: "+ args[1]);
        System.out.println("Finding the "+num_of_pics+" most similar images.");

		// needs to be a max heap
        PriorityQueue<Pair> histograms = new PriorityQueue<>( );
        

        File[] directory = new File( dataset_directory ).listFiles();
        if( directory == null ) throw new Exception("No files in directory or directory doesn't exist.");


        // add histograms to heap, with relatedness as value
        for( File f : directory ){

            if( !f.getName().toLowerCase().endsWith(".jpg" ) ){
                continue;
            }

			// can't be the query image
			if( f.getName().toLowerCase().equals(q_histogram.getFilename().toLowerCase() ) ) continue;


            ColorHistogram h = new ColorHistogram( f.getPath() );
            double relatednessScore = q_histogram.compare( h );
            Pair pair = new Pair(h, relatednessScore);
            histograms.add( pair );


			if( histograms.size() > num_of_pics ){
				histograms.poll();
			}
        }




        //print the results
		for(int i=0; i<num_of_pics; i++){
            System.out.println( ""+( num_of_pics-i )+") "+histograms.poll().getImageFileName() );
		}        
    }


	static class Pair implements Comparable<Pair>{
		ColorHistogram histogram;
		double relatednessValue;
	
		public Pair( ColorHistogram h, double r ){
			histogram = h;
			relatednessValue = r;
		}
	
		public String getImageFileName(){ return histogram.getFilename(); }
	
		@Override
		public int compareTo(Pair other) {
			if (this.relatednessValue > other.relatednessValue){
				return 1;
			}else if ( this.relatednessValue == other.relatednessValue){
				return 0;
			}
			return -1;
		}
	}

}