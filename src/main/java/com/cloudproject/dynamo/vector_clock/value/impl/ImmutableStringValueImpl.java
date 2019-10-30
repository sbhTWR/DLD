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
package com.cloudproject.dynamo.vector_clock.value.impl;

import com.cloudproject.dynamo.vector_clock.core.MessagePacker;
import com.cloudproject.dynamo.vector_clock.value.ImmutableStringValue;
import com.cloudproject.dynamo.vector_clock.value.Value;
import com.cloudproject.dynamo.vector_clock.value.ValueType;
import com.cloudproject.dynamo.vector_clock.value.StringValue;

import java.io.IOException;
import java.util.Arrays;

/**
 * {@code ImmutableStringValueImpl} Implements {@code ImmutableStringValue} using a {@code byte[]} field.
 * This implementation caches result of {@code toString()} and {@code asString()} using a private {@code String} field.
 *
 * @see StringValue
 */
public class ImmutableStringValueImpl
        extends AbstractImmutableRawValue
        implements ImmutableStringValue
{
    public ImmutableStringValueImpl(byte[] data)
    {
        super(data);
    }

    public ImmutableStringValueImpl(String string)
    {
        super(string);
    }

    @Override
    public ValueType getValueType()
    {
        return ValueType.STRING;
    }

    @Override
    public ImmutableStringValue immutableValue()
    {
        return this;
    }

    @Override
    public ImmutableStringValue asStringValue()
    {
        return this;
    }

    @Override
    public void writeTo(MessagePacker pk)
            throws IOException
    {
        pk.packRawStringHeader(data.length);
        pk.writePayload(data);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Value)) {
            return false;
        }
        Value v = (Value) o;
        if (!v.isStringValue()) {
            return false;
        }

        if (v instanceof ImmutableStringValueImpl) {
            ImmutableStringValueImpl bv = (ImmutableStringValueImpl) v;
            return Arrays.equals(data, bv.data);
        }
        else {
            return Arrays.equals(data, v.asStringValue().asByteArray());
        }
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(data);
    }
}
