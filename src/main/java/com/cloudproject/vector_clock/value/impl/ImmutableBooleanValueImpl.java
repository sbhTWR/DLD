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
import com.cloudproject.dynamo.vector_clock.value.ImmutableBooleanValue;
import com.cloudproject.dynamo.vector_clock.value.BooleanValue;
import com.cloudproject.dynamo.vector_clock.value.Value;
import com.cloudproject.dynamo.vector_clock.value.ValueType;

import java.io.IOException;

/**
 * {@code ImmutableBooleanValueImpl} Implements {@code ImmutableBooleanValue} using a {@code boolean} field.
 *
 * This class is a singleton. {@code ImmutableBooleanValueImpl.trueInstance()} and {@code ImmutableBooleanValueImpl.falseInstance()} are the only instances of this class.
 *
 * @see BooleanValue
 */
public class ImmutableBooleanValueImpl
        extends AbstractImmutableValue
        implements ImmutableBooleanValue
{
    public static final ImmutableBooleanValue TRUE = new ImmutableBooleanValueImpl(true);
    public static final ImmutableBooleanValue FALSE = new ImmutableBooleanValueImpl(false);

    private final boolean value;

    private ImmutableBooleanValueImpl(boolean value)
    {
        this.value = value;
    }

    @Override
    public ValueType getValueType()
    {
        return ValueType.BOOLEAN;
    }

    @Override
    public ImmutableBooleanValue asBooleanValue()
    {
        return this;
    }

    @Override
    public ImmutableBooleanValue immutableValue()
    {
        return this;
    }

    @Override
    public boolean getBoolean()
    {
        return value;
    }

    @Override
    public void writeTo(MessagePacker packer)
            throws IOException
    {
        packer.packBoolean(value);
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Value)) {
            return false;
        }
        Value v = (Value) o;

        if (!v.isBooleanValue()) {
            return false;
        }
        return value == v.asBooleanValue().getBoolean();
    }

    @Override
    public int hashCode()
    {
        if (value) {
            return 1231;
        }
        else {
            return 1237;
        }
    }

    @Override
    public String toJson()
    {
        return Boolean.toString(value);
    }

    @Override
    public String toString()
    {
        return toJson();
    }
}