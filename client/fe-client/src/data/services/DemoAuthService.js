/**
 * Demo auth service — replace with real authentication later.
 * @param {import('../../domain/interfaces/IAdminRepository.js').IAdminRepository} adminRepo
 * @returns {import('../../domain/interfaces/IAuthService.js').IAuthService}
 */
export function createAuthService(adminRepo) {
  /** @type {import('../../domain/types.js').User | null} */
  let currentUser = null;

  return {
    getCurrentUser() {
      return currentUser;
    },

    loginAs(role, adminId) {
      if (role === 'guest') {
        currentUser = { id: 'guest', name: 'Guest', email: '', role: 'guest' };
        return currentUser;
      }

      if (role === 'superadmin') {
        currentUser = {
          id: 'superadmin',
          name: 'Super Admin',
          email: 'superadmin@scannserve.com',
          role: 'superadmin',
        };
        return currentUser;
      }

      if (role === 'admin' && adminId) {
        const admin = adminRepo.getById(adminId);
        if (!admin || !admin.isActive) return null;
        currentUser = {
          id: admin.id,
          name: admin.name,
          email: admin.email,
          role: 'admin',
          homestayId: admin.homestayId,
        };
        return currentUser;
      }

      return null;
    },

    logout() {
      currentUser = null;
    },
  };
}
