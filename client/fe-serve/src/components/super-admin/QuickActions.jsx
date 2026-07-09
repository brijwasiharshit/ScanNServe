import { Plus } from "lucide-react";
import Button from "../common/Button";

export default function QuickActions({ onCreateProperty, onAddFood }) {
    return (
        <div className="mt-6 flex flex-col gap-3 sm:flex-row">
            <Button onClick={onCreateProperty}>
                <Plus size={18} />
                Onboard Admin
            </Button>

            <Button variant="warning" onClick={onAddFood}>
                <Plus size={18} />
                Add Food Item
            </Button>
        </div>
    );
}
