// components/NavBar.js
import { NPElement } from '../../lib/NPElement.js';

export class NavBar extends NPElement {
  constructor() {
    super();
    this.render();
  }

  get template() {
    return this.safeHTML`
      <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
          <a class="navbar-brand" href="/home" data-route>Home</a>
          <a class="navbar-brand" href="/components" data-route>Components</a>
        </div>
      </nav>
    `;
  }

  render() {
    this.innerHTML = this.template;
  }

}

if (!customElements.get('nav-bar')) {
  customElements.define('nav-bar', NavBar);
}
