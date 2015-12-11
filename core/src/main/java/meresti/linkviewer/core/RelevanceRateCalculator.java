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

package meresti.linkviewer.core;

import meresti.linkviewer.core.entities.Relevance;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Map;

public class RelevanceRateCalculator {

    public BigDecimal calculateFrom(final Map<Relevance, Long> relevanceCounts) {
        BigInteger relevanceRateCount = BigInteger.ZERO;
        BigDecimal relevanceRateSum = BigDecimal.ZERO;
        for (final Map.Entry<Relevance, Long> entry : relevanceCounts.entrySet()) {
            final Relevance relevance = entry.getKey();
            final Long count = entry.getValue();
            relevanceRateCount = relevanceRateCount.add(BigInteger.valueOf(count));
            relevanceRateSum = relevanceRateSum.add(BigDecimal.valueOf(count * (long) relevance.getWeight()));

        }
        return BigInteger.ZERO.equals(relevanceRateCount) ? BigDecimal.ZERO : relevanceRateSum.divide(new BigDecimal(relevanceRateCount), new MathContext(2, RoundingMode.HALF_DOWN));
    }
}
