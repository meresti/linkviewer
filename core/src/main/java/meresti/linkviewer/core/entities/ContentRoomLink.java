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

package meresti.linkviewer.core.entities;

import org.jetbrains.annotations.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.EnumMap;
import java.util.Map;

@Document(collection = "ContentRoomLink")
public class ContentRoomLink {

    @Id
    private BigInteger id;

    @DBRef
    private ContentRoom room;

    @DBRef
    private Link link;

    private @Nullable Relevance relevance;

    private @Nullable Map<Relevance, Long> relevanceCounts = new EnumMap<>(Relevance.class);

    private BigDecimal relevanceRate = BigDecimal.ZERO;

    public ContentRoomLink() {
    }

    public ContentRoomLink(final BigInteger id, final ContentRoom room, final Link link) {
        this.id = id;
        this.room = room;
        this.link = link;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(final BigInteger id) {
        this.id = id;
    }

    public ContentRoom getRoom() {
        return room;
    }

    public void setRoom(final ContentRoom room) {
        this.room = room;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(final Link link) {
        this.link = link;
    }

    public @Nullable Relevance getRelevance() {
        return relevance;
    }

    public void setRelevance(final @Nullable Relevance relevance) {
        this.relevance = relevance;
    }

    public @Nullable Map<Relevance, Long> getRelevanceCounts() {
        return relevanceCounts;
    }

    public void setRelevanceCounts(final @Nullable Map<Relevance, Long> relevanceCounts) {
        this.relevanceCounts = relevanceCounts;
    }

    public BigDecimal getRelevanceRate() {
        return relevanceRate;
    }

    public void setRelevanceRate(final BigDecimal relevanceRate) {
        this.relevanceRate = relevanceRate;
    }
}
