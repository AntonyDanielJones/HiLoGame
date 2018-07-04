/**
 * HiLoFrame v.1.0
 * 30.10.16
 * Daniel Jones
 */

package hilogame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Creates and implements necessary components and rules to play a high-low
 * dice game. 
 * 
 * Rules: bet an amount (£1, £5 or £10) on whether the sum of two dice will
 * be seven, higher or lower. Payoff for seven is 4:1, payoff for higher/lower
 * is 1:1. Starting balance is £50. If your balance reaches £100, you win. If it 
 * reaches, £0 you lose.
 * 
 * @author Daniel Jones
 */
public class HiLoFrame extends JFrame {
    //Components
    JPanel framePanel, graphicsPanel, GUIPanel, radioPanel;
    JRadioButton high, low, sevens;
    ButtonGroup radioGroup;
    JComboBox amtDropdown;
    JLabel balanceLabel;
    JButton throwButton;
    JComponent dice1Component = new Dice1Component();
    JComponent dice2Component = new Dice2Component();
    
    //listeners
    ActionListener buttonListener, radioButtonListener, comboButtonListener;
    
    //Dice and DiceFace
    Dice dice1 =  new Dice(6);
    Dice dice2 = new Dice(6);
    DiceFace face1 = new DiceFace(100, 0, 0, dice1.getValue(), Color.RED);
    DiceFace face2 = new DiceFace(100, 0, 0, dice2.getValue(), Color.BLACK);
    
    //Game variables
    int amt, balance, gameType;
    String[] amount = {"£1", "£5", "£10"};
    
    /**
     * Constructs HiLoFrame for HiLoViewer.
     */
    public HiLoFrame() {
        balance = 50; //initial balance of £50
        amt = 1;
        createComponents();
        pack();
    }
    
    /**
     * Listener for button to throw dice. 
     */
    class throwListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            dice1.throwDice(); 
            face1.setDiceFace(dice1.getValue());
            dice2.throwDice();
            face2.setDiceFace(dice2.getValue());
            
            //add or subtract from balance according to game rules
            if (gameType == 0) {
                if (dice1.getValue() + dice2.getValue() > 7) {
                    balance += amt;
                }
                else {
                    balance -= amt;
                }
            }
            else if (gameType == 1) {
                if (dice1.getValue() + dice2.getValue() < 7) {
                    balance += amt;
                }
                else {
                    balance -= amt;
                }
            }
            else if (gameType == 2) {
                if (dice1.getValue() + dice2.getValue() == 7) {
                    balance += 4 * amt;
                }
                else {
                    balance -= 4 * amt;
                }
            }
            
            //set text to match new balance
            balanceLabel.setText("Balance = £" + balance);
            
            //do not allow bet to be larger than balance
            if (balance < 10 && amtDropdown.getSelectedIndex() == 2) {
                amtDropdown.setSelectedIndex(0); 
                amt = 1;
            }
            else if (balance < 5 && amtDropdown.getSelectedIndex() == 1) {
                amtDropdown.setSelectedIndex(0); 
                amt = 1;
            }
            
            repaint();
            
            //win message
            if (balance >= 100) {
                JOptionPane.showMessageDialog(null, "Congratulations, you won!", 
                        "You Win!", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            
            //lose message
            if (balance <= 0) {
                JOptionPane.showMessageDialog(null, "Sorry, you lose!", 
                        "You Lose!", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
    }
    
    /**
     * Listener for radio buttons.
     */
    class radioListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            throwButton.setEnabled(true);
            if (event.getSource() == high) {
                gameType = 0;
            }
            else if (event.getSource() == low) {
                gameType = 1;
            }
            else if (event.getSource() == sevens) {
                gameType = 2;
            }
        }
    }
    
    /**
     * Listener for combobox.
     */
    class comboListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            int i = amtDropdown.getSelectedIndex();
            if (i == 0) {
                amt = 1;
            }
            else if (i == 1) {
                if (balance < 5) {
                    amtDropdown.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null, "You don't have that much!", 
                        "Error!", JOptionPane.ERROR_MESSAGE);
                    amt = 1;
                }
                else
                amt = 5;
            }
            else if (i == 2) {
                if (balance < 10) {
                    amtDropdown.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null, "You don't have that much!", 
                        "Error!", JOptionPane.ERROR_MESSAGE);
                    amt = 1;
                }
                else
                amt = 10;
            }
        }
    }
    
    /**
     * Helps draw first die.
     */
    public class Dice1Component extends JComponent {
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            face1.draw(g2); 
        }
    
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 100);
        }
    }
    
    /**
     * Helps draw second die.
     */
    public class Dice2Component extends JComponent {
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            face2.draw(g2); 
        }
    
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 100);
        }
    }
    
    /**
     * Creates components and adds them to the frame. 
     */
    private void createComponents() {
        throwButton = new JButton("Throw dice");
        throwButton.setEnabled(false);
        buttonListener = new throwListener();
        throwButton.addActionListener(buttonListener);
        
        high = new JRadioButton("High");
        low = new JRadioButton("Low");
        sevens = new JRadioButton("Sevens");
        radioGroup = new ButtonGroup();
        radioGroup.add(high);
        radioGroup.add(low);
        radioGroup.add(sevens);
        radioButtonListener = new radioListener();
        high.addActionListener(radioButtonListener);
        low.addActionListener(radioButtonListener);
        sevens.addActionListener(radioButtonListener);
        
        amtDropdown = new JComboBox(amount);
        comboButtonListener = new comboListener();
        amtDropdown.addActionListener(comboButtonListener);
        
        balanceLabel = new JLabel("Balance = £" + balance);
        
        framePanel = new JPanel();
        framePanel.setLayout(new BorderLayout());
        graphicsPanel = new JPanel();
        GUIPanel = new JPanel();
        radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(3, 1));
        
        graphicsPanel.add(dice1Component);
        graphicsPanel.add(dice2Component);
        radioPanel.add(high);
        radioPanel.add(low);
        radioPanel.add(sevens);
        GUIPanel.add(radioPanel);
        GUIPanel.add(amtDropdown);
        GUIPanel.add(balanceLabel);
        GUIPanel.add(throwButton);
        framePanel.add(graphicsPanel, BorderLayout.NORTH);
        framePanel.add(GUIPanel, BorderLayout.SOUTH);
        add(framePanel);
    }
}
