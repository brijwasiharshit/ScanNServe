/**
 * @typedef {import('../types.js').Admin} Admin
 */

/**
 * @typedef {Object} IAdminRepository
 * @property {() => Admin[]} getAll
 * @property {(id: string) => Admin | undefined} getById
 * @property {(admin: Omit<Admin, 'id'>) => Admin} create
 * @property {(id: string, updates: Partial<Admin>) => Admin | undefined} update
 * @property {(id: string) => boolean} remove
 */

export {};
