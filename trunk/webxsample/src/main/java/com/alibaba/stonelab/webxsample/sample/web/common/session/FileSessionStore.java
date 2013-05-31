/**
 * 
 */
package com.alibaba.stonelab.webxsample.sample.web.common.session;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.alibaba.citrus.service.requestcontext.session.SessionConfig;
import com.alibaba.citrus.service.requestcontext.session.SessionStore;

/**
 * @author <a href="mailto:li.jinl@tmall.com>Stone.J</a>
 * 
 */
public class FileSessionStore implements SessionStore {

	private String base;

	@Override
	public void init(String storeName, SessionConfig sessionConfig) throws Exception {
	}

	@Override
	public Iterable<String> getAttributeNames(String sessionID, StoreContext storeContext) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object loadAttribute(String attrName, String sessionID, StoreContext storeContext) {
		if (!isSessionExists(sessionID)) {
			return null;
		}
		InputStream in = null;
		try {
			in = new FileInputStream(getSessionFile(sessionID));
			List<String> list = IOUtils.readLines(in);
			for (String l : list) {
				if (l.contains(attrName)) {
					return l.split("=")[1].trim();
				}
			}
			return null;
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			IOUtils.closeQuietly(in);
		}
		return null;
	}

	@Override
	public void invaldiate(String sessionID, StoreContext storeContext) {

	}

	@Override
	public void commit(Map<String, Object> modifiedAttrs, String sessionID, StoreContext storeContext) {
		OutputStream out = null;
		List<String> list = new ArrayList<String>();
		try {
			out = new FileOutputStream(getSessionFile(sessionID));
			for (String key : modifiedAttrs.keySet()) {
				list.add(key + "=" + modifiedAttrs.get(key));
			}
			IOUtils.writeLines(list, "\n", out);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			IOUtils.closeQuietly(out);
		}

	}

	private boolean isSessionExists(String sessionID) {
		return new File(getSessionFile(sessionID)).exists();
	}

	private String getSessionFile(String sessionID) {
		return base + sessionID;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

}
