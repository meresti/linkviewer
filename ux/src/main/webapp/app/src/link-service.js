import {inject} from 'aurelia-framework';
import {HttpClient} from 'aurelia-fetch-client';
import 'fetch';

export class LinkService {
    constructor() {
        this.http = new HttpClient().configure(config=> {
            config.useStandardConfiguration();
        });
    }


    getLinks(currentIndex, batchSize) {
        return this.http.fetch('/linkviewer/links/' + currentIndex + '/' + batchSize).then(response => response.json());
    }
}
