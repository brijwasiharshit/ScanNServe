import { useRepositories } from './useRepositories.js';

export function useHomestay(homestayId) {
  const { homestayRepo, version } = useRepositories();
  void version;
  return homestayRepo.getById(homestayId);
}

export function useHomestayByAdmin(adminId) {
  const { homestayRepo, version } = useRepositories();
  void version;
  return adminId ? homestayRepo.getByAdminId(adminId) : undefined;
}

export function useAllHomestays() {
  const { homestayRepo, version } = useRepositories();
  void version;
  return homestayRepo.getAll();
}
