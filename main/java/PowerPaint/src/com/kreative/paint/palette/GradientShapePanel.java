/*
 * Copyright &copy; 2009-2011 Rebecca G. Bettencourt / Kreative Software
 * <p>
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * <a href="http://www.mozilla.org/MPL/">http://www.mozilla.org/MPL/</a>
 * <p>
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * <p>
 * Alternatively, the contents of this file may be used under the terms
 * of the GNU Lesser General Public License (the "LGPL License"), in which
 * case the provisions of LGPL License are applicable instead of those
 * above. If you wish to allow use of your version of this file only
 * under the terms of the LGPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the LGPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the LGPL License.
 * @since PowerPaint 1.0
 * @author Rebecca G. Bettencourt, Kreative Software
 */

package com.kreative.paint.palette;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.*;
import com.kreative.paint.PaintContext;
import com.kreative.paint.material.MaterialManager;
import com.kreative.paint.material.gradient.*;
import com.kreative.paint.swing.*;

public class GradientShapePanel extends PaintContextPanel {
	private static final long serialVersionUID = 1L;

	private boolean eventexec = false;
	private CellSelectorModel<GradientShape> palmodel;
	private CellSelector<GradientShape> palcomp;
	private GradientShapeSelector gs;
	private JScrollPane gsp;
	private GradientPaint2 paint;
	
	public GradientShapePanel(PaintContext pc, MaterialManager mm) {
		super(pc, CHANGED_PAINT|CHANGED_EDITING);

		palmodel = new DefaultCellSelectorModel<GradientShape>(mm.gradientLoader().getGradientShapes(), GradientShape.SIMPLE_LINEAR);
		palcomp = new CellSelector<GradientShape>(palmodel, new CellSelectorRenderer<GradientShape>() {
			public int getCellHeight() { return 25; }
			public int getCellWidth() { return 25; }
			public int getColumns() { return 0; }
			public int getRows() { return 0; }
			public boolean isFixedHeight() { return true; }
			public boolean isFixedWidth() { return true; }
			public void paint(Graphics g, GradientShape object, int x, int y, int w, int h) {
				((Graphics2D)g).setPaint(new GradientPaint2(object, GradientColorMap.BLACK_TO_WHITE, null));
				g.fillRect(x, y, w, h);
			}
		});
		paint = GradientPaint2.BLACK_TO_WHITE;
		
		gs = new GradientShapeSelector(
			GradientPaint2.BLACK_TO_WHITE,
			mm.gradientLoader().getGradientShapes().values().toArray(new GradientShape[0]),
			mm.gradientLoader().getGradientShapes().keySet().toArray(new String[0])
		);
		gsp = new JScrollPane(gs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		palmodel.addCellSelectionListener(new CellSelectionListener<GradientShape>() {
			public void cellSelected(CellSelectionEvent<GradientShape> e) {
				if (!eventexec) {
					paint = paint.derivePaint(e.getObject());
					GradientShapePanel.this.pc.setEditedEditedground(paint);
				}
			}
		});
		gs.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!eventexec) {
					paint = paint.derivePaint(gs.getGradientShape(gs.getSelectedIndex()));
					GradientShapePanel.this.pc.setEditedEditedground(paint);
				}
			}
		});

		setLayout(new BorderLayout());
		add(gsp, BorderLayout.CENTER);
		update();
	}
	
	public CellSelector<GradientShape> asPopup() {
		return palcomp.asPopup();
	}
	
	public void update() {
		Paint p = pc.getEditedEditedground();
		if (p instanceof GradientPaint2) {
			paint = (GradientPaint2)p;
			if (!eventexec) {
				eventexec = true;
				gs.setGradient(paint);
				palmodel.setSelectedObject(paint.shape);
				eventexec = false;
			}
		}
	}
	
	public GradientPaint2 getGradient() {
		return paint;
	}
	
	private static class GradientShapeSelector extends JList {
		private static final long serialVersionUID = 1L;
		private GradientShape[] shapes;
		private String[] names;
		public GradientShapeSelector(GradientPaint2 paint, GradientShape[] shapes, String[] names) {
			super(names);
			this.shapes = shapes;
			this.names = names;
			setVisibleRowCount(8);
			setBorder(BorderFactory.createEmptyBorder(2, 0, 1, 0));
			setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			setCellRenderer(new DefaultListCellRenderer() {
				private static final long serialVersionUID = 1L;
				public Component getListCellRendererComponent(JList list, Object value, int index, boolean sel, boolean focus) {
					JLabel l = (JLabel)super.getListCellRendererComponent(list, value, index, sel, focus);
					BufferedImage bi = new BufferedImage(27,27,BufferedImage.TYPE_INT_ARGB);
					Graphics2D g = bi.createGraphics();
					g.setPaint(Color.gray);
					g.fillRect(0, 0, 27, 27);
					g.setPaint(new GradientPaint2(GradientShapeSelector.this.shapes[index], GradientColorMap.BLACK_TO_WHITE, null));
					g.fillRect(1, 1, 25, 25);
					g.dispose();
					l.setIcon(new ImageIcon(bi));
					l.setText(GradientShapeSelector.this.names[index]);
					l.setBorder(BorderFactory.createEmptyBorder(1, 3, 2, 8));
					return l;
				}
			});
			this.clearSelection();
			for (int i = 0; i < shapes.length; i++) {
				if (shapes[i].equals(paint.shape)) {
					this.setSelectedIndex(i);
					this.ensureIndexIsVisible(i);
					break;
				}
			}
		}
		public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
			return 30;
		}
		public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
			return 30*(visibleRect.height/30);
		}
		public boolean getScrollableTracksViewportWidth() {
			return true;
		}
		public boolean getScrollableTracksViewportHeight() {
			return false;
		}
		public void setGradient(GradientPaint2 p) {
			this.clearSelection();
			for (int i = 0; i < shapes.length; i++) {
				if (shapes[i].equals(p.shape)) {
					this.setSelectedIndex(i);
					this.ensureIndexIsVisible(i);
					break;
				}
			}
		}
		public GradientShape getGradientShape(int idx) {
			return shapes[idx];
		}
	}
}
