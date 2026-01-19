import { NPElement } from '../../lib/NPElement.js';
import { ComponentService } from '../../services/ComponentService.js';

export class OrderableItemsPage extends NPElement {

  constructor(internalPartNumber) {
    super();
    this.componentService = ComponentService.getInstance();
    this.internalPartNumber = internalPartNumber;
    this.render();
    this.loadComponent();
  }

  get template() {
    return this.safeHTML`
      <h1>Order Options for ${this.internalPartNumber}</h1>
      <div id="orderableItemsList">Loading...</div>
    `;
  }

  render() {
    this.innerHTML = this.template;
  }

  async loadComponent() {
    try {
      const component = await this.componentService
        .getComponent(this.internalPartNumber);

      if (!Array.isArray(component.orderableItems)) {
        throw new Error("OrderableItems response is not an array");
      }

      this._renderOrderableItems(component.orderableItems);
    } catch (error) {
      this._displayError(`Failed to load components: ${error.message}`);
      console.error('Components loading error:', error);
    }
  }

  _renderOrderableItems(orderableItems) {
    const container = this._getLocalElementByID('orderableItemsList');

    const lines = orderableItems
      .map(item => this._createOrderableItemLine(item))
      .join('');

    container.innerHTML = this.unsafeHTML`
      <table class="table">
        <thead>
          <tr>
            <th>MPN</th>
            <th>SKU</th>
            <th>Distributor</th>
            <th>Min Order Quantity</th>
            <th>Order Multiple</th>
            <th>Price</th>
            <th>Currency</th>
          </tr>
        </thead>
        <tbody>
          ${lines}
          <tr>
            <td><input id="new_mpn" type="text" /></td>
            <td><input id="new_sku" type="text" /></td>
            <td>
              <select id="new_distributor">
                <option value="MOUSER">MOUSER</option>
                <option value="FARNELL">FARNELL</option>
                <option value="DIGIKEY">DIGIKEY</option>
                <option value="LCSC">LCSC</option>
              </select>
            </td>
            <td><input id="new_minQuantity" type="number" /></td>
            <td><input id="new_orderMultiple" type="number" /></td>
            <td><input id="new_price" type="number" step="0.01" /></td>
            <td>
              <select id="new_currency">
                <option value="CHF">CHF</option>
                <option value="USD">USD</option>
                <option value="EUR">EUR</option>
              </select>
            </td>
          </tr>
        </tbody>
      </table>

      <button id="addOrderableItemBtn" class="btn btn-primary">
        Add new orderable Item
      </button>
    `;

    this._attachAddHandler();
  }

  _attachAddHandler() {
    const btn = this._getLocalElementByID('addOrderableItemBtn');
    btn.addEventListener('click', () => this._handleAddOrderableItem());
  }

  async _handleAddOrderableItem() {
    console.log("_handleAddOrderableItem");
    const newItem = {
      mpn: this._getLocalElementByID('new_mpn').value.trim(),
      sku: this._getLocalElementByID('new_sku').value.trim(),
      distributor: this._getLocalElementByID('new_distributor').value,
      minQuantity: Number(this._getLocalElementByID('new_minQuantity').value),
      orderMultiple: Number(this._getLocalElementByID('new_orderMultiple').value),
      price: Number(this._getLocalElementByID('new_price').value),
      currency: this._getLocalElementByID('new_currency').value
    };

    if (!newItem.mpn || !newItem.sku) {
      alert('MPN and SKU are required');
      return;
    }

    try {
      await this.componentService.addOrderableItem(
        this.internalPartNumber,
        newItem
      );

      // Reload updated list
      await this.loadComponent();
    } catch (error) {
      console.error('Failed to add orderable item:', error);
      alert('Failed to add orderable item');
    }
  }

  _createOrderableItemLine(orderableItem) {
    return this.safeHTML`
      <tr>
        <td>${orderableItem.mpn}</td>
        <td>${orderableItem.sku}</td>
        <td>${orderableItem.distributor}</td>
        <td>${orderableItem.minQuantity}</td>
        <td>${orderableItem.orderMultiple}</td>
        <td>${orderableItem.price}</td>
        <td>${orderableItem.currency}</td>
      </tr>
    `;
  }

  _displayError(message) {
    const container = this._getLocalElementByID('orderableItemsList');
    container.innerHTML = this.safeHTML`
      <p class="text-danger">${message}</p>
    `;
  }
}

if (!customElements.get('orderableitems-page')) {
  customElements.define('orderableitems-page', OrderableItemsPage);
}