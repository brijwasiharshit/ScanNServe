/**
 * @typedef {import('../types.js').Homestay} Homestay
 */

/**
 * @typedef {Object} IHomestayRepository
 * @property {(id: string) => Homestay | undefined} getById
 * @property {() => Homestay[]} getAll
 * @property {(homestay: Homestay) => Homestay} update
 * @property {(adminId: string) => Homestay | undefined} getByAdminId
 */

export {};
