package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

public class CalcLayout implements LayoutManager2 {
	
	private int vgap;
    private int minWidth = 0, minHeight = 0;
    private int preferredWidth = 0, preferredHeight = 0;
    private int maxWidth = 0, maxHeight = 0;
    private double maxComponentWidth = 0, maxComponentHeight = 0;
    private boolean sizeUnknown = true;
    private Map<Component, RCPosition> components = new HashMap<>();
    
    public CalcLayout() {
		this(0);
	}
    
    public CalcLayout(int v) {
		vgap = v;
	}
    

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);
        setSizes(parent);

        Insets insets = parent.getInsets();
        dim.width = preferredWidth - insets.left - insets.right;
        dim.height = preferredHeight - insets.top - insets.bottom;
        sizeUnknown = false;

        return dim;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);
        setSizes(parent);

        Insets insets = parent.getInsets();
        dim.width = minWidth - insets.left - insets.right;
        dim.height = minHeight - insets.top - insets.bottom;
        sizeUnknown = false;

        return dim;
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		Dimension dim = new Dimension(0, 0);
        setSizes(target);

        Insets insets = target.getInsets();
        dim.width = maxWidth - insets.left - insets.right;
        dim.height = maxHeight - insets.top - insets.bottom;
        sizeUnknown = false;

        return dim;
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition position = null;
		if (constraints.getClass().equals(RCPosition.class)) {
			position = (RCPosition) constraints;
		} else if (constraints.getClass().equals(String.class)) {
			position = RCPosition.parse((String) constraints);
		} else {
			throw new IllegalArgumentException("Drugi argument mora biti tipa RCPosition ili String.");
		}
		
		if (position == null)
			throw new NullPointerException("Ogranicenje ne smije biti null.");
		
		if (!components.containsValue(position) && checkPosition(position)) {
			components.put(comp, position);
		} else {
			throw new CalcLayoutException("Ogranicenje nije dobro zadano.");
		}
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}
	
	@Override
	public void layoutContainer(Container parent) {
		Insets ins = parent.getInsets();
		int maxWidth = parent.getWidth() - (ins.left + ins.right);
		int maxHeight = parent.getHeight() - (ins.bottom + ins.top);
		
		if (sizeUnknown) 
			setSizes(parent);
		
		if (maxWidth != preferredWidth)
			maxComponentWidth = (maxWidth - 6*vgap) / 7;
		
		if (maxHeight != preferredHeight)
			maxComponentHeight = (maxHeight - 4*vgap) / 5;
		
		int width = (int) maxComponentWidth;
		int height = (int) maxComponentHeight;

		for (Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			Component c = entry.getKey();
			RCPosition p = entry.getValue();
			if (c.isVisible()) {
				if (p.getColumn() != 1 || p.getRow() != 1) {
					int x = (int) ((p.getColumn() - 1) * (width + vgap) + ins.left);
					int y = (int) ((p.getRow() - 1) * (height + vgap) + ins.top);
					c.setBounds(x, y, (int) maxComponentWidth, (int) maxComponentHeight);
				} else {
					c.setBounds(ins.left, ins.top, (int) maxComponentWidth * 5 + 4 * vgap, (int) maxComponentHeight);
				}
			}
			
		}
	}
	
	private void setSizes(Container parent) {
		
		Dimension d = null;
		preferredWidth = 0;
		preferredHeight = 0;
		minWidth = 0;
		minHeight = 0;
		
		for (Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			Component c = entry.getKey();
			RCPosition p = entry.getValue();
			if (c.isVisible()) {
				d = c.getPreferredSize();
				if (d != null) {
					if (p.getRow() != 1 || p.getColumn() != 1) {
						maxComponentWidth = Math.max(d.getWidth(), maxComponentWidth);
					} else {
						maxComponentWidth = Math.max((d.getWidth()-4*vgap) / 5, maxComponentWidth);
					}
					maxComponentHeight = Math.max(d.getHeight(), maxComponentHeight);
				}
			}
		}
		
		preferredWidth = (int) (7 * maxComponentWidth + 6 * vgap);
		preferredHeight = (int) (5 * maxComponentHeight + 4 * vgap);
				
		maxWidth = preferredWidth;
		maxHeight = preferredHeight;
		
		minWidth = preferredWidth;
		minHeight = preferredHeight;
	}	
	
	public static boolean checkPosition(RCPosition position) {
		if (position.getRow() < 1 || position.getRow() > 5 || position.getColumn() < 1 || position.getColumn() > 7) 
			return false;
		
		if (position.getRow() == 1 && position.getColumn() > 1 && position.getColumn() < 6)
			return false;
		
		return true;
	}

}
