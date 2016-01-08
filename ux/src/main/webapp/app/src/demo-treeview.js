import {NodeModel} from './treeview/node-model';

export class DemoTreeview {
    constructor() {
        // temp hack. Couldn't figure out yet how to bind "this" to each node in the template html file.
        this.self = this;
    }

    activate() {
        var texas = new NodeModel('Texas', [new NodeModel('Houston'), new NodeModel('Austin')]);

        let newYork = new NodeModel('New York');
        let newYorkCity = new NodeModel('New York City');
        this.add(newYorkCity, [
            new NodeModel('Manhattan'),
            new NodeModel('Brooklyn'),
            new NodeModel('Bronx'),
            new NodeModel('Queens'),
            new NodeModel('Staten Island')]);
        let buffalo = new NodeModel('Buffalo')
        this.add(newYork, [newYorkCity, buffalo]);

        let oregon = new NodeModel('Oregon', [new NodeModel('Portland')]);
        let california = new NodeModel('California', [new NodeModel('Los Angeles'), new NodeModel('San Francisco')]);
        this.roots = [texas, newYork, oregon, california];
        new NodeModel('Buffalo')
    }

    onClick(node) {
        this.deselectAll(this.roots);
        node.selected = true;

        //this.removeChildren(node);

        //this.remove(node);
    }

    deselectAll(nodes) {
        for (let node of nodes) {
            node.selected = false;
            this.deselectAll(node.children);
        }
    }

    add(parent, children) {
        parent.add(children);
    }

    remove(node) {
        this._remove(node, undefined, this.roots);
    }

    _remove(node, parent, nodes) {
        let candidates = nodes || parent.children;
        for (let i = 0; i < candidates.length; i++) {
            let candidate = candidates[i];
            if (node === candidate) {
                if (parent) {
                    parent.removeAt(i);
                } else {
                    this.roots.splice(i, 1);
                }
            } else {
                this._remove(node, candidate);
            }
        }
    }

    removeChildren(node) {
        node.clearChildren();
    }
}