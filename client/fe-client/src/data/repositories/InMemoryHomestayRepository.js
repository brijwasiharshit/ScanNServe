import { homestays as initialHomestays } from '../dummy/homestays.js';

/**
 * In-memory homestay repository.
 * Implements IHomestayRepository — swap with API implementation later without changing consumers.
 * @returns {import('../../domain/interfaces/IHomestayRepository.js').IHomestayRepository}
 */
export function createHomestayRepository() {
  /** @type {import('../../domain/types.js').Homestay[]} */
  let store = structuredClone(initialHomestays);

  return {
    getById(id) {
      return store.find((h) => h.id === id);
    },

    getAll() {
      return [...store];
    },

    update(homestay) {
      const index = store.findIndex((h) => h.id === homestay.id);
      if (index === -1) return homestay;
      store[index] = { ...homestay };
      return store[index];
    },

    getByAdminId(adminId) {
      return store.find((h) => h.adminId === adminId);
    },
  };
}
