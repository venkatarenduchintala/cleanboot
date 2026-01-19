
import {NPService} from '../lib/NPService.js'

export class ComponentService extends NPService {
static #instance = null;

  static getInstance() {
    if (!ComponentService.#instance) {
      ComponentService.#instance = new ComponentService();
    }
    return ComponentService.#instance;
  }

  constructor() {
    super('/api/components');
  }

  getAllComponents() {
    return this.request('');
  }

  getComponent(internalPartNumber) {
    return this.request('/' + internalPartNumber);
  }

  addOrderableItem(internalPartNumber, orderableItem) {
    return this.post('/' + internalPartNumber + "/orderableItems", orderableItem);
  }
  
}