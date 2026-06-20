import { createContext } from 'react';

/** @type {React.Context<null | {
 *   homestayRepo: import('../domain/interfaces/IHomestayRepository.js').IHomestayRepository;
 *   menuRepo: import('../domain/interfaces/IMenuRepository.js').IMenuRepository;
 *   adminRepo: import('../domain/interfaces/IAdminRepository.js').IAdminRepository;
 *   authService: import('../domain/interfaces/IAuthService.js').IAuthService;
 *   version: number;
 *   refresh: () => void;
 * }>} */
export const RepositoryContext = createContext(null);
