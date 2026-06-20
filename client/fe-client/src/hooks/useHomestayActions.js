import { useRepositories } from './useRepositories.js';

export function useHomestayActions() {
  const { homestayRepo, refresh } = useRepositories();

  return {
    updateHomestay: (homestay) => {
      const result = homestayRepo.update(homestay);
      refresh();
      return result;
    },
  };
}
