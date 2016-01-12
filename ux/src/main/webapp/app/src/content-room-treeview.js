import {inject} from 'aurelia-framework';
import {ContentRoomService} from './content-room-service';
import {NodeModel} from './treeview/node-model';
import {Tree} from './treeview/tree';

@inject(ContentRoomService)
export class TestTreeview {
    constructor(contentRoomService) {
        this.contentRoomService = contentRoomService;
        this.tree = new Tree();
    }

    activate() {
        this.contentRoomService.getRooms().then(rooms=> {
            let roomNodes = [];
            for (let room of rooms) {
                let node = new NodeModel(room.name);
                roomNodes.push(node);
            }

            let allRooms = new NodeModel('All', roomNodes);

            this.tree.setRoots([allRooms]);
        });
    }

    onSelectionChanged(event) {
        console.log('Content room "' + event.detail.node.data + '" selected.');
    }
}