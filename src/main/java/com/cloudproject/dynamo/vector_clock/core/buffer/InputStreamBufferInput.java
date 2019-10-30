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
package com.cloudproject.dynamo.vector_clock.core.buffer;

import com.cloudproject.dynamo.vector_clock.core.Preconditions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;


/**
 * {@link MessageBufferInput} adapter for {@link InputStream}
 */
public class InputStreamBufferInput
        implements MessageBufferInput
{
    private InputStream in;
    private final byte[] buffer;

    public static MessageBufferInput newBufferInput(InputStream in)
    {
        Preconditions.checkNotNull(in, "InputStream is null");
        if (in instanceof FileInputStream) {
            FileChannel channel = ((FileInputStream) in).getChannel();
            if (channel != null) {
                return new ChannelBufferInput(channel);
            }
        }
        return new InputStreamBufferInput(in);
    }

    public InputStreamBufferInput(InputStream in)
    {
        this(in, 8192);
    }

    public InputStreamBufferInput(InputStream in, int bufferSize)
    {
        this.in = Preconditions.checkNotNull(in, "input is null");
        this.buffer = new byte[bufferSize];
    }

    /**
     * Reset Stream. This method doesn't close the old resource.
     *
     * @param in new stream
     * @return the old resource
     */
    public InputStream reset(InputStream in)
            throws IOException
    {
        InputStream old = this.in;
        this.in = in;
        return old;
    }

    @Override
    public MessageBuffer next()
            throws IOException
    {
        int readLen = in.read(buffer);
        if (readLen == -1) {
            return null;
        }
        return MessageBuffer.wrap(buffer, 0, readLen);
    }

    @Override
    public void close()
            throws IOException
    {
        in.close();
    }
}
