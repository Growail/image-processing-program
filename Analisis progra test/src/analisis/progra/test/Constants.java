/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisis.progra.test;

/**
 *
 * @author Steven
 */
public interface Constants {
    
    int CURRENT_IMAGE_ANALYSIS_ALGORITHM = 3; //esto es para un switch que activa alguno de los algoritmos de fitness
    int GENE_POOL_SIZE = 10; //cantidad de cromosomas en la genepool
    int GENERATIONS = 100; //la cantidad de iteraciones del algoritmo genético
    int FIRST_QUARTER = (GENE_POOL_SIZE/4)*1; //esto es para separar la genepool en cuartos para cruces
    int SECOND_QUARTER = (GENE_POOL_SIZE/4)*2; //la idea es mantener genes de los individuos menos aptos
    int THIRD_QUARTER = (GENE_POOL_SIZE/4)*3; //y así evitar degeneracion de la poblacion
    String TARGET_IMAGE = "C:\\Users\\Steven\\Desktop\\Progra análisis images\\ProyectIMG.jpg"; //imagen meta
    String MODIFIED_IMAGE_NAME ="\\ProyectIMGMod"; //para naming conventions
    String MODIFIED_IMAGE_DIRECTORY ="C:\\Users\\Steven\\Desktop\\Progra análisis images\\Modified"; //para saber donde ponerlas
    String ORGANIZED_IMAGE_DIRECTORY = "C:\\Users\\Steven\\Desktop\\Progra análisis images\\Organized"; //ditto
    String FALSE_IMAGE_DIRECTORY = "C:\\Users\\Steven\\Pictures\\ProyectIMGMod-250724343.jpg"; //ditto
    int PROBABILITY_OF_MUTATION = 15; //la probabilidad de que un individuo mute
    int AMOUNT_OF_MATINGS = GENE_POOL_SIZE/3; //debe ser menor que GENE_POOL_SIZE/2
    int GENERATION_PRINTABLE = GENERATIONS/10;
    int TOP_OF_GEN = -1; //para imprimir el top de individuos de cada generación, -1 para no imprimir nada y apresurar el proceso
    int DIFFERENCE_BETWEEN_TONES = 335; //para distinguir entre 2 colores, distancia considerada en el RGB
}
