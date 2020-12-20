/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asciirenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author A
 */
public class AsciiRenderer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Initiating ASCII Renderer");
        char[] ascii = {'#', '$', 'U', 'Q', 'w',';','-', '.', ' '}; //ASCII Alternative - From left to right: dark to light
        
        BufferedImage img = null; 
        File f = null; 
  
        //read image 
        try
        { 
            f = new File("assets\\house.jpg"); 
            img = ImageIO.read(f); 
        } 
        catch(IOException e) 
        { 
            System.out.println(e); 
        } 
  
        //get image width and height 
        int width = img.getWidth(); 
        int height = img.getHeight(); 
        
        int area = width*height;
        
        //loading the image into a buffer
        int[][] pixels = new int[width][height];
        
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                pixels[i][j] = img.getRGB(i,j); 
            }
        }
        
        
        String asciiImage = "";
        double current = 0;
        int progress = 0;
        int loading = 0;
        int resolution = 5; //Resolution of the image, the lower the sharper
        
        //Converting the image
        for (int j = 0; j < height; j+=resolution*2){
            for (int i = 0; i < width; i+=(resolution)) {
                int r = (pixels[i][j]>>16) & 0xFF;
                int g = (pixels[i][j]>>8) & 0xFF;
                int b = (pixels[i][j]) & 0xFF;
                
                int gray = (int)(r*0.3 + g*0.59 + b*0.11); //Grayscaling the pixel
                
                asciiImage += convertPixelToAscii(ascii, gray);
                
                current++;
                
                loading = (int)((current/area)*100);
            }
            
            asciiImage += '\n';
            System.out.println("Image Conversion At : " + loading + "%");
        }
  
        System.out.println("\n\nImage Converted Successfully : \n\n\n" + asciiImage); //Print Image
    }
    
    public static char convertPixelToAscii(char[] chars, int pixelColor){
        int stepSize = 256/chars.length;
        int error = 256 - stepSize*chars.length;
        
        int lastStep = 0;
        char ascii = chars[0];
        
        for (int i = 0; i < chars.length; i++) {
            int currentStep = (stepSize*i + error);
            if(pixelColor > lastStep && pixelColor < (stepSize*i + error))
                ascii = chars[i]; 
            
            lastStep = currentStep;
        }
        
        return ascii;
    }
    
    public static void loading(int progress, double current, int complete, int step){
        int percentage = (int)((current/complete)*100);
        
        if(percentage >= step*progress){
            System.out.println("Image Conversion At : " + percentage + "%");
            progress++;
        }
    }
}
