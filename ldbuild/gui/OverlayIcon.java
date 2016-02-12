package ldbuild.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
//found this at https://www.java.net//node/678566
public class OverlayIcon extends ImageIcon {
    /**
     * 
     */
    private static final long serialVersionUID = -6482575169794960739L;
    private ImageIcon base;
    private List<ImageIcon> overlays;

    public OverlayIcon(ImageIcon base) {
	super(base.getImage());
	this.base = base;
	this.overlays = new ArrayList<ImageIcon>();
    }

    public OverlayIcon add(ImageIcon overlay) {
	overlays.add(overlay);
        return this;
    }

    @Override
    public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
	base.paintIcon(c, g, x, y);
	for (ImageIcon icon : overlays) {
	    icon.paintIcon(c, g, x, y);
	}
    }
}
