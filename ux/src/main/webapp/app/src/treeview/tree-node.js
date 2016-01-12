import {customElement, bindable, inject} from 'aurelia-framework';
import {DOM} from 'aurelia-pal';

@customElement('tree-node')
@inject(Element)
export class TreeNode {
    @bindable node = null;

    constructor(element) {
        this.element = element;
    }

    onClick() {
        let event = DOM.createCustomEvent('select', {
            detail: {node: this.node},
            bubbles: true
        });
        this.element.dispatchEvent(event);
    }
}