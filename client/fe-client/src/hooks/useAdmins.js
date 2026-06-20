import { useRepositories } from './useRepositories.js';

export function useAdmins() {
  const { adminRepo, version } = useRepositories();
  void version;
  return adminRepo.getAll();
}

export function useAdminActions() {
  const { adminRepo, refresh } = useRepositories();

  return {
    createAdmin: (admin) => {
      const result = adminRepo.create(admin);
      refresh();
      return result;
    },
    updateAdmin: (id, updates) => {
      const result = adminRepo.update(id, updates);
      refresh();
      return result;
    },
    removeAdmin: (id) => {
      const result = adminRepo.remove(id);
      refresh();
      return result;
    },
  };
}
