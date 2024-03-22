/*
 * Copyright 2021-2024 spring-boot-extension the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *       https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.livk.context.lock.support;

import com.livk.context.lock.DistributedLock;
import com.livk.context.lock.LockScope;
import com.livk.context.lock.LockType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author livk
 */
class LocalLockTest {

	static DistributedLock lock = new LocalLock();

	static ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();

	@AfterAll
	static void close() {
		service.close();
	}

	@Test
	void tryLock() throws ExecutionException, InterruptedException {
		lock.lock(LockType.LOCK, "tryLock", false);
		assertFalse(service.submit(() -> lock.tryLock(LockType.LOCK, "tryLock", 3, 3, false)).get());
		assertTrue(lock.tryLock(LockType.LOCK, "tryLock", 3, 3, false));
		assertTrue(lock.tryLock(LockType.LOCK, "key", 3, 3, false));

		lock.unlock();

		assertTrue(lock.tryLock(LockType.LOCK, "key", 3, 3, false));

		lock.unlock();
	}

	@Test
	void scope() {
		assertEquals(LockScope.STANDALONE_LOCK, lock.scope());
	}

}
