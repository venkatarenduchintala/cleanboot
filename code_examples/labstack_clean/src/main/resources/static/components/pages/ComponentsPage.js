import { NPElement } from '../../lib/NPElement.js';
import { ComponentService } from '../../services/ComponentService.js';

export class ComponentsPage extends NPElement {
  constructor() {
    super();
    this.componentService = ComponentService.getInstance();
    this.render();
    this.loadComponents();
  }

  get template() {
    return this.safeHTML`
      <h1>Available Components</h1>
      <div id="componentList">Loading...</div>
    `;
  }

  render() {
    this.innerHTML = this.template;
  }

  async loadComponents() {
    try {
      const components = await this.componentService.getAllComponents();

      if (!Array.isArray(components)) {
        throw new Error("Books response is not an array");
      }

      this._renderComponents(components);
    } catch (error) {
      this._displayError(`Failed to load components: ${error.message}`);
      console.error('Components loading error:', error);
    }
  }

  _renderComponents(components) {
    const container = this._getLocalElementByID('componentList');

    const lines = components.map(component => this._createComponentEntry(component)).join('');
    container.innerHTML = this.unsafeHTML`
      <table class="table">
        <thead>
          <tr>
            <th scope="col">Part</th>
            <th scope="col">Category</th>
            <th scope="col">Value</th>
            <th scope="col">Footprint</th>
            <th scope="col">Footprint</th>
          </tr>
        </thead>
        <tbody>
          ${lines}
        <tbody>
      </table>`;
  }



  _createComponentEntry(component) {
    return this.safeHTML`
      <tr>
        <th scope="row"><a href="/components/${component.ipn}/orderableItems" data-route>${component.ipn}</a></th>
        <td>${component.category}</td>
        <td>${component.value}</td>
        <td>${component.symbol}</td>        
        <td>${component.footprint}</td>        
      </tr>
    `;
  }

  _displayError(message) {
    const container = this._getLocalElementByID('componentList');
    container.innerHTML = this.safeHTML`<p class="text-danger">${message}</p>`;
  }
}

if (!customElements.get('components-page')) {
  customElements.define('components-page', ComponentsPage);
}
