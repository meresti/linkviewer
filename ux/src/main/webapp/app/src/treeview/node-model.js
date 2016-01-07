export class NodeModel {
    constructor(name, children, openIcon, closedIcon) {
        this.name = name;
        this.children = children || [];
        this.visible = true;
        this.openIcon = openIcon || 'fa fa-caret-down';
        this.closedIcon = closedIcon || 'fa fa-caret-right';

        if (this.hasChildren()) {
            this.icon = this.openIcon;
            this.expanded = true;
        }
    }

    hasChildren() {
        return this.children.length > 0;
    }

    toggleNode() {
        for (var i = 0; i < this.children.length; i++) {
            this.children[i].visible = !this.children[i].visible;
        }

        this.expanded = !this.expanded;
        this.icon = this.expanded === true ? this.openIcon : this.closedIcon;
    }
}