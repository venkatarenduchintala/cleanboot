import { NPElement } from './lib/NPElement.js'
import { HomePage } from './components/pages/HomePage.js'
import { ComponentsPage } from './components/pages/ComponentsPage.js'
import { OrderableItemsPage } from './components/pages/OrderableItemsPage.js'


import { NavBar } from './components/navigation/NavBar.js'
import { NPRouter } from './lib/NPRouter.js';


export class App extends NPElement {

    get template() {
        return this.safeHTML`
            <nav-bar></nav-bar>
            <div id="outlet"></div> 
        `;
    }

    constructor(parameters) {
        super();
        this.render();

        const router = new NPRouter(this._getLocalElementNameByID('outlet'));

        router.define([
                { path: '/', component: () => new HomePage() },
                { path: '/home', component: () => new HomePage() },
                { path: '/components', component: () => new ComponentsPage() },                     
                { path: '/components/:id/orderableItems', component: (id) => new OrderableItemsPage(id) },           
            ]);

        router.handleInitialLoad();
        router.enableLinkInterception();

        window.router = router;
    }

    render() {
        this.innerHTML = this.template;
    }

}

if (!customElements.get('np-app')) {
    customElements.define('np-app', App);
}

