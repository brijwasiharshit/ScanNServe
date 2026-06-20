/**
 * @typedef {import('../types.js').User} User
 * @typedef {import('../types.js').UserRole} UserRole
 */

/**
 * @typedef {Object} IAuthService
 * @property {() => User | null} getCurrentUser
 * @property {(role: UserRole, adminId?: string) => User | null} loginAs
 * @property {() => void} logout
 */

export {};
