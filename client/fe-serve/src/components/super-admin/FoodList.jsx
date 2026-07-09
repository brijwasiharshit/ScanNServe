import { Trash2 } from "lucide-react";
import Button from "../common/Button";

export default function FoodList({ foods }) {
    return (
        <div className="mt-6 grid gap-4">
            {foods.map((food) => (
                <div
                    key={food.id}
                    className="rounded-2xl border border-[#E2E8F0] bg-white p-5"
                >
                    <h3 className="text-lg font-semibold">{food.name}</h3>
                    <p className="text-slate-500">{food.category}</p>
                    <p className="mt-2 font-bold text-[#155E75]">Rs. {food.price}</p>

                    <Button variant="danger" className="mt-4 rounded-lg px-4 py-2">
                        <Trash2 size={16} />
                        Delete
                    </Button>
                </div>
            ))}
        </div>
    );
}
