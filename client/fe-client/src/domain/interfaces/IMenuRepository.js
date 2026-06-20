/**
 * @typedef {import('../types.js').MenuItem} MenuItem
 * @typedef {import('../types.js').HomestayMenuItem} HomestayMenuItem
 * @typedef {import('../types.js').MenuItemWithPrice} MenuItemWithPrice
 */

/**
 * @typedef {Object} IMenuRepository
 * @property {() => MenuItem[]} getCatalogItems
 * @property {(item: Omit<MenuItem, 'id'>) => MenuItem} addCatalogItem
 * @property {(id: string, updates: Partial<MenuItem>) => MenuItem | undefined} updateCatalogItem
 * @property {(id: string) => boolean} deleteCatalogItem
 * @property {(homestayId: string) => MenuItemWithPrice[]} getHomestayMenu
 * @property {(homestayId: string, menuItemId: string, price: number) => HomestayMenuItem} addToHomestay
 * @property {(homestayMenuItemId: string, updates: Partial<HomestayMenuItem>) => HomestayMenuItem | undefined} updateHomestayMenuItem
 * @property {(homestayMenuItemId: string) => boolean} removeFromHomestay
 */

export {};
