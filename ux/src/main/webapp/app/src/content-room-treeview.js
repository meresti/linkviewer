import {inject} from 'aurelia-framework';
import {Router} from 'aurelia-router';

import {ContentRoomService} from './content-room-service';
import {NodeModel} from './treeview/node-model';
import {Tree} from './treeview/tree';

@inject(Router, ContentRoomService)
export class ContentRoomTreeview {
    constructor(router, contentRoomService) {
        this.router = router;
        this.contentRoomService = contentRoomService;
        this.tree = new Tree();
    }

    activate() {
        return this.contentRoomService.getRooms().then(rooms=> {
            let roomNodes = [];
            for (let room of rooms) {
                let node = new NodeModel({text: room.name, id: room.roomId});
                roomNodes.push(node);
            }

            let allRooms = new NodeModel('All', roomNodes);

            this.tree.setRoots([allRooms]);
        });
    }

    onSelectionChanged(event) {
        console.log('Content room "' + event.detail.node.data.text + '(id:' + event.detail.node.data.id + ')" selected.');
        this.router.navigateToRoute("content-room", {id: event.detail.node.data.id});
    }
}