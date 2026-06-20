import { useContext } from 'react';
import { RepositoryContext } from '../context/repositoryContext.js';

export function useRepositories() {
  const ctx = useContext(RepositoryContext);
  if (!ctx) throw new Error('useRepositories must be used within RepositoryProvider');
  return ctx;
}
