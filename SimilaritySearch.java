import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
/***************************
 * CSI 2120 - Assignment 1 *
 *                         *
 * Ben Miller - 300297574  *
 * Arin Barak - 300280812  *
 *                         *
 ***************************/


public class SimilaritySearch {
    

	/*  - you must specify the image filename, the image dataset directory
		- java SimilaritySearch q01.jpg imageDataset2_15_20
		- you can assume that the search is done on 3-bit color reduced images
		- The program must print the name of the 5 most similar images to the query image
    */


    public static void main(String[] args) throws Exception{
        ColorImage query_image = new ColorImage(args[0]);
        String dataset_directory = args[1];
        int num_of_pics = 5;

        //for the results
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<Double> comparison_array= new ArrayList<Double>();

        //creating the histogram for the query image.
        ColorHistogram d_histogram;
        ColorHistogram q_histogram = new ColorHistogram(3);
        q_histogram.setImage(query_image);
        q_histogram.createHistogram();
        double[] query_hist = q_histogram.getHistogram();

        System.out.println("Query image: "+ args[0]);
        System.out.println("Dataset directory: "+ args[1]);
        System.out.println("Finding the 5 most similar images.");


        //reading the files in the dataset directory.
        ArrayList<String> files = new ArrayList<String>();
        File directory_path = new File(dataset_directory);
        File filesList[] = directory_path.listFiles();

        for(File file: filesList){
        	//filtering the .txt files
        	if (file.isFile() && file.getName().endsWith(".txt")){
        		files.add(file.getName());

        		//create its histogram and do the comparison.
        		d_histogram = new ColorHistogram(file.getName());
				double comparing_result = q_histogram.compare(d_histogram);

				//Add the first element to the comparison array and result (to save the image file names).
				if(comparison_array.size() == 0){
					comparison_array.add(comparing_result);
					result.add(file.getName());
				}
				else{
					for(int k=0; k<num_of_pics && k<comparison_array.size(); k++){

						if(comparing_result>comparison_array.get(k)){
							comparison_array.add(k, comparing_result);
							result.add(k, file.getName());

							//if there are more number of elements than the desired number of pics, delete the least similar image (the image at the end).
							if(comparison_array.size()>num_of_pics){
								comparison_array.remove(num_of_pics);
								result.remove(num_of_pics);
							}
							break;
						}
					}
				}
        	}
        }

        //print the results
		for(int i=0; i<5; i++){
			System.out.println("#"+(5-i)+" image file name: "+result.get(i));
		}
        
    }

}