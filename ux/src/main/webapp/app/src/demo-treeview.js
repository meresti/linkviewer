import {NodeModel} from './treeview/node-model';
import {Tree} from './treeview/tree';

export class DemoTreeview {
    constructor() {
        this.tree1 = new Tree();
        this.tree2 = new Tree();
    }

    activate() {
        var texas = new NodeModel('Texas', [new NodeModel('Houston'), new NodeModel('Austin')]);

        let newYork = new NodeModel('New York');
        let newYorkCity = new NodeModel('New York City');
        this.tree1.add(newYorkCity, [
            new NodeModel('Manhattan'),
            new NodeModel('Brooklyn'),
            new NodeModel('Bronx'),
            new NodeModel('Queens'),
            new NodeModel('Staten Island')]);
        let buffalo = new NodeModel('Buffalo')
        this.tree1.add(newYork, [newYorkCity, buffalo]);

        let oregon = new NodeModel('Oregon', [new NodeModel('Portland')]);
        let california = new NodeModel('California', [new NodeModel('Los Angeles'), new NodeModel('San Francisco')]);

        let roots = [texas, newYork, oregon, california];

        this.tree1.setRoots(roots);

        let hargita = new NodeModel('Hargita', [new NodeModel('Almas'), new NodeModel('Lovete')]);
        let kolozs = new NodeModel('Kolozs', [new NodeModel('Kolozsvar')]);

        this.tree2.setRoots([hargita, kolozs]);
    }

    onSelectionChanged(event) {
        console.log("Selection changed to: " + event.detail.node.data);
    }
}