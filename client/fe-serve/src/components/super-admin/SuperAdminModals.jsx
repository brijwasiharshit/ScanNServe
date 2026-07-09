import Modal from "../common/Modal";
import AdminForm from "./AdminForm";
import FoodForm from "./FoodForm";
import PropertyForm from "./PropertyForm";

export default function SuperAdminModals({
    activeModal,
    onClose,
    onPropertyNext,
}) {
    if (activeModal === "property") {
        return (
            <Modal title="Create Property" onClose={onClose}>
                <PropertyForm onNext={onPropertyNext} />
            </Modal>
        );
    }

    if (activeModal === "admin") {
        return (
            <Modal title="Create Admin" onClose={onClose}>
                <AdminForm />
            </Modal>
        );
    }

    if (activeModal === "food") {
        return (
            <Modal title="Add Food Item" onClose={onClose}>
                <FoodForm />
            </Modal>
        );
    }

    return null;
}
