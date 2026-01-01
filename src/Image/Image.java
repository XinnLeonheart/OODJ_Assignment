/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Image;


import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 *
 * @author Xenia Thor
 */
public class Image extends JComponent {
    
    private Icon icon;
    private int Size;
    
    public Icon getIcon(Icon icon){
        return icon;
    }
    
    public void setIcon(Icon icon){
        this.icon = icon;
    }
    
    public int getSize(int size){
        return Size;
    }
    
    public void setSize (int Size){
        this.Size = Size;
    }
    
    @Override
    protected void paintComponent (Graphics graphics){
        if (icon != null) {
            int width = getWidth();
            int height = getHeight();
            int diameter = Math.min(width, height);
            int x = width / 2 - diameter / 2;
            int y = height / 2 - diameter / 2;
            int border = Size * 2;
            diameter -= border;
            
            Rectangle size = getAutoSize(icon, diameter);
            
            BufferedImage img = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2_img = img.createGraphics();
            g2_img.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2_img.fillOval(0, 0, diameter, diameter);
            Composite composite = g2_img.getComposite();
            g2_img.setComposite(AlphaComposite.SrcIn);
            g2_img.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            
            g2_img.drawImage(toImage(icon), size.x, size.y, size.width, size.height, null);
            g2_img.setComposite(composite);
            g2_img.dispose();
            Graphics2D g2 = (Graphics2D) graphics;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (Size > 0) {
                diameter += border;
                g2.setColor(getForeground());
                g2.fillOval(x, y, diameter, diameter);
            }
            if (isOpaque()) {
                g2.setColor(getBackground());
                diameter -= border;
                g2.fillOval(x + Size, y + Size, diameter, diameter);
            }
            g2.drawImage(img, x + Size, y + Size, null);
        }
        super.paintComponent(graphics);
    }
    
    private Rectangle getAutoSize (Icon image, int Size) {
        int w = Size;
        int h = Size;
        int iw = image.getIconWidth();
        int ih = image.getIconHeight();
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.max(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        if (width < 1) {
            width = 1;
        }
        if (height < 1) {
            height = 1;
        }
        int cw = Size;
        int ch =Size;
        int x = (cw - width) / 2;
        int y = (ch - height) / 2;
        return new Rectangle(new Point(x, y), new Dimension(width, height));
    }
    private java.awt.Image toImage (Icon icon){
        return ((ImageIcon) icon).getImage();
    }
}
