export class App {
    configureRouter(config, router) {
        config.title = 'Aurelia';
        config.map([
            {route: ['', 'welcome'], name: 'welcome', moduleId: 'welcome', nav: true, title: 'Welcome'},
            {route: 'users', name: 'users', moduleId: 'users', nav: true, title: 'Github Users'},
            {route: 'demo-treeview', name: 'demo-treeview', moduleId: 'demo-treeview', nav: true, title: 'Demo TreeView'},
            {route: 'child-router', name: 'child-router', moduleId: 'child-router', nav: true, title: 'Child Router'},
            {route: 'link-viewer', name: 'link-viewer', moduleId: 'link-viewer', nav: true, title: 'Link Viewer'},
        ]);

        this.router = router;
    }
}
