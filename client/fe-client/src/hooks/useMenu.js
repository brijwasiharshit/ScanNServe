import { useRepositories } from './useRepositories.js';

export function useHomestayMenu(homestayId) {
  const { menuRepo, version } = useRepositories();
  void version;
  return homestayId ? menuRepo.getHomestayMenu(homestayId) : [];
}

export function useCatalogItems() {
  const { menuRepo, version } = useRepositories();
  void version;
  return menuRepo.getCatalogItems();
}

export function useMenuActions() {
  const { menuRepo, refresh } = useRepositories();

  return {
    addCatalogItem: (item) => {
      const result = menuRepo.addCatalogItem(item);
      refresh();
      return result;
    },
    updateCatalogItem: (id, updates) => {
      const result = menuRepo.updateCatalogItem(id, updates);
      refresh();
      return result;
    },
    deleteCatalogItem: (id) => {
      const result = menuRepo.deleteCatalogItem(id);
      refresh();
      return result;
    },
    addToHomestay: (homestayId, menuItemId, price) => {
      const result = menuRepo.addToHomestay(homestayId, menuItemId, price);
      refresh();
      return result;
    },
    updateHomestayMenuItem: (id, updates) => {
      const result = menuRepo.updateHomestayMenuItem(id, updates);
      refresh();
      return result;
    },
    removeFromHomestay: (id) => {
      const result = menuRepo.removeFromHomestay(id);
      refresh();
      return result;
    },
  };
}
