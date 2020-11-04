/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 *
 * @author apcoz
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Bresenham implements Runnable {

    private JFrame frame;
    private final ArrayList<Point>  iniFin  = new ArrayList<>(2);
    private  int count=0;
    
     
    public static void main(String[] args) {
        EventQueue.invokeLater(new Bresenham());
    }

    @Override
    public void run() {
        initGUI();
    }

    public void initGUI() {
        frame = new JFrame("Bresenham");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.add(createPixels());
      
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createPixels() {
        int width = 30;
        int height = 30;
        int indice = 0;
        JPanel panel = new JPanel();
        
        panel.setLayout(new GridLayout(height, width, 0, 0));

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                PixelPanel pixelPanel = new PixelPanel();
                Point point = new Point (column,row);
                pixelPanel.pos=point;
                pixelPanel.index=indice;
                indice++;
                pixelPanel.addMouseListener(new ColorListener(pixelPanel,panel));
                panel.add(pixelPanel);
            }
        }
        
        return panel;
    }

    public class PixelPanel extends JPanel {

        private static final int PIXEL_SIZE = 20;

        private Color backgroundColor;
        private Point pos;
        private int index;
        

        public PixelPanel() {
            this.index=index;
            this.pos=pos;
            this.backgroundColor = Color.WHITE;
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            this.setPreferredSize(new Dimension(PIXEL_SIZE, PIXEL_SIZE));
        }
        
        public Color getBackgroundColor() {
            return backgroundColor;
        }
       
        public void setBackgroundColor(Color backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(getBackgroundColor());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public class ColorListener  extends MouseAdapter {
       
        private final PixelPanel pixel;
        private final JPanel panel;
       
        public ColorListener(PixelPanel pixel,JPanel panel) {
            this.pixel = pixel;
            this.panel=panel;
        }
        
        @Override
        public void mousePressed(MouseEvent event) {
            
            while (count<2){
                if((event.getButton() == MouseEvent.BUTTON1)) {
                    
                    pixel.setBackgroundColor(Color.BLUE);
                    pixel.repaint();
                    iniFin.add(count, pixel.pos);
                    count++;
                    
                }break;  
            }
            if (count == 2){
                findLine(panel,iniFin.get(0).x,iniFin.get(0).y,iniFin.get(1).x,iniFin.get(1).y);
            }
        }
    }
    
        public void findLine(JPanel panel,int x0, int y0, int x1, int y1) {
            
            int a;
            PixelPanel aux;
            int dx = Math.abs(x1 - x0);
            int dy = Math.abs(y1 - y0);

            int sx = x0 < x1 ? 1 : -1;
            int sy = y0 < y1 ? 1 : -1;

            int err = dx - dy;
            int e2;

            while (true){
                
                
                e2 = 2 * err;
                
                if (e2 > -dy) {
                    err = err - dy;
                    x0 = x0 + sx;   
                }
                
                if (e2 < dx) {
                    err = err + dx;
                    y0 = y0 + sy;
                }
                
                if (x0 == x1 && y0 == y1)
                    break;
               
                a = (y0*30)+x0;
  
                aux = (PixelPanel) panel.getComponent(a);
                aux.setBackgroundColor(Color.CYAN);
                aux.repaint();
                
            }     
        }   
}
    


