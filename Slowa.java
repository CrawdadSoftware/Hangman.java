
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Crawdad Software
 */
public class Slowa 
{
    private HashMap<String, String[]> listaSlow;
    private ArrayList<String> kategorie;
    
    public Slowa()
    {
        try
        {
          listaSlow = new HashMap<>();
          kategorie = new ArrayList<>();
          
          // Przejdź do ścieżki dostępu
          String filePath = getClass().getClassLoader().getResource(WspolneZmienne.DATA_PATH).getPath();
          if(filePath.contains("%20")) filePath = filePath.replaceAll("%20", " ");
          BufferedReader reader = new BufferedReader(new FileReader(filePath));
          
          // Pętla wykonująca się przez każdą linijkę w Data.txt
          String line;
          while((line = reader.readLine()) != null)
          {
              String[] parts = line.split(",");
              String category = parts[0];
              kategorie.add(category);
              String values[] = Arrays.copyOfRange(parts, 1, parts.length);
              listaSlow.put(category, values);
          }
        }catch(IOException e)
        {
            System.out.println("Error: " + e);
        }
    }
    
    public String[] loadChallenge()
    {
        Random rand = new Random();
        
        String category = kategorie.get(rand.nextInt(kategorie.size()));
        String[] categoryValues = listaSlow.get(category);
        String word = categoryValues[rand.nextInt(categoryValues.length)];
        
        return new String[]{category.toUpperCase(), word.toUpperCase()};
    }
}