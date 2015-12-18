/*
 * Copyright (c) 2015. meresti
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
 */

package meresti.linkviewer.mockito.answers;

import org.mockito.stubbing.Answer;

import java.util.function.Function;

public final class AdditionalAnswers {
    private AdditionalAnswers() {
    }

    public static <S, T> Answer<T> returnsFirstArgMapped(final Class<S> argumentType, final Function<? super S, ? extends T> mapper) {
        return returnsArgumentMappedAt(0, argumentType, mapper);
    }

    public static <S, T> Answer<T> returnsSecondArgMapped(final Class<S> argumentType, final Function<? super S, ? extends T> mapper) {
        return returnsArgumentMappedAt(1, argumentType, mapper);
    }

    public static <S, T> Answer<T> returnsLastArgMapped(final Class<S> argumentType, final Function<? super S, ? extends T> mapper) {
        return returnsArgumentMappedAt(ReturnsArgumentMappedAt.LAST_ARGUMENT, argumentType, mapper);
    }

    public static <S, T> Answer<T> returnsArgumentMappedAt(final int argumentPosition, final Class<S> argumentType, final Function<? super S, ? extends T> mapper) {
        return new ReturnsArgumentMappedAt<>(argumentPosition, argumentType, mapper);
    }
}
