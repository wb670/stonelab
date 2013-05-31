/**
 * 
 */
package com.alibaba.stonelab.javalab.misc.d;

/**
 * @author <a href="mailto:li.jinl@tmall.com>Stone.J</a>
 * 
 */
public abstract class BrushWrapper implements Brush {

	private Brush brush;

	public BrushWrapper(Brush brush) {
		this.brush = brush;
	}

	@Override
	public String red() {
		return brush.red();
	}

	@Override
	public String blue() {
		return brush.blue();
	}

	@Override
	public String black() {
		return brush.black();
	}

	@Override
	public String white() {
		return brush.white();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "->" + brush.toString();
	}

}
