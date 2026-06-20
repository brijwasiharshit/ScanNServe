import { createContext } from 'react';

/** @type {React.Context<null | {
 *   user: import('../domain/types.js').User | null;
 *   loginAs: (role: import('../domain/types.js').UserRole, adminId?: string) => boolean;
 *   logout: () => void;
 * }>} */
export const AuthContext = createContext(null);
