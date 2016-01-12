/*
 * Copyright (c) 2016. meresti
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
import {EventAggregator} from 'aurelia-event-aggregator';

import {ContentRoomService} from './content-room-service';
import {NodeModel} from './treeview/node-model';
import {Tree} from './treeview/tree';

const SpecialContentRooms = {
    ALL_ROOMS: {name: 'All', id: '-1'}
};

@inject(EventAggregator, ContentRoomService)
export class LinkViewerRouter {
    heading = 'Child Router';

    configureRouter(config, router) {
        config.map([
            {route: '', redirect: 'entry-page'},
            {route: 'content-room/:id', name: 'content-room', moduleId: 'content-room', title: 'Content Room'},
            {route: 'entry-page', name: 'entry-page', moduleId: 'entry-page', title: 'Entry Page'},
        ]);

        this.router = router;
    }

    constructor(eventAggregator, contentRoomService) {
        this.eventAggregator = eventAggregator;
        this.contentRoomService = contentRoomService;
        this.tree = new Tree();
    }

    activate() {
        return this.contentRoomService.getRooms().then(rooms=> {
            let roomNodes = [];
            for (let room of rooms) {
                let node = new NodeModel({text: room.name, key: room.roomId});
                roomNodes.push(node);
            }

            let allRooms = new NodeModel({text: SpecialContentRooms.ALL_ROOMS.name, key: SpecialContentRooms.ALL_ROOMS.id}, roomNodes);

            this.tree.setRoots([allRooms]);
        });
    }

    attached() {
        // Quite sure this is not the best way of finding the current room's id.
        // One solution would be to publish a message from content-room.js (where the id is available) but I don't like that solution either.
        this.subscription = this.eventAggregator.subscribe(
            'router:navigation:success',
            payload => {
                let instruction = payload.instruction;
                let fragment = instruction.fragment;
                let idStart = fragment.indexOf('content-room/');
                if (idStart > -1) {
                    let id = fragment.substring(idStart + 'content-room/'.length);

                    let node = this.tree.findByKey(id);
                    if (this.tree.getSelection() !== node) {
                        this.tree.select(node);
                    }
                }
            }
        );
    }

    detached() {
        this.subscription.dispose();
    }

    onSelectionChanged(event) {
        this.router.navigateToRoute("content-room", {id: event.detail.node.data.key});
    }
}
