/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2025 FalsePattern, The MEGA Team
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the words "MEGA"
 * shall be included in all copies or substantial portions of the Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, only version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.falsepattern.endlessids.util;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Like WeakHashMap, but uses identity instead of equality when comparing keys.
 */
public class WeakIdentityHashMap<K,V> {
    private final HashMap<WeakReference<K>,V> mMap = new HashMap<>();
    private final ReferenceQueue<Object> mRefQueue = new ReferenceQueue<>();
    private void cleanUp() {
        Reference<?> ref;
        while ((ref = mRefQueue.poll()) != null) {
            mMap.remove(ref);
        }
    }
    public void put(K key, V value) {
        cleanUp();
        mMap.put(new CmpWeakReference<>(key, mRefQueue), value);
    }
    public V get(K key) {
        cleanUp();
        return mMap.get(new CmpWeakReference<>(key));
    }
    public Collection<V> values() {
        cleanUp();
        return mMap.values();
    }
    public Set<Map.Entry<WeakReference<K>, V>> entrySet() {
        return mMap.entrySet();
    }
    public int size() {
        cleanUp();
        return mMap.size();
    }
    public boolean isEmpty() {
        cleanUp();
        return mMap.isEmpty();
    }
    private static class CmpWeakReference<K> extends WeakReference<K> {
        private final int mHashCode;
        public CmpWeakReference(K key) {
            super(key);
            mHashCode = System.identityHashCode(key);
        }
        public CmpWeakReference(K key, ReferenceQueue<Object> refQueue) {
            super(key, refQueue);
            mHashCode = System.identityHashCode(key);
        }
        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            K k = get();
            if (k != null && o instanceof CmpWeakReference) {
                return ((CmpWeakReference) o).get() == k;
            }
            return false;
        }
        @Override
        public int hashCode() {
            return mHashCode;
        }
    }
}