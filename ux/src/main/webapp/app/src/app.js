export class App {
    configureRouter(config, router) {
        config.title = 'Aurelia';
        config.map([
            {route: ['', 'welcome'], name: 'welcome', moduleId: 'welcome', nav: true, title: 'Welcome'},
            {route: 'users', name: 'users', moduleId: 'users', nav: true, title: 'Github Users'},
            {route: 'content-room', name: 'content-room', moduleId: 'content-room', nav: true, title: 'Content Room'},
            {route: 'content-room-treeview', name: 'content-room-treeview', moduleId: 'content-room-treeview', nav: true, title: 'Content Room TreeView'},
            {route: 'demo-treeview', name: 'demo-treeview', moduleId: 'demo-treeview', nav: true, title: 'Demo TreeView'},
            {route: 'child-router', name: 'child-router', moduleId: 'child-router', nav: true, title: 'Child Router'}
        ]);

        this.router = router;
    }
}
