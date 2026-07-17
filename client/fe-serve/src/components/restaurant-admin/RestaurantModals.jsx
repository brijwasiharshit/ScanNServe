import Modal from "../common/Modal";
import TableForm from "./TableForm";
import MenuItemForm from "./MenuItemForm";
import EditMenuItemForm from "./EditMenuItemForm";

export default function RestaurantModals({
    activeModal,
    onClose,
    onTableSubmit,
    onMenuItemSubmit,
    initialMenuData,
    existingMenu
}) {
    if (activeModal === "table") {
        return (
            <Modal title="Create Table" onClose={onClose}>
                <TableForm onSubmit={onTableSubmit} />
            </Modal>
        );
    }

    if (activeModal === "menuItem") {
        if (initialMenuData) {
            return (
                <Modal title="Edit Menu Item" onClose={onClose}>
                    <EditMenuItemForm initialData={initialMenuData} onSubmit={onMenuItemSubmit} />
                </Modal>
            );
        }

        return (
            <Modal title="Subscribe to Food Item" onClose={onClose}>
                <MenuItemForm onSubmit={onMenuItemSubmit} existingMenu={existingMenu} />
            </Modal>
        );
    }

    return null;
}
