import {NodeModel} from './treeview/node-model';
import {Tree} from './treeview/tree';

export class DemoTreeview {
    constructor() {
        this.tree = new Tree();
    }

    activate() {
        var texas = new NodeModel('Texas', [new NodeModel('Houston'), new NodeModel('Austin')]);

        let newYork = new NodeModel('New York');
        let newYorkCity = new NodeModel('New York City');
        this.tree.add(newYorkCity, [
            new NodeModel('Manhattan'),
            new NodeModel('Brooklyn'),
            new NodeModel('Bronx'),
            new NodeModel('Queens'),
            new NodeModel('Staten Island')]);
        let buffalo = new NodeModel('Buffalo')
        this.tree.add(newYork, [newYorkCity, buffalo]);

        let oregon = new NodeModel('Oregon', [new NodeModel('Portland')]);
        let california = new NodeModel('California', [new NodeModel('Los Angeles'), new NodeModel('San Francisco')]);

        let roots = [texas, newYork, oregon, california];

        this.tree.setRoots(roots);
    }
}