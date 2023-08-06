
import javax.swing.SwingUtilities;

/**
 *
 * @author Crawdad Software
 */
public class Aplikacja 
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new Wisielec().setVisible(true);
            }
        });
    }
}
