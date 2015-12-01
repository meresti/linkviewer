import {inject} from 'aurelia-framework';
import {Router} from 'aurelia-router';
import {LinkService} from './link-service';

@inject(Router, LinkService)
export class Cards {
    currentIndex = 0;
    batchSize = 20;
    cards = [];

    constructor(rooter, service) {
        this.rooter = rooter;
        this.service = service;
    }

    activate() {
        return this.service.getLinks(this.currentIndex, this.batchSize).then(response=> {
            this.addCards(response);
        });
    }

    addCards(cards) {
        if (cards.length > 0) {
            this.cards.push.apply(this.cards, cards);
            this.currentIndex += cards.length;
        }
    }

    handleScrollEvent(event) {
        if (event.target.scrollTop >= event.target.scrollHeight - event.target.offsetHeight - 400) {
            this.service.getLinks(this.currentIndex, this.batchSize).then(response=> {
                this.addCards(response);
            });
        }
    }

    navigate() {
        this.rooter.navigate("users");
    }
}
