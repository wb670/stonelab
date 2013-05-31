/**
 * 
 */
package com.alibaba.stonelab.javalab.misc.d;

import com.alibaba.stonelab.javalab.misc.d.i.Black;
import com.alibaba.stonelab.javalab.misc.d.i.Red;
import com.alibaba.stonelab.javalab.misc.d.i.White;

/**
 * @author <a href="mailto:li.jinl@tmall.com>Stone.J</a>
 * 
 */
public class Main {
	public static void main(String[] args) {
		Brush brush = new Black(new Red(new White(new BaseBrush())));
		System.out.println(brush);
		System.out.println(brush.white());
		System.out.println(brush.red());
		System.out.println(brush.black());
		System.out.println(brush.blue());
	}
}
