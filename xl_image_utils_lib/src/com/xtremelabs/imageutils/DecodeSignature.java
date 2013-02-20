/*
 * Copyright 2013 Xtreme Labs
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xtremelabs.imageutils;

import android.graphics.Bitmap;

public class DecodeSignature {
	final String uri;
	final int sampleSize;
	final Bitmap.Config bitmapConfig;

	private final int hashCode;

	DecodeSignature(String uri, int sampleSize, Bitmap.Config bitmapConfig) {
		this.uri = uri;
		this.sampleSize = sampleSize;
		this.bitmapConfig = bitmapConfig;

		this.hashCode = generateHashCode();
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	private int generateHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bitmapConfig == null) ? 0 : bitmapConfig.hashCode());
		result = prime * result + sampleSize;
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DecodeSignature other = (DecodeSignature) obj;
		if (bitmapConfig != other.bitmapConfig)
			return false;
		if (sampleSize != other.sampleSize)
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}
}
