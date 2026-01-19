// lib/Router.js

export class NPRouter {
    /**
     * @param {string} outletSelector CSS selector for outlet div
     */
    constructor(outletSelector = '#outlet') {
        this.routes = [];
        this.outlet = document.querySelector(outletSelector);
        this._onPopState = this._onPopState.bind(this);
        window.addEventListener('popstate', this._onPopState);
    }

    /**
     * Register a single route
     * @param {string} path Route path starting with '/'
     * @param {function|string} handler Function returning DOM or string HTML
     */
    register(path, handler) {
        if (!path.startsWith('/')) {
            throw new Error("Route path must start with '/': " + path);
        }

        const paramNames = [];
        const regexPath = path.replace(/:([^/]+)/g, (_, paramName) => {
            paramNames.push(paramName);
            return '([^/]+)';
        });
        const regex = new RegExp(`^${regexPath}$`);

        this.routes.push({ path, handler, regex, paramNames });
    }

    /**
     * Define routes declaratively
     * @param {Array<{path: string, component: function|string}>} routes 
     */
    define(routes) {
        routes.forEach(({ path, component }) => this.register(path, component));
    }

    /**
     * Navigate programmatically to a route
     * @param {string} path Route path, starting with '/'
     * @param {string|null} arg Optional single parameter (appended to URL)
     */
    navigate(path) {
        if (!path.startsWith('/')) {
            throw new Error("Navigate path must start with '/':" + path);
        }
        history.pushState({ path }, '', path);
        this._render(path);
    }

    _onPopState(event) {
        const { path, arg } = event.state || {};
        if (!path) {
            // fallback to current location
            this._handleLocationChange();
        } else {
            this._render(path, arg);
        }
    }

    _handleLocationChange() {
        const { pathname } = window.location;
        this._render(pathname);
    }


    _render(path) {
        for (const route of this.routes) {
            const match = path.match(route.regex);
            if (match) {
                const paramValues = match.slice(1);
                const arg = paramValues.length === 1 ? paramValues[0] : paramValues;

                const element = route.handler.length > 0 ? route.handler(arg) : route.handler();

                if (element instanceof HTMLElement) {
                    this.outlet.innerHTML = '';
                    this.outlet.appendChild(element);
                } else {
                    console.error("Route handler must return an HTMLElement, got " + element);
                }
                return;
            }
        }

        this.outlet.innerHTML = `<p class="text-danger">404 - Not found: ${path}</p>`;
    }


    _buildUrl(path, arg) {
        return arg != null ? `${path}/${arg}` : path;
    }

    _splitPathAndArg(pathname) {
        const parts = pathname.split('/').filter(Boolean);
        if (parts.length === 0) {
            return ['/home', null];
        }
        if (parts.length === 1) {
            return [`/${parts[0]}`, null];
        }
        return [`/${parts[0]}`, parts[1]];
    }

    /**
     * Call on page load to render current path
     */
    handleInitialLoad() {
        this._handleLocationChange();
    }

    /**
     * Intercept links with [data-route] attribute and route them client-side
     */
    enableLinkInterception() {
        document.body.addEventListener('click', (event) => {
            const link = event.target.closest('a[data-route]');
            if (link) {
                event.preventDefault();
                const href = link.getAttribute('href');
                this.navigate(href);
            }
        });
    }
}
