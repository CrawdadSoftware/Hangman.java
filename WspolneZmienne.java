import java.awt.*;
/**
 *
 * @author Crawdad Software
 */
public class WspolneZmienne 
{
    // Konfiguracja ścieżki dostępu
    public static final String DATA_PATH = "resource/Data.txt";
    public static final String IMAGE_PATH = "resource/1.png";
    public static final String FONT_PATH = "resource/Cartoonero.ttf";
    
    // Konfiguracja rozmiaru
    public static final Dimension FRAME_SIZE = new Dimension(540, 760);
    public static final Dimension BUTTON_PANEL_SIZE = new Dimension(FRAME_SIZE.width, (int)(FRAME_SIZE.height * 0.42));
    public static final Dimension RESULT_DIALOG_SIZE = new Dimension((int)(FRAME_SIZE.width/2), (int)(FRAME_SIZE.height/6));
    
    // Konfiguracja koloru
    public static final Color PRIMARY_COLOR = Color.decode("#14212D");
    public static final Color SECONDARY_COLOR = Color.decode("#FCA311");
    public static final Color BACKGROUND_COLOR = Color.decode("#101820");
}
