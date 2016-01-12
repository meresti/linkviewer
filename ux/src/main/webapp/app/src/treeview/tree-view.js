import {customElement, bindable, inject} from 'aurelia-framework';
import {DOM} from 'aurelia-pal';

@customElement('tree-view')
@inject(Element)
export class TreeView {
    @bindable tree;

    constructor(element) {
        this.element = element;
    }

    onSelect(event) {
        let node = event.detail.node;

        let oldSelection = this.tree.getSelection();

        this.tree.select(node);

        if (oldSelection !== node) {
            let event = DOM.createCustomEvent('selection', {
                detail: {node: node},
                bubbles: true
            });
            this.element.dispatchEvent(event);
        }
    }
}