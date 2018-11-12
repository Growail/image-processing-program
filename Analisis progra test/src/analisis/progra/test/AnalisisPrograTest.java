/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisis.progra.test;

import static analisis.progra.test.Constants.TARGET_IMAGE;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Steven
 */
public class AnalisisPrograTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ChromosomePool CP = new ChromosomePool();
        CP.targetIMG = ImageIO.read(new File(TARGET_IMAGE));
        CP.fillPool();
        
        /**Matrix m = new Matrix();
        m.img2matriz();
        //m.toString();
        m.searchKeypoints(new Matrix());
        Matrix m2 = new Matrix();
        m2.img2matriz();
        m2.rndImg();
        m2.searchKeypoints(new Matrix());
        m2.CompareKeyPoints();
        
        m.selfMade(m2);**/
        
        
        
        CP.SortByBest();
        CP.simulateGenetics();
        CP.showPool();
        
    }
    
}
