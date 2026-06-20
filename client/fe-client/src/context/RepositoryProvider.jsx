import { useMemo, useReducer } from 'react';
import { createHomestayRepository } from '../data/repositories/InMemoryHomestayRepository.js';
import { createMenuRepository } from '../data/repositories/InMemoryMenuRepository.js';
import { createAdminRepository } from '../data/repositories/InMemoryAdminRepository.js';
import { createAuthService } from '../data/services/DemoAuthService.js';
import { RepositoryContext } from './repositoryContext.js';

function repositoryReducer(state) {
  return state + 1;
}

export function RepositoryProvider({ children }) {
  const repositories = useMemo(() => {
    const homestayRepo = createHomestayRepository();
    const menuRepo = createMenuRepository();
    const adminRepo = createAdminRepository();
    const authService = createAuthService(adminRepo);
    return { homestayRepo, menuRepo, adminRepo, authService };
  }, []);

  const [version, dispatch] = useReducer(repositoryReducer, 0);

  const value = useMemo(
    () => ({
      ...repositories,
      version,
      refresh: () => dispatch(),
    }),
    [repositories, version],
  );

  return <RepositoryContext.Provider value={value}>{children}</RepositoryContext.Provider>;
}
