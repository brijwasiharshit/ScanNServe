/** @typedef {'guest' | 'admin' | 'superadmin'} UserRole */

/**
 * @typedef {Object} User
 * @property {string} id
 * @property {string} name
 * @property {string} email
 * @property {UserRole} role
 * @property {string} [homestayId] - Present when role is admin
 */

/**
 * @typedef {Object} Homestay
 * @property {string} id
 * @property {string} name
 * @property {string} description
 * @property {string} address
 * @property {string} phone
 * @property {string} imageUrl
 * @property {string} adminId
 */

/**
 * @typedef {'starters' | 'mains' | 'desserts' | 'beverages' | 'snacks'} MenuCategory
 */

/**
 * @typedef {Object} MenuItem
 * @property {string} id
 * @property {string} name
 * @property {string} description
 * @property {number} basePrice - Global catalog price (super admin)
 * @property {MenuCategory} category
 * @property {boolean} isAvailable
 * @property {string} [imageUrl]
 */

/**
 * @typedef {Object} HomestayMenuItem
 * @property {string} id
 * @property {string} homestayId
 * @property {string} menuItemId
 * @property {number} price - Admin-customized price
 * @property {boolean} isAvailable
 */

/**
 * @typedef {Object} MenuItemWithPrice
 * @property {MenuItem} item
 * @property {number} price
 * @property {boolean} isAvailable
 * @property {string} homestayMenuItemId
 */

/**
 * @typedef {Object} Admin
 * @property {string} id
 * @property {string} name
 * @property {string} email
 * @property {string} homestayId
 * @property {boolean} isActive
 */

export const MENU_CATEGORIES = /** @type {const} */ ([
  'starters',
  'mains',
  'desserts',
  'beverages',
  'snacks',
]);

export const CATEGORY_LABELS = /** @type {Record<MenuCategory, string>} */ ({
  starters: 'Starters',
  mains: 'Main Course',
  desserts: 'Desserts',
  beverages: 'Beverages',
  snacks: 'Snacks',
});
