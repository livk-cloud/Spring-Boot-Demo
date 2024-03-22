/*
 * Copyright 2021-2024 spring-boot-extension the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.livk.context.lock.support;

import com.livk.context.lock.LockScope;
import com.livk.context.lock.LockType;
import com.livk.context.lock.exception.LockException;
import lombok.RequiredArgsConstructor;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * CuratorLock
 * </p>
 *
 * @author livk
 */
@RequiredArgsConstructor
public class CuratorLock extends AbstractLockSupport<InterProcessLock> {

	private final CuratorFramework curatorFramework;

	@Override
	protected InterProcessLock getLock(LockType type, String key) {
		if (!key.startsWith("/")) {
			key = "/".concat(key);
		}
		return switch (type) {
			case LOCK, FAIR -> new InterProcessMutex(curatorFramework, key);
			case READ -> new InterProcessReadWriteLock(curatorFramework, key).readLock();
			case WRITE -> new InterProcessReadWriteLock(curatorFramework, key).writeLock();
		};
	}

	@Override
	protected boolean unlock(String key, InterProcessLock lock) {
		try {
			lock.release();
			return !isLocked(lock);
		}
		catch (Exception e) {
			throw new LockException(e);
		}
	}

	@Override
	protected boolean tryLock(InterProcessLock lock, long leaseTime, long waitTime) throws Exception {
		return lock.acquire(waitTime, TimeUnit.SECONDS);
	}

	@Override
	protected void lock(InterProcessLock lock) throws Exception {
		lock.acquire();
	}

	@Override
	protected boolean isLocked(InterProcessLock lock) {
		return lock.isAcquiredInThisProcess();
	}

	@Override
	public LockScope scope() {
		return LockScope.DISTRIBUTED_LOCK;
	}

}
