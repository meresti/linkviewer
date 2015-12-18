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

import {inject} from 'aurelia-framework';
import {Router} from 'aurelia-router';
import {ContentRoomService} from './content-room-service';

@inject(Router, ContentRoomService)
export class ContentRoom {

    constructor(rooter, contentRoomService) {
        this.rooter = rooter;
        this.contentRoomService = contentRoomService;

        this.currentRoom = 1;
        this.currentIndex = 0;
        this.batchSize = 20;
        this.links = [];
    }

    activate() {
        return this._getNextBatch();
    }

    _getNextBatch() {
        return this.contentRoomService.getLinks(this.currentRoom, this.currentIndex, this.batchSize).then(response=> {
            this._addLinks(response);
        });
    }

    _addLinks(links) {
        if (links.length > 0) {
            this.links.push.apply(this.links, links);
            this.currentIndex += links.length;
        }
    }

    handleScrollEvent(event) {
        if (event.target.scrollTop >= event.target.scrollHeight - event.target.offsetHeight - 400) {
            this._getNextBatch();
        }
    }

    addLinkToRoom(link) {
        this.contentRoomService.addLinkToRoom(this.currentRoom, link);
    }

    removeLinkFromRoom(link) {
        this.contentRoomService.removeLinkFromRoom(this.currentRoom, link)
            .then(response=> {
                let index = this.links.indexOf(link);
                if (index > -1) {
                    this.links.splice(index, 1);
                }
            });
    }

    navigate() {
        this.rooter.navigate("users");
    }
}
