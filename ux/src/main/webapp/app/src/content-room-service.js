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

import {HttpClient} from 'aurelia-http-client';
import {ContentRoomLink} from 'content-room-link'

export class ContentRoomService {
    constructor() {
        this.http = new HttpClient().configure(builder=> {
            builder.withBaseUrl('/linkviewer');
        });
    }

    getLinks(roomId, currentIndex, batchSize) {
        return this.http.get('/rooms/' + roomId + '/links/' + currentIndex + '/' + batchSize)
            .then(responseMessage => JSON.parse(responseMessage.response))
            .then(links => links.map(link => new ContentRoomLink(link)));
    }

    addLinkToRoom(roomId, link) {
        return this.http.post('/rooms/' + roomId + '/links', link);
    }

    removeLinkFromRoom(roomId, link) {
        return this.http.delete('/rooms/' + roomId + '/links/' + link.linkId);
    }
}