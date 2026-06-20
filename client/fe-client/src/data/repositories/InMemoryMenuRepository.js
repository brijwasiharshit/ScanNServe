import { catalogItems as initialCatalog, homestayMenuItems as initialHomestayMenu } from '../dummy/menuItems.js';

let nextCatalogId = 100;
let nextHomestayMenuId = 100;

/**
 * @returns {import('../../domain/interfaces/IMenuRepository.js').IMenuRepository}
 */
export function createMenuRepository() {
  /** @type {import('../../domain/types.js').MenuItem[]} */
  let catalog = structuredClone(initialCatalog);

  /** @type {import('../../domain/types.js').HomestayMenuItem[]} */
  let homestayMenu = structuredClone(initialHomestayMenu);

  return {
    getCatalogItems() {
      return [...catalog];
    },

    addCatalogItem(item) {
      const newItem = { ...item, id: `item-${++nextCatalogId}` };
      catalog.push(newItem);
      return newItem;
    },

    updateCatalogItem(id, updates) {
      const index = catalog.findIndex((i) => i.id === id);
      if (index === -1) return undefined;
      catalog[index] = { ...catalog[index], ...updates };
      return catalog[index];
    },

    deleteCatalogItem(id) {
      const before = catalog.length;
      catalog = catalog.filter((i) => i.id !== id);
      homestayMenu = homestayMenu.filter((hm) => hm.menuItemId !== id);
      return catalog.length < before;
    },

    getHomestayMenu(homestayId) {
      return homestayMenu
        .filter((hm) => hm.homestayId === homestayId)
        .map((hm) => {
          const item = catalog.find((c) => c.id === hm.menuItemId);
          if (!item) return null;
          return {
            item,
            price: hm.price,
            isAvailable: hm.isAvailable && item.isAvailable,
            homestayMenuItemId: hm.id,
          };
        })
        .filter(Boolean);
    },

    addToHomestay(homestayId, menuItemId, price) {
      const existing = homestayMenu.find(
        (hm) => hm.homestayId === homestayId && hm.menuItemId === menuItemId,
      );
      if (existing) return existing;

      const catalogItem = catalog.find((c) => c.id === menuItemId);
      const newEntry = {
        id: `hm-${++nextHomestayMenuId}`,
        homestayId,
        menuItemId,
        price: price ?? catalogItem?.basePrice ?? 0,
        isAvailable: true,
      };
      homestayMenu.push(newEntry);
      return newEntry;
    },

    updateHomestayMenuItem(homestayMenuItemId, updates) {
      const index = homestayMenu.findIndex((hm) => hm.id === homestayMenuItemId);
      if (index === -1) return undefined;
      homestayMenu[index] = { ...homestayMenu[index], ...updates };
      return homestayMenu[index];
    },

    removeFromHomestay(homestayMenuItemId) {
      const before = homestayMenu.length;
      homestayMenu = homestayMenu.filter((hm) => hm.id !== homestayMenuItemId);
      return homestayMenu.length < before;
    },
  };
}
