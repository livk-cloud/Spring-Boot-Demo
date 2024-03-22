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
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * RedissonLock
 * </p>
 *
 * @author livk
 */
@RequiredArgsConstructor
public class RedissonLock extends AbstractLockSupport<RLock> {

	private final RedissonClient redissonClient;

	@Override
	protected RLock getLock(LockType type, String key) {
		return switch (type) {
			case LOCK -> redissonClient.getLock(key);
			case FAIR -> redissonClient.getFairLock(key);
			case READ -> redissonClient.getReadWriteLock(key).readLock();
			case WRITE -> redissonClient.getReadWriteLock(key).writeLock();
		};
	}

	@Override
	protected boolean unlock(String key, RLock lock) {
		lock.unlock();
		return !isLocked(lock);
	}

	@Override
	protected boolean tryLockAsync(RLock lock, long leaseTime, long waitTime) throws Exception {
		return lock.tryLockAsync(waitTime, leaseTime, TimeUnit.SECONDS).get();
	}

	@Override
	protected boolean tryLock(RLock lock, long leaseTime, long waitTime) throws Exception {
		return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
	}

	@Override
	protected void lockAsync(RLock lock) throws Exception {
		lock.lockAsync().get();
	}

	@Override
	protected void lock(RLock lock) {
		lock.lock();
	}

	@Override
	protected boolean isLocked(RLock lock) {
		return lock.isLocked() && lock.isHeldByCurrentThread();
	}

	@Override
	public LockScope scope() {
		return LockScope.DISTRIBUTED_LOCK;
	}

	@Override
	protected boolean supportAsync() {
		return true;
	}

}
