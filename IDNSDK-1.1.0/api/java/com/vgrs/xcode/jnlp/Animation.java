package com.vgrs.xcode.jnlp;

import java.awt.*;
import javax.swing.*;

/**
 * A class used to implement a browser like animation that only runs during
 * calculation.  After much experimenting with Java's Thread Priority
 * support, and much failure, we've abandoned that approach.  Instead, this
 * object offers a next() method which allows callers to explicitly show the
 * next image.  This way a single Thread can control it's own process as well
 * as the animation.  After instantiating, a call to the Animation's next()
 * method should be placed somewhere in the main calculation loop.
 */
public class Animation extends JLabel {

	/**
	 * All images loaded are scaled to a certain dimension using the scaling
	 * algorithm specified by this constant.
	 * <br>This variable is hard coded to the value
	 * <i>Image.SCALE_FAST</i>
	 */
	static public final int SCALE_TYPE = Image.SCALE_FAST;

	// Internals
	static private ClassLoader loader = Animation.class.getClassLoader();
	static private Toolkit kit = Toolkit.getDefaultToolkit();

	// Object attributes
	private MediaTracker tracker;
	private Image[] images;
	private int length;
	private int width;
	private int height;
	private int frame;
	private long last;
	private int delay;

	private Image offImage;
	private Graphics offGraphics;


	/**
	 * Construct an Animation object.
	 * @param src An array of resources which point to slides in the animation.
	 * @param width The width of the slides
	 * @param height The height of the slides
	 * @param fps The Frames-Per-Second to show
	 */
	public Animation(String[] src, int width, int height, int fps) {
		super();
		this.width = width;
		this.height = height;
		this.setSize(width,height);
		this.setPreferredSize(new Dimension(width,height));
		length = src.length;
		images = new Image[length];
		tracker = new MediaTracker(this);
		for (int i=0; i<length; i++) {
			images[i] = loadImage(src[i],width,height);
			tracker.addImage(images[i],0);
		}
		frame = 0;
		last = 0;
		delay = (fps > 0) ? (1000 / fps) : 100;

		offImage = null;
		offGraphics = null;
	}


	/**
	 * Show the next slide in the animation.
	 */
	public void next() {
		long click = System.currentTimeMillis();
		if (click - last > delay) {
			frame++;
			paint(this.getGraphics());
			last = click;
		}
	}


	/**
	 * Display the Animation
	 * @param g A Graphics object on which to draw
	 */
	public void paint(Graphics g) {
		update(g);
	}


	/**
	 * A double-buffered display implementation
	 * @param g A Graphics object on which to draw
	 */
	public void update(Graphics g) {
		// Create the off-buffer
		Dimension d = this.getSize();
		if ((offGraphics == null) ||
			(d.width != width) ||
			(d.height != height)) {
			this.setSize(width,height);
			offImage = createImage(width, height);
			offGraphics = offImage.getGraphics();
		}

		// Erase the off-buffer
		offGraphics.setColor(getBackground());
		offGraphics.fillRect(0, 0, width, height);
		offGraphics.setColor(Color.black);

		// Paint the image onto the off-buffer
		paintFrame(offGraphics);

		// Paint the off-buffer onto the screen
		g.drawImage(offImage, 0, 0, null);
	}


	/**
	 * Wait until all frames have been loaded, then draw
	 * @param g A Graphics object on which to draw
	 */
	public void paintFrame(Graphics g) {
		if (tracker.statusID(0, true) == MediaTracker.COMPLETE) {
			g.drawImage(images[frame % length], 0, 0, null);
		}
	}


	private void log(String msg) {
		System.err.println(msg);
	}


	private Image loadImage(String i, int w, int h) {
		return kit.getImage(loader.getResource(i)).getScaledInstance(w,h,SCALE_TYPE);
	}

}
