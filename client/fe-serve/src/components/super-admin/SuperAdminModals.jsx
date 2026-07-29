import Modal from "../common/Modal";
import AdminForm from "./AdminForm";
import FoodForm from "./FoodForm";
import PropertyForm from "./PropertyForm";
import CategoryForm from "./CategoryForm";

export default function SuperAdminModals({
    activeModal,
    onClose,
    onPropertyNext,
    createdRestaurantId,
    onAdminCreated,
    categories,
    onFoodCreated,
    onCategoryCreated
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
                <AdminForm 
                    restaurantId={createdRestaurantId}
                    onAdminCreated={onAdminCreated}
                />
            </Modal>
        );
    }

    if (activeModal === "food") {
        return (
            <Modal title="Add Food Item" onClose={onClose}>
                <FoodForm 
                    categories={categories}
                    onFoodCreated={onFoodCreated}
                />
            </Modal>
        );
    }

    if (activeModal === "category") {
        return (
            <Modal title="Add Category" onClose={onClose}>
                <CategoryForm onCategoryCreated={onCategoryCreated} />
            </Modal>
        );
    }

    return null;
}
