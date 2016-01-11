import {customElement, bindable} from 'aurelia-framework';

@customElement('tree-node')
export class TreeNode {
    @bindable node = null;
    @bindable tree;

    onClick() {
        if (this.tree) {
            this.tree.onClick(this.node);
        }
    }
}