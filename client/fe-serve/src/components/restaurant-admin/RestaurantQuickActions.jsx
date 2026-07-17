import { Plus } from "lucide-react";
import Button from "../common/Button";

export default function RestaurantQuickActions({ onAddTable, onAddMenuItem }) {
    return (
        <div className="mt-6 flex flex-col gap-3 sm:flex-row">
            <Button onClick={onAddTable}>
                <Plus size={18} />
                Create Table
            </Button>

            <Button variant="warning" onClick={onAddMenuItem}>
                <Plus size={18} />
                Subscribe Menu Item
            </Button>
        </div>
    );
}
