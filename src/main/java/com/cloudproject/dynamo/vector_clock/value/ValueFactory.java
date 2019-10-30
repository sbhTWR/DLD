/*
 * MIT License
 *
 * Copyright (c) 2017 Distributed clocks
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

//
// MessagePack for Java
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
package com.cloudproject.dynamo.vector_clock.value;

import com.cloudproject.dynamo.vector_clock.value.impl.*;

import java.math.BigInteger;
import java.util.*;

public final class ValueFactory
{
    private ValueFactory()
    {
    }

    public static ImmutableNilValue newNil()
    {
        return ImmutableNilValueImpl.get();
    }

    public static ImmutableBooleanValue newBoolean(boolean v)
    {
        return v ? ImmutableBooleanValueImpl.TRUE : ImmutableBooleanValueImpl.FALSE;
    }

    public static ImmutableIntegerValue newInteger(byte v)
    {
        return new ImmutableLongValueImpl(v);
    }

    public static ImmutableIntegerValue newInteger(short v)
    {
        return new ImmutableLongValueImpl(v);
    }

    public static ImmutableIntegerValue newInteger(int v)
    {
        return new ImmutableLongValueImpl(v);
    }

    public static ImmutableIntegerValue newInteger(long v)
    {
        return new ImmutableLongValueImpl(v);
    }

    public static ImmutableIntegerValue newInteger(BigInteger v)
    {
        return new ImmutableBigIntegerValueImpl(v);
    }

    public static ImmutableFloatValue newFloat(float v)
    {
        return new ImmutableDoubleValueImpl(v);
    }

    public static ImmutableFloatValue newFloat(double v)
    {
        return new ImmutableDoubleValueImpl(v);
    }

    public static ImmutableBinaryValue newBinary(byte[] b)
    {
        return newBinary(b, false);
    }

    public static ImmutableBinaryValue newBinary(byte[] b, boolean omitCopy)
    {
        if (omitCopy) {
            return new ImmutableBinaryValueImpl(b);
        }
        else {
            return new ImmutableBinaryValueImpl(Arrays.copyOf(b, b.length));
        }
    }

    public static ImmutableBinaryValue newBinary(byte[] b, int off, int len)
    {
        return newBinary(b, off, len, false);
    }

    public static ImmutableBinaryValue newBinary(byte[] b, int off, int len, boolean omitCopy)
    {
        if (omitCopy && off == 0 && len == b.length) {
            return new ImmutableBinaryValueImpl(b);
        }
        else {
            return new ImmutableBinaryValueImpl(Arrays.copyOfRange(b, off, len));
        }
    }

    public static ImmutableStringValue newString(String s)
    {
        return new ImmutableStringValueImpl(s);
    }

    public static ImmutableStringValue newString(byte[] b)
    {
        return new ImmutableStringValueImpl(b);
    }

    public static ImmutableStringValue newString(byte[] b, boolean omitCopy)
    {
        if (omitCopy) {
            return new ImmutableStringValueImpl(b);
        }
        else {
            return new ImmutableStringValueImpl(Arrays.copyOf(b, b.length));
        }
    }

    public static ImmutableStringValue newString(byte[] b, int off, int len)
    {
        return newString(b, off, len, false);
    }

    public static ImmutableStringValue newString(byte[] b, int off, int len, boolean omitCopy)
    {
        if (omitCopy && off == 0 && len == b.length) {
            return new ImmutableStringValueImpl(b);
        }
        else {
            return new ImmutableStringValueImpl(Arrays.copyOfRange(b, off, len));
        }
    }

    public static ImmutableArrayValue newArray(List<? extends Value> list)
    {
        if (list.isEmpty()) {
            return ImmutableArrayValueImpl.empty();
        }
        Value[] array = list.toArray(new Value[list.size()]);
        return new ImmutableArrayValueImpl(array);
    }

    public static ImmutableArrayValue newArray(Value... array)
    {
        if (array.length == 0) {
            return ImmutableArrayValueImpl.empty();
        }
        else {
            return new ImmutableArrayValueImpl(Arrays.copyOf(array, array.length));
        }
    }

    public static ImmutableArrayValue newArray(Value[] array, boolean omitCopy)
    {
        if (array.length == 0) {
            return ImmutableArrayValueImpl.empty();
        }
        else if (omitCopy) {
            return new ImmutableArrayValueImpl(array);
        }
        else {
            return new ImmutableArrayValueImpl(Arrays.copyOf(array, array.length));
        }
    }

    public static ImmutableArrayValue emptyArray()
    {
        return ImmutableArrayValueImpl.empty();
    }

    public static <K extends Value, V extends Value>
    ImmutableMapValue newMap(Map<K, V> map)
    {
        Value[] kvs = new Value[map.size() * 2];
        int index = 0;
        for (Map.Entry<K, V> pair : map.entrySet()) {
            kvs[index] = pair.getKey();
            index++;
            kvs[index] = pair.getValue();
            index++;
        }
        return new ImmutableMapValueImpl(kvs);
    }

    public static ImmutableMapValue newMap(Value... kvs)
    {
        if (kvs.length == 0) {
            return ImmutableMapValueImpl.empty();
        }
        else {
            return new ImmutableMapValueImpl(Arrays.copyOf(kvs, kvs.length));
        }
    }

    public static ImmutableMapValue newMap(Value[] kvs, boolean omitCopy)
    {
        if (kvs.length == 0) {
            return ImmutableMapValueImpl.empty();
        }
        else if (omitCopy) {
            return new ImmutableMapValueImpl(kvs);
        }
        else {
            return new ImmutableMapValueImpl(Arrays.copyOf(kvs, kvs.length));
        }
    }

    public static ImmutableMapValue emptyMap()
    {
        return ImmutableMapValueImpl.empty();
    }

    public static MapValue newMap(Map.Entry<? extends Value, ? extends Value>... pairs)
    {
        Value[] kvs = new Value[pairs.length * 2];
        for (int i = 0; i < pairs.length; i += 2) {
            kvs[i * 2] = pairs[i].getKey();
            kvs[i * 2 + 1] = pairs[i].getValue();
        }
        return newMap(kvs, true);
    }

    public static MapBuilder newMapBuilder()
    {
        return new MapBuilder();
    }

    public static Map.Entry<Value, Value> newMapEntry(Value key, Value value)
    {
        return new AbstractMap.SimpleEntry<Value, Value>(key, value);
    }

    public static class MapBuilder
    {
        private final Map<Value, Value> map = new LinkedHashMap<Value, Value>();

        public MapBuilder()
        {
        }

        public MapValue build()
        {
            return newMap(map);
        }

        public MapBuilder put(Map.Entry<? extends Value, ? extends Value> pair)
        {
            put(pair.getKey(), pair.getValue());
            return this;
        }

        public MapBuilder put(Value key, Value value)
        {
            map.put(key, value);
            return this;
        }

        public MapBuilder putAll(Iterable<? extends Map.Entry<? extends Value, ? extends Value>> entries)
        {
            for (Map.Entry<? extends Value, ? extends Value> entry : entries) {
                put(entry.getKey(), entry.getValue());
            }
            return this;
        }

        public MapBuilder putAll(Map<? extends Value, ? extends Value> map)
        {
            for (Map.Entry<? extends Value, ? extends Value> entry : map.entrySet()) {
                put(entry);
            }
            return this;
        }
    }

    public static ImmutableExtensionValue newExtension(byte type, byte[] data)
    {
        return new ImmutableExtensionValueImpl(type, data);
    }
}
