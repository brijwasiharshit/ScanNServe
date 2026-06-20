import { useCallback, useContext, useMemo, useState } from 'react';
import { AuthContext } from './authContext.js';
import { RepositoryContext } from './repositoryContext.js';

export function AuthProvider({ children }) {
  const { authService, refresh } = useContext(RepositoryContext);
  if (!authService) throw new Error('AuthProvider must be used within RepositoryProvider');

  const [user, setUser] = useState(() => authService.getCurrentUser());

  const loginAs = useCallback(
    (role, adminId) => {
      const result = authService.loginAs(role, adminId);
      if (result) {
        setUser({ ...result });
        refresh();
        return true;
      }
      return false;
    },
    [authService, refresh],
  );

  const logout = useCallback(() => {
    authService.logout();
    setUser(null);
    refresh();
  }, [authService, refresh]);

  const value = useMemo(() => ({ user, loginAs, logout }), [user, loginAs, logout]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
