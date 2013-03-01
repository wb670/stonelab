/**
 * 
 */
package com.alibaba.stonelab.toolkit.cs4p.communication.cmd;

import net.sf.json.JSONObject;

import com.alibaba.stonelab.toolkit.cs4p.model.Battlefield;

/**
 * @author <a href="mailto:li.jinl@tmall.com>Stone.J</a>
 * 
 */
public class BattleCtl extends BaseCmd implements Cmd {

	private static final String CMD = "bctl";

	private boolean battle;

	public Cmd parser(String cmd) {
		String[] token = cmd.split(" ");
		if (token.length != 2) {
			return null;
		}
		if (!CMD.equals(token[0])) {
			return null;
		}
		if (!("y".equals(token[1]) || "n".equals(token[1]))) {
			return null;
		}
		battle = "y".equals(token[1]);
		return this;
	}

	@Override
	public String response() {
		Battlefield field = session.getBattlefield();
		field.setBattle(battle);
		return JSONObject.fromObject(field).toString();
	}

}
