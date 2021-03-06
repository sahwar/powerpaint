package com.kreative.paint.tool;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import com.kreative.paint.ToolContext;
import com.kreative.paint.document.draw.ShapeDrawObject;
import com.kreative.paint.form.BooleanOption;
import com.kreative.paint.form.DoubleOption;
import com.kreative.paint.form.Form;
import com.kreative.paint.form.IntegerOption;
import com.kreative.paint.form.PreviewGenerator;
import com.kreative.paint.geom.Flower;
import com.kreative.paint.geom.draw.FlowerDrawObject;

public class FlowerTool extends CircularShapeTool implements ToolOptions.Custom {
	private static final int K = 0xFF000000;
	private static final Image icon = ToolUtilities.makeIcon(
			16, 16,
			new int[] {
					0,0,0,0,0,0,0,K,K,0,0,0,0,0,0,0,
					0,0,0,0,0,0,K,0,0,K,0,0,0,0,0,0,
					0,0,0,0,0,K,0,0,0,0,K,0,0,0,0,0,
					0,0,0,0,0,K,0,0,0,0,K,0,0,0,0,0,
					0,0,0,0,0,K,0,0,0,0,K,0,0,0,0,0,
					0,0,K,K,K,0,K,0,0,K,0,K,K,K,0,0,
					0,K,0,0,0,K,K,0,0,K,K,0,0,0,K,0,
					K,0,0,0,0,0,0,K,K,0,0,0,0,0,0,K,
					K,0,0,0,0,0,0,K,K,0,0,0,0,0,0,K,
					0,K,0,0,0,K,K,0,0,K,K,0,0,0,K,0,
					0,0,K,K,K,0,K,0,0,K,0,K,K,K,0,0,
					0,0,0,0,0,K,0,0,0,0,K,0,0,0,0,0,
					0,0,0,0,0,K,0,0,0,0,K,0,0,0,0,0,
					0,0,0,0,0,K,0,0,0,0,K,0,0,0,0,0,
					0,0,0,0,0,0,K,0,0,K,0,0,0,0,0,0,
					0,0,0,0,0,0,0,K,K,0,0,0,0,0,0,0,
			}
	);

	public ToolCategory getCategory() {
		return ToolCategory.MISC;
	}
	
	protected Image getBWIcon() {
		return icon;
	}
	
	public ShapeDrawObject makeShape(ToolEvent e, float cx, float cy, float ex, float ey) {
		int petals = e.tc().getCustom(FlowerTool.class, "petals", Integer.class, 8);
		double width = e.tc().getCustom(FlowerTool.class, "width", Double.class, 65.0);
		int smoothness = e.tc().getCustom(FlowerTool.class, "smoothness", Integer.class, 12);
		boolean includeCenter = e.tc().getCustom(FlowerTool.class, "includeCenter", Boolean.class, false);
		return new FlowerDrawObject(e.getPaintSettings(), petals, width, smoothness, includeCenter, cx, cy, ex, ey);
	}
	
