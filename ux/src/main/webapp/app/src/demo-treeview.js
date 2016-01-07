import {NodeModel} from './treeview/node-model';

const openIcon = 'fa fa-chevron-down';
const closedIcon = 'fa fa-chevron-right'

export class TestTreeview {
    activate() {
        var texas = new NodeModel('Texas', [new NodeModel('Houston'), new NodeModel('Austin')], openIcon, closedIcon);
        var newYork = new NodeModel('New York', [
            new NodeModel('New York City', [
                new NodeModel('Manhattan'),
                new NodeModel('Brooklyn'),
                new NodeModel('Bronx'),
                new NodeModel('Queens'),
                new NodeModel('Staten Island')], openIcon, closedIcon),
            new NodeModel('Buffalo')], openIcon, closedIcon);
        var oregon = new NodeModel('Oregon', [new NodeModel('Portland')], openIcon, closedIcon);
        var california = new NodeModel('California', [new NodeModel('Los Angeles'), new NodeModel('San Francisco')], openIcon, closedIcon);
        this.nodes = [texas, newYork, oregon, california];

        console.log(this.nodes);
    }
}