export class NodeModel {
    constructor(data, children) {
        if (typeof data === 'string' || data instanceof String) {
            this.data = {text: data, key: data};
        } else {
            this.data = data;
        }
        this.children = children || [];
        this.visible = true;
        this.selected = false;

        this._refreshState();
    }

    _refreshState() {
        if (this.children.length > 0) {
            this.expanded = true;
        } else {
            this.expanded = undefined;
        }
    }

    toggleNode() {
        for (var i = 0; i < this.children.length; i++) {
            this.children[i].visible = !this.children[i].visible;
        }

        this.expanded = !this.expanded;
    }

    add(children) {
        this.children.push.apply(this.children, children);
        this._refreshState();
    }

    remove(children) {
        for (let child of children) {
            let index = this.children.indexOf(child);
            if (index > -1) {
                this.children.splice(index, 1);
                this._refreshState();
            }
        }
    }

    removeAt(index) {
        this.children.splice(index, 1);
        this._refreshState();
    }

    clearChildren() {
        this.children = [];
        this._refreshState();
    }
}