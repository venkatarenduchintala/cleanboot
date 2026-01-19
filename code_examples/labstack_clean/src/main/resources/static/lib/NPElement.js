export class NPElement extends HTMLElement {

    constructor() {
        super();
        this.UUID = this._generateUid();
    }

    _sanitizeId(idname) {
        // Keep only alphanumeric, underscore, hyphen
        return String(idname).replace(/[^\"A-Za-z0-9_]/g, "_");
    }

    _uniqueIDs(str) {
        return String(str).replace(/id="([^"]*)"/g, (match, idFragment) => {
            const safeFragment = this._sanitizeId(idFragment);
            return `id="${this.UUID}${safeFragment}"`;
        });
    }

    _generateUid() {
        return "np" + Math.random().toString(36).slice(2, 10);
    }

    safeHTML(strings, ...values) {
        // First, build the raw HTML string
        let raw = strings.reduce((out, str, i) => {
            const val = i < values.length ? this._escape(values[i]) : '';
            return out + str + val;
        }, '');

        // THEN perform ID rewriting on the whole string at once
        raw = this._uniqueIDs(raw);

        return raw;
    }

    unsafeHTML(strings, ...values) {
        // First, build the raw HTML string
        let raw = strings.reduce((out, str, i) => {
            const val = i < values.length ? values[i] : '';
            return out + str + val;
        }, '');

        // THEN perform ID rewriting on the whole string at once
        raw = this._uniqueIDs(raw);

        return raw;
    }

    _escape(str) {
        return String(str)
            .replace(/&/g, '&amp;')
            .replace(/"/g, '&quot;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
    }

    _dispatchEvent(name, detail) {
        this.dispatchEvent(new CustomEvent(name, {
            detail,
            bubbles: true,
            composed: true,
        }));
    }

    _getLocalElementNameByID(idname) {
        return "#" + this.UUID + this._sanitizeId(idname);
    }

    _getLocalElementByID(idname) {
        return this.querySelector(
            "#" + this.UUID + this._sanitizeId(idname)
        );
    }

}