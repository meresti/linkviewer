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

import $ from 'jquery';
import 'jstree';

export class JsTreeDemo {
    attached() {
        $('#jstree')
            .on('select_node.jstree', function (event, data) {
                console.log('Selection changed to ' + data.node.text + '.');
            })
            .jstree({
                "core": {
                    "multiple": false,
                    "check_callback": true,
                    "themes": {"default": true, dots: false, icons: false},
                }
            });

        let jstree = $('#jstree').jstree();

        jstree.create_node('#', {"id": "TX", "text": "Texas"});
        jstree.create_node('TX', "Houston");
        jstree.create_node('TX', "Austin");
        jstree.create_node('#', {"id": "NY", "text": "New York"});
        jstree.create_node('NY', {"id": "NYC", "text": "New York Cit"});
        jstree.create_node('NYC', "Manhattan");
        jstree.create_node('NYC', "Brooklyn");
        jstree.create_node('NYC', "Bronx");
        jstree.create_node('NYC', "Queens");
        jstree.create_node('NYC', "Staten Island");
        jstree.create_node('NY', "Buffalo");
        jstree.create_node('#', {"id": "OR", "text": "Oregon"});
        jstree.create_node('OR', "Portland");
        jstree.create_node('#', {"id": "CA", "text": "California"});
        jstree.create_node('CA', "Los Angeles");
        jstree.create_node('CA', "San Francisco");
        jstree.open_all();
    }
}