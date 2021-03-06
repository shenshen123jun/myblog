/**
 * Copyright (c) 2005-2012, Rory Ye
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the Jdkcn.com nor the names of its contributors may
 *       be used to endorse or promote products derived from this software without
 *       specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jdkcn.myblog.service.impl;

import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.jdkcn.myblog.domain.Comment;
import com.jdkcn.myblog.domain.Comment.Status;
import com.jdkcn.myblog.service.CommentService;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: CommentServiceImpl.java 427 2011-05-13 09:28:19Z rory.cn $
 */
public class CommentServiceImpl implements CommentService {

	@Inject
	private Provider<EntityManager> entityManagerProvider;
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public Comment saveOrUpdate(Comment comment) {
		if (comment.getAuthor() != null && StringUtils.isBlank(comment.getAuthorName())) {
			comment.setAuthorName(comment.getAuthor().getUsername());
		}
		if (comment.getPostDate() == null) {
			comment.setPostDate(new Date());
		}
		if (comment.getStatus() == null) {
			comment.setStatus(Status.WAITING_FOR_APPROVE);
		}
		return entityManagerProvider.get().merge(comment);
	}

	/**
	 * {@inheritDoc}
	 */
	public Comment get(String id) {
		return entityManagerProvider.get().find(Comment.class, id);
	}

}
