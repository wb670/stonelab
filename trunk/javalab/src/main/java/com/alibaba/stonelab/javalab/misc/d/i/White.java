/**
 * 
 */
package com.alibaba.stonelab.javalab.misc.d.i;

import com.alibaba.stonelab.javalab.misc.d.Brush;
import com.alibaba.stonelab.javalab.misc.d.BrushWrapper;

/**
 * @author <a href="mailto:li.jinl@tmall.com>Stone.J</a>
 * 
 */
public class White extends BrushWrapper {

	public White(Brush brush) {
		super(brush);
	}

	@Override
	public String white() {
		return "white";
	}

}
