package com.xtremelabs.imageutils;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.os.Build;

class LifoThreadPool {
	private ThreadPoolExecutor mThreadPool;
	private LifoBlockingStack mStack;

	public LifoThreadPool(int poolSize) {
		if (Build.VERSION.SDK_INT >= 9) {
			mStack = new LifoBlockingStack();
			mThreadPool = new ThreadPoolExecutor(poolSize, poolSize, 5000, TimeUnit.SECONDS, mStack);
		} else {
			mThreadPool = new ThreadPoolExecutor(poolSize, poolSize, 5000, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		}
	}

	public void execute(Runnable runnable) {
		mThreadPool.execute(runnable);
	}
	
	public void bump(Runnable runnable) {
		if (mStack != null) {
			mStack.bump(runnable);
		}
	}

	private class LifoBlockingStack extends LinkedBlockingDeque<Runnable> {
		private static final long serialVersionUID = -4854985351588039351L;

		@Override
		public synchronized boolean offer(Runnable runnable) {
			return super.offerFirst(runnable);
		}

		@Override
		public synchronized boolean offer(Runnable runnable, long timeout, TimeUnit unit) throws InterruptedException {
			return super.offerFirst(runnable, timeout, unit);
		}

		@Override
		public synchronized boolean add(Runnable runnable) {
			return super.offerFirst(runnable);
		}

		@Override
		public synchronized void put(Runnable runnable) throws InterruptedException {
			super.putFirst(runnable);
		}
		
		public synchronized void bump(Runnable runnable) {
			super.remove(runnable);
			super.offerFirst(runnable);
		}
	};
}