import { admins as initialAdmins } from '../dummy/admins.js';
import { homestays as initialHomestays } from '../dummy/homestays.js';

let nextAdminId = 100;
let nextHomestayId = 100;

/**
 * @returns {import('../../domain/interfaces/IAdminRepository.js').IAdminRepository}
 */
export function createAdminRepository() {
  /** @type {import('../../domain/types.js').Admin[]} */
  let store = structuredClone(initialAdmins);

  /** @type {import('../../domain/types.js').Homestay[]} */
  let homestays = structuredClone(initialHomestays);

  return {
    getAll() {
      return [...store];
    },

    getById(id) {
      return store.find((a) => a.id === id);
    },

    create(admin) {
      const homestayId = admin.homestayId || `hs-${++nextHomestayId}`;
      const newAdmin = { ...admin, id: `admin-${++nextAdminId}`, homestayId, isActive: true };

      if (!admin.homestayId) {
        homestays.push({
          id: homestayId,
          name: 'New Property',
          description: 'Update your property description in settings.',
          address: '',
          phone: '',
          imageUrl: 'https://images.unsplash.com/photo-1566073771259-6a8506099945?w=800&q=80',
          adminId: newAdmin.id,
        });
      } else {
        const hs = homestays.find((h) => h.id === homestayId);
        if (hs) hs.adminId = newAdmin.id;
      }

      store.push(newAdmin);
      return newAdmin;
    },

    update(id, updates) {
      const index = store.findIndex((a) => a.id === id);
      if (index === -1) return undefined;
      store[index] = { ...store[index], ...updates };
      return store[index];
    },

    remove(id) {
      const before = store.length;
      store = store.filter((a) => a.id !== id);
      return store.length < before;
    },
  };
}
