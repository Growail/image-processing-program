/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisis.progra.test;

import static java.lang.Math.abs;
import java.util.Random;

/**
 *
 * @author Steven
 */
public class RGB implements Constants{
    
    int R;
    int G;
    int B;
    int X;
    int Y;

    public RGB(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }
    
    
    

    public RGB(int R, int G, int B, int X, int Y) {
        this.R = R;
        this.G = G;
        this.B = B;
        this.X = X;
        this.Y = Y;
        this.toString();
    }

    public float getR() {
        return R;
    }

    public float getG() {
        return G;
    }
    public float getB() {
        return B;
    }
    
    @Override
    public String toString() {
        return "RGB{" + "X=" + X + ", Y=" + Y + ", R=" + R + ", G=" + G + ", B=" + B + '}';
    }
    
    
    public int compareTo(RGB pixel)
    {
        return (int) Math.round(Math.sqrt(Math.abs(
                Math.pow((this.R - pixel.R),2) + 
                Math.pow((this.G - pixel.G),2) + 
                Math.pow((this.B - pixel.B),2)
                )));
    }
    
    public void mutate()
    {
        
        Random r = new Random();
        int red = ChromosomePool.targetMatrix.IMG[this.X][this.Y].R;
        int green = ChromosomePool.targetMatrix.IMG[this.X][this.Y].G;
        int blue = ChromosomePool.targetMatrix.IMG[this.X][this.Y].B;
        if(r.nextInt(100)<PROBABILITY_OF_MUTATION)
        {
            int tempR = r.nextInt(256);
            if (abs(red - this.R)>abs(red-tempR))
                this.R = tempR;
        }
        if(r.nextInt(100)<PROBABILITY_OF_MUTATION)
        {
            int tempG = r.nextInt(256);
            if (abs(green - this.G)>abs(green-tempG))
                this.R = tempG;
        }
        if(r.nextInt(100)<PROBABILITY_OF_MUTATION)
        {
            int tempB = r.nextInt(256);
            if (abs(blue - this.B)>abs(blue-tempB))
                this.R = tempB;
        }
    }
    
    
    
    
}
