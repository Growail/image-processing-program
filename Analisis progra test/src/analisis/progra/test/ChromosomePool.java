/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisis.progra.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Steven
 */
public class ChromosomePool implements Constants{
    
    Matrix [] GenePool;
    Matrix m1 = new Matrix();
    BufferedImage targetIMG;
    public static Matrix targetMatrix;
    

    public ChromosomePool() throws IOException {
        
        
        
        m1.img2matriz();
        m1.pic = ImageIO.read(new File(FALSE_IMAGE_DIRECTORY));
        this.GenePool = new Matrix[GENE_POOL_SIZE];
        targetIMG= ImageIO.read(new File(TARGET_IMAGE));
        targetMatrix = new Matrix();
        targetMatrix.img2matriz();
        targetMatrix.searchKeypoints(new Matrix());
        
    }

    public void fillPool() throws IOException
    {
        Matrix m = null;
        for (int i = 0; i< GENE_POOL_SIZE; i++)
        {
            //m = ChromosomePool.targetMatrix;
            m = new Matrix();
            m.img2matriz();
            m.rndImg();
            GenePool[i] = m;
            
            //m.matriz2img();
            
        }
        //GenePool[0] =  ChromosomePool.targetMatrix;
    }
    
    public void showPool() throws IOException
    {
        for (int i = 0; i< GENE_POOL_SIZE; i++)
        {
            //System.out.println("Matrix #" + i + " added to the pool");
            GenePool[i].matriz2img();
            
        }
    }
    
    
    public void SortByBest() throws IOException
    {
        for (Matrix element : GenePool) 
        {
            element.compareToWithAlgorithm(targetMatrix, CURRENT_IMAGE_ANALYSIS_ALGORITHM );
        }
        Arrays.sort(GenePool);
        //DESCOMENTAR ESTO PARA IR IMPRIMIENDO MIENTRAS SE ITERA
        //System.out.println("Sorted by fitness");
        //Matrix elementPrintable : GenePool
        for (int i=0; i<TOP_OF_GEN;i++) 
        {
            System.out.println(this.GenePool[i].getDistance());
            //System.out.println(elementPrintable.getDistance());
        }
        //System.out.println("");
    }
            
    
    
    public static int compare(Matrix m1, int algorithm) throws IOException
    {
        switch (algorithm)
        {
            case 1:
                return m1.compareToWithAlgorithm(targetMatrix, 1);
                
        }
        return -1;
        
    }
    
    
    public void simulateGenetics() throws IOException
    {
        int pointer;
        Random r = new Random();
        int parent1, parent2;
        int generation_printable_local = GENERATION_PRINTABLE;
        for (int genCounter = 0; genCounter<GENERATIONS; genCounter++)
        {
            System.out.println(genCounter);
            pointer = FIRST_QUARTER;
            for (int mateCounter = 0; mateCounter<AMOUNT_OF_MATINGS; mateCounter++)
            {
            parent2 = (r.nextInt(3));
            switch (parent2)
            {
                case 0:
                    parent2 = r.nextInt(FIRST_QUARTER)+1;
                case 1:
                    parent2 = r.nextInt(FIRST_QUARTER)+FIRST_QUARTER;
                case 2:
                    parent2 = r.nextInt(FIRST_QUARTER)+ SECOND_QUARTER;
                case 3:
                    parent2 = r.nextInt(FIRST_QUARTER)+ THIRD_QUARTER;
            }
            Matrix cub = new Matrix();
            cub.modified = true;
            cub.IMG = this.GenePool[0].reproduce(this.GenePool[parent2]) ;
            cub.searchKeypoints(cub);
            this.GenePool[pointer] = cub;
            pointer+=1;
            }
            if (r.nextInt(100)<PROBABILITY_OF_MUTATION)
            {
                this.GenePool[r.nextInt(FIRST_QUARTER)+r.nextInt(3)+1].mutate();
                this.GenePool[r.nextInt(FIRST_QUARTER)+r.nextInt(3)+1].modified = true;
            }
            SortByBest();
            
            if (genCounter == generation_printable_local)
            {
                this.GenePool[0].matriz2img();
                generation_printable_local += GENERATION_PRINTABLE;
            }
            
        }
    }
    
        
}
