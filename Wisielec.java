
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Crawdad Software
 */
public class Wisielec extends JFrame implements ActionListener
{
    private final Slowa Slowa;
    private int incorrectGuesses;
    private String[] wordChallenge;
    private JLabel hangmanImage, categoryLabel, hiddenWordLabel, resultLabel, wordLabel;
    private JButton[] letterButtons;
    private JDialog resultDialog;
    private Font customFont;
    
    public Wisielec()
    {
        super("Gra w wisielca");
        setSize((Dimension) WspolneZmienne.FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(WspolneZmienne.BACKGROUND_COLOR);
        
        Slowa = new Slowa();
        wordChallenge = Slowa.loadChallenge();
        letterButtons = new JButton[26];
        customFont = Narzedzia.createFont(WspolneZmienne.FONT_PATH);
        wordChallenge = Slowa.loadChallenge();
        createResultDialog();
        
        addGuiComponents();
    }
    private void addGuiComponents()
    {
        // Obrazek wisielca
        hangmanImage = Narzedzia.loadImage(WspolneZmienne.IMAGE_PATH);
        hangmanImage.setBounds(0, 0, hangmanImage.getPreferredSize().width, hangmanImage.getPreferredSize().height);
        
        // Wyświetlenie kategorii
        categoryLabel = new JLabel(wordChallenge[0]);
        categoryLabel.setFont(customFont.deriveFont(30f));
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setOpaque(true);
        categoryLabel.setBackground(WspolneZmienne.SECONDARY_COLOR);
        categoryLabel.setBorder(BorderFactory.createLineBorder(WspolneZmienne.SECONDARY_COLOR));
        categoryLabel.setBounds
        (
                0,
                hangmanImage.getPreferredSize().height - 28,
                WspolneZmienne.FRAME_SIZE.width,
                categoryLabel.getPreferredSize().height
        );
        
        // Ukryte słowa
        hiddenWordLabel = new JLabel(Narzedzia.hideWords(wordChallenge[1]));
        hiddenWordLabel.setFont(customFont.deriveFont(64f));
        hiddenWordLabel.setForeground(Color.WHITE);
        hiddenWordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hiddenWordLabel.setBounds
        (
                0,
                categoryLabel.getY() + categoryLabel.getPreferredSize().height + 50,
                WspolneZmienne.FRAME_SIZE.width,
                hiddenWordLabel.getPreferredSize().height
        );
        
        // Przyciski słów
        GridLayout gridLayout = new GridLayout(4, 7);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds
        (
                -5,
                hiddenWordLabel.getY() + hiddenWordLabel.getPreferredSize().height,
                WspolneZmienne.BUTTON_PANEL_SIZE.width,
                WspolneZmienne.BUTTON_PANEL_SIZE.height
        );
        buttonPanel.setLayout(gridLayout);
        
        // Tworzenie przycisków liter
        for(char c = 'A'; c <= 'Z'; c++)
        {
            JButton button = new JButton(Character.toString(c));
            button.setFont(customFont.deriveFont(22f));
            button.setBackground(WspolneZmienne.PRIMARY_COLOR);
            button.setForeground(Color.WHITE);
            button.addActionListener(this); 
            
            int currentIndex = c - 'A';
            letterButtons[currentIndex] = button;
            buttonPanel.add(letterButtons[currentIndex]);

        }
        
        // Tworzenie przycisku resetu
        JButton resetButton = new JButton("Reset");
        resetButton.setFont(customFont.deriveFont(22f));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(WspolneZmienne.SECONDARY_COLOR);
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);
        
        //Tworzenie przycisku wyjścia z gry
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(customFont.deriveFont(22f));
        quitButton.setForeground(Color.WHITE);
        quitButton.setBackground(WspolneZmienne.SECONDARY_COLOR);
        quitButton.addActionListener(this);
        buttonPanel.add(quitButton);
        
        getContentPane().add(categoryLabel);
        getContentPane().add(hangmanImage);
        getContentPane().add(hiddenWordLabel);
        getContentPane().add(buttonPanel);
        
    }
    

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String command = e.getActionCommand();
        if(command.equals("Reset") || command.equals("Restart"))
        {
            resetGame();
            
             if(command.equals("Restart"))
             {
                 resultDialog.setVisible(false);
             }
        }
        else if(command.equals("Wyjście"))
        {
            dispose();
            return;
        }
        else
        {
            // Wyłącz przyciski
            JButton button = (JButton) e.getSource();
            button.setEnabled(false);
            
            // Sprawdź czy słowo zawiera traf użytkownika
            if(wordChallenge[1].contains(command))
            {
                // Wskazuje że użytkownik trafił dobrze
                button.setBackground(Color.GREEN);
                
                // Zaktualizuj ukryty tekst
                char[] hiddenWord = hiddenWordLabel.getText().toCharArray();
                
                for(int i = 0; i < wordChallenge[1].length(); i++)
                {
                    // Zaktualizuj na poprawną literę
                    if(wordChallenge[1].charAt(i) == command.charAt(0))
                    {
                        hiddenWord[i] = command.charAt(0);
                    }
                }
                
                // Zaktualizuj hiddenWordLabel
                hiddenWordLabel.setText(String.valueOf(hiddenWord));
                
                // Jeśli użytkownik prawidłowo zaznaczył słowo
                if(!hiddenWordLabel.getText().contains("*"))
                {
                    // Wyświetl dialog z pozytywnym wynikiem
                    resultLabel.setText("Wygrałeś");
                    resultDialog.setVisible(true);
                }
                
            }
            else
            {
                // Wskazuje że użytkownik wybrał zła literę
                button.setBackground(Color.RED);
                
                // Zwiekszamy wskaźnik nieprawidłowych trafień o 1
                ++incorrectGuesses;
                
                // Zaktualizuj obrazek wisielca 
                Narzedzia.updateImage(hangmanImage, "resource/" + (incorrectGuesses + 1) + ".png");
                
                // Użytkownikowi nie udało się trafić odpowiedniego słowa
                if(incorrectGuesses >= 6)
                {
                    // Wyświetl dialog typu game over
                    resultLabel.setText("Przegrałeś");
                    resultDialog.setVisible(true);
                }
            }
            wordLabel.setText("Prawidłowe słowo: " + wordChallenge[1]);
        }
    }
    
    private void createResultDialog()
    {
        resultDialog = new JDialog();
        resultDialog.setTitle("Wyniki");
        resultDialog.setSize(WspolneZmienne.RESULT_DIALOG_SIZE);
        resultDialog.getContentPane().setBackground(WspolneZmienne.BACKGROUND_COLOR);
        resultDialog.setResizable(false);
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setModal(true);
        resultDialog.setLayout(new GridLayout(3, 1));
        resultDialog.addWindowListener(new WindowAdapter()
        {
           @Override
           public void windowClosing(WindowEvent e)
           {
               resetGame();
           }
        });
        
        resultLabel = new JLabel();
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        wordLabel = new JLabel();
        wordLabel.setForeground(Color.WHITE);
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton restartButton = new JButton("Restart");
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(WspolneZmienne.SECONDARY_COLOR);
        restartButton.addActionListener(this);
        
        resultDialog.add(resultLabel);
        resultDialog.add(wordLabel);
        resultDialog.add(restartButton);
        
    }
    
    private void resetGame()
    {
        // Załaduj nową grę
        wordChallenge = Slowa.loadChallenge();
        incorrectGuesses = 0;
        
        // Załaduj obrazek początkowy
        Narzedzia.updateImage(hangmanImage, WspolneZmienne.IMAGE_PATH);
        
        // Update
        categoryLabel.setText(wordChallenge[0]);
        String hiddenWord = Narzedzia.hideWords(wordChallenge[1]);
        hiddenWordLabel.setText(hiddenWord);
        
        // Ponownie włącz wszystkie przyciski
        for(int i = 0; i < letterButtons.length; i++)
        {
            letterButtons[i].setEnabled(true);
            letterButtons[i].setBackground(WspolneZmienne.PRIMARY_COLOR);
        }
    }
}
