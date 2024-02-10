public class Pixel {
    
    int r;
    int g;
    int b;

    public Pixel(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int[] getArray(){ return new int[]{r, g, b};  }


}
