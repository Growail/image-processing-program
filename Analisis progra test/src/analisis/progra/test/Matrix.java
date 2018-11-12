/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisis.progra.test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;

/**
 *
 * @author Steven
 */
public class Matrix implements Constants,Comparable<Matrix>{
    
    int Height;
    int Width;
    RGB[][] IMG;
    BufferedImage pic;
    int distance;
    ArrayList<RGB> keypoints = new ArrayList<RGB>();
    Boolean modified;
    
    public Matrix() throws IOException, IOException {
        
        pic = ImageIO.read(new File(TARGET_IMAGE));
        this.Width = pic.getWidth();
        this.Height = pic.getHeight();
        IMG = new RGB[pic.getHeight()][pic.getWidth()];
        distance = 0;
        keypoints = new ArrayList<RGB>();
        
    }
    
    //esto es para una sección donde necesito matrices un poco mas pequeñas
    public Matrix(int width, int height) throws IOException
    {
        pic = ImageIO.read(new File(TARGET_IMAGE));
        this.Width = width;
        this.Height = height;
        IMG = new RGB[height][width];
        distance = 0;
    }
    
    public void rndImg()
    {
    int red, green, blue;
    Random r = new Random();
    for(int i=0;i<this.Height;i++) 
    {
        for(int j=0;j<this.Width;j++)
        {
            
            red = r.nextInt(256);
            green = r.nextInt(256);
            blue = r.nextInt(256);
            IMG[i][j] = new RGB(red, green, blue,j,i);
        }
    }
    }
    public void img2matriz() throws IOException, NullPointerException{
    double[][] C;
    int RGBCode;
    int red, green, blue;
    for(int i=0;i<this.Height;i++) 
    {
        for(int j=0;j<this.Width;j++)
        {
            
            RGBCode = pic.getRGB(j, i);
            red = (RGBCode>> 16) & 0x000000FF;
            green = (RGBCode>>8 ) & 0x000000FF;
            blue = (RGBCode) & 0x000000FF;
            IMG[i][j] = new RGB(red, green, blue,j,i);
        }
    }     
    }
    
    public void matriz2img() throws IOException
    {
    BufferedImage image = new BufferedImage(this.Width, this.Height, 
        BufferedImage.TYPE_BYTE_INDEXED);
    Color newColor = null;
    int red, green, blue;
    for(int i=0; i<IMG.length; i++) {
        for(int j=0; j< IMG[0].length; j++) {
            RGB a = IMG[i][j];
            red = a.R;
            green = a.G;
            blue = a.B;
            newColor = new Color (red, green, blue);
            
            image.setRGB(j,i,newColor.getRGB());
        }
    }
    Random r = new Random();
    File output = new File(MODIFIED_IMAGE_DIRECTORY+MODIFIED_IMAGE_NAME 
            + r.nextInt()+ ".jpg" );
    ImageIO.write(image, "jpg", output);
    }

    @Override
    public String toString() {
        System.out.println("Matrix{" + "Height=" + Height + ", Weight=" + Width) ;
        for (int i = 0; i < this.Height; i++)
        {
            for (int j = 0; j < this.Width; j++)
            {
                System.out.println(this.IMG[i][j].toString());
                
        }
            
    }
    
    return "";
    
            }
    
    // ALGORITMO QUE FUNCIONA COMO UN SWITCH PARA ACTIVAR LOS DISTINTOS TIPOS DE ALGORITMOS DE FITNESS
    
    public int compareToWithAlgorithm(Matrix target, int algorithm) throws IOException
    {
        switch (algorithm)
        {
            case 1:
                return EuclideanDistance(target);
            case 2:
                this.searchKeypoints(target);
                return CompareKeyPoints();
            case 3:
                this.selfMade(target);
                
        }
        return -1;
        
    }
    
    
    // ALGORITMO DE DISTANCIA EUCLIDIANA
    public int EuclideanDistance (Matrix target)
    {
        double distanceFromTarget = 0;
        double distancePerPixel;
        RGB currentPixel, targetPixel;
        for(int i=0;i<this.Height;i++) 
        {
            for(int j=0;j<this.Width;j++)
            {
                distancePerPixel = IMG[i][j].compareTo(target.IMG[i][j]);
                distanceFromTarget += Math.pow(distancePerPixel,2);
                
            }
        }
        this.distance = (int) Math.round(Math.sqrt(distanceFromTarget));
        return this.distance;
    }
    
    
    // ALGORITMOS DEL MÉTODO DE KEYPOINTS
    