	public boolean keyPressed(ToolEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_DOWN:
			e.tc().decrementCustom(FlowerTool.class, "petals", Integer.class, 8, 1);
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_UP:
			e.tc().incrementCustom(FlowerTool.class, "petals", Integer.class, 8);
			break;
		case KeyEvent.VK_PLUS:
		case KeyEvent.VK_ADD:
		case KeyEvent.VK_EQUALS:
			e.tc().incrementCustom(FlowerTool.class, "width", Double.class, 65.0, 90.0);
			break;
		case KeyEvent.VK_MINUS:
		case KeyEvent.VK_SUBTRACT:
			e.tc().decrementCustom(FlowerTool.class, "width", Double.class, 65.0, 0.0);
			break;
		case KeyEvent.VK_1:
			if (e.isShiftDown()) e.tc().setCustom(FlowerTool.class, "width", 0.0);
			else e.tc().setCustom(FlowerTool.class, "petals", 1);
			break;
		case KeyEvent.VK_2:
			if (e.isShiftDown()) e.tc().setCustom(FlowerTool.class, "width", 10.0);
			else e.tc().setCustom(FlowerTool.class, "petals", 2);
			break;
		case KeyEvent.VK_3:
			if (e.isShiftDown()) e.tc().setCustom(FlowerTool.class, "width", 20.0);
			else e.tc().setCustom(FlowerTool.class, "petals", 3);
			break;
		case KeyEvent.VK_4:
			if (e.isShiftDown()) e.tc().setCustom(FlowerTool.class, "width", 30.0);
			else e.tc().setCustom(FlowerTool.class, "petals", 4);
			break;
		case KeyEvent.VK_5:
			if (e.isShiftDown()) e.tc().setCustom(FlowerTool.class, "width", 40.0);
			else e.tc().setCustom(FlowerTool.class, "petals", 5);
			break;
		case KeyEvent.VK_6:
			if (e.isShiftDown()) e.tc().setCustom(FlowerTool.class, "width", 50.0);
			else e.tc().setCustom(FlowerTool.class, "petals", 6);
			break;
		case KeyEvent.VK_7:
			if (e.isShiftDown()) e.tc().setCustom(FlowerTool.class, "width", 60.0);
			else e.tc().setCustom(FlowerTool.class, "petals", 7);
			break;
		case KeyEvent.VK_8:
			if (e.isShiftDown()) e.tc().setCustom(FlowerTool.class, "width", 70.0);
			else e.tc().setCustom(FlowerTool.class, "petals", 8);
			break;
		case KeyEvent.VK_9:
			if (e.isShiftDown()) e.tc().setCustom(FlowerTool.class, "width", 80.0);
			else e.tc().setCustom(FlowerTool.class, "petals", 9);
			break;
		case KeyEvent.VK_0:
			if (e.isShiftDown()) e.tc().setCustom(FlowerTool.class, "width", 90.0);
			else e.tc().setCustom(FlowerTool.class, "petals", 10);
			break;
		}
		return false;
	}
	
	public Form getCustomOptionsForm(final ToolContext tc) {
		Form f = new Form();
		f.add(new PreviewGenerator() {
			public String getName() { return null; }
			public void generatePreview(Graphics2D g, Rectangle rec) {
				int petals = tc.getCustom(FlowerTool.class, "petals", Integer.class, 8);
				double width = tc.getCustom(FlowerTool.class, "width", Double.class, 65.0);
				int smoothness = tc.getCustom(FlowerTool.class, "smoothness", Integer.class, 12);
				boolean includeCenter = tc.getCustom(FlowerTool.class, "includeCenter", Boolean.class, false);
				g.draw(new Flower(petals, width, smoothness, includeCenter, (float)rec.getCenterX(), (float)rec.getCenterY(), (float)rec.getMaxX(), (float)rec.getCenterY()));
			}
		});
		f.add(new IntegerOption() {
			public String getName() { return ToolUtilities.messages.getString("flower.options.Petals"); }
			public int getMaximum() { return Integer.MAX_VALUE; }
			public int getMinimum() { return 1; }
			public int getStep() { return 1; }
			public int getValue() { return tc.getCustom(FlowerTool.class, "petals", Integer.class, 8); }
			public void setValue(int v) { tc.setCustom(FlowerTool.class, "petals", v); }
			public boolean useSlider() { return false; }
		});
		f.add(new DoubleOption() {
			public String getName() { return ToolUtilities.messages.getString("flower.options.Width"); }
			public double getMaximum() { return 90; }
			public double getMinimum() { return 1; }
			public double getStep() { return 1; }
			public double getValue() { return tc.getCustom(FlowerTool.class, "width", Double.class, 65.0); }
			public void setValue(double v) { tc.setCustom(FlowerTool.class, "width", v); }
		});
		f.add(new IntegerOption() {
			public String getName() { return ToolUtilities.messages.getString("flower.options.Smoothness"); }
			public int getMaximum() { return Integer.MAX_VALUE; }
			public int getMinimum() { return 1; }
			public int getStep() { return 1; }
			public int getValue() { return tc.getCustom(FlowerTool.class, "smoothness", Integer.class, 12); }
			public void setValue(int v) { tc.setCustom(FlowerTool.class, "smoothness", v); }
			public boolean useSlider() { return false; }
		});
		f.add(new BooleanOption() {
			public String getName() { return ""; }
			public boolean getValue() { return tc.getCustom(FlowerTool.class, "includeCenter", Boolean.class, false); }
			public void setValue(boolean v) { tc.setCustom(FlowerTool.class, "includeCenter", v); }
			public boolean useTrueFalseLabels() { return false; }
			public String getLabel(boolean v) { return ToolUtilities.messages.getString("flower.options.IncludeCenter"); }
		});
		return f;
	}
}
