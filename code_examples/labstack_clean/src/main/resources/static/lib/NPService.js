export class NPService {
  constructor(basePath = '') {
    this.basePath = basePath;
  }

  async request(endpoint, options = {}) {
    const url = `${this.basePath}${endpoint}`;
    const response = await fetch(url, {
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
        ...(options.headers || {})
      },
      ...options
    });

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`${response.status} ${response.statusText}: ${errorText}`);
    }

    const contentType = response.headers.get('content-type') || '';
    return contentType.includes('application/json')
      ? await response.json()
      : await response.text();
  }

  async post(endpoint, data, options = {}) {
  const url = `${this.basePath}${endpoint}`;

  const response = await fetch(url, {
    method: 'POST',
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json',
      ...(options.headers || {})
    },
    body: JSON.stringify(data),
    ...options
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(`${response.status} ${response.statusText}: ${errorText}`);
  }

  const contentType = response.headers.get('content-type') || '';

  return contentType.includes('application/json')
    ? await response.json()
    : await response.text();
}
}