    public void searchKeypoints(Matrix target)
    {
        if (this.modified = true)
        {
        RGB currentPixel, topRight, topLeft, bottomRight, bottomLeft;
        int dtl, dtr, dbl, dbr;
        for(int i=1;i<this.Height-1;i++) 
        {
            for(int j=1;j<this.Width-1;j++)
            {
                currentPixel = IMG[i][j];
                topLeft = IMG[i-1][j-1];
                topRight = IMG[i+1][j-1];
                bottomLeft = IMG[i-1][j+1];
                bottomRight = IMG[i+1][j+1];
                dtl = currentPixel.compareTo(topLeft);
                dtr = currentPixel.compareTo(topRight);
                dbl = currentPixel.compareTo(bottomLeft);
                dbr = currentPixel.compareTo(bottomRight);
                if(dtl>DIFFERENCE_BETWEEN_TONES || dtr>DIFFERENCE_BETWEEN_TONES ||
                        dbl>DIFFERENCE_BETWEEN_TONES || dbr>DIFFERENCE_BETWEEN_TONES)
                {
                    this.keypoints.add(currentPixel);
                    this.modified = false;
                }
            }
        }
    }
        //return CompareKeyPoints();
    }
    
    public int CompareKeyPoints()
    {
        double unit = ChromosomePool.targetMatrix.keypoints.size();
        //System.out.println("cantidad de keypoints en la original: "+ unit);
        //System.out.println("cantidad de keypoints en esta: "+ this.keypoints.size());
        float percentage = (float) (1/unit*100);
        //System.out.println("porcentage de un elemento: "+ percentage);
        float similarity = 0;
        //System.out.println("porcentage de similitud inicial: "+ similarity);
        
        for (int i = 0; i<this.keypoints.size(); i++)
        {
            if (ChromosomePool.targetMatrix.keypoints.contains(keypoints.get(i)))
            {
                similarity += percentage;
            }
        }
        //esto es porque en la euclidiana se acomodan por menor distancia y aqui como es por mayor porcentage de
        //similitud tuve que darle vuelta, en el fondo funciona exactamente igual, es solo que para usar el
        //comparable es mucho mas facil así
        this.distance = -1*(Math.round(similarity));
        return this.distance;
        
    }
    
    // VARIACIÓN DEL ALGORITMO DE KEYPOINTS CON ENFOQUE PROBABILÍSTICO
    
