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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.mirage.aop;

import com.liferay.portal.mirage.service.MirageServiceFactory;
import com.sun.portal.cms.mirage.service.custom.ContentTypeService;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * <a href="JournalTemplateLocalServiceInterceptor.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Prakash Reddy
 *
 */
public class JournalTemplateLocalServiceAspect extends MirageAspect {

	protected Object doInvoke(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		String methodName = proceedingJoinPoint.getSignature().getName();

		if (methodName.equals("addTemplate") ||
			methodName.equals("addTemplateToGroup") ||
			methodName.equals("deleteTemplate") ||
			methodName.equals("deleteTemplates") ||
			methodName.equals("getTemplate") ||
			methodName.equals("getTemplateBySmallImageId") ||
			methodName.equals("updateTemplate")) {

			TemplateInvoker templateInvoker = new TemplateInvoker(proceedingJoinPoint);

			ContentTypeService contentTypeService =
				MirageServiceFactory.getContentTypeService();

			if (methodName.equals("addTemplate") ||
				methodName.equals("addTemplateToGroup")) {

				contentTypeService.addTemplateToContentType(
					templateInvoker, null);
			}
			else if (methodName.equals("deleteTemplate")) {
				contentTypeService.deleteTemplateOfContentType(
					null, templateInvoker);
			}
			else if (methodName.equals("deleteTemplates")) {
				contentTypeService.deleteTemplatesOfContentType(
					null, new TemplateInvoker[] {templateInvoker});
			}
			else if (methodName.equals("getTemplate") ||
					 methodName.equals("getTemplateBySmallImageId")) {

				contentTypeService.getTemplate(templateInvoker, null);
			}
			else if (methodName.equals("updateTemplate")) {
				contentTypeService.updateTemplateOfContentType(
					templateInvoker, null);
			}

			return templateInvoker.getReturnValue();
		}
		else if (methodName.equals("getStructureTemplates") ||
				 methodName.equals("getTemplates") ||
				 methodName.equals("getTemplatesCount") ||
				 methodName.equals("search") ||
				 methodName.equals("searchCount")) {

			SearchCriteriaInvoker searchCriteriaInvoker =
				new SearchCriteriaInvoker(proceedingJoinPoint);

			ContentTypeService contentTypeService =
				MirageServiceFactory.getContentTypeService();

			if (methodName.equals("getStructureTemplates") ||
				methodName.equals("getTemplates") ||
				methodName.equals("search")) {

				contentTypeService.searchTemplates(searchCriteriaInvoker);
			}
			else if (methodName.equals("getTemplatesCount") ||
					 methodName.equals("searchCount")) {

				contentTypeService.searchTemplatesCount(searchCriteriaInvoker);
			}

			return searchCriteriaInvoker.getReturnValue();
		}
		else {
			return proceedingJoinPoint.proceed();
		}
	}

}