/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="SocialBookmarkTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author David Truong
 * @author Jorge Ferrer
 *
 */
public class SocialBookmarkTag extends TagSupport {

	public static String doTag(
			String type, String url, String title, String target,
			PageContext pageContext)
		throws Exception {

		Object returnObj = null;

		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		try {
			Thread.currentThread().setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			Object targetWrapper = target;

			if (targetWrapper == null) {
				targetWrapper = new NullWrapper(String.class.getName());
			}

			MethodWrapper methodWrapper = new MethodWrapper(
				_TAG_CLASS, _TAG_DO_END_METHOD,
				new Object[] {
					_PAGE, type, url, title, targetWrapper, pageContext
				});

			returnObj = MethodInvoker.invoke(methodWrapper);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			Thread.currentThread().setContextClassLoader(contextClassLoader);
		}

		if (returnObj != null) {
			return returnObj.toString();
		}
		else {
			return StringPool.BLANK;
		}
	}

	public int doEndTag() throws JspException {
		try {
			doTag(_type, _url, _title, _target, pageContext);
		}
		catch (Exception e) {
			if (e instanceof JspException) {
				throw (JspException)e;
			}
			else {
				throw new JspException(e);
			}
		}

		return EVAL_PAGE;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setTarget(String target) {
		_target = target;
	}

	private static final String _TAG_CLASS =
		"com.liferay.portal.servlet.taglib.ui.SocialBookmarkTagUtil";

	private static final String _TAG_DO_END_METHOD = "doEndTag";

	private static final String _PAGE =
		"/html/taglib/ui/social_bookmark/page.jsp";

	private static Log _log = LogFactory.getLog(SocialBookmarkTag.class);

	private String _type;
	private String _url;
	private String _title;
	private String _target;

}