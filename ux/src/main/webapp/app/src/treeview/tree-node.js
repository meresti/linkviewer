import {customElement, bindable} from 'aurelia-framework';

@customElement('tree-node')
export class TreeNode {
    @bindable node = null;
    @bindable treeview;

    onClick() {
        if (this.treeview) {
            this.treeview.onClick(this.node);
        }
    }
}