    public int selfMade(Matrix target) throws IOException
    {
        Random r = new Random();
        int quarterWidth, quarterHeight, proportionWidth, proportionHeight;
        int similarity = 0;
        quarterWidth = this.Width/4;
        quarterHeight = this.Height/4;
        proportionWidth = this.Width/8;
        proportionHeight = this.Height/8;
        RGB anchorTl = new RGB(quarterWidth*1, quarterHeight*1);
        RGB anchorTR = new RGB(quarterWidth*3, quarterHeight*1);
        RGB anchorBL = new RGB(quarterWidth*1, quarterHeight*3);
        RGB anchorBR = new RGB(quarterWidth*3, quarterHeight*3);
        
        //el 4 aquí es porque esa es la cantidad de anclas en la imagen, si fueran más se cambia de acuerdo con eso
        for (int i = 0; i<4; i++)
        {
            if (r.nextInt(100)>25)
            {
                Matrix m1 = new Matrix(proportionWidth, proportionHeight);
                Matrix m2 = new Matrix(proportionWidth, proportionHeight);
                switch (i)
                {
                    case 0:
                        m1.getSubMatrix(anchorTl.X, anchorTl.Y, proportionWidth, proportionHeight, this, m1);
                        m2.getSubMatrix(anchorTl.X, anchorTl.Y, proportionWidth, proportionHeight, ChromosomePool.targetMatrix, m2);
                    case 1:
                        m1.getSubMatrix(anchorTR.X, anchorTR.Y, proportionWidth, proportionHeight, this, m1);
                        m2.getSubMatrix(anchorTR.X, anchorTR.Y, proportionWidth, proportionHeight, ChromosomePool.targetMatrix, m2);
                    case 2:
                        m1.getSubMatrix(anchorBL.X, anchorBL.Y, proportionWidth, proportionHeight, this, m1);
                        m2.getSubMatrix(anchorBL.X, anchorBL.Y, proportionWidth, proportionHeight, ChromosomePool.targetMatrix, m2);
                    case 3:
                        m1.getSubMatrix(anchorBR.X, anchorBR.Y, proportionWidth, proportionHeight, this, m1);
                        m2.getSubMatrix(anchorBR.X, anchorBR.Y, proportionWidth, proportionHeight, ChromosomePool.targetMatrix, m2);
                }
                
                similarity += m1.checkMatrixes(m1, m2);
                
                
            }
        }
        this.distance = similarity;
        return similarity;
    }
    
    public void getSubMatrix(int anchorX, int anchorY, int proprtionWidth, int proportionHeight, Matrix target, Matrix Objective)
    {
        for (int i = 0; i < Objective.Width; i++)
        {
            for (int j = 0; j < Objective.Height; j++)
            {
                Objective.IMG[i][j] = 
                        target.IMG[(anchorX - proprtionWidth/2) + i][(anchorY - proportionHeight/2) + j];
            }
        }
    }
    
    public int checkMatrixes(Matrix m1, Matrix m2)
    {
        int similarityCounter = 0;
        for (int i = 0; i < m1.IMG[0].length; i++)
        {
            for (int j = 0; j < m1.IMG.length; j++)
            {
                if (m1.IMG[i][j].compareTo(m2.IMG[i][j]) > DIFFERENCE_BETWEEN_TONES)
                {
                    similarityCounter += 1;
                }
            }
        }
        return similarityCounter;
    }
    
    
    //este override es porque en la clase ChromosomePool uso el comando Arrays.sort y ese siempre necesita que
    //los objetos en el array implementen la interfaz Comparable para acomodarlos, IMPORTANTE, si se cambian los 
    //valores aquí se cambia el orden en el que se acomoda
    @Override
    public int compareTo(Matrix other) {
        if(this.getDistance() > other.getDistance())
            return 1;
        else if (this.getDistance() == other.getDistance())
            return 0 ;
        return -1 ;
    }

    public int getDistance() {
        return this.distance;
    }
    
    public RGB[][] reproduce(Matrix mate) throws IOException
    {
        this.modified = true;
        Matrix cub = new Matrix();
        for(int i=0;i<this.Height;i++) 
        {
            for(int j=0;j<this.Width;j++)
            {
                if (i%2==0)
                    cub.IMG[i][j] = mate.IMG[i][j];
                else
                    cub.IMG[i][j] = this.IMG[i][j];
            }
        }/**
        if (ChromosomePool.compare(cub, CURRENT_IMAGE_ANALYSIS_ALGORITHM)
                >ChromosomePool.compare(mate, CURRENT_IMAGE_ANALYSIS_ALGORITHM))
            return cub.IMG;
        else
            return mate.IMG;**/
        return cub.IMG;
    }
    
    public void mutate()
    {
        this.modified = true;
        Random r = new Random();
        for(int i=0;i<this.Height;i++) 
        {
            for(int j=0;j<this.Width;j++)
            {
                if (r.nextInt(100)<PROBABILITY_OF_MUTATION)
                {
                    this.IMG[i][j].mutate();
                }
            }
        }
    }
    
    }


    
    
    
    
    

