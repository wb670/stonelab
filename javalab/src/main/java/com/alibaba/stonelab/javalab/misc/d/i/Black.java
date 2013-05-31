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
public class Black extends BrushWrapper {

	public Black(Brush brush) {
		super(brush);
	}

	@Override
	public String black() {
		return "black";
	}

}
