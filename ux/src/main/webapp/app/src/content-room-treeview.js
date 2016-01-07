import {inject} from 'aurelia-framework';
import {ContentRoomService} from './content-room-service';
import {NodeModel} from './treeview/node-model';

@inject(ContentRoomService)
export class TestTreeview {
    constructor(contentRoomService) {
        this.contentRoomService = contentRoomService;
    }

    activate() {
        this.contentRoomService.getRooms().then(rooms=> {
            let roomNodes = [];
            for (let room of rooms) {
                let node = new NodeModel(room.name);
                roomNodes.push(node);
            }

            let allRooms = new NodeModel('All', roomNodes);

            this.nodes = [allRooms];
        });
    }
}