/*
 * Copyright (c) 2016. meresti
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

export class Tree {
    constructor() {
        this.roots = [];
    }

    setRoots(roots) {
        this.roots = roots;
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

    select(node) {
        node.selected = true;
    }

    deselect(node) {
        node.selected = false;
    }

    deselectAll(nodes) {
        for (let node of nodes) {
            this.deselect(node);
            this.deselectAll(node.children);
        }
    }

    onClick(node) {
        this.deselectAll(this.roots);
        this.select(node);

        //this.removeChildren(node);

        //this.remove(node);
    }
}