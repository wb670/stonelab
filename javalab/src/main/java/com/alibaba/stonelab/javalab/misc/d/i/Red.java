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
public class Red extends BrushWrapper {

	public Red(Brush brush) {
		super(brush);
	}

	@Override
	public String red() {
		return "red";
	}

}
