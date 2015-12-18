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

import org.mockito.exceptions.Reporter;
import org.mockito.internal.stubbing.answers.ReturnsArgumentAt;
import org.mockito.internal.util.Primitives;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.function.Function;

class ReturnsArgumentMappedAt<S, T> implements Answer<T> {

    public static final int LAST_ARGUMENT = -1;

    private final ReturnsArgumentAt returnsArgumentAt;

    private final Class<S> argumentType;

    private final Function<? super S, ? extends T> mapper;

    ReturnsArgumentMappedAt(final int argumentPosition, final Class<S> argumentType, final Function<? super S, ? extends T> mapper) {
        this.argumentType = argumentType;
        returnsArgumentAt = new ReturnsArgumentAt(argumentPosition);
        this.mapper = mapper;
    }

    @Override
    public T answer(final InvocationOnMock invocation) throws Throwable {
        validateReturnArgIdentity(invocation);
        final S answer = (S) returnsArgumentAt.answer(invocation);
        return mapper.apply(answer);
    }

    private void validateReturnArgIdentity(final InvocationOnMock invocation) {
        returnsArgumentAt.validateIndexWithinInvocationRange(invocation);

        if (!isValidReturnType(returnsArgumentAt.returnedTypeOnSignature(invocation))) {
            new Reporter().wrongTypeOfArgumentToReturn(invocation, argumentType.getSimpleName(),
                    returnsArgumentAt.returnedTypeOnSignature(invocation),
                    returnsArgumentAt.wantedArgumentPosition());
        }
    }

    public boolean isValidReturnType(final Class<?> actualArgumentType) {
        final boolean valid;
        //noinspection IfMayBeConditional
        if (argumentType.isPrimitive() || actualArgumentType.isPrimitive()) {
            valid = Primitives.primitiveTypeOf(actualArgumentType) == Primitives.primitiveTypeOf(argumentType);
        } else {
            valid = argumentType.isAssignableFrom(actualArgumentType);
        }
        return valid;
    }
}
