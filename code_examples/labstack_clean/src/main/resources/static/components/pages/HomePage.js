import { NPElement } from '../../lib/NPElement.js'

export class HomePage extends NPElement {

    get template() {
    return this.safeHTML`
        <section class="homepage-welcome">
            <h1>Welcome to LabStack!</h1>
            <p>Your one-stop platform for organizing and ordering electronic parts.</p>            
        </section>
    `;
    }

    constructor(parameters) {
        super();
        this.render();
    }

    render() {
        this.innerHTML = this.template;
    }
}

if (!customElements.get('home-page')) {
    customElements.define('home-page', HomePage);
}