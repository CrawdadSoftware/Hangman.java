
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 *
 * @author Crawdad Software
 */
public class Narzedzia 
{
    public static JLabel loadImage(String resource)
    {
        BufferedImage image;
        try
        {
            InputStream inputStream = Narzedzia.class.getResourceAsStream(resource);
            image = ImageIO.read(inputStream);
            return new JLabel(new ImageIcon(image));
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e);
        }
        return null;
    }
    
    public static void updateImage(JLabel imageContainer, String resource)
    {
        BufferedImage image;
        
        try
        {
            InputStream inputStream = Narzedzia.class.getResourceAsStream(resource);
            image = ImageIO.read(inputStream);
            imageContainer.setIcon(new ImageIcon(image));
        }
        catch(IOException e)
        {
            System.out.println("Error: " + e);
        }
    }
    
    public static Font createFont(String resource)
    {
        // Zdobądź ścieżkę dostępu do czcionki
        String filePath = Narzedzia.class.getClassLoader().getResource(resource).getPath();
        
        // robimy proces walidacji wykrywający buga w ścieżce dostępu
        if(filePath.contains("%20"))
        {
            filePath = filePath.replaceAll("%20", " ");
        }
        
        // Tworzenie czcionki
        try 
        {
            File customFontFile = new File(filePath);
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, customFontFile);
            return customFont;
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e);
        }
        return null;
    }
    
    public static String hideWords(String word)
    {
        String hiddenWord = "";
        for(int i = 0; i < word.length(); i++)
        {
            if(!(word.charAt(i) == ' '))
            {
                hiddenWord += "*";
            }
            else
            {
                hiddenWord += " ";
            }
        }
        return hiddenWord;
    }
}
