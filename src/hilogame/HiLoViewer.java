/**
 * HiLoViewer v.1.0
 * 30.10.16
 * Daniel Jones
 */

package hilogame;

import javax.swing.JFrame;

/**
 * Viewer for HiLoFrame class.
 * @author Daniel Jones
 */
public class HiLoViewer {
    public static void main(String[] args) {
        JFrame frame = new HiLoFrame();
        frame.setTitle("Dice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
