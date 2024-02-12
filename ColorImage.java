import java.io.File;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/***************************
 * CSI 2120 - Assignment 1 *
 *                         *
 * Ben Miller - 300297574  *
 * Arin Barak - 300280812  *
 *                         *
 ***************************/



public class ColorImage {
    
    private int width;
    private int height;
    private int depth;  // bits per pixel

    private Pixel[][] pixelMap;
    
    private static int DEFAULT_DEPTH = 8;
    private static int DEFAULT_REDUCED_DEPTH = 3;

    private BufferedImage image;
    private String filename;



    public ColorImage( String filename ) {
        // https://www.tutorialspoint.com/how-to-get-pixels-rgb-values-of-an-image-using-java-opencv-library
        try{
            image = ImageIO.read( new File( filename ) );
        }catch(Exception e){
            e.printStackTrace();
        }

        this.filename = filename;

        height = image.getHeight();
        width = image.getWidth();
        depth = DEFAULT_DEPTH;

        pixelMap = new Pixel[height][width];

        // loop through all pixels, separate the RGB values
        // to put into an int array of 3 elements 
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {

                int pixel = image.getRGB(y,x);
                Color color = new Color(pixel, true);

                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                pixelMap[x][y] = new Pixel(red, green, blue);
            }
        }

        reduceColour( DEFAULT_REDUCED_DEPTH );
    	
    }


    public int[] getPixel( int row, int col ){
        return pixelMap[row][col].getArray();
    }


    public void reduceColour( int d ){
        for( int r=0; r<height; r++ ){
            for( int c=0; c<width; c++ ){
                pixelMap[r][c] = bitShiftPixel( pixelMap[r][c] , d); 
            }
        }
        this.depth = d;
    }


    private Pixel bitShiftPixel(Pixel pixel, int d ){
        
        int red = pixel.r;
        int green = pixel.g;
        int blue = pixel.b;

        int newRed = red >> ( depth - d );
        int newGreen = green >> ( depth - d );
        int newBlue = blue >> ( depth - d );

        return new Pixel(newRed, newGreen, newBlue);
    }

    public int getWidth(){ return width; }

    public int getHeight(){ return height; }

    public int getDepth(){ return depth; }

    public String getFilename(){ return filename; }

}
