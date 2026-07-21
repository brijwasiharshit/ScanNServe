import { Plus, TrendingUp } from "lucide-react";
import Button from "../common/Button";

export default function RestaurantQuickActions({ onAddTable, onAddMenuItem, onViewSalesAnalysis }) {
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

            <Button
                onClick={onViewSalesAnalysis}
                className="bg-gradient-to-r from-purple-600 to-indigo-600 hover:from-purple-700 hover:to-indigo-700 text-white shadow-md border-none"
            >
                <TrendingUp size={18} />
                Check Sales Analysis
            </Button>
        </div>
    );
}